package org.jeecgframework.workflow.pojo.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.jeecgframework.core.common.entity.IdEntity;
import org.jeecgframework.web.system.pojo.base.TSFunction;

@Entity
@Table(name = "t_p_processnode_function")
public class TPProcessnodeFunction extends IdEntity implements java.io.Serializable {
	private TSFunction TSFunction;
	private TPProcessnode TPProcessnode;
	private String operation;
	private String dataRule;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "functionid")
	public TSFunction getTSFunction() {
		return TSFunction;
	}
	public void setTSFunction(TSFunction tSFunction) {
		TSFunction = tSFunction;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "processnodeid")
	public TPProcessnode getTPProcessnode() {
		return TPProcessnode;
	}
	public void setTPProcessnode(TPProcessnode tPProcessnode) {
		TPProcessnode = tPProcessnode;
	}
	@Column(name = "operation", length = 100)
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	@Column(name = "dataRule", length = 100)
	public String getDataRule() {
		return dataRule;
	}
	public void setDataRule(String dataRule) {
		this.dataRule = dataRule;
	}
	
	
}
