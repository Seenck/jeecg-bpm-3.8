package org.jeecgframework.web.system.controller.core;

import java.util.List;

import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.web.system.pojo.base.TSRole;
import org.jeecgframework.web.system.pojo.base.TSRoleUser;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.workflow.service.ActivitiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SyncUserThreadTask implements ThreadTask {
	private static final long serialVersionUID = 5253700279807396631L;
	public final static Logger log = LoggerFactory.getLogger(SyncUserThreadTask.class);
	public static String syncUserFlag = "0";

	public SyncUserThreadTask(){
	}


	public void run() {
		try {
			log.info("-----SyncUserThreadTask-----同步开始------");
			log.info("-----SyncUserThreadTask--syncUserFlag---------"+syncUserFlag);
             //TODO 同步用户
			//1、清空工作流相关数据
			SystemService systemService = ApplicationContextUtil.getContext().getBean(SystemService.class);
			ActivitiService activitiService = ApplicationContextUtil.getContext().getBean(ActivitiService.class);
			systemService.executeSql("DELETE FROM act_id_membership");
			systemService.executeSql("DELETE FROM act_id_user");
			systemService.executeSql("DELETE FROM act_id_group");
			//2、查询需要同步的用户
			String hql = "from TSUser t where  t.status="+Globals.User_Normal+" and t.activitiSync =1 ";
			 List<TSUser> list = systemService.findHql(hql);
			 if(list!=null&&list.size()>0){
				    for(TSUser user:list){
				    	String queryRoleHql = "from TSRoleUser t where t.TSUser.id = ?";
						List<TSRoleUser> userroles = systemService.findHql(queryRoleHql, user.getId());
						String roleids = "";
						for(TSRoleUser userrole:userroles){
							TSRole role = userrole.getTSRole();
							roleids += ","+role.getId();
						}
						activitiService.save(user, roleids.replaceFirst(",", ""), user.getActivitiSync());//同步用户到引擎
				    }
			 }
//			
			SyncUserThreadTask.syncUserFlag = "0";
			log.info("-----SyncUserThreadTask-----同步结束------");
		} catch (Exception e1) {
			e1.printStackTrace();
			log.info("-----SyncUserThreadTask-----同步异常------");
		}
	}
	

}
