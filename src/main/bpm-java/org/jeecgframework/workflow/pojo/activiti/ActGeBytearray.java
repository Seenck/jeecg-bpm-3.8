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

import org.hibernate.annotations.GenericGenerator;

/**
 * 流程文件存储表
 */
@Entity
@Table(name = "act_ge_bytearray")
public class ActGeBytearray implements java.io.Serializable {

	private String id;//主键ID
	private ActReDeployment actReDeployment;
	private Integer rev;//版本号
	private String name;//流程文件名称
	private String bytes;//流程文件XML二进制内容
	private Boolean generated;//是否自动生成
	private Set<ActReModel> actReModelsForEditorSourceExtraValueId = new HashSet<ActReModel>(0);
	private Set<ActReModel> actReModelsForEditorSourceValueId = new HashSet<ActReModel>(0);
	private Set<ActRuJob> actRuJobs = new HashSet<ActRuJob>(0);
	private Set<ActRuVariable> actRuVariables = new HashSet<ActRuVariable>(0);

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
	@JoinColumn(name = "deployment_id_")
	public ActReDeployment getActReDeployment() {
		return this.actReDeployment;
	}

	public void setActReDeployment(ActReDeployment actReDeployment) {
		this.actReDeployment = actReDeployment;
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

	@Column(name = "bytes_")
	public String getBytes() {
		return this.bytes;
	}

	public void setBytes(String bytes) {
		this.bytes = bytes;
	}

	@Column(name = "generated_")
	public Boolean getGenerated() {
		return this.generated;
	}

	public void setGenerated(Boolean generated) {
		this.generated = generated;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "actGeBytearrayByEditorSourceExtraValueId")
	public Set<ActReModel> getActReModelsForEditorSourceExtraValueId() {
		return this.actReModelsForEditorSourceExtraValueId;
	}

	public void setActReModelsForEditorSourceExtraValueId(Set<ActReModel> actReModelsForEditorSourceExtraValueId) {
		this.actReModelsForEditorSourceExtraValueId = actReModelsForEditorSourceExtraValueId;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "actGeBytearrayByEditorSourceValueId")
	public Set<ActReModel> getActReModelsForEditorSourceValueId() {
		return this.actReModelsForEditorSourceValueId;
	}

	public void setActReModelsForEditorSourceValueId(Set<ActReModel> actReModelsForEditorSourceValueId) {
		this.actReModelsForEditorSourceValueId = actReModelsForEditorSourceValueId;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "actGeBytearray")
	public Set<ActRuJob> getActRuJobs() {
		return this.actRuJobs;
	}

	public void setActRuJobs(Set<ActRuJob> actRuJobs) {
		this.actRuJobs = actRuJobs;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "actGeBytearray")
	public Set<ActRuVariable> getActRuVariables() {
		return this.actRuVariables;
	}

	public void setActRuVariables(Set<ActRuVariable> actRuVariables) {
		this.actRuVariables = actRuVariables;
	}

}