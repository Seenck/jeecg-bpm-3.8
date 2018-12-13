package jeecg.workflow.controller.bus;

import javax.servlet.http.HttpServletRequest;

import jeecg.workflow.entity.bus.TBLeaveMobile;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.workflow.service.ActivitiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


/**
 * @ClassName: TBLeaveMobileController
 * @Description: TODO(演示业务处理类)
 * @author zhoujf
 */
@Controller
@RequestMapping("/tBLeaveMobileController")
public class TBLeaveMobileController extends BaseController {
	private static final Logger logger = Logger.getLogger(TBLeaveMobileController.class);
	@Autowired
	private SystemService systemService;
	@Autowired
	private ActivitiService activitiService;
	
	/**
	 * 请假申请页面跳转（移动端表单）
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TBLeaveMobile leave, HttpServletRequest request) {
		return new ModelAndView("business/demobus/leave-mobile");

	}
	
	/**
	 * 请假申请页面跳转（移动端表单）
	 */
	@RequestMapping(params = "goDetail")
	public ModelAndView goAdd(String id, HttpServletRequest request) {
		TBLeaveMobile leave = systemService.get(TBLeaveMobile.class, id);
		request.setAttribute("leave", leave);
		return new ModelAndView("business/demobus/leave-mobile-detail");

	}
	
	/**
	 * 添加jeecg_demo
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TBLeaveMobile leave, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "添加成功";
		try{
			systemService.save(leave);
			j.setObj(leave);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			//================流程提交 start======================
			//业务表名
			String tableName = "t_b_leave_mobile";
			//表单数据ID
			String id = leave.getId();
			//pc端默认表单URL
			String formUrl = "tBLeaveMobileController.do?goDetail";
			//移动端默认表单URL
			String formUrlMobile = "tBLeaveMobileController.do?goDetail";
			activitiService.startCommonUserDefinedProcess(tableName, id, formUrl, formUrlMobile);
			//================流程提交 end======================
		}catch(Exception e){
			e.printStackTrace();
			message = "添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

}
