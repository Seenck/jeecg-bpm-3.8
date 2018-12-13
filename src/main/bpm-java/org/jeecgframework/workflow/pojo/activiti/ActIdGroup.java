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

import org.hibernate.annotations.GenericGenerator;

/**
 * 角色表
 */
@Entity
@Table(name = "act_id_group")
public class ActIdGroup implements java.io.Serializable {

	// Fields

	private String id;
	private Integer rev;
	private String name;
	private String type;
	private Set<ActIdMembership> actIdMemberships = new HashSet<ActIdMembership>(0);

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

	@Column(name = "type_")
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "actIdGroup")
	public Set<ActIdMembership> getActIdMemberships() {
		return this.actIdMemberships;
	}

	public void setActIdMemberships(Set<ActIdMembership> actIdMemberships) {
		this.actIdMemberships = actIdMemberships;
	}

}