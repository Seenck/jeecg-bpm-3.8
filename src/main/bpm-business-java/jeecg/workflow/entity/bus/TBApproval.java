package jeecg.workflow.entity.bus;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.jeecgframework.core.annotation.JeecgEntityTitle;
import org.jeecgframework.core.common.entity.IdEntity;
import org.jeecgframework.web.system.pojo.base.TSUser;


/**
 * 业务审批表
 */
@Entity
@Table(name = "t_b_approval")
public class TBApproval extends IdEntity implements java.io.Serializable {

	
	private TSUser TSUser;
	private String approvalrole;
	private String approvalopinion;
	private Timestamp approvaldate;
	private String note;
	private Integer approvaloperation;



	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userid")
	public TSUser getTSUser() {
		return this.TSUser;
	}

	public void setTSUser(TSUser TSUser) {
		this.TSUser = TSUser;
	}

	@Column(name = "approvalrole", nullable = false, length = 100)
	public String getApprovalrole() {
		return this.approvalrole;
	}

	public void setApprovalrole(String approvalrole) {
		this.approvalrole = approvalrole;
	}

	@Column(name = "approvalopinion", length = 300)
	public String getApprovalopinion() {
		return this.approvalopinion;
	}

	public void setApprovalopinion(String approvalopinion) {
		this.approvalopinion = approvalopinion;
	}

	@Column(name = "approvaldate", nullable = false, length = 35)
	public Timestamp getApprovaldate() {
		return this.approvaldate;
	}

	public void setApprovaldate(Timestamp approvaldate) {
		this.approvaldate = approvaldate;
	}

	@Column(name = "note", length = 300)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "approvaloperation")
	public Integer getApprovaloperation() {
		return this.approvaloperation;
	}

	public void setApprovaloperation(Integer approvaloperation) {
		this.approvaloperation = approvaloperation;
	}

}