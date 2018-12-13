package org.jeecgframework.core.extend.hqlsearch;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.annotation.query.QueryTimeFormat;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.common.QueryCondition;
import org.jeecgframework.core.extend.hqlsearch.parse.ObjectParseUtil;
import org.jeecgframework.core.extend.hqlsearch.parse.PageValueConvertRuleEnum;
import org.jeecgframework.core.extend.hqlsearch.parse.vo.HqlRuleEnum;
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.core.util.JeecgDataAutorUtils;
import org.jeecgframework.core.util.LogUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.web.system.pojo.base.TSDataRule;
import org.springframework.util.NumberUtils;

/**
 * 
 * @author 张代浩
 * @de
 * 
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class HqlGenerateUtil {

	/** 时间查询符号 */
	private static final String END = "_end";
	private static final String BEGIN = "_begin";

	//add-begin--Author:yugwu  Date:20170812 for:TASK #2267 【bug】网友问题 SimpleDateFormat多线程不安全问题----
	private static final ThreadLocal<SimpleDateFormat> local = new ThreadLocal<SimpleDateFormat>();
	private static SimpleDateFormat getTime(){
		SimpleDateFormat time = local.get();
		if(time == null){
			time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			local.set(time);
		}
		return time;
	}
	//add-end--Author:yugwu  Date:20170812 for:TASK #2267 【bug】网友问题 SimpleDateFormat多线程不安全问题----
	/**自定义sql表达式时rule_column的默认前缀*/
	private static final String SQL_RULES_COLUMN = "SQL_RULES_COLUMN";

	/**
	 * 自动生成查询条件HQL 模糊查询 不带有日期组合
	 * 
	 * @param cq
	 * @param searchObj
	 * @throws Exception
	 */
	public static void installHql(CriteriaQuery cq, Object searchObj) {
//		--author：龙金波 ------start---date：20150519--------for：统一函数处理sqlbuilder---------------------------------- 
		installHql(cq,searchObj,null);
//		--author：龙金波 ------end---date：20150519--------for：统一函数处理sqlbuilder---------------------------------- 

	}

	/**
	 * 自动生成查询条件HQL（扩展区间查询功能）
	 * 
	 * @param cq
	 * @param searchObj
	 * @param parameterMap
	 *            request参数集合，封装了所有查询信息
	 */
	public static void installHql(CriteriaQuery cq, Object searchObj,Map<String, String[]> parameterMap) {
		installHqlJoinAlias(cq, searchObj, getRuleMap(), parameterMap, "");
//		--author：龙金波 ------start---date：20150422--------for：增加一个特殊sql参数处理---------------------------------- 
		try{
			String json= null;
			if(StringUtil.isNotEmpty(cq.getDataGrid().getSqlbuilder())){
				json =cq.getDataGrid().getSqlbuilder();
			}else if(parameterMap!=null&&StringUtil.isNotEmpty(parameterMap.get("sqlbuilder"))){
				json = parameterMap.get("sqlbuilder")[0];
			}
			if(StringUtil.isNotEmpty(json)){
				List<QueryCondition> list  = JSONHelper.toList(json , QueryCondition.class);
				String sql=getSql(list,"",searchObj.getClass());
				LogUtil.debug("DEBUG sqlbuilder:"+sql);
				//TODO 此用法在多表关联查询，两个表存在相同字段的时候，会存在问题（hibernate维护的实体关系）
				cq.add(Restrictions.sqlRestriction(sql));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
//		--author：龙金波 ------start---date：201504022--------for：增加一个特殊sql参数处理---------------------------------- 

		cq.add();
	}

	/**
	 * 添加Alias别名的查询
	 * 
	 * @date 2014年1月19日
	 * @param cq
	 * @param searchObj
	 * @param parameterMap
	 * @param alias
	 */
	private static void installHqlJoinAlias(CriteriaQuery cq, Object searchObj,
			Map<String, TSDataRule> ruleMap,
			Map<String, String[]> parameterMap, String alias) {
		PropertyDescriptor origDescriptors[] = PropertyUtils.getPropertyDescriptors(searchObj);
		///直接拿到rulemap判断有没有key为xxx的直接循环组装cq
		//update-begin-author:taoYan date:20170811 for:自定义sql表达式条件拼接---
		boolean addPreCondition = true;
		for (String c : ruleMap.keySet()) {
			if(oConvertUtils.isNotEmpty(c) && c.startsWith(SQL_RULES_COLUMN)){
				if(addPreCondition){
					cq.add(Restrictions.sqlRestriction("1=1"));
					addPreCondition = false;
				}
				cq.add(Restrictions.sqlRestriction("("+getSqlRuleValue(ruleMap.get(c).getRuleValue())+")"));
			}
		}
		//update-end-author:taoYan date:20170811 for:自定义sql表达式条件拼接---
		String aliasName, name, type;
		for (int i = 0; i < origDescriptors.length; i++) {
			aliasName = (alias.equals("") ? "" : alias + ".")+ origDescriptors[i].getName();
			name = origDescriptors[i].getName();
			type = origDescriptors[i].getPropertyType().toString();
			try {
				if (judgedIsUselessField(name)|| !PropertyUtils.isReadable(searchObj, name)) {
					continue;
				}
				// 如果规则包含这个属性
				if (ruleMap.containsKey(aliasName)) {
					addRuleToCriteria(ruleMap.get(aliasName), aliasName,origDescriptors[i].getPropertyType(), cq);
				}

				// 添加 判断是否有区间值
				String beginValue = null;
				String endValue = null;
				if (parameterMap != null&& parameterMap.containsKey(name + BEGIN)) {
					beginValue = parameterMap.get(name + BEGIN)[0].trim();
				}
				if (parameterMap != null&& parameterMap.containsKey(name + END)) {
					endValue = parameterMap.get(name + END)[0].trim();
				}

				Object value = PropertyUtils.getSimpleProperty(searchObj, name);
				// 根据类型分类处理
				if (type.contains("class java.lang")
						|| type.contains("class java.math")) {
					// ------------update--Author:JueYue Date:2014-8-23
					// for：查询拼装的替换
					if (value != null && !value.equals("")) {
						//update-begin-Author:xuelin  Date:20170712 for：TASK #2205 【UI标签库】列表查询条件动态生成，下拉换成redio模式切换 -->
						//checkbox多选查询
						if (null != value && value.toString().startsWith(",") && value.toString().endsWith(",")) {
							String[] vals = value.toString().replace(",,", ",").split(",");
							//Criteria.add(Restrictions.or(cq, Restrictions.or(Restrictions.or(lhs, rhs))));
							Disjunction dis = Restrictions.disjunction();//替代多个Restrictions.or
							for (String val : vals) {
								if (StringUtils.isNotBlank(val)) {
									dis.add(Restrictions.eq(name, val));
								}
							}
							cq.add(dis);							
						}else{
						
							HqlRuleEnum rule = PageValueConvertRuleEnum.convert(value);
							
							//update-begin--Author:zzl  Date:20151123 for：加入配置属性可默认进行模糊查询
							//update-begin--Author:jg_renjie  Date:20150328 for：#1003:【bug】单表模式年龄查询报错
	//						if(HqlRuleEnum.LIKE.equals(rule)&&(!(value+"").contains("*"))&&!"class java.lang.Integer".contains(type)){
	//							value="*%"+String.valueOf(value.toString())+"%*";
	//						} else {
	//							rule = HqlRuleEnum.EQ;
	//						}
							//update-end--Author:jg_renjie  Date:20150328 for：#1003:【bug】单表模式年龄查询报错
							//update-end--Author:zzl  Date:20151123 for：加入配置属性可默认进行模糊查询
							
							value = PageValueConvertRuleEnum.replaceValue(rule,value);
							ObjectParseUtil.addCriteria(cq, aliasName, rule, value);
						}
						//update-end-Author:xuelin  Date:20170712 for：TASK #2205 【UI标签库】列表查询条件动态生成，下拉换成redio模式切换 -->
					} else if (parameterMap != null) {
						//update-end--Author:ll3231@126.com  Date:20151222 for：支持所有类型数据
						Object beginValue_=null , endValue_ =null;
						if ("class java.lang.Integer".equals(type)) {
							if(!"".equals(beginValue)&&null!=beginValue)
								beginValue_ = Integer.parseInt(beginValue);
							if(!"".equals(endValue)&&null!=endValue)
								endValue_ =Integer.parseInt(endValue);
						} else if ("class java.math.BigDecimal".equals(type)) {
							if(!"".equals(beginValue)&&null!=beginValue)
								beginValue_ = new BigDecimal(beginValue);
							if(!"".equals(endValue)&&null!=endValue)
								endValue_ = new BigDecimal(endValue);
						} else if ("class java.lang.Short".equals(type)) {
							if(!"".equals(beginValue)&&null!=beginValue)
								beginValue_ =Short.parseShort(beginValue);
							if(!"".equals(endValue)&&null!=endValue)
								endValue_ =Short.parseShort(endValue);
						} else if ("class java.lang.Long".equals(type)) {
							if(!"".equals(beginValue)&&null!=beginValue)
								beginValue_ = Long.parseLong(beginValue);
							if(!"".equals(endValue)&&null!=endValue)
								endValue_ =Long.parseLong(endValue);
						} else if ("class java.lang.Float".equals(type)) {
							if(!"".equals(beginValue)&&null!=beginValue)
								beginValue_ = Float.parseFloat(beginValue);
							if(!"".equals(endValue)&&null!=endValue)
								endValue_ =Float.parseFloat(endValue);
					    //update-begin-Author:zhoujf  Date:20180316 for：TASK #2557 范围查询double字段错误 ------
						}else if ("class java.lang.Double".equals(type)) {
							if(!"".equals(beginValue)&&null!=beginValue)
								beginValue_ = Double.parseDouble(beginValue);
							if(!"".equals(endValue)&&null!=endValue)
								endValue_ =Double.parseDouble(endValue);
						//update-end-Author:zhoujf  Date:20180316 for：TASK #2557 范围查询double字段错误 ------	
						}else{
							 beginValue_ = beginValue;
							 endValue_ = endValue;
						}
						ObjectParseUtil.addCriteria(cq, aliasName,
								HqlRuleEnum.GE, beginValue_);
						ObjectParseUtil.addCriteria(cq, aliasName,
								HqlRuleEnum.LE, endValue_);
					}
					// ------------update--Author:JueYue Date:2014-8-23
					// for：查询拼装的替换
				} else if ("class java.util.Date".equals(type)) {
					QueryTimeFormat format = origDescriptors[i].getReadMethod().getAnnotation(QueryTimeFormat.class);
					SimpleDateFormat userDefined = null;
					if (format != null) {
						userDefined = new SimpleDateFormat(format.format());
					}
					if (StringUtils.isNotBlank(beginValue)) {
						if (userDefined != null) {
							cq.ge(aliasName, userDefined.parse(beginValue));
						} else if (beginValue.length() == 19) {
							cq.ge(aliasName, getTime().parse(beginValue));
						} else if (beginValue.length() == 10) {
							cq.ge(aliasName,getTime().parse(beginValue + " 00:00:00"));
						}
					}
					if (StringUtils.isNotBlank(endValue)) {
						if (userDefined != null) {
							cq.ge(aliasName, userDefined.parse(beginValue));
						} else if (endValue.length() == 19) {
							cq.le(aliasName, getTime().parse(endValue));
						} else if (endValue.length() == 10) {
							// 对于"yyyy-MM-dd"格式日期，因时间默认为0，故此添加" 23:59:59"并使用time解析，以方便查询日期时间数据
							cq.le(aliasName, getTime().parse(endValue + " 23:59:59"));
						}
					}
					if (isNotEmpty(value)) {
						cq.eq(aliasName, value);
					}
				} else if (!StringUtil.isJavaClass(origDescriptors[i].getPropertyType())) {
					Object param = PropertyUtils.getSimpleProperty(searchObj,name);
					if (isHaveRuleData(ruleMap, aliasName) ||( isNotEmpty(param)&& itIsNotAllEmpty(param))) {
						// 如果是实体类,创建别名,继续创建查询条件
						// ------------update--Author:JueYue Date:20140521
						// for：用户反馈
						cq.createAlias(aliasName,aliasName.replaceAll("\\.", "_"));
						// ------------end--Author:JueYue Date:20140521 for：用户反馈
						installHqlJoinAlias(cq, param, ruleMap, parameterMap,aliasName);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	//update-begin-author:zhoujf date:20181119 for:TASK #2459 【改进】数据权限新增SQL片段支持，但是不支持解析表达式需要改造--
	private static String getSqlRuleValue(String sqlRule){
		try {
			Set<String> varParams = getSqlRuleParams(sqlRule);
			for(String var:varParams){
				String tempValue = ResourceUtil.converRuleValue(var);
				sqlRule = sqlRule.replace("#{"+var+"}",tempValue);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sqlRule;
	}
	
	private static Set<String> getSqlRuleParams(String sql) {
		if(oConvertUtils.isEmpty(sql)){
			return null;
		}
		Set<String> varParams = new HashSet<String>();
		String regex = "\\#\\{\\w+\\}";
		
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(sql);
		while(m.find()){
			String var = m.group();
			varParams.add(var.substring(var.indexOf("{")+1,var.indexOf("}")));
		}
		return varParams;
	}
	//update-end-author:zhoujf date:20181119 for:TASK #2459 【改进】数据权限新增SQL片段支持，但是不支持解析表达式需要改造--

	/**
	 * 判断数据规则是不是包含这个实体类
	 * 
	 * @param ruleMap
	 * @param aliasName
	 * @return
	 */
	private static boolean isHaveRuleData(Map<String, TSDataRule> ruleMap,
			String aliasName) {
		for (String key : ruleMap.keySet()) {
			if (key.contains(aliasName)) {
				return true;
			}
		}
		return false;
	}

	private static void addRuleToCriteria(TSDataRule tsDataRule,
			String aliasName, Class propertyType, CriteriaQuery cq) {
		HqlRuleEnum rule = HqlRuleEnum.getByValue(tsDataRule.getRuleConditions());
		if (rule.equals(HqlRuleEnum.IN)) {
			String[] values = tsDataRule.getRuleValue().split(",");
			Object[] objs = new Object[values.length];
			if (! propertyType.equals(String.class)) {
				for (int i = 0; i < values.length; i++) {
					objs[i] = NumberUtils.parseNumber(values[i], propertyType);
				}
			}else {
				objs = values;
			}
			ObjectParseUtil.addCriteria(cq, aliasName, rule, objs);
		} else {
			if (propertyType.equals(String.class)) {
				ObjectParseUtil.addCriteria(cq, aliasName, rule,converRuleValue(tsDataRule.getRuleValue()));
			} else {
				ObjectParseUtil.addCriteria(cq, aliasName, rule, NumberUtils.parseNumber(tsDataRule.getRuleValue(), propertyType));
			}
		}
	}

	private static String converRuleValue(String ruleValue) {
		//---author:jg_xugj----start-----date:20151226--------for：#814 【数据权限】扩展支持写表达式，通过session取值
		//这个方法建议去掉，直接调用ResourceUtil.converRuleValue(ruleValue)
		String value = ResourceUtil.converRuleValue(ruleValue);
		//---author:jg_xugj----end-----date:20151226--------for：#814 【数据权限】扩展支持写表达式，通过session取值
		return value!= null ? value : ruleValue;
	}

	private static boolean judgedIsUselessField(String name) {
		return "class".equals(name) || "ids".equals(name)
				|| "page".equals(name) || "rows".equals(name)
				|| "sort".equals(name) || "order".equals(name);
	}

	/**
	 * 判断是不是空
	 */
	public static boolean isNotEmpty(Object value) {
		return value != null && !"".equals(value);
	}

	/**
	 * 判断这个类是不是所以属性都为空
	 * 
	 * @param param
	 * @return
	 */
	private static boolean itIsNotAllEmpty(Object param) {
		boolean isNotEmpty = false;
		try {
			PropertyDescriptor origDescriptors[] = PropertyUtils
					.getPropertyDescriptors(param);
			String name;
			for (int i = 0; i < origDescriptors.length; i++) {
				name = origDescriptors[i].getName();
				if ("class".equals(name)
						|| !PropertyUtils.isReadable(param, name)) {
					continue;
				}
				if (Map.class.isAssignableFrom(origDescriptors[i]
						.getPropertyType())) {
					Map<?, ?> map = (Map<?, ?>) PropertyUtils
							.getSimpleProperty(param, name);
					if (map != null && map.size() > 0) {
						isNotEmpty = true;
						break;
					}
				} else if (Collection.class.isAssignableFrom(origDescriptors[i]
						.getPropertyType())) {
					Collection<?> c = (Collection<?>) PropertyUtils
							.getSimpleProperty(param, name);
					if (c != null && c.size() > 0) {
						isNotEmpty = true;
						break;
					}
				} else if (StringUtil.isNotEmpty(PropertyUtils
						.getSimpleProperty(param, name))) {
					isNotEmpty = true;
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isNotEmpty;
	}

	private static Map<String, TSDataRule> getRuleMap() {
		Map<String, TSDataRule> ruleMap = new HashMap<String, TSDataRule>();
		List<TSDataRule> list =JeecgDataAutorUtils.loadDataSearchConditonSQL(); //(List<TSDataRule>) ContextHolderUtils
			//	.getRequest().getAttribute(Globals.MENU_DATA_AUTHOR_RULES);
		if(list != null&&list.size()>0){
			if(list.get(0)==null){
				return ruleMap;
			}
			for (TSDataRule rule : list) {
				//update-begin-author:taoYan date:20170811 for:自定义sql表达式没有ruleColumn字段的值---默认赋值
				String column = rule.getRuleColumn();
				if(oConvertUtils.isEmpty(column)){
					column = SQL_RULES_COLUMN+rule.getId();
				}
				ruleMap.put(column, rule);
				//update-end-author:taoYan date:20170811 for:自定义sql表达式没有ruleColumn字段的值---默认赋值
			}
		}
		return ruleMap;
	}

//	--author：龙金波 ------start---date：20150628--------for：sql高级查询器参数的sql组装
	/**
	 * @author ljb
	 * 根据对象拼装sql 
	 * TODO 结合DataRule
	 * @param list
	 * @param tab格式化
	 * @return
	 */
	public static String getSql(List<QueryCondition> list,String tab,Class claszz){
		//update-begin-author:jiaqiankun date:20180704 for:TASK #2881 【bug】高级查询JS错误普遍存在的问题
		if(list==null || list.size()==0){
			return "";
		}
		//update-end-author:jiaqiankun date:20180704 for:TASK #2881 【bug】高级查询JS错误普遍存在的问题
		StringBuffer sb=new StringBuffer();
		//update-begin-Author:LiShaoQing Date:20170915 for:[TASK #2340]动态组查询
		if(list.get(0).getRelation().equals("or")) {
			sb.append(" 1=0 ");
		} else {
			sb.append(" 1=1 ");
		}
		//update-end-Author:LiShaoQing Date:20170915 for:[TASK #2340]动态组查询
		for(QueryCondition c :list){
			String column = invokeFindColumn(claszz,c.getField());
			String type = invokeFindType(claszz,c.getField());
			c.setType(type);
			c.setField(column);
			sb.append(tab+c);sb.append("\r\n");
			if(c.getChildren()!=null){
				List list1= JSONHelper.toList(c.getChildren(), QueryCondition.class);
				sb.append(tab);
				sb.append(c.getRelation()+"( ");
				sb.append(getSql(list1,tab+"\t",claszz));
				sb.append(tab+")\r\n");
			}
		}
		return sb.toString();
	}

//	--author：龙金波 ------end---date：20150628--------for：sql组装
//	--author：陈璞 ------begin---date：20150612--------for：sql组装
	/**
	 * 根据字段名称,获取字段的类型字符串
	 * return: java.lang.Integer
	 */
	public static String invokeFindType(Class clazz,String field_name){
		String type=null;
		Field field;
		try {
			field = clazz.getDeclaredField(field_name);
			if(field!=null){
				type=field.getType().getSimpleName();
			}
		} catch (Exception e) {
			return type;
		}
		return type;
	}
	/**
	 * 根据字段名称返回hibernate映射数据库字段名
	 * @param clazz
	 * @param field_name	字段名称
	 * @return
	 */
	public static String invokeFindColumn(Class clazz,String field_name){
		String column=null;
		Field field;
		try {
			//update-begin-Author:LiShaoQing date:20171027 for:判断是否存在字段，没有向父类查找--------
			//TODO	只能向上找一级，其他则失败。
			boolean flag = getSuperDeclaredField(clazz,field_name);
			if(flag) {
				field = clazz.getDeclaredField(field_name);
			} else {
				Class cla = clazz.getSuperclass();
				field = cla.getDeclaredField(field_name);
			}
			//update-end-Author:LiShaoQing date:20171027 for:判断是否存在字段，没有向父类查找--------
			PropertyDescriptor pd = new PropertyDescriptor(field.getName(),clazz);  
	        Method getMethod = pd.getReadMethod();//获得get方法 
			Column col=getMethod.getAnnotation(Column.class);
			if(col!=null){
				column=col.name();
			}
		} catch (Exception e) {
			return column;
		}
		return column;
	}
//	--author：陈璞 ------end---date：20150612--------for：sql组装
	
	
	
	/**
	 * 获取装载数据权限条件的HQL
	 * @return cq
	 * @param cq
	 * @param searchObj
	 */
	public static CriteriaQuery getDataAuthorConditionHql(CriteriaQuery cq, Object searchObj) {
		Map<String, TSDataRule> ruleMap = getRuleMap();
		PropertyDescriptor origDescriptors[] = PropertyUtils.getPropertyDescriptors(searchObj);
		String aliasName, name, type;
		for (int i = 0; i < origDescriptors.length; i++) {
			aliasName = origDescriptors[i].getName();
			name = origDescriptors[i].getName();
			type = origDescriptors[i].getPropertyType().toString();
			try {
				if (judgedIsUselessField(name) || !PropertyUtils.isReadable(searchObj, name)) {
					continue;
				}
				// 如果规则包含这个属性
				if (ruleMap.containsKey(aliasName)) {
					addRuleToCriteria(ruleMap.get(aliasName), aliasName, origDescriptors[i].getPropertyType(), cq);
				}

				Object value = PropertyUtils.getSimpleProperty(searchObj, name);
				// 根据类型分类处理
				if (type.contains("class java.lang") || type.contains("class java.math")) {
					// ------------update--Author:JueYue Date:2014-8-23
					// for：查询拼装的替换
					if (value != null && !value.equals("")) {
						HqlRuleEnum rule = PageValueConvertRuleEnum.convert(value);
						value = PageValueConvertRuleEnum.replaceValue(rule, value);
						ObjectParseUtil.addCriteria(cq, aliasName, rule, value);
					}
					// ------------update--Author:JueYue Date:2014-8-23
					// for：查询拼装的替换
				} else if ("class java.util.Date".equals(type)) {
					QueryTimeFormat format = origDescriptors[i].getReadMethod().getAnnotation(QueryTimeFormat.class);
					SimpleDateFormat userDefined = null;
					if (format != null) {
						userDefined = new SimpleDateFormat(format.format());
					}
					if (isNotEmpty(value)) {
						cq.eq(aliasName, value);
					}
				} else if (!StringUtil.isJavaClass(origDescriptors[i].getPropertyType())) {
					Object param = PropertyUtils.getSimpleProperty(searchObj, name);
					if (isHaveRuleData(ruleMap, aliasName) || (isNotEmpty(param) && itIsNotAllEmpty(param))) {
						// 如果是实体类,创建别名,继续创建查询条件
						// ------------update--Author:JueYue Date:20140521
						// for：用户反馈
						cq.createAlias(aliasName, aliasName.replaceAll("\\.", "_"));
						// ------------end--Author:JueYue Date:20140521 for：用户反馈
						getDataAuthorConditionHql(cq, param);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return cq;
	}
	
	//update-begin-Author:LiShaoQing date:20171027 for:判断是否存在字段--------
	/**
	 * 判断有没有field字段
	 * @param clazz
	 * @param fieldName
	 * @return
	 */
	public static boolean getSuperDeclaredField(Class clazz,String fieldName) {
		Field[] fields=clazz.getDeclaredFields();
		boolean b=false;
		for (int i = 0; i < fields.length; i++) {
		    if(fields[i].getName().equals(fieldName))
		    {
		        b=true;
		        break;
		    }
		}
		return b;
	}
	//update-end-Author:LiShaoQing date:20171027 for:判断是否存在字段--------
}
