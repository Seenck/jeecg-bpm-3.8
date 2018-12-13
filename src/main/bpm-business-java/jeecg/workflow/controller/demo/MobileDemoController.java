package jeecg.workflow.controller.demo;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**   
 * @Title: Controller
 * @Description: student_zuoye
 * @author zhoujf
 * @date 2016-03-03 
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/mobileDemoController")
public class MobileDemoController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MobileDemoController.class);

	/**
	 * 跳转到测试表单申请
	 */
	@RequestMapping(params = "goDemoForm")
	public ModelAndView goDemoForm(HttpServletRequest request) {
		return new ModelAndView("workflow/mobile/form/demo-form-add");
	}
}
