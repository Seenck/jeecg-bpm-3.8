<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.jeecgframework.web.cgform.common.CgAutoListConstant"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>智能表单-代码生成</title>
<t:base type="jquery,easyui,tools"></t:base>
<script type="text/javascript" src="plug-in/cgform/js/fileTree.js"></script>
<script type="text/javascript">
function browseFolder(path) {
    try {
        var Message = "\u8bf7\u9009\u62e9\u6587\u4ef6\u5939"; //选择框提示信息
        var Shell = new ActiveXObject("Shell.Application");
        var Folder = Shell.BrowseForFolder(0, Message, 64, 17); //起始目录为：我的电脑
        if (Folder != null) {
            Folder = Folder.items(); // 返回 FolderItems 对象
            Folder = Folder.item(); // 返回 Folderitem 对象
            Folder = Folder.Path; // 返回路径
            if (Folder.charAt(Folder.length - 1) != "\\") {
                Folder = Folder + "\\";
            }
            document.getElementById(path).value = Folder;
            return Folder;
        }
    }
    catch (e) {
        alert(e.message);
    }
   }
   
</script>
<style type="text/css">
.table-list {
	margin: 0;
	width: auto;
	margin-left: 0px;
	margin-right: 0px;
	overflow: hidden;
}

.table-list td,.table-list th {
	text-align: center;
}

.t_table {
	overflow: auto; /*让内容表格外面的div自动有滚动条*/
	margin-left: 0px;
	margin-right: 0px;
	width: auto;
	max-height: 240px;
}
</style>
</head>
<body style="overflow-y: hidden; overflow-x: hidden;" scroll="no">
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" tiptype="1" action="generateController.do?dogenerate">
	<input id="id" name="id" type="hidden" value="${cgFormHeadPage.id}">
	<input id="tableName" name="tableName" type="hidden" value="${cgFormHeadPage.tableName}">
	<input type="hidden" id="fieldRowNum" name="fieldRowNum" value="1">
	<table cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right"><label class="Validform_label">代码生成目录: </label></td>
			<td class="value"><input type="text" class="inputxt" value="${projectPath }" name="projectPath" id="projectPath" datatype="*" /> <a href="#" id="openFoldSelect" class="easyui-linkbutton"
				icon="icon-search" onclick="openFolder('projectPath')">浏览</a></td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label">代码分层风格: </label></td>
			<td class="value"><select name="packageStyle">
				<option value="service">业务分层</option>
				<option value="project">代码分层</option>
			</select>
			</td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label"> 数据模型: </label></td>
			<td class="value"><select id="jformType" disabled="disabled" name="jformType">
				<option value="1" <c:if test="${cgFormHeadPage.jformType eq '1' || cgFormHeadPage.jformType eq '3'}"> selected='selected'</c:if>>单表</option>
				<option value="2" <c:if test="${cgFormHeadPage.jformType eq '2'}"> selected="selected"</c:if>>一对多</option>
			</select></td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label"> 表名: </label></td>
			<td class="value" colspan="3"><input disabled="disabled" class="inputxt" id="tableName_tmp" name="tableName_tmp" value="${cgFormHeadPage.tableName}" datatype="*"> <span
				class="Validform_checktip"></span>
				<!-- update-begin-author:taoyan date:20180628 for:布局修改 -->
				<div style="display:inline-block">
					<span>树形列表: </span>
					<input disabled type="radio" name="supportTree" <c:if test="${cgFormHeadPage.isTree eq 'Y'}">checked="checked"</c:if> value="1"/>是
				<input disabled type="radio" name="supportTree" <c:if test="${cgFormHeadPage.isTree eq 'N'}">checked="checked"</c:if> value="0"/>否
				</div>
				<!-- update-end-author:taoyan date:20180628 for:布局修改 -->
			</td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label"> 功能说明: </label></td>
			<td class="value" colspan="3"><input class="inputxt" id="ftlDescription" name="ftlDescription" value="${cgFormHeadPage.content}" datatype="*"> <span class="Validform_checktip"></span></td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label"> 包名(小写): </label></td>
			<td class="value" colspan="3"><input class="inputxt" id="entityPackage" name="entityPackage" datatype="*"> <span class="Validform_checktip"></span></td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label"> 实体类名(首字母大写): </label></td>
			<td class="value" colspan="3"><input class="inputxt" id="entityName" name="entityName" value="${entityNames[cgFormHeadPage.tableName]}" datatype="*"> <span class="Validform_checktip"></span></td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label"> 需要生成的代码: </label></td>
			<td class="value" colspan="3"><input type="checkbox" value="1" name="actionFlag" id="actionFlag" checked="checked">Action</input> <input type="checkbox" value="1" name="jspFlag" id="jspFlag"
				checked="checked">Jsp</input> <input type="checkbox" value="1" name="serviceIFlag" id="serviceIFlag" checked="checked">ServiceI</input> <input type="checkbox" value="1" name="serviceImplFlag"
				id="serviceImplFlag" checked="checked">ServiceImpl</input> <input type="checkbox" value="1" name="pageFlag" id="pageFlag" checked="checked">Page</input> <input type="checkbox" value="1"
				name="entityFlag" id="entityFlag" checked="checked">Entity</input> <span class="Validform_checktip"></span>
				<!-- update-begin-author:taoyan date:20180628 for:布局修改 -->
				<div style="display:inline-block">
					<span>【</span>
					<span>是否支持Restful:</span>
					<input type="radio" name="supportRestful" value="1"/>是
					<input type="radio" name="supportRestful" checked="checked" value="0"/>否
					<span>】</span>
				</div>
				<!-- update-end-author:taoyan date:20180628 for:布局修改 -->
			</td>
		</tr>
		<!-- update--begin--author:zhoujf date:20180503 for:一对多主子表关联外键的问题 -->
		<!-- update--begin--author:zhoujf date:20180614 for:TASK #2787 【online 代码生成器】支持生成word 布局模板 -->
		<tr>
			<td align="right"><label class="Validform_label"> 模板类型: </label></td>
			<td class="value" colspan="3">
			<input type="radio" name="version" value="ext">老版本模板(IE8+/不支持移动/标签列表)
			<input type="radio" name="version" checked="checked" value="ext-common">新一代模板(IE10+/移动支持/Bootstrap/Vue/支持原生态列表)
			<input type="radio" name="version" datatype="*" value="system">Online原样生成(Word模板)
			<span class="Validform_checktip"></span></td>
		</tr>
		<!-- update--end--author:zhoujf date:20180614 for:TASK #2787 【online 代码生成器】支持生成word 布局模板 -->
		<!-- update--end--author:zhoujf date:20180503 for:一对多主子表关联外键的问题 -->
		<tr id="jspMode_tr">
			<td align="right"><label class="Validform_label"> 页面风格: </label></td>
			<td class="value" colspan="3">
			<select id="jspMode" name="jspMode" style="width: 220px" datatype="*">
		     		<c:forEach items="${jspModeList }" var="style">
			     	 <option value="${style.code }" >${style.desc }</option>
			     	</c:forEach>
		     </select>
			<span class="Validform_checktip"></span></td>
		</tr>
	</table>
</t:formvalid>
</body>
<!-- update--begin--author:zhoujf date:20180503 for:一对多主子表关联外键的问题 -->
<script type="text/javascript">
<!-- update-begin-author:taoyan date:20180627 for:TASK #2817 【bug】代码生成器模板是否支持树类型隔离 -->
$(function(){
	$("input[name='version']").change(function(){
		var type = "single";
		var version = this.value;
		if(version=='system'){
			$("#jspMode").removeAttr("datatype");
		}else{
			$("#jspMode").attr("datatype","*");
		}
		getSingleTemplate(type,version,'');
	});
});
//获取表单风格模板名称
function getSingleTemplate(type,version,supportTree){
	if(version =='system'){
		$("#jspMode_tr").hide();
		$("#jspMode").empty();
	}else{
		$("#jspMode_tr").show();
		if(!supportTree){
			supportTree = $("input[name='supportTree']:checked").val();
		}
		$.ajax({
			url:"${pageContext.request.contextPath}/generateController.do?getOnlineTempletStyle",
			type:"post",
			data:{
				type:type,
				version:version,
				supportTree:supportTree
			},
			dataType:"json",
			success:function(data){
				if(data.success){
					$("#jspMode").empty();
					//$("#jspMode").append("<option value='' ><t:mutiLang langKey="common.please.select"/></option>");
					$.each(data.obj,function(i,tem){
						$("#jspMode").append("<option value='"+tem.code+"' >"+tem.desc+"</option>");
					});
				}else{
					$("#jspMode").empty();
				}
			}
		});
	}
	
}
<!-- update-end-author:taoyan date:20180627 for:TASK #2817 【bug】代码生成器模板是否支持树类型隔离 -->
</script>
<!-- update--end--author:zhoujf date:20180503 for:一对多主子表关联外键的问题 -->
</html>