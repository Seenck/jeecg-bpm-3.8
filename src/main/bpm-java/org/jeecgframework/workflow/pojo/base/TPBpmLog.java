package org.jeecgframework.workflow.pojo.base;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 2014-8-17 业务流程日志备注表
 */
@Entity
@Table(name = "t_p_bpm_log")
public class TPBpmLog implements java.io.Serializable {
	private String id;//主键ID
	private String bpm_id; //流程实例ID
	private String op_name;//操作人名称
	private Timestamp op_time;//操作时间
	private String op_code;//操作人工号
	private String op_type;//类型
	private String task_name;//流程名称
	private String task_node;//节点名称
	private String memo;//备注
	List<TPBpmFile> bpmFiles;//附件
	
	@Id
	@GeneratedValue(generator = "hibernate-uuid")
	@GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
	@Column(name = "id", unique = true, nullable = false, length = 64)
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "bpm_id")
	public String getBpm_id() {
		return bpm_id;
	}

	public void setBpm_id(String bpmId) {
		bpm_id = bpmId;
	}

	@Column(name = "op_name")
	public String getOp_name() {
		return op_name;
	}

	public void setOp_name(String opName) {
		op_name = opName;
	}
	
	@Column(name = "op_time", length = 35)
	public Timestamp getOp_time() {
		return op_time;
	}

	public void setOp_time(Timestamp opTime) {
		op_time = opTime;
	}

	@Column(name = "op_code")
	public String getOp_code() {
		return op_code;
	}

	public void setOp_code(String opCode) {
		op_code = opCode;
	}

	@Column(name = "op_type")
	public String getOp_type() {
		return op_type;
	}

	public void setOp_type(String opType) {
		op_type = opType;
	}

	@Column(name = "task_name")
	public String getTask_name() {
		return task_name;
	}

	public void setTask_name(String taskName) {
		task_name = taskName;
	}

	@Column(name = "task_node")
	public String getTask_node() {
		return task_node;
	}

	public void setTask_node(String taskNode) {
		task_node = taskNode;
	}

	@Column(name = "memo")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	@OneToMany(mappedBy="bpmlog",cascade={CascadeType.REMOVE})
	public List<TPBpmFile> getBpmFiles() {
		return bpmFiles;
	}

	public void setBpmFiles(List<TPBpmFile> bpmFiles) {
		this.bpmFiles = bpmFiles;
	}
}
