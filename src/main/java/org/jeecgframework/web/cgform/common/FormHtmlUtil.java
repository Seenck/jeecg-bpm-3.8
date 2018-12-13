package org.jeecgframework.web.cgform.common;

import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.cgform.entity.config.CgFormFieldEntity;
import org.jeecgframework.web.cgform.service.impl.config.util.ExtendJsonConvert;
/**   
 * @author 张代浩
 * @date 2013-08-11 09:47:30
 * @version V1.0   
 */
public class FormHtmlUtil {
	
	
	/**
     *根据CgFormFieldEntity表属性配置，返回表单HTML代码
     */
    public static String getFormHTML(CgFormFieldEntity cgFormFieldEntity,String tableName){
    	String html="";
        if(cgFormFieldEntity.getShowType().equals("text")){
        	//update--begin-------author:zhoujf------date:20180614----for:word 布局模板唯一值校验问题---------------
        	 if("only".equalsIgnoreCase(cgFormFieldEntity.getFieldValidType())){
        		 html=getTextOnlyFormHtml(cgFormFieldEntity,tableName);
        	 }else{
        		 html=getTextFormHtml(cgFormFieldEntity);
        	 }
        	//update--begin-------author:zhoujf------date:20180614----for:word 布局模板唯一值校验问题---------------
        }else if(cgFormFieldEntity.getShowType().equals("password")){
        	html=getPwdFormHtml(cgFormFieldEntity);
        }else if(cgFormFieldEntity.getShowType().equals("radio")){
        	html=getRadioFormHtml(cgFormFieldEntity);
        }else if(cgFormFieldEntity.getShowType().equals("checkbox")){
        	html=getCheckboxFormHtml(cgFormFieldEntity);
        }else if(cgFormFieldEntity.getShowType().equals("list")){
        	html=getListFormHtml(cgFormFieldEntity);
        }else if(cgFormFieldEntity.getShowType().equals("date")){
        	html=getDateFormHtml(cgFormFieldEntity);
        }else if(cgFormFieldEntity.getShowType().equals("datetime")){
        	html=getDatetimeFormHtml(cgFormFieldEntity);
        }else if(cgFormFieldEntity.getShowType().equals("file")){
        	html=getFileFormHtml(cgFormFieldEntity);
        }else if(cgFormFieldEntity.getShowType().equals("textarea")){
        	html=getTextAreaFormHtml(cgFormFieldEntity);
        }else if(cgFormFieldEntity.getShowType().equals("popup")){
        	html=getPopupFormHtml(cgFormFieldEntity);
        }
        else {
        	html=getTextFormHtml(cgFormFieldEntity);
        }
        return html;
    }
    /**
     * 返回textarea的表单html
     * @param cgFormFieldEntity
     * @return style="width: 300px" class="inputxt" rows="6"
     */
    private static String getTextAreaFormHtml(
			CgFormFieldEntity cgFormFieldEntity) {
    	StringBuilder html = new StringBuilder("");
    	//update-begin--Author:zhoujf  Date:20180802 for：TASK #3036 【Online表单模板】表单模板不支持填值规则、校验必填、扩展参数
    	//update-begin--Author:zhoujf  Date:20151218 for：online表单模板textarea优化--------------------
    	 html.append("<textarea rows=\"6\" ");
    	 if(StringUtils.isNotEmpty(cgFormFieldEntity.getExtendJson())){
        	 html.append(" "+ExtendJsonConvert.json2Html(cgFormFieldEntity.getExtendJson())+" ");;
         }
    	 if(cgFormFieldEntity.getFieldLength()!=null&&cgFormFieldEntity.getFieldLength()>0){
       	  	html.append("style=\"width:").append(cgFormFieldEntity.getFieldLength()).append("px\" ");
         }
    	//update-end--Author:zhoujf  Date:20151218 for：online表单模板textarea优化--------------------
    	 html.append("id=\"").append(cgFormFieldEntity.getFieldName()).append("\" ");
         html.append("name=\"").append(cgFormFieldEntity.getFieldName()).append("\" ");
         if("Y".equals(cgFormFieldEntity.getFieldMustInput())){
	   	  	  //校验必填
	   	  	  html.append("ignore=\"checked\" ");
	   	  }else{
	   	        if("Y".equals(cgFormFieldEntity.getIsNull())){
	   	        	html.append("ignore=\"ignore\" ");
	   	        }else{
	   	        	html.append("ignore=\"checked\" ");
	   	        }
	   	  }
         if(cgFormFieldEntity.getFieldValidType()!=null&&cgFormFieldEntity.getFieldValidType().length()>0){
       	  html.append("datatype=\"").append(cgFormFieldEntity.getFieldValidType()).append("\" ");
         }else{
   		  html.append("datatype=\"*\" ");
   	  }
         html.append("\\>");
//         html.append("\\${").append(cgFormFieldEntity.getFieldName()).append("?if_exists?html}</textarea> ");
         html.append("\\${data\\['"+cgFormFieldEntity.getTable().getTableName()+"'\\]\\['").append(cgFormFieldEntity.getFieldName()).append("'\\]?if_exists?html}</textarea> ");
       //update-begin--Author:zhoujf  Date:20180802 for：TASK #3036 【Online表单模板】表单模板不支持填值规则、校验必填、扩展参数
         return html.toString();
	}

	/**
     *返回text类型的表单html
     */
    private static String getTextFormHtml(CgFormFieldEntity cgFormFieldEntity)
    {
      StringBuilder html = new StringBuilder("");
      html.append("<input type=\"text\" ");
      html.append("id=\"").append(cgFormFieldEntity.getFieldName()).append("\" ");
      html.append("name=\"").append(cgFormFieldEntity.getFieldName()).append("\" ");
    //update-begin--Author:zhoujf  Date:20180802 for：TASK #3036 【Online表单模板】表单模板不支持填值规则、校验必填、扩展参数
      if(StringUtils.isNotEmpty(cgFormFieldEntity.getExtendJson())){
     	 html.append(" "+ExtendJsonConvert.json2Html(cgFormFieldEntity.getExtendJson())+" ");;
     }
      if(cgFormFieldEntity.getFieldLength()!=null&&cgFormFieldEntity.getFieldLength()>0){
    	  html.append("style=\"width:").append(cgFormFieldEntity.getFieldLength()).append("px\" ");
      }
//      html.append("value=\"\\${").append(cgFormFieldEntity.getFieldName()).append("?if_exists?html}\" ");
      html.append("value=\"\\${data\\['"+cgFormFieldEntity.getTable().getTableName()+"'\\]\\['").append(cgFormFieldEntity.getFieldName()).append("'\\]?if_exists?html}\" ");
      if("Y".equals(cgFormFieldEntity.getFieldMustInput())){
	  	  //校验必填
	  	  html.append("ignore=\"checked\" ");
	  }else{
	        if("Y".equals(cgFormFieldEntity.getIsNull())){
	        	html.append("ignore=\"ignore\" ");
	        }else{
	        	html.append("ignore=\"checked\" ");
	        }
	  }
    //update-end--Author:zhoujf  Date:20180802 for：TASK #3036 【Online表单模板】表单模板不支持填值规则、校验必填、扩展参数
      if(cgFormFieldEntity.getFieldValidType()!=null&&cgFormFieldEntity.getFieldValidType().length()>0){
    	  html.append("datatype=\"").append(cgFormFieldEntity.getFieldValidType()).append("\" ");
      }else{
    	  if("int".equals(cgFormFieldEntity.getType())){
    		  html.append("datatype=\"n\" ");
    	  }else if("double".equals(cgFormFieldEntity.getType())){
    		  html.append("datatype=\"\\/^(-?\\\\d+)(\\\\.\\\\d+)?\\$\\/\" ");
    	  }else{
    		  html.append("datatype=\"*\" ");
    	  }
      }
      html.append("\\/>");
      return html.toString();
    }
    
  //update--begin-------author:zhoujf------date:20180614----for:word 布局模板唯一值校验问题---------------
    /**
     *返回text类型的表单html(唯一值校验)
     */
    private static String getTextOnlyFormHtml(CgFormFieldEntity cgFormFieldEntity,String tableName)
    {
    	StringBuilder html = new StringBuilder("");
        html.append("<input type=\"text\" ");
        html.append("id=\"").append(cgFormFieldEntity.getFieldName()).append("\" ");
        html.append("name=\"").append(cgFormFieldEntity.getFieldName()).append("\" ");
      //update-begin--Author:zhoujf  Date:20180802 for：TASK #3036 【Online表单模板】表单模板不支持填值规则、校验必填、扩展参数
        if(StringUtils.isNotEmpty(cgFormFieldEntity.getExtendJson())){
        	 html.append(" "+ExtendJsonConvert.json2Html(cgFormFieldEntity.getExtendJson())+" ");;
        }
        if(cgFormFieldEntity.getFieldLength()!=null&&cgFormFieldEntity.getFieldLength()>0){
      	  html.append("style=\"width:").append(cgFormFieldEntity.getFieldLength()).append("px\" ");
        }
//        html.append("value=\"\\${").append(cgFormFieldEntity.getFieldName()).append("?if_exists?html}\" ");
        html.append("value=\"\\${data\\['"+cgFormFieldEntity.getTable().getTableName()+"'\\]\\['").append(cgFormFieldEntity.getFieldName()).append("'\\]?if_exists?html}\" ");
        if("Y".equals(cgFormFieldEntity.getFieldMustInput())){
      	  //校验必填
      	  html.append("ignore=\"checked\" ");
        }else{
	        if("Y".equals(cgFormFieldEntity.getIsNull())){
	        	html.append("ignore=\"ignore\" ");
	        }else{
	        	html.append("ignore=\"checked\" ");
	        }
        }
        //update-end--Author:zhoujf  Date:20180802 for：TASK #3036 【Online表单模板】表单模板不支持填值规则、校验必填、扩展参数
        html.append("validtype=\"").append(tableName).append(",").append(cgFormFieldEntity.getFieldName()).append(",id\" ");
  	  	html.append("datatype=\"*\" ");
        html.append("\\/>");
        return html.toString();
    }
    
  //update--begin-------author:zhoujf------date:20180614----for:word 布局模板唯一值校验问题---------------
    /**
     *返回password类型的表单html
     */
    private static String getPwdFormHtml(CgFormFieldEntity cgFormFieldEntity)
    {
      StringBuilder html = new StringBuilder("");
      html.append("<input type=\"password\" ");
      html.append("id=\"").append(cgFormFieldEntity.getFieldName()).append("\" ");
      html.append("name=\"").append(cgFormFieldEntity.getFieldName()).append("\" ");
    //update-begin--Author:zhoujf  Date:20180802 for：TASK #3036 【Online表单模板】表单模板不支持填值规则、校验必填、扩展参数
      if(StringUtils.isNotEmpty(cgFormFieldEntity.getExtendJson())){
     	 html.append(" "+ExtendJsonConvert.json2Html(cgFormFieldEntity.getExtendJson())+" ");;
      }
      if(cgFormFieldEntity.getFieldLength()!=null&&cgFormFieldEntity.getFieldLength()>0){
    	  html.append("style=\"width:").append(cgFormFieldEntity.getFieldLength()).append("px\" ");
      }
//      html.append("value=\"\\${").append(cgFormFieldEntity.getFieldName()).append("?if_exists?html}\" ");
      html.append("value=\"\\${data\\['"+cgFormFieldEntity.getTable().getTableName()+"'\\]\\['").append(cgFormFieldEntity.getFieldName()).append("'\\]?if_exists?html}\" ");
      if("Y".equals(cgFormFieldEntity.getFieldMustInput())){
	  	  //校验必填
	  	  html.append("ignore=\"checked\" ");
	  }else{
	        if("Y".equals(cgFormFieldEntity.getIsNull())){
	        	html.append("ignore=\"ignore\" ");
	        }else{
	        	html.append("ignore=\"checked\" ");
	        }
	  }
    //update-end--Author:zhoujf  Date:20180802 for：TASK #3036 【Online表单模板】表单模板不支持填值规则、校验必填、扩展参数
      if(cgFormFieldEntity.getFieldValidType()!=null&&cgFormFieldEntity.getFieldValidType().length()>0){
    	  html.append("datatype=\"").append(cgFormFieldEntity.getFieldValidType()).append("\" ");
      }else{
		  html.append("datatype=\"*\" ");
	  }
      html.append("\\/>");
      return html.toString();
    }
    
    
    /**
     *返回radio类型的表单html  
     */
    private static String getRadioFormHtml(CgFormFieldEntity cgFormFieldEntity)
    {
    	
    	if(StringUtil.isEmpty(cgFormFieldEntity.getDictField())){
      	  return getTextFormHtml(cgFormFieldEntity);
        }else{
  	      StringBuilder html = new StringBuilder("");
  	      html.append("<@DictData name=\""+cgFormFieldEntity.getDictField()+"\"");
  	      if(!StringUtil.isEmpty(cgFormFieldEntity.getDictTable())){
  	    	html.append(" tablename=\""+cgFormFieldEntity.getDictTable()+"\"");
  	      }
  	      html.append(" var=\"dictDataList\">");
  	      html.append("<#list dictDataList as dictdata>");
  	      html.append(" <input type=\"radio\" value=\"\\${dictdata.typecode?if_exists?html}\" name=\""+cgFormFieldEntity.getFieldName()+"\" ");
  	      //update-begin--Author:zhoujf  Date:20180802 for：TASK #3036 【Online表单模板】表单模板不支持填值规则、校验必填、扩展参数
//  	      html.append("<#if dictdata.typecode=='\\${").append(cgFormFieldEntity.getFieldName()).append("?if_exists?html}'>");
  	      html.append("<#if dictdata.typecode==\"\\${data\\['"+cgFormFieldEntity.getTable().getTableName()+"'\\]\\['").append(cgFormFieldEntity.getFieldName()).append("'\\]?if_exists?html}\">");
  	      //update-end--Author:zhoujf  Date:20180802 for：TASK #3036 【Online表单模板】表单模板不支持填值规则、校验必填、扩展参数
  	      html.append(" checked=\"true\" ");
  	      html.append("</#if> ");
  	      html.append(">");
  	      html.append("\\${dictdata.typename?if_exists?html}");
  	      html.append("</#list> ");
  	      html.append("</@DictData> ");
  	      return html.toString();
        }
    }
    
    
    /**
     *返回checkbox类型的表单html ${data['${po.field_name}']?if_exists?html}
     */
    private static String getCheckboxFormHtml(CgFormFieldEntity cgFormFieldEntity)
    {
    	  if(StringUtil.isEmpty(cgFormFieldEntity.getDictField())){
        	  return getTextFormHtml(cgFormFieldEntity);
          }else{
        	 
    	      StringBuilder html = new StringBuilder("");
    	      //update-start--Author: jg_huangxg  Date:20160816 for：TASK #1266 【平台升级】word布局自定义模板，多选checkbox的时候，编辑的时候没有赋值
    	      //update-begin--Author:zhoujf  Date:20180802 for：TASK #3036 【Online表单模板】表单模板不支持填值规则、校验必填、扩展参数
//    	      html.append("<#assign checkboxstr>\\${").append(cgFormFieldEntity.getFieldName()).append("?if_exists?html}</#assign>");
    	      html.append("<#assign checkboxstr>\\${data\\['"+cgFormFieldEntity.getTable().getTableName()+"'\\]\\['").append(cgFormFieldEntity.getFieldName()).append("'\\]?if_exists?html}</#assign>");
    	      //update-end--Author:zhoujf  Date:20180802 for：TASK #3036 【Online表单模板】表单模板不支持填值规则、校验必填、扩展参数
    	      //update-end--Author: jg_huangxg  Date:20160816 for：TASK #1266 【平台升级】word布局自定义模板，多选checkbox的时候，编辑的时候没有赋值
    	      html.append("<#assign checkboxlist=checkboxstr?split(\",\")> ");
    	      html.append("<@DictData name=\""+cgFormFieldEntity.getDictField()+"\"");
      	      if(!StringUtil.isEmpty(cgFormFieldEntity.getDictTable())){
      	    	html.append(" tablename=\""+cgFormFieldEntity.getDictTable()+"\"");
      	      }
      	      html.append(" var=\"dictDataList\">");
    	      html.append("<#list dictDataList as dictdata>");
    	      html.append(" <input type=\"checkbox\" value=\"\\${dictdata.typecode?if_exists?html}\" name=\""+cgFormFieldEntity.getFieldName()+"\" ");
    	      html.append("<#list checkboxlist as x >");
    	      html.append("<#if dictdata.typecode=='\\${x?if_exists?html}'>");
    	      html.append(" checked=\"true\" ");
    	      html.append("</#if> ");
    	      html.append("</#list> ");
    	      html.append(">");
    	      html.append("\\${dictdata.typename?if_exists?html}");
    	      html.append("</#list> ");
    	      html.append("</@DictData> ");
    	      return html.toString();
          }
    }
    
    
    /**
     *返回list类型的表单html
     */
    private static String getListFormHtml(CgFormFieldEntity cgFormFieldEntity)
    {
      if(StringUtil.isEmpty(cgFormFieldEntity.getDictField())){
    	  return getTextFormHtml(cgFormFieldEntity);
      }else{
	      StringBuilder html = new StringBuilder("");
	      html.append("<@DictData name=\""+cgFormFieldEntity.getDictField()+"\"");
	      if(!StringUtil.isEmpty(cgFormFieldEntity.getDictText())){
  	    	html.append(" text=\""+cgFormFieldEntity.getDictText()+"\"");
  	      }
  	      if(!StringUtil.isEmpty(cgFormFieldEntity.getDictTable())){
  	    	html.append(" tablename=\""+cgFormFieldEntity.getDictTable()+"\"");
  	      }
  	      html.append(" var=\"dictDataList\">");
	      html.append("<select name=\""+cgFormFieldEntity.getFieldName()+"\" id=\""+cgFormFieldEntity.getFieldName()+"\"> ");
	      html.append("<#list dictDataList as dictdata>");
	      html.append(" <option value=\"\\${dictdata.typecode?if_exists?html}\" ");
	    //update-begin--Author:zhoujf  Date:20180802 for：TASK #3036 【Online表单模板】表单模板不支持填值规则、校验必填、扩展参数
//	      html.append("<#if dictdata.typecode=='\\${").append(cgFormFieldEntity.getFieldName()).append("?if_exists?html}'>");
	      html.append("<#if dictdata.typecode==\"\\${data\\['"+cgFormFieldEntity.getTable().getTableName()+"'\\]\\['").append(cgFormFieldEntity.getFieldName()).append("'\\]?if_exists?html}\">");
	    //update-end--Author:zhoujf  Date:20180802 for：TASK #3036 【Online表单模板】表单模板不支持填值规则、校验必填、扩展参数
	      html.append("</#if> ");
	      html.append(">");
	      html.append("\\${dictdata.typename?if_exists?html}");
	      html.append("</option> ");
	      html.append("</#list> ");
	      html.append("</select>");
	      html.append("</@DictData> ");
	      return html.toString();
      }
    }
    
    
    /**
     *返回date类型的表单html
     */
    private static String getDateFormHtml(CgFormFieldEntity cgFormFieldEntity)
    {
      StringBuilder html = new StringBuilder("");
      html.append("<input type=\"text\" ");
      html.append("id=\"").append(cgFormFieldEntity.getFieldName()).append("\" ");
      html.append("name=\"").append(cgFormFieldEntity.getFieldName()).append("\" ");
    //update-begin--Author:zhoujf  Date:20180802 for：TASK #3036 【Online表单模板】表单模板不支持填值规则、校验必填、扩展参数
      if(StringUtils.isNotEmpty(cgFormFieldEntity.getExtendJson())){
    	  html.append(" "+ExtendJsonConvert.json2Html(cgFormFieldEntity.getExtendJson())+" ");;
      }
      html.append("class=\"Wdate\" ");
      html.append("onClick=\"WdatePicker()\" ");
      if(cgFormFieldEntity.getFieldLength()!=null&&cgFormFieldEntity.getFieldLength()>0){
    	  html.append("style=\"width:").append(cgFormFieldEntity.getFieldLength()).append("px\" ");
      }
//      html.append("value=\"\\${").append(cgFormFieldEntity.getFieldName()).append("?if_exists?html}\" ");
      html.append("value=\"\\${data\\['"+cgFormFieldEntity.getTable().getTableName()+"'\\]\\['").append(cgFormFieldEntity.getFieldName()).append("'\\]?if_exists?html}\" ");
      if("Y".equals(cgFormFieldEntity.getFieldMustInput())){
	  	  //校验必填
	  	  html.append("ignore=\"checked\" ");
	  }else{
	        if("Y".equals(cgFormFieldEntity.getIsNull())){
	        	html.append("ignore=\"ignore\" ");
	        }else{
	        	html.append("ignore=\"checked\" ");
	        }
	  }
    //update-end--Author:zhoujf  Date:20180802 for：TASK #3036 【Online表单模板】表单模板不支持填值规则、校验必填、扩展参数
      if(cgFormFieldEntity.getFieldValidType()!=null&&cgFormFieldEntity.getFieldValidType().length()>0){
    	  html.append("datatype=\"").append(cgFormFieldEntity.getFieldValidType()).append("\" ");
      }else{
		  html.append("datatype=\"*\" ");
	  }
      html.append("\\/>");
      return html.toString();
    }
    
    /**
     *返回datetime类型的表单html
     */
    private static String getDatetimeFormHtml(CgFormFieldEntity cgFormFieldEntity)
    {
      StringBuilder html = new StringBuilder("");
      html.append("<input type=\"text\" ");
      html.append("id=\"").append(cgFormFieldEntity.getFieldName()).append("\" ");
      html.append("name=\"").append(cgFormFieldEntity.getFieldName()).append("\" ");
    //update-begin--Author:zhoujf  Date:20180802 for：TASK #3036 【Online表单模板】表单模板不支持填值规则、校验必填、扩展参数
      if(StringUtils.isNotEmpty(cgFormFieldEntity.getExtendJson())){
     	 html.append(" "+ExtendJsonConvert.json2Html(cgFormFieldEntity.getExtendJson())+" ");;
     }
      html.append("class=\"Wdate\" ");
      html.append("onClick=\"WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})\" ");
      if(cgFormFieldEntity.getFieldLength()!=null&&cgFormFieldEntity.getFieldLength()>0){
    	  html.append("style=\"width:").append(cgFormFieldEntity.getFieldLength()).append("px\" ");
      }
//      html.append("value=\"\\${").append(cgFormFieldEntity.getFieldName()).append("?if_exists?html}\" ");
      html.append("value=\"\\${data\\['"+cgFormFieldEntity.getTable().getTableName()+"'\\]\\['").append(cgFormFieldEntity.getFieldName()).append("'\\]?if_exists?html}\" ");
      if("Y".equals(cgFormFieldEntity.getFieldMustInput())){
	  	  //校验必填
	  	  html.append("ignore=\"checked\" ");
	  }else{
	        if("Y".equals(cgFormFieldEntity.getIsNull())){
	        	html.append("ignore=\"ignore\" ");
	        }else{
	        	html.append("ignore=\"checked\" ");
	        }
	  }
    //update-end--Author:zhoujf  Date:20180802 for：TASK #3036 【Online表单模板】表单模板不支持填值规则、校验必填、扩展参数
      if(cgFormFieldEntity.getFieldValidType()!=null&&cgFormFieldEntity.getFieldValidType().length()>0){
    	  html.append("datatype=\"").append(cgFormFieldEntity.getFieldValidType()).append("\" ");
      }else{
		  html.append("datatype=\"*\" ");
	  }
      html.append("\\/>");
      return html.toString();
    }
    
    /**
     *返回file类型的表单html
     */
	private static String getFileFormHtml(CgFormFieldEntity cgFormFieldEntity){
    	StringBuilder html = new StringBuilder("");

    	//update-start--Author: jg_huangxg  Date:20160816 for：TASK #1267 【平台升级】word布局自定义模板，上传组件，多文件上传功能
//    	html.append("<link rel=\"stylesheet\" href=\"plug-in/uploadify/css/uploadify.css\" type=\"text/css\"></link>");
//    	html.append("<script type=\"text/javascript\" src=\"plug-in/uploadify/jquery.uploadify-3.1.js\"></script>");
    	//update-start--Author: zhoujf  Date:20170524 for：TASK #2014 【online表单】online表单 模板配置 basePath问题 word模板文件上传问题
    	html.append("<table>");
    	html.append("<#list filesList as fileB>");
    	html.append("<tr style=\"height:34px;\">");
    	html.append("<td>\\${fileB['title']}</td>");
    	html.append("<td><a href=\"commonController.do?viewFile&fileid=\\${fileB['fileKey']}&subclassname=org.jeecgframework.web.cgform.entity.upload.CgUploadEntity\" title=\"下载\">下载</a></td>");
    	html.append("<td><a href=\"javascript:void(0);\" onclick=\"openwindow('预览','commonController.do?openViewFile&fileid=\\${fileB['fileKey']}&subclassname=org.jeecgframework.web.cgform.entity.upload.CgUploadEntity','fList',700,500)\">预览</a></td>");
    	html.append("<td><a href=\"javascript:void(0)\" class=\"jeecgDetail\" onclick=\"del('cgUploadController.do?delFile&id=\\${fileB['fileKey']}',this)\">删除</a></td>");
    	html.append("</tr></#list></table>");
    	html.append("<div class=\"form jeecgDetail\">");
    	html.append("<script type=\"text/javascript\">");
    	html.append("var serverMsg=\"\";");
    	html.append("var m = new Map();");
    	html.append("\\$(function(){\\$('#").append(cgFormFieldEntity.getFieldName()).append("').uploadify(");
    	html.append("{buttonText:'添加文件',auto:false,progressData:'speed',multi:true,height:25,overrideEvents:['onDialogClose'],fileTypeDesc:'文件格式:',");
    	html.append("queueID:'filediv_").append(cgFormFieldEntity.getFieldName()).append("',");
    	//update-begin----author:scott -- date:20170317 -- for:配置rar或者zip的时候,点击上传按钮之后要过10多秒才弹出文件选择框，采用方案不做上传类型限制--
    	//html.append("fileTypeExts:'*.rar;*.zip;*.doc;*.docx;*.txt;*.ppt;*.xls;*.xlsx;*.html;*.htm;*.pdf;*.jpg;*.gif;*.png',");
    	//update-end----author:scott -- date:20170317 -- for:配置rar或者zip的时候,点击上传按钮之后要过10多秒才弹出文件选择框，采用方案不做上传类型限制--
    	html.append("fileSizeLimit:'15MB',swf:'\\${basePath}/plug-in/uploadify/uploadify.swf',");
    	html.append("uploader:'\\${basePath}/cgUploadController.do?saveFiles&jsessionid='+\\$(\"#sessionUID\").val()+'',");
    	html.append("onUploadStart : function(file) { ");
    	html.append("var cgFormId=\\$(\"input[name='id']\").val();");
    	html.append("\\$('#").append(cgFormFieldEntity.getFieldName()).append("').uploadify(\"settings\", \"formData\", {'cgFormId':cgFormId,'cgFormName':'").append(cgFormFieldEntity.getTable().getTableName()).append("','cgFormField':'").append(cgFormFieldEntity.getFieldName()).append("'});} ,");
    	html.append("onQueueComplete : function(queueData) {var win = frameElement.api.opener;win.reloadTable();win.tip(serverMsg);frameElement.api.close();},");
    	html.append("onUploadSuccess : function(file, data, response) {var d=\\$.parseJSON(data);if(d.success){var win = frameElement.api.opener;serverMsg = d.msg;}},onFallback : function(){tip(\"您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试\")},onSelectError : function(file, errorCode, errorMsg){switch(errorCode) {case -100:tip(\"上传的文件数量已经超出系统限制的\"+\\$('#").append(cgFormFieldEntity.getFieldName()).append("').uploadify('settings','queueSizeLimit')+\"个文件！\");break;case -110:tip(\"文件 [\"+file.name+\"] 大小超出系统限制的\"+\\$('#").append(cgFormFieldEntity.getFieldName()).append("').uploadify('settings','fileSizeLimit')+\"大小！\");break;case -120:tip(\"文件 [\"+file.name+\"] 大小异常！\");break;case -130:tip(\"文件 [\"+file.name+\"] 类型不正确！\");break;}},");
    	html.append("onUploadProgress : function(file, bytesUploaded, bytesTotal, totalBytesUploaded, totalBytesTotal) { }});});");
    	html.append("</script><span id=\"file_uploadspan\"><input type=\"file\" name=\"").append(cgFormFieldEntity.getFieldName()).append("\" id=\"").append(cgFormFieldEntity.getFieldName()).append("\" /></span>");
    	html.append("</div><div class=\"form\" id=\"filediv_").append(cgFormFieldEntity.getFieldName()).append("\"> </div>");
    	
    	html.append("<script type=\"text/javascript\">");
//    	html.append("function uploadFile(data){");
//    	html.append("if(!\\$(\"input[name='id']\").val()){");
//    	html.append("if(data.obj != null && data.obj != 'undefined'){\\$(\"input[name='id']\").val(data.obj.id);}}");
//    	html.append("if(\\$(\".uploadify-queue-item\").length > 0){upload();}else{if (neibuClickFlag){alert(data.msg); neibuClickFlag = false;}else {var win = frameElement.api.opener; win.reloadTable(); win.tip(data.msg); frameElement.api.close();}}}");
    	html.append("function upload() {\\$('#").append(cgFormFieldEntity.getFieldName()).append("').uploadify('upload', '\\*');}");
    	html.append("function cancel() {\\$('#").append(cgFormFieldEntity.getFieldName()).append("').uploadify('cancel', '\\*');}");
//    	html.append("var neibuClickFlag = false;function neibuClick() {neibuClickFlag = true;\\$('#btn_sub').trigger('click');}");
    	html.append("</script>");
    	//update-end--Author: zhoujf  Date:20170524 for：TASK #2014 【online表单】online表单 模板配置 basePath问题 word模板文件上传问题
    	//update-end--Author: jg_huangxg  Date:20160816 for：TASK #1267 【平台升级】word布局自定义模板，上传组件，多文件上传功能
      	return html.toString();
    }
    
    
    /**
     *返回popup类型的表单html
     */
    private static String getPopupFormHtml(CgFormFieldEntity cgFormFieldEntity)
    {
      StringBuilder html = new StringBuilder("");
      html.append("<input type=\"text\" readonly=\"readonly\" class=\"searchbox-inputtext\" ");
      html.append("id=\"").append(cgFormFieldEntity.getFieldName()).append("\" ");
      html.append("name=\"").append(cgFormFieldEntity.getFieldName()).append("\" ");
    //update-begin--Author:zhoujf  Date:20180802 for：TASK #3036 【Online表单模板】表单模板不支持填值规则、校验必填、扩展参数
      if(StringUtils.isNotEmpty(cgFormFieldEntity.getExtendJson())){
     	 html.append(" "+ExtendJsonConvert.json2Html(cgFormFieldEntity.getExtendJson())+" ");;
      }
      if(cgFormFieldEntity.getFieldLength()!=null&&cgFormFieldEntity.getFieldLength()>0){
    	  html.append("style=\"width:").append(cgFormFieldEntity.getFieldLength()).append("px\" ");
      }
//      html.append("value=\"\\${").append(cgFormFieldEntity.getFieldName()).append("?if_exists?html}\" ");
      html.append("value=\"\\${data\\['"+cgFormFieldEntity.getTable().getTableName()+"'\\]\\['").append(cgFormFieldEntity.getFieldName()).append("'\\]?if_exists?html}\" ");
      //-- update-begin--Author:zhoujf  Date:20180409 for：TASK #2627 【online表单】online表单 表单模板 popup控件回填问题--
      html.append("onclick=\"popupClick(this,'"+cgFormFieldEntity.getDictText()+"','"+cgFormFieldEntity.getDictField()+"','"+cgFormFieldEntity.getDictTable()+"');\" ");
      //-- update-end--Author:zhoujf  Date:20180409 for：TASK #2627 【online表单】online表单 表单模板 popup控件回填问题--
      if("Y".equals(cgFormFieldEntity.getFieldMustInput())){
	  	  //校验必填
	  	  html.append("ignore=\"checked\" ");
	  }else{
	        if("Y".equals(cgFormFieldEntity.getIsNull())){
	        	html.append("ignore=\"ignore\" ");
	        }else{
	        	html.append("ignore=\"checked\" ");
	        }
	  }
    //update-end--Author:zhoujf  Date:20180802 for：TASK #3036 【Online表单模板】表单模板不支持填值规则、校验必填、扩展参数
      if(cgFormFieldEntity.getFieldValidType()!=null&&cgFormFieldEntity.getFieldValidType().length()>0){
    	  html.append("datatype=\"").append(cgFormFieldEntity.getFieldValidType()).append("\" ");
      }else{
		  html.append("datatype=\"*\" ");
	  }
      html.append("\\/>");
      return html.toString();
    }
}
