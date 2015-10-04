<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.wfsc.common.utils.PermissionId"%>
<%@ taglib prefix="w" uri="/WEB-INF/wfsc.tld"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
            <table class="table table-bordered table-striped with-check">
              <thead>
                <tr>
                  <th><input type="checkbox" id="title-table-checkbox" name="title-table-checkbox" /></th>
                  <th>用户名</th>
                  <th>角色</th>
                  <th>中文名</th>
                   <th>地区</th>
                   <th>邮箱</th>
                  <th>手机</th>
                  <th>最后登陆时间</th>
                  <th>状态</th>
                  <th>操作</th>
                </tr>
              </thead>
              <tbody>
              <s:if test="#request.adminList.size>0">
                <s:iterator value="#request.adminList" var="admin">
                 <tr>
                  <td><input type="checkbox" name="adminIds" value='<s:property value="id"/>'/></td>
                  <td><s:property value="username"/></td>
                  <td><s:property value="roleString"/></td>
                  <td><s:property value="zhname"/></td>
                  <td><s:property value="area"/></td>
                   <td><s:property value="email"/></td>
                  <td><s:property value="phone"/></td>
                  <td><s:date name="lastLoginDate" format="yyyy-MM-dd hh:mm:ss" /></td>
                  <td>
                  	<s:if test="#admin.status==1">启用</s:if>
                  	<s:else>禁用</s:else>
                  </td>
                  
                  <td>
                   <w:permission permissionId="<%=PermissionId.SYSTEM_ADMIN_MGT_UPDATE%>">
                  <button class="label label-info btn btn-primary btn-mini" onclick="updateAdmin('<s:property value="username"/>');">修改</button>
                  </w:permission>
                  </td>
                </tr>
               </s:iterator>
               </s:if>
                <s:else>
					<tr>
						<td colspan="9">
							 <div class="alert alert-block"> 
				               <h4 align="center" class="alert-heading">暂时没有符合条件的记录！</h4>
				             </div>
						</td>
					</tr>
				</s:else>
              </tbody>
            </table>
          <tags:ajaxPagination scripts="ajaxGetAdminList" divId="adminTableId" queryFormId="adminQueryForm" page="${page }"></tags:ajaxPagination>
