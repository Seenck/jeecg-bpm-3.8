package org.jeecgframework.workflow.pojo.base;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.jeecgframework.core.common.entity.IdEntity;

/**
 * 流程环节表
 */
@Entity
@Table(name = "t_p_processnode_deployment")
public class TPProcessnodeDeployment extends IdEntity implements java.io.Serializable {

	private static final long serialVersionUID = 8546601410017843723L;
	private String formid;//节点名称
	private String processid;//节点名称
	private String processnodename;//节点名称
	private String processnodecode;//节点ID
	private String modelandview;//外部表单跳转方法
	private String modelandviewMobile;//外部表单跳转方法(移动端)
	private Integer nodeTimeout;//节点任务超时设置（单位时）
	private String deploymentId;//节点名称
	//update-begin--Author:zhoujf  Date:20180706 for：TASK #2876 【新功能】流程状态变更，根据节点的状态变更
	private String nodeBpmStatus;//流程状态
	//update-end--Author:zhoujf  Date:20180706 for：TASK #2876 【新功能】流程状态变更，根据节点的状态变更


	@Column(name = "modelandview_mobile", length = 50)
	public String getModelandviewMobile() {
		return modelandviewMobile;
	}

	public void setModelandviewMobile(String modelandviewMobile) {
		this.modelandviewMobile = modelandviewMobile;
	}

	@Column(name = "processnodename", length = 50)
	public String getProcessnodename() {
		return this.processnodename;
	}

	public void setProcessnodename(String processnodename) {
		this.processnodename = processnodename;
	}

	@Column(name = "processnodecode", length = 50)
	public String getProcessnodecode() {
		return this.processnodecode;
	}

	public void setProcessnodecode(String processnodecode) {
		this.processnodecode = processnodecode;
	}
	@Column(name = "modelandview", length = 50)
	public String getModelandview() {
		return modelandview;
	}

	public void setModelandview(String modelandview) {
		this.modelandview = modelandview;
	}

	@Column(name = "NODE_TIMEOUT")
	public Integer getNodeTimeout() {
		return nodeTimeout;
	}

	public void setNodeTimeout(Integer nodeTimeout) {
		this.nodeTimeout = nodeTimeout;
	}

	@Column(name = "FORMID")
	public String getFormid() {
		return formid;
	}

	public void setFormid(String formid) {
		this.formid = formid;
	}

	@Column(name = "PROCESSID")
	public String getProcessid() {
		return processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	@Column(name = "DEPLOYMENT_ID")
	public String getDeploymentId() {
		return deploymentId;
	}

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

	@Column(name = "NODE_BPM_STATUS")
	public String getNodeBpmStatus() {
		return nodeBpmStatus;
	}

	public void setNodeBpmStatus(String nodeBpmStatus) {
		this.nodeBpmStatus = nodeBpmStatus;
	}

}