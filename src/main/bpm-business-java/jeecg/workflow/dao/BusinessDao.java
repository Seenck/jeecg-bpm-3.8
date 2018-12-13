package jeecg.workflow.dao;

import org.jeecgframework.minidao.annotation.MiniDao;

@MiniDao
public interface BusinessDao {
	
	/**
	 * 查询最大商机编号
	 * @return
	 */
	public String getMaxBusCode();
}
