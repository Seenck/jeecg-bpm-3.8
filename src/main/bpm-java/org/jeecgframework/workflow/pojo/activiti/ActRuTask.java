package org.jeecgframework.workflow.pojo.activiti;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 运行时任务表
 */
@Entity
@Table(name = "act_ru_task")
public class ActRuTask implements java.io.Serializable {

	// Fields

	private String id;//主键ID
	private ActRuExecution actRuExecutionByExecutionId;
	private ActReProcdef actReProcdef;
	private ActRuExecution actRuExecutionByProcInstId;
	private Integer rev;
	private String name;//任务定义名称 
	private String parentTaskId;
	private String description;//任务描述
	private String taskDefKey;//任务定义ID
	private String owner;//创建人
	private String assignee;//执行人
	private String delegation;//描述
	private Integer priority;//优先级
	private Timestamp createTime;//创建时间
	private Timestamp dueDate;//任务完成期限
	private Integer suspensionState;
	private Set<ActRuIdentitylink> actRuIdentitylinks = new HashSet<ActRuIdentitylink>(0);

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
	@JoinColumn(name = "proc_def_id_")
	public ActReProcdef getActReProcdef() {
		return this.actReProcdef;
	}

	public void setActReProcdef(ActReProcdef actReProcdef) {
		this.actReProcdef = actReProcdef;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "proc_inst_id_")
	public ActRuExecution getActRuExecutionByProcInstId() {
		return this.actRuExecutionByProcInstId;
	}

	public void setActRuExecutionByProcInstId(ActRuExecution actRuExecutionByProcInstId) {
		this.actRuExecutionByProcInstId = actRuExecutionByProcInstId;
	}

	@Column(name = "rev_")
	public Integer getRev() {
		return this.rev;
	}

	public void setRev(Integer rev) {
		this.rev = rev;
	}

	@Column(name = "name_")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "parent_task_id_", length = 64)
	public String getParentTaskId() {
		return this.parentTaskId;
	}

	public void setParentTaskId(String parentTaskId) {
		this.parentTaskId = parentTaskId;
	}

	@Column(name = "description_", length = 4000)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "task_def_key_")
	public String getTaskDefKey() {
		return this.taskDefKey;
	}

	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}

	@Column(name = "owner_")
	public String getOwner() {
		return this.owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	@Column(name = "assignee_")
	public String getAssignee() {
		return this.assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	@Column(name = "delegation_", length = 64)
	public String getDelegation() {
		return this.delegation;
	}

	public void setDelegation(String delegation) {
		this.delegation = delegation;
	}

	@Column(name = "priority_")
	public Integer getPriority() {
		return this.priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	@Column(name = "create_time_", length = 29)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "due_date_", length = 29)
	public Timestamp getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(Timestamp dueDate) {
		this.dueDate = dueDate;
	}

	@Column(name = "suspension_state_")
	public Integer getSuspensionState() {
		return this.suspensionState;
	}

	public void setSuspensionState(Integer suspensionState) {
		this.suspensionState = suspensionState;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "actRuTask")
	public Set<ActRuIdentitylink> getActRuIdentitylinks() {
		return this.actRuIdentitylinks;
	}

	public void setActRuIdentitylinks(Set<ActRuIdentitylink> actRuIdentitylinks) {
		this.actRuIdentitylinks = actRuIdentitylinks;
	}

}