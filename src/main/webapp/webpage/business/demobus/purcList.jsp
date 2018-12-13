<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
 <div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
<t:datagrid name="purchase" title="采购订单" onClick="purchaseDetail" actionUrl="busController.do?purchaseGrid" idField="id">
 <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
 <t:dgCol title="采购编号" field="purcnumber"></t:dgCol>
 <t:dgCol title="采购人" field="TSUser_userName"></t:dgCol>
 <t:dgCol title="采购金额" field="totalprice"></t:dgCol>
 <t:dgCol title="状态" field="TSPrjstatus_description"></t:dgCol>
 <t:dgCol hidden="true" title="状态ID(该字段隐藏)" field="TSPrjstatus_code"></t:dgCol>
 <t:dgCol title="业务配置ID" field="TSBusConfig_id" hidden="true"></t:dgCol>
 <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
 <t:dgConfOpt exp="TSPrjstatus_code#eq#new" url="activitiController.do?startBusProcess&businessKey={id}&busconfigKey={TSBusConfig_id}" message="确认完毕,提交申请?" title="提交订单"></t:dgConfOpt>
 <t:dgFunOpt exp="TSPrjstatus_code#eq#doing" funname="progress(id,TSUser_userName)" title="项目进度"></t:dgFunOpt>
 <t:dgDelOpt exp="TSPrjstatus_code#eq#new" url="busController.do?delBustrip&id={id}" title="删除"></t:dgDelOpt>
 <t:dgToolBar title="新建订单" icon="icon-add" url="busController.do?createPurchase" funname="doSubmit"></t:dgToolBar>
</t:datagrid>
</div>
<div region="east" style="width:625px; overflow: hidden;" split="true" border="false">
<div class="easyui-panel" title="订单详细" style="padding:1px;" fit="true" border="false" id="purchasedetailpanel">
  </div>
</div>
</div>
</div>
<script type="text/javascript">
function createpurc(title,url,name)
{
	var ids=getpurchasedetailSelections('id');
	if(ids=='')
	{
		tip('请选择物品');
		return;
	}
	$.dialog.confirm('确定生成订单?', function(){
		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			url : url+'&ids='+ids,// 请求的action路径
			error : function() {// 请求失败处理函数
			},
			success : function(data) {
				var d = $.parseJSON(data);
				if (d.success) {
					var msg = d.msg;
					tip(msg);
					reloadpurchase();
				}
			}
		});
	}, function(){
	});
}
function purchaseDetail(rowindex,rowdata)
{
	$('#purchasedetailpanel').panel("refresh", "busController.do?purchaseDetailList&purchaseid=" + rowdata.id);
}
</script>
