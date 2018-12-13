package org.jeecgframework.workflow.listener.global;

import java.util.List;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang.StringUtils;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.workflow.common.WorkFlowGlobals;
import org.jeecgframework.workflow.dao.ActivitiDao;
import org.jeecgframework.workflow.listener.FlowEventHandler;
import org.jeecgframework.workflow.pojo.base.TPMutilFlowBizEntity;
import org.jeecgframework.workflow.pojo.base.TPProcess;
import org.jeecgframework.workflow.pojo.base.TPProcessnodeDeployment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Activiti全局事件监听器--任务完成监听器 
 * 任务完成时根据节点配置的流程状态更改表单状态
 * @author zhoujf
 *
 */
@Component("flowTaskCompleteListener")
public class FlowTaskCompleteListener implements FlowEventHandler {
	public final static Logger logger = LoggerFactory.getLogger(FlowTaskCompleteListener.class);
	
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private ActivitiDao activitiDao;

	/**
	 * 没有设置值/驳回/流程追回/流程作废   都不做处理
	 */
	@Override
	public void handle(ActivitiEvent event) {
		  ActivitiEntityEventImpl eventImpl=(ActivitiEntityEventImpl)event;  
		  TaskEntity taskEntity=(TaskEntity)eventImpl.getEntity(); 
		  //获取流程实例id
		  String insId = taskEntity.getProcessInstanceId();
		  String varBpmStatus = (String)runtimeService.getVariable(insId, WorkFlowGlobals.BPM_STATUS);
		  //驳回时不做处理
		  if(WorkFlowGlobals.PROCESS_REJECTPROCESS_STATUS.equals(varBpmStatus)){
			  return;
		  }
		  //流程追回时不做处理
		  if(WorkFlowGlobals.PROCESS_CALLBACKPROCESS_STATUS.equals(varBpmStatus)){
			  return;
		  }
		  //流程作废时不做处理
		  if(WorkFlowGlobals.PROCESS_INVALIDPROCESS_STATUS.equals(varBpmStatus)){
			  return;
		  }
		  ProcessDefinition processDefinition =  repositoryService.createProcessDefinitionQuery().processDefinitionId(taskEntity.getProcessDefinitionId()).singleResult();
		  String taskDefinitionKey = taskEntity.getTaskDefinitionKey();// 节点key
		  String processkey = processDefinition.getKey();
		  TPProcess tpProcess = this.systemService.findUniqueByProperty(TPProcess.class, "processkey", processkey);
	      List<TPProcessnodeDeployment> processnodeList = this.systemService.findHql("from TPProcessnodeDeployment where processid = ? and deploymentId = ? and processnodecode= ? ", 
	    		      tpProcess.getId(),processDefinition.getDeploymentId(),taskDefinitionKey);
		  String bpmStatus = null;
		  TPProcessnodeDeployment processnode = null;
		  if (processnodeList.size() > 0) {
				processnode = processnodeList.get(0);
				logger.debug("task complete processnode ---->" + processnode);
		  }
		  if(processnode!=null){
			  bpmStatus = processnode.getNodeBpmStatus();
		  }
		  logger.debug("task complete is "+taskEntity.getName()+" key is:"+taskEntity.getTaskDefinitionKey());  
		  logger.debug("enter the task complete listener ---->" + event.getType().name());
		  //更新流程状态
		  //没有设置时不做处理
		  if(StringUtils.isEmpty(bpmStatus)){
			  return;
		  }
		  String formType = (String)runtimeService.getVariable(insId, WorkFlowGlobals.BPM_FORM_TYPE);
		  String bpmFormKey = (String)runtimeService.getVariable(insId, WorkFlowGlobals.BPM_FORM_KEY);
		  String id = (String)runtimeService.getVariable(insId, WorkFlowGlobals.BPM_DATA_ID);
		  //--update-begin---author:zhoujf-----date:20180727---------------for:常量抽取
		  if(WorkFlowGlobals.BPM_FORM_TYPE_MUTIL_FLOW.equals(formType)){
		  //--update-end---author:zhoujf-----date:20180727---------------for:常量抽取
			  //多流程表单模式处理
			  ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(insId).singleResult();
			  String businessKey = processInstance.getBusinessKey();
			  TPMutilFlowBizEntity bizFlow = systemService.get(TPMutilFlowBizEntity.class, businessKey);
				//更新数据状态为已完成
			  activitiDao.updateFormDataStatus(id,bpmFormKey,bizFlow.getMutilFlowStatusCol(),bpmStatus);
		  }else{
			  //其他表单模式处理
			  activitiDao.updateFormDataStatus(id, bpmFormKey, bpmStatus);
		  }
		  
	}

}
