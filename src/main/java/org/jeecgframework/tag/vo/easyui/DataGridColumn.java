package org.jeecgframework.tag.vo.easyui;
/**
 * 
 * 类描述：列表字段模型
 * 
 * @author:  张代浩
 * @date： 日期：2012-12-7 时间：上午10:17:45
 * @version 1.0
 */
public class DataGridColumn {
	protected String title;//表格列名
	protected String field;//数据库对应字段
	protected Integer width;//宽度
	//author：xugj start date:2016年5月11日 for:TASK #1080 【UI标签改造】t:dgCol 显示内容长度控制 -->
	protected Integer showLen; //显示长度
	//author：xugj end date:2016年5月11日 for:TASK #1080 【UI标签改造】t:dgCol 显示内容长度控制 -->
	protected String rowspan;//跨列
	protected String colspan;//跨行
	protected String align;//对齐方式
	protected boolean sortable;//是否排序
	protected boolean checkbox;//是否显示复选框
	protected String formatter;//格式化函数
	protected String formatterjs;//自定义函数名称(调用页面自定义js方法 参数为(value,row,index)
	protected boolean hidden;//是否隐藏
	protected String treefield;//
	protected boolean image;//是否是图片
	protected boolean query;//是否查询
	protected String queryMode = "single";//字段查询模式：single单字段查询；group范围查询
	
	protected boolean autoLoadData = true; // 列表是否自动加载数据
	private boolean frozenColumn=false; // 是否是冰冻列    默认不是
	protected String url;//自定义链接
	protected String funname="openwindow";//自定义函数名称
	protected String arg;
	protected String dictionary;
	//-- update-begin--Author:gj_shaojc  Date:20180314 for：TASK #2560 【UI标签扩展】column列表字典属性扩展dictCondition加sql条件  --
	protected String dictCondition;
	//-- update-end--Author:gj_shaojc  Date:20180314 for：TASK #2560 【UI标签扩展】column列表字典属性扩展dictCondition加sql条件  --
	protected boolean popup=false;	//是否启用popup模式选择 默认不启用
	protected String replace;
	protected String extend;
	protected String style; //列的颜色值
	protected String imageSize;//自定义图片显示大小
	protected String downloadName;//附件下载
	protected boolean autocomplete;//自动补全
	protected String extendParams;//扩展参数,easyui有的,但是jeecg没有的参数进行扩展
	protected String editor;//高级查询用的编辑器
//  update-start--Author:chenjin  Date:20160715 for：扩展标签<t:dgCol 增加字段defaultVal=""
	private String defaultVal = "";//列默认值
//  update-end--Author:chenjin  Date:20160715 for：扩展标签<t:dgCol 增加字段defaultVal=""	
	//update-begin--Author:xuelin  Date:20170706 for：TASK #2205 【UI标签库】列表查询条件动态生成，下拉换成redio模式切换----------------------
	protected String showMode;//表单元素,查询表单中显示样式,默认样式select	
	//update--begin--author:zhangjiaqiang Date:20170815 for:TASK #2273 【demo】datagrid 多表头demo
	protected boolean newColumn;
	//update-begin-Author:taoYan  Date:20180117 for:船舶风格列属性扩展 
	private String filterType = "text";//过滤操作的类型
	private boolean optsMenu = false;//操作列风格转变
	//update-end-Author:taoYan  Date:20180117 for:船舶风格列属性扩展 
	//update-begin-Author:zhoujf  Date:20180712 for:TASK #2809 【bug】网友反馈问题，字典性能问题
	private boolean isAjaxDict = false;
	//update-end-Author:zhoujf  Date:20180712 for:TASK #2809 【bug】网友反馈问题，字典性能问题
	public boolean isNewColumn() {
		return newColumn;
	}

	public void setNewColumn(boolean newColumn) {
		this.newColumn = newColumn;
	}
	//update--begin--author:zhangjiaqiang Date:20170815 for:TASK #2273 【demo】datagrid 多表头demo
	public String getShowMode() {
		return showMode;
	}

	public void setShowMode(String showMode) {
		this.showMode = showMode;
	}
	//update-end--Author:xuelin  Date:20170706 for：TASK #2205 【UI标签库】列表查询条件动态生成，下拉换成redio模式切换----------------------

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}
	public String getDownloadName() {
		return downloadName;
	}

	public void setDownloadName(String downloadName) {
		this.downloadName = downloadName;
	}

	public String getImageSize() {
		return imageSize;
	}

	public void setImageSize(String imageSize) {
		this.imageSize = imageSize;
	}

	public boolean isQuery() {
		return query;
	}

	public String getArg() {
		return arg;
	}

	public void setArg(String arg) {
		this.arg = arg;
	}

	public void setQuery(boolean query) {
		this.query = query;
	}

	public boolean isImage() {
		return image;
	}

	public void setImage(boolean image) {
		this.image = image;
	}

	public String getTreefield() {
		return treefield;
	}

	public void setTreefield(String treefield) {
		this.treefield = treefield;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setField(String field) {
		this.field = field;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getShowLen() {
		return showLen;
	}

	public void setShowLen(Integer showLen) {
		this.showLen = showLen;
	}

	public void setRowspan(String rowspan) {
		this.rowspan = rowspan;
	}

	public void setColspan(String colspan) {
		this.colspan = colspan;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}

	public void setCheckbox(boolean checkbox) {
		this.checkbox = checkbox;
	}

	public void setFormatter(String formatter) {
		this.formatter = formatter;
	}
	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public String getTitle() {
		return title;
	}

	public String getField() {
		return field;
	}

	public Integer getWidth() {
		return width;
	}

	public String getRowspan() {
		return rowspan;
	}

	public String getColspan() {
		return colspan;
	}

	public String getAlign() {
		return align;
	}

	public boolean isSortable() {
		return sortable;
	}

	public boolean isCheckbox() {
		return checkbox;
	}

	public String getFormatter() {
		return formatter;
	}
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFunname() {
		return funname;
	}

	public void setFunname(String funname) {
		this.funname = funname;
	}

	public String getDictionary() {
		return dictionary;
	}

	public void setDictionary(String dictionary) {
		this.dictionary = dictionary;
	}
	//-- update-begin--Author:gj_shaojc  Date:20180314 for：TASK #2560 【UI标签扩展】column列表字典属性扩展dictCondition加sql条件  --
	public String getDictCondition() {
		return dictCondition;
	}

	public void setDictCondition(String dictCondition) {
		this.dictCondition = dictCondition;
	}
	//-- update-end--Author:gj_shaojc  Date:20180314 for：TASK #2560 【UI标签扩展】column列表字典属性扩展dictCondition加sql条件  --
	public boolean isPopup() {
		return popup;
	}

	public void setPopup(boolean popup) {
		this.popup = popup;
	}

	public String getQueryMode() {
		return queryMode;
	}

	public void setQueryMode(String queryMode) {
		this.queryMode = queryMode;
	}

	public String getReplace() {
		return replace;
	}

	public void setReplace(String replace) {
		this.replace = replace;
	}

	public boolean isAutoLoadData() {
		return autoLoadData;
	}

	public void setAutoLoadData(boolean autoLoadData) {
		this.autoLoadData = autoLoadData;
	}

	public boolean isFrozenColumn() {
		return frozenColumn;
	}

	public void setFrozenColumn(boolean frozenColumn) {
		this.frozenColumn = frozenColumn;
	}

	public String getExtend() {
		return extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public boolean isAutocomplete() {
		return autocomplete;
	}

	public void setAutocomplete(boolean autocomplete) {
		this.autocomplete = autocomplete;
	}

	public String getExtendParams() {
		return extendParams;
	}

	public void setExtendParams(String extendParams) {
		this.extendParams = extendParams;
	}

	public String getFormatterjs() {
		return formatterjs;
	}

	public void setFormatterjs(String formatterjs) {
		this.formatterjs = formatterjs;
	}

	public String getDefaultVal() {
		return defaultVal;
	}

	public void setDefaultVal(String defaultVal) {
		this.defaultVal = defaultVal;
	}

	public String getFilterType() {
		return filterType;
	}
	public void setFilterType(String filterType) {
		this.filterType = filterType;
	}
	
	public boolean isOptsMenu() {
		return optsMenu;
	}
	public void setOptsMenu(boolean optsMenu) {
		this.optsMenu = optsMenu;
	}
	
	public boolean getIsAjaxDict() {
		return isAjaxDict;
	}

	public void setAjaxDict(boolean isAjaxDict) {
		this.isAjaxDict = isAjaxDict;
	}

	@Override
	public String toString() {
		return "DataGridColumn [title=" + title + ", field=" + field + ", width=" + width + ", showLen=" + showLen + ", rowspan=" + rowspan + ", colspan=" + colspan + 
		", align=" + align + ", sortable=" + sortable + ", checkbox=" + checkbox + ", formatter=" + formatter + ", formatterjs=" + formatterjs + ", hidden=" + hidden + 
		", treefield=" + treefield + ", image=" + image + ", query=" + query + ", queryMode=" + queryMode + ", autoLoadData=" + autoLoadData + ", frozenColumn=" + frozenColumn + 
		", url=" + url + ", funname=" + funname + ", arg=" + arg + ", dictionary=" + dictionary + ", popup=" + popup + ", replace=" + replace + ", extend=" + extend +
		", style=" + style + ", imageSize=" + imageSize + ", downloadName=" + downloadName + ", autocomplete=" + autocomplete + ", extendParams="
				+ extendParams + ", editor=" + editor + ", defaultVal=" + defaultVal + ", showMode=" + showMode + ", newColumn=" + newColumn + "]";
	}
	
	
}
