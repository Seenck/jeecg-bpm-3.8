<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title><t:mutiLang langKey="form.property.info"></t:mutiLang></title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
	<t:formvalid formid="formobj" layout="div" dialog="true" action="processController.do?saveFPro" tabtitle="common.base.settings,common.high.settings">
		<input name="id" type="hidden" value="${formpro.id}">
		<input name="TPForm.id" type="hidden" value="${formid}">
		<fieldset class="step">
			<legend>
				<t:mutiLang langKey="menu.basic.settings"></t:mutiLang>
			</legend>
			<div class="form">
				<label class="Validform_label">
					<t:mutiLang langKey="property.name"></t:mutiLang>
				</label>
				<input name="formproname" value="${formpro.formproname}" datatype="s2-50" errormsg="propertyname.rang2-50">
				<span class="Validform_checktip">
					<t:mutiLang langKey="propertyname.rang2-50"></t:mutiLang>
				</span>
			</div>
			<div class="form">
				<label class="Validform_label">
					<t:mutiLang langKey="property.value"></t:mutiLang>
				</label>
				<input name="formproval" value="${formpro.formproval}">
			</div>
			<div class="form">
				<label class="Validform_label">
					<t:mutiLang langKey="property.id"></t:mutiLang>
				</label>
				<input name="formprokey" value="${formpro.formprokey}">
			</div>
			<div class="form">
				<label class="Validform_label">
					<t:mutiLang langKey="property.code"></t:mutiLang>
				</label>
				<input name="formprocode" value="${formpro.formprocode}">
			</div>
			<div class="form">
				<label class="Validform_label">
					<t:mutiLang langKey="is.operate"></t:mutiLang>
				</label>
				<select name="processopt">
					<option value="yes" <c:if test="${formpro.processopt=='yes'}">selected="selected"</c:if>><t:mutiLang langKey="common.yes"></t:mutiLang></option>
					<option value="no" <c:if test="${formpro.processopt=='no'}">selected="selected"</c:if>><t:mutiLang langKey="common.no"></t:mutiLang></option>
				</select>
			</div>
			<div class="form">
				<label class="Validform_label">
					<t:mutiLang langKey="property.state"></t:mutiLang>
				</label>
				<select name="formprostate">
					<option value="true" <c:if test="${formpro.formprostate==true}">selected="selected"</c:if>><t:mutiLang langKey="common.enable"></t:mutiLang></option>
					<option value="false" <c:if test="${formpro.formprostate==false}">selected="selected"</c:if>><t:mutiLang langKey="common.forbid"></t:mutiLang></option>
				</select>
			</div>
			<div class="form">
				<label class="Validform_label">
					<t:mutiLang langKey="property.type"></t:mutiLang>
				</label>
				<select name="formprotype" id="formprotype">
					<option value="text" <c:if test="${formpro.formprotype=='text'}">selected="selected"</c:if>>text</option>
					<option value="select" <c:if test="${formpro.formprotype=='select'}">selected="selected"</c:if>>select</option>
					<option value="hidden" <c:if test="${formpro.formprotype=='hidden'}">selected="selected"</c:if>>hidden</option>
					<option value="button" <c:if test="${formpro.formprotype=='button'}">selected="selected"</c:if>>button</option>
					<option value="checkbox" <c:if test="${formpro.formprotype=='checkbox'}">selected="selected"</c:if>>checkbox</option>
					<option value="radio" <c:if test="${formpro.formprotype=='radio'}">selected="selected"</c:if>>radio</option>
					<option value="textarea" <c:if test="${formpro.formprotype=='textarea'}">selected="selected"</c:if>>textarea</option>
				</select>
			</div>
		</fieldset>
		<fieldset class="step">
			<legend>
				<t:mutiLang langKey="common.high.settings"></t:mutiLang>
			</legend>
			<div class="form">
				<label class="Validform_label">
					<t:mutiLang langKey="property.event"></t:mutiLang>
				</label>
				<input name="formprofun" value="${formpro.formprofun }">
			</div>
			<div class="form">
				<label class="Validform_label">
					<t:mutiLang langKey="property.function"></t:mutiLang>
				</label>
				<textarea name="formproscript" rows="5" cols="40">${formpro.formproscript}</textarea>
			</div>
			<div class="form">
				<label class="Validform_label">
					<t:mutiLang langKey="form.property.style"></t:mutiLang>
				</label>
				<textarea name="formprocss" rows="5" cols="40">${formpro.formprocss}</textarea>
			</div>
		</fieldset>
	</t:formvalid>
</body>
</html>
