package org.jeecgframework.web.cgform.service.impl.generate;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.jeecgframework.codegenerate.database.JeecgReadTable;
import org.jeecgframework.core.util.PropertiesUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.web.cgform.common.CgAutoListConstant;
import org.jeecgframework.web.cgform.entity.config.CgFormHeadEntity;
import org.jeecgframework.web.cgform.service.build.DataBaseService;
import org.jeecgframework.web.cgform.service.cgformftl.CgformFtlServiceI;
import org.jeecgframework.web.cgform.service.config.CgFormFieldServiceI;
import org.jeecgframework.web.cgform.util.TemplateUtil;
import org.jeecgframework.web.system.service.CacheServiceI;
import org.jeecgframework.web.system.service.SystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;

@Component("templetContextWord")
public class TempletContextWord {
	private static final Logger log = LoggerFactory.getLogger(TempletContextWord.class);
	
	@Autowired
	private CgFormFieldServiceI cgFormFieldService;
	@Autowired
	private DataBaseService dataBaseService;
	@Autowired
	private  SystemService systemService;
	@Autowired
	private CgformFtlServiceI cgformFtlService;
	
	@Resource(name = "freemarkerWord")
	private Configuration freemarker;
	
	private Map<String, TemplateDirectiveModel> tags;
	
	private static final String ENCODING = "UTF-8";
	@Autowired
	private CacheServiceI cacheService;
	
	/**
	 * 系统模式：
	 * PUB-生产（使用ehcache）
	 * DEV-开发
	 */
	private static String _sysMode = null;
	static{
		PropertiesUtil util = new PropertiesUtil("sysConfig.properties");
		_sysMode = util.readProperty(CgAutoListConstant.SYS_MODE_KEY);
	}

	@PostConstruct
	public void init() {
		if (tags == null)
			return;
		for (String key : tags.keySet()) {
			freemarker.setSharedVariable(key, tags.get(key));
		}
	}

	public Locale getLocale() {
		return freemarker.getLocale();
	}

	public Template getTemplate(String tableName, String ftlVersion) {
		Template template = null;
		if (tableName == null) {
			return null;
		}
		//update--begin-------author:zhoujf------date:20180614----for:TASK #2787 【online 代码生成器】支持生成word 布局模板---------------
//		String oldTableName = tableName;
//        update-start--Author:zhangguoming  Date:20140922 for：根据ftlVersion动态读取模板
//        if (ftlVersion != null && ftlVersion.length() > 0) {
//            tableName = tableName + "&ftlVersion=" + ftlVersion;
//        }
//        update-end--Author:zhangguoming  Date:20140922 for：根据ftlVersion动态读取模板
        try {
//			if(CgAutoListConstant.SYS_MODE_DEV.equalsIgnoreCase(_sysMode)){//开发模式
				template = freemarker.getTemplate(tableName,freemarker.getLocale(), ENCODING);
//			}else if(CgAutoListConstant.SYS_MODE_PUB.equalsIgnoreCase(_sysMode)){//生产模式（缓存）
//				//获取版本号
//		    	String version = cgFormFieldService.getCgFormVersionByTableName(oldTableName);
//				template = getTemplateFromCache(tableName, ENCODING,version);
//			}else{
//				throw new RuntimeException("sysConfig.properties的freeMarkerMode配置错误：(PUB:生产模式，DEV:开发模式)");
//			}
				//update--begin-------author:zhoujf------date:20180614----for:TASK #2787 【online 代码生成器】支持生成word 布局模板---------------
			return template;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}
	
	/**
	 * 从缓存中读取ftl模板
	 * @param template
	 * @param encoding
	 * @return
	 */
	public Template getTemplateFromCache(String tableName,String encoding,String version){
		Template template =  null;
		try {
			//cache的键：类名.方法名.参数名
			String cacheKey = this.getClass().getSimpleName()+".getTemplateFormCache."+tableName+"."+version;
			Object templateObj = cacheService.get(CacheServiceI.SYSTEM_BASE_CACHE,cacheKey);
			if(templateObj==null){
				template = freemarker.getTemplate(tableName,freemarker.getLocale(), ENCODING);
				cacheService.put(CacheServiceI.SYSTEM_BASE_CACHE,cacheKey,template);
				log.info("--setTemplateFromCache-------cacheKey: [{}]-------------",cacheKey);
			}else{
				template = (Template)templateObj;
				log.info("--getTemplateFromCache-------cacheKey: [{}]-------------",cacheKey);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return template;
	}

	public Configuration getFreemarker() {
		return freemarker;
	}

	public void setFreemarker(Configuration freemarker) {
		this.freemarker = freemarker;
	}

	public Map<String, TemplateDirectiveModel> getTags() {
		return tags;
	}

	public void setTags(Map<String, TemplateDirectiveModel> tags) {
		this.tags = tags;
	}
	/**
	 * 预处理一些
	 * @param html
	 * @param cgFormHead
	 * @return
	 */
	public String autoFormGenerateHtml(String tableName,String id,String mode) {
		String html = autoFormViewGenerateHtml(tableName, id, mode);
		//update--begin-------author:zhoujf------date:20180614----for:TASK #2787 【online 代码生成器】支持生成word 布局模板---------------
//		html = html.replace("<html xmlns:m=\"http://schemas.microsoft.com/office/2004/12/omml\">", "<%@ page language=\"java\" import=\"java.util.*\" contentType=\"text/html; charset=UTF-8\" pageEncoding=\"UTF-8\"%><br><%@include file=\"/context/mytags.jsp\"%>");
		//update--end-------author:zhoujf------date:20180614----for:TASK #2787 【online 代码生成器】支持生成word 布局模板---------------
//		html = replaceAddJSP(html);
		html = html.replace("cgFormBuildController.do?saveOrUpdate", "@@{entityName?uncap_first}Controller.do?doAdd");
//		html = html.replace("<input id=\"jformHiddenField\" name=\"jformHiddenField\" type=\"text\" value=\"@@@{@@{entityName?uncap_first}.jformHiddenField}\" style=\"width: 150px\" class=\"inputxt\" >", "");
		html = html.replace("@@@", "${'$'}");
		html = html.replace("@{onlineCodeGenereateEntityKey@", "${'$'}{${entityName?uncap_first}Page");
		html = html.replace("onlineCodeGenereateEntityKey", "${entityName?uncap_first}Page");
		html = html.replace("@@", "$");
		return html;
	}
	
	
	/**
	 * 通过在线表单预览功能生成html
	 * @param tableName
	 * @param id
	 * @param mode
	 * @return
	 */
	private String autoFormViewGenerateHtml(String tableName,String id,String mode){
        Map<String, Object> data = new HashMap<String, Object>();
		String templateName=tableName+"_";
		TemplateUtil.TemplateType templateType = TemplateUtil.TemplateType.LIST;
		if(StringUtils.isBlank(id)){
			templateName+=TemplateUtil.TemplateType.ADD.getName();
			templateType=TemplateUtil.TemplateType.ADD;
		}else if("read".equals(mode)){
			templateName+=TemplateUtil.TemplateType.DETAIL.getName();
			templateType=TemplateUtil.TemplateType.DETAIL;
		}else{
			templateName+=TemplateUtil.TemplateType.UPDATE.getName();
			templateType=TemplateUtil.TemplateType.UPDATE;
		}
		//获取版本号
        String version = cgFormFieldService.getCgFormVersionByTableName(tableName);
        //装载表单配置
    	Map configData = cgFormFieldService.getFtlFormConfig(tableName,version);
    	data = new HashMap(configData);
    	//如果该表是主表查出关联的附表
    	CgFormHeadEntity head = (CgFormHeadEntity)data.get("head");
        Map<String, Object> dataForm = new HashMap<String, Object>();
        if(StringUtils.isNotEmpty(id)){
        	dataForm = dataBaseService.findOneForJdbc(tableName, id);
        }
        Iterator it=dataForm.entrySet().iterator();
	    while(it.hasNext()){
	    	Map.Entry entry=(Map.Entry)it.next();
	        String ok=(String)entry.getKey();
	        Object ov=entry.getValue();
	        data.put(ok, ov);
	    }
        Map<String, Object> tableData  = new HashMap<String, Object>();
        //获取主表或单表表单数据
        tableData.put(tableName, dataForm);
        //获取附表表表单数据
    	if(StringUtils.isNotEmpty(id)){
	    	if(head.getJformType()==CgAutoListConstant.JFORM_TYPE_MAIN_TALBE){
		    	String subTableStr = head.getSubTableStr();
		    	if(StringUtils.isNotEmpty(subTableStr)){
		    		 String [] subTables = subTableStr.split(",");
		    		 List<Map<String,Object>> subTableData = new ArrayList<Map<String,Object>>();
		    		 for(String subTable:subTables){
			    			subTableData = cgFormFieldService.getSubTableData(tableName,subTable,id);
			    			tableData.put(subTable, subTableData);
		    		 }
		    	}
	    	}
    	}
    	//装载单表/(主表和附表)表单数据
    	data.put("data", tableData);
    	data.put("id", id);
    	data.put("head", head);
    	//update--begin-------author:zhoujf------date:20180614----for:TASK #2787 【online 代码生成器】支持生成word 布局模板---------------
    	data.put("basePath", "<%=basePath%>");
		String content =null;
		content = getTableTemplate(templateName,data);
		//增加jsp 标签
		String jspHead = "<%@ page language=\"java\" contentType=\"text/html; charset=UTF-8\" pageEncoding=\"UTF-8\"%>\r\n<%@include file=\"/context/mytags.jsp\"%>\r\n";
		return jspHead+content;
		//update--end-------author:zhoujf------date:20180614----for:TASK #2787 【online 代码生成器】支持生成word 布局模板---------------
	}
	
	/**
	 * 替换#{} 为${}
	 * @param cgformJspHtml
	 * @return
	 */
	private String replaceAddJSP(String cgformJspHtml) {
		String key,realKey;
		while (cgformJspHtml.indexOf("#{") > 0) {
			key  = cgformJspHtml.substring(cgformJspHtml.indexOf("#{"),cgformJspHtml.indexOf("}",cgformJspHtml.indexOf("#{"))+1);
			realKey = key.substring(2, key.length() -1);
			cgformJspHtml = cgformJspHtml.replace(key, "<input id='"+JeecgReadTable.formatField(realKey)+"' name='"+JeecgReadTable.formatField(realKey)+"' type='text' value='${'$'}{${entityName?uncap_first}."+JeecgReadTable.formatField(realKey)+"}' style='width: 150px' class='inputxt' >");
		}
		return cgformJspHtml;
	}
	
	/**
	 * 获取表配置中存储的风格模板
	 * @param templateName
	 * @param request
	 * @param data
	 * @return
	 */
	private String getTableTemplate(String templateName,Map data){
		StringWriter stringWriter = new StringWriter();
		BufferedWriter writer = new BufferedWriter(stringWriter);
		String ftlVersion = oConvertUtils.getString(data.get("version"));
		Template template = getTemplate(templateName, ftlVersion);
		try {
			template.process(data, writer);
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stringWriter.toString();
	}
}
