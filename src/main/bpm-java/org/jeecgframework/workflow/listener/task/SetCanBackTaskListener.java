/**  
 * @Title: SetCanBackTaskListener.java
 * @date 2013-8-21 下午4:42:19
 * @Copyright: 2013 
 */
package org.jeecgframework.workflow.listener.task;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * 驳回监听器 事件:create 任务创建时
 * 在此任务本地变量存一个变量，标记此任务可以进行驳回操作
 * 
 * @author	jeecg
 * @version	 1.0
 *
 */
public class SetCanBackTaskListener implements TaskListener{

	/**
	 * @Fields serialVersionUID : 
	 */
	
	private static final long serialVersionUID = 1L;

	public void notify(DelegateTask delegateTask) {
		
		delegateTask.setVariableLocal("canBack", true);//此任务可以进行驳回操作，用此变量标识
		
	}

}
