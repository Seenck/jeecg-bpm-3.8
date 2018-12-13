<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
 <div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
<t:datagrid name="purchasedetail"  actionUrl="busController.do?purchaseDetialGrid&pruchaseid=${pruchaseid}" idField="id">
 <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
 <t:dgCol title="物品名称" field="purcname"></t:dgCol>
 <t:dgCol title="物品单价" field="purcprice"></t:dgCol>
 <t:dgCol title="采购数量" field="purcnum"></t:dgCol>
 <t:dgCol title="合计" field="purctotalprice"></t:dgCol>
 <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
 <t:dgDelOpt  url="busController.do?delBustrip&id={id}" title="删除"></t:dgDelOpt>
 <t:dgToolBar title="添加采购物品" icon="icon-add" url="busController.do?purchaseaddorupdate&purchaseid=${pruchaseid}" funname="add"></t:dgToolBar>
</t:datagrid>
</div>
</div>
</div>
