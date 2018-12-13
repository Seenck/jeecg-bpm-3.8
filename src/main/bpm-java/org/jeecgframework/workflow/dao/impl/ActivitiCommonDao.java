package org.jeecgframework.workflow.dao.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.jeecgframework.core.common.dao.IGenericBaseCommonDao;
import org.jeecgframework.core.common.dao.impl.GenericBaseCommonDao;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.workflow.dao.IActivitiCommonDao;
import org.springframework.stereotype.Repository;

@Repository
public class ActivitiCommonDao extends GenericBaseCommonDao implements IActivitiCommonDao,IGenericBaseCommonDao{

	public <T> void batchDelete(Class<T> entityClass){
		Query query = this.getSession().createQuery("DELETE FROM "+entityClass.getName());
		query.executeUpdate();
	}

}
