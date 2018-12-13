<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<script type="text/javascript">
	$(function(){
		$('#functionid').tree({
			checkbox : true,
			url : 'processController.do?setNodeAuthority&nodeId=${nodeId}',
			onLoadSuccess : function(node) {
				expandAll();
			},
			onClick: function(node){
				var isRoot =  $('#functionid').tree('getChildren', node.target);
				if(isRoot==''){
					var nodeId = $("#nodeId").val();
					$('#operationListpanel').panel("refresh", "processController.do?nodeOperaListForFunction&functionId="+node.id+"&nodeId="+nodeId);
					$('#dataRuleListpanel').panel("refresh", "processController.do?nodeDataRuleListForFunction&functionId="+node.id+"&nodeId="+nodeId);
				}else {
				}
			}
		});
		$("#functionListPanel").panel(
			{
				title :'<t:mutiLang langKey="menu.list"/>',
				tools:[{iconCls:'icon-save',handler:function(){mynodesubmit();}}]
			}
		);
		$("#operationListpanel").panel(
			{
				title :'<t:mutiLang langKey="operate.button.list"/>',
				tools:[{iconCls:'icon-save',handler:function(){submitNodeOperation();}}]
			}
		);
		
		$("#dataRuleListpanel").panel(
			{
				title :'<t:mutiLang langKey="common.data.rule"/>',
				tools:[{iconCls:'icon-save',handler:function(){submitNodeDataRule();}}]
			}
		);
	});
	function mynodesubmit() {
		var nodeId = $("#nodeId").val();
		var s = GetNode();
		doSubmit("processController.do?updateNodeAuthority&nodefunctions=" + s + "&nodeId=" + nodeId);
	}
	function GetNode() {
		var node = $('#functionid').tree('getChecked');
		var cnodes = '';
		var pnodes = '';
		var pnode = null; //保存上一步所选父节点
		for ( var i = 0; i < node.length; i++) {
			if ($('#functionid').tree('isLeaf', node[i].target)) {
				cnodes += node[i].id + ',';
				pnode = $('#functionid').tree('getParent', node[i].target); //获取当前节点的父节点
				while (pnode!=null) {//添加全部父节点
					pnodes += pnode.id + ',';
					pnode = $('#functionid').tree('getParent', pnode.target); 
				}
			}
		}
		cnodes = cnodes.substring(0, cnodes.length - 1);
		pnodes = pnodes.substring(0, pnodes.length - 1);
		return cnodes + "," + pnodes;
	};
	
	function expandAll() {
		var node = $('#functionid').tree('getSelected');
		if (node) {
			$('#functionid').tree('expandAll', node.target);
		} else {
			$('#functionid').tree('expandAll');
		}
	}
	function selecrAll() {
		var node = $('#functionid').tree('getRoots');
		for ( var i = 0; i < node.length; i++) {
			var childrenNode =  $('#functionid').tree('getChildren',node[i].target);
			for ( var j = 0; j < childrenNode.length; j++) {
				$('#functionid').tree("check",childrenNode[j].target);
			}
	    }
	}
	function reset() {
		$('#functionid').tree('reload');
	}

	$('#selecrAllBtn').linkbutton({   
	}); 
	$('#resetBtn').linkbutton({   
	});   
</script>
<div class="easyui-layout" fit="true">
	<div region="west" style="width:270px;padding: 1px;" split="true">
		<div class="easyui-panel" style="padding: 1px;" fit="true" border="false" id="functionListPanel">
			<input type="hidden" name="nodeId" value="${nodeId}" id="nodeId"> 
			<a id="selecrAllBtn" onclick="selecrAll();"><t:mutiLang langKey="select.all"/></a> 
			<a id="resetBtn" onclick="reset();"><t:mutiLang langKey="common.reset"/></a>
			<ul id="functionid"></ul>
		</div>
	</div>
	<div region="center" split="true">
		<div class="easyui-layout" fit="true">
			<div region="west" style="width:270px;padding: 1px;" split="true">
				<div class="easyui-panel" style="padding: 1px;" fit="true" border="false" id="operationListpanel"></div>
			</div>
			<div region="center" split="true">
				<div class="easyui-panel" style="padding: 1px;" fit="true" border="false" id="dataRuleListpanel"></div>
			</div>
		</div>
	</div>
	
</div>
