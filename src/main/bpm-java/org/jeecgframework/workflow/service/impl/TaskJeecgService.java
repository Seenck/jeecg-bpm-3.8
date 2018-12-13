package org.jeecgframework.workflow.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.annotation.Ehcache;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.StreamUtils;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.web.autoform.entity.AutoFormDataListEntity;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.workflow.common.WorkFlowGlobals;
import org.jeecgframework.workflow.dao.ActivitiDao;
import org.jeecgframework.workflow.service.ActivitiService;
import org.jeecgframework.workflow.util.HttpUtils;
import org.jeecgframework.workflow.util.XmlActivitiUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 
 * 类名：TaskJeecgService
 * 功能： 工作流核心处理类
 * 详细：
 * 作者：jeecg
 * 版本：1.0
 * 日期：2013-7-25 上午10:19:16
 *
 */

@Service("taskJeecgService")
@Transactional(rollbackFor=Exception.class)
public class TaskJeecgService{
	
	@Autowired
	protected RepositoryService repositoryService; //部署流程服务
	@Autowired
	protected RuntimeService runtimeService; //流程执行服务 
	@Autowired
	protected TaskService taskService ; //任务服务
	@Autowired
	protected HistoryService historyService; //历史服务
	@Autowired
	private ActivitiService activitiService;
	@Autowired
	private ActivitiDao activitiDao;
	@Autowired
	private SystemService systemService;
	
	/** 
     * 走向流程 【带参数】
     *  
     * @param taskId 
     *            当前任务节点 ID
     * @param nextTaskId 
     *            下个任务节点ID
     * @param variables 
     *            流程存储参数 
     * @throws Exception 
     */  
    public synchronized void  goProcessTaskNode(String taskId, String nextTaskId, Map<String, Object> variables) throws Exception {  
//    	try {
	        // 查找所有并行任务节点，同时驳回  
	        List<Task> taskList = findTaskListByKey(findProcessInstanceByTaskId(taskId).getId(), findTaskById(taskId).getTaskDefinitionKey());
	        for (Task task : taskList) {
	        	if(taskId.equals(task.getId())){
	        		 commitProcess(task.getId(), variables, nextTaskId); 
	        	}
	        } 
//    	} catch (Exception e) {
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		} 
    }


	/** 
     * 走向流程  【不带参数】
     *  
     * @param taskId 
     *            当前任务节点 
     * @param nextTaskId 
     *            下个任务节点
     * @throws Exception 
     */  
    public  void goProcessTaskNode(String taskId, String nextTaskId)  throws Exception {  
        if (StringUtils.isEmpty(nextTaskId)) {  
            throw new Exception("目标节点ID为空！");  
        }  
  
        // 查找所有并行任务节点，同时取回  
        List<Task> taskList = findTaskListByKey(findProcessInstanceByTaskId(taskId).getId(), findTaskById(taskId).getTaskDefinitionKey());  
        for (Task task : taskList) {  
            commitProcess(task.getId(), null, nextTaskId);  
        }  
    }


	/** 
     * 清空指定活动节点流向 
     *  
     * @param activityImpl 
     *            活动节点 
     * @return 节点流向集合 
     */  
    private  List<PvmTransition> clearTransition(ActivityImpl activityImpl) {  
        // 存储当前节点所有流向临时变量  
        List<PvmTransition> oriPvmTransitionList = new ArrayList<PvmTransition>();  
        // 获取当前节点所有流向，存储到临时变量，然后清空  
        List<PvmTransition> pvmTransitionList = activityImpl  
                .getOutgoingTransitions();  
        for (PvmTransition pvmTransition : pvmTransitionList) {  
            oriPvmTransitionList.add(pvmTransition);  
        }  
        pvmTransitionList.clear();  
  
        return oriPvmTransitionList;  
    }


	/** 
     * @param taskId 
     *            当前任务ID 
     * @param variables 
     *            流程变量 
     * @param activityId 
     *            流程转向执行任务节点ID<br> 
     *            此参数为空，默认为提交操作 
     * @throws Exception 
     */  
    private  void commitProcess(String taskId, Map<String, Object> variables,String activityId) throws Exception {  
       
        // 跳转节点为空，默认提交操作  
        if (oConvertUtils.isEmpty(activityId)) {  
            taskService.complete(taskId, variables);  
        } else {// 流程转向操作  
            turnTransition(taskId, activityId, variables);  
        }  
    }


	/** 
     * 中止流程(特权人直接审批通过等) 
     *  
     * @param taskId 
     */  
    public  void endProcess(String taskId) throws Exception {  
        ActivityImpl endActivity = findActivitiImpl(taskId, "end");  
        commitProcess(taskId, null, endActivity.getId());  
    }


	/** 
     * 根据流入任务集合，查询最近一次的流入任务节点 
     *  
     * @param processInstance 
     *            流程实例 
     * @param tempList 
     *            流入任务集合 
     * @return 
     */  
    private  ActivityImpl filterNewestActivity(ProcessInstance processInstance,  
            List<ActivityImpl> tempList) {  
        while (tempList.size() > 0) {  
            ActivityImpl activity_1 = tempList.get(0);  
            HistoricActivityInstance activityInstance_1 = findHistoricUserTask(  
                    processInstance, activity_1.getId());  
            if (activityInstance_1 == null) {  
                tempList.remove(activity_1);  
                continue;  
            }  
  
            if (tempList.size() > 1) {  
                ActivityImpl activity_2 = tempList.get(1);  
                HistoricActivityInstance activityInstance_2 = findHistoricUserTask(  
                        processInstance, activity_2.getId());  
                if (activityInstance_2 == null) {  
                    tempList.remove(activity_2);  
                    continue;  
                }  
  
                if (activityInstance_1.getEndTime().before(  
                        activityInstance_2.getEndTime())) {  
                    tempList.remove(activity_1);  
                } else {  
                    tempList.remove(activity_2);  
                }  
            } else {  
                break;  
            }  
        }  
        if (tempList.size() > 0) {  
            return tempList.get(0);  
        }  
        return null;  
    }


	/** 
     * 根据任务ID和节点ID获取活动节点 <br> 
     *  
     * @param taskId 
     *            任务ID 
     * @param activityId 
     *            活动节点ID <br> 
     *            如果为null或""，则默认查询当前活动节点 <br> 
     *            如果为"end"，则查询结束节点 <br> 
     *  
     * @return 
     * @throws Exception 
     */  
    private  ActivityImpl findActivitiImpl(String taskId, String activityId)  
            throws Exception {  
        // 取得流程定义  
        ProcessDefinitionEntity processDefinition = findProcessDefinitionEntityByTaskId(taskId);  
  
        // 获取当前活动节点ID  
        if (StringUtils.isEmpty(activityId)) {  
            activityId = findTaskById(taskId).getTaskDefinitionKey();  
        }  
  
        // 根据流程定义，获取该流程实例的结束节点  
        if (activityId.toUpperCase().equals("END")) {  
            for (ActivityImpl activityImpl : processDefinition.getActivities()) {  
                List<PvmTransition> pvmTransitionList = activityImpl  
                        .getOutgoingTransitions();  
                if (pvmTransitionList.isEmpty()) {  
                    return activityImpl;  
                }  
            }  
        }  
  
        // 根据节点ID，获取对应的活动节点  
        ActivityImpl activityImpl = ((ProcessDefinitionImpl) processDefinition)  
                .findActivity(activityId);  
  
        return activityImpl;  
    }


	/** 
     * 根据当前任务ID，查询可以驳回的任务节点 
     *  
     * @param taskId 
     *            当前任务ID 
     */  
    public  List<ActivityImpl> findBackAvtivity(String taskId) throws Exception {  
        List<ActivityImpl> rtnList =  iteratorBackActivity(taskId, findActivitiImpl(taskId,  
                    null), new ArrayList<ActivityImpl>(),  
                    new ArrayList<ActivityImpl>());  
        
        TaskEntity task=findTaskById(taskId);
       
        ProcessDefinitionEntity  pde=(ProcessDefinitionEntity)repositoryService.getProcessDefinition(task.getProcessDefinitionId());
		//获取流程定义全部节点
		List<ActivityImpl> activitiList =pde.getActivities();
        
		for(ActivityImpl act:activitiList){
			//System.out.println(act.getProperty("name")+"==="+act.getProperty("type"));
			
			//System.out.println(act.getProperties().toString());
			
		}
		
        //return activitiList;
		
		System.out.println("size===="+rtnList.size());
		
        return reverList(rtnList);  
    }

	/** 
     * 查询指定任务节点的最新记录 
     *  
     * @param processInstance 
     *            流程实例 
     * @param activityId 
     * @return 
     */  
    private  HistoricActivityInstance findHistoricUserTask(  
            ProcessInstance processInstance, String activityId) {  
        HistoricActivityInstance rtnVal = null;  
        // 查询当前流程实例审批结束的历史节点  
        List<HistoricActivityInstance> historicActivityInstances = historyService  
                .createHistoricActivityInstanceQuery().activityType("userTask")  
                .processInstanceId(processInstance.getId()).activityId(  
                        activityId).finished()  
                .orderByHistoricActivityInstanceEndTime().desc().list();  
        if (historicActivityInstances.size() > 0) {  
            rtnVal = historicActivityInstances.get(0);  
        }  
  
        return rtnVal;  
    }  
  
	/** 
     * 根据当前节点，查询输出流向是否为并行终点，如果为并行终点，则拼装对应的并行起点ID 
     *  
     * @param activityImpl 
     *            当前节点 
     * @return 
     */  
    private  String findParallelGatewayId(ActivityImpl activityImpl) {  
        List<PvmTransition> incomingTransitions = activityImpl  
                .getOutgoingTransitions();  
        for (PvmTransition pvmTransition : incomingTransitions) {  
            TransitionImpl transitionImpl = (TransitionImpl) pvmTransition;  
            activityImpl = transitionImpl.getDestination();  
            String type = (String) activityImpl.getProperty("type");  
            if ("parallelGateway".equals(type)) {// 并行路线  
                String gatewayId = activityImpl.getId();  
                String gatewayType = gatewayId.substring(gatewayId  
                        .lastIndexOf("_") + 1);  
                if ("END".equals(gatewayType.toUpperCase())) {  
                    return gatewayId.substring(0, gatewayId.lastIndexOf("_"))  
                            + "_start";  
                }  
            }  
        }  
        return null;  
    }  
  
	/** 
     * 根据任务ID获取流程定义 
     *  
     * @param taskId 
     *            任务ID 
     * @return 
     * @throws Exception 
     */  
    public  ProcessDefinitionEntity findProcessDefinitionEntityByTaskId(  
            String taskId) throws Exception {  
        // 取得流程定义  
        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)  
                .getDeployedProcessDefinition(findTaskById(taskId)  
                        .getProcessDefinitionId());  
  
        if (processDefinition == null) {  
            throw new Exception("流程定义未找到!");  
        }  
  
        return processDefinition;  
    }  
  
	/** 
     * 根据任务ID获取对应的流程实例 
     *  
     * @param taskId 
     *            任务ID 
     * @return 
     * @throws Exception 
     */  
    public  ProcessInstance findProcessInstanceByTaskId(String taskId)  
            throws Exception {  
        // 找到流程实例  
        ProcessInstance processInstance = runtimeService  
                .createProcessInstanceQuery().processInstanceId(  
                        findTaskById(taskId).getProcessInstanceId())  
                .singleResult();  
        if (processInstance == null) {   
            throw new Exception("流程实例未找到!");  
        }  
        return processInstance;  
    }  
  
    /** 
     * 根据任务ID获得任务实例 
     *  
     * @param taskId 
     *            任务ID 
     * @return 
     * @throws Exception 
     */  
    private TaskEntity findTaskById(String taskId) throws Exception {  
        TaskEntity task = (TaskEntity) taskService.createTaskQuery().taskId(taskId).singleResult();  
        if (task == null) {  
            throw new Exception("任务实例未找到!");  
        }  
        return task;  
    }  
  
  
    /** 
     * 根据流程实例ID和任务key值查询所有同级任务集合 
     *  
     * @param processInstanceId 
     * @param key 
     * @return 
     */  
    private  List<Task> findTaskListByKey(String processInstanceId, String key) {  
        return taskService.createTaskQuery().processInstanceId(  
                processInstanceId).taskDefinitionKey(key).list();  
    }  
  
  
    /** 
     * 迭代循环流程树结构，查询当前节点可驳回的任务节点 
     *  
     * @param taskId 
     *            当前任务ID 
     * @param currActivity 
     *            当前活动节点 
     * @param rtnList 
     *            存储回退节点集合 
     * @param tempList 
     *            临时存储节点集合（存储一次迭代过程中的同级userTask节点） 
     * @return 回退节点集合 
     */  
    private  List<ActivityImpl> iteratorBackActivity(String taskId,  
            ActivityImpl currActivity, List<ActivityImpl> rtnList,  
            List<ActivityImpl> tempList) throws Exception {  
        // 查询流程定义，生成流程树结构  
        ProcessInstance processInstance = findProcessInstanceByTaskId(taskId);  
  
        // 当前节点的流入来源  
        List<PvmTransition> incomingTransitions = currActivity  
                .getIncomingTransitions();  
        // 条件分支节点集合，userTask节点遍历完毕，迭代遍历此集合，查询条件分支对应的userTask节点  
        List<ActivityImpl> exclusiveGateways = new ArrayList<ActivityImpl>();  
        // 并行节点集合，userTask节点遍历完毕，迭代遍历此集合，查询并行节点对应的userTask节点  
        List<ActivityImpl> parallelGateways = new ArrayList<ActivityImpl>();  
        // 遍历当前节点所有流入路径  
        for (PvmTransition pvmTransition : incomingTransitions) {  
            TransitionImpl transitionImpl = (TransitionImpl) pvmTransition;  
            ActivityImpl activityImpl = transitionImpl.getSource();  
            String type = (String) activityImpl.getProperty("type");  
            /** 
             * 并行节点配置要求：<br> 
             * 必须成对出现，且要求分别配置节点ID为:XXX_start(开始)，XXX_end(结束) 
             */  
            if ("parallelGateway".equals(type)) {// 并行路线  
                String gatewayId = activityImpl.getId();  
                String gatewayType = gatewayId.substring(gatewayId  
                        .lastIndexOf("_") + 1);  
                if ("START".equals(gatewayType.toUpperCase())) {// 并行起点，停止递归  
                   return rtnList;  
                } else {// 并行终点，临时存储此节点，本次循环结束，迭代集合，查询对应的userTask节点  
                    parallelGateways.add(activityImpl);  
                }  
            } else if ("startEvent".equals(type)) {// 开始节点，停止递归  
                return rtnList;  
            } else if ("userTask".equals(type)) {// 用户任务  
                tempList.add(activityImpl);  
            } else if ("exclusiveGateway".equals(type)) {// 分支路线，临时存储此节点，本次循环结束，迭代集合，查询对应的userTask节点  
                currActivity = transitionImpl.getSource();  
                exclusiveGateways.add(currActivity);  
            }  
        }  
  
        /** 
         * 迭代条件分支集合，查询对应的userTask节点 
         */  
        for (ActivityImpl activityImpl : exclusiveGateways) {  
            iteratorBackActivity(taskId, activityImpl, rtnList, tempList);  
        }  
  
        /** 
         * 迭代并行集合，查询对应的userTask节点 
         */  
        for (ActivityImpl activityImpl : parallelGateways) {  
            iteratorBackActivity(taskId, activityImpl, rtnList, tempList);  
        }  
  
        /** 
         * 根据同级userTask集合，过滤最近发生的节点 
         */  
        currActivity = filterNewestActivity(processInstance, tempList);  
        if (currActivity != null) {  
            // 查询当前节点的流向是否为并行终点，并获取并行起点ID  
            String id = findParallelGatewayId(currActivity);  
            if (StringUtils.isEmpty(id)) {// 并行起点ID为空，此节点流向不是并行终点，符合驳回条件，存储此节点  
                rtnList.add(currActivity);  
            } else {// 根据并行起点ID查询当前节点，然后迭代查询其对应的userTask任务节点  
                currActivity = findActivitiImpl(taskId, id);  
            }  
  
            // 清空本次迭代临时集合  
            tempList.clear();  
            // 执行下次迭代  
            iteratorBackActivity(taskId, currActivity, rtnList, tempList);  
        }  
        return rtnList;  
    }  
  
  
    /** 
     * 还原指定活动节点流向 
     *  
     * @param activityImpl 
     *            活动节点 
     * @param oriPvmTransitionList 
     *            原有节点流向集合 
     */  
    private  void restoreTransition(ActivityImpl activityImpl,  
            List<PvmTransition> oriPvmTransitionList) {  
        // 清空现有流向  
        List<PvmTransition> pvmTransitionList = activityImpl  
                .getOutgoingTransitions();  
        pvmTransitionList.clear();  
        // 还原以前流向  
        for (PvmTransition pvmTransition : oriPvmTransitionList) {  
            pvmTransitionList.add(pvmTransition);  
        }  
    }  
  
    /** 
     * 反向排序list集合，便于驳回节点按顺序显示 
     *  
     * @param list 
     * @return 
     */  
    private  List<ActivityImpl> reverList(List<ActivityImpl> list) {  
        List<ActivityImpl> rtnList = new ArrayList<ActivityImpl>();  
        // 由于迭代出现重复数据，排除重复  
        for (int i = list.size(); i > 0; i--) {  
            if (!rtnList.contains(list.get(i - 1)))  
                rtnList.add(list.get(i - 1));  
        }  
        return rtnList;  
    }  
  
    /** 
     * 转办流程 
     *  
     * @param taskId 
     *            当前任务节点ID 
     * @param userCode 
     *            被转办人Code 
     */  
    public  void transferAssignee(String taskId, String userCode) {  
        taskService.setAssignee(taskId, userCode);  
    }  
  
    /** 
     * 流程转向操作 
     *  
     * @param taskId 
     *            当前任务ID 
     * @param activityId 
     *            目标节点任务ID 
     * @param variables 
     *            流程变量 
     * @throws Exception 
     */  
    private  void turnTransition(String taskId, String activityId,  
            Map<String, Object> variables) throws Exception {  
        // 当前节点  
        ActivityImpl currActivity = findActivitiImpl(taskId, null);
      //update-begin----------author:zhoujf--------date:20170106------for:多分支不能触发执行监听，提交异常后按钮消失问题
        //update-begin----------author:scott--------date:20151106------for:分支支持监听----------------
//        List<PvmTransition> pvmTransitionList = currActivity.getOutgoingTransitions();
      //update-end----------author:scott--------date:20151106------for:分支支持监听----------------
      //update-end----------author:zhoujf--------date:20170106------for:多分支不能触发执行监听，提交异常后按钮消失问题
        
        // 清空当前流向  
        List<PvmTransition> oriPvmTransitionList = clearTransition(currActivity);  
  
        // 创建新流向  
        TransitionImpl newTransition = currActivity.createOutgoingTransition();  
        // 目标节点  
        ActivityImpl pointActivity = findActivitiImpl(taskId, activityId);  
      //update-begin----------author:zhoujf--------date:20170106------for:多分支不能触发执行监听，提交异常后按钮消失问题
        try {
			// 设置新流向的目标节点  
			newTransition.setDestination(pointActivity);
			
			//update-begin----------author:scott--------date:20151106------for:分支支持监听----------------
			// 多分支模式，点击button动态给选择分支加监听
			//List<PvmTransition> pvmTransitionList = currActivity.getOutgoingTransitions();
			for(PvmTransition pv:oriPvmTransitionList){
				if(activityId.equals(pv.getDestination().getId())){
					TransitionImpl tra = (TransitionImpl) pv;
					if(tra.getExecutionListeners()!=null && tra.getExecutionListeners().size()>0){
						newTransition.setExecutionListeners(tra.getExecutionListeners());
					}
					break;
				}
			}
			//update-end----------author:scott--------date:20151106------for:分支支持监听----------------
  
  
			// 执行转向任务  
			taskService.complete(taskId, variables);
		} catch (Exception e) {
			throw e;
		} finally{
			// 删除目标节点新流入  
			pointActivity.getIncomingTransitions().remove(newTransition);  
			
			// 还原以前流向  
			restoreTransition(currActivity, oriPvmTransitionList); 
		}
		//update-end----------author:zhoujf--------date:20170106------for:多分支不能触发执行监听，提交异常后按钮消失问题
    }  
    
    /**
     * 指定下一步操作人
     * @param processInstanceId
     * @param taskDefKey
     */
    public String getTaskIdByProins(String proInsId,String taskDefKey){
    	ActivitiDao activitiDao=ApplicationContextUtil.getContext().getBean(ActivitiDao.class);
		return activitiDao.getTaskIdByProins(proInsId, taskDefKey,1,1);
    }
    
    
    /**
     * 通过流程定义key和任务，判断任务是否是会签节点
     * 处理方法:通过解析xml文件
     * @param taskId
     * @return
     */
    //TODO
    public boolean checkUserTaskIsHuiQian(String taskId ,String taskkey){
    	Task task = activitiService.getTask(taskId);
    	//String taskkey = task.getTaskDefinitionKey();
    	String processkey = task.getProcessDefinitionId();
    	ProcessDefinition pd = repositoryService.getProcessDefinition(processkey);
		InputStream resourceAsStream = repositoryService.getResourceAsStream(pd.getDeploymentId(), pd.getResourceName());
		String xmlString = StreamUtils.InputStreamTOString(resourceAsStream);
		return XmlActivitiUtil.parseXml(xmlString, taskkey);
    }
    
    
    /**
     * 修改流程状态
     */
    public void updateFormDataStatus(DelegateExecution execution){
    	//将业务数据的流程状态改为已完成
		Object bpm_form_key = execution.getVariable(WorkFlowGlobals.BPM_FORM_KEY);//获取流程对应的表单
		if(oConvertUtils.isNotEmpty(bpm_form_key)){
			//修改业务数据的状态
			Object id = execution.getVariable("id");//获取流程对应的表单
			if(id == null){
				 id = execution.getVariable("ID");//获取流程对应的表单
			}
			activitiDao.updateFormDataStatus((String)id, (String)bpm_form_key);
		}
    }
    
  //--update-begin---author:zhoujf-----date:20180707---------------for:TASK #1184 流程表单多流程发起功能
    /**
     * 修改流程状态
     */
    public void updateMutilFlowFormDataStatus(String id,String tableName,String colName,String bpmStatus){
    	//将业务数据的流程状态改为已完成
		activitiDao.updateFormDataStatus(id, tableName, colName, bpmStatus);
    }
  //--update-end---author:zhoujf-----date:20180707---------------for:TASK #1184 流程表单多流程发起功能
   //update-begin--Author:zhoujf  Date:20170329 for：自定义表单对接工作流
    /**
     * 修改流程状态(待提交)
     */
    public void updateAutoFormDataListStatus(String id,String status,boolean isCallBackProcess){
    	AutoFormDataListEntity entity = systemService.get(AutoFormDataListEntity.class,id);
		entity.setBpmStatus(status);
		systemService.updateEntitie(entity);
		if(isCallBackProcess){
			//删除业务数据
			activitiDao.deleteBaseBusStart(id);
		}
    }
  //update-end--Author:zhoujf  Date:20170329 for：自定义表单对接工作流
    
  //update-begin--Author:zhoujf  Date:20170330 for：第三方表单对接工作流
    public  void doCallBack(String callBackUrl,String id,boolean isCallBackProcess){
    	if(isCallBackProcess){
			//删除业务数据
			activitiDao.deleteBaseBusStart(id);
		}
		final String url = callBackUrl;
		// 回调处理
		Thread t = new Thread(new Runnable() {
		public void run() {
			    int i = 0;
			    System.out.println("==流程结束==url request===1==="+url);
				String res = HttpUtils.get(url);
				System.out.println("==流程结束==url response===1==="+res);
				while(i<2){
					if(!"success".equals(res)){
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
						}
						System.out.println("==流程结束==url request==="+(i+1)+"==="+url);
						res = HttpUtils.get(url);
						System.out.println("==流程结束==url response==="+(i+1)+"==="+res);
						i++;
					}
				}
				System.out.println("==流程结束==url 开始==="+url);
			}
		});
		t.start();
	}
  //update-begin--Author:zhoujf  Date:20170330 for：第三方表单对接工作流
    
    /**
     * 修改流程状态(待提交)
     */
    public void updateFormDataStatusStart(DelegateExecution execution){
    	//将业务数据的流程状态改为已完成
		Object bpm_form_key = execution.getVariable(WorkFlowGlobals.BPM_FORM_KEY);//获取流程对应的表单
		if(oConvertUtils.isNotEmpty(bpm_form_key)){
			//修改业务数据的状态
			Object id = execution.getVariable("id");//获取流程对应的表单
			if(id == null){
				 id = execution.getVariable("ID");//获取流程对应的表单
			}
			activitiDao.updateFormDataStatusStart((String)id, (String)bpm_form_key);
			//删除业务数据
			activitiDao.deleteBaseBusStart((String)id);
		}
    }
    
    /**
	 * 根据用户ID，获取用户真实名字
	 * @param user_id
	 */
    @Ehcache
    public String getUserRealName(String user_id) {
		if (oConvertUtils.isEmpty(user_id)) {
			return "";
		}else{
			return activitiDao.getUserRealName(user_id.trim());
		}
	}
}
