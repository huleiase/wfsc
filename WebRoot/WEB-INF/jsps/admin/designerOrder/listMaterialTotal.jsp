<%@ page language="java" pageEncoding="UTF-8" import="java.util.*"%>
<%@page import="com.wfsc.common.utils.PermissionId"%>
<%@ taglib prefix="w" uri="/WEB-INF/wfsc.tld"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
            <table class="table table-bordered">
              <thead>
                <tr>
                 	<th>单号</th>
                 	<th>合同号</th>
                  <th>合同金额</th>
                  <th>产品报价</th>
                  <th>销售成本</th>
                  <th>毛利</th>
                  <th>毛利率</th>
                </tr>
              </thead>
              <tbody>
              <s:if test="#request.page.data.size>0">
                <s:iterator value="#request.page.data" var="obj">
                 <tr>
                  <td><a href="<%=request.getContextPath()%>/admin/order_manager.Q?orderNo=<s:property value="orderNo"/>" target="_blank"> <s:property value="orderNo"/></a></td>
                 <td><s:property value="contractNo"/></td>
                  <td><s:property value="sumMoney"/></td> 
                  <td><s:property value="bjTotal"/></td>
                  <td><s:property value="cbTotal"/></td> 
                  <td><s:property value="sellProfit"/></td> 
                  <td><s:property value="sellProfitRate"/></td>
                </tr>
               </s:iterator>
               </s:if>
                <s:else>
					<tr>
						<td colspan="6">
							 <div class="alert alert-block"> 
				               <h4 align="center" class="alert-heading">暂时没有符合条件的记录！</h4>
				             </div>
						</td>
					</tr>
				</s:else>
              </tbody>
            </table>
          <tags:ajaxPagination divId="listTableDiv" queryFormId="queryForm" page="${page }"></tags:ajaxPagination>
