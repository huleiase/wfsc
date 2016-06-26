	
		function dahuojia(lineNo){
			var isdhj = $("#isCgbj"+lineNo).val();
			if(isdhj==0){
				return;
			}
			var quoteFormate = $("#quoteFormate").val();
			if(quoteFormate!='1' && quoteFormate!='2'){
				art.dialog({content:"不支持切换",okVal:"确定",ok:true});
				return;
			}
			var dhjVcOldPrice = $("#dhjVcOldPrice"+lineNo).val();
			var dhjVcHKPrice = $("#dhjVcHKPrice"+lineNo).val();
			var dhjCost = $("#dhjCost"+lineNo).val();
			if(0==dhjCost){
				art.dialog({content:"没有大货价",okVal:"确定",ok:true});
				return;
			}
			$("#isCgbj"+lineNo).val("0");
			 var dhjWidth = $("#dhjWidth"+lineNo).val();
				if(!dhjWidth){
					dhjWidth = 0;
				}
				var vcWidth = $("#vcWidth"+lineNo).val();
				if(!dhjWidth){
					dhjWidth = 0;
				}
			$("#vcWidth"+lineNo).val(dhjWidth);	
			$("#dhjWidth"+lineNo).val(vcWidth);
			
			 var oldPrice = $("#vcOldPrice"+lineNo).val();
			 
			if(quoteFormate==1){
				$("#vcOldPrice"+lineNo).val(dhjVcOldPrice);
				$("#dhjVcOldPrice"+lineNo).val(oldPrice);
			}else if(quoteFormate==2){
				$("#vcOldPrice"+lineNo).val(dhjVcHKPrice);
				$("#dhjVcHKPrice"+lineNo).val(oldPrice);
			}
			setVcPrice(lineNo);
		}
		function cgjia(lineNo){
			var isdhj = $("#isCgbj"+lineNo).val();
			if(isdhj==1){
				return;
			}
			var quoteFormate = $("#quoteFormate").val();
			if(quoteFormate!='1' && quoteFormate!='2'){
				art.dialog({content:"不支持切换",okVal:"确定",ok:true});
				return;
			}
			 var oldPrice = $("#vcOldPrice"+lineNo).val();
			 var singlePrice = $("#singlePrice"+lineNo).val();
			 if(0==singlePrice){
			 	art.dialog({content:"没有常规价",okVal:"确定",ok:true});
				return;
			 }
			$("#isCgbj"+lineNo).val("1");
			 var dhjWidth = $("#dhjWidth"+lineNo).val();
				if(!dhjWidth){
					dhjWidth = 0;
				}
				var vcWidth = $("#vcWidth"+lineNo).val();
				if(!dhjWidth){
					dhjWidth = 0;
				}
			$("#vcWidth"+lineNo).val(dhjWidth);	
			$("#dhjWidth"+lineNo).val(vcWidth);
			
			
			 var dhjVcOldPrice = $("#dhjVcOldPrice"+lineNo).val();
			var dhjVcHKPrice = $("#dhjVcHKPrice"+lineNo).val();
			if(quoteFormate==1){
				$("#vcOldPrice"+lineNo).val(dhjVcOldPrice);
				$("#dhjVcOldPrice"+lineNo).val(oldPrice);
			}else if(quoteFormate==2){
				$("#vcOldPrice"+lineNo).val(dhjVcHKPrice);
				$("#dhjVcHKPrice"+lineNo).val(oldPrice);
			}
			setVcPrice(lineNo);
		}
			//选择产品
			function selFabric(){
				var quoteId = $("#quoteId").val();
			    var quoteFormate = $("#quoteFormate").val();
			    if(quoteFormate==""){
			    	art.dialog({
					    content: '请先选择报价方式',
					    okVal:'确定',
					    ok: true
					});
			    }else{
			    	var url = basePath+"admin/fabric_getFabricForQuote.Q?isHtCode=0&quoteFormate="+quoteFormate;
			    	if(quoteId){
			    		url += "&quoteId="+quoteId
			    	}
			    	art.dialog.open(url, {title: '选择产品', lock: true, drag:true, width: getClientWidth()*0.8, height: getClientHeight()*0.85});
			    }
			}
			
			
			//设置最终价格 。参数（行号）
			function setVcPrice(lineNo){
				
			    //所含税率
			    var ctax = $("#containTax").val();
			    if(!ctax || ctax==0){
			    	ctax = 1.08
			    }
			    // 报价折扣
			    var vcdiscount = $("#vcDiscount"+lineNo).val();
			    if(!vcdiscount || vcdiscount==0){
			    	vcdiscount = 1
			    }
			    //特殊费用
			    var vcSpecialExp = $("#vcSpecialExp"+lineNo).val();
			     if(!vcSpecialExp){
			    	vcSpecialExp = 0
			    }
			    //面价
			    var oldPrice = $("#vcOldPrice"+lineNo).val();
			    //幅宽
			    var width = $("#vcWidth"+lineNo).val();
			    //面价单位
			    var oldPriceUnit = $("#vcOldPriceUnit"+lineNo).val();
			  //  var oldPriceUnit = $("#qUnit"+lineNo).html();
			    //最终单价单位
			    var vcPriceUnit = $("#vcPriceUnit"+lineNo).val();
			 //是否包含运费
			 	var isFre = $("#isFreight").val();
			 	//工程运费
			 	 var proFre = $("#vcProFre"+lineNo).val();
			 	  if(!proFre){
					proFre = 0;
				}
				//零售运费
			    var retFre = $("#vcRetFre"+lineNo).val();
				if(!retFre){
					retFre = 0;
				}
				var rmb2hkd = $("#rmb2hkd").val();
				retFre = Number(retFre)*Number(rmb2hkd)
				//工程报价或零售报价
			   var quoteFormate = $("#quoteFormate").val();
			   
				//大货价大陆运费
			    var dhjInlandTransCost = $("#dhjInlandTransCost"+lineNo).val();
				if(!dhjInlandTransCost){
					dhjInlandTransCost = 0;
				}
				//大货价香港运费
			    var dhjHKTransCost = $("#dhjHKTransCost"+lineNo).val();
				if(!dhjHKTransCost){
					dhjHKTransCost = 0;
				}
				
				//大货价成本价
		//	    var dhjCost = $("#dhjCost"+lineNo).val();
		//		if(!dhjCost){
		//			dhjCost = 0;
		//		}
				//大货价幅宽
		//	    var dhjWidth = $("#dhjWidth"+lineNo).val();
		//		if(!dhjWidth){
		//			dhjWidth = 0;
		//		}
				//大货价大陆系数
		//	    var dhjInlandRate = $("#dhjInlandRate"+lineNo).val();
		//		if(!dhjInlandRate){
		//			dhjInlandRate = 0;
		//		}
				//大货价香港系数
		//	    var dhjHKRate = $("#dhjHKRate"+lineNo).val();
		//		if(!dhjHKRate){
		///			dhjHKRate = 0;
		//		}
				//进价折扣
		//	    var vcPurDis = $("#vcPurDis"+lineNo).val();
		//		if(!vcPurDis){
		//			vcPurDis = 1;
		//		}
			//	var vcExchangeRate = 0;
			//	var dhjrate = dhjInlandRate;
			//	if(quoteFormate==2){
			//		dhjrate = dhjHKRate;
			//	}
				var isCgbj =$("#isCgbj"+lineNo).val();
			//	if(isCgbj==0 && quoteFormate==1 ){
			//		oldPrice = $("#dhjVcOldPrice"+lineNo).val();
			//		width = dhjWidth;
			//	}
			//	if(isCgbj==0 && quoteFormate==2 ){
			//		oldPrice = $("#dhjVcHKPrice"+lineNo).val();
			//		width = dhjWidth;
			//	}
			 
			 //产品ID
			//   var fabricId=$("#fabricId"+lineNo).val();
			   
			   //初始化最终单价为0
			   var price = 0;
			   if('1'==quoteFormate || '3'==quoteFormate || '5'==quoteFormate){
			   		var transCost = ('3'==quoteFormate || isCgbj==0) ? dhjInlandTransCost : proFre;
			   		if(oldPriceUnit=="㎡" && vcPriceUnit=="sf" ){
			   			if(1==isFre){
			   				price = (Number(oldPrice)*Number(vcdiscount)+Number(transCost)+Number(vcSpecialExp))/10.764*Number(ctax);
			   			}else{
			   				price = (Number(oldPrice)*Number(vcdiscount)+Number(vcSpecialExp))/10.764*Number(ctax);
			   			}
			   			$("#qUnit"+lineNo).html("sf");
			   		}else if((oldPriceUnit=="sf" || oldPriceUnit=="SF") && vcPriceUnit=="㎡" ){
			   			if(1==isFre){
			   				price = (Number(oldPrice)*Number(vcdiscount)+Number(transCost)+Number(vcSpecialExp))*10.764*Number(ctax);
			   			}else{
			   				price = (Number(oldPrice)*Number(vcdiscount)+Number(vcSpecialExp))*10.764*Number(ctax);
			   			}
			   			$("#qUnit"+lineNo).html("㎡");
			   		}else if((oldPriceUnit=="yd" || oldPriceUnit=="YD") && vcPriceUnit=="㎡" ){
			   			if(1==isFre){
			   				
			   					price = (Number(oldPrice)/0.914*Number(vcdiscount)+Number(transCost)+Number(vcSpecialExp))/width*100*Number(ctax);
			   				
			   					
			   				
			   				
			   			}else{
			   				prcice = (Number(oldPrice)/0.914*Number(vcdiscount)+Number(vcSpecialExp))/width*100*Number(ctax);
			   			}
			   			$("#qUnit"+lineNo).html("㎡");
			   		}else if((oldPriceUnit=="m" || oldPriceUnit=="M") && vcPriceUnit=="㎡" ){
			   			if(1==isFre){
			   				price = (Number(oldPrice)*Number(vcdiscount)+Number(transCost)+Number(vcSpecialExp))/width*100*Number(ctax);
			   			}else{
			   				price = (Number(oldPrice)*Number(vcdiscount)+Number(vcSpecialExp))/width*100*Number(ctax);
			   			}
			   			$("#qUnit"+lineNo).html("㎡");
			   		}else if((oldPriceUnit=="yd" || oldPriceUnit=="YD") && vcPriceUnit=="m" ){
			   			if(1==isFre){
			   				
			   					price = (Number(oldPrice)/0.914*Number(vcdiscount)+Number(transCost)+Number(vcSpecialExp))*Number(ctax);
			   				
			   				
			   			}else{
			   				price = (Number(oldPrice)/0.914*Number(vcdiscount)+Number(vcSpecialExp))*Number(ctax);
			   			}
			   			$("#qUnit"+lineNo).html("m");
			   		}else if((oldPriceUnit=="m" || oldPriceUnit=="M") && vcPriceUnit=="yd" ){
			   			if(1==isFre){
			   				if('1'==quoteFormate){
			   					price = (Number(oldPrice)*0.914*Number(vcdiscount)+Number(transCost)*0.914+Number(vcSpecialExp))*Number(ctax);
			   				}else{
			   					price = (Number(oldPrice)*0.914*Number(vcdiscount)+Number(transCost)+Number(vcSpecialExp))*Number(ctax);
			   				}
			   				
			   			}else{
			   				price = (Number(oldPrice)*0.914*Number(vcdiscount)+Number(vcSpecialExp))*Number(ctax);
			   			}
			   			$("#qUnit"+lineNo).html("yd");
			   		}else if((oldPriceUnit=="roll" || oldPriceUnit=="ROLL") && vcPriceUnit=="roll" ){
			   			if(1==isFre){
			   				price = (Number(oldPrice)*Number(vcdiscount)+Number(transCost)+Number(vcSpecialExp))*Number(ctax);
			   			}else{
			   				price = (Number(oldPrice)*Number(vcdiscount)+Number(vcSpecialExp))*Number(ctax);
			   			}
			   			$("#qUnit"+lineNo).html("roll");
			   		}else if((oldPriceUnit=="m" || oldPriceUnit=="M") && vcPriceUnit=="m" ){
			   			if(1==isFre){
			   				price = (Number(oldPrice)*Number(vcdiscount)+Number(transCost)+Number(vcSpecialExp))*Number(ctax);
			   			}else{
			   				price = (Number(oldPrice)*Number(vcdiscount)+Number(vcSpecialExp))*Number(ctax);
			   			}
			   			$("#qUnit"+lineNo).html("m");
			   		}else if((oldPriceUnit=="sf" || oldPriceUnit=="SF") && vcPriceUnit=="sf" ){
			   			if(1==isFre){
			   				price = (Number(oldPrice)*Number(vcdiscount)+Number(transCost)+Number(vcSpecialExp))*Number(ctax);
			   			}else{
			   				price = (Number(oldPrice)*Number(vcdiscount)+Number(vcSpecialExp))*Number(ctax);
			   			}
			   			$("#qUnit"+lineNo).html("sf");
			   		}else if(oldPriceUnit=="㎡" && vcPriceUnit=="㎡" ){
			   			if(1==isFre){
			   				price = (Number(oldPrice)*Number(vcdiscount)+Number(transCost)+Number(vcSpecialExp))*Number(ctax);
			   			}else{
			   				price = (Number(oldPrice)*Number(vcdiscount)+Number(vcSpecialExp))*Number(ctax);
			   			}
			   			$("#qUnit"+lineNo).html("㎡");
			   		}else if((oldPriceUnit=="yd" || oldPriceUnit=="YD") && vcPriceUnit=="yd" ){
			   			if(1==isFre){
			   				price = (Number(oldPrice)*Number(vcdiscount)+Number(transCost)*0.914+Number(vcSpecialExp))*Number(ctax);
			   			}else{
			   				price = (Number(oldPrice)*Number(vcdiscount)+Number(vcSpecialExp))*Number(ctax);
			   			}
			   			$("#qUnit"+lineNo).html("yd");
			   		}else if(oldPriceUnit=="㎡" && vcPriceUnit=="m" ){
			   			if(1==isFre){
			   				price = (Number(oldPrice)*Number(vcdiscount)+Number(transCost)+Number(vcSpecialExp))*Number(width)/100*Number(ctax);
			   			}else{
			   				price = (Number(oldPrice)*Number(vcdiscount)+Number(vcSpecialExp))*Number(width)/100*Number(ctax);
			   			}
			   			$("#qUnit"+lineNo).html("m");
			   		}else if((oldPriceUnit=="pc" || oldPriceUnit=="PC") && vcPriceUnit=="pc" ){
			   			if(1==isFre){
			   				price = (Number(oldPrice)*Number(vcdiscount)+Number(transCost)+Number(vcSpecialExp))*Number(ctax);
			   			}else{
			   				price = (Number(oldPrice)*Number(vcdiscount)+Number(vcSpecialExp))*Number(ctax);
			   			}
			   			$("#qUnit"+lineNo).html("pc");
			   		}else if((oldPriceUnit=="set" || oldPriceUnit=="SET") && vcPriceUnit=="set" ){
			   			if(1==isFre){
			   				price = (Number(oldPrice)*Number(vcdiscount)+Number(transCost)+Number(vcSpecialExp))*Number(ctax);
			   			}else{
			   				price = (Number(oldPrice)*Number(vcdiscount)+Number(vcSpecialExp))*Number(ctax);
			   			}
			   			$("#qUnit"+lineNo).html("set");
			   		}else if((oldPriceUnit=="unit" || oldPriceUnit=="UNIT") && vcPriceUnit=="unit" ){
			   			if(1==isFre){
			   				price = (Number(oldPrice)*Number(vcdiscount)+Number(transCost)+Number(vcSpecialExp))*Number(ctax);
			   			}else{
			   				price = (Number(oldPrice)*Number(vcdiscount)+Number(vcSpecialExp))*Number(ctax);
			   			}
			   			$("#qUnit"+lineNo).html("unit");
			   		}else if((oldPriceUnit=="pair" || oldPriceUnit=="PAIR") && vcPriceUnit=="pair" ){
			   			if(1==isFre){
			   				price = (Number(oldPrice)*Number(vcdiscount)+Number(transCost)+Number(vcSpecialExp))*Number(ctax);
			   			}else{
			   				price = (Number(oldPrice)*Number(vcdiscount)+Number(vcSpecialExp))*Number(ctax);
			   			}
			   			$("#qUnit"+lineNo).html("pair");
			   		}
			   }else{
			   		var transCost = ('4'==quoteFormate || isCgbj==0) ? dhjHKTransCost : retFre;
			   		if(oldPriceUnit=="㎡" && vcPriceUnit=="sf" ){
			   			if(1==isFre){
			   				price = (Number(oldPrice)*Number(vcdiscount)+Number(transCost)+Number(vcSpecialExp))/10.764*Number(ctax);
			   			}else{
			   				price = (Number(oldPrice)*Number(vcdiscount)+Number(vcSpecialExp))/10.764*Number(ctax);
			   			}
			   			$("#qUnit"+lineNo).html("sf");
			   		}else if((oldPriceUnit=="sf" || oldPriceUnit=="SF") && vcPriceUnit=="㎡" ){
			   			if(1==isFre){
			   				price = (Number(oldPrice)*Number(vcdiscount)+Number(transCost)+Number(vcSpecialExp))*10.764*Number(ctax);
			   			}else{
			   				price = (Number(oldPrice)*Number(vcdiscount)+Number(vcSpecialExp))*10.764*Number(ctax);
			   			}
			   			$("#qUnit"+lineNo).html("㎡");
			   		}else if((oldPriceUnit=="yd" || oldPriceUnit=="YD") && vcPriceUnit=="㎡" ){
			   			if(1==isFre){
			   				
			   					price = (Number(oldPrice)/0.914*Number(vcdiscount)+Number(transCost)+Number(vcSpecialExp))/width*100*Number(ctax);
			   				
			   				
			   			}else{
			   				price = (Number(oldPrice)/0.914*Number(vcdiscount)+Number(vcSpecialExp))/width*100*Number(ctax);
			   			}
			   			$("#qUnit"+lineNo).html("㎡");
			   		}else if((oldPriceUnit=="m" || oldPriceUnit=="M") && vcPriceUnit=="㎡" ){
			   			if(1==isFre){
			   				price = (Number(oldPrice)*Number(vcdiscount)+Number(transCost)+Number(vcSpecialExp))/width*100*Number(ctax);
			   			}else{
			   				price = (Number(oldPrice)*Number(vcdiscount)+Number(vcSpecialExp))/width*100*Number(ctax);
			   			}
			   			$("#qUnit"+lineNo).html("㎡");
			   		}else if((oldPriceUnit=="yd" || oldPriceUnit=="YD") && vcPriceUnit=="m" ){
			   			if(1==isFre){
				   			
				   				price = (Number(oldPrice)/0.914*Number(vcdiscount)+Number(transCost)+Number(vcSpecialExp))*Number(ctax);
				   			
			   				
			   			}else{
			   				price = (Number(oldPrice)/0.914*Number(vcdiscount)+Number(vcSpecialExp))*Number(ctax);
			   			}
			   			$("#qUnit"+lineNo).html("m");
			   		}else if((oldPriceUnit=="m" || oldPriceUnit=="M") && vcPriceUnit=="yd" ){
			   			if(1==isFre){
			   				if('2'==quoteFormate){
			   					price = (Number(oldPrice)*0.914*Number(vcdiscount)+Number(transCost)*0.914+Number(vcSpecialExp))*Number(ctax);
			   				}else{
			   					price = (Number(oldPrice)*0.914*Number(vcdiscount)+Number(transCost)+Number(vcSpecialExp))*Number(ctax);
			   				}
			   				
			   			}else{
			   				price = (Number(oldPrice)*0.914*Number(vcdiscount)+Number(vcSpecialExp))*Number(ctax);
			   			}
			   			$("#qUnit"+lineNo).html("yd");
			   		}else if((oldPriceUnit=="m" || oldPriceUnit=="M") && vcPriceUnit=="m" ){
			   			if(1==isFre){
			   				price = (Number(oldPrice)*Number(vcdiscount)+Number(transCost)+Number(vcSpecialExp))*Number(ctax);
			   			}else{
			   				price = (Number(oldPrice)*Number(vcdiscount)+Number(vcSpecialExp))*Number(ctax);
			   			}
			   			$("#qUnit"+lineNo).html("m");
			   		}else if((oldPriceUnit=="roll" || oldPriceUnit=="ROLL") && vcPriceUnit=="roll" ){
			   			if(1==isFre){
			   				price = (Number(oldPrice)*Number(vcdiscount)+Number(transCost)+Number(vcSpecialExp))*Number(ctax);
			   			}else{
			   				price = (Number(oldPrice)*Number(vcdiscount)+Number(vcSpecialExp))*Number(ctax);
			   			}
			   			$("#qUnit"+lineNo).html("roll");
			   		}else if((oldPriceUnit=="sf" || oldPriceUnit=="SF") && vcPriceUnit=="sf" ){
			   			if(1==isFre){
			   				price = (Number(oldPrice)*Number(vcdiscount)+Number(transCost)+Number(vcSpecialExp))*Number(ctax);
			   			}else{
			   				price = (Number(oldPrice)*Number(vcdiscount)+Number(vcSpecialExp))*Number(ctax);
			   			}
			   			$("#qUnit"+lineNo).html("sf");
			   		}else if(oldPriceUnit=="㎡" && vcPriceUnit=="㎡" ){
			   			if(1==isFre){
			   				price = (Number(oldPrice)*Number(vcdiscount)+Number(transCost)+Number(vcSpecialExp))*Number(ctax);
			   			}else{
			   				price = (Number(oldPrice)*Number(vcdiscount)+Number(vcSpecialExp))*Number(ctax);
			   			}
			   			$("#qUnit"+lineNo).html("㎡");
			   		}else if((oldPriceUnit=="yd" || oldPriceUnit=="YD") && vcPriceUnit=="yd" ){
			   			if(1==isFre){
			   				price = (Number(oldPrice)*Number(vcdiscount)+Number(transCost)*0.914+Number(vcSpecialExp))*Number(ctax);
			   			}else{
			   				price = (Number(oldPrice)*Number(vcdiscount)+Number(vcSpecialExp))*Number(ctax);
			   			}
			   			$("#qUnit"+lineNo).html("yd");
			   		}else if((oldPriceUnit=="㎡") && vcPriceUnit=="m" ){
			   			if(1==isFre){
			   				price = (Number(oldPrice)*Number(vcdiscount)+Number(transCost)+Number(vcSpecialExp))*Number(width)/100*Number(ctax);
			   			}else{
			   				price = (Number(oldPrice)*Number(vcdiscount)+Number(vcSpecialExp))*Number(width)/100*Number(ctax);
			   			}
			   			$("#qUnit"+lineNo).html("m");
			   		}else if((oldPriceUnit=="pc" || oldPriceUnit=="PC") && vcPriceUnit=="pc" ){
			   			if(1==isFre){
			   				price = (Number(oldPrice)*Number(vcdiscount)+Number(transCost)+Number(vcSpecialExp))*Number(ctax);
			   			}else{
			   				price = (Number(oldPrice)*Number(vcdiscount)+Number(vcSpecialExp))*Number(ctax);
			   			}
			   			$("#qUnit"+lineNo).html("pc");
			   		}else if((oldPriceUnit=="set" || oldPriceUnit=="SET") && vcPriceUnit=="set" ){
			   			if(1==isFre){
			   				price = (Number(oldPrice)*Number(vcdiscount)+Number(transCost)+Number(vcSpecialExp))*Number(ctax);
			   			}else{
			   				price = (Number(oldPrice)*Number(vcdiscount)+Number(vcSpecialExp))*Number(ctax);
			   			}
			   			$("#qUnit"+lineNo).html("set");
			   		}else if((oldPriceUnit=="unit" || oldPriceUnit=="UNIT") && vcPriceUnit=="unit" ){
			   			if(1==isFre){
			   				price = (Number(oldPrice)*Number(vcdiscount)+Number(transCost)+Number(vcSpecialExp))*Number(ctax);
			   			}else{
			   				price = (Number(oldPrice)*Number(vcdiscount)+Number(vcSpecialExp))*Number(ctax);
			   			}
			   			$("#qUnit"+lineNo).html("unit");
			   		}else if((oldPriceUnit=="pair" || oldPriceUnit=="PAIR") && vcPriceUnit=="pair" ){
			   			if(1==isFre){
			   				price = (Number(oldPrice)*Number(vcdiscount)+Number(transCost)+Number(vcSpecialExp))*Number(ctax);
			   			}else{
			   				price = (Number(oldPrice)*Number(vcdiscount)+Number(vcSpecialExp))*Number(ctax);
			   			}
			   			$("#qUnit"+lineNo).html("pair");
			   		}
			   	}
			   	$("#vcPrice"+lineNo).val(price.toFixed(0));
			   	setTrVcTotal(lineNo);
			   }
			 
			  
		   //设置每行的总金额
		   function setTrVcTotal(index){
		   		var vcPrice = $("#vcPrice"+index).val();
		   		if(!vcPrice){
		   			vcPrice = 0;
		   		}
		   		var vcQuantity = $("#vcQuantity"+index).val();
		   		if(!vcQuantity){
		   			vcQuantity = 0;
		   		}
		   		var trtotal = Number(vcPrice)*Number(vcQuantity)
		   		$("#vcTotal"+index).val(Math.round(trtotal));
		   		var isHidden = $("#isHidden"+index).val();
		   		if(1==isHidden){//如果是隐藏型号就不去累加小计
		   		}else{
		   		setSumMoney();
		   		}
		   		
		   }
		   
		     //设置小计和总金额
function setSumMoney(){
	var ctax = document.getElementById("containTax").value;
	if(!ctax || ctax==0){
		ctax = 1.08;
	}
	var subtotal = 0;
	$("input[name$='vcTotal']:not([isHiddenAttr])").each(function(i){
 		subtotal += Number($(this).val());
	});
	
	
	var taxes = $("#taxes").val();
	if(!taxes){
		taxes=0;
	} else {
		taxes = Number(taxes)*Number(ctax);
	}
	
	var urgentCost = $("#urgentCost").val();
	if(!urgentCost){
		urgentCost=0;
	} else {
		urgentCost = Number(urgentCost)*Number(ctax);
	}
	var lowestFreight = $("#lowestFreight").val();
	if(!lowestFreight){
		lowestFreight=0;
	}else{
		lowestFreight = Number(lowestFreight)*Number(ctax);
	}
	var engineTotal = $("#engineTotal").val();
	if(!engineTotal) {
		engineTotal = 0;
	} else {
		engineTotal = Number(engineTotal) * Number(ctax);
	}
	
	var flameTotal = $("#flameTotal").val();
	if(!flameTotal) flameTotal = 0;
	else flameTotal = Number(flameTotal) * Number(ctax);
	
	var arriveTransport = $("#arriveTransport").val();
	if(!arriveTransport) arriveTransport = 0;
	else arriveTransport = Number(arriveTransport) * Number(ctax);
	
	var inputCol1 = $("#inputCol1").val();
	if(!inputCol1) inputCol1 = 0;
	else inputCol1 = Number(inputCol1) * Number(ctax);
	
	var inputCol2 = $("#inputCol2").val();
	if(!inputCol2) inputCol2 = 0;
	else inputCol2 = Number(inputCol2) * Number(ctax);
	
	var inputCol3 = $("#inputCol3").val();
	if(!inputCol3) inputCol3 = 0;
	else inputCol3 = Number(inputCol3) * Number(ctax);
	
	var inputCol4 = $("#inputCol4").val();
	if(!inputCol4) inputCol4 = 0;
	else inputCol4 = Number(inputCol4) * Number(ctax);
	
	var inputCol5 = $("#inputCol5").val();
	if(!inputCol5) inputCol5 = 0;
	else inputCol5 = Number(inputCol5) * Number(ctax);

	var vcProcessFre = $("#vcProcessFre").val();
	if(!vcProcessFre) vcProcessFre = 0;
	else vcProcessFre = Number(vcProcessFre) * Number(ctax);

	var vcInstallFre = $("#vcInstallFre").val();
	if(!vcInstallFre) vcInstallFre = 0;
	else vcInstallFre = Number(vcInstallFre) * Number(ctax);

	var vcAftertreatment = $("#vcAftertreatment").val();
	if(!vcAftertreatment) vcAftertreatment = 0;
	else vcAftertreatment = Number(vcAftertreatment) * Number(ctax);

	var vcOther = $("#vcOther").val();
	if(!vcOther) vcOther = 0;
	else vcOther = Number(vcOther) * Number(ctax);

	var lcFre = $("#lcFre").val();
	if(!lcFre) lcFre = 0;
	else lcFre = Number(lcFre) * Number(ctax);
	
	subtotal = subtotal + Number(urgentCost) + Number(lowestFreight)+ Number(engineTotal)+ Number(flameTotal)
			+ Number(arriveTransport)+ Number(inputCol1)+ Number(inputCol2)+ Number(inputCol3)+ Number(inputCol4)
			+ Number(inputCol5) + Number(lcFre)+ Number(taxes) + Number(vcProcessFre) + Number(vcInstallFre) + Number(vcAftertreatment) + Number(vcOther);
	$("#subtotal").val(Math.round(subtotal));
	var discountMoney = $("#discountMoney").val();
	if(!discountMoney){
		discountMoney=0;
	}
	var sumMoney = subtotal-Number(discountMoney);
	$("#sumMoney").val(Math.round(sumMoney));
}

function setUrgentCost(){
	var ctax = document.getElementById("containTax").value;
	if(!ctax || ctax==0){
		ctax = 1.08
	}
	var urgentCost = $("#urgentCost").val();
	if(!urgentCost){
		urgentCost=0;
	}else{
		urgentCost = Number(urgentCost)*Number(ctax);
		$("#urgentCost").val(urgentCost.toFixed(2));
	}
	setSumMoney();
}
			  function setLowestFreight(){
			  var ctax = document.getElementById("containTax").value;
			    if(!ctax || ctax==0){
			    	ctax = 1.08
			    }
			    var lowestFreight = $("#lowestFreight").val();
				if(!lowestFreight){
					lowestFreight=0;
				}else{
					lowestFreight = Number(lowestFreight)*Number(ctax);
					$("#lowestFreight").val(lowestFreight.toFixed(2));
				}
				setSumMoney();
			  }
			  
		   
		
		  //通过是否包含运费设置每行的价格
		  function setPriceByFre2(){
		  	$("#quoteFabricTable tr:gt(0)").each(function(i){
		  		var trId = $(this).attr("id");
		  		var lineNum = trId.substr(2);
			    setVcPrice(lineNum);
				});
		  }
		  //通过优惠金额设置总金额
		function setPriceByDisMoney(dicountMoney){
			if(!dicountMoney){
				dicountMoney = 0;
			}
			var subtotal = $("#subtotal").val();
			if(!subtotal){
				subtotal = 0;
			}
			var sumMoney = Number(subtotal)-Number(dicountMoney);
			$("#sumMoney").val(sumMoney.toFixed(0));
		}
		
		  //根据型号查询以前报过的价格
		 function selPriceByModel(vcCount){
		   	var factoryCode = $("#vcFactoryCode"+vcCount).val();
		    var model = $("#vcModelNum"+vcCount).val();
		    var htCode = $("#htCode"+vcCount).val();
		    var isHtCode = $("#isHtCode"+vcCount).val();
		   art.dialog({
		    id: 'selPriceByModel',
		    content: '请选择你要查价的地区!',
		    button: [
		        {
		            name: '内地报价',
		            callback: function () {
		            	var url = basePath+"admin/quote_selPriceByModel.Q?parentJsp=1&quoteFormate=1&factoryCode="+factoryCode+"&model="+model+"&htCode="+htCode+"&isHtCode="+isHtCode;
		            //	url = encodeURIComponent(url);
		            	window.open(url);
		                return true;
		            },
		            focus: true
		        },
		        {
		            name: '香港报价',
		            callback: function () {
		            	var url = basePath+"admin/quote_selPriceByModel.Q?parentJsp=1&quoteFormate=2&factoryCode="+factoryCode+"&model="+model+"&htCode="+htCode+"&isHtCode="+isHtCode;
		            	window.open(url);
		                return true;
		            }
		        },
		        {
		            name: '关闭'
		        }
		    ]
		});
			 
		   }
		   //切换货币单位设置价格，暂时还没实现
		   function changeHB2(index){
		   	return;
		   }
		
		//复制报价中的布匹
		function copyFabric2(index){
			var vcDis = $("#vcDis"+index).val();
			if(vcDis=="停用"){
				art.dialog.tips('该型号已被停用，不允许被复制');
				return;
			}
			art.dialog.confirm('你确定要复制该型号吗？', function () {
				 //获取最后一行的行号
				var lastIndex = $("table#quoteFabricTable tr").length-1;
				var allTrAray = $("#quoteFabricTable tr:gt(0)");
                if(allTrAray&&allTrAray.length>0){
                    for(var j=0;j<allTrAray.length;j++ ){
                    //	if(allTrAray[j]){
	                    	var allTr = $(allTrAray[j]);
	                        var vcCountindex = allTr.attr("id").substr(2);
	                        if(Number(lastIndex)<Number(vcCountindex)){
	                        	lastIndex = vcCountindex;
	                        }
                    //	}
                        
                    }
                }
				 
			//	var lastIndex = $("table#quoteFabricTable tr").last().find("input[name$='vcIndex']").val();
				//最新行号
				var newLastIndex = Number(lastIndex)+1;
				//复制并设置id的值，再追加到列表中
				var newTr = $("#tr"+index).clone(false).attr("id","tr"+newLastIndex);
				$("table#quoteFabricTable tr").last().after(newTr);
			//	$("#tr"+index).clone(false).attr("id","tr"+newLastIndex).appendTo("#quoteFabricTable");
				//把复制行里面的id属性值全部替换为最新的行号，因为之前每行里面的元素的id属性值都跟行号绑定了，所以复制后要替换成最新的行号
				$("#tr"+newLastIndex).find("[id]").each(function(i){
					var idValue = $(this).attr("id");
					var newIdValue = idValue.replace(index,newLastIndex);
					$(this).attr("id",newIdValue);
					var clickFuctionAttr = $(this).attr("onclick");
					if(clickFuctionAttr){
						var newClickFuctionAttr = clickFuctionAttr.replace("("+index,"("+newLastIndex);
						var lastClickFuctionAttr = newClickFuctionAttr.replace("("+index,"("+newLastIndex);
						$(this).attr("onclick",lastClickFuctionAttr);
					}
					var changeFuctionAttr = $(this).attr("onchange");
					if(changeFuctionAttr){
						var newChangeFuctionAttr = changeFuctionAttr.replace("("+index,"("+newLastIndex);
						var lastChangeFuctionAttr = newChangeFuctionAttr.replace("("+index,"("+newLastIndex);
						$(this).attr("onchange",lastChangeFuctionAttr);
					}
					var keyupFuctionAttr = $(this).attr("onkeyup");
					if(keyupFuctionAttr){
						var newKeyupFuctionAttr = keyupFuctionAttr.replace("("+index,"("+newLastIndex);
						var lastKeyupFuctionAttr = newKeyupFuctionAttr.replace("("+index,"("+newLastIndex);
						$(this).attr("onkeyup",lastKeyupFuctionAttr);
					}
				})
				//重新设置name属性值
				$("#tr"+newLastIndex).find("[name^='quoteFabricList']").each(function(i){
					var nameValue = $(this).attr("name");
					var newNameValue = nameValue.replace(Number(index)-1,Number(newLastIndex)-1);
					$(this).attr("name",newNameValue);
				})
			//	removAndAdd(newLastIndex);
				//设置复制行里面一些默认值,比如行号，序号。
				$("#tr"+newLastIndex).find("input[name$='vcIndex']").val(newLastIndex-1);
				$("#tr"+newLastIndex).find("input[name$='orderId']").val(newLastIndex);
				$("#tr"+newLastIndex).find("input[name$='vcProject']").val(newLastIndex);
				$("#iscopy"+newLastIndex).val("Y");
				$("#isHidden"+newLastIndex).val("0");
				$("#isReplaced"+newLastIndex).val("0");
				$("#replaceId"+newLastIndex).val("");
				$("#isHiddenisReplaced"+newLastIndex).val("");
				$("#replaceRemark"+newLastIndex).val("");
				$("#quoteFabricId"+newLastIndex).val("");
				
			//	$("#qfId"+newLastIndex).val(0);
				setSumMoney();
			}, function () {
				art.dialog.tips('取消操作');
			});
		}
		//删除报价中的布匹
		function delFabric2(index){
		//isHiddenisReplaced
			var rrrr = $("#isReplaced"+index).val();
			var hiddrep = $("#isHiddenisReplaced"+index).val();
			if(1==rrrr || hiddrep!=""){art.dialog({content:"请先解除替代",okVal:"确定",ok:true});return;}
			art.dialog.confirm('你确定要删除该型号吗？', function () {
				$("#tr"+index).remove();
				setSumMoney();
			}, function () {
				art.dialog.tips('取消操作');
			});
		}
		
		function removAndAdd(newLastIndex){
		//先要移除原有的事件，再重新绑定事件
			//$("#copy"+newLastIndex).removeAttr("onClick");
			$("#copy"+newLastIndex).bind("click",{'index':newLastIndex,'type':'copy'},toHandle);
			
			//$("#vcPriceUnit"+newLastIndex).removeAttr("onChange");
			$("#vcPriceUnit"+newLastIndex).bind("change",{'index':newLastIndex,'type':'vcPriceUnit'},toHandle);
			
			//$("#vcSpecialExp"+newLastIndex).removeAttr("onkeyup");
			$("#vcSpecialExp"+newLastIndex).bind("keyup",{'index':newLastIndex,'type':'vcSpecialExp'},toHandle);
			
			//$("#vcDiscount"+newLastIndex).removeAttr("onkeyup");
			$("#vcDiscount"+newLastIndex).bind("keyup",{'index':newLastIndex,'type':'vcDiscount'},toHandle);
			
			//$("#remove"+newLastIndex).removeAttr("onClick");
			$("#remove"+newLastIndex).bind("click",{'index':newLastIndex,'type':'remove'},toHandle);
			
			//$("#uploadFabric"+newLastIndex).removeAttr("onClick");
			$("#uploadFabric"+newLastIndex).bind("click",{'index':newLastIndex,'type':'uploadIMGt'},toHandle);
			
			//$("#replaceFabric"+newLastIndex).removeAttr("onClick");
			$("#replaceFabric"+newLastIndex).bind("click",{'index':newLastIndex,'type':'replaceFabric2tt'},toHandle);
			
			//$("#delreplaceFabric"+newLastIndex).removeAttr("onClick");
			$("#delreplaceFabric"+newLastIndex).bind("click",{'index':newLastIndex,'type':'delreplaceFabric2tt'},toHandle);
			
			//$("#orderQuantity"+newLastIndex).removeAttr("onkeyup");
			$("#orderQuantity"+newLastIndex).bind("keyup",{'index':newLastIndex,'type':'orderQuantity'},toHandle);
			
			//$("#customerQuantity"+newLastIndex).removeAttr("onkeyup");
			$("#customerQuantity"+newLastIndex).bind("keyup",{'index':newLastIndex,'type':'customerQuantity'},toHandle);
			
			//$("#customerUnit"+newLastIndex).removeAttr("onChange");
			$("#customerUnit"+newLastIndex).bind("change",{'index':newLastIndex,'type':'customerQuantity'},toHandle);
			
			//$("#vcTotal"+newLastIndex).removeAttr("onClick");
			$("#vcTotal"+newLastIndex).bind("click",{'index':newLastIndex,'type':'setTrVcTotal'},toHandle);
			
			//$("#dhjbj"+newLastIndex).removeAttr("onClick");
			$("#dhjbj"+newLastIndex).bind("click",{'index':newLastIndex,'type':'dahuojia'},toHandle);
			
			//$("#cgbj"+newLastIndex).removeAttr("onClick");
			$("#cgbj"+newLastIndex).bind("click",{'index':newLastIndex,'type':'cgjia'},toHandle);
		}
		//通过jQuery绑定事件的统一调用这个方法
		function toHandle(e){
			var vcindex = e.data.index;
			var type = e.data.type;
			if("copy"==type){
				copyFabric2(vcindex);
			}else if("vcPriceUnit"==type || "vcSpecialExp"==type || "vcDiscount"==type){
				setVcPrice(vcindex);
			}else if("vcQuantity"==type){
				setTrVcTotal(vcindex);
			}else if("remove"==type){
				delFabric2(vcindex);
			}else if('uploadIMGt'==type){
				uploadIMGt(vcindex);
			}else if('replaceFabric2tt'==type){
				replaceFabric2tt(vcindex);
			}else if('delreplaceFabric2tt'==type){
				delreplaceFabric2tt(vcindex);
			}else if('orderQuantity'==type){
				changeCustomerUnit(vcindex,1);
			}else if("customerQuantity"==type){
				changeCustomerUnit(vcindex,2);
			}else if("setTrVcTotal"==type){
				setTrVcTotal(vcindex);
			}else if("dahuojia"==type){
				dahuojia(vcindex);
			}else if("cgjia"==type){
				cgjia(vcindex);
			}
			
		}
		
		//改变工程报价或零售报价时，重新去生成HTML
		 function changeQuoteFormate(quoteFormate){
				var quoteId=$("#quoteId").val();
				var ctax = $("#containTax").val();
					$.ajax({
						url:"/bym/quote/quote!ajaxQuoteFabric.jspx",
						type:"POST",
						dataType:"json",
						data:{'quoteFormate':quoteFormate,'quoteId':quoteId,'ctax':ctax},
						success:function(data){
								var stopFabric = "";
								var noFabric = "";
								$("#urgentCost").val(0);
								$("#lowestFreight").val(0);
								$("#subtotal").val(0);
								$("#discountMoney").val(0);
								$("#sumMoney").val(0);
								if(data && data.noFabricList){
									for(var i=0;i<data.noFabricList.length;i++){
										noFabric+=data.noFabricList[i]+","
									}
								}
								
								if(noFabric){
									$("#noFabric").html(noFabric+"没有找到对应的原厂型号");
								}
								if(data && data.stopFabricList){
									for(var i=0;i<data.stopFabricList.length;i++){
									stopFabric+=data.stopFabricList[i]+","
									}
								}
								
								if(stopFabric){
									$("#stopFabric").html(stopFabric+"已停产");
								}
								if(data.fabricList){
									$(window.document).find("#quoteFabricTable tr:gt(0)").remove();
									$("#quoteFabricTable").append(data.fabricList);
								}
						
						}
					
					
					});
				
			}
			//切换报价地时改变报价地的电话和传真
			function setTel(local){
				if("GZ"==local){
					$("#vcFormTel").val("020-83309415");
					$("#vcFormFax").val("020-83363021");
				}else if("SZ"==local){
					$("#vcFormTel").val("0755-82156870");
					$("#vcFormFax").val("0755-82255881");
				}else if("SH"==local){
					$("#vcFormTel").val("021-63457328");
					$("#vcFormFax").val("021-63457650");
				}else if("BJ"==local){
					$("#vcFormTel").val("010-57097646");
					$("#vcFormFax").val("010-67048410");
				}else if("HK"==local){
					$("#vcFormTel").val("852-37412235");
					$("#vcFormFax").val("852-3741223");
				}
			}
			//根据销售的名字去找对应的手机号码
			function setSellPhone(sellname){
				if(sellname){
					$.ajax({
						url:basePath+"admin/quote_getSellPhone.Q?sellname="+sellname,
						dataType:"json",
						success:function(data){
							if(data.phone){
								$("#vcFormPhone").val(data.phone);
							}else{
								art.dialog({
								    content: '没找到对应的手机号码',
								    okVal:'确定',
								    ok: true
								});
							}
							if(data.zhname){
								$("#vcFormName").val(data.zhname);
							}else{
								art.dialog({
								    content: '没找到对应的中文名称',
								    okVal:'确定',
								    ok: true
								});
							}
							
						}
					});
				}else{
					$("#vcFormPhone").val("");
					$("#vcFormName").val("");
				}
				
			}
			//设置 	Accept by（在最下面那行）
			function setDeputyName2(){
				var deputyName = $("#vcAttn").val();//询价公司的名字
				$("#deputyName").val(deputyName);
			
			}
			//设置For and On Behalf of（在最下面那行）
			function setDeputyCom2(){
				var deputyCom = $("#vcFrom").val();	//
				$("#deputyCom").val(deputyCom);
			}
			//设置尊敬的XXX
			function setCustomer(){
				var customer = $("#vcAttnName").val();//报价公司的名字
				$("#customer").val(customer);
			}
			function uploadIMGt(vcCount){
				var factoryCode = $("#vcFactoryCode"+vcCount).val();
				var befModel = $("#vcModelNum"+vcCount).val();
				var url = basePath+"admin/quote_toImport.Q?factoryCode="+factoryCode+"&befModel="+befModel;
				art.dialog.open(url, {title: '上传附图片', lock: true, drag:true, width: getClientWidth()*0.8, height: getClientHeight()*0.4});
			}
			
			function changeCustomerUnit(index,num){
					 //面价单位
				    var oldPriceUnit = "";
				      //幅宽
			    	var width = $("#vcWidth"+index).val();
				    
				    var customerQuantity = 0;
				    var vcPriceUnit = "";
				    if(num==2){//客户提供的数量/单位，改变的是换算数量
				    	oldPriceUnit = $("#vcOldPriceUnit"+index).val();
				     	vcPriceUnit = $("#customerUnit"+index).val();
				     	customerQuantity = $("#customerQuantity"+index).val();
				    }else if(num==1){//实际订货数量/单位，改变的是报价数量
				    	customerQuantity = $("#orderQuantity"+index).val();
				    	oldPriceUnit = $("#vcPriceUnit"+index).val();
				    	vcPriceUnit = $("#vcOldPriceUnit"+index).val();
				    	$("#orderQuantityDisplay"+index).html(customerQuantity);
				    }
				    
				    if(!customerQuantity){
				    	customerQuantity=0;
				    }
				    //转换后的数量
				    var conversionQuantity = 0;
				    //sf转㎡
				    if(oldPriceUnit=="㎡" && (vcPriceUnit=="sf" || vcPriceUnit=="SF")){
			   			conversionQuantity = Number(customerQuantity)*0.0929;
			   		}else if((oldPriceUnit=="sf"  || oldPriceUnit=="SF") && vcPriceUnit=="㎡" ){
			   			conversionQuantity = Number(customerQuantity)/0.0929;
			   		}else if((oldPriceUnit=="yd" || oldPriceUnit=="YD") && vcPriceUnit=="㎡" ){
			   			//yd*0.914*幅宽/100=㎡ ==> yd=㎡/0.914/幅宽*100
			   			conversionQuantity = Number(customerQuantity)/0.914/Number(width)*100;
			   		}else if((oldPriceUnit=="m" || oldPriceUnit=="M") && vcPriceUnit=="㎡" ){
			   			// ㎡/【幅宽（cm）*0.01】=m
			   			conversionQuantity = Number(customerQuantity)/(Number(width)*0.01)
			   		}else if((oldPriceUnit=="yd" || oldPriceUnit=="YD") && (vcPriceUnit=="M" || vcPriceUnit=="m")){
			   			//m/0.914=yd
			   			conversionQuantity = Number(customerQuantity)/0.914;
			   		}else if((oldPriceUnit=="m" || oldPriceUnit=="M") && (vcPriceUnit=="yd" ||vcPriceUnit=="YD") ){
			   			conversionQuantity = Number(customerQuantity)*0.914;
			   		}else if((oldPriceUnit=="m" || oldPriceUnit=="M") && (vcPriceUnit=="M" || vcPriceUnit=="m")){
			   			conversionQuantity = Number(customerQuantity);
			   		}else if((oldPriceUnit=="sf" || oldPriceUnit=="SF") && (vcPriceUnit=="sf" || vcPriceUnit=="SF") ){
			   			conversionQuantity = Number(customerQuantity);
			   		}else if(oldPriceUnit=="㎡" && vcPriceUnit=="㎡" ){
			   			conversionQuantity = Number(customerQuantity);
			   		}else if((oldPriceUnit=="yd" || oldPriceUnit=="YD") && (vcPriceUnit=="yd" ||vcPriceUnit=="YD") ){
			   			conversionQuantity = Number(customerQuantity);
			   		}else if(oldPriceUnit=="㎡" && (vcPriceUnit=="M" || vcPriceUnit=="m") ){
			   			//m【幅宽（cm）*0.01】=㎡
			   			conversionQuantity = Number(customerQuantity)*(Number(width)*0.01)
			   		}else if(oldPriceUnit=="㎡" && (vcPriceUnit=="yd" || vcPriceUnit=="YD" )){
			   		//yd*0.914*幅宽/100=㎡
			   			conversionQuantity = Number(customerQuantity)*0.914*Number(width)/100;
			   		}else{
			   			conversionQuantity = Number(customerQuantity);
			   		}
			   		if(num==2){
			   			$("#conversionQuantity"+index).val(conversionQuantity.toFixed(3));
			   		}else if(num==1){
			   			$("#vcQuantity"+index).val((conversionQuantity+0.0049).toFixed(2));
			   		}
			   			
			   }
			   //隐藏型号和显性型号的设置，
			function replaceFabric2tt(index){
				var isReplaced = $("#isReplaced"+index).val();
				var isHiddenisReplaced = $("#isHiddenisReplaced"+index).val();
				if(isReplaced==1 || isHiddenisReplaced!=""){
					art.dialog({content:"请先解除替代",okVal:"确定",ok:true});
					return;
				}
				var replaceSelectHtml = "<select id=\"replaceSelectHtml\"><option value=\"noselect,0,0\">请选择</option>";
				$(window.document).find("#quoteFabricTable tr:gt(0)").each(function(i){
					//var trIndex = i+1;
					var trId = $(this).attr("id");
					var trIndex = trId.substr(2);
					var fabricId = $("#vcModelNum"+trIndex).val()+"_"+$("#vcFactoryCode"+trIndex).val();
					var vcModelNumDisplay = $("#vcModelNumDisplay"+trIndex).val();
					var isReplaced = $("#isReplaced"+trIndex).val();
					var isHiddenisReplaced = $("#isHiddenisReplaced"+trIndex).val();
					var replaceOption = "";
					if(index!=trIndex && !!vcModelNumDisplay && isReplaced==0 && isHiddenisReplaced==""){//排除当前型号、已删除的型号、已是显性型号、已是隐藏型号、
						replaceOption = "<option value=\'"+fabricId+","+vcModelNumDisplay+","+trIndex+"\'>"+vcModelNumDisplay+"</option>";
					}
					
					replaceSelectHtml+=replaceOption;
				})
				replaceSelectHtml += "</select>";
				art.dialog({
				    title: '请选择要替换的型号',
				    content: replaceSelectHtml,
				    ok: function () {
				        var selectValueText = $('#replaceSelectHtml').val();
			    		var valueText = selectValueText.split(",");
			    		if(valueText[0]!="noselect" && valueText[0]!=""){
				    		var isHiddenisReplaced = $("#isHiddenisReplaced"+index).val();
				    		if(isHiddenisReplaced){//把之前设置的替代关系清除
				    			var isReplacedIndex = isHiddenisReplaced.split("_")[1];
				    			$("#isReplaced"+isReplacedIndex).val("0");
				    			$("#replaceRemark"+isReplacedIndex).val("");
				    		}
				    		
				    	//为被点击的型号的总价钱文本框增加一个属性，为了计算小计的时候不把该型号的价钱加上
				    		$("#vcTotal"+index).attr("isHiddenAttr","isHiddenAttr");
				    		$("#isHidden"+index).val("1");//设置被点击的型号为隐藏型号
				    		$("#replaceId"+index).val(valueText[1]);
				    		$("#replaceRemark"+index).val("替代"+valueText[1]+"");
				    		var displayvcModelNum = $("#vcModelNumDisplay"+index).val();
				    		$("#replaceRemark"+valueText[2]).val("被"+displayvcModelNum+"替代");
				    		$("#isReplaced"+valueText[2]).val("1");//设置被选择的型号为被替代型号
				    		$("#isHiddenisReplaced"+index).val(index+"_"+valueText[2]);
				    		setSumMoney();
			    		}
			    		
				        return true;
				    }
				});
			}
			function delreplaceFabric2tt(index){
				var isHiddenisReplaced = $("#isHiddenisReplaced"+index).val();
				if(isHiddenisReplaced){
						var hiddenIndex = isHiddenisReplaced.split("_")[0];
						var replaceIndex = isHiddenisReplaced.split("_")[1];
						$("#vcTotal"+hiddenIndex).removeAttr("isHiddenAttr");
			    		$("#isHidden"+hiddenIndex).val("0");
			    		$("#replaceId"+hiddenIndex).val("");
			    		$("#replaceRemark"+hiddenIndex).val("");
			    		$("#replaceRemark"+replaceIndex).val("");
			    		$("#isReplaced"+replaceIndex).val("0");
			    		$("#isHiddenisReplaced"+hiddenIndex).val("");
			    		setSumMoney();
				}else{
					art.dialog({content:"该型号目前还没设置替代",okVal:"确定",ok:true});
				}
			
			}
			
function setSells(local,salesman) {
	var sell = $("#vcSalesman");
	$.ajax({
		url : basePath+"admin/quote_getSalesByArea.Q",
		type : "POST",
		dataType : "json",
		data : {'area' : local},
		success : function(data) {
			sell.empty();
			sell.append("<option value=''>请选择销售人员</option>");
			for(var i = 0; i < data.length; i++) {
				var opt = "<option value='" + data[i].username + "' ";
				if(salesman == data[i].username) {
					opt += "selected=selected";
				}
				opt += ">" + data[i].username + "</option>";
				sell.append(opt);
			}
		}
	});
}



function autoFill2(){

				var compName = $("#vcAttn").val();//公司名称
				if(!compName){
					return ;
				}
				var url = basePath+"admin/customer_getCustomerByName.Q";
				$.ajax({
					type : "POST",
					url :url,
					dataType :'json',
					data:{"companyName":compName},
					success:function(data){
						$("#vcAttnTel").val(data.tel);
						$("#vcAttnFax").val(data.fax);
						var linkMan = data.linkman;
						var linkManPhone = data.phone;
						var linkManEmail = data.email;
						var linkManAttr = linkMan.split(",");
						var linkManPhoneAttr = linkManPhone.split(",");
						var linkManEmailAttr = linkManEmail.split(",");
						if(linkManAttr.length>1){
							var nameSelect = "<select id=\"vcAttnName\" onchange=\"setLinkPhone(\'"+linkManAttr+"\',\'"+linkManPhoneAttr+"\',\'"+linkManEmailAttr+"\',this.value);\" name=\"quote.vcAttnName\"  style=\"width:219px;float: left;\" class=\"span3\">";
								var nameOption="";
								for(var i=0;i<linkManAttr.length;i++){
									nameOption += "<option value="+linkManAttr[i]+">"+linkManAttr[i]+"</option>";
								}
								nameSelect += nameOption;
								nameSelect += "</select>"
								$("#vcAttnName").replaceWith(nameSelect);
						}else{
							var nameInput = "<input name=\"quote.vcAttnName\" id=\"vcAttnName\" type=\"text\" class=\"span3\"  value=\"\">";
							$("#vcAttnName").replaceWith(nameInput);
						}
						$("#vcAttnName").val(linkManAttr[0]);
						$("#vcAttnPhone").val(linkManPhoneAttr[0]);
						$("#vcAttnEmail").val(linkManEmailAttr[0]);
						setCustomer();
					}
				});
			}
			//根据选择的客户名称设置对应的号码和邮箱
		function setLinkPhone(names,phones,emails,linkname){
			var linkManAttr = names.split(",");
			var linkManPhoneAttr = phones.split(",");
			var linkManEmailAttr = emails.split(",");
			var index = 0;
			for(var i=0;i<linkManAttr.length;i++){
				if(linkname==linkManAttr[i]){
					index = i;
					break;
				}
			}
			var p = "";
			var e = "";
			if(linkManPhoneAttr[index]){
				p=linkManPhoneAttr[index];
			}
			if(linkManEmailAttr[index]){
				e=linkManEmailAttr[index];
			}
			$("#vcAttnPhone").val(p);
			$("#vcAttnEmail").val(e);
			setCustomer();
		}
	function setRefNo(area){
		$.ajax({
			url : basePath+"admin/quote_getRefNo.Q",
			type : "GET",
			dataType : "text",
			data : {'area' : area},
			success : function(data) {
				$("#vcQuoteNum").val(data);
			}
		});
	}
	
	function setPN(){
		var vcQuoteLocal= $("#vcQuoteLocal").val();
		var vcSalesman= $("#vcSalesman").val();
		var vcQuoteNum= $("#vcQuoteNum").val();
		if(!vcQuoteLocal){
			art.dialog({content:"请先选择报价地",okVal:"确定",ok:true});
			return;
		}
		if(!vcSalesman){
			art.dialog({content:"请先选择销售",okVal:"确定",ok:true});
			return;
		}
		if(!vcQuoteNum){
			art.dialog({content:"请填写RefNO",okVal:"确定",ok:true});
			return;
		}
		$("#projectNum").val(vcQuoteLocal+"-"+vcSalesman+"-"+vcQuoteNum);
	}