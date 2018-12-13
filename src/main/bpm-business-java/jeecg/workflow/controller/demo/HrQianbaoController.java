package jeecg.workflow.controller.demo;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import jeecg.workflow.entity.demo.HrQianbaoEntity;
import jeecg.workflow.service.demo.HrQianbaoServiceI;
import jeecg.workflow.util.FormProcUtil;

import org.apache.log4j.Logger;
import org.jeecgframework.core.beanvalidator.BeanValidators;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.workflow.pojo.activiti.ActRuTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

/**   
 * @Title: Controller
 * @Description: hr_qianbao
 * @author onlineGenerator
 * @date 2016-11-22 16:00:46
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/hrQianbaoController")
public class HrQianbaoController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(HrQianbaoController.class);

	@Autowired
	private HrQianbaoServiceI hrQianbaoService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * hr_qianbao列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("business/demo/qianbao/hrQianbaoList");
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
	public void datagrid(HrQianbaoEntity hrQianbao,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(HrQianbaoEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, hrQianbao, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.hrQianbaoService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除hr_qianbao
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(HrQianbaoEntity hrQianbao, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		hrQianbao = systemService.getEntity(HrQianbaoEntity.class, hrQianbao.getId());
		message = "hr_qianbao删除成功";
		try{
			hrQianbaoService.delete(hrQianbao);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "hr_qianbao删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除hr_qianbao
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "hr_qianbao删除成功";
		try{
			for(String id:ids.split(",")){
				HrQianbaoEntity hrQianbao = systemService.getEntity(HrQianbaoEntity.class, 
				id
				);
				hrQianbaoService.delete(hrQianbao);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "hr_qianbao删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加hr_qianbao
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(HrQianbaoEntity hrQianbao, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "hr_qianbao添加成功";
		try{
			hrQianbaoService.save(hrQianbao);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "hr_qianbao添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新hr_qianbao
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(HrQianbaoEntity hrQianbao, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "hr_qianbao更新成功";
		HrQianbaoEntity t = hrQianbaoService.get(HrQianbaoEntity.class, hrQianbao.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(hrQianbao, t);
			hrQianbaoService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "hr_qianbao更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * hr_qianbao新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(HrQianbaoEntity hrQianbao, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(hrQianbao.getId())) {
			hrQianbao = hrQianbaoService.getEntity(HrQianbaoEntity.class, hrQianbao.getId());
			req.setAttribute("hrQianbaoPage", hrQianbao);
		}
		return new ModelAndView("business/demo/qianbao/hrQianbao-add");
	}
	/**
	 * hr_qianbao编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(HrQianbaoEntity hrQianbao, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(hrQianbao.getId())) {
			hrQianbao = hrQianbaoService.getEntity(HrQianbaoEntity.class, hrQianbao.getId());
			req.setAttribute("hrQianbaoPage", hrQianbao);
		}
		return new ModelAndView("business/demo/qianbao/hrQianbao-update");
	}
	
	
	
	/**
	 * 1.1.1	拟稿
	 * 
	 * @return
	 */
	@RequestMapping(params = "goForm_1_1_1")
	public ModelAndView goForm_1_1_1(HrQianbaoEntity hrQianbao, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(hrQianbao.getId())) {
			hrQianbao = hrQianbaoService.getEntity(HrQianbaoEntity.class, hrQianbao.getId());
			req.setAttribute("hrQianbaoPage", hrQianbao);
		}
		//初始化表单参数
		FormProcUtil.initFormParam(req);
		return new ModelAndView("business/demo/qianbao/hrQianbao-form-1-1-1");
	}
	
	
	
	/**
	 * 1.1.1.1	内部核稿
	 * 
	 * @return
	 */
	@RequestMapping(params = "goForm_1_1_1_1")
	public ModelAndView goForm_1_1_1_1(HrQianbaoEntity hrQianbao, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(hrQianbao.getId())) {
			hrQianbao = hrQianbaoService.getEntity(HrQianbaoEntity.class, hrQianbao.getId());
			req.setAttribute("hrQianbaoPage", hrQianbao);
		}
		String taskId = oConvertUtils.getString(req.getParameter("taskId"));
		req.setAttribute("taskId", taskId);
		return new ModelAndView("business/demo/qianbao/hrQianbao-form-1-1-1-1");
	}
	
	/**
	 * 1.1.2	部门领导审核
	 * 
	 * @return
	 */
	@RequestMapping(params = "goForm_1_1_2")
	public ModelAndView goForm_1_1_2(HrQianbaoEntity hrQianbao, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(hrQianbao.getId())) {
			hrQianbao = hrQianbaoService.getEntity(HrQianbaoEntity.class, hrQianbao.getId());
			req.setAttribute("hrQianbaoPage", hrQianbao);
		}
		String taskId = oConvertUtils.getString(req.getParameter("taskId"));
		req.setAttribute("taskId", taskId);
		return new ModelAndView("business/demo/qianbao/hrQianbao-form-1-1-2");
	}
	
	/**
	 * 1.1.3	拟稿人传递
	 * 
	 * @return
	 */
	@RequestMapping(params = "goForm_1_1_3")
	public ModelAndView goForm_1_1_3(HrQianbaoEntity hrQianbao, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(hrQianbao.getId())) {
			hrQianbao = hrQianbaoService.getEntity(HrQianbaoEntity.class, hrQianbao.getId());
			req.setAttribute("hrQianbaoPage", hrQianbao);
		}
		String taskId = oConvertUtils.getString(req.getParameter("taskId"));
		req.setAttribute("taskId", taskId);
		return new ModelAndView("business/demo/qianbao/hrQianbao-form-1-1-3");
	}
	
	/**
	 * 1.1.4	会签 (1)
	 * 
	 * @return
	 */
	@RequestMapping(params = "goForm_1_1_4_1")
	public ModelAndView goForm_1_1_4_1(HrQianbaoEntity hrQianbao, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(hrQianbao.getId())) {
			hrQianbao = hrQianbaoService.getEntity(HrQianbaoEntity.class, hrQianbao.getId());
			req.setAttribute("hrQianbaoPage", hrQianbao);
		}
		String taskId = oConvertUtils.getString(req.getParameter("taskId"));
		req.setAttribute("taskId", taskId);
		if (StringUtil.isNotEmpty(taskId)) {
			ActRuTask ruTask = this.systemService.get(ActRuTask.class, taskId);
			String assignee = ruTask.getAssignee();
			req.setAttribute("assignee", assignee);
			if("admin".equals(assignee)){
				return new ModelAndView("business/demo/qianbao/hrQianbao-form-1-1-4-2");
			}
		}
		return new ModelAndView("business/demo/qianbao/hrQianbao-form-1-1-4-1");
	}
	
	/**
	 * 1.1.4	会签 (2)
	 * 
	 * @return
	 */
	@RequestMapping(params = "goForm_1_1_4_2")
	public ModelAndView goForm_1_1_4_2(HrQianbaoEntity hrQianbao, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(hrQianbao.getId())) {
			hrQianbao = hrQianbaoService.getEntity(HrQianbaoEntity.class, hrQianbao.getId());
			req.setAttribute("hrQianbaoPage", hrQianbao);
		}
		String taskId = oConvertUtils.getString(req.getParameter("taskId"));
		req.setAttribute("taskId", taskId);
		return new ModelAndView("business/demo/qianbao/hrQianbao-form-1-1-4-2");
	}
	
	/**
	 * 1.1.5	拟稿人处理
	 * 
	 * @return
	 */
	@RequestMapping(params = "goForm_1_1_5")
	public ModelAndView goForm_1_1_5(HrQianbaoEntity hrQianbao, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(hrQianbao.getId())) {
			hrQianbao = hrQianbaoService.getEntity(HrQianbaoEntity.class, hrQianbao.getId());
			req.setAttribute("hrQianbaoPage", hrQianbao);
		}
		String taskId = oConvertUtils.getString(req.getParameter("taskId"));
		req.setAttribute("taskId", taskId);
		return new ModelAndView("business/demo/qianbao/hrQianbao-form-1-1-5");
	}
	
	/**
	 * 1.1.6	ISO9000审核
	 * 
	 * @return
	 */
	@RequestMapping(params = "goForm_1_1_6")
	public ModelAndView goForm_1_1_6(HrQianbaoEntity hrQianbao, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(hrQianbao.getId())) {
			hrQianbao = hrQianbaoService.getEntity(HrQianbaoEntity.class, hrQianbao.getId());
			req.setAttribute("hrQianbaoPage", hrQianbao);
		}
		String taskId = oConvertUtils.getString(req.getParameter("taskId"));
		req.setAttribute("taskId", taskId);
		return new ModelAndView("business/demo/qianbao/hrQianbao-form-1-1-6");
	}
	
	/**
	 * 1.1.7	ISO9000部门领导审核
	 * 
	 * @return
	 */
	@RequestMapping(params = "goForm_1_1_7")
	public ModelAndView goForm_1_1_7(HrQianbaoEntity hrQianbao, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(hrQianbao.getId())) {
			hrQianbao = hrQianbaoService.getEntity(HrQianbaoEntity.class, hrQianbao.getId());
			req.setAttribute("hrQianbaoPage", hrQianbao);
		}
		String taskId = oConvertUtils.getString(req.getParameter("taskId"));
		req.setAttribute("taskId", taskId);
		return new ModelAndView("business/demo/qianbao/hrQianbao-form-1-1-7");
	}
	
	/**
	 * 1.1.8	机要秘书组登记
	 * 
	 * @return
	 */
	@RequestMapping(params = "goForm_1_1_8")
	public ModelAndView goForm_1_1_8(HrQianbaoEntity hrQianbao, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(hrQianbao.getId())) {
			hrQianbao = hrQianbaoService.getEntity(HrQianbaoEntity.class, hrQianbao.getId());
			req.setAttribute("hrQianbaoPage", hrQianbao);
		}
		String taskId = oConvertUtils.getString(req.getParameter("taskId"));
		req.setAttribute("taskId", taskId);
		return new ModelAndView("business/demo/qianbao/hrQianbao-form-1-1-8");
	}
	
	/**
	 * 1.1.9	领导签批
	 * 
	 * @return
	 */
	@RequestMapping(params = "goForm_1_1_9")
	public ModelAndView goForm_1_1_9(HrQianbaoEntity hrQianbao, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(hrQianbao.getId())) {
			hrQianbao = hrQianbaoService.getEntity(HrQianbaoEntity.class, hrQianbao.getId());
			req.setAttribute("hrQianbaoPage", hrQianbao);
		}
		String taskId = oConvertUtils.getString(req.getParameter("taskId"));
		req.setAttribute("taskId", taskId);
		return new ModelAndView("business/demo/qianbao/hrQianbao-form-1-1-9");
	}
	
	/**
	 * 1.1.10	机要秘书组传递
	 * 
	 * @return
	 */
	@RequestMapping(params = "goForm_1_1_10")
	public ModelAndView goForm_1_1_10(HrQianbaoEntity hrQianbao, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(hrQianbao.getId())) {
			hrQianbao = hrQianbaoService.getEntity(HrQianbaoEntity.class, hrQianbao.getId());
			req.setAttribute("hrQianbaoPage", hrQianbao);
		}
		String taskId = oConvertUtils.getString(req.getParameter("taskId"));
		req.setAttribute("taskId", taskId);
		return new ModelAndView("business/demo/qianbao/hrQianbao-form-1-1-10");
	}
	
	/**
	 * 1.1.11	拟稿人办理
	 * 
	 * @return
	 */
	@RequestMapping(params = "goForm_1_1_11")
	public ModelAndView goForm_1_1_11(HrQianbaoEntity hrQianbao, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(hrQianbao.getId())) {
			hrQianbao = hrQianbaoService.getEntity(HrQianbaoEntity.class, hrQianbao.getId());
			req.setAttribute("hrQianbaoPage", hrQianbao);
		}
		String taskId = oConvertUtils.getString(req.getParameter("taskId"));
		req.setAttribute("taskId", taskId);
		return new ModelAndView("business/demo/qianbao/hrQianbao-form-1-1-11");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","hrQianbaoController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(HrQianbaoEntity hrQianbao,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(HrQianbaoEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, hrQianbao, request.getParameterMap());
		List<HrQianbaoEntity> hrQianbaos = this.hrQianbaoService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"hr_qianbao");
		modelMap.put(NormalExcelConstants.CLASS,HrQianbaoEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("hr_qianbao列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,hrQianbaos);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(HrQianbaoEntity hrQianbao,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"hr_qianbao");
    	modelMap.put(NormalExcelConstants.CLASS,HrQianbaoEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("hr_qianbao列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
    	"导出信息"));
    	modelMap.put(NormalExcelConstants.DATA_LIST,new ArrayList());
    	return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				List<HrQianbaoEntity> listHrQianbaoEntitys = ExcelImportUtil.importExcel(file.getInputStream(),HrQianbaoEntity.class,params);
				for (HrQianbaoEntity hrQianbao : listHrQianbaoEntitys) {
					hrQianbaoService.save(hrQianbao);
				}
				j.setMsg("文件导入成功！");
			} catch (Exception e) {
				j.setMsg("文件导入失败！");
				logger.error(ExceptionUtil.getExceptionMessage(e));
			}finally{
				try {
					file.getInputStream().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return j;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<HrQianbaoEntity> list() {
		List<HrQianbaoEntity> listHrQianbaos=hrQianbaoService.getList(HrQianbaoEntity.class);
		return listHrQianbaos;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		HrQianbaoEntity task = hrQianbaoService.get(HrQianbaoEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody HrQianbaoEntity hrQianbao, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<HrQianbaoEntity>> failures = validator.validate(hrQianbao);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		hrQianbaoService.save(hrQianbao);

		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = hrQianbao.getId();
		URI uri = uriBuilder.path("/rest/hrQianbaoController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody HrQianbaoEntity hrQianbao) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<HrQianbaoEntity>> failures = validator.validate(hrQianbao);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		hrQianbaoService.saveOrUpdate(hrQianbao);

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") String id) {
		hrQianbaoService.deleteEntityById(HrQianbaoEntity.class, id);
	}
}
