package org.jeecgframework.workflow.controller.process;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtil;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.common.DBTable;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSType;
import org.jeecgframework.web.system.pojo.base.TSTypegroup;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.workflow.common.WorkFlowGlobals;
import org.jeecgframework.workflow.pojo.base.TSTable;
import org.jeecgframework.workflow.service.ActivitiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


/**
 * 数据库操作处理类
 * 
 */
@Controller("dbController")
@RequestMapping("/dbController")
public class DataBaseController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DataBaseController.class);
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

	@Autowired
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	/**
	 * 数据表分组页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tableTypeTabs")
	public ModelAndView tableTypeTabs(HttpServletRequest request) {
		loadTables(request);
		return new ModelAndView("workflow/database/tableTypeTabs");
	}

	/**
	 * 从hibernate中把所有有变化的表加载到t_s_table表中
	 * @param request
	 */
	private void loadTables(HttpServletRequest request) {
		Integer tableSize = systemService.getAllDbTableSize();
		List<TSTable> tsTableList = systemService.loadAll(TSTable.class);
		if (tableSize > tsTableList.size()) {
			TSTypegroup tsTypegroup = systemService.getTypeGroup(WorkFlowGlobals.TypeGroup_Database,"数据表");
			TSType actType=systemService.getType(WorkFlowGlobals.DataBase_Activiti, "工作流引擎表", tsTypegroup);
			TSType baseType=systemService.getType(WorkFlowGlobals.DataBase_Base, "系统基础表", tsTypegroup);
			TSType busType=systemService.getType(WorkFlowGlobals.DataBase_Bus, "业务表", tsTypegroup);
			TSType proType=systemService.getType(WorkFlowGlobals.DataBase_Process, "自定义引擎表", tsTypegroup);
			TSType otherType=systemService.getType(WorkFlowGlobals.DataBase_Other, "其他表", tsTypegroup);
			
			List<DBTable> dbTables = systemService.getAllDbTableName();
			for (DBTable dbTable : dbTables) {
				TSTable tsTable = new TSTable();
				Class<?> c = dbTable.getClass();
				tsTable.setTableTitle(dbTable.getTableTitle());
				tsTable.setEntityName(dbTable.getEntityName());
				tsTable.setTableName(dbTable.getTableName());
				tsTable.setEntityKey(dbTable.getTableName().substring(dbTable.getTableName().lastIndexOf("_")+1));
				if (dbTable.getTableName().indexOf(WorkFlowGlobals.DataBase_Activiti) >= 0) {
					tsTable.setTSType(actType);
				}
				if (dbTable.getTableName().indexOf(WorkFlowGlobals.DataBase_Base) >= 0) {
					tsTable.setTSType(baseType);
				}
				if (dbTable.getTableName().indexOf(WorkFlowGlobals.DataBase_Bus) >= 0) {
					tsTable.setTSType(busType);
				}
				if (dbTable.getTableName().indexOf(WorkFlowGlobals.DataBase_Process) >= 0) {
					tsTable.setTSType(proType);
				}
				if (dbTable.getTableName().indexOf(WorkFlowGlobals.DataBase_Process) < 0 
					&& dbTable.getTableName().indexOf(WorkFlowGlobals.DataBase_Bus)<0
				    && dbTable.getTableName().indexOf(WorkFlowGlobals.DataBase_Base) < 0 
				    && dbTable.getTableName().indexOf(WorkFlowGlobals.DataBase_Activiti)< 0) {
					tsTable.setTSType(otherType);
				}
				TSTable oldTable = systemService.findUniqueByProperty(TSTable.class, "tableName", dbTable.getTableName());
				if (oldTable == null) {
					systemService.save(tsTable);
				}
			}
		}
		TSTypegroup tsTypeList=systemService.findUniqueByProperty(TSTypegroup.class,"typegroupcode",WorkFlowGlobals.TypeGroup_Database);
		request.setAttribute("tsTypeList", tsTypeList.getTSTypes());
	}
	
	/**
	 * 强制重新加载表
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "reloadData")
	@ResponseBody
	public AjaxJson reloadData(HttpServletRequest request){
		AjaxJson json = new AjaxJson();
		this.activitiService.batchDelete(TSTable.class);
		loadTables(request);
		json.setMsg("重新加载成功");
		json.setSuccess(true);
		return json;
	}

	/**
	 * 数据表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tableList")
	public ModelAndView tableList(HttpServletRequest request) {
		String typeid=oConvertUtils.getString(request.getParameter("typeid"));
		TSType type=systemService.getEntity(TSType.class,typeid);
		request.setAttribute("type",type);
		return new ModelAndView("workflow/database/tableList");
	}
	/**
	 * 数据表编辑跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "aouTable")
	public ModelAndView aouTable(TSTable table,HttpServletRequest req) {
		table=systemService.getEntity(TSTable.class,table.getId());
		req.setAttribute("table",table);
		return new ModelAndView("workflow/database/datatable");
	}
	/**
	 * 数据表编辑
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "saveTable")
	@ResponseBody
	public AjaxJson saveTable(TSTable table, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(table.getId())) {
			message = "数据表: " + table.getTableName() + "更新成功";
			systemService.saveOrUpdate(table);
			systemService.addLog(message, WorkFlowGlobals.Log_Type_UPDATE,
					WorkFlowGlobals.Log_Leavel_INFO);
		} else {
			message = "数据表: " + table.getTableName() + "添加成功";
			systemService.save(table);
			systemService.addLog(message, WorkFlowGlobals.Log_Type_INSERT,
					WorkFlowGlobals.Log_Leavel_INFO);
		}
		return j;
	}

	/**
	 * 数据表请求数据
	 */
	@RequestMapping(params = "tableGrid")
	public void tableGrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String typeid=oConvertUtils.getString(request.getParameter("typeid"));
		CriteriaQuery cq = new CriteriaQuery(TSTable.class, dataGrid);
		cq.eq("TSType.id",typeid);
		cq.add();
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
}
