package jeecg.bizflow.overtimeleave.service.impl;
import java.io.Serializable;

import jeecg.bizflow.overtimeleave.entity.JoaOvertimeLeaveEntity;
import jeecg.bizflow.overtimeleave.service.JoaOvertimeLeaveServiceI;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("joaOvertimeLeaveService")
@Transactional
public class JoaOvertimeLeaveServiceImpl extends CommonServiceImpl implements JoaOvertimeLeaveServiceI {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
 	public void delete(JoaOvertimeLeaveEntity entity) throws Exception{
 		super.delete(entity);
 	}
 	
 	public Serializable save(JoaOvertimeLeaveEntity entity) throws Exception{
 		Serializable t = super.save(entity);
 		return t;
 	}
 	
 	public void saveOrUpdate(JoaOvertimeLeaveEntity entity) throws Exception{
 		super.saveOrUpdate(entity);
 	}
 	
}