<%@ page language="java" pageEncoding="UTF-8"%>
<div style="width:100%;height:150px;border-color: #C5C5C5;overflow: hidden;  position: relative;">
	<div class="panel-toolbar-ty">
		<span style="display: table; width: 100%; table-layout: fixed; height: 100%;">
			<div style="display:table-cell;height:100%;vertical-align:top;">
				<!-- <a class="btn-ty btn-ty-small btn-column">
					<span style="position: relative; display: block;">
					<span class="item-button-ty">
						<span class="item-button-ty-text">新上传</span> 
						<span class="item-button-ty-icon icon-add-ty">&nbsp;</span>
					</span>
					</span>
				</a> -->
				
				<a class="btn-ty btn-ty-small btn-column detail-hidden" href="javascript:void(0)" onclick = "selectFileFromBase()">
					<span style="position: relative; display: block;">
					<span class="item-button-ty">
						<span class="item-button-ty-text">文件库</span> 
						<span class="item-button-ty-icon icon-filebase-ty">&nbsp;</span>
					</span>
					</span>
				</a>
				
				<a class="btn-ty btn-ty-small btn-column" href="javascript:void(0)" onclick = "downAllSelect()">
					<span style="position: relative; display: block;">
					<span class="item-button-ty">
						<span class="item-button-ty-text">全部下载</span> 
						<span class="item-button-ty-icon icon-downAll-ty">&nbsp;</span>
					</span>
					</span>
				</a>
				
				<a class="btn-ty btn-ty-small btn-column detail-hidden" href="javascript:void(0)" onclick = "clearAllSelect()">
					<span style="position: relative; display: block;">
					<span class="item-button-ty">
						<span class="item-button-ty-text">清空</span> 
						<span class="item-button-ty-icon icon-del-ty">&nbsp;</span>
					</span>
					</span>
				</a>
				
			</div>
		</span>
	</div>
	<div class="panel-content-ty panel-content-body">
		<div class="panel-content-inner-ty" id="fileShowArea">
			<!-- <div class="file-single-ty">
				<div class="file-icon-box">
					<span class="icon-gif"></span>
				</div>
				<div class="file-name">tst.gif</div>
			</div> -->
		</div>
	</div>
</div>

<div id="fileShowAreaMenus" data-options="onshow:onAreaMenuShow" class="easyui-menu" style="width:80px;">
<div data-options="iconCls:'icon-put'" onclick="downFilety()">下载</div>
<div data-options="iconCls:'icon-cancel'" class="detail-hidden" onclick="delFilety()">删除</div>
<!-- <div data-options="iconCls:'icon-remove'" onclick="clearAllSelect()">清空</div> -->
</div>
<script type="text/javascript">
   $(function(){
	   $("#fileShowArea").on('contextmenu','.file-single-ty',function(e){
		   e.preventDefault();
		   return false;
	   });
   });
</script>
