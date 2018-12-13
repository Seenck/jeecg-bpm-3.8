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
 * 任务参与者
 */
@Entity
@Table(name = "act_ru_identitylink")
public class ActRuIdentitylink implements java.io.Serializable {

	// Fields

	private String id;//主键ID
	private ActReProcdef actReProcdef;//流程定义ID
	private ActRuTask actRuTask;//任务ID
	private Integer rev;
	private String groupId;//角色ID
	private String type;
	private String userId;

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
	@JoinColumn(name = "proc_def_id_")
	public ActReProcdef getActReProcdef() {
		return this.actReProcdef;
	}

	public void setActReProcdef(ActReProcdef actReProcdef) {
		this.actReProcdef = actReProcdef;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "task_id_")
	public ActRuTask getActRuTask() {
		return this.actRuTask;
	}

	public void setActRuTask(ActRuTask actRuTask) {
		this.actRuTask = actRuTask;
	}

	@Column(name = "rev_")
	public Integer getRev() {
		return this.rev;
	}

	public void setRev(Integer rev) {
		this.rev = rev;
	}

	@Column(name = "group_id_")
	public String getGroupId() {
		return this.groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	@Column(name = "type_")
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "user_id_")
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}