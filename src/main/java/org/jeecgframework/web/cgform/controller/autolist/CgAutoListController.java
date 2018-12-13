package org.jeecgframework.web.cgform.controller.autolist;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.enums.SysThemesEnum;
import org.jeecgframework.core.online.util.FreemarkerHelper;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.IpUtil;
import org.jeecgframework.core.util.JeecgDataAutorUtils;
import org.jeecgframework.core.util.MutiLangUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.SqlInjectionUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.SysThemesUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.web.cgform.common.CgAutoListConstant;
import org.jeecgframework.web.cgform.entity.config.CgFormFieldEntity;
import org.jeecgframework.web.cgform.entity.config.CgFormHeadEntity;
import org.jeecgframework.web.cgform.entity.template.CgformTemplateEntity;
import org.jeecgframework.web.cgform.service.autolist.CgTableServiceI;
import org.jeecgframework.web.cgform.service.autolist.ConfigServiceI;
import org.jeecgframework.web.cgform.service.config.CgFormFieldServiceI;
import org.jeecgframework.web.cgform.service.template.CgformTemplateServiceI;
import org.jeecgframework.web.cgform.util.PublicUtil;
import org.jeecgframework.web.cgform.util.QueryParamUtil;
import org.jeecgframework.web.cgform.util.TemplateUtil;
import org.jeecgframework.web.system.controller.core.LoginController;
import org.jeecgframework.web.system.pojo.base.DictEntity;
import org.jeecgframework.web.system.pojo.base.TSOperation;
import org.jeecgframework.web.system.pojo.base.TSType;
import org.jeecgframework.web.system.service.MutiLangServiceI;
import org.jeecgframework.web.system.service.SystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @Title:CgAutoListController
 * @description:动态列表控制器[根据表名读取配置文件，进行动态数据展现]
 * @author 赵俊夫
 * @date Jul 5, 2013 2:55:36 PM
 * @version V1.0
 */
@Controller
@RequestMapping("/cgAutoListController")
public class CgAutoListController extends BaseController{
	private static final Logger log = LoggerFactory.getLogger(CgAutoListController.class);
	
	@Autowired
	private ConfigServiceI configService;
	@Autowired
	private CgTableServiceI cgTableService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private CgFormFieldServiceI cgFormFieldService;
	@Autowired
	private CgformTemplateServiceI cgformTemplateService;
	@Autowired
	private MutiLangServiceI mutiLangService;
	/**
	 * 动态列表展现入口
	 * @param id 动态配置ID
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "list")
	public void list(String id, HttpServletRequest request,
			HttpServletResponse response) {
		long start = System.currentTimeMillis();
		//step.1 根据表名获取该表单的配置参数
		String jversion = cgFormFieldService.getCgFormVersionByTableName(id);
		Map<String, Object> configs = configService.queryConfigs(id,jversion);
		//step.2 获取列表ftl模板路径
		FreemarkerHelper viewEngine = new FreemarkerHelper();
		Map<String, Object> paras = new HashMap<String, Object>();
		//step.3 封装页面数据
		loadVars(configs,paras,request);
		//step.4 组合模板+数据参数，进行页面展现
		//update-begin--Author:张忠亮  Date:20151019 for：url中加入olstylecode 可指定风格
		String template=request.getParameter("olstylecode");
		if(StringUtils.isBlank(template)){
				CgFormHeadEntity head = cgFormFieldService.getCgFormHeadByTableName(id);
				//update-begin--Author:张忠亮  Date:20150707 for：online表单风格加入录入、编辑、列表、详情页面设置
				template=head.getFormTemplate();
			paras.put("_olstylecode","");
		}else{
			paras.put("_olstylecode",template);
		}
        paras.put("this_olstylecode",template);
        //update-begin--Author:guoxianhui  Date:20171204 for：TASK #2450 【改造】支持主子表效果
        if(template!=null && template.indexOf("subgrid")>=0){
        	String tableName = id;
        	String tablename = PublicUtil.replaceTableName(tableName);
            Map<String, Object> data = new HashMap<String, Object>();
            Map configData = null;
	        configData = cgFormFieldService.getFtlFormConfig(tableName,jversion);
            
        	data = new HashMap(configData);
        	//如果该表是主表查出关联的附表
        	CgFormHeadEntity head = (CgFormHeadEntity)data.get("head");
            Map<String, Object> dataForm = new HashMap<String, Object>();
          
            Iterator it=dataForm.entrySet().iterator();
    	    while(it.hasNext()){
    	    	Map.Entry entry=(Map.Entry)it.next();
    	        String ok=(String)entry.getKey();
    	        Object ov=entry.getValue();
    	        data.put(ok, ov);
    	    }
            Map<String, Object> tableData  = new HashMap<String, Object>();
            //获取主表或单表表单数据

            tableData.put(tablename, dataForm);
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
        	paras.putAll(data);
        }
        //update-end--Author:guoxianhui  Date:20171204 for：TASK #2450 【改造】支持主子表效果
        
        //update--begin--author:zhangjiaqiang date:20170628 for: TASK #2194 【online链接样式切换】Online 功能测试的列表链接样式，需要根据浏览器IE进行切换 
        paras.put("brower_type", ContextHolderUtils.getSession().getAttribute("brower_type"));
      //update--end--author:zhangjiaqiang date:20170628 for: TASK #2194 【online链接样式切换】Online 功能测试的列表链接样式，需要根据浏览器IE进行切换 
        CgformTemplateEntity entity=cgformTemplateService.findByCode(template);
		String html = viewEngine.parseTemplate(TemplateUtil.getTempletPath(entity,0, TemplateUtil.TemplateType.LIST), paras);
		//update-end--Author:张忠亮  Date:20151019 for：url中加入olstylecode 可指定风格
		PrintWriter writer = null;
		try {
			response.setContentType("text/html");
			response.setHeader("Cache-Control", "no-store");
			writer = response.getWriter();
			writer.println(html);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				writer.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		long end = System.currentTimeMillis();
		log.debug("动态列表生成耗时："+(end-start)+" ms");
	}

	/**
	 * 动态列表数据查询
	 * @param configId 配置id 修正使用id会造成主键查询的冲突
	 * @param page 分页页面
	 * @param rows 分页大小
	 * @param request 
	 * @param response
	 * @param dataGrid
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "datagrid")
	public void datagrid(String configId,String page,String field,String rows,String sort,String order, HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) throws Exception {
		Object dataRuleSql =JeecgDataAutorUtils.loadDataSearchConditonSQLString(); //request.getAttribute(Globals.MENU_DATA_AUTHOR_RULE_SQL);
		long start = System.currentTimeMillis();
		//step.1 获取动态配置
		String jversion = cgFormFieldService.getCgFormVersionByTableName(configId);
		Map<String, Object>  configs = configService.queryConfigs(configId,jversion);
		String table = (String) configs.get(CgAutoListConstant.TABLENAME);
		//update-begin--Author:gengjiajia  Date:20160809 for：TASK #1214 online表单一个表，支持多个配置,还原物理表名
		table = PublicUtil.replaceTableName(table);
		//update-end--Author:gengjiajia  Date:20160809 for：TASK #1214 online表单一个表，支持多个配置,还原物理表名
		Map params =  new HashMap<String,Object>();
		//step.2 获取查询条件以及值
		List<CgFormFieldEntity> beans = (List<CgFormFieldEntity>) configs.get(CgAutoListConstant.FILEDS);
		Map<String, String[]> fieldMap = new HashMap<String, String[]>();
		for(CgFormFieldEntity b:beans){
			QueryParamUtil.loadQueryParams(request,b,params);
			fieldMap.put(b.getFieldName(), new String[]{b.getType(), b.getFieldDefault()});
		}
		
		//------------------update-begin-------2015年6月1日----author:zhongsy------for:表单配置支持树形------------
		//参数处理
		boolean isTree = configs.get(CgAutoListConstant.CONFIG_ISTREE) == null ? false
				: CgAutoListConstant.BOOL_TRUE.equalsIgnoreCase(configs.get(CgAutoListConstant.CONFIG_ISTREE).toString());
		String treeId = request.getParameter("id");
		String parentIdFieldName = null;
		String parentIdDefault = null;
		String parentIdFieldType = null;
		if(isTree) {
			parentIdFieldName = configs.get(CgAutoListConstant.TREE_PARENTID_FIELDNAME).toString();
		    parentIdFieldType = fieldMap.get(parentIdFieldName)[0];
			parentIdDefault = fieldMap.get(parentIdFieldName)[1];
			if("null".equalsIgnoreCase(parentIdDefault)) {
				parentIdDefault = null;
			}
			//update--begin--author:zhoujf Date:20180606 for:TASK #2751 【sql注入】online开发问题
			SqlInjectionUtil.filterContent(treeId);
			//update--end--author:zhoujf Date:20180606 for:TASK #2751 【sql注入】online开发问题
			if(treeId == null) {
				treeId = parentIdDefault;
			}else {
				if(parentIdFieldType.equalsIgnoreCase(CgAutoListConstant.TYPE_STRING)) {
					treeId = "'" + treeId + "'";
				}
			}
			if(oConvertUtils.isEmpty(treeId)) {
				params.put(parentIdFieldName, " is null");
			}else {
				params.put(parentIdFieldName, "=" + treeId);
			}
		}
		//------------------update-end-------2015年6月1日----author:zhongsy------for:表单配置支持树形------------
		
		int p = page==null?1:Integer.parseInt(page);
		int r = rows==null?99999:Integer.parseInt(rows);
		//step.3 进行查询返回结果，如果为tree的下级数据，则不需要分页
		//------------------update-start-------2015年6月1日----author:zhongsy------for:表单配置支持树形------------
		List<Map<String, Object>> result = null;
		if(isTree && treeId !=null) {
			//防止下级数据太大，最大只取500条
			result=cgTableService.querySingle(table, field.toString(), params,sort,order, 1, 500);
		}else {
			result=cgTableService.querySingle(table, field.toString(), params,sort,order, p,r );
		}
		
		//treeform 处理是否有下级菜单
		if(isTree) {
			cgTableService.treeFromResultHandle(table, parentIdFieldName, parentIdFieldType,
					result);
		}
		//------------------update-end-------2015年6月1日----author:zhongsy------for:表单配置支持树形------------
		
		//处理页面中若存在checkbox只能显示code值而不能显示text值问题
		Map<String, Object> dicMap = new HashMap<String, Object>();
		for(CgFormFieldEntity b:beans){
			loadDic(dicMap, b);
			List<DictEntity> dicList = (List<DictEntity>)dicMap.get(CgAutoListConstant.FIELD_DICTLIST);
			if(dicList.size() > 0){
				for(Map<String, Object> resultMap:result){
					StringBuffer sb = new StringBuffer();
					//update-begin--Author:scott----  Date:20170511 ----for：字典类型支持int类型-----
					Object obj = resultMap.get(b.getFieldName());
					String value = null;
					if(obj instanceof Integer){
						value = String.valueOf(obj);
					}else{
						value = (String)obj;
					}
					//update-end--Author:scott----  Date:20170511 ----for：字典类型支持int类型-----
					if(oConvertUtils.isEmpty(value)){continue;}
					String[] arrayVal = value.split(",");
					if(arrayVal.length > 1){
						for(String val:arrayVal){
							for(DictEntity dictEntity:dicList){
								if(val.equals(dictEntity.getTypecode())){
									sb.append(dictEntity.getTypename());
									sb.append(",");
								}
								
							}
						}
						String dicStr = sb.toString();
						log.info("----Online---字典字段----FieldName: {}, dicStr: {}",b.getFieldName(),dicStr);
						if(oConvertUtils.isNotEmpty(dicStr) && dicStr.endsWith(",")){
							resultMap.put(b.getFieldName(), dicStr.substring(0, dicStr.length()-1));
						}
					}

				}
			}
			//update-begin--Author:Yandong----  Date:20180521 ----for：TASK #2727 【online问题】UE编辑器图片 回显问题----blob类型报错 图片上传问题-----
			if("Blob".equals(b.getType())) {
				for(Map<String, Object> resultMap:result){
					Object obj = resultMap.get(b.getFieldName());
					if(obj instanceof byte[]) {
						resultMap.put(b.getFieldName(), new String((byte[])obj,"utf-8"));
					}
				}
			}
			//update-end--Author:Yandong----  Date:20180521 ----for：TASK #2727 【online问题】UE编辑器图片 回显问题----blob类型报错 图片上传问题-----
		}
		Long size = cgTableService.getQuerySingleSize(table, field, params);
		dealDic(result,beans);
		response.setContentType("application/json");
		response.setHeader("Cache-Control", "no-store");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			//------------------update-start-------2015年6月1日----author:zhongsy------for:表单配置支持树形------------
			if(isTree && treeId !=null) {
				//下级列表
				writer.println(QueryParamUtil.getJson(result));
			}else {
				writer.println(QueryParamUtil.getJson(result,size));
			}
			//------------------update-end-------2015年6月1日----author:zhongsy------for:表单配置支持树形------------
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				writer.close();
			} catch (Exception e2) {
			}
		}
		long end = System.currentTimeMillis();
		log.debug("动态列表查询耗时："+(end-start)+" ms");
	}
	
	/**
	 * 处理数据字典
	 * @param result 查询的结果集
	 * @param beans 字段配置
	 */
	@SuppressWarnings("unchecked")
	private void dealDic(List<Map<String, Object>> result,
			List<CgFormFieldEntity> beans) {
		for(CgFormFieldEntity bean:beans){
			String dicTable = bean.getDictTable();//字典Table
			String dicCode = bean.getDictField();//字典Code
			String dicText= bean.getDictText();//字典text
			if(StringUtil.isEmpty(dicTable)&& StringUtil.isEmpty(dicCode)){
				//不需要处理字典
				continue;
			}else{
				if(!bean.getShowType().equals("popup")){
					List<DictEntity> dicDataList = queryDic(dicTable, dicCode,dicText);
					for(Map r:result){
						String value = String.valueOf(r.get(bean.getFieldName()));
//						for(Map m:dicDatas){
//							String typecode = String.valueOf(m.get("typecode"));
//							String typename = String.valueOf(m.get("typename"));
//							if(value.equalsIgnoreCase(typecode)){
//								r.put(bean.getFieldName(),typename);
//							}
//						}
						for(DictEntity dictEntity:dicDataList){
							if(value.equalsIgnoreCase(dictEntity.getTypecode())){
								//------------------update-begin------for:-国际化处理-----------------------author:zhagndaihao------------
								r.put(bean.getFieldName(),MutiLangUtil.getLang(dictEntity.getTypename()));
								//------------------update-end-----for:-国际化处理----------------------------author:zhagndaihao---------
								break;
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 删除动态表
	 * @param configId 配置id
	 * @param id 主键
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(String configId,String id,
			HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		//update-begin--Author:gengjiajia  Date:20160809 for：TASK #1214 online表单一个表，支持多个配置,还原真实表名
		String tableName = PublicUtil.replaceTableName(configId);
		String jversion = cgFormFieldService.getCgFormVersionByTableName(tableName);
		String table = (String) configService.queryConfigs(tableName,jversion).get(CgAutoListConstant.TABLENAME);
		//String jversion = cgFormFieldService.getCgFormVersionByTableName(configId);
		//String table = (String) configService.queryConfigs(configId,jversion).get(CgAutoListConstant.TABLENAME);
		//update-end--Author:gengjiajia  Date:20160809 for：TASK #1214 online表单一个表，支持多个配置,还原真实表名
		cgTableService.delete(table, id);
		String message = "删除成功";
		log.info("["+IpUtil.getIpAddr(request)+"][online表单数据删除]"+message+"表名："+configId);
		systemService.addLog(message, Globals.Log_Type_DEL,
				Globals.Log_Leavel_INFO);
		j.setMsg(message);
		return j;
	}
	/**
	 * 删除动态表-批量
	 * @param configId 配置id
	 * @param id 主键
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "delBatch")
	@ResponseBody
	public AjaxJson delBatch(String configId,String ids,
			HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String jversion = cgFormFieldService.getCgFormVersionByTableName(configId);
		String table = (String) configService.queryConfigs(configId,jversion).get(CgAutoListConstant.TABLENAME);
		String message = "删除成功";
		try {
			String[] id = ids.split(",");
			cgTableService.deleteBatch(table, id);
		} catch (Exception e) {
			message = e.getMessage();
		}
		systemService.addLog(message, Globals.Log_Type_DEL,
				Globals.Log_Leavel_INFO);
		log.info("["+IpUtil.getIpAddr(request)+"][online表单数据批量删除]"+message+"表名："+configId);
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 装载要传入到ftl中的变量
	 * @param configs 从数据库中取出来的配置
	 * @param paras 要传入ftl的参数（需要对configs进行一些改造）
	 * @param request 
	 * @return 要传入ftl的参数（该方法直接操作paras容器，当然可以使用此返回值）
	 */
	@SuppressWarnings("unchecked")
	private Map loadVars(Map<String, Object> configs,Map<String, Object> paras, HttpServletRequest request) {
		paras.putAll(configs);
		List<Map> fieldList = new ArrayList<Map>();
		List<Map> queryList = new ArrayList<Map>();
		StringBuilder fileds = new StringBuilder();
		StringBuilder initQuery = new StringBuilder();
		//------------------update-begin-------2014年9月3日----author:JueYue------for:-列表数据隐藏权限------------
		Set<String> operationCodes = (Set<String>) request.getAttribute(Globals.OPERATIONCODES);
		Map<String,TSOperation> operationCodesMap = new HashMap<String, TSOperation>();
		if(operationCodes != null){
			TSOperation tsOperation;
			for (String id : operationCodes) {
				tsOperation = systemService.getEntity(TSOperation.class, id);
				if(tsOperation != null && tsOperation.getOperationType() == 0 && tsOperation.getStatus() == 0){
					operationCodesMap.put(tsOperation.getOperationcode(), tsOperation);
				}
			}
		}
		for (CgFormFieldEntity bean : (List<CgFormFieldEntity>) configs.get(CgAutoListConstant.FILEDS)) {
			if(operationCodesMap.containsKey(bean.getFieldName())) {
				continue;
			}
			//------------------update-end---2014年9月3日----author:JueYue------for:-列表数据隐藏权限------------
			Map fm = new HashMap<String, Object>();
			fm.put(CgAutoListConstant.FILED_ID, bean.getFieldName());
			fm.put(CgAutoListConstant.FIELD_TITLE, bean.getContent());
			String isShowList = bean.getIsShowList();
			if(StringUtil.isEmpty(isShowList)){
				if("id".equalsIgnoreCase(bean.getFieldName())){
					isShowList = CgAutoListConstant.BOOL_FALSE;
				}else{
					isShowList = CgAutoListConstant.BOOL_TRUE;
				}
			}
			fm.put(CgAutoListConstant.FIELD_ISSHOW, isShowList);
			fm.put(CgAutoListConstant.FIELD_QUERYMODE, bean.getQueryMode());
			fm.put(CgAutoListConstant.FIELD_SHOWTYPE, bean.getShowType());
			fm.put(CgAutoListConstant.FIELD_TYPE, bean.getType());
			//update-begin--Author:scott  Date:20170425 for：行编辑非空判断-------
			fm.put(CgAutoListConstant.FIELD_IS_NULL, bean.getIsNull());
			//update-begin--Author:scott  Date:20170425 for：行编辑非空判断-------
			fm.put(CgAutoListConstant.FIELD_LENGTH, bean.getFieldLength()==null?"120":bean.getFieldLength());
			fm.put(CgAutoListConstant.FIELD_HREF, bean.getFieldHref()==null?"":bean.getFieldHref());
			loadDic(fm,bean);
			fieldList.add(fm);
			if (CgAutoListConstant.BOOL_TRUE.equals(bean.getIsQuery())) {
				Map fmq = new HashMap<String, Object>();
				fmq.put(CgAutoListConstant.FILED_ID, bean.getFieldName());
				fmq.put(CgAutoListConstant.FIELD_TITLE, bean.getContent());
				fmq.put(CgAutoListConstant.FIELD_QUERYMODE, bean.getQueryMode());
				fmq.put(CgAutoListConstant.FIELD_TYPE, bean.getType());
				fmq.put(CgAutoListConstant.FIELD_SHOWTYPE, bean.getShowType());
				fmq.put(CgAutoListConstant.FIELD_DICTFIELD, bean.getDictField());
				fmq.put(CgAutoListConstant.FIELD_DICTTABLE, bean.getDictTable());
				//<#--update--begin--author:gj_shaojc date:20180316 for:TASK #2557 【问题确认】网友问题确认 --
				fmq.put(CgAutoListConstant.FIELD_DICTTEXT, bean.getDictText());
				//<#--update--end--author:gj_shaojc date:20180316 for:TASK #2557 【问题确认】网友问题确认 --
				fmq.put(CgAutoListConstant.FIELD_ISQUERY,"Y");
				loadDefaultValue(fmq,bean,request);
				loadDic(fmq,bean);
				queryList.add(fmq);
			}
			loadUrlDataFilter(queryList,bean,request);
			loadInitQuery(initQuery,bean,request);
			fileds.append(bean.getFieldName()).append(",");
		}
		loadAuth(paras, request);
		loadIframeConfig(paras, request);
		paras.put(CgAutoListConstant.CONFIG_FIELDLIST, fieldList);
		paras.put(CgAutoListConstant.CONFIG_QUERYLIST, queryList);
		paras.put(CgAutoListConstant.FILEDS, fileds);
		paras.put(CgAutoListConstant.INITQUERY, initQuery);
		return paras;
	}
	/**
	 * 加载iframe设置
	 * @param paras
	 * @param request
	 */
	private void loadIframeConfig(Map<String, Object> paras,
			HttpServletRequest request) {
		HttpSession session = ContextHolderUtils.getSession();
		String lang = (String)session.getAttribute("lang");
		
		//如果列表以iframe形式的话，需要加入样式文件
		StringBuilder sb= new StringBuilder("");
		if(!request.getQueryString().contains("isHref")){
//			String cssTheme ="default";
//			Cookie[] cookies = request.getCookies();
//			for (Cookie cookie : cookies) {
//				if (cookie == null || StringUtils.isEmpty(cookie.getName())) {
//					continue;
//				}
//				if (cookie.getName().equalsIgnoreCase("JEECGCSSTHEME")) {
//					cssTheme = cookie.getValue();
//				}
//			}
//			if(StringUtil.isEmpty(cssTheme)){
//				cssTheme ="default";
//			}
			SysThemesEnum sysThemesEnum = SysThemesUtil.getSysTheme(request);
			sb.append("<script type=\"text/javascript\" src=\"plug-in/jquery/jquery-1.8.3.js\"></script>");
			sb.append("<script type=\"text/javascript\" src=\"plug-in/jquery-plugs/i18n/jquery.i18n.properties.js\"></script>");
			sb.append("<script type=\"text/javascript\" src=\"plug-in/tools/dataformat.js\"></script>");
			sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"plug-in/accordion/css/accordion.css\">");
//			sb.append("<link id=\"easyuiTheme\" rel=\"stylesheet\" href=\"plug-in/easyui/themes/"+cssTheme+"/easyui.css\" type=\"text/css\"></link>");
			sb.append(SysThemesUtil.getEasyUiTheme(sysThemesEnum));
			sb.append(SysThemesUtil.getEasyUiIconTheme(sysThemesEnum));
//			sb.append("<link rel=\"stylesheet\" href=\"plug-in/easyui/themes/icon.css\" type=\"text/css\"></link>");
			sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"plug-in/accordion/css/icons.css\">");
			sb.append("<script type=\"text/javascript\" src=\"plug-in/easyui/jquery.easyui.min.1.3.2.js\"></script>");
			sb.append("<script type=\"text/javascript\" src=\"plug-in/easyui/locale/zh-cn.js\"></script>");
			sb.append("<script type=\"text/javascript\" src=\"plug-in/tools/syUtil.js\"></script>");
			sb.append("<script type=\"text/javascript\" src=\"plug-in/My97DatePicker/WdatePicker.js\"></script>");
//			sb.append("<link rel=\"stylesheet\" href=\"plug-in/tools/css/common.css\" type=\"text/css\"></link>");
			sb.append(SysThemesUtil.getCommonTheme(sysThemesEnum));
//			sb.append("<script type=\"text/javascript\" src=\"plug-in/lhgDialog/lhgdialog.min.js\"></script>");
			sb.append(SysThemesUtil.getLhgdialogTheme(sysThemesEnum));
			//update--begin--author:scott Date:20170304 for:替换layer风格提示框
			sb.append("<script type=\"text/javascript\" src=\"plug-in/layer/layer.js\"></script>");
			//update--end--author:scott Date:20170304 for:替换layer风格提示框
			sb.append("<script type=\"text/javascript\" src=\"plug-in/tools/curdtools.js\"></script>");
			
			sb.append("<script type=\"text/javascript\" src=\"plug-in/tools/easyuiextend.js\"></script>");
//			if("metro".equals(cssTheme)){
//				sb.append("<link id=\"easyuiTheme\" rel=\"stylesheet\" href=\"plug-in/easyui/themes/"+cssTheme+"/main.css\" type=\"text/css\"></link>");
//			}
			sb.append(SysThemesUtil.getEasyUiMainTheme(sysThemesEnum));
		}else{
		}
		paras.put(CgAutoListConstant.CONFIG_IFRAME, sb.toString());
	}
	/**
	 * Online表单控制列表链接按钮权限
	 * @param paras
	 * @param request
	 */
	private void loadAuth(Map<String, Object> paras, HttpServletRequest request) {
		List<TSOperation>  nolist = (List<TSOperation>) request.getAttribute(Globals.NOAUTO_OPERATIONCODES);
		if(ResourceUtil.getSessionUser().getUserName().equals("admin")|| !Globals.BUTTON_AUTHORITY_CHECK){
			nolist = null;
		}
		List<String> list = new ArrayList<String>();
		String nolistStr = "";
		if(nolist!=null){
			for(TSOperation operation:nolist){
				nolistStr+=operation.getOperationcode();
				nolistStr+=",";
				list.add(operation.getOperationcode());
			}
		}
		paras.put(CgAutoListConstant.CONFIG_NOLIST, list);
		paras.put(CgAutoListConstant.CONFIG_NOLISTSTR, nolistStr==null?"":nolistStr);
	}
	/**
	 * 加载列表初始查询条件-
	 * @param initQuery
	 * @param bean
	 * @param request
	 */
	private void loadInitQuery(StringBuilder initQuery, CgFormFieldEntity bean,
			HttpServletRequest request) {
		if(bean.getFieldName().equalsIgnoreCase("id")){
			return;
		}
		String paramV = request.getParameter(bean.getFieldName());
		String paramVbegin = request.getParameter(bean.getFieldName()+"_begin");
		String paramVend = request.getParameter(bean.getFieldName()+"_end");
		paramV = getSystemValue(paramV);
		if(!StringUtil.isEmpty(paramV)){
			initQuery.append("&"+bean.getFieldName()+"="+paramV);
		}
		if(!StringUtil.isEmpty(paramVbegin)){
			initQuery.append("&"+bean.getFieldName()+"_begin="+paramVbegin);
		}
		if(!StringUtil.isEmpty(paramVend)){
			initQuery.append("&"+bean.getFieldName()+"_end="+paramVend);
		}
	}

	/**
	 * 加载URL中的过滤参数[在未配置查询字段的情况下，作为hidden控件使用]
	 * @param queryList
	 * @param bean
	 * @param request
	 */
	@SuppressWarnings("unchecked")
	private void loadUrlDataFilter(List<Map> queryList, CgFormFieldEntity bean,
			HttpServletRequest request) {
		String paramV = request.getParameter(bean.getFieldName());
		String paramVbegin = request.getParameter(bean.getFieldName()+"_begin");
		String paramVend = request.getParameter(bean.getFieldName()+"_end");
		if(bean.getFieldName().equalsIgnoreCase("id")){
			return;
		}
		for(Map mq:queryList){
			if(mq.containsValue(bean.getFieldName())){
				return;
			}
		}
		if(!StringUtil.isEmpty(paramV) || !StringUtil.isEmpty(paramVbegin) ||!StringUtil.isEmpty(paramVend)){
			Map fmq = new HashMap<String, Object>();
			fmq.put(CgAutoListConstant.FILED_ID, bean.getFieldName());
			fmq.put(CgAutoListConstant.FIELD_TITLE, bean.getContent());
			fmq.put(CgAutoListConstant.FIELD_QUERYMODE, bean.getQueryMode());
			fmq.put(CgAutoListConstant.FIELD_TYPE, bean.getType());
			fmq.put(CgAutoListConstant.FIELD_ISQUERY,"N");
			paramV = getSystemValue(paramV);
			fmq.put(CgAutoListConstant.FIELD_VALUE, paramV);
			paramVend = getSystemValue(paramVend);
			fmq.put(CgAutoListConstant.FIELD_VALUE_BEGIN, StringUtil.isEmpty(paramVbegin)?"":paramVbegin);
			fmq.put(CgAutoListConstant.FIELD_VALUE_END, StringUtil.isEmpty(paramVend)?"":paramVend);
			queryList.add(fmq);
		}
	}

	/**
	 * 加载URL中的过滤参数[在已配置查询字段的情况下，给该查询字段加上默认值]
	 * @param fmq
	 * @param bean
	 * @param request
	 */
	@SuppressWarnings("unchecked")
	private void loadDefaultValue(Map fmq, CgFormFieldEntity bean,
			HttpServletRequest request) {
		if(bean.getQueryMode().equalsIgnoreCase("single")){
			String paramV = request.getParameter(bean.getFieldName());
			if(!StringUtil.isEmpty(paramV)){
				paramV = getSystemValue(paramV);
				fmq.put(CgAutoListConstant.FIELD_VALUE, paramV);
			}
		}else if(bean.getQueryMode().equalsIgnoreCase("group")){
			String paramVbegin = request.getParameter(bean.getFieldName()+"_begin");
			String paramVend = request.getParameter(bean.getFieldName()+"_end");
			fmq.put(CgAutoListConstant.FIELD_VALUE_BEGIN, StringUtil.isEmpty(paramVbegin)?"":paramVbegin);
			fmq.put(CgAutoListConstant.FIELD_VALUE_END, StringUtil.isEmpty(paramVend)?"":paramVend);
		}
	}

	/**
	 * 装载数据字典
	 * @param m	要放入freemarker的数据
	 * @param bean 读取出来的动态配置数据
	 */
	@SuppressWarnings("unchecked")
	private void loadDic(Map m, CgFormFieldEntity bean) {
		String dicT = bean.getDictTable();//字典Table
		String dicF = bean.getDictField();//字典Code
		String dicText = bean.getDictText();//字典Text
		if(StringUtil.isEmpty(dicT)&& StringUtil.isEmpty(dicF)){
			//如果这两个字段都为空，则没有数据字典
			m.put(CgAutoListConstant.FIELD_DICTLIST, new ArrayList(0));
			return;
		}
		if(!bean.getShowType().equals("popup")){
			List<DictEntity> dicDatas = queryDic(dicT, dicF,dicText);
			m.put(CgAutoListConstant.FIELD_DICTLIST, dicDatas);
		}else{
			m.put(CgAutoListConstant.FIELD_DICTLIST, new ArrayList(0));
		}
	}
	/**
	 * 查询数据字典
	 * @param dicTable 数据字典表
	 * @param dicCode	数据字典字段
	 * @param dicText 数据字典显示文本
	 * @return
	 */
	private List<DictEntity> queryDic(String dicTable, String dicCode,String dicText) {
		//add--begin--author:guoxianhui Date:20171204 for:TASK #2444 【改进】根据你的建议优化系统 第四点
		if(dicTable==null || dicTable.length()<=0){
			List<TSType> listt = ResourceUtil.getCacheTypes(dicCode.toLowerCase());
			List<DictEntity> li = new ArrayList<DictEntity>();
			if(listt!=null){
				for (TSType tsType : listt) {
					DictEntity d = new DictEntity();
					d.setTypecode(tsType.getTypecode());
					//update--begin--Author:LiShaoQing Date:20180110 for:TASK #2478【bug】online测试功能，查询条件没有翻译过来---
					d.setTypename(mutiLangService.getLang(tsType.getTypename()));
					//update--end--Author:LiShaoQing Date:20180110 for:TASK #2478【bug】online测试功能，查询条件没有翻译过来---
					li.add(d);
				}
			}
			return li;
		}
		//add--end--author:guoxianhui Date:20171204 for:TASK #2444 【改进】根据你的建议优化系统 第四点
		
//		StringBuilder dicSql = new StringBuilder();
//		if(StringUtil.isEmpty(dicTable)){//step.1 如果没有字典表则使用系统字典表
//			dicTable = CgAutoListConstant.SYS_DIC;
//			dicSql.append(" SELECT TYPECODE,TYPENAME FROM");
//			dicSql.append(" "+dicTable);
//			dicSql.append(" "+"WHERE TYPEGROUPID = ");
//			dicSql.append(" "+"(SELECT ID FROM "+CgAutoListConstant.SYS_DICGROUP+" WHERE TYPEGROUPCODE = '"+dicCode+"' )");
//		}else{//step.2 如果给定了字典表则使用该字典表，这个功能需要在表单配置追加字段
//			//table表查询
//			dicSql.append("SELECT DISTINCT ").append(dicCode).append(" as typecode, ");
//			if(dicText!=null&&dicText.length()>0){
//				dicSql.append(dicText).append(" as typename ");
//			}else{
//				dicSql.append(dicCode).append(" as typename ");
//			}
//			dicSql.append(" FROM ").append(dicTable);
//			dicSql.append(" ORDER BY ").append(dicCode);
//		}
//		//step.3 字典数据
//		List<Map<String, Object>> dicDatas = systemService.findForJdbc(dicSql.toString());
		return systemService.queryDict(dicTable, dicCode, dicText);
	}
	
	private String getSystemValue(String sysVarName) {
		if(StringUtil.isEmpty(sysVarName)){
			return sysVarName;
		}
		if(sysVarName.contains("{") && sysVarName.contains("}")){
			sysVarName = sysVarName.replaceAll("\\{", "");
			sysVarName = sysVarName.replaceAll("\\}", "");
			sysVarName =sysVarName.replace("sys.", "");
			//---author:jg_xugj----start-----date:20151226--------for：#814 【数据权限】扩展支持写表达式，通过session取值
			return ResourceUtil.converRuleValue(sysVarName); 		
			//---author:jg_xugj----end-----date:20151226--------for：#814 【数据权限】扩展支持写表达式，通过session取值
		}else{
			return sysVarName;
		}
	}
}
