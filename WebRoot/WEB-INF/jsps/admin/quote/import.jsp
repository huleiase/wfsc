<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>【广州诚光进销存后台管理系统】</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<link rel="stylesheet" href="js/artDialog4.1.7/skins/default.css" />
<link href="css/global.css" rel="stylesheet" type="text/css" />
<link href="css/new_main.css" rel="stylesheet" type="text/css" />
<link href="css/main.css" rel="stylesheet" type="text/css" />
<script src="js/artDialog4.1.7/artDialog.js"></script> 
<script src="js/artDialog4.1.7/plugins/iframeTools.js"></script>
<script src="mm/js/jquery.min.js"></script> 
<script src="js/common/commonUse.js"></script> 
<style type="text/css">
body {
    min-width: 900px;
}
</style>
<script type="text/javascript">
		function checkFile(){
			$("#msgDiv").html("");
			if($("#txt").val()==''){
				$("#msgDiv").html("<div class='err_info'><div class='err_nr'> <h1 class='beijing4'>请选择上传的文件 </h1></div></div>");
				return false;
			}else{
				$("#importForm").submit();
				showOssWaitMsg('处理中请稍后...');
			}
		}
		</script>
	</head>
	<body>
		<div class="height2px"></div>
		<div class="main_w wai_border">
			<div class="main white_border">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="100%" align="left" valign="top">
							<div class="works_search left_margin8px all_border">
								<div class="all_space"></div>
								<div class="works_ss_tiaojian white_border">
									<table width="100%" border="0" cellspacing="0" cellpadding="0"
										class="gongdan_xq">
										<tr>
											<td width="925" height="25" align="left" class="">
													<form action="admin/quote_importFile.Q" method="post" enctype="multipart/form-data" id="importForm">
													<input type="hidden" name="quoteId" value="${quoteId }">
													<input type="hidden" name="befModel" value="${befModel }">
													<input type="hidden" name="factoryCode" value="${factoryCode }">
													<center>
									                      <div style="text-align: center;" class="add_work_od">
									                        <div id="div1">
									                          <ul style="margin-top: 34px;">
									                            <li style="margin-left: 40px;">选择文件：</li>
									                            <li>
									                              <div style="width:450px; float:left;overflow:hidden">
									                                <input type="text" name="txt" id="txt" class="all_input inp_wid360"/>
									                                <input type="button" value="浏&nbsp;览" class="bn_css all_butt wen_center"/>
									                                <input type="file" name="attachment" onchange="txt.value=this.value"  class="input_file" style="margin-left: -78px; _margin-left: -54px;height: 35px; width: 70px; _width: 70px; overflow: hidden"/>
									                                </div><div  style="width:200px; float:left;text-align:left">
									                                &nbsp;&nbsp;<input type="button" onclick="checkFile()"  value="导&nbsp;入" class="bn_css all_butt wen_center" >
									                              </div>
									                            </li>
									                          </ul>
									                        </div>
									                      </div>
									                    </center>
											
													<form>
											</td>
										</tr>
									</table>
										<div class="cuowutishi" style="padding-bottom:0"  id="msgDiv">
										<c:if test="${!empty errorMsg}">
							             <div class="err_info" >
							                 <div class="err_nr" >
							                  <c:forEach items="${errorMsg}" var="msg" > 
							                  <h1 class="beijing4">
													${msg}
							                    </h1>
							                  </c:forEach>
							                  </div> 
							                </div>
							               </c:if>
							               <c:if test="${successMsg!=null}">
							                <div class="err_info">
							                  <div class="err_nr"  >
							                    <h1 class="beijing2">
													${successMsg}
							                    </h1>
							                  </div>
							                </div>
									</c:if>
              						</div>
								</div>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</body>
</html>


