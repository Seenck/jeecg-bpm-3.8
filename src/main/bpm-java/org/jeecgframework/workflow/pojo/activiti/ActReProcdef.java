package org.jeecgframework.workflow.pojo.activiti;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

/**
 * 流程定义列表
 */
@Entity
@Table(name = "act_re_procdef", uniqueConstraints = @UniqueConstraint(columnNames = { "key_", "version_" }))
public class ActReProcdef implements java.io.Serializable {

	// Fields

	private String id;
	private Integer rev;
	private String category;
	private String name;
	private String key;
	private Integer version;
	private String deploymentId;
	private String resourceName;
	private String dgrmResourceName;
	private String description;
	private Boolean hasStartFormKey;
	private Integer suspensionState;
	private Set<ActRuTask> actRuTasks = new HashSet<ActRuTask>(0);
	private Set<ActRuExecution> actRuExecutions = new HashSet<ActRuExecution>(0);
	private Set<ActRuIdentitylink> actRuIdentitylinks = new HashSet<ActRuIdentitylink>(0);

	@Id
	@GeneratedValue(generator = "hibernate-uuid")
	@GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
	@Column(name="id_")
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "rev_")
	public Integer getRev() {
		return this.rev;
	}

	public void setRev(Integer rev) {
		this.rev = rev;
	}

	@Column(name = "category_")
	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Column(name = "name_")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "key_", nullable = false)
	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Column(name = "version_", nullable = false)
	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Column(name = "deployment_id_", length = 64)
	public String getDeploymentId() {
		return this.deploymentId;
	}

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

	@Column(name = "resource_name_", length = 4000)
	public String getResourceName() {
		return this.resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	@Column(name = "dgrm_resource_name_", length = 4000)
	public String getDgrmResourceName() {
		return this.dgrmResourceName;
	}

	public void setDgrmResourceName(String dgrmResourceName) {
		this.dgrmResourceName = dgrmResourceName;
	}

	@Column(name = "description_", length = 4000)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "has_start_form_key_")
	public Boolean getHasStartFormKey() {
		return this.hasStartFormKey;
	}

	public void setHasStartFormKey(Boolean hasStartFormKey) {
		this.hasStartFormKey = hasStartFormKey;
	}

	@Column(name = "suspension_state_")
	public Integer getSuspensionState() {
		return this.suspensionState;
	}

	public void setSuspensionState(Integer suspensionState) {
		this.suspensionState = suspensionState;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "actReProcdef")
	public Set<ActRuTask> getActRuTasks() {
		return this.actRuTasks;
	}

	public void setActRuTasks(Set<ActRuTask> actRuTasks) {
		this.actRuTasks = actRuTasks;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "actReProcdef")
	public Set<ActRuExecution> getActRuExecutions() {
		return this.actRuExecutions;
	}

	public void setActRuExecutions(Set<ActRuExecution> actRuExecutions) {
		this.actRuExecutions = actRuExecutions;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "actReProcdef")
	public Set<ActRuIdentitylink> getActRuIdentitylinks() {
		return this.actRuIdentitylinks;
	}

	public void setActRuIdentitylinks(Set<ActRuIdentitylink> actRuIdentitylinks) {
		this.actRuIdentitylinks = actRuIdentitylinks;
	}

}