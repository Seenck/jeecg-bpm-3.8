package org.jeecgframework.web.cgform.controller.excel;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.DataBaseConstant;
import org.jeecgframework.core.util.DBTypeUtil;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.MutiLangUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.UUIDGenerator;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.params.ExcelExportEntity;
import org.jeecgframework.poi.excel.entity.vo.MapExcelConstants;
import org.jeecgframework.poi.handler.impl.ExcelDataHandlerDefaultImpl;
import org.jeecgframework.poi.util.PoiPublicUtil;
import org.jeecgframework.web.cgform.common.CgAutoListConstant;
import org.jeecgframework.web.cgform.entity.config.CgFormFieldEntity;
import org.jeecgframework.web.cgform.entity.config.CgFormHeadEntity;
import org.jeecgframework.web.cgform.service.autolist.CgTableServiceI;
import org.jeecgframework.web.cgform.service.autolist.ConfigServiceI;
import org.jeecgframework.web.cgform.service.build.DataBaseService;
import org.jeecgframework.web.cgform.service.config.CgFormFieldServiceI;
import org.jeecgframework.web.cgform.service.impl.config.util.FieldNumComparator;
import org.jeecgframework.web.cgform.util.QueryParamUtil;
import org.jeecgframework.web.system.pojo.base.DictEntity;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.jdbc.support.incrementer.OracleSequenceMaxValueIncrementer;
import org.springframework.jdbc.support.incrementer.PostgreSQLSequenceMaxValueIncrementer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

/**
 * @author huiyong
 * @ClassName: excelTempletController
 * @Description: excel模版处理
 */
//@Scope("prototype")
@Controller
@RequestMapping("/excelTempletController")
public class ExcelTempletController extends BaseController {
	private static final Logger logger = Logger
			.getLogger(ExcelTempletController.class);
	@Autowired
	private ConfigServiceI configService;
	@Autowired
	private CgFormFieldServiceI cgFormFieldService;
	@Autowired
	private DataBaseService dataBaseService;
	@Autowired
	private CgTableServiceI cgTableService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private AbstractRoutingDataSource dataSource;


	/**
	 * 导出excel模版
	 *
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("all")
	@RequestMapping(params = "exportXls")
	//update-begin--Author:dangzhenghui  Date:20170429 for：TASK #1906 【online excel】Online excel 导出功能改进
	public String exportXls(HttpServletRequest request, ModelMap modelMap,
							HttpServletResponse response, String field, DataGrid dataGrid,String id) {//update-begin--Author:dangzhenghui  Date:20170429 for：TASK #1906 【online excel】Online excel 导出功能改进
		//update-end--Author:dangzhenghui  Date:20170429 for：TASK #1906 【online excel】Online excel 导出功能改进

		String codedFileName = "文件";
		String sheetName = "导出信息";
		List<CgFormFieldEntity> lists = null;
		if (StringUtil.isNotEmpty(request.getParameter("tableName"))) {
			String configId = request.getParameter("tableName");
			String jversion = cgFormFieldService.getCgFormVersionByTableName(configId);
			Map<String, Object> configs = configService.queryConfigs(configId, jversion);
			Map params = new HashMap<String, Object>();
			//step.2 获取查询条件以及值
			List<CgFormFieldEntity> beans = (List<CgFormFieldEntity>) configs.get(CgAutoListConstant.FILEDS);
			for (CgFormFieldEntity b : beans) {
//				if(CgAutoListConstant.BOOL_TRUE.equals(b.getIsQuery())){
				QueryParamUtil.loadQueryParams(request, b, params);
//				}
			}
			//--author：zhoujf---start------date:20170207--------for:online表单物理表查询数据异常处理
			configId = configId.split("__")[0];
			//--author：zhoujf---end------date:20170207--------for:online表单物理表查询数据异常处理
			List<Map<String, Object>> result = cgTableService.querySingle(configId, field.toString(), params, null, null, 1, 99999);

			//表单列集合
			lists = (List<CgFormFieldEntity>) configs.get(CgAutoListConstant.FILEDS);
			for (int i = lists.size() - 1; i >= 0; i--) {
				if (!field.contains(lists.get(i).getFieldName())) {
					lists.remove(i);
				}
			}
			handlePageDic(beans, result);
			//处理字典项
			dealDic(result, beans);
			//表的中文名称
			sheetName = (String) configs.get(CgAutoListConstant.CONFIG_NAME);
			//表的英文名称
			String tableName = (String) configs.get(CgAutoListConstant.TABLENAME);
			//导出文件名称 form表单中文名-v版本号.xsl
			codedFileName = sheetName + "_" + tableName + "-v" + (String) configs.get(CgAutoListConstant.CONFIG_VERSION);

			//--author：JueYue---------date:20150615--------for: 导出替换成EasyPoi
			List<ExcelExportEntity> entityList = convertToExportEntity(lists);

			//判断是不是主表
			if (CgAutoListConstant.JFORM_TYPE_MAIN_TALBE == Integer.parseInt(configs.get(CgAutoListConstant.TABLE_TYPE).toString())) {
				String subTableStr = configs.get(CgAutoListConstant.SUB_TABLES).toString();
				if (StringUtils.isNotEmpty(subTableStr)) {
					String[] subTables = subTableStr.split(",");
					for (String subTable : subTables) {
						addAllSubTableDate(subTable, configs, result, entityList);
					}
				}
			}
			//update-begin--Author:dangzhenghui  Date:20170429 for：TASK #1906 【online excel】Online excel 导出功能改进
			List<Map<String, Object>> nresult=new ArrayList<Map<String, Object>>();
			if (StringUtil.isNotEmpty(id)){
				for(Map map:result){
					//update-begin--Author:zhoujf  Date:20180402 for：TASK #2464 【bug】excel功能bug(Id非UUID的报错问题)
					if(id.contains(map.get("id").toString())){
						nresult.add(map);
					}
					//update-end--Author:zhoujf  Date:20180402 for：TASK #2464 【bug】excel功能bug(Id非UUID的报错问题)
				}
			}else {
				nresult.addAll(result);
			}
			//update-begin--Author:dangzhenghui  Date:20170429 for：TASK #1906 【online excel】Online excel 导出功能改进
			modelMap.put(MapExcelConstants.ENTITY_LIST, entityList);
			modelMap.put(MapExcelConstants.MAP_LIST, nresult);
			modelMap.put(MapExcelConstants.FILE_NAME, codedFileName);
			modelMap.put(MapExcelConstants.PARAMS, new ExportParams(null, sheetName));
			return MapExcelConstants.JEECG_MAP_EXCEL_VIEW;
			//--author：JueYue---------date:20150615--------for: 导出替换成EasyPoi
		} else {
			throw new BusinessException("参数错误");
		}

	}

	/**
	 * 把基础数据转换成Excel导出的数据
	 *
	 * @param lists
	 * @return
	 */
	private List<ExcelExportEntity> convertToExportEntity(List<CgFormFieldEntity> lists) {
		// 对字段列按顺序排序
		Collections.sort(lists, new FieldNumComparator());
		List<ExcelExportEntity> entityList = new ArrayList<ExcelExportEntity>();
		for (int i = 0; i < lists.size(); i++) {
			if (lists.get(i).getIsShow().equals("Y")) {
				ExcelExportEntity entity = new ExcelExportEntity(lists.get(i).getContent(), lists.get(i).getFieldName());
				int columnWidth = lists.get(i).getLength() == 0 ? 12 : lists.get(i).getLength() > 30 ? 30 : lists.get(i).getLength();
				if (lists.get(i).getShowType().equals("date")) {
					entity.setFormat("yyyy-MM-dd");
				} else if (lists.get(i).getShowType().equals("datetime")) {
					entity.setFormat("yyyy-MM-dd HH:mm:ss");
				}
				entity.setWidth(columnWidth);
				entityList.add(entity);
			}
		}
		return entityList;
	}

	/**
	 * 把从表数据加到主表中
	 *
	 * @param subTable
	 * @param configs
	 * @param result
	 * @param entityList
	 */
	private void addAllSubTableDate(String subTable, Map<String, Object> configs, List<Map<String, Object>> result, List<ExcelExportEntity> entityList) {
		String jversion = cgFormFieldService.getCgFormVersionByTableName(subTable);
		Map<String, Object> subConfigs = configService.queryConfigs(subTable, jversion);
		ExcelExportEntity tableEntity = new ExcelExportEntity(subConfigs.get(CgAutoListConstant.CONFIG_NAME).toString(), subTable);
		List<CgFormFieldEntity> beans = (List<CgFormFieldEntity>) subConfigs.get(CgAutoListConstant.FILEDS);
		tableEntity.setList(convertToExportEntity(beans));
		entityList.add(tableEntity);
		// 对字段列按顺序排序
		for (int i = 0; i < result.size(); i++) {
			List<Map<String, Object>> subResult = cgFormFieldService.getSubTableData(configs.get(CgAutoListConstant.CONFIG_ID).toString(), subTable, result.get(i).get("id"));
			handlePageDic(beans, subResult);
			dealDic(subResult, beans);
			result.get(i).put(subTable,subResult);
		}
	}

	/**
	 * 处理页面中若存在checkbox只能显示code值而不能显示text值问题
	 *
	 * @param beans
	 * @param result
	 */
	private void handlePageDic(List<CgFormFieldEntity> beans, List<Map<String, Object>> result) {
		Map<String, Object> dicMap = new HashMap<String, Object>();
		for (CgFormFieldEntity b : beans) {
			loadDic(dicMap, b);
			List<DictEntity> dicList = (List<DictEntity>) dicMap.get(CgAutoListConstant.FIELD_DICTLIST);
			if (dicList.size() > 0) {
				for (Map<String, Object> resultMap : result) {
					StringBuffer sb = new StringBuffer();
					String value = (String) resultMap.get(b.getFieldName());
					if (oConvertUtils.isEmpty(value)) {
						continue;
					}
					String[] arrayVal = value.split(",");
					if (arrayVal.length > 1) {
						for (String val : arrayVal) {
							for (DictEntity dictEntity : dicList) {
								if (val.equals(dictEntity.getTypecode())) {
									sb.append(dictEntity.getTypename());
									sb.append(",");
								}

							}
						}
						if(oConvertUtils.isNotEmpty(sb.toString())){
							resultMap.put(b.getFieldName(), sb.toString().substring(0, sb.toString().length() - 1));
						}
					}

				}
			}
		}
	}

	@RequestMapping(params = "goImplXls", method = RequestMethod.GET)
	public ModelAndView goImplXls(HttpServletRequest request) {
		request.setAttribute("tableName", request.getParameter("tableName"));
		return new ModelAndView("jeecg/cgform/excel/upload");
	}

	/**
	 * 上传模版数据
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	@SuppressWarnings("all")
	public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
		String message = "上传成功";
		AjaxJson j = new AjaxJson();
		String configId = request.getParameter("tableName");
		String jversion = cgFormFieldService.getCgFormVersionByTableName(configId);
		Map<String, Object> configs = configService.queryConfigs(configId, jversion);
		//数据库中版本号
		String version = (String) configs.get(CgAutoListConstant.CONFIG_VERSION);
		List<CgFormFieldEntity> lists = (List<CgFormFieldEntity>) configs.get(CgAutoListConstant.FILEDS);
		Object subTables = configs.get("subTables");
		List<String> subTabList = new ArrayList();
		if (null != subTables) {
			subTabList.addAll(Arrays.asList(subTables.toString().split(",")));
		}
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			//上传文件的版本号
			String docVersion = getDocVersion(file.getOriginalFilename());
			if (docVersion.equals(version)) {
				List<Map<String, Object>> listDate;
				//--author：luobaoli---------date:20150615--------for: 处理service层抛出的异常
				try {
					//读取excel模版数据
					//--author：JueYue---------date:20150622--------for: 修改为EasyPoi 的导入
					ImportParams params = new ImportParams();
					params.setDataHanlder(new CgFormExcelHandler(lists));
					listDate = ExcelImportUtil.importExcel(file.getInputStream(), Map.class, params);
					//--author：JueYue---------date:20150622--------for: 修改为EasyPoi 的导入
					if (listDate == null) {
						message = "识别模版数据错误";
						logger.error(message);
					} else {
						//--author：zhoujf---start------date:20170207--------for:online表单物理表查询数据异常处理
						configId = configId.split("__")[0];
						//--author：zhoujf---end------date:20170207--------for:online表单物理表查询数据异常处理
						//update-begin--Author:xuelin  Date:20171203 for：TASK #2098 【excel问题】 Online 一对多导入失败--------------------
						//update-begin--Author:xuelin  Date:20171101 for：TASK #2401 【bug】excel一对多导入外键逻辑问题
						//update-begin--Author:zhoujf  Date:20180402 for：TASK #2464 【bug】excel功能bug(Id非UUID的报错问题)
//						String mainId = "";
						Object mainId = "";
						for (Map<String, Object> map : listDate) {
							//标志是否为主表数据
							boolean isMainData = false;
							Set<String> keySet = map.keySet();
							Map mainData = new HashMap();
							for (String key : keySet) {
								if (key.indexOf("$subTable$")==-1) {
									if (key.indexOf("$mainTable$")!=-1 && StringUtils.isNotEmpty(map.get(key).toString())) {
										isMainData = true;
//										mainId = UUIDGenerator.generate();
										mainId = getPkValue(configId);
									}
									mainData.put(key.replace("$mainTable$", ""), map.get(key));
								}
							}
							//update-begin--Author:zhoujf  Date:20180402 for：TASK #2464 【bug】excel功能bug
//							map.put("$mainTable$id", mainId);//为子表准备
							if (isMainData) {
								//update-begin-author:taoYan date:20180207 for:online excel导入处理数据字典---
								//处理字典项
								dealDicForImport(mainData, lists);
								//update-end-author:taoYan date:20180207 for:online excel导入处理数据字典---
								mainData.put("id", mainId);//主表数据
								dataBaseService.insertTable(configId, mainData);
								mainId =  mainData.get("id");
							}
							map.put("$mainTable$id", mainId);//为子表准备
							//update-end--Author:zhoujf  Date:20180402 for：TASK #2464 【bug】excel功能bug
						}
						//update-end--Author:zhoujf  Date:20180402 for：TASK #2464 【bug】excel功能bug(Id非UUID的报错问题)
						//导入子表数据，如果有
						for (String subConfigId: subTabList) {
							Map<String, Object> subConfigs = configService.queryConfigs(subConfigId, jversion);
							List<CgFormFieldEntity> subLists = (List<CgFormFieldEntity>) subConfigs.get(CgAutoListConstant.FILEDS);
							//将表头字段替换成数据库中对应的字段名
							String configName = subConfigs.get("config_name").toString();
							for (Map<String, Object> map : listDate) {
								//标志是否为子表数据
								boolean isSubData = false;
								Map subData = new HashMap();
								for (CgFormFieldEntity fieldEntity: subLists) {
									String mainTab = fieldEntity.getMainTable();
									String mainField = fieldEntity.getMainField();
									boolean isForeignKey = configId.equals(mainTab) && StringUtil.isNotEmpty(mainField);
									String tempKey = configName+"_"+fieldEntity.getContent();
									//已经考虑兼容多个外键的情况
									if(isForeignKey){
										subData.put(fieldEntity.getFieldName(), map.get("$mainTable$"+mainField));
									}
									Object subObj = map.get("$subTable$"+tempKey);
									if (null != subObj && StringUtils.isNotEmpty(subObj.toString())) {
										isSubData = true;
										//System.out.println(tempKey+"=>"+fieldEntity.getFieldName()+"--"+subObj);
										subData.put(fieldEntity.getFieldName(), subObj);
									}
								}
								//设置子表记录ID
								if (isSubData) {
									//update-begin-author:taoYan date:20180207 for:online excel导入处理数据字典---
									//处理字典项
									dealDicForImport(subData, subLists);
									//update-end-author:taoYan date:20180207 for:online excel导入处理数据字典---
									//update-begin--Author:zhoujf  Date:20180402 for：TASK #2464 【bug】excel功能bug(Id非UUID的报错问题)
//									subData.put("id", UUIDGenerator.generate());
									subData.put("id", getPkValue(subConfigId));
									//update-end--Author:zhoujf  Date:20180402 for：TASK #2464 【bug】excel功能bug(Id非UUID的报错问题)
									dataBaseService.insertTable(subConfigId, subData);
								}
							}
						}
						//update-end--Author:xuelin  Date:20171101 for：TASK #2401 【bug】excel一对多导入外键逻辑问题
						//update-end--Author:xuelin  Date:20171203 for：TASK #2098 【excel问题】 Online 一对多导入失败--------------------
						message = "文件导入成功！";
					}
				} catch (Exception e) {
					message = "文件导入失败！";
					logger.error(ExceptionUtil.getExceptionMessage(e));
				}
				//--author：luobaoli---------date:20150615--------for: 处理service层抛出的异常
			} else {
				message = "模版文件版本和表达不匹配，请重新下载模版";
				logger.error(message);
			}
		}
		j.setMsg(message);
		return j;
	}
	
	//update-begin--Author:zhoujf  Date:20180402 for：TASK #2464 【bug】excel功能bug(Id非UUID的报错问题)
	/**
	 * 根据主键策略获取实际插入的主键值
	 * @param tableName 表单名称
	 * @return
	 */
	public Object getPkValue(String tableName) {
		Object pkValue = null;
		CgFormHeadEntity  cghead = cgFormFieldService.getCgFormHeadByTableName(tableName);
		String dbType = DBTypeUtil.getDBType();
		String pkType = cghead.getJformPkType();
		String pkSequence = cghead.getJformPkSequence();
		if(StringUtil.isNotEmpty(pkType)&&"UUID".equalsIgnoreCase(pkType)){
			pkValue = UUIDGenerator.generate();
		}else if(StringUtil.isNotEmpty(pkType)&&"NATIVE".equalsIgnoreCase(pkType)){
			if(StringUtil.isNotEmpty(dbType)&&"oracle".equalsIgnoreCase(dbType)){
				OracleSequenceMaxValueIncrementer incr = new OracleSequenceMaxValueIncrementer(dataSource, "HIBERNATE_SEQUENCE");
				try{
					pkValue = incr.nextLongValue();
				}catch (Exception e) {
					logger.error(e,e);
				}
			}else if(StringUtil.isNotEmpty(dbType)&&"postgres".equalsIgnoreCase(dbType)){
				PostgreSQLSequenceMaxValueIncrementer incr = new PostgreSQLSequenceMaxValueIncrementer(dataSource, "HIBERNATE_SEQUENCE");
				try{
					pkValue = incr.nextLongValue();
				}catch (Exception e) {
					logger.error(e,e);
				}
			}else{
				pkValue = null;
			}
		}else if(StringUtil.isNotEmpty(pkType)&&"SEQUENCE".equalsIgnoreCase(pkType)){
			if(StringUtil.isNotEmpty(dbType)&&"oracle".equalsIgnoreCase(dbType)){
				OracleSequenceMaxValueIncrementer incr = new OracleSequenceMaxValueIncrementer(dataSource, pkSequence);
				try{
					pkValue = incr.nextLongValue();
				}catch (Exception e) {
					logger.error(e,e);
				}
			}else if(StringUtil.isNotEmpty(dbType)&&"postgres".equalsIgnoreCase(dbType)){
				PostgreSQLSequenceMaxValueIncrementer incr = new PostgreSQLSequenceMaxValueIncrementer(dataSource, pkSequence);
				try{
					pkValue = incr.nextLongValue();
				}catch (Exception e) {
					logger.error(e,e);
				}
			}else{
				pkValue = null;
			}
		}else{
			pkValue = UUIDGenerator.generate();
		}
		return pkValue;
	}
	//update-end--Author:zhoujf  Date:20180402 for：TASK #2464 【bug】excel功能bug(Id非UUID的报错问题)

	/**
	 * 返回模版文件的版本号
	 * 默认文件名是： form表单中文名-v版本号.xsl
	 * 也有可能是： form表单中文名-v版本号(1).xsl
	 *
	 * @param docName
	 * @return
	 */
	private static String getDocVersion(String docName) {
		//--author：JueYue---------date:20150621--------for: 删除名字空格
		if (docName.indexOf("(") > 0) {
			return docName.substring(docName.indexOf("-v") + 2, docName.indexOf("(")).trim();
		} else {
			return docName.substring(docName.indexOf("-v") + 2, docName.indexOf(".")).trim();
		}
		//--author：JueYue---------date:20150621--------for: 删除名字空格
	}

	private void loadDic(Map m, CgFormFieldEntity bean) {
		String dicT = bean.getDictTable();//字典Table
		String dicF = bean.getDictField();//字典Code
		String dicText = bean.getDictText();//字典Text
		if (StringUtil.isEmpty(dicT) && StringUtil.isEmpty(dicF)) {
			//如果这两个字段都为空，则没有数据字典
			m.put(CgAutoListConstant.FIELD_DICTLIST, new ArrayList(0));
			return;
		}
		if (!bean.getShowType().equals("popup")) {
			List<DictEntity> dicDatas = queryDic(dicT, dicF, dicText);
			m.put(CgAutoListConstant.FIELD_DICTLIST, dicDatas);
		} else {
			m.put(CgAutoListConstant.FIELD_DICTLIST, new ArrayList(0));
		}
	}

	private List<DictEntity> queryDic(String dicTable, String dicCode, String dicText) {
		return this.systemService.queryDict(dicTable, dicCode, dicText);
	}

	/**
	 * 处理数据字典
	 *
	 * @param result 查询的结果集
	 * @param beans  字段配置
	 */
	@SuppressWarnings("unchecked")
	private void dealDic(List<Map<String, Object>> result,
						 List<CgFormFieldEntity> beans) {
		for (CgFormFieldEntity bean : beans) {
			String dicTable = bean.getDictTable();//字典Table
			String dicCode = bean.getDictField();//字典Code
			String dicText = bean.getDictText();//字典text
			if (StringUtil.isEmpty(dicTable) && StringUtil.isEmpty(dicCode)) {
				//不需要处理字典
				continue;
			} else {
				if (!bean.getShowType().equals("popup")) {
					List<DictEntity> dicDataList = queryDic(dicTable, dicCode, dicText);
					for (Map r : result) {
						String value = String.valueOf(r.get(bean.getFieldName()));
						for (DictEntity dictEntity : dicDataList) {
							if (value.equalsIgnoreCase(dictEntity.getTypecode())) {
								//------------------update-begin------for:-国际化处理-----------------------author:zhagndaihao------------
								r.put(bean.getFieldName(), MutiLangUtil.getLang(dictEntity.getTypename()));
								//------------------update-end-----for:-国际化处理----------------------------author:zhagndaihao---------
							}
						}
					}
				}
			}
		}
	}


	//update-begin-author:taoYan date:20180207 for:online excel导入处理数据字典---
	/**
	 * 处理数据字典
	 * @param result 查询的结果集
	 * @param beans  字段配置
	 */
	@SuppressWarnings("unchecked")
	private void dealDicForImport(Map result,List<CgFormFieldEntity> beans) {
		for (CgFormFieldEntity bean : beans) {
			String dicTable = bean.getDictTable();//字典Table
			String dicCode = bean.getDictField();//字典Code
			String dicText = bean.getDictText();//字典text
			if (StringUtil.isEmpty(dicTable) && StringUtil.isEmpty(dicCode)) {
				//不需要处理字典
				continue;
			} else {
				if (!bean.getShowType().equals("popup")) {
					List<DictEntity> dicDataList = queryDic(dicTable, dicCode, dicText);
						String value = String.valueOf(result.get(bean.getFieldName()));
						for (DictEntity dictEntity : dicDataList) {
							if (value.equals(dictEntity.getTypename())) {
								result.put(bean.getFieldName(), dictEntity.getTypecode());
							}
						}
				}
			}
		}
	}
	//update-end-author:taoYan date:20180207 for:online excel导入处理数据字典---
	
	
	private class CgFormExcelHandler extends ExcelDataHandlerDefaultImpl {

		Map<String, CgFormFieldEntity> fieldMap;

		public CgFormExcelHandler(List<CgFormFieldEntity> lists) {
			fieldMap = convertDate(lists);
		}

		private Map<String, CgFormFieldEntity> convertDate(List<CgFormFieldEntity> lists) {
			Map<String, CgFormFieldEntity> maps = new HashMap<String, CgFormFieldEntity>();

			for (CgFormFieldEntity cgFormFieldEntity : lists) {
				maps.put(cgFormFieldEntity.getContent(), cgFormFieldEntity);
			}
			return maps;
		}


		@Override
		public void setMapValue(Map<String, Object> map, String originKey, Object value) {
			if (value instanceof Double) {
				map.put(getRealKey(originKey), PoiPublicUtil.doubleToString((Double) value));
			} else {
				map.put(getRealKey(originKey), value.toString());
			}
		}
		//update-begin--Author:xuelin  Date:20171203 for：TASK #2098 【excel问题】 Online 一对多导入失败--------------------
		private String getRealKey(String originKey) {
			if (fieldMap.containsKey(originKey)) {
				//主表字段
				return "$mainTable$"+fieldMap.get(originKey).getFieldName();
			}
			//子表字段
			return "$subTable$"+originKey;
		}
		//update-end--Author:xuelin  Date:20171203 for：TASK #2098 【excel问题】 Online 一对多导入失败--------------------

	}
}
