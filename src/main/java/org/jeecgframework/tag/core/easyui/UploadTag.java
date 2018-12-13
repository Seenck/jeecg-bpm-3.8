package org.jeecgframework.tag.core.easyui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;

import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.MutiLangUtil;
import org.jeecgframework.core.util.SysThemesUtil;
import org.jeecgframework.tag.core.JeecgTag;

/**
 * 
 * 类描述：上传标签
 * 
 * 张代浩
 * @date： 日期：2012-12-7 时间：上午10:17:45
 * @version 1.0
 */
public class UploadTag extends JeecgTag {
	private static final long serialVersionUID = 1L;
	protected String id;// ID
	protected String uploader;//url
	protected String name;//控件名称
	protected String formData;//参数名称
	protected String extend="";//上传文件的扩展名	
	protected String buttonText="浏览";//按钮文本
	protected boolean multi=true;//是否多文件
	protected String queueID="filediv";//文件容器ID
	protected boolean dialog=true;//是否是弹出窗口模式
	protected String callback;
	protected boolean auto=false;//是否自动上传
	protected String onUploadSuccess;//上传成功处理函数
	protected boolean view=false;//生成查看删除链接
//  update-begin--Author:jb_longjb 龙金波  Date:20150318 for:新增formId属性，保持formData属性向前兼容
	protected String formId;//参数名称
	 //update--begin--author:zhangjiaqiang date:20170709 for:多字段上传
	private boolean outhtml = true;
	//update-begin-author:taoYan date:20180427 for:扩展onUploadStart 若该值存在走这个方法--
	private String onUploadStart;//上传开始处理函数
	//update-end-author:taoYan date:20180427 for:扩展onUploadStart 若该值存在走这个方法--
	
	private String height="18";//自定义上传按钮高度
	private String width="80";//自定义上传按钮宽度
	
	public boolean isOuthtml() {
		return outhtml;
	}
	public void setOuthtml(boolean outhtml) {
		this.outhtml = outhtml;
	}
	 //update--end--author:zhangjiaqiang date:20170709 for:多字段上传
	public String getFormId() {
		return formId;
	}
	public void setFormId(String formId) {
		this.formId = formId;
	}
//  update-end--Author:jb_longjb 龙金波  Date:20150318 for:新增formId属性，保持formData属性向前兼容
	
//  update-begin--Author:chenj 陈劲  Date:20160819 for:TASK #1318 【标签扩展】上传组件增加属性，上传文件大小设置
	private String fileSizeLimit = "15MB";//上传文件大小设置
	public String getFileSizeLimit() {
		return fileSizeLimit;
	}
	public void setFileSizeLimit(String fileSizeLimit) {
		this.fileSizeLimit = fileSizeLimit;
	}
//  update-end--Author:chenj 陈劲  Date:20160819 for:TASK #1318 【标签扩展】上传组件增加属性，上传文件大小设置


	public void setView(boolean view) {
		this.view = view;
	}
	public void setOnUploadSuccess(String onUploadSuccess) {
		this.onUploadSuccess = onUploadSuccess;
	}
	public void setAuto(boolean auto) {
		this.auto = auto;
	}
	public void setCallback(String callback) {
		this.callback = callback;
	}
	public void setDialog(boolean dialog) {
		this.dialog = dialog;
	}
	public void setQueueID(String queueID) {
		this.queueID = queueID;
	}
	public void setButtonText(String buttonText) {
		this.buttonText = buttonText;
	}
	public void setMulti(boolean multi) {
		this.multi = multi;
	}
	public void setUploader(String uploader) {
		this.uploader = uploader;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getOnUploadStart() {
		return onUploadStart;
	}
	public void setOnUploadStart(String onUploadStart) {
		this.onUploadStart = onUploadStart;
	}
	@SuppressWarnings("unchecked")
	public int doStartTag() throws JspTagException {
		 //update--begin--author:zhangjiaqiang date:20170709 for:多字段上传
		List<String> idList  = (List<String>) pageContext.getRequest().getAttribute("nameList");
		if(idList == null || idList.isEmpty()){
			idList = new ArrayList<String>();
		}
		idList.add(id);
		pageContext.getRequest().setAttribute("nameList", idList);
		 //update--end--author:zhangjiaqiang date:20170709 for:多字段上传
		return EVAL_PAGE;
	}
	public int doEndTag() throws JspTagException {
		JspWriter out = null;
		try {
			out = this.pageContext.getOut();
			out.print(end().toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				out.clear();
				out.close();
			} catch (Exception e2) {
			}
		}
		return EVAL_PAGE;
	}
	@SuppressWarnings("unchecked")
	public StringBuffer end() {
		//update-start--Author:yugwu  Date:20170828 for:TASK #2258 【优化系统】jeecg的jsp页面，采用标签方式，每次都生成html，很慢----
		StringBuffer sb = this.getTagCache();
		if(sb != null){
			return sb;
		}
		sb = new StringBuffer();
		//update-end--Author:yugwu  Date:20170828 for:TASK #2258 【优化系统】jeecg的jsp页面，采用标签方式，每次都生成html，很慢----
		if("pic".equals(extend))
		{
			extend="*.jpg;*.jpeg;*.png;*.gif;*.bmp;*.ico;*.tif";
		}
		if(extend.equals("office"))
		{
			extend="*.doc;*.docx;*.txt;*.ppt;*.xls;*.xlsx;*.html;*.htm";
		}
		//update-begin-author:taoYan date:20180427 for:扩展onUploadStart 若该值存在走这个方法--
		 //update--begin--author:zhangjiaqiang date:20170709 for:多字段上传
		if(outhtml){
			sb.append("<link rel=\"stylesheet\" href=\"plug-in/uploadify/css/uploadify.css\" type=\"text/css\"></link>");
			sb.append("<script type=\"text/javascript\" src=\"plug-in/uploadify/jquery.uploadify-3.1.js\"></script>");
			//update--begin--author:zhangjiaqiang date:20170731 for:兼容IE多文件上传
			sb.append("<script type=\"text/javascript\" src=\"plug-in/tools/Map.js\"></script>");
			//update--end--author:zhangjiaqiang date:20170731 for:兼容IE多文件上传
		}
		 //update--end--author:zhangjiaqiang date:20170709 for:多字段上传
		sb.append("<script type=\"text/javascript\">"
				+"var flag = false;"
				+"var fileitem=\"\";"
				+"var fileKey=\"\";"
				+"var serverMsg=\"\";"
				+"var m = new Map();"
				+ "$(function(){"
				+"$(\'#"+id+"\').uploadify({"
				+"buttonText:\'"+MutiLangUtil.getLang(buttonText)+"\',"
				+"auto:"+auto+","
				+"progressData:\'speed\'," 
				+"multi:"+multi+","
				+"height:"+height+","
				+"width:"+width+","
				+"overrideEvents:[\'onDialogClose\']," 
				+"fileTypeDesc:\'文件格式:\'," 
				+"queueID:\'"+queueID+"\',"
				+"fileTypeExts:\'"+extend+"\',"
				+"fileSizeLimit:\'"+fileSizeLimit+"\',"
				+"swf:\'plug-in/uploadify/uploadify.swf\',	"
				+"uploader:\'"+getUploader()			
						+"onUploadStart : function(file) { ");	
				if(onUploadStart==null || "".equals(onUploadStart)){
					if (formData!=null) {
						String[] paramnames=formData.split(",");				
						for (int i = 0; i < paramnames.length; i++) {
							String paramname=paramnames[i];
							//update--begin--author:zhangjiaqiang date:20170727 for:支持同名多个参数设置
							String fieldName = paramname;
							if(paramname.indexOf("_")> -1 ){
								fieldName = paramname.substring(0, paramname.indexOf("_"));
							}
							sb.append("var "+fieldName+"=$(\'#"+paramname+"\').val();");
							//update--end--author:zhangjiaqiang date:20170727 for:支持同名多个参数设置
						}				 
				        sb.append("$(\'#"+id+"\').uploadify(\"settings\", \"formData\", {");
				        for (int i = 0; i < paramnames.length; i++) {
							String paramname=paramnames[i];
							//update--begin--author:zhangjiaqiang date:20170727 for:支持同名多个参数设置
							if(paramname.indexOf("_")> -1 ){
								paramname = paramname.substring(0, paramname.indexOf("_"));
							}
							//update--end--author:zhangjiaqiang date:20170727 for:支持同名多个参数设置
							if (i==paramnames.length-1) {
								sb.append("'"+paramname+"':"+paramname+"");	
							}else{
								sb.append("'"+paramname+"':"+paramname+",");
							}
						}
				        sb.append("});");
//			          update-begin--Author:jb_longjb 龙金波  Date:20150318 for:用formId简化表单与附件同时提交的序列化问题
					}else if (formId!=null) {
						sb.append(" var o = {};");
	            		sb.append("    var _array = $('#"+formId+"').serializeArray();");
	            		sb.append("    $.each(_array, function() {");
	            		sb.append("        if (o[this.name]) {");
	            		sb.append("            if (!o[this.name].push) {");
	            		sb.append("                o[this.name] = [ o[this.name] ];");
	            		sb.append("            }");
	            		sb.append("            o[this.name].push(this.value || '');");
	            		sb.append("        } else {");
	            		sb.append("            o[this.name] = this.value || '';");
	            		sb.append("        }");
	            		sb.append("    });");
	            		sb.append("$(\'#"+id+"\').uploadify(\"settings\", \"formData\", o);");
					}
				}else{
					sb.append(this.onUploadStart+"(file);");
				}
				//update-end-author:taoYan date:20180427 for:扩展onUploadStart 若该值存在走这个方法--
//          update-end--Author:jb_longjb 龙金波  Date:20150318 for:简化表单与附件同时提交的序列化问题
		       sb.append("} ," 	          
				+"onQueueComplete : function(queueData) { ");
				if(dialog)
				{
				sb.append("var win = frameElement.api.opener;"  	  
	            +"win.reloadTable();"
	            +"win.tip(serverMsg);"
	            //update--begin--author:zhangjiaqiang Date:20170504 for:修订导入数据之后，loading弹出窗未关闭
	            +"if(subDlgIndex && $('#infoTable-loading')){"
				+"$('#infoTable-loading').hide();"
	            //update--begin--author:zhangjiaqiang date:20170714 for:修订多文件导入问题
				+"if(!subDlgIndex.closed)subDlgIndex.close();"
				 //update--end--author:zhangjiaqiang date:20170714 for:修订多文件导入问题
				+"}"
				 //update--end--author:zhangjiaqiang Date:20170504 for:修订导入数据之后，loading弹出窗未关闭
	            +"frameElement.api.close();");
				}
				else
				{
				  if(callback!=null)
				  sb.append(""+callback+"();");
				}
				if(view)
				{
				sb.append("$(\"#viewmsg\").html(m.toString());");
				sb.append("$(\"#fileKey\").val(fileKey);");
				}
				sb.append("},");
				//上传成功处理函数
				sb.append("onUploadSuccess : function(file, data, response) {");
				sb.append("var d=$.parseJSON(data);");
				if(view)
				{
				sb.append("var fileitem=\"<span id=\'\"+d.attributes.id+\"\'><a href=\'#\' onclick=openwindow(\'文件查看\',\'\"+d.attributes.viewhref+\"\',\'70%\',\'80%\') title=\'查看\'>\"+d.attributes.name+\"</a><img border=\'0\' onclick=confuploadify(\'\"+d.attributes.delurl+\"\',\'\"+d.attributes.id+\"\') title=\'删除\' src=\'plug-in/uploadify/img/uploadify-cancel.png\' widht=\'15\' height=\'15\'>&nbsp;&nbsp;</span>\";");
				//update-begin--author:scott-----date:20160511----for:火狐下上传问题,客户反馈--------
				sb.append(" m=new Map(); ");
				//update-begin--author:scott-----date:20160511----for:火狐下上传问题,客户反馈--------
				sb.append("m.put(d.attributes.id,fileitem);");
				sb.append("fileKey=d.attributes.fileKey;");
				}
				if(onUploadSuccess!=null)
				{
				sb.append(onUploadSuccess+"(d,file,response);");
				}
				sb.append("if(d.success){");
				sb.append("var win = frameElement.api.opener;");
//				sb.append("win.tip(d.msg);");
				sb.append("serverMsg = d.msg;");
				sb.append("}");
				sb.append("},");
				sb.append("onFallback : function(){tip(\"您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试\")},");
				sb.append("onSelectError : function(file, errorCode, errorMsg){");
				sb.append("switch(errorCode) {");
				sb.append("case -100:");
				sb.append("tip(\"上传的文件数量已经超出系统限制的\"+$(\'#"+id+"\').uploadify(\'settings\',\'queueSizeLimit\')+\"个文件！\");");
				sb.append("break;");
				sb.append("case -110:"
				+"tip(\"文件 [\"+file.name+\"] 大小超出系统限制的\"+$(\'#"+id+"\').uploadify(\'settings\',\'fileSizeLimit\')+\"大小！\");"
				+"break;"
				+"case -120:"
				+"tip(\"文件 [\"+file.name+\"] 大小异常！\");"
				+"break;"
				+"case -130:"
				+"tip(\"文件 [\"+file.name+\"] 类型不正确！\");"
				+"break;"
				+"}");
		       sb.append("},"
				+"onUploadProgress : function(file, bytesUploaded, bytesTotal, totalBytesUploaded, totalBytesTotal) { "
				//+"tip('<span>文件上传成功:'+totalBytesUploaded/1024 + ' KB 已上传 ,总数' + totalBytesTotal/1024 + ' KB.</span>');"  	  	             
				+"}"
	   			+"});"
				+"});");
		      //update--begin--author:zhangjiaqiang date:20170709 for:多字段上传
		       if(outhtml){
		    	   List<String> idList  = (List<String>) pageContext.getRequest().getAttribute("nameList");
		    	   sb.append("function upload() {");
		    	   for (int i = 0; i < idList.size(); i++) {
		    		   String tempId = idList.get(i);
		    		   sb.append("	$(\'#"+tempId+"\').uploadify('upload', '*');");
					
		    	   }
		    	   sb.append("return flag;");
		   			sb.append("}");
		   			sb.append("function cancel() {");
		   		 for (int i = 0; i < idList.size(); i++) {
		    		   String tempId = idList.get(i);
		   			sb.append("$(\'#"+tempId+"\').uploadify('cancel', '*');");	
		   		 }
		   			sb.append("}");		
		       }
				sb.append("</script>");	
				//update--end--author:zhangjiaqiang date:20170709 for:多字段上传		
		       sb.append("<span id=\""+id+"span\"><input type=\"file\" name=\""+name+"\" id=\""+id+"\" /></span>");
		       if(view)
		       {
		       sb.append("<span id=\"viewmsg\"></span>");
		       sb.append("<input type=\"hidden\" name=\"fileKey\" id=\"fileKey\" />");
		       }
				//update-start--Author:yugwu  Date:20170828 for:TASK #2258 【优化系统】jeecg的jsp页面，采用标签方式，每次都生成html，很慢----
				this.putTagCache(sb);
				//update-end--Author:yugwu  Date:20170828 for:TASK #2258 【优化系统】jeecg的jsp页面，采用标签方式，每次都生成html，很慢----
		return sb;
	}
	
	/**
	 * 获取上传路径,修改jsessionid传不到后台的bug jueyue --- 20130916
	 * @return
	 */
	private String getUploader() {
		return uploader+"&sessionId="+pageContext.getSession().getId()+"',";
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public void setFormData(String formData) {
		this.formData = formData;
	}
	public void setExtend(String extend) {
		this.extend = extend;
	}
	
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UploadTag [id=").append(id).append(", uploader=")
				.append(uploader).append(", name=").append(name)
				.append(", formData=").append(formData).append(", extend=")
				.append(extend).append(", buttonText=").append(buttonText)
				.append(", multi=").append(multi).append(", queueID=")
				.append(queueID).append(", dialog=").append(dialog)
				.append(", callback=").append(callback).append(", auto=")
				.append(auto).append(", onUploadSuccess=")
				.append(onUploadSuccess).append(", view=").append(view)
				.append(", formId=").append(formId).append(", outhtml=")
				.append(outhtml).append(", fileSizeLimit=").append(fileSizeLimit)
				.append(",sysTheme=").append(SysThemesUtil.getSysTheme(ContextHolderUtils.getRequest()).getStyle())
				.append(",brower_type=").append(ContextHolderUtils.getSession().getAttribute("brower_type"))
				.append(",height=").append(height)
				.append(",width=").append(width)
				.append("]");
		return builder.toString();
	}
	
}
