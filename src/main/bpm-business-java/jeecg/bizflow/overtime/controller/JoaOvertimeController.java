package jeecg.bizflow.overtime.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jeecg.bizflow.overtime.entity.JoaOvertimeEntity;
import jeecg.bizflow.overtime.service.JoaOvertimeServiceI;
import jeecg.bizflow.overtime.vo.OvertimeVO;
import jeecg.bizflow.overtimedetail.entity.JoaOvertimeDetailEntity;
import jeecg.bizflow.overtimedetail.entity.JoaOvertimePage;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.criterion.Property;
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
 * @Description: 加班申请单
 * @author onlineGenerator
 * @date 2018-08-01 15:54:24
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/joaOvertimeController")
public class JoaOvertimeController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(JoaOvertimeController.class);

	@Autowired
	private JoaOvertimeServiceI joaOvertimeService;
	@Autowired
	private SystemService systemService;
	


	/**
	 * 加班申请单列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("bizflow/overtime/joaOvertimeList");
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
	public void datagrid(JoaOvertimeEntity joaOvertime,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(JoaOvertimeEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, joaOvertime, request.getParameterMap());
		try{
		//自定义追加查询条件
		
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.joaOvertimeService.getDataGridReturn(cq, true);
		List<JoaOvertimeEntity> list = new ArrayList<JoaOvertimeEntity>();
		dataGrid.setResults(list);
		dataGrid.setTotal(0);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除加班申请单
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(JoaOvertimeEntity joaOvertime, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		joaOvertime = systemService.getEntity(JoaOvertimeEntity.class, joaOvertime.getId());
		message = "加班申请单删除成功";
		try{
			joaOvertimeService.delete(joaOvertime);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "加班申请单删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除加班申请单
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "加班申请单删除成功";
		try{
			for(String id:ids.split(",")){
				JoaOvertimeEntity joaOvertime = systemService.getEntity(JoaOvertimeEntity.class, 
				id
				);
				joaOvertimeService.delete(joaOvertime);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "加班申请单删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加加班申请单
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(JoaOvertimeEntity joaOvertime, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "加班申请单添加成功";
		try{
			joaOvertimeService.save(joaOvertime);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "加班申请单添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新加班申请单
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(JoaOvertimeEntity joaOvertime, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "加班申请单更新成功";
		JoaOvertimeEntity t = joaOvertimeService.get(JoaOvertimeEntity.class, joaOvertime.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(joaOvertime, t);
			joaOvertimeService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "加班申请单更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 加班申请单新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(JoaOvertimeEntity joaOvertime, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(joaOvertime.getId())) {
			joaOvertime = joaOvertimeService.getEntity(JoaOvertimeEntity.class, joaOvertime.getId());
			req.setAttribute("joaOvertime", joaOvertime);
		}
		return new ModelAndView("bizflow/overtime/joaOvertime-add");
	}
	/**
	 * 加班申请单编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(JoaOvertimeEntity joaOvertime, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(joaOvertime.getId())) {
			joaOvertime = joaOvertimeService.getEntity(JoaOvertimeEntity.class, joaOvertime.getId());
			req.setAttribute("joaOvertime", joaOvertime);
		}
		return new ModelAndView("bizflow/overtime/joaOvertime-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","joaOvertimeController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(JoaOvertimeEntity joaOvertime,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(JoaOvertimeEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, joaOvertime, request.getParameterMap());
		List<JoaOvertimeEntity> joaOvertimes = this.joaOvertimeService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"加班申请单");
		modelMap.put(NormalExcelConstants.CLASS,JoaOvertimeEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("加班申请单列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,joaOvertimes);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(JoaOvertimeEntity joaOvertime,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"加班申请单");
    	modelMap.put(NormalExcelConstants.CLASS,JoaOvertimeEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("加班申请单列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
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
				List<JoaOvertimeEntity> listJoaOvertimeEntitys = ExcelImportUtil.importExcel(file.getInputStream(),JoaOvertimeEntity.class,params);
				for (JoaOvertimeEntity joaOvertime : listJoaOvertimeEntitys) {
					joaOvertimeService.save(joaOvertime);
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
	 * 选择加班单DataGrid
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "selectDatagrid")
	public void selectDatagrid(JoaOvertimeEntity joaOvertime,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(JoaOvertimeEntity.class, dataGrid);
		cq.eq("bpmStatus", "3");
		String userName = ResourceUtil.getSessionUser().getUserName();
		cq.eq("createBy", userName);
		String leaveId = request.getParameter("leaveId");
		String sql = "select overtime_id from joa_overtime_detail where overtime_leave_id = ?";
		List<Map<String,Object>> listMap = systemService.findForJdbc(sql, leaveId);
		//去除已选择的加班单数据
		CriteriaQuery subCq = new CriteriaQuery(JoaOvertimeEntity.class);
		if(listMap != null && listMap.size() > 0) {
			String[] overtimes = new String[listMap.size()];
			for (int i = 0; i < listMap.size(); i++) {
				overtimes[i] = listMap.get(i).get("overtime_id").toString();
			}
			subCq.setProjection(Property.forName("id"));
			subCq.in("id", overtimes);
			subCq.add();
			cq.add(Property.forName("id").notIn(subCq.getDetachedCriteria()));
		}
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, joaOvertime, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.joaOvertimeService.getDataGridReturn(cq, true);
		Map<String,Map<String,Object>> extMap = new HashMap<String, Map<String,Object>>();
		List<JoaOvertimeEntity> joaOvertimeList = dataGrid.getResults();
		for(JoaOvertimeEntity temp : joaOvertimeList){
			Map<String,Object> m = new HashMap<String, Object>();
			if(temp.getApplyDay()==null){
				temp.setApplyDay(0);
			}
			if(temp.getApplyHour()==null){
				temp.setApplyHour(0);
			}
			//拼装总加班格式:X天X小时
			m.put("total", temp.getTotalDay() + "天" + temp.getTotalHour() + "小时");
			//拼装已补偿时间
			m.put("compensatedTime", temp.getApplyDay() + "天" + temp.getApplyHour() + "小时");
			//计算时间判断是否是补偿过的或未补偿过的
			Integer total = Integer.parseInt(temp.getTotalDay()) * 24 + Integer.parseInt(temp.getTotalHour());
			Integer time = temp.getApplyDay() * 24 + temp.getApplyHour();
			if(total - time != 0) {
				//总加班时间减已补偿时间算出未补偿时间
				Integer datetime = total - time;
				if(datetime >= 0) {
					Integer time_day = datetime / 24;
					Integer time_hour = ( datetime % 24 );
					if(time_day >= 0 && time_hour >= 0) {
						m.put("makeupTime", time_day + "天" + time_hour + "小时");
					}
					m.put("compensation", "未补偿完毕");
				}
			} else if(time == 0) { 
				m.put("compensation", "暂未补偿");
			} else {
				m.put("compensation", "补偿完毕");
			}
			extMap.put(temp.getId(), m);
		}
		TagUtil.datagrid(response, dataGrid, extMap);
	}
	
	
	/**
	 * 保存新增/更新的行数据
	 * @param page
	 * @return
	 */
	@RequestMapping(params = "saveRows")
	@ResponseBody
	public AjaxJson saveRows(JoaOvertimePage joaOvertimePage,HttpServletRequest request, HttpServletResponse response){
		AjaxJson j = new AjaxJson();
		j.setMsg("调休申请时间设置失败");
		List<JoaOvertimeDetailEntity> joaOvertimes = joaOvertimePage.getJoaOvertimes();
		if(CollectionUtils.isNotEmpty(joaOvertimes)) {
			//获取所编辑的记录
			for (JoaOvertimeDetailEntity joaOvertime : joaOvertimes) {
				if(oConvertUtils.isNotEmpty(joaOvertime.getId())) {
					try {
						JoaOvertimeDetailEntity joaOvertimeDetail = systemService.getEntity(JoaOvertimeDetailEntity.class, joaOvertime.getId());
						MyBeanUtils.copyBeanNotNull2Bean(joaOvertime, joaOvertimeDetail);
						joaOvertimeService.saveOrUpdate(joaOvertimeDetail);
						j.setMsg("调休申请时间设置成功");
					} catch (Exception e) {
						e.printStackTrace();
						j.setSuccess(false);
					}
				}
			}
		}
		return j;
	}
	
	
	/**
	 * 加载已选加班单信息
	 * @param joaOvertime
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "overtimeDatagrid")
	public void overtimeDatagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		// 获取调休表ID
		String leaveId = request.getParameter("leaveId");
		if (oConvertUtils.isNotEmpty(leaveId)) {
			String hql = "select new jeecg.bizflow.overtime.vo.OvertimeVO(jod.id,jo.beginTime,jo.endTime,jo.totalDay,jo.totalHour,jo.applyDay,jo.applyHour,jod.applyDay,jod.applyHour) "
					+ "from JoaOvertimeDetailEntity jod, JoaOvertimeEntity jo where jod.overtimeId = jo.id and jod.overtimeLeaveId = ?";
			List<OvertimeVO> overtimeList = this.systemService.findHql(hql, leaveId);
			try {
				List<OvertimeVO> overtimeLists = new ArrayList<OvertimeVO>();
				for (OvertimeVO overtime : overtimeList) {
					if(overtime.getJoApplyDay()==null){
						overtime.setJoApplyDay(0);
					}
					if(overtime.getJoApplyHour()==null){
						overtime.setJoApplyHour(0);
					}
					// 总加班时间
					String total = overtime.getTotalDay() + "天" + overtime.getTotalHour() + "小时";
					// 未经补偿时间
					String makeupTime = "";
					Integer total_day = Integer.parseInt(overtime.getTotalDay()) * 24 + Integer.parseInt(overtime.getTotalHour());
					Integer time = overtime.getJoApplyDay() * 24 + overtime.getJoApplyHour();
					if (total_day - time != 0) {
						// 总加班时间减已补偿时间算出未补偿时间
						Integer datetime = total_day - time;
						if (datetime >= 0) {
							Integer time_day = datetime / 24;
							Integer time_hour = (datetime % 24);
							if (time_day >= 0 && time_hour >= 0) {
								makeupTime = time_day + "天" + time_hour + "小时";
							}
						}
					}
					// 调休时间天/小时
					Integer applyDay = 0;
					Integer applyHour = 0;
					if (overtime.getApplyDay() != null && overtime.getApplyHour() != null) {
						applyDay = (Integer) overtime.getApplyDay() != 0 ? overtime.getApplyDay() : 0;
						applyHour = (Integer) overtime.getApplyHour() != 0 ? overtime.getApplyHour() : 0;
					}
					// 拼装加班单VO
					OvertimeVO overtimeVO = new OvertimeVO(overtime.getId(), overtime.getBeginTime(), overtime.getEndTime(), total, makeupTime, applyDay, applyHour);
					overtimeLists.add(overtimeVO);
				}
				dataGrid.setResults(overtimeLists);
				dataGrid.setTotal(overtimeLists.size());
				TagUtil.datagrid(response, dataGrid);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
