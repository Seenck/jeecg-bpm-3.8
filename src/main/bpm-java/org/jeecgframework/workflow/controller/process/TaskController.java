package org.jeecgframework.workflow.controller.process;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.bpmn.behavior.ParallelGatewayActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.ParallelMultiInstanceBehavior;
import org.activiti.engine.impl.bpmn.behavior.SequentialMultiInstanceBehavior;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.CommonRandomUtil;
import org.apache.commons.lang.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.online.util.FreemarkerHelper;
import org.jeecgframework.core.util.PropertiesUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.cgform.util.SignatureUtil;
import org.jeecgframework.web.system.pojo.base.TSRoleUser;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.workflow.common.ProcDealStyleEnum;
import org.jeecgframework.workflow.common.WorkFlowGlobals;
import org.jeecgframework.workflow.dao.ActivitiDao;
import org.jeecgframework.workflow.model.activiti.ProcessHandle;
import org.jeecgframework.workflow.pojo.base.TPProcessnode;
import org.jeecgframework.workflow.service.ActivitiService;
import org.jeecgframework.workflow.service.impl.TaskJeecgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


/**
 * 
 * @ClassName: TaskController
 * @Description: 我的任务
 * @author scott
 * @date 2012-8-19 下午04:20:06
 * 
 */
@Controller("taskController")
@RequestMapping("/taskController")
public class TaskController {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	protected RepositoryService repositoryService;
	@Autowired
	protected RuntimeService runtimeService;
	@Autowired
	private ActivitiService activitiService;
	@Autowired
	protected TaskService taskService;
	@Autowired
	protected IdentityService identityService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private HistoryService historyService; 
	@Autowired
	private ActivitiDao activitiDao;
	@Autowired
	private TaskJeecgService taskJeecgService;
	/**
	 * 跳转到我的任务列表(总任务列表)
	 */
	@RequestMapping(params = "goTaskListTab")
	public ModelAndView goTaskListTab(HttpServletRequest request) {
		return new ModelAndView("workflow/task/taskList-tab");
	}
	
	/**
	 * 跳转到我的任务列表- 我的任务（个人）
	 */
	@RequestMapping(params = "goMyTaskList")
	public ModelAndView goMyTaskList(HttpServletRequest request) {
		return new ModelAndView("workflow/task/taskList-person");
	}
	
	/**
	 * 跳转到我的任务列表 - 我的任务（角色组）需要签收
	 */
	@RequestMapping(params = "goGroupTaskList")
	public ModelAndView goGroupTaskList(HttpServletRequest request) {
		//TaskQuery userRoleTask = taskService.createTaskQuery().processDefinitionKey(getProcessDefKey()).taskCandidateUser(userId);
		//request.setAttribute("userRoleTask", userRoleTask);
		return new ModelAndView("workflow/task/taskList-group");
	}

	
	/**
	 * 跳转到我的任务列表- 我的任务（历史任务）
	 */
	@RequestMapping(params = "goHistoryTaskList")
	public ModelAndView goHistoryTaskList(HttpServletRequest request) {
		return new ModelAndView("workflow/task/taskList-history");
	}
	
	/**
	 * 跳转到历史任务（历史任务）
	 */
	@RequestMapping(params = "goAllHistoryTaskList")
	public ModelAndView goAllHistoryTaskList(HttpServletRequest request) {
		return new ModelAndView("workflow/task/taskList-history-all");
	}
	
	/**
	 * 跳转到历史任务（历史任务）
	 */
	@RequestMapping(params = "goAllCcHistoryTaskList")
	public ModelAndView goAllCcHistoryTaskList(HttpServletRequest request) {
		return new ModelAndView("workflow/task/taskList-history-cc-all");
	}
	
	/**
	 * 跳转到我的任务处理页面
	 */
	@RequestMapping(params = "goTaskTab")
	public ModelAndView goTaskTab(HttpServletRequest request) {
		String taskId = request.getParameter("taskId");
		request.setAttribute("taskId", taskId);
		//通过任务ID获取流程实例ID
		String processInstanceId = activitiService.getTask(taskId).getProcessInstanceId();
		request.setAttribute("processInstanceId", processInstanceId);
		
		//-update-begin-----------author:zhoujf date:20151210 for:任务处理页面
		String bpmProcDealStyle = (String)taskService.getVariable(taskId, WorkFlowGlobals.BPM_PROC_DEAL_STYLE);
		ProcDealStyleEnum style = ProcDealStyleEnum.toEnum(bpmProcDealStyle);
		return new ModelAndView("workflow/task/"+style.getViewName()+"task-tab");
		//-update-end-----------author:zhoujf date:20151210 for:任务处理页面
	}
	
	
	/**
	 * 跳转到我的任务处理页面
	 */
	@RequestMapping(params = "goProcessHisTab")
	public ModelAndView goProcessHisTab(HttpServletRequest request) {
		String processInstanceId = request.getParameter("processInstanceId");
		request.setAttribute("processInstanceId", processInstanceId);
		//-update-begin-----------author:zhoujf date:20151210 for:任务处理页面
		String bpmProcDealStyle = activitiDao.getHisVarinst(WorkFlowGlobals.BPM_PROC_DEAL_STYLE,processInstanceId);
		ProcDealStyleEnum style = ProcDealStyleEnum.toEnum(bpmProcDealStyle);
		return new ModelAndView("workflow/task/"+style.getViewName()+"process-his-tab");
		//-update-end-----------author:zhoujf date:20151210 for:任务处理页面
	}
	
	/**
	 * 跳转到我的任务-附加页面
	 */
	@RequestMapping(params = "goTaskForm")
	public ModelAndView goTaskForm(HttpServletRequest request) {
		String taskId = request.getParameter("taskId");
		Task  task = activitiService.getTask(taskId);
		String CONTENT_URL = null;//任务节点对应的单据页面
		//获取流程定义ID
		String insId = task.getProcessInstanceId();
		ProcessHandle processHandle = activitiService.getProcessHandle(taskId);
		
		//update-begin--Author:zhoujf  Date:20150804 for：流程节点权限控制修改
		//首先判断节点里面，是否配置了对应的表单页面
		//update-begin--Author:zhoujf  Date:20170912 for：TASK #1093 【流程节点】流程节点历史配置URL记录
	    //CONTENT_URL = oConvertUtils.getString(processHandle.getTpProcessnode().getModelandview());
		CONTENT_URL = (String)runtimeService.getVariable(insId,task.getTaskDefinitionKey()+":"+WorkFlowGlobals.SUFFIX_BPM_FORM_URL);
	    //update-end--Author:zhoujf  Date:20170912 for：TASK #1093 【流程节点】流程节点历史配置URL记录
		if(CommonRandomUtil.isNotEmpty(CONTENT_URL)){
			//拼接参数
			//update--begin-----author:zhoujf----date:21060421-----for:数据id的获取问题修改----------------
			Object id = getBpmDataId(insId);
			//update--begin-----author:zhoujf----date:20170509-----for:TASK #1942 工作流流程节点配置最新online表单页面请求地址404错误----------------
			
			//update--begin-----author:scott----date:20170807-----for:TASK #688流程节点参数传递，支持freemarker语法，自定义参数规则（同时兼容老模式）----------------
			//参数匹配规则，支持 ${id}语法
			if(CONTENT_URL.indexOf("${")!=-1){
				Map<String,Object> variablesMap = runtimeService.getVariables(insId);
				CONTENT_URL = FreemarkerHelper.parseTemplateContent(CONTENT_URL, variablesMap);
			}else{
				if(CONTENT_URL.indexOf("?")>-1){
					CONTENT_URL = CONTENT_URL +"&id="+id;
				}else{
					CONTENT_URL = CONTENT_URL +"?id="+id;
				}
			}
			//update--end-----author:scott----date:20170807-----for:TASK #688流程节点参数传递，支持freemarker语法，自定义参数规则（同时兼容老模式）----------------
			
			//update--end-----author:zhoujf----date:20170509-----for:TASK #1942 工作流流程节点配置最新online表单页面请求地址404错误----------------
			//update--end-----author:zhoujf----date:21060421-----for:数据id的获取问题修改----------------
			//传递到页面
//			request.setAttribute(WorkFlowGlobals.ProcNode_Start, CONTENT_URL);
		}else{
			//如果流程节点没配置表单，则获取流程变量中的CONTENT_URL
		     CONTENT_URL = oConvertUtils.getString(runtimeService.getVariable(insId, WorkFlowGlobals.BPM_FORM_CONTENT_URL));
			if(CommonRandomUtil.isEmpty(CONTENT_URL)){
				//step.1 获取流程中的start节点配置的表单
				TPProcessnode startNode = activitiService.getProcessStartNode(taskId);
				if(startNode!=null){
					CONTENT_URL = startNode.getModelandview();
					//ID取值，优先从BPM_DATA_ID获取，为空则从ID获取
					//update--begin-----author:zhoujf----date:21060421-----for:数据id的获取问题修改----------------
					Object id = getBpmDataId(insId);
					//拼接参数
					//update--begin-----author:zhoujf----date:20170509-----for:TASK #1942 工作流流程节点配置最新online表单页面请求地址404错误----------------
					if(CONTENT_URL.indexOf("?")>-1){
						CONTENT_URL = CONTENT_URL +"&id="+id;
					}else{
						CONTENT_URL = CONTENT_URL +"?id="+id;
					}
					//update--end-----author:zhoujf----date:20170509-----for:TASK #1942 工作流流程节点配置最新online表单页面请求地址404错误----------------
					//update--end-----author:zhoujf----date:21060421-----for:数据id的获取问题修改----------------
					runtimeService.setVariable(insId,WorkFlowGlobals.BPM_FORM_CONTENT_URL, CONTENT_URL);
				}
			}
//			request.setAttribute(WorkFlowGlobals.ProcNode_Start, CONTENT_URL);
		}
		
		String processnodeId = "";
		if(processHandle.getTpProcessnode()!=null){
			processnodeId = processHandle.getTpProcessnode().getId();
		}
		//update--begin-----author:scott----date:21060309-----for:传递给子流程追加参数taskId----------------
		CONTENT_URL = CONTENT_URL+ "&processnodeId="+processnodeId+"&taskId="+taskId;
		//update--begin-----author:scott----date:21060309-----for:传递给子流程追加参数taskId----------------
		//update-begin--author:zhoujf  date:20170912 for：TASK #2297 【表单访问权限】我的任务里面的表单访问，改造方案---------------
//		PropertiesUtil p = new PropertiesUtil("sysConfig.properties");
//		String key = p.readProperty("form.audit.sign");
		String key = ResourceUtil.getSessionattachmenttitle("form.audit.sign");
		if(StringUtil.isNotEmpty(key)){
			Map<String,String> t = SignatureUtil.getSignMap(CONTENT_URL);
			String sign = SignatureUtil.sign(t, key);
			CONTENT_URL = CONTENT_URL + "&sign="+sign;
		}
		//update-begin--author:zhoujf  date:20170912 for：TASK #2297 【表单访问权限】我的任务里面的表单访问，改造方案---------------
		request.setAttribute(WorkFlowGlobals.ProcNode_Start, CONTENT_URL);
		//update-end--Author:zhoujf  Date:20150804 for：流程节点权限控制修改
		
		//-update-begin-----------author:zhoujf date:20151210 for:任务处理页面
		String bpmProcDealStyle = (String)taskService.getVariable(taskId, WorkFlowGlobals.BPM_PROC_DEAL_STYLE);
		ProcDealStyleEnum style = ProcDealStyleEnum.toEnum(bpmProcDealStyle);
		return new ModelAndView("workflow/task/"+style.getViewName()+"task-form");
		//-update-end-----------author:zhoujf date:20151210 for:任务处理页面
	}
	
	
	private Object getBpmDataId(String insId){
		Object id = runtimeService.getVariable(insId, WorkFlowGlobals.BPM_DATA_ID);
		if(id==null){
			id = runtimeService.getVariable(insId, "id");
		}
		if(id==null){
			id = runtimeService.getVariable(insId, "ID");
		}
		return id;
	}
	
	
	//update-begin---author:scott-----------date:20160301-----for:移动端页面特定访问入口----
	/**
	 * 跳转到我的任务-附加页面【跳转移动端表单】
	 */
	@RequestMapping(params = "goTaskFormMobile")
	public ModelAndView goTaskFormMobile(HttpServletRequest request) {
		String taskId = request.getParameter("taskId");
		Task  task = activitiService.getTask(taskId);
		String CONTENT_URL = null;//任务节点对应的单据页面
		//获取流程定义ID
		String insId = task.getProcessInstanceId();
		ProcessHandle processHandle = activitiService.getProcessHandle(taskId);
		
		//update-begin--Author:zhoujf  Date:20150804 for：流程节点权限控制修改
		//首先判断节点里面，是否配置了对应的表单页面
		//update-begin--Author:zhoujf  Date:20170912 for：TASK #1093 【流程节点】流程节点历史配置URL记录
//	    CONTENT_URL = oConvertUtils.getString(processHandle.getTpProcessnode().getModelandviewMobile());
	    CONTENT_URL = (String)runtimeService.getVariable(insId,task.getTaskDefinitionKey()+":"+WorkFlowGlobals.SUFFIX_BPM_FORM_URL_MOBILE);
	    //update-end--Author:zhoujf  Date:20170912 for：TASK #1093 【流程节点】流程节点历史配置URL记录
		if(CommonRandomUtil.isNotEmpty(CONTENT_URL)){
			//拼接参数
			//update--begin-----author:zhoujf----date:21060421-----for:数据id的获取问题修改----------------
			Object id = getBpmDataId(insId);
			CONTENT_URL = CONTENT_URL +"&id="+id;
			//update--end-----author:zhoujf----date:21060421-----for:数据id的获取问题修改----------------
			//传递到页面
//			request.setAttribute(WorkFlowGlobals.ProcNode_Start, CONTENT_URL);
		}else{
			//如果流程节点没配置表单，则获取流程变量中的CONTENT_URL
		     CONTENT_URL = oConvertUtils.getString(runtimeService.getVariable(insId, WorkFlowGlobals.BPM_FORM_CONTENT_URL_MOBILE));
			if(CommonRandomUtil.isEmpty(CONTENT_URL)){
				//step.1 获取流程中的start节点配置的表单
				TPProcessnode startNode = activitiService.getProcessStartNode(taskId);
				if(startNode!=null){
					CONTENT_URL = startNode.getModelandviewMobile();
					//拼接参数
					//update--begin-----author:zhoujf----date:21060421-----for:数据id的获取问题修改----------------
					Object id = getBpmDataId(insId);
					CONTENT_URL = CONTENT_URL +"&id="+id;
					//update--end-----author:zhoujf----date:21060421-----for:数据id的获取问题修改----------------
					runtimeService.setVariable(insId,WorkFlowGlobals.BPM_FORM_CONTENT_URL_MOBILE, CONTENT_URL);
				}
			}
//			request.setAttribute(WorkFlowGlobals.ProcNode_Start, CONTENT_URL);
		}
		
		String processnodeId = "";
		if(processHandle.getTpProcessnode()!=null){
			processnodeId = processHandle.getTpProcessnode().getId();
		}
		CONTENT_URL = CONTENT_URL+ "&processnodeId="+processnodeId;
		//update-begin--author:zhoujf  date:20170912 for：TASK #2297 【表单访问权限】我的任务里面的表单访问，改造方案---------------
//		PropertiesUtil p = new PropertiesUtil("sysConfig.properties");
//		String key = p.readProperty("form.audit.sign");
		String key = ResourceUtil.getSessionattachmenttitle("form.audit.sign");
		if(StringUtil.isNotEmpty(key)){
			Map<String,String> t = SignatureUtil.getSignMap(CONTENT_URL);
			String sign = SignatureUtil.sign(t, key);
			CONTENT_URL = CONTENT_URL + "&sign="+sign;
		}
		//update-begin--author:zhoujf  date:20170912 for：TASK #2297 【表单访问权限】我的任务里面的表单访问，改造方案---------------
		request.setAttribute(WorkFlowGlobals.ProcNode_Start, CONTENT_URL);
		//update-end--Author:zhoujf  Date:20150804 for：流程节点权限控制修改
		
		//-update-begin-----------author:zhoujf date:20151210 for:任务处理页面
		String bpmProcDealStyle = (String)taskService.getVariable(taskId, WorkFlowGlobals.BPM_PROC_DEAL_STYLE);
		ProcDealStyleEnum style = ProcDealStyleEnum.toEnum(bpmProcDealStyle);
		return new ModelAndView("workflow/task/"+style.getViewName()+"task-form");
		//-update-end-----------author:zhoujf date:20151210 for:任务处理页面
	}
	//update-end---author:scott-----------date:20160301-----for:移动端页面特定访问入口----
	
	/**
	 * 跳转到我的任务-附加页面
	 */
	@RequestMapping(params = "goProcessHisForm")
	public ModelAndView goProcessHisForm(HttpServletRequest request) {
		//获取流程定义ID
		String insId = request.getParameter("processInstanceId");
		//如果流程节点没配置表单，则获取流程变量中的CONTENT_URL
		String CONTENT_URL = activitiDao.getHisVarinst(WorkFlowGlobals.BPM_FORM_CONTENT_URL,insId);
		request.setAttribute(WorkFlowGlobals.ProcNode_Start, CONTENT_URL);
		//-update-begin-----------author:zhoujf date:20151210 for:任务处理页面
		String bpmProcDealStyle = activitiDao.getHisVarinst(WorkFlowGlobals.BPM_PROC_DEAL_STYLE,insId);
		ProcDealStyleEnum style = ProcDealStyleEnum.toEnum(bpmProcDealStyle);
		return new ModelAndView("workflow/task/"+style.getViewName()+"task-form-his");
		//-update-end-----------author:zhoujf date:20151210 for:任务处理页面
	}
	
	/**
	 * 跳转到我的任务-任务处理页面
	 */
	@RequestMapping(params = "goTaskOperate")
	public ModelAndView goTaskOperate(HttpServletRequest request) {
		String taskId = request.getParameter("taskId");
		Task task = activitiService.getTask(taskId);
		int fromIndex = 0;
		int toIndex = 0;
		//流程下一步节点操作
		List<Map> transList = activitiService.getOutTransitions(taskId);
		
		//判断一下任务节点后续的分支，是否只有一个，如果是且分支的路线中文名字没有的话，默认为提交
		if(transList.size()==1){
			for(Map t:transList){
				t.put("Transition", "确认提交");
			}
		}
		//流程审批日志
		List bpmLogList = activitiService.findBpmLogsByBpmID(task.getProcessInstanceId());
		if(bpmLogList.size()-3>0){
			fromIndex = bpmLogList.size() - 3;
			toIndex =  bpmLogList.size();
		}else{
			fromIndex = 0;
			toIndex =  bpmLogList.size();
		}
		List bpmLogNewList = bpmLogList.subList(fromIndex, toIndex);
		//处理意见信息
		request.setAttribute("bpmLogList", bpmLogList);
		request.setAttribute("taskId", taskId);
		request.setAttribute("taskName", task.getName());
		//-update-begin-----------author:zhoujf date:20171226 for:TASK #2455 【bug】我的任务，流程进度条，当前节点处理人显示的账号，应该改成名称
		//根据用户登录账户查询用户真实名称
		String taskAssigneeName = "";
		//-update-begin-----------author:zhoujf date:20180713 for:TASK #2938 【改进】进度条优化
		String taskNameStartTime = "";
		if(task!=null&&StringUtils.isNotEmpty(task.getAssignee())){
			TSUser user = this.systemService.findUniqueByProperty(TSUser.class, "userName", task.getAssignee());
			if(user!=null){
				taskAssigneeName = user.getRealName();
			}
			taskNameStartTime = task.getCreateTime()==null?"":DateFormatUtils.format(task.getCreateTime(), "MM-dd HH:mm:ss");
		}
		request.setAttribute("taskAssigneeName", taskAssigneeName);
		request.setAttribute("taskNameStartTime", taskNameStartTime);
		//-update-end-----------author:zhoujf date:20180713 for:TASK #2938 【改进】进度条优化
		//-update-end-----------author:zhoujf date:20171226 for:TASK #2455 【bug】我的任务，流程进度条，当前节点处理人显示的账号，应该改成名称
		//当前任务
		request.setAttribute("task",task);
		//流程分支
		request.setAttribute("transitionList", transList);
		//下一步节点数目
		request.setAttribute("nextCodeCount", transList==null?0:transList.size());
		request.setAttribute("bpmLogListCount",bpmLogList.size());
		request.setAttribute("bpmLogNewList", bpmLogNewList);
		request.setAttribute("bpmLogNewListCount", bpmLogNewList.size());
		//历史任务节点
		List<Map<String,Object>> histListNode = activitiService.getHistTaskNodeList(task.getProcessInstanceId());
		request.setAttribute("histListNode", histListNode);
		request.setAttribute("histListSize", histListNode.size());
		//-update-begin-----------author:zhoujf date:20170622 for:TASK #2124 【我的任务】审批页面，只有一个通过按钮，没有不通过按钮
		//-update-begin-----------author:zhoujf date:20170620 for:TASK #2124 【我的任务】审批页面，只有一个通过按钮，没有不通过按钮
		//获取上一步的节点
		String turnbackTaskId = getTurnbackTaskId(task,histListNode);
		logger.info("turnbackTaskId======>"+turnbackTaskId);
		request.setAttribute("turnbackTaskId", turnbackTaskId);
		//-update-end-----------author:zhoujf date:20170620 for:TASK #2124 【我的任务】审批页面，只有一个通过按钮，没有不通过按钮
		//-update-end-----------author:zhoujf date:20170622 for:TASK #2124 【我的任务】审批页面，只有一个通过按钮，没有不通过按钮
		//-update-begin-----------author:zhoujf date:20151210 for:任务处理页面
		String bpmProcDealStyle = (String)taskService.getVariable(taskId, WorkFlowGlobals.BPM_PROC_DEAL_STYLE);
		ProcDealStyleEnum style = ProcDealStyleEnum.toEnum(bpmProcDealStyle);
		return new ModelAndView("workflow/task/"+style.getViewName()+"task-operate");
		//-update-end-----------author:zhoujf date:20151210 for:任务处理页面
	}
	
	//-update-begin-----------author:zhoujf date:20170622 for:TASK #2124 【我的任务】审批页面，只有一个通过按钮，没有不通过按钮
	//获取退回的上个节点
	private String getTurnbackTaskId(Task task,List<Map<String,Object>> histListNode){
		String turnbackTaskId = "";
		List<String> taskIdList = new ArrayList<String>();
		List<ActivityImpl> actList = getInTask(task);
		//找出走过的上个节点
		if(histListNode!=null&&histListNode.size()>0){
			for(int i=histListNode.size();i>0;i--){
				Map<String,Object> map = histListNode.get(i-1);
				if(actList!=null&&actList.size()>0){
					for(ActivityImpl act :actList){
						if(act.getId().equals((String)map.get("task_def_key_"))){
							taskIdList.add(act.getId());
							break;
						}
					}
				}
			}
		}
		if(taskIdList.size()>0){
			turnbackTaskId = taskIdList.get(0);
		}
		return turnbackTaskId;
	}
	
	//获取来源节点
	private List<ActivityImpl> getInTask(Task task){
		List<ActivityImpl> list = new ArrayList<ActivityImpl>();
		 // 取得流程定义
        ProcessDefinitionEntity definition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
                .getDeployedProcessDefinition(task
                        .getProcessDefinitionId());
		// 取得上一步活动
        ActivityImpl currActivity = ((ProcessDefinitionImpl) definition)
                .findActivity(task.getTaskDefinitionKey());
        if(currActivity.getActivityBehavior() instanceof SequentialMultiInstanceBehavior){
        	//顺序会签节点不能回退
        }else if(currActivity.getActivityBehavior() instanceof ParallelMultiInstanceBehavior){
        	//并行会签节点不能回退
        }else{
        	list.addAll(getInTaskByAct(currActivity));
        }
        return list;
	}
	
	
	private List<ActivityImpl> getInTaskByAct(ActivityImpl act){
		List<ActivityImpl> list = new ArrayList<ActivityImpl>();
        //也就是节点间的连线
        List<PvmTransition> inTransitionList = act.getIncomingTransitions();
        for(PvmTransition pvm:inTransitionList){
        	ActivityImpl t = (ActivityImpl)pvm.getSource();
        	if(t.getActivityBehavior() instanceof UserTaskActivityBehavior){
        		list.add(t);
        	}else if(t.getActivityBehavior() instanceof ParallelGatewayActivityBehavior){
        		//上个节点是并行网关 不回退
        		break;
        	}else{
        		list.addAll(getInTaskByAct(t));
        	}
        }
        return list;
	}
	//-update-end-----------author:zhoujf date:20170622 for:TASK #2124 【我的任务】审批页面，只有一个通过按钮，没有不通过按钮
	
	
	/**
	 * 跳转到我的任务-任务处理页面
	 */
	@RequestMapping(params = "goProcessHisOperate")
	public ModelAndView goProcessHisOperate(HttpServletRequest request) {
		String insId = request.getParameter("processInstanceId");
		request.setAttribute("processInstanceId", insId);
		int fromIndex = 0;
		int toIndex = 0;
		//流程审批日志
		List bpmLogList = activitiService.findBpmLogsByBpmID(insId);
		if(bpmLogList.size()-3>0){
			fromIndex = bpmLogList.size() - 3;
			toIndex =  bpmLogList.size();
		}else{
			fromIndex = 0;
			toIndex =  bpmLogList.size();
		}
		List bpmLogNewList = bpmLogList.subList(fromIndex, toIndex);
		request.setAttribute("bpmLogList", bpmLogList);
		request.setAttribute("bpmLogListCount",bpmLogList.size());
		request.setAttribute("bpmLogNewList", bpmLogNewList);
		request.setAttribute("bpmLogNewListCount", bpmLogNewList.size());
		//-update-begin-----------author:zhoujf date:20180704 for:TASK #2877 【新功能】历史查看当前任务
		//判断是否有结束节点，没有则说明流程未结束，展示当前节点
		List<Map<String,Object>> startAndEndlist = activitiDao.getActHiActinst(insId);
		boolean flag = false;
		//结束节点
        for(Map<String,Object> m:startAndEndlist){
        	if("endEvent".equals(m.get("ACT_TYPE_"))){
        		flag = true;
        		break;
        	}
        }
        String currTaskName = null;
        String currTaskNameAssignee = null;
        String currTaskNameStartTime = null;
        //流程未结束,获取流程历史,获取当前流程
        if(!flag){
        	List<HistoricTaskInstance> historicTasks = historyService.createHistoricTaskInstanceQuery().processInstanceId(insId).list();
        	if(historicTasks!=null&&historicTasks.size()>0){
        		HistoricTaskInstance hisTask= historicTasks.get(historicTasks.size()-1);
        		currTaskName = hisTask.getName();
        		currTaskNameAssignee= taskJeecgService.getUserRealName(hisTask.getAssignee());
        		currTaskNameStartTime = hisTask.getStartTime()==null?"":DateFormatUtils.format(hisTask.getStartTime(), "MM-dd HH:mm:ss");
        	}
        	
        }
        request.setAttribute("currTaskName", currTaskName);
        request.setAttribute("currTaskNameAssignee", currTaskNameAssignee);
        request.setAttribute("currTaskNameStartTime", currTaskNameStartTime);
        //-update-end-----------author:zhoujf date:20180704 for:TASK #2877 【新功能】历史查看当前任务
		//-update-begin-----------author:zhoujf date:20151210 for:任务处理页面
		String bpmProcDealStyle = activitiDao.getHisVarinst(WorkFlowGlobals.BPM_PROC_DEAL_STYLE,insId);
		ProcDealStyleEnum style = ProcDealStyleEnum.toEnum(bpmProcDealStyle);
		return new ModelAndView("workflow/task/"+style.getViewName()+"task-operate-his");
		//-update-end-----------author:zhoujf date:20151210 for:任务处理页面
	}
	
	/**
	 * 跳转到我的任务-流程图
	 */
	@RequestMapping(params = "goTaskMap")
	public ModelAndView goTaskMap(HttpServletRequest request) {
		String taskId = request.getParameter("taskId");
		String mapUrl = "activitiController.do?openProcessPic&tag=task&taskId="+taskId;
		request.setAttribute("mapUrl", mapUrl);
		return new ModelAndView("workflow/task/task-map");
	}
	
	/**
	 * 待办任务列表-用户所有的任务
	 */

	@RequestMapping(params = "taskAllList")
	public void taskAllList(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		TSUser user = ResourceUtil.getSessionUser();
		List taskList = activitiService.findPriTodoTasks(user.getUserName(),request);
		Long taskSize = activitiService.countPriTodaoTask(user.getUserName(),request);
		dataGrid.setTotal(taskSize.intValue());
		dataGrid.setResults(taskList);
		TagUtil.datagrid(response, dataGrid);

	}
	
	//update-begin--Author:zhoujf  Date:20170309 for：TASK #975 【首页提醒】我的任务
	/**
	 * 查询待办任务数量
	 * 
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "getTaskCount")
	@ResponseBody
	public AjaxJson getTaskCount(HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		try {
			TSUser user = ResourceUtil.getSessionUser();
			Long taskSize = activitiService.countPriTodaoTask(user.getUserName(),req);
			j.setObj(taskSize);
		} catch (Exception e) {
			j.setSuccess(false);
			e.printStackTrace();
		}
		return j;
	}
	//update-end--Author:zhoujf  Date:20170309 for：TASK #975 【首页提醒】我的任务
	
	/**
	 * 待办任务列表-组任务
	 */

	@RequestMapping(params = "taskGroupList")
	public void taskGroupList(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		TSUser user = ResourceUtil.getSessionUser();
		//获取当前用户角色集
		List<TSRoleUser> roles = systemService.findByProperty(TSRoleUser.class, "TSUser", user);
		List taskList = activitiService.findGroupTodoTasks(roles, request);
		Long taskSize = activitiService.countGroupTodoTasks(roles, request);
		dataGrid.setTotal(taskSize.intValue());
		dataGrid.setResults(taskList);
		TagUtil.datagrid(response, dataGrid);

	}
	/**
	 * 待办任务列表-历史任务
	 */

	@RequestMapping(params = "taskHistoryList")
	public void taskHistoryList(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		TSUser user = ResourceUtil.getSessionUser();
		List taskList = activitiService.findHistoryTasks(user.getUserName(),request);
		Long taskSize = activitiService.countHistoryTasks(user.getUserName(),request);
		dataGrid.setTotal(taskSize.intValue());
		dataGrid.setResults(taskList);
		TagUtil.datagrid(response, dataGrid);

	}
	
	//-update-begin-----------author:zhoujf date:20151216 for:增加查询所有的流程历史
	/**
	 * 历史任务(查看所有历史任务)
	 */

	@RequestMapping(params = "taskAllHistoryList")
	public void taskAllHistoryList(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		List taskList = activitiService.findAllHistoryTasks(request);
		Long taskSize = activitiService.countAllHistoryTasks(request);
		dataGrid.setTotal(taskSize.intValue());
		dataGrid.setResults(taskList);
		TagUtil.datagrid(response, dataGrid);
	}
	//-update-end-----------author:zhoujf date:20151216 for:增加查询所有的流程历史
	
	//-update-begin-----------author:zhoujf date:20170306 for:TASK #1100 【工作流完善】抄送功能
	/**
	 * 抄送历史任务(查看所有历史任务)
	 */
	@RequestMapping(params = "taskAllCcHistoryList")
	public void taskAllCcHistoryList(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		List taskList = activitiService.findAllCcHistoryTasks(request);
		Long taskSize = activitiService.countAllCcHistoryTasks(request);
		dataGrid.setTotal(taskSize.intValue());
		dataGrid.setResults(taskList);
		TagUtil.datagrid(response, dataGrid);
	}
	//-update-end-----------author:zhoujf date:20170306 for:TASK #1100 【工作流完善】抄送功能
	
	//-update-begin-----------author:zhoujf date:20180713 for:TASK #2939 【改进】催办任务
	/**
     * 流程催办
     * @param processInstanceId 流程部署ID
     */
	@RequestMapping(params = "taskNotification")
	@ResponseBody
	public AjaxJson taskNotification(@RequestParam("processInstanceId") String processInstanceId,@RequestParam("remarks") String remarks, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "";
		try {
			if(StringUtils.isNotEmpty(remarks)){
				remarks = remarks.replace("\'", "‘");
			}
			activitiService.taskNotification(processInstanceId,remarks);
			message = "流程催办成功";
		} catch (Exception e) {
			e.printStackTrace();
			message = "流程催办失败";
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 跳转到催办页面
	 */
	@RequestMapping(params = "goTaskNotification")
	public ModelAndView goTaskNotification(HttpServletRequest request) {
		String processInstanceId = request.getParameter("processInstanceId");
		request.setAttribute("processInstanceId", processInstanceId);
		return new ModelAndView("workflow/task/task-notification");
	}
	//-update-end-----------author:zhoujf date:20180713 for:TASK #2939 【改进】催办任务
}
