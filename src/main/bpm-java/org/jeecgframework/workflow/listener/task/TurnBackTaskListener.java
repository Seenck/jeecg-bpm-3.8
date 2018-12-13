/**  
 * @Title: TurnBackTaskListener.java
 * @date 2013-8-19 下午4:26:40
 * @Copyright: 2013 
 */
package org.jeecgframework.workflow.listener.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * 驳回监听器
 * 将此监听器放在可以驳回的用户任务，事件：complete(任务完成)
 * 允许驳回到此任务节点，将此任务放入可驳回列表
 * @author	jeecg
 * @version	 1.0
 *
 */
public class TurnBackTaskListener implements TaskListener{

	/**
	 * @Fields serialVersionUID : 
	 */
	
	private static final long serialVersionUID = 1L;

	public void notify(DelegateTask dt) {
		
		//从流程变量获取可以驳回的任务节点id集合
		List<Map<String,String>> ids=(List<Map<String,String>>)dt.getVariable("backTaskIds");
		if(ids==null){
			ids=new ArrayList<Map<String,String>>();
		}
		//将当前任务id,name  放于可驳回的集合
		
		boolean notHas=true;
		for(Map<String,String> m:ids){
			if(dt.getTaskDefinitionKey().equals((String)m.get("id"))){
				notHas=false;
				break;
			}
		}
		if(notHas){
			Map<String,String>map=new HashMap<String,String>();
			map.put("id", dt.getTaskDefinitionKey());
			map.put("name", dt.getName());
			ids.add(map);
		}
		
		dt.setVariable("backTaskIds", ids);
		
		
	}

}
