<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>日志详情</title>
<t:base type="jquery,tools"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" layout="div" dialog="true">
	<fieldset class="step">
	
	<div class="form"><label class="form"> <%-- <t:mutiLang langKey="common.username"/> --%>操作ID:${tsLog.username } </label>
	 </div>
	 <div class="form"><label class="form"> <%-- <t:mutiLang langKey="common.realname"/> --%>操作人:${tsLog.realname } </label>
	 </div>
	<div class="form"><label class="form"> <%-- <t:mutiLang langKey="common.logtype"/> --%>日志类型: </label>
	   <!-- 以后再统一由数据字典维护  fangwenrong-->
	   <c:if test="${tsLog.operatetype==1}"><t:mutiLang langKey="common.login"/></c:if>
	   <c:if test="${tsLog.operatetype==2}"><t:mutiLang langKey="common.logout"/></c:if>
	   <c:if test="${tsLog.operatetype==3}"><t:mutiLang langKey="common.insert"/></c:if>
	   <c:if test="${tsLog.operatetype==4}"><t:mutiLang langKey="common.delete"/></c:if>
	   <c:if test="${tsLog.operatetype==5}"><t:mutiLang langKey="common.update"/></c:if>
	   <c:if test="${tsLog.operatetype==6}"><t:mutiLang langKey="common.upload"/></c:if>
	   <c:if test="${tsLog.operatetype==7}"><t:mutiLang langKey="common.other"/></c:if>
	</div>
		<%--update-begin--Author:dangzhenghui  Date:20170624 for：TASK #2178 【日志表单效果】日志查看表单-----------------%>
		<div class="form"><label class="form"> <t:mutiLang langKey="log.content"/>: </label><textarea style="width: 100% ;background-color:#f4f4f4" rows="6" >${tsLog.logcontent }</textarea> </div>
			<%--update-end--Author:dangzhenghui  Date:20170624 for：TASK #2178 【日志表单效果】日志查看表单-----------------%>
		<div class="form"><label class="form"> <%-- <t:mutiLang langKey="common.loglevel"/> --%>日志等级: </label>
	   <!-- 以后再统一由数据字典维护  fangwenrong-->
	   <c:if test="${tsLog.loglevel==1}">INFO</c:if>
	   <c:if test="${tsLog.loglevel==2}">WARRING</c:if>
	   <c:if test="${tsLog.loglevel==3}">ERROR</c:if>
	</div>

	<div class="form"><label class="form"> <t:mutiLang langKey="operate.ip"/>: </label> ${tsLog.note}</div>
	<div class="form"><label class="form"> <t:mutiLang langKey="operate.time"/>: </label>
	  <fmt:formatDate value="${tsLog.operatetime}" pattern="yyyy-MM-dd HH:mm:ss"/> 
	 </div>
	<div class="form"><label class="form"> <t:mutiLang langKey="common.browser"/>: </label> ${tsLog.broswer}</div>
	

	</fieldset>
	</form>
</t:formvalid>
</body>
</html>


