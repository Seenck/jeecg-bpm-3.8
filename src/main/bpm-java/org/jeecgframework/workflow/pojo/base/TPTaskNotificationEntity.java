package org.jeecgframework.workflow.pojo.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: 流程任务催办处理表
 * @Description: t_p_task_notification
 * @author onlineGenerator
 * @date 2018-07-04 14:48:37
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_p_task_notification", schema = "")
@SuppressWarnings("serial")
public class TPTaskNotificationEntity implements java.io.Serializable {
	/**序号*/
	private java.lang.String id;
	/**任务ID*/
	@Excel(name="任务ID",width=15)
	private java.lang.String taskId;
	/**任务名称*/
	@Excel(name="任务名称",width=15)
	private java.lang.String taskName;
	/**任务处理人*/
	@Excel(name="任务处理人",width=15)
	private java.lang.String taskAssignee;
	/**催办操作时间*/
	@Excel(name="催办操作时间",width=15)
	private java.util.Date opTime;
	//-update-begin-----------author:zhoujf date:20180713 for:TASK #2939 【改进】催办任务
	/**催办说明*/
	private java.lang.String remarks;
	//-update-end-----------author:zhoujf date:20180713 for:TASK #2939 【改进】催办任务
	/**创建人名称*/
	@Excel(name="创建人名称",width=15)
	private java.lang.String createName;
	/**创建人登录名称*/
	@Excel(name="创建人登录名称",width=15)
	private java.lang.String createBy;
	/**创建日期*/
	@Excel(name="创建日期",width=15,format = "yyyy-MM-dd")
	private java.util.Date createDate;
	/**更新人名称*/
	@Excel(name="更新人名称",width=15)
	private java.lang.String updateName;
	/**更新人登录名称*/
	@Excel(name="更新人登录名称",width=15)
	private java.lang.String updateBy;
	/**更新日期*/
	@Excel(name="更新日期",width=15,format = "yyyy-MM-dd")
	private java.util.Date updateDate;
	/**所属部门*/
	@Excel(name="所属部门",width=15)
	private java.lang.String sysOrgCode;
	/**所属公司*/
	@Excel(name="所属公司",width=15)
	private java.lang.String sysCompanyCode;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  序号
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
	 *@param: java.lang.String  序号
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  任务ID
	 */

	@Column(name ="TASK_ID",nullable=true,length=64)
	public java.lang.String getTaskId(){
		return this.taskId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  任务ID
	 */
	public void setTaskId(java.lang.String taskId){
		this.taskId = taskId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  任务名称
	 */

	@Column(name ="TASK_NAME",nullable=true,length=255)
	public java.lang.String getTaskName(){
		return this.taskName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  任务名称
	 */
	public void setTaskName(java.lang.String taskName){
		this.taskName = taskName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  任务处理人
	 */

	@Column(name ="TASK_ASSIGNEE",nullable=true,length=100)
	public java.lang.String getTaskAssignee(){
		return this.taskAssignee;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  任务处理人
	 */
	public void setTaskAssignee(java.lang.String taskAssignee){
		this.taskAssignee = taskAssignee;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  催办操作时间
	 */

	@Column(name ="OP_TIME",nullable=true)
	public java.util.Date getOpTime(){
		return this.opTime;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  催办操作时间
	 */
	public void setOpTime(java.util.Date opTime){
		this.opTime = opTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人名称
	 */

	@Column(name ="CREATE_NAME",nullable=true,length=50)
	public java.lang.String getCreateName(){
		return this.createName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人名称
	 */
	public void setCreateName(java.lang.String createName){
		this.createName = createName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人登录名称
	 */

	@Column(name ="CREATE_BY",nullable=true,length=50)
	public java.lang.String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人登录名称
	 */
	public void setCreateBy(java.lang.String createBy){
		this.createBy = createBy;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建日期
	 */

	@Column(name ="CREATE_DATE",nullable=true)
	public java.util.Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建日期
	 */
	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人名称
	 */

	@Column(name ="UPDATE_NAME",nullable=true,length=50)
	public java.lang.String getUpdateName(){
		return this.updateName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人名称
	 */
	public void setUpdateName(java.lang.String updateName){
		this.updateName = updateName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人登录名称
	 */

	@Column(name ="UPDATE_BY",nullable=true,length=50)
	public java.lang.String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人登录名称
	 */
	public void setUpdateBy(java.lang.String updateBy){
		this.updateBy = updateBy;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  更新日期
	 */

	@Column(name ="UPDATE_DATE",nullable=true)
	public java.util.Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  更新日期
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

	@Column(name ="REMARKS")
	public java.lang.String getRemarks() {
		return remarks;
	}

	public void setRemarks(java.lang.String remarks) {
		this.remarks = remarks;
	}

	@Override
	public String toString() {
		return "TPTaskNotificationEntity [id=" + id + ", taskId=" + taskId
				+ ", taskName=" + taskName + ", taskAssignee=" + taskAssignee
				+ ", opTime=" + opTime + ", createName=" + createName
				+ ", createBy=" + createBy + ", createDate=" + createDate
				+ ", updateName=" + updateName + ", updateBy=" + updateBy
				+ ", updateDate=" + updateDate + ", sysOrgCode=" + sysOrgCode
				+ ", sysCompanyCode=" + sysCompanyCode + "]";
	}
	
}
