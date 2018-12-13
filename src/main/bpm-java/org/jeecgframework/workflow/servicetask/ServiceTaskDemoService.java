package org.jeecgframework.workflow.servicetask;

import java.io.Serializable;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.jeecgframework.core.common.exception.BusinessException;
/** 
 *  
 * javaClass:
 * 作为activiti的ServiceTask的实现，必须实现JavaDelegate接口，但是不需要实现序列化接口 
 * activiti会使用反射机制将类初始化，因此在实现JavaDelegate接口的时候需要提供一个 
 * 无参数的构造器，否则会抛出异常。 
 * delegateExecution:
 * 实现JavaDelegate接口，使用其中的execute方法 由于要放入流程定义中，所以要实现可序列话接口 
 *  
 * @author zhoujf 
 * 
 */ 
public class ServiceTaskDemoService implements Serializable,JavaDelegate {

	private static final long serialVersionUID = -3189242802743801949L;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		    System.out.println("---------------------------------------------");  
	        System.out.println();  
	        System.out.println("Service task " + this.toString()  
	                + "测试服务任务!");  
	        System.out.println("---------------------------------------------");  
	        System.out.println();
//	        throw new BusinessException("测试服务任务异常");
	}

}
