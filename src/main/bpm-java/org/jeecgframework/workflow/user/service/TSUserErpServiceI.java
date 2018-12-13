package org.jeecgframework.workflow.user.service;
import java.io.Serializable;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.workflow.user.entity.TSUserErpEntity;

public interface TSUserErpServiceI extends CommonService{
	
 	public void delete(TSUserErpEntity entity) throws Exception;
 	
 	public Serializable save(TSUserErpEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TSUserErpEntity entity) throws Exception;
 	
}
