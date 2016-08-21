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
                  <th>合同号</th>
                  <th>客户名称</th>
                  <th>项目</th>
                  <th>合同金额</th>
                  <th>本地比例</th>
                  <th>本地收入金额</th>
                  <th>分摊地</th>
                  <th>分摊比例</th>
                   <th>分摊地</th>
                  <th>分摊比例</th>
                   <th>分摊地</th>
                  <th>分摊比例</th>
                   <th>分摊地</th>
                  <th>分摊比例</th>
                   <th>分摊地</th>
                  <th>分摊比例</th>
                  <th>设计师1</th>
                  <th>设计费1</th>
                  <th>设计师2</th>
                  <th>设计费2</th>
                  <th>设计师3</th>
                  <th>设计费3</th>
                  <th>备注</th>
                </tr>
              </thead>
              <tbody>
              <s:if test="#request.page.data.size>0">
                <s:iterator value="#request.page.data" var="obj">
                 <tr>
                  <td> <s:date name="createDate" format="yyyy-MM-dd HH:mm:ss" /></td>
                   <td><s:property value="contractNo"/></td>
                    <td><s:property value="customerName"/></td>
                     <td><s:property value="projectName"/></td>
                      <td><s:property value="sumMoney"/></td>
                       <td><s:property value="realRate"/></td>
                       <td><s:property value="realTotel"/></td>
                       <td><s:property value="shareArea1"/></td>
                        <td><s:property value="shareRate1"/></td>
                        <td><s:property value="shareArea2"/></td>
                        <td><s:property value="shareRate2"/></td>
                        <td><s:property value="shareArea3"/></td>
                        <td><s:property value="shareRate3"/></td>
                        <td><s:property value="shareArea4"/></td>
                        <td><s:property value="shareRate4"/></td>
                        <td><s:property value="shareArea5"/></td>
                        <td><s:property value="shareRate5"/></td>
                         <td><s:property value="designer1"/></td>
                          <td><s:property value="designMony1"/></td>
                           <td><s:property value="designer2"/></td>
                          <td><s:property value="designMony2"/></td>
                           <td><s:property value="designer3"/></td>
                          <td><s:property value="designMony3"/></td>
                           <td><s:property value="remark"/></td>
                </tr>
               </s:iterator>
               </s:if>
                <s:else>
					<tr>
						<td colspan="23">
							 <div class="alert alert-block"> 
				               <h4 align="center" class="alert-heading">暂时没有符合条件的记录！</h4>
				             </div>
						</td>
					</tr>
				</s:else>
              </tbody>
            </table>
          <tags:ajaxPagination divId="listTableDiv" queryFormId="queryForm" page="${page }"></tags:ajaxPagination>
