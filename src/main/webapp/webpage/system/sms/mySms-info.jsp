<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="jquery,tools"></t:base>
</head>
<!-- update--begin--author:zhangjiaqiang date:20170314 for:修订页面内容过多不可滚动 -->
<body >
	<div style="width:95%;">
	    <legend>${tSSms.esTitle}</legend>
			<div class="form">
				<label class="form"><fmt:formatDate value='${tSSms.createDate}' type='both'/></label>
			</div>
	    <hr style="BORDER-RIGHT: #00686b 1px dotted; BORDER-TOP: #00686b 1px dotted; BORDER-LEFT: #00686b 1px dotted; BORDER-BOTTOM: #00686b 1px dotted" noShade SIZE=1/>
			<div class="form">
				${tSSms.esContent}
		    </div>
	    </div>
    </div>
    <!-- update--end--author:zhangjiaqiang date:20170314 for:修订页面内容过多不可滚动 -->
</body>
</html>