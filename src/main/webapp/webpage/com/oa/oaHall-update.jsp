<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>厅室列表</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1">
<t:base type="bootstrap,layer,validform,bootstrap-form"></t:base>
</head>
 <body style="overflow:hidden;overflow-y:auto;">
 <div class="container" style="width:100%;">
	<div class="panel-heading"></div>
	<div class="panel-body">
	<form class="form-horizontal" role="form" id="formobj" action="oaHallController.do?doUpdate" method="POST">
		<input type="hidden" id="btn_sub" class="btn_sub"/>
		<input type="hidden" id="id" name="id" value="${oaHall.id}"/>
	<div class="form-group">
		<label for="hallCode" class="col-sm-3 control-label">厅室编号：</label>
		<div class="col-sm-7">
			<div class="input-group" style="width:100%">
				<input id="hallCode" name="hallCode" value='${oaHall.hallCode}' type="text" maxlength="32" class="form-control input-sm" placeholder="请输入厅室编号"  ignore="checked" />
			</div>
		</div>
	</div>
	<div class="form-group">
		<label for="hallName" class="col-sm-3 control-label">厅室名称：</label>
		<div class="col-sm-7">
			<div class="input-group" style="width:100%">
				<input id="hallName" name="hallName" value='${oaHall.hallName}' type="text" maxlength="50" class="form-control input-sm" placeholder="请输入厅室名称"  ignore="checked" />
			</div>
		</div>
	</div>
	<div class="form-group">
		<label for="orderName" class="col-sm-3 control-label">预约人姓名：</label>
		<div class="col-sm-7">
			<div class="input-group" style="width:100%">
				<input id="orderName" name="orderName" value='${oaHall.orderName}' type="text" maxlength="32" class="form-control input-sm" placeholder="请输入预约人姓名"  ignore="checked" />
			</div>
		</div>
	</div>
	<div class="form-group">
		<label for="orderStime" class="col-sm-3 control-label">预约开始时间：</label>
		<div class="col-sm-7">
			<div class="input-group" style="width:100%">
      		    <input id="orderStime" name="orderStime" type="text" class="form-control input-sm laydate-datetime" placeholder="请输入预约开始时间"  ignore="checked"   value="<fmt:formatDate pattern='yyyy-MM-dd HH:mm:ss' type='both' value='${oaHall.orderStime}'/>" />
                <span class="input-group-addon" ><span class="glyphicon glyphicon-calendar"></span> </span>
			</div>
		</div>
	</div>
	<div class="form-group">
		<label for="orderEtime" class="col-sm-3 control-label">预约结束时间：</label>
		<div class="col-sm-7">
			<div class="input-group" style="width:100%">
      		    <input id="orderEtime" name="orderEtime" type="text" class="form-control input-sm laydate-datetime" placeholder="请输入预约结束时间"  ignore="checked"   value="<fmt:formatDate pattern='yyyy-MM-dd HH:mm:ss' type='both' value='${oaHall.orderEtime}'/>" />
                <span class="input-group-addon" ><span class="glyphicon glyphicon-calendar"></span> </span>
			</div>
		</div>
	</div>
	<div class="form-group">
		<label for="address" class="col-sm-3 control-label">厅室地址：</label>
		<div class="col-sm-7">
			<div class="input-group" style="width:100%">
				<input id="address" name="address" value='${oaHall.address}' type="text" maxlength="100" class="form-control input-sm" placeholder="请输入厅室地址"  ignore="checked" />
			</div>
		</div>
	</div>
	<div class="form-group">
		<label for="status" class="col-sm-3 control-label">预约状态：</label>
		<div class="col-sm-7">
			<div class="input-group" style="width:100%">
				<input id="status" name="status" value='${oaHall.status}' type="text" maxlength="1" class="form-control input-sm" placeholder="请输入预约状态"  datatype="n"  ignore="ignore" />
			</div>
		</div>
	</div>
	</form>
	</div>
 </div>
<script type="text/javascript">
var subDlgIndex = '';
$(document).ready(function() {
	//单选框/多选框初始化
	$('.i-checks').iCheck({
		labelHover : false,
		cursor : true,
		checkboxClass : 'icheckbox_square-green',
		radioClass : 'iradio_square-green',
		increaseArea : '20%'
	});
	
	//表单提交
	$("#formobj").Validform({
		tiptype:function(msg,o,cssctl){
			if(o.type==3){
				validationMessage(o.obj,msg);
			}else{
				removeMessage(o.obj);
			}
		},
		btnSubmit : "#btn_sub",
		btnReset : "#btn_reset",
		ajaxPost : true,
		beforeSubmit : function(curform) {
		},
		usePlugin : {
			passwordstrength : {
				minLen : 6,
				maxLen : 18,
				trigger : function(obj, error) {
					if (error) {
						obj.parent().next().find(".Validform_checktip").show();
						obj.find(".passwordStrength").hide();
					} else {
						$(".passwordStrength").show();
						obj.parent().next().find(".Validform_checktip").hide();
					}
				}
			}
		},
		callback : function(data) {
			var win = frameElement.api.opener;
			if (data.success == true) {
				frameElement.api.close();
			    win.reloadTable();
			    win.tip(data.msg);
			} else {
			    if (data.responseText == '' || data.responseText == undefined) {
			        $.messager.alert('错误', data.msg);
			        $.Hidemsg();
			    } else {
			        try {
			            var emsg = data.responseText.substring(data.responseText.indexOf('错误描述'), data.responseText.indexOf('错误信息'));
			            $.messager.alert('错误', emsg);
			            $.Hidemsg();
			        } catch (ex) {
			            $.messager.alert('错误', data.responseText + "");
			            $.Hidemsg();
			        }
			    }
			    return false;
			}
		}
	});
});
</script>
</body>
</html>