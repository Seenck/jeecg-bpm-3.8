package org.jeecgframework.workflow.pojo.activiti;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 流程节点实例历史表
 */
@Entity
@Table(name = "act_hi_actinst")
public class ActHiActinst implements java.io.Serializable {

	// Fields

	private String id;//主键ID
	private String procDefId;//流程定义ID(流程名：版本号：自增ID)
	private String procInstId; //流程实例ID
	private String executionId;//流程运行时ID
	private String actId;//节点编码
	private String taskId;//任务表ID
	private String callProcInstId;//调用流程实例ID
	private String actName;//节点名称
	private String actType;//节点类型
	private String assignee;//执行人
	private Timestamp startTime;//节点开始时间
	private Timestamp endTime;//节点结束时间
	private Long duration;//持续时间

	@Id
	@GeneratedValue(generator = "hibernate-uuid")
	@GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
	@Column(name = "id_", unique = true, nullable = false, length = 64)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "proc_def_id_", nullable = false, length = 64)
	public String getProcDefId() {
		return this.procDefId;
	}

	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}

	@Column(name = "proc_inst_id_", nullable = false, length = 64)
	public String getProcInstId() {
		return this.procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}

	@Column(name = "execution_id_", nullable = false, length = 64)
	public String getExecutionId() {
		return this.executionId;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	@Column(name = "act_id_", nullable = false)
	public String getActId() {
		return this.actId;
	}

	public void setActId(String actId) {
		this.actId = actId;
	}

	@Column(name = "task_id_", length = 64)
	public String getTaskId() {
		return this.taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	@Column(name = "call_proc_inst_id_", length = 64)
	public String getCallProcInstId() {
		return this.callProcInstId;
	}

	public void setCallProcInstId(String callProcInstId) {
		this.callProcInstId = callProcInstId;
	}

	@Column(name = "act_name_")
	public String getActName() {
		return this.actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	@Column(name = "act_type_", nullable = false)
	public String getActType() {
		return this.actType;
	}

	public void setActType(String actType) {
		this.actType = actType;
	}

	@Column(name = "assignee_", length = 64)
	public String getAssignee() {
		return this.assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	@Column(name = "start_time_", nullable = false, length = 29)
	public Timestamp getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	@Column(name = "end_time_", length = 29)
	public Timestamp getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	@Column(name = "duration_")
	public Long getDuration() {
		return this.duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

}