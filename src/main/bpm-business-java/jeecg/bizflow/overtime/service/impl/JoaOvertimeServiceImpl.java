package jeecg.bizflow.overtime.service.impl;
import java.io.Serializable;

import jeecg.bizflow.overtime.entity.JoaOvertimeEntity;
import jeecg.bizflow.overtime.service.JoaOvertimeServiceI;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("joaOvertimeService")
@Transactional
public class JoaOvertimeServiceImpl extends CommonServiceImpl implements JoaOvertimeServiceI {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
 	public void delete(JoaOvertimeEntity entity) throws Exception{
 		super.delete(entity);
 	}
 	
 	public Serializable save(JoaOvertimeEntity entity) throws Exception{
 		Serializable t = super.save(entity);
 		return t;
 	}
 	
 	public void saveOrUpdate(JoaOvertimeEntity entity) throws Exception{
 		super.saveOrUpdate(entity);
 	}
 	
}