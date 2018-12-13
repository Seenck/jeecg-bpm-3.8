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
 * @Description: 用户代理人设置
 * @author onlineGenerator
 * @date 2017-03-06 11:21:34
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_s_user_agent", schema = "")
@SuppressWarnings("serial")
public class TSUserAgentEntity implements java.io.Serializable {
	/**序号*/
	private java.lang.String id;
	/**用户名*/
	@Excel(name="用户名")
	private java.lang.String userName;
	/**代理人用户名*/
	@Excel(name="代理人用户名")
	private java.lang.String agentUserName;
	/**代理开始时间*/
	@Excel(name="代理开始时间",format = "yyyy-MM-dd")
	private java.util.Date startTime;
	/**代理结束时间*/
	@Excel(name="代理结束时间",format = "yyyy-MM-dd")
	private java.util.Date endTime;
	/**
	 * 状态 0 无效 1有效
	 */
	private String status;
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
	@Column(name ="ID",nullable=true,length=32)
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
	 *@return: java.lang.String  用户名
	 */
	@Column(name ="USER_NAME",nullable=true,length=100)
	public java.lang.String getUserName(){
		return this.userName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户名
	 */
	public void setUserName(java.lang.String userName){
		this.userName = userName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  代理人用户名
	 */
	@Column(name ="AGENT_USER_NAME",nullable=true,length=100)
	public java.lang.String getAgentUserName(){
		return this.agentUserName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  代理人用户名
	 */
	public void setAgentUserName(java.lang.String agentUserName){
		this.agentUserName = agentUserName;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  代理开始时间
	 */
	@Column(name ="START_TIME",nullable=true)
	public java.util.Date getStartTime(){
		return this.startTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  代理开始时间
	 */
	public void setStartTime(java.util.Date startTime){
		this.startTime = startTime;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  代理结束时间
	 */
	@Column(name ="END_TIME",nullable=true)
	public java.util.Date getEndTime(){
		return this.endTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  代理结束时间
	 */
	public void setEndTime(java.util.Date endTime){
		this.endTime = endTime;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TSUserAgentEntity {\"id\":\"");
		builder.append(id);
		builder.append("\", \"userName\":\"");
		builder.append(userName);
		builder.append("\", \"agentUserName\":\"");
		builder.append(agentUserName);
		builder.append("\", \"startTime\":\"");
		builder.append(startTime);
		builder.append("\", \"endTime\":\"");
		builder.append(endTime);
		builder.append("\", \"status\":\"");
		builder.append(status);
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
