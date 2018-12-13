package org.jeecgframework.workflow.pojo.activiti;

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
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

/**
 * 流程执行表(活动实例)
 */
@Entity
@Table(name = "act_ru_execution", uniqueConstraints = @UniqueConstraint(columnNames = { "proc_def_id_", "business_key_" }))
public class ActRuExecution implements java.io.Serializable {

	// Fields

	private String id;//主键ID
	private ActRuExecution actRuExecutionByParentId;
	private ActRuExecution actRuExecutionBySuperExec;
	private ActReProcdef actReProcdef;
	private ActRuExecution actRuExecutionByProcInstId;
	private Integer rev;
	private String businessKey;//业务主键
	private String actId;//流程节点ID
	private Boolean isActive;
	private Boolean isConcurrent;//是否并行
	private Boolean isScope;//是否在作用域
	private Boolean isEventScope;
	private Integer suspensionState;
	private Integer cachedEntState;
	private Set<ActRuVariable> actRuVariablesForExecutionId = new HashSet<ActRuVariable>(0);
	private Set<ActRuExecution> actRuExecutionsForParentId = new HashSet<ActRuExecution>(0);
	private Set<ActRuVariable> actRuVariablesForProcInstId = new HashSet<ActRuVariable>(0);
	private Set<ActRuTask> actRuTasksForProcInstId = new HashSet<ActRuTask>(0);
	private Set<ActRuExecution> actRuExecutionsForProcInstId = new HashSet<ActRuExecution>(0);
	private Set<ActRuExecution> actRuExecutionsForSuperExec = new HashSet<ActRuExecution>(0);
	private Set<ActRuTask> actRuTasksForExecutionId = new HashSet<ActRuTask>(0);
	private Set<ActRuEventSubscr> actRuEventSubscrs = new HashSet<ActRuEventSubscr>(0);

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
	@JoinColumn(name = "parent_id_")
	public ActRuExecution getActRuExecutionByParentId() {
		return this.actRuExecutionByParentId;
	}

	public void setActRuExecutionByParentId(ActRuExecution actRuExecutionByParentId) {
		this.actRuExecutionByParentId = actRuExecutionByParentId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "super_exec_")
	public ActRuExecution getActRuExecutionBySuperExec() {
		return this.actRuExecutionBySuperExec;
	}

	public void setActRuExecutionBySuperExec(ActRuExecution actRuExecutionBySuperExec) {
		this.actRuExecutionBySuperExec = actRuExecutionBySuperExec;
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

	@Column(name = "business_key_")
	public String getBusinessKey() {
		return this.businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	@Column(name = "act_id_")
	public String getActId() {
		return this.actId;
	}

	public void setActId(String actId) {
		this.actId = actId;
	}

	@Column(name = "is_active_")
	public Boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Column(name = "is_concurrent_")
	public Boolean getIsConcurrent() {
		return this.isConcurrent;
	}

	public void setIsConcurrent(Boolean isConcurrent) {
		this.isConcurrent = isConcurrent;
	}

	@Column(name = "is_scope_")
	public Boolean getIsScope() {
		return this.isScope;
	}

	public void setIsScope(Boolean isScope) {
		this.isScope = isScope;
	}

	@Column(name = "is_event_scope_")
	public Boolean getIsEventScope() {
		return this.isEventScope;
	}

	public void setIsEventScope(Boolean isEventScope) {
		this.isEventScope = isEventScope;
	}

	@Column(name = "suspension_state_")
	public Integer getSuspensionState() {
		return this.suspensionState;
	}

	public void setSuspensionState(Integer suspensionState) {
		this.suspensionState = suspensionState;
	}

	@Column(name = "cached_ent_state_")
	public Integer getCachedEntState() {
		return this.cachedEntState;
	}

	public void setCachedEntState(Integer cachedEntState) {
		this.cachedEntState = cachedEntState;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "actRuExecutionByExecutionId")
	public Set<ActRuVariable> getActRuVariablesForExecutionId() {
		return this.actRuVariablesForExecutionId;
	}

	public void setActRuVariablesForExecutionId(Set<ActRuVariable> actRuVariablesForExecutionId) {
		this.actRuVariablesForExecutionId = actRuVariablesForExecutionId;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "actRuExecutionByParentId")
	public Set<ActRuExecution> getActRuExecutionsForParentId() {
		return this.actRuExecutionsForParentId;
	}

	public void setActRuExecutionsForParentId(Set<ActRuExecution> actRuExecutionsForParentId) {
		this.actRuExecutionsForParentId = actRuExecutionsForParentId;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "actRuExecutionByProcInstId")
	public Set<ActRuVariable> getActRuVariablesForProcInstId() {
		return this.actRuVariablesForProcInstId;
	}

	public void setActRuVariablesForProcInstId(Set<ActRuVariable> actRuVariablesForProcInstId) {
		this.actRuVariablesForProcInstId = actRuVariablesForProcInstId;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "actRuExecutionByProcInstId")
	public Set<ActRuTask> getActRuTasksForProcInstId() {
		return this.actRuTasksForProcInstId;
	}

	public void setActRuTasksForProcInstId(Set<ActRuTask> actRuTasksForProcInstId) {
		this.actRuTasksForProcInstId = actRuTasksForProcInstId;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "actRuExecutionByProcInstId")
	public Set<ActRuExecution> getActRuExecutionsForProcInstId() {
		return this.actRuExecutionsForProcInstId;
	}

	public void setActRuExecutionsForProcInstId(Set<ActRuExecution> actRuExecutionsForProcInstId) {
		this.actRuExecutionsForProcInstId = actRuExecutionsForProcInstId;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "actRuExecutionBySuperExec")
	public Set<ActRuExecution> getActRuExecutionsForSuperExec() {
		return this.actRuExecutionsForSuperExec;
	}

	public void setActRuExecutionsForSuperExec(Set<ActRuExecution> actRuExecutionsForSuperExec) {
		this.actRuExecutionsForSuperExec = actRuExecutionsForSuperExec;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "actRuExecutionByExecutionId")
	public Set<ActRuTask> getActRuTasksForExecutionId() {
		return this.actRuTasksForExecutionId;
	}

	public void setActRuTasksForExecutionId(Set<ActRuTask> actRuTasksForExecutionId) {
		this.actRuTasksForExecutionId = actRuTasksForExecutionId;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "actRuExecution")
	public Set<ActRuEventSubscr> getActRuEventSubscrs() {
		return this.actRuEventSubscrs;
	}

	public void setActRuEventSubscrs(Set<ActRuEventSubscr> actRuEventSubscrs) {
		this.actRuEventSubscrs = actRuEventSubscrs;
	}

}