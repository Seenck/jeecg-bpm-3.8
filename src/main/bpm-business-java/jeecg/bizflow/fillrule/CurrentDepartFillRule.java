package jeecg.bizflow.fillrule;

import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.cgform.enhance.IFillRuleHandler;

/**
 * 获取当前登录人的部门规则实现类
 * @author LiShaoQing
 *
 */
public class CurrentDepartFillRule implements IFillRuleHandler {

	@Override
	public Object execute(String paramJson) {
		String departName = ResourceUtil.getSessionUser().getCurrentDepart().getDepartname();
		return departName;
	}

}
