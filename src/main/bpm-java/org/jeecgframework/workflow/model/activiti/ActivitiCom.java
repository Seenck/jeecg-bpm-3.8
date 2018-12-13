package org.jeecgframework.workflow.model.activiti;
/**
* @ClassName: ActivitiCom 
* @Description: TODO(流程办理返回信息) 
* @author jeecg 
* @date 2013-1-28 下午02:13:17 
*
 */
public class ActivitiCom {
	private String msg;//提示信息
	private Boolean complete=true;//办理标志
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Boolean getComplete() {
		return complete;
	}
	public void setComplete(Boolean complete) {
		this.complete = complete;
	}

}
