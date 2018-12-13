package org.jeecgframework.tag.core.easyui;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import jodd.util.StringUtil;

import org.apache.log4j.Logger;
import org.jeecgframework.core.enums.SysThemesEnum;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.SysThemesUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.tag.core.JeecgTag;

/**
 * 
 * @author  张代浩
 *
 */
public class BaseTag extends JeecgTag {
	private static Logger log = Logger.getLogger(BaseTag.class);
	private static final long serialVersionUID = 1L;
	protected String type = "default";// 加载类型
	protected String cssTheme ;
	
	public String getCssTheme() {
		return cssTheme;
	}


	public void setCssTheme(String cssTheme) {
		this.cssTheme = cssTheme;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	public int doStartTag() throws JspException {
		return EVAL_PAGE;
	}

	
	public int doEndTag() throws JspException {
		JspWriter out = null;
		try {
			out = this.pageContext.getOut();
			out.print(end().toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(out!=null){
				try {
					out.clearBuffer();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return EVAL_PAGE;
	}

	public StringBuffer end(){
		StringBuffer sb = this.getTagCache();
		if(sb != null){
			return sb;
		}
		sb = new StringBuffer();
		String types[] = type.split(",");
		SysThemesEnum sysThemesEnum = null;
		if(StringUtil.isEmpty(cssTheme)||"null".equals(cssTheme)){
			sysThemesEnum = SysThemesUtil.getSysTheme((HttpServletRequest) super.pageContext.getRequest());
		}else{
			sysThemesEnum = SysThemesEnum.toEnum(cssTheme);
		}
		
		//插入多语言脚本
		HttpServletRequest request =(HttpServletRequest) this.pageContext.getRequest();
		String lang = (String)request.getSession().getAttribute("lang");
		String basePath = ResourceUtil.getBasePath();
		if(lang==null){lang="zh-cn";}
		String langjs = StringUtil.replace("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/mutiLang/{0}.js\"></script>", "{0}", lang);
		sb.append(langjs);
		if (oConvertUtils.isIn("jquery-webos", types)) {
            sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/sliding/js/jquery-1.7.1.min.js\"></script>");
            //update--begin--author:scott---------- date:20180702 ----------- for: i18n前段国际化-----------------------------------
			sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/jquery-plugs/i18n/jquery.i18n.properties.js\"></script>");
			//update--end--author:scott---------- date:20180702 ----------- for: i18n前段国际化-------------------------------------
            
		} else if (oConvertUtils.isIn("jquery", types)) {
			sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/jquery/jquery-1.8.3.js\"></script>");
			sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/jquery/jquery.cookie.js\" ></script>");
			sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/jquery-plugs/storage/jquery.storageapi.min.js\" ></script>");
			//update--begin--author:scott---------- date:20180702 ----------- for: i18n前段国际化-----------------------------------
			sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/jquery-plugs/i18n/jquery.i18n.properties.js\"></script>");
			//update--end--author:scott---------- date:20180702 ----------- for: i18n前段国际化-------------------------------------
		}
		if (oConvertUtils.isIn("ckeditor", types)) {
			sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/ckeditor/ckeditor.js\"></script>");
			sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/tools/ckeditorTool.js\"></script>");
		}
		if (oConvertUtils.isIn("easyui", types)) {
			sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/tools/dataformat.js\"></script>");
			sb.append(SysThemesUtil.getEasyUiTheme(sysThemesEnum));
			sb.append(SysThemesUtil.getEasyUiMainTheme(sysThemesEnum));
			sb.append(SysThemesUtil.getEasyUiIconTheme(sysThemesEnum));
			sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\""+basePath+"/plug-in/accordion/css/accordion.css\">");
			sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\""+basePath+"/plug-in/accordion/css/icons.css\">");
			sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/easyui/jquery.easyui.min.1.3.2.js\"></script>");
			sb.append(StringUtil.replace("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/easyui/locale/{0}.js\"></script>", "{0}", lang)); 
			sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/tools/syUtil.js\"></script>");
			sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/easyui/extends/datagrid-scrollview.js\"></script>");
			sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/mutitables/datagrid-filter.js\"></script>");
		}
		if (oConvertUtils.isIn("DatePicker", types)) {
			sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/My97DatePicker/WdatePicker.js\"></script>");
		}
		if (oConvertUtils.isIn("jqueryui", types)) {
			sb.append("<link rel=\"stylesheet\" href=\""+basePath+"/plug-in/jquery-ui/css/ui-lightness/jquery-ui-1.9.2.custom.min.css\" type=\"text/css\"></link>");
			sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/jquery-ui/js/jquery-ui-1.9.2.custom.min.js\"></script>");
		}
		if (oConvertUtils.isIn("jqueryui-sortable", types)) {
			sb.append("<link rel=\"stylesheet\" href=\""+basePath+"/plug-in/jquery-ui/css/ui-lightness/jquery-ui-1.9.2.custom.min.css\" type=\"text/css\"></link>");
			sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/jquery-ui/js/ui/jquery.ui.core.js\"></script>");
			sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/jquery-ui/js/ui/jquery.ui.widget.js\"></script>");
			sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/jquery-ui/js/ui/jquery.ui.mouse.js\"></script>");
			sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/jquery-ui/js/ui/jquery.ui.sortable.js\"></script>");
		}
		if (oConvertUtils.isIn("prohibit", types)) {
			sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/tools/prohibitutil.js\"></script>");		}
		if (oConvertUtils.isIn("designer", types)) {
			sb.append("<script type=\"text/javascript\" src=\"plug-in/designer/easyui/jquery-1.7.2.min.js\"></script>");
			sb.append("<link id=\"easyuiTheme\" rel=\"stylesheet\" href=\"plug-in/designer/easyui/easyui.css\" type=\"text/css\"></link>");
			sb.append("<link rel=\"stylesheet\" href=\"plug-in/designer/easyui/icon.css\" type=\"text/css\"></link>");
			sb.append("<script type=\"text/javascript\" src=\"plug-in/designer/easyui/jquery.easyui.min.1.3.0.js\"></script>");
			
			//加载easyui多语言
			sb.append(StringUtil.replace("<script type=\"text/javascript\" src=\"plug-in/easyui/locale/{0}.js\"></script>", "{0}", lang));	
			
			sb.append("<script type=\"text/javascript\" src=\"plug-in/tools/syUtil.js\"></script>");
			sb.append("<script type=\'text/javascript\' src=\'plug-in/jquery/jquery-autocomplete/lib/jquery.bgiframe.min.js\'></script>");
			sb.append("<script type=\'text/javascript\' src=\'plug-in/jquery/jquery-autocomplete/lib/jquery.ajaxQueue.js\'></script>");
			sb.append("<script type=\'text/javascript\' src=\'plug-in/jquery/jquery-autocomplete/jquery.autocomplete.min.js\'></script>");
			sb.append("<link href=\"plug-in/designer/designer.css\" type=\"text/css\" rel=\"stylesheet\" />");
			sb.append("<script src=\"plug-in/designer/draw2d/wz_jsgraphics.js\"></script>");
			sb.append("<script src=\'plug-in/designer/draw2d/mootools.js\'></script>");
			sb.append("<script src=\'plug-in/designer/draw2d/moocanvas.js\'></script>");
			sb.append("<script src=\'plug-in/designer/draw2d/draw2d.js\'></script>");
			sb.append("<script src=\"plug-in/designer/MyCanvas.js\"></script>");
			sb.append("<script src=\"plug-in/designer/ResizeImage.js\"></script>");
			sb.append("<script src=\"plug-in/designer/event/Start.js\"></script>");
			sb.append("<script src=\"plug-in/designer/event/End.js\"></script>");
			sb.append("<script src=\"plug-in/designer/connection/MyInputPort.js\"></script>");
			sb.append("<script src=\"plug-in/designer/connection/MyOutputPort.js\"></script>");
			sb.append("<script src=\"plug-in/designer/connection/DecoratedConnection.js\"></script>");
			sb.append("<script src=\"plug-in/designer/task/Task.js\"></script>");
			sb.append("<script src=\"plug-in/designer/task/UserTask.js\"></script>");
			sb.append("<script src=\"plug-in/designer/task/ManualTask.js\"></script>");
			sb.append("<script src=\"plug-in/designer/task/ServiceTask.js\"></script>");
			sb.append("<script src=\"plug-in/designer/gateway/ExclusiveGateway.js\"></script>");
			sb.append("<script src=\"plug-in/designer/gateway/ParallelGateway.js\"></script>");
			sb.append("<script src=\"plug-in/designer/boundaryevent/TimerBoundary.js\"></script>");
			sb.append("<script src=\"plug-in/designer/boundaryevent/ErrorBoundary.js\"></script>");
			sb.append("<script src=\"plug-in/designer/subprocess/CallActivity.js\"></script>");
			sb.append("<script src=\"plug-in/designer/task/ScriptTask.js\"></script>");
			sb.append("<script src=\"plug-in/designer/task/MailTask.js\"></script>");
			sb.append("<script src=\"plug-in/designer/task/ReceiveTask.js\"></script>");
			sb.append("<script src=\"plug-in/designer/task/BusinessRuleTask.js\"></script>");
			sb.append("<script src=\"plug-in/designer/designer.js\"></script>");
			sb.append("<script src=\"plug-in/designer/mydesigner.js\"></script>");

		}
		if (oConvertUtils.isIn("tools", types)) {
			sb.append(SysThemesUtil.getCommonTheme(sysThemesEnum));
			sb.append(SysThemesUtil.getLhgdialogTheme(sysThemesEnum));
			sb.append(SysThemesUtil.getBootstrapTabTheme(sysThemesEnum));
			sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/layer/layer.js\"></script>");
			//update--begin--author:scott---------- date:20180702 ----------- for: i18n前段国际化-----------------------------------
			sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/jquery-plugs/i18n/jquery.i18n.properties.js\"></script>");
			//update--end--author:scott---------- date:20180702 ----------- for: i18n前段国际化-------------------------------------
			sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/tools/curdtools.js\"></script>");
			sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/tools/easyuiextend.js\"></script>");
			sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/jquery-plugs/hftable/jquery-hftable.js\"></script>");
			sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/tools/json2.js\" ></script>");
		}
		if (oConvertUtils.isIn("toptip", types)) {
			sb.append("<link rel=\"stylesheet\" href=\""+basePath+"/plug-in/toptip/css/css.css\" type=\"text/css\"></link>");
			sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/toptip/manhua_msgTips.js\"></script>");
		}
		if (oConvertUtils.isIn("autocomplete", types)) {
			sb.append("<link rel=\"stylesheet\" href=\""+basePath+"/plug-in/jquery/jquery-autocomplete/jquery.autocomplete.css\" type=\"text/css\"></link>");
			sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/jquery/jquery-autocomplete/jquery.autocomplete.min.js\"></script>");
		}
		if (oConvertUtils.isIn("jeasyuiextensions", types)) {
			sb.append("<script src=\""+basePath+"/plug-in/jquery-extensions/release/jquery.jdirk.min.js\" type=\"text/javascript\"></script>");
			sb.append("<link href=\""+basePath+"/plug-in/jquery-extensions/icons/icon-all.css\" rel=\"stylesheet\" type=\"text/css\" />");
			sb.append("<link href=\""+basePath+"/plug-in/jquery-extensions/jeasyui-extensions/jeasyui.extensions.css\" rel=\"stylesheet\" type=\"text/css\" />");
			sb.append("<script src=\""+basePath+"/plug-in/jquery-extensions/jeasyui-extensions/jeasyui.extensions.js\" type=\"text/javascript\"></script>");
			sb.append("<script src=\""+basePath+"/plug-in/jquery-extensions/jeasyui-extensions/jeasyui.extensions.linkbutton.js\" type=\"text/javascript\"></script>");
			sb.append("<script src=\""+basePath+"/plug-in/jquery-extensions/jeasyui-extensions/jeasyui.extensions.menu.js\" type=\"text/javascript\"></script>");
			sb.append("<script src=\""+basePath+"/plug-in/jquery-extensions/jeasyui-extensions/jeasyui.extensions.panel.js\" type=\"text/javascript\"></script>");
			sb.append("<script src=\""+basePath+"/plug-in/jquery-extensions/jeasyui-extensions/jeasyui.extensions.window.js\" type=\"text/javascript\"></script>");
			sb.append("<script src=\""+basePath+"/plug-in/jquery-extensions/jeasyui-extensions/jeasyui.extensions.dialog.js\" type=\"text/javascript\"></script>");
			sb.append("<script src=\""+basePath+"/plug-in/jquery-extensions/jeasyui-extensions/jeasyui.extensions.datagrid.js\" type=\"text/javascript\"></script>");
		}
		if (oConvertUtils.isIn("ztree", types)) {
			sb.append("<link rel=\"stylesheet\" href=\""+basePath+"/plug-in/ztree/css/metroStyle.css\" type=\"text/css\"></link>");
			sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/ztree/js/jquery.ztree.core-3.5.min.js\"></script>");
			sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/ztree/js/jquery.ztree.excheck-3.5.min.js\"></script>");
		}
		//update--begin--author:zhoujf date:20180515 for:boostrap-table 标签封装
		if (oConvertUtils.isIn("bootstrap", types)) {
			sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/jquery/jquery-1.9.1.js\"></script>");
			sb.append("<link rel=\"stylesheet\" href=\""+basePath+"/plug-in/bootstrap3.3.5/css/bootstrap.min.css\" type=\"text/css\"></link>");
			sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/bootstrap3.3.5/js/bootstrap.min.js\"></script>");
			sb.append("<link rel=\"stylesheet\" href=\""+basePath+"/plug-in/bootstrap3.3.5/css/default.css\" type=\"text/css\"></link>");
		}
		if (oConvertUtils.isIn("bootstrap-table", types)) {
			sb.append("<link rel=\"stylesheet\" href=\""+basePath+"/plug-in/bootstrap-table/bootstrap-table.min.css\" type=\"text/css\"></link>");
			sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/bootstrap-table/bootstrap-table.js\"></script>");
			sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/bootstrap-table/locale/bootstrap-table-zh-CN.min.js\"></script>");
			sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/themes/bootstrap-ext/js/common.js\"></script>");
			sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/tools/dataformat.js\"></script>");
			sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/lhgDialog/lhgdialog.min.js?skin=metrole\"></script>");
			
			//update-begin-author:liushaoqian date:20180720 for:TASK #2981 【改进】命名修改
			sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/themes/bootstrap-ext/js/bootstrap-lhgdialog-curdtools.js\"></script>");
			//update-begin-author:liushaoqian date:20180720 for:TASK #2981 【改进】命名修改
			
			//update-begin-author:taoYan date:20180605 for:TASK #2743 bootstrap-table表单，列表按钮图标样式和ace，hplus保持统一
			sb.append("<link rel=\"stylesheet\" href=\""+basePath+"/plug-in/ace/css/common.css\" type=\"text/css\"></link>");
			sb.append("<link rel=\"stylesheet\" href=\""+basePath+"/plug-in/ace/css/font-awesome.css\" type=\"text/css\"></link>");
			//update-end-author:taoYan date:20180605 for:TASK #2743 bootstrap-table表单，列表按钮图标样式和ace，hplus保持统一
		}
		if (oConvertUtils.isIn("layer", types)) {
			sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/layer/layer.js\"></script>");
			sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/laydate/laydate.js\"></script>");
		}
		//update--end--author:zhoujf date:20180515 for:boostrap-table 标签封装
		//update-begin-author: taoYan date:20180705 for:ace-validform-webuploader-bootstrap-form-ueditor-uploadify标签封装
		if (oConvertUtils.isIn("aceform", types)) {
			sb.append("<link rel=\"stylesheet\" href=\""+basePath+"/online/template/ledefault/css/vendor.css\" type=\"text/css\"></link>");
			sb.append("<link rel=\"stylesheet\" href=\""+basePath+"/online/template/ledefault/css/bootstrap-theme.css\" type=\"text/css\"></link>");
			sb.append("<link rel=\"stylesheet\" href=\""+basePath+"/online/template/ledefault/css/bootstrap.css\" type=\"text/css\"></link>");
			sb.append("<link rel=\"stylesheet\" href=\""+basePath+"/online/template/ledefault/css/app.css\" type=\"text/css\"></link>");
			sb.append("<link rel=\"stylesheet\" href=\""+basePath+"/plug-in/Validform/css/metrole/style.css\" type=\"text/css\"></link>");
			sb.append("<link rel=\"stylesheet\" href=\""+basePath+"/plug-in/Validform/css/metrole/tablefrom.css\" type=\"text/css\"></link>");
			//easyui的使用经快速测试唯一用到就只有单表为树形列表时，需要用到combotree插件
			sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/easyui/jquery.easyui.min.1.3.2.js\"></script>");
			sb.append(StringUtil.replace("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/easyui/locale/{0}.js\"></script>", "{0}", lang));
			sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/tools/syUtil.js\"></script>");
			//一对多子表的选择文件弹框
			sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/lhgDialog/lhgdialog.min.js\"></script>");
			//大量js工具函数
			sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/tools/curdtools.js\"></script>");
		}
		if (oConvertUtils.isIn("validform", types)) {
			sb.append(StringUtil.replace("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/Validform/js/Validform_v5.3.1_min_{0}.js\"></script>", "{0}", lang));
			sb.append(StringUtil.replace("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/Validform/js/Validform_Datatype_{0}.js\"></script>", "{0}", lang));
			sb.append(StringUtil.replace("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/Validform/js/datatype_{0}.js\"></script>", "{0}", lang));
			sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/Validform/plugin/passwordStrength/passwordStrength-min.js\"></script>");
		}
		if (oConvertUtils.isIn("webuploader", types)) {
			sb.append("<link rel=\"stylesheet\" href=\""+basePath+"/plug-in/webuploader/custom.css\" type=\"text/css\"></link>");
			sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/webuploader/webuploader.min.js\"></script>");
		}
		if (oConvertUtils.isIn("bootstrap-form", types)) {
			sb.append("<link rel=\"stylesheet\" href=\""+basePath+"/plug-in/themes/bootstrap-ext/css/validform-ext.css\" type=\"text/css\"></link>");
			//icheck组件引用
			//update--begin--author:scott ------date:20180716 ------for: icheck样式采用h+自定义的样式------
			sb.append("<link rel=\"stylesheet\" href=\""+basePath+"/plug-in/hplus/css/plugins/iCheck/custom.css\" type=\"text/css\"></link>");
			sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/hplus/js/plugins/iCheck/icheck.min.js\"></script>");
			//update--end--author:scott ------ date:20180716 ------ for: icheck样式采用h+自定义的样式 -------
			//通用组件引用 
			sb.append("<link rel=\"stylesheet\" href=\""+basePath+"/plug-in/bootstrap3.3.5/css/default.css\" type=\"text/css\"></link>");
			sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/themes/bootstrap-ext/js/common.js\"></script>");
			//自定义form样式-一定要放在最后引入
			sb.append("<link rel=\"stylesheet\" href=\""+basePath+"/plug-in/bootstrap3.3.5/css/bootstrap-form.css\" type=\"text/css\"></link>");
		}
		if (oConvertUtils.isIn("ueditor", types)) {
			sb.append("<script type=\"text/javascript\" charset=\"utf-8\" src=\""+basePath+"/plug-in/ueditor/ueditor.config.js\"></script>");
			sb.append("<script type=\"text/javascript\" charset=\"utf-8\" src=\""+basePath+"/plug-in/ueditor/ueditor.all.min.js\"></script>");
		}
		if (oConvertUtils.isIn("uploadify", types)) {
			sb.append("<link rel=\"stylesheet\" href=\""+basePath+"/plug-in/uploadify/css/uploadify.css\" type=\"text/css\"></link>");
			sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/uploadify/jquery.uploadify-3.1.js\"></script>");
		}
		//update-end-author: taoYan date:20180705 for:ace-validform-webuploader-bootstrap-form-ueditor-uploadify标签封装
		types = null;
		this.putTagCache(sb);
		
		//update--begin--author:scott ------date:20180730 ------for: 判断引用plug-in/tools/curdtools.js的时候，是否同时引用了国际化js，没有的话则追加------
		if(sb.indexOf("plug-in/tools/curdtools.js")!=-1 && sb.indexOf("/plug-in/jquery-plugs/i18n/jquery.i18n.properties.js")==-1){
			sb.append("<script type=\"text/javascript\" src=\""+basePath+"/plug-in/jquery-plugs/i18n/jquery.i18n.properties.js\"></script>");
		}
		//update--end--author:scott ------date:20180730 ------for: 判断引用plug-in/tools/curdtools.js的时候，是否同时引用了国际化js，没有的话则追加------
		return sb;
	}


	public String toString() {
		String basePath = ResourceUtil.getBasePath();
		return new StringBuffer().append("BaseTag [type=").append(type)
				.append(",sysTheme=").append(SysThemesUtil.getSysTheme(ContextHolderUtils.getRequest()).getStyle())
				.append(",brower_type=").append(ContextHolderUtils.getSession().getAttribute("brower_type"))
				.append(",cssTheme=").append(cssTheme)
				.append(",basePath=").append(basePath)
				.append("]").toString();
	}
	
}
