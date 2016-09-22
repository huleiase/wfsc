<%@ page language="java" pageEncoding="UTF-8" import="java.util.*"%>
<%@page import="com.wfsc.common.utils.PermissionId"%>
<%@ taglib prefix="w" uri="/WEB-INF/wfsc.tld"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
            <table class="table table-bordered" style="max-width: none;width:200%;">
              <thead>
                <tr>
                
                  <th rowspan="2">时间</th>
                  <th rowspan="2">PO号</th>
                  <th rowspan="2">合同号</th>
                  <c:if test="${isAdmin}">
                   <th rowspan="2">供应商</th>
                   </c:if>
                  <th rowspan="2">客户名称</th>
                  <th rowspan="2">项目</th>
                  <th rowspan="2">合同金额</th>
                  <th colspan="8">产品报价</th>
                  <th colspan="8">销售成本</th>
                  <th rowspan="2">毛利</th>
                  <th rowspan="2">毛利率</th>
                </tr>
                <th>型号</th>
                 <th>色号</th>
                  <th>数量</th>
                  <th>单价</th>
                  <th>合计</th>
                  <th>运费</th>
                  <th>工地运费</th>
                  <th>单价调整</th>
                 <th>型号</th>
                  <th>色号</th>
                  <th>订货量</th>
                  <th>实订量</th>
                  <th>单价</th>
                  <th>实价</th>
                  <th>币种合计</th>
                  <th>合计</th>
                </tr>
              </thead>
              <tbody>
              <s:if test="#request.page.data.size>0">
                <s:iterator value="#request.page.data" var="obj">
                 <tr>
                  <td> <s:date name="createDate" format="yyyy-MM-dd HH:mm:ss" /></td>
                  <td><a href="<%=request.getContextPath()%>/admin/order_manager.Q?orderNo=<s:property value="orderNo"/>" target="_blank"> <s:property value="orderNo"/></a></td>
                   <td><s:property value="contractNo"/></td>
                   <c:if test="${isAdmin}">
                   <td><s:property value="supplier"/></td>
                   </c:if>
                    <td><s:property value="customerName"/></td>
                     <td><s:property value="projectName"/></td>
                      <td><s:property value="sumMoney"/></td>
                     <td><s:property value="vcModelNum"/></td> 
                      <td><s:property value="bjColor"/></td> 
                      <td><s:property value="vcQuantity"/><s:property value="vcPriceUnit"/></td> 
                       <td><s:property value="bjPrice"/><s:property value="vcMoney"/></td> 
                         <td><s:property value="vcOldPriceTotal"/><s:property value="vcMoney"/></td>  
                         <td><s:property value="vcFre"/></td> 
                         <td><s:property value="vcSpecialExp"/></td> 
                         <td><s:property value="priceAdjust"/></td> 
                         <td><s:property value="cbModelNum"/></td> 
                         <td><s:property value="cbColor"/></td> 
                         <td><s:property value="orderNum"/><s:property value="cbPriceUnit"/></td>
                          <td><s:property value="cbQuantity"/><s:property value="cbPriceUnit"/></td>
                          <td><s:property value="singleMoney"/><s:property value="priceCur"/></td> 
                          <td><s:property value="cbPrice"/><s:property value="priceCur"/></td> 
                          <td><s:property value="cbTotal"/><s:property value="priceCur"/></td>  
                           <td><s:property value="amountrmb"/><s:property value="vcMoney"/></td>  
                          <td><s:property value="sellProfit"/></td> 
                          <td><s:property value="sellProfitRate"/></td>
                </tr>
               </s:iterator>
               </s:if>
                <s:else>
					<tr>
						<td colspan="34">
							 <div class="alert alert-block"> 
				               <h4 align="center" class="alert-heading">暂时没有符合条件的记录！</h4>
				             </div>
						</td>
					</tr>
				</s:else>
              </tbody>
            </table>
          <tags:ajaxPagination divId="listTableDiv" queryFormId="queryForm" page="${page }"></tags:ajaxPagination>
