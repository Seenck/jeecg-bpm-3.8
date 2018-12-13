package org.jeecgframework.workflow.mobile.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 业务申请管理
 * @author onlineGenerator
 * @date 2017-03-04 15:41:23
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_p_mobile_biz_form", schema = "")
@SuppressWarnings("serial")
public class TPMobileBizFormEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**表单名称*/
	@Excel(name="表单名称")
	private java.lang.String formName;
	/**表单地址*/
	@Excel(name="表单地址")
	private java.lang.String formUrl;
	/**表单排序*/
	@Excel(name="表单排序")
	private java.lang.Integer formSort;
	/**icon*/
	@Excel(name="icon")
	private java.lang.String formIcon;
	/**icon*/
	private java.lang.String appFormIcon;
	/**icon颜色*/
	@Excel(name="icon颜色")
	private java.lang.String formIconColor;
	/**是否推荐*/
	@Excel(name="是否推荐")
	private java.lang.String isRecommend;
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
	 *@return: java.lang.String  id
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
	 *@param: java.lang.String  id
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  表单名称
	 */
	@Column(name ="FORM_NAME",nullable=true,length=255)
	public java.lang.String getFormName(){
		return this.formName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  表单名称
	 */
	public void setFormName(java.lang.String formName){
		this.formName = formName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  表单地址
	 */
	@Column(name ="FORM_URL",nullable=true,length=500)
	public java.lang.String getFormUrl(){
		return this.formUrl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  表单地址
	 */
	public void setFormUrl(java.lang.String formUrl){
		this.formUrl = formUrl;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  表单排序
	 */
	@Column(name ="FORM_SORT",nullable=true,length=10)
	public java.lang.Integer getFormSort(){
		return this.formSort;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  表单排序
	 */
	public void setFormSort(java.lang.Integer formSort){
		this.formSort = formSort;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  icon
	 */
	@Column(name ="FORM_ICON",nullable=true,length=255)
	public java.lang.String getFormIcon(){
		return this.formIcon;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  icon
	 */
	public void setFormIcon(java.lang.String formIcon){
		this.formIcon = formIcon;
	}
	
	@Column(name ="APP_FORM_ICON",nullable=true,length=255)
	public java.lang.String getAppFormIcon() {
		return appFormIcon;
	}

	public void setAppFormIcon(java.lang.String appFormIcon) {
		this.appFormIcon = appFormIcon;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  icon颜色
	 */
	@Column(name ="FORM_ICON_COLOR",nullable=true,length=255)
	public java.lang.String getFormIconColor(){
		return this.formIconColor;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  icon颜色
	 */
	public void setFormIconColor(java.lang.String formIconColor){
		this.formIconColor = formIconColor;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否推荐
	 */
	@Column(name ="IS_RECOMMEND",nullable=true,length=2)
	public java.lang.String getIsRecommend(){
		return this.isRecommend;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否推荐
	 */
	public void setIsRecommend(java.lang.String isRecommend){
		this.isRecommend = isRecommend;
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
		builder.append("TPMobileBizFormEntity {\"id\":\"");
		builder.append(id);
		builder.append("\", \"formName\":\"");
		builder.append(formName);
		builder.append("\", \"formUrl\":\"");
		builder.append(formUrl);
		builder.append("\", \"formSort\":\"");
		builder.append(formSort);
		builder.append("\", \"formIcon\":\"");
		builder.append(formIcon);
		builder.append("\", \"formIconColor\":\"");
		builder.append(formIconColor);
		builder.append("\", \"isRecommend\":\"");
		builder.append(isRecommend);
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
