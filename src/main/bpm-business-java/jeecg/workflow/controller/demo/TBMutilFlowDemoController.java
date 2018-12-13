package jeecg.workflow.controller.demo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Validator;

import jeecg.workflow.entity.demo.TBMutilFlowDemoEntity;
import jeecg.workflow.service.demo.TBMutilFlowDemoServiceI;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.workflow.common.WorkFlowGlobals;
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

/**   
 * @Title: 出差借款多流程审批demo  
 * @Description: 出差借款多流程审批
 * @author onlineGenerator
 * @date 2018-07-07 15:45:43
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tBMutilFlowDemoController")
public class TBMutilFlowDemoController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(TBMutilFlowDemoController.class);

	@Autowired
	private TBMutilFlowDemoServiceI tBMutilFlowDemoService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 出差借款多流程审批列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("business/demo/tBMutilFlowDemoList");
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
	public void datagrid(TBMutilFlowDemoEntity tBMutilFlowDemo,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TBMutilFlowDemoEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBMutilFlowDemo, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tBMutilFlowDemoService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除出差借款多流程审批
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TBMutilFlowDemoEntity tBMutilFlowDemo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tBMutilFlowDemo = systemService.getEntity(TBMutilFlowDemoEntity.class, tBMutilFlowDemo.getId());
		message = "出差借款多流程审批删除成功";
		try{
			tBMutilFlowDemoService.delete(tBMutilFlowDemo);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "出差借款多流程审批删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除出差借款多流程审批
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "出差借款多流程审批删除成功";
		try{
			for(String id:ids.split(",")){
				TBMutilFlowDemoEntity tBMutilFlowDemo = systemService.getEntity(TBMutilFlowDemoEntity.class, 
				id
				);
				tBMutilFlowDemoService.delete(tBMutilFlowDemo);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "出差借款多流程审批删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加出差借款多流程审批
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TBMutilFlowDemoEntity tBMutilFlowDemo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "出差借款多流程审批添加成功";
		try{
			tBMutilFlowDemo.setBpmStatus(WorkFlowGlobals.BPM_BUS_STATUS_1);
			tBMutilFlowDemo.setBorrowBpmStatus(WorkFlowGlobals.BPM_BUS_STATUS_1);
			tBMutilFlowDemoService.save(tBMutilFlowDemo);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "出差借款多流程审批添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新出差借款多流程审批
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TBMutilFlowDemoEntity tBMutilFlowDemo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "出差借款多流程审批更新成功";
		TBMutilFlowDemoEntity t = tBMutilFlowDemoService.get(TBMutilFlowDemoEntity.class, tBMutilFlowDemo.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tBMutilFlowDemo, t);
			tBMutilFlowDemoService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "出差借款多流程审批更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 出差借款多流程审批新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TBMutilFlowDemoEntity tBMutilFlowDemo, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBMutilFlowDemo.getId())) {
			tBMutilFlowDemo = tBMutilFlowDemoService.getEntity(TBMutilFlowDemoEntity.class, tBMutilFlowDemo.getId());
			req.setAttribute("tBMutilFlowDemoPage", tBMutilFlowDemo);
		}
		return new ModelAndView("business/demo/tBMutilFlowDemo-add");
	}
	/**
	 * 出差借款多流程审批编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TBMutilFlowDemoEntity tBMutilFlowDemo, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBMutilFlowDemo.getId())) {
			tBMutilFlowDemo = tBMutilFlowDemoService.getEntity(TBMutilFlowDemoEntity.class, tBMutilFlowDemo.getId());
			req.setAttribute("tBMutilFlowDemoPage", tBMutilFlowDemo);
		}
		return new ModelAndView("business/demo/tBMutilFlowDemo-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tBMutilFlowDemoController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TBMutilFlowDemoEntity tBMutilFlowDemo,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TBMutilFlowDemoEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBMutilFlowDemo, request.getParameterMap());
		List<TBMutilFlowDemoEntity> tBMutilFlowDemos = this.tBMutilFlowDemoService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"出差借款多流程审批");
		modelMap.put(NormalExcelConstants.CLASS,TBMutilFlowDemoEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("出差借款多流程审批列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tBMutilFlowDemos);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TBMutilFlowDemoEntity tBMutilFlowDemo,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"出差借款多流程审批");
    	modelMap.put(NormalExcelConstants.CLASS,TBMutilFlowDemoEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("出差借款多流程审批列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
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
				List<TBMutilFlowDemoEntity> listTBMutilFlowDemoEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TBMutilFlowDemoEntity.class,params);
				for (TBMutilFlowDemoEntity tBMutilFlowDemo : listTBMutilFlowDemoEntitys) {
					tBMutilFlowDemoService.save(tBMutilFlowDemo);
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
