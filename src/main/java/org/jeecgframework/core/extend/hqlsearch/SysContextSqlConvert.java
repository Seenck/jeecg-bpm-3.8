package org.jeecgframework.core.extend.hqlsearch;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jeecgframework.core.extend.hqlsearch.parse.vo.HqlRuleEnum;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.web.system.pojo.base.TSDataRule;

/**
 * 数据库列表序列化转换sql
 * 
 * @ClassName: SqlJsonConvert
 * @Description: TODO
 * @author Comsys-skyCc cmzcheng@gmail.com
 * @date 2014-8-25 下午7:12:41
 * 
 */
public class SysContextSqlConvert {

	enum Signal {
		GREEN, YELLOW, RED
	}

	/**
	 * 
	 * setSqlModel sql行列转换
	 * 
	 * @Title: setSqlModel
	 * @Description: TODO
	 * @param @param dataRule
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String setSqlModel(TSDataRule dataRule){
		if(dataRule == null) 
		return "";
		String sqlValue="";
		HqlRuleEnum ruleEnum=HqlRuleEnum.getByValue(dataRule.getRuleConditions());
		//update-begin-author:taoYan date:20170811 for:数据权限minidao模式下的自定义sql表达式拼接--
		//update-begin-author:zhoujf date:20171225 for:TASK #2459 【改进】数据权限新增SQL片段支持，但是不支持解析表达式需要改造--
		if(ruleEnum == HqlRuleEnum.SQL_RULES){
			sqlValue +=" and ("+ getSqlRuleValue(dataRule.getRuleValue())+")";
			return sqlValue;
		}
		//update-end-author:zhoujf date:20171225 for:TASK #2459 【改进】数据权限新增SQL片段支持，但是不支持解析表达式需要改造--
		//update-end-author:taoYan date:20170811 for:数据权限minidao模式下的自定义sql表达式拼接--
		//-----------------------------------------------------------------------
		//#{sys_user_code}%
		String ValueTemp = dataRule.getRuleValue();
		String moshi = "";
		if(ValueTemp.indexOf("}")!=-1){
			 moshi = ValueTemp.substring(ValueTemp.indexOf("}")+1);
		}
		String returnValue = null;
		//针对特殊标示处理#{sysOrgCode}，判断替换
		if (ValueTemp.contains("#{")) {
			ValueTemp = ValueTemp.substring(2,ValueTemp.indexOf("}"));
		} else {
			ValueTemp = ValueTemp;
		}
		//-----------------------------------------------------------------------
		String tempValue = null;
		//---author:jg_xugj----start-----date:20151226--------for：#814 【数据权限】扩展支持写表达式，通过session取值
		tempValue = ResourceUtil.converRuleValue(ValueTemp);
		//---author:jg_xugj----end-----date:20151226--------for：#814 【数据权限】扩展支持写表达式，通过session取值

		if(tempValue!=null){
			tempValue = tempValue + moshi;
		}else{
			tempValue = ValueTemp;
		}
		switch (ruleEnum) {
		//------------------update-begin-------20150730----author:zhoujf------for:-大于号控制修正------------
		case GT:
			sqlValue+=" and "+dataRule.getRuleColumn()+" >'"+tempValue+"'";
			break;
		//------------------update-end-------20150730----author:zhoujf------for:-大于号控制修正------------
		case GE:
			sqlValue+=" and "+dataRule.getRuleColumn()+" >='"+tempValue+"'";
			break;
		case LT:
			sqlValue+=" and "+dataRule.getRuleColumn()+" <'"+tempValue+"'";
			break;
		case LE:
			sqlValue+=" and "+dataRule.getRuleColumn()+" <= '"+tempValue+"'";
			break;
		case  EQ:
			sqlValue+=" and "+dataRule.getRuleColumn()+" ='"+tempValue+"'";
			break;
		case LIKE:
			sqlValue+=" and "+dataRule.getRuleColumn()+" like '"+tempValue+"'";
			break;
		case NE:
			sqlValue+=" and "+dataRule.getRuleColumn()+" !='"+tempValue+"'";
			break;
		case IN:
			sqlValue+=" and "+dataRule.getRuleColumn()+" IN('"+tempValue+"')";
		default:
			break;
		}
		
		
		return sqlValue;
	}
	
	//update-begin-author:zhoujf date:20171225 for:TASK #2459 【改进】数据权限新增SQL片段支持，但是不支持解析表达式需要改造--
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
	//update-end-author:zhoujf date:20171225 for:TASK #2459 【改进】数据权限新增SQL片段支持，但是不支持解析表达式需要改造--
	
	// /**
	// *
	// * setSqlIn sql为in的方法
	// *
	// * @Title: setSqlIn
	// * @Description: TODO
	// * @param @param dataRule
	// * @param @param sqlValue
	// * @param @return 设定文件
	// * @return String 返回类型
	// * @throws
	// */
	// public static String setSqlIn(List<DSDataRule>T dataRule,String
	// sqlValue){
	// sqlValue+="'"+dataRule.getRuleValue()+"',";
	// return sqlValue;
	// }
}
