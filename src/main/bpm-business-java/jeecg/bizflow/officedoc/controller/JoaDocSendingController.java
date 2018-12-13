package jeecg.bizflow.officedoc.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import jeecg.bizflow.officedoc.entity.JoaDocSendingEntity;
import jeecg.bizflow.officedoc.service.JoaDocSendingServiceI;

/**   
 * @Title: Controller  
 * @Description: 公文发文
 * @author onlineGenerator
 * @date 2018-07-30 14:58:23
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/joaDocSendingController")
public class JoaDocSendingController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(JoaDocSendingController.class);

	@Autowired
	private JoaDocSendingServiceI joaDocSendingService;
	@Autowired
	private SystemService systemService;
	
	


	/**
	 * 公文发文列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("business/bizflow/officedoc/joaDocSendingList");
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
	public void datagrid(JoaDocSendingEntity joaDocSending,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(JoaDocSendingEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, joaDocSending, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.joaDocSendingService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除公文发文
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(JoaDocSendingEntity joaDocSending, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		joaDocSending = systemService.getEntity(JoaDocSendingEntity.class, joaDocSending.getId());
		message = "公文发文删除成功";
		try{
			joaDocSendingService.delete(joaDocSending);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "公文发文删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除公文发文
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "公文发文删除成功";
		try{
			for(String id:ids.split(",")){
				JoaDocSendingEntity joaDocSending = systemService.getEntity(JoaDocSendingEntity.class, 
				id
				);
				joaDocSendingService.delete(joaDocSending);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "公文发文删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加公文发文
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(JoaDocSendingEntity joaDocSending, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "公文发文添加成功";
		try{
			if(oConvertUtils.isEmpty(joaDocSending.getDocCode())){
				String sql = "select count(*) from joa_doc_sending";
				Long count = this.systemService.getCountForJdbc(sql);
				String docCode = "发文【"+DateUtils.formatDate(new Date(),"yyyy")+"】第"+String.format("%4d", count+1).replace(" ", "0")+"号";
				joaDocSending.setDocCode(docCode);
			}
			joaDocSendingService.save(joaDocSending);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "公文发文添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新公文发文
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(JoaDocSendingEntity joaDocSending, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "公文发文更新成功";
		JoaDocSendingEntity t = joaDocSendingService.get(JoaDocSendingEntity.class, joaDocSending.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(joaDocSending, t);
			joaDocSendingService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "公文发文更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 公文发文新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(JoaDocSendingEntity joaDocSending, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(joaDocSending.getId())) {
			joaDocSending = joaDocSendingService.getEntity(JoaDocSendingEntity.class, joaDocSending.getId());
			req.setAttribute("joaDocSendingPage", joaDocSending);
		}
		TSUser user = ResourceUtil.getSessionUser();
		req.setAttribute("user", user.getUserName());
		req.setAttribute("currdate",new Date());
		return new ModelAndView("business/bizflow/officedoc/joaDocSending-add");
	}
	/**
	 * 公文发文编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(JoaDocSendingEntity joaDocSending, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(joaDocSending.getId())) {
			joaDocSending = joaDocSendingService.getEntity(JoaDocSendingEntity.class, joaDocSending.getId());
			req.setAttribute("joaDocSendingPage", joaDocSending);
		}
		return new ModelAndView("business/bizflow/officedoc/joaDocSending-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","joaDocSendingController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(JoaDocSendingEntity joaDocSending,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(JoaDocSendingEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, joaDocSending, request.getParameterMap());
		List<JoaDocSendingEntity> joaDocSendings = this.joaDocSendingService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"公文发文");
		modelMap.put(NormalExcelConstants.CLASS,JoaDocSendingEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("公文发文列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,joaDocSendings);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(JoaDocSendingEntity joaDocSending,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"公文发文");
    	modelMap.put(NormalExcelConstants.CLASS,JoaDocSendingEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("公文发文列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
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
				List<JoaDocSendingEntity> listJoaDocSendingEntitys = ExcelImportUtil.importExcel(file.getInputStream(),JoaDocSendingEntity.class,params);
				for (JoaDocSendingEntity joaDocSending : listJoaDocSendingEntitys) {
					joaDocSendingService.save(joaDocSending);
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
	
	
}
