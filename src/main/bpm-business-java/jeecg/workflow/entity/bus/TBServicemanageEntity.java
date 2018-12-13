package jeecg.workflow.entity.bus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.jeecgframework.core.annotation.JeecgEntityTitle;
import org.jeecgframework.workflow.pojo.base.TSBaseBus;

/**   
 * @Title: Entity
 * @Description: 服务上报
 * @author zhangdaihao
 * @date 2013-09-15 22:08:13
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_servicemanage", schema = "")
@PrimaryKeyJoinColumn(name = "id")
@JeecgEntityTitle(name="服务管理表")
public class TBServicemanageEntity extends TSBaseBus implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8831249135063857112L;
	/**上报来源*/
	private java.lang.String trapSource;
	/**服务团体*/
	private java.lang.String serviceTeam;
	/**服务目录*/
	private java.lang.String serviceItem;
	/**操作用户*/
	private java.lang.String createName;
	/**操作时间*/
	private java.util.Date createDate;
	/**操作人id*/
	private java.lang.String createBy;
	/**主题*/
	private java.lang.String subject;
	/**描述*/
	private java.lang.String content;
	/**紧急程度*/
	private java.lang.String urgentLevel;
	/**影响程度*/
	private java.lang.String effectLevel;
	/**客户端ip*/
	private java.lang.String clientIp;
	/**提交时间*/
	private java.util.Date submitDate;
	/**提交用户ID*/
	private java.lang.String submitBy;
	/**提交用户部门id*/
	private java.lang.String departId;
	/**提交用户部门名称*/
	private java.lang.String departName;
	
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  上报来源
	 */
	@Column(name ="TRAP_SOURCE",nullable=true)
	public java.lang.String getTrapSource(){
		return this.trapSource;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  上报来源
	 */
	public void setTrapSource(java.lang.String trapSource){
		this.trapSource = trapSource;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  服务团体
	 */
	@Column(name ="SERVICE_TEAM",nullable=true)
	public java.lang.String getServiceTeam(){
		return this.serviceTeam;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  服务团体
	 */
	public void setServiceTeam(java.lang.String serviceTeam){
		this.serviceTeam = serviceTeam;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  服务目录
	 */
	@Column(name ="SERVICE_ITEM",nullable=true)
	public java.lang.String getServiceItem(){
		return this.serviceItem;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  服务目录
	 */
	public void setServiceItem(java.lang.String serviceItem){
		this.serviceItem = serviceItem;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  操作用户
	 */
	@Column(name ="CREATE_NAME",nullable=true)
	public java.lang.String getCreateName(){
		return this.createName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  操作用户
	 */
	public void setCreateName(java.lang.String createName){
		this.createName = createName;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  操作时间
	 */
	@Column(name ="CREATE_DATE",nullable=true)
	public java.util.Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  操作时间
	 */
	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  操作人id
	 */
	@Column(name ="CREATE_BY",nullable=true)
	public java.lang.String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  操作人id
	 */
	public void setCreateBy(java.lang.String createBy){
		this.createBy = createBy;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主题
	 */
	@Column(name ="SUBJECT",nullable=true)
	public java.lang.String getSubject(){
		return this.subject;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主题
	 */
	public void setSubject(java.lang.String subject){
		this.subject = subject;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  描述
	 */
	@Column(name ="CONTENT",nullable=true)
	public java.lang.String getContent(){
		return this.content;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  描述
	 */
	public void setContent(java.lang.String content){
		this.content = content;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  紧急程度
	 */
	@Column(name ="URGENT_LEVEL",nullable=true)
	public java.lang.String getUrgentLevel(){
		return this.urgentLevel;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  紧急程度
	 */
	public void setUrgentLevel(java.lang.String urgentLevel){
		this.urgentLevel = urgentLevel;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  影响程度
	 */
	@Column(name ="EFFECT_LEVEL",nullable=true)
	public java.lang.String getEffectLevel(){
		return this.effectLevel;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  影响程度
	 */
	public void setEffectLevel(java.lang.String effectLevel){
		this.effectLevel = effectLevel;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  客户端ip
	 */
	@Column(name ="CLIENT_IP",nullable=true)
	public java.lang.String getClientIp(){
		return this.clientIp;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  客户端ip
	 */
	public void setClientIp(java.lang.String clientIp){
		this.clientIp = clientIp;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  提交时间
	 */
	@Column(name ="SUBMIT_DATE",nullable=true)
	public java.util.Date getSubmitDate(){
		return this.submitDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  提交时间
	 */
	public void setSubmitDate(java.util.Date submitDate){
		this.submitDate = submitDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  提交用户ID
	 */
	@Column(name ="SUBMIT_BY",nullable=true)
	public java.lang.String getSubmitBy(){
		return this.submitBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  提交用户ID
	 */
	public void setSubmitBy(java.lang.String submitBy){
		this.submitBy = submitBy;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  提交用户部门id
	 */
	@Column(name ="DEPART_ID",nullable=true)
	public java.lang.String getDepartId(){
		return this.departId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  提交用户部门id
	 */
	public void setDepartId(java.lang.String departId){
		this.departId = departId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  提交用户部门名称
	 */
	@Column(name ="DEPART_NAME",nullable=true)
	public java.lang.String getDepartName(){
		return this.departName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  提交用户部门名称
	 */
	public void setDepartName(java.lang.String departName){
		this.departName = departName;
	}
}
