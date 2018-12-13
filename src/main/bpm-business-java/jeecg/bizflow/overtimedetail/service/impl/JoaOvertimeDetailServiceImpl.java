package jeecg.bizflow.overtimedetail.service.impl;
import java.io.Serializable;

import jeecg.bizflow.overtimedetail.entity.JoaOvertimeDetailEntity;
import jeecg.bizflow.overtimedetail.service.JoaOvertimeDetailServiceI;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("joaOvertimeDetailService")
@Transactional
public class JoaOvertimeDetailServiceImpl extends CommonServiceImpl implements JoaOvertimeDetailServiceI {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
 	public void delete(JoaOvertimeDetailEntity entity) throws Exception{
 		super.delete(entity);
 	}
 	
 	public Serializable save(JoaOvertimeDetailEntity entity) throws Exception{
 		Serializable t = super.save(entity);
 		return t;
 	}
 	
 	public void saveOrUpdate(JoaOvertimeDetailEntity entity) throws Exception{
 		super.saveOrUpdate(entity);
 	}
 	
}