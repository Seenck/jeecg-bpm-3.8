<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<html>
	<head>
		<title><t:mutiLang langKey="common.process.handle"/></title>	
		<t:base type="jquery,tools"></t:base>			
	</head>
	<body style="overflow-y: hidden" scroll="no">
 <t:formvalid formid="formobj" action="${action}" layout="div" dialog="true">
 </t:formvalid> 
	</body>
</html>
