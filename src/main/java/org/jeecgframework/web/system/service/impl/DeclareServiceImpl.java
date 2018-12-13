package org.jeecgframework.web.system.service.impl;

import java.util.List;

import org.jeecgframework.web.system.pojo.base.TSAttachment;
import org.jeecgframework.web.system.service.DeclareService;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("declareService")
@Transactional
public class DeclareServiceImpl extends CommonServiceImpl implements DeclareService {

	public List<TSAttachment> getAttachmentsByCode(String businessKey,String description)
	{
		//update-begin-author:taoyan date:20180528 for:TASK #2745 【Sql 注入问题修改】
		String hql="from TSAttachment t where t.businessKey= ? and t.description = ?";
		return commonDao.findHql(hql,businessKey,description);
		//update-end-author:taoyan date:20180528 for:TASK #2745 【Sql 注入问题修改】
	}
	
}
