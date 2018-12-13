package org.jeecgframework.workflow.user.service;
import java.io.Serializable;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.workflow.user.entity.TSUserAgentEntity;

public interface TSUserAgentServiceI extends CommonService{
	
 	public void delete(TSUserAgentEntity entity) throws Exception;
 	
 	public Serializable save(TSUserAgentEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TSUserAgentEntity entity) throws Exception;
 	
}
