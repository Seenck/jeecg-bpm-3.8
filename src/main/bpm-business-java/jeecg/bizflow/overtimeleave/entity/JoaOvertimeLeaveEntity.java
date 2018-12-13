package jeecg.bizflow.overtimeleave.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 调休申请单
 * @author onlineGenerator
 * @date 2018-07-31 18:01:14
 * @version V1.0   
 *
 */
@Entity
@Table(name = "joa_overtime_leave", schema = "")
@SuppressWarnings("serial")
public class JoaOvertimeLeaveEntity implements java.io.Serializable {
	/**ID*/
	private java.lang.String id;
	/**申请人*/
	@Excel(name="申请人",width=15)
	private java.lang.String applyUser;
	/**部门*/
	@Excel(name="部门",width=15)
	private java.lang.String department;
	/**申请时间*/
	@Excel(name="申请时间",width=15,format = "yyyy-MM-dd")
	private java.util.Date applyTime;
	/**调休时长*/
	@Excel(name="调休时长",width=15)
	private java.lang.String leaveTime;
	/**调休开始时间*/
	@Excel(name="调休开始时间",width=15,format = "yyyy-MM-dd")
	private java.util.Date beginTime;
	/**调休结束时间*/
	@Excel(name="调休结束时间",width=15,format = "yyyy-MM-dd")
	private java.util.Date endTime;
	/**调休期间联系方式*/
	@Excel(name="调休期间联系方式",width=15)
	private java.lang.String contactWay;
	/**工作代理人*/
	@Excel(name="工作代理人",width=15)
	private java.lang.String workAgent;
	/**工作安排*/
	@Excel(name="工作安排",width=15)
	private java.lang.String workPlan;
	/**直接领导审批*/
	@Excel(name="直接领导审批",width=15)
	private java.lang.String leaderRemark;
	/**部门领导审批*/
	@Excel(name="部门领导审批",width=15)
	private java.lang.String deptLeaderRemark;
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
	@Excel(name="流程状态",width=15,dicCode="bpm_status")
	private java.lang.String bpmStatus;
	/**逻辑删除标识0未删除1删除*/
	private java.lang.Integer delFlag;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  ID
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
	 *@param: java.lang.String  ID
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
	 *@return: java.lang.String  调休时长
	 */

	@Column(name ="LEAVE_TIME",nullable=true,length=30)
	public java.lang.String getLeaveTime(){
		return this.leaveTime;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  调休时长
	 */
	public void setLeaveTime(java.lang.String leaveTime){
		this.leaveTime = leaveTime;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  调休开始时间
	 */

	@Column(name ="BEGIN_TIME",nullable=true)
	public java.util.Date getBeginTime(){
		return this.beginTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  调休开始时间
	 */
	public void setBeginTime(java.util.Date beginTime){
		this.beginTime = beginTime;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  调休结束时间
	 */

	@Column(name ="END_TIME",nullable=true)
	public java.util.Date getEndTime(){
		return this.endTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  调休结束时间
	 */
	public void setEndTime(java.util.Date endTime){
		this.endTime = endTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  调休期间联系方式
	 */

	@Column(name ="CONTACT_WAY",nullable=true,length=255)
	public java.lang.String getContactWay(){
		return this.contactWay;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  调休期间联系方式
	 */
	public void setContactWay(java.lang.String contactWay){
		this.contactWay = contactWay;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  工作代理人
	 */

	@Column(name ="WORK_AGENT",nullable=true,length=50)
	public java.lang.String getWorkAgent(){
		return this.workAgent;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  工作代理人
	 */
	public void setWorkAgent(java.lang.String workAgent){
		this.workAgent = workAgent;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  工作安排
	 */

	@Column(name ="WORK_PLAN",nullable=true)
	public java.lang.String getWorkPlan(){
		return this.workPlan;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  工作安排
	 */
	public void setWorkPlan(java.lang.String workPlan){
		this.workPlan = workPlan;
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
}