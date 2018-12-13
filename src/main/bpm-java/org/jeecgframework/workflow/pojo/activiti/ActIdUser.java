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
 * 引擎用户表
 */
@Entity
@Table(name = "act_id_user")
public class ActIdUser implements java.io.Serializable {

	// Fields

	private String id;
	private Integer rev;
	private String first;
	private String last;
	private String email;
	private String pwd;
	private String pictureId;
	private Set<ActIdMembership> actIdMemberships = new HashSet<ActIdMembership>(0);

	@Id
	@GeneratedValue(generator = "hibernate-uuid")
	@GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
	@Column(name = "id_")
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

	@Column(name = "first_")
	public String getFirst() {
		return this.first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	@Column(name = "last_")
	public String getLast() {
		return this.last;
	}

	public void setLast(String last) {
		this.last = last;
	}

	@Column(name = "email_")
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "pwd_")
	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Column(name = "picture_id_", length = 64)
	public String getPictureId() {
		return this.pictureId;
	}

	public void setPictureId(String pictureId) {
		this.pictureId = pictureId;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "actIdUser")
	public Set<ActIdMembership> getActIdMemberships() {
		return this.actIdMemberships;
	}

	public void setActIdMemberships(Set<ActIdMembership> actIdMemberships) {
		this.actIdMemberships = actIdMemberships;
	}

}