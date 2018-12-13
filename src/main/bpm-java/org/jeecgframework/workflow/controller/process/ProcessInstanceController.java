package org.jeecgframework.workflow.controller.process;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.Query;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.UserService;
import org.jeecgframework.workflow.common.WorkFlowGlobals;
import org.jeecgframework.workflow.model.activiti.ProcessHandle;
import org.jeecgframework.workflow.model.activiti.Variable;
import org.jeecgframework.workflow.pojo.activiti.ActHiVarinst;
import org.jeecgframework.workflow.service.ActivitiService;
import org.jeecgframework.workflow.service.impl.TaskJeecgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 与流程实例相关的功能在这里
 * @author liujinghua
 *
 */
@Controller("processInstanceController")
@RequestMapping("/processInstanceController")
public class ProcessInstanceController {
	
	@Autowired
	private ActivitiService activitiService;
	@Autowired
	protected RepositoryService repositoryService;
	
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TaskJeecgService taskJeecgService;
	
	/**
	 * easyui 运行中流程列表页面
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "runningProcessList")
	public ModelAndView runningProcessList(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		return new ModelAndView("workflow/activiti/runninglist");
	}

	/**
	 * 我发起的流程列表数据
	 * @return
	 */
	@RequestMapping(params = "myRunningProcessListDataGrid")
	public void myRunningProcessListDataGrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		
		String currentUserName = ResourceUtil.getSessionUser().getUserName();
		//-update-begin-----------author:zhoujf date:20170217 for:TASK #1191 【bug】我发起的流程页面增加查询条件
		String processDefinitionId = request.getParameter("processDefinitionId");
		HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery().startedBy(currentUserName);
		if(StringUtil.isNotEmpty(processDefinitionId)){
			historicProcessInstanceQuery.processDefinitionId(processDefinitionId);
		}
		//-update-end-----------author:zhoujf date:20170217 for:TASK #1191 【bug】我发起的流程页面增加查询条件
	    List<HistoricProcessInstance> list = historicProcessInstanceQuery.orderByProcessInstanceStartTime().desc().listPage((dataGrid.getPage()-1)*dataGrid.getRows(), dataGrid.getRows());

	    StringBuffer rows = new StringBuffer();
		for(HistoricProcessInstance hi : list){
			String starttime = DateFormatUtils.format(hi.getStartTime(), "yyyy-MM-dd HH:mm:ss");
			String endtime = hi.getEndTime()==null?"":DateFormatUtils.format(hi.getEndTime(), "yyyy-MM-dd HH:mm:ss");
			
			long totalTimes = hi.getEndTime()==null?(Calendar.getInstance().getTimeInMillis()-hi.getStartTime().getTime()):(hi.getEndTime().getTime()-hi.getStartTime().getTime());
			
			long dayCount = totalTimes /(1000*60*60*24);//计算天
			long restTimes = totalTimes %(1000*60*60*24);//剩下的时间用于计于小时
			long hourCount = restTimes/(1000*60*60);//小时
			restTimes = restTimes % (1000*60*60);
			long minuteCount = restTimes / (1000*60);
			
			String spendTimes = dayCount+"天"+hourCount+"小时"+minuteCount+"分";
			ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(hi.getProcessDefinitionId()).singleResult();
			//update-begin--Author:zhoujf  Date:20151208 for：增加业务标题
			String bpmBizTitle = getHiVariable(hi.getId(),WorkFlowGlobals.BPM_BIZ_TITLE);
			rows.append("{'id':"+hi.getId()+",'prcocessDefinitionName':'"+StringUtils.trimToEmpty(processDefinition.getName())+"','startUserId':'"+hi.getStartUserId()+"','starttime':'"+starttime+"','endtime':'"+endtime+"','spendTimes':'"+spendTimes+"','processDefinitionId':'"+hi.getProcessDefinitionId() +"','processInstanceId':'"+hi.getId()+"','bpmBizTitle':'"+bpmBizTitle+"'},");
			//update-begin--Author:zhoujf  Date:20151208 for：增加业务标题
		}
		
		
		String rowStr = StringUtils.substringBeforeLast(rows.toString(), ",");
		
		JSONObject jObject = JSONObject.fromObject("{'total':"+historicProcessInstanceQuery.count()+",'rows':["+rowStr+"]}");
		responseDatagrid(response, jObject);
	}
	
	/**
	 * 历史流程列表数据
	 * @return
	 */
	@RequestMapping(params = "historyProcessListDataGrid")
	public void historyProcessListDataGrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		
		HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery();
	    List<HistoricProcessInstance> list = historicProcessInstanceQuery.orderByProcessInstanceStartTime().desc().listPage((dataGrid.getPage()-1)*dataGrid.getRows(), dataGrid.getRows());

	    StringBuffer rows = new StringBuffer();
		for(HistoricProcessInstance hi : list){
			String starttime = DateFormatUtils.format(hi.getStartTime(), "yyyy-MM-dd HH:mm:ss");
			String endtime = hi.getEndTime()==null?"":DateFormatUtils.format(hi.getEndTime(), "yyyy-MM-dd HH:mm:ss");
			
			long totalTimes = hi.getEndTime()==null?(Calendar.getInstance().getTimeInMillis()-hi.getStartTime().getTime()):(hi.getEndTime().getTime()-hi.getStartTime().getTime());
			
			long dayCount = totalTimes /(1000*60*60*24);//计算天
			long restTimes = totalTimes %(1000*60*60*24);//剩下的时间用于计于小时
			long hourCount = restTimes/(1000*60*60);//小时
			restTimes = restTimes % (1000*60*60);
			long minuteCount = restTimes / (1000*60);
			
			String spendTimes = dayCount+"天"+hourCount+"小时"+minuteCount+"分";
			ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(hi.getProcessDefinitionId()).singleResult();
			//update-begin--Author:zhoujf  Date:20151208 for：增加业务标题
			String bpmBizTitle = getHiVariable(hi.getId(),WorkFlowGlobals.BPM_BIZ_TITLE);
			rows.append("{'id':"+hi.getId()+",'prcocessDefinitionName':'"+StringUtils.trimToEmpty(processDefinition.getName())+"','startUserId':'"+hi.getStartUserId()+"','starttime':'"+starttime+"','endtime':'"+endtime+"','spendTimes':'"+spendTimes+"','processDefinitionId':'"+hi.getProcessDefinitionId() +"','processInstanceId':'"+hi.getId()+"','bpmBizTitle':'"+bpmBizTitle+"'},");
			//update-begin--Author:zhoujf  Date:20151208 for：增加业务标题
		}
		
		
		String rowStr = StringUtils.substringBeforeLast(rows.toString(), ",");
		
		JSONObject jObject = JSONObject.fromObject("{'total':"+historicProcessInstanceQuery.count()+",'rows':["+rowStr+"]}");
		responseDatagrid(response, jObject);
	}
	
	private String getHiVariable(String taskId, String variableName){
		StringBuilder sb = new StringBuilder("");
		sb.append("from ActHiVarinst VAR where VAR.procInstId =? and VAR.name = ?");
		Query query = activitiService.getSession().createQuery(sb.toString());
		query.setString(0, taskId);
		query.setString(1, variableName);
		ActHiVarinst obj = (ActHiVarinst)query.uniqueResult();
		return obj==null?"":obj.getText();
	}
	

	/** 
     * 我发起的流程，作废按钮
     * 流程追回：删除流程实例，同时删除启动表单，
     * @param processInstanceId 流程部署ID
     */
	@RequestMapping(params = "invalidProcess")
	@ResponseBody
	public AjaxJson invalidProcess(@RequestParam("processInstanceId") String processInstanceId, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		
		ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		//--update-begin---author:zhoujf-----date:20180707---------------for:设置作废状态
		runtimeService.setVariable(pi.getProcessInstanceId(), WorkFlowGlobals.BPM_STATUS, WorkFlowGlobals.PROCESS_INVALIDPROCESS_STATUS);
		//--update-end-author:zhoujf-----date:20180707---------------for:设置作废状态
		runtimeService.deleteProcessInstance(processInstanceId, "发起人流程作废");
		String message = "作废流程成功";
		j.setMsg(message);
		return j;
	}
	
	/**
     * 我发起的流程，流程追回按钮
     * 流程追回：删除流程实例，启动表单单据保留，
     * @param processInstanceId 流程部署ID
     */
	@RequestMapping(params = "callBackProcess")
	@ResponseBody
	public AjaxJson callBackProcess(@RequestParam("processInstanceId") String processInstanceId, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		
		ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		//--update-begin---author:zhoujf-----date:20180707---------------for:设置流程追回状态
		runtimeService.setVariable(pi.getProcessInstanceId(), WorkFlowGlobals.BPM_STATUS, WorkFlowGlobals.PROCESS_CALLBACKPROCESS_STATUS);
		//--update-end---author:zhoujf-----date:20180707---------------for:设置流程追回状态
		runtimeService.deleteProcessInstance(processInstanceId, "发起人流程追回");		
		String message = "流程追回成功";
		j.setMsg(message);
		return j;
	}

	
	/**
	 * easyui 委派页面
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "reassignInit")
	public ModelAndView reassignInit(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		
		String taskId = request.getParameter("taskId");
		request.setAttribute("taskId", taskId);
		return new ModelAndView("workflow/activiti/reassignInit");
	}
	
	/**
     * 进行委派操作
     * @param processInstanceId 流程部署ID
     */
	@RequestMapping(params = "reassign")
	@ResponseBody
	public AjaxJson reassign(@RequestParam("taskId") String taskId, @RequestParam("userName") String assignUserId,HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Task  task = activitiService.getTask(taskId);		
		String currentUserName = ResourceUtil.getSessionUser().getUserName();
		taskService.setOwner(task.getId(), currentUserName);
		taskService.setAssignee(task.getId(), assignUserId);
		String message = "委派成功";
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 委派人选择页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "reassignUsers")
	public String reassignUsers() {
		return "workflow/activiti/reassignUsers";
	}
	
	/**
	 * 委派人列表
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "datagridUsers")
	public void datagridUsers(TSUser tsuser,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSUser.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tsuser, request.getParameterMap());
		this.userService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * easyui 运行中流程列表数据
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "runningProcessDataGrid")
	public void runningProcessDataGrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		
		/*List<HistoricProcessInstance> historicProcessInstances = historyService
                .createHistoricProcessInstanceQuery()i
                .unfinished().list();*/
		HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery();
		
		if(StringUtils.isNotEmpty(request.getParameter("startUserId"))){
			historicProcessInstanceQuery = historicProcessInstanceQuery.startedBy(request.getParameter("startUserId"));
		}
		if(StringUtils.isNotEmpty(request.getParameter("processInstanceId"))){
			historicProcessInstanceQuery = historicProcessInstanceQuery.processInstanceId(request.getParameter("processInstanceId"));
		}
		
		String starttime_begin = request.getParameter("starttime_begin");//时间开始
		String starttime_end = request.getParameter("starttime_end");//时间结束
		if(StringUtils.isNotEmpty(starttime_begin)){
			try {
				historicProcessInstanceQuery.startedAfter(DateUtils.parseDate(starttime_begin, "yyyy-MM-dd"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(StringUtils.isNotEmpty(starttime_end)){
			try {
				historicProcessInstanceQuery.startedBefore(DateUtils.parseDate(starttime_end, "yyyy-MM-dd"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		String endtime_begin = request.getParameter("endtime_begin");//时间开始
		String endtime_end = request.getParameter("endtime_end");//时间结束
		if(StringUtils.isNotEmpty(endtime_begin)){
			try {
				historicProcessInstanceQuery.finishedAfter(DateUtils.parseDate(endtime_begin, "yyyy-MM-dd"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(StringUtils.isNotEmpty(endtime_end)){
			try {
				historicProcessInstanceQuery.finishedBefore(DateUtils.parseDate(endtime_end, "yyyy-MM-dd"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		//TODO 改造treegrid
	    List<HistoricProcessInstance> list = historicProcessInstanceQuery.unfinished().orderByProcessInstanceStartTime().desc().listPage((dataGrid.getPage()-1)*dataGrid.getRows(), dataGrid.getRows());
	    long count = historicProcessInstanceQuery.unfinished().count();
		StringBuffer rows = new StringBuffer();
		for(HistoricProcessInstance hi : list){
			String starttime = DateFormatUtils.format(hi.getStartTime(), "yyyy-MM-dd HH:mm:ss");
			String endtime = hi.getEndTime()==null?"":DateFormatUtils.format(hi.getEndTime(), "yyyy-MM-dd HH:mm:ss");
			
			
			long totalTimes = hi.getEndTime()==null?(Calendar.getInstance().getTimeInMillis()-hi.getStartTime().getTime()):(hi.getEndTime().getTime()-hi.getStartTime().getTime());
			
			long dayCount = totalTimes /(1000*60*60*24);//计算天
			long restTimes = totalTimes %(1000*60*60*24);//剩下的时间用于计于小时
			long hourCount = restTimes/(1000*60*60);//小时
			restTimes = restTimes % (1000*60*60);
			long minuteCount = restTimes / (1000*60);
			
			String spendTimes = dayCount+"天"+hourCount+"小时"+minuteCount+"分";
			ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(hi.getProcessDefinitionId()).singleResult();
			
			String isSuspended = "finished";
			String activityName = "";//当前任务名称
			String activityUser = "";//当前任务签收人
			String taskId = "";//任务ID
			if(hi.getEndTime()==null){//endtime为空表示流程实例未结束
				//update-begin--Author:zhoujf  Date:20151215 for：增加业务标题
				String bpmBizTitle = getHiVariable(hi.getId(),WorkFlowGlobals.BPM_BIZ_TITLE);
				bpmBizTitle = StringUtil.isEmpty(bpmBizTitle)?processDefinition.getName():bpmBizTitle;
				ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(hi.getId()).singleResult();
				List<Task> tasks = taskService.createTaskQuery().processInstanceId(hi.getId()).list();
				if(tasks.size()>1){
					//update-begin--Author:zhangdaihao  Date:20140826 for：针对会签或者一个流程多个分支任务（一个流程实例，同时存在多个任务情况），流程实例报错处理--------------------
					isSuspended = ""+pi.isSuspended();
					activityName = StringUtils.trimToEmpty(tasks.get(0).getName());
					activityUser = StringUtils.trimToEmpty(tasks.get(0).getAssignee());
					taskId = oConvertUtils.getString(tasks.get(0).getId());
					rows.append("{'id':'"+hi.getId()+"','iconCls':'icon-comturn','state':'closed','text':'"+bpmBizTitle+"','_parentId':'','taskId':'"+taskId+"','activityName':'"+activityName+"','activityUser':'','prcocessDefinitionName':'"+processDefinition.getName()+"','startUserId':'"+hi.getStartUserId()+"','starttime':'"+starttime+"','endtime':'"+endtime+"','spendTimes':'"+spendTimes+"','isSuspended':'','processDefinitionId':'"+hi.getProcessDefinitionId() +"','processInstanceId':'"+hi.getId()+"'},");
					int i = 1;
					for(Task task:tasks){
						isSuspended = ""+pi.isSuspended();
						activityName = StringUtils.trimToEmpty(task.getName());
						activityUser = StringUtils.trimToEmpty(task.getAssignee());
						taskId = oConvertUtils.getString(task.getId());
						rows.append("{'id':'taskid:"+hi.getId()+":"+(i++)+"','iconCls':'icon-comturn','text':'"+bpmBizTitle+"','_parentId':'"+hi.getId()+"','taskId':'"+taskId+"','activityName':'"+activityName+"','activityUser':'"+activityUser+"','prcocessDefinitionName':'"+processDefinition.getName()+"','startUserId':'"+hi.getStartUserId()+"','starttime':'"+starttime+"','endtime':'"+endtime+"','spendTimes':'"+spendTimes+"','isSuspended':'"+isSuspended+"','processDefinitionId':'"+hi.getProcessDefinitionId() +"','processInstanceId':'"+hi.getId()+"'},");
					}
					//update-end--Author:zhangdaihao  Date:20140826 for：针对会签或者一个流程多个分支任务（一个流程实例，同时存在多个任务情况），流程实例报错处理----------------------
				}else{
					for(Task task:tasks){
						isSuspended = ""+pi.isSuspended();
						activityName = StringUtils.trimToEmpty(task.getName());
						activityUser = StringUtils.trimToEmpty(task.getAssignee());
						taskId = oConvertUtils.getString(task.getId());
						rows.append("{'id':'"+hi.getId()+"','text':'"+bpmBizTitle+"','iconCls':'icon-comturn','_parentId':'','taskId':'"+taskId+"','activityName':'"+activityName+"','activityUser':'"+activityUser+"','prcocessDefinitionName':'"+processDefinition.getName()+"','startUserId':'"+hi.getStartUserId()+"','starttime':'"+starttime+"','endtime':'"+endtime+"','spendTimes':'"+spendTimes+"','isSuspended':'"+isSuspended+"','processDefinitionId':'"+hi.getProcessDefinitionId() +"','processInstanceId':'"+hi.getId()+"'},");
					}
				}
				//update-begin--Author:zhoujf  Date:20151215 for：增加业务标题
			}
		}
		
		
		String rowStr = StringUtils.substringBeforeLast(rows.toString(), ",");
		
		JSONObject jObject = JSONObject.fromObject("{'total':"+count+",'rows':["+rowStr+"]}");
		responseDatagrid(response, jObject);
	}

	/**
     * 暂停正在运行的流程实例
     * @param processInstanceId 流程部署ID
     */
	@RequestMapping(params = "suspend")
	@ResponseBody
	public AjaxJson suspend(@RequestParam("processInstanceId") String processInstanceId, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		
		runtimeService.suspendProcessInstanceById(processInstanceId);
		
		String message = "暂停成功";
		j.setMsg(message);
		return j;
	}
	
	/**
     * 启动暂停的流程实例
     * @param processInstanceId 流程部署ID
     */
	@RequestMapping(params = "restart")
	@ResponseBody
	public AjaxJson restart(@RequestParam("processInstanceId") String processInstanceId, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		
		runtimeService.activateProcessInstanceById(processInstanceId);
		
		String message = "启动成功";
		j.setMsg(message);
		return j;
	}
	
	/**
     * 作废运行中的流程实例
     * @param processInstanceId 流程部署ID
     */
	@RequestMapping(params = "close")
	@ResponseBody
	public AjaxJson close(@RequestParam("processInstanceId") String processInstanceId, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		
		//repositoryService.deleteDeployment(deploymentId, true);
		runtimeService.deleteProcessInstance(processInstanceId, "主动作废流程");
		
		String message = "作废成功";
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 我发起的流程列表页面
	 * @return
	 */
	@RequestMapping(params = "myRunningProcessList")
	public String myRunningProcessList() {
		return "workflow/activiti/myRunningProcessList";
	}
	
	/**
	 * 历史流程列表页面
	 * @return
	 */
	@RequestMapping(params = "historyProcessList")
	public String historyProcessList() {
		return "workflow/activiti/historyProcessList";
	}
	
	/**
	 * 流程跳转到哪一节点
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @return
	 */
	@RequestMapping(params = "skipNodeInit")
	public ModelAndView skipNodeInit(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String taskId = request.getParameter("taskId");
		List taskList  = activitiService.getAllTaskNode(taskId);
		request.setAttribute("taskId", taskId);
		request.setAttribute("taskList", taskList);
		return new ModelAndView("workflow/activiti/skipNodeInit");
	}
	
	/**
	 * 执行跳转操作
	 * @param taskId
	 * @param skipNode
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(params = "skipNode")
	@ResponseBody
	public AjaxJson skipNode(@RequestParam("taskId") String taskId, @RequestParam("skipTaskNode") String skipNode,HttpServletRequest request,Variable var) throws Exception {
		AjaxJson j = new AjaxJson();
		ProcessHandle processHandle = activitiService.getProcessHandle(taskId);
		//流程变量参数
		Map<String, Object> map = var.getVariableMap(processHandle.getTpProcesspros());
		if("end".equals(skipNode)){
			taskJeecgService.endProcess(taskId);
		}else{
			taskJeecgService.goProcessTaskNode(taskId,skipNode,map);
		}
		String message = "跳转成功";
		j.setMsg(message);
		return j;
	}
	
	// -----------------------------------------------------------------------------------
	// 以下各函数可以提成共用部件 (Add by Quainty)
	// -----------------------------------------------------------------------------------
	public void responseDatagrid(HttpServletResponse response, JSONObject jObject) {
		response.setContentType("application/json");
		response.setHeader("Cache-Control", "no-store");
		PrintWriter pw= null;
		try {
			pw=response.getWriter();
			pw.write(jObject.toString());
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(pw!=null){
					pw.close();
				}
			} catch (Exception e) {
			}
		}
	}
}
