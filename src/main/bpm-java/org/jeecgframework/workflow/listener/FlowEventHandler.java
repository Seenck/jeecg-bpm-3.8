package org.jeecgframework.workflow.listener;

import org.activiti.engine.delegate.event.ActivitiEvent;

/**
 * Activiti的事件处理器 
 * @author zhoujf
 *
 */
public interface FlowEventHandler {
	 /** 
	  * 事件处理器 
	  * @param event 
	  */  
	 public void handle(ActivitiEvent event); 
}
