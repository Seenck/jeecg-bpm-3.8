package org.jeecgframework.workflow.pojo.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.jeecgframework.core.common.entity.IdEntity;

/**
 * 流程环节监听器映射表
 */
@Entity
@Table(name = "t_p_processlistener")
public class TPProcessListener extends IdEntity implements java.io.Serializable {

	private TPListerer TPListerer;//监听器
	private TPProcessnode TPProcessnode;//流程节点
	private TPProcess TPProcess;//所属流程
	private String nodename;//节点名称
	private Short status;//监听 状态
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "listenerid")
	public TPListerer getTPListerer() {
		return this.TPListerer;
	}

	public void setTPListerer(TPListerer TPListerer) {
		this.TPListerer = TPListerer;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "porcessnodeid")
	public TPProcessnode getTPProcessnode() {
		return this.TPProcessnode;
	}

	public void setTPProcessnode(TPProcessnode TPProcessnode) {
		this.TPProcessnode = TPProcessnode;
	}
	@Column(name = "status")
	public Short getStatus() {
		return status;
	}
	public void setStatus(Short status) {
		this.status = status;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "processid")
	public TPProcess getTPProcess() {
		return this.TPProcess;
	}

	public void setTPProcess(TPProcess TPProcess) {
		this.TPProcess = TPProcess;
	}
	@Column(name = "nodename", length = 50)
	public String getNodename() {
		return this.nodename;
	}

	public void setNodename(String nodename) {
		this.nodename = nodename;
	}


}