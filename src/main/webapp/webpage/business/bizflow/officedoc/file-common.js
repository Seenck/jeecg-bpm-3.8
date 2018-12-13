   var extFileMaxLimit = 10;
	/**
	 * 从文件库选择文件
	 * @returns
	 */
	function selectFileFromBase(){
	    top.layer.open({
	      title: '文件选择',
	      type: 2,
	  	  shadeClose: true,
	  	  closeBtn:1,
	  	  shade: 0.5,
	  	  area: ['95%', '95%'],
	  	  content: 'joaFileInfoController.do?listForSelect',
	        cancel: function(index) {
	        	layer.close(index);
	        },
	        btn: ['确认'],
	        yes: function(index,layero) {
	      	  var files = layero[0].getElementsByTagName("iframe")[0].contentWindow.getSelectFiles();
	      	  var alreadyNumber = $("#fileShowArea").find(".file-single-ty").length;
	      	  var restNumber = extFileMaxLimit - alreadyNumber;
	      	  //TODO  计算剩余文件数量的时候应该把重复的剔除掉
	      	  if(files.length>restNumber){
	      		var mytip = ""; 
	      		if(restNumber == 0){
	      			mytip = "文件数量超标,总共只能添加"+extFileMaxLimit+"个文件！"
	      		}else{
	      			mytip = "文件数量超标,现只能添加"+restNumber+"个文件！"
	      		}
	      		layero[0].getElementsByTagName("iframe")[0].contentWindow.tip(mytip);
	      	  }else{
	      		 loadHtmlByArray(files);
		      	 top.layer.close(index);
	      	  }
	        },
	      });
  }
  
	function clearAllSelect(){
		document.getElementById('fileShowArea').innerHTML = "";
	}
	
	function loadHtmlByArray(files){
		 var html = '';
     	 for(var a = 0 ;a<files.length;a++){
     		 var id = files[a].id;
     		 var filename = files[a].name+"."+files[a].ext;
     		 var icon = getFileIcon(files[a].type);
     		 html += '<div class="file-single-ty" onclick = "showRightClickMenu(event,this);"><input name="extFile" type="hidden" value="'+id+'"/><div class="file-icon-box"><span class="'+icon+'"></span></div><div class="file-name">'+filename+'</div></div>';
     	 }
     	 $("#fileShowArea").append(html);
	}
 /**
  * 根据类型返回图标
  * @param type
  * @returns
  */
  function getFileIcon(type){
	  if(type=="图片"){
		  return "icon-img-ty";
	  }else if(type=="Word"){
		  return "icon-word-ty";
	  }else if(type=="Excel"){
		  return "icon-excel-ty";
	  }else if(type=="文本"){
		  return "icon-txt-ty";
	  }else if(type="PDF"){
		  return "icon-pdf-ty";
	  }else if(type="PPT"){
		  return "icon-ppt-ty";
	  }else{
		  return "icon-unkown-ty";
	  }
  }
  
  function getHistoryFiles(ids){
	  var url = "joaFileInfoController.do?getHistoryFiles";
	  $.ajax({
		url:url,
		type:"POST",
		data:{ids:ids},
		dataType:"JSON",
		success:function(data){
			if(data.success){
				var files = data.obj;
				loadHtmlByArray(files);
			}else{
				tip(data.msg);
			}
		},
		error:function(e){
			console.log("reeor:"+e);
		}
	 });
  }
  
  //本来想触发右击事件的，但是tmd就是有bug，先用click吧
  function showRightClickMenu(event,obj){
	 /* if(event.which != 3){
		  return false;
	  }*/
	  $(obj).addClass("active");
	  $(obj).siblings(".file-single-ty").removeClass("active");
	  var h = $(window).height();
	  var w = $(window).width();
	  var menuWidth = 120;
	  var menuHeight = 75;
	  var x = event.pageX, y = event.pageY;
	  $('#fileShowAreaMenus').menu('show', {
		  left :x,
		  top : y
	  });
  }
  function onAreaMenuShow(){
	  //nothing to do!
  }
  
  function downFilety(){
	  var fileid = $("#fileShowArea").find(".file-single-ty.active").children("input").val();
	  location.href = "joaFileInfoController.do?downFile&id="+fileid;
  }
  
  function downAllSelect(){
	  var ids = "";
	  $("#fileShowArea").find(".file-single-ty").each(function(){
		 ids += $(this).children("input").val()+",";
	  });
	  if(ids==""){
		  return false;
	  }
	  location.href = "joaFileInfoController.do?downFilesOnZip&ids="+ids;
  }
  function delFilety(){
	  $("#fileShowArea").find(".file-single-ty.active").remove();
  }
  
  /**
   * 打开用户选择弹框
   * @returns
   */
  function openSelectUser(cur,link){
		$.dialog.setting.zIndex = getzIndex(); 
		$.dialog({
			content: 'url:joaFileInfoController.do?goUserSelect',
			zIndex: getzIndex(), 
			title: '用户选择列表', 
			lock: true,
			width: '800px',
			height: '400px',
			opacity: 0.4,
			button: [
				{	name: '确定',
					focus: true,
					callback: function(){
						var iframe = this.iframe.contentWindow;
						var table = iframe.$("#table1");
						var id='',userName='',realName='';
						$(table).find("tbody tr").each(function() {
							 id += $(this).find("input").val()+",";
							 realName += $(this).find("span").text()+",";
							 userName += $(this).find("input[id=userName]").val()+",";
						});
						$(cur).val(realName.substring(0,realName.length-1));
						$("#"+link).val(userName.substring(0,userName.length-1));
					}
				},
				{name: '取消', 
					callback: function (){}
				}
			]
		}).zindex();
  }
$(function(){
	/*----------------------------------选择人员-begin----------------------------------------*/
	var linkarg = "receiver";//选择弹框回调填值输入框ID
	if($("#select2textarea").length>0){
		$("#select2textarea").attr("readOnly","readOnly");
		$("#select2textarea").closest("td").css("position","relative");
		//浮动改变x的显隐
		$("#select2textarea").hover(function(){
			if(!$(this).val()){
				//当前无值则x看不见
				$(this).next('.clear-select-ty').removeClass("active");
			}else{
				$(this).next('.clear-select-ty').addClass("active");
			}
		},function(){
			$(this).next('.clear-select-ty').removeClass("active");
		});
		$("#select2textarea").next('.clear-select-ty').hover(function(){
			if(!$("#select2textarea").val()){
				//当前无值则x看不见
				$(this).removeClass("active");
			}else{
				$(this).addClass("active");
			}
		},function(){
			$(this).removeClass("active");
		});
		//x的点击事件
		$("#select2textarea").next('.clear-select-ty').click(function(){
			$("#select2textarea").val("");
			if(linkarg){
				$("#"+linkarg).val("");
			}
		});
		//textarea点击事件
		$("#select2textarea").click(function(){
			if(!$(this).val()){
				//当前无值，探矿咯
				openSelectUser(this,linkarg);
			}else{
				return false;
			}
		});
		//小图标的点击事件
		$("#select2textarea").next().next('.triggle-select-ty').click(function(){
			openSelectUser($("#select2textarea")[0],linkarg);
		});
	}
	/*----------------------------------选择人员-end----------------------------------------*/
});