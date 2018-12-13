package jeecg.bizflow.officedoc.service;

import org.jeecgframework.core.common.service.CommonService;
import jeecg.bizflow.officedoc.entity.JoaDocReceivingEntity;

import java.io.Serializable;

public interface JoaDocReceivingServiceI extends CommonService{
	
 	public void delete(JoaDocReceivingEntity entity) throws Exception;
 	
 	public Serializable save(JoaDocReceivingEntity entity) throws Exception;
 	
 	public void saveOrUpdate(JoaDocReceivingEntity entity) throws Exception;
 	
}
