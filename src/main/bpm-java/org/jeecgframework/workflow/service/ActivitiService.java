package org.jeecgframework.workflow.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.system.pojo.base.TSOperation;
import org.jeecgframework.web.system.pojo.base.TSRoleUser;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.workflow.api.vo.FlowDto;
import org.jeecgframework.workflow.model.activiti.ActivitiCom;
import org.jeecgframework.workflow.model.activiti.ProcessHandle;
import org.jeecgframework.workflow.pojo.base.TPProcess;
import org.jeecgframework.workflow.pojo.base.TPProcessnode;
import org.jeecgframework.workflow.pojo.base.TSBusConfig;
import org.jeecgframework.workflow.pojo.base.TSPrjstatus;
import org.springframework.transaction.annotation.Transactional;


public interface ActivitiService extends CommonService {
	/**
	 * synToActiviti 同步用户到Activiti数据库
	 */
	public void save(TSUser user, String roleIds, Short synToActiviti);

	/**
	 * synToActiviti 同步用户到Activiti数据库
	 * @param userId
	 */
	public void delete(TSUser user);
	/**
	 * 启动流程
	 */
	public ProcessInstance startWorkflow(Object entity,String businessKey,
			Map<String, Object> variables,TSBusConfig tsBusbase);

	
	
	
	/**
	 * 启动流程
	 */
	public ProcessInstance startOnlineWorkflow(String create_by,String businessKey,
			Map<String, Object> variables,TSBusConfig tsBusbase) throws BusinessException;
	
	//update-begin--Author:zhoujf  Date:20170329 for：启动流程(第三方表单对接)
	/**
	 * 启动流程(第三方表单对接)
	 * @param applyUserId
	 * @param businessKey
	 * @param variables
	 */
	public void startApiProcess(String applyUserId,String businessKey,
			Map<String, Object> variables,FlowDto flowDto);
	//update-end--Author:zhoujf  Date:20170329 for：启动流程(第三方表单对接)
	
	//update-begin--Author:zhoujf  Date:20170329 for：启动流程事物处理(自定义表单流程)
	/**
	 * 启动流程(online表单流程)
	 * @param create_by
	 * @param businessKey
	 * @param variables
	 * @param tsBusbase
	 */
	public void startAutoFormProcess(String create_by,String businessKey,
			Map<String, Object> variables,TSBusConfig tsBusbase) throws BusinessException;
	//update-end--Author:zhoujf  Date:20170329 for：启动流程事物处理(自定义表单流程)
	
	//update-begin--Author:zhoujf  Date:20150804 for：启动流程事物处理(online表单流程)
	/**
	 * 启动流程(online表单流程)
	 * @param create_by
	 * @param businessKey
	 * @param variables
	 * @param tsBusbase
	 */
	public void startOnlineProcess(String create_by,String businessKey,
			Map<String, Object> variables,TSBusConfig tsBusbase) throws BusinessException;
	//update-end--Author:zhoujf  Date:20150804 for：启动流程事物处理(online表单流程)
	
	//update-begin--Author:zhoujf  Date:20150804 for：启动流程事物处理(自定义开发表单流程)
	/**
	 * 启动流程(自定义开发表单流程)
	 * @param create_by
	 * @param businessKey
	 * @param variables
	 * @param tsBusbase
	 */
	public void startUserDefinedProcess(String create_by,String businessKey,
			Map<String, Object> variables,TSBusConfig tsBusbase) throws BusinessException;
	//update-begin--Author:zhoujf  Date:20150804 for：启动流程事物处理(自定义开发表单流程)
	
	//--update-begin---author:zhoujf-----date:20180707---------------for:TASK #1184 流程表单多流程发起功能
	/**
	 * 启动流程(自定义开发表单  | 一个表单启动多个流程)
	 * @param create_by
	 * @param businessKey
	 * @param variables
	 * @param tsBusbase
	 */
	public void startMutilProcess(String create_by,String dataId,Map<String, Object> dataForm,TSBusConfig tsBusbase) throws BusinessException;
	//--update-end---author:zhoujf-----date:20180707---------------for:TASK #1184 流程表单多流程发起功能
	//update-begin--Author:zhoujf  Date:20170306 for：启动流程通用方法(自定义开发表单流程)
	/**
	 * 启动流程(自定义开发表单流程)
	 * @param tableName 数据库表名
	 * @param id 保存到数据中的ID 
	 * @param formUrl PC端默认表单地址
	 * @param formUrlMobile 移动端默认表单地址
	 */
	public void startCommonUserDefinedProcess(String tableName,String id,String formUrl,String formUrlMobile) throws BusinessException;
	//update-begin--Author:zhoujf  Date:20170306 for：启动流程事物处理(自定义开发表单流程)
	
	/**
	 * 查询待办任务	
	 * 
	 * @param userId
	 *            用户ID
	 * @return
	 */
	public List findTodoTasks(String userId,List<TSBusConfig> tsBusConfigs);

	/**
	 * 获取流程图跟踪信息
	 * 
	 * @param request
	 * @return
	 */
	public List<Map<String, Object>> traceProcess(String processInstanceId)
			throws Exception;

	/**
	 * 获取跟踪信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public ActivityImpl getProcessMap(String processInstanceId);
	public List<String> highLight(String processInstanceId);
	/**
	 * 获取业务ID
	 * 
	 * @param taskId
	 * @return
	 */
	public String getBusinessKeyByTask(Task task);
	/**
	 * 完成任务
	 */
	public ActivitiCom complete(String taskId, Map<String, Object> map);

	/**
	 * 获取业务Id
	 */
	public String getBusinessKeyByTask(String taskId);
	/**
	 *根据流程KEY和当前流程环节标示获取当前环节对象
	 */
	public TPProcessnode getTPProcessnode(String taskDefinitionKey,String processkey);
	/**
	 * 获取全部部署流程
	 */
	@Transactional(readOnly = true)
	public List processDefinitionList();
	
	//update-begin--Author:zhoujunfeng  Date:20140809 for：流程processkey获取该流程发布的流程-------------------
	/**
	 * 获取流程processkey获取该流程下已经发布的流程
	 */
	@Transactional(readOnly = true)
	public List processDefinitionListByProcesskey(String processkey);
	//update-end--Author:zhoujunfeng  Date:20140809 for：流程processkey获取该流程发布的流程-------------------
	
	/**
	 * 根据taskId获取task对象
	 * @param taskId
	 * @return
	 */
	public Task getTask(String taskId);
	/**
	 * 查询流程定义对象
	 * 
	 * @param processDefinitionId
	 *            流程定义ID
	 * @return
	 */
	public ProcessDefinition getProcessDefinition(String processDefinitionId);
	/**
	 * 根据taskId封装ProcessHandle对象
	 * @param taskId
	 * @return
	 */
	public ProcessHandle getProcessHandle(String taskId);
	/**
	 * 根据业务ID获取HistoricProcessInstance对象
	 * 
	 * @param businessKey
	 * @return
	 */
	public HistoricProcessInstance getHiProcInstByBusKey(String businessKey);
	/**
	 * 根据父流程业务ID更新子流程的业务ID
	 * 
	 * @param parBusKey
	 *            父流程业务ID
	 * @param subBusKey
	 *            子流程业务ID
	 * @return
	 */
	public void updateHiProcInstBusKey(String parBusKey, String subBusKey);
	
	
	/**
	 * 根据角色编码和当前审批状态值获取审批状态
	 */
	public TSPrjstatus getTBPrjstatusByCode(String prjstate, String rolecode);
	/**
	 * 根据流程编码和业务类名获取业务配置类
	 */
	public TSBusConfig getTSBusConfig(Class classname, String processkey);
	
	/**
	 * 注意会把整个表数据删除
	 * @param <T>
	 */
	public <T> void batchDelete(final Class<T> entityClass);
	/**
	 * 查询待办任务-只查询用户自己的
	 * @param userId
	 * @param tsBusConfigs
	 * @param request 
	 * @return
	 */
	public List findPriTodoTasks(String userId,  HttpServletRequest request);
	/**
	 * 查询组待办任务
	 * @param userName
	 * @param tsBusConfigs
	 * @param request 
	 * @return
	 */
	public List findGroupTodoTasks(List<TSRoleUser> roles,HttpServletRequest request);
	/**
	 * 查询历史任务
	 * @param userName
	 * @param request 
	 * @return
	 */
	public List findHistoryTasks(String userName, HttpServletRequest request);
	/**
	 * 查询所有的历史任务
	 * @param request 
	 * @return
	 */
	public List findAllHistoryTasks(HttpServletRequest request);
	
	//-update-begin-----------author:zhoujf date:20170306 for:TASK #1100 【工作流完善】抄送功能
	/**
	 * 查询抄送给我的历史任务
	 * @param request 
	 * @return
	 */
	public List findAllCcHistoryTasks(HttpServletRequest request);
	//-update-end-----------author:zhoujf date:20170306 for:TASK #1100 【工作流完善】抄送功能
	
	/**
	 * 查询待办任务-只查询用户自己的 (统计总数)
	 * @param userName
	 * @param tsBusConfigs
	 * @param request
	 * @return
	 */
	public Long countPriTodaoTask(String userName,HttpServletRequest request);
	/**
	 * 查询组待办任务 (统计总数)
	 * @param roles
	 * @param tsBusConfigs
	 * @param request
	 * @return
	 */
	public Long countGroupTodoTasks(List<TSRoleUser> roles, HttpServletRequest request);
	/**
	 * 查询历史任务(统计总数)
	 * @param userName
	 * @param request
	 * @return
	 */
	public Long countHistoryTasks(String userName, HttpServletRequest request);
	
	/**
	 * 查询所有的历史任务(统计总数)
	 * @param userName
	 * @param request
	 * @return
	 */
	public Long countAllHistoryTasks(HttpServletRequest request);
	
	//-update-begin-----------author:zhoujf date:20170306 for:TASK #1100 【工作流完善】抄送功能
	/**
	 * 查询所有抄送给我的历史任务(统计总数)
	 * @param userName
	 * @param request
	 * @return
	 */
	public Long countAllCcHistoryTasks(HttpServletRequest request);
	//-update-end-----------author:zhoujf date:20170306 for:TASK #1100 【工作流完善】抄送功能
	
	/**
	 * 通过节点ID，获取当前节点出发的路径
	 */
	public List<PvmTransition> getOutgoingTransitions(String taskId);
	
	/**
	 * 查询流程下审批意见
	 * @param taskId
	 * @return
	 */
	public List findBpmLogsByBpmID(String bpm_id);
	
	/**
	 * 通过任务节点ID，获取当前节点 分支
	 */
	public List getOutTransitions(String taskId) ;
	
	/**
	 * 根据taskId,获取根节点Start的信息
	 * 
	 * @param taskId
	 * @return
	 */
	public TPProcessnode getProcessStartNode(String taskId);
	
	/**
	 * 根据processkey,获取根节点Start的信息
	 * 
	 * @param processkey
	 * @return
	 */
	public TPProcessnode getProcessStartByProcesskey(String processkey);
	
	/**
	 * 查询流程的历史任务节点
	 * @param proceInsId
	 * @return
	 */
	public List<Map<String,Object>> getHistTaskNodeList(String proceInsId);
	
	/**
	 * 查询流程的所有节点
	 * @param taskId
	 * @return
	 */
	public List getAllTaskNode(String taskId);
	
	/**
	 * 根据流程节点ID 和 菜单Id 获取 具有操作权限的按钮Codes
	 * @param nodeId
	 * @param functionId
	 * @return
	 */
	public Set<String> getNodeOperaCodesByNodeIdAndFunctionId(String nodeId, String functionId);
	
	//update-begin--Author:zhoujf  Date:20180702 for：节点权限授权
	/**
	 * 查询节点权限，权限并集处理，剔除授权的权限
	 * @param nodeId
	 * @param operations 菜单权限
	 * @return
	 */
	public List<TSOperation> getNodeOperaCodesByNodeId(String nodeId, List<TSOperation> operations);
	//update-end--Author:zhoujf  Date:20180702 for：节点权限授权
	
	/**
	 * 
	 * @param nodeId
	 * @param functionId
	 * @return
	 */
	public  Set<String> getOperationCodesByNodeIdAndruleDataId(String nodeId,String functionId);
	
	//update-begin--Author:zhoujf  Date:20170619 for：TASK #2126 【删除关联数据没删除】删除流程，流程相关的表数据没清理
	/**
	 * 删除流程
	 * @param 
	 * @return
	 * @author：zhoujf
	 * @since：2017-6-19 下午05:57:54
	 */
	public void delprocess(TPProcess process);
	//update-end--Author:zhoujf  Date:20170619 for：TASK #2126 【删除关联数据没删除】删除流程，流程相关的表数据没清理
	
	//update-begin--Author:zhoujunfeng  Date:20171012 for：流程节点表单配置，记录发布版本-------------------
	/**
	 * 发布流程
	 * @param process
	 */
	public void deployProcess(TPProcess process) throws Exception;
	//update-end--Author:zhoujunfeng  Date:20171012 for：流程节点表单配置，记录发布版本-------------------
	
	//-update-begin-----------author:zhoujf date:20180713 for:TASK #2939 【改进】催办任务
	/**
	 * 催办
	 * @param processInstanceId
	 */
	public void taskNotification(String processInstanceId,String remarks);
	//-update-end-----------author:zhoujf date:20180713 for:TASK #2939 【改进】催办任务
}
