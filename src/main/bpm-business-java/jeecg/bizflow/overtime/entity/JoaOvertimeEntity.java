package jeecg.bizflow.overtime.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 加班申请单
 * @author onlineGenerator
 * @date 2018-08-01 15:54:24
 * @version V1.0   
 *
 */
@Entity
@Table(name = "joa_overtime", schema = "")
@SuppressWarnings("serial")
public class JoaOvertimeEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**申请人*/
	@Excel(name="申请人",width=15)
	private java.lang.String applyUser;
	/**部门*/
	@Excel(name="部门",width=15)
	private java.lang.String department;
	/**申请时间*/
	@Excel(name="申请时间",width=15,format = "yyyy-MM-dd HH:mm:ss")
	private java.util.Date applyTime;
	/**加班时段*/
	@Excel(name="加班时段",width=15,dicCode="overtime")
	private java.lang.String overtimeType;
	/**加班开始时间*/
	@Excel(name="加班开始时间",width=15,format = "yyyy-MM-dd HH:mm:ss")
	private java.util.Date beginTime;
	/**加班结束时间*/
	@Excel(name="加班结束时间",width=15,format = "yyyy-MM-dd HH:mm:ss")
	private java.util.Date endTime;
	/**加班原由*/
	@Excel(name="加班原由",width=15)
	private java.lang.String reason;
	/**总加班时间 天*/
	@Excel(name="总加班时间 天",width=15)
	private java.lang.String totalDay;
	/**总加班时间 小时*/
	@Excel(name="总加班时间 小时",width=15)
	private java.lang.String totalHour;
	/**直接领导审批*/
	private java.lang.String leaderRemark;
	/**部门领导审批*/
	private java.lang.String deptLeaderRemark;
	/**调休申请(天)**/
	private java.lang.Integer applyDay;
	/**调休申请(小时)**/
	private java.lang.Integer applyHour;
	/**创建人*/
	private java.lang.String createName;
	/**创建人id*/
	private java.lang.String createBy;
	/**创建时间*/
	private java.util.Date createDate;
	/**修改人*/
	private java.lang.String updateName;
	/**修改人id*/
	private java.lang.String updateBy;
	/**修改时间*/
	private java.util.Date updateDate;
	/**所属部门*/
	private java.lang.String sysOrgCode;
	/**所属公司*/
	private java.lang.String sysCompanyCode;
	/**流程状态*/
	private java.lang.String bpmStatus;
	/**逻辑删除标识0未删除1删除*/
	private java.lang.Integer delFlag;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")

	@Column(name ="ID",nullable=false,length=32)
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  id
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  申请人
	 */

	@Column(name ="APPLY_USER",nullable=true,length=50)
	public java.lang.String getApplyUser(){
		return this.applyUser;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  申请人
	 */
	public void setApplyUser(java.lang.String applyUser){
		this.applyUser = applyUser;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  部门
	 */

	@Column(name ="DEPARTMENT",nullable=true,length=100)
	public java.lang.String getDepartment(){
		return this.department;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  部门
	 */
	public void setDepartment(java.lang.String department){
		this.department = department;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  申请时间
	 */

	@Column(name ="APPLY_TIME",nullable=true)
	public java.util.Date getApplyTime(){
		return this.applyTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  申请时间
	 */
	public void setApplyTime(java.util.Date applyTime){
		this.applyTime = applyTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  加班时段
	 */

	@Column(name ="OVERTIME_TYPE",nullable=true,length=10)
	public java.lang.String getOvertimeType(){
		return this.overtimeType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  加班时段
	 */
	public void setOvertimeType(java.lang.String overtimeType){
		this.overtimeType = overtimeType;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  加班开始时间
	 */

	@Column(name ="BEGIN_TIME",nullable=true)
	public java.util.Date getBeginTime(){
		return this.beginTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  加班开始时间
	 */
	public void setBeginTime(java.util.Date beginTime){
		this.beginTime = beginTime;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  加班结束时间
	 */

	@Column(name ="END_TIME",nullable=true)
	public java.util.Date getEndTime(){
		return this.endTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  加班结束时间
	 */
	public void setEndTime(java.util.Date endTime){
		this.endTime = endTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  加班原由
	 */

	@Column(name ="REASON",nullable=true,length=500)
	public java.lang.String getReason(){
		return this.reason;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  加班原由
	 */
	public void setReason(java.lang.String reason){
		this.reason = reason;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  总加班时间 天
	 */

	@Column(name ="TOTAL_DAY",nullable=true,length=10)
	public java.lang.String getTotalDay(){
		return this.totalDay;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  总加班时间 天
	 */
	public void setTotalDay(java.lang.String totalDay){
		this.totalDay = totalDay;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  总加班时间 小时
	 */

	@Column(name ="TOTAL_HOUR",nullable=true,length=10)
	public java.lang.String getTotalHour(){
		return this.totalHour;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  总加班时间 小时
	 */
	public void setTotalHour(java.lang.String totalHour){
		this.totalHour = totalHour;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  直接领导审批
	 */

	@Column(name ="LEADER_REMARK",nullable=true,length=50)
	public java.lang.String getLeaderRemark(){
		return this.leaderRemark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  直接领导审批
	 */
	public void setLeaderRemark(java.lang.String leaderRemark){
		this.leaderRemark = leaderRemark;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  部门领导审批
	 */

	@Column(name ="DEPT_LEADER_REMARK",nullable=true,length=50)
	public java.lang.String getDeptLeaderRemark(){
		return this.deptLeaderRemark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  部门领导审批
	 */
	public void setDeptLeaderRemark(java.lang.String deptLeaderRemark){
		this.deptLeaderRemark = deptLeaderRemark;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人
	 */

	@Column(name ="CREATE_NAME",nullable=true,length=50)
	public java.lang.String getCreateName(){
		return this.createName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人
	 */
	public void setCreateName(java.lang.String createName){
		this.createName = createName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人id
	 */

	@Column(name ="CREATE_BY",nullable=true,length=50)
	public java.lang.String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人id
	 */
	public void setCreateBy(java.lang.String createBy){
		this.createBy = createBy;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建时间
	 */

	@Column(name ="CREATE_DATE",nullable=true)
	public java.util.Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建时间
	 */
	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  修改人
	 */

	@Column(name ="UPDATE_NAME",nullable=true,length=50)
	public java.lang.String getUpdateName(){
		return this.updateName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  修改人
	 */
	public void setUpdateName(java.lang.String updateName){
		this.updateName = updateName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  修改人id
	 */

	@Column(name ="UPDATE_BY",nullable=true,length=50)
	public java.lang.String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  修改人id
	 */
	public void setUpdateBy(java.lang.String updateBy){
		this.updateBy = updateBy;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  修改时间
	 */

	@Column(name ="UPDATE_DATE",nullable=true)
	public java.util.Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  修改时间
	 */
	public void setUpdateDate(java.util.Date updateDate){
		this.updateDate = updateDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属部门
	 */

	@Column(name ="SYS_ORG_CODE",nullable=true,length=50)
	public java.lang.String getSysOrgCode(){
		return this.sysOrgCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属部门
	 */
	public void setSysOrgCode(java.lang.String sysOrgCode){
		this.sysOrgCode = sysOrgCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属公司
	 */

	@Column(name ="SYS_COMPANY_CODE",nullable=true,length=50)
	public java.lang.String getSysCompanyCode(){
		return this.sysCompanyCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属公司
	 */
	public void setSysCompanyCode(java.lang.String sysCompanyCode){
		this.sysCompanyCode = sysCompanyCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  流程状态
	 */

	@Column(name ="BPM_STATUS",nullable=true,length=2)
	public java.lang.String getBpmStatus(){
		return this.bpmStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  流程状态
	 */
	public void setBpmStatus(java.lang.String bpmStatus){
		this.bpmStatus = bpmStatus;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  逻辑删除标识0未删除1删除
	 */

	@Column(name ="DEL_FLAG",nullable=true,length=10)
	public java.lang.Integer getDelFlag(){
		return this.delFlag;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  逻辑删除标识0未删除1删除
	 */
	public void setDelFlag(java.lang.Integer delFlag){
		this.delFlag = delFlag;
	}
	
	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  调休申请（天）
	 */
	@Column(name ="APPLY_DAY",nullable=true,length=10)
	public java.lang.Integer getApplyDay() {
		return applyDay;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  调休申请（天）
	 */
	public void setApplyDay(java.lang.Integer applyDay) {
		this.applyDay = applyDay;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  调休申请（小时）
	 */
	
	@Column(name ="APPLY_HOUR",nullable=true,length=10)
	public java.lang.Integer getApplyHour() {
		return applyHour;
	}
	
	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  调休申请（小时）
	 */
	public void setApplyHour(java.lang.Integer applyHour) {
		this.applyHour = applyHour;
	}
	
}