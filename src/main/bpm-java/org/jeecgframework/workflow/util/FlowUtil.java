package org.jeecgframework.workflow.util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.workflow.dao.ActivitiDao;

/**
 * 
 * 类名：FlowUtil
 * 功能：流程实例运行中辅助类
 * 详细：此类暴露给流程，可在流程定义使用表达式来使用此类的方法，必须由Spring创建才有效
 * 
 * 对外暴露的名称 flowUtil
 * 
 * 作者：jeecg
 * 版本：1.0
 * 日期：2013-8-10 下午4:28:40
 *
 */
public class FlowUtil {
	
	private static final Logger logger = Logger.getLogger(FlowUtil.class);

	public List stringToList(String content){
		//--update--begin------author:zhoujf-----date:20180625-----for:设置固定会签人员，还提示会签选择-----------------
		logger.info("------stringToList------"+content);
		if(StringUtils.isEmpty(content)){
			throw new BusinessException("提交失败，下一任务是会签节点，请选择会签人员");
		}
		//--update--end------author:zhoujf-----date:20180625-----for:设置固定会签人员，还提示会签选择-----------------
		String[] s = content.split(",");
		return Arrays.asList(s);
	}
	
	/**
	 * 获取某个人的上级领导（单个）
	 * @param applyUserId
	 * @return
	 */
	public String getGangWeiName(String applyUserId){
		ActivitiDao activitiDao=ApplicationContextUtil.getContext().getBean(ActivitiDao.class);
		String gangWeiName = activitiDao.getGangWeiName(applyUserId);
		return gangWeiName;
	}
	
	/**
	 * 获取某个人的上级领导（单个）
	 * @param applyUserId
	 * @return
	 */
	public String getDeptHeadId(String applyUserId){
		//通过组织机构，获取该人的上级领导
		List<Map<String,Object>> list = this.getDeptHeadLeaderSql(applyUserId);
		Map<String,Object> map = null;
		String username = "";
		if(list.size()>0){
			map = list.get(0);
			username = map.get("username").toString();
		}
		return username ;
	}
	
	/**
	 *  获取某个人的上级领导（多个领导）
	 * @param applyUserId
	 * @return
	 */
	public String getDeptHeadIds(String applyUserId){
		//通过组织机构，获取该人的上级领导
		List<Map<String,Object>> list = this.getDeptHeadLeaderSql(applyUserId);
		Map<String,Object> map = null;
		StringBuffer sf = new StringBuffer();
		for(int i=0;i<list.size();i++){
			map = list.get(i);
			sf.append(map.get("username"));
			if(i < list.size()-1){
				sf.append(",");
			}
		}
		return sf.toString() ;
	}
	
	/**
	 * 获取某人的上级领导（sql查询）
	 * @param applyUserId
	 * @return
	 */
	private List<Map<String, Object>> getDeptHeadLeaderSql(String username){
		//通过组织机构，获取该人的上级领导
		ActivitiDao activitiDao=ApplicationContextUtil.getContext().getBean(ActivitiDao.class);
		List<Map<String,Object>> list = activitiDao.getDeptHeadLeader(username);
		return list;
	}
}
