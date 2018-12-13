package jeecg.bizflow.officedoc.service.impl;
import java.io.Serializable;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jeecg.bizflow.officedoc.entity.JoaDocSendingEntity;
import jeecg.bizflow.officedoc.service.JoaDocSendingServiceI;

@Service("joaDocSendingService")
@Transactional
public class JoaDocSendingServiceImpl extends CommonServiceImpl implements JoaDocSendingServiceI {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
 	public void delete(JoaDocSendingEntity entity) throws Exception{
 		super.delete(entity);
 	}
 	
 	public Serializable save(JoaDocSendingEntity entity) throws Exception{
 		Serializable t = super.save(entity);
 		return t;
 	}
 	
 	public void saveOrUpdate(JoaDocSendingEntity entity) throws Exception{
 		super.saveOrUpdate(entity);
 	}
 	
}