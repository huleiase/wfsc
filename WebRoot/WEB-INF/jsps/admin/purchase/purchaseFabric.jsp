<%@ page language="java" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
            <table class="table table-bordered table-striped" id="quoteFabricTable">
              <thead>
                <tr>
                  <th>序号</th>
                  <th>报价型号</th>
                   <th>色号</th>
                   <s:if test="#request.purchase.rilegou==1">
                    <th>原厂型号</th>
                    </s:if>
                  <th>幅宽</th>
                  <th>订货数量</th>
                  <th>报价数量</th>
                  <th>分段铺量</th>
                  <th>实际到货</th>
                  <th>出货数明细</th>
                   <th>类型</th>
                  <th>备注</th>
                <s:if test="#request.purchase.purchaseType==2">
                  <th>操作</th>
                  </s:if>
                </tr>
              </thead>
              <tbody>
              
              <s:if test="#request.quoteFabricList.size>0">
                <s:iterator value="#request.quoteFabricList" var="obj" status="st">
                <c:set value='${st.index}' var="vcIndex"/>
                 <c:set value='${st.index+1 }' var="vcCount"/>
                  <tr id="tr${vcCount }">
                  <input type="hidden" value="<s:property value="id"/>" id="id${vcCount }" name="quoteFabricList[${vcIndex }].id"/>
                 <td><s:property value="orderId"/></td>
			     <td><s:property value="vcModelNumDisplay"/></td>
			     <td><s:property value="vcColorNum"/></td>
			     <s:if test="#request.purchase.rilegou==1">
			     <td>
			     	<s:property value="vcFactoryCode"/>&nbsp;<s:property value="vcModelNum"/>
			     </td>
			     </s:if>
                 <td>
                 <div style="width:100px;">
                 	<s:property value="vcWidth"/>&nbsp;&nbsp;<s:property value="vcWidthUnit"/>
                 </div>
                 </td>
                  <td>
                  <div style="width:100px;">
                  	<s:property value="orderQuantity"/>&nbsp;&nbsp;
                  	<s:property value="vcOldPriceUnit"/>
                  </div>
                  </td>
     			  <td>
     			  <div style="width:100px;">
     			  	<s:property value="vcQuantity"/>&nbsp;&nbsp;
                  	<s:property value="vcPriceUnit"/>
     			  </div>
     			  </td>
     			 <td><input type="text" value="<s:property value="vcSubLay"/>" id="vcSubLay${vcCount }" name="quoteFabricList[${vcIndex }].vcSubLay"  class="widthShort"/></td>
			     <td><input type="text" value="<s:property value="vcRealityAog"/>" id="vcRealityAog${vcCount }" name="quoteFabricList[${vcIndex }].vcRealityAog"  class="widthShort"/></td>
			     <td><input type="text" value="<s:property value="vcShipmentNum"/>" id="vcShipmentNum${vcCount }" name="quoteFabricList[${vcIndex }].vcShipmentNum"  class="widthShort"/></td>
			     <td><input type="text" value="<s:property value="vcType"/>" id="vcType${vcCount }" name="quoteFabricList[${vcIndex }].vcType"  class="widthShort"/></td>
			     <td><input type="text" value="<s:property value="vcPurchaseRmk"/>" id="vcPurchaseRmk${vcCount }" name="quoteFabricList[${vcIndex }].vcPurchaseRmk" /></td>
                <s:if test="#request.purchase.purchaseType==2">
                <td>
                	<select id="vcAssignAutor${vcCount }" name="quoteFabricList[${vcIndex }].vcAssignAutor" style="width: 120px;">
                 		<option value="">请选择采购员</option>
		                 <s:iterator value="#request.userList">
		                  	<option value='<s:property value="username"/>' <c:if test="${obj.vcAssignAutor==username}">selected</c:if>><s:property value="username"/></option>
		                 </s:iterator>
                 	</select>
                 </td>
                </s:if>
                </tr>
               </s:iterator>
               </s:if>
               
              </tbody>
            </table>
