package org.jeecgframework.core.interceptors;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtil;
import org.apache.log4j.Logger;
import org.jeecgframework.core.annotation.JAuth;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.enums.Permission;
import org.jeecgframework.core.extend.hqlsearch.SysContextSqlConvert;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.core.util.JeecgDataAutorUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.cgform.util.SignatureUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.web.system.manager.ClientManager;
import org.jeecgframework.web.system.pojo.base.Client;
import org.jeecgframework.web.system.pojo.base.TSDataRule;
import org.jeecgframework.web.system.pojo.base.TSOperation;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.workflow.service.ActivitiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;


/**
 * 权限拦截器
 * @Date    20180523 (重构)
 * @Author  张代浩
 * @Description:  [权限拦截器：  菜单访问权限、数据权限、按钮权限、页面表单权限]   
 * 
 */
public class AuthInterceptor implements HandlerInterceptor {
	 
	private static final Logger logger = Logger.getLogger(AuthInterceptor.class);
	@Autowired
	private SystemService systemService;
	@Resource
	private ClientManager clientManager;
	@Autowired
	private ActivitiService activitiService;
	//精确匹配排除URLS
	private List<String> excludeUrls;
	//模糊匹配排除URLS
	private List<String> excludeContainUrls;
	
	

	/**
	 * 在controller前拦截
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		//-----------------------注解排除权限拦截机制---------------------------------------
		HandlerMethod handlerMethod=(HandlerMethod)object;
		JAuth jauthType =handlerMethod.getBean().getClass().getAnnotation(JAuth.class);
		if(jauthType!=null && jauthType.auth()==Permission.SKIP_AUTH){
			return true;
		}else{
			JAuth jauthMethod =handlerMethod.getMethod().getAnnotation(JAuth.class);
			if(jauthMethod!=null && jauthMethod.auth()==Permission.SKIP_AUTH){
				return true;
			}
		}
		//-----------------------注解排除权限拦截机制---------------------------------------
		
		
		//通过转换，获取用户的请求URL地址
		String requestPath = ResourceUtil.getJgAuthRequsetPath(request);
		//API接口，不做登陆验证
		if (requestPath.length()>3&&"api/".equals(requestPath.substring(0,4))) {
			return true;
		}
		
		//针对拦截器排除URLS，进行排除
		if (excludeUrls.contains(requestPath)) {
			return true;
		} else if(moHuContain(excludeContainUrls, requestPath)){
			return true;
		} else {
			Client client = clientManager.getClient(ContextHolderUtils.getSession().getId());
			TSUser currLoginUser = client!=null?client.getUser():null;
			if (currLoginUser!=null ) {
				String loginUserName = currLoginUser.getUserName();
				String loginUserId = currLoginUser.getId();
				String orgId = currLoginUser.getDepartid();
				//点击菜单ID
				String functionId = request.getParameter("clickFunctionId");
				
				//-----------------OnlineCoding--------------------------------------------------------------
				//步骤二： Online功能请求URL特殊规则，根据规则重组URL，支持多个参数
				if(requestPath.equals("cgAutoListController.do?datagrid")) {
					requestPath += "&configId=" +  request.getParameter("configId");
				}else if(requestPath.equals("cgAutoListController.do?list")) {
					requestPath += "&id=" +  request.getParameter("id");
				}
				if(requestPath.endsWith("?olstylecode=")) {
					requestPath = requestPath.replace("?olstylecode=", "");
				}
				
				//-----【BPM逻辑】追加流程节点页面按钮权限----------------------------------------------------
				String processnodeId = request.getParameter("processnodeId");
				if(StringUtil.isNotEmpty(processnodeId)){
					if(requestPath.startsWith("cgFormBuildController/ftlForm")){
						if(requestPath.indexOf("?")>0){
							requestPath = requestPath.substring(0,requestPath.indexOf("?"));
						}
					}
				}
				//-----【BPM逻辑】追加流程节点页面按钮权限----------------------------------------------------
				
				logger.debug("-----authInterceptor----requestPath------"+requestPath);
				
				
				//步骤三：  判断请求URL，是否有菜单访问权限 
				if(!systemService.loginUserIsHasMenuAuth(requestPath,functionId,loginUserId,orgId)&& !checkSign(request)){
					Boolean isAjax=isAjax(request);
					if(isAjax){
						processAjax(response);
					}else {
						response.sendRedirect(request.getSession().getServletContext().getContextPath()+"/loginController.do?noAuth");
					}
					return false;
				}
				
				
				//Admin拥有特权，数据权限、页面表单权限、按钮权限不做控制
				if(!"admin".equals(loginUserName)){
					//-----------------------------------------------------------------------------------------------------------------
					if(oConvertUtils.isEmpty(functionId)){
						//查询请求URL对应的菜单ID（因为数据权限、页面控件权限是基于菜单ID配置的数据）
						String url = request.getRequestURI().substring(request.getContextPath().length() + 1);
						functionId = systemService.getFunctionIdByUrl(url,requestPath);
						//如果通过请求URL，无法匹配出数据库中菜单ID，则不进行数据权限、页面控件权限的逻辑处理
						if(oConvertUtils.isEmpty(functionId)){
							//update-begin--Author:zhoujf  Date:20180702 for：节点权限授权
							//查询节点权限，权限并集处理，剔除授权的权限
							if(!StringUtil.isEmpty(processnodeId)){
								List<TSOperation> operations = new ArrayList<TSOperation>();
								operations = activitiService.getNodeOperaCodesByNodeId(processnodeId, operations);
								request.setAttribute(Globals.NOAUTO_OPERATIONCODES, operations);
								if(operations!=null){
									Set<String> operationCodes = new HashSet<String>();
									for (TSOperation operation : operations) {
										operationCodes.add(operation.getId());
									}
									request.setAttribute(Globals.OPERATIONCODES, operationCodes);
								}
								 request.setAttribute("dataRulecodes", new HashSet<String>());
								 return true;
							}else{
								return true;
							}
							//update-end--Author:zhoujf  Date:20180702 for：节点权限授权
						}
					}
					//-----------------------------------------------------------------------------------------------------------------
					
					//Step.1 【页面控件权限】第一部分处理页面表单和列表的页面控件权限（页面表单字段+页面按钮等控件）
					//获取菜单对应的页面控制权限（包括表单字段和操作按钮）
					//多个角色权限（并集问题），因为是反的控制，导致有admin的最大权限反而受小权限控制
					//update-begin-Author:LiShaoQing -- date:20180604 -- for:TASK #2749 【权限问题】增加组织机构角色授权页面权限的逻辑,传入OrgId参数----
					List<TSOperation> operations = systemService.getLoginOperationsByUserId(loginUserId, functionId, orgId,processnodeId);
					//update-end-Author:LiShaoQing -- date:20180604 -- for:TASK #2749 【权限问题】增加组织机构角色授权页面权限的逻辑,传入OrgId参数----
					request.setAttribute(Globals.NOAUTO_OPERATIONCODES, operations);
					if(operations!=null){
						Set<String> operationCodes = new HashSet<String>();
						for (TSOperation operation : operations) {
							operationCodes.add(operation.getId());
						}
						request.setAttribute(Globals.OPERATIONCODES, operationCodes);
					}
					
					 //Step.2  【数据权限】第二部分处理列表数据级权限 (菜单数据规则集合)
					 List<TSDataRule> MENU_DATA_AUTHOR_RULES = new ArrayList<TSDataRule>(); 
					 String MENU_DATA_AUTHOR_RULE_SQL="";
					
				 	//数据权限规则的查询
				 	//查询当前用户授权的数据规则IDS
					 //update-begin-Author:LiShaoQing -- date:20180604 -- for:TASK #2749 【权限问题】增加组织机构角色授权数据权限的逻辑,传入OrgId参数----
					 Set<String> dataRuleIds = systemService.getLoginDataRuleIdsByUserId(loginUserId, functionId, orgId);
					 //update-end-Author:LiShaoQing -- date:20180604 -- for:TASK #2749 【权限问题】增加组织机构角色授权数据权限的逻辑,传入OrgId参数----
					 
					 //-----【BPM逻辑】流程节点数据规则权限获取----------------------------------------------------
					 if(!StringUtil.isEmpty(processnodeId)){
						Set<String> nodeDataruleCodes = activitiService.getOperationCodesByNodeIdAndruleDataId(processnodeId, functionId);
						dataRuleIds.addAll(nodeDataruleCodes);
					 }
					 //-----【BPM逻辑】流程节点数据规则权限获取----------------------------------------------------
					 
					 request.setAttribute("dataRulecodes", dataRuleIds);
					 for (String dataRuleId : dataRuleIds) {
						TSDataRule dataRule = systemService.getEntity(TSDataRule.class, dataRuleId);
					 	MENU_DATA_AUTHOR_RULES.add(dataRule);
					 	MENU_DATA_AUTHOR_RULE_SQL += SysContextSqlConvert.setSqlModel(dataRule);
					}
					//【加载数据权限】数据权限规则，Hibernate字段方式
					JeecgDataAutorUtils.installDataSearchConditon(request, MENU_DATA_AUTHOR_RULES);
					//【加载数据权限】数据权限规则，Sql方式
					JeecgDataAutorUtils.installDataSearchConditon(request, MENU_DATA_AUTHOR_RULE_SQL);
				}
				return true;
			} else {
				//登录用户信息为空，跳转到用户登录超时页面
				forwardTimeOut(request, response);
				return false;
			}
		}
	}
	
	/**
	 * 在controller后拦截
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception) throws Exception {
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView modelAndView) throws Exception {

	}
	
	//update-begin--author:zhoujf  date:20170912 for：TASK #2297 【表单访问权限】我的任务里面的表单访问，改造方案---------------
	private boolean checkSign(HttpServletRequest request){
		try {
			Map<String,String> t = SignatureUtil.getSignMap(request);
			if(t.containsKey("sign")){
				String key = "";
				String sign = t.get("sign");
//				PropertiesUtil p = new PropertiesUtil("sysConfig.properties");
//				key = p.readProperty("form.audit.sign");
				key = ResourceUtil.getSessionattachmenttitle("form.audit.sign");
				if(SignatureUtil.checkSign(t, key, sign)){
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
		
	}
	//update-end--author:zhoujf  date:20170912 for：TASK #2297 【表单访问权限】我的任务里面的表单访问，改造方案---------------
	
	/**
	 * 转发
	 * 
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "forword")
	public ModelAndView forword(HttpServletRequest request) {
		return new ModelAndView(new RedirectView("loginController.do?login"));
	}

	/**
	 * 跳转： 登录超时页面
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void forwardTimeOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect(request.getSession().getServletContext().getContextPath()+"/webpage/login/timeout.jsp");
	}
	
	/**
	 * 模糊匹配字符串
	 * @param list
	 * @param key
	 * @return
	 */
	private boolean moHuContain(List<String> list,String key){
		for(String str : list){
			if(key.contains(str)){
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断当前请求是否为异步请求.
	 */
	private boolean isAjax(HttpServletRequest request){
		return oConvertUtils.isNotEmpty(request.getHeader("X-Requested-With"));
	}
	
	/**
	 * 返回Ajax格式的权限提醒消息
	 * @param response
	 */
	private void processAjax(HttpServletResponse response){
		AjaxJson json = new AjaxJson();
		json.setSuccess(false);
		json.setMsg("用户权限不足，请联系管理员!");
		PrintWriter pw = null;
		try {
			pw=response.getWriter();
			pw.write(JSONHelper.bean2json(json));
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			pw.close();
		}
	}
	
	
	public List<String> getExcludeUrls() {
		return excludeUrls;
	}

	public void setExcludeUrls(List<String> excludeUrls) {
		this.excludeUrls = excludeUrls;
	}

	public List<String> getExcludeContainUrls() {
		return excludeContainUrls;
	}

	public void setExcludeContainUrls(List<String> excludeContainUrls) {
		this.excludeContainUrls = excludeContainUrls;
	}
}
