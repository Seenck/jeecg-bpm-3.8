<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>菜单信息</title>
<t:base type="jquery,easyui,tools"></t:base>
<script type="text/javascript">
		
	$(function() {
		$('#cc').combotree({
			url : 'functionController.do?setPFunction&selfId=${function.id}',
			panelHeight: 200,
			width: 157,
			onClick: function(node){
				$("#functionId").val(node.id);
			}
		});
		
		if($('#functionLevel').val()=='1'){
			$('#pfun').show();
		}else{
			$('#pfun').hide();
		}
		
		
		$('#functionLevel').change(function(){
			if($(this).val()=='1'){
				$('#pfun').show();
				var t = $('#cc').combotree('tree');
				var nodes = t.tree('getRoots');
				if(nodes.length>0){
					$('#cc').combotree('setValue', nodes[0].id);
					$("#functionId").val(nodes[0].id);
				}
			}else{
				var t = $('#cc').combotree('tree');
				var node = t.tree('getSelected');
				if(node){
					$('#cc').combotree('setValue', null);
				}
                $("#functionId").val(null);
				$('#pfun').hide();
			}
		});
	});
	
	function viewStyle(param) {
		var url = "<%=basePath%>/functionIconStyle.jsp?style = "+ param;
		//add("图标样式预览",url,'functionIconStyle',700,450);
		window.open(url,"_blank");
	}
</script>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" layout="div" dialog="true" callback="@Override callbackTreeLoad" refresh="true" action="functionController.do?saveFunction">
	<input id="id" name="id" type="hidden" value="${function.id}">
	<fieldset class="step">
	<div class="form">
        <label class="Validform_label"> <t:mutiLang langKey="menu.name"/>: </label>
        <input name="functionName" class="inputxt" value="${function.functionName}" datatype="*2-50" />
        <span class="Validform_checktip"> <t:mutiLang langKey="menuname.rang2to15"/> </span>
    </div>
   <div class="form">
        <label class="Validform_label"> <t:mutiLang langKey="funcType"/>: </label>
        <select name="functionType" id="functionType" datatype="*">
            <option value="0" <c:if test="${function.functionType eq 0}">selected="selected"</c:if>>
                <t:mutiLang langKey="funcType.page"/>
            </option>
            <option value="1" <c:if test="${function.functionType>0}"> selected="selected"</c:if>>
                <t:mutiLang langKey="funcType.from"/>
            </option>
        </select>
        <span class="Validform_checktip"></span>
    </div>
	<div class="form">
        <label class="Validform_label"> <t:mutiLang langKey="menu.level"/>: </label>
        <select name="functionLevel" id="functionLevel" datatype="*">
            <option value="0" <c:if test="${function.functionLevel eq 0}">selected="selected"</c:if>>
                <t:mutiLang langKey="main.function"/>
            </option>
            <option value="1" <c:if test="${function.functionLevel>0}"> selected="selected"</c:if>>
                <t:mutiLang langKey="sub.function"/>
            </option>
        </select>
        <span class="Validform_checktip"></span>
    </div>
	<div class="form" id="pfun">
        <label class="Validform_label"> <t:mutiLang langKey="parent.function"/>: </label>
        <!-- update-begin-Author:LiShaoQing date:20180802 for:TASK #3022 【jeecg-菜单管理-刘少谦】选中下级菜单，点击菜单录入，录入页面父菜单点击异常 -->
        <input id="cc" <c:if test="${function.TSFunction.functionLevel eq 0}"> value="${function.TSFunction.id}"</c:if>
		<c:if test="${function.TSFunction.functionLevel > 0}"> value="<t:mutiLang langKey="${function.TSFunction.functionName}"/>"</c:if>>
        <!-- update-end-Author:LiShaoQing date:20180802 for:TASK #3022 【jeecg-菜单管理-刘少谦】选中下级菜单，点击菜单录入，录入页面父菜单点击异常 -->
        <input id="functionId" name="TSFunction.id" style="display: none;" value="${function.TSFunction.id}">
    </div>
	<div class="form" id="funurl">
        <label class="Validform_label">
            <t:mutiLang langKey="menu.url"/>:
        </label>
        <input name="functionUrl" class="inputxt" value="${function.functionUrl}" style="width:70%">
    </div>
    <div class="form" id="icon">
        <label class="Validform_label"> <t:mutiLang langKey="common.icon"/>: </label>
        <select name="TSIcon.id">
            <c:forEach items="${iconlist}" var="icon">
                <option value="${icon.id}" <c:if test="${icon.id==function.TSIcon.id || (function.id eq null && icon.iconClas eq 'default') }">selected="selected"</c:if>>
                    <t:mutiLang langKey="${icon.iconName}"/>
                </option>
            </c:forEach>
        </select>
    </div>
    <%--update-begin--Author:zhangguoming  Date:20140509 for：云桌面图标管理--%>
    <div class="form" id="desktopIcon">
        <label class="Validform_label"> <t:mutiLang langKey="desktop.icon"/>: </label>
        <select name="TSIconDesk.id">
            <c:forEach items="${iconDeskList}" var="icon">
                <option value="${icon.id}" <c:if test="${icon.id==function.TSIconDesk.id || (function.id eq null && icon.iconClas eq 'System Folder') }">selected="selected"</c:if>>
                    <t:mutiLang langKey="${icon.iconName}"/>
                </option>
            </c:forEach>
        </select>
    </div>
    <%--update-end--Author:zhangguoming  Date:20140509 for：云桌面图标管理--%>
	<div class="form" id="funorder"><label class="Validform_label"> <t:mutiLang langKey="menu.order"/>: </label> <input name="functionOrder" class="inputxt" value="${function.functionOrder}" datatype="n1-3"></div>
	   <%-- update-begin--Author:chenj  Date:20160722 for：添加菜单图标样式  --%>
	<div class="form" id="funiconstyle">
        <label class="Validform_label">
            <t:mutiLang langKey="menu.funiconstyle"/>:
        </label>
        <input name="functionIconStyle" class="inputxt" value="${function.functionIconStyle}" />
        <%-- update-start--Author:dangzhenghui  Date:20170608 for：注释掉ace样式 --%>
       <%-- <a href="<%=basePath%>/webpage/common/functionIconStyleList.jsp?style=ace"  target="_blank">[ace图标样式]</a>--%>
        <a href="http://fontawesome.dashgame.com" target="_blank"> <i class="fa fa-eye-slash"></i>选择图标库 </a>
            <%-- update-start--Author:dangzhenghui  Date:20170608 for：注释掉ace样式 --%>
          <%-- update-end--Author:chenj  Date:20160729 for：增加图标样式预览页面 --%>
    </div>
     <%-- update-end--Author:chenj  Date:20160722 for：添加菜单图标样式  --%>
	</fieldset>
</t:formvalid> 
<script type="text/javascript">
//update-begin-Author:zhoujf -- date:20180807 -- for:增删改树节点刷新问题
function callbackTreeLoad(data){
		var win = frameElement.api.opener;
		if (data.success == true) {
			frameElement.api.close();
			win.tip(data.msg);
		} else {
			if (data.responseText == ''
					|| data.responseText == undefined) {
				$.messager.alert('错误', data.msg);
				$.Hidemsg();
			} else {
				try {
					var emsg = data.responseText
							.substring(
									data.responseText
											.indexOf('错误描述'),
									data.responseText
											.indexOf('错误信息'));
					$.messager.alert('错误', emsg);
					$.Hidemsg();
				} catch (ex) {
					$.messager.alert('错误',
							data.responseText + "");
					$.Hidemsg();
				}
			}
			return false;
		}
		win.reloadTreeNode();
}
//update-begin-Author:zhoujf -- date:20180807 -- for:增删改树节点刷新问题
</script>
</body>
</html>
