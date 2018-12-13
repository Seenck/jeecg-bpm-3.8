package jeecg.bizflow.officedoc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 公文发文
 * @author onlineGenerator
 * @date 2018-07-30 14:58:23
 * @version V1.0   
 *
 */
@Entity
@Table(name = "joa_doc_sending", schema = "")
@SuppressWarnings("serial")
public class JoaDocSendingEntity implements java.io.Serializable {
	/**主键*/

	private java.lang.String id;
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
	/**流程状态*/
    @Excel(name="流程状态",width=15)
		
	private java.lang.String bpmStatus;
	
    
    /**发文字号*/
    @Excel(name="发文字号",width=15)
		
	private java.lang.String docCode;
	/**登记人*/
    @Excel(name="登记人",width=15)
		
	private java.lang.String booker;
	/**登记时间*/
    @Excel(name="登记时间",width=15,format = "yyyy-MM-dd")
		
	private java.util.Date bookDate;
	/**公文标题*/
    @Excel(name="公文标题",width=15)
		
	private java.lang.String title;
	/**发文目标*/
    @Excel(name="发文目标",width=15,dicCode="of_fwmb")
		
	private java.lang.String sendTarget;
	/**主送机关*/
    @Excel(name="主送机关",width=15)
		
	private java.lang.String sendDepart;
	/**公文分类*/
    @Excel(name="公文分类",width=15,dicCode="of_gwfl")
		
	private java.lang.String docType;
	/**文种*/
    @Excel(name="文种",width=15,dicCode="of_wenz")
		
	private java.lang.String classification;
	/**机密度*/
    @Excel(name="机密度",width=15,dicCode="of_jimi")
		
	private java.lang.String confidentiality;
	/**缓急程度*/
    @Excel(name="缓急程度",width=15,dicCode="of_hjcd")
	private java.lang.String urgency;
	/**成文日期*/
    @Excel(name="成文日期",width=15,format = "yyyy-MM-dd")
		
	private java.util.Date writtenDate;
	/**审阅时间*/
    @Excel(name="审阅时间",width=15,format = "yyyy-MM-dd")
		
	private java.util.Date reviewDate;
	/**主题词*/
    @Excel(name="主题词",width=15)
	private java.lang.String theme;
	/**相关文件*/
    @Excel(name="相关文件",width=15)
	private java.lang.String extFile;
    
    @Excel(name="印刷分数",width=15)
 	private java.lang.String printScore;
    @Excel(name="收文人",width=15)
   	private java.lang.String receiver;
    
    @Excel(name="收文人名称",width=15)
   	private java.lang.String receiverName;
	
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
	 *@return: java.lang.String  发文字号
	 */
	@Column(name ="DOC_CODE",nullable=true,length=100)
	public java.lang.String getDocCode(){
		return this.docCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  发文字号
	 */
	public void setDocCode(java.lang.String docCode){
		this.docCode = docCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  登记人
	 */
	@Column(name ="BOOKER",nullable=true,length=32)
	public java.lang.String getBooker(){
		return this.booker;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  登记人
	 */
	public void setBooker(java.lang.String booker){
		this.booker = booker;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  登记时间
	 */
	@Column(name ="BOOK_DATE",nullable=true)
	public java.util.Date getBookDate(){
		return this.bookDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  登记时间
	 */
	public void setBookDate(java.util.Date bookDate){
		this.bookDate = bookDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  公文标题
	 */
	@Column(name ="TITLE",nullable=true,length=100)
	public java.lang.String getTitle(){
		return this.title;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  公文标题
	 */
	public void setTitle(java.lang.String title){
		this.title = title;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  发文目标
	 */
	@Column(name ="SEND_TARGET",nullable=true,length=3)
	public java.lang.String getSendTarget(){
		return this.sendTarget;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  发文目标
	 */
	public void setSendTarget(java.lang.String sendTarget){
		this.sendTarget = sendTarget;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主送机关
	 */
	@Column(name ="SEND_DEPART",nullable=true,length=500)
	public java.lang.String getSendDepart(){
		return this.sendDepart;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主送机关
	 */
	public void setSendDepart(java.lang.String sendDepart){
		this.sendDepart = sendDepart;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  公文分类
	 */
	@Column(name ="DOC_TYPE",nullable=true,length=3)
	public java.lang.String getDocType(){
		return this.docType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  公文分类
	 */
	public void setDocType(java.lang.String docType){
		this.docType = docType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  文种
	 */
	@Column(name ="CLASSIFICATION",nullable=true,length=3)
	public java.lang.String getClassification(){
		return this.classification;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  文种
	 */
	public void setClassification(java.lang.String classification){
		this.classification = classification;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  机密度
	 */
	@Column(name ="CONFIDENTIALITY",nullable=true,length=3)
	public java.lang.String getConfidentiality(){
		return this.confidentiality;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  机密度
	 */
	public void setConfidentiality(java.lang.String confidentiality){
		this.confidentiality = confidentiality;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  缓急程度
	 */
	@Column(name ="URGENCY",nullable=true,length=3)
	public java.lang.String getUrgency(){
		return this.urgency;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  缓急程度
	 */
	public void setUrgency(java.lang.String urgency){
		this.urgency = urgency;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  成文日期
	 */
	@Column(name ="WRITTEN_DATE",nullable=true)
	public java.util.Date getWrittenDate(){
		return this.writtenDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  成文日期
	 */
	public void setWrittenDate(java.util.Date writtenDate){
		this.writtenDate = writtenDate;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  审阅时间
	 */
	@Column(name ="REVIEW_DATE",nullable=true)
	public java.util.Date getReviewDate(){
		return this.reviewDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  审阅时间
	 */
	public void setReviewDate(java.util.Date reviewDate){
		this.reviewDate = reviewDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主题词
	 */
	@Column(name ="THEME",nullable=true,length=255)
	public java.lang.String getTheme(){
		return this.theme;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主题词
	 */
	public void setTheme(java.lang.String theme){
		this.theme = theme;
	}
	@Column(name ="EXT_FILE",nullable=true,length=500)
	public java.lang.String getExtFile(){
		return this.extFile;
	}

	public void setExtFile(java.lang.String extFile){
		this.extFile = extFile;
	}

	@Column(name ="print_score",nullable=true,length=10)
	public java.lang.String getPrintScore() {
		return printScore;
	}

	public void setPrintScore(java.lang.String printScore) {
		this.printScore = printScore;
	}

	@Column(name ="receiver",nullable=true,length=500)
	public java.lang.String getReceiver() {
		return receiver;
	}

	public void setReceiver(java.lang.String receiver) {
		this.receiver = receiver;
	}

	@Column(name ="receiver_name",nullable=true,length=500)
	public java.lang.String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(java.lang.String receiverName) {
		this.receiverName = receiverName;
	}
	
	
}
