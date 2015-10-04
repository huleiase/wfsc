<%@ page language="java" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
            <table class="table table-bordered table-striped with-check">
              <thead>
               
              </thead>
              <tbody>
              <s:if test="#request.page.data.size>0">
                 <tr>
                 <td>
                 	<select name="allFabric" id="allFabric" style="width:366px;height:180px" multiple="multiple">
                 	 <s:iterator value="#request.page.data" var="obj">
	                 	 <option value='<s:property value="id"/>' >
	                 	 	<s:if test="#obj.isHtCode==1"><s:property value="htCode"/>
	                 	 	（<s:property value="vcDis"/>）
	                 	 	</s:if>
	                 	 	<s:else><s:property value="vcFactoryCode"/>——<s:property value="vcBefModel"/>
	                 	 	（<s:property value="vcDis"/>）
	                 	 	</s:else>
	                 	 </option>
                 	 </s:iterator>
	             	</select>
	             </td>
	             <td style="text-align: center;vertical-align: middle;" >
	             	<button class="label label-info btn btn-primary btn-mini" onclick="addFabric();">&gt;&gt;</button>
	             	<br><br><br>
	             	<button class="label label-info btn btn-primary btn-mini" onclick="delFabric();">&lt;&lt;</button>
	             </td>
                  <td>
                  <select name="selectedFabric" id="selectedFabric" style="width:366px;height:180px" multiple="multiple">
                  <s:iterator value="#request.selectedFabric" var="selectedobj">
                  <option value='<s:property value="id"/>'><s:property value="fabricCode"/></option>
                  </s:iterator>
                  </select>
                  </td>
                  
                </tr>
               
               </s:if>
                
              </tbody>
            </table>
          <tags:ajaxPagination divId="listTableDiv" queryFormId="queryForm" page="${page }"></tags:ajaxPagination>
