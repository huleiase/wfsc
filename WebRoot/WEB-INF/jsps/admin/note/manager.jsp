<%@ page language="java" pageEncoding="UTF-8" import="java.util.*"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>【广州诚光进销存后台管理系统】</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<%@include file="/WEB-INF/jsps/admin//common/adminCommonHeader.jsp"%>

<script type="text/javascript">
	function resetForm(){
		$("#vcUser").val("");
		$("#vcName").val("");
	}
	function addOrUpdate(id){
		var url = "<%=basePath%>admin/note_input.Q";
		if(id){
			url+="?id="+id;
		}
		window.location.href = url;
	}
	function deleteByIds(){
		var selectCheckbox=$("input[type=checkbox][name=ids]:checked");
		if(selectCheckbox.length<1){
			art.dialog({
			    content: '请至少选择一条记录删除',
			    okVal:'确定',
			    ok: true
			});
		}else{
			var ids = "";
			selectCheckbox.each(function(i){
			   ids+=$(this).val()+",";
			 });
			 if(ids){
			 	ids = ids.substring(0,ids.length-1);
			 }
			art.dialog({
			    content: '你确定要删除选中的记录吗？',
			    okVal:'确定',
			    ok: function () {
			    	$.ajax({
						url:"<%=basePath%>admin/note_deleteByIds.Q",
						data:{"ids":ids},
						dataType:'text',
						success:function(data){
							if("ok"==data){
								window.location.href="<%=basePath%>admin/note_manager.Q";
							}else{
								art.dialog({
									time: 2,
									content: '删除失败！'
								});
							}
					    }
					})
			    },
			    cancelVal: '取消',
			    cancel: true 
			});
		}
	}

</script>
</head>
<body>
<%@include file="/WEB-INF/jsps/admin/common/adminTopAndLeft.jsp"%>
<div class="container-fluid">
      <div class="widget-box">
        <div class="widget-content">
          <form action="admin/note_manager.Q" method="post" id="queryForm">
            <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:66px;">发起人</label>
	            <input name="vcUser" id="vcUser" type="text" class="span2"  value="${ vcUser}">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:66px;">公告名</label>
	            <input name="vcName" id="vcName" type="text" class="span2"  value="${vcName }">
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	            <button type="submit" class="btn btn-success" style="margin-left:355px;">查询</button>&nbsp;&nbsp;&nbsp;
	             <button type="button" class="btn btn-primary" onclick="resetForm();">重置</button>
	          </div>
          </form>
        </div>
        <div class="widget-title"> <span class="icon"> <i class="icon-th"></i> </span>
            <h5>公告列表</h5>
             <w:permission permissionId="<%=PermissionId.SYSTEM_NOTICE_MGT_ADD%>">
            <button class="label label-info btn btn-primary btn-mini" onclick="addOrUpdate();">新增</button>
            </w:permission>
         	  <w:permission permissionId="<%=PermissionId.SYSTEM_NOTICE_MGT_DELETE%>">
            <button class="label label-info btn btn-primary btn-mini" onclick="deleteByIds();">删除</button>
            </w:permission>
         </div>
         <div class="widget-content nopadding" id="listTableDiv">
            <%@include file="list.jsp"%>
      </div>
</div>
</div>

<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
</body>
</html>
