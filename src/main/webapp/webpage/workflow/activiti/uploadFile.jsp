<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title><t:mutiLang langKey="deploy.process"></t:mutiLang></title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
	<t:formvalid formid="formobj" layout="div" dialog="true" beforeSubmit="upload">
		<fieldset class="step">
			<div class="form">
				<t:upload name="fiels" buttonText="choose.process.files" uploader="activitiController.do?developProcess" extend="*.xml" id="file_upload" formData="documentTitle"></t:upload>
			</div>
			<div class="form" id="filediv" style="height: 50px"></div>
		</fieldset>
	</t:formvalid>
</body>
</html>
