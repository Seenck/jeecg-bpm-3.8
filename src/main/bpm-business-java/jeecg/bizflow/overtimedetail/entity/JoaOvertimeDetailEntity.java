package jeecg.bizflow.overtimedetail.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 调休明细表
 * @author onlineGenerator
 * @date 2018-08-09 15:22:14
 * @version V1.0   
 *
 */
@Entity
@Table(name = "joa_overtime_detail", schema = "")
@SuppressWarnings("serial")
public class JoaOvertimeDetailEntity implements java.io.Serializable {
	/**ID*/
	private java.lang.String id;
	/**调休申请ID*/
	@Excel(name="调休申请ID",width=15)
	private java.lang.String overtimeLeaveId;
	/**加班ID*/
	@Excel(name="加班ID",width=15)
	private java.lang.String overtimeId;
	/**调休申请天*/
	@Excel(name="调休申请天",width=15)
	private java.lang.Integer applyDay;
	/**调休申请小时*/
	@Excel(name="调休申请小时",width=15)
	private java.lang.Integer applyHour;
	/**创建人*/
	@Excel(name="创建人",width=15)
	private java.lang.String createName;
	/**创建人id*/
	@Excel(name="创建人id",width=15)
	private java.lang.String createBy;
	/**创建时间*/
	@Excel(name="创建时间",width=15,format = "yyyy-MM-dd")
	private java.util.Date createDate;
	/**修改人*/
	@Excel(name="修改人",width=15)
	private java.lang.String updateName;
	/**修改人id*/
	@Excel(name="修改人id",width=15)
	private java.lang.String updateBy;
	/**修改时间*/
	@Excel(name="修改时间",width=15,format = "yyyy-MM-dd")
	private java.util.Date updateDate;
	/**所属部门*/
	@Excel(name="所属部门",width=15)
	private java.lang.String sysOrgCode;
	/**所属公司*/
	@Excel(name="所属公司",width=15)
	private java.lang.String sysCompanyCode;
	/**流程状态*/
	@Excel(name="流程状态",width=15)
	private java.lang.String bpmStatus;
	/**逻辑删除标识*/
	@Excel(name="逻辑删除标识",width=15)
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
	 *@return: java.lang.String  调休申请ID
	 */

	@Column(name ="OVERTIME_LEAVE_ID",nullable=true,length=32)
	public java.lang.String getOvertimeLeaveId(){
		return this.overtimeLeaveId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  调休申请ID
	 */
	public void setOvertimeLeaveId(java.lang.String overtimeLeaveId){
		this.overtimeLeaveId = overtimeLeaveId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  加班ID
	 */

	@Column(name ="OVERTIME_ID",nullable=true,length=32)
	public java.lang.String getOvertimeId(){
		return this.overtimeId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  加班ID
	 */
	public void setOvertimeId(java.lang.String overtimeId){
		this.overtimeId = overtimeId;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  调休申请天
	 */

	@Column(name ="APPLY_DAY",nullable=true,length=10)
	public java.lang.Integer getApplyDay(){
		return this.applyDay;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  调休申请天
	 */
	public void setApplyDay(java.lang.Integer applyDay){
		this.applyDay = applyDay;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  调休申请小时
	 */

	@Column(name ="APPLY_HOUR",nullable=true,length=10)
	public java.lang.Integer getApplyHour(){
		return this.applyHour;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  调休申请小时
	 */
	public void setApplyHour(java.lang.Integer applyHour){
		this.applyHour = applyHour;
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
	 *@return: java.lang.Integer  逻辑删除标识
	 */

	@Column(name ="DEL_FLAG",nullable=true,length=10)
	public java.lang.Integer getDelFlag(){
		return this.delFlag;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  逻辑删除标识
	 */
	public void setDelFlag(java.lang.Integer delFlag){
		this.delFlag = delFlag;
	}
}