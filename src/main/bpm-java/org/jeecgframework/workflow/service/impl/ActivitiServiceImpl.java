package org.jeecgframework.workflow.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.activiti.engine.impl.ProcessEngineImpl;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.db.DbSqlSession;
import org.activiti.engine.impl.db.DbSqlSessionFactory;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.delegate.ActivityBehavior;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.NativeTaskQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.hibernate.Query;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.constant.DataBaseConstant;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.DBTypeUtil;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.MyClassLoader;
import org.jeecgframework.core.util.ReflectHelper;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StreamUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.minidao.util.MiniDaoUtil;
import org.jeecgframework.web.autoform.entity.AutoFormDataListEntity;
import org.jeecgframework.web.cgform.service.build.DataBaseService;
import org.jeecgframework.web.system.pojo.base.TSBaseUser;
import org.jeecgframework.web.system.pojo.base.TSOperation;
import org.jeecgframework.web.system.pojo.base.TSRole;
import org.jeecgframework.web.system.pojo.base.TSRoleUser;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.sms.util.TuiSongMsgUtil;
import org.jeecgframework.workflow.api.vo.FlowDto;
import org.jeecgframework.workflow.common.ProcDealStyleEnum;
import org.jeecgframework.workflow.common.WorkFlowGlobals;
import org.jeecgframework.workflow.dao.ActivitiDao;
import org.jeecgframework.workflow.dao.IActivitiCommonDao;
import org.jeecgframework.workflow.model.activiti.ActivitiCom;
import org.jeecgframework.workflow.model.activiti.Process;
import org.jeecgframework.workflow.model.activiti.ProcessHandle;
import org.jeecgframework.workflow.model.activiti.WorkflowUtils;
import org.jeecgframework.workflow.pojo.activiti.ActHiProcinst;
import org.jeecgframework.workflow.pojo.activiti.ActHiTaskinst;
import org.jeecgframework.workflow.pojo.activiti.ActHiVarinst;
import org.jeecgframework.workflow.pojo.base.TPBpmLog;
import org.jeecgframework.workflow.pojo.base.TPForm;
import org.jeecgframework.workflow.pojo.base.TPMutilFlowBizEntity;
import org.jeecgframework.workflow.pojo.base.TPProcess;
import org.jeecgframework.workflow.pojo.base.TPProcessnode;
import org.jeecgframework.workflow.pojo.base.TPProcessnodeDeployment;
import org.jeecgframework.workflow.pojo.base.TPProcessnodeFunction;
import org.jeecgframework.workflow.pojo.base.TPProcesspro;
import org.jeecgframework.workflow.pojo.base.TPTaskNotificationEntity;
import org.jeecgframework.workflow.pojo.base.TSBusConfig;
import org.jeecgframework.workflow.pojo.base.TSPrjstatus;
import org.jeecgframework.workflow.service.ActivitiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service("activitiService")
@Transactional(rollbackFor=Exception.class)
public class ActivitiServiceImpl extends CommonServiceImpl implements ActivitiService {
	private static Logger logger = LoggerFactory.getLogger(ActivitiServiceImpl.class);

	private IdentityService identityService;
	private RuntimeService runtimeService;
	protected TaskService taskService;
	protected HistoryService historyService;
	protected RepositoryService repositoryService;
	protected String hql;
	@Autowired
	private TaskJeecgService taskJeecgService;
	private SystemService systemService;
	private IActivitiCommonDao activitiCommonDao;
	@Autowired
	private ActivitiDao activitiDao;
	@Autowired
	private DataBaseService dataBaseService;
	@Autowired
	private ProcessEngine processEngine;
	@Autowired
	public void setIdentityService(IdentityService identityService) {
		this.identityService = identityService;
	}

	@Autowired
	public void setRuntimeService(RuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}

	@Autowired
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	@Autowired
	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}

	@Autowired
	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	@Resource
	public void setActivitiCommonDao(IActivitiCommonDao activitiCommonDao) {
		this.activitiCommonDao = activitiCommonDao;
	}

	public SystemService getSystemService() {
		return systemService;
	}
	@Autowired
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	/**
	 * synToActiviti 同步用户到Activiti数据库
	 */
	public void save(TSUser user, String roleidstr, Short synToActiviti) {
		if (WorkFlowGlobals.Activiti_Deploy_YES.equals(synToActiviti)) {
			String userId = user.getUserName();
			UserQuery userQuery = identityService.createUserQuery();
			List<User> activitiUsers = userQuery.userId(userId).list();
			if (activitiUsers.size() == 1) {
				updateActivitiData(user, roleidstr, activitiUsers.get(0));
			} else if (activitiUsers.size() > 1) {
				String errorMsg = "发现重复用户：id=" + userId;
				logger.error(errorMsg);
				throw new RuntimeException(errorMsg);
			} else {
				newActivitiUser(user, roleidstr);
			}
		}

	}

	private void newActivitiUser(TSUser user, String roleidstr) {
		String userId = user.getUserName();
		addActivitiGroup(roleidstr);
		// 添加用户
		saveActivitiUser(user);
		// 添加membership
		addMembershipToIdentify(roleidstr, userId);

	}

	private void addActivitiGroup(String roleidstr) {
		GroupQuery groupQuery = identityService.createGroupQuery();
		String[] roleIds = roleidstr.split(",");
		for (String string : roleIds) {
			TSRole role = commonDao.getEntity(TSRole.class, string);
			if (role != null) {
				List<Group> activitiGroups = groupQuery.groupId(role.getRoleCode()).list();
				if (activitiGroups.size() <= 0) {
					saveActivitiGroup(role);
				}
			}

		}
	}

	/**
	 * 同步角色到到Activiti数据库组表
	 * 
	 * @param role
	 */
	private void saveActivitiGroup(TSRole role) {
		Group activitiGroup = identityService.newGroup(role.getRoleCode());
		activitiGroup.setId(role.getRoleCode());
		activitiGroup.setName(role.getRoleName());
		identityService.saveGroup(activitiGroup);
	}

	private void saveActivitiUser(TSUser user) {
		String userId = oConvertUtils.getString(user.getUserName());
		User activitiUser = identityService.newUser(userId);
		cloneAndSaveActivitiUser(user, activitiUser);

	}

	private void cloneAndSaveActivitiUser(TSUser user, User activitiUser) {
		activitiUser.setFirstName(user.getRealName());
		activitiUser.setLastName(user.getRealName());
		activitiUser.setPassword(user.getPassword());
		activitiUser.setEmail(user.getEmail());
		identityService.saveUser(activitiUser);

	}

	private void addMembershipToIdentify(String roleidstr, String userId) {
		String[] roleIds = roleidstr.split(",");
		for (String string : roleIds) {
			TSRole role = commonDao.getEntity(TSRole.class, string);
			logger.debug("add role to activit: {}", role);
			if (role != null) {
				identityService.createMembership(userId, role.getRoleCode());
			}
		}
	}

	private void updateActivitiData(TSUser user, String roleidstr, User activitiUser) {
		String[] roleIds = roleidstr.split(",");
		String userId = user.getUserName();
		if (roleIds.length > 0) {
			addActivitiGroup(roleidstr);
		}
		// 更新用户主体信息
		cloneAndSaveActivitiUser(user, activitiUser);
		// 删除用户的membership
		List<Group> activitiGroups = identityService.createGroupQuery().groupMember(userId).list();
		for (Group group : activitiGroups) {
			logger.debug("delete group from activit: {}", group);
			identityService.deleteMembership(userId, group.getId());
		}
		// 添加membership
		addMembershipToIdentify(roleidstr, userId);

	}

	/**
	 * 同步删除用户和用户组
	 * 
	 * @param userId
	 *            用户id
	 */
	public void delete(TSUser user) {
		if (user == null) {
			logger.debug("删除用户异常");
		}
		// 同步删除Activiti User
		List<TSRoleUser> roleUserList = findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
		if (roleUserList.size() >= 1) {
			for (TSRoleUser tRoleUser : roleUserList) {
				TSRole role = tRoleUser.getTSRole();
				if (role != null) {
					identityService.deleteMembership(user.getUserName(), role.getRoleCode());
				}

			}
		}
		// 同步删除Activiti User
		identityService.deleteUser(user.getUserName());
	}

	/**
	 * 启动流程
	 */
	public ProcessInstance startWorkflow(Object entity, String businessKey, Map<String, Object> variables, TSBusConfig tsBusbase) {
		ReflectHelper reflectHelper = new ReflectHelper(entity);
		TSUser tsUser = (TSUser) reflectHelper.getMethodValue("TSUser");// 获取创建人
		identityService.setAuthenticatedUserId(tsUser.getUserName());// 设置流程发起人
		//标准变量
		variables.put(WorkFlowGlobals.BPM_DATA_ID, businessKey);
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(tsBusbase.getTPProcess().getProcesskey(), businessKey, variables);
		
		//update-begin---author:scott-------date:20160308--------------for:流程实例ID，存在流程变量里面，支持子流程传递-------------
		if(processInstance==null||oConvertUtils.isEmpty(processInstance.getProcessInstanceId())){
			return null;
		}
		//将流程实例ID存在流程变量里面
		runtimeService.setVariable(processInstance.getProcessInstanceId(), WorkFlowGlobals.JG_LOCAL_PROCESS_ID, processInstance.getProcessInstanceId());
		//update-end---author:scott-------date:20160308--------------for:流程实例ID，存在流程变量里面，支持子流程传递-------------
		return processInstance;
	}
	
	private String getBpmBizTitle(Map<String, Object> variables,String exp){
		try {
			Set<String> varParams = getVarParams(exp);
			for(String var:varParams){
				Object obj = variables.get(var);
				if(obj==null){
					obj = "";
				}else if(obj instanceof Date){
					obj = DateUtils.formatDate((Date)obj);
				}
				exp = exp.replace("${"+var+"}", obj.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return exp;
	}
	
	private Set<String> getVarParams(String exp) {
		if(oConvertUtils.isEmpty(exp)){
			return null;
		}
		Set<String> varParams = new HashSet<String>();
		String regex = "\\$\\{\\w+\\}";
		
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(exp);
		while(m.find()){
			String var = m.group();
			varParams.add(var.substring(var.indexOf("{")+1,var.indexOf("}")));
		}
		return varParams;
	}
	
	/**
	 * 启动流程
	 * @throws BusinessException 
	 */
	public ProcessInstance startOnlineWorkflow(String create_by, String businessKey, Map<String, Object> variables, TSBusConfig tsBusbase) throws BusinessException {
		identityService.setAuthenticatedUserId(create_by);// 设置流程发起人
		//--update-begin---author:zhoujf-----date:20180707---------------for:TASK #1184 流程表单多流程发起功能
		//t_p_mutil_flow_biz表的数据ID [t_s_basebus id 一个]  暂时用不上备用
		variables.put(WorkFlowGlobals.BPM_FORM_BUSINESSKEY, businessKey);
		variables.put(WorkFlowGlobals.BPM_FORM_TYPE, tsBusbase.getFormType());
		//--update-end---author:zhoujf-----date:20180707---------------for:TASK #1184 流程表单多流程发起功能
		//update-begin--Author:zhoujf  Date:20151208 for：增加业务标题表达式
		variables.put(WorkFlowGlobals.BPM_BIZ_TITLE, getBpmBizTitle(variables,tsBusbase.getBusTitleExp()));
		//update-end--Author:zhoujf  Date:20151208 for：增加业务标题表达式
		//update-begin--Author:zhoujf  Date:20151210 for：增加流程办理风格
		variables.put(WorkFlowGlobals.BPM_PROC_DEAL_STYLE, ProcDealStyleEnum.toEnum(tsBusbase.getProcessDealStyle()).getCode());
		//update-end--Author:zhoujf  Date:20151210 for：增加流程办理风格
		//update-begin--Author:zhoujf  Date:20170912 for：TASK #1093 【流程节点】流程节点历史配置URL记录
		//根据流程id查询流程节点配置
		//update-begin--Author:zhoujunfeng  Date:20171012 for：流程节点表单配置，记录发布版本-------------------
//		List<TPProcessnode> nodeList = this.systemService.findByProperty(TPProcessnode.class, "TPProcess.id", tsBusbase.getTPProcess().getId());
		//获取最新发布的流程定义
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey(tsBusbase.getTPProcess().getProcesskey()).latestVersion().singleResult();
		//update-begin--Author:zhoujf  Date:20180727 for：流程未发布流程提交异常处理
		if(processDefinition==null){
			throw new BusinessException("流程未发布，请先发布流程！");
		}
		//update-end--Author:zhoujf  Date:20180727 for：流程未发布流程提交异常处理
		List<TPProcessnodeDeployment> nodeList = this.systemService.findHql("from TPProcessnodeDeployment where processid = ? and deploymentId = ? ", tsBusbase.getTPProcess().getId(),processDefinition.getDeploymentId());
		if(nodeList!=null&&nodeList.size()>0){
			for(TPProcessnodeDeployment node:nodeList){
				if(StringUtil.isNotEmpty(node.getModelandview())){
					variables.put(node.getProcessnodecode()+":"+WorkFlowGlobals.SUFFIX_BPM_FORM_URL, node.getModelandview());
				}
				if(StringUtil.isNotEmpty(node.getModelandviewMobile())){
					variables.put(node.getProcessnodecode()+":"+WorkFlowGlobals.SUFFIX_BPM_FORM_URL_MOBILE, node.getModelandview());
				}
			}
		}
		//update-end--Author:zhoujunfeng  Date:20171012 for：流程节点表单配置，记录发布版本-------------------
		//update-end--Author:zhoujf  Date:20170912 for：TASK #1093 【流程节点】流程节点历史配置URL记录
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(tsBusbase.getTPProcess().getProcesskey(), businessKey, variables);
		//update-begin---author:scott-------date:20160308--------------for:流程实例ID，存在流程变量里面，支持子流程传递-------------
		if(processInstance==null||oConvertUtils.isEmpty(processInstance.getProcessInstanceId())){
			return null;
		}
		//将流程实例ID存在流程变量里面
		runtimeService.setVariable(processInstance.getProcessInstanceId(), WorkFlowGlobals.JG_LOCAL_PROCESS_ID, processInstance.getProcessInstanceId());
		//update-end---author:scott-------date:20160308--------------for:流程实例ID，存在流程变量里面，支持子流程传递-------------
		return processInstance;
	}
	
	//update-begin--Author:zhoujf  Date:20170330 for：启动流程(第三方表单流程)
	
	/**
	 * 启动流程
	 */
	public ProcessInstance startApiWorkflow(String create_by, String businessKey, Map<String, Object> variables,FlowDto flowDto) {
		identityService.setAuthenticatedUserId(create_by);// 设置流程发起人
		variables.put(WorkFlowGlobals.BPM_BIZ_TITLE, flowDto.getBizTitile());
		variables.put(WorkFlowGlobals.BPM_PROC_DEAL_STYLE, ProcDealStyleEnum.DEFAULT.getCode());
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(flowDto.getProcessKey(), businessKey, variables);
		if(processInstance==null||oConvertUtils.isEmpty(processInstance.getProcessInstanceId())){
			return null;
		}
		//将流程实例ID存在流程变量里面
		runtimeService.setVariable(processInstance.getProcessInstanceId(), WorkFlowGlobals.JG_LOCAL_PROCESS_ID, processInstance.getProcessInstanceId());
		return processInstance;
	}
	
	/**
	 * 启动流程(第三方表单)
	 * @param applyUserId
	 * @param businessKey
	 * @param variables
	 * @param tsBusbase
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void startApiProcess(String applyUserId,String businessKey,Map<String, Object> dataForm,FlowDto flowDto){
		//获取onlinecoding表名
		//1.加载出onlinecoding的表单数据（单表或主表）
		//2.制定表单访问变量content_url,默认等于表单的查看页面
		//3.通过onlinecoding表名,读取流程表单配置,获取流程实例
		String data_id = businessKey;
		this.startApiWorkflow(applyUserId, data_id, dataForm, flowDto);
		//4.修改onlinecoding表单的BPM业务流程状态
		dataForm.put(WorkFlowGlobals.BPM_STATUS, WorkFlowGlobals.BPM_BUS_STATUS_2);
		//----------------------------------------------------------------
		//5.往原来的流程设计里面设置数据
		Map mp = new HashMap();
		mp.put("id", data_id);
		mp.put("userid", flowDto.getApplyUserId());
		mp.put("prjstateid", WorkFlowGlobals.BPM_BUS_STATUS_2);
		mp.put("busconfigid", "");
		activitiDao.insert(mp);
	}
	//update-end--Author:zhoujf  Date:20170330 for：启动流程(第三方表单流程)
	
	//update-begin--Author:zhoujf  Date:20170329 for：启动流程事物处理(自定义表单流程)
	/**
	 * 启动流程(online表单流程)
	 * @param create_by
	 * @param businessKey
	 * @param variables
	 * @param tsBusbase
	 * @throws BusinessException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void startAutoFormProcess(String create_by,String businessKey,Map<String, Object> dataForm,TSBusConfig tsBusbase) throws BusinessException{
		//获取onlinecoding表名
		//1.加载出onlinecoding的表单数据（单表或主表）
		//2.制定表单访问变量content_url,默认等于表单的查看页面
		//3.通过onlinecoding表名,读取流程表单配置,获取流程实例
		String data_id = businessKey;
		this.startOnlineWorkflow(create_by, data_id, dataForm, tsBusbase);
		//4.修改onlinecoding表单的BPM业务流程状态
		dataForm.put(WorkFlowGlobals.BPM_STATUS, WorkFlowGlobals.BPM_BUS_STATUS_2);
//		String configId = dataForm.get(WorkFlowGlobals.BPM_FORM_KEY).toString();
//		try {
			AutoFormDataListEntity entity = this.get(AutoFormDataListEntity.class,data_id);
			entity.setBpmStatus(WorkFlowGlobals.BPM_BUS_STATUS_2);
//			dataBaseService.updateTable(configId, data_id, dataForm);
			this.updateEntitie(entity);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		//----------------------------------------------------------------
		//5.往原来的流程设计里面设置数据
		Map mp = new HashMap();
		mp.put("id", data_id);
		mp.put("userid", create_by);
		mp.put("prjstateid", WorkFlowGlobals.BPM_BUS_STATUS_2);
		mp.put("busconfigid", tsBusbase.getId());
		activitiDao.insert(mp);
	}
	//update-end--Author:zhoujf  Date:20170329 for：启动流程事物处理(自定义表单流程)
	
	//update-begin--Author:zhoujf  Date:20150804 for：启动流程事物处理(online表单流程)
	/**
	 * 启动流程(online表单流程)
	 * @param create_by
	 * @param businessKey
	 * @param variables
	 * @param tsBusbase
	 * @throws BusinessException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void startOnlineProcess(String create_by,String businessKey,Map<String, Object> dataForm,TSBusConfig tsBusbase) throws BusinessException{
		//获取onlinecoding表名
		//1.加载出onlinecoding的表单数据（单表或主表）
		//2.制定表单访问变量content_url,默认等于表单的查看页面
		//3.通过onlinecoding表名,读取流程表单配置,获取流程实例
		String data_id = businessKey;
		this.startOnlineWorkflow(create_by, data_id, dataForm, tsBusbase);
		//4.修改onlinecoding表单的BPM业务流程状态
		dataForm.put(WorkFlowGlobals.BPM_STATUS, WorkFlowGlobals.BPM_BUS_STATUS_2);
		String configId = dataForm.get(WorkFlowGlobals.BPM_FORM_KEY).toString();
		try {
			//update-begin--Author:zhoujf  Date:20170731 for：只更新表单流程状态
			Map<String, Object> statusMap = new HashMap<String, Object>();
			statusMap.put(WorkFlowGlobals.BPM_STATUS, WorkFlowGlobals.BPM_BUS_STATUS_2);
			dataBaseService.updateTable(configId, data_id, statusMap);
			//update-end--Author:zhoujf  Date:20170731 for：只更新表单流程状态
		} catch (Exception e) {
			e.printStackTrace();
		}
		//----------------------------------------------------------------
		//5.往原来的流程设计里面设置数据
		Map mp = new HashMap();
		mp.put("id", data_id);
		mp.put("userid", dataForm.get(DataBaseConstant.CREATE_BY_DB));
		mp.put("prjstateid", WorkFlowGlobals.BPM_BUS_STATUS_2);
		mp.put("busconfigid", tsBusbase.getId());
		activitiDao.insert(mp);
	}
	//update-end--Author:zhoujf  Date:20150804 for：启动流程事物处理(online表单流程)
	
	//update-begin--Author:zhoujf  Date:20150804 for：启动流程事物处理(自定义开发表单流程)
	/**
	 * 启动流程(自定义开发表单流程)
	 * @param create_by
	 * @param businessKey
	 * @param variables
	 * @param tsBusbase
	 * @throws BusinessException 
	 */
	public void startUserDefinedProcess(String create_by,String businessKey,Map<String, Object> dataForm,TSBusConfig tsBusbase) throws BusinessException{
		//获取onlinecoding表名
		//1.加载出onlinecoding的表单数据（单表或主表）
		//2.制定表单访问变量content_url,默认等于表单的查看页面
		//3.通过onlinecoding表名,读取流程表单配置,获取流程实例
		String data_id = businessKey;
		this.startOnlineWorkflow(create_by, data_id, dataForm, tsBusbase);
		//4.修改onlinecoding表单的BPM业务流程状态
		String tableName = dataForm.get(WorkFlowGlobals.BPM_FORM_KEY).toString();
		String update_status_sql = "update "+tableName+" set bpm_status = " + WorkFlowGlobals.BPM_BUS_STATUS_2 +" where id="+"'"+data_id+"'";
		systemService.executeSql(update_status_sql);
		//----------------------------------------------------------------
		//5.往原来的流程设计里面设置数据
		Map<String,Object> mp = new HashMap<String,Object>();
		//业务数据ID
		mp.put("id", data_id);
		//创建人
		mp.put("userid", dataForm.get(DataBaseConstant.CREATE_BY_DB));
		//办理中
		mp.put("prjstateid", WorkFlowGlobals.BPM_BUS_STATUS_2);
		//业务配置ID
		mp.put("busconfigid", tsBusbase.getId());
		activitiDao.insert(mp);
	}
	//update-end--Author:zhoujf  Date:20150804 for：启动流程事物处理(自定义开发表单流程)
	
	//--update-begin---author:zhoujf-----date:20180707---------------for:TASK #1184 流程表单多流程发起功能
	/**
	 * 启动流程(多流程表单流程   | 一个表单启动多个流程)
	 * @param create_by
	 * @param businessKey
	 * @param variables
	 * @param tsBusbase
	 * @throws BusinessException 
	 */
	public void startMutilProcess(String create_by,String dataId,Map<String, Object> dataForm,TSBusConfig tsBusbase) throws BusinessException{
		//获取onlinecoding表名
		//1.加载出onlinecoding的表单数据（单表或主表）
		//2.制定表单访问变量content_url,默认等于表单的查看页面
		//3.通过onlinecoding表名,读取流程表单配置,获取流程实例
		//报错多流程业务数据，业务表id作为流程业务key
		TPMutilFlowBizEntity mutilFlowBiz = new TPMutilFlowBizEntity();
		mutilFlowBiz.setMutilFlowCode(tsBusbase.getMutilFlowCode());
		mutilFlowBiz.setMutilFlowForm(tsBusbase.getMutilFlowForm());
		mutilFlowBiz.setMutilFlowStatusCol(tsBusbase.getMutilFlowStatusCol());
		mutilFlowBiz.setMutilFlowFormId(dataId);
		mutilFlowBiz.setTSBusConfig(tsBusbase);
		this.systemService.save(mutilFlowBiz);
		String businessKey = mutilFlowBiz.getId();
		ProcessInstance processInstance = this.startOnlineWorkflow(create_by, businessKey, dataForm, tsBusbase);
		mutilFlowBiz.setProcInstId(processInstance.getProcessInstanceId());
		this.systemService.saveOrUpdate(mutilFlowBiz);
		//4.修改onlinecoding表单的BPM业务流程状态
		String tableName = dataForm.get(WorkFlowGlobals.BPM_FORM_KEY).toString();
		activitiDao.updateFormDataStatus(dataId, tableName,mutilFlowBiz.getMutilFlowStatusCol(),WorkFlowGlobals.BPM_BUS_STATUS_2);
	}
	//--update-end---author:zhoujf-----date:20180707---------------for:TASK #1184 流程表单多流程发起功能
	
	//update-begin--Author:zhoujf  Date:20170306 for：启动流程通用方法(自定义开发表单流程)
	/**
	 * 启动流程通用方法(自定义开发表单流程)
	 * @param tableName 数据库表名
	 * @param id 保存到数据中的ID 
	 * @param formUrl PC端默认表单地址
	 * @param formUrlMobile 移动端默认表单地址
	 * @throws BusinessException 
	 */
	public void startCommonUserDefinedProcess(String tableName,String id,String formUrl,String formUrlMobile) throws BusinessException{
		//获取业务数据，并且加载到流程变量中
		Map<String,Object> dataForm = dataBaseService.findOneForJdbc(tableName, id);
		dataForm.put(WorkFlowGlobals.BPM_DATA_ID, id);
		dataForm.put(WorkFlowGlobals.BPM_FORM_CONTENT_URL, formUrl+"&id="+id);
		if(oConvertUtils.isNotEmpty(formUrlMobile)){
			dataForm.put(WorkFlowGlobals.BPM_FORM_CONTENT_URL_MOBILE, formUrlMobile+"&id="+id);
		}else{
			//没有传移动端表单，默认用PC端
			dataForm.put(WorkFlowGlobals.BPM_FORM_CONTENT_URL_MOBILE, formUrl+"&id="+id);
		}
		
		dataForm.put(WorkFlowGlobals.BPM_FORM_KEY, tableName);
		String create_by = dataForm.get(DataBaseConstant.CREATE_BY_DB).toString();//业务数据创建人
		String data_id = id;//online数据ID
		TSBusConfig tsBusbase =  systemService.findUniqueByProperty(TSBusConfig.class, "onlineId",tableName);
		//获取onlinecoding表名
		//1.加载出onlinecoding的表单数据（单表或主表）
		//2.制定表单访问变量content_url,默认等于表单的查看页面
		//3.通过onlinecoding表名,读取流程表单配置,获取流程实例
		this.startOnlineWorkflow(create_by, data_id, dataForm, tsBusbase);
		//4.修改onlinecoding表单的BPM业务流程状态
		String update_status_sql = "update "+tableName+" set bpm_status = " + WorkFlowGlobals.BPM_BUS_STATUS_2 +" where id="+"'"+data_id+"'";
		systemService.executeSql(update_status_sql);
		//----------------------------------------------------------------
		//5.往原来的流程设计里面设置数据
		Map<String,Object> mp = new HashMap<String,Object>();
		//业务数据ID
		mp.put("id", data_id);
		//创建人
		mp.put("userid", dataForm.get(DataBaseConstant.CREATE_BY_DB));
		//办理中
		mp.put("prjstateid", WorkFlowGlobals.BPM_BUS_STATUS_2);
		//业务配置ID
		mp.put("busconfigid", tsBusbase.getId());
		activitiDao.insert(mp);
	}
	//update-end--Author:zhoujf  Date:20170306 for：启动流程通用方法(自定义开发表单流程)

	/**
	 * 查询待办任务
	 * 
	 * @param userId
	 *            用户ID
	 * @return
	 */ 
	@SuppressWarnings("unchecked")
	public List findTodoTasks(String userId, List<TSBusConfig> tsBusConfigs) {
		List results = new ArrayList();
		List<Task> tasks = new ArrayList<Task>();
		Map classMap =  new HashMap<String,Object>();
		String busentity = "";
		List<Task> todoList;
		List<Task> unsignedTasks;
		if (tsBusConfigs.size() > 0) {
			for (TSBusConfig busConfig : tsBusConfigs) {
				try{
					String processKey = busConfig.getTPProcess().getProcesskey();
					busentity = busConfig.getTSTable().getEntityName();
					// 根据当前人的ID查询
					todoList = taskService.createTaskQuery().processDefinitionKey(processKey).taskAssignee(userId).orderByTaskPriority().desc().orderByTaskCreateTime().desc().list();
					// 根据当前人未签收的任务
					unsignedTasks = taskService.createTaskQuery().processDefinitionKey(processKey).taskCandidateUser(userId).orderByTaskPriority().desc().orderByTaskCreateTime().desc().list();
					// 合并
					//先建立临时集合
					List<Task> tempList = new ArrayList<Task>(0);
					tempList.addAll(todoList);
					tempList.addAll(unsignedTasks);
					//将实体对应到任务列表
					for(Task task:tempList){
						classMap.put(task.getId(), busentity);
					}
					tasks.addAll(tempList);
				}catch (Exception e) {
					//异常情况继续下一次循环
					logger.info(e.getMessage());
					e.printStackTrace();
				}
			}
			// 根据流程的业务ID查询实体并关联
			for (Task task : tasks) {
				String processInstanceId = task.getProcessInstanceId();
				ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
				// String businessKey = processInstance.getBusinessKey();
				String businessKey = getBusinessKeyByTask(task);
				Class entityClass = MyClassLoader.getClassByScn((String)classMap.get(task.getId()));// 业务类
				Object entityObj = getEntity(entityClass, businessKey);
				if (entityObj != null) {
					ReflectHelper reflectHelper = new ReflectHelper(entityObj);
					Process process = (Process) reflectHelper.getMethodValue("Process");
					process.setTask(task);
					process.setProcessInstance(processInstance);
					process.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
					// reflectHelper.setMethodValue("Process", process);
					results.add(entityObj);
				} else {
					return tasks;
				}
			}
		}
		return results;
	}

	/**
	 * 查询流程定义对象
	 * 
	 * @param processDefinitionId
	 *            流程定义ID
	 * @return
	 */
	public ProcessDefinition getProcessDefinition(String processDefinitionId) {
		return repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
	}

	/**
	 * 获取流程图跟踪信息
	 * 
	 * @param request
	 * @return
	 */
	public List<Map<String, Object>> traceProcess(String processInstanceId) throws Exception {
		Execution execution = runtimeService.createExecutionQuery().executionId(processInstanceId).singleResult();// 执行实例

		Object property = PropertyUtils.getProperty(execution, "activityId");
		String activityId = "";
		if (property != null) {
			activityId = property.toString();
		}
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(processInstance.getProcessDefinitionId());
		List<ActivityImpl> activitiList = processDefinition.getActivities();// 获得当前任务的所有节点

		List<Map<String, Object>> activityInfos = new ArrayList<Map<String, Object>>();
		for (ActivityImpl activity : activitiList) {

			boolean currentActiviti = false;
			String id = activity.getId();

			// 当前节点
			if (id.equals(activityId)) {
				currentActiviti = true;
			}

			Map<String, Object> activityImageInfo = packageSingleActivitiInfo(activity, processInstance, currentActiviti);

			activityInfos.add(activityImageInfo);
		}

		return activityInfos;

	}

	/**
	 * 封装输出信息，包括：当前节点的X、Y坐标、变量信息、任务类型、任务描述
	 * 
	 * @param activity
	 * @param processInstance
	 * @param currentActiviti
	 * @return
	 */
	private Map<String, Object> packageSingleActivitiInfo(ActivityImpl activity, ProcessInstance processInstance, boolean currentActiviti) throws Exception {
		Map<String, Object> vars = new HashMap<String, Object>();
		Map<String, Object> activityInfo = new HashMap<String, Object>();
		activityInfo.put("currentActiviti", currentActiviti);
		setPosition(activity, activityInfo);
		setWidthAndHeight(activity, activityInfo);

		Map<String, Object> properties = activity.getProperties();
		vars.put("任务类型", WorkflowUtils.parseToZhType(properties.get("type").toString()));

		ActivityBehavior activityBehavior = activity.getActivityBehavior();
		logger.debug("activityBehavior={}", activityBehavior);
		if (activityBehavior instanceof UserTaskActivityBehavior) {

			Task currentTask = null;

			/*
			 * 当前节点的task
			 */
			if (currentActiviti) {
				currentTask = getCurrentTaskInfo(processInstance);
			}

			/*
			 * 当前任务的分配角色
			 */
			UserTaskActivityBehavior userTaskActivityBehavior = (UserTaskActivityBehavior) activityBehavior;
			TaskDefinition taskDefinition = userTaskActivityBehavior.getTaskDefinition();
			Set<Expression> candidateGroupIdExpressions = taskDefinition.getCandidateGroupIdExpressions();
			if (!candidateGroupIdExpressions.isEmpty()) {

				// 任务的处理角色
				setTaskGroup(vars, candidateGroupIdExpressions);

				// 当前处理人
				if (currentTask != null) {
					setCurrentTaskAssignee(vars, currentTask);
				}
			}
		}

		vars.put("节点说明", properties.get("documentation"));

		String description = activity.getProcessDefinition().getDescription();
		vars.put("描述", description);

		logger.debug("trace variables: {}", vars);
		activityInfo.put("vars", vars);
		return activityInfo;
	}

	private void setTaskGroup(Map<String, Object> vars, Set<Expression> candidateGroupIdExpressions) {
		String roles = "";
		for (Expression expression : candidateGroupIdExpressions) {
			String expressionText = expression.getExpressionText();
			if (expressionText.startsWith("$")) {
				expressionText = expressionText.replace("${insuranceType}", "life");
			}
			String roleName = identityService.createGroupQuery().groupId(expressionText).singleResult().getName();
			roles += roleName;
		}
		vars.put("任务所属角色", roles);
	}

	/**
	 * 设置当前处理人信息
	 * 
	 * @param vars
	 * @param currentTask
	 */
	private void setCurrentTaskAssignee(Map<String, Object> vars, Task currentTask) {
		String assignee = currentTask.getAssignee();
		if (assignee != null) {
			User assigneeUser = identityService.createUserQuery().userId(assignee).singleResult();
			String userInfo = assigneeUser.getFirstName() + " " + assigneeUser.getLastName();
			vars.put("当前处理人", userInfo);
		}
	}

	/**
	 * 获取当前节点信息
	 * 
	 * @param processInstance
	 * @return
	 */
	private Task getCurrentTaskInfo(ProcessInstance processInstance) {
		Task currentTask = null;
		try {
			String activitiId = (String) PropertyUtils.getProperty(processInstance, "activityId");
			logger.debug("current activity id: {}", activitiId);

			currentTask = taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskDefinitionKey(activitiId).singleResult();
			logger.debug("current task for processInstance: {}", ToStringBuilder.reflectionToString(currentTask));

		} catch (Exception e) {
			logger.error("can not get property activityId from processInstance: {}", processInstance);
		}
		return currentTask;
	}

	/**
	 * 设置宽度、高度属性
	 * 
	 * @param activity
	 * @param activityInfo
	 */
	private void setWidthAndHeight(ActivityImpl activity, Map<String, Object> activityInfo) {
		activityInfo.put("width", activity.getWidth());
		activityInfo.put("height", activity.getHeight());
	}

	/**
	 * 设置坐标位置
	 * 
	 * @param activity
	 * @param activityInfo
	 */
	private void setPosition(ActivityImpl activity, Map<String, Object> activityInfo) {
		activityInfo.put("x", activity.getX());
		activityInfo.put("y", activity.getY());
	}

	/**
	 * 获取跟踪信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public ActivityImpl getProcessMap(String processInstanceId) {
		Execution execution = runtimeService.createExecutionQuery().executionId(processInstanceId).singleResult();// 执行实例
		if(execution==null){return null;}
		Object property = null;
		try {
			property = PropertyUtils.getProperty(execution, "activityId");
		} catch (IllegalAccessException e) {
			logger.error("反射异常", e);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		String activityId = "";
		if (property != null) {
			activityId = property.toString();// 当前实例的执行到哪个节点
		}
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processInstance.getProcessDefinitionId()).singleResult();
		ProcessDefinitionImpl pdImpl = (ProcessDefinitionImpl) processDefinition;
		String processDefinitionId = pdImpl.getId();// 流程标识
		ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(processInstance.getProcessDefinitionId());
		ActivityImpl actImpl = null;
		List<String> activitiIds = runtimeService.getActiveActivityIds(execution.getId());
		List<String> a = highLight(processInstanceId);
		List<ActivityImpl> activitiList = def.getActivities();// 获得当前任务的所有节点
		// for(String activitiId : activitiIds){
		for (ActivityImpl activityImpl : activitiList) {
			String id = activityImpl.getId();
			if (id.equals(activityId)) {// 获得执行到那个节点
				actImpl = activityImpl;
				break;
			}
		}
		// }
		return actImpl;
	}

	/**
	 * 获取高亮节点
	 */
	public List<String> highLight(String processInstanceId) {
		List<String> highLihth = new ArrayList<String>();
		List<Execution> executions = runtimeService.createExecutionQuery().processInstanceId(processInstanceId).list();
		for (Execution execution : executions) {
			ExecutionEntity entity = (ExecutionEntity) runtimeService.createExecutionQuery().executionId(execution.getId()).singleResult();
			highLihth.add(entity.getActivityId());
		}
		return highLihth;

	}

	/**
	 * 获取业务ID
	 * 
	 * @param taskId
	 * @return
	 */
	public String oldgetBusinessKeyByTask(Task task) {
		String businessKey = "";
		TaskEntity taskEntity = (TaskEntity) taskService.createTaskQuery().taskId(task.getId()).singleResult();
		ExecutionEntity executionEntity = (ExecutionEntity) runtimeService.createExecutionQuery().executionId(taskEntity.getExecutionId()).singleResult();
		if (executionEntity.getSuperExecutionId() == null) {
			businessKey = executionEntity.getBusinessKey();
		} else {
			ExecutionEntity executionEntity2 = (ExecutionEntity) runtimeService.createExecutionQuery().executionId(executionEntity.getSuperExecutionId()).singleResult();
			ExecutionEntity executionEntity3 = (ExecutionEntity) runtimeService.createExecutionQuery().executionId(executionEntity2.getParentId()).singleResult();
			businessKey = executionEntity3.getBusinessKey();
		}
		return businessKey;
	}

	/**
	 * 获取业务ID
	 * 
	 * @param taskId
	 * @return
	 */
	public String getBusinessKeyByTask(Task task) {
		String businessKey = "";
		TaskEntity taskEntity = (TaskEntity) taskService.createTaskQuery().taskId(task.getId()).singleResult();
		HistoricProcessInstance hiproins = historyService.createHistoricProcessInstanceQuery().processInstanceId(taskEntity.getProcessInstanceId()).singleResult();
		if (hiproins != null) {
			if (hiproins.getSuperProcessInstanceId() != null && hiproins.getBusinessKey() == null) {
				hiproins = historyService.createHistoricProcessInstanceQuery().processInstanceId(hiproins.getSuperProcessInstanceId()).singleResult();
				businessKey = hiproins.getBusinessKey();
			} else {
				businessKey = hiproins.getBusinessKey();
			}
		}
		return businessKey;
	}

	/**
	 * 根据业务ID获取HistoricProcessInstance对象
	 * 
	 * @param businessKey
	 * @return
	 */
	public HistoricProcessInstance getHiProcInstByBusKey(String businessKey) {
		HistoricProcessInstance hiproins = null;
		//update-begin--Author:zhoujf  Date:20170801 for：表单流程追回后查看流程图报错问题--------------------
		List<HistoricProcessInstance> hiproinsList = historyService.createHistoricProcessInstanceQuery().processInstanceBusinessKey(businessKey).orderByProcessInstanceStartTime().desc().list();
		if(hiproinsList!=null&&hiproinsList.size()>0){
			hiproins = hiproinsList.get(0);
		}
		//update-end--Author:zhoujf  Date:20170801 for：表单流程追回后查看流程图报错问题--------------------
		return hiproins;
	}

	/**
	 * 根据父业务ID获取HistoricProcessInstance对象
	 * 
	 * @param businessKey
	 * @return
	 */
	public HistoricProcessInstance getHiProcInstByParprocInstId(String parprocInstId) {
		return historyService.createHistoricProcessInstanceQuery().superProcessInstanceId(parprocInstId).singleResult();
	}

	/**
	 * 根据父流程业务ID更新子流程的业务ID
	 * 
	 * @param parBusKey
	 *            父流程业务ID
	 * @param subBusKey
	 *            子流程业务ID
	 * @return
	 */
	public void updateHiProcInstBusKey(String parBusKey, String subBusKey) {
		HistoricProcessInstance parhiproins = getHiProcInstByBusKey(parBusKey);
		HistoricProcessInstance subhiproins = getHiProcInstByParprocInstId(parhiproins.getId());
		if (subhiproins != null) {
			HistoricProcessInstanceEntity historicProcessInstanceEntity = (HistoricProcessInstanceEntity) subhiproins;
			ActHiProcinst actHiProcinst = getEntity(ActHiProcinst.class, historicProcessInstanceEntity.getId());
			actHiProcinst.setBusinessKey(subBusKey);
			updateEntitie(actHiProcinst);
		}

	}

	/**
	 * 完成任务
	 */
	public ActivitiCom complete(String taskId, Map<String, Object> map) {
		ActivitiCom activitiCom = new ActivitiCom();
		String msg = "";
		Boolean complete = false;
		try {
			//根据taskId获取businessKey
			String businessKey = getBusinessKeyByTask(taskId);
			
			//update-begin--Author:zhangdaihao  Date:20140825 for：任意节点跳转模式--------------------
			//taskService.complete(taskId, map);
			try {
				//用户手工指定节点
				String USER_SELECT_TASK_NODE =  oConvertUtils.getString(map.get(WorkFlowGlobals.USER_SELECT_TASK_NODE));
				taskJeecgService.goProcessTaskNode(taskId, USER_SELECT_TASK_NODE,map);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//update-end--Author:zhangdaihao  Date:20140825 for：任意节点跳转模式----------------------
			//根据businessKey判断是否流程结束
			List<HistoricProcessInstance> list = historyService.createHistoricProcessInstanceQuery().finished().processInstanceBusinessKey(businessKey).list();
			if(list!=null&&list.size()==1){
				// 流程结束 根据id修改t_s_basebus的状态为结束(id即为businessKey)
				commonDao.updateBySqlString("update t_s_basebus set prjstateid = '3' where id ='"+businessKey+"'");
			}
			complete = true;
			msg = "办理成功";
		} catch (ActivitiException e) {
			if (e.getMessage().indexOf("no processes deployed with key") != -1) {
				msg = "没有部署子流程!,请在流程配置中部署流程";
				complete = false;
				e.printStackTrace();
			} else {
				msg = "启动流程失败!,内部错误";
				complete = false;
				e.printStackTrace();

			}
		} catch (Exception e) {
			msg = "内部错误";
			complete = false;
			e.printStackTrace();
		}
		activitiCom.setComplete(complete);
		activitiCom.setMsg(msg);
		return activitiCom;
	}

	/**
	 * 获取业务Id
	 */
	public String getBusinessKeyByTask(String taskId) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		return getBusinessKeyByTask(task);
	}

	/**
	 * 根据流程ID和当前流程环节标示获取当前环节对象
	 */
	public TPProcessnode getTPProcessnode(String taskDefinitionKey, String processkey) {
		TPProcessnode processnode = null;
		hql = "from TPProcessnode t where t.TPProcess.processkey='" + processkey + "' and t.processnodecode='" + taskDefinitionKey + "'";
		List<TPProcessnode> processnodeList = commonDao.findByQueryString(hql);
		if (processnodeList.size() > 0) {
			processnode = processnodeList.get(0);
		}
		return processnode;
	}

	/**
	 * 获取全部部署流程
	 */
	@Transactional(readOnly = true)
	public List processDefinitionList() {
		return repositoryService.createProcessDefinitionQuery().list();
	}
	
	//update-begin--Author:zhoujunfeng  Date:20140809 for：流程processkey获取该流程发布的流程-------------------
	/**
	 * 获取流程processkey获取该流程下已经发布的流程
	 */
	@Transactional(readOnly = true)
	public List processDefinitionListByProcesskey(String processkey){
		return repositoryService.createProcessDefinitionQuery().processDefinitionKey(processkey).list();
	}
	//update-end--Author:zhoujunfeng  Date:20140809 for：流程processkey获取该流程发布的流程-------------------
	
	/**
	 * 根据taskId获取task对象
	 * 
	 * @param taskId
	 * @return
	 */
	public Task getTask(String taskId) {
		return taskService.createTaskQuery().taskId(taskId).singleResult();
	}

	/**
	 * 根据taskId封装ProcessHandle对象
	 * 
	 * @param taskId
	 * @return
	 */
	public ProcessHandle getProcessHandle(String taskId) {
		ProcessHandle processHandle = new ProcessHandle();
		Task task = getTask(taskId);// 引擎任务对象
		String businessKey = getBusinessKeyByTask(taskId);// 业务主键
		String processDefinitionKey = getProcessDefinition(task.getProcessDefinitionId()).getKey();
		String taskDefinitionKey = task.getTaskDefinitionKey();// 节点key
		/** 根据流程名称得到流程对象 */
		TPProcess tpProcess = findUniqueByProperty(TPProcess.class, "processkey", processDefinitionKey);
		TPProcessnode tpProcessnode = getTPProcessnode(taskDefinitionKey, processDefinitionKey);
		TPForm tpForm = tpProcessnode.getTPForm(); // 表单对象
		List<TPProcesspro> tpProcesspros = tpProcessnode.getTPProcesspros();// 流程变量
		processHandle.setProcessDefinitionKey(processDefinitionKey);
		processHandle.setTaskDefinitionKey(taskDefinitionKey);
		processHandle.setBusinessKey(businessKey);
		processHandle.setTaskId(taskId);
		processHandle.setTpForm(tpForm);
		processHandle.setTpProcess(tpProcess);
		processHandle.setTpProcessnode(tpProcessnode);
		processHandle.setTpProcesspros(tpProcesspros);
		return processHandle;
	}

	
	/**
	 * 根据taskId,获取根节点Start的信息
	 * 
	 * @param taskId
	 * @return
	 */
	public TPProcessnode getProcessStartNode(String taskId) {
		Task task = getTask(taskId);// 引擎任务对象
		String processDefinitionKey = getProcessDefinition(task.getProcessDefinitionId()).getKey();
		/** 根据流程名称得到流程对象 */
		TPProcessnode tpProcessnode = getTPProcessnode("start", processDefinitionKey);
		return tpProcessnode;
	}
	
	/**
	 * 根据processkey,获取根节点Start的信息
	 * 
	 * @param processkey
	 * @return
	 */
	public TPProcessnode getProcessStartByProcesskey(String processkey){
		/** 根据流程名称得到流程对象 */
		TPProcessnode tpProcessnode = getTPProcessnode("start", processkey);
		return tpProcessnode;
	}
	
	private static SqlSession getSqlSession() {
		ProcessEngineImpl processEngine = null;
		DbSqlSessionFactory dbSqlSessionFactory = (DbSqlSessionFactory) processEngine.getProcessEngineConfiguration().getSessionFactories().get(DbSqlSession.class);
		SqlSessionFactory sqlSessionFactory = dbSqlSessionFactory.getSqlSessionFactory();
		return sqlSessionFactory.openSession();
	}
	
	
	/**
	 * 根据角色编码和状态值获取审批状态
	 * 
	 * @param prjstate
	 * @param rolecode
	 * @return
	 */
	public TSPrjstatus getTBPrjstatusByCode(String prjstate, String rolecode) {
		TSPrjstatus tsPrjstatus = null;
		String[] rolecodes = rolecode.split(",");
		String search = "";
		for (int i = 0; i < rolecodes.length; i++) {
			search += "'" + rolecodes[i] + "'";
			if (i < rolecodes.length - 1) {
				search += ",";
			}

		}
		if (search != "") {
			List<TSPrjstatus> tbPrjstatuList = commonDao.findByQueryString("from TSPrjstatus p where p.code = '" + prjstate + "' and p.executerole in(" + search + ")");
			if (tbPrjstatuList.size() > 0) {
				tsPrjstatus = tbPrjstatuList.get(0);
			}
		}

		return tsPrjstatus;
	}

	/**
	 * 根据流程编码和业务类名获取业务配置类
	 */
	public TSBusConfig getTSBusConfig(Class classname, String processkey) {
		TSBusConfig tsBusConfig = null;
		String hql = "from TSBusConfig where TSTable.entityName='" + classname.getName() + "' and TPProcess.processkey='" + processkey + "'";
		List<TSBusConfig> tsBusConfigList = commonDao.findByQueryString(hql);
		if (tsBusConfigList.size() > 0) {
			tsBusConfig = tsBusConfigList.get(0);
		}
		return tsBusConfig;

	}
	
	
	public <T> void batchDelete(Class<T> entityClass) {
		this.activitiCommonDao.batchDelete(entityClass);
	}
	/**
	 * 查询待办任务-基础代码
	 * @param isPri 是否只查询用户私有的
	 * @param id 标识：username或者rolecode
	 * @param tsBusConfigs
	 * @return
	 */
	private List findBaseTodoTasks(boolean isPri,String id,HttpServletRequest request){
		List results = new ArrayList();
		List<Task> tasks = new ArrayList<Task>();
		List<Task> todoList;
		List<Task> unsignedTasks;
		//分页参数
		Integer page = Integer.parseInt(request.getParameter("page"));
		Integer rows = Integer.parseInt(request.getParameter("rows"));
		Integer start = (page-1)*rows;
		Integer end = page*rows-1;
		try{
			//建立临时集合
			List<Task> tempList = new ArrayList<Task>();
			if(isPri){
				//-update-begin-----------author:zhangdaihao date:20140917 for:待选人模式下任务查询不到---------------------------------------------------
				//TODO 还得修改加分页
//				TaskService taskService = processEngine.getTaskService();
//				List<Task> todoListtemp = taskService.createTaskQuery().taskCandidateUser(id).orderByTaskCreateTime().desc().active().list();//.taskCandidateGroup("hr").active().list();
//				tempList.addAll(todoListtemp);
//				//-update-end-----------author:zhangdaihao date:20140917 for:待选人模式下任务查询不到---------------------------------------------------
//				
//				// 根据当前人的ID查询
//				/*
//				 * 注入查询条件
//				 */
//				TaskQuery tq = taskService.createTaskQuery().taskAssignee(id).orderByTaskCreateTime().desc().orderByTaskPriority().desc();
//				tq = installQueryParam(tq,request);
//				todoList = tq.listPage(start,end);
//				tempList.addAll(todoList);
				//-update-begin-----------author:zhoujf date:20170221 for:TASK #1190 【bug】我的任务列表查询sql优化 增加任务发起人查询条件
				String userName = request.getParameter("userName");
				StringBuilder userNamesb = new StringBuilder("");
				if(StringUtil.isNotEmpty(userName)){
					List<ProcessInstance> processInstanceList = runtimeService.createProcessInstanceQuery().variableValueEquals("applyUserId", userName).list();
					if(processInstanceList!=null&&processInstanceList.size()>0){
						for(int i=0;i<processInstanceList.size();i++){
							if(i==0){
								userNamesb.append("'"+processInstanceList.get(i).getProcessInstanceId()+"'");
							}else{
								userNamesb.append(",'"+processInstanceList.get(i).getProcessInstanceId()+"'");
							}
						}
					}
				}
				String inUserNameStr = userNamesb.toString();
				//-update-end-----------author:zhoujf date:20170221 for:TASK #1190 【bug】我的任务列表查询sql优化 增加任务发起人查询条件
				
				//-update-begin-----------author:zhoujf date:20170217 for:TASK #1190 【bug】我的任务页面查询条件无效
				//查询条件
				String procDefId = request.getParameter("Process.processDefinition.id");
				
				//-update-begin-----------author:zhoujf date:20151207 for:待办任务分页处理---------------------------------------------------
				StringBuilder sb = new StringBuilder("");
				sb.append("select  * ").append("from (");
				sb.append("(select distinct RES.* ");
				sb.append(" from ACT_RU_TASK RES inner join ACT_RU_IDENTITYLINK I on I.TASK_ID_ = RES.ID_ ");
				sb.append("WHERE RES.ASSIGNEE_ is null and I.TYPE_ = 'candidate' ");
				sb.append("	and ( I.USER_ID_ = #{userid}  or I.GROUP_ID_ IN ( select g.GROUP_ID_ from ACT_ID_MEMBERSHIP g where g.USER_ID_ = #{userid}  ) ");
				sb.append(" ) ").append(" and RES.SUSPENSION_STATE_ = 1 ");
				if(StringUtil.isNotEmpty(procDefId)){
					sb.append("  AND RES.PROC_DEF_ID_ LIKE #{procDefId} ");
				}
				//-update-begin-----------author:zhoujf date:20170406 for:流程发起人查询问题
				if(StringUtil.isNotEmpty(userName)){
					if(StringUtil.isNotEmpty(inUserNameStr)){
						sb.append("  AND RES.PROC_INST_ID_ in ("+inUserNameStr+") ");
					}else{
						sb.append("  AND RES.PROC_INST_ID_ in ('-1') ");
					}
				}
				//-update-end-----------author:zhoujf date:20170406 for:流程发起人查询问题
				sb.append(") union ");
				sb.append("(select distinct RES.* ");
				sb.append(" from ACT_RU_TASK RES ");
				sb.append("WHERE RES.ASSIGNEE_ = #{userid} ");
				if(StringUtil.isNotEmpty(procDefId)){
					sb.append("  AND RES.PROC_DEF_ID_ LIKE #{procDefId} ");
				}
				//-update-begin-----------author:zhoujf date:20170406 for:流程发起人查询问题
				if(StringUtil.isNotEmpty(userName)){
					if(StringUtil.isNotEmpty(inUserNameStr)){
						sb.append("  AND RES.PROC_INST_ID_ in ("+inUserNameStr+") ");
					}else{
						sb.append("  AND RES.PROC_INST_ID_ in ('-1') ");
					}
				}
				//-update-end-----------author:zhoujf date:20170406 for:流程发起人查询问题
				sb.append(" )) v ");
				sb.append(" order by v.CREATE_TIME_ desc, v.PRIORITY_ desc ");
//				sb.append("  LIMIT #{start},#{rows} ");
				String dbType = DBTypeUtil.getDBType();
				String sql = MiniDaoUtil.createPageSql(dbType, sb.toString(), page, rows);
				
				NativeTaskQuery query = taskService.createNativeTaskQuery()
				.sql(sql)
		        .parameter("userid", id);
				if(StringUtil.isNotEmpty(procDefId)){
					query.parameter("procDefId", "%"+procDefId+"%");
				}
//		        .parameter("start", start)  
//		        .parameter("rows", rows)
		        List<Task> pretasks = query.list(); 
				tempList.addAll(pretasks);
				//-update-end-----------author:zhoujf date:20151207 for:待办任务分页处理---------------------------------------------------
				//-update-end-----------author:zhoujf date:20170217 for:TASK #1190 【bug】我的任务页面查询条件无效
			}else{
				// 根据当前人所在的组查询
				/*
				 * 注入查询条件
				 */
				TaskQuery tq = taskService.createTaskQuery().taskCandidateGroupIn(Arrays.asList(id.split(","))).orderByTaskCreateTime().desc().orderByTaskPriority().desc();
				tq = installQueryParam(tq,request);
				unsignedTasks = tq.listPage(start,end);
				tempList.addAll(unsignedTasks);
			}
			tasks.addAll(tempList);
		}catch (Exception e) {
			e.printStackTrace();
		}
		// 根据流程的业务ID查询实体并关联
		for (Task task : tasks) {
			String processInstanceId = task.getProcessInstanceId();
			ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
			// String businessKey = processInstance.getBusinessKey();
			String businessKey = getBusinessKeyByTask(task);
			//-update-begin-----------author:zhoujf date:20150730 for:对象持久化问题导致数据问题---------------------------------------------------
			Class entityClass = MyClassLoader.getClassByScn("org.jeecgframework.workflow.pojo.base.TSBaseBusQuery");// 业务基类
			Object entityObj = getEntity(entityClass, businessKey);
			Object obj = null;
			try {
				obj = entityClass.newInstance();
				MyBeanUtils.copyBeanNotNull2Bean(entityObj, obj);
			} catch (Exception e) {
			}
			if (obj != null) {
				ReflectHelper reflectHelper = new ReflectHelper(obj);
//				Process process = (Process) reflectHelper.getMethodValue("Process");
				Process process = new Process();
				process.setTask(task);
				process.setProcessInstance(processInstance);
				process.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
				reflectHelper.setMethodValue("Process", process);
				String userid = (String) reflectHelper.getMethodValue("userid");
				if(userid!=null){
					TSBaseUser baseUser = activitiCommonDao.findUniqueByProperty(TSBaseUser.class, "userName", userid);
					if(baseUser!=null){
						reflectHelper.setMethodValue("userRealName", baseUser.getRealName());
					}
				}
				String assigneeUserId =  task.getAssignee();
				if(assigneeUserId!=null){
					TSBaseUser baseUser = activitiCommonDao.findUniqueByProperty(TSBaseUser.class, "userName", assigneeUserId);
					if(baseUser!=null){
						reflectHelper.setMethodValue("assigneeName", baseUser.getRealName());
					}
				}
				String bpmBizTitle = (String)taskService.getVariable(task.getId(), WorkFlowGlobals.BPM_BIZ_TITLE);
				if(bpmBizTitle!=null){
					reflectHelper.setMethodValue("bpmBizTitle", bpmBizTitle);
				}
				//判断是否超时
				String processnodecode = task.getTaskDefinitionKey();
				String processDefinitionId = task.getProcessDefinitionId();
				String sql = "select node.NODE_TIMEOUT from T_P_PROCESSNODE node where node.PROCESSID = (select tpp.ID from T_P_PROCESS tpp where tpp.PROCESSKEY = ";
				sql += "(select arp.KEY_ from ACT_RE_PROCDEF arp where arp.ID_ = ?)) and node.PROCESSNODECODE = ?";
				List<Map<String, Object>> list = systemService.findForJdbc(sql, processDefinitionId,processnodecode);
				if(list!=null&&list.size()>0){
					Map<String, Object> map = list.get(0);
					Integer nodeTimeout = (Integer)map.get("NODE_TIMEOUT");
					if(checkTimeOut(task.getCreateTime(),nodeTimeout)){
						reflectHelper.setMethodValue("timeoutRemaid", true);
					}else{
						reflectHelper.setMethodValue("timeoutRemaid", false);
					}
				}
				//判断是否催办
				TPTaskNotificationEntity taskNotificationEntity = activitiCommonDao.findUniqueByProperty(TPTaskNotificationEntity.class, "taskId", task.getId());
				if(taskNotificationEntity!=null){
					//-update-begin-----------author:zhoujf date:20180713 for:TASK #2939 【改进】催办任务
					String taskNotificationRemarks = "催办人："+taskNotificationEntity.getCreateName()+"<br/>";
					taskNotificationRemarks += "催办时间："+(taskNotificationEntity.getOpTime()==null?"":DateFormatUtils.format(taskNotificationEntity.getOpTime(), "yyyy-MM-dd HH:mm:ss"))+"<br/>";
					taskNotificationRemarks += "催办说明："+(taskNotificationEntity.getRemarks()==null?"":taskNotificationEntity.getRemarks());
					reflectHelper.setMethodValue("taskNotificationRemarks", taskNotificationRemarks);
					//-update-end-----------author:zhoujf date:20180713 for:TASK #2939 【改进】催办任务
					reflectHelper.setMethodValue("taskNotification", true);
				}else{
					reflectHelper.setMethodValue("taskNotification", false);
				}
				results.add(obj);
				//-update-end-----------author:zhoujf date:20150730 for:对象持久化问题导致数据问题---------------------------------------------------
			} 
//-update-begin-----------author:zhangdaihao date:20150506 for:屏蔽业务关联查询不到的情况，导致我的任务字段不对应---------------------------------------------------
//			else {
//				return tasks;
//			}
//-update-begin-----------author:zhangdaihao date:20150506 for:屏蔽业务关联查询不到的情况，导致我的任务字段不对应---------------------------------------------------
		}
		return results;
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
	
	
	@SuppressWarnings("unchecked")
	public List findPriTodoTasks(String userId,HttpServletRequest request) {
		return findBaseTodoTasks(true,userId,request);
	}
	
	public List findGroupTodoTasks(List<TSRoleUser> roles,HttpServletRequest request) {
		StringBuilder roleIds = new StringBuilder();
		//用户所在的组可能有多个，需要合并
		for(TSRoleUser role:roles){
			roleIds.append(role.getTSRole().getRoleCode()).append(",");
		}
		roleIds.deleteCharAt(roleIds.length()-1);
		List resulttemp = findBaseTodoTasks(false,roleIds.toString(),request);
		return resulttemp;
	}

	
	public List findHistoryTasks(String userName,HttpServletRequest request) {
		//历史任务直接查询activiti的act_hi_taskinst表
		//分页参数
		Integer page = Integer.parseInt(request.getParameter("page"));
		Integer rows = Integer.parseInt(request.getParameter("rows"));
		Integer start = (page-1)*rows;
		Query query = getSession().createQuery(installQueryParamH("from ActHiTaskinst o where o.duration>0 and o.assignee = ? ",request));
		query.setFirstResult(start);
		query.setMaxResults(rows);
		query.setString(0, userName);
		installQueryParamHV(query,request,1);
		List result = query.list();
		for(int i=0;i<result.size();i++){
			ActHiTaskinst hi = (ActHiTaskinst)result.get(i);
			hi.setBpmBizTitle(getHiVariable(hi.getProcInstId(),WorkFlowGlobals.BPM_BIZ_TITLE));
		}
		return result;
	}
	
	//-update-begin-----------author:zhoujf date:20151216 for:增加查询所有的流程历史
	public List findAllHistoryTasks(HttpServletRequest request) {
		//历史任务直接查询activiti的act_hi_taskinst表
		//分页参数
		Integer page = Integer.parseInt(request.getParameter("page"));
		Integer rows = Integer.parseInt(request.getParameter("rows"));
		Integer start = (page-1)*rows;
		Query query = getSession().createQuery(installQueryParamH("from ActHiTaskinst o where o.duration>0 ",request));
		query.setFirstResult(start);
		query.setMaxResults(rows);
		installQueryParamHV(query,request,0);
		List result = query.list();
		for(int i=0;i<result.size();i++){
			ActHiTaskinst hi = (ActHiTaskinst)result.get(i);
			hi.setBpmBizTitle(getHiVariable(hi.getProcInstId(),WorkFlowGlobals.BPM_BIZ_TITLE));
		}
		return result;
	}
	//-update-end-----------author:zhoujf date:20151216 for:增加查询所有的流程历史
	
	//-update-begin-----------author:zhoujf date:20170306 for:TASK #1100 【工作流完善】抄送功能
	@Override
	public List findAllCcHistoryTasks(HttpServletRequest request) {
		//历史任务直接查询activiti的act_hi_taskinst表
		//分页参数
		TSUser user = ResourceUtil.getSessionUser();
		Integer page = Integer.parseInt(request.getParameter("page"));
		Integer rows = Integer.parseInt(request.getParameter("rows"));
		Integer start = (page-1)*rows;
		String hql = "select o from ActHiTaskinst o,TPTaskCcEntity cc where o.taskDefKey =cc.taskDefKey and o.procInstId = cc.procInstId and o.executionId =cc.executionId and o.duration>0 ";
		hql += " and cc.ccUserName = ? ";
		hql = installQueryParamH(hql,request);
		Query query = getSession().createQuery(hql);
		query.setFirstResult(start);
		query.setMaxResults(rows);
		query.setParameter(0, user.getUserName());
		installQueryParamHV(query,request,1);
		List result = query.list();
		for(int i=0;i<result.size();i++){
			ActHiTaskinst hi = (ActHiTaskinst)result.get(i);
			hi.setBpmBizTitle(getHiVariable(hi.getProcInstId(),WorkFlowGlobals.BPM_BIZ_TITLE));
		}
		return result;
	}
	//-update-end-----------author:zhoujf date:20170306 for:TASK #1100 【工作流完善】抄送功能
	
	private String getHiVariable(String taskId, String variableName){
		StringBuilder sb = new StringBuilder("");
		sb.append("from ActHiVarinst VAR where VAR.procInstId =? and VAR.name = ?");
		Query query = getSession().createQuery(sb.toString());
		query.setString(0, taskId);
		query.setString(1, variableName);
		ActHiVarinst obj = (ActHiVarinst)query.uniqueResult();
		return obj==null?"":obj.getText();
	}
	
	
	public Long countPriTodaoTask(String userName, HttpServletRequest request) {
		//查询条件
//		String procDefId = request.getParameter("Process.processDefinition.id");
//		String procName = request.getParameter("Process.processDefinition.name");
		Long size = 0L;
		// 根据当前人的ID查询
//		TaskQuery  tq = taskService.createTaskQuery().taskAssignee(userName).orderByTaskPriority().desc().orderByTaskCreateTime().desc();
//		installQueryParam( tq,request);
//		size = tq.count();
		//-update-begin-----------author:zhoujf date:20170221 for:TASK #1190 【bug】我的任务列表查询sql优化 增加任务发起人查询条件
		String userName2 = request.getParameter("userName");
		StringBuilder userNamesb = new StringBuilder("");
		if(StringUtil.isNotEmpty(userName2)){
			List<ProcessInstance> processInstanceList = runtimeService.createProcessInstanceQuery().variableValueEquals("applyUserId", userName2).list();
			if(processInstanceList!=null&&processInstanceList.size()>0){
				for(int i=0;i<processInstanceList.size();i++){
					if(i==0){
						userNamesb.append("'"+processInstanceList.get(i).getProcessInstanceId()+"'");
					}else{
						userNamesb.append(",'"+processInstanceList.get(i).getProcessInstanceId()+"'");
					}
				}
			}
		}
		String inUserNameStr = userNamesb.toString();
		//-update-end-----------author:zhoujf date:20170221 for:TASK #1190 【bug】我的任务列表查询sql优化 增加任务发起人查询条件
		
		//-update-begin-----------author:zhoujf date:20170217 for:TASK #1190 【bug】我的任务页面查询条件无效
		//查询条件
		String procDefId = request.getParameter("Process.processDefinition.id");
		//-update-begin-----------author:zhoujf date:20151207 for:待办任务分页处理---------------------------------------------------
		StringBuilder sb = new StringBuilder("");
		sb.append("select  count(*) ").append("from (");
		sb.append("(select distinct RES.* ").append("from ACT_RU_TASK RES inner join ACT_RU_IDENTITYLINK I on I.TASK_ID_ = RES.ID_ ");
		sb.append("WHERE RES.ASSIGNEE_ is null and I.TYPE_ = 'candidate' ");
		sb.append("	and ( I.USER_ID_ = #{userid}  or I.GROUP_ID_ IN ( select g.GROUP_ID_ from ACT_ID_MEMBERSHIP g where g.USER_ID_ = #{userid}  ) ");
		sb.append(" ) ").append(" and RES.SUSPENSION_STATE_ = 1 ");
		if(StringUtil.isNotEmpty(procDefId)){
			sb.append("  AND RES.PROC_DEF_ID_ LIKE #{procDefId} ");
		}
		//-update-begin-----------author:zhoujf date:20170406 for:流程发起人查询问题
		if(StringUtil.isNotEmpty(userName2)){
			if(StringUtil.isNotEmpty(inUserNameStr)){
				sb.append("  AND RES.PROC_INST_ID_ in ("+inUserNameStr+") ");
			}else{
				sb.append("  AND RES.PROC_INST_ID_ in ('-1') ");
			}
		}
		//-update-end-----------author:zhoujf date:20170406 for:流程发起人查询问题
		sb.append(") union ");
		sb.append("(select distinct RES.* ").append("from ACT_RU_TASK RES ");
		sb.append("WHERE RES.ASSIGNEE_ = #{userid} ");
		if(StringUtil.isNotEmpty(procDefId)){
			sb.append("  AND RES.PROC_DEF_ID_ LIKE #{procDefId} ");
		}
		//-update-begin-----------author:zhoujf date:20170406 for:流程发起人查询问题
		if(StringUtil.isNotEmpty(userName2)){
			if(StringUtil.isNotEmpty(inUserNameStr)){
				sb.append("  AND RES.PROC_INST_ID_ in ("+inUserNameStr+") ");
			}else{
				sb.append("  AND RES.PROC_INST_ID_ in ('-1') ");
			}
		}
		//-update-end-----------author:zhoujf date:20170406 for:流程发起人查询问题
		sb.append(" )) v ");
		
		NativeTaskQuery query = taskService.createNativeTaskQuery()  
		.sql(sb.toString())
        .parameter("userid", userName);
		if(StringUtil.isNotEmpty(procDefId)){
			query.parameter("procDefId", "%"+procDefId+"%");
		}
		size = query.count(); 
		//-update-end-----------author:zhoujf date:20151207 for:待办任务分页处理---------------------------------------------------
		//-update-end-----------author:zhoujf date:20170217 for:TASK #1190 【bug】我的任务页面查询条件无效
		return size;
	}

	
	public Long countGroupTodoTasks(List<TSRoleUser> roles, HttpServletRequest request) {
		//查询条件
		String procDefId = request.getParameter("Process.processDefinition.id");
		String procName = request.getParameter("Process.processDefinition.name");
		Long size = 0L;
		StringBuilder roleIds = new StringBuilder();
		//用户所在的组可能有多个，需要合并
		for(TSRoleUser role:roles){
			roleIds.append(role.getTSRole().getRoleCode()).append(",");
		}
		roleIds.deleteCharAt(roleIds.length()-1);
		// 根据当前组的ID查询
		TaskQuery  tq =taskService.createTaskQuery().taskCandidateGroupIn(Arrays.asList(roleIds.toString().split(","))).orderByTaskPriority().desc().orderByTaskCreateTime().desc();
		installQueryParam( tq,request);
		size = tq.count();
		return size;
	}

	
	@SuppressWarnings("rawtypes")
	public Long countHistoryTasks(String userName, HttpServletRequest request) {
		Map r = systemService.findOneForJdbc(installCountH("select count(1) as hsize  from act_hi_taskinst o inner join act_re_procdef ap on ap.id_ = o.proc_def_id_ and o.duration_>0 and  o.assignee_ = ?",request),installCountHv(userName,request));
		Long size = Long.parseLong(r.get("hsize").toString());
		return size;
	}
	
	//-update-begin-----------author:zhoujf date:20151216 for:增加查询所有的流程历史
	@SuppressWarnings("rawtypes")
	public Long countAllHistoryTasks(HttpServletRequest request) {
		Map r = systemService.findOneForJdbc(installCountH("select count(1) as hsize  from act_hi_taskinst o inner join act_re_procdef ap on ap.id_ = o.proc_def_id_ and o.duration_>0 ",request),installCountHv(request));
		Long size = Long.parseLong(r.get("hsize").toString());
		return size;
	}
	//-update-end-----------author:zhoujf date:20151216 for:增加查询所有的流程历史
	
	//-update-begin-----------author:zhoujf date:20170306 for:TASK #1100 【工作流完善】抄送功能
	@Override
	public Long countAllCcHistoryTasks(HttpServletRequest request) {
//		Map r = systemService.findOneForJdbc(installCountH("select count(1) as hsize  from act_hi_taskinst o inner join act_re_procdef ap on ap.id_ = o.proc_def_id_ and o.duration_>0 ",request),installCountHv(request));
//		Long size = Long.parseLong(r.get("hsize").toString());
//		return size;
		TSUser user = ResourceUtil.getSessionUser();
		String hql = "select count(*) from ActHiTaskinst o,TPTaskCcEntity cc where o.taskDefKey =cc.taskDefKey and o.procInstId = cc.procInstId and o.executionId =cc.executionId and o.duration>0 ";
		hql += " and cc.ccUserName = ? ";
		//-update-begin-----------author:zhoujf date:20170804 for:sqlserver 不兼容问题
		hql = installQueryParamHC(hql,request);
		//-update-end-----------author:zhoujf date:20170804 for:sqlserver 不兼容问题
		Query query = getSession().createQuery(hql);
		query.setParameter(0, user.getUserName());
		installQueryParamHV(query,request,1);
		List result = query.list();
		if(result!=null){
			return (Long)result.get(0);
		}
		return 0L;
	}
	//-update-end-----------author:zhoujf date:20170306 for:TASK #1100 【工作流完善】抄送功能
	/**
	 * 拼装过滤条件
	 * @param tq
	 * @param request
	 * @return
	 */
	private TaskQuery installQueryParam(
			TaskQuery tq,HttpServletRequest request) {
		//查询条件
		String procDefId = request.getParameter("Process.processDefinition.id");
		String procName = request.getParameter("Process.processDefinition.name");
		if(StringUtil.isNotEmpty(procDefId)){
			tq = tq.processDefinitionId(procDefId);
		}
		if(StringUtil.isNotEmpty(procName)){
			tq = tq.processDefinitionName(procName);
		}
		return tq;
	}
	/**
	 * 拼装过滤条件
	 * @param string
	 * @param request
	 * @return
	 */
	private String installQueryParamH(String sql, HttpServletRequest request) {
		//查询条件
		String procDefId = request.getParameter("procDefId");
		String procName = request.getParameter("prodef.name");
		StringBuilder s = new StringBuilder(sql);
		if(StringUtil.isNotEmpty(procDefId)){
			s.append(" and o.procDefId = ? ");
		}
		if(StringUtil.isNotEmpty(procName)){
			s.append(" and o.prodef.name = ? ");
		}
		s.append(" order by o.startTime desc ");
		return s.toString();
	}
	
	//-update-begin-----------author:zhoujf date:20170804 for:sqlserver 不兼容问题
	/**
	 * 拼装过滤条件
	 * @param string
	 * @param request
	 * @return
	 */
	private String installQueryParamHC(String sql, HttpServletRequest request) {
		//查询条件
		String procDefId = request.getParameter("procDefId");
		String procName = request.getParameter("prodef.name");
		StringBuilder s = new StringBuilder(sql);
		if(StringUtil.isNotEmpty(procDefId)){
			s.append(" and o.procDefId = ? ");
		}
		if(StringUtil.isNotEmpty(procName)){
			s.append(" and o.prodef.name = ? ");
		}
		//s.append(" order by o.startTime desc ");
		return s.toString();
	}
	//-update-end-----------author:zhoujf date:20170804 for:sqlserver 不兼容问题
	
	private void installQueryParamHV(Query query,HttpServletRequest request,int index) {
		//查询条件
		String procDefId = request.getParameter("procDefId");
		String procName = request.getParameter("prodef.name");
		if(StringUtil.isNotEmpty(procDefId)){
			query.setParameter(index, procDefId);
			index++;
		}
		if(StringUtil.isNotEmpty(procName)){
			query.setParameter(index, procName);
		}
	}

	private String installCountH(String sql, HttpServletRequest request) {
		//查询条件
		String procDefId = request.getParameter("procDefId");
		String procName = request.getParameter("prodef.name");
		StringBuilder s = new StringBuilder(sql);
		if(StringUtil.isNotEmpty(procDefId)){
			s.append(" and o.proc_def_id_ = ? ");
		}
		if(StringUtil.isNotEmpty(procName)){
			s.append(" and ap.name_ = ? ");
		}
		return s.toString();
	}

	private Object[] installCountHv(String userName,HttpServletRequest request) {
		//查询条件
		List reList = new ArrayList(0);
		reList.add(userName);
		String procDefId = request.getParameter("procDefId");
		String procName = request.getParameter("prodef.name");
		if(StringUtil.isNotEmpty(procDefId)){
			reList.add(procDefId);
		}
		if(StringUtil.isNotEmpty(procName)){
			reList.add(procName);
		}
		return reList.toArray();
	}
	
	private Object[] installCountHv(HttpServletRequest request) {
		//查询条件
		List reList = new ArrayList(0);
		String procDefId = request.getParameter("procDefId");
		String procName = request.getParameter("prodef.name");
		if(StringUtil.isNotEmpty(procDefId)){
			reList.add(procDefId);
		}
		if(StringUtil.isNotEmpty(procName)){
			reList.add(procName);
		}
		return reList.toArray();
	}
	
	/**
	 * 通过任务节点ID，获取当前节点出发的路径
	 */
	public List<PvmTransition> getOutgoingTransitions(String taskId) {
		List<PvmTransition> outTransitions = null;
		Task task = getTask(taskId);
		ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(task.getProcessDefinitionId());
		List<ActivityImpl> activitiList = def.getActivities(); // rs是指RepositoryService的实例

		String excId = task.getExecutionId();
		ExecutionEntity execution = (ExecutionEntity) runtimeService
				.createExecutionQuery().executionId(excId).singleResult();
		String activitiId = execution.getActivityId();

		for (ActivityImpl activityImpl : activitiList) {
			String id = activityImpl.getId();
			if (activitiId.equals(id)) {
				System.out.println("当前任务：" + activityImpl.getProperty("name")); // 输出某个节点的某种属性
				outTransitions = activityImpl.getOutgoingTransitions();// 获取从某个节点出来的所有线路
				for (PvmTransition tr : outTransitions) {
					System.out.println("线路名："+tr.getProperty("name"));
					PvmActivity ac = tr.getDestination(); // 获取线路的终点节点
					System.out.println("下一步任务任务：" + ac.getProperty("name"));
				}
				break;
			}
		}
		return outTransitions;
	}
	
	
	/**
	 * 查询流程下审批意见
	 */
	public List findBpmLogsByBpmID(String bpm_id) {
		hql = "from TPBpmLog t where t.bpm_id='" + bpm_id + "' order by op_time";
		List<TPBpmLog> logList = commonDao.findByQueryString(hql);
		return logList;
	}
	
	/**
	 * 通过任务节点ID，获取当前节点出发的分支
	 */
	public List getOutTransitions(String taskId) {
		List<PvmTransition> outTransitions = null;
		List<Map> trans = new ArrayList();
		Task task = getTask(taskId);
		ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(task.getProcessDefinitionId());
		List<ActivityImpl> activitiList = def.getActivities(); // rs是指RepositoryService的实例

		String excId = task.getExecutionId();
		ExecutionEntity execution = (ExecutionEntity) runtimeService
				.createExecutionQuery().executionId(excId).singleResult();
		String activitiId = execution.getActivityId();

		for (ActivityImpl activityImpl : activitiList) {
			String id = activityImpl.getId();
			if (activitiId.equals(id)) {
				outTransitions = activityImpl.getOutgoingTransitions();// 获取从某个节点出来的所有线路
				for (PvmTransition tr : outTransitions) {
					if(tr.getId()!=null){
						Map m = new HashMap();
						//获取分支线路的名字，如果名字为空则取线路ID
						String name = (String) (oConvertUtils.isNotEmpty(tr.getProperty("name"))?tr.getProperty("name"):tr.getId());
						m.put("Transition", name);
						PvmActivity ac = tr.getDestination(); // 获取线路的终点节点
						m.put("nextnode",ac.getId());
						trans.add(m);
					}
				}
				break;
			}
		}
		return trans;
	}
	
	/**
	 * 查询流程的历史任务节点
	 */
	public List<Map<String,Object>> getHistTaskNodeList(String proceInsId){
		ActivitiDao activitiDao=ApplicationContextUtil.getContext().getBean(ActivitiDao.class);
		List<Map<String,Object>> list = activitiDao.getHistTaskNodeList(proceInsId);
		return list;
	}
	
	/**
	 * 查询流程的所有节点
	 * @param taskId
	 * @return
	 */
	public List getAllTaskNode(String taskId) {
		List<Map> list = new ArrayList();
		Task task = getTask(taskId);
		ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(task.getProcessDefinitionId());
		List<ActivityImpl> activitiList = def.getActivities(); // rs是指RepositoryService的实例
		for (ActivityImpl activityImpl : activitiList) {
			Map m = new HashMap();
			String id = activityImpl.getId();
			m.put("taskKey", id);
			String name = (String)activityImpl.getProperty("name");
			m.put("name", name);
			list.add(m);
		}
		return list;
	}
	
	/**
	 * 根据流程节点ID 和 菜单Id 获取 具有操作权限的按钮Codes
	 * @param nodeId
	 * @param functionId
	 * @return
	 */
	public Set<String> getNodeOperaCodesByNodeIdAndFunctionId(String nodeId, String functionId) {
		Set<String> operationCodes = new HashSet<String>();
		TPProcessnode proNode = commonDao.get(TPProcessnode.class, nodeId);
		CriteriaQuery cq1 = new CriteriaQuery(TPProcessnodeFunction.class);
		cq1.eq("TPProcessnode.id", proNode.getId());
		cq1.eq("TSFunction.id", functionId);
		cq1.add();
		List<TPProcessnodeFunction> rFunctions = getListByCriteriaQuery(cq1, false);
		if (null != rFunctions && rFunctions.size() > 0) {
			TPProcessnodeFunction tsNodeFunction = rFunctions.get(0);
			if (null != tsNodeFunction.getOperation()) {
				String[] operationArry = tsNodeFunction.getOperation().split(",");
				for (int i = 0; i < operationArry.length; i++) {
					operationCodes.add(operationArry[i]);
				}
			}
		}
		return operationCodes;
	}
	
	//update-begin--Author:zhoujf  Date:20180702 for：节点权限授权
	@Override
	public List<TSOperation> getNodeOperaCodesByNodeId(String nodeId,
			List<TSOperation> operations) {
		List<TSOperation> nodeOperateList = commonDao.findByProperty(TSOperation.class, "processnodeId", nodeId);
		if(nodeOperateList==null){
			return operations;
		}
		//1、并集处理
		for(TSOperation nodeOperate:nodeOperateList){
	        operations.add(nodeOperate);
	    }
		//2、剔除已授权的权限
		for(TSOperation nodeOperate:nodeOperateList){
	        for(int i =0;i<operations.size();i++){
	        	if(nodeOperate.getOperationcode().equals(operations.get(i).getOperationcode())
	        			&&nodeOperate.getStatus()==1){
	        		operations.remove(i);
	        		break;
	        	}
	        }
	    }
		return operations;
	}
	//update-end--Author:zhoujf  Date:20180702 for：节点权限授权
	
	/**
	 * 根据流程节点ID和菜单ID获取 具有操作权限的数据规则
	 */
	public Set<String> getOperationCodesByNodeIdAndruleDataId(String nodeId,String functionId) {
		Set<String> operationCodes = new HashSet<String>();
		TPProcessnode proNode = commonDao.get(TPProcessnode.class, nodeId);
		CriteriaQuery cq1 = new CriteriaQuery(TPProcessnodeFunction.class);
		cq1.eq("TPProcessnode.id", proNode.getId());
		cq1.eq("TSFunction.id", functionId);
		cq1.add();
		List<TPProcessnodeFunction> rFunctions = getListByCriteriaQuery(cq1, false);
		if (null != rFunctions && rFunctions.size() > 0) {
			TPProcessnodeFunction tsNodeFunction = rFunctions.get(0);
			if (null != tsNodeFunction.getDataRule()) {
				String[] operationArry = tsNodeFunction.getDataRule().split(",");
				for (int i = 0; i < operationArry.length; i++) {
					operationCodes.add(operationArry[i]);
				}
			}
		}
		return operationCodes;
	}

	//update-begin--Author:zhoujf  Date:20170619 for：TASK #2126 【删除关联数据没删除】删除流程，流程相关的表数据没清理
	@Override
	public void delprocess(TPProcess process) {
		//删除节点权限
		String delSql = "delete from t_p_processnode_function where processnodeid in (select id from t_p_processnode where processid =?)";
		commonDao.executeSql(delSql, process.getId());
		//根据流程id删除流程监听
		String delHql = "delete from TPProcessListener where TPProcess.id = '"+process.getId()+"'";
		commonDao.executeHql(delHql);
		//根据流程ID删除的流程业务配置
		delHql = "delete from TSBusConfig where TPProcess.id = '"+process.getId()+"'";
		commonDao.executeHql(delHql);
		
		//删除发布的流程节点
		String delSqlt_p_processnode_deployment = "delete from t_p_processnode_deployment where processid = ?";
		commonDao.executeSql(delSqlt_p_processnode_deployment, process.getId());
		
		//删除流程
		commonDao.delete(process);
		
	}
	//update-end--Author:zhoujf  Date:20170619 for：TASK #2126 【删除关联数据没删除】删除流程，流程相关的表数据没清理

	//update-begin--Author:zhoujunfeng  Date:20171012 for：流程节点表单配置，记录发布版本-------------------
	@Override
	public void deployProcess(TPProcess process) throws Exception {
		Deployment deployment = repositoryService.createDeployment().addInputStream(process.getProcesskey() + ".bpmn", StreamUtils.byteTOInputStream(process.getProcessxml())).name(process.getProcesskey()).deploy();
		process.setProcessstate(WorkFlowGlobals.Process_Deploy_YES);
		//根据流程id查询流程节点,保存节点发布版本
		List<TPProcessnode> nodelist = systemService.findHql("from TPProcessnode where TPProcess.id = ?", process.getId());
		if(nodelist!=null&&nodelist.size()>0){
			for(TPProcessnode node:nodelist){
				TPProcessnodeDeployment processnodeDeployment = new TPProcessnodeDeployment();
				processnodeDeployment.setDeploymentId(deployment.getId());
				if(node.getTPForm()!=null){
					processnodeDeployment.setFormid(node.getTPForm().getId());
				}
				processnodeDeployment.setModelandview(node.getModelandview());
				processnodeDeployment.setModelandviewMobile(node.getModelandviewMobile());
				processnodeDeployment.setNodeTimeout(node.getNodeTimeout());
				processnodeDeployment.setProcessid(process.getId());
				processnodeDeployment.setProcessnodecode(node.getProcessnodecode());
				processnodeDeployment.setProcessnodename(node.getProcessnodename());
				//update-begin--Author:zhoujf  Date:20180706 for：TASK #2876 【新功能】流程状态变更，根据节点的状态变更
				processnodeDeployment.setNodeBpmStatus(node.getNodeBpmStatus());
				//update-end--Author:zhoujf  Date:20180706 for：TASK #2876 【新功能】流程状态变更，根据节点的状态变更
				systemService.save(processnodeDeployment);
			}
		}
		systemService.updateEntitie(process);
	}
	//update-end--Author:zhoujunfeng  Date:20171012 for：流程节点表单配置，记录发布版本-------------------

	@Override
	public void taskNotification(String processInstanceId,String remarks) {
		//根据流程实例id查询当前代办任务
		List<Task> taskList = taskService.createTaskQuery().active().processInstanceId(processInstanceId).list();
		TPTaskNotificationEntity taskNotification = null;
		for(Task task:taskList){
			//根据任务id判断是否有催办记录
			taskNotification = this.commonDao.findUniqueByProperty(TPTaskNotificationEntity.class, "taskId", task.getId());
			if(taskNotification!=null){
				taskNotification.setTaskAssignee(task.getAssignee());
				taskNotification.setRemarks(remarks);
				taskNotification.setOpTime(new Date());
				this.commonDao.saveOrUpdate(taskNotification);
			}else{
				taskNotification = new TPTaskNotificationEntity();
				taskNotification.setTaskId(task.getId());
				taskNotification.setTaskName(task.getName());
				taskNotification.setTaskAssignee(task.getAssignee());
				taskNotification.setRemarks(remarks);
				taskNotification.setOpTime(new Date());
				this.commonDao.save(taskNotification);
			}
			//update-begin--Author:zhoujunfeng  Date:20180727 for：催办系统消息发送处理
			//如果已分配处理人则发通知
			if(StringUtils.isNotEmpty(task.getAssignee())){
				String curUser = ResourceUtil.getSessionUser().getRealName();
				Map<String,Object> data = new HashMap<String,Object>();
				data.put("taskName", task.getName());
				String currTaskNameAssignee= taskJeecgService.getUserRealName(task.getAssignee());
				//update-begin--Author:zhoujunfeng  Date:20180802 for：催办系统消息模板修改
				data.put("userName", currTaskNameAssignee);
				//催办人
				data.put("cbUserName", taskNotification.getCreateName());
				//催办时间
				data.put("cbDateTime", DateFormatUtils.format(taskNotification.getOpTime(), "yyyy-MM-dd HH:mm:ss"));
				//催办说明
				data.put("cbDesc", taskNotification.getRemarks());
				//update-end--Author:zhoujunfeng  Date:20180802 for：催办系统消息模板修改
				logger.info("==========发站内信============="+data);
				String r = TuiSongMsgUtil.sendMessage("SYS001", data, curUser, task.getAssignee());
				if("success".equals(r)){
					logger.info("==========发站内信发送成功！=============");
				}
			}
			//update-end--Author:zhoujunfeng  Date:20180727 for：催办系统消息发送处理
		}
	}

}
