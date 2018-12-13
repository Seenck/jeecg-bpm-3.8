package jeecg.workflow.controller.bus;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jeecg.workflow.entity.bus.TBServicemanageEntity;
import jeecg.workflow.service.bus.TBServicemanageServiceI;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.workflow.model.activiti.ActivitiCom;
import org.jeecgframework.workflow.model.activiti.ProcessHandle;
import org.jeecgframework.workflow.model.activiti.Variable;
import org.jeecgframework.workflow.pojo.base.TSBusConfig;
import org.jeecgframework.workflow.pojo.base.TSPrjstatus;
import org.jeecgframework.workflow.service.ActivitiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**   
 * @Title: Controller
 * @Description: 服务上报
 * @author zhangdaihao
 * @date 2013-09-15 22:08:12
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tBServicemanageController")
public class TBServicemanageController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBServicemanageController.class);

	@Autowired
	private TBServicemanageServiceI tBServicemanageService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private ActivitiService activitiService;
	
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 服务上报列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tBServicemanage")
	public ModelAndView tBServicemanage(HttpServletRequest request) {
		return new ModelAndView("business/demo/tBServicemanageList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TBServicemanageEntity tBServicemanage,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TBServicemanageEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBServicemanage, request.getParameterMap());
		this.tBServicemanageService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除服务上报
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(TBServicemanageEntity tBServicemanage, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tBServicemanage = systemService.getEntity(TBServicemanageEntity.class, tBServicemanage.getId());
		message = "服务上报删除成功";
		tBServicemanageService.delete(tBServicemanage);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加服务上报
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(TBServicemanageEntity tBServicemanage, HttpServletRequest request,Variable var) {
		AjaxJson j = new AjaxJson();
		TSUser user = ResourceUtil.getSessionUser();
		String code = oConvertUtils.getString(request.getParameter("code"), "new");
		String taskId = oConvertUtils.getString(request.getParameter("taskId"));
		TSBusConfig busConfig = this.activitiService.getTSBusConfig(TBServicemanageEntity.class, "serviecemanage");
		TSPrjstatus prjstatus = systemService.findUniqueByProperty(TSPrjstatus.class, "code", code);
		tBServicemanage.setTSBusConfig(busConfig);
		tBServicemanage.setTSPrjstatus(prjstatus);
		tBServicemanage.setTSUser(user);
		if (StringUtil.isNotEmpty(tBServicemanage.getId())) {
			message = "服务上报更新成功";
			TBServicemanageEntity t = tBServicemanageService.get(TBServicemanageEntity.class, tBServicemanage.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(tBServicemanage, t);
				tBServicemanageService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "服务上报更新失败";
			}
		} else {
			message = "服务上报添加成功";
			tBServicemanageService.save(tBServicemanage);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		if (StringUtil.isNotEmpty(taskId)) {
			ProcessHandle processHandle = activitiService.getProcessHandle(taskId);
			Map<String, Object> map = var.getVariableMap(processHandle.getTpProcesspros());
			ActivitiCom activitiCom = activitiService.complete(taskId, map);
			if (activitiCom.getComplete()) {
				message = activitiCom.getMsg();
			} else {
				message = activitiCom.getMsg();
			}
		}
		return j;
	}

	/**
	 * 服务上报列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(TBServicemanageEntity tBServicemanage, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBServicemanage.getId())) {
			tBServicemanage = tBServicemanageService.getEntity(TBServicemanageEntity.class, tBServicemanage.getId());
			req.setAttribute("tBServicemanagePage", tBServicemanage);
		}
		return new ModelAndView("business/demo/tBServicemanage");
	}
	
	/**
	 * 服务管理待办任务页面跳转
	 * 
	 * @param icon
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "tBServicemanageTaskList")
	public ModelAndView leaveTaskList() {
		return new ModelAndView("business/demo/tBServicemanageTaskList");
	}
}
