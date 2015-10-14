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
                  <th>报价单号</th>
                  <th>项目名称</th>
                  <th>销售姓名</th>
                  <th>询价方</th>
                   <th>设计方</th>
                  <th>报价地</th>
                  <th>创建人</th>
                  <th>日期</th>
                   <th>是否签单</th>
                  <th>审核状态</th>
                  <th>审核人</th>
                  <th>基本操作</th>
                </tr>
              </thead>
              <tbody>
              <s:if test="#request.page.data.size>0">
                <s:iterator value="#request.page.data" var="obj">
                 <tr>
                  <td><input type="checkbox" name="ids" value='<s:property value="id"/>'/></td>
                   <td><a class="tip-bottom" title="点击查看详细信息" href='<%=request.getContextPath()%>/admin/quote_input.Q?isView=1&id=<s:property value="id"/>' target="_blank"><s:property value="projectNum"/></a></td>
                  <td><s:property value="projectName"/></td>
                  <td><s:property value="vcSalesman"/></td>
                  <td><s:property value="vcAttn"/></td>
                  <td><s:property value="projectDesignComp"/></td>
                  <td><s:property value="vcQuoteLocal"/></td>
                  <td><s:property value="curUserName"/></td>
                 <td> <s:date name="vcFormDate" format="yyyy-MM-dd" /></td>
                 <td>
                  	<s:if test="#obj.isWritPerm==1">是</s:if>
                  	<s:else>否</s:else>
                  </td>
                   <td>
                  	<s:if test="#obj.vcAudit==1">已审核</s:if>
                  	<s:else>未审核</s:else>
                  </td>
                   <td><s:property value="auditPerson"/></td>
                  <td>
                  <w:permission permissionId="<%=PermissionId.QUOTE_MGT_UPDATE%>">
                  <button class="label label-info btn btn-primary btn-mini" onclick="updateOrCopy('<s:property value="id"/>');">修改</button>
                 </w:permission>
                 <w:permission permissionId="<%=PermissionId.QUOTE_MGT_AUDIT%>">
                  <s:if test="#obj.vcAudit!=1">
                  <button class="label label-info btn btn-primary btn-mini" onclick="auditQuote('<s:property value="id"/>','<s:property value="quoteFormate"/>');">审核</button>
                  </s:if>
                  </w:permission>
                  <w:permission permissionId="<%=PermissionId.QUOTE_MGT_WRITEPERM%>">
                  <s:if test="#obj.vcAudit==1 && #obj.isWritPerm!=1">
                  <button class="label label-info btn btn-primary btn-mini" onclick="writPerm('<s:property value="id"/>','<s:property value="quoteFormate"/>');">签单</button>
                   </s:if>
                   </w:permission>
                   <s:if test="#obj.vcAudit==1">
                   <w:permission permissionId="<%=PermissionId.QUOTE_MGT_PRINT%>">
                  <button class="label label-info btn btn-primary btn-mini" onclick="printQuote('<s:property value="id"/>');">打印</button>
                  </w:permission>
                  <w:permission permissionId="<%=PermissionId.QUOTE_MGT_DESIGN%>">
                  <button class="label label-info btn btn-primary btn-mini" onclick="designQuote('<s:property value="id"/>');">设计</button>
                 </w:permission>
                 </s:if>
                 <w:permission permissionId="<%=PermissionId.QUOTE_MGT_ATTACHMENT%>">
                  <button class="label label-info btn btn-primary btn-mini" onclick="uploadFile('<s:property value="id"/>');">上传</button>
                  <button class="label label-info btn btn-primary btn-mini" onclick="downloadFile('<s:property value="id"/>');">下载</button>
                  </w:permission>
                  </td>
                </tr>
               </s:iterator>
               </s:if>
                <s:else>
					<tr>
						<td colspan="15">
							 <div class="alert alert-block"> 
				               <h4 align="center" class="alert-heading">暂时没有符合条件的记录！</h4>
				             </div>
						</td>
					</tr>
				</s:else>
              </tbody>
            </table>
          <tags:ajaxPagination divId="listTableDiv" queryFormId="queryForm" page="${page }"></tags:ajaxPagination>
