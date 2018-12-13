package jeecg.bizflow.overtime.vo;

import java.io.Serializable;
import java.util.Date;

public class OvertimeVO implements Serializable{

	private static final long serialVersionUID = -1887239613819496357L;
	/**ID**/
	private java.lang.String id;
	/**加班开始时间**/
	private java.util.Date beginTime;
	/**加班结束时间**/
	private java.util.Date endTime;
	/**总加班时间（天）**/
	private java.lang.String totalDay;
	/**总加班时间（小时）**/
	private java.lang.String totalHour;
	/**总加班时间**/
	private java.lang.String total;
	/**未经补偿时间**/
	private java.lang.String makeupTime;
	/**加班表调休（天）**/
	private java.lang.Integer joApplyDay;
	/**加班表调休（小时）**/
	private java.lang.Integer joApplyHour;
	/**调休申请（天）**/
	private java.lang.Integer applyDay;
	/**调休申请（小时）**/
	private java.lang.Integer applyHour;
	
	
	public OvertimeVO() {
		super();
	}


	public OvertimeVO(String id, Date beginTime, Date endTime, String totalDay,
			String totalHour, Integer joApplyDay, Integer joApplyHour,
			Integer applyDay, Integer applyHour) {
		super();
		this.id = id;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.totalDay = totalDay;
		this.totalHour = totalHour;
		this.joApplyDay = joApplyDay;
		this.joApplyHour = joApplyHour;
		this.applyDay = applyDay;
		this.applyHour = applyHour;
	}

	
	public OvertimeVO(String id, Date beginTime, Date endTime, String total,
			String makeupTime, Integer applyDay, Integer applyHour) {
		super();
		this.id = id;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.total = total;
		this.makeupTime = makeupTime;
		this.applyDay = applyDay;
		this.applyHour = applyHour;
	}


	public java.lang.String getId() {
		return id;
	}


	public void setId(java.lang.String id) {
		this.id = id;
	}


	public java.util.Date getBeginTime() {
		return beginTime;
	}


	public void setBeginTime(java.util.Date beginTime) {
		this.beginTime = beginTime;
	}


	public java.util.Date getEndTime() {
		return endTime;
	}


	public void setEndTime(java.util.Date endTime) {
		this.endTime = endTime;
	}


	public java.lang.String getTotal() {
		return total;
	}


	public void setTotal(java.lang.String total) {
		this.total = total;
	}


	public java.lang.String getMakeupTime() {
		return makeupTime;
	}


	public void setMakeupTime(java.lang.String makeupTime) {
		this.makeupTime = makeupTime;
	}


	public java.lang.Integer getApplyDay() {
		return applyDay;
	}


	public void setApplyDay(java.lang.Integer applyDay) {
		this.applyDay = applyDay;
	}


	public java.lang.Integer getApplyHour() {
		return applyHour;
	}


	public void setApplyHour(java.lang.Integer applyHour) {
		this.applyHour = applyHour;
	}


	public java.lang.String getTotalDay() {
		return totalDay;
	}


	public void setTotalDay(java.lang.String totalDay) {
		this.totalDay = totalDay;
	}


	public java.lang.String getTotalHour() {
		return totalHour;
	}


	public void setTotalHour(java.lang.String totalHour) {
		this.totalHour = totalHour;
	}


	public java.lang.Integer getJoApplyDay() {
		return joApplyDay;
	}


	public void setJoApplyDay(java.lang.Integer joApplyDay) {
		this.joApplyDay = joApplyDay;
	}


	public java.lang.Integer getJoApplyHour() {
		return joApplyHour;
	}


	public void setJoApplyHour(java.lang.Integer joApplyHour) {
		this.joApplyHour = joApplyHour;
	}
	
	
	
}
