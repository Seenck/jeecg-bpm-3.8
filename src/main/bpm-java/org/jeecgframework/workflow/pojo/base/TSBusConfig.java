package org.jeecgframework.workflow.pojo.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import org.jeecgframework.core.common.entity.IdEntity;

/**
 * 业务配置表
 */
@Entity
@Table(name = "t_s_busconfig")
public class TSBusConfig extends IdEntity implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private String busname;//业务名称
	
	//update-begin--Author:zhoujf  Date:20151208 for：增加业务标题表达式
	private String busTitleExp;//业务标题表达式
	//update-end--Author:zhoujf  Date:20151208 for：增加业务标题表达式
	
	//update-begin--Author:zhoujf  Date:20151210 for：增加流程办理风格配置
	private String processDealStyle;//流程办理风格
	//update-end--Author:zhoujf  Date:20151210 for：增加流程办理风格配置
	
	//update-begin--Author:zhoujf  Date:20180707 for：TASK #1184 【设计难点】流程表单多流程发起问题
	private String mutilFlowCode;//多流程表单编码
	private String mutilFlowForm;//多流程表单表名
	private String mutilFlowStatusCol;//多流程状态列名
	//update-begin--Author:zhoujf  Date:20180707 for：TASK #1184 【设计难点】流程表单多流程发起问题
	
	/**
	 * 表单类型：自定义实体，onlinecoding配置表
	 */
	private String formType;
	/**
	 * onlinecoding配置表ID
	 */
	private String onlineId;
	private TSTable TSTable=new TSTable();//业务表
	private TPProcess TPProcess=new TPProcess();
	@Column(name = "busname", length = 30)
	public String getBusname() {
		return busname;
	}

	public void setBusname(String busname) {
		this.busname = busname;
	}
	@Column(name = "BUS_TITLE_EXP", length = 150)
	public String getBusTitleExp() {
		return busTitleExp;
	}

	public void setBusTitleExp(String busTitleExp) {
		this.busTitleExp = busTitleExp;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tableid")
	public TSTable getTSTable() {
		return TSTable;
	}

	public void setTSTable(TSTable tSTable) {
		TSTable = tSTable;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "processid")
	public TPProcess getTPProcess() {
		return TPProcess;
	}

	public void setTPProcess(TPProcess tPProcess) {
		TPProcess = tPProcess;
	}
	
	@Column(name = "form_type", length = 10)
	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}

	@Column(name = "online_id", length = 50)
	public String getOnlineId() {
		return onlineId;
	}

	public void setOnlineId(String onlineId) {
		this.onlineId = onlineId;
	}

	@Column(name = "PROCESS_DEAL_STYLE", length = 100)
	public String getProcessDealStyle() {
		return processDealStyle;
	}

	public void setProcessDealStyle(String processDealStyle) {
		this.processDealStyle = processDealStyle;
	}

	@Column(name = "MUTIL_FLOW_CODE")
	public String getMutilFlowCode() {
		return mutilFlowCode;
	}

	public void setMutilFlowCode(String mutilFlowCode) {
		this.mutilFlowCode = mutilFlowCode;
	}

	@Column(name = "MUTIL_FLOW_FORM")
	public String getMutilFlowForm() {
		return mutilFlowForm;
	}

	public void setMutilFlowForm(String mutilFlowForm) {
		this.mutilFlowForm = mutilFlowForm;
	}

	@Column(name = "MUTIL_FLOW_STATUS_COL")
	public String getMutilFlowStatusCol() {
		return mutilFlowStatusCol;
	}

	public void setMutilFlowStatusCol(String mutilFlowStatusCol) {
		this.mutilFlowStatusCol = mutilFlowStatusCol;
	}



}