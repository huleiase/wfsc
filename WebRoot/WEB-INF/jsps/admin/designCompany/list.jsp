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
                  <th>公司名称</th>
                  <th>电话</th>
                  <th>传真</th>
                  <th>联系人</th>
                  <th>手机</th>
                  <th>状态</th>
                  <th>操作</th>
                </tr>
              </thead>
              <tbody>
              <s:if test="#request.page.data.size>0">
                <s:iterator value="#request.page.data" var="obj">
                 <tr>
                  <td><input type="checkbox" name="ids" value='<s:property value="id"/>'/></td>
                  <td><a title="点击查看详细信息" href='<%=request.getContextPath()%>/admin/designCompany_detail.Q?id=<s:property value="id"/>' target="_blank"><s:property value="companyName"/></a></td>
                  <td><s:property value="tel"/></td>
                  <td><s:property value="fax"/></td>
                  <td><s:property value="linkman"/></td>
                   <td><s:property value="phone"/></td>
                  <td><s:property value="dis"/></td>
                  <td>
                   <w:permission permissionId="<%=PermissionId.BASIC_DESIGNCOMPANY_MGT_UPDATE%>">
                  <button class="label label-info btn btn-primary btn-mini" onclick="addOrUpdate('<s:property value="id"/>');">修改</button>
                  </w:permission>
                  </td>
                </tr>
               </s:iterator>
               </s:if>
                <s:else>
					<tr>
						<td colspan="10">
							 <div class="alert alert-block"> 
				               <h4 align="center" class="alert-heading">暂时没有符合条件的记录！</h4>
				             </div>
						</td>
					</tr>
				</s:else>
              </tbody>
            </table>
          <tags:ajaxPagination divId="listTableDiv" queryFormId="queryForm" page="${page }"></tags:ajaxPagination>
