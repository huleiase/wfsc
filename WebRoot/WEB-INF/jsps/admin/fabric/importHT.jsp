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
													<form action="admin/fabric_importHTData.Q" method="post" enctype="multipart/form-data" id="importForm">
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
									<div style="margin-bottom: 30px;" class="cuowutishi">
										<div class="tishi_info">
											<div class="tishi_nr">
												<h1 class="nerrbeijing5">
													<strong>温馨提示</strong>
													<br>
													<p>
														1、文件导入时，请保持导入的Excel的格式正确，请严格按照系统提供的文档编排文件
													</p>
													<p>
														2、文件的大小不要大于5MB，以免影响上传的时间，而造成页面超时
													</p>
													<p>
														3、导入的日期格式支持年-月-日，如 2012-12-01 
													</p>
													<p>
														4、多个联系人用英文逗号分隔，如 张三,李四。其他如电话、邮箱类似
													</p>
													<p>
														您可在此处下载模板,点击
														<samp>
														<a style="text-decoration: underline; color: rgb(255, 102, 0);" title="" href="admin/fabric_getHTFabricExcelModel.Q">下载模板</a>！
													</p>
												</h1>
											</div>
										</div>
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


