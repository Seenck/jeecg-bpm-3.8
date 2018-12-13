package jeecg.bizflow.officedoc.service.impl;
import java.io.Serializable;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jeecg.bizflow.officedoc.entity.JoaDocReceivingEntity;
import jeecg.bizflow.officedoc.service.JoaDocReceivingServiceI;

@Service("joaDocReceivingService")
@Transactional
public class JoaDocReceivingServiceImpl extends CommonServiceImpl implements JoaDocReceivingServiceI {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
 	public void delete(JoaDocReceivingEntity entity) throws Exception{
 		super.delete(entity);
 	}
 	
 	public Serializable save(JoaDocReceivingEntity entity) throws Exception{
 		Serializable t = super.save(entity);
 		return t;
 	}
 	
 	public void saveOrUpdate(JoaDocReceivingEntity entity) throws Exception{
 		super.saveOrUpdate(entity);
 	}
 	
}