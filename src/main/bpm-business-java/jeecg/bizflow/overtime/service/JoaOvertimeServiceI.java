package jeecg.bizflow.overtime.service;
import java.io.Serializable;

import jeecg.bizflow.overtime.entity.JoaOvertimeEntity;

import org.jeecgframework.core.common.service.CommonService;

public interface JoaOvertimeServiceI extends CommonService{
	
 	public void delete(JoaOvertimeEntity entity) throws Exception;
 	
 	public Serializable save(JoaOvertimeEntity entity) throws Exception;
 	
 	public void saveOrUpdate(JoaOvertimeEntity entity) throws Exception;
 	
}
