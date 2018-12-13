package org.jeecgframework.workflow.dao;

import org.jeecgframework.core.common.dao.IGenericBaseCommonDao;

public interface IActivitiCommonDao extends IGenericBaseCommonDao{
	
	/**
	 * 注意会把整个表数据删除
	 * @param <T>
	 */
	public <T> void batchDelete(final Class<T> entityClass);

}
