package org.jeecgframework.web.cgdynamgraph.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.enums.SysThemesEnum;
import org.jeecgframework.core.online.def.CgReportConstant;
import org.jeecgframework.core.online.exception.CgReportNotFoundException;
import org.jeecgframework.core.online.util.CgReportQueryParamUtil;
import org.jeecgframework.core.online.util.FreemarkerHelper;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.DynamicDBUtil;
import org.jeecgframework.core.util.SqlInjectionUtil;
import org.jeecgframework.core.util.SqlUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.SysThemesUtil;
import org.jeecgframework.web.cgdynamgraph.service.core.CgDynamGraphServiceI;
import org.jeecgframework.web.cgreport.service.core.CgReportServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * 
 * @Title:CgReportController
 * @description: Online移动报表展示
 * @author scott
 * @date Jul 29, 2013 9:39:40 PM
 * @version V1.0
 */
@Controller
@RequestMapping("/cgDynamGraphController")
public class CgDynamGraphController extends BaseController {
	@Autowired
	private CgDynamGraphServiceI cgDynamGraphService;
	@Autowired
	private CgReportServiceI cgReportService;
	
	@RequestMapping(params = "design")
	public void design(String id, HttpServletRequest request,String gtype,
			HttpServletResponse response) {
		//step.1 根据id获取该动态报表的配置参数
		Map<String, Object>  cgDynamGraphMap = null;
		try{
			cgDynamGraphMap = cgDynamGraphService.queryCgDynamGraphConfig(id);
		}catch (Exception e) {
			throw new CgReportNotFoundException("动态报表配置不存在!");
		}
		//step.2 获取列表ftl模板路径
		FreemarkerHelper viewEngine = new FreemarkerHelper();
		//step.3 组合模板+数据参数，进行页面展现
		loadVars(cgDynamGraphMap,request);
		String html;
		
	    //update-begin--Author:xuguojie  Date:20160303 for： #959 【报表功能】在线配置图表改造，支持移动端单独展示
		//判断是否为综合类
		Map<String, Object> mainConfig = (Map<String, Object> )cgDynamGraphMap.get(CgReportConstant.MAIN);
		String defaultGtype =mainConfig.get("graph_type")==null?null:(String)mainConfig.get("graph_type");
		//图表类型全为空，则跳转到综合界面。
		if(StringUtil.isEmpty(gtype)&&StringUtil.isEmpty(defaultGtype)){
			html = viewEngine.parseTemplate("/org/jeecgframework/web/cgdynamgraph/engine/core/cgDynamGraphDesign.ftl", cgDynamGraphMap);
		}
		//跳转到简单页面，只有查询条件与图
		else{
			gtype = StringUtils.isEmpty(gtype)?defaultGtype:gtype; //优先级：参数中的值比表头配置的值高
			cgDynamGraphMap.put("gtype", gtype);
			//设置gtype
			html = viewEngine.parseTemplate("/org/jeecgframework/web/cgdynamgraph/engine/core/cgDynamGraphDesignMobile.ftl", cgDynamGraphMap);
		}
	    //update-end--Author:xuguojie  Date:20160303 for： #959 【报表功能】在线配置图表改造，支持移动端单独展示
		try {
			response.setContentType("text/html");
			response.setHeader("Cache-Control", "no-store");
			PrintWriter writer = response.getWriter();
			writer.println(html);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				response.getWriter().close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		
	}
	
	private String getHtmlHead(HttpServletRequest request){
		HttpSession session = ContextHolderUtils.getSession();
		String lang = (String)session.getAttribute("lang");
		StringBuilder sb= new StringBuilder("");
		SysThemesEnum sysThemesEnum = SysThemesUtil.getSysTheme(request);
		sb.append("<script type=\"text/javascript\" src=\"plug-in/jquery/jquery-1.8.3.js\"></script>");
		sb.append("<script type=\"text/javascript\" src=\"plug-in/jquery-plugs/i18n/jquery.i18n.properties.js\"></script>");
		sb.append("<script type=\"text/javascript\" src=\"plug-in/tools/dataformat.js\"></script>");
		sb.append(SysThemesUtil.getEasyUiTheme(sysThemesEnum));
		sb.append("<link rel=\"stylesheet\" href=\"plug-in/easyui/themes/icon.css\" type=\"text/css\"></link>");
		sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"plug-in/accordion/css/accordion.css\">");
		sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"plug-in/accordion/css/icons.css\">");
		sb.append("<script type=\"text/javascript\" src=\"plug-in/easyui/jquery.easyui.min.1.3.2.js\"></script>");
		sb.append("<script type=\"text/javascript\" src=\"plug-in/easyui/locale/zh-cn.js\"></script>");
		sb.append("<script type=\"text/javascript\" src=\"plug-in/tools/syUtil.js\"></script>");
		sb.append(SysThemesUtil.getLhgdialogTheme(sysThemesEnum));
		//update--begin--author:zhangjiaqiang date:20170315 for:修订layer提示框异常
		sb.append("<script type=\"text/javascript\" src=\"plug-in/layer/layer.js\"></script>");
		//update--end--author:zhangjiaqiang date:20170315 for:修订layer提示框异常
		sb.append("<script type=\"text/javascript\" src=\"plug-in/tools/curdtools.js\"></script>");
		sb.append("<script type=\"text/javascript\" src=\"plug-in/tools/easyuiextend.js\"></script>");
		return sb.toString();
	}
	
	/**
	 * 组装模版参数
	 * @param cgDynamGraphMap
	 */
	@SuppressWarnings("unchecked")
	private void loadVars(Map<String, Object> cgDynamGraphMap,HttpServletRequest request) {
		Map mainM = (Map) cgDynamGraphMap.get(CgReportConstant.MAIN);
		List<Map<String,Object>> fieldList = (List<Map<String, Object>>) cgDynamGraphMap.get(CgReportConstant.ITEMS);
		List<String> paramList = (List<String>)cgDynamGraphMap.get(CgReportConstant.PARAMS);
		List<Map<String,Object>> queryList = new ArrayList<Map<String,Object>>(0);
		for(Map<String,Object> fl:fieldList){
			fl.put(CgReportConstant.ITEM_FIELDNAME, ((String)fl.get(CgReportConstant.ITEM_FIELDNAME)).toLowerCase());
			String isQuery = (String) fl.get(CgReportConstant.ITEM_ISQUERY);
			if(CgReportConstant.BOOL_TRUE.equalsIgnoreCase(isQuery)){
				cgReportService.loadDic(fl);
				queryList.add(fl);
			}
		}
		StringBuilder sb = new StringBuilder("");
		if(paramList!=null&&paramList.size()>0){
//			queryList = new ArrayList<Map<String,Object>>(0);
			for(String param:paramList){
				sb.append("&").append(param).append("=");
				String value = request.getParameter(param);
    			if(StringUtil.isNotEmpty(value)){
    				sb.append(value);
    			}
			}
		}
		cgDynamGraphMap.put(CgReportConstant.CONFIG_ID, mainM.get("code"));
		cgDynamGraphMap.put(CgReportConstant.CONFIG_NAME, mainM.get("name"));
		cgDynamGraphMap.put(CgReportConstant.CONFIG_FIELDLIST, fieldList);
		cgDynamGraphMap.put(CgReportConstant.CONFIG_QUERYLIST, queryList);
		//获取传递参数
		cgDynamGraphMap.put(CgReportConstant.CONFIG_PARAMS, sb.toString());
	}
	
	
	/**
	 * 动态报表数据查询
	 * @param configId 配置id-code
	 * @param page 分页页面
	 * @param rows 分页大小
	 * @param request 
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "datagrid")
	public void datagrid(String configId,HttpServletRequest request,
			HttpServletResponse response) {
		//step.1 根据id获取该动态报表的配置参数
		Map<String, Object>  cgDynamGraphMap = null;
		try{
			cgDynamGraphMap = cgDynamGraphService.queryCgDynamGraphConfig(configId);
			if(cgDynamGraphMap.size()<=0){
				throw new CgReportNotFoundException("动态报表配置不存在!");
			}
		}catch (Exception e) {
			throw new CgReportNotFoundException("查找动态报表配置失败!"+e.getMessage());
		}
		//step.2 获取该配置的查询SQL
		Map configM = (Map) cgDynamGraphMap.get(CgReportConstant.MAIN);
		String querySql = (String) configM.get(CgReportConstant.CONFIG_SQL);
		List<Map<String,Object>> items = (List<Map<String, Object>>) cgDynamGraphMap.get(CgReportConstant.ITEMS);
		List<String> paramList = (List<String>) cgDynamGraphMap.get(CgReportConstant.PARAMS);
		//页面参数查询字段（占位符的条件语句）
		Map pageSearchFields =  new LinkedHashMap<String,Object>();
		//update--begin--author:zhoujf Date:20180615 for:TASK #2780 [Online开发]Online报表自定义sql,以及查询条件处理重构成占位符
		//获取查询条件数据
		Map<String,Object> paramData = new HashMap<String, Object>();
		if(paramList!=null&&paramList.size()>0){
			for(String param :paramList){
				String value = request.getParameter(param);
				value = value==null?"":value;
				//update--begin--author:zhoujf Date:20180606 for:TASK #2751 【sql注入】online开发问题
//				SqlInjectionUtil.filterContent(value);
				//update--end--author:zhoujf Date:20180606 for:TASK #2751 【sql注入】online开发问题
//				querySql = querySql.replace("${"+param+"}", value);
				
				querySql = querySql.replace("'${"+param+"}'", ":"+param);
				querySql = querySql.replace("${"+param+"}", ":"+param);
				paramData.put(param, value);
			}
		}
		//update--begin--author:zhoujf Date:20180615 for:配置的参数和列表的查询条件同时有效
		for(Map<String,Object> item:items){
			String isQuery = (String) item.get(CgReportConstant.ITEM_ISQUERY);
			if(CgReportConstant.BOOL_TRUE.equalsIgnoreCase(isQuery)){
				//step.3 装载查询条件
				CgReportQueryParamUtil.loadQueryParams(request, item, pageSearchFields,paramData);
			}
		}
		//update--end--author:zhoujf Date:20180615 for:配置的参数和列表的查询条件同时有效
		//update--end--author:zhoujf Date:20180615 for:TASK #2780 [Online开发]Online报表自定义sql,以及查询条件处理重构成占位符
		//step.4 进行查询返回结果
        //update-begin--Author:张忠亮  Date:20150608 for：多数据源支持
        String dbKey=(String)configM.get("db_source");
        List<Map<String, Object>> result=null;
        Long size=0l;
        if(StringUtils.isNotBlank(dbKey)){
        	//update--begin--author:zhoujf Date:20180615 for:TASK #2780 [Online开发]Online报表自定义sql,以及查询条件处理重构成占位符
        	Map map= null;
        	if(paramData!=null&&paramData.size()>0){
        		result= DynamicDBUtil.findListByHash(dbKey,SqlUtil.getFullSql(querySql,pageSearchFields),(HashMap<String, Object>)paramData);
        		map=(Map)DynamicDBUtil.findOneByHash(dbKey,SqlUtil.getCountSql(querySql,pageSearchFields),(HashMap<String, Object>)paramData);
        	}else{
        		result= DynamicDBUtil.findList(dbKey,querySql);
        		map=(Map)DynamicDBUtil.findOne(dbKey,SqlUtil.getCountSql(querySql,null));
        	}
        	//update--end--author:zhoujf Date:20180615 for:TASK #2780 [Online开发]Online报表自定义sql,以及查询条件处理重构成占位符
            if(map.get("COUNT(*)") instanceof BigDecimal){
            	BigDecimal count = (BigDecimal)map.get("COUNT(*)");
            	size = count.longValue();
            }else{
            	size=(Long)map.get("COUNT(*)");
            }
        }else{
        	//update--begin--author:zhoujf Date:20180615 for:TASK #2780 [Online开发]Online报表自定义sql,以及查询条件处理重构成占位符
            result= cgDynamGraphService.queryByCgDynamGraphSql(querySql, pageSearchFields,paramData);
            size = cgDynamGraphService.countQueryByCgDynamGraphSql(querySql, pageSearchFields,paramData);
          //update--end--author:zhoujf Date:20180615 for:TASK #2780 [Online开发]Online报表自定义sql,以及查询条件处理重构成占位符
        }
        //update-end--Author:张忠亮  Date:20150608 for：多数据源支持
        cgReportService.dealDic(result,items);
        cgReportService.dealReplace(result,items);
		response.setContentType("application/json");
		response.setHeader("Cache-Control", "no-store");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.println(CgReportQueryParamUtil.getJson(result,size));
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				writer.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}
	/**
	 * 解析SQL，返回字段集
	 * @param sql
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "getFields", method = RequestMethod.POST)
	@ResponseBody
	public Object getSqlFields(String sql,String dbKey){
		List<String> fields = null;
		List<String> params = null;
		Map reJson = new HashMap<String, Object>();
		try{
			fields = cgReportService.getFields(sql, dbKey);
			params = cgReportService.getSqlParams(sql);
		}catch (Exception e) {
			e.printStackTrace();
			String errorInfo = "解析失败!<br><br>失败原因：";
			//update-start--Author: jg_huangxg  Date:20151210 for：修改提示内容
			//无法直接捕捉到:java.net.ConnectException异常
			int i = e.getMessage().indexOf("Connection refused: connect");
			
			if (i != -1) {//非链接异常
				errorInfo += "数据源连接失败.";
			}else{
				errorInfo += "SQL语法错误.";
			}
			//update-end--Author: jg_huangxg  Date:20151210 for：修改提示内容
			reJson.put("status", "error");
			reJson.put("datas", errorInfo);
			return reJson;
		}
		reJson.put("status", "success");
		reJson.put("fields", fields);
		reJson.put("params", params);
		return reJson;
	}
	
}
