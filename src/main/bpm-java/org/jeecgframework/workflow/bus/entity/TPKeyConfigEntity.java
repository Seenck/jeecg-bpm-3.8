package org.jeecgframework.workflow.bus.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 第三方密钥管理
 * @author zhoujf
 * @date 2017-03-30 10:32:20
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_p_key_config", schema = "")
@SuppressWarnings("serial")
public class TPKeyConfigEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**系统标识*/
	@Excel(name="系统标识")
	private java.lang.String sysCode;
	/**加解密密钥*/
	@Excel(name="加解密密钥")
	private java.lang.String encrypKey;
	/**加签验签密钥*/
	@Excel(name="加签验签密钥")
	private java.lang.String signKey;
	/**是否启用*/
	@Excel(name="是否启用")
	private java.lang.Integer isUsed;
	/**创建人ID*/
	private java.lang.String createBy;
	/**创建人名称*/
	private java.lang.String createName;
	/**创建日期*/
	private java.util.Date createDate;
	/**修改人ID*/
	private java.lang.String updateBy;
	/**修改人名称*/
	private java.lang.String updateName;
	/**修改日期*/
	private java.util.Date updateDate;
	/**所属部门*/
	private java.lang.String sysOrgCode;
	/**所属公司*/
	private java.lang.String sysCompanyCode;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=36)
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
	 *@return: java.lang.String  系统标识
	 */
	@Column(name ="SYS_CODE",nullable=false,length=32)
	public java.lang.String getSysCode(){
		return this.sysCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  系统标识
	 */
	public void setSysCode(java.lang.String sysCode){
		this.sysCode = sysCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  加解密密钥
	 */
	@Column(name ="ENCRYP_KEY",nullable=true,length=64)
	public java.lang.String getEncrypKey(){
		return this.encrypKey;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  加解密密钥
	 */
	public void setEncrypKey(java.lang.String encrypKey){
		this.encrypKey = encrypKey;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  加签验签密钥
	 */
	@Column(name ="SIGN_KEY",nullable=false,length=64)
	public java.lang.String getSignKey(){
		return this.signKey;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  加签验签密钥
	 */
	public void setSignKey(java.lang.String signKey){
		this.signKey = signKey;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  是否启用
	 */
	@Column(name ="IS_USED",nullable=false,length=10)
	public java.lang.Integer getIsUsed(){
		return this.isUsed;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  是否启用
	 */
	public void setIsUsed(java.lang.Integer isUsed){
		this.isUsed = isUsed;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人ID
	 */
	@Column(name ="CREATE_BY",nullable=true,length=50)
	public java.lang.String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人ID
	 */
	public void setCreateBy(java.lang.String createBy){
		this.createBy = createBy;
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
	 *@return: java.lang.String  修改人ID
	 */
	@Column(name ="UPDATE_BY",nullable=true,length=50)
	public java.lang.String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  修改人ID
	 */
	public void setUpdateBy(java.lang.String updateBy){
		this.updateBy = updateBy;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  修改人名称
	 */
	@Column(name ="UPDATE_NAME",nullable=true,length=50)
	public java.lang.String getUpdateName(){
		return this.updateName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  修改人名称
	 */
	public void setUpdateName(java.lang.String updateName){
		this.updateName = updateName;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  修改日期
	 */
	@Column(name ="UPDATE_DATE",nullable=true)
	public java.util.Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  修改日期
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
}
