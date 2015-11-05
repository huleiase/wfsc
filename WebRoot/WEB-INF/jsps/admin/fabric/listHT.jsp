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
                  <th>原厂型号</th>
                  <th>色号</th>
                  <th>HT型号</th>
                  <th>书号</th>
                  <th>品牌</th>
                  <th>状态</th>
                  <th>操作</th>
                </tr>
              </thead>
              <tbody>
              <s:if test="#request.page.data.size>0">
                <s:iterator value="#request.page.data" var="obj">
                 <tr>
                  <td><input type="checkbox" name="ids" value='<s:property value="id"/>'/></td>
                   <td><s:property value="vcFactoryCode"/></td>
                  <td><a title="点击查看原厂型号信息" class="tip-bottom" onclick='gotoFabricPage("<s:property value="vcFactoryCode"/>","<s:property value="vcBefModel"/>")' href='javascript:void(0);'><s:property value="vcBefModel"/></a></td>
                  <td><s:property value="colorCode"/></td>
                  <td><a title="点击查看HT型号信息" class="tip-bottom" href='<%=request.getContextPath()%>/admin/fabric_detailHT.Q?id=<s:property value="id"/>' target="_blank"><s:property value="htCode"/></a></td>
                  <td><s:property value="bookNo"/></td>
                   <td><s:property value="brand"/></td>
                  <td><s:property value="vcDis"/></td>
                  <td>
                  <w:permission permissionId="<%=PermissionId.BASIC_HTFABRIC_MGT_UPDATE%>">
                  <button class="label label-info btn btn-primary btn-mini" onclick="addOrUpdate('<s:property value="id"/>');">修改</button>
                  </w:permission>
                  </td>
                </tr>
               </s:iterator>
               </s:if>
                <s:else>
					<tr>
						<td colspan="13">
							 <div class="alert alert-block"> 
				               <h4 align="center" class="alert-heading">暂时没有符合条件的记录！</h4>
				             </div>
						</td>
					</tr>
				</s:else>
              </tbody>
            </table>
          <tags:ajaxPagination divId="listTableDiv" queryFormId="queryForm" page="${page }"></tags:ajaxPagination>
