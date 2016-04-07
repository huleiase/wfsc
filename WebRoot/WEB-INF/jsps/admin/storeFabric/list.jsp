<%@ page language="java" pageEncoding="UTF-8" import="java.util.*"%>
<%@page import="com.wfsc.common.utils.PermissionId"%>
<%@ taglib prefix="w" uri="/WEB-INF/wfsc.tld"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
            <table class="table table-bordered" style="max-width: none;width:300%;">
              <thead>
                <tr>
                  <th><input type="checkbox" id="title-table-checkbox" name="title-table-checkbox" /></th>
                  <th>项目名称</th>
                  <th>报价单号</th>
                  <th>订单号</th>
                  <c:if test="${!lessPermission}">
                  <th>供应商</th>
                  </c:if>
                   <th>支付方式</th>
                   <th>报价型号</th>
                  <th>原厂型号</th>
                  <th>色号</th>
                  <th>幅宽</th>
                  <th>订货量</th>
                   <th>实订量</th>
                  <th>分铺段量</th>
                  <th>到货数量</th>
                  <th>实际到货</th>
                   <th>剩余数量</th>
                  <th>单位</th>
                  <th>经手人</th>
                   <th>位置</th>
                  <th>入库日期</th>
                  
                  <th>是否已发货</th>
                  <th>快递单号1</th>
                  <th>快递单号2</th>
                  <th>快递单号3</th>
                  <th>物流方式1</th>
                  <th>物流方式2</th>
                  <th>物流方式3</th>
                  <th>货到目的地完结</th>
                  <th>出库数量</th>
                  
                   <th>出库日期</th>
                   <th>订单日期</th>
                  <th>完结状态</th>
                   <th>备注</th>
                   <th>备注1</th>
                   <th>发货地址</th>
                   <th>出货经手人</th>
                    <th>快递单号</th>
                     <th>快递公司</th>
                      <th>到货公司</th>
                       <th>订单确认</th>
                        <th>货期要求</th>
                         <th>备注2</th>
                          <th>到货地址</th>
                           <th>特殊要求</th>
                           <th>要求到货日期</th>
                  <th>图片</th>
                  <th>操作</th>
                </tr>
              </thead>
              <tbody>
              <s:if test="#request.page.data.size>0">
                <s:iterator value="#request.page.data" var="obj">
                 <tr>
                  <td><input type="checkbox" name="ids" value='<s:property value="id"/>'/></td>
                  <td><s:property value="vcProject"/></td>
                   <td><s:property value="quoteNum"/></td>
                  <td><s:property value="orderNo"/></td>
                    <c:if test="${!lessPermission}">
                  <td><s:property value="supplie"/></td>
                  </c:if>
                  <td><s:property value="payment"/></td>
                  <td><s:property value="displayNum"/></td>
                  <td><s:property value="vcFactoryCode"/> <s:property value="vcModelNum"/></td>
                  <td><s:property value="vcColorNum"/></td>
                   <td><s:property value="vcWidth"/> <s:property value="vcWidthUnit"/></td>
                    <td><s:property value="orderQuantity"/></td>
                  <td><s:property value="vcQuoteNum"/></td>
                  <td><s:property value="vcSubLay"/></td>
                   <td><s:property value="arrivalNum"/></td>
                  <td><s:property value="vcRealityAog"/></td>
                  <td><s:property value="surplus"/></td>
                  <td><s:property value="unit"/></td>
                  <td><s:property value="vcAssignAutor"/></td>
                  <td><s:property value="vcAddr"/></td>
                  <td><s:date name="inStoreDate" format="yyyy-MM-dd" /></td>
                  
                  <td><s:property value="order.isShipments"/></td>
                  <td><s:property value="order.expressNumber1"/></td>
                  <td><s:property value="order.expressNumber2"/></td>
                  <td><s:property value="order.expressNumber3"/></td>
                  <td><s:property value="order.express1"/></td>
                  <td><s:property value="order.express2"/></td>
                  <td><s:property value="order.express3"/></td>
                  <td><s:property value="order.isArrivalOver"/></td>
                  <td><s:property value="outNum"/></td>
                  
                   <td><s:date name="outStoreDate" format="yyyy-MM-dd" /></td>
                   <td><s:date name="orderDate" format="yyyy-MM-dd" /></td>
                  <td id='isStoreOver<s:property value="id"/>'>
                  <s:if test="#obj.isStoreOver==1">已完结</s:if>
                  	<s:else>未完结</s:else>
                  </td>
                  <td><s:property value="vcPurchaseRmk"/></td>
                  <td><s:property value="vcRmk"/></td>
                  <td><s:property value="shipAddress"/></td>
                   <td><s:property value="shipPerson"/></td>
                    <td><s:property value="expressNumber"/></td>
                     <td><s:property value="expressCompany"/></td>
                      <td><s:property value="arrivalCompany"/></td>
                       <td><s:if test="#obj.isOrderConfirm==1">是</s:if>
                  	<s:else>否</s:else></td>
                        <td><s:property value="deliveryRequirements"/></td>
                         <td><s:property value="vcRmk2"/></td>
                          <td><s:property value="arrivalAddress"/></td>
                           <td><s:property value="specialReq"/></td>
                            <td><s:date name="arrivalDate" format="yyyy-MM-dd" /></td>
                  <td>
                  	<a target="_blank" title="点击查看图片" href='<s:property value="fileName"/>' rel="attachment" class="tip-bottom" style="display: inline-block; width: 60px;">
				     		<img width="30" height="20" alt="暂无图片" src='<s:property value="fileName"/>' /> 
				     </a>
                  </td>
                  <td>
                  <w:permission permissionId="<%=PermissionId.PROCESS_STOCk_MGT_UPDATE%>">
                  <button class="label label-info btn btn-primary btn-mini" onclick="storeOver('<s:property value="id"/>');">完结</button>
                  <button class="label label-info btn btn-primary btn-mini" onclick="addOrUpdate('<s:property value="id"/>');">修改</button>
                  </w:permission>
                  <w:permission permissionId="<%=PermissionId.PROCESS_STOCk_MGT_TRANSFER%>">
                  <button class="label label-info btn btn-primary btn-mini" onclick="transfer('<s:property value="id"/>');">转移</button>
                  </w:permission>
                  <w:permission permissionId="<%=PermissionId.PROCESS_STOCk_MGT_SHIPMENTS%>">
                  <button class="label label-info btn btn-primary btn-mini" onclick="shipments('<s:property value="id"/>');">发货</button>
                  </w:permission>
                  <w:permission permissionId="<%=PermissionId.PROCESS_STOCk_MGT_RECORD%>">
                  <button class="label label-info btn btn-primary btn-mini" onclick="record('<s:property value="vcFactoryCode"/>','<s:property value="vcModelNum"/>');">记录</button>
                  </w:permission>
                  <w:permission permissionId="<%=PermissionId.PROCESS_STOCk_MGT_ATTACHMENT%>">
                  <button class="label label-info btn btn-primary btn-mini" onclick="uploadFile('<s:property value="vcFactoryCode"/>','<s:property value="vcModelNum"/>');">上传图片</button>
                  </w:permission>
                  </td>
                </tr>
               </s:iterator>
               </s:if>
                <s:else>
					<tr>
						<td colspan="35">
							 <div class="alert alert-block"> 
				               <h4 align="center" class="alert-heading">暂时没有符合条件的记录！</h4>
				             </div>
						</td>
					</tr>
				</s:else>
              </tbody>
            </table>
          <tags:ajaxPagination divId="listTableDiv" queryFormId="queryForm" page="${page }"></tags:ajaxPagination>
