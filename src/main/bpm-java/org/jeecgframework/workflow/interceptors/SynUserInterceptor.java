package org.jeecgframework.workflow.interceptors;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtil;
import org.apache.log4j.Logger;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.workflow.common.WorkFlowGlobals;
import org.jeecgframework.workflow.service.ActivitiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 同步用户到工作流，拦截器
 * @author zhangdaihao
 *
 */
public class SynUserInterceptor  implements HandlerInterceptor {
	private List<String> includeUrls;
	
	private static final Logger logger = Logger.getLogger(SynUserInterceptor.class);
	@Autowired
	private ActivitiService activitiService;
	@Autowired
	private SystemService systemService;
	private String message = null;
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		String requestPath = ResourceUtil.getJgAuthRequsetPath(arg0);// 用户访问的资源地址
		if(!includeUrls.contains(requestPath)){
			return;
		}
		//获取表单ID
		String userId = arg0.getParameter("id");
		String roleid = arg0.getParameter("roleid");
		String activitiSync = arg0.getParameter("activitiSync");
		if(requestPath.indexOf("userController.do?saveUser")!=-1){
			if (StringUtil.isEmpty(activitiSync)) {
				return;
			}
			if (StringUtil.isNotEmpty(roleid)) {
				if (StringUtil.isEmpty(userId)) {
					//添加时同步
					TSUser user = new TSUser();
					user.setUserName(arg0.getParameter("userName"));
					user.setEmail(arg0.getParameter("email"));
					user.setOfficePhone(arg0.getParameter("officePhone"));
					user.setMobilePhone(arg0.getParameter("mobilePhone"));
					TSDepart depart = new TSDepart();
					depart.setId(arg0.getParameter("TSDepart.id"));
					//TODO  待修改组织机构修改
					//user.setTSDepart(depart);
					user.setRealName(arg0.getParameter("realName"));
					user.setStatus(WorkFlowGlobals.User_Normal);
					user.setActivitiSync(Short.valueOf(activitiSync));
					try{
						activitiService.save(user, roleid, user.getActivitiSync());//同步用户到引擎
						message = "添加用户: " + user.getUserName() + "时同步到工作流成功";
						systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
					}catch (Exception e) {
						e.printStackTrace();
						logger.debug(e.getMessage());
					}
					return;
				}else{
					//更新时同步
					try{
						TSUser users = systemService.getEntity(TSUser.class, userId);
						activitiService.save(users, roleid, users.getActivitiSync());//同步用户到引擎
						message = "更新用户: " + users.getUserName() + "时同步到工作流成功";
						systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
					}catch (Exception e) {
						e.printStackTrace();
						logger.debug(e.getMessage());
					}
					return;
				}
			}
		}
	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
	}

	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2) throws Exception {
		String requestPath = ResourceUtil.getJgAuthRequsetPath(arg0);// 用户访问的资源地址
		if(!includeUrls.contains(requestPath)){
			return true;
		}
		//获取表单ID
		String userId = arg0.getParameter("id");
		if(requestPath.indexOf("userController.do?delete")!=-1){
			try{
				if (StringUtil.isEmpty(userId)){
					return true;
				}
				TSUser user = systemService.getEntity(TSUser.class, userId);
				activitiService.delete(user);
				message = "用户: " + user.getUserName() + "工作流中同步删除成功";
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			}catch (Exception e) {
				e.printStackTrace();
				logger.debug(e.getMessage());
			}
		}
		return true;
	}

	public List<String> getIncludeUrls() {
		return includeUrls;
	}

	public void setIncludeUrls(List<String> includeUrls) {
		this.includeUrls = includeUrls;
	}

}
