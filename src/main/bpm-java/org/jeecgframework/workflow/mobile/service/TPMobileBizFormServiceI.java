package org.jeecgframework.workflow.mobile.service;
import java.io.Serializable;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.workflow.mobile.entity.TPMobileBizFormEntity;

public interface TPMobileBizFormServiceI extends CommonService{
	
 	public void delete(TPMobileBizFormEntity entity) throws Exception;
 	
 	public Serializable save(TPMobileBizFormEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TPMobileBizFormEntity entity) throws Exception;
 	
}
