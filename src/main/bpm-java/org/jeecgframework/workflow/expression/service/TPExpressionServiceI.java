package org.jeecgframework.workflow.expression.service;
import java.io.Serializable;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.workflow.expression.entity.TPExpressionEntity;

public interface TPExpressionServiceI extends CommonService{
	
 	public void delete(TPExpressionEntity entity) throws Exception;
 	
 	public Serializable save(TPExpressionEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TPExpressionEntity entity) throws Exception;
 	
}
