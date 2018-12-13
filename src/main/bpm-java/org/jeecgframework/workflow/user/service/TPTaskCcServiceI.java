package org.jeecgframework.workflow.user.service;
import java.io.Serializable;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.workflow.user.entity.TPTaskCcEntity;

public interface TPTaskCcServiceI extends CommonService{
	
 	public void delete(TPTaskCcEntity entity) throws Exception;
 	
 	public Serializable save(TPTaskCcEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TPTaskCcEntity entity) throws Exception;
 	
}
