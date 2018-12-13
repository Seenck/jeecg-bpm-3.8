package org.jeecgframework.workflow.pojo.activiti;

import java.sql.Timestamp;
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
 * ActReModel entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "act_re_model")
public class ActReModel implements java.io.Serializable {

	// Fields

	private String id;
	private ActGeBytearray actGeBytearrayByEditorSourceExtraValueId;
	private ActGeBytearray actGeBytearrayByEditorSourceValueId;
	private ActReDeployment actReDeployment;
	private Integer rev;
	private String name;
	private String key;
	private String category;
	private Timestamp createTime;
	private Timestamp lastUpdateTime;
	private Integer version;
	private String metaInfo;

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
	@JoinColumn(name = "editor_source_extra_value_id_")
	public ActGeBytearray getActGeBytearrayByEditorSourceExtraValueId() {
		return this.actGeBytearrayByEditorSourceExtraValueId;
	}

	public void setActGeBytearrayByEditorSourceExtraValueId(ActGeBytearray actGeBytearrayByEditorSourceExtraValueId) {
		this.actGeBytearrayByEditorSourceExtraValueId = actGeBytearrayByEditorSourceExtraValueId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "editor_source_value_id_")
	public ActGeBytearray getActGeBytearrayByEditorSourceValueId() {
		return this.actGeBytearrayByEditorSourceValueId;
	}

	public void setActGeBytearrayByEditorSourceValueId(ActGeBytearray actGeBytearrayByEditorSourceValueId) {
		this.actGeBytearrayByEditorSourceValueId = actGeBytearrayByEditorSourceValueId;
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

	@Column(name = "key_")
	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Column(name = "category_")
	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Column(name = "create_time_", length = 29)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "last_update_time_", length = 29)
	public Timestamp getLastUpdateTime() {
		return this.lastUpdateTime;
	}

	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	@Column(name = "version_")
	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Column(name = "meta_info_", length = 4000)
	public String getMetaInfo() {
		return this.metaInfo;
	}

	public void setMetaInfo(String metaInfo) {
		this.metaInfo = metaInfo;
	}

}