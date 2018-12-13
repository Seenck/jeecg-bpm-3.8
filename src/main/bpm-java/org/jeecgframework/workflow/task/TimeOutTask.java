package org.jeecgframework.workflow.task;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.activiti.engine.TaskService;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.sms.entity.TSSmsEntity;
import org.jeecgframework.workflow.common.WorkFlowGlobals;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

//@Component("timeoutTask")
public class TimeOutTask implements Job{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TimeOutTask.class);

//	@Autowired
	private TaskService taskService;
//	@Autowired
	private SystemService systemService;
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
        try {
        	logger.info("-------------任务查询设置任务任务超时的节点开始---------------");
        	taskService=ApplicationContextUtil.getContext().getBean(TaskService.class);
    		systemService=ApplicationContextUtil.getContext().getBean(SystemService.class);
        	StringBuilder sb = new StringBuilder("");
        	sb.append("SELECT RES.*,t.NODE_TIMEOUT  FROM ACT_RU_TASK RES ");
        	sb.append(" LEFT JOIN T_P_PROCESSNODE t  on t.PROCESSNODECODE =RES.TASK_DEF_KEY_  ");
        	sb.append(" WHERE t.NODE_TIMEOUT > 0 and (RES.ASSIGNEE_ IS NOT NULL or RES.ASSIGNEE_ != '')");
        	int page = 1;
        	int rows = 1000;
			List<Map<String, Object>>  list = null;
			do{
				list = systemService.findForJdbc(sb.toString(), page, rows);
				taskDeal(list);
				page = page+1;
			}while(list!=null&&list.size()>0);
			logger.info("-------------任务查询设置任务任务超时的节点结束---------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    
    private void taskDeal(List<Map<String, Object>>  list){
    	try {
			for(Map<String, Object> map: list){
				Integer nodeTimeout = (Integer)map.get("NODE_TIMEOUT");
				Date startTime  = (Date)map.get("CREATE_TIME_");
				if(checkTimeOut(startTime,nodeTimeout)){
					logger.info("-------------任务超时-----map----------"+map.toString());
					String taskId = (String)map.get("ID_");
					String bpmBizTitle = (String)taskService.getVariable(taskId, WorkFlowGlobals.BPM_BIZ_TITLE);
					//提醒模板
					StringBuilder sb = new StringBuilder("");
					sb.append("您有待处理的超时任务，任务信息如下：").append("\n");
					sb.append("标题：").append(bpmBizTitle).append("\n");
					sb.append("任务节点：").append((String)map.get("NAME_")).append("\n");
					sb.append("流程实例ID：").append((String)map.get("PROC_INST_ID_")).append("\n");
					sb.append("任务处理人：").append((String)map.get("ASSIGNEE_")).append("\n");
					String ctreatetime = startTime==null?"":DateFormatUtils.format(startTime, "yyyy-MM-dd HH:mm:ss");
					sb.append("任务开始时间：").append(ctreatetime);
					logger.info("-------------任务超时-----msg----------"+sb.toString());
					//系统提醒
					TSSmsEntity smsEntity = new TSSmsEntity();
					smsEntity.setEsType("3");//系统提醒
					smsEntity.setEsTitle("任务超时提醒");
					smsEntity.setEsContent(sb.toString());
					smsEntity.setEsSender("system.task");
					smsEntity.setEsReceiver((String)map.get("ASSIGNEE_"));
					smsEntity.setEsSendtime(new Date());
					systemService.save(smsEntity);
				}
				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
	private boolean checkTimeOut(Date startTime,Integer timeout){
		boolean flag = false;
		try {
			if(timeout == null||startTime==null){
				return flag;
			}
			Calendar calSrc = Calendar.getInstance();
			Calendar calDes = Calendar.getInstance();
			calDes.setTime(startTime);
			int diff = DateUtils.dateDiff('h', calSrc, calDes);
			if(diff>=timeout){
				flag = true;
			}
		} catch (Exception e) {
		}
		return flag;
	}

}
