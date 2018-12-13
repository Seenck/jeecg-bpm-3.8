package org.jeecgframework.workflow.pojo.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.jeecgframework.core.common.entity.IdEntity;
import org.jeecgframework.web.system.pojo.base.TSType;

/**
 * 数据库所有表对象
 */
@Entity
@Table(name = "t_s_table")
public class TSTable extends IdEntity implements java.io.Serializable {
	private String tableTitle;//数据表标题
	private String entityName;//数据表对应实体名称
	private String tableName;//数据表对应库名称
	private String entityKey;//数据表实体类型
	private TSType TSType;//数据表类型

	@Column(name = "tabletitle", length = 20)
	public String getTableTitle() {
		return this.tableTitle;
	}

	public void setTableTitle(String tableTitle) {
		this.tableTitle = tableTitle;
	}

	@Column(name = "entityname", length = 200)
	public String getEntityName() {
		return this.entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	@Column(name = "tablename", length = 30)
	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Column(name = "entitykey", length = 50)
	public String getEntityKey() {
		return this.entityKey;
	}

	public void setEntityKey(String entityKey) {
		this.entityKey = entityKey;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "typeid")
	public TSType getTSType() {
		return this.TSType;
	}

	public void setTSType(TSType TSType) {
		this.TSType = TSType;
	}

}