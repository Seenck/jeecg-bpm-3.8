package org.jeecgframework.workflow.pojo.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.common.entity.IdEntity;

/**
 * 流程变量表
 */
@Entity
@Table(name = "t_p_processpro")
public class TPProcesspro extends IdEntity implements java.io.Serializable {

	// 所属流程
	private TPProcess TPProcess = new TPProcess();
	// 标量标示
	private String processprokey;
	// 变量名称
	private String processproname;
	// 变量类型
	private String processprotype;
	// 变量值
	private String processprovalue;

	// 所属节点
	private TPProcessnode TPProcessnode = new TPProcessnode();

	@Column(name = "processprotype", length = 50)
	public String getProcessprotype() {
		return processprotype;
	}

	public void setProcessprotype(String processprodatatype) {
		this.processprotype = processprodatatype;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "processid")
	public TPProcess getTPProcess() {
		return this.TPProcess;
	}

	public void setTPProcess(TPProcess TPProcess) {
		this.TPProcess = TPProcess;
	}

	@Column(name = "processproname", length = 50)
	public String getProcessproname() {
		return this.processproname;
	}

	public void setProcessproname(String processproname) {
		this.processproname = processproname;
	}
	@Column(name = "processprokey", length = 20)
	public String getProcessprokey() {
		return processprokey;
	}

	public void setProcessprokey(String processprokey) {
		this.processprokey = processprokey;
	}


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "processnodeid")
	public TPProcessnode getTPProcessnode() {
		return TPProcessnode;
	}

	public void setTPProcessnode(TPProcessnode tProcessnode) {
		TPProcessnode = tProcessnode;
	}

	@Column(name = "processprovalue")
	public String getProcessprovalue() {
		return processprovalue;
	}

	public void setProcessprovalue(String processprovalue) {
		this.processprovalue = processprovalue;
	}

}