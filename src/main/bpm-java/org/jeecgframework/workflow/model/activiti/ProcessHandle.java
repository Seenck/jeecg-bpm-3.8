package org.jeecgframework.workflow.model.activiti;

import java.util.List;

import org.jeecgframework.workflow.pojo.base.TPForm;
import org.jeecgframework.workflow.pojo.base.TPProcess;
import org.jeecgframework.workflow.pojo.base.TPProcessnode;
import org.jeecgframework.workflow.pojo.base.TPProcesspro;


/**
 * @ClassName: ProcessHandle
 * @Description: TODO(流程办理参数模型)
 * @author jeecg
 * @date 2013-1-26 下午09:30:49
 * 
 */
public class ProcessHandle {
	private String taskId;// TASK主键
	private String businessKey;//业务主键
	private String processDefinitionKey;// 流程定义唯一编码
	private String taskDefinitionKey;// 任务节点编码
	private TPProcess tpProcess;
	private TPProcessnode tpProcessnode;
	private TPForm tpForm;
	List<TPProcesspro> tpProcesspros;

	public List<TPProcesspro> getTpProcesspros() {
		return tpProcesspros;
	}

	public void setTpProcesspros(List<TPProcesspro> tpProcesspros) {
		this.tpProcesspros = tpProcesspros;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}

	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}

	public String getTaskDefinitionKey() {
		return taskDefinitionKey;
	}

	public void setTaskDefinitionKey(String taskDefinitionKey) {
		this.taskDefinitionKey = taskDefinitionKey;
	}

	public TPProcess getTpProcess() {
		return tpProcess;
	}

	public void setTpProcess(TPProcess tpProcess) {
		this.tpProcess = tpProcess;
	}

	public TPProcessnode getTpProcessnode() {
		return tpProcessnode;
	}

	public void setTpProcessnode(TPProcessnode tpProcessnode) {
		this.tpProcessnode = tpProcessnode;
	}

	public TPForm getTpForm() {
		return tpForm;
	}

	public void setTpForm(TPForm tpForm) {
		this.tpForm = tpForm;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

}
