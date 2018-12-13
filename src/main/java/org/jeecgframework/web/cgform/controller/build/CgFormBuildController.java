package org.jeecgframework.web.cgform.controller.build;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.enums.SysThemesEnum;
import org.jeecgframework.core.online.util.FreemarkerHelper;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.IpUtil;
import org.jeecgframework.core.util.LogUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.SysThemesUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.web.cgform.common.CgAutoListConstant;
import org.jeecgframework.web.cgform.common.CommUtils;
import org.jeecgframework.web.cgform.engine.TempletContext;
import org.jeecgframework.web.cgform.entity.config.CgFormHeadEntity;
import org.jeecgframework.web.cgform.entity.config.CgSubTableVO;
import org.jeecgframework.web.cgform.entity.template.CgformTemplateEntity;
import org.jeecgframework.web.cgform.entity.upload.CgUploadEntity;
import org.jeecgframework.web.cgform.exception.BusinessException;
import org.jeecgframework.web.cgform.service.build.DataBaseService;
import org.jeecgframework.web.cgform.service.config.CgFormFieldServiceI;
import org.jeecgframework.web.cgform.service.template.CgformTemplateServiceI;
import org.jeecgframework.web.cgform.util.FillRuleUtil;
import org.jeecgframework.web.cgform.util.PublicUtil;
import org.jeecgframework.web.cgform.util.TemplateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @ClassName: formBuildController
 * @Description: 读取模板生成填报表单（添加、修改）-执行表单数据添加和修改操作
 * @author 周俊峰
 */
//@Scope("prototype")
@Controller
@RequestMapping("/cgFormBuildController")
public class CgFormBuildController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(CgFormBuildController.class);
	
	@Autowired
	private TempletContext templetContext;
	@Autowired
	private DataBaseService dataBaseService;
	@Autowired
	private CgformTemplateServiceI cgformTemplateService;
	@Autowired
	private CgFormFieldServiceI cgFormFieldService;

	//update-begin--Author:zhoujf  Date:20170310 for：TASK #1736 【改造】online、自定义表单，请求方式改造，采用restful方式实现（改成.do）--------------------
	//update-begin--Author:许国杰  Date:20151219 for：#813 【online表单】扩展出三个请求：独立的添加、查看、编辑请求，原来的保留
	@RequestMapping(value = "ftlForm/{tableName}/goAdd")
	public void goAdd(@PathVariable("tableName") String tableName,HttpServletRequest request,HttpServletResponse response) {
		 ftlForm(tableName,"",request,response);
	}
	@RequestMapping(value = "ftlForm/{tableName}/goAddButton")
	public void goAddButton(@PathVariable("tableName") String tableName,HttpServletRequest request,HttpServletResponse response) {
		 ftlForm(tableName,"onbutton",request,response);
	}
	@RequestMapping(value = "ftlForm/{tableName}/goUpdate")
	public void goUpdate(@PathVariable("tableName") String tableName,HttpServletRequest request,HttpServletResponse response) {
		 ftlForm(tableName,"",request,response);
	}
	@RequestMapping(value = "ftlForm/{tableName}/goUpdateButton")
	public void goUpdateButton(@PathVariable("tableName") String tableName,HttpServletRequest request,HttpServletResponse response) {
		 ftlForm(tableName,"onbutton",request,response);
	}
	@RequestMapping(value = "ftlForm/{tableName}/goDetail")
	public void goDatilFtlForm(@PathVariable("tableName") String tableName,HttpServletRequest request,HttpServletResponse response) {
		 ftlForm(tableName,"read",request,response);
	}
	//update-end--Author:许国杰  Date:20151219 for：#813 【online表单】扩展出三个请求：独立的添加、查看、编辑请求，原来的保留
	//update-end--Author:zhoujf  Date:20170310 for：TASK #1736 【改造】online、自定义表单，请求方式改造，采用restful方式实现（改成.do）--------------------
	
	//add-start--Author:scott Date:20160301 for：online表单移动样式单独配置
	/**
	 * Online表单移动端，访问页面
	 */
	@RequestMapping(params = "mobileForm")
	public void mobileForm(HttpServletRequest request,HttpServletResponse response) {
		String tableName =request.getParameter("tableName");
		String sql = "select form_template_mobile from cgform_head where table_name = ?";
		Map<String, Object> mp = cgFormFieldService.findOneForJdbc(sql,tableName);
		if(mp.containsKey("form_template_mobile") && oConvertUtils.isNotEmpty(mp.get("form_template_mobile"))){
			String urlTemplateName=request.getParameter("olstylecode");
			if(oConvertUtils.isEmpty(urlTemplateName)){
				request.setAttribute("olstylecode", mp.get("form_template_mobile").toString().trim());
			}
		}
		//update-begin--Author:zhoujf  Date:20170310 for：TASK #1736 【改造】online、自定义表单，请求方式改造，采用restful方式实现（改成.do）--------------------
		ftlForm(tableName,"",request,response);
		//update-end--Author:zhoujf  Date:20170310 for：TASK #1736 【改造】online、自定义表单，请求方式改造，采用restful方式实现（改成.do）--------------------
		
	}
	//add-end--Author:scott Date:20160301 for：online表单移动样式单独配置
	
	//add-begin--Author:Yandong  Date:20180105 for：TASK #2469 【online改造】增加填值规则字段
	private void putFormData(List<Map<String,Object>> list,Map<String,Object> dataForm){
		if(list!=null && !list.isEmpty()){
			for (Map<String, Object> column : list) {
				Object value=column.get("fill_rule_code");
				if(value!=null && !value.equals("")){
					dataForm.put(column.get("field_name").toString(), FillRuleUtil.executeRule(value.toString()));
				}
			}
		}
	}
	//add-end--Author:Yandong  Date:20180105 for：TASK #2469 【online改造】增加填值规则字段
	
	//add-begin--Author:Yandong  Date:20180521 for：TASK #2723 【bug】online扩展参数用法问题
	/**
	 * 过滤online扩展参数中value属性
	 * @param list 表单列集合
	 */
	private void replaceExtendJson(List<Map<String,Object>> list){
		if(list!=null && !list.isEmpty()){
			for (Map<String, Object> column : list) {
				Object extendJson=column.get("extend_json");
				if(extendJson!=null && !extendJson.equals("")){
					String reg="value=\"[\\S]*\" ";
					column.put("extend_json", extendJson.toString().replaceAll(reg,""));
				}
			}
		}
	}
	//add-end--Author:Yandong  Date:20180521 for：TASK #2723 【bug】online扩展参数用法问题
	
	//update-begin--Author:zhoujf  Date:20170310 for：TASK #1736 【改造】online、自定义表单，请求方式改造，采用restful方式实现（改成.do）--------------------
	
	
	//update-begin--Author:scott----------Date:20180604----------for：Online添加页面，select\radio\checkbox 支持默认值设置--------------------
	/**
	 * 设置页面字典类型的默认值
	 * 类型： select、checkbox、radio
	 * @param list
	 * @param dataForm
	 */
	private void initAddDictTagDefaultVal(List<Map<String,Object>> list,Map<String, Object> dataForm){
		if(list!=null && !list.isEmpty()){
			for (Map<String, Object> column : list) {
				Object extendJson = column.get("extend_json");
				Object show_type = column.get("show_type");
				if(oConvertUtils.isNotEmpty(extendJson) && oConvertUtils.isNotEmpty(show_type) && "radio|checkbox|list".contains(show_type.toString())){
					 Pattern p = Pattern.compile("value=\"[\\S]*\" ");
				     Matcher m = p.matcher(extendJson.toString());
				     String dfVal = "";
				     while(m.find()) {
				    	 dfVal = m.group();
				     }
				     dfVal = dfVal.replace("value=","").replace("\"","").trim();
					 dataForm.put(column.get("field_name").toString(),dfVal);
					 logger.debug("--------------online添加页面字典类型默认值初始化----------field_name:{} ,dfVal:{} ,show_type :{}",
							 					new Object[] { column.get("field_name").toString(), dfVal, show_type.toString() });
				}
			}
		}
	}
	//update-end--Author:scott----------Date:20180604----------for：Online添加页面，select\radio\checkbox 支持默认值设置--------------------
	
	
	/**
	 * form表单页面跳转
	 */
//	@SuppressWarnings("unchecked")
//	@RequestMapping(params = "ftlForm")
	private void ftlForm(String tableName,String mode,HttpServletRequest request,HttpServletResponse response) {
		try {
			long start = System.currentTimeMillis();
//			String tableName =request.getParameter("tableName");
			//update-beign-author:taoYan date:20180705 for:TASK #2894 【bug】online模块，js引用全部改成标签方式
			String lang = (String)request.getSession().getAttribute("lang");
			if(oConvertUtils.isEmpty(lang)){
				lang = "zh-cn";
			}
			//update-end-author:taoYan date:20180705 for:TASK #2894 【bug】online模块，js引用全部改成标签方式
	        Map<String, Object> data = new HashMap<String, Object>();
	        String id = request.getParameter("id");
			//update-begin--Author:张忠亮  Date:20150707 for：online表单风格加入录入、编辑、列表、详情页面设置
//			String mode=request.getParameter("mode");
			//update-begin--Author:gengjiajia  Date:20160809 for：TASK #1214 online表单一个表，支持多个配置,还原真实表名
			String tablename = PublicUtil.replaceTableName(tableName);
			String templateName=tablename+"_";
			//String templateName=tableName+"_";
			//update-end--Author:gengjiajia  Date:20160809 for：TASK #1214 online表单一个表，支持多个配置,还原真实表名
			
			//update-begin--Author:scott  Date:20170804 for：online表单维护页面，添加页面带id参数报错处理--------------------
			//update-begin--Author:gj_shaojc  Date:20180404 for：TASK #2586 【问题确认】online 自定义word模板---------
//			Map<String, Object> dataForm = new HashMap<String, Object>();
//	        if(StringUtils.isNotEmpty(id)){
//	        	//update-begin--Author:gengjiajia  Date:20160809 for：TASK #1214 online表单一个表，支持多个配置,还原真实表名
//	        	dataForm = dataBaseService.findOneForJdbc(tablename, id);
//	        	//dataForm = dataBaseService.findOneForJdbc(tableName, id);
//	        	//update-end--Author:gengjiajia  Date:20160809 for：TASK #1214 online表单一个表，支持多个配置,还原真实表名
//	        	//update-begin--Author:zhoujf  Date:20151223 for：恢复--------------------
//		        if(dataForm!=null){
//		        	Iterator it=dataForm.entrySet().iterator();
//				    while(it.hasNext()){
//				    	Map.Entry entry=(Map.Entry)it.next();
//				        String ok=(String)entry.getKey();
//				        Object ov=entry.getValue();
//				        data.put(ok, ov);
//				    }
//		        }else{
//		        	logger.info("online表单【"+tablename+"】【"+id+"】不存在");
//		        	id = null;
//		        	dataForm = new HashMap<String, Object>();
//		        }
//		      //update-begin--Author:zhoujf  Date:20151223 for：恢复--------------------
//	        }
			//update-end--Author:gj_shaojc  Date:20180404 for：TASK #2586 【问题确认】online 自定义word模板---------
	        //update-end--Author:scott  Date:20170804 for：online表单维护页面，添加页面带id参数报错处理-------------------
	        
			//update-begin--Author:张忠亮  Date:20151019 for：url中加入olstylecode 可指定风格
			TemplateUtil.TemplateType templateType=TemplateUtil.TemplateType.LIST;
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
	    	//update-begin--Author:gj_shaojc  Date:20180404 for：TASK #2586 【问题确认】online 自定义word模板---------
	    	Map<String, Object> dataForm = new HashMap<String, Object>();
	    	if(StringUtils.isNotEmpty(id)){
	        	dataForm = dataBaseService.findOneForJdbc(tablename, id);
		        if(dataForm!=null){
		        	Iterator it=dataForm.entrySet().iterator();
				    while(it.hasNext()){
				    	Map.Entry entry=(Map.Entry)it.next();
				        String ok=(String)entry.getKey();
				        Object ov=entry.getValue();
				        //update-begin--Author:Yandong----  Date:20180521 ----for：TASK #2727 【online问题】UE编辑器图片 回显问题----blob类型报错 图片上传问题-----
				        if(ov instanceof byte[]) {
				        	ov=new String((byte[])ov,"utf-8");
				        	entry.setValue(ov);
				        }
				        //update-end--Author:Yandong----  Date:20180521 ----for：TASK #2727 【online问题】UE编辑器图片 回显问题----blob类型报错 图片上传问题-----
						data.put(ok, ov);
				    }
		        }else{
		        	logger.info("online表单【"+tablename+"】【"+id+"】不存在");
		        	id = null;
		        	dataForm = new HashMap<String, Object>();
		        }
		    }
	    	//update-end--Author:gj_shaojc  Date:20180404 for：TASK #2586 【问题确认】online 自定义word模板---------
	    	//如果该表是主表查出关联的附表
	    	CgFormHeadEntity head = (CgFormHeadEntity)data.get("head");
	      
	        Map<String, Object> tableData  = new HashMap<String, Object>();
	        //获取主表或单表表单数据
	      //update-begin--Author:gengjiajia  Date:20160809 for：TASK #1214 online表单一个表，支持多个配置,还原真实表名
	        
	        //update-begin--Author:Yandong  Date:20180105 for：TASK #2469 【online改造】增加填值规则字段
			if(StringUtils.isBlank(id)){
				//update-begin--Author:scott----------Date:20180604----------for：Online添加页面，select\radio\checkbox 支持默认值设置--------------------
				//Online添加页面，select\radio\checkbox 支持默认值设置
				initAddDictTagDefaultVal((List<Map<String,Object>>)data.get("columns"),dataForm);
				//update-end--Author:scott------------Date:20180604----------for：Online添加页面，select\radio\checkbox 支持默认值设置--------------------
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				logger.info("============================填值规则开始时间:"+sdf.format(new Date())+"==============================");
				long startTime = System.currentTimeMillis();
				//给主表字段加默认值
				putFormData((List<Map<String,Object>>) data.get("columns"),dataForm);
				putFormData((List<Map<String,Object>>) data.get("columnhidden"),dataForm);
				//子表加默认值
				String subTableStr = head.getSubTableStr();
				if(StringUtils.isNotEmpty(subTableStr)){
					String [] subTables = subTableStr.split(",");
					Map<String,Object> subDataForm=null;
					List<Map<String,Object>> subTableData =null;
					Map<String, Object> field = (Map<String, Object>) data.get("field");
					for(String subTable:subTables){
						CgSubTableVO subTableVO=(CgSubTableVO) field.get(subTable);
						subTableData=new ArrayList<Map<String,Object>>();
						subDataForm=new HashMap<String, Object>();
						//update-begin--Author:scott----------Date:20180604----------for：Online添加页面，select\radio\checkbox 支持默认值设置--------------------
						//Online添加页面，select\radio\checkbox控件， 支持默认值设置
						initAddDictTagDefaultVal((List<Map<String,Object>>)subTableVO.getFieldList(),subDataForm);
						//update-end--Author:scott----------Date:20180604----------for：Online添加页面，select\radio\checkbox 支持默认值设置--------------------
						putFormData(subTableVO.getFieldList(),subDataForm);
						putFormData(subTableVO.getHiddenFieldList(),subDataForm);
						subTableData.add(subDataForm);
						tableData.put(subTable,subTableData);
					}
				}
				long endTime = System.currentTimeMillis();
				logger.info("================================填值规则结束时间:"+sdf.format(new Date())+"==============================");
				logger.info("================================填值规则耗时:"+(endTime-startTime)+"ms==============================");
			}
			//update-end--Author:Yandong  Date:20180105 for：TASK #2469 【online改造】增加填值规则字段

	        
	        tableData.put(tablename, dataForm);
	        //tableData.put(tableName, dataForm);
	      //update-end--Author:gengjiajia  Date:20160809 for：TASK #1214 online表单一个表，支持多个配置,还原真实表名
	        //获取附表表表单数据
	    	if(StringUtils.isNotEmpty(id)){
	    		//add-begin--Author:Yandong  Date:20180521 for：TASK #2723 【bug】online扩展参数用法问题
	    		//过滤扩展参数value属性
	    		replaceExtendJson((List<Map<String,Object>>) data.get("columns"));
	    		replaceExtendJson((List<Map<String,Object>>) data.get("columnhidden"));
	    		//add-end--Author:Yandong  Date:20180521 for：TASK #2723 【bug】online扩展参数用法问题
		    	if(head.getJformType()==CgAutoListConstant.JFORM_TYPE_MAIN_TALBE){
			    	String subTableStr = head.getSubTableStr();
			    	if(StringUtils.isNotEmpty(subTableStr)){
			    		 String [] subTables = subTableStr.split(",");
			    		 List<Map<String,Object>> subTableData = new ArrayList<Map<String,Object>>();
			    		 Map<String, Object> field = (Map<String, Object>) data.get("field");
			    		 for(String subTable:subTables){
			    			subTableData = cgFormFieldService.getSubTableData(tableName,subTable,id);
			    			tableData.put(subTable, subTableData);
			    			CgSubTableVO subTableVO=(CgSubTableVO) field.get(subTable);
			    			//add-begin--Author:Yandong  Date:20180521 for：TASK #2723 【bug】online扩展参数用法问题
			    			replaceExtendJson(subTableVO.getFieldList());
			    			replaceExtendJson(subTableVO.getHiddenFieldList());
			    			//add-end--Author:Yandong  Date:20180521 for：TASK #2723 【bug】online扩展参数用法问题
			    		 }
			    	}
		    	}
	    	}
	    	data.put("lang", lang);//国际化
	    	//装载单表/(主表和附表)表单数据
	    	data.put("data", tableData);
	    	data.put("id", id);
	    	//update-begin--Author:钟世云  Date:20150610 for：online支持树配置--------------------
	    	data.put("head", head);
			//update-end--Author:钟世云  Date:20150610 for：online支持树配置----------------------
	    	
	    	//页面样式js引用
	    	data.put(CgAutoListConstant.CONFIG_IFRAME, getHtmlHead(request));
	    	//装载附件信息数据
	    	pushFiles(data, id);
	    	pushImages(data, id);
	    	
	    	//增加basePath
	    	String basePath = request.getScheme()+"://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	    	data.put(CgAutoListConstant.BASEPATH, basePath);
	    	//update--begin--author:scott date:20170628 for: 需要根据浏览器IE进行切换 提示消息方式，layer/easyui
	    	data.put("brower_type", ContextHolderUtils.getSession().getAttribute("brower_type"));
	        //update--end--author:scott date:20170628 for: T 需要根据浏览器IE进行切换 提示消息方式，layer/easyui
			String content =null;
			response.setContentType("text/html;charset=utf-8");
			//update-begin--Author:张忠亮  Date:20151019 for：url中加入olstylecode 可指定风格
			String urlTemplateName = request.getParameter("olstylecode");
			//update-begin---author:scott---date:20160301-----for:online表单移动样式单独配置-----
			if(oConvertUtils.isEmpty(urlTemplateName)){
				urlTemplateName = (String) request.getAttribute("olstylecode");
			}
			//update-end---author:scott---date:20160301-----for:online表单移动样式单独配置-----
			
			if(StringUtils.isNotBlank(urlTemplateName)){
				data.put("this_olstylecode",urlTemplateName);
				LogUtil.debug("-------------urlTemplateName-----------"+urlTemplateName);
				content=getUrlTemplate(urlTemplateName,templateType,data);
			}else{
				data.put("this_olstylecode",head.getFormTemplate());
				LogUtil.debug("-------------formTemplate-----------"+head.getFormTemplate());
				content=getTableTemplate(templateName,request,data);
			}
			//update-end--Author:张忠亮  Date:20151019 for：url中加入olstylecode 可指定风格
			response.getWriter().print(content);
			response.getWriter().flush();
			long end = System.currentTimeMillis();
			logger.debug("自定义表单生成耗时："+(end-start)+" ms");
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				response.getWriter().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	//update-end--Author:zhoujf  Date:20170310 for：TASK #1736 【改造】online、自定义表单，请求方式改造，采用restful方式实现（改成.do）--------------------
	
	
//update-begin--Author:张忠亮  Date:20151020 for：url中加入olstylecode 可指定风格 代码优化
	/**
	 * 获取url指定模板
	 * @param templateName
	 * @param templateType
	 * @param dataMap
	 * @return
	 */
	private String getUrlTemplate(String templateName,TemplateUtil.TemplateType templateType,Map dataMap){
		String content=null;
		CgformTemplateEntity entity=cgformTemplateService.findByCode(templateName);
		if(entity!=null){
			FreemarkerHelper viewEngine = new FreemarkerHelper();
			dataMap.put("DictData", ApplicationContextUtil.getContext().getBean("dictDataTag"));
			content = viewEngine.parseTemplate(TemplateUtil.getTempletPath(entity,0, templateType), dataMap);
		}
		return content;
	}

	/**
	 * 获取表配置中存储的风格模板
	 * @param templateName
	 * @param request
	 * @param data
	 * @return
	 */
	private String getTableTemplate(String templateName,HttpServletRequest request,Map data){
		StringWriter stringWriter = new StringWriter();
		BufferedWriter writer = new BufferedWriter(stringWriter);
		//如果URL请求有ftlVersion参数，表示用户指定word模板渲染表单
		String wordFtlVersion =request.getParameter("ftlVersion");
		Template template = templetContext.getTemplate(templateName, wordFtlVersion);
		try {
			//update-begin---author:scott---date:20150118-----for:修改linux时间格式不对问题-------------
			template.setDateTimeFormat("yyyy-MM-dd HH:mm:ss");  
			template.setDateFormat("yyyy-MM-dd");  
			template.setTimeFormat("HH:mm:ss");
			//update-end---author:scott---date:20150118-----for:修改linux时间格式不对问题---------------
			template.process(data, writer);
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stringWriter.toString();
	}
	
//update--begin-------author:zhoujf------date:20170315----for:TASK #1736 【改造】online、自定义表单，请求方式改造，采用restful方式实现（改成.do）(basePath问题)---------------
//update-end--Author:张忠亮  Date:20151020 for：url中加入olstylecode 可指定风格 代码优化
	private String getHtmlHead(HttpServletRequest request){
		HttpSession session = ContextHolderUtils.getSession();
		String lang = (String)session.getAttribute("lang");
		if(lang==null||lang.length()<=0){
			lang = "zh-cn";
		}
		StringBuilder sb= new StringBuilder("");
		SysThemesEnum sysThemesEnum = SysThemesUtil.getSysTheme(request);
		String basePath = request.getScheme()+"://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/jquery/jquery-1.8.3.js\"></script>");
		sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/jquery-plugs/i18n/jquery.i18n.properties.js\"></script>");
		sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/tools/dataformat.js\"></script>");
		sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\""+basePath+"/plug-in/accordion/css/accordion.css\">");
		sb.append(SysThemesUtil.getEasyUiTheme(sysThemesEnum,basePath));
		//update-begin--Author:scott ---  Date:20170406 ------ for：online一对多样式加载未按照风格切换--------
		sb.append(SysThemesUtil.getEasyUiIconTheme(sysThemesEnum));
		//sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\""+basePath+"/plug-in/accordion/css/icons.css\">");
		//update-end--Author:scott --- Date:20170406 ------ for：online一对多样式加载未按照风格切换--------
		sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/easyui/jquery.easyui.min.1.3.2.js\"></script>");
		sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/easyui/locale/zh-cn.js\"></script>");
		sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/tools/syUtil.js\"></script>");
		sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/My97DatePicker/WdatePicker.js\"></script>");
//		sb.append("<link rel=\"stylesheet\" href=\"plug-in/tools/css/common.css\" type=\"text/css\"></link>");
		//common.css
		sb.append(SysThemesUtil.getCommonTheme(sysThemesEnum,basePath));
//		sb.append("<script type=\"text/javascript\" src=\"plug-in/lhgDialog/lhgdialog.min.js\"></script>");
		sb.append(SysThemesUtil.getLhgdialogTheme(sysThemesEnum,basePath));
		//update--begin--author:scott Date:20170304 for:替换layer风格提示框
		sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/layer/layer.js\"></script>");
		//update--end--author:scott Date:20170304 for:替换layer风格提示框
		sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/tools/curdtools.js\"></script>");
		sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/tools/easyuiextend.js\"></script>");
		sb.append(SysThemesUtil.getEasyUiMainTheme(sysThemesEnum,basePath));
		sb.append("<link rel=\"stylesheet\" href=\""+basePath+"/plug-in/uploadify/css/uploadify.css\" type=\"text/css\"></link>");
		sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/uploadify/jquery.uploadify-3.1.js\"></script>");
		sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/tools/Map.js\"></script>");
		sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/Validform/js/Validform_v5.3.1_min_zh-cn.js\"></script>");
		sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/Validform/js/Validform_Datatype_zh-cn.js\"></script>");
		sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/Validform/js/datatype_zh-cn.js\"></script>");
		sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/Validform/plugin/passwordStrength/passwordStrength-min.js\"></script>");
		//style.css
		sb.append(SysThemesUtil.getValidformStyleTheme(sysThemesEnum,basePath));
		//tablefrom.css
		sb.append(SysThemesUtil.getValidformTablefrom(sysThemesEnum,basePath));
		//update--begin-------author:zhoujf------date:20170316----for:TASK #1770 【删除】删除插件umeditor
		//uedit
		sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/ueditor/ueditor.config.js\"></script>");
		sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/ueditor/ueditor.all.js\"></script>");
		//update--end-------author:zhoujf------date:20170316----for:TASK #1770 【删除】删除插件umeditor
		
		return sb.toString();
	}
	//update--end-------author:zhoujf------date:20170315----for:TASK #1736 【改造】online、自定义表单，请求方式改造，采用restful方式实现（改成.do）(basePath问题)---------------

	
	/**
	 * 如果表单带有附件，则查询出来传递到页面
	 * @param data 传往页面的数据容器
	 * @param id 表单主键，用户查找附件数据
	 */
	private void pushFiles(Map<String, Object> data, String id) {
		List<CgUploadEntity> uploadBeans = cgFormFieldService.findByProperty(CgUploadEntity.class, "cgformId", id);
		List<Map<String,Object>> files = new ArrayList<Map<String,Object>>(0);
		for(CgUploadEntity b:uploadBeans){
			String title = b.getAttachmenttitle();//附件名
			String fileKey = b.getId();//附件主键
			String path = b.getRealpath();//附件路径
			String field = b.getCgformField();//表单中作为附件控件的字段
			Map<String, Object> file = new HashMap<String, Object>();
			file.put("title", title);
			file.put("fileKey", fileKey);
			file.put("path", path);
			file.put("field", field==null?"":field);
			files.add(file);
		}
		data.put("filesList", files);
	}
//update-start--Author: jg_huangxg  Date:20160113 for：TASK #824 【online开发】控件类型扩展增加一个图片类型 image
	/**
	 * 如果表单带有 附件(图片),则查询出来传递到页面
	 * @param data 传往页面的数据容器
	 * @param id 表单主键,用户查找附件数据
	 */
	private void pushImages(Map<String, Object> data, String id) {
		List<CgUploadEntity> uploadBeans = cgFormFieldService.findByProperty(CgUploadEntity.class, "cgformId", id);
		List<Map<String,Object>> images = new ArrayList<Map<String,Object>>(0);
		for(CgUploadEntity b:uploadBeans){
			String title = b.getAttachmenttitle();//附件名
			String fileKey = b.getId();//附件主键
			String path = b.getRealpath();//附件路径
			String field = b.getCgformField();//表单中作为附件控件的字段
			Map<String, Object> image = new HashMap<String, Object>();
			image.put("title", title);
			image.put("fileKey", fileKey);
			image.put("path", path);
			image.put("field", field==null?"":field);
			images.add(image);
		}
		data.put("imageList", images);
	}
//update-end--Author: jg_huangxg  Date:20160113 for：TASK #824 【online开发】控件类型扩展增加一个图片类型 image
	/**
	 * 保存或更新
	 * 
	 * @param jeecgDemo
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "saveOrUpdate")
	@ResponseBody
	public AjaxJson saveOrUpdate(HttpServletRequest request) throws Exception{
		String message = null;
		AjaxJson j = new AjaxJson();
		Map data = request.getParameterMap();
		if(data!=null){
			data = CommUtils.mapConvert(data);
			String tableName = (String)data.get("tableName");
			String id = (String)data.get("id");
			//打印测试
		    Iterator it=data.entrySet().iterator();
		    while(it.hasNext()){
		    	Map.Entry entry=(Map.Entry)it.next();
		        Object ok=entry.getKey();
		        Object ov=entry.getValue()==null?"":entry.getValue();
		        logger.debug("name:"+ok.toString()+";value:"+ov.toString());
		    }
		    if(StringUtils.isEmpty(id)){
			    //消除不是表的字段
			    String [] filterName = {"tableName","saveOrUpdate"};
			    data = CommUtils.attributeMapFilter(data,filterName);
			    //保存数据库
			    try {
			    	Object pkValue = null;
			    	pkValue = dataBaseService.getPkValue(tableName);
			    	data.put("id", pkValue);
			    	//--author：luobaoli---------date:20150615--------for: 处理service层抛出的异常
			    	try {
						dataBaseService.insertTable(tableName, data);
						j.setSuccess(true);
						message = "保存成功";
			    	}catch (Exception e) {
			    		j.setSuccess(false);
						message = "保存失败";
						e.printStackTrace();
			    	}
			    	//--author：luobaoli---------date:20150615--------for: 处理service层抛出的异常
				} catch (Exception e) {
					e.printStackTrace();
					j.setSuccess(false);
					message = e.getMessage();
				}
			}else{
				//消除不是表的字段
			    String [] filterName = {"tableName","saveOrUpdate","id"};
			    data = CommUtils.attributeMapFilter(data,filterName);
			    //更新数据库
			    try {
					int num = dataBaseService.updateTable(tableName, id, data);
					if (num>0) {
						j.setSuccess(true);
						message = "保存成功";
					}else {
						j.setSuccess(false);
						message = "保存失败";
					}
				} catch (Exception e) {
					e.printStackTrace();
					j.setSuccess(false);
					message = e.getMessage();
				}
				logger.info("["+IpUtil.getIpAddr(request)+"][online表单单表数据保存和更新]"+message+"表名："+tableName);
			}
		}
		j.setMsg(message);
		j.setObj(data);
		return j;
	}
	
	
	
	/**
	 * 保存或更新
	 * 
	 * @param jeecgDemo
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "saveOrUpdateMore")
	@ResponseBody
	public AjaxJson saveOrUpdateMore(HttpServletRequest request) throws Exception{
		String message = null;
		AjaxJson j = new AjaxJson();
		Map data = request.getParameterMap();
		if(data!=null){
			data = CommUtils.mapConvert(data);
			String tableName = (String)data.get("tableName");
			String id = (String)data.get("id");
			//打印测试
		    Iterator it=data.entrySet().iterator();
		    while(it.hasNext()){
		    	Map.Entry entry=(Map.Entry)it.next();
		        Object ok=entry.getKey();
		        Object ov=entry.getValue()==null?"":entry.getValue();
		        logger.debug("name:"+ok.toString()+";value:"+ov.toString());
		    }
		    Map<String,List<Map<String,Object>>> mapMore =CommUtils.mapConvertMore(data, tableName);
		    if(StringUtils.isEmpty(id)){
		    	logger.info("一对多添加!!!!!");
		    	try {
		    		Map<String, Object> result = dataBaseService.insertTableMore(mapMore, tableName);
		    		data.put("id", result.get("id"));
		    		j.setSuccess(true);
					message = "添加成功";
				} catch (BusinessException e) {
					e.printStackTrace();
					j.setSuccess(false);
					message = e.getMessage();
				}
		    	
			}else{
				logger.info("一对多修改!!!!!");
				try {
					dataBaseService.updateTableMore(mapMore, tableName);
					j.setSuccess(true);
					message = "更新成功";
				} catch (BusinessException e) {
					e.printStackTrace();
					j.setSuccess(false);
					message = e.getMessage();
				}
			}
		    logger.info("["+IpUtil.getIpAddr(request)+"][online表单一对多数据保存和更新]"+message+"表名："+tableName);
		}
		j.setMsg(message);
		j.setObj(data);
		return j;
	}
	
	
	/**
	 * 自定义按钮（触发对应的后台方法）
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "doButton")
	@ResponseBody
	public AjaxJson doButton(HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		try {
			String formId = request.getParameter("formId");
			String buttonCode = request.getParameter("buttonCode");
			String tableName = request.getParameter("tableName");
			String id = request.getParameter("id");
			Map<String,Object> data  = dataBaseService.findOneForJdbc(tableName, id);
			if(data!=null){
				//打印测试
			    Iterator it=data.entrySet().iterator();
			    while(it.hasNext()){
			    	Map.Entry entry=(Map.Entry)it.next();
			        Object ok=entry.getKey();
			        Object ov=entry.getValue()==null?"":entry.getValue();
			        logger.debug("name:"+ok.toString()+";value:"+ov.toString());
			    }
				data = CommUtils.mapConvert(data);
				dataBaseService.executeSqlExtend(formId, buttonCode, data);
				//update-start--Author:luobaoli  Date:20150630 for：  增加java增强逻辑处理
				dataBaseService.executeJavaExtend(formId, buttonCode, data);
				//update-end--Author:luobaoli  Date:20150630 for：  增加java增强逻辑处理
			}
			j.setSuccess(true);
			message = "操作成功";
			logger.info("["+IpUtil.getIpAddr(request)+"][online表单自定义按钮action触发]"+message+"表名："+tableName);
		} catch (Exception e) {
			e.printStackTrace();
			message = "操作失败";
		}
		j.setMsg(message);
		return j;
	}
	
}
