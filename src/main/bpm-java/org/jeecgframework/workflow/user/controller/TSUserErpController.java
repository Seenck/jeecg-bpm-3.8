package org.jeecgframework.workflow.user.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
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
import org.jeecgframework.workflow.user.entity.TSUserErpEntity;
import org.jeecgframework.workflow.user.service.TSUserErpServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

/**   
 * @Title: Controller  
 * @Description: 用户第三方系统关系表
 * @author onlineGenerator
 * @date 2017-04-19 10:51:21
 * @version V1.0   
 *
 */
@Controller("tSUserErpController")
@RequestMapping("/tSUserErpController")
public class TSUserErpController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TSUserErpController.class);

	@Autowired
	private TSUserErpServiceI tSUserErpService;
	@Autowired
	private SystemService systemService;
	


	/**
	 * 用户第三方系统关系表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("workflow/user/tSUserErpList");
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
	public void datagrid(TSUserErpEntity tSUserErp,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSUserErpEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tSUserErp, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tSUserErpService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除用户第三方系统关系表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TSUserErpEntity tSUserErp, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tSUserErp = systemService.getEntity(TSUserErpEntity.class, tSUserErp.getId());
		message = "用户第三方系统关系表删除成功";
		try{
			tSUserErpService.delete(tSUserErp);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "用户第三方系统关系表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除用户第三方系统关系表
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "用户第三方系统关系表删除成功";
		try{
			for(String id:ids.split(",")){
				TSUserErpEntity tSUserErp = systemService.getEntity(TSUserErpEntity.class, 
				id
				);
				tSUserErpService.delete(tSUserErp);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "用户第三方系统关系表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加用户第三方系统关系表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TSUserErpEntity tSUserErp, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "用户第三方系统关系表添加成功";
		try{
			tSUserErpService.save(tSUserErp);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(BusinessException e){
			e.printStackTrace();
			message = e.getMessage();
//			j.setSuccess(false);
		}catch(Exception e){
			e.printStackTrace();
			message = "用户第三方系统关系表添加失败";
//			j.setSuccess(false);
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新用户第三方系统关系表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TSUserErpEntity tSUserErp, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "用户第三方系统关系表更新成功";
		TSUserErpEntity t = tSUserErpService.get(TSUserErpEntity.class, tSUserErp.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tSUserErp, t);
			tSUserErpService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(BusinessException e){
			e.printStackTrace();
			message = e.getMessage();
//			j.setSuccess(false);
		} catch (Exception e) {
			e.printStackTrace();
			message = "用户第三方系统关系表更新失败";
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 用户第三方系统关系表新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TSUserErpEntity tSUserErp, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tSUserErp.getId())) {
			tSUserErp = tSUserErpService.getEntity(TSUserErpEntity.class, tSUserErp.getId());
			req.setAttribute("tSUserErpPage", tSUserErp);
		}
		return new ModelAndView("workflow/user/tSUserErp-add");
	}
	/**
	 * 用户第三方系统关系表编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TSUserErpEntity tSUserErp, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tSUserErp.getId())) {
			tSUserErp = tSUserErpService.getEntity(TSUserErpEntity.class, tSUserErp.getId());
			req.setAttribute("tSUserErpPage", tSUserErp);
		}
		return new ModelAndView("workflow/user/tSUserErp-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tSUserErpController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
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
				List<TSUserErpEntity> listTSUserErpEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TSUserErpEntity.class,params);
				for (TSUserErpEntity tSUserErp : listTSUserErpEntitys) {
					try {
						tSUserErpService.save(tSUserErp);
					} catch (Exception e) {
						logger.error(e.getMessage());
					}
				}
				j.setMsg("文件导入完成！");
				
			} catch (BusinessException e) {
				j.setMsg("文件导入失败！"+e.getMessage());
				logger.error(ExceptionUtil.getExceptionMessage(e));
			}catch (Exception e) {
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
	
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TSUserErpEntity tSUserErp,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"BPM第三方系统用户");
    	modelMap.put(NormalExcelConstants.CLASS,TSUserErpEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("BPM第三方系统用户", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
    	"导出信息"));
    	modelMap.put(NormalExcelConstants.DATA_LIST,new ArrayList());
    	return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
}
