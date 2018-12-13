package jeecg.workflow.controller.bus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jeecg.workflow.entity.bus.TBBormoney;
import jeecg.workflow.entity.bus.TBBusinesstrip;
import jeecg.workflow.entity.bus.TBLeave;
import jeecg.workflow.entity.bus.TBPurchase;
import jeecg.workflow.entity.bus.TBPurchaseDetail;
import jeecg.workflow.service.bus.BusinessService;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.DataUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSType;
import org.jeecgframework.web.system.pojo.base.TSTypegroup;
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
 * @ClassName: busController
 * @Description: TODO(演示业务处理类)
 * @author jeecg
 */
@Controller
@RequestMapping("/busController")
public class BusinessController extends BaseController {
	private static final Logger logger = Logger.getLogger(BusinessController.class);
	@Autowired
	private SystemService systemService;
	@Autowired
	private ActivitiService activitiService;
	@Autowired
	private BusinessService busniessService;
	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * 请假申请页面跳转
	 */
	@RequestMapping(params = "aoruleave")
	public ModelAndView aoruleave(TBLeave leave, HttpServletRequest request) {
		String taskId = oConvertUtils.getString(request.getParameter("taskId"));
		if (leave.getId() != null) {
			leave = systemService.getEntity(TBLeave.class, leave.getId());
		}
		if (StringUtil.isNotEmpty(taskId)) {
			String businessKey = activitiService.getBusinessKeyByTask(taskId);
			leave = systemService.getEntity(TBLeave.class, businessKey);
			
		}
		TSTypegroup typegroup = systemService.findUniqueByProperty(TSTypegroup.class, "typegroupcode", "leave");
		List<TSType> typeList = typegroup.getTSTypes();
		request.setAttribute("typeList", typeList);
		request.setAttribute("taskId", taskId);
		request.setAttribute("leave", leave);
		return new ModelAndView("business/demobus/leave");

	}
	
	
	/**
	 * 请假申请页面跳转
	 */
	@RequestMapping(params = "leaveHasButton")
	public ModelAndView leaveHasButton(TBLeave leave, HttpServletRequest request) {
		String id = oConvertUtils.getString(request.getParameter("id"));
		leave = systemService.getEntity(TBLeave.class, id);
		TSTypegroup typegroup = systemService.findUniqueByProperty(TSTypegroup.class, "typegroupcode", "leave");
		List<TSType> typeList = typegroup.getTSTypes();
		request.setAttribute("typeList", typeList);
		request.setAttribute("leave", leave);
		return new ModelAndView("business/demobus/leaveHasButton");

	}

	/**
	 * 请假列表页面跳转
	 */
	@RequestMapping(params = "leaveList")
	public ModelAndView leaveList(HttpServletRequest request) {
		return new ModelAndView("business/demobus/leaveList");
	}

	/**
	 * 请假列表数据
	 */

	@RequestMapping(params = "leaveGrid")
	public void leaveGrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TBLeave.class, dataGrid);
		TSUser user = ResourceUtil.getSessionUser();
		String begintime=oConvertUtils.getString(request.getParameter("begintime"));
		String endtime=oConvertUtils.getString(request.getParameter("endtime"));
		if (!begintime.equals("") || !endtime.equals("")) {
			cq.ge("begintime", DataUtils.str2Date(begintime, DataUtils.date_sdf));
			cq.le("endtime", DataUtils.str2Date(endtime, DataUtils.date_sdf));
		}
		cq.eq("TSUser.id", user.getId());
		cq.add();
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	/**
	 * 保存请假申请
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "saveLeave")
	@ResponseBody
	public AjaxJson saveLeave(TBLeave leave, HttpServletRequest request, Variable var) {
		AjaxJson j = new AjaxJson();
		
		String taskId = oConvertUtils.getString(request.getParameter("taskId"));
		//[1].获取请假流程的，流程业务配置关系数据
		TSBusConfig busConfig = this.activitiService.getTSBusConfig(TBLeave.class, "leave");
		//[2].获取业务数据的状态
		String code = oConvertUtils.getString(request.getParameter("code"), "new");
		TSPrjstatus prjstatus = systemService.findUniqueByProperty(TSPrjstatus.class, "code", code);
		//[3].获取业务流程创建人
		TSUser user = ResourceUtil.getSessionUser();
		leave.setTSBusConfig(busConfig);
		leave.setTSPrjstatus(prjstatus);
		leave.setTSUser(user);
		if (StringUtil.isNotEmpty(leave.getId())) {
			systemService.updateEntitie(leave);
			message = "更新成功";
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} else {
			message = "添加成功";
			systemService.save(leave);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);

		}
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
		j.setMsg(message);

		return j;
	}

	
	
	/**
	 * 保存请假申请
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "updateLeave")
	@ResponseBody
	public AjaxJson updateLeave(TBLeave leave, HttpServletRequest request, Variable var) {
		AjaxJson j = new AjaxJson();
		String taskId = oConvertUtils.getString(request.getParameter("taskId"));
		//[1].获取请假流程的，流程业务配置关系数据
		TSBusConfig busConfig = this.activitiService.getTSBusConfig(TBLeave.class, "leave");
		//[2].获取业务数据的状态
		String code = oConvertUtils.getString(request.getParameter("code"), "new");
		TSPrjstatus prjstatus = systemService.findUniqueByProperty(TSPrjstatus.class, "code", code);
		//[3].获取业务流程创建人
		TSUser user = ResourceUtil.getSessionUser();
		leave.setTSBusConfig(busConfig);
		leave.setTSPrjstatus(prjstatus);
		leave.setTSUser(user);
		if (StringUtil.isNotEmpty(leave.getId())) {
			systemService.updateEntitie(leave);
			message = "更新成功";
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} else {
			message = "添加成功";
			systemService.save(leave);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);

		}
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
		j.setMsg(message);

		return j;
	}
	
	/**
	 * 删除请假
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "delLeave")
	@ResponseBody
	public AjaxJson delLeave(TBLeave leave, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		leave = systemService.getEntity(TBLeave.class, leave.getId());
		message = "删除成功";
		systemService.delete(leave);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		j.setMsg(message);
		return j;
	}

	/**
	 * 请假待办任务页面跳转
	 * 
	 * @param icon
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "leaveTaskList")
	public ModelAndView leaveTaskList() {
		return new ModelAndView("business/demobus/leavetaskList");
	}

	/**
	 * 出差列表页面跳转
	 */
	@RequestMapping(params = "bustripList")
	public ModelAndView bustripList(HttpServletRequest request) {
		return new ModelAndView("business/demobus/bustripList");
	}

	/**
	 * 出差列表数据
	 */

	@RequestMapping(params = "bustripGrid")
	public void bustripGrid(HttpServletRequest request,HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TBBusinesstrip.class, dataGrid);
		String begintime=oConvertUtils.getString(request.getParameter("begintime"));
		String endtime=oConvertUtils.getString(request.getParameter("endtime"));
		if (!begintime.equals("") || !endtime.equals("")) {
			cq.ge("begintime", DataUtils.str2Date(begintime, DataUtils.date_sdf));
			cq.le("endtime", DataUtils.str2Date(endtime, DataUtils.date_sdf));
		}
		//update-begin-author:taoYan date:20170803 for:查询条件（出差事由添加）--
		String bustripreson=oConvertUtils.getString(request.getParameter("bustripreson"));
		if(!"".equals(bustripreson)){
			cq.eq("bustripreson", bustripreson);
		}
		//update-end-author:taoYan date:20170803 for:查询条件（出差事由添加）--
		cq.add();
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 请假申请页面跳转
	 */
	@RequestMapping(params = "aorubustrip")
	public ModelAndView aorubustrip(TBBusinesstrip bustrip, HttpServletRequest request) {
		if (bustrip.getId() != null) {
			bustrip = systemService.getEntity(TBBusinesstrip.class, bustrip.getId());
			request.setAttribute("bustrip", bustrip);
		}
		return new ModelAndView("business/demobus/bustrip");
	}

	/**
	 * 保存出差申请
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "saveBustrip")
	@ResponseBody
	public AjaxJson saveBustrip(TBBusinesstrip bustrip, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		TSUser user = ResourceUtil.getSessionUser();
		String code = oConvertUtils.getString(request.getParameter("code"), "new");
		TSBusConfig busConfig = activitiService.getTSBusConfig(TBBusinesstrip.class, "bustrip");
		TSPrjstatus prjstatus = systemService.findUniqueByProperty(TSPrjstatus.class, "code", code);
		bustrip.setTSBusConfig(busConfig);
		bustrip.setTSPrjstatus(prjstatus);
		bustrip.setTSUser(user);
		if (StringUtil.isNotEmpty(bustrip.getId())) {
			systemService.updateEntitie(bustrip);
			message = "更新成功";
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} else {
			message = "添加成功";
			systemService.save(bustrip);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);

		}
		j.setMsg(message);

		return j;
	}

	/**
	 * 删除出差
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "delBustrip")
	@ResponseBody
	public AjaxJson delBustrip(TBBusinesstrip bustrip, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		bustrip = systemService.getEntity(TBBusinesstrip.class, bustrip.getId());
		message = "删除成功";
		systemService.delete(bustrip);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		j.setMsg(message);
		return j;
	}

	/**
	 * 出差待办任务页面跳转
	 * 
	 * @param icon
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "bustripTaskList")
	public ModelAndView bustripTaskList() {
		return new ModelAndView("business/demobus/bustriptaskList");
	}

	/**
	 * 总经理审批页面跳转
	 */
	@RequestMapping(params = "managerApp")
	public ModelAndView managerApp(HttpServletRequest request) {
		String taskId = oConvertUtils.getString(request.getParameter("taskId"));
		String businessKey = activitiService.getBusinessKeyByTask(taskId);
		TBBusinesstrip tbBusinesstrip = systemService.getEntity(TBBusinesstrip.class, businessKey);
		Double bormoney = tbBusinesstrip.getBustripmoney();
		if (bormoney > 0) {
			request.setAttribute("bormoney", "true");
		} else {
			request.setAttribute("bormoney", "false");
		}
		request.setAttribute("tbBusinesstrip", tbBusinesstrip);
		request.setAttribute("taskId", taskId);
		return new ModelAndView("business/demobus/managerApp");
	}

	/**
	 * 部门领导审批页面跳转
	 */
	@RequestMapping(params = "deptLeaderApp")
	public ModelAndView deptLeaderApp(HttpServletRequest request) {
		String taskId = oConvertUtils.getString(request.getParameter("taskId"));
		String businessKey = activitiService.getBusinessKeyByTask(taskId);
		TBBusinesstrip tbBusinesstrip = systemService.getEntity(TBBusinesstrip.class, businessKey);
		request.setAttribute("tbBusinesstrip", tbBusinesstrip);
		request.setAttribute("taskId", taskId);
		return new ModelAndView("business/demobus/deptLeaderApp");
	}

	/**
	 * 调整申请跳转
	 */
	@RequestMapping(params = "modifyApply")
	public ModelAndView modifyApply(HttpServletRequest request) {
		String taskId = oConvertUtils.getString(request.getParameter("taskId"));
		String businessKey = activitiService.getBusinessKeyByTask(taskId);
		TBBusinesstrip tbBusinesstrip = systemService.getEntity(TBBusinesstrip.class, businessKey);
		request.setAttribute("tbBusinesstrip", tbBusinesstrip);
		request.setAttribute("taskId", taskId);
		return new ModelAndView("business/demobus/modifyApply");
	}
	
	/**
	 * 调整申请跳转【带按钮】
	 */
	@RequestMapping(params = "modifyApplyHasButton")
	public ModelAndView modifyApplyHasButton(HttpServletRequest request) {
		String id = oConvertUtils.getString(request.getParameter("id"));
		TBBusinesstrip tbBusinesstrip = systemService.getEntity(TBBusinesstrip.class, id);
		request.setAttribute("tbBusinesstrip", tbBusinesstrip);
		return new ModelAndView("business/demobus/modifyApplyHasButton");
	}

	/**
	 * 调整申请保存
	 */
	@RequestMapping(params = "modifyApplySave")
	@ResponseBody
	public AjaxJson modifyApplySave(HttpServletRequest request, Variable var, TBBusinesstrip tbBusinesstrip) {
		AjaxJson j = new AjaxJson();
		String taskId = oConvertUtils.getString(request.getParameter("taskId"));
		saveBustrip(tbBusinesstrip, request);
		ProcessHandle processHandle = activitiService.getProcessHandle(taskId);
		Map<String, Object> map = var.getVariableMap(processHandle.getTpProcesspros());
		ActivitiCom activitiCom = activitiService.complete(taskId, map);
		if (activitiCom.getComplete()) {
			j.setMsg(activitiCom.getMsg());
		} else {
			j.setMsg(activitiCom.getMsg());
		}
		return j;
	}
	
	
	/**
	 * 调整申请保存
	 */
	@RequestMapping(params = "updateBusinesstrip")
	@ResponseBody
	public AjaxJson updateBusinesstrip(HttpServletRequest request, Variable var, TBBusinesstrip tbBusinesstrip) {
		AjaxJson j = new AjaxJson();
		saveBustrip(tbBusinesstrip, request);
		return j;
	}

	/**
	 * 借款申请页面跳转
	 */
	@RequestMapping(params = "aorubormoney")
	public ModelAndView aorubormoney(TBBormoney tbBormoney, HttpServletRequest request) {
		String taskId = oConvertUtils.getString(request.getParameter("taskId"));
		if (tbBormoney.getId() != null) {
			tbBormoney = systemService.getEntity(TBBormoney.class, tbBormoney.getId());
			request.setAttribute("tbBormoney", tbBormoney);
		}
		request.setAttribute("taskId", taskId);
		return new ModelAndView("business/demobus/bormoney");
	}
	
	/**
	 * 借款申请页面跳转[页面嵌入按钮]
	 */
	@RequestMapping(params = "bormoneyHasButton")
	public ModelAndView bormoneyHasButton(TBBormoney tbBormoney, HttpServletRequest request) {
		String taskId = oConvertUtils.getString(request.getParameter("taskId"));
		
		String bormoney_id = request.getParameter("bormoney_id");
		if (bormoney_id != null) {
			tbBormoney = systemService.getEntity(TBBormoney.class, bormoney_id);
			request.setAttribute("tbBormoney", tbBormoney);
		}
		request.setAttribute("taskId", taskId);
		return new ModelAndView("business/demobus/bormoneyHasButton");
	}

	/**
	 * 保存借款申请
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "saveBormoney")
	@ResponseBody
	public AjaxJson saveBormoney(TBBormoney tbBormoney, HttpServletRequest request, Variable var) {
		AjaxJson j = new AjaxJson();
		TSUser user = ResourceUtil.getSessionUser();
		String taskId = oConvertUtils.getString(request.getParameter("taskId"));
		//获取流程与业务配置
		TSBusConfig busConfig = activitiService.getTSBusConfig(TBBormoney.class, "bormoney");
		//获取流程，流转状态[新建]
		TSPrjstatus prjstatus = systemService.findUniqueByProperty(TSPrjstatus.class, "code", "new");
		tbBormoney.setTSBusConfig(busConfig);
		tbBormoney.setTSPrjstatus(prjstatus);
		tbBormoney.setTSUser(user);
		tbBormoney.setCreatetime(DataUtils.gettimestamp());
		if (StringUtil.isNotEmpty(tbBormoney.getId())) {
			systemService.updateEntitie(tbBormoney);
			message = "更新成功";
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} else {
			message = "添加成功";
			systemService.save(tbBormoney);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		if (StringUtil.isNotEmpty(taskId)) {
			//根据taskId封装ProcessHandle对象
			ProcessHandle processHandle = activitiService.getProcessHandle(taskId);
			Map<String, Object> map = var.getVariableMap(processHandle.getTpProcesspros());
			//update--begin---author:scott-----date:20160309------for:将子流程数据ID,存在流程变量里面-----
			map.put("sub_id", tbBormoney.getId());
			//update--end---author:scott-----date:20160309------for:将子流程数据ID,存在流程变量里面-----
			ActivitiCom activitiCom = activitiService.complete(taskId, map);
			activitiService.updateHiProcInstBusKey(processHandle.getBusinessKey(), tbBormoney.getId());
			//获取流程，流转状态[办理中]
			TSPrjstatus prjstatu = systemService.findUniqueByProperty(TSPrjstatus.class, "code", "doing");
			tbBormoney.setTSPrjstatus(prjstatu);
			//修改流程状态为{办理中}
			systemService.updateEntitie(tbBormoney);
			if (activitiCom.getComplete()) {
				message = activitiCom.getMsg();
			} else {
				message = activitiCom.getMsg();
			}
		}
		j.setMsg(message);
		return j;
	}
	
	
	/**
	 * 保存借款申请[启动流程]
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "startBormoney")
	@ResponseBody
	public AjaxJson startBormoney(TBBormoney tbBormoney, HttpServletRequest request, Variable var) {
		AjaxJson j = new AjaxJson();
		TSUser user = ResourceUtil.getSessionUser();
		TSBusConfig busConfig = activitiService.getTSBusConfig(TBBormoney.class, "bormoney");
		TSPrjstatus prjstatus = systemService.findUniqueByProperty(TSPrjstatus.class, "code", "new");
		tbBormoney.setTSBusConfig(busConfig);
		tbBormoney.setTSPrjstatus(prjstatus);
		tbBormoney.setTSUser(user);
		tbBormoney.setCreatetime(DataUtils.gettimestamp());
		if (StringUtil.isNotEmpty(tbBormoney.getId())) {
			systemService.updateEntitie(tbBormoney);
			message = "更新成功";
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} else {
			message = "添加成功";
			systemService.save(tbBormoney);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		
		//------------------------------------------
		//主流的任务ID获取到，把子流程的业务数据ID，存入主流程中
		String taskId = request.getParameter("taskId");
		//------------------------------------------
		
		j.setMsg(message);
		return j;
	}

	/**
	 * 借款申请页面跳转
	 * 
	 * @param icon
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "bormoneyList")
	public ModelAndView bormoneyList() {
		return new ModelAndView("business/demobus/bormoneyList");
	}

	/**
	 * 借款申请列表数据
	 */

	@RequestMapping(params = "bormoneyGrid")
	public void bormoneyGrid(TBBormoney bormoney,HttpServletRequest request,HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TBBormoney.class, dataGrid);
		String createtime=oConvertUtils.getString(request.getParameter("createtime"));
		if (!createtime.equals("") ) {
			cq.ge("createtime", DataUtils.str2Date(createtime, DataUtils.date_sdf));
			
		}
		cq.add();
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除借款
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "delBormoney")
	@ResponseBody
	public AjaxJson delBormoney(TBBormoney tbBormoney, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tbBormoney = systemService.getEntity(TBBormoney.class, tbBormoney.getId());
		message = "删除成功";
		systemService.delete(tbBormoney);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		j.setMsg(message);
		return j;
	}

	
	/**
	 * 借款待办任务页面跳转
	 * 
	 * @param icon
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "bormoneyTaskList")
	public ModelAndView bormoneyTaskList() {
		return new ModelAndView("business/demobus/bormoneytaskList");
	}
	/**
	 * 采购列表跳转
	 * @param icon
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "purchaseList")
	public ModelAndView purchaseList() {
		return new ModelAndView("business/demobus/purcList");
	}
	/**
	 * 采购列表跳转
	 * @param icon
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "purchaseDetailList")
	public ModelAndView purchaseDetailList(HttpServletRequest request) {
		String pruchaseid=oConvertUtils.getString(request.getParameter("purchaseid"));
		request.setAttribute("pruchaseid",pruchaseid);
		return new ModelAndView("business/demobus/purchaseDetailList");
	}
	/**
	 * 采购添加页面跳转
	 * @param icon
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "purchaseaddorupdate")
	public ModelAndView purchaseaddorupdate(HttpServletRequest request) {
		String pruchaseid=oConvertUtils.getString(request.getParameter("purchaseid"));
		request.setAttribute("purchaseid",pruchaseid);
		return new ModelAndView("business/demobus/purchasedetail");
	}
	/**
	 * 采购列表数据
	 */

	@RequestMapping(params = "purchaseGrid")
	public void purchaseGrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TBPurchase.class, dataGrid);
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	/**
	 * 采购物品详细列表数据
	 */

	@RequestMapping(params = "purchaseDetialGrid")
	public void purchaseDetialGrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String pruchaseid=oConvertUtils.getString(request.getParameter("pruchaseid"));
		CriteriaQuery cq = new CriteriaQuery(TBPurchaseDetail.class, dataGrid);
		cq.eq("TBPurchase.id", pruchaseid);
		cq.add();
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	/**
	 * 订单详细保存
	 */
	@RequestMapping(params = "savePurchase")
	@ResponseBody
	public AjaxJson savePurchase(TBPurchaseDetail tbPurchaseDetail, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tbPurchaseDetail.setPurctotalprice(tbPurchaseDetail.getPurcprice()*tbPurchaseDetail.getPurcnum());
		message = "物品: " + tbPurchaseDetail.getPurcname() + "添加成功";
		systemService.save(tbPurchaseDetail);
		TBPurchase tbPurchase=systemService.getEntity(TBPurchase.class,tbPurchaseDetail.getTBPurchase().getId());
		tbPurchase.setTotalprice(oConvertUtils.getDou(tbPurchase.getTotalprice(),0.0)+tbPurchaseDetail.getPurctotalprice());
		systemService.saveOrUpdate(tbPurchase);
		systemService.addLog(message, Globals.Log_Type_INSERT,
		Globals.Log_Leavel_INFO);
		return j;
	}
	/**
	 * 订单详细保存
	 */
	@RequestMapping(params = "createPurchase")
	@ResponseBody
	public AjaxJson createPurchase(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		TBPurchase tbPurchase=new TBPurchase();
		TSPrjstatus prjstatus = systemService.findUniqueByProperty(TSPrjstatus.class, "code", "new");
		TSBusConfig busConfig = activitiService.getTSBusConfig(TBPurchase.class, "pruchase");
		TSUser user = ResourceUtil.getSessionUser();
		tbPurchase.setTSUser(user);
		tbPurchase.setCreatetime(DataUtils.gettimestamp());
		tbPurchase.setTSBusConfig(busConfig);
		tbPurchase.setTSPrjstatus(prjstatus);
		systemService.save(tbPurchase);
		systemService.saveOrUpdate(tbPurchase);
		return j;
	}
	/**
	 * 采购待办任务页面跳转
	 * 
	 * @param icon
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "purchaseTaskList")
	public ModelAndView purchaseTaskList() {
		return new ModelAndView("business/demobus/purchasetaskList");
	}
	/**
	 * 总经理审批页面跳转
	 */
	@RequestMapping(params = "purchaseapp")
	public ModelAndView purchaseapp(HttpServletRequest request) {
		String taskId = oConvertUtils.getString(request.getParameter("taskId"));
		String businessKey = activitiService.getBusinessKeyByTask(taskId);
		TBPurchase tbPurchase=systemService.getEntity(TBPurchase.class,businessKey);
		Double money = tbPurchase.getTotalprice();
		request.setAttribute("money",money);
		request.setAttribute("taskId", taskId);
		return new ModelAndView("business/demobus/purchaseapp");
	}
	
	/**
	 * 获取商机信息（商机编码）
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getBusinessCode")
	@ResponseBody
	public AjaxJson getBusinessCode(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String code = busniessService.getMaxBusCode();
		String businessCode = "SJ"+formatter.format(new Date());
		if(code != null && !"".equals(code)){ 
			int len = code.length();
			String subStr = code.substring(len-4, len);
			 String subCode = Integer.parseInt(subStr)+1+"";
			 if(subCode.length() == 1){
				 subCode = "000"+subCode;
			 }else if(subCode.length() == 2){
				 subCode = "00"+subCode;
			 }else if(subCode.length() == 3){
				 subCode = "0"+subCode;
			 }
			 businessCode = businessCode + subCode;
		}else{
			businessCode = businessCode+"0001";
		}
		j.setObj(businessCode);
		return j;
	}
}
