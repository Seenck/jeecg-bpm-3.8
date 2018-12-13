/**  
 * @Project: jeecg
 * @Title: ProcessEndListener.java
 * @Package com.oa.manager.workFlow.listener.execution
 * @date 2013-8-16 下午2:04:12
 * @Copyright: 2013 
 */
package org.jeecgframework.workflow.listener.execution;

import java.util.Map;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.workflow.common.WorkFlowGlobals;

/**
 * 
 * 类名：ProcessEndListener 
 * 功能：子流程启动监听 详细： 
 * 作者：jeecg 
 * 版本：1.0 
 * 日期：2013-8-16 
 * 下午2:04:12
 * 
 */
public class SubProcessStartListener implements ExecutionListener {

	/**
	 * @Fields serialVersionUID :
	 */

	private static final long serialVersionUID = 1L;

	public void notify(DelegateExecution execution) throws Exception {
		//获取子流程实例ID
		String sub_executionId = execution.getProcessInstanceId();
		//获取主流程实例ID
		String sub_main_process_id = (String) execution.getVariable(WorkFlowGlobals.JG_SUB_MAIN_PROCESS_ID);
		if(oConvertUtils.isNotEmpty(sub_main_process_id)){
			RuntimeService runtimeService = ApplicationContextUtil.getContext().getBean(RuntimeService.class);
			//获取主流程的流程变量
			Map<String, Object> main_map = runtimeService.getVariables(sub_main_process_id);
			for (String key : main_map.keySet()) {
				System.out.println("key= " + key + " and value= " + main_map.get(key));
				//TODO根据自己情况编写即可
				//runtimeService.setVariable(sub_executionId, key, main_map.get(key));
			}
		}
	}

}
