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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 *流程文件存储父表
 */
@Entity
@Table(name = "act_re_deployment")
public class ActReDeployment implements java.io.Serializable {

	// Fields

	private String id;//主键
	private String name;
	private String category;
	private Timestamp deployTime;//部署时间
	private Set<ActReModel> actReModels = new HashSet<ActReModel>(0);
	private Set<ActGeBytearray> actGeBytearraies = new HashSet<ActGeBytearray>(0);

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

	@Column(name = "name_")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "category_")
	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Column(name = "deploy_time_", length = 29)
	public Timestamp getDeployTime() {
		return this.deployTime;
	}

	public void setDeployTime(Timestamp deployTime) {
		this.deployTime = deployTime;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "actReDeployment")
	public Set<ActReModel> getActReModels() {
		return this.actReModels;
	}

	public void setActReModels(Set<ActReModel> actReModels) {
		this.actReModels = actReModels;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "actReDeployment")
	public Set<ActGeBytearray> getActGeBytearraies() {
		return this.actGeBytearraies;
	}

	public void setActGeBytearraies(Set<ActGeBytearray> actGeBytearraies) {
		this.actGeBytearraies = actGeBytearraies;
	}

}