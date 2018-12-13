<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>公文收文</title>
  <meta name="description" content="">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <t:base type="jquery,aceform,DatePicker,validform,ueditor"></t:base>
  <link rel="stylesheet" href="plug-in/ueditor/ext/ueditor-ext.css" type="text/css"></link>
  <link rel="stylesheet" href="plug-in/ztree/ext/custom.css" type="text/css"></link>
  <link rel="stylesheet" href="plug-in/easyui/themes/metrole/icon.css" type="text/css"></link>
  <script type="text/javascript" src="plug-in/hplus/js/plugins/layer/layer.min.js"></script>
</head>
 <body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="joaDocReceivingController.do?doUpdate" tiptype="1" >
		<input type="hidden" id="btn_sub" class="btn_sub"/>
		<input type="hidden" name="id" value='${joaDocReceivingPage.id}'/>
		<input type="hidden" id="receiver" name="receiver" value='${joaDocReceivingPage.receiver}'/>
		<div class="tableform-shadow" style="margin: 0 auto;margin-top:10px;padding: 20px; border-width: 0px; width: 794px;">
			<div class="ueditor-box">
				<p style="text-align: center;">
					<br>
				</p>
				<h1 style="text-align: center;margin-bottom:10px;">
					<span style="text-decoration: none;">收文单</span><br>
				</h1>
			</div>
			<table align="center">
				<tbody>
					<tr class="firstRow">
						<td class="bg-bai" width="118.66666666666667" valign="middle" align="center">
							<span style="color: rgb(255, 0, 0);">*</span>来文标题
						</td>
						<td width="269" valign="middle" class="bg-lan" align="left" height="" data-fill="0">
							<input type="text" datatype="*" value='${joaDocReceivingPage.title}' name="title" autocomplete="off" class="tableform-field"/>
						</td>
						<td class="bg-bai" width="118.66666666666667" valign="middle" align="center">
							收文编号
						</td>
						<td width="250.66666666666666" align="left" class="bg-bai" align="center" height="" data-fill="0">
							<span style="margin-left:5px">${joaDocReceivingPage.docCode}</span>
						</td>
					</tr>
					
					<tr>
						<td class="bg-bai" width="118.66666666666667" valign="middle" align="center">
							来文单位
						</td>
						<td width="269" align="left" valign="middle" class="bg-lan" align="center" height="" data-fill="0">
							<input type="text" name="fromDepart" value='${joaDocReceivingPage.fromDepart}' autocomplete="off" class="tableform-field"/>
						</td>
						<td class="bg-bai" width="118.66666666666667" valign="middle" align="center">
							来文日期
						</td>
						<td width="250.66666666666666" align="left" class="bg-lan" height="" data-fill="0">
							<input name="fromDate" type="text" style="background: url('plug-in/ace/images/datetime.png') no-repeat scroll right center transparent;" 
								class="tableform-field" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${joaDocReceivingPage.fromDate}' type='date' pattern='yyyy-MM-dd'/>"/>  
						</td>
					</tr>
					
					<tr>
						<td class="bg-bai" width="118.66666666666667" valign="middle" align="center">
							公文分类
						</td>
						<td width="269" align="left" valign="middle" class="bg-lan" align="center" height="" data-fill="0">
							<t:dictSelect field="docType" defaultVal="${joaDocReceivingPage.docType}" type="radio" hasLabel="false" typeGroupCode="of_gwfl"></t:dictSelect>
						</td>
						<td class="bg-bai" width="118.66666666666667" valign="middle" align="center">
							文种
						</td>
						<td width="250.66666666666666" align="left" class="bg-lan" height="" data-fill="0">
							<t:dictSelect field="classification" defaultVal="${joaDocReceivingPage.classification}" hasLabel="false" typeGroupCode="of_wenz" extendJson="{class:tableform-field}"></t:dictSelect>
						</td>
					</tr>
					
					<tr>
						<td class="bg-bai" width="118.66666666666667" valign="middle" align="center">
							缓急程度
						</td>
						<td width="269" align="left" valign="middle" class="bg-lan" align="center" height="" data-fill="0">
							<t:dictSelect field="urgency" type="radio" defaultVal="${joaDocReceivingPage.urgency}" hasLabel="false" typeGroupCode="of_hjcd"></t:dictSelect>
						</td>
						<td class="bg-bai" width="118.66666666666667" valign="middle" align="center">
							机密程度
						</td>
						<td width="250.66666666666666" align="left" class="bg-lan" height="" data-fill="0">
							<t:dictSelect field="confidentiality" type="radio" defaultVal="${joaDocReceivingPage.confidentiality}" hasLabel="false" typeGroupCode="of_jimi"></t:dictSelect>
						</td>
					</tr>
					
					<tr>
						<td class="bg-bai" width="118.66666666666667" valign="middle" align="center">
							关键词
						</td>
						<td align="left" colspan="3" valign="middle" class="bg-lan" align="center" height="" data-fill="0">
							<input type="text" name="keyword" value="${joaDocReceivingPage.keyword}" autocomplete="off" class="tableform-field"/>
						</td>
					</tr>
					
					<tr>
						<td class="bg-bai" width="118.66666666666667" valign="middle" align="center">
							收文人
						</td>
						<td align="left" colspan="3" valign="middle" class="bg-lan" align="center" height="" data-fill="0">
							<textarea id="select2textarea" rows="3" name="receiverName" style="width:100%" class="tableform-field">${joaDocReceivingPage.receiverName}</textarea>
							<div class="clear-select-ty icon-cha-ty"></div>
							<div class="triggle-select-ty icon-list-ty detail-hidden"></div>
						</td>
					</tr>
					
					<tr>
						<td class="bg-bai" width="118.66666666666667" valign="middle" align="center">
							文件
						</td>
						<td align="left" colspan="3" valign="middle" class="bg-bai" align="center" height="" data-fill="0">
							<%@include file="file-common.jsp"%>		
						</td>
					</tr>

					<tr>
						<td class="bg-bai" width="118.66666666666667" valign="middle" align="center">
							登记人
						</td>
						<td width="269" align="left" valign="middle" class="bg-bai" align="center" height="" data-fill="0">
							<input type="text" name="booker" value="${joaDocReceivingPage.booker}" readonly="readonly" autocomplete="off" class="tableform-field"/>
						</td>
						<td class="bg-bai" width="118.66666666666667" valign="middle" align="center">
							登记时间
						</td>
						<td width="250.66666666666666" align="left" class="bg-bai" height="" data-fill="0">
							<input id="bookDate" name="bookDate" readonly="readonly" type="text" style="background: url('plug-in/ace/images/datetime.png') no-repeat scroll right center transparent;"  
								class="tableform-field"  value="<fmt:formatDate value='${joaDocReceivingPage.bookDate}' type='date' pattern='yyyy-MM-dd'/>"/>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
  </t:formvalid>
<script type="text/javascript" src="webpage/business/bizflow/officedoc/file-common.js"></script>
<script type="text/javascript">
   $(function(){
    //查看模式情况下,删除和上传附件功能禁止使用
	if(location.href.indexOf("load=detail")!=-1){
		$("#formobj").find(":input").removeAttr("onclick").attr("disabled","disabled");
		$(".detail-hidden").css("display","none");
	}
	if(location.href.indexOf("mode=read")!=-1){
		//查看模式控件禁用
		$("#formobj").find(":input").attr("disabled","disabled");
	}
	if(location.href.indexOf("mode=onbutton")!=-1){
		//其他模式显示提交按钮
		$("#sub_tr").show();
	}
	//加载文件列表
	var ids = "${joaDocReceivingPage.extFile}";
	if(!!ids){
		getHistoryFiles(ids);
	}
   });

  var neibuClickFlag = false;
  function neibuClick() {
	  neibuClickFlag = true; 
	  $('#btn_sub').trigger('click');
  }

</script>
 </body>
</html>