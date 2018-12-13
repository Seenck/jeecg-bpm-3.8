package org.jeecgframework.workflow.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 任务抄送表
 * @author onlineGenerator
 * @date 2017-03-06 16:03:41
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_p_task_cc", schema = "")
@SuppressWarnings("serial")
public class TPTaskCcEntity implements java.io.Serializable {
	/**序号*/
	private java.lang.String id;
	/**流程定义ID*/
	@Excel(name="流程定义ID")
	private java.lang.String procDefId;
	/**节点定义ID*/
	@Excel(name="节点定义ID")
	private java.lang.String taskDefKey;
	/**流程实例ID*/
	@Excel(name="流程实例ID")
	private java.lang.String procInstId;
	/**执行实例ID*/
	@Excel(name="执行实例ID")
	private java.lang.String executionId;
	/**任务ID*/
	@Excel(name="任务ID")
	private java.lang.String taskId;
	/**名称*/
	@Excel(name="名称")
	private java.lang.String taskName;
	/**任务处理人*/
	@Excel(name="任务处理人")
	private java.lang.String fromUserName;
	/**任务抄送人员*/
	@Excel(name="任务抄送人员")
	private java.lang.String ccUserName;
	/**创建人名称*/
	private java.lang.String createName;
	/**创建人登录名称*/
	private java.lang.String createBy;
	/**创建日期*/
	private java.util.Date createDate;
	/**更新人名称*/
	private java.lang.String updateName;
	/**更新人登录名称*/
	private java.lang.String updateBy;
	/**更新日期*/
	private java.util.Date updateDate;
	/**所属部门*/
	private java.lang.String sysOrgCode;
	/**所属公司*/
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
	 *@return: java.lang.String  流程定义ID
	 */
	@Column(name ="PROC_DEF_ID",nullable=true,length=64)
	public java.lang.String getProcDefId(){
		return this.procDefId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  流程定义ID
	 */
	public void setProcDefId(java.lang.String procDefId){
		this.procDefId = procDefId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  节点定义ID
	 */
	@Column(name ="TASK_DEF_KEY",nullable=true,length=255)
	public java.lang.String getTaskDefKey(){
		return this.taskDefKey;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  节点定义ID
	 */
	public void setTaskDefKey(java.lang.String taskDefKey){
		this.taskDefKey = taskDefKey;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  流程实例ID
	 */
	@Column(name ="PROC_INST_ID",nullable=true,length=64)
	public java.lang.String getProcInstId(){
		return this.procInstId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  流程实例ID
	 */
	public void setProcInstId(java.lang.String procInstId){
		this.procInstId = procInstId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  执行实例ID
	 */
	@Column(name ="EXECUTION_ID",nullable=true,length=64)
	public java.lang.String getExecutionId(){
		return this.executionId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  执行实例ID
	 */
	public void setExecutionId(java.lang.String executionId){
		this.executionId = executionId;
	}
	
	@Column(name ="TASK_ID",nullable=true,length=255)
	public java.lang.String getTaskId() {
		return taskId;
	}

	public void setTaskId(java.lang.String taskId) {
		this.taskId = taskId;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  名称
	 */
	@Column(name ="TASK_NAME",nullable=true,length=255)
	public java.lang.String getTaskName(){
		return this.taskName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  名称
	 */
	public void setTaskName(java.lang.String taskName){
		this.taskName = taskName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  任务处理人
	 */
	@Column(name ="FROM_USER_NAME",nullable=true,length=100)
	public java.lang.String getFromUserName(){
		return this.fromUserName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  任务处理人
	 */
	public void setFromUserName(java.lang.String fromUserName){
		this.fromUserName = fromUserName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  任务抄送人员
	 */
	@Column(name ="CC_USER_NAME",nullable=true,length=100)
	public java.lang.String getCcUserName(){
		return this.ccUserName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  任务抄送人员
	 */
	public void setCcUserName(java.lang.String ccUserName){
		this.ccUserName = ccUserName;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TPTaskCcEntity {\"id\":\"");
		builder.append(id);
		builder.append("\", \"procDefId\":\"");
		builder.append(procDefId);
		builder.append("\", \"taskDefKey\":\"");
		builder.append(taskDefKey);
		builder.append("\", \"procInstId\":\"");
		builder.append(procInstId);
		builder.append("\", \"executionId\":\"");
		builder.append(executionId);
		builder.append("\", \"taskId\":\"");
		builder.append(taskId);
		builder.append("\", \"taskName\":\"");
		builder.append(taskName);
		builder.append("\", \"fromUserName\":\"");
		builder.append(fromUserName);
		builder.append("\", \"ccUserName\":\"");
		builder.append(ccUserName);
		builder.append("\", \"createName\":\"");
		builder.append(createName);
		builder.append("\", \"createBy\":\"");
		builder.append(createBy);
		builder.append("\", \"createDate\":\"");
		builder.append(createDate);
		builder.append("\", \"updateName\":\"");
		builder.append(updateName);
		builder.append("\", \"updateBy\":\"");
		builder.append(updateBy);
		builder.append("\", \"updateDate\":\"");
		builder.append(updateDate);
		builder.append("\", \"sysOrgCode\":\"");
		builder.append(sysOrgCode);
		builder.append("\", \"sysCompanyCode\":\"");
		builder.append(sysCompanyCode);
		builder.append("\"}");
		return builder.toString();
	}
	
}
