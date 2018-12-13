/**  
 * @Project: jeecg
 * @Title: ProcessEndListener.java
 * @Package com.oa.manager.workFlow.listener.execution
 * @date 2013-8-16 下午2:04:12
 * @Copyright: 2013 
 */
package org.jeecgframework.workflow.listener.execution;

import java.util.List;

import org.activiti.engine.HistoryService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.persistence.entity.HistoricVariableInstanceEntity;
import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.workflow.common.WorkFlowGlobals;
import org.jeecgframework.workflow.pojo.base.TPMutilFlowBizEntity;
import org.jeecgframework.workflow.service.impl.TaskJeecgService;



/**
 * 
 * 类名：ProcessEndListener
 * 功能：流程实例结束监听器
 * 详细：
 * 作者：jeecg
 * 版本：1.0
 * 日期：2013-8-16 下午2:04:12
 *
 */
public class ProcessEndListener implements ExecutionListener{

	/**
	 * @Fields serialVersionUID : 
	 */
	
	private static final long serialVersionUID = 1L;

	public void notify(DelegateExecution execution) throws Exception {
		
//		String applyUserId=(String)execution.getVariable("applyUserId");//获取流程发起人id
//		System.out.println("==流程结束=="+applyUserId);
		
		//update-begin--Author:zhoujf  Date:20170330 for：第三方表单对接工作流
		//修改流程状态
		doUpdateStatus(execution);
		//update-end--Author:zhoujf  Date:20170330 for：第三方表单对接工作流
		
		//清空流程实例所有历史流程变量,任务变量
		HistoryService historyService= ApplicationContextUtil.getContext().getBean(HistoryService.class);
		
		List<HistoricVariableInstance> hvis=historyService.createHistoricVariableInstanceQuery().processInstanceId(execution.getProcessInstanceId()).list();
		
		for(HistoricVariableInstance h:hvis){
			//System.out.println(h.getVariableName());
			//update-begin--Author:zhoujf  Date:20151208 for：业务标题不清空
			//流程对应的表单业务标题，不清空
			if(WorkFlowGlobals.BPM_BIZ_TITLE.equals(h.getVariableName())){
				continue;
			}
			//流程办理风格，不清空
			if(WorkFlowGlobals.BPM_PROC_DEAL_STYLE.equals(h.getVariableName())){
				continue;
			}
			//update-end--Author:zhoujf  Date:20151208 for：业务标题不清空
			//流程对应的表单页面，不清空
			if(!WorkFlowGlobals.BPM_FORM_CONTENT_URL.equals(h.getVariableName()) && !WorkFlowGlobals.BPM_FORM_CONTENT_URL_MOBILE.equals(h.getVariableName())){
				((HistoricVariableInstanceEntity)h).delete();
			}
		}
	}
	
	
	private void doUpdateStatus(DelegateExecution execution){
		TaskJeecgService taskJeecgService= ApplicationContextUtil.getContext().getBean(TaskJeecgService.class);
		String bpmStatus=(String)execution.getVariable(WorkFlowGlobals.BPM_STATUS);//获取流程状态
		String callBackUrl=(String)execution.getVariable(WorkFlowGlobals.BPM_API_CALLBACK_URL);//获取流程状态
		String dataListId=(String)execution.getVariable(WorkFlowGlobals.BPM_AUTO_FORM_DATA_ID);
		//--update-begin---author:zhoujf-----date:20180707---------------for:TASK #1184 流程表单多流程发起功能
		String bpm_form_key = (String)execution.getVariable(WorkFlowGlobals.BPM_FORM_KEY);//获取流程对应的表单
		String id = (String)execution.getVariable(WorkFlowGlobals.BPM_DATA_ID);//获取表单数据id
		String formType = (String)execution.getVariable(WorkFlowGlobals.BPM_FORM_TYPE);//获取表单类型
		//--update-end---author:zhoujf-----date:20180707---------------for:TASK #1184 流程表单多流程发起功能
		if(WorkFlowGlobals.PROCESS_CALLBACKPROCESS_STATUS.equals(bpmStatus)){
			//流程追回逻辑
			//--update-begin---author:zhoujf-----date:20180707---------------for:TASK #1184 流程表单多流程发起功能
			if("mutlflowform".equals(formType)){
				//根据流程实例id查询表单业务数据信息
				String businessKey =  execution.getProcessBusinessKey();
				SystemService systemService= ApplicationContextUtil.getContext().getBean(SystemService.class);
				TPMutilFlowBizEntity bizFlow = systemService.get(TPMutilFlowBizEntity.class, businessKey);
				//更新数据状态为待提交，并且删除多流程表单业务数据
				taskJeecgService.updateMutilFlowFormDataStatus(id,bpm_form_key,bizFlow.getMutilFlowStatusCol(),WorkFlowGlobals.BPM_BUS_STATUS_1);
				systemService.delete(bizFlow);
				//--update-end---author:zhoujf-----date:20180707---------------for:TASK #1184 流程表单多流程发起功能
			}else if(StringUtils.isNotEmpty(callBackUrl)){
				//第三方表单对接回调
				System.out.println("==流程结束==callBackUrl==="+callBackUrl);
				String sign=(String)execution.getVariable(WorkFlowGlobals.BPM_DATA_ID_SIGN);
				if(callBackUrl.indexOf("?")>-1){
					callBackUrl += "&id="+id+"&bpmStatus="+WorkFlowGlobals.BPM_BUS_STATUS_1+"&sign="+sign;
				}else{
					callBackUrl += "?id="+id+"&bpmStatus="+WorkFlowGlobals.BPM_BUS_STATUS_1+"&sign="+sign;
				}
				taskJeecgService.doCallBack(callBackUrl,id,true);
				
			}else if(StringUtils.isNotEmpty(dataListId)){
				//自定义表单对接更新状态
				taskJeecgService.updateAutoFormDataListStatus(dataListId,WorkFlowGlobals.BPM_BUS_STATUS_1,true);
			}else{
				taskJeecgService.updateFormDataStatusStart(execution);
			}
		}else{
			//--update-begin---author:zhoujf-----date:20180707---------------for:TASK #1184 流程表单多流程发起功能
			if("mutlflowform".equals(formType)){
				//根据流程实例id查询表单业务数据信息
				String businessKey =  execution.getProcessBusinessKey();
				SystemService systemService= ApplicationContextUtil.getContext().getBean(SystemService.class);
				TPMutilFlowBizEntity bizFlow = systemService.get(TPMutilFlowBizEntity.class, businessKey);
				//更新数据状态为已完成
				taskJeecgService.updateMutilFlowFormDataStatus(id,bpm_form_key,bizFlow.getMutilFlowStatusCol(),WorkFlowGlobals.BPM_BUS_STATUS_3);
				//--update-end---author:zhoujf-----date:20180707---------------for:TASK #1184 流程表单多流程发起功能
			}else if(StringUtils.isNotEmpty(callBackUrl)){
				//第三方表单对接回调
				System.out.println("==流程结束==callBackUrl==="+callBackUrl);
				String sign=(String)execution.getVariable(WorkFlowGlobals.BPM_DATA_ID_SIGN);
				if(callBackUrl.indexOf("?")>-1){
					callBackUrl += "&id="+id+"&bpmStatus="+WorkFlowGlobals.BPM_BUS_STATUS_3+"&sign="+sign;
				}else{
					callBackUrl += "?id="+id+"&bpmStatus="+WorkFlowGlobals.BPM_BUS_STATUS_3+"&sign="+sign;
				}
				taskJeecgService.doCallBack(callBackUrl,id,false);
				
			}else if(StringUtils.isNotEmpty(dataListId)){
				//自定义表单对接更新状态
				taskJeecgService.updateAutoFormDataListStatus(dataListId,WorkFlowGlobals.BPM_BUS_STATUS_3,false);
			}else{
				taskJeecgService.updateFormDataStatus(execution);
			}
		}
	}

}
