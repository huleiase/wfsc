<%@ page language="java" pageEncoding="UTF-8" import="java.util.*"%>
<%@page import="com.wfsc.common.utils.PermissionId"%>
<%@ taglib prefix="w" uri="/WEB-INF/wfsc.tld"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
            <table class="table table-bordered table-striped with-check" style="background-color: #f9f9f9;">
              <thead>
                <tr>
                
                  <th rowspan="2">时间</th>
                  <th rowspan="2">PO号</th>
                  <th rowspan="2">合同号</th>
                  <th rowspan="2">客户名称</th>
                  <th rowspan="2">项目</th>
                  <th rowspan="2">合同金额</th>
                  <th colspan="5">产品报价</th>
                  <th colspan="5">销售成本</th>
                  <th rowspan="2">毛利</th>
                  <th rowspan="2">毛利率</th>
                </tr>
                <th>型号</th>
                  <th>数量</th>
                  <th>单价</th>
                  <th>税率</th>
                  <th>合计</th>
                 <th>型号</th>
                  <th>数量</th>
                  <th>单价</th>
                  <th>税率</th>
                  <th>合计</th>
                </tr>
              </thead>
              <tbody>
              <s:if test="#request.page.data.size>0">
                <s:iterator value="#request.page.data" var="obj">
                 <tr>
                  <td> <s:date name="createDate" format="yyyy-MM-dd" /></td>
                  <td><s:property value="orderNo"/></td>
                   <td><s:property value="contractNo"/></td>
                    <td><s:property value="customerName"/></td>
                     <td><s:property value="projectName"/></td>
                      <td><s:property value="sumMoney"/></td>
                     <td><s:property value="vcModelNum"/></td> 
                      <td><s:property value="vcQuantity"/><s:property value="vcPriceUnit"/></td> 
                       <td><s:property value="vcPrice"/></td> 
                        <td><s:property value="taxation"/></td> 
                         <td><s:property value="bjTotal"/></td>  
                         <td><s:property value="cbModelNum"/></td> 
                          <td><s:property value="cbQuantity"/><s:property value="cbPriceUnit"/></td>
                          <td><s:property value="cbPrice"/></td> 
                          <td><s:property value="taxation"/></td> 
                          <td><s:property value="cbTotal"/></td>  
                          <td><s:property value="sellProfit"/></td> 
                          <td><s:property value="sellProfitRate"/></td>
                </tr>
               </s:iterator>
               </s:if>
                <s:else>
					<tr>
						<td colspan="33">
							 <div class="alert alert-block"> 
				               <h4 align="center" class="alert-heading">暂时没有符合条件的记录！</h4>
				             </div>
						</td>
					</tr>
				</s:else>
              </tbody>
            </table>
          <tags:ajaxPagination divId="listTableDiv" queryFormId="queryForm" page="${page }"></tags:ajaxPagination>
