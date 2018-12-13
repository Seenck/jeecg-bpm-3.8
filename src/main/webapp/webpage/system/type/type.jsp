<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <t:base type="jquery,easyui,tools"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" layout="div" dialog="true" action="systemController.do?saveType">
    <input name="id" type="hidden" value="${type.id }">
    <input name="TSTypegroup.id" type="hidden" value="${typegroupid}">
    <fieldset class="step">
        <%--// add-start--Author:zhangguoming  Date:20140928 for：添加显示字段--%>
        <div class="form">
            <label class="Validform_label"> <t:mutiLang langKey="lang.dictionary.type"/>: </label>
            <input readonly="true" class="inputxt" value="${typegroupname }">
        </div>
        <%--// add-end--Author:zhangguoming  Date:20140928 for：添加显示字段--%>
        <div class="form">
            <label class="Validform_label"> <t:mutiLang langKey="dict.name"/>: </label>
            <!-- update-begin-Author:taoyan date:20180628 for:TASK #2835 【Excel 导出问题】typecode 如果 带有下划线 就不能正确的检索数据字典表 -->
            <input name="typename" class="inputxt" value="${type.typename }" datatype="/^[A-Za-z0-9\u4E00-\u9FA5\uf900-\ufa2d\.\s]{1,50}$/">
            <!-- update-end-Author:taoyan date:20180628 for:TASK #2835 【Excel 导出问题】typecode 如果 带有下划线 就不能正确的检索数据字典表 -->
            <span class="Validform_checktip"><t:mutiLang langKey="common.range1to50" /></span>
        </div>
        <div class="form">
            <label class="Validform_label"> <t:mutiLang langKey="dict.code"/>: </label>
            <!--update--begin--author:zhoujf Date:20180605 for:TASK #2772 【数据字典】数据字典项录入字典code重复校验问题 -->
            <input name="typecode" class="inputxt" value="${type.typecode }" datatype="/^[A-Za-z0-9\u4E00-\u9FA5\uf900-\ufa2d\.\s]{1,50}$/"
                   ajaxurl="systemController.do?checkType&code=${type.typecode }&typeGroupCode=${typegroup.typegroupcode}">
            <!-- update--end--author:zhoujf Date:20180605 for:TASK #2772 【数据字典】数据字典项录入字典code重复校验问题 -->
            <span class="Validform_checktip"><t:mutiLang langKey="common.range1to10" /></span>
        </div>
         <!-- update-begin-Author:taoyan date:20180620 for:TASK #2826 【新功能】字典值不支持排序，增加一个排序字段 -->
        <div class="form">
            <label class="Validform_label"><t:mutiLang langKey="dict.order"/>: </label>
            <input name="orderNum" class="inputxt" value="${type.orderNum}" datatype="/\b\d{1,3}\b/g" errormsg="请输入一至三位整数">
            <span class="Validform_checktip">请输入一至三位整数</span>
        </div>
        <!-- update-end-Author:taoyan date:20180620 for:TASK #2826 【新功能】字典值不支持排序，增加一个排序字段 -->
    </fieldset>
</t:formvalid>
</body>
</html>
