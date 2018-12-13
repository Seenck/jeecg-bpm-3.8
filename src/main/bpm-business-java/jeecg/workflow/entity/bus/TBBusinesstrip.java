package jeecg.workflow.entity.bus;

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
 * 出差申请表
 */
@Entity
@Table(name = "t_b_businesstrip")
@PrimaryKeyJoinColumn(name = "id")
@JeecgEntityTitle(name="出差申请表")
public class TBBusinesstrip extends TSBaseBus implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String bustriplocale;//出差地址
	private Short peoplenum;//出差人数
	private Date begintime;//开始时间
	private Date endtime;//返回时间
	private Short goouttype;//出行方式
	private String bustripreson;//出差事由
	private Double bustripmoney;//出差经费

	
	@Column(name = "bustriplocale", length = 100)
	public String getBustriplocale() {
		return this.bustriplocale;
	}

	public void setBustriplocale(String bustriplocale) {
		this.bustriplocale = bustriplocale;
	}

	@Column(name = "peoplenum")
	public Short getPeoplenum() {
		return this.peoplenum;
	}

	public void setPeoplenum(Short peoplenum) {
		this.peoplenum = peoplenum;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "begintime")
	public Date getBegintime() {
		return this.begintime;
	}

	public void setBegintime(Date begintime) {
		this.begintime = begintime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "endtime")
	public Date getEndtime() {
		return this.endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	@Column(name = "goouttype")
	public Short getGoouttype() {
		return this.goouttype;
	}

	public void setGoouttype(Short goouttype) {
		this.goouttype = goouttype;
	}

	@Column(name = "bustripreson", length = 2000)
	public String getBustripreson() {
		return this.bustripreson;
	}

	public void setBustripreson(String bustripreson) {
		this.bustripreson = bustripreson;
	}

	@Column(name = "bustripmoney", scale = 0)
	public Double getBustripmoney() {
		return this.bustripmoney;
	}

	public void setBustripmoney(Double bustripmoney) {
		this.bustripmoney = bustripmoney;
	}

}