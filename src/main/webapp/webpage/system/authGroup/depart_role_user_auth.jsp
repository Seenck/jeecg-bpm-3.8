<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>授权组授权</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<!-- update-begin-author:LiShaoQing date:20180621 for: 左侧图标样式太大问题，右侧ztree复选框不出现问题-->
<link rel="stylesheet" href="plug-in/ztree/css/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="plug-in/ztree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="plug-in/ztree/js/ztreeCreator.js"></script>
<script type="text/javascript" src="plug-in/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
<!-- update-end-author:LiShaoQing date:20180621 for: 左侧图标样式太大问题，右侧ztree复选框不出现问题-->
<script type="text/javascript">
	var departId = "";
    var setting = {
        check: {
            enable: false,
            chkboxType: { "Y": "", "N": "" }
        },
        data: {
            simpleData: {
                enable: true
            }
        },callback: {
            onExpand: zTreeOnExpand,
            onClick:onClick
        }
    };
    function onClick(event, treeId, treeNode){
        var departname = treeNode.name;
        var queryParams = $('#userLists').datagrid('options').queryParams;
        queryParams.orgIds = treeNode.id;
        departId = treeNode.id;
        $('#userLists').datagrid('options').queryParams=queryParams;
        $("#userLists").datagrid("reload");
        //刷新右侧授权部门角色
        $("#orgTree").empty();
    }
    //加载展开方法
    function zTreeOnExpand(event, treeId, treeNode){
        var treeNodeId = treeNode.id;
        $.post(
                'departAuthGroupController.do?getDepartInfo',
                {parentid:treeNodeId},
                function(data){
                    var d = $.parseJSON(data);
                    if (d.success) {
                        var dbDate = eval(d.msg);
                        var tree = $.fn.zTree.getZTreeObj("departSelect");

                        if (!treeNode.zAsync){
                            tree.addNodes(treeNode, dbDate);
                            treeNode.zAsync = true;
                        } else{
                            tree.reAsyncChildNodes(treeNode, "refresh");
                        }
                    }
                }
        );
    }
    //首次进入加载level为1的
    $(function(){
        $.post(
                'departAuthGroupController.do?getDepartInfo',
                function(data){
                    var d = $.parseJSON(data);
                    if (d.success) {
                        var dbDate = eval(d.msg);
                        $.fn.zTree.init($("#departSelect"), setting, dbDate);
                    }
                }
        );
    });
    
    function openAuthorizeSet(id) {
    	var zNodes;
    	jQuery.ajax({  
            async : false,  
            cache:false,  
            type: 'POST',  
            dataType : "json",  
            url: 'departAuthGroupController.do?getDepartGroupRoleTree&userId='+id+'&departId='+departId,//请求的action路径  
            error: function () {//请求失败处理函数  
                tip('请求失败');  
            },  
            success:function(data){ //请求成功后处理函数。
                zNodes = data.obj;   //把后台封装好的简单Json格式赋给zNodes  
            }  
        });  

    	var ztreeCreator = new ZtreeCreator('orgTree',"departAuthGroupController.do?getDepartRoleTree",zNodes)
    			.setCheckboxType({ "Y": "ps", "N": "ps" })
     			.initZtree({},function(treeObj){orgTree = treeObj});
		//菜单权限保存
		$("#functionListPanel").panel({
			title : '授权部门角色',
			tools : [ {
				iconCls : 'icon-save',
				handler : function() {
					mysubmit(id);
				}
			} ]
		});
    };
    //获取实际被选中的节点，去除第一节点
    function GetNode() {
		var zTree = $.fn.zTree.getZTreeObj("orgTree");				
		var node = zTree.getCheckedNodes(true);
		//加入实际被选中的节点
		var cnodes = '';
		for ( var i = 0; i < node.length; i++) {
			//取消一级节点
			if(node[i].isParent!=true){
				cnodes += node[i].id + ',';
			}
		}
		cnodes = cnodes.substring(0, cnodes.length - 1);
		return cnodes;
    }
    function mysubmit(userId) {
    	var s = GetNode();
    	$.ajax({
    		url : "departAuthGroupController.do?saveRoleUser",
    		type : "POST",
    		data : {
    			"userId":userId,
    			"roleId":s
    		},
    		success:function(data){
    			tip('保存数据成功');
    		},
    		error:function(data) {
    			var d = $.parseJSON(data);
    			tip(d.msg);
    		}
    	});
    }
</script>
</head>
<body>
<div id="divUserList" class="easyui-layout" style="width:100%;">
    <div data-options="region:'west',split:true" title="<t:mutiLang langKey='common.department'/>" style="width:200px;">
        <ul id="departSelect" class="ztree" ></ul>
    </div>
    <div data-options="region:'center'">
        <t:datagrid checkbox="true" name="userLists" title="common.user.select" actionUrl="departAuthGroupController.do?departRoleUserDataGrid"
                    fit="true" fitColumns="true" idField="id" queryMode="group" sortName="createDate" sortOrder="desc">
            <t:dgCol title="common.id" field="id" hidden="true"></t:dgCol>
            <t:dgCol title="common.username" sortable="false" field="userName" query="true"></t:dgCol>
            <t:dgCol title="common.real.name" field="realName" query="false"></t:dgCol>
            <t:dgCol title="common.department" sortable="false" field="orgNames" query="false"></t:dgCol>
            <t:dgCol title="common.role" field="userKey" ></t:dgCol>
            <t:dgCol title="common.createby" field="createBy" hidden="true"></t:dgCol>
            <t:dgCol title="common.createtime" field="createDate" formatter="yyyy-MM-dd" hidden="true"></t:dgCol>
            <t:dgCol title="common.updateby" field="updateBy" hidden="true"></t:dgCol>
            <t:dgCol title="common.updatetime" field="updateDate" formatter="yyyy-MM-dd" hidden="true"></t:dgCol>
            <t:dgCol title="common.status" sortable="true" field="status" replace="common.active_1,common.inactive_0,super.admin_-1" ></t:dgCol>
            <t:dgCol title="common.operation" field="opt" width="100"></t:dgCol>
            <t:dgFunOpt funname="openAuthorizeSet(id)" title="分配部门角色" urlclass="ace_button"  urlfont="fa-cog" urlStyle="background-color:#18a689;"></t:dgFunOpt>
        </t:datagrid>
    </div>
</div>
<div region="east" title="授权部门角色" style="width: 300px;" split="true" id="functionListPanel">
	<div id="orgTree" class="ztree"></div>
</div>

</body>
<script type="text/javascript">
	var divHeight = $(window).height();
	$("#divUserList").css("height",divHeight+"px"); 
</script>
</html>