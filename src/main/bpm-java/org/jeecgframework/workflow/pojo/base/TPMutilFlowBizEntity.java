package org.jeecgframework.workflow.pojo.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: 多流程表单业务数据表
 * @Description: t_p_mutil_flow_biz
 * @author onlineGenerator
 * @date 2018-07-07 13:16:40
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_p_mutil_flow_biz")
@PrimaryKeyJoinColumn(name = "id")
@SuppressWarnings("serial")
public class TPMutilFlowBizEntity extends TSBaseBus implements java.io.Serializable {
	/**多流程表单编码*/
	@Excel(name="多流程表单编码",width=15)
	private java.lang.String mutilFlowCode;
	/**多流程表单表名*/
	@Excel(name="多流程表单表名",width=15)
	private java.lang.String mutilFlowForm;
	/**多流程状态列名*/
	@Excel(name="多流程状态列名",width=15)
	private java.lang.String mutilFlowStatusCol;
	/**多流程表单id*/
	@Excel(name="多流程表单id",width=15)
	private java.lang.String mutilFlowFormId;
	/**流程实例id*/
	private java.lang.String procInstId;
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
	 *@return: java.lang.String  多流程表单编码
	 */

	@Column(name ="MUTIL_FLOW_CODE",nullable=true,length=32)
	public java.lang.String getMutilFlowCode(){
		return this.mutilFlowCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  多流程表单编码
	 */
	public void setMutilFlowCode(java.lang.String mutilFlowCode){
		this.mutilFlowCode = mutilFlowCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  多流程表单表名
	 */

	@Column(name ="MUTIL_FLOW_FORM",nullable=true,length=50)
	public java.lang.String getMutilFlowForm(){
		return this.mutilFlowForm;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  多流程表单表名
	 */
	public void setMutilFlowForm(java.lang.String mutilFlowForm){
		this.mutilFlowForm = mutilFlowForm;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  多流程状态列名
	 */

	@Column(name ="MUTIL_FLOW_STATUS_COL",nullable=true,length=50)
	public java.lang.String getMutilFlowStatusCol(){
		return this.mutilFlowStatusCol;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  多流程状态列名
	 */
	public void setMutilFlowStatusCol(java.lang.String mutilFlowStatusCol){
		this.mutilFlowStatusCol = mutilFlowStatusCol;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  多流程表单id
	 */

	@Column(name ="MUTIL_FLOW_FORM_ID",nullable=true,length=32)
	public java.lang.String getMutilFlowFormId(){
		return this.mutilFlowFormId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  多流程表单id
	 */
	public void setMutilFlowFormId(java.lang.String mutilFlowFormId){
		this.mutilFlowFormId = mutilFlowFormId;
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

	@Column(name ="PROC_INST_ID")
	public java.lang.String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(java.lang.String procInstId) {
		this.procInstId = procInstId;
	}
	
	
}
