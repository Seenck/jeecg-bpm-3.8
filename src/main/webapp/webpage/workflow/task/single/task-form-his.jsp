<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title></title>
  <meta name="description" content="">
  <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
 <body>

<c:if test="${not empty nodeStart }">
<iframe src="${nodeStart}" id="taskformiframe" scrolling="no" frameborder="0" width="100%" height="90%" onload="iframeresize('taskformiframe');"></iframe>
</c:if>
<c:if test="${empty  nodeStart }">
 <br> <br> <br> <br>
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<t:mutiLang langKey="common.nodestart.no"/>
</c:if>
<script type="text/javascript">
function iframeresize(id)
{
	var iframe = document.getElementById(id); 
	var iframeDocument = null;
	//safari和chrome都是webkit内核的浏览器，但是webkit可以,chrome不可以
	if (iframe.contentDocument)
	{ 
		//ie 8,ff,opera,safari
		iframeDocument = iframe.contentDocument;
	} 
	else if (iframe.contentWindow) 
	{ 
		// for IE, 6 and 7:
		iframeDocument = iframe.contentWindow.document;
	} 
	if (!!iframeDocument) {
		iframe.width=iframeDocument.documentElement.scrollWidth+"px";
		iframe.height=iframeDocument.documentElement.scrollHeight+"px";		
	} else {
		alert("this browser doesn't seem to support the iframe document object");
	} 

}

		$('#passBtn').linkbutton({   
		});  
		$('#returnBtn').linkbutton({   
		}); 
		function procPass(yes){
			var iframe  = window.frames["iframeChild"].document;
			var inputvar = $("[vartype]", iframe);
			setvar(yes, inputvar, window.frames["iframeChild"]);
			var formData = {};
			$(iframe).find("input,textarea,select").each(function(){
				formData[$(this).attr("name")]= $(this).val();
			});
			var formAction = iframe.forms["formobj"].action;
			//ajax方式提交iframe内的表单
			$.ajax({
				async : false,
				cache : false,
				type : 'POST',
				data : formData,
				url : formAction,// 请求的action路径
				error : function() {// 请求失败处理函数
				},
				success : function(data) {
					var d = $.parseJSON(data);
					if (d.success) {
						var msg = d.msg;
						W.tip(msg);
						W.reloadTable();
						windowapi.close();
					}
				}
			});
		}
</script>
 </body>
</html>