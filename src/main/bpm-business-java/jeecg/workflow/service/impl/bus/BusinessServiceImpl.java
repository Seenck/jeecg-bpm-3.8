package jeecg.workflow.service.impl.bus;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jeecg.workflow.dao.BusinessDao;
import jeecg.workflow.service.bus.BusinessService;

@Service("businessService")
@Transactional
public class BusinessServiceImpl extends CommonServiceImpl implements BusinessService {

	/**
	 * 获取数据库中最大的商业编码
	 */
	public String getMaxBusCode() {
		BusinessDao businessDao = ApplicationContextUtil.getContext().getBean(BusinessDao.class);
		return businessDao.getMaxBusCode();
	}
}
