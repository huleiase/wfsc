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
                  <th>仓库名</th>
                  <th>仓库所属地</th>
                  <th>仓库地址</th>
                   <th>仓库电话</th>
                  <th>仓库传真</th>
                  <th>联系方式</th>
                   <th>备注</th>
                  <th>创建人</th>
                  <th>所属人</th>
                  <th>操作</th>
                </tr>
              </thead>
              <tbody>
              <s:if test="#request.stores.size>0">
                <s:iterator value="#request.stores" var="obj">
                 <tr>
                  <td><input type="checkbox" name="ids" value='<s:property value="id"/>'/></td>
                  <td><s:property value="storeName"/></td>
                  <td><s:property value="vcLocal"/></td>
                  <td><s:property value="storeAddr"/></td>
                   <td><s:property value="tel"/></td>
                  <td><s:property value="fax"/></td>
                  <td><s:property value="linkMethod"/></td>
                   <td><s:property value="remark"/></td>
                  <td><s:property value="creater"/></td>
                  <td><s:property value="userName"/></td>
                  <td>
                  <w:permission permissionId="<%=PermissionId.PROCESS_STOCk_MGT_UPDATE%>">
                  <button class="label label-info btn btn-primary btn-mini" onclick="addOrUpdate('<s:property value="id"/>');">修改</button>
                  </w:permission>
                  <w:permission permissionId="<%=PermissionId.PROCESS_STOCk_MGT_TRANSFER%>">
                  <button class="label label-info btn btn-primary btn-mini" onclick="transferAll('<s:property value="id"/>');">转移</button>
                  </w:permission>
                  <w:permission permissionId="<%=PermissionId.PROCESS_STOCk_MGT_ADD%>">
                  <button class="label label-info btn btn-primary btn-mini" onclick="importExcel('<s:property value="id"/>');">导入</button>
                  </w:permission>
                  <w:permission permissionId="<%=PermissionId.PROCESS_STOCk_MGT_FABRIC%>">
                  <button class="label label-info btn btn-primary btn-mini" onclick="showStoreFabric('<s:property value="id"/>');">产品</button>
                 </w:permission>
                  </td>
                </tr>
               </s:iterator>
               </s:if>
                <s:else>
					<tr>
						<td colspan="11">
							 <div class="alert alert-block"> 
				               <h4 align="center" class="alert-heading">暂时没有符合条件的记录！</h4>
				             </div>
						</td>
					</tr>
				</s:else>
              </tbody>
            </table>
