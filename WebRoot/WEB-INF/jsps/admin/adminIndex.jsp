<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>【广州诚光进销存后台管理系统】</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<%
	String contextPath = request.getContextPath();
%>
<link rel="stylesheet" href="<%=contextPath %>/mm/css/bootstrap.min.css" />
<link rel="stylesheet" href="<%=contextPath %>/mm/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="<%=contextPath %>/mm/css/fullcalendar.css" />
<link rel="stylesheet" href="<%=contextPath %>/mm/css/matrix-style.css" />
<link rel="stylesheet" href="<%=contextPath %>/mm/css/matrix-media.css" />
<link href="<%=contextPath %>/mm/font-awesome/css/font-awesome.css" rel="stylesheet" />

<script src="<%=contextPath %>/mm/js/jquery.min.js"></script> 
<script src="<%=contextPath %>/mm/js/jquery.ui.custom.js"></script> 
<script src="<%=contextPath %>/mm/js/bootstrap.min.js"></script> 
<script src="<%=contextPath %>/mm/js/fullcalendar.min.js"></script> 
<script src="<%=contextPath %>/mm/js/matrix.js"></script> 
<script src="<%=contextPath %>/mm/js/matrix.calendar.js"></script>
<style type="text/css">
.container-fluid {
    padding-left: 0px;
    padding-right: 0px;
}
.container-fluid .row-fluid:first-child {
    margin-top: 0px;
}
.widget-box {
    background: #f9f9f9 none repeat scroll 0 0;
    border-left: 1px solid #cdcdcd;
    border-right: 1px solid #cdcdcd;
    border-top: 1px solid #cdcdcd;
    clear: both;
    margin-bottom: 0px;
    margin-top: 0px;
    position: relative;
}
.panel-left {
    margin-top: 25px;
}
.fc-state-highlight {
    background: #be4e4e none repeat scroll 0 0;
}
</style>
<script type="text/javascript">


</script>
</head>
<body >
<%@include file="/WEB-INF/jsps/admin/common/adminTopAndLeft.jsp"%>
	<!--container-fluid-->   
  <div class="container-fluid"> 
  <div class="row-fluid">
      <div class="span12">
        <div class="widget-box widget-calendar">
          <div class="widget-content">
            <div class="panel-left">
              <div id="fullcalendar"></div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
    <!--end-container-fluid-->   
</div>
<!--end-main-container-part-->

<!--Footer-part-->

<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />

<!--end-Footer-part-->

</body>
</html>
