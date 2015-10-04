<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>【广州诚光进销存后台管理系统】</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<%@include file="/WEB-INF/jsps/common/commonHeader.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminCommonHeader.jsp"%>
<script src="/wfsc/js/artDialog4.1.7/plugins/iframeTools.js"></script> 
<script type="text/javascript">
	function checkProductsForm(){
	//	$("#saveButton").attr("disabled", "disabled");
		//var  picUrl1 = $("#picUrl1File").val();
		//alert(picUrl1);
		//var  picUrl2 = $("#picUrl2File").val();
		//alert(picUrl2);
		var pass = true;
		var name = $("#name").val();
		if(!name){
			pass = false;
		}
		var prdCatCode = $("#prdCatCode").val();
		if(!prdCatCode){
			pass = false;
		}
		var prdPrice = $("#prdPrice").val();
		if(!prdPrice || isNaN(prdPrice)){
			pass = false;
		}
		var prdDisPrice = $("#prdDisPrice").val();
		if(!prdDisPrice || isNaN(prdDisPrice)){
			pass = false;
		}
		var picUrl1Value = $("#picUrl1Value").val();
		var productsId = $("#productsId").val();
		if(!picUrl1Value && !productsId){
			pass = false;
		}
		if(pass){
			$("#productsForm").submit();
		}else{
			art.dialog({fixed : true,content : '请检查是否有未填写的项并正确填写',okVal:'确定',ok:true});
		}
		
	}
	function chekRecommend(recommend){
		if(1==recommend || 2==recommend){
			var prdCatCode = $("#prdCatCode").val();
			if(!prdCatCode){
				$("#recommend").val(0);
				art.dialog({fixed : true,content : '请先选择商品所属分类',okVal:'确定',ok:true});
				return;
			}
			$.ajax({
				url:"/wfsc/admin/products_chekRecommend.Q",
				data:{"recommend":recommend,"prdCatCode":prdCatCode},
				dataType:'text',
				success:function(data){
					if(1==data){
						$("#recommend").val(0);
						art.dialog({fixed : true,content : '一个大类里面只能有6个推荐，目前已超过6个。',okVal:'确定',ok:true});
					}else if(2==data){
						$("#recommend").val(0);
						art.dialog({fixed : true,content : '一个大类里面只能有1个大图推荐，目前已超过1个。',okVal:'确定',ok:true});
					}
				}
			})
		}
	}
	function toProductsList(){
		window.location.href = "/wfsc/admin/products_manager.Q";
	}
	function openPrdCatTree(){
    	var url = "/wfsc/admin/products_openPrdCatTree.Q";
       art.dialog.open(url, {limit:false,lock:true,title:'<b>商品类型</b>',width:'260px',height:'400px'});
	}
	function changeTargetValue(id,obj){
		var path=getPath(obj);
		$("#"+id).val(path);
	}
	function getPath(obj) {
	         if(obj)
	     {  
	         if (window.navigator.userAgent.indexOf("MSIE")>=1)
	        {	var textValue = "";
	        	try{
	        		obj.select();
	           	 	textValue = document.selection.createRange().text;
	        	}catch(e){
	        		if(!textValue){
	            		textValue = obj.value;
	            	}
	        	}
	            
	            
	            return textValue;
	        }   
	         else 
	         {
	            if(obj.files)
	             {
	                   // return obj.files.item(0).getAsDataURL();
	                   return obj.value;
	              }
	              return obj.value;
	         }
	        return obj.value;
	    }
	}
</script>
<script type="text/javascript" charset="utf-8" src="/wfsc/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="/wfsc/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="/wfsc/ueditor/lang/zh-cn/zh-cn.js"></script>
</head>
<body>
<%@include file="/WEB-INF/jsps/admin/common/adminTop.jsp"%>
<%@include file="/WEB-INF/jsps/admin/common/adminleft.jsp"%>
<input type="hidden" id="tab" value="m1,msub11"/>
<div id="content">
<div id="content-header">
    <div id="breadcrumb"> <a href="/wfsc/admin/admin_index.Q" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a></div>
</div>
<div class="container-fluid">
  <div class="row-fluid">
      <div class="widget-box">
        <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
          <h5>商品信息</h5>
        </div>
        <div class="widget-content nopadding">
          <form action="/wfsc/admin/products_save.Q" method="post" class="form-horizontal" id="productsForm" enctype="multipart/form-data">
          	<input type="hidden" name="products.id" id="productsId" value="${products.id }">
          	<input type="hidden" name="products.prdCode" id="prdCode" value="${products.prdCode }">
            <div class="control-group">
              <label class="control-label">商品名</label>
              <div class="controls">
                <input type="text" id="name" class="span5" name="products.name" value="${products.name }" />&nbsp;&nbsp;
              </div>
            </div>
           
           <div class="control-group">
              <label class="control-label">商品规格</label>
              <div class="controls">
                <input type="text" class="span5" name="products.prdStandard" value="${products.prdStandard }" id="prdStandard"/>&nbsp;&nbsp;
              </div>
            </div>
             <div class="control-group">
              <label class="control-label">商品价格</label>
              <div class="controls">
                <input type="text" class="span5" name="products.prdPrice" value="${products.prdPrice }" id="prdPrice"/>&nbsp;&nbsp;
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">折扣价</label>
              <div class="controls">
                <input type="text" class="span5" name="products.prdDisPrice" value="${products.prdDisPrice }" id="prdDisPrice"/>&nbsp;&nbsp;
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">所属分类</label>
              <div class="controls">
                <input type="text" class="span5" name="products.prdCatName" value="${products.prdCatName }" id="prdCatName" onclick="openPrdCatTree();"/>&nbsp;&nbsp;
                <input type="hidden" class="span5" name="products.prdCatCode" value="${products.prdCatCode }" id="prdCatCode"/>&nbsp;&nbsp;
              </div>
            </div>
              <div class="control-group">
              <label class="control-label">是否推荐</label>
              <div class="controls">
                <select name="products.recommend" id="recommend" style="width:579px;" onchange="chekRecommend(this.value)">
                  <option value="0" <s:if test="products.recommend==0">selected</s:if> >不推荐</option>
                  <option value="1" <s:if test="products.recommend==1">selected</s:if>>推荐</option>
                  <option value="2" <s:if test="products.recommend==2">selected</s:if>>大图推荐</option>
                </select>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">图1(必填)</label>
              <div class="controls">
              <input type="hidden" class="span5" name="picUrl" id="picUrl1Value" value="" readonly="readonly"/>
                <input type="file" name="myFile" id="picUrl1File" size="0" onchange="changeTargetValue('picUrl1Value',this);" style="width:71px;"/>
                 &nbsp;${products.picUrl1 }
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">图2</label>
              <div class="controls">
               <input type="hidden" class="span5" name="picUrl" id="picUrl2Value" value="" readonly="readonly"/>
                <input type="file" name="myFile" id="picUrl2File" onchange="changeTargetValue('picUrl2Value',this);" style="width:71px;"/>
                &nbsp;${products.picUrl2 }
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">图3</label>
              <div class="controls">
              <input type="hidden" class="span5" name="picUrl" value="" id="picUrl3Value" readonly="readonly"/>
                <input type="file" name="myFile" id="picUrl3File" onchange="changeTargetValue('picUrl3Value',this);" style="width:71px;"/>
                 &nbsp;${products.picUrl3 }
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">图4</label>
              <div class="controls">
              <input type="hidden" class="span5" name="picUrl" value="" id="picUrl4Value" readonly="readonly"/>
                <input type="file" name="myFile" id="picUrl4" onchange="changeTargetValue('picUrl4Value',this);" style="width:71px;"/>
                 &nbsp;${products.picUrl4 }
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">图5</label>
              <div class="controls">
              <input type="hidden" class="span5" name="picUrl" value="" id="picUrl5Value" readonly="readonly"/>
                <input type="file" name="myFile" id="picUrl5File" onchange="changeTargetValue('picUrl5Value',this);" style="width:71px;"/>
                 &nbsp;${products.picUrl5 }
              </div>
            </div>
             <div class="control-group">
              <label class="control-label">商品描述</label>
              <div class="controls">
                <textarea name="products.prdDesc" id="prdDesc" style="width:800px;height:300px;">${products.prdDesc }</textarea>&nbsp;&nbsp;
                <script type="text/javascript">
					UE.getEditor('prdDesc');
				</script>
              </div>
            </div>
            
            <div class="control-group">
              <label class="control-label">品牌故事</label>
              <div class="controls">
                <textarea name="products.prdStory" id="prdStory" style="width:800px;height:300px;">${products.prdStory }</textarea>&nbsp;&nbsp;
                <script type="text/javascript">
					UE.getEditor('prdStory');
				</script>
              </div>
            </div>
            
            <div class="form-actions">
              <button type="button" class="btn btn-success" onclick="checkProductsForm()" id="saveButton">保存</button>&nbsp;&nbsp;
              <button type="button" class="btn btn-success" onclick="toProductsList();">返回</button>
            </div>
          </form>
        </div>
      </div>
      </div>
      </div>
      
</div>
<jsp:include page="/WEB-INF/jsps/admin/common/adminFooter.jsp" />

</body>
</html>
