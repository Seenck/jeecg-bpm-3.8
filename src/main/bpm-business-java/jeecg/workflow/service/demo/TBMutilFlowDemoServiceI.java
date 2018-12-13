package jeecg.workflow.service.demo;
import java.io.Serializable;

import jeecg.workflow.entity.demo.TBMutilFlowDemoEntity;

import org.jeecgframework.core.common.service.CommonService;

/**
 * 出差借款多流程审批demo
 * @author zhoujf
 *
 */
public interface TBMutilFlowDemoServiceI extends CommonService{
	
 	public void delete(TBMutilFlowDemoEntity entity) throws Exception;
 	
 	public Serializable save(TBMutilFlowDemoEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TBMutilFlowDemoEntity entity) throws Exception;
 	
}
