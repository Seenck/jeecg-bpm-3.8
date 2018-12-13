package jeecg.workflow.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.workflow.pojo.base.TPProcesspro;
import org.jeecgframework.workflow.service.ActivitiService;

import com.google.common.collect.Maps;


/**
 * 
 * 类名：FormProcUtil
 *
 */
public class FormProcUtil {

	private static final Logger logger = Logger.getLogger(FormProcUtil.class);
	
	
	/**
	 * 初始化表单参数
	 * @param request
	 */
	public static void initFormParam(HttpServletRequest request){
		//初始化form表单参数
		commonParam(request);
		//初始化工作流相关参数
		initWorkflowParam(request);
		//初始节点变量
		initProcessnodeVar(request);
	}

	//参数透传处理
	private static void commonParam(HttpServletRequest request){
		Map<String,String[]> paramMap = request.getParameterMap();
		for(Map.Entry<String, String[]> entry:paramMap.entrySet()){   
			 String[] value = entry.getValue();
			 String valueStr = "";
			 if(value!=null&&value.length>0){
				 valueStr = value[0];
			 }
		     request.setAttribute(entry.getKey(), valueStr);
		}   
	}
	
	//工作流按钮信息处理
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void initWorkflowParam(HttpServletRequest request){
		ActivitiService activitiService = ApplicationContextUtil.getContext().getBean(ActivitiService.class);
		String taskId = oConvertUtils.getString(request.getParameter("taskId"));
		//流程下一步节点操作
		List<Map> transList = activitiService.getOutTransitions(taskId);
		//判断一下任务节点后续的分支，是否只有一个，如果是且分支的路线中文名字没有的话，默认为提交
		if(transList.size()==1){
			for(Map t:transList){
				String name = (String)t.get("Transition");
				if(StringUtil.isEmpty(name)||name.startsWith("flow")){
					t.put("Transition", "下一步");
				}
			}
		}
		//流程分支
		request.setAttribute("transitionList", transList);
		//下一步节点数目
		request.setAttribute("nextCodeCount", transList==null?0:transList.size());
		
	}
	
	//初始化节点变量
	private static void initProcessnodeVar(HttpServletRequest request){
		String taskId = oConvertUtils.getString(request.getParameter("taskId"));
		ActivitiService activitiService = ApplicationContextUtil.getContext().getBean(ActivitiService.class);
		Map<String,String> map = new HashMap<String, String>();
		//根据节点ID 查询节点变量配置
		Task task = activitiService.getTask(taskId);
		String nodeid = task.getTaskDefinitionKey();
		String hql = "from TPProcesspro where TPProcessnode.processnodecode = ? and processprotype = 'default'";
		List<TPProcesspro> list = activitiService.findHql(hql, nodeid);
		if(list!=null&&list.size()>0){
			for(TPProcesspro processpro:list){
				map.put(processpro.getProcessprokey(), processpro.getProcessprovalue());
			}
		}
		request.setAttribute("nodeVariables", map);
	}
	
}
