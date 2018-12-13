package com.oa.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.lang.String;
import java.lang.Double;
import java.lang.Integer;
import java.math.BigDecimal;
import javax.xml.soap.Text;
import java.sql.Blob;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.SequenceGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 厅室列表
 * @author onlineGenerator
 * @date 2018-12-12 17:36:27
 * @version V1.0   
 *
 */
@Entity
@Table(name = "oa_hall", schema = "")
@SuppressWarnings("serial")
public class OaHallEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**流程状态*/
	private java.lang.String bpmStatus;
	/**创建人名称*/
	private java.lang.String createName;
	/**创建人登录名称*/
	private java.lang.String createBy;
	/**更新人名称*/
	private java.lang.String updateName;
	/**更新人登录名称*/
	private java.lang.String updateBy;
	/**所属部门*/
	private java.lang.String sysOrgCode;
	/**创建日期*/
	private java.util.Date createDate;
	/**更新日期*/
	private java.util.Date updateDate;
	/**厅室编号*/
	@Excel(name="厅室编号",width=15)
	private java.lang.String hallCode;
	/**厅室名称*/
	@Excel(name="厅室名称",width=15)
	private java.lang.String hallName;
	/**预约人姓名*/
	@Excel(name="预约人姓名",width=15)
	private java.lang.String orderName;
	/**预约开始时间*/
	@Excel(name="预约开始时间",width=15,format = "yyyy-MM-dd HH:mm:ss")
	private java.util.Date orderStime;
	/**预约结束时间*/
	@Excel(name="预约结束时间",width=15,format = "yyyy-MM-dd HH:mm:ss")
	private java.util.Date orderEtime;
	/**厅室地址*/
	@Excel(name="厅室地址",width=15)
	private java.lang.String address;
	/**预约状态*/
	@Excel(name="预约状态",width=15)
	private java.lang.Integer status;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
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
	 *@param: java.lang.String  主键
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  流程状态
	 */

	@Column(name ="BPM_STATUS",nullable=true,length=32)
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
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建日期
	 */

	@Column(name ="CREATE_DATE",nullable=true,length=20)
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
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  更新日期
	 */

	@Column(name ="UPDATE_DATE",nullable=true,length=20)
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
	 *@return: java.lang.String  厅室编号
	 */

	@Column(name ="HALL_CODE",nullable=true,length=32)
	public java.lang.String getHallCode(){
		return this.hallCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  厅室编号
	 */
	public void setHallCode(java.lang.String hallCode){
		this.hallCode = hallCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  厅室名称
	 */

	@Column(name ="HALL_NAME",nullable=true,length=50)
	public java.lang.String getHallName(){
		return this.hallName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  厅室名称
	 */
	public void setHallName(java.lang.String hallName){
		this.hallName = hallName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  预约人姓名
	 */

	@Column(name ="ORDER_NAME",nullable=true,length=32)
	public java.lang.String getOrderName(){
		return this.orderName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  预约人姓名
	 */
	public void setOrderName(java.lang.String orderName){
		this.orderName = orderName;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  预约开始时间
	 */

	@Column(name ="ORDER_STIME",nullable=true,length=32)
	public java.util.Date getOrderStime(){
		return this.orderStime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  预约开始时间
	 */
	public void setOrderStime(java.util.Date orderStime){
		this.orderStime = orderStime;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  预约结束时间
	 */

	@Column(name ="ORDER_ETIME",nullable=true,length=32)
	public java.util.Date getOrderEtime(){
		return this.orderEtime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  预约结束时间
	 */
	public void setOrderEtime(java.util.Date orderEtime){
		this.orderEtime = orderEtime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  厅室地址
	 */

	@Column(name ="ADDRESS",nullable=true,length=100)
	public java.lang.String getAddress(){
		return this.address;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  厅室地址
	 */
	public void setAddress(java.lang.String address){
		this.address = address;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  预约状态
	 */

	@Column(name ="STATUS",nullable=true,length=1)
	public java.lang.Integer getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  预约状态
	 */
	public void setStatus(java.lang.Integer status){
		this.status = status;
	}
}