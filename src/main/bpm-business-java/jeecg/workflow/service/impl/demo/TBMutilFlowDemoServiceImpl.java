package jeecg.workflow.service.impl.demo;
import java.io.Serializable;

import jeecg.workflow.entity.demo.TBMutilFlowDemoEntity;
import jeecg.workflow.service.demo.TBMutilFlowDemoServiceI;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 出差借款多流程审批demo
 * @author zhoujf
 *
 */
@Service("tBMutilFlowDemoService")
@Transactional
public class TBMutilFlowDemoServiceImpl extends CommonServiceImpl implements TBMutilFlowDemoServiceI {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
 	public void delete(TBMutilFlowDemoEntity entity) throws Exception{
 		super.delete(entity);
 	}
 	
 	public Serializable save(TBMutilFlowDemoEntity entity) throws Exception{
 		Serializable t = super.save(entity);
 		return t;
 	}
 	
 	public void saveOrUpdate(TBMutilFlowDemoEntity entity) throws Exception{
 		super.saveOrUpdate(entity);
 	}
 	
 	
}