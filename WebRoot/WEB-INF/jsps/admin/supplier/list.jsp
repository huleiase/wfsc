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
                  <th>工厂代码</th>
                  <th>名称</th>
                  <th>产地</th>
                  <th>联系人</th>
                  <th>电话</th>
                  <th>传真</th>
                  <th>邮箱</th>
                  <th>状态</th>
                  <th>操作</th>
                </tr>
              </thead>
              <tbody>
              <s:if test="#request.page.data.size>0">
                <s:iterator value="#request.page.data" var="obj">
                 <tr>
                  <td><input type="checkbox" name="ids" value='<s:property value="id"/>'/></td>
                  <td><a class="tip-bottom" title="点击查看详细信息" href='<%=request.getContextPath()%>/admin/supplier_detail.Q?id=<s:property value="id"/>' target="_blank"><s:property value="vcFactoryCode"/></a></td>
                  <td><s:property value="vcFactoryName"/></td>
                  <td><s:property value="vcPlaceProduce"/></td>
                  <td><s:property value="vcLinkMan"/></td>
                   <td><s:property value="vcTel"/></td>
                  <td><s:property value="vcFax"/></td>
                   <td><s:property value="vcEmail"/></td>
                  <td><s:property value="vcDis"/></td>
                  <td>
                   <w:permission permissionId="<%=PermissionId.BASIC_SUPPLIER_MGT_UPDATE%>">
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
