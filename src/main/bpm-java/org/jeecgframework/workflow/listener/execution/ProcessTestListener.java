/**  
 * @Project: jeecg
 * @Title: ProcessEndListener.java
 * @Package com.oa.manager.workFlow.listener.execution
 * @date 2013-8-16 下午2:04:12
 * @Copyright: 2013 
 */
package org.jeecgframework.workflow.listener.execution;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;



/**
 * 
 * 类名：ProcessTestListener
 * 功能：流程实例测试监听器
 * 详细：
 * 作者：jeecg
 * 版本：1.0
 * 日期：2013-8-16 下午2:04:12
 *
 */
public class ProcessTestListener implements ExecutionListener{

	/**
	 * @Fields serialVersionUID : 
	 */
	
	private static final long serialVersionUID = 1L;

	public void notify(DelegateExecution execution) throws Exception {
		System.out.println("-----------------------------------执行测试流程监听---------------------");
	}

}
