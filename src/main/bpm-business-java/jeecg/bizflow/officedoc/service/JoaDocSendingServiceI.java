package jeecg.bizflow.officedoc.service;

import org.jeecgframework.core.common.service.CommonService;
import jeecg.bizflow.officedoc.entity.JoaDocSendingEntity;

import java.io.Serializable;

public interface JoaDocSendingServiceI extends CommonService{
	
 	public void delete(JoaDocSendingEntity entity) throws Exception;
 	
 	public Serializable save(JoaDocSendingEntity entity) throws Exception;
 	
 	public void saveOrUpdate(JoaDocSendingEntity entity) throws Exception;
 	
}
