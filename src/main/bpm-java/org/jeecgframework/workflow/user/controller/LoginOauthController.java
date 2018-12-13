package org.jeecgframework.workflow.user.controller;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.IpUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.web.system.manager.ClientManager;
import org.jeecgframework.web.system.pojo.base.Client;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.MutiLangServiceI;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.service.UserService;
import org.jeecgframework.workflow.user.entity.TSUserErpEntity;
import org.jeecgframework.workflow.util.ThirdLoginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**   
 * @Title: LoginAuthorController
 * @Description: 微信企业号网页授权登录
 * @author  zhoujf
 * @date 2017-03-11 17:40:57
 * @version V1.0   
 *
 */
@Controller("loginOauth")
@RequestMapping("/loginOauth")
public class LoginOauthController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(LoginOauthController.class);
	private static final ResourceBundle bundle = java.util.ResourceBundle.getBundle("qyweixinconfig");
	@Autowired
	private SystemService systemService;
	@Autowired
	private UserService userService;
	@Autowired
	private MutiLangServiceI mutiLangService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 微信企业号网页授权登录
	 * @return
	 */
	@RequestMapping(params = "/link", method = {RequestMethod.GET, RequestMethod.POST})
	public String link(HttpServletRequest request,HttpServletResponse response) {
		String retStr=null;
		String code=request.getParameter("code");
		String corpid=bundle.getString("qyweixin.3rdlogin.corpid");
		String hrefurl="";
		if(oConvertUtils.isEmpty(code)){
			try {
				String state="";
				String redirect_uri=URLEncoder.encode(bundle.getString("qyweixin.3rdlogin.domain")+"/loginOauth.do?link","utf-8");
				//String redirect_uri=bundle.getString("qyweixin.3rdlogin.domain")+"/loginAuthorController.do?link";
				String corpcodeUrl=bundle.getString("qyweixin.3rdlogin.corpcode");
				hrefurl=corpcodeUrl.replace("CORPID", corpid).replace("REDIRECT_URI",redirect_uri).replace("STATE",state);
				logger.info("登录Oauth:"+hrefurl);
				response.sendRedirect(hrefurl);
				return null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			String corpsecret=bundle.getString("qyweixin.3rdlogin.corpsecret");
			JSONObject tokenJson=ThirdLoginUtil.getAccessToken(corpid, corpsecret);
		/*	JSONObject tokenJson=new JSONObject();
			tokenJson.put("access_token", "nHWWyG00udex-8XVH8ZhWez1Pj0WiFI5fi7q7ZeJ43jZxiP0sibdOAzZlY_H1ck");*/
			if(tokenJson.containsKey("access_token")){
				String accessToken=tokenJson.getString("access_token");
				JSONObject json=ThirdLoginUtil.getCorpUser(accessToken, code);
				//json.put("UserId", "ileida");
				if(json.containsKey("UserId")){
					String UserId=json.getString("UserId");
					logger.info("登录用户userId:"+UserId);
					try {
						thirdLogin(UserId,request,response);
						retStr= "workflow/mobile/app/task/task-running";
					} catch (BusinessException e) {
						logger.info(e.getMessage());
						retStr="workflow/mobile/login/login";
					}
				}else{
					logger.info("请求获取user信息没数据");
					retStr="workflow/mobile/login/login";
				}
			}else{
				logger.info("请求token没数据");
				retStr="workflow/mobile/login/login";
			}
		}
		return retStr;
	}
	
	private void thirdLogin(String uuid,HttpServletRequest request,HttpServletResponse response) {
		if(StringUtil.isNotEmpty(uuid)){
			String hql = "from TSUserErpEntity where erpNo = ? and sysCode = ?";
			List<TSUserErpEntity> correlaList=this.systemService.findHql(hql, uuid,"qyweixin");
			if(null==correlaList||correlaList.size()==0){
				throw new BusinessException("无匹配用户");
			}
			TSUserErpEntity correlation=correlaList.get(0);
			String userId=correlation.getUserId();
			TSUser u = userService.findUniqueByProperty(TSUser.class, "userName", userId);
            if(u == null) {
            	throw new BusinessException("绑定用户不存在");
            }
            TSUser u2 = userService.getEntity(TSUser.class, u.getId());
            if (u != null&&u2.getStatus()!=0) {
            		//处理用户有多个组织机构的情况，以弹出框的形式让用户选择
                  Map<String, Object> attrMap = new HashMap<String, Object>();
                  String orgId = request.getParameter("orgId");
                  if (oConvertUtils.isEmpty(orgId)) { // 没有传组织机构参数，则获取当前用户的组织机构
                      Long orgNum = systemService.getCountForJdbc("select count(1) from t_s_user_org where user_id = '" + u.getId() + "'");
                      if (orgNum > 1) {
                          attrMap.put("orgNum", orgNum);
                          attrMap.put("user", u2);
                          //TODO 多个组织机构默认第一个组织机构
                          List<TSDepart> orgList = systemService.findHql("select d from TSDepart d,TSUserOrg uo where d.id=uo.tsDepart.id and uo.tsUser.id=?", u.getUserName());
                          orgId = orgList.get(0).getId();
                          saveLoginSuccessInfo(request,response, u2, orgId);
                      } else {
                          Map<String, Object> userOrgMap = systemService.findOneForJdbc("select org_id as orgId from t_s_user_org where user_id=?", u2.getId());
                          saveLoginSuccessInfo(request,response, u2, (String) userOrgMap.get("orgId"));
                      }
                  } else {
                      attrMap.put("orgNum", 1);
                      saveLoginSuccessInfo(request,response, u2, orgId);
                  }
            } else {
            	throw new BusinessException(mutiLangService.getLang("common.username.or.password.error"));
            }
		}
	}
	
	/**
     * 保存用户登录的信息，并将当前登录用户的组织机构赋值到用户实体中；
     * @param req request
     * @param user 当前登录用户
     * @param orgId 组织主键
     */
    private void saveLoginSuccessInfo(HttpServletRequest request,HttpServletResponse response, TSUser user, String orgId) {
        TSDepart currentDepart = systemService.get(TSDepart.class, orgId);
        user.setCurrentDepart(currentDepart);

        HttpSession session = request.getSession();
        session.setAttribute(ResourceUtil.LOCAL_CLINET_USER, user);
        String message = "企业微信 登录："+mutiLangService.getLang("common.user") + ": " + user.getUserName() + "["
                + currentDepart.getDepartname() + "]" + mutiLangService.getLang("common.login.success");

        //当前session为空 或者 当前session的用户信息与刚输入的用户信息一致时，则更新Client信息
        Client clientOld = ClientManager.getInstance().getClient(session.getId());
		if(clientOld == null || clientOld.getUser() ==null ||user.getUserName().equals(clientOld.getUser().getUserName())){
			Client client = new Client();
	        client.setIp(IpUtil.getIpAddr(request));
	        client.setLogindatetime(new Date());
	        client.setUser(user);
	        ClientManager.getInstance().addClinet(session.getId(), client);
		} else {//如果不一致，则注销session并通过session=req.getSession(true)初始化session
			ClientManager.getInstance().removeClinet(session.getId());
			session.invalidate();
			session=request.getSession(true);//session初始化
			session.setAttribute(ResourceUtil.LOCAL_CLINET_USER, user);
			session.setAttribute("randCode",request.getParameter("randCode"));//保存验证码
			thirdLogin(user.getUserName(),request,response);
		}
        // 添加登陆日志
        systemService.addLog(message, Globals.Log_Type_LOGIN, Globals.Log_Leavel_INFO);
    }
	
//	@RequestMapping(params = "toLogin")
	public String toLogin(HttpServletRequest request,HttpServletResponse response) {
		String mlogin= "workflow/mobile/login/login";
		String code=request.getParameter("code");
		String accessToken="";
		try {
			JSONObject json=ThirdLoginUtil.getCorpUser(accessToken, code);
			if(json.containsKey("UserId")){
				String UserId=json.getString("UserId");
				thirdLogin(UserId,request,response);
				mlogin= "workflow/mobile/app/task/task-running";
			}
		} catch (BusinessException e) {
			logger.info(e.getMessage());
		} 
		return mlogin;
			
	}
	
}
