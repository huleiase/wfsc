<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单-设计</title>
 <script language="javascript" src="../../themes/com/script.js"></script>
 <script language="javascript" src="../../js/util.js"></script>
 <script language="javascript" src="../../js/search.js"></script>
 
 
<script language="javascript" src="${ctx}/My97DatePicker/WdatePicker.js"></script>
<%@ include file="/common/meta.jsp"%>
    <link href="${ctx}/css/style.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/css/search.css" rel="stylesheet" type="text/css" />
     <link href="${ctx}/themes/com/style.css" rel="stylesheet" type="text/css" />
    <style type="text/css">
    	.suggest{
		position: absolute;
		background-color: #FFFFFF;
		text-align: left;
		border: 1px solid #000000;
		left:600;
		width:100px;;
		display: none;
	}
	.tw{
		background: #DCDCDC;
	}
    </style>
<script language="javascript">

		    function myhideDiv(divID){
		    	document.getElementById(divID).style.display="none";
			}

			function formsubmit2(){
				   document.forms[0].submit();
			}
			
			function setShareMoney(obj){
				var jObj =  $(obj);
				var objId = jObj.attr("id");
				var objValue = jObj.val();
				var idAttr = objId.split("_");
				var suffixId  = idAttr[1];
				if(objValue==""){
					return ;
				}
				var shareMonew = Number($("#sumMoney").val())*Number(objValue)/100;
				$("#shareMony_"+suffixId).val(shareMonew.toFixed(2));
				
			}
			
			function setShareRate(obj){
				var jObj =  $(obj);
				var objId = jObj.attr("id");
				var objValue = jObj.val();
				var idAttr = objId.split("_");
				var suffixId  = idAttr[1];
				if(objValue==""){
					return ;
				}
				var shareRate = Number(objValue)/Number($("#sumMoney").val())*100;
				$("#shareRate_"+suffixId).val(shareRate.toFixed(2));
				
			}
			
			function setSharetotle(){
				var shareMony1 = $("#shareMony_1").val();
				if(!shareMony1){
					shareMony1 = 0;
				}
				var shareMony2 = $("#shareMony_2").val();
				if(!shareMony2){
					shareMony2 = 0;
				}
				var shareMony3 = $("#shareMony_3").val();
				if(!shareMony3){
					shareMony3 = 0;
				}
				var shareMony4 = $("#shareMony_4").val();
				if(!shareMony4){
					shareMony4 = 0;
				}
				var shareMony5 = $("#shareMony_5").val();
				if(!shareMony5){
					shareMony5 = 0;
				}
				var sharetotle = Number(shareMony1)+Number(shareMony2)+Number(shareMony3)+Number(shareMony4)+Number(shareMony5);
				$("#sharetotle").val(sharetotle.toFixed(2));
				
			}
			
			function setHasApplyTotle(){
				var frontMoney = $("#frontMoney").val();
				if(!frontMoney){
					frontMoney = 0;
				}
				var planMoney1 = $("#planMoney1").val();
				if(!planMoney1){
					planMoney1 = 0;
				}
				var planMoney2 = $("#planMoney2").val();
				if(!planMoney2){
					planMoney2 = 0;
				}
				var planMoney3 = $("#planMoney3").val();
				if(!planMoney3){
					planMoney3 = 0;
				}
				var qualityMoney = $("#qualityMoney").val();
				if(!qualityMoney){
					qualityMoney = 0;
				}
				var hasApplyTotle = Number(frontMoney)+Number(planMoney1)+Number(planMoney2)+Number(planMoney3)+Number(qualityMoney);
				$("#hasApplyTotle").val(hasApplyTotle.toFixed(2));
			}
			
			function setNoApply(){
				var sumMony = Number($("#sumMoney").val()).toFixed(2);
				var hasApplyTotle = $("#hasApplyTotle").val();
				if(!hasApplyTotle){
					hasApplyTotle=0
				}
				$("#hasNoApplyTotle").val(sumMony-hasApplyTotle);
			}
			
			function setYuE(){
				var sumMony = Number($("#sumMoney").val()).toFixed(2);
				var mon1 = $("#mon1").val();
				if(!mon1){
					mon1=0;
				}
				var mon2 = $("#mon2").val();
				if(!mon2){
					mon2=0;
				}
				var mon3 = $("#mon3").val();
				if(!mon3){
					mon3=0;
				}
				var mon4 = $("#mon4").val();
				if(!mon4){
					mon4=0;
				}
				var mon5 = $("#mon5").val();
				if(!mon5){
					mon5=0;
				}
				var mon6 = $("#mon6").val();
				if(!mon6){
					mon6=0;
				}
				var mon7 = $("#mon7").val();
				if(!mon7){
					mon7=0;
				}
				var mon8 = $("#mon8").val();
				if(!mon8){
					mon8=0;
				}
				var mon9 = $("#mon9").val();
				if(!mon9){
					mon9=0;
				}
				var mon10 = $("#mon10").val();
				if(!mon10){
					mon10=0;
				}
				var mon11 = $("#mon11").val();
				if(!mon11){
					mon11=0;
				}
				var mon12 = $("#mon12").val();
				if(!mon12){
					mon12=0;
				}
				var yue = sumMony-mon1-mon2-mon3-mon4-mon5-mon6-mon7-mon8-mon9-mon10-mon11-mon12;
				$("#yue").val(yue);
			}
			$(function(){
				setNoApply();
				setYuE();
			});
    </script>
	<style>
	 .tw{
	 width:100px;}
	 .tw1{
	 width:60px;}
	 .tw2{
	 width:70px;}
	 .tw3{
	 width:750px;height:200px;}
	 .tw4{
	 width:50px;}
	</style>
</head>

<body>
<s:form id="inputForm" name="inputForm" method="post" action="order!saveDesignOrder.jspx">
<input type="hidden" name="id" value="${id}"/>
<input type="hidden" name="sumMoney" value="${sumMoney}" id="sumMoney"/>

<table width="100%" border="0" cellspacing="0" cellpadding="0" >
  <tr>
    <td height="30" colspan="4" background="../../css/images/tab_05.gif">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="12" height="30"><img src="../../css/images/tab_03.gif" width="12" height="30" /></td>
        <td>订单-设计</td>
        <td width="16"><img src="../../css/images/tab_07.gif" width="16" height="30" /></td>
      </tr>
    </table>
    </td>
  </tr>
  <tr>
    <td colspan="4"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="8" background="../../css/images/tab_12.gif">&nbsp;</td>
        <td>
        <table width="100%" border="0px" cellpadding="0" cellspacing="1" bgcolor="b5d6e6" >
            <tr>
                <td width="9%"><label></label>
                <label></label></td>
                <td width="8%">分摊地：&nbsp;&nbsp;&nbsp;&nbsp;  &nbsp;&nbsp;&nbsp;&nbsp;
                  <label></label></td>
                <td width="16%"><input name="designerOrder.shareArea1" id="shareArea_1" type="text" value="GZ" readonly="readonly"/></td>
                <td width="12%">分摊比例：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   
                  <label></label></td>
                <td width="17%"><input name="designerOrder.shareRate1" id="shareRate_1" type="text" value="${designerOrder.shareRate1}" onblur="setShareMoney(this)"/>%</td>
                <td width="9%">分摊金额：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <label></label></td>
                <td width="29%"><input name="designerOrder.shareMony1" id="shareMony_1" type="text" value="${designerOrder.shareMony1}" onblur="setShareRate(this)"/></td>
            </tr>
            <tr>
            <td><label></label></td>
            	 <td colspan="2"><div align="center" id="attnFill" class="suggest" style="width: 300px;"></div></td>
            </tr>
            <tr>
              <td><label></label></td>
              <td width="8%">分摊地：&nbsp;&nbsp;&nbsp;&nbsp;  &nbsp;&nbsp;&nbsp;&nbsp;
                  <label></label></td>
                <td width="16%"><input name="designerOrder.shareArea2" id="shareArea_2" type="text" value="SH" readonly="readonly"/></td>
                <td width="12%">分摊比例：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   
                  <label></label></td>
                <td width="17%"><input name="designerOrder.shareRate2" id="shareRate_2" type="text" value="${designerOrder.shareRate2}" onblur="setShareMoney(this)"/>%</td>
                <td width="9%">分摊金额：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <label></label></td>
                <td width="29%"><input name="designerOrder.shareMony2" id="shareMony_2" type="text" value="${designerOrder.shareMony2}" onblur="setShareRate(this)"/></td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td width="8%">分摊地：&nbsp;&nbsp;&nbsp;&nbsp;  &nbsp;&nbsp;&nbsp;&nbsp;
                  <label></label></td>
                <td width="16%"><input name="designerOrder.shareArea3" id="shareArea_3" type="text" value="BJ" readonly="readonly"/></td>
                <td width="12%">分摊比例：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   
                  <label></label></td>
                <td width="17%"><input name="designerOrder.shareRate3" id="shareRate_3" type="text" value="${designerOrder.shareRate3}" onblur="setShareMoney(this)"/>%</td>
                <td width="9%">分摊金额：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <label></label></td>
                <td width="29%"><input name="designerOrder.shareMony3" id="shareMony_3" type="text" value="${designerOrder.shareMony3}" onblur="setShareRate(this)"/></td>
            </tr>
            <tr>
              <td><label></label></td>
              <td width="8%">分摊地：&nbsp;&nbsp;&nbsp;&nbsp;  &nbsp;&nbsp;&nbsp;&nbsp;
                  <label></label></td>
                <td width="16%"><input name="designerOrder.shareArea4" id="shareArea_4" type="text" value="SZ" readonly="readonly"/></td>
                <td width="12%">分摊比例：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   
                  <label></label></td>
                <td width="17%"><input name="designerOrder.shareRate4" id="shareRate_4" type="text" value="${designerOrder.shareRate4}" onblur="setShareMoney(this)"/>%</td>
                <td width="9%">分摊金额：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <label></label></td>
                <td width="29%"><input name="designerOrder.shareMony4" id="shareMony_4" type="text" value="${designerOrder.shareMony4}" onblur="setShareRate(this)"/></td>
            </tr>
			<tr>
              <td><label></label></td>
              <td width="8%">分摊地：&nbsp;&nbsp;&nbsp;&nbsp;  &nbsp;&nbsp;&nbsp;&nbsp;
                  <label></label></td>
                <td width="16%"><input name="designerOrder.shareArea5" id="shareArea_5" type="text" value="HK" readonly="readonly"/></td>
                <td width="12%">分摊比例：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   
                  <label></label></td>
                <td width="17%"><input name="designerOrder.shareRate5" id="shareRate_5" type="text" value="${designerOrder.shareRate5}" onblur="setShareMoney(this)"/>%</td>
                <td width="9%">分摊金额：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <label></label></td>
                <td width="29%"><input name="designerOrder.shareMony5" id="shareMony_5" type="text" value="${designerOrder.shareMony5}" onblur="setShareRate(this)"/></td>
			</tr>
			<tr>
			<td><label></label></td>
			 <td width="8%">分摊合计：&nbsp;&nbsp;&nbsp;&nbsp;  &nbsp;&nbsp;&nbsp;&nbsp;
                  <label></label></td>
                <td width="16%"><input name="designerOrder.sharetotle" id="sharetotle" type="text" value="${designerOrder.sharetotle}" readonly="readonly" onclick="setSharetotle();"/></td>
                <td width="12%">类型：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   
                  <label></label></td>
                <td width="17%"><input name="designerOrder.type" id="type" type="text" value="${designerOrder.type}" /></td>
                <td width="9%">发票：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <label></label></td>
                <td width="29%"><select id="isInvoice" name="designerOrder.isInvoice" style="width:152px">
                 <option value="是" <c:if test="${designerOrder.isInvoice=='是' }">selected=selected</c:if>>是</option>
                 <option value="否" <c:if test="${designerOrder.isInvoice=='否' }">selected=selected</c:if>>否</option>
                </select>
                </td>
			</tr>
			<tr>
			<td><label></label></td>
			 <td width="8%">收款时间：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			 <td  width="16%"><input type="text"
				style="width:150px;" id="gatheringDate"
				name="gatheringDate" value="${designerOrder.gatheringDate }" />
				<img src="${ctx}/css/images/date.gif"
				onclick="WdatePicker({el:'gatheringDate',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
				width="16" height="18" />&nbsp;<br /></td>
				
			  <td width="12%">定金：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   
                  <label></label></td>
                <td width="17%"><input name="designerOrder.frontMoney" id="frontMoney" type="text" value="${designerOrder.frontMoney}" /></td>
                <td width="9%">进度款1：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <label></label></td>
                <td width="29%"><input name="designerOrder.planMoney1" id="planMoney1" type="text" value="${designerOrder.planMoney1}" /></td>
               
			</tr>
			<tr>
			<td><label></label></td>
               <td width="8%">进度款2：&nbsp;&nbsp;&nbsp;&nbsp;  &nbsp;&nbsp;&nbsp;&nbsp;
                  <label></label></td>
                <td width="16%"><input name="designerOrder.planMoney2" id="planMoney2" type="text" value="${designerOrder.planMoney2}" /></td>
                
                <td width="12%">进度款3：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   
                  <label></label></td>
                <td width="17%"><input name="designerOrder.planMoney3" id="planMoney3" type="text" value="${designerOrder.planMoney3}" /></td>
                <td width="9%">保质金：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <label></label></td>
                <td width="29%"><input name="designerOrder.qualityMoney" id="qualityMoney" type="text" value="${designerOrder.qualityMoney}" /></td>
			</tr>
			<tr>
              <td>&nbsp;</td>
               <td width="8%">已付合计：&nbsp;&nbsp;&nbsp;&nbsp;  &nbsp;&nbsp;&nbsp;&nbsp;
                  <label></label></td>
                <td width="16%"><input name="designerOrder.hasApplyTotle" id="hasApplyTotle" type="text" value="${designerOrder.hasApplyTotle}" readonly="readonly" onclick="setHasApplyTotle();"/></td>
                <td width="12%">收款地：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   
                  <label></label></td>
                <td width="17%"><input name="designerOrder.gatheringArea" id="gatheringArea" type="text" value="${designerOrder.gatheringArea}" /></td>
                <td width="9%">是否付清：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <label></label></td>
                <td width="29%"><select id="isPayTotle" name="designerOrder.isPayTotle" style="width:152px">
                 <option value="是" <c:if test="${designerOrder.isPayTotle=='是' }">selected=selected</c:if>>是</option>
                 <option value="否" <c:if test="${designerOrder.isPayTotle=='否' }">selected=selected</c:if>>否</option>
                </select>
                </td>
            </tr>
            
            <tr>
              <td>&nbsp;</td>
               <td width="8%">一月收款：&nbsp;&nbsp;&nbsp;&nbsp;  &nbsp;&nbsp;&nbsp;&nbsp;
                  <label></label></td>
                <td width="16%"><input name="designerOrder.mon1" id="mon1" type="text" value="${designerOrder.mon1}" /></td>
                <td width="12%">二月收款：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   
                  <label></label></td>
                <td width="17%"><input name="designerOrder.mon2" id="mon2" type="text" value="${designerOrder.mon2}" /></td>
                <td width="9%">三月收款：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <label></label></td>
                <td width="29%">
               <input name="designerOrder.mon3" id="mon3" type="text" value="${designerOrder.mon3}" />
                </td>
            </tr>
              <tr>
              <td>&nbsp;</td>
               <td width="8%">四月收款：&nbsp;&nbsp;&nbsp;&nbsp;  &nbsp;&nbsp;&nbsp;&nbsp;
                  <label></label></td>
                <td width="16%"><input name="designerOrder.mon4" id="mon4" type="text" value="${designerOrder.mon4}" /></td>
                <td width="12%">五月收款：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   
                  <label></label></td>
                <td width="17%"><input name="designerOrder.mon5" id="mon5" type="text" value="${designerOrder.mon5}" /></td>
                <td width="9%">六月收款：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <label></label></td>
                <td width="29%">
               <input name="designerOrder.mon6" id="mon6" type="text" value="${designerOrder.mon6}" />
                </td>
            </tr>
              <tr>
              <td>&nbsp;</td>
               <td width="8%">七月收款：&nbsp;&nbsp;&nbsp;&nbsp;  &nbsp;&nbsp;&nbsp;&nbsp;
                  <label></label></td>
                <td width="16%"><input name="designerOrder.mon7" id="mon7" type="text" value="${designerOrder.mon7}" /></td>
                <td width="12%">八月收款：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   
                  <label></label></td>
                <td width="17%"><input name="designerOrder.mon8" id="mon8" type="text" value="${designerOrder.mon8}" /></td>
                <td width="9%">九月收款：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <label></label></td>
                <td width="29%">
               <input name="designerOrder.mon9" id="mon9" type="text" value="${designerOrder.mon9}" />
                </td>
            </tr>
              <tr>
              <td>&nbsp;</td>
               <td width="8%">十月收款：&nbsp;&nbsp;&nbsp;&nbsp;  &nbsp;&nbsp;&nbsp;&nbsp;
                  <label></label></td>
                <td width="16%"><input name="designerOrder.mon10" id="mon10" type="text" value="${designerOrder.mon10}" /></td>
                <td width="12%">十一月收款：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   
                  <label></label></td>
                <td width="17%"><input name="designerOrder.mon11" id="mon11" type="text" value="${designerOrder.mon11}" /></td>
                <td width="9%">十二月收款：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <label></label></td>
                <td width="29%">
               <input name="designerOrder.mon12" id="mon12" type="text" value="${designerOrder.mon12}" />
                </td>
            </tr>
            <tr>
              <td>&nbsp;</td>
               <td width="8%">未付余额：&nbsp;&nbsp;&nbsp;&nbsp;  &nbsp;&nbsp;&nbsp;&nbsp;
                  <label></label></td>
                <td width="16%"><input type="text" id="hasNoApplyTotle" value="" onclick="setNoApply();"/></td>
                <td width="12%">余额：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   
                  <label></label></td>
                <td width="17%"><input type="text" value=""  id="yue" onclick="setYuE();"/></td>
                <td width="9%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <label></label></td>
                <td width="29%">
               &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <label></label>
                </td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td colspan="6"><p>备&nbsp;&nbsp;&nbsp; 注：</p>
                <p>
                  <textarea name="designerOrder.remark" cols="100" rows="5" style="width:78%;height: 50px" >${designerOrder.remark }</textarea>
                  </p></td>
              </tr>
          
      </table></td></tr></table></td></tr>    
      
 <tr>
    <td height="35" colspan="4" background="../../css/images/tab_19.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="12" height="35"><img src="../../css/images/tab_18.gif" width="12" height="35" /></td>
        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td class="font03515d">
            <div align="center">
            <span class="font03515d">
            <img src="../../css/images/edt.gif" width="16" height="16" />
            <a href="javascript:formsubmit2();"class="miniNav" onClick="">提交</a>
            &nbsp; &nbsp;<img src="../../css/images/del.gif" width="16" height="16" />
            <a href="###" class="miniNav" onClick="window.close();">关闭</a></span>
            </div>
            </td>
            <td>
            <table border="0" align="right" cellpadding="0" cellspacing="0">
                <tr> 
                </tr>
            </table>
            </td>
          </tr>
        </table></td>
        <td width="16"><img src="../../css/images/tab_20.gif" width="16" height="35" /></td>
      </tr>
    </table></td>
  </tr>
</table>
</s:form>
</body>
<script type="text/javascript" src="${ctx}/js/forbidRefersh.js"></script>
</html>

