package org.jeecgframework.workflow.user.service.impl;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.PasswordUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.workflow.common.WorkFlowGlobals;
import org.jeecgframework.workflow.service.ActivitiService;
import org.jeecgframework.workflow.user.entity.TSUserErpEntity;
import org.jeecgframework.workflow.user.service.TSUserErpServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("tSUserErpService")
@Transactional
public class TSUserErpServiceImpl extends CommonServiceImpl implements TSUserErpServiceI {

	@Autowired
	private ActivitiService activitiService;
	
 	public void delete(TSUserErpEntity entity) throws Exception{
 		super.delete(entity);
 	}
 	
 	public Serializable save(TSUserErpEntity entity) throws Exception{
 		TSUser user = null;
 		if("1".equals(entity.getBindUserFlag())){
 			String userId = entity.getUserId();
 			if(StringUtils.isEmpty(userId)){
 				throw new BusinessException("系统用户账号不能为空");
 			}
 			user = this.findUniqueByProperty(TSUser.class, "userName",userId);
 			if(user==null){
 				throw new BusinessException("系统用户账号不存在");
 			}
 		}else{
 			String userId = entity.getUserId();
 			if(StringUtils.isEmpty(userId)){
 				userId = entity.getSysCode()+"_"+entity.getErpNo();
 			}
 			entity.setUserId(userId);
 			user = this.findUniqueByProperty(TSUser.class, "userName",userId);
			if (user != null) {
				throw new BusinessException("用户: " + user.getUserName() + "已经存在");
			} else {
				user = new TSUser();
				user.setUserName(userId);
				user.setRealName(entity.getUserName());
				user.setMobilePhone(entity.getMobilePhone());
				user.setOfficePhone(entity.getOfficePhone());
				user.setEmail(entity.getEmail());
				String password = "000000";
				user.setPassword(PasswordUtil.encrypt(user.getUserName(), password, PasswordUtil.getStaticSalt()));
	//			if (user.getTSDepart().equals("")) {
	//				user.setTSDepart(null);
	//			}
				user.setDevFlag("0");
				user.setStatus(Globals.User_Normal);
				user.setDeleteFlag(Globals.Delete_Normal);
				user.setActivitiSync(WorkFlowGlobals.Activiti_Deploy_YES);
				this.save(user);
	            // todo zhanggm 保存多个组织机构
	//            saveUserOrgList(req, user);
	//			if (StringUtil.isNotEmpty(roleid)) {
	//				saveRoleUser(user, roleid);
	//			}
			}
 		}
 		//同步到工作流
		//添加时同步
		user.setActivitiSync(WorkFlowGlobals.Activiti_Deploy_YES);
	    activitiService.save(user, "", WorkFlowGlobals.Activiti_Deploy_YES);//同步用户到引擎
 		//保存第三方用户关联表
 		Serializable t = super.save(entity);
 		return t;
 	}
 	
 	public void saveOrUpdate(TSUserErpEntity entity) throws Exception{
 		TSUser user = null;
 		String userId = entity.getUserId();
		if(StringUtils.isEmpty(userId)){
			throw new BusinessException("系统用户账号不能为空");
		}
 		user = this.findUniqueByProperty(TSUser.class, "userName",userId);
		if(user==null){
			throw new BusinessException("系统用户账号不存在");
		}
 		super.saveOrUpdate(entity);
 	}
 	
 	
 	private Map<String,Object> populationMap(TSUserErpEntity t){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", t.getId());
		map.put("user_id", t.getUserId());
		map.put("user_name", t.getUserName());
		map.put("erp_no", t.getErpNo());
		map.put("sys_code", t.getSysCode());
		map.put("create_name", t.getCreateName());
		map.put("create_by", t.getCreateBy());
		map.put("create_date", t.getCreateDate());
		map.put("update_name", t.getUpdateName());
		map.put("update_by", t.getUpdateBy());
		map.put("update_date", t.getUpdateDate());
		map.put("sys_org_code", t.getSysOrgCode());
		map.put("sys_company_code", t.getSysCompanyCode());
		return map;
	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @param t
	 * @return
	 */
 	public String replaceVal(String sql,TSUserErpEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{user_id}",String.valueOf(t.getUserId()));
 		sql  = sql.replace("#{user_name}",String.valueOf(t.getUserName()));
 		sql  = sql.replace("#{erp_no}",String.valueOf(t.getErpNo()));
 		sql  = sql.replace("#{sys_code}",String.valueOf(t.getSysCode()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{sys_org_code}",String.valueOf(t.getSysOrgCode()));
 		sql  = sql.replace("#{sys_company_code}",String.valueOf(t.getSysCompanyCode()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
 	
}