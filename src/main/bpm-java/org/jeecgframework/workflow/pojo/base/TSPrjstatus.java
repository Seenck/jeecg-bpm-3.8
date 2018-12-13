package org.jeecgframework.workflow.pojo.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.jeecgframework.core.common.entity.IdEntity;


/**
 * 业务流程状态表
 */
@Entity
@Table(name = "t_s_prjstatus")
public class TSPrjstatus extends IdEntity implements java.io.Serializable {

	private String status;// 状态值
	private String description;// 状态描述
	private String code;// 状态唯一编码
	private String name;// 状态简称
	private String executerole;// 执行角色
	private String nextsteprole;// 下一步操作的角色

	@Column(name = "executerole", length = 100)
	public String getExecuterole() {
		return executerole;
	}

	public void setExecuterole(String executerole) {
		this.executerole = executerole;
	}

	@Column(name = "nextsteprole", length = 100)
	public String getNextsteprole() {
		return nextsteprole;
	}

	public void setNextsteprole(String nextsteprole) {
		this.nextsteprole = nextsteprole;
	}

	@Column(name = "status", length = 100)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "description", length = 300)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "code", length = 50)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "name", length = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}