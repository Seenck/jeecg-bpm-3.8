package jeecg.workflow.controller.demo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jeecg.workflow.entity.demo.TBZhangEntity;
import jeecg.workflow.service.demo.TestZhangServiceI;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.workflow.pojo.base.TSBusConfig;
import org.jeecgframework.workflow.pojo.base.TSPrjstatus;
import org.jeecgframework.workflow.service.ActivitiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**   
 * @Title: Controller
 * @Description: t_b_zhangdaihao
 * @author zhangdaihao
 * @date 2013-03-19 17:46:54
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/testZhangController")
public class TestZhangController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TestZhangController.class);

	@Autowired
	private TestZhangServiceI testZhangService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private ActivitiService activitiService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	
	/**
	 * 入职待办任务页面跳转
	 * 
	 * @param icon
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "testZhangTaskList")
	public ModelAndView bustripTaskList() {
		return new ModelAndView("business/demo/testZhangTaskList");
	}
	
	/**
	 * t_b_zhangdaihao列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "testZhang")
	public ModelAndView testZhang(HttpServletRequest request) {
		return new ModelAndView("business/demo/testZhangList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TBZhangEntity.class, dataGrid);
		this.testZhangService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除t_b_zhangdaihao
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(TBZhangEntity testZhang, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		testZhang = systemService.getEntity(TBZhangEntity.class, testZhang.getId());
		message = "删除成功";
		testZhangService.delete(testZhang);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加t_b_zhangdaihao
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(TBZhangEntity testZhang, HttpServletRequest request) {
		
		AjaxJson j = new AjaxJson();
		
		//-------------------------------------------------------------------
		//流程参数代码
		TSUser user = ResourceUtil.getSessionUser();
		String code = oConvertUtils.getString(request.getParameter("code"), "new");
		//huiqian_ceshi ->流程ID
		TSBusConfig busConfig = activitiService.getTSBusConfig(TBZhangEntity.class, "huiqian_ceshi");
		TSPrjstatus prjstatus = systemService.findUniqueByProperty(TSPrjstatus.class, "code", code);
		testZhang.setTSBusConfig(busConfig);
		testZhang.setTSPrjstatus(prjstatus);
		testZhang.setTSUser(user);
		//-------------------------------------------------------------------
		
		if (StringUtil.isNotEmpty(testZhang.getId())) {
			systemService.updateEntitie(testZhang);
			message = "更新成功";
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} else {
			message = "添加成功";
			systemService.save(testZhang);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);

		}
		j.setMsg(message);
		return j;
	}

	/**
	 * t_b_zhangdaihao列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(TBZhangEntity testZhang, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(testZhang.getId())) {
			testZhang = testZhangService.getEntity(TBZhangEntity.class, testZhang.getId());
			req.setAttribute("testZhangPage", testZhang);
		}
		return new ModelAndView("business/demo/testZhang");
	}
}
