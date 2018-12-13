package org.jeecgframework.web.cgform.enhance;

import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.util.LogUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by zzl_h on 2015/11/24.
 */
@Service("cgformJavaInterDemo")
public class CgformJavaInterDemo implements CgformEnhanceJavaInter {
    @Override
  //update-begin--author:zhoujf  date:20170307 for：java 增强扩展tableName字段
    public void execute(String tableName,Map map) throws BusinessException {
    	LogUtil.info("============调用[java增强]成功!========tableName:"+tableName+"===map==="+map);
    }
  //update-end--author:zhoujf  date:20170307 for：java 增强扩展tableName字段
}
