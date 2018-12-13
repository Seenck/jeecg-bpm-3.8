package org.jeecgframework.workflow.user.controller;
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

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Property;
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
import org.jeecgframework.web.system.pojo.base.TSRoleUser;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.pojo.base.TSUserOrg;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.workflow.user.entity.TSUserAgentEntity;
import org.jeecgframework.workflow.user.service.TSUserAgentServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
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
 * @Description: 用户代理人设置
 * @author onlineGenerator
 * @date 2017-03-06 11:21:34
 * @version V1.0   
 *
 */
@Controller("tSUserAgentController")
@RequestMapping("/tSUserAgentController")
public class TSUserAgentController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TSUserAgentController.class);

	@Autowired
	private TSUserAgentServiceI tSUserAgentService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 用户代理人设置列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("workflow/user/tSUserAgentList");
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
	public void datagrid(TSUserAgentEntity tSUserAgent,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSUserAgentEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tSUserAgent, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tSUserAgentService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除用户代理人设置
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TSUserAgentEntity tSUserAgent, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tSUserAgent = systemService.getEntity(TSUserAgentEntity.class, tSUserAgent.getId());
		message = "用户代理人设置删除成功";
		try{
			tSUserAgentService.delete(tSUserAgent);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "用户代理人设置删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除用户代理人设置
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "用户代理人设置删除成功";
		try{
			for(String id:ids.split(",")){
				TSUserAgentEntity tSUserAgent = systemService.getEntity(TSUserAgentEntity.class, 
				id
				);
				tSUserAgentService.delete(tSUserAgent);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "用户代理人设置删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加用户代理人设置
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TSUserAgentEntity tSUserAgent, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "用户代理人设置添加成功";
		try{
			tSUserAgentService.save(tSUserAgent);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "用户代理人设置添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新用户代理人设置
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TSUserAgentEntity tSUserAgent, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "用户代理人设置更新成功";
		TSUserAgentEntity t = tSUserAgentService.get(TSUserAgentEntity.class, tSUserAgent.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tSUserAgent, t);
			tSUserAgentService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "用户代理人设置更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新用户代理人设置
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAddOrUpdate")
	@ResponseBody
	public AjaxJson doAddOrUpdate(TSUserAgentEntity tSUserAgent, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "用户代理人设置成功";
		try {
			if(StringUtils.isEmpty(tSUserAgent.getId())){
				tSUserAgentService.save(tSUserAgent);
			}else{
				TSUserAgentEntity t = tSUserAgentService.get(TSUserAgentEntity.class, tSUserAgent.getId());
				MyBeanUtils.copyBeanNotNull2Bean(tSUserAgent, t);
				tSUserAgentService.saveOrUpdate(t);
			}
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "用户代理人设置失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 用户代理人设置新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TSUserAgentEntity tSUserAgent, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tSUserAgent.getId())) {
			tSUserAgent = tSUserAgentService.getEntity(TSUserAgentEntity.class, tSUserAgent.getId());
			req.setAttribute("tSUserAgentPage", tSUserAgent);
		}
		return new ModelAndView("workflow/user/tSUserAgent-add");
	}
	/**
	 * 用户代理人设置编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TSUserAgentEntity tSUserAgent, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tSUserAgent.getId())) {
			tSUserAgent = tSUserAgentService.getEntity(TSUserAgentEntity.class, tSUserAgent.getId());
			req.setAttribute("tSUserAgentPage", tSUserAgent);
		}
		return new ModelAndView("workflow/user/tSUserAgent-update");
	}
	
	/**
	 * 用户代理人设置设置页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAgentUser")
	public ModelAndView goAgentUser(TSUserAgentEntity tSUserAgent, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tSUserAgent.getUserName())) {
			TSUserAgentEntity tSUserAgentEntity = tSUserAgentService.findUniqueByProperty(TSUserAgentEntity.class, "userName",tSUserAgent.getUserName());
			if(tSUserAgentEntity!=null){
				req.setAttribute("tSUserAgentPage", tSUserAgentEntity);
			}else{
				req.setAttribute("tSUserAgentPage", tSUserAgent);
			}
		}
		return new ModelAndView("workflow/user/tSUserAgent");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tSUserAgentController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TSUserAgentEntity tSUserAgent,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TSUserAgentEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tSUserAgent, request.getParameterMap());
		List<TSUserAgentEntity> tSUserAgents = this.tSUserAgentService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"用户代理人设置");
		modelMap.put(NormalExcelConstants.CLASS,TSUserAgentEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("用户代理人设置列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tSUserAgents);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TSUserAgentEntity tSUserAgent,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"用户代理人设置");
    	modelMap.put(NormalExcelConstants.CLASS,TSUserAgentEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("用户代理人设置列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
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
				List<TSUserAgentEntity> listTSUserAgentEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TSUserAgentEntity.class,params);
				for (TSUserAgentEntity tSUserAgent : listTSUserAgentEntitys) {
					tSUserAgentService.save(tSUserAgent);
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
	public List<TSUserAgentEntity> list() {
		List<TSUserAgentEntity> listTSUserAgents=tSUserAgentService.getList(TSUserAgentEntity.class);
		return listTSUserAgents;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TSUserAgentEntity task = tSUserAgentService.get(TSUserAgentEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TSUserAgentEntity tSUserAgent, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TSUserAgentEntity>> failures = validator.validate(tSUserAgent);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tSUserAgentService.save(tSUserAgent);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tSUserAgent.getId();
		URI uri = uriBuilder.path("/rest/tSUserAgentController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TSUserAgentEntity tSUserAgent) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TSUserAgentEntity>> failures = validator.validate(tSUserAgent);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tSUserAgentService.saveOrUpdate(tSUserAgent);
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
		tSUserAgentService.deleteEntityById(TSUserAgentEntity.class, id);
	}
	
	/**
	 * 选择用户跳转页面
	 *
	 * @return
	 */
	@RequestMapping(params = "userSelect")
	public String userSelect() {
		return "workflow/user/userSelect";
	}
	
	/**
	 * easyuiAJAX用户列表请求数据 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "userdatagrid")
	public void userdatagrid(TSUser user,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TSUser.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, user);
        Short[] userstate = new Short[]{Globals.User_Normal, Globals.User_ADMIN, Globals.User_Forbidden};
        cq.in("status", userstate);
        cq.eq("deleteFlag", Globals.Delete_Normal);
        cq.eq("activitiSync", new Short("1"));
        
        String orgIds = request.getParameter("orgIds");
        List<String> orgIdList = extractIdListByComma(orgIds);
        // 获取 当前组织机构的用户信息
        if (!CollectionUtils.isEmpty(orgIdList)) {
            CriteriaQuery subCq = new CriteriaQuery(TSUserOrg.class);
            subCq.setProjection(Property.forName("tsUser.id"));
            subCq.in("tsDepart.id", orgIdList.toArray());
            subCq.add();

            cq.add(Property.forName("id").in(subCq.getDetachedCriteria()));
        }

        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        List<TSUser> cfeList = new ArrayList<TSUser>();
        for (Object o : dataGrid.getResults()) {
            if (o instanceof TSUser) {
                TSUser cfe = (TSUser) o;
                if (cfe.getId() != null && !"".equals(cfe.getId())) {
                    List<TSRoleUser> roleUser = systemService.findByProperty(TSRoleUser.class, "TSUser.id", cfe.getId());
                    if (roleUser.size() > 0) {
                        String roleName = "";
                        for (TSRoleUser ru : roleUser) {
                            roleName += ru.getTSRole().getRoleName() + ",";
                        }
                        roleName = roleName.substring(0, roleName.length() - 1);
                        cfe.setUserKey(roleName);
                    }
                }
                cfeList.add(cfe);
            }
        }
        TagUtil.datagrid(response, dataGrid);
    }
}
