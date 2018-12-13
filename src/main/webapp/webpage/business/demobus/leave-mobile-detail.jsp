<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
		<meta name="keywords" content="Jeecg 移动表单" />
		<meta name="description" content="Jeecg 移动表单" />
		<title>Jeecg 移动表单</title>
		<link type="text/css" rel="stylesheet" href="online/template/moblieCommon001/css/formviewm.css" />
		<link type="text/css" rel="stylesheet" href="online/template/moblieCommon001/css/theme/default.css" />
		<script type="text/javascript" src="online/template/moblieCommon001/js/head.load.min.js"></script>
		<script type="text/javascript" src="online/template/moblieCommon001/js/jquery-1.7.2.min.js"></script>
		<script type="text/javascript" src="online/template/moblieCommon001/js/lang-cn.js"></script>
		<script type="text/javascript" src="online/template/moblieCommon001/js/ajaxfileupload.js"></script>
		<script type="text/javascript" src="online/template/moblieCommon001/js/address-cn.js"></script>
		<script type="text/javascript" src="online/template/moblieCommon001/js/utils.js"></script>
		<script type="text/javascript" src="plug-in/My97DatePicker/WdatePicker.js"></script>
		<style id="__wechat_default_css">
			::-webkit-scrollbar{
				width: 10px;
				height: 10px;
				background-color: #FFF;
			}
			::-webkit-scrollbar-button:start:decrement,::-webkit-scrollbar-button:end:increment{
				display: block;
			}
			::-webkit-scrollbar-button:vertical:start:increment,::-webkit-scrollbar-button:vertical:end:decrement{
				display: none;
			}
			::-webkit-scrollbar-button:end:increment{
				background-color: transparent;
			}
			::-webkit-scrollbar-button:start:decrement{
				background-color: transparent;
			}
			::-webkit-scrollbar-track-piece:vertical:start{
				background-color: transparent;
			}
			::-webkit-scrollbar-track-piece:vertical:end{
				background-color: transparent;
			}
			::-webkit-scrollbar-thumb:vertical{
				background: rgb(191, 191, 191);
			}
		</style>
	</head>
	<body class="wallpaper wallpaperm">
	<div id="container" class="container" mobile="1">
		<div>
			<h1 id="logo" class="logo"><a></a></h1>
		</div>
		<div class="ui-content">
			<form id="form1" class="form" name="formobj" method="post">
				<input type="hidden" id="btn_sub" class="btn_sub"/>
				  <input type="hidden" id="bpmStatus" name="bpmStatus" value="1" >
				<ul id="fields" class="fields">
							<li id="name_li" class="clearfix " typ="name" reqd="1">
								<label class="desc">请假人:</label>
								<div class="content">
								    <input type="text" class="ui-input-text xl input fld" 
											name="name" id="name" value="${leave.name}" />
								</div>
							</li>
								<li id="begainTime_li" class="clearfix " typ="date" reqd="1">
									<label class="desc">开始时间: </label>
									<div class="content">
										<input type="text" class="ui-input-text xl input fld" 
											name="begainTime" id="begainTime" value="${leave.begainTime}" 
											onClick="WdatePicker({})" />
									</div>
								</li>
								<li id="endTime_li" class="clearfix " typ="date" reqd="1">
									<label class="desc">结束时间: </label>
									<div class="content">
										<input type="text" class="ui-input-text xl input fld" 
											name="endTime" id="endTime" value="${leave.endTime}"
											onClick="WdatePicker({})" />
									</div>
								</li>
							<li id="reason_li" class="clearfix " typ="name" reqd="1">
								<label class="desc">请假原因:</label>
								<div class="content">
								    <textarea name="reason" class="ui-input-text xl input fld"  datatype="*" style="resize: none;" rows="4" cols="30">${leave.reason}</textarea>
								</div>
							</li>
					<li id="sub_tr">
					</li>
					</ul>
				</form>
				<div style="display:block !important;" class="powerby">由<a href="#">JEECG</a>提供技术支持</div>
			</div>
			<div id="status" class="mobile hide"></div>
			
	</div>
</body>
</html>