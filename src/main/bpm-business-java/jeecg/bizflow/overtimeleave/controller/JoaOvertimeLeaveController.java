package jeecg.bizflow.overtimeleave.controller;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jeecg.bizflow.overtimeleave.entity.JoaOvertimeLeaveEntity;
import jeecg.bizflow.overtimeleave.service.JoaOvertimeLeaveServiceI;

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
import org.jeecgframework.web.system.pojo.base.TSBaseUser;
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

/**   
 * @Title: Controller  
 * @Description: 调休申请单
 * @author onlineGenerator
 * @date 2018-07-31 18:01:14
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/joaOvertimeLeaveController")
public class JoaOvertimeLeaveController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(JoaOvertimeLeaveController.class);

	@Autowired
	private JoaOvertimeLeaveServiceI joaOvertimeLeaveService;
	@Autowired
	private SystemService systemService;
	


	/**
	 * 调休申请单列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("business/bizflow/overtimeleave/joaOvertimeLeaveList");
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
	public void datagrid(JoaOvertimeLeaveEntity joaOvertimeLeave,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(JoaOvertimeLeaveEntity.class, dataGrid);
		String userName = ResourceUtil.getSessionUser().getUserName();
		cq.eq("createBy", userName);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, joaOvertimeLeave, request.getParameterMap());
		try{
		//自定义追加查询条件
		
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.joaOvertimeLeaveService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除调休申请单
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(JoaOvertimeLeaveEntity joaOvertimeLeave, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		joaOvertimeLeave = systemService.getEntity(JoaOvertimeLeaveEntity.class, joaOvertimeLeave.getId());
		message = "调休申请单删除成功";
		try{
			joaOvertimeLeaveService.delete(joaOvertimeLeave);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "调休申请单删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除调休申请单
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "调休申请单删除成功";
		try{
			for(String id:ids.split(",")){
				JoaOvertimeLeaveEntity joaOvertimeLeave = systemService.getEntity(JoaOvertimeLeaveEntity.class, 
				id
				);
				joaOvertimeLeaveService.delete(joaOvertimeLeave);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "调休申请单删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加调休申请单
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(JoaOvertimeLeaveEntity joaOvertimeLeave, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "调休申请单添加成功";
		try{
			joaOvertimeLeaveService.save(joaOvertimeLeave);
			j.setObj(joaOvertimeLeave.getId());
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "调休申请单添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新调休申请单
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(JoaOvertimeLeaveEntity joaOvertimeLeave, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "调休申请单更新成功";
		JoaOvertimeLeaveEntity t = joaOvertimeLeaveService.get(JoaOvertimeLeaveEntity.class, joaOvertimeLeave.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(joaOvertimeLeave, t);
			joaOvertimeLeaveService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "调休申请单更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 调休申请单新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(JoaOvertimeLeaveEntity joaOvertimeLeave, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(joaOvertimeLeave.getId())) {
			joaOvertimeLeave = joaOvertimeLeaveService.getEntity(JoaOvertimeLeaveEntity.class, joaOvertimeLeave.getId());
			req.setAttribute("joaOvertimeLeave", joaOvertimeLeave);
		}
		TSBaseUser tsBaseUser = ResourceUtil.getSessionUser();
		req.setAttribute("tsBaseUser", tsBaseUser);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		req.setAttribute("nowDate", sdf.format(new Date()));
		return new ModelAndView("business/bizflow/overtimeleave/joaOvertimeLeave-add");
	}
	/**
	 * 调休申请单编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(JoaOvertimeLeaveEntity joaOvertimeLeave, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(joaOvertimeLeave.getId())) {
			joaOvertimeLeave = joaOvertimeLeaveService.getEntity(JoaOvertimeLeaveEntity.class, joaOvertimeLeave.getId());
			req.setAttribute("joaOvertimeLeave", joaOvertimeLeave);
		}
		return new ModelAndView("business/bizflow/overtimeleave/joaOvertimeLeave-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","joaOvertimeLeaveController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(JoaOvertimeLeaveEntity joaOvertimeLeave,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(JoaOvertimeLeaveEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, joaOvertimeLeave, request.getParameterMap());
		List<JoaOvertimeLeaveEntity> joaOvertimeLeaves = this.joaOvertimeLeaveService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"调休申请单");
		modelMap.put(NormalExcelConstants.CLASS,JoaOvertimeLeaveEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("调休申请单列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,joaOvertimeLeaves);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(JoaOvertimeLeaveEntity joaOvertimeLeave,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"调休申请单");
    	modelMap.put(NormalExcelConstants.CLASS,JoaOvertimeLeaveEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("调休申请单列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
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
				List<JoaOvertimeLeaveEntity> listJoaOvertimeLeaveEntitys = ExcelImportUtil.importExcel(file.getInputStream(),JoaOvertimeLeaveEntity.class,params);
				for (JoaOvertimeLeaveEntity joaOvertimeLeave : listJoaOvertimeLeaveEntitys) {
					joaOvertimeLeaveService.save(joaOvertimeLeave);
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
	
	/**
	 * 打开加班单选择界面
	 * @author LeeShaoQing
	 * @return
	 */
	@RequestMapping(params = "openSelectOrvertime")
	public ModelAndView openSelectOrvertime(HttpServletRequest req) {
		String leaveId = req.getParameter("leaveId");
		req.setAttribute("leaveId", leaveId);
		return new ModelAndView("business/bizflow/overtimeleave/openSelectOvertime");
	}
	
	
	/**
	 * 调休申请单详情页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goDetail")
	public ModelAndView goDetail(JoaOvertimeLeaveEntity joaOvertimeLeave, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(joaOvertimeLeave.getId())) {
			joaOvertimeLeave = joaOvertimeLeaveService.getEntity(JoaOvertimeLeaveEntity.class, joaOvertimeLeave.getId());
			req.setAttribute("joaOvertimeLeave", joaOvertimeLeave);
		}
		return new ModelAndView("business/bizflow/overtimeleave/joaOvertimeLeave-detail");
	}
}
