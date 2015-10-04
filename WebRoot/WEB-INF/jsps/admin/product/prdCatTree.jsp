<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>【广州诚光进销存后台管理系统】</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link href="/wfsc/js/ztree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="/wfsc/js/artDialog4.1.7/skins/default.css" />

		<script src="/wfsc/mm/js/jquery.min.js"></script> 
		<script src="/wfsc/js/artDialog4.1.7/artDialog.js"></script> 
		<script src="/wfsc/js/artDialog4.1.7/plugins/iframeTools.js"></script> 
		<script type="text/javascript" src="/wfsc/js/ztree/js/jquery.ztree.all-3.5.min.js"></script>
		<script type="text/javascript">
			var setting = {
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onClick: onClickFuc
			}
		};

			$(function(){
				$.ajax({
					url:"/wfsc/admin/products_getPrdCatTreeData.Q",
					async:false,
					dataType:'json',
					success:function(data){
						$.fn.zTree.init($("#treeDemo"),setting,data);
					}
				
				});
				
			})
			function onClickFuc(event, treeId, treeNode, clickFlag){
				if(!treeNode.children){
					$("#code").val(treeNode.code);
					$("#name").val(treeNode.name);
				}
			}
			function confirmInput(){
				var code = $("#code").val();
				var name = $("#name").val();
				if(code!="" && name!=""){
					$(window.parent.document).find("#prdCatCode").val(code);
					$(window.parent.document).find("#prdCatName").val(name);
					art.dialog.close();
				}else{
					art.dialog({content:"请选择类型",ok:true,okVal:"确定"});
				}
				
			}
			function resetInput(){
				$(window.parent.document).find("#prdCatName").val("");
				$(window.parent.document).find("#prdCatCode").val("");
				art.dialog.close();
			}
	</script>
</head>
<body>
	<div class="zTreeDemoBackground left">
		<ul id="treeDemo" class="ztree">
		</ul>
		<br/>
		 <div class="bn_ceng">
		 	<input type="hidden" value="" id="code">
		 	<input type="hidden" value="" id="name">
		 	<input name="" type="button" onclick="confirmInput();" value="确 定" />&nbsp;&nbsp;
		     <input name="" type="button"  onclick="resetInput();" value="清 除" />
		 </div>
	</div>
</body>
</html>