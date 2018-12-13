package org.jeecgframework.workflow.mobile.controller;
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
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.workflow.mobile.entity.TPMobileBizFormEntity;
import org.jeecgframework.workflow.mobile.service.TPMobileBizFormServiceI;
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
 * @Description: 业务申请管理
 * @author onlineGenerator
 * @date 2017-03-04 15:41:23
 * @version V1.0   
 *
 */
@Controller("tPMobileBizFormController")
@RequestMapping("/tPMobileBizFormController")
public class TPMobileBizFormController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TPMobileBizFormController.class);

	@Autowired
	private TPMobileBizFormServiceI tPMobileBizFormService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 业务申请管理列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("workflow/mobile/biz/tPMobileBizFormList");
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
	public void datagrid(TPMobileBizFormEntity tPMobileBizForm,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TPMobileBizFormEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPMobileBizForm, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tPMobileBizFormService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	@RequestMapping(params = "getApplayButtonList")
	@ResponseBody
	public List<TPMobileBizFormEntity> getApplayButtonList(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String hql = "from TPMobileBizFormEntity where isRecommend= '1' order by formSort";
		List<TPMobileBizFormEntity> list = tPMobileBizFormService.findHql(hql);
		return list;
	}

	/**
	 * 删除业务申请管理
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TPMobileBizFormEntity tPMobileBizForm, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tPMobileBizForm = systemService.getEntity(TPMobileBizFormEntity.class, tPMobileBizForm.getId());
		message = "业务申请管理删除成功";
		try{
			tPMobileBizFormService.delete(tPMobileBizForm);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "业务申请管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除业务申请管理
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "业务申请管理删除成功";
		try{
			for(String id:ids.split(",")){
				TPMobileBizFormEntity tPMobileBizForm = systemService.getEntity(TPMobileBizFormEntity.class, 
				id
				);
				tPMobileBizFormService.delete(tPMobileBizForm);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "业务申请管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加业务申请管理
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TPMobileBizFormEntity tPMobileBizForm, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "业务申请管理添加成功";
		try{
			tPMobileBizFormService.save(tPMobileBizForm);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "业务申请管理添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新业务申请管理
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TPMobileBizFormEntity tPMobileBizForm, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "业务申请管理更新成功";
		TPMobileBizFormEntity t = tPMobileBizFormService.get(TPMobileBizFormEntity.class, tPMobileBizForm.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tPMobileBizForm, t);
			tPMobileBizFormService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "业务申请管理更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 业务申请管理新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TPMobileBizFormEntity tPMobileBizForm, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tPMobileBizForm.getId())) {
			tPMobileBizForm = tPMobileBizFormService.getEntity(TPMobileBizFormEntity.class, tPMobileBizForm.getId());
			req.setAttribute("tPMobileBizFormPage", tPMobileBizForm);
		}
		return new ModelAndView("workflow/mobile/biz/tPMobileBizForm-add");
	}
	/**
	 * 业务申请管理编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TPMobileBizFormEntity tPMobileBizForm, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tPMobileBizForm.getId())) {
			tPMobileBizForm = tPMobileBizFormService.getEntity(TPMobileBizFormEntity.class, tPMobileBizForm.getId());
			req.setAttribute("tPMobileBizFormPage", tPMobileBizForm);
		}
		return new ModelAndView("workflow/mobile/biz/tPMobileBizForm-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tPMobileBizFormController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TPMobileBizFormEntity tPMobileBizForm,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TPMobileBizFormEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPMobileBizForm, request.getParameterMap());
		List<TPMobileBizFormEntity> tPMobileBizForms = this.tPMobileBizFormService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"业务申请管理");
		modelMap.put(NormalExcelConstants.CLASS,TPMobileBizFormEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("业务申请管理列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tPMobileBizForms);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TPMobileBizFormEntity tPMobileBizForm,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"业务申请管理");
    	modelMap.put(NormalExcelConstants.CLASS,TPMobileBizFormEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("业务申请管理列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
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
				List<TPMobileBizFormEntity> listTPMobileBizFormEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TPMobileBizFormEntity.class,params);
				for (TPMobileBizFormEntity tPMobileBizForm : listTPMobileBizFormEntitys) {
					tPMobileBizFormService.save(tPMobileBizForm);
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
	public List<TPMobileBizFormEntity> list() {
		List<TPMobileBizFormEntity> listTPMobileBizForms=tPMobileBizFormService.getList(TPMobileBizFormEntity.class);
		return listTPMobileBizForms;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TPMobileBizFormEntity task = tPMobileBizFormService.get(TPMobileBizFormEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TPMobileBizFormEntity tPMobileBizForm, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TPMobileBizFormEntity>> failures = validator.validate(tPMobileBizForm);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tPMobileBizFormService.save(tPMobileBizForm);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tPMobileBizForm.getId();
		URI uri = uriBuilder.path("/rest/tPMobileBizFormController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TPMobileBizFormEntity tPMobileBizForm) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TPMobileBizFormEntity>> failures = validator.validate(tPMobileBizForm);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tPMobileBizFormService.saveOrUpdate(tPMobileBizForm);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") String id) {
		tPMobileBizFormService.deleteEntityById(TPMobileBizFormEntity.class, id);
	}
}
