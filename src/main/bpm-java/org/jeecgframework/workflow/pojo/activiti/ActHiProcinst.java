package org.jeecgframework.workflow.pojo.activiti;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

/**
 * 业务流程实例历史表
 */
@Entity
@Table(name = "act_hi_procinst", uniqueConstraints = { @UniqueConstraint(columnNames = { "proc_def_id_", "business_key_" }), @UniqueConstraint(columnNames = "proc_inst_id_") })
public class ActHiProcinst implements java.io.Serializable {

	// Fields

	private String id;//主键ID
	private String procInstId;//流程实例ID
	private String businessKey;//业务主键ID
	private String procDefId;//流程定义ID
	private Timestamp startTime;//开始时间
	private Timestamp endTime;//结束时间
	private Long duration;//持续时间
	private String startUserId;//
	private String startActId;//开始节点ID
	private String endActId;//结束节点ID
	private String superProcessInstanceId;//父流程实例ID
	private String deleteReason;//删除原因

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

	@Column(name = "proc_inst_id_", unique = true, nullable = false, length = 64)
	public String getProcInstId() {
		return this.procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}

	@Column(name = "business_key_")
	public String getBusinessKey() {
		return this.businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	@Column(name = "proc_def_id_", nullable = false, length = 64)
	public String getProcDefId() {
		return this.procDefId;
	}

	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
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

	@Column(name = "start_user_id_")
	public String getStartUserId() {
		return this.startUserId;
	}

	public void setStartUserId(String startUserId) {
		this.startUserId = startUserId;
	}

	@Column(name = "start_act_id_")
	public String getStartActId() {
		return this.startActId;
	}

	public void setStartActId(String startActId) {
		this.startActId = startActId;
	}

	@Column(name = "end_act_id_")
	public String getEndActId() {
		return this.endActId;
	}

	public void setEndActId(String endActId) {
		this.endActId = endActId;
	}

	@Column(name = "super_process_instance_id_", length = 64)
	public String getSuperProcessInstanceId() {
		return this.superProcessInstanceId;
	}

	public void setSuperProcessInstanceId(String superProcessInstanceId) {
		this.superProcessInstanceId = superProcessInstanceId;
	}

	@Column(name = "delete_reason_", length = 4000)
	public String getDeleteReason() {
		return this.deleteReason;
	}

	public void setDeleteReason(String deleteReason) {
		this.deleteReason = deleteReason;
	}

}