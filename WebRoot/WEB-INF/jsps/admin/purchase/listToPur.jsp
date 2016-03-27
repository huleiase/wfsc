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
                  <th>合同编号</th>
                  <th>货期要求</th>
                  <th>下单日期</th>
                  <th>单号</th>
                   <th>地址</th>
                  <th>客户名称</th>
                  <th>状态</th>
                  <th>审核人</th>
                  <th>基本操作</th>
                </tr>
              </thead>
              <tbody>
              <s:if test="#request.page.data.size>0">
                <s:iterator value="#request.page.data" var="obj">
                 <tr>
                  <td><input type="checkbox" name="ids" value='<s:property value="id"/>'/></td>
                   <td><a class="tip-bottom" title="点击查看详细信息" href='<%=request.getContextPath()%>/admin/purchase_input.Q?isView=1&isToPur=1&oper=1&id=<s:property value="id"/>' target="_blank"><s:property value="contractNo"/></a></td>
                  <td><s:property value="deliveryRequirements"/></td>
                   <td> <s:date name="contractDate" format="yyyy-MM-dd HH:mm:ss" /></td>
                  <td><s:property value="orderNo"/></td>
                  <td><s:property value="address"/></td>
                  <td><s:property value="customer"/></td>
                 <td>
                  	<s:if test="#obj.orderStatus==0">未提交</s:if>
                  	<s:elseif test="#obj.orderStatus==1">未审核</s:elseif>
                  	<s:elseif test="#obj.orderStatus>=2">已审核</s:elseif>
                  </td>
                   <td><s:property value="auditor"/></td>
                  <td>
                  <w:permission permissionId="<%=PermissionId.PROCESS_TO_PURCHASE_MGT_UPDATE%>">
                  <s:if test="#obj.orderStatus==0">
                   <button class="label label-info btn btn-primary btn-mini" onclick="operPurchase('<s:property value="id"/>',1);">提交</button>
                  </s:if>
                  </w:permission>
                  <w:permission permissionId="<%=PermissionId.PROCESS_TO_PURCHASE_MGT_AUDIT%>">
                  <s:if test="#obj.orderStatus==1">
                  <button class="label label-info btn btn-primary btn-mini" onclick="operPurchase('<s:property value="id"/>',1);">审核</button>
                  </s:if>
                  </w:permission>
                  <w:permission permissionId="<%=PermissionId.PROCESS_TO_PURCHASE_MGT_PRINT%>">
                  <s:if test="#obj.orderStatus>=2">
                  <button class="label label-info btn btn-primary btn-mini" onclick="operPurchase('<s:property value="id"/>',3);">打印</button>
                  </s:if>
                  </w:permission>
                   <w:permission permissionId="<%=PermissionId.PROCESS_TO_PURCHASE_MGT_PRINTQUOTE%>">
                  <button class="label label-info btn btn-primary btn-mini" onclick="printQuote('<s:property value="quote.id"/>');">打印报价单</button>
                  </w:permission>
                   <w:permission permissionId="<%=PermissionId.PROCESS_TO_PURCHASE_MGT_UPLOAD_ATTACHMENT%>">
                  <button class="label label-info btn btn-primary btn-mini" onclick="uploadFile('<s:property value="id"/>');">上传</button>
                  </w:permission>
                    <w:permission permissionId="<%=PermissionId.PROCESS_TO_PURCHASE_MGT_DOWNLOAD_ATTACHMENT%>">
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
