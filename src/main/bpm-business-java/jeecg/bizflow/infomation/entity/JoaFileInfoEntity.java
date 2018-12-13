package jeecg.bizflow.infomation.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 文件库
 * @author onlineGenerator
 * @date 2018-07-31 19:05:49
 * @version V1.0   
 *
 */
@Entity
@Table(name = "joa_file_info", schema = "")
@SuppressWarnings("serial")
public class JoaFileInfoEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	
	/**文件路径*/
	@Excel(name="文件路径",width=15)
	private java.lang.String path;
	/**文件名称*/
	@Excel(name="文件名称",width=15)
	private java.lang.String name;
	/**扩展名*/
	@Excel(name="扩展名",width=15)
	private java.lang.String ext;
	/**文件格式*/
	@Excel(name="文件格式",width=15)
	private java.lang.String type;
	/**文件大小kb*/
	@Excel(name="文件大小/b",width=15)
	private java.lang.Double size;
	/**业务类型*/
	@Excel(name="业务类型",width=15)
	private java.lang.String business;
	
	private String fromUser;
	private String owner;
	/**父级目录id*/
	private java.lang.String parentId;
	/**图标*/
	private java.lang.String icon;
	private Integer leaf;//是叶子节点1是0否
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
	/**流程状态*/
	private java.lang.String bpmStatus;

	
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
	 *@return: java.lang.String  文件路径
	 */

	@Column(name ="PATH",nullable=true,length=255)
	public java.lang.String getPath(){
		return this.path;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  文件路径
	 */
	public void setPath(java.lang.String path){
		this.path = path;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  文件名称
	 */

	@Column(name ="NAME",nullable=true,length=200)
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  文件名称
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  扩展名
	 */

	@Column(name ="EXT",nullable=true,length=10)
	public java.lang.String getExt(){
		return this.ext;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  扩展名
	 */
	public void setExt(java.lang.String ext){
		this.ext = ext;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  文件格式
	 */

	@Column(name ="TYPE",nullable=true,length=10)
	public java.lang.String getType(){
		return this.type;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  文件格式
	 */
	public void setType(java.lang.String type){
		this.type = type;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  文件大小kb
	 */

	@Column(name ="SIZE",nullable=true,scale=2,length=13)
	public java.lang.Double getSize(){
		return this.size;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  文件大小kb
	 */
	public void setSize(java.lang.Double size){
		this.size = size;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  业务类型
	 */

	@Column(name ="BUSINESS",nullable=true,length=32)
	public java.lang.String getBusiness(){
		return this.business;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  业务类型
	 */
	public void setBusiness(java.lang.String business){
		this.business = business;
	}

	@Column(name ="from_user",nullable=true,length=32)
	public String getFromUser() {
		return fromUser;
	}

	/**
	 * @param fromUser the fromUser to set
	 */
	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	@Column(name ="owner",nullable=true,length=32)
	public String getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	@Column(name ="PARENT_ID",nullable=true,length=32)
	public java.lang.String getParentId(){
		return this.parentId;
	}

	public void setParentId(java.lang.String parentId){
		this.parentId = parentId;
	}
	
	@Column(name ="ICON",nullable=true,length=50)
	public java.lang.String getIcon(){
		return this.icon;
	}

	public void setIcon(java.lang.String icon){
		this.icon = icon;
	}
	
	@Column(name ="LEAF",nullable=true,length=1)
	public Integer getLeaf() {
		return leaf;
	}

	public void setLeaf(Integer leaf) {
		this.leaf = leaf;
	}
}
