package jeecg.workflow.entity.bus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.jeecgframework.core.common.entity.IdEntity;


/**
 * TInfotype entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_b_infotype")
public class TBInfotype extends IdEntity implements java.io.Serializable {
	private String typecode;
	private String typename;
	private String description;
	private String note;
//	private Set<TSAttachment> TSAttachments = new HashSet<TSAttachment>(0);


	@Column(name = "typename", nullable = false, length = 100)
	public String getTypename() {
		return this.typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	@Column(name = "description", length = 300)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "note", length = 300)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TBInfotype")
//	public Set<TSAttachment> getTSAttachments() {
//		return this.TSAttachments;
//	}
//
//	public void setTSAttachments(Set<TSAttachment> TSAttachments) {
//		this.TSAttachments = TSAttachments;
//	}
	@Column(name = "typecode", length = 20)
	public String getTypecode() {
		return typecode;
	}

	public void setTypecode(String typecode) {
		this.typecode = typecode;
	}
	
}