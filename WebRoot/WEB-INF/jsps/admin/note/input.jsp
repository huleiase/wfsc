<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>【广州诚光进销存后台管理系统】</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminCommonHeader.jsp"%>
<script type="text/javascript">
	function checkForm(){
		$("#inputForm").submit();
	}
	function toList(){
		window.location.href = "/wfsc/admin/note_manager.Q";
	}
</script>
</head>
<body>
<%@include file="/WEB-INF/jsps/admin/common/adminTopAndLeft.jsp"%>
<div class="container-fluid">
  <div class="row-fluid">
      <div class="widget-box">
        <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
          <h5>基本信息</h5>
        </div>
        <div class="widget-content nopadding">
          <form action="/wfsc/admin/admin_saveAdmin.Q" method="post" class="form-horizontal" id="adminForm">
            <div class="control-group">
              <label class="control-label">用户名&nbsp;&nbsp;</label>
              <div class="controls">
                <input type="text" id="username" class="span5" name="admin.username" value="${admin.username }" <s:if test="#request.admin.id!=null && #request.admin.id>0">readonly=readonly</s:if> />&nbsp;&nbsp;
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">中文名&nbsp;&nbsp;</label>
              <div class="controls">
                <input type="text" id="zhname" class="span5" name="admin.zhname" value="${admin.zhname }"  />&nbsp;&nbsp;
              </div>
            </div>
            <div class="form-actions">
              <button type="button" class="btn btn-success" onclick="checkForm()" id="saveButton">保存</button>&nbsp;&nbsp;
              <button type="button" class="btn btn-success" onclick="toList();">返回</button>
            </div>
          </form>
        </div>
      </div>
      </div>
      </div>
      
</div>
 
<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />
</body>
</html>
