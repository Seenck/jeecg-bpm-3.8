package org.jeecgframework.workflow.pojo.activiti;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * ActHiDetail entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "act_hi_detail")
public class ActHiDetail implements java.io.Serializable {

	// Fields

	private String id;
	private String type;
	private String procInstId;
	private String executionId;
	private String taskId;
	private String actInstId;
	private String name;
	private String varType;
	private Integer rev;
	private Timestamp time;
	private String bytearrayId;
	private Double double_;
	private Long long_;
	private String text;
	private String text2;

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

	@Column(name = "type_", nullable = false)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "proc_inst_id_", length = 64)
	public String getProcInstId() {
		return this.procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}

	@Column(name = "execution_id_", length = 64)
	public String getExecutionId() {
		return this.executionId;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	@Column(name = "task_id_", length = 64)
	public String getTaskId() {
		return this.taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	@Column(name = "act_inst_id_", length = 64)
	public String getActInstId() {
		return this.actInstId;
	}

	public void setActInstId(String actInstId) {
		this.actInstId = actInstId;
	}

	@Column(name = "name_", nullable = false)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "var_type_", length = 64)
	public String getVarType() {
		return this.varType;
	}

	public void setVarType(String varType) {
		this.varType = varType;
	}

	@Column(name = "rev_")
	public Integer getRev() {
		return this.rev;
	}

	public void setRev(Integer rev) {
		this.rev = rev;
	}

	@Column(name = "time_", nullable = false, length = 29)
	public Timestamp getTime() {
		return this.time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	@Column(name = "bytearray_id_", length = 64)
	public String getBytearrayId() {
		return this.bytearrayId;
	}

	public void setBytearrayId(String bytearrayId) {
		this.bytearrayId = bytearrayId;
	}

	@Column(name = "double_", precision = 17, scale = 17)
	public Double getDouble_() {
		return this.double_;
	}

	public void setDouble_(Double double_) {
		this.double_ = double_;
	}

	@Column(name = "long_")
	public Long getLong_() {
		return this.long_;
	}

	public void setLong_(Long long_) {
		this.long_ = long_;
	}

	@Column(name = "text_", length = 4000)
	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Column(name = "text2_", length = 4000)
	public String getText2() {
		return this.text2;
	}

	public void setText2(String text2) {
		this.text2 = text2;
	}

}