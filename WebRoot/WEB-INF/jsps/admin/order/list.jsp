<%@ page language="java" pageEncoding="UTF-8" import="java.util.*"%>
<%@page import="com.wfsc.common.utils.PermissionId"%>
<%@ taglib prefix="w" uri="/WEB-INF/wfsc.tld"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
            <table class="table table-bordered table-striped with-check">
              <thead>
                <tr>
                  <th><input type="checkbox" id="title-table-checkbox" name="title-table-checkbox" /></th>
                  <th>订单号</th>
                  <th>供应商</th>
                  <th>联系人</th>
                  <th>经手人</th>
                   <th>订货日期</th>
                  <th>报价单号</th>
                  <th>下单地区</th>
                  <th>订单确认</th>
                   <th>是否发货</th>
                  <th>订单状态</th>
                  <th>总金额</th>
                  <th>基本操作</th>
                </tr>
              </thead>
              <tbody>
              <s:if test="#request.page.data.size>0">
                <s:iterator value="#request.page.data" var="obj">
                 <tr>
                  <td><input type="checkbox" name="ids" value='<s:property value="id"/>'/></td>
                  <td><a class="tip-bottom" title="点击查看详细信息" href='<%=request.getContextPath()%>/admin/order_input.Q?isView=1&id=<s:property value="id"/>' target="_blank"><s:property value="orderNo"/></a></td>
                  <td><s:property value="supplier"/></td>
                   <td><s:property value="atten"/></td>
                  <td><s:property value="vcfrom"/></td>
                   <td> <s:date name="orderDate" format="yyyy-MM-dd" /></td>
                  <td><s:property value="quantation"/></td>
                  <td><s:property value="areaZh"/></td>
                 <td>
                  	<s:if test="#obj.isOrderConfirm==1">已确认</s:if>
                  	<s:else>未确认</s:else>
                  </td>
                   <td>
                  	<s:if test="#obj.isShipments==1">已发货</s:if>
                  	<s:else>未发货</s:else>
                  </td>
                  <td>
                  	<s:if test="#obj.orderStatus==0">未提交</s:if>
                  	<s:elseif test="#obj.orderStatus==1">已提交</s:elseif>
                  	<s:elseif test="#obj.orderStatus==2">已注销</s:elseif>
                  	<s:elseif test="#obj.orderStatus==3">已审核</s:elseif>
                  </td>
                   <td><s:property value="sumMoney"/> <s:property value="hbUnit"/></td>
                  <td>
                  <w:permission permissionId="<%=PermissionId.PROCESS_ORDER_MGT_UPDATE%>">
                  <s:if test="#obj.orderStatus!=3||#request.isAdmin||#request.isPurManager">
                  <button class="label label-info btn btn-primary btn-mini" onclick="operOrder('<s:property value="id"/>',1);">提交</button>
                  </s:if>
                  </w:permission>
                  <w:permission permissionId="<%=PermissionId.PROCESS_ORDER_MGT_AUDIT%>">
                  <s:if test="#obj.orderStatus==1">
                  <button class="label label-info btn btn-primary btn-mini" onclick="operOrder('<s:property value="id"/>',3);">审核</button>
                  </s:if>
                  </w:permission>
                  <s:if test="#obj.orderStatus==3">
                  <w:permission permissionId="<%=PermissionId.PROCESS_ORDER_MGT_PRINT%>">
                  <button class="label label-info btn btn-primary btn-mini" onclick="printOrder('<s:property value="id"/>');">打印</button>
                  </w:permission>
                  <w:permission permissionId="<%=PermissionId.PROCESS_ORDER_MGT_DOWNLOAD%>">
                   <button class="label label-info btn btn-primary btn-mini" onclick="downloadOrder('<s:property value="id"/>');">下载订单</button>
                   </w:permission>
                  </s:if>
                  <w:permission permissionId="<%=PermissionId.PROCESS_ORDER_MGT_ATTACHMENT%>">
                   <button class="label label-info btn btn-primary btn-mini" onclick="uploadFile('<s:property value="id"/>');">上传</button>
                    <button class="label label-info btn btn-primary btn-mini" onclick="downloadFile('<s:property value="id"/>');">下载</button>
                  </w:permission>
                  </td>
                </tr>
               </s:iterator>
               </s:if>
                <s:else>
					<tr>
						<td colspan="15">
							 <div class="alert alert-block"> 
				               <h4 align="center" class="alert-heading">暂时没有符合条件的记录！</h4>
				             </div>
						</td>
					</tr>
				</s:else>
              </tbody>
            </table>
          <tags:ajaxPagination divId="listTableDiv" queryFormId="queryForm" page="${page }"></tags:ajaxPagination>
