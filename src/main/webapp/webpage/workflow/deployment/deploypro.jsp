<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<html>
<head>
<title><t:mutiLang langKey="deploy.process"></t:mutiLang></title>
<t:base type="jquery,tools"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
	<t:formvalid formid="formobj" layout="div" dialog="true">
		<fieldset class="step">
			<div class="form">
				<label class="Validform_label">
					<t:mutiLang langKey="common.process.name"></t:mutiLang>
				</label>
				<input name="note" id="note" type="text" />
			</div>
			<div class="form">
				<t:upload name="deploy.process" uploader="activitiController.do?deployProcess" extend="*.bpmn;*.bar;*.zip*" id="file_upload" formData="note"></t:upload>
			</div>
		</fieldset>
	</t:formvalid>
</body>
</html>
