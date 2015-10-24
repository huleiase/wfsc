<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>【广州诚光进销存后台管理系统】</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminCommonHeader.jsp"%>
<script src="js/dpicker/WdatePicker.js" type="text/javascript"></script>
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
          <form action="/wfsc/admin/note_save.Q" method="post" class="form-horizontal" id="inputForm">
          <input name="note.id" value="${note.id }" type="hidden">
            <div class="control-group">
              <label class="control-label">公告名&nbsp;&nbsp;</label>
              <div class="controls">
                <input type="text" id="vcName" class="span5" name="note.vcName" value="${note.vcName }" />&nbsp;&nbsp;
              </div>
            </div>
             <div class="control-group">
              <label class="control-label">公告内容&nbsp;&nbsp;</label>
              <div class="controls">
                <input type="text" id="vcMemo" class="span5" name="note.vcMemo" value="${note.vcMemo }" />&nbsp;&nbsp;
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">开始时间&nbsp;&nbsp;</label>
              <div class="controls">
                 <input type="text" id="dtsTime" name="note.dtsTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${note.dtsTime}" class="span2" />
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">结束时间&nbsp;&nbsp;</label>
              <div class="controls">
                 <input type="text" id="dteTime" name="note.dteTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'dtsTime\')}'})" value="${note.dteTime}" class="span2" />
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
