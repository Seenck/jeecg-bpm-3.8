package org.jeecgframework.web.cgform.enhance;

import java.util.Map;

import org.jeecgframework.core.common.exception.BusinessException;

/**
 * JAVA增强
 * @author luobaoli
 *
 */
public interface CgformEnhanceJavaInter {
	/**
	 * @param tableName 数据库表名
	 * @param map 表单数据
	 */
	//update-begin--author:zhoujf  date:20170307 for：java 增强扩展tableName字段
	public void execute(String tableName,Map map) throws BusinessException;
	//update-end--author:zhoujf  date:20170307 for：java 增强扩展tableName字段
}
