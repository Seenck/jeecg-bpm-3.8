package org.jeecgframework.web.system.controller.core;

import java.io.Serializable;

/**
 * 线程任务接口
 * @author Administrator
 *
 */
public interface ThreadTask extends Runnable, Serializable {
	public void run();
}
