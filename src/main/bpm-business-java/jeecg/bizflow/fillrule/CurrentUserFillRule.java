package jeecg.bizflow.fillrule;

import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.cgform.enhance.IFillRuleHandler;

/**
 * 获取当前登录人规则实现类
 * @author LiShaoQing
 *
 */

public class CurrentUserFillRule implements IFillRuleHandler {

	@Override
	public Object execute(String paramJson) {
		String realName = ResourceUtil.getSessionUser().getRealName();
		return realName;
	}

}
