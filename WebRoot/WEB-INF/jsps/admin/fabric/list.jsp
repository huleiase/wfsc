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
                  <th>幅宽</th>
                  <th>类型</th>
                  <th>卷长</th>
                  <th>最小分格</th><!--
                  <th>成分</th>
                  --><th>密度</th>
                  <th>原始进价</th>
                  <th>采购折扣</th>
                  <th>起订量</th>
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
                  <td><a title="点击查看详细信息" class="tip-bottom" href='<%=request.getContextPath()%>/admin/fabric_detail.Q?id=<s:property value="id"/>' target="_blank"><s:property value="vcBefModel"/></a></td>
                  <td><s:property value="vcWidth"/></td>
                  <td><s:property value="vcType"/></td>
                  <td><s:property value="vcPieceLength"/></td>
                   <td><s:property value="vcMinLattice"/></td><!--
                  <td><s:property value="vcComposition"/></td>
                   --><td><s:property value="vcDensity"/></td>
                     <td><s:property value="vcOldPrice"/>/<s:property value="vcPriceCur"/>/<s:property value="vcMeasure"/></td>
                  <td><s:property value="vcPurDis"/></td>
                   <td><s:property value="vcMinNum"/></td>
                  <td><s:property value="vcDis"/></td>
                  <td>
                   <w:permission permissionId="<%=PermissionId.BASIC_FABRIC_MGT_UPDATE%>">
                  <button class="label label-info btn btn-primary btn-mini" onclick="addOrUpdate('<s:property value="id"/>');">修改</button>
                  </w:permission>
                  </td>
                </tr>
               </s:iterator>
               </s:if>
                <s:else>
					<tr>
						<td colspan="14">
							 <div class="alert alert-block"> 
				               <h4 align="center" class="alert-heading">暂时没有符合条件的记录！</h4>
				             </div>
						</td>
					</tr>
				</s:else>
              </tbody>
            </table>
          <tags:ajaxPagination divId="listTableDiv" queryFormId="queryForm" page="${page }"></tags:ajaxPagination>
