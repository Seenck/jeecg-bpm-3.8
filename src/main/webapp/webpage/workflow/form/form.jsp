<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title><t:mutiLang langKey="form.info"></t:mutiLang></title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
	<t:formvalid formid="formobj" dialog="true" layout="div" action="processController.do?saveForm">
		<input name="id" type="hidden" value="${form.id}">
		<fieldset class="step">
			<div class="form">
				<label class="Validform_label">
					<t:mutiLang langKey="form.name"></t:mutiLang>
				</label>
				<input name="formname" value="${form.formname }" datatype="s3-50" class="inputxt">
				<span class="Validform_checktip">
					<t:mutiLang langKey="formname.rang3-50"></t:mutiLang>
				</span>
			</div>
			<div class="form">
				<label class="Validform_label">
					<t:mutiLang langKey="submit.url"></t:mutiLang>
				</label>
				<input name="formaction" value="${form.formaction}" class="inputxt">
			</div>
			<div class="form">
				<label class="Validform_label">
					<t:mutiLang langKey="form.type"></t:mutiLang>
				</label>
				<select name="TSType.id">
					<c:forEach items="${typeList}" var="t">
						<option value="${t.id }" <c:if test="${t.id==form.TSType.id}">selected="selected"</c:if>>${t.typename}</option>
					</c:forEach>
				</select>
			</div>
			<div class="form">
				<label class="Validform_label">
					<t:mutiLang langKey="form.key"></t:mutiLang>
				</label>
				<input name="formkey" value="${form.formkey }" class="inputxt">
			</div>
			<div class="form">
				<label class="Validform_label">
					<t:mutiLang langKey="form.code"></t:mutiLang>
				</label>
				<input name="formcode" value="${form.formcode}" class="inputxt">
			</div>
			<div class="form">
				<label class="Validform_label">
					<t:mutiLang langKey="form.note"></t:mutiLang>
				</label>
				<TEXTAREA name="formnote">${form.formnote}</TEXTAREA>
			</div>
		</fieldset>
	</t:formvalid>
</body>
</html>
