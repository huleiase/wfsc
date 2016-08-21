<%@ page language="java" pageEncoding="UTF-8" import="java.util.*"%>
<%@page import="com.wfsc.common.utils.PermissionId"%>
<%@ taglib prefix="w" uri="/WEB-INF/wfsc.tld"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
            <table class="table table-bordered" style="max-width: none;width:200%;">
              <thead>
                <tr>
                <th>时间</th>
                <th>PO号</th>
                  <th>合同号</th>
                  <th>客户名称</th>
                  <th>项目</th>
                  <th>销售人员</th>
                  <th>设计师</th>
                  <th>合同金额</th>
                  <th>GZ</th>
                  <th>SH</th>
                  <th>BJ</th>
                  <th>SZ</th>
                  <th>HK</th>
                  <th>分摊合计</th>
                  <th>实际金额</th>
                  <th>类型</th>
                  <th>发票</th>
                  <th>备注</th>
                </tr>
              </thead>
              <tbody>
              <s:if test="#request.page.data.size>0">
                <s:iterator value="#request.page.data" var="obj">
                 <tr>
                  <td> <s:date name="createDate" format="yyyy-MM-dd HH:mm:ss" /></td>
                  <td><s:property value="orderNo"/></td>
                   <td><s:property value="contractNo"/></td>
                    <td><s:property value="customerName"/></td>
                     <td><s:property value="projectName"/></td>
                     <td><s:property value="vcSalesman"/></td>
                     <td><s:property value="designer1"/>,<s:property value="designer2"/>,<s:property value="designer3"/></td>
                      <td><s:property value="sumMoney"/></td>
                     <td><s:property value="shareMony1"/></td> 
                      <td><s:property value="shareMony2"/></td> 
                       <td><s:property value="shareMony3"/></td> 
                        <td><s:property value="shareMony4"/></td> 
                         <td><s:property value="shareMony5"/></td>  
                         <td><s:property value="sharetotle"/></td> 
                          <td><s:property value="realTotel"/></td>
                          <td><s:property value="type"/></td> 
                          <td><s:property value="isInvoice"/></td> 
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
