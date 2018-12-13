package org.jeecgframework.web.cgform.service.impl.button;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.jeecgframework.web.cgform.entity.button.CgformButtonSqlEntity;
import org.jeecgframework.web.cgform.service.button.CgformButtonSqlServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;

@Service("cgformButtonSqlService")
@Transactional
public class CgformButtonSqlServiceImpl extends CommonServiceImpl implements CgformButtonSqlServiceI {

	/**
	 * 校验按钮sql增强唯一性
	 * @param cgformButtonEntity
	 * @return
	 */
	
	public List<CgformButtonSqlEntity> checkCgformButtonSql(
			CgformButtonSqlEntity cgformButtonSqlEntity) {
		StringBuilder hql = new StringBuilder("");
		hql.append(" from CgformButtonSqlEntity t");
		//update--begin--author:zhoujf Date:20180605 for:TASK #2745 【Sql 注入问题修改】
		hql.append(" where t.formId=?");
		hql.append(" and  t.buttonCode =?");
		List<CgformButtonSqlEntity> list = null;
		if(cgformButtonSqlEntity.getId()!=null){
			hql.append(" and t.id !=?");
			list = this.findHql(hql.toString(),cgformButtonSqlEntity.getFormId(),cgformButtonSqlEntity.getButtonCode(),cgformButtonSqlEntity.getId());
		}else{
			list = this.findHql(hql.toString(),cgformButtonSqlEntity.getFormId(),cgformButtonSqlEntity.getButtonCode());
		}
		//update--end--author:zhoujf Date:20180605 for:TASK #2745 【Sql 注入问题修改】
		return list;
	}
	
	
	public CgformButtonSqlEntity getCgformButtonSqlByCodeFormId(String buttonCode, String formId) {
		StringBuilder hql = new StringBuilder("");
		hql.append(" from CgformButtonSqlEntity t");
		hql.append(" where t.formId='").append(formId).append("'");
		hql.append(" and  t.buttonCode ='").append(buttonCode).append("'");
		List<CgformButtonSqlEntity> list = this.findHql(hql.toString());
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
}