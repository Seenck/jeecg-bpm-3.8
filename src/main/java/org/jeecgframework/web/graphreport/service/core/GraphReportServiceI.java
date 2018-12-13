package org.jeecgframework.web.graphreport.service.core;

import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.service.CommonService;

/**
 * 
 * @Title:CgReportServiceI
 * @description:图表配置服务接口
 * @author 钟世云
 * @date 2015.4.11
 * @version V1.0
 */
public interface GraphReportServiceI extends CommonService{
	/**
	 * 根据报表的ID获得报表的抬头配置以及明细配置
	 * @param reportId
	 * @return
	 */
	public Map<String,Object> queryCgReportConfig(String reportId);
	
	//update-begin-----author:scott-----------date:20160817-------for:注释掉无用代码----------
//	/**
//	 * 根据报表id获得报表抬头配置
//	 * @param reportId
//	 * @return
//	 */
//	public Map<String,Object> queryCgReportMainConfig(String reportId);
//	/**
//	 * 根据报表id获得报表明细配置
//	 * @param reportId
//	 * @return
//	 */
//	public List<Map<String,Object>> queryCgReportItems(String reportId);
	//update-end-----author:scott-----------date:20160817-------for:注释掉无用代码----------
	
	/**
	 * 执行报表SQL获取结果集
	 * @param sql 报表SQL
	 * @param params 查询条件
	 * @param page 页面数
	 * @param rows 要获取的条目总数
	 * @return
	 */
	//update--begin--author:zhoujf Date:20180615 for:TASK #2780 [Online开发]Online报表自定义sql,以及查询条件处理重构成占位符
	public List<Map<String,Object>> queryByCgReportSql(String sql,Map params,Map<String,Object> paramData,int page,int rows);
	//update--end--author:zhoujf Date:20180615 for:TASK #2780 [Online开发]Online报表自定义sql,以及查询条件处理重构成占位符
	/**
	 * 获取报表sql结果集大小
	 * @param sql 报表SQL
	 * @param params 查询条件
	 * @return
	 */
	public long countQueryByCgReportSql(String sql,Map params);
	/**
	 * 通过执行sql获得该sql语句中的字段集合
	 * @param sql 报表sql
	 * @return
	 */
	public List<String> getSqlFields(String sql);
}
