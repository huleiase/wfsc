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
                  <th>发起人</th>
                  <th>公告名</th>
                  <th>公告内容</th>
                  <th>公告时间</th>
                  <th>操作</th>
                </tr>
              </thead>
              <tbody>
              <s:if test="#request.page.data.size>0">
                <s:iterator value="#request.page.data" var="obj">
                 <tr>
                  <td><input type="checkbox" name="ids" value='<s:property value="id"/>'/></td>
                  <td><s:property value="vcUser"/></td>
                  <td><s:property value="vcName"/></td>
                  <td><s:property value="vcMemo"/></td>
                  <td><s:data value="dtsTime" format="yyyy-MM-dd"/>--<s:data value="dteTime" format="yyyy-MM-dd"/></td>
                  <td> 
                  <w:permission permissionId="<%=PermissionId.SYSTEM_LOG_MGT_UPDATE%>">
                  <button class="label label-info btn btn-primary btn-mini" onclick="addOrUpdate('<s:property value="id"/>');">修改</button>
                  </w:permission>
                  </td>
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
