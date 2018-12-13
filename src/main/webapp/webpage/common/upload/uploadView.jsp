<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<html>
 <head>
<t:base type="jquery,easyui,tools"></t:base>
<script type="text/javascript">
		//update-begin-author:taoyan date:20180727 for:TASK #2958 【bug 】这个公用上传，加个上传刷新遮罩, 防止没上传完，用户点击确认按钮，导致出问题
        function uploadSuccess(d,file,response){
                $("#fileUrl").val(d.attributes.url);
                $("#fileName").val(d.attributes.name);
                $("#swfpath").val(d.attributes.swfpath);
                var url = $("#fileUrl").val();
                var html="";
                if(url.indexOf(".gif")!=-1 || 
                                url.indexOf(".jpg")!=-1        ||
                                url.indexOf(".png")!=-1 ||
                                url.indexOf(".bmp")!=-1){
                        html += "<img src='"+url+"' width =400 height=300 />";
                }else{
                        html += "<a href='"+url+"' target=_blank >下载:"+d.attributes.name+"</a>";
                }
                $("#fileShow").html(html);
            	changebutton(false);
        }
        function uploadCallback(callback,inputId){
                var url = $("#fileUrl").val();
                var name= $("#fileName").val();
                var swfpath = $("#swfpath").val();
                callback(url,name,inputId,swfpath);
        }
        //修改确认按钮禁用状态 
        function changebutton(flag){
	       	var api = frameElement.api;
         	api.button({
         		id: 'ok',
        		name: flag?"上传中":"确定",
                disabled: flag
            });
        }
        //默认未上传文件，确认按钮为禁用状态 
        function myUploadStart(){
        	var documentTitle = $('#documentTitle').val();
    	    $('#instruction').uploadify("settings", "formData", {
    	        'documentTitle': documentTitle
    	    });
    	    changebutton(true);
        }
      //update-end-author:taoyan date:20180727 for:TASK #2958 【bug 】这个公用上传，加个上传刷新遮罩, 防止没上传完，用户点击确认按钮，导致出问题
</script>
</head>
<!-- update--begin--author:zhangjiaqiang date:20170601 for:去除水平方向的滚动条，保存竖直方向的滚动条 -->
 <body style="overflow-x: hidden">
 <!-- update--end--author:zhangjiaqiang date:20170601 for:去除水平方向的滚动条，保存竖直方向的滚动条 -->
  <table cellpadding="0" cellspacing="1" class="formtable">
  <input id="documentTitle" type="hidden" name="documentTitle" value="blank"/>
  <input id="fileUrl" type="hidden"/>
  <input id="fileName" type="hidden"/>
  <input id="swfpath" type="hidden">
   <tbody>
    <tr>
     <td align="right">
       <label class="Validform_label"></label>
     </td>
     <td class="value">
     <!-- update--begin--author:zhangjiaqiang date:20170601 for:切换上传资源的保存路径 -->
      <t:upload onUploadStart="myUploadStart" name="instruction" dialog="false" multi="false" extend="" queueID="instructionfile" view="false" auto="true" uploader="cgUploadController.do?ajaxSaveFile" onUploadSuccess="uploadSuccess"  id="instruction" formData="documentTitle"></t:upload>
     <!-- update--end--author:zhangjiaqiang date:20170601 for:切换上传资源的保存路径 -->
     </td>
    </tr>
    <tr>
     <td colspan="2" id="instructionfile" class="value">
     </td>
    </tr>
   </tbody>
  </table>
   <div id="fileShow" >
  </div>
 </body>
 </html>
