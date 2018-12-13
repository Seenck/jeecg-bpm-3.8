package jeecg.workflow.entity.bus;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


import org.jeecgframework.core.annotation.JeecgEntityTitle;
import org.jeecgframework.workflow.pojo.base.TSBaseBus;
import org.springframework.format.annotation.DateTimeFormat;


/**
 * 请假表
 */
@Entity
@Table(name = "t_b_leave")
@PrimaryKeyJoinColumn(name = "id")
@JeecgEntityTitle(name="请假表")
public class TBLeave extends TSBaseBus implements Serializable {
	private static final long serialVersionUID = 1L;
	private Date begintime;// 开始时间
	private Date endtime;// 结束时间
	private String reason;// 请假原因
	private Date realbegintime;// 实际开始时间
	private Date realendtime;// 实际结束时间

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "begintime")
	public Date getBegintime() {
		return begintime;
	}

	public void setBegintime(Date begintime) {
		this.begintime = begintime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "endtime")
	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	@Column(name = "reason", length = 5000)
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "realbegintime")
	public Date getRealbegintime() {
		return realbegintime;
	}

	public void setRealbegintime(Date realbegintime) {
		this.realbegintime = realbegintime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "realendtime")
	public Date getRealendtime() {
		return realendtime;
	}

	public void setRealendtime(Date realendtime) {
		this.realendtime = realendtime;
	}

}
