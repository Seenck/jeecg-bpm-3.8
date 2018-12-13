package org.jeecgframework.web.cgform.controller.generate;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.codegenerate.database.JeecgReadTable;
import org.jeecgframework.codegenerate.extcommon.CreateFileConfig;
import org.jeecgframework.codegenerate.extcommon.onetomany.CgformCodeOne2ManyExtCommonGenerate;
import org.jeecgframework.codegenerate.extcommon.single.CgformCodeExtCommonGenerate;
import org.jeecgframework.codegenerate.generate.CgformCodeGenerate;
import org.jeecgframework.codegenerate.generate.onetomany.CgformCodeGenerateOneToMany;
import org.jeecgframework.codegenerate.pojo.CreateFileProperty;
import org.jeecgframework.codegenerate.pojo.onetomany.CodeParamEntity;
import org.jeecgframework.codegenerate.pojo.onetomany.SubTableEntity;
import org.jeecgframework.codegenerate.util.CodeResourceUtil;
import org.jeecgframework.codegenerate.util.CodeStringUtils;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.enums.OnlineGenerateEnum;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.web.cgform.entity.button.CgformButtonEntity;
import org.jeecgframework.web.cgform.entity.button.CgformButtonSqlEntity;
import org.jeecgframework.web.cgform.entity.config.CgFormFieldEntity;
import org.jeecgframework.web.cgform.entity.config.CgFormHeadEntity;
import org.jeecgframework.web.cgform.entity.enhance.CgformEnhanceJavaEntity;
import org.jeecgframework.web.cgform.entity.enhance.CgformEnhanceJsEntity;
import org.jeecgframework.web.cgform.entity.generate.GenerateEntity;
import org.jeecgframework.web.cgform.entity.generate.GenerateSubListEntity;
import org.jeecgframework.web.cgform.service.build.DataBaseService;
import org.jeecgframework.web.cgform.service.button.CgformButtonServiceI;
import org.jeecgframework.web.cgform.service.button.CgformButtonSqlServiceI;
import org.jeecgframework.web.cgform.service.cgformftl.CgformFtlServiceI;
import org.jeecgframework.web.cgform.service.config.CgFormFieldServiceI;
import org.jeecgframework.web.cgform.service.enhance.CgformEnhanceJsServiceI;
import org.jeecgframework.web.cgform.service.impl.generate.TempletContextWord;
import org.jeecgframework.web.cgform.util.GenerateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @Title:CgGenerateController
 * @description:智能表单代码生成器[根据智能表单配置+代码生成设置->生成代码]
 * @author 赵俊夫
 * @date Sep 7, 2013 12:19:32 PM
 * @version V1.0
 */
@Controller
@RequestMapping("/generateController")
public class GenerateController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(GenerateController.class);
	
	@Autowired
	private CgFormFieldServiceI cgFormFieldService;
	@Autowired
	private CgformButtonServiceI cgformButtonService;
	@Autowired
	private CgformButtonSqlServiceI cgformButtonSqlService;
	@Autowired
	private CgformEnhanceJsServiceI cgformEnhanceJsService;
	@Autowired
	private TempletContextWord templetContextWord;
	@Autowired
	private DataBaseService dataBaseService;
	@Autowired
	private CgformFtlServiceI cgformFtlService;
	/**
	 * 代码生成配置页面
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "gogenerate")
	public ModelAndView gogenerate( CgFormHeadEntity cgFormHead,HttpServletRequest request) {
		if (StringUtil.isNotEmpty(cgFormHead.getId())) {
			cgFormHead = cgFormFieldService.getEntity(
					CgFormHeadEntity.class, cgFormHead.getId());
		}else{
			throw new RuntimeException("表单配置不存在");
		}
		String returnModelAndView = null;
		Map<String,String> entityNameMap = new HashMap<String,String>(0);
		if(cgFormHead.getJformType()==1 || cgFormHead.getJformType()==3){
			//如果是单表或者附表，则进入单表模型
			//update-begin-author:taoyan date:20180627 for:TASK #2817 【bug】代码生成器模板是否支持树类型隔离
			//update--begin--author:jiaqiankun Date:20180628 for:TASK#2859::【online】代码生成器的代码重构，抽取到service，不允许写在control
			request.setAttribute("jspModeList", GenerateUtil.getOnlineGenerateEnum("single","ext-common","Y".equals(cgFormHead.getIsTree())));// 默认老版本模板(IE8+/不支持移动/列表标签)
			//update--end--author:jiaqiankun Date:20180628 for:TASK#2859::【online】代码生成器的代码重构，抽取到service，不允许写在control
			//update-end-author:taoyan date:20180627 for:TASK #2817 【bug】代码生成器模板是否支持树类型隔离
			returnModelAndView = "jeecg/cgform/generate/single";
		}else{
			//如果是主表，则进入一对多模型
			List<CgFormHeadEntity> subTableList = new ArrayList<CgFormHeadEntity>();
			if(StringUtil.isNotEmpty(cgFormHead.getSubTableStr())){
				String[] subTables = cgFormHead.getSubTableStr().split(",");
				for(String subTable :subTables){
					CgFormHeadEntity subHead = cgFormFieldService.getCgFormHeadByTableName(subTable);
					subTableList.add(subHead);
					entityNameMap.put(subHead.getTableName(), JeecgReadTable.formatFieldCapital(subHead.getTableName()));
				}
			}
			//update-begin-author:taoyan date:20180627 for:TASK #2817 【bug】代码生成器模板是否支持树类型隔离
			//update--begin--author:jiaqiankun Date:20180628 for:TASK#2859::【online】代码生成器的代码重构，抽取到service，不允许写在control
			request.setAttribute("jspModeList", GenerateUtil.getOnlineGenerateEnum("onetomany","ext-common","Y".equals(cgFormHead.getIsTree())));// 默认老版本模板(IE8+/不支持移动/列表标签)
			//update--end--author:jiaqiankun Date:20180628 for:TASK#2859::【online】代码生成器的代码重构，抽取到service，不允许写在control
			//update-end-author:taoyan date:20180627 for:TASK #2817 【bug】代码生成器模板是否支持树类型隔离
			request.setAttribute("subTableList", subTableList);
			returnModelAndView = "jeecg/cgform/generate/one2many";
		}
		String projectPath = CodeResourceUtil.getProject_path();
		try{
		    Cookie[] cookies=request.getCookies();
		    if(cookies!=null){
		    for(int i=0;i<cookies.length;i++){
		        if(cookies[i].getName().equals("cookie_projectPath")){
		        String value =  cookies[i].getValue();
		        if(value!=null&&!"".equals(value)){
		        	projectPath=cookies[i].getValue();
		        	projectPath= URLDecoder.decode(projectPath, "UTF-8"); 
		            }
		         }
		        request.setAttribute("projectPath",projectPath);
		    }
		    }
		}catch(Exception e){
		    e.printStackTrace();
		}
		String entityName = JeecgReadTable.formatFieldCapital(cgFormHead.getTableName());
		entityNameMap.put(cgFormHead.getTableName(), entityName);
		request.setAttribute("cgFormHeadPage", cgFormHead);
		request.setAttribute("entityNames",entityNameMap );
		return new ModelAndView(returnModelAndView);
	}
	
	
	/**
	 * 根据模板类型查询模板风格
	 * @return
	 */
	@RequestMapping(params = "getOnlineTempletStyle")
	@ResponseBody
	public AjaxJson getOnlineTempletStyle(String type,String version,boolean supportTree){
		AjaxJson j = new AjaxJson();
		try {
			//update-begin-author:taoyan date:20180627 for:TASK #2817 【bug】代码生成器模板是否支持树类型隔离
			//update--begin--author:jiaqiankun Date:20180628 for:TASK#2859::【online】代码生成器的代码重构，抽取到service，不允许写在control
			List<OnlineGenerateEnum> list =  GenerateUtil.getOnlineGenerateEnum(type,version,supportTree);
			//update--end--author:jiaqiankun Date:20180628 for:TASK#2859::【online】代码生成器的代码重构，抽取到service，不允许写在control
			//update-end-author:taoyan date:20180627 for:TASK #2817 【bug】代码生成器模板是否支持树类型隔离
			List<Map<String,String>> mapList = new ArrayList<Map<String,String>>();
			Map<String,String> map = null;
			for(OnlineGenerateEnum item : list) {
				map = new HashMap<String, String>();
				map.put("code", item.getCode());
				map.put("desc", item.getDesc());
				mapList.add(map);
			}
			j.setObj(mapList);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("查询失败");
			e.printStackTrace();
		}
		return j;
	}
	/**
	 * 代码生成执行-单表
	 * @param generateEntity
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(params = "dogenerate")
	public void dogenerate(CgFormHeadEntity cgFormHead,GenerateEntity generateEntity,CreateFileProperty createFileProperty,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		//step.1 准备好智能表单的配置
		if (StringUtil.isNotEmpty(cgFormHead.getId())) {
			cgFormHead = cgFormFieldService.getEntity(
					CgFormHeadEntity.class, cgFormHead.getId());
			getCgformConfig(cgFormHead, generateEntity);
		}else{
			throw new RuntimeException("表单配置不存在");
		}
		AjaxJson j =  new AjaxJson();
		String tableName = generateEntity.getTableName();
		String ftlDescription = generateEntity.getFtlDescription();
		try {
			//step.2 判断表是否存在
			boolean tableexist = new JeecgReadTable().checkTableExist(tableName);
			if(tableexist){
				//update--begin-------author:zhoujf------date:20160713----for:online生成器用户自定义模板支持---------------
				//update-begin--Author:zhoujf  Date:20180503 for：TASK #2500 【代码生成器】代码生成器开发一通用模板生成功能
				String version = request.getParameter("version");
				OnlineGenerateEnum modeEnum = OnlineGenerateEnum.toEnum(createFileProperty.getJspMode(),version);
				//update-end--Author:zhoujf  Date:20180503 for：TASK #2500 【代码生成器】代码生成器开发一通用模板生成功能
				//update--begin-------author:zhoujf------date:20180614----for:TASK #2787 【online 代码生成器】支持生成word 布局模板---------------
				if(modeEnum!=null){
					if("ext".equals(modeEnum.getVersion())){
						CgformCodeGenerate generate = new CgformCodeGenerate(createFileProperty,generateEntity);
						generate.generateToFileUserDefined();
					}
					//update-begin--Author:zhoujf  Date:20180503 for：TASK #2500 【代码生成器】代码生成器开发一通用模板生成功能
					else if("ext-common".equals(modeEnum.getVersion())){
						CreateFileConfig createFileConfig = new CreateFileConfig();
						createFileConfig.setStylePath(createFileProperty.getJspMode().replace(".", File.separator));
						createFileConfig.setTemplateRootDir("src/main/resources/jeecg/ext-common-template");
						CgformCodeExtCommonGenerate g = new CgformCodeExtCommonGenerate(createFileConfig,generateEntity);
						g.generateToFile();
					}
					//update-end--Author:zhoujf  Date:20180503 for：TASK #2500 【代码生成器】代码生成器开发一通用模板生成功能
					j.setMsg(ftlDescription+"：功能生成成功，请刷新项目重启，菜单访问路径："+CodeStringUtils.getInitialSmall(generateEntity.getEntityName())+"Controller.do?list");
				}else if("system".equals(version)){
					CgformCodeGenerate generate = new CgformCodeGenerate(createFileProperty,generateEntity);
					createFileProperty.setJspMode(OnlineGenerateEnum.ONLINE_TABLE_SINGLE.getCode());
					//判断是否获取word模板
					Map<String,Object> cgformFtlEntity = cgformFtlService.getCgformFtlByTableName(tableName);
					if(cgformFtlEntity!=null){
						String formhtml = templetContextWord.autoFormGenerateHtml(tableName, null, null);
						generate.setCgformJspHtml(formhtml);
					}else{
						//update--begin-------author:zhoujf------date:20180703----for:TASK #2867 【体验】代码生成，如果选择word模板，需要给用户提醒，没有word模板情况下，不允许生成---------------
						j.setMsg("该表单没有激活的word模板不能生成");
						try {
							String projectPath = URLEncoder.encode(generateEntity.getProjectPath(), "UTF-8");
							Cookie cookie = new Cookie("cookie_projectPath",projectPath );				
							cookie.setMaxAge(60*60*24*30); //cookie 保存30天
							response.addCookie(cookie);
							response.getWriter().print(j.getJsonStr());
							response.getWriter().flush();
						} catch (IOException e) {
							e.printStackTrace();
						}finally{
							try {
								response.getWriter().close();
							} catch (Exception e2) {
							}
						}
						return;
						//update--end-------author:zhoujf------date:20180703----for:TASK #2867 【体验】代码生成，如果选择word模板，需要给用户提醒，没有word模板情况下，不允许生成---------------
					}
					generate.generateToFileUserDefined();
				}else{
					j.setMsg("代码生成器不支持该页面风格");
				}
				//update--end-------author:zhoujf------date:20180614----for:TASK #2787 【online 代码生成器】支持生成word 布局模板---------------
				//update--end-------author:zhoujf------date:20160713----for:online生成器用户自定义模板支持---------------
			}else{
				j.setMsg("表["+tableName+"] 在数据库中，不存在");
			}
			
		} catch (Exception e1) {
			e1.printStackTrace();
			j.setMsg(e1.getMessage());
			throw new RuntimeException(e1.getMessage());
		}
		try {
			String projectPath = URLEncoder.encode(generateEntity.getProjectPath(), "UTF-8");
			Cookie cookie = new Cookie("cookie_projectPath",projectPath );				
			cookie.setMaxAge(60*60*24*30); //cookie 保存30天
			response.addCookie(cookie);
			response.getWriter().print(j.getJsonStr());
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				response.getWriter().close();
			} catch (Exception e2) {
			}
		}
	}
	
	
	
	
	/**
	 * 代码生成执行-一对多
	 * @param generateEntity
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "dogenerateOne2Many")
	@ResponseBody
	public void dogenerateOne2Many(CodeParamEntity codeParamEntityIn,GenerateSubListEntity subTableListEntity,String jspMode,HttpServletRequest request,HttpServletResponse response){
		AjaxJson j =  new AjaxJson();
		//step.1 设置主表
		//从前台获取：codeParamEntityIn
		//step.2 设置子表集合
		//从前台获取：subTabParamIn,并设置外键字段
		try{
			//step.3 填充主表的所有智能表单配置
			String mainTable = codeParamEntityIn.getTableName();
			//主表的智能表单配置
			GenerateEntity mainG = new GenerateEntity();
			mainG.setProjectPath(subTableListEntity.getProjectPath());
			mainG.setPackageStyle(subTableListEntity.getPackageStyle());
			//update-begin-author:taoYan date:20180619 for:TASK #2812 【代码生成器优化】Restful swggerUI 代码生成，可选择
			mainG.setSupportRestful(request.getParameter("supportRestful"));
			//update-end-author:taoYan date:20180619 for:TASK #2812 【代码生成器优化】Restful swggerUI 代码生成，可选择
			CgFormHeadEntity mCgFormHead = cgFormFieldService.getCgFormHeadByTableName(mainTable);
			getCgformConfig(mCgFormHead, mainG);
			//step.4 填充子表的所有智能表单配置
			Map<String,GenerateEntity> subsG = new HashMap<String,GenerateEntity>();
			List<SubTableEntity>  subTabParamIn = subTableListEntity.getSubTabParamIn();
			for(SubTableEntity po:subTabParamIn){
				String sTableName = po.getTableName();
				CgFormHeadEntity cgSubHead = cgFormFieldService.getCgFormHeadByTableName(sTableName);
				List<CgFormFieldEntity> colums = cgSubHead.getColumns();
				String[] foreignKeys =getForeignkeys(colums);
				po.setForeignKeys(foreignKeys);
				GenerateEntity subG = new GenerateEntity();
				getCgformConfig(cgSubHead, subG);
				subG.setEntityName(po.getEntityName());
				subG.setEntityPackage(po.getEntityPackage());
				subG.setFieldRowNum(1);
				subG.setFtlDescription(po.getFtlDescription());
				subG.setForeignKeys(foreignKeys);
				subG.setTableName(po.getTableName());
				subG.setProjectPath(subTableListEntity.getProjectPath());
				subG.setPackageStyle(subTableListEntity.getPackageStyle());
				subsG.put(sTableName, subG);
			}
			codeParamEntityIn.setSubTabParam(subTabParamIn);
			//update--begin-------author:zhoujf------date:20160713----for:online生成器用户自定义模板支持---------------
			//update-begin--Author:zhoujf  Date:20180503 for：TASK #2500 【代码生成器】代码生成器开发一通用模板生成功能
			String version = request.getParameter("version");
			OnlineGenerateEnum modeEnum = OnlineGenerateEnum.toEnum(jspMode,version);
			//update-end--Author:zhoujf  Date:20180503 for：TASK #2500 【代码生成器】代码生成器开发一通用模板生成功能
			if(modeEnum!=null){
				if("system".equals(modeEnum.getVersion())){
					//step.5 一对多(父子表)数据模型,代码生成
					//update--begin-------author:zhoujf------date:20160318----for:online生成器支持bootstrap风格生成---------------
					if("06".equals(jspMode)){
						CgformCodeGenerateOneToMany.oneToManyCreateBootstap(subTabParamIn, codeParamEntityIn,mainG,subsG);
					}else{
						CgformCodeGenerateOneToMany.oneToManyCreate(subTabParamIn, codeParamEntityIn,mainG,subsG);
					}
					//update--end-------author:zhoujf------date:20160318----for:online生成器支持bootstrap风格生成---------------
					//j.setMsg("成功生成增删改查->功能："+codeParamEntityIn.getFtlDescription());
				}else if("ext".equals(modeEnum.getVersion())){
					CgformCodeGenerateOneToMany.oneToManyCreateUserDefined(jspMode,subTabParamIn, codeParamEntityIn,mainG,subsG);
				}
				//update-begin--Author:zhoujf  Date:20180503 for：TASK #2500 【代码生成器】代码生成器开发一通用模板生成功能
				else if("ext-common".equals(modeEnum.getVersion())){
					CreateFileConfig createFileConfig = new CreateFileConfig();
					createFileConfig.setStylePath(jspMode.replace(".", File.separator));
					createFileConfig.setTemplateRootDir("src/main/resources/jeecg/ext-common-template");
					CgformCodeOne2ManyExtCommonGenerate g = new CgformCodeOne2ManyExtCommonGenerate(createFileConfig,subTabParamIn, codeParamEntityIn,mainG,subsG);
					g.generateToFile();
				}
				//update-end--Author:zhoujf  Date:20180503 for：TASK #2500 【代码生成器】代码生成器开发一通用模板生成功能
				j.setMsg(codeParamEntityIn.getFtlDescription()+"：功能生成成功，请刷新项目重启，菜单访问路径："+CodeStringUtils.getInitialSmall(codeParamEntityIn.getEntityName())+"Controller.do?list");
			}else{
				j.setMsg("代码生成器不支持该页面风格");
			}
			//update--end-------author:zhoujf------date:20160713----for:online生成器用户自定义模板支持---------------
		}catch (Exception e) {
			e.printStackTrace();
			j.setMsg(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		try {
			String projectPath = URLEncoder.encode(subTableListEntity.getProjectPath(), "UTF-8");
			Cookie cookie = new Cookie("cookie_projectPath",projectPath );						
			cookie.setMaxAge(60*60*24*30); //cookie 保存30天
			response.addCookie(cookie);
			response.getWriter().print(j.getJsonStr());
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				response.getWriter().close();
			} catch (Exception e2) {
			}
		}
	}
	/**
	 * 获取智能表单中外键：根据是否设置了主表以及主表字段来判断
	 * @param colums
	 * @return
	 */
	private String[] getForeignkeys(List<CgFormFieldEntity> colums) {
		List<String> fs = new ArrayList<String>(0);
		for(CgFormFieldEntity c : colums){
			if(StringUtil.isNotEmpty(c.getMainTable()) && StringUtil.isNotEmpty(c.getMainField())){
				//update-begin--Author:zhoujf  Date:20180503 for：一对多主子表关联外键的问题
//				fs.add(c.getFieldName().toUpperCase());
				fs.add(oConvertUtils.camelNameCapFirst(c.getFieldName()));
				//update-end--Author:zhoujf  Date:20180503 for：一对多主子表关联外键的问题
			}
		}
		String[] foreignkeys = (String[]) fs.toArray(new String[fs.size()]);
		return foreignkeys;
	}
	//update--begin--author:gj_shaojc-- date:20180411--for:TASK #2629 【代码生成器】增强sql为空字符串的时候，不要生成这块的逻辑(table)---
	private String[] getCgformButtonSql(CgformButtonSqlEntity cbs){
		String[] newcgbsql=new String[]{};
		//update--begin--author:zhoujf date:20180413 for:TASK #2623 【bug】生成代码sql 不支持表达式--
//		if(cbs!=null){
//			StringBuffer cgb =new StringBuffer("");
//			String[] cgbsql=cbs.getCgbSqlStr().replaceAll("(\r\n|\r|\n|\n\r)", "").split(";");
//			for(int i=0;i<cgbsql.length;i++){
//				if(!("").equals(cgbsql[i].toString().trim())){
//					cgb.append(cgbsql[i]+";");
//				}
//			}
//			if(cgb.length()>0){
//				newcgbsql=cgb.toString().split(";");
//			}
//		}
		if(cbs!=null){
			if(StringUtils.isNotEmpty(cbs.getCgbSqlStr())){
				String sql = cbs.getCgbSqlStr().replaceAll("(\r\n|\r|\n|\n\r)", "");
				if(!("").equals(sql.toString().trim())){
					newcgbsql=new String[]{sql};
				}
			}
		}
		//update--end--author:zhoujf date:20180413 for:TASK #2623 【bug】生成代码sql 不支持表达式--
		return newcgbsql;
	}
	//update--end--author:gj_shaojc-- date:20180411--for:TASK #2629 【代码生成器】增强sql为空字符串的时候，不要生成这块的逻辑(table)---
	/**
	 * 获取智能表单的所有配置
	 * @param cgFormHead
	 * @param generateEntity
	 * @throws Exception 
	 */
	private void getCgformConfig(CgFormHeadEntity cgFormHead,
			GenerateEntity generateEntity) throws Exception {
		int filedNums = cgFormHead.getColumns().size();
		List<CgformButtonEntity> buttons = null;
		Map<String, String[]> buttonSqlMap = new LinkedHashMap<String, String[]>();
		//表单配置
		cgFormHead = cgFormFieldService.getEntity(CgFormHeadEntity.class, cgFormHead.getId());
		//按钮配置
		buttons = cgformButtonService.getCgformButtonListByFormId(cgFormHead.getId());
		
		//update--begin--author:scott-- date:20180704--for:TASK #2889 【优化】online代码生成器模板优化，sql增强判断为空的情况，不生成对应的增强代码---
		//按钮SQL增强
		for(CgformButtonEntity cb:buttons){
			CgformButtonSqlEntity cbs = cgformButtonSqlService.getCgformButtonSqlByCodeFormId(cb.getButtonCode(), cgFormHead.getId());
			if(cbs !=null && oConvertUtils.isNotEmpty(cbs.getCgbSqlStr())){
				buttonSqlMap.put(cb.getButtonCode(),this.getCgformButtonSql(cbs));
			}
		}
		CgformButtonSqlEntity cbsAdd = cgformButtonSqlService.getCgformButtonSqlByCodeFormId("add", cgFormHead.getId());
		if(cbsAdd!=null && oConvertUtils.isNotEmpty(cbsAdd.getCgbSqlStr())){
			buttonSqlMap.put("add",this.getCgformButtonSql(cbsAdd));
		}
		CgformButtonSqlEntity cbsUpdate = cgformButtonSqlService.getCgformButtonSqlByCodeFormId("update", cgFormHead.getId());
		if(cbsUpdate!=null && oConvertUtils.isNotEmpty(cbsUpdate.getCgbSqlStr())){
			buttonSqlMap.put("update",this.getCgformButtonSql(cbsUpdate));
		}
		CgformButtonSqlEntity cbsDelete = cgformButtonSqlService.getCgformButtonSqlByCodeFormId("delete", cgFormHead.getId());
		if(cbsDelete!=null && oConvertUtils.isNotEmpty(cbsDelete.getCgbSqlStr())){
			buttonSqlMap.put("delete",this.getCgformButtonSql(cbsDelete));
		}
		//update--end--author:scott-- date:20180704--for:TASK #2889 【优化】online代码生成器模板优化，sql增强判断为空的情况，不生成对应的增强代码---
		
		//按钮java增强
		Map<String, CgformEnhanceJavaEntity> buttonJavaMap = new LinkedHashMap<String, CgformEnhanceJavaEntity>();
		List<CgformEnhanceJavaEntity> javaList = dataBaseService.getCgformEnhanceJavaEntityByFormId(cgFormHead.getId());
		if(javaList!=null&&javaList.size()>0){
			for(CgformEnhanceJavaEntity e:javaList){
				if(StringUtil.isNotEmpty(e.getCgJavaValue())){
					buttonJavaMap.put(e.getButtonCode(), e);
				}
			}
		}
		
		//JS增强-列表
		CgformEnhanceJsEntity listJs = 	cgformEnhanceJsService.getCgformEnhanceJsByTypeFormId("list", cgFormHead.getId());
		CgformEnhanceJsEntity listJsCopy = null;
		try{
			listJsCopy = listJs.deepCopy();
		}catch (Exception e) {
			logger.debug(e.getMessage());
		}
		//JS增强-表单
		CgformEnhanceJsEntity formJs = 	cgformEnhanceJsService.getCgformEnhanceJsByTypeFormId("form", cgFormHead.getId());
		CgformEnhanceJsEntity formJsCopy = null;
		try{
			formJsCopy = formJs.deepCopy();
		}catch (Exception e) {
			logger.debug(e.getMessage());
		}
		//将js中带有online字段名的 转换成java命名
		for(CgFormFieldEntity field : cgFormHead.getColumns()){
			String fieldName = field.getFieldName();
			if(listJsCopy!=null){
				listJsCopy.setCgJsStr(listJsCopy.getCgJsStr().replace(fieldName, JeecgReadTable.formatField(fieldName)));
			}
			if(formJsCopy!=null&&formJsCopy.getCgJsStr()!=null){
				formJsCopy.setCgJsStr(formJsCopy.getCgJsStr().replace(fieldName, JeecgReadTable.formatField(fieldName)));
			}
			//online代码生成，popup对应的字典字段进行java命名转换
			//-- update-begin--Author:zhoujf  Date:20180410 for：datagrid，popup查询条件多字段回填问题(机制改造传值前代码生成器转换)--
			//-- update-begin--Author:zhoujf  Date:20180413 for：TASK #2641 【popup重构问题】代码生成后 添加页面 使用的popup 需要把@转成逗号--
			if("popup".equals(field.getShowType()) && oConvertUtils.isNotEmpty(field.getDictField())){
				field.setDictField(oConvertUtils.camelNames(field.getDictField()));
			}
			//-- update-end--Author:zhoujf  Date:20180413 for：TASK #2641 【popup重构问题】代码生成后 添加页面 使用的popup 需要把@转成逗号--
			//-- update-end--Author:zhoujf  Date:20180410 for：datagrid，popup查询条件多字段回填问题(机制改造传值前代码生成器转换)--
		}
		generateEntity.setButtons(buttons);
		generateEntity.setButtonSqlMap(buttonSqlMap);
		generateEntity.setButtonJavaMap(buttonJavaMap);
		generateEntity.setCgFormHead(cgFormHead);
		generateEntity.setListJs(listJsCopy);
		generateEntity.setFormJs(formJsCopy);
	}
	/**
	 * 跳转到文件夹目录树
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "goFileTree")
	public ModelAndView goFileTree(HttpServletRequest request) {
		return new ModelAndView("jeecg/cgform/generate/fileTree");
	}
	/**
	 * 返回子目录json
	 * @param parentNode
	 * @return
	 */
	@RequestMapping(params = "doExpandFileTree")
	@ResponseBody
	public Object doExpandFileTree(String parentNode){
		JSONArray fjson = new JSONArray();
		try{
			if(StringUtil.isEmpty(parentNode)){
				//返回磁盘驱动器根目录
				File[] roots = File.listRoots();
				for(File r:roots){
					JSONObject item = new JSONObject();
					item.put("id", r.getAbsolutePath());
					item.put("text", r.getPath());
					item.put("iconCls", "icon-folder");
					if(hasDirs(r)){
						item.put("state", "closed");
					}else{
						item.put("state", "open");
					}
					fjson.add(item);
				}
			}else{
				try {
					parentNode =  new String(parentNode.getBytes("ISO-8859-1"), "UTF-8");
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
				//返回子目录集
				File parent = new File(parentNode);
				File[] chs = parent.listFiles();
				for(File r:chs){
					JSONObject item = new JSONObject();
					if(r.isDirectory()){
						item.put("id", r.getAbsolutePath());
						item.put("text", r.getPath());
						if(hasDirs(r)){
							item.put("state", "closed");
						}else{
							item.put("state", "open");
						}
						fjson.add(item);
					}else{
						
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("该文件夹不可选择");
		}
		return fjson;
	}
	private boolean hasDirs(File dir){
		try{
			if(dir.listFiles().length==0){
	//			item.put("state", "open");
				return false;
			}else{
	//			item.put("state", "closed");
				return true;
			}
		}catch (Exception e) {
			logger.info(e.getMessage());
			return false;
		}
	}
}
