package org.jeecgframework.workflow.task;

import java.util.Date;
import java.util.List;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.NativeTaskQuery;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.workflow.pojo.activiti.ActHiTaskinst;
import org.jeecgframework.workflow.pojo.activiti.ActRuTask;
import org.jeecgframework.workflow.user.entity.TSUserAgentEntity;
import org.jeecgframework.workflow.user.service.TSUserAgentServiceI;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component("userAgentTask")
public class UserAgentTask implements Job{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(UserAgentTask.class);

//	@Autowired
	private TSUserAgentServiceI tSUserAgentService;
//	@Autowired
	private TaskService taskService;
//	@Autowired
	private SystemService systemService;
	
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
        try {
        	logger.info("-------------任务代理开始---------------");
        	tSUserAgentService=ApplicationContextUtil.getContext().getBean(TSUserAgentServiceI.class);
    		taskService=ApplicationContextUtil.getContext().getBean(TaskService.class);
    		systemService=ApplicationContextUtil.getContext().getBean(SystemService.class);
			//查询有代理设置的用户
			String currDateStr = DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
			Date currDate = DateUtils.parseDate(currDateStr, "yyyy-MM-dd HH:mm:ss");
			String hql = "from TSUserAgentEntity t where t.status = '1' and startTime <= ? and endTime >= ?";
			List<TSUserAgentEntity> agentUserList = tSUserAgentService.findHql(hql, currDate,currDate);
			for(TSUserAgentEntity agent:agentUserList){
				logger.info("--------任务处理人："+agent.getUserName()+",代办人："+agent.getAgentUserName());
				taskDeal(agent);
			}
			
			logger.info("-------------任务代理结束---------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    
    private void taskDeal(TSUserAgentEntity agent){
    	//1、查用户的待处理的任务
    	StringBuilder sb = new StringBuilder("");
		sb.append("select  * ").append("from (");
		sb.append("(select distinct RES.* ");
		sb.append(" from ACT_RU_TASK RES inner join ACT_RU_IDENTITYLINK I on I.TASK_ID_ = RES.ID_ ");
		sb.append("WHERE RES.ASSIGNEE_ is null and I.TYPE_ = 'candidate' ");
		sb.append("	and ( I.USER_ID_ = #{userid}  or I.GROUP_ID_ IN ( select g.GROUP_ID_ from ACT_ID_MEMBERSHIP g where g.USER_ID_ = #{userid}  ) ");
		sb.append(" ) ").append(" and RES.SUSPENSION_STATE_ = 1 ");
		sb.append(") union ");
		sb.append("(select distinct RES.* ");
		sb.append(" from ACT_RU_TASK RES ");
		sb.append("WHERE RES.ASSIGNEE_ = #{userid} ");
		sb.append(" )) v ");
//		sb.append(" order by v.CREATE_TIME_ desc, v.PRIORITY_ desc ");
		
		NativeTaskQuery query = taskService.createNativeTaskQuery()
		.sql(sb.toString())
        .parameter("userid", agent.getUserName());
        List<Task> pretasks = query.list(); 
    	//2、任务委派给代理人
        for(Task task:pretasks){
        	logger.info("-------------任务id:"+task.getId()+",taskName:"+task.getName());
        	try{
    			ActRuTask ruTask = this.systemService.get(ActRuTask.class, task.getId());
    			ruTask.setAssignee(agent.getAgentUserName());
    			this.systemService.saveOrUpdate(ruTask);
    			//update-begin--Author:zhoujf  Date:20170309 for：TASK #1199 流程委托、驳回 日志问题
    			String description = "<b>"+ruTask.getAssignee()+"</b> 委托给 <b>"+agent.getAgentUserName()+"</b> 办理";
    			ActHiTaskinst actHiTaskinst = this.systemService.get(ActHiTaskinst.class, ruTask.getId());
    			actHiTaskinst.setAssignee(agent.getAgentUserName());
    			actHiTaskinst.setDescription(description);
    			actHiTaskinst.setDeleteReason(description);
    			//update-end--Author:zhoujf  Date:20170309 for：TASK #1199 流程委托、驳回 日志问题
    			this.systemService.saveOrUpdate(actHiTaskinst);
    			String msg = "用户"+task.getName()+"[任务,id:"+task.getId()+",taskName:"+task.getName()+"]委派给"+agent.getAgentUserName();
    			logger.info(msg);
    		}catch(Exception e){
    			e.printStackTrace();
    		}
        }
    }


}
