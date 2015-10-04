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
                  <th>项目名称</th>
                  <th>报价单号</th>
                  <th>订单号</th>
                  <th>供应商</th>
                   <th>支付方式</th>
                  <th>原厂型号</th>
                  <th>HT型号</th>
                  <th>色号</th>
                   <th>实订量</th>
                  <th>分铺段量</th>
                  <th>实际到货</th>
                   <th>剩余数量</th>
                  <th>单位</th>
                  <th>经手人</th>
                   <th>位置</th>
                  <th>入库日期</th>
                  <th>图片</th>
                  <th>操作</th>
                </tr>
              </thead>
              <tbody>
              <s:if test="#request.page.data.size>0">
                <s:iterator value="#request.page.data" var="obj">
                 <tr>
                  <td><input type="checkbox" name="ids" value='<s:property value="id"/>'/></td>
                  <td><s:property value="vcProject"/></td>
                   <td><s:property value="quoteNum"/></td>
                  <td><s:property value="orderNo"/></td>
                  <td><s:property value="supplie"/></td>
                  <td><s:property value="payment"/></td>
                  <td><s:property value="vcModelNum"/></td>
                   <td><s:property value="htCode"/></td>
                  <td><s:property value="vcColorNum"/></td>
                  <td><s:property value="vcQuoteNum"/></td>
                  <td><s:property value="vcSubLay"/></td>
                  <td><s:property value="vcRealityAog"/></td>
                  <td><s:property value="surplus"/></td>
                  <td><s:property value="unit"/></td>
                  <td><s:property value="vcAssignAutor"/></td>
                  <td><s:property value="vcAddr"/></td>
                  <td><s:date name="inStoreDate" format="yyyy-MM-dd" /></td>
                  <td>
                  	<a target="_blank" title="点击查看图片" href='<s:property value="fileName"/>' rel="attachment" class="tip-bottom" style="display: inline-block; width: 60px;">
				     		<img width="30" height="20" alt="暂无图片" src='<s:property value="fileName"/>' /> 
				     </a>
                  </td>
                  <td>
                  <w:permission permissionId="<%=PermissionId.PROCESS_STOCk_MGT_UPDATE%>">
                  <button class="label label-info btn btn-primary btn-mini" onclick="addOrUpdate('<s:property value="id"/>');">修改</button>
                  </w:permission>
                  <w:permission permissionId="<%=PermissionId.PROCESS_STOCk_MGT_TRANSFER%>">
                  <button class="label label-info btn btn-primary btn-mini" onclick="transfer('<s:property value="id"/>');">转移</button>
                  </w:permission>
                  <w:permission permissionId="<%=PermissionId.PROCESS_STOCk_MGT_SHIPMENTS%>">
                  <button class="label label-info btn btn-primary btn-mini" onclick="shipments('<s:property value="id"/>');">发货</button>
                  </w:permission>
                  <w:permission permissionId="<%=PermissionId.PROCESS_STOCk_MGT_RECORD%>">
                  <button class="label label-info btn btn-primary btn-mini" onclick="record('<s:property value="vcFactoryCode"/>','<s:property value="vcModelNum"/>');">记录</button>
                  </w:permission>
                  <w:permission permissionId="<%=PermissionId.PROCESS_STOCk_MGT_ATTACHMENT%>">
                  <button class="label label-info btn btn-primary btn-mini" onclick="uploadFile('<s:property value="vcFactoryCode"/>','<s:property value="vcModelNum"/>');">上传图片</button>
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
