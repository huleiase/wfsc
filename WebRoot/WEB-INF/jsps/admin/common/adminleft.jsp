<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.wfsc.common.utils.PermissionId"%>
<%@ taglib prefix="w" uri="/WEB-INF/wfsc.tld"%>

<script type="text/javascript">
	$(function(){
		var topmenu = '${topmenu}';
	    var submenu = '${submenu}';
	    $("#"+topmenu).show();
	    $("#"+submenu).addClass("active");
	 
	});
	
</script>
<!--sidebar-menu-->
<div id="sidebar"><a href="#" class="visible-phone"><i class="icon icon-home"></i> 后台管理系统</a>
  <ul>
    <w:permission permissionId="<%=PermissionId.QUOTE_MENU%>">
	    <li class="submenu" id="m1"> <a href="javascript:void(0);"><i class="icon icon-th-list"></i> <span>报价管理</span> <span class="label label-important">3</span></a>
	      <ul style="display: none" id="quoteUL">
		      <w:permission permissionId="<%=PermissionId.QUOTE_MGT%>"> 
		      	<li id="msub11"><a href="<%=request.getContextPath()%>/admin/quote_manager.Q?topmenu=quoteUL&submenu=msub11"><i class="icon icon-pencil"></i> <span>报价单管理</span></a></li>
		      </w:permission>
		      <w:permission permissionId="<%=PermissionId.QUOTE_DESIGN_FEE_SALE%>">
		       <li id="msub12"><a href="<%=request.getContextPath()%>/admin/designerExpense_managerSell.Q?topmenu=quoteUL&submenu=msub12"><i class="icon icon-pencil"></i> <span>设计费</span></a></li>
		       <li id="msub13"><a href="<%=request.getContextPath()%>/admin/designerExpense_managerSellPerson.Q?topmenu=quoteUL&submenu=msub13"><i class="icon icon-pencil"></i> <span>个人设计费</span></a></li>
		      </w:permission>
	      </ul>
	    </li>
    </w:permission>
    
   <w:permission permissionId="<%=PermissionId.PROCESS_MENU%>">
	    <li class="submenu" id="m2"> <a href="javascript:void(0);"><i class="icon icon-th-list"></i> <span>采购流程</span> <span class="label label-important">4</span></a>
	      <ul style="display: none" id="purchaseUL">
		      <w:permission permissionId="<%=PermissionId.PROCESS_TO_PURCHASE_MGT%>">
		       <li id="msub21"><a href="<%=request.getContextPath()%>/admin/purchase_managerToPur.Q?topmenu=purchaseUL&submenu=msub21"><i class="icon icon-pencil"></i> <span>待采购单管理</span></a></li>
		       </w:permission>
		      <w:permission permissionId="<%=PermissionId.PROCESS_PURCHASE_MGT%>">
		       <li id="msub22"><a href="<%=request.getContextPath()%>/admin/purchase_manager.Q?topmenu=purchaseUL&submenu=msub22"><i class="icon icon-pencil"></i> <span>采购单管理</span></a></li>
		      </w:permission>
		      <w:permission permissionId="<%=PermissionId.PROCESS_ORDER_MGT%>">
		       <li id="msub23"><a href="<%=request.getContextPath()%>/admin/order_manager.Q?topmenu=purchaseUL&submenu=msub23"><i class="icon icon-pencil"></i> <span>订单管理</span></a></li>
		      </w:permission>
		      <w:permission permissionId="<%=PermissionId.PROCESS_STOCk_MGT%>">
		       <li id="msub24"><a href="<%=request.getContextPath()%>/admin/store_manager.Q?topmenu=purchaseUL&submenu=msub24"><i class="icon icon-pencil"></i> <span>库存管理</span></a></li>
		      </w:permission>
	      </ul>
	    </li>
    </w:permission>
    
    <w:permission permissionId="<%=PermissionId.BASIC_MENU%>">
	    <li class="submenu" id="m3"> <a href="javascript:void(0);"><i class="icon icon-th-list"></i> <span>基础数据</span> <span class="label label-important">7</span></a>
	      <ul style="display: none" id="basisUL">
		    <w:permission permissionId="<%=PermissionId.BASIC_DESIGNER_MGT%>">
		     <li id="msub31"><a href="<%=request.getContextPath()%>/admin/designer_manager.Q?topmenu=basisUL&submenu=msub31"><i class="icon icon-pencil"></i> <span>设计师管理</span></a></li>
		    </w:permission>
		    <w:permission permissionId="<%=PermissionId.BASIC_DESIGNCOMPANY_MGT%>">
		     <li id="msub32"><a href="<%=request.getContextPath()%>/admin/designCompany_manager.Q?topmenu=basisUL&submenu=msub32"><i class="icon icon-pencil"></i> <span>设计公司管理</span></a></li>
		    </w:permission>
		    <w:permission permissionId="<%=PermissionId.BASIC_FABRIC_MGT%>">
		     <li id="msub33"><a href="<%=request.getContextPath()%>/admin/fabric_manager.Q?topmenu=basisUL&submenu=msub33"><i class="icon icon-pencil"></i> <span>产品管理</span></a></li>
		    </w:permission>
		    <w:permission permissionId="<%=PermissionId.BASIC_HTFABRIC_MGT%>">
		     <li id="msub34"><a href="<%=request.getContextPath() %>/admin/fabric_managerHT.Q?topmenu=basisUL&submenu=msub34"><i class="icon icon-pencil"></i> <span>对照码管理</span></a></li>
		    </w:permission>
		    <w:permission permissionId="<%=PermissionId.BASIC_CUSTOMER_MGT%>">
		     <li id="msub35"><a href="<%=request.getContextPath()%>/admin/customer_manager.Q?topmenu=basisUL&submenu=msub35"><i class="icon icon-pencil"></i> <span>客户管理</span></a></li>
		    </w:permission>
		    <w:permission permissionId="<%=PermissionId.BASIC_CURRENCY_MGT%>">
		     <li id="msub36"><a href="<%=request.getContextPath()%>/admin/currencyConversion_manager.Q?topmenu=basisUL&submenu=msub36"><i class="icon icon-pencil"></i> <span>货币管理</span></a></li>
		    </w:permission>
		    <w:permission permissionId="<%=PermissionId.BASIC_SUPPLIER_MGT%>">
		     <li id="msub37"><a href="<%=request.getContextPath() %>/admin/supplier_manager.Q?topmenu=basisUL&submenu=msub37"><i class="icon icon-pencil"></i> <span>供应商管理</span></a></li>
		    </w:permission>
	      </ul>
	    </li>
    </w:permission>
   
    <w:permission permissionId="<%=PermissionId.SYSTEM_MENU%>">
     <li class="submenu" id="m4"> <a href="javascript:void(0);"><i class="icon icon-group"></i> <span>系统管理</span><span class="label label-important">4</span> </a>
      <ul style="display: none" id="systemUL">
        <w:permission permissionId="<%=PermissionId.SYSTEM_ADMIN_MGT%>">
         <li id="msub41"><a href="<%=request.getContextPath()%>/admin/admin_manager.Q?topmenu=systemUL&submenu=msub41"><i class="icon icon-pencil"></i> <span>用户管理</span></a></li>
        </w:permission>
        <w:permission permissionId="<%=PermissionId.SYSTEM_ROLE_MGT%>">
         <li id="msub42"><a href="<%=request.getContextPath()%>/admin/sec_roleManager.Q?topmenu=systemUL&submenu=msub42"><i class="icon icon-pencil"></i> <span>角色管理</span></a></li>
        </w:permission>
        <w:permission permissionId="<%=PermissionId.SYSTEM_NOTICE_MGT%>">
         <li id="msub43"><a href="<%=request.getContextPath()%>/admin/note_manager.Q?topmenu=systemUL&submenu=msub43"><i class="icon icon-pencil"></i> <span>公告管理</span></a></li>
        </w:permission>
        <w:permission permissionId="<%=PermissionId.SYSTEM_LOG_MGT%>">
         <li id="msub44"><a href="<%=request.getContextPath()%>/admin/syslog_manager.Q?topmenu=systemUL&submenu=msub44"><i class="icon icon-pencil"></i> <span>日志管理</span></a></li>
        </w:permission>
      </ul>
    </li>
    </w:permission>
    
    <w:permission permissionId="<%=PermissionId.REPORT_MENU%>">
	    <li id="m5" class="submenu"> <a href="javascript:void(0);"><i class="icon icon-bar-chart"></i> <span>统计报表</span> <span class="label label-important">6</span> </a>
	      <ul style="display: none" id="reprotUL">
	        <w:permission permissionId="<%=PermissionId.REPORT_SALE_INCOME_ALL%>">
	         <li id="msub51"><a href="<%=request.getContextPath()%>/admin/designerOrder_managerSellIn.Q?topmenu=reprotUL&submenu=msub51"><i class="icon icon-pencil"></i> <span>销售收入表</span></a></li>
	        </w:permission>
	        <w:permission permissionId="<%=PermissionId.REPORT_SALE_INCOME_PERSONAL%>">
	         <li id="msub52"><a href="<%=request.getContextPath()%>/admin/designerOrder_managerSellInPerson.Q?topmenu=reprotUL&submenu=msub52"><i class="icon icon-pencil"></i> <span>个人销售收入表</span></a></li>
	        </w:permission>
	        <w:permission permissionId="<%=PermissionId.REPORT_SALE_INCOME_DORA%>">
	         <li id="msub53"><a href="<%=request.getContextPath()%>/admin/designerOrder_managerDora.Q?topmenu=reprotUL&submenu=msub53"><i class="icon icon-pencil"></i> <span>销售收入表dora</span></a></li>
	        </w:permission>
	        <w:permission permissionId="<%=PermissionId.REPOTR_GATHERING%>">
	         <li id="msub54"><a href="<%=request.getContextPath()%>/admin/designerOrder_managerInCome.Q?topmenu=reprotUL&submenu=msub54"><i class="icon icon-pencil"></i> <span>收款表</span></a></li>
	        </w:permission>
	        <w:permission permissionId="<%=PermissionId.REPORT_SALE_COST%>">
	         <li id="msub55"><a href="<%=request.getContextPath()%>/admin/designerOrder_managerSellCost.Q?topmenu=reprotUL&submenu=msub55"><i class="icon icon-pencil"></i> <span>销售成本表</span></a></li>
	        </w:permission>
	        <w:permission permissionId="<%=PermissionId.REPORT_MATERIAL_DETAIL%>">
	         <li id="msub56"><a href="<%=request.getContextPath()%>/admin/designerOrder_managerMaterial.Q?topmenu=reprotUL&submenu=msub56"><i class="icon icon-pencil"></i> <span>材料明细</span></a></li>
	        </w:permission>
	      </ul>
	    </li>
    </w:permission>
    
     <w:permission permissionId="<%=PermissionId.REPORT_MENU%>">
	    <li id="m5" class="submenu"> <a href="javascript:void(0);"><i class="icon icon-bar-chart"></i> <span>图形报表</span> <span class="label label-important">1</span> </a>
	      <ul style="display: none" id="echartReprotUL">
	       <!-- <li id="msub65"><a href="<%=request.getContextPath()%>/admin/report_test.Q?topmenu=echartReprotUL&submenu=msub65"><i class="icon icon-pencil"></i> <span>某型号销量</span></a></li>-->
	        <li id="msub66"><a href="<%=request.getContextPath()%>/admin/report_managerMostSell.Q?topmenu=echartReprotUL&submenu=msub66"><i class="icon icon-pencil"></i> <span>型号销量排行</span></a></li>
	      </ul>
	    </li>
    </w:permission>
  </ul>
</div>
<!--close-left-menu-stats-sidebar-->