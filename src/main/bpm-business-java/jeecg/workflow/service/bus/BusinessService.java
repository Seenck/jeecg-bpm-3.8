package jeecg.workflow.service.bus;

import org.jeecgframework.core.common.service.CommonService;

public interface BusinessService extends CommonService{
	/**
	 * 获取数据库中最大的商业编码
	 * @return
	 */
	public String getMaxBusCode();
}
