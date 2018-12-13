package jeecg.workflow.entity.demo;

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
 * @Description: hr_qianbao
 * @author onlineGenerator
 * @date 2016-11-22 16:00:46
 * @version V1.0   
 *
 */
@Entity
@Table(name = "hr_qianbao", schema = "")
@SuppressWarnings("serial")
public class HrQianbaoEntity implements java.io.Serializable {
	/**ID*/
	private java.lang.String id;
	/**公文类别*/
	@Excel(name="公文类别")
	private java.lang.String documentCategory;
	/**流程状态*/
	@Excel(name="流程状态")
	private java.lang.String bpmStatus;
	/**签报编码年份*/
	@Excel(name="签报编码年份")
	private java.lang.String qianbaoCodeYear;
	/**签报编码号*/
	@Excel(name="签报编码号")
	private java.lang.String qianbaoCodeNum;
	/**机要年份*/
	@Excel(name="机要年份")
	private java.lang.String confidentialYear;
	/**机要号*/
	@Excel(name="机要号")
	private java.lang.String confidentialNum;
	/**签报单位编码*/
	@Excel(name="签报单位编码")
	private java.lang.String qianbaoDepartCode;
	/**签报单位名称*/
	@Excel(name="签报单位名称")
	private java.lang.String qianbaoDepartName;
	/**签报日期*/
	@Excel(name="签报日期",format = "yyyy-MM-dd")
	private java.util.Date qianbaoDate;
	/**是否需要会签（0不需要会签1需要会签）*/
	@Excel(name="是否需要会签（0不需要会签1需要会签）")
	private java.lang.String qianbaoFlag;
	/**是否为制度文件（0否1是）*/
	@Excel(name="是否为制度文件（0否1是）")
	private java.lang.String policyFlag;
	/**创建人名称*/
	@Excel(name="创建人名称")
	private java.lang.String createName;
	/**创建人登录名称*/
	@Excel(name="创建人登录名称")
	private java.lang.String createBy;
	/**创建日期*/
	@Excel(name="创建日期",format = "yyyy-MM-dd")
	private java.util.Date createDate;
	/**更新人名称*/
	@Excel(name="更新人名称")
	private java.lang.String updateName;
	/**更新人登录名称*/
	@Excel(name="更新人登录名称")
	private java.lang.String updateBy;
	/**更新日期*/
	@Excel(name="更新日期",format = "yyyy-MM-dd")
	private java.util.Date updateDate;
	/**所属部门*/
	@Excel(name="所属部门")
	private java.lang.String sysOrgCode;
	/**所属公司*/
	@Excel(name="所属公司")
	private java.lang.String sysCompanyCode;
	
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
	 *@return: java.lang.String  公文类别
	 */
	@Column(name ="DOCUMENT_CATEGORY",nullable=true,length=32)
	public java.lang.String getDocumentCategory(){
		return this.documentCategory;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  公文类别
	 */
	public void setDocumentCategory(java.lang.String documentCategory){
		this.documentCategory = documentCategory;
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
	 *@return: java.lang.String  机要年份
	 */
	@Column(name ="CONFIDENTIAL_YEAR",nullable=true,length=32)
	public java.lang.String getConfidentialYear(){
		return this.confidentialYear;
	}

	@Column(name ="QIANBAO_CODE_YEAR",nullable=true,length=32)
	public java.lang.String getQianbaoCodeYear() {
		return qianbaoCodeYear;
	}

	public void setQianbaoCodeYear(java.lang.String qianbaoCodeYear) {
		this.qianbaoCodeYear = qianbaoCodeYear;
	}

	@Column(name ="QIANBAO_CODE_NUM",nullable=true,length=32)
	public java.lang.String getQianbaoCodeNum() {
		return qianbaoCodeNum;
	}

	public void setQianbaoCodeNum(java.lang.String qianbaoCodeNum) {
		this.qianbaoCodeNum = qianbaoCodeNum;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  机要年份
	 */
	public void setConfidentialYear(java.lang.String confidentialYear){
		this.confidentialYear = confidentialYear;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  机要号
	 */
	@Column(name ="CONFIDENTIAL_NUM",nullable=true,length=32)
	public java.lang.String getConfidentialNum(){
		return this.confidentialNum;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  机要号
	 */
	public void setConfidentialNum(java.lang.String confidentialNum){
		this.confidentialNum = confidentialNum;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  签报单位编码
	 */
	@Column(name ="QIANBAO_DEPART_CODE",nullable=true,length=32)
	public java.lang.String getQianbaoDepartCode(){
		return this.qianbaoDepartCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  签报单位编码
	 */
	public void setQianbaoDepartCode(java.lang.String qianbaoDepartCode){
		this.qianbaoDepartCode = qianbaoDepartCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  签报单位名称
	 */
	@Column(name ="QIANBAO_DEPART_NAME",nullable=true,length=255)
	public java.lang.String getQianbaoDepartName(){
		return this.qianbaoDepartName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  签报单位名称
	 */
	public void setQianbaoDepartName(java.lang.String qianbaoDepartName){
		this.qianbaoDepartName = qianbaoDepartName;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  签报日期
	 */
	@Column(name ="QIANBAO_DATE",nullable=true)
	public java.util.Date getQianbaoDate(){
		return this.qianbaoDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  签报日期
	 */
	public void setQianbaoDate(java.util.Date qianbaoDate){
		this.qianbaoDate = qianbaoDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否需要会签（0不需要会签1需要会签）
	 */
	@Column(name ="QIANBAO_FLAG",nullable=true,length=32)
	public java.lang.String getQianbaoFlag(){
		return this.qianbaoFlag;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否需要会签（0不需要会签1需要会签）
	 */
	public void setQianbaoFlag(java.lang.String qianbaoFlag){
		this.qianbaoFlag = qianbaoFlag;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否为制度文件（0否1是）
	 */
	@Column(name ="POLICY_FLAG",nullable=true,length=32)
	public java.lang.String getPolicyFlag(){
		return this.policyFlag;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否为制度文件（0否1是）
	 */
	public void setPolicyFlag(java.lang.String policyFlag){
		this.policyFlag = policyFlag;
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
}
