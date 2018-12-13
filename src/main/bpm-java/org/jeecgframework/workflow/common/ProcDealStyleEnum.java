package org.jeecgframework.workflow.common;

/**
 * 流程办理风格枚举
 * @author junfeng.zhou
 * @version $ v1.0 $
 */
public enum ProcDealStyleEnum {
	DEFAULT("default", "默认",""),
	office("office", "流程与表单自定义","office/"),
	SINGLE("single", "单页面","single/"),
	SINGLE_SUBMIT("singlesubmit", "单页面并提交表单","singlesubmit/");
	private String code;//风格编码
	private String name;//风格名称
	private String viewName;//风格页面路径
	
	private ProcDealStyleEnum(String code, String name,String viewName) {
		this.code = code;
		this.name = name;
		this.viewName = viewName;
	}
	public static ProcDealStyleEnum toEnum(String code) {
		for (ProcDealStyleEnum type : ProcDealStyleEnum.values()) {
			if (type.code.equals(code)) {
				return type;
			}
		}
		return ProcDealStyleEnum.DEFAULT;
//		throw new IllegalArgumentException("the style argument of " + code 
//				+ " has no correspond ProcDealStyleEnum enums.");
	}
	
	public String getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
	public String getViewName() {
		return viewName;
	}
	
}

