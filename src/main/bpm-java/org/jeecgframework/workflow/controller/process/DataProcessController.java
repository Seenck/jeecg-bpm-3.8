package org.jeecgframework.workflow.controller.process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.apache.commons.lang.StringUtils;
import org.jeecgframework.codegenerate.util.CodeResourceUtil;
import org.jeecgframework.codegenerate.util.def.ConvertDef;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.util.DynamicDBUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.web.autoform.entity.AutoFormDataListEntity;
import org.jeecgframework.web.autoform.entity.AutoFormDbEntity;
import org.jeecgframework.web.autoform.entity.AutoFormDbFieldEntity;
import org.jeecgframework.web.autoform.entity.AutoFormEntity;
import org.jeecgframework.web.autoform.service.AutoFormDbServiceI;
import org.jeecgframework.web.system.pojo.base.DynamicDataSourceEntity;
import org.jeecgframework.web.system.service.DynamicDataSourceServiceI;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.workflow.common.WorkFlowGlobals;
import org.jeecgframework.workflow.dao.ActivitiDao;
import org.jeecgframework.workflow.pojo.base.TPProcess;
import org.jeecgframework.workflow.pojo.base.TSBusConfig;
import org.jeecgframework.workflow.service.ActivitiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @ClassName: ActivitiController
 * @Description: (流程引擎处理类)
 * @author jeecg
 * @date 2012-8-19 下午04:20:06
 * 
 */
@Controller("dataProcessController")
@RequestMapping("/dataProcessController")
public class DataProcessController {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	protected RepositoryService repositoryService;
	protected RuntimeService runtimeService;
	private ActivitiService activitiService;
	protected TaskService taskService;
	protected IdentityService identityService;
	private SystemService systemService;
	private String message;
	@Autowired
	private ActivitiDao activitiDao;
	@Autowired
	private AutoFormDbServiceI autoFormDbService;
	@Autowired
	private DynamicDataSourceServiceI dynamicDataSourceServiceI;

	@Autowired
	public void setIdentityService(IdentityService identityService) {
		this.identityService = identityService;
	}

	@Autowired
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	@Autowired
	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	@Autowired
	public void setRuntimeService(RuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}

	@Autowired
	public void setActivitiService(ActivitiService activitiService) {
		this.activitiService = activitiService;
	}

	@Autowired
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	/**
	 * 跳转到选择流程页面
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "goSelectProcess")
	public ModelAndView goSelectProcess(String id,String autoFormCode,HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("workflow/process/selectProcess");
		String hql = "from TSBusConfig where onlineId = ? and formType = 'autoform'";
		List<TSBusConfig> tsBusbaseList =  systemService.findHql(hql, autoFormCode);
		mv.addObject("tsBusbaseList",tsBusbaseList);
		mv.addObject("autoFormCode",autoFormCode);
		mv.addObject("id",id);
		return mv;
	}
	
	/**
	 * 选择的流程更新到
	 * @return
	 */
	@RequestMapping(params = "updateDataList")
	@ResponseBody
	public AjaxJson updateDataList(String id,String autoFormCode,String processkey,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		String message="更新成功";
		try {
			if(StringUtils.isEmpty(processkey)){
				throw new BusinessException("请选择流程");
			}
			AutoFormDataListEntity autoFormDataListEntity = this.systemService.get(AutoFormDataListEntity.class, id);
			autoFormDataListEntity.setProcTypeCode(processkey);
			this.systemService.updateEntitie(autoFormDataListEntity);
			j.setSuccess(true);
			j.setObj(autoFormDataListEntity);
		}catch (BusinessException e) {
			e.printStackTrace();
			j.setSuccess(false);
			message = e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
			message = "系统异常";
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 判断自定义表单关联的流程是否是一个，是更新数据返回流程数据
	 * @return
	 */
	@RequestMapping(params = "checkProcessOnly")
	@ResponseBody
	public AjaxJson checkProcessOnly(String autoFormListId,HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message="";
		try {
			AutoFormDataListEntity autoFormDataListEntity = this.systemService.get(AutoFormDataListEntity.class, autoFormListId);
			String hql = "from TSBusConfig where onlineId = ? and formType = 'autoform'";
			List<TSBusConfig> tsBusbaseList =  systemService.findHql(hql, autoFormDataListEntity.getAutoFormCode());
			if(tsBusbaseList==null||tsBusbaseList.size()<=0){
				j.setSuccess(false);
				message = "没有找到相关的流程配置";
			}else if(tsBusbaseList.size()==1){
				j.setSuccess(true);
				j.setObj("only");
				TSBusConfig TSBusConfig = tsBusbaseList.get(0);
				TPProcess process = TSBusConfig.getTPProcess(); 
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("processkey", process.getProcesskey());
 				j.setAttributes(map);
 				autoFormDataListEntity.setProcTypeCode(process.getProcesskey());
 				this.systemService.updateEntitie(autoFormDataListEntity);
			}else if(tsBusbaseList.size()>1){
				j.setSuccess(true);
				j.setObj("more");
			}
			
		}catch (BusinessException e) {
			e.printStackTrace();
			j.setSuccess(false);
			message = "系统异常";
		} catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
			message = "系统异常";
		}
		j.setMsg(message);
		return j;
	}
	
	
	
	/**
	 * 提交业务，启动流程[自定义表单模块]
	 * configId : 表名
	 * id : 业务数据ID
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "startAutoFormProcess")
	@ResponseBody
	public AjaxJson startAutoFormProcess(String id,String autoFormCode,String autoFormId,String processkey,HttpServletRequest request) {
		message = "启动流程,成功!";
		
		AjaxJson j = new AjaxJson();
		
		try {
			if(StringUtils.isEmpty(processkey)){
				throw new BusinessException("请选择流程");
			}
			AutoFormDataListEntity autoFormDataListEntity = this.systemService.get(AutoFormDataListEntity.class, id);
			String hql = "from TSBusConfig where onlineId = ? and formType = 'autoform' and TPProcess.processkey = ?";
			List<TSBusConfig> tsBusbaseList =  systemService.findHql(hql, autoFormCode,processkey);
			if(tsBusbaseList==null){
				throw new BusinessException("没有找到相关的流程配置");
			}
			TSBusConfig tsBusbase = tsBusbaseList.get(0);
			//TODO 获取业务数据，并且加载到流程变量中（自定义表单的一对多的获取主数据源的数据）
//			Map<String,Object> dataForm = dataBaseService.findOneForJdbc(autoFormCode, autoFormId);
			Map<String,Object> dataForm = getFormData(autoFormCode, autoFormId);
			//-----------update-begin-----for:中德福利（文档上传 关联了工作流报错）----date：20150514-----------------------------
			dataForm.put(WorkFlowGlobals.BPM_DATA_ID, autoFormId);
			//-----------update-end-----for:中德福利（文档上传 关联了工作流报错）----date：20150514-----------------------------
			dataForm.put(WorkFlowGlobals.BPM_AUTO_FORM_DATA_ID, id);
			//--------------------------------------------------------------------------------------------------------------------
			//update-begin--Author:zhangdaihao  Date:20150713 for：判断流程发起节点有没有个性化设置页面
			//获取发起流程配置的表单地址
			String BPM_FORM_CONTENT_URL  = activitiDao.getProcessStartNodeView(tsBusbase.getTPProcess().getId());
			if(oConvertUtils.isEmpty(BPM_FORM_CONTENT_URL)){
				//update-begin--Author:zhoujf  Date:20170328 for：online表单访问地址更换
//				BPM_FORM_CONTENT_URL = "cgFormBuildController.do?ftlForm&tableName="+configId+"&mode=read&load=detail&id="+id;
				BPM_FORM_CONTENT_URL = "autoFormController/af/"+autoFormCode+"/goViewPage.do?id="+autoFormId;
				//update-end--Author:zhoujf  Date:20170328 for：online表单访问地址更换
			}
			dataForm.put(WorkFlowGlobals.BPM_FORM_CONTENT_URL, BPM_FORM_CONTENT_URL);
			
			
			//update-begin--author:scott  date:20160301 for：全局移动端表单写入流程中-------
			//获取发起流程配置的表单地址
			String BPM_FORM_CONTENT_URL_MOBILE  = activitiDao.getProcessStartNodeViewMobile(tsBusbase.getTPProcess().getId());
			if(oConvertUtils.isEmpty(BPM_FORM_CONTENT_URL_MOBILE)){
				BPM_FORM_CONTENT_URL_MOBILE = "cgFormBuildController.do?mobileForm&tableName="+autoFormCode+"&mode=read&load=detail&id="+autoFormId;
			}
			dataForm.put(WorkFlowGlobals.BPM_FORM_CONTENT_URL_MOBILE, BPM_FORM_CONTENT_URL_MOBILE);
			//update-begin--author:scott  date:20160301 for：全局移动端表单写入流程中------
			
			//update-end--Author:zhangdaihao  Date:20150713 for：判断流程发起节点有没有个性化设置页面
			//--------------------------------------------------------------------------------------------------------------------
			
			
			dataForm.put(WorkFlowGlobals.BPM_FORM_KEY, autoFormCode);
			String create_by = autoFormDataListEntity.getCreateBy();//dataForm.get(DataBaseConstant.CREATE_BY_DB).toString();//业务数据创建人
			String data_id = id;//online数据ID
			
			//update-begin--Author:zhoujf  Date:20150804 for：启动流程事物处理
//			//获取onlinecoding表名
//			//1.加载出onlinecoding的表单数据（单表或主表）
//			//2.制定表单访问变量content_url,默认等于表单的查看页面
//			//3.通过onlinecoding表名,读取流程表单配置,获取流程实例
//			activitiService.startOnlineWorkflow(create_by, data_id, dataForm, tsBusbase);
//			//4.修改onlinecoding表单的BPM业务流程状态
//			dataForm.put(WorkFlowGlobals.BPM_STATUS, WorkFlowGlobals.BPM_BUS_STATUS_2);
//			dataBaseService.updateTable(configId, id, dataForm);
//			//----------------------------------------------------------------
//			//5.往原来的流程设计里面设置数据
//			Map mp = new HashMap();
//			mp.put("id", id);
//			mp.put("userid", dataForm.get(DataBaseConstant.CREATE_BY_DB));
//			mp.put("prjstateid", 2);
//			mp.put("busconfigid", tsBusbase.getId());
//			activitiDao.insert(mp);
			//----------------------------------------------------------------
			activitiService.startAutoFormProcess(create_by, data_id, dataForm, tsBusbase);
			//update-end--Author:zhoujf  Date:20150804 for：启动流程事物处理
		} catch (ActivitiException e) {
			if (e.getMessage().indexOf("no processes deployed with key") != -1) {
				message = "没有部署流程!,请在流程配置中部署流程!";
			} else if (e.getMessage().indexOf("Error while evaluating expression") != -1) {
				message = "启动流程失败,流程表达式异常!";
				try {
					message += e.getCause().getCause().getMessage();
				} catch (Exception e1) {
				}
			} else {
				message = "启动流程失败!请确认流程和表单是否关联!";
			}
		}  catch (BusinessException e) {
			message = "启动流程失败:"+e.getMessage();
			e.printStackTrace();
		} catch (Exception e) {
			message = "启动流程失败!,请确认流程和表单是否关联!";
			e.printStackTrace();
		}
		j.setMsg(message);
		return j;
	}
	
	
	/**
	 * 获取表单数据
	 * @param formName
	 * @param paramMap
	 * @return
	 */
	private Map<String, Object> getFormData(String autoFormCode,String autoFormId){
		String message = "";
		AutoFormEntity autoForm = this.systemService.findUniqueByProperty(AutoFormEntity.class, "formName", autoFormCode);
		if(autoForm==null){
			throw new BusinessException("没有找到自定义表单");
		}
		if(StringUtils.isEmpty(autoForm.getMainTableSource())){
			throw new BusinessException("自定义表单没有配置主数据源");
		}
		String hql = "from AutoFormDbEntity where autoFormId = ? and dbName = ? ";
		//装载数值的容器
		Map<String, List<Map<String, Object>>> paras = new HashMap<String, List<Map<String, Object>>>();
		List<AutoFormDbEntity> formDbList = this.systemService.findHql(hql, autoForm.getId(),autoForm.getMainTableSource());
		AutoFormDbEntity formDb = formDbList.get(0);
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> typeList = getColumnTypes(formDb.getTbDbTableName(),formDb.getDbKey());
		
		if("table".equals(formDb.getDbType())){
			//如果数据源类型为表类型，通过属性表里面的属性拼出SQL
			String hqlField = "from AutoFormDbFieldEntity where 1 = 1 AND aUTO_FORM_DB_ID = ? ";
		    try{
		    	List<AutoFormDbFieldEntity> autoFormDbFieldEntityList = systemService.findHql(hqlField,formDb.getId());
		    	
		    	if(autoFormDbFieldEntityList.size()>0){
		    		StringBuffer hqlTable = new StringBuffer().append("select ");
		    		for(AutoFormDbFieldEntity autoFormDbFieldEntity:autoFormDbFieldEntityList){
		    			//author:jg_renjie----start-----date:20160228--------for：TASK #704 【表单填报预览】针对特殊类型数据，需要进行转换，比如blob
		    			boolean flag = false;
		    			for(Map<String,Object> typeMap:typeList){
		    				String dataType = typeMap.get("dataType").toString().toUpperCase();
		    	        	String columnNm = typeMap.get("columnNm").toString().toUpperCase();
		    	        	
		    	        	if(dataType.contains("BLOB") && columnNm.equals(autoFormDbFieldEntity.getFieldName().toUpperCase())){
		    	        		hqlTable.append("CONVERT(GROUP_CONCAT("+autoFormDbFieldEntity.getFieldName()+") USING utf8) as "+autoFormDbFieldEntity.getFieldName()+",");
		    	        		flag = true;
		    	        	} 
		    			}
		    			if(!flag){
		    				hqlTable.append(autoFormDbFieldEntity.getFieldName()+",");
		    			}
		    			//author:jg_renjie----end-----date:20160228--------for：TASK #704 【表单填报预览】针对特殊类型数据，需要进行转换，比如blob
		    			
			    	}
		    		hqlTable.deleteCharAt(hqlTable.length()-1).append(" from "+formDb.getDbTableName());
		    		String id = autoFormId;
		    		hqlTable.append(" where ID ='").append(id).append("'");

		    		//update-start--Author:luobaoli  Date:20150701 for：如果数据源为空，那么以当前上下文中的DB配置为准，查询出表数据
					if("".equals(formDb.getDbKey())){
						//当前上下文中的DB环境，获取数据库表中的所有数据
						data = systemService.findForJdbc(hqlTable.toString());
					}
					//update-end--Author:luobaoli  Date:20150701 for：如果数据源为空，那么以当前上下文中的DB配置为准，查询出表数据
					else{
						DynamicDataSourceEntity dynamicDataSourceEntity = dynamicDataSourceServiceI.getDynamicDataSourceEntityForDbKey(formDb.getDbKey());
						if(dynamicDataSourceEntity!=null){
							data = DynamicDBUtil.findList(formDb.getDbKey(),hqlTable.toString());
						}
					}
					
		    	}else{
		    		message = "表属性配置有误！";
		    		throw new BusinessException(message);
		    	}
//		    	paras.put(formDb.getDbName(), data);
			}catch(Exception e){
				logger.info(e.getMessage());
			}
		}else if("sql".equals(formDb.getDbType())){
			//如果数据源类型为SQL类型，直接通过替换SQL里面的参数变量解析出可执行的SQL
			String dbDynSql = formDb.getDbDynSql();
			List<String> params = autoFormDbService.getSqlParams(dbDynSql);
			
			dbDynSql = dbDynSql.replaceAll("\\$\\{id\\}", autoFormId);
//			for(String param:params){
//				Object value = paramMap.get(param);
//				if(value==null){
//					continue;
//				}else if(value instanceof String[]){
//					String[] paramValue=(String[])value;
//					dbDynSql = dbDynSql.replaceAll("\\$\\{"+param+"\\}", paramValue[0]);
//                }else{
//                	String paramValue= value.toString();
//                	dbDynSql = dbDynSql.replaceAll("\\$\\{"+param+"\\}", paramValue);
//                }
//			}
			
			// 判断sql中是否还有没有被替换的变量，如果有，抛出错误！
			if (dbDynSql.contains("\\$")) {
				message = "动态SQL数据查询失败！";
				throw new BusinessException(message);
			} else {
				try {
					// update-start--Author:gengjiajia Date:20160616 for:TASK #1110  修改使用动态数据源查询数据
					// data = systemService.findForJdbc(dbDynSql);
//					Object dbKeys = paramMap.get("dbKey");
					Object dbKeys = null;
					if (oConvertUtils.isNotEmpty(dbKeys)) {
						if (dbKeys instanceof String) {
							String dbKey = dbKeys.toString();
							data = DynamicDBUtil.findList(dbKey, dbDynSql);
						} else {
							String[] keys = (String[]) dbKeys;
							String dbKey = keys[0];
							if(oConvertUtils.isNotEmpty(dbKey)){
								data = DynamicDBUtil.findList(dbKey, dbDynSql);
							}else{
								data = systemService.findForJdbc(dbDynSql);
							}
						}
					} else {
						data = systemService.findForJdbc(dbDynSql);
					}
					// update-start--Author:gengjiajia Date:20160616 for: #1110 修改使用动态数据源查询数据
				} catch (Exception e) {
					logger.info(e.getMessage());
					message = "动态SQL数据查询失败！";
					throw new BusinessException(message);
				}
			}
//			paras.put(formDb.getDbName(), formatData(typeList,data));
		}else{
			//预留给CLAZZ类型
		}
		List<Map<String, Object>>  list = formatData(typeList,data);
		if(list!=null){
			return list.get(0);
		}
		return  null;
	}
	
	/**
	 * 根据表名获取各个字段的类型
	 * @param dbTableNm
	 * @return
	 */
	//author:jg_renjie----start-----date:20160228--------for：TASK #704 【表单填报预览】针对特殊类型数据，需要进行转换，比如blob
	private List<Map<String, Object>> getColumnTypes(String dbTableNm,String dbkey){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String sql = "select  DATA_TYPE as dataType,COLUMN_NAME as columnNm from information_schema.COLUMNS where TABLE_NAME='"+dbTableNm.toUpperCase()+"'";
		//--author：zhoujf---start------date:20170216--------for:自定义表单获取列类型不同数据库获取问题
		//---------------------------------------------------------------------------------------
		//[DB SQL]
		if(CodeResourceUtil.DATABASE_TYPE.equals(ConvertDef.DATABASE_TYPE_MYSQL)){
			//mysql
			sql = "select COLUMN_NAME as columnNm,DATA_TYPE as dataType from information_schema.COLUMNS where TABLE_NAME='"+dbTableNm.toUpperCase()+"'";
		}else if(CodeResourceUtil.DATABASE_TYPE.equals(ConvertDef.DATABASE_TYPE_ORACLE)){
			//oracle
			sql = " select colstable.column_name columnNm, colstable.data_type dataType"
				+ " from user_tab_cols colstable "
				+ " inner join user_col_comments commentstable "
				+ " on colstable.column_name = commentstable.column_name "
				+ " where colstable.table_name = commentstable.table_name "
				+ " and colstable.table_name = '"+dbTableNm.toUpperCase()+"'";
		}else if(CodeResourceUtil.DATABASE_TYPE.equals(ConvertDef.DATABASE_TYPE_postgresql)){
			//postgresql
			sql = "SELECT a.attname AS  columnNm,t.typname AS dataType"
				   +" FROM pg_class c,pg_attribute  a,pg_type t "
				   +" WHERE c.relname = '"+dbTableNm.toUpperCase()+"' and a.attnum > 0  and a.attrelid = c.oid and a.atttypid = t.oid "
				   +" ORDER BY a.attnum ";
		}else if(CodeResourceUtil.DATABASE_TYPE.equals(ConvertDef.DATABASE_TYPE_SQL_SERVER)){
			//sqlserver
//			sql = "select cast(a.name as varchar(50)) columnNm,  cast(b.name as varchar(50)) dataType" +
//					"  from sys.columns a left join sys.types b on a.user_type_id=b.user_type_id left join sys.objects c on a.object_id=c.object_id and c.type='''U''' left join sys.extended_properties e on e.major_id=c.object_id and e.minor_id=a.column_id and e.class=1 where c.name='"+dbTableNm.toUpperCase()+"'";
			sql = "select  DATA_TYPE as dataType,COLUMN_NAME as columnNm from information_schema.COLUMNS where TABLE_NAME='"+dbTableNm.toUpperCase()+"'";
		}
		//---------------------------------------------------------------------------------------
		//--author：zhoujf---end------date:20170216--------for:自定义表单获取列类型不同数据库获取问题
		if(StringUtils.isNotBlank(dbkey)){
			list= DynamicDBUtil.findList(dbkey,sql);
		} else {
			list  = systemService.findForJdbc(sql);
		}
		return list;
	} 
	
	@SuppressWarnings({ "rawtypes", "rawtypes"})
	private List<Map<String, Object>> formatData(List<Map<String, Object>> typeList,List<Map<String, Object>> data){
		   for(Map<String, Object> hashmap:data){
				
			   java.util.Map.Entry entry = null;
			   Iterator it = hashmap.entrySet().iterator();
			   while(it.hasNext()){
				   entry = (java.util.Map.Entry)it.next();
				   
				   for(Map<String,Object> typeMap:typeList){
						String dataType = typeMap.get("dataType").toString().toUpperCase();
			        	String columnNm = typeMap.get("columnNm").toString().toUpperCase();
			        			        	
			        	if(dataType.contains("BLOB") && columnNm.equals(entry.getKey().toString().toUpperCase())){
			        		
							String srt2="";
							try {
								//blob类型不处理
//								srt2 = new String((byte[])entry.getValue(),"UTF-8");
								hashmap.put(entry.getKey().toString(), srt2);
							} catch (Exception e) {
								e.printStackTrace();
							}
			        	}
			        	
					}
			   }
		   }
			return data;
		}
	
}
