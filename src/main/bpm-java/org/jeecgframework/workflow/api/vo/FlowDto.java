package org.jeecgframework.workflow.api.vo;

public class FlowDto {
	
	/**
	 * 系统标识
	 */
	private String sysCode;
	/**
	 * pc表单访问地址
	 */
	private String formUrl;
	/**
	 * 单据ID
	 */
	private String dataId;
	/**
	 * 流程业务标题
	 */
	private String bizTitile;
	/**
	 * 第三方系统类型
	 */
	private String applySysCode;
	/**
	 * 流程发起人
	 */
	private String applyUserId;
	/**
	 * 移动端表单访问地址
	 */
	private String mobileFormUrl;
	/**
	 * 主数据 json数据
	 */
	private String data;
	/**
	 * 流程key
	 */
	private String processKey;
	/**
	 * 业务回调地址
	 */
	private String callBackUrl;
	/**
	 * 签名
	 */
	private String sign; 
	
	public String getSysCode() {
		return sysCode;
	}
	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}
	public String getFormUrl() {
		return formUrl;
	}
	public void setFormUrl(String formUrl) {
		this.formUrl = formUrl;
	}
	public String getMobileFormUrl() {
		return mobileFormUrl;
	}
	public void setMobileFormUrl(String mobileFormUrl) {
		this.mobileFormUrl = mobileFormUrl;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getProcessKey() {
		return processKey;
	}
	public void setProcessKey(String processKey) {
		this.processKey = processKey;
	}
	public String getCallBackUrl() {
		return callBackUrl;
	}
	public void setCallBackUrl(String callBackUrl) {
		this.callBackUrl = callBackUrl;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getDataId() {
		return dataId;
	}
	public void setDataId(String dataId) {
		this.dataId = dataId;
	}
	public String getApplyUserId() {
		return applyUserId;
	}
	public void setApplyUserId(String applyUserId) {
		this.applyUserId = applyUserId;
	}
	public String getBizTitile() {
		return bizTitile;
	}
	public void setBizTitile(String bizTitile) {
		this.bizTitile = bizTitile;
	}
	public String getApplySysCode() {
		return applySysCode;
	}
	public void setApplySysCode(String applySysCode) {
		this.applySysCode = applySysCode;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FlowDto {\"sysCode\":\"");
		builder.append(sysCode);
		builder.append("\", \"formUrl\":\"");
		builder.append(formUrl);
		builder.append("\", \"dataId\":\"");
		builder.append(dataId);
		builder.append("\", \"bizTitile\":\"");
		builder.append(bizTitile);
		builder.append("\", \"applySysCode\":\"");
		builder.append(applySysCode);
		builder.append("\", \"applyUserId\":\"");
		builder.append(applyUserId);
		builder.append("\", \"mobileFormUrl\":\"");
		builder.append(mobileFormUrl);
		builder.append("\", \"data\":\"");
		builder.append(data);
		builder.append("\", \"processKey\":\"");
		builder.append(processKey);
		builder.append("\", \"callBackUrl\":\"");
		builder.append(callBackUrl);
		builder.append("\", \"sign\":\"");
		builder.append(sign);
		builder.append("\"}");
		return builder.toString();
	}
	
	

}
