package jeecg.bizflow.overtimedetail.service;
import java.io.Serializable;

import jeecg.bizflow.overtimedetail.entity.JoaOvertimeDetailEntity;

import org.jeecgframework.core.common.service.CommonService;

public interface JoaOvertimeDetailServiceI extends CommonService{
	
 	public void delete(JoaOvertimeDetailEntity entity) throws Exception;
 	
 	public Serializable save(JoaOvertimeDetailEntity entity) throws Exception;
 	
 	public void saveOrUpdate(JoaOvertimeDetailEntity entity) throws Exception;
 	
}
