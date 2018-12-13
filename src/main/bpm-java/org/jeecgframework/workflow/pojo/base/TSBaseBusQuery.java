package org.jeecgframework.workflow.pojo.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.jeecgframework.core.common.entity.IdEntity;
import org.jeecgframework.web.system.pojo.base.TSType;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.workflow.model.activiti.Process;


/**
 * 业务父类表,如需走流程引擎的业务须继承该表
 */
@Entity
@Table(name = "t_s_basebus")
public class TSBaseBusQuery extends IdEntity implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private TSUser TSUser;// 创建人
	private TSType TSType;// 业务类型
	private Date createtime;// 创建时间
	private TSPrjstatus TSPrjstatus;// 业务自定义状态表
	private Process Process = new Process();//工作流引擎实例模型
	private TSBusConfig TSBusConfig = new TSBusConfig();// 业务配置表
	private String userid;
	private String userRealName;//任务发起人
	private String assigneeName;//任务办理人
	//update-begin--Author:zhoujf  Date:20151208 for：增加业务标题
	private String bpmBizTitle;//业务标题
	//update-begin--Author:zhoujf  Date:20151208 for：增加业务标题
	
	private Boolean timeoutRemaid;//超时提醒
	
	private Boolean taskNotification;//催办
	//-update-begin-----------author:zhoujf date:20180713 for:TASK #2939 【改进】催办任务
	private String taskNotificationRemarks;//催办说明
	//-update-end-----------author:zhoujf date:20180713 for:TASK #2939 【改进】催办任务

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userid")
	public TSUser getTSUser() {
		return TSUser;	
	}

	public void setTSUser(TSUser tSUser) {
		TSUser = tSUser;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "typeid")
	public TSType getTSType() {
		return TSType;
	}

	public void setTSType(TSType tSType) {
		TSType = tSType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "prjstateid")
	public TSPrjstatus getTSPrjstatus() {
		return TSPrjstatus;
	}

	public void setTSPrjstatus(TSPrjstatus tSPrjstatus) {
		this.TSPrjstatus = tSPrjstatus;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "busconfigid")
	public TSBusConfig getTSBusConfig() {
		return TSBusConfig;
	}

	public void setTSBusConfig(TSBusConfig TSBusConfig) {
		this.TSBusConfig = TSBusConfig;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "createtime", length = 13)
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	@Transient
	public Process getProcess() {
		return Process;
	}
	public void setProcess(Process Process) {
		this.Process = Process;
	}
	
	@Column(name = "userid",insertable=false, updatable=false)
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	@Transient
	public String getUserRealName() {
		return userRealName;
	}

	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}

	@Transient
	public String getAssigneeName() {
		return assigneeName;
	}

	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
	}

	@Transient
	public String getBpmBizTitle() {
		return bpmBizTitle;
	}

	public void setBpmBizTitle(String bpmBizTitle) {
		this.bpmBizTitle = bpmBizTitle;
	}

	@Transient
	public Boolean getTimeoutRemaid() {
		return timeoutRemaid;
	}

	public void setTimeoutRemaid(Boolean timeoutRemaid) {
		this.timeoutRemaid = timeoutRemaid;
	}

	@Transient
	public Boolean getTaskNotification() {
		return taskNotification;
	}

	public void setTaskNotification(Boolean taskNotification) {
		this.taskNotification = taskNotification;
	}

	@Transient
	public String getTaskNotificationRemarks() {
		return taskNotificationRemarks;
	}

	public void setTaskNotificationRemarks(String taskNotificationRemarks) {
		this.taskNotificationRemarks = taskNotificationRemarks;
	}

	
	
}