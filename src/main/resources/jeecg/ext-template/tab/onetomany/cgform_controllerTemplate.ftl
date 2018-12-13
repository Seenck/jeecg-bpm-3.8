<#if packageStyle == "service">
package ${bussiPackage}.${entityPackage}.controller;
import ${bussiPackage}.${entityPackage}.entity.${entityName}Entity;
import ${bussiPackage}.${entityPackage}.service.${entityName}ServiceI;
import ${bussiPackage}.${entityPackage}.page.${entityName}Page;
<#list subTab as sub>
import ${bussiPackage}.${sub.entityPackage}.entity.${sub.entityName}Entity;
</#list>
<#else>
package ${bussiPackage}.controller.${entityPackage};
import ${bussiPackage}.entity.${entityPackage}.${entityName}Entity;
import ${bussiPackage}.service.${entityPackage}.${entityName}ServiceI;
import ${bussiPackage}.page.${entityPackage}.${entityName}Page;
<#list subTab as sub>
import ${bussiPackage}.entity.${sub.entityPackage}.${sub.entityName}Entity;
</#list>
</#if>
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.jeecgframework.jwt.util.GsonUtil;
import org.jeecgframework.jwt.util.ResponseMessage;
import org.jeecgframework.jwt.util.Result;
import org.apache.commons.lang3.StringUtils;
import com.alibaba.fastjson.JSONArray;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.io.IOException;
import java.util.Map;
<#if cgformConfig.supportRestful?? && cgformConfig.supportRestful == "1">
<#-- restful 通用方法生成 -->
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.jwt.util.GsonUtil;
import org.jeecgframework.jwt.util.ResponseMessage;
import org.jeecgframework.jwt.util.Result;
import com.alibaba.fastjson.JSONArray;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.jeecgframework.core.beanvalidator.BeanValidators;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.net.URI;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;
<#-- restful 通用方法生成 -->

<#-- swagger api  start -->
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
<#-- swagger api end -->
</#if>
<#-- 列为文件类型的文件代码生成 -->
<#assign fileFlag = false />
<#list columns as filePo>
<#-- update--begin--author:gj_shaojc date:20180302 for：TASK #2551 【bug】网友问题验证确认 -->
	<#if filePo.showType=='file'  || filePo.showType == 'image'>
<#-- update--end--author:gj_shaojc date:20180302 for：TASK #2551 【bug】网友问题验证确认 -->
		<#assign fileFlag = true />
	</#if>
</#list>

<#if fileFlag==true>
import org.jeecgframework.web.cgform.entity.upload.CgUploadEntity;
import org.jeecgframework.web.cgform.service.config.CgFormFieldServiceI;
import java.util.HashMap;
</#if>
<#-- 列为文件类型的文件代码生成 -->
/**   
 * @Title: Controller
 * @Description: ${ftl_description}
 * @author onlineGenerator
 * @date ${ftl_create_time}
 * @version V1.0   
 *
 */
 <#if cgformConfig.supportRestful?? && cgformConfig.supportRestful == "1">
 <#-- update--begin--author:zhangjiaqiang date:20171031 for:API 注解 start -->
@Api(value="${entityName}",description="${ftl_description}",tags="${entityName?uncap_first}Controller")
<#-- update--end--author:zhangjiaqiang date:20171031 for:API 注解 start -->
</#if>
@Controller
@RequestMapping("/${entityName?uncap_first}Controller")
public class ${entityName}Controller extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(${entityName}Controller.class);

	@Autowired
	private ${entityName}ServiceI ${entityName?uncap_first}Service;
	@Autowired
	private SystemService systemService;
	<#if cgformConfig.supportRestful?? && cgformConfig.supportRestful == "1">
	@Autowired
	private Validator validator;
	</#if>
	<#-- 列为文件类型的文件代码生成 -->
	<#if fileFlag==true>
	@Autowired
	private CgFormFieldServiceI cgFormFieldService;
	</#if>
	<#-- 列为文件类型的文件代码生成 -->

	/**
	 * ${ftl_description}列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("${bussiPackage?replace(".","/")}/${entityPackage}/${entityName?uncap_first}List");
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
	public void datagrid(${entityName}Entity ${entityName?uncap_first},HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(${entityName}Entity.class, dataGrid);
		//查询条件组装器
		<#-- update--begin--author:jiaqiankun date:20180709 for：TASK #2928 代码生成器，为什么要单独生成范围查询的逻辑，查询过滤器不是有这个功能吗 -->
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, ${entityName?uncap_first}, request.getParameterMap());
		<#-- update--end--author:jiaqiankun date:20180709 for：TASK #2928 代码生成器，为什么要单独生成范围查询的逻辑，查询过滤器不是有这个功能吗 -->
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.${entityName?uncap_first}Service.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除${ftl_description}
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(${entityName}Entity ${entityName?uncap_first}, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		${entityName?uncap_first} = systemService.getEntity(${entityName}Entity.class, ${entityName?uncap_first}.getId());
		String message = "${ftl_description}删除成功";
		try{
			${entityName?uncap_first}Service.delMain(${entityName?uncap_first});
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "${ftl_description}删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除${ftl_description}
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		String message = "${ftl_description}删除成功";
		try{
			for(String id:ids.split(",")){
				${entityName}Entity ${entityName?uncap_first} = systemService.getEntity(${entityName}Entity.class,
				<#if cgformConfig.cgFormHead.jformPkType?if_exists?html == "UUID">
				id
				<#elseif cgformConfig.cgFormHead.jformPkType?if_exists?html == "NATIVE">
				Integer.parseInt(id)
				<#elseif cgformConfig.cgFormHead.jformPkType?if_exists?html == "SEQUENCE">
				Integer.parseInt(id)
				<#else>
				id
				</#if>
				);
				${entityName?uncap_first}Service.delMain(${entityName?uncap_first});
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "${ftl_description}删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加${ftl_description}
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(${entityName}Entity ${entityName?uncap_first},${entityName}Page ${entityName?uncap_first}Page, HttpServletRequest request) {
		<#list subTab as sub>
		List<${sub.entityName}Entity> ${sub.entityName?uncap_first}List =  ${entityName?uncap_first}Page.get${sub.entityName}List();
		</#list>
		AjaxJson j = new AjaxJson();
		String message = "添加成功";
		try{
			${entityName?uncap_first}Service.addMain(${entityName?uncap_first}, <#list subTab as sub>${sub.entityName?uncap_first}List<#if sub_has_next>,</#if></#list>);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "${ftl_description}添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		<#-- 列为文件类型的文件代码生成 -->
		<#if fileFlag==true>
		j.setObj(${entityName?uncap_first});
		</#if>
		<#-- 列为文件类型的文件代码生成 -->
		return j;
	}
	/**
	 * 更新${ftl_description}
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(${entityName}Entity ${entityName?uncap_first},${entityName}Page ${entityName?uncap_first}Page, HttpServletRequest request) {
		<#list subTab as sub>
		List<${sub.entityName}Entity> ${sub.entityName?uncap_first}List =  ${entityName?uncap_first}Page.get${sub.entityName}List();
		</#list>
		AjaxJson j = new AjaxJson();
		String message = "更新成功";
		try{
			${entityName?uncap_first}Service.updateMain(${entityName?uncap_first}, <#list subTab as sub>${sub.entityName?uncap_first}List<#if sub_has_next>,</#if></#list>);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "更新${ftl_description}失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * ${ftl_description}新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAddOrUpdate")
	public ModelAndView goAddOrUpdate(${entityName}Entity ${entityName?uncap_first}, HttpServletRequest req) {
		//跳转主页面
		String id = req.getParameter("id");
		req.setAttribute("mainId",id);
		req.setAttribute("load", req.getParameter("load"));
		return new ModelAndView("${bussiPackage?replace(".","/")}/${entityPackage}/${entityName?uncap_first}-add");
	}
	
	/**
	 * ${ftl_description}新增编辑字段页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "mainPage")
	public ModelAndView mainPage(${entityName}Entity ${entityName?uncap_first}, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(${entityName?uncap_first}.getId())) {
			${entityName?uncap_first} = ${entityName?uncap_first}Service.getEntity(${entityName}Entity.class, ${entityName?uncap_first}.getId());
			req.setAttribute("${entityName?uncap_first}Page", ${entityName?uncap_first});
		}
		return new ModelAndView("${bussiPackage?replace(".","/")}/${entityPackage}/${entityName?uncap_first}-update");
	}
	
	
	<#list subTab as sub>
	/**
	 * 加载明细列表[${sub.ftlDescription}]
	 * 
	 * @return
	 */
	@RequestMapping(params = "${sub.entityName?uncap_first}List")
	public ModelAndView ${sub.entityName?uncap_first}List(${entityName}Entity ${entityName?uncap_first}, HttpServletRequest req) {
	
		//===================================================================================
		//获取参数
		<#list sub.foreignKeys as key>
		    <#if key?lower_case?index_of("${jeecg_table_id}")!=-1>
		Object ${jeecg_table_id}${sub_index} = ${entityName?uncap_first}.get${jeecg_table_id?cap_first}();
		    <#else>
		Object ${key?uncap_first}${sub_index} = ${entityName?uncap_first}.get${key}();
		    </#if>
	    </#list>
		//===================================================================================
		//查询-${sub.ftlDescription}
	    String hql${sub_index} = "from ${sub.entityName}Entity where 1 = 1<#list sub.foreignKeys as key> AND ${key?uncap_first} = ? </#list>";
	    try{
	    	List<${sub.entityName}Entity> ${sub.entityName?uncap_first}EntityList = systemService.findHql(hql${sub_index},<#list sub.foreignKeys as key><#if key?lower_case?index_of("${jeecg_table_id}")!=-1>${jeecg_table_id}${sub_index}<#else>${key?uncap_first}${sub_index}</#if><#if key_has_next>,</#if></#list>);
			req.setAttribute("${sub.entityName?uncap_first}List", ${sub.entityName?uncap_first}EntityList);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		return new ModelAndView("${bussiPackage?replace(".","/")}/${sub.entityPackage}/${sub.entityName?uncap_first}List");
	}
	</#list>

    /**
    * 导出excel
    *
    * @param request
    * @param response
    */
    @RequestMapping(params = "exportXls")
    public String exportXls(${entityName}Entity ${entityName?uncap_first},HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid,ModelMap map) {
    	CriteriaQuery cq = new CriteriaQuery(${entityName}Entity.class, dataGrid);
    	//查询条件组装器
    	org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, ${entityName?uncap_first});
    	try{
    	//自定义追加查询条件
    	}catch (Exception e) {
    		throw new BusinessException(e.getMessage());
    	}
    	cq.add();
    	List<${entityName}Entity> list=this.${entityName?uncap_first}Service.getListByCriteriaQuery(cq, false);
    	List<${entityName}Page> pageList=new ArrayList<${entityName}Page>();
        if(list!=null&&list.size()>0){
        	for(${entityName}Entity entity:list){
        		try{
        		${entityName}Page page=new ${entityName}Page();
        		   MyBeanUtils.copyBeanNotNull2Bean(entity,page);
	        <#list subTab as sub>
			<#list sub.foreignKeys as key>
				<#if key?lower_case?index_of("${jeecg_table_id}")!=-1>
            	    Object ${jeecg_table_id}${sub_index} = entity.get${jeecg_table_id?cap_first}();
				<#else>
           		    Object ${key?uncap_first}${sub_index} = entity.get${key}();
				</#if>
			</#list>
				    String hql${sub_index} = "from ${sub.entityName}Entity where 1 = 1<#list sub.foreignKeys as key> AND ${key?uncap_first} = ? </#list>";
        	        List<${sub.entityName}Entity> ${sub.entityName?uncap_first}EntityList = systemService.findHql(hql${sub_index},<#list sub.foreignKeys as key><#if key?lower_case?index_of("${jeecg_table_id}")!=-1>${jeecg_table_id}${sub_index}<#else>${key?uncap_first}${sub_index}</#if><#if key_has_next>,</#if></#list>);
            		page.set${sub.entityName}List(${sub.entityName?uncap_first}EntityList);
			</#list>
            		pageList.add(page);
            	}catch(Exception e){
            		logger.info(e.getMessage());
            	}
            }
        }
        map.put(NormalExcelConstants.FILE_NAME,"${ftl_description}");
        map.put(NormalExcelConstants.CLASS,${entityName}Page.class);
        map.put(NormalExcelConstants.PARAMS,new ExportParams("${ftl_description}列表", "导出人:Jeecg",
            "导出信息"));
        map.put(NormalExcelConstants.DATA_LIST,pageList);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

    /**
	 * 通过excel导入数据
	 * @param request
	 * @param
	 * @return
	 */
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
			params.setHeadRows(2);
			params.setNeedSave(true);
			try {
				List<${entityName}Page> list =  ExcelImportUtil.importExcel(file.getInputStream(), ${entityName}Page.class, params);
				${entityName}Entity entity1=null;
				for (${entityName}Page page : list) {
					entity1=new ${entityName}Entity();
					MyBeanUtils.copyBeanNotNull2Bean(page,entity1);
		            ${entityName?uncap_first}Service.addMain(entity1, <#list subTab as sub>page.get${sub.entityName}List()<#if sub_has_next>,</#if></#list>);
				}
				j.setMsg("文件导入成功！");
			} catch (Exception e) {
				j.setMsg("文件导入失败！");
				logger.error(e.getMessage());
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
	/**
	* 导出excel 使模板
	*/
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(ModelMap map) {
		map.put(NormalExcelConstants.FILE_NAME,"${ftl_description}");
		map.put(NormalExcelConstants.CLASS,${entityName}Page.class);
		map.put(NormalExcelConstants.PARAMS,new ExportParams("${ftl_description}列表", "导出人:"+ ResourceUtil.getSessionUser().getRealName(),
		"导出信息"));
		map.put(NormalExcelConstants.DATA_LIST,new ArrayList());
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	* 导入功能跳转
	*
	* @return
	*/
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name", "${entityName?uncap_first}Controller");
		return new ModelAndView("common/upload/pub_excel_upload");
	}

		<#list buttons as btn>
 	<#if btn.buttonStyle =='button' && btn.optType=='action'>
 	/**
	 * 自定义按钮-sql增强-${btn.buttonName}
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "do${btn.buttonCode?cap_first}")
	@ResponseBody
	public AjaxJson do${btn.buttonCode?cap_first}(${entityName}Entity ${entityName?uncap_first}, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		<#-- update--begin--author:zhoujf date:20180413 for:生成报错修正-->
		String message = "${btn.buttonName}成功";
		<#-- update--end--author:zhoujf date:20180413 for:生成报错修正-->
		${entityName}Entity t = ${entityName?uncap_first}Service.get(${entityName}Entity.class, ${entityName?uncap_first}.getId());
		try{
			${entityName?uncap_first}Service.do${btn.buttonCode?cap_first}Sql(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "${btn.buttonName}失败";
		}
		j.setMsg(message);
		return j;
	}
 	</#if>
 	</#list> 
 	
 	<#if cgformConfig.supportRestful?? && cgformConfig.supportRestful == "1">
 	<#-- restful 通用方法生成 -->
 	<#-- update--begin--author:zhangjiaqiang date:20171113 for:TASK #2415 【restful接口模板】模板再次改造，封装了通用了返回结果，加了必要校验 -->
 	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	<#-- update--begin--author:zhangjiaqiang date:20171031 for:TASK #2397 【新功能】代码生成器模板修改，追加swagger-ui注解 -->
	@ApiOperation(value="${ftl_description}列表信息",produces="application/json",httpMethod="GET")
	<#-- update--end--author:zhangjiaqiang date:20171031 for:TASK #2397 【新功能】代码生成器模板修改，追加swagger-ui注解 -->
	<#-- update--begin--author:zhangjiaqiang date:20171031 for:TASK #2400 【功能不足】一对多，restful接口不足，目前只返回主表的数据，应该把主子表的数据一起返回 -->
	public ResponseMessage<List<${entityName}Page>> list() {
		List<${entityName}Entity> list= ${entityName?uncap_first}Service.getList(${entityName}Entity.class);
    	List<${entityName}Page> pageList=new ArrayList<${entityName}Page>();
        if(list!=null&&list.size()>0){
        	for(${entityName}Entity entity:list){
        		try{
        			${entityName}Page page=new ${entityName}Page();
        		   MyBeanUtils.copyBeanNotNull2Bean(entity,page);
            	    <#list subTab as sub>
					    <#list sub.foreignKeys as key>
					    	<#if key?lower_case?index_of("${jeecg_table_id}")!=-1>
					Object ${jeecg_table_id}${sub_index} = entity.get${jeecg_table_id?cap_first}();
					    	<#else>
					Object ${key?uncap_first}${sub_index} = entity.get${key}();
					   	 	</#if>
					    </#list>
				    </#list>
				    <#list subTab as sub>
				     String hql${sub_index} = "from ${sub.entityName}Entity where 1 = 1<#list sub.foreignKeys as key> AND ${key?uncap_first} = ? </#list>";
	    			List<${sub.entityName}Entity> ${sub.entityName?uncap_first}OldList = this.${entityName?uncap_first}Service.findHql(hql${sub_index},<#list sub.foreignKeys as key><#if key?lower_case?index_of("${jeecg_table_id}")!=-1>${jeecg_table_id}${sub_index}<#else>${key?uncap_first}${sub_index}</#if><#if key_has_next>,</#if></#list>);
            		page.set${sub.entityName}List(${sub.entityName?uncap_first}OldList);
            		</#list>
            		pageList.add(page);
            	}catch(Exception e){
            		logger.info(e.getMessage());
            	}
            }
        }
		return Result.success(pageList);
		<#-- update--end--author:zhangjiaqiang date:20171031 for:TASK #2400 【功能不足】一对多，restful接口不足，目前只返回主表的数据，应该把主子表的数据一起返回 -->
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	<#-- update--begin--author:zhangjiaqiang date:20171031 for:TASK #2397 【新功能】代码生成器模板修改，追加swagger-ui注解 -->
	@ApiOperation(value="根据ID获取${ftl_description}信息",notes="根据ID获取${ftl_description}信息",httpMethod="GET",produces="application/json")
	<#-- update--end--author:zhangjiaqiang date:20171031 for:TASK #2397 【新功能】代码生成器模板修改，追加swagger-ui注解 -->
	public ResponseMessage<?> get(@ApiParam(required=true,name="id",value="ID")@PathVariable("id") String id) {
		${entityName}Entity task = ${entityName?uncap_first}Service.get(${entityName}Entity.class, id);
		if (task == null) {
			return Result.error("根据ID获取${ftl_description}信息为空");
		}
		${entityName}Page page = new ${entityName}Page();
		try {
			MyBeanUtils.copyBeanNotNull2Bean(task, page);
			<#list subTab as sub>
				<#list sub.foreignKeys as key>
				   <#if key?lower_case?index_of("${jeecg_table_id}")!=-1>
				Object ${jeecg_table_id}${sub_index} = task.get${jeecg_table_id?cap_first}();
				   <#else>
				Object ${key?uncap_first}${sub_index} = task.get${key}();
				   </#if>
				</#list>
			</#list>
			<#list subTab as sub>
		    String hql${sub_index} = "from ${sub.entityName}Entity where 1 = 1<#list sub.foreignKeys as key> AND ${key?uncap_first} = ? </#list>";
			List<${sub.entityName}Entity> ${sub.entityName?uncap_first}OldList = this.${entityName?uncap_first}Service.findHql(hql${sub_index},<#list sub.foreignKeys as key><#if key?lower_case?index_of("${jeecg_table_id}")!=-1>${jeecg_table_id}${sub_index}<#else>${key?uncap_first}${sub_index}</#if><#if key_has_next>,</#if></#list>);
    		page.set${sub.entityName}List(${sub.entityName?uncap_first}OldList);
    		</#list>
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Result.success(page);
	}
 	
 	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	<#-- update--begin--author:zhangjiaqiang date:20171031 for:TASK #2397 【新功能】代码生成器模板修改，追加swagger-ui注解 -->
	@ApiOperation(value="创建${ftl_description}")
	public ResponseMessage<?> create(@ApiParam(name="${ftl_description}对象")@RequestBody ${entityName}Page ${entityName?uncap_first}Page, UriComponentsBuilder uriBuilder) {
		<#-- update--end--author:zhangjiaqiang date:20171031 for:TASK #2397 【新功能】代码生成器模板修改，追加swagger-ui注解 -->
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<${entityName}Page>> failures = validator.validate(${entityName?uncap_first}Page);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		<#list subTab as sub>
		List<${sub.entityName}Entity> ${sub.entityName?uncap_first}List =  ${entityName?uncap_first}Page.get${sub.entityName}List();
		</#list>
		
		${entityName}Entity ${entityName?uncap_first} = new ${entityName}Entity();
		try{
			MyBeanUtils.copyBeanNotNull2Bean(${entityName?uncap_first}Page,${entityName?uncap_first});
		}catch(Exception e){
            logger.info(e.getMessage());
            return Result.error("保存${ftl_description}失败");
        }
		${entityName?uncap_first}Service.addMain(${entityName?uncap_first}, <#list subTab as sub>${sub.entityName?uncap_first}List<#if sub_has_next>,</#if></#list>);

		return Result.success(${entityName?uncap_first});
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	<#-- update--begin--author:zhangjiaqiang date:20171031 for:TASK #2397 【新功能】代码生成器模板修改，追加swagger-ui注解 -->
	@ApiOperation(value="更新${ftl_description}",notes="更新${ftl_description}")
	<#-- update--end--author:zhangjiaqiang date:20171031 for:TASK #2397 【新功能】代码生成器模板修改，追加swagger-ui注解 -->
		<#-- update--begin--author:zhangjiaqiang date:20171102 for: TASK #2400 【功能不足】一对多，restful接口不足，目前只返回主表的数据，应该把主子表的数据一起返回-->
	public ResponseMessage<?> update(@RequestBody ${entityName}Page ${entityName?uncap_first}Page) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<${entityName}Page>> failures = validator.validate(${entityName?uncap_first}Page);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		<#list subTab as sub>
		List<${sub.entityName}Entity> ${sub.entityName?uncap_first}List =  ${entityName?uncap_first}Page.get${sub.entityName}List();
		</#list>
		
		${entityName}Entity ${entityName?uncap_first} = new ${entityName}Entity();
		try{
			MyBeanUtils.copyBeanNotNull2Bean(${entityName?uncap_first}Page,${entityName?uncap_first});
		}catch(Exception e){
            logger.info(e.getMessage());
            return Result.error("${ftl_description}更新失败");
        }
		${entityName?uncap_first}Service.updateMain(${entityName?uncap_first}, <#list subTab as sub>${sub.entityName?uncap_first}List<#if sub_has_next>,</#if></#list>);

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return Result.success();
	}
	<#-- update--end--author:zhangjiaqiang date:20171102 for: TASK #2400 【功能不足】一对多，restful接口不足，目前只返回主表的数据，应该把主子表的数据一起返回-->
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	<#-- update--begin--author:zhangjiaqiang date:20171031 for:TASK #2397 【新功能】代码生成器模板修改，追加swagger-ui注解 -->
	@ApiOperation(value="删除${ftl_description}")
	<#-- update--begin--author:zhangjiaqiang date:20171031 for:TASK #2397 【新功能】代码生成器模板修改，追加swagger-ui注解 -->
	public ResponseMessage<?> delete(@ApiParam(name="id",value="ID",required=true)@PathVariable("id") String id) {
		logger.info("delete[{}]" , id);
		// 验证
		if (StringUtils.isEmpty(id)) {
			return Result.error("ID不能为空");
		}
		try {
			${entityName}Entity ${entityName?uncap_first} = ${entityName?uncap_first}Service.get(${entityName}Entity.class, id);
			${entityName?uncap_first}Service.delMain(${entityName?uncap_first});
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("${ftl_description}删除失败");
		}

		return Result.success();
	}
	<#-- update--end--author:zhangjiaqiang date:20171113 for:TASK #2415 【restful接口模板】模板再次改造，封装了通用了返回结果，加了必要校验 -->
	<#-- restful 通用方法生成 -->
	</#if>
	
	<#-- 列为文件类型的文件代码生成 -->
	<#if fileFlag==true>
	/**
	 * 获取文件附件信息
	 * 
	 * @param id ${entityName?uncap_first}主键id
	 */
	@RequestMapping(params = "getFiles")
	@ResponseBody
	public AjaxJson getFiles(String id){
		List<CgUploadEntity> uploadBeans = cgFormFieldService.findByProperty(CgUploadEntity.class, "cgformId", id);
		List<Map<String,Object>> files = new ArrayList<Map<String,Object>>(0);
		for(CgUploadEntity b:uploadBeans){
			String title = b.getAttachmenttitle();//附件名
			String fileKey = b.getId();//附件主键
			String path = b.getRealpath();//附件路径
			String field = b.getCgformField();//表单中作为附件控件的字段
			Map<String, Object> file = new HashMap<String, Object>();
			file.put("title", title);
			file.put("fileKey", fileKey);
			file.put("path", path);
			file.put("field", field==null?"":field);
			files.add(file);
		}
		AjaxJson j = new AjaxJson();
		j.setObj(files);
		return j;
	}
	</#if>
	<#-- 列为文件类型的文件代码生成 -->
}
