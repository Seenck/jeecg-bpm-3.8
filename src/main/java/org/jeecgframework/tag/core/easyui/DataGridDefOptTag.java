package org.jeecgframework.tag.core.easyui;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 
 * 类描述：列表默认操作项标签
 * 
 * 张代浩
 * @date： 日期：2012-12-7 时间：上午10:17:45
 * @version 1.0
 */
public class DataGridDefOptTag extends TagSupport {
	protected String url;
	protected String title;
	private String exp;//判断链接是否显示的表达式
	private String operationCode;//按钮的操作Code
	private String urlStyle;//样式
	
	//update--begin--author:zhangjiaqiang date:20170912 for:TASK 1779 UI标签样式修订
	private String urlclass;//按钮样式
	private String urlfont;//按钮图标
	//update--end--author:zhangjiaqiang date:20170912 for:TASK 1779 UI标签样式修订
	//update-begin-author：jiaqiankun date:20180713 for:TASK #2872 【新功能】列表按钮改造成配置，支持折叠模式，增加一个参数
	private boolean inGroup;
	//update-end-author：jiaqiankun date:20180713 for:TASK #2872 【新功能】列表按钮改造成配置，支持折叠模式，增加一个参数
	
	public int doStartTag() throws JspTagException {
		return EVAL_PAGE;
	}
	public int doEndTag() throws JspTagException {
		Tag t = findAncestorWithClass(this, DataGridTag.class);
		DataGridTag parent = (DataGridTag) t;
		//update-begin-author：jiaqiankun date:20180713 for:TASK #2872 【新功能】列表按钮改造成配置，支持折叠模式，增加一个参数
		parent.setDefUrl(url, title, exp,operationCode,urlStyle,urlclass,urlfont,inGroup);
		//update-end-author：jiaqiankun date:20180713 for:TASK #2872 【新功能】列表按钮改造成配置，支持折叠模式，增加一个参数
		return EVAL_PAGE;
	}
	
	public void setExp(String exp) {
		this.exp = exp;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setOperationCode(String operationCode) {
		this.operationCode = operationCode;
	}
	public void setUrlStyle(String urlStyle) {
		this.urlStyle = urlStyle;
	}
	public String getUrlStyle() {
		return urlStyle;
	}
	public String getUrlclass() {
		return urlclass;
	}
	public void setUrlclass(String urlclass) {
		this.urlclass = urlclass;
	}
	public String getUrlfont() {
		return urlfont;
	}
	public void setUrlfont(String urlfont) {
		this.urlfont = urlfont;
	}
	//update-begin-author：jiaqiankun date:20180712 for:TASK #2872 【新功能】列表按钮改造成配置，支持折叠模式，增加一个参数
	public boolean isInGroup() {
		return inGroup;
	}
	public void setInGroup(boolean inGroup) {
		this.inGroup = inGroup;
	}
	//update-end-author：jiaqiankun date:20180712 for:TASK #2872 【新功能】列表按钮改造成配置，支持折叠模式，增加一个参数
}
