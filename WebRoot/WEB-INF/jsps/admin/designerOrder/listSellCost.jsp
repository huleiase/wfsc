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
                  <th colspan="7">产品报价</th>
                  <th>销售成本</th>
                  <th colspan="8">销售费用</th>
                  <th rowspan="2">毛利</th>
                  <th rowspan="2">毛利率</th>
                </tr>
                 <tr>
                <th>材料合计</th>
                  <th>加工费</th>
                  <th>量窗费</th>
                  <th>安装费</th>
                  <th>运费</th>
                  <th>税率</th>
                  <th>合计</th>
                  <th>材料合计</th>
                  <th>加工费</th>
                  <th>安装费</th>
                  <th>运费</th>
                   <th>差旅费</th>
                   <th>设计费</th>
                  <th>税费</th>
                  <th>其他</th>
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
                     <td><s:property value="bjClTotel"/></td> 
                      <td><s:property value="vcProcessFre"/></td> 
                       <td><s:property value="lcFre"/></td> 
                        <td><s:property value="vcInstallFre"/></td> 
                         <td><s:property value="bjFreight"/></td>  
                         <td><s:property value="taxation"/></td> 
                          <td><s:property value="bjTotel"/></td>
                          <td><s:property value="cbClTotel"/></td> 
                          <td><s:property value="processFee"/></td> 
                          <td><s:property value="installFee"/></td> 
                          <td><s:property value="cbFreight"/></td> 
                          <td><s:property value="travelExpenses"/></td> 
                          <td><s:property value="designFre"/></td> 
                          <td><s:property value="taxationFee"/></td> 
                          <td><s:property value="otherFre"/></td> 
                          <td><s:property value="cbTotel"/></td>  
                          <td><s:property value="profit"/></td> 
                          <td><s:property value="profitRate"/></td>  
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
