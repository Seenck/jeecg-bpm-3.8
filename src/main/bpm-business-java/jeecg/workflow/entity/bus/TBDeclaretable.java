package jeecg.workflow.entity.bus;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.jeecgframework.core.annotation.JeecgEntityTitle;
import org.jeecgframework.workflow.pojo.base.TSBaseBus;


/**
 * TDeclaretable entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_b_declaretable")
@PrimaryKeyJoinColumn(name = "id")
public class TBDeclaretable extends TSBaseBus implements java.io.Serializable {
	private String declareno;
	private String constructionunit;
	private String projectname;
	private String projectlocation;
	private String administrativeregion;
	private Date declaredate;
	private Short declaretype;
	private String legalrepresentative;
	private String phone;
	private String agent;
	private String agentphone;
	private String address;
	private String post;
	private String projectnum;
	private String projectaddress;
	private Double totalconstructionarea;
	private Double floorarea;
	private Double undergroundarea;
	private BigDecimal householdnum;
	private BigDecimal peoplenum;
	private String note;
	private String scandata;
	@Column(name = "declareno", length = 100)
	public String getDeclareno() {
		return this.declareno;
	}

	public void setDeclareno(String declareno) {
		this.declareno = declareno;
	}

	@Column(name = "constructionunit", nullable = false, length = 100)
	public String getConstructionunit() {
		return this.constructionunit;
	}

	public void setConstructionunit(String constructionunit) {
		this.constructionunit = constructionunit;
	}

	@Column(name = "projectname", nullable = false, length = 100)
	public String getProjectname() {
		return this.projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}

	@Column(name = "projectlocation", length = 100)
	public String getProjectlocation() {
		return this.projectlocation;
	}

	public void setProjectlocation(String projectlocation) {
		this.projectlocation = projectlocation;
	}

	@Column(name = "administrativeregion", length = 50)
	public String getAdministrativeregion() {
		return this.administrativeregion;
	}

	public void setAdministrativeregion(String administrativeregion) {
		this.administrativeregion = administrativeregion;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "declaredate", length = 13)
	public Date getDeclaredate() {
		return this.declaredate;
	}

	public void setDeclaredate(Date declaredate) {
		this.declaredate = declaredate;
	}

	@Column(name = "declaretype", nullable = false)
	public Short getDeclaretype() {
		return this.declaretype;
	}

	public void setDeclaretype(Short declaretype) {
		this.declaretype = declaretype;
	}

	@Column(name = "legalrepresentative", nullable = false, length = 10)
	public String getLegalrepresentative() {
		return this.legalrepresentative;
	}

	public void setLegalrepresentative(String legalrepresentative) {
		this.legalrepresentative = legalrepresentative;
	}

	@Column(name = "phone", nullable = false, length = 30)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "agent", length = 10)
	public String getAgent() {
		return this.agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	@Column(name = "agentphone", length = 30)
	public String getAgentphone() {
		return this.agentphone;
	}

	public void setAgentphone(String agentphone) {
		this.agentphone = agentphone;
	}

	@Column(name = "address", length = 300)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "post", length = 10)
	public String getPost() {
		return this.post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	@Column(name = "projectnum", length = 50)
	public String getProjectnum() {
		return this.projectnum;
	}

	public void setProjectnum(String projectnum) {
		this.projectnum = projectnum;
	}

	@Column(name = "projectaddress", length = 200)
	public String getProjectaddress() {
		return this.projectaddress;
	}

	public void setProjectaddress(String projectaddress) {
		this.projectaddress = projectaddress;
	}

	@Column(name = "totalconstructionarea", precision = 12)
	public Double getTotalconstructionarea() {
		return this.totalconstructionarea;
	}

	public void setTotalconstructionarea(Double totalconstructionarea) {
		this.totalconstructionarea = totalconstructionarea;
	}

	@Column(name = "floorarea", precision = 12)
	public Double getFloorarea() {
		return this.floorarea;
	}

	public void setFloorarea(Double floorarea) {
		this.floorarea = floorarea;
	}

	@Column(name = "undergroundarea", precision = 12)
	public Double getUndergroundarea() {
		return this.undergroundarea;
	}

	public void setUndergroundarea(Double undergroundarea) {
		this.undergroundarea = undergroundarea;
	}

	@Column(name = "householdnum", precision = 131089, scale = 0)
	public BigDecimal getHouseholdnum() {
		return this.householdnum;
	}

	public void setHouseholdnum(BigDecimal householdnum) {
		this.householdnum = householdnum;
	}

	@Column(name = "peoplenum", precision = 131089, scale = 0)
	public BigDecimal getPeoplenum() {
		return this.peoplenum;
	}

	public void setPeoplenum(BigDecimal peoplenum) {
		this.peoplenum = peoplenum;
	}

	@Column(name = "note", length = 300)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "scandata", length = 1)
	public String getScandata() {
		return this.scandata;
	}

	public void setScandata(String scandata) {
		this.scandata = scandata;
	}
}