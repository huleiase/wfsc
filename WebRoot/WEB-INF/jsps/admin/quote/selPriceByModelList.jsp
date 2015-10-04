<%@ page language="java" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
            <table class="table table-bordered table-striped">
              <thead>
                <tr>
                 <th>项目</th>
                  <th>描述</th>
                  <th>型号</th>
                   <th>色号</th>
                  <th>幅宽</th>
                  <th>最终单价</th>
                  <th>面价</th>
                   <th>货币</th>
                  <th>产地</th>
                  <th>特殊费用</th>
                   <th>數量</th>
                  <th>折扣</th>
                  <th>金額</th>
                </tr>
              </thead>
              <tbody>
              <s:if test="#request.page.data.size>0">
                <s:iterator value="#request.page.data" var="obj">
                 <tr>
                  <td><s:property value="vcProject"/></td>
                  <td><s:property value="vcDes"/></td>
                  <td><s:property value="vcModelNumDisplay"/></td>
                  <td><s:property value="vcColorNum"/></td>
                  <td><s:property value="vcWidth"/> <s:property value="vcWidthUnit"/></td>
                  <td><s:property value="vcPrice"/> <s:property value="vcPriceUnit"/></td>
                  <td><s:property value="vcOldPrice"/> <s:property value="vcOldPriceUnit"/></td>
                  <td><s:property value="vcMoney"/></td>
                  <td><s:property value="vcProduceLocal"/></td>
                  <td><s:property value="vcSpecialExp"/></td>
                   <td><s:property value="vcQuantity"/></td>
                  <td><s:property value="vcDiscount"/></td>
                  <td><s:property value="vcTotal"/></td>
                 
                </tr>
               </s:iterator>
               </s:if>
                <s:else>
					<tr>
						<td colspan="25">
							 <div class="alert alert-block"> 
				               <h4 align="center" class="alert-heading">暂时没有符合条件的记录！</h4>
				             </div>
						</td>
					</tr>
				</s:else>
              </tbody>
            </table>
          <tags:ajaxPagination divId="listTableDiv" queryFormId="queryForm" page="${page }"></tags:ajaxPagination>
