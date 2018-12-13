<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<t:tabs id="tableGroupTabs" iframe="false" tabPosition="bottom">
	<c:forEach items="${tsTypeList}" var="type">
		<t:tab iframe="dbController.do?tableList&typeid=${type.id}" icon="icon-add" title="${type.typename}" id="${type.typecode}"></t:tab>
	</c:forEach>
</t:tabs>
