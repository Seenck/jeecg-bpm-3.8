package org.jeecgframework.web.cgform.util;

import java.util.ArrayList;
import java.util.List;

import org.jeecgframework.core.enums.OnlineGenerateEnum;

/**
 * 代码生成公共方法
 * 
 * @author	jiaqiankun
 */
public class GenerateUtil {
	
	public static List<OnlineGenerateEnum> getOnlineGenerateEnum(String type){
		List<OnlineGenerateEnum> list = new ArrayList<OnlineGenerateEnum>();
		for(OnlineGenerateEnum item : OnlineGenerateEnum.values()) {
			if(item.getFormType().equals(type)) {
				list.add(item);
			}
		}
		return list;
	}
	//update-begin--Author:zhoujf  Date:20180503 for：TASK #2500 【代码生成器】代码生成器开发一通用模板生成功能
	public static List<OnlineGenerateEnum> getOnlineGenerateEnum(String type,String version){
		List<OnlineGenerateEnum> list = new ArrayList<OnlineGenerateEnum>();
		for(OnlineGenerateEnum item : OnlineGenerateEnum.values()) {
			if(item.getFormType().equals(type)&&item.getVersion().equals(version)) {
				list.add(item);
			}
		}
		return list;
	}
	//update-end--Author:zhoujf  Date:20180503 for：TASK #2500 【代码生成器】代码生成器开发一通用模板生成功能
	//update-begin-author:taoyan date:20180627 for:TASK #2817 【bug】代码生成器模板是否支持树类型隔离
	public static List<OnlineGenerateEnum> getOnlineGenerateEnum(String type,String version,boolean isTree){
		List<OnlineGenerateEnum> list = new ArrayList<OnlineGenerateEnum>();
		for(OnlineGenerateEnum item : OnlineGenerateEnum.values()) {
			if(item.getFormType().equals(type)&&item.getVersion().equals(version)&&(item.isSupportTreegrid()||!isTree)) {
				list.add(item);
			}
		}
		return list;
	}
	//update-end-author:taoyan date:20180627 for:TASK #2817 【bug】代码生成器模板是否支持树类型隔离
	
}
