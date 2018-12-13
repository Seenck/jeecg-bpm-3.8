package jeecg.workflow.service.impl.bus;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jeecg.workflow.service.bus.TBServicemanageServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;

@Service("tBServicemanageService")
@Transactional
public class TBServicemanageServiceImpl extends CommonServiceImpl implements TBServicemanageServiceI {
	
}