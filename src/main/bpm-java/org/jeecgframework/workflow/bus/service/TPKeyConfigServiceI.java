package org.jeecgframework.workflow.bus.service;
import java.io.Serializable;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.workflow.bus.entity.TPKeyConfigEntity;

public interface TPKeyConfigServiceI extends CommonService{
	
 	public void delete(TPKeyConfigEntity entity) throws Exception;
 	
 	public Serializable save(TPKeyConfigEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TPKeyConfigEntity entity) throws Exception;
 	
}
