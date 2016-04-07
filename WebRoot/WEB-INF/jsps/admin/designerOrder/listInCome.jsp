<%@ page language="java" pageEncoding="UTF-8" import="java.util.*"%>
<%@page import="com.wfsc.common.utils.PermissionId"%>
<%@ taglib prefix="w" uri="/WEB-INF/wfsc.tld"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
           <table class="table table-bordered" style="max-width: none;width:200%;">
              <thead>
                <tr>
                <th>PO号</th>
                  <th>收款时间</th>
                  <th>合同号</th>
                  <th>客户名称</th>
                  <th>项目</th>
                  <th>销售人员</th>
                  <th>设计师</th>
                  <th>合同金额</th>
                  <th>本地比例</th>
                  <th>本地收入金额</th>
                  <th>分摊地</th>
                  <th>分摊比例</th>
                  <th>分摊金额</th>
                  <th>设计费</th>
                  <th>税率</th>
                  <th>是否开具发票</th>
                  <th>订金</th>
                  <th>进度款1</th>
                  <th>进度款2</th>
                  <th>进度款3</th>
                   <th>保质金</th>
                  <th>已付合计</th>
                  <th>未付余额</th>
                   <th>是否已付清</th>
                  <th>收款地</th>
                  <th>备注</th>
                </tr>
              </thead>
              <tbody>
              <s:if test="#request.page.data.size>0">
                <s:iterator value="#request.page.data" var="obj">
                 <tr>
                  <td><s:property value="orderNo"/></td>
                  <td> <s:date name="gatheringDate" format="yyyy-MM-dd" /></td>
                   <td><s:property value="contractNo"/></td>
                    <td><s:property value="customerName"/></td>
                     <td><s:property value="projectName"/></td>
                     <td><s:property value="vcSalesman"/></td>
                     <td><s:property value="designer1"/>,<s:property value="designer2"/>,<s:property value="designer3"/></td>
                      <td><s:property value="sumMoney"/></td>
                     <td><s:property value="realRate"/></td> 
                      <td><s:property value="realTotel"/></td> 
                       <td><s:property value="shareArea1"/>,<s:property value="shareArea2"/>,<s:property value="shareArea3"/>
                       <s:property value="shareArea4"/>,<s:property value="shareArea5"/></td> 
                        <td><s:property value="shareRate1"/>,<s:property value="shareRate2"/>,<s:property value="shareRate3"/>
                        <s:property value="shareRate4"/>,<s:property value="shareRate5"/></td> 
                         <td><s:property value="shareMony1"/>,<s:property value="shareMony2"/>,<s:property value="shareMony3"/>
                         <s:property value="shareMony4"/>,<s:property value="shareMony5"/></td>  
                         <td><s:property value="designFre"/></td> 
                          <td><s:property value="taxation"/></td>
                          <td><s:property value="isInvoice"/></td> 
                          <td><s:property value="frontMoney"/></td> 
                          <td><s:property value="planMoney1"/></td> 
                          <td><s:property value="planMoney2"/></td> 
                          <td><s:property value="planMoney3"/></td> 
                          <td><s:property value="qualityMoney"/></td> 
                          <td><s:property value="hasApplyTotle"/></td> 
                          <td><s:property value="hasNoApplyTotle"/></td> 
                          <td><s:property value="isPayTotle"/></td> 
                          <td><s:property value="gatheringArea"/></td> 
                          <td><s:property value="remark"/></td>  
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
