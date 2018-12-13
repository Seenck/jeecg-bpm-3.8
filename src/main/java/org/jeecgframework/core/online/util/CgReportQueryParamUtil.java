package org.jeecgframework.core.online.util;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jeecgframework.core.online.def.CgReportConstant;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.StringUtil;

/**
 * 
 * @Title:QueryParamUtil
 * @description:动态报表查询条件处理工具
 * @author 赵俊夫
 * @date Jul 5, 2013 10:22:31 PM
 * @version V1.0
 */
public class CgReportQueryParamUtil{
	
	//update--begin--author:zhoujf Date:20180615 for:TASK #2780 [Online开发]Online报表自定义sql,以及查询条件处理重构成占位符
	/**
	 * 组装查询参数
	 * @param request 请求(查询值从此处取)
	 * @param item 动态报表字段配置
	 * @param pageSearchFields 页面参数查询字段（占位符的条件语句）
	 * @param paramData 占位符对应的数据
	 */
	public static void loadQueryParams(HttpServletRequest request, Map<String,Object> item, Map<String,Object> pageSearchFields,Map<String,Object> paramData) {
		String filedName = (String) item.get(CgReportConstant.ITEM_FIELDNAME);
		String queryMode = (String) item.get(CgReportConstant.ITEM_QUERYMODE);
		String filedType = (String) item.get(CgReportConstant.ITEM_FIELDTYPE);
		if("single".equals(queryMode)){
			//单条件组装方式
			//update-begin--Author:gj_shaojc  Date:20180425 for：TASK #2665 【问题确认】论坛问题
			String value =request.getParameter(filedName.toLowerCase());
			//update-end--Author:gj_shaojc  Date:20180425 for：TASK #2665 【问题确认】论坛问题
			try {
				if(StringUtil.isEmpty(value)){
					return;
				}
				String uri = request.getQueryString();
				if(uri.contains(filedName+"=")){
					String contiansChinesevalue = new String(value.getBytes("ISO-8859-1"), "UTF-8");
					value = contiansChinesevalue;
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return;
			} 
			//update--begin--author:zhoujf Date:20180606 for:TASK #2751 【sql注入】online开发问题
//			sql_inj_throw(value);
//			SqlInjectionUtil.filterContent(value);
			//update--end--author:zhoujf Date:20180606 for:TASK #2751 【sql注入】online开发问题
			//update--begin--author:zhoujf Date:20180709 for:TASK #2759 【Online报表配置】oracle时间处理的一个bug
//			value = applyType(filedType,value);
			//update--end--author:zhoujf Date:20180709 for:TASK #2759 【Online报表配置】oracle时间处理的一个bug
			if(!StringUtil.isEmpty(value)){
				if(value.contains("*")){
					//模糊查询
					value = value.replaceAll("\\*", "%");
//					params.put(filedName, CgReportConstant.OP_LIKE+value);
					pageSearchFields.put(filedName, CgReportConstant.OP_LIKE+":"+filedName);
				}else{
//					params.put(filedName, CgReportConstant.OP_EQ+value);
					pageSearchFields.put(filedName, CgReportConstant.OP_EQ+":"+filedName);
				}
			}
			//update--begin--author:zhoujf Date:20180709 for:TASK #2759 【Online报表配置】oracle时间处理的一个bug
			paramData.put(filedName, covertData(filedType,value,true));
			//update--end--author:zhoujf Date:20180709 for:TASK #2759 【Online报表配置】oracle时间处理的一个bug
		}else if("group".equals(queryMode)){
			//范围查询组装
			//update--begin--author:zhoujf Date:20180709 for:TASK #2759 【Online报表配置】oracle时间处理的一个bug
			String begin = request.getParameter(filedName.toLowerCase()+"_begin");
			//update--end--author:zhoujf Date:20180709 for:TASK #2759 【Online报表配置】oracle时间处理的一个bug
			//update--begin--author:zhoujf Date:20180606 for:TASK #2751 【sql注入】online开发问题
//			sql_inj_throw(begin);
//			SqlInjectionUtil.filterContent(begin);
			//update--end--author:zhoujf Date:20180606 for:TASK #2751 【sql注入】online开发问题
//			begin= applyType(filedType,begin);
			//update--begin--author:zhoujf Date:20180709 for:TASK #2759 【Online报表配置】oracle时间处理的一个bug
			String end = request.getParameter(filedName.toLowerCase()+"_end");
			//update--end--author:zhoujf Date:20180709 for:TASK #2759 【Online报表配置】oracle时间处理的一个bug
			//update--begin--author:zhoujf Date:20180606 for:TASK #2751 【sql注入】online开发问题
//			sql_inj_throw(end);
//			SqlInjectionUtil.filterContent(end);
			//update--end--author:zhoujf Date:20180606 for:TASK #2751 【sql注入】online开发问题
//			end= applyType(filedType,end);
			//update--begin--author:zhoujf Date:20180709 for:TASK #2759 【Online报表配置】oracle时间处理的一个bug
			if(!StringUtil.isEmpty(begin)){
//				String re = CgReportConstant.OP_RQ+begin;
				String re = CgReportConstant.OP_RQ+":"+filedName+"_begin";
				pageSearchFields.put(filedName, re);
				paramData.put(filedName+"_begin", covertData(filedType,begin,true));
			} 
			if(!StringUtil.isEmpty(end)){
//				String re = CgReportConstant.OP_LQ+end;
				String re = CgReportConstant.OP_LQ+":"+filedName+"_end";
				pageSearchFields.put(filedName, re);
				paramData.put(filedName+"_end", covertData(filedType,end,false));
			}
			//update--end--author:zhoujf Date:20180709 for:TASK #2759 【Online报表配置】oracle时间处理的一个bug
		}
	}
	//update--end--author:zhoujf Date:20180615 for:TASK #2780 [Online开发]Online报表自定义sql,以及查询条件处理重构成占位符
	
	//update--begin--author:zhoujf Date:20180709 for:TASK #2759 【Online报表配置】oracle时间处理的一个bug
	private static Object covertData(String fieldType,String value,boolean isBegin){
		Object obj = null;
		if(!StringUtil.isEmpty(value)){
			if(CgReportConstant.TYPE_STRING.equalsIgnoreCase(fieldType)){
				obj = value;
			}else if(CgReportConstant.TYPE_DATE.equalsIgnoreCase(fieldType)){
				if (value.length() == 19) {
				} else if (value.length() == 10) {
					if(isBegin){
						value +=" 00:00:00";
					}else{
						value +=" 23:59:59";
					}
				}
				SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				obj = DateUtils.str2Date(value, datetimeFormat);
			}else if(CgReportConstant.TYPE_DOUBLE.equalsIgnoreCase(fieldType)){
				obj = value;
			}else if(CgReportConstant.TYPE_INTEGER.equalsIgnoreCase(fieldType)){
				obj = value;
			}else{
				obj = value;
			}
		}
		return obj;
	}
	//update--end--author:zhoujf Date:20180709 for:TASK #2759 【Online报表配置】oracle时间处理的一个bug
	
	/**
	 * 将结果集转化为列表json格式
	 * @param result 结果集
	 * @param size 总大小
	 * @return 处理好的json格式
	 */
	public static String getJson(List<Map<String, Object>> result,Long size){
		JSONObject main = new JSONObject();
		JSONArray rows = new JSONArray();
		main.put("total",size );
		if(result!=null){
			for(Map m:result){
				JSONObject item = new JSONObject();
				Iterator  it =m.keySet().iterator();
				while(it.hasNext()){
					String key = (String) it.next();
					String value =String.valueOf(m.get(key));
					key = key.toLowerCase();
					if(key.contains("time")||key.contains("date")){
						value = datatimeFormat(value);
					}
					item.put(key,value );
				}
				rows.add(item);
			}
		}
		main.put("rows", rows);
		return main.toString();
	}
	
	/**
	 * 将结果集转化为列表json格式(不含分页信息)
	 * @param result 结果集
	 * @param size 总大小
	 * @return 处理好的json格式
	 */
	public static String getJson(List<Map<String, Object>> result){
		JSONArray rows = new JSONArray();
		for(Map m:result){
			JSONObject item = new JSONObject();
			Iterator  it =m.keySet().iterator();
			while(it.hasNext()){
				String key = (String) it.next();
				String value =String.valueOf(m.get(key));
				key = key.toLowerCase();
				if(key.contains("time")||key.contains("date")){
					value = datatimeFormat(value);
				}
				item.put(key,value );
			}
			rows.add(item);
		}
		return rows.toString();
	}
	
	/**
	 * 将毫秒数去掉
	 * @param datetime
	 * @return
	 */
	public static String datatimeFormat(String datetime){
		SimpleDateFormat  dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		SimpleDateFormat  dateFormatTo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d  = null;
		try{
			d = dateFormat.parse(datetime);
			return dateFormatTo.format(d);
		}catch (Exception e) {
			return datetime;
		}
	}
	//update--begin--author:zhoujf Date:20180606 for:TASK #2751 【sql注入】online开发问题
//	/**
//	 * 防止sql注入
//	 * @param str 输入sql
//	 * @return 是否存在注入关键字
//	 */
//	public static boolean sql_inj(String str) {
//		if(StringUtil.isEmpty(str)){
//			return false;
//		}
//		String inj_str = "'|and|exec|insert|select|delete|update|count|chr|mid|master|truncate|char|declare|;|or|+|,";
////		String inj_str = "'|and|exec|insert|select|delete|update|count|chr|mid|master|truncate|char|declare|;|or|-|+|,";
//		String inj_stra[] = inj_str.split("\\|");
//		for (int i = 0; i < inj_stra.length; i++) {
//			if (str.indexOf(" "+inj_stra[i]+" ") >= 0) {
//				return true;
//			}
//		}
//		return false;
//	}
//	/**
//	 * 当存在sql注入时抛异常
//	 * @param str 输入sql
//	 */
//	public static void sql_inj_throw(String str){
//		if(sql_inj(str)){
//			throw new RuntimeException("请注意,填入的参数可能存在SQL注入!");
//		}
//	}
	//update--end--author:zhoujf Date:20180606 for:TASK #2751 【sql注入】online开发问题
	
	/**
	 * 根据字段类型 进行处理
	 * @param fieldType 字段类型
	 * @param value 值
	 * @return
	 */
	/*public static String applyType(String fieldType, String value) {
		if(!StringUtil.isEmpty(value)){
			String result = "";
			if(CgReportConstant.TYPE_STRING.equalsIgnoreCase(fieldType)){
				//update-begin--Author:zzl  Date:20151123 for：加入配置属性可默认进行模糊查询
				//if(ResourceUtil.fuzzySearch&&(!value.contains("*"))){
				//	value="*"+value+"*";
				//}
				//update-end--Author:zzl  Date:20151123 for：加入配置属性可默认进行模糊查询
				//update--begin--author:zhoujf Date:20180615 for:TASK #2780 [Online开发]Online报表自定义sql,以及查询条件处理重构成占位符
//				result = "'" +value+ "'";
				result = value;
				//update--end--author:zhoujf Date:20180615 for:TASK #2780 [Online开发]Online报表自定义sql,以及查询条件处理重构成占位符
			}else if(CgReportConstant.TYPE_DATE.equalsIgnoreCase(fieldType)){
				result = getDateFunction(value, "yyyy-MM-dd");
			}else if(CgReportConstant.TYPE_DOUBLE.equalsIgnoreCase(fieldType)){
				result = value;
			}else if(CgReportConstant.TYPE_INTEGER.equalsIgnoreCase(fieldType)){
				result = value;
			}else{
				result = value;
			}
			return result;
		}else{
			return "";
		}
	}*/
	
	/**
	 * 跨数据库返回日期函数
	 * @param dateStr 日期值
	 * @param dateFormat 日期格式
	 * @return 日期函数
	 */
	/*public static String getDateFunction(String dateStr,String dateFormat){
		String dbType = getDBType();
		String dateFunction = "";
		if("mysql".equalsIgnoreCase(dbType)){
			//mysql日期函数
			dateFunction = "'"+dateStr+"'";
		}else if("oracle".equalsIgnoreCase(dbType)){
			//oracle日期函数
			dateFunction = "TO_DATE('"+dateStr+"','"+dateFormat+"')";
		}else if("sqlserver".equalsIgnoreCase(dbType)){
			//sqlserver日期函数
			dateFunction = "(CONVERT(VARCHAR,'"+dateStr+"') as DATETIME)";
		}else if("postgres".equalsIgnoreCase(dbType)){
			//postgres日期函数
			dateFunction = "'"+dateStr+"'::date ";
		}else{
			dateFunction = dateStr;
		}
		return dateFunction;
	}*/
	
	/**
	 * 获取数据库类型
	 * @return
	 */
	/*public static String getDBType(){
		return DBTypeUtil.getDBType();
	}*/
}
