package org.jeecgframework.workflow.pojo.activiti;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 运行时定时任务数据表
 */
@Entity
@Table(name = "act_ru_job")
public class ActRuJob implements java.io.Serializable {

	// Fields

	private String id;
	private ActGeBytearray actGeBytearray;
	private Integer rev;
	private String type;
	private Timestamp lockExpTime;
	private String lockOwner;
	private Boolean exclusive;
	private String executionId;
	private String processInstanceId;
	private String procDefId;
	private Integer retries;
	private String exceptionMsg;
	private Timestamp duedate;
	private String repeat;
	private String handlerType;
	private String handlerCfg;

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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "exception_stack_id_")
	public ActGeBytearray getActGeBytearray() {
		return this.actGeBytearray;
	}

	public void setActGeBytearray(ActGeBytearray actGeBytearray) {
		this.actGeBytearray = actGeBytearray;
	}

	@Column(name = "rev_")
	public Integer getRev() {
		return this.rev;
	}

	public void setRev(Integer rev) {
		this.rev = rev;
	}

	@Column(name = "type_", nullable = false)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "lock_exp_time_", length = 29)
	public Timestamp getLockExpTime() {
		return this.lockExpTime;
	}

	public void setLockExpTime(Timestamp lockExpTime) {
		this.lockExpTime = lockExpTime;
	}

	@Column(name = "lock_owner_")
	public String getLockOwner() {
		return this.lockOwner;
	}

	public void setLockOwner(String lockOwner) {
		this.lockOwner = lockOwner;
	}

	@Column(name = "exclusive_")
	public Boolean getExclusive() {
		return this.exclusive;
	}

	public void setExclusive(Boolean exclusive) {
		this.exclusive = exclusive;
	}

	@Column(name = "execution_id_", length = 64)
	public String getExecutionId() {
		return this.executionId;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	@Column(name = "process_instance_id_", length = 64)
	public String getProcessInstanceId() {
		return this.processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	@Column(name = "proc_def_id_", length = 64)
	public String getProcDefId() {
		return this.procDefId;
	}

	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}

	@Column(name = "retries_")
	public Integer getRetries() {
		return this.retries;
	}

	public void setRetries(Integer retries) {
		this.retries = retries;
	}

	@Column(name = "exception_msg_", length = 4000)
	public String getExceptionMsg() {
		return this.exceptionMsg;
	}

	public void setExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}

	@Column(name = "duedate_", length = 29)
	public Timestamp getDuedate() {
		return this.duedate;
	}

	public void setDuedate(Timestamp duedate) {
		this.duedate = duedate;
	}

	@Column(name = "repeat_")
	public String getRepeat() {
		return this.repeat;
	}

	public void setRepeat(String repeat) {
		this.repeat = repeat;
	}

	@Column(name = "handler_type_")
	public String getHandlerType() {
		return this.handlerType;
	}

	public void setHandlerType(String handlerType) {
		this.handlerType = handlerType;
	}

	@Column(name = "handler_cfg_", length = 4000)
	public String getHandlerCfg() {
		return this.handlerCfg;
	}

	public void setHandlerCfg(String handlerCfg) {
		this.handlerCfg = handlerCfg;
	}

}