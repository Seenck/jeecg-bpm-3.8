package jeecg.bizflow.overtimeleave.service;
import java.io.Serializable;

import jeecg.bizflow.overtimeleave.entity.JoaOvertimeLeaveEntity;

import org.jeecgframework.core.common.service.CommonService;

public interface JoaOvertimeLeaveServiceI extends CommonService{
	
 	public void delete(JoaOvertimeLeaveEntity entity) throws Exception;
 	
 	public Serializable save(JoaOvertimeLeaveEntity entity) throws Exception;
 	
 	public void saveOrUpdate(JoaOvertimeLeaveEntity entity) throws Exception;
 	
}
