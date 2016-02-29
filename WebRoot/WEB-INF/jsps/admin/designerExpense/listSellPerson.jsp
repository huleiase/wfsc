<%@ page language="java" pageEncoding="UTF-8" import="java.util.*"%>
<%@page import="com.wfsc.common.utils.PermissionId"%>
<%@ taglib prefix="w" uri="/WEB-INF/wfsc.tld"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
            <table class="table table-bordered table-striped with-check" style="background-color: #f9f9f9;">
              <thead>
                <tr>
                  <th>时间</th>
                  <th>合同号</th>
                  <th>客户名称</th>
                  <th>项目</th>
                  <th>合同金额</th>
                  <th>加工费</th>
                  <th>安装费</th>
                  <th>加急费</th>
                  <th>运费</th>
                  <th>税费</th>
                  <th>后处理</th>
                  <th>其它</th>
                  <th>实际金额</th>
                  <th>设计师</th>
                  <th>比例</th>
                  <th>设计费金额</th>
                  <th>实际收款金额</th>
                  <th>是否收齐款</th>
                  <th>是否支付</th>
                  <th>支付时间</th>
                  <th>备注</th>
                </tr>
              </thead>
              <tbody>
              <s:if test="#request.page.data.size>0">
                <s:iterator value="#request.page.data" var="obj">
                 <tr>
                  <td> <s:date name="createDate" format="yyyy-MM-dd" /></td>
                   <td><s:property value="contractNo"/></td>
                    <td><s:property value="customerName"/></td>
                     <td><s:property value="projectName"/></td>
                      <td><s:property value="sumMoney"/></td>
                       <td><s:property value="vcProcessFre"/></td>
                       <td><s:property value="vcInstallFre"/></td>
                       <td><s:property value="urgentCost"/></td>
                        <td><s:property value="freight"/></td>
                         <td><s:property value="taxationCost"/></td>
                          <td><s:property value="vcAftertreatment"/></td>
                           <td><s:property value="vcOther"/></td>
                            <td><s:property value="realTotel"/></td>
                             <td><s:property value="designer1"/></td>
                              <td><s:property value="counselorRate1"/></td>
                               <td><s:property value="designMony1"/></td>
                                <td><s:property value="hasApply1"/></td>
                                 <td><s:property value="isGetAll1"/></td>
                                  <td><s:property value="isApply1"/></td>
                                  <td> <s:date name="applyDate1" format="yyyy-MM-dd" /></td>
                                 <td><s:property value="remark1"/></td>
                </tr>
               </s:iterator>
               </s:if>
                <s:else>
					<tr>
						<td colspan="23">
							 <div class="alert alert-block"> 
				               <h4 align="center" class="alert-heading">暂时没有符合条件的记录！</h4>
				             </div>
						</td>
					</tr>
				</s:else>
              </tbody>
            </table>
          <tags:ajaxPagination divId="listTableDiv" queryFormId="queryForm" page="${page }"></tags:ajaxPagination>
