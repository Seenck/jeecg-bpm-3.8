package org.jeecgframework.workflow.pojo.base;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.jeecgframework.core.common.entity.IdEntity;
import org.jeecgframework.web.system.pojo.base.TSType;
import org.jeecgframework.web.system.pojo.base.TSUser;


/**
 * 流程定义表
 */
@Entity
@Table(name = "t_p_process")
public class TPProcess extends IdEntity implements java.io.Serializable {
	private TSType TSType;//流程类型
	private TSUser TSUser;//创建人
	private String processname;//流程名称
	private Short processstate;//流程状态
	private String processxmlpath;//流程物理路径
	private byte[] processxml;//流程二进制文件
	private String processkey;//流程唯一Key
	private String note;//流程描述
	private Set<TPProcesspro> TPProcesspros = new HashSet<TPProcesspro>(0);	//流程变量集合
	private List<TPProcessnode> TPProcessnodes = new ArrayList<TPProcessnode>(0);//流程环节集合
	
	
	/**创建时间*/
	private java.util.Date createDate;
	/**创建人ID*/
	private java.lang.String createBy;
	/**创建人名称*/
	private java.lang.String createName;
	/**修改时间*/
	private java.util.Date updateDate;
	/**修改人*/
	private java.lang.String updateBy;
	/**修改人名称*/
	private java.lang.String updateName;

	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建时间
	 */
	@Column(name ="create_date",nullable=true)
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
	 *@return: java.lang.String  创建人ID
	 */
	@Column(name ="create_by",nullable=true,length=32)
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
	@Column(name ="create_name",nullable=true,length=32)
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
	 *@return: java.util.Date  修改时间
	 */
	@Column(name ="update_date",nullable=true)
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
	 *@return: java.lang.String  修改人ID
	 */
	@Column(name ="update_by",nullable=true,length=32)
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
	@Column(name ="update_name",nullable=true,length=32)
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "typeid")
	public TSType getTSType() {
		return this.TSType;
	}

	public void setTSType(TSType TSType) {
		this.TSType = TSType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userid")
	public TSUser getTSUser() {
		return this.TSUser;
	}

	public void setTSUser(TSUser TSUser) {
		this.TSUser = TSUser;
	}

	@Column(name = "processname", length = 50)
	public String getProcessname() {
		return this.processname;
	}

	public void setProcessname(String processname) {
		this.processname = processname;
	}

	@Column(name = "processstate")
	public Short getProcessstate() {
		return this.processstate;
	}

	public void setProcessstate(Short processstate) {
		this.processstate = processstate;
	}

	@Column(name = "processxmlpath", length = 100)
	public String getProcessxmlpath() {
		return this.processxmlpath;
	}

	public void setProcessxmlpath(String processxmlpath) {
		this.processxmlpath = processxmlpath;
	}

	@Column(name = "processxml")
	public byte[] getProcessxml() {
		return this.processxml;
	}

	public void setProcessxml(byte[] processxml) {
		this.processxml = processxml;
	}

	@Column(name = "processkey", length = 100)
	public String getProcesskey() {
		return this.processkey;
	}

	public void setProcesskey(String processkey) {
		this.processkey = processkey;
	}

	@Column(name = "note", length = 200)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TPProcess")
	public Set<TPProcesspro> getTPProcesspros() {
		return this.TPProcesspros;
	}

	public void setTPProcesspros(Set<TPProcesspro> TPProcesspros) {
		this.TPProcesspros = TPProcesspros;
	}	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TPProcess")
	public List<TPProcessnode> getTPProcessnodes() {
		return this.TPProcessnodes;
	}

	public void setTPProcessnodes(List<TPProcessnode> TPProcessnodes) {
		this.TPProcessnodes = TPProcessnodes;
	}

}