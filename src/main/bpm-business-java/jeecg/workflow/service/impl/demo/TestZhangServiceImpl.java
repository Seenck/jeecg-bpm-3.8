package jeecg.workflow.service.impl.demo;

import jeecg.workflow.service.demo.TestZhangServiceI;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("testZhangService")
@Transactional
public class TestZhangServiceImpl extends CommonServiceImpl implements TestZhangServiceI {
	
}