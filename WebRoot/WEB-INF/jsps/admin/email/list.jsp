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
                  <th>内容</th>
                  <th>操作</th>
                </tr>
              </thead>
              <tbody>
              <s:if test="#request.page.data.size>0">
                <s:iterator value="#request.page.data" var="obj">
                 <tr>
                  <td><input type="checkbox" name="ids" value='<s:property value="id"/>'/></td>
                  <td><s:property value="detail"/></td>
                  <td>
                  <s:if test="#obj.status==0">
                   <button class="label label-info btn btn-primary btn-mini" onclick="updateStatus('<s:property value="id"/>');">标为已读</button>
                  </s:if>
                  <s:else>
                   <button class="label label-info btn btn-primary btn-mini" onclick="handle('<s:property value="id"/>','<s:property value="status"/>','<s:property value="action"/>','<s:property value="quoteId"/>','<s:property value="purchaseId"/>','<s:property value="orderId"/>');">去处理</button>
                  </s:else>
                 
                  </td>
                </tr>
               </s:iterator>
               </s:if>
                <s:else>
					<tr>
						<td colspan="3">
							 <div class="alert alert-block"> 
				               <h4 align="center" class="alert-heading">暂时没有符合条件的记录！</h4>
				             </div>
						</td>
					</tr>
				</s:else>
              </tbody>
            </table>
          <tags:ajaxPagination divId="listTableDiv" queryFormId="queryForm" page="${page }"></tags:ajaxPagination>
