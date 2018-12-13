package org.jeecgframework.workflow.pojo.activiti;

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
 * 运行时流程变量
 */
@Entity
@Table(name = "act_ru_variable")
public class ActRuVariable implements java.io.Serializable {

	// Fields

	private String id;//主键ID
	private ActRuExecution actRuExecutionByExecutionId;
	private ActRuExecution actRuExecutionByProcInstId;
	private ActGeBytearray actGeBytearray;
	private Integer rev;
	private String type;//变量类型
	private String name;//变量名称
	private String taskId;//任务ID
	private Double double_;//当值类型为DOUBLE时的值
	private Long long_;//当值类型为boolean时的值
	private String text;//当值类型为String时的值
	private String text2;//当值类型为String时的值

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
	@JoinColumn(name = "execution_id_")
	public ActRuExecution getActRuExecutionByExecutionId() {
		return this.actRuExecutionByExecutionId;
	}

	public void setActRuExecutionByExecutionId(ActRuExecution actRuExecutionByExecutionId) {
		this.actRuExecutionByExecutionId = actRuExecutionByExecutionId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "proc_inst_id_")
	public ActRuExecution getActRuExecutionByProcInstId() {
		return this.actRuExecutionByProcInstId;
	}

	public void setActRuExecutionByProcInstId(ActRuExecution actRuExecutionByProcInstId) {
		this.actRuExecutionByProcInstId = actRuExecutionByProcInstId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bytearray_id_")
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

	@Column(name = "name_", nullable = false)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "task_id_", length = 64)
	public String getTaskId() {
		return this.taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
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