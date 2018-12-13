package org.jeecgframework.web.system.pojo.base;
// default package

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.jeecgframework.core.common.entity.IdEntity;

/**
 * 通用类型字典表
 *  @author  张代浩
 */
@Entity
@Table(name = "t_s_type")
public class TSType extends IdEntity implements java.io.Serializable {

	private TSTypegroup TSTypegroup;//类型分组
	private TSType TSType;//父类型
	private String typename;//类型名称
	private String typecode;//类型编码
	//update-begin--Author:zhangjiaqiang  Date:20160904 for：TASK #1338 【功能改造】字典表，没有创建时间，列表按照创建时间排序
	private Date createDate;//创建时间
	private String createName;//创建用户
	//update-begin--Author:zhangjiaqiang  Date:20160904 for：TASK #1338 【功能改造】字典表，没有创建时间，列表按照创建时间排序
//	private List<TPProcess> TSProcesses = new ArrayList();
	private List<TSType> TSTypes =new ArrayList();

	private Integer orderNum;//序号
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "typegroupid")
	public TSTypegroup getTSTypegroup() {
		return this.TSTypegroup;
	}

	public void setTSTypegroup(TSTypegroup TSTypegroup) {
		this.TSTypegroup = TSTypegroup;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "typepid")
	public TSType getTSType() {
		return this.TSType;
	}

	public void setTSType(TSType TSType) {
		this.TSType = TSType;
	}

	@Column(name = "typename", length = 50)
	public String getTypename() {
		return this.typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	@Column(name = "typecode", length = 50)
	public String getTypecode() {
		return this.typecode;
	}

	public void setTypecode(String typecode) {
		this.typecode = typecode;
	}
	//update-begin--Author:zhangjiaqiang  Date:20160904 for：TASK #1338 【功能改造】字典表，没有创建时间，列表按照创建时间排序
	@Column(name="create_date")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name="create_name",length=36)
	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}
	//update-end--Author:zhangjiaqiang  Date:20160904 for：TASK #1338 【功能改造】字典表，没有创建时间，列表按照创建时间排序
	
//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TSType")
//	public List<TPProcess> getTSProcesses() {
//		return this.TSProcesses;
//	}
//
//	public void setTSProcesses(List<TPProcess> TSProcesses) {
//		this.TSProcesses = TSProcesses;
//	}
	
	@Column(name="order_num",length=3)
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TSType")
	public List<TSType> getTSTypes() {
		return this.TSTypes;
	}

	public void setTSTypes(List<TSType> TSTypes) {
		this.TSTypes = TSTypes;
	}

}