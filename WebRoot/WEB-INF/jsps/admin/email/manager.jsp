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
		$("#state").val("");
	}
	
	function updateStatus(id){
		var url = "<%=basePath%>admin/email_updateStatus.Q?id="+id;
		$.ajax({
			url:url,
			dataType:'text',
			success:function(data){
				if("1"==data){
					art.dialog({
						content: '已标记为已读！',
						okVal:"确认",
						ok:function(){
							window.location.reload();
						}
					});
				}else{
				}
			}
		})
	}
	function handle(id,status,action,quoteId,purchaseId,orderId){
		var getStatusUrl = "<%=basePath%>admin/quote_getQuoteStatus.Q?emailId="+id+"&status="+status+"&quoteId="+quoteId;
		if("toPurchase"==action){
			getStatusUrl = "<%=basePath%>admin/purchase_getPurchaseStatus.Q?emailId="+id+"&status="+status+"&purchaseId="+purchaseId;
		}else if("purchase"==action){
			getStatusUrl = "<%=basePath%>admin/purchase_getPurchaseStatus.Q?emailId="+id+"&status="+status+"&purchaseId="+purchaseId;
		}else if("order"==action){
			getStatusUrl = "<%=basePath%>admin/order_getOrderStatusForEmail.Q?emailId="+id+"&status="+status+"&orderId="+orderId;
		}
		$.ajax({
			url:getStatusUrl,
			dataType:'text',
			success:function(data){
				if("1"==data){
					var handleUrl ="#";
					if("quote"==action){
						if(1==status){
							handleUrl = "<%=basePath%>admin/quote_detail.Q?operate=audit&id="+quoteId;
							window.location.href=handleUrl;
						}else if(2==status){
							handleUrl = "<%=basePath%>admin/quote_detail.Q?operate=writPerm&id="+quoteId;
							window.location.href=handleUrl;
						}else if(3==status){
							art.dialog({
							    id: 'printQuote',
							    content: '请选择你要打印的模板!',
							    button: [
							        {
							            name: '内地模板',
							            callback: function () {
							            	var url = "<%=basePath%>admin/quote_printQuote.Q?id="+id;
							            	window.open(url);
							                return true;
							            },
							            focus: true
							        },
							        {
							            name: '香港模板',
							            callback: function () {
							            	var url = "<%=basePath%>admin/quote_printQuote.Q?id="+id+"&local=hk";
							            	window.open(url);
							                return true;
							            }
							        },
							        {
							            name: '关闭'
							        }
							    ]
							});
						}else if(4==status){
						//去设计报价单
							handleUrl = "<%=basePath%>admin/quote_designQuote.Q?id="+quoteId;
							window.location.href=handleUrl;
						}
					}
					if("toPurchase"==action){
						handleUrl = "<%=basePath%>admin/purchase_input.Q?id="+purchaseId+"&oper=1";
						window.location.href=handleUrl;
					}else if("purchase"==action){
						handleUrl = "<%=basePath%>admin/purchase_input.Q?id="+purchaseId+"&oper=2";
						window.location.href=handleUrl;
					}else if("order"==action){
						if(1==status){
							handleUrl = "<%=basePath%>admin/order_input.Q?id="+orderId+"&oper=1";
							window.location.href=handleUrl;
						}else if(2==status){
							handleUrl = "<%=basePath%>admin/order_input.Q?id="+orderId+"&oper=3";
							window.location.href=handleUrl;
						}else if(3==status){
							//去设计订单
						}
						
					}
					
				}else{
					art.dialog({
						content: '已被处理！',
						okVal:"确认",
						ok:function(){
							window.location.reload();
						}
					});
				}
			}
		})
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
						url:"<%=basePath%>admin/email_deleteByIds.Q",
						data:{"ids":ids},
						dataType:'text',
						success:function(data){
							if("ok"==data){
								window.location.href="<%=basePath%>admin/email_manager.Q";
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
 function deleteByUser(){
 	art.dialog({
			    content: '你确定要删除全部的邮件吗？',
			    okVal:'确定',
			    ok: function () {
			    	$.ajax({
						url:"<%=basePath%>admin/email_deleteByUser.Q",
						dataType:'text',
						success:function(data){
							if("ok"==data){
								window.location.href="<%=basePath%>admin/email_manager.Q";
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
	
</script>
</head>
<body>
<%@include file="/WEB-INF/jsps/admin/common/adminTopAndLeft.jsp"%>
<div class="container-fluid">
      <div class="widget-box">
        <div class="widget-content">
          <form action="admin/email_manager.Q" method="post" id="queryForm">
            <div class="controls">
	            <label class="span1" for="inputSuccess" style="margin-top:5px;width:66px;">是否已读</label>
	             <select name="state" id="state" style="width:170px;float:left;">
	            	<option value="0">请选择</option>
                  	<option value="1" <s:if test="#request.state==1">selected</s:if> >未读</option>
                  	<option value="2" <s:if test="#request.state==2">selected</s:if> >已读</option>
	             </select>
	         </div>
	         <div class="clear"></div>
	          <div class="controls">
	            <button type="submit" class="btn btn-success" style="margin-left:355px;">查询</button>&nbsp;&nbsp;&nbsp;
	             <button type="button" class="btn btn-primary" onclick="resetForm();">重置</button>
	          </div>
          </form>
        </div>
        <div class="widget-title"> <span class="icon"> <i class="icon-th"></i> </span>
            <h5>邮件列表</h5>
            <button class="label label-info btn btn-primary btn-mini" onclick="deleteByIds();">删除选中</button>
             <button class="label label-info btn btn-primary btn-mini" onclick="deleteByUser();">全部删除</button>
         </div>
         <div class="widget-content nopadding" id="listTableDiv">
            <%@include file="list.jsp"%>
      </div>
</div>
</div>

<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
</body>
</html>
