/******************************************************************************** 
 * Create Author   : Apex Team
 * Create Date     : Oct 21, 2009
 * File Name       : PermissionId.java
 *
 * Apex OssWorks是上海泰信科技有限公司自主研发的一款IT运维产品，公司拥有完全自主知识产权及专利，
 * 本系统的源代码归公司所有，任何团体或个人不得以任何形式拷贝、反编译、传播，更不得作为商业用途，对
 * 侵犯产品知识产权的任何行为，上海泰信科技有限公司将依法对其追究法律责任。
 *
 * Copyright 1999 - 2009 Tekview Technology Co.,Ltd. All right reserved.
 ********************************************************************************/
package com.wfsc.common.utils;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * 各模块PermissionId的定义文件，PermissionId的定义方式为模块名_PermissionId，该定义需要和数据库中保存的权限项相同
 */
public abstract class PermissionId {

    private static Set<String> permissionIds;
    //报价管理
    public static final String QUOTE_MENU = "quote_menu";
    //报价单管理
    public static final String QUOTE_MGT = "quote_mgt";
    public static final String QUOTE_MGT_ADD = "quote_mgt_add";
    public static final String QUOTE_MGT_UPDATE = "quote_mgt_update";
    public static final String QUOTE_MGT_DELETE = "quote_mgt_delete";
    public static final String QUOTE_MGT_AUDIT = "quote_mgt_audit";
    public static final String QUOTE_MGT_WRITEPERM = "quote_mgt_writPerm";
    public static final String QUOTE_MGT_PRINT = "quote_mgt_print";
    public static final String QUOTE_MGT_DOWNLOAD = "quote_mgt_download";
    public static final String QUOTE_MGT_UPLOAD_ATTACHMENT = "quote_mgt_upload_attachment";
    public static final String QUOTE_MGT_DOWNLOAD_ATTACHMENT = "quote_mgt_download_attachment";
    public static final String QUOTE_MGT_DESIGN = "quote_mgt_design";
    //设计费下载（销售）
    public static final String QUOTE_DESIGN_FEE_SALE = "quote_design_fee_sale";
    //设计费下载（财务）
    public static final String QUOTE_DESIGN_FEE_FINANCE = "quote_design_fee_finance";
    //采购流程
    public static final String PROCESS_MENU = "process_menu";
    //待采购单管理
    public static final String PROCESS_TO_PURCHASE_MGT = "process_to_purchase_mgt";
    public static final String PROCESS_TO_PURCHASE_MGT_UPDATE = "process_to_purchase_mgt_update";
    public static final String PROCESS_TO_PURCHASE_MGT_DELETE = "process_to_purchase_mgt_delete";
    public static final String PROCESS_TO_PURCHASE_MGT_AUDIT = "process_to_purchase_mgt_audit";
    public static final String PROCESS_TO_PURCHASE_MGT_PRINT = "process_to_purchase_mgt_print";
    public static final String PROCESS_TO_PURCHASE_MGT_DOWNLOAD = "process_to_purchase_mgt_download";
    public static final String PROCESS_TO_PURCHASE_MGT_UPLOAD_ATTACHMENT = "process_to_purchase_mgt_upload_attachment";
    public static final String PROCESS_TO_PURCHASE_MGT_DOWNLOAD_ATTACHMENT = "process_to_purchase_mgt_download_attachment";
    public static final String PROCESS_TO_PURCHASE_MGT_PRINTQUOTE = "process_to_purchase_mgt_printQuote";
    
    //采购单管理
    public static final String PROCESS_PURCHASE_MGT = "process_purchase_mgt";
    public static final String PROCESS_PURCHASE_MGT_DELETE = "process_purchase_mgt_delete";
    public static final String PROCESS_PURCHASE_MGT_AUDIT = "process_purchase_mgt_audit";
    public static final String PROCESS_PURCHASE_MGT_PRINT = "process_purchase_mgt_print";
    public static final String PROCESS_PURCHASE_MGT_DOWNLOAD = "process_purchase_mgt_download";
    public static final String PROCESS_PURCHASE_MGT_UPLOAD_ATTACHMENT = "process_purchase_mgt_upload_attachment";
    public static final String PROCESS_PURCHASE_MGT_DOWNLOAD_ATTACHMENT = "process_purchase_mgt_download_attachment";
    //订单管理
    public static final String PROCESS_ORDER_MGT = "process_order_mgt";
    public static final String PROCESS_ORDER_MGT_UPDATE = "process_order_mgt_update";
    public static final String PROCESS_ORDER_MGT_DELETE = "process_order_mgt_delete";
    public static final String PROCESS_ORDER_MGT_AUDIT = "process_order_mgt_audit";
    public static final String PROCESS_ORDER_MGT_PRINT = "process_order_mgt_print";
    public static final String PROCESS_ORDER_MGT_DOWNLOAD = "process_order_mgt_download";
    public static final String PROCESS_ORDER_MGT_UPLOAD_ATTACHMENT = "process_order_mgt_upload_attachment";
    public static final String PROCESS_ORDER_MGT_DOWNLOAD_ATTACHMENT = "process_order_mgt_download_attachment";
    public static final String PROCESS_ORDER_MGT_DESIGN = "process_order_mgt_design";
    //库存管理
    public static final String PROCESS_STOCk_MGT = "process_stock_mgt";
    public static final String PROCESS_STOCk_MGT_ADD = "process_stock_mgt_add";
    public static final String PROCESS_STOCk_MGT_UPDATE = "process_stock_mgt_update";
    public static final String PROCESS_STOCk_MGT_DELETE = "process_stock_mgt_delete";
    public static final String PROCESS_STOCk_MGT_TRANSFER = "process_stock_mgt_transfer";
    public static final String PROCESS_STOCk_MGT_SHIPMENTS = "process_stock_mgt_shipments";
    public static final String PROCESS_STOCk_MGT_DOWNLOAD = "process_stock_mgt_download";
    public static final String PROCESS_STOCk_MGT_ATTACHMENT = "process_stock_mgt_attachment";
    public static final String PROCESS_STOCk_MGT_FABRIC = "process_stock_mgt_fabric";
    public static final String PROCESS_STOCk_MGT_RECORD = "process_stock_mgt_record";
    //基础数据
    public static final String BASIC_MENU = "basic_menu";
    //设计师管理
    public static final String BASIC_DESIGNER_MGT = "basic_designer_mgt";
    public static final String BASIC_DESIGNER_MGT_ADD = "basic_designer_mgt_add";
    public static final String BASIC_DESIGNER_MGT_UPDATE = "basic_designer_mgt_update";
    public static final String BASIC_DESIGNER_MGT_DELETE = "basic_designer_mgt_delete";
    public static final String BASIC_DESIGNER_MGT_DOWNLOAD = "basic_designer_mgt_download";
    //设计公司管理
    public static final String BASIC_DESIGNCOMPANY_MGT = "basic_designcompany_mgt";
    public static final String BASIC_DESIGNCOMPANY_MGT_ADD = "basic_designcompany_mgt_add";
    public static final String BASIC_DESIGNCOMPANY_MGT_UPDATE = "basic_designcompany_mgt_update";
    public static final String BASIC_DESIGNCOMPANY_MGT_DELETE = "basic_designcompany_mgt_delete";
    public static final String BASIC_DESIGNCOMPANY_MGT_DOWNLOAD = "basic_designcompany_mgt_download";
    //产品管理
    public static final String BASIC_FABRIC_MGT = "basic_fabric_mgt";
    public static final String BASIC_FABRIC_MGT_ADD = "basic_fabric_mgt_add";
    public static final String BASIC_FABRIC_MGT_UPDATE = "basic_fabric_mgt_update";
    public static final String BASIC_FABRIC_MGT_DELETE = "basic_fabric_mgt_delete";
    public static final String BASIC_FABRIC_MGT_DOWNLOAD = "basic_fabric_mgt_download";
    //对照码管理
    public static final String BASIC_HTFABRIC_MGT = "basic_htfabric_mgt";
    public static final String BASIC_HTFABRIC_MGT_ADD = "basic_htfabric_mgt_add";
    public static final String BASIC_HTFABRIC_MGT_UPDATE = "basic_htfabric_mgt_update";
    public static final String BASIC_HTFABRIC_MGT_DELETE = "basic_htfabric_mgt_delete";
    public static final String BASIC_HTFABRIC_MGT_DOWNLOAD = "basic_htfabric_mgt_download";
    //客户管理
    public static final String BASIC_CUSTOMER_MGT = "basic_customer_mgt";
    public static final String BASIC_CUSTOMER_MGT_ADD = "basic_customer_mgt_add";
    public static final String BASIC_CUSTOMER_MGT_UPDATE = "basic_customer_mgt_update";
    public static final String BASIC_CUSTOMER_MGT_DELETE = "basic_customer_mgt_delete";
    public static final String BASIC_CUSTOMER_MGT_DOWNLOAD = "basic_customer_mgt_download";
    //货币管理
    public static final String BASIC_CURRENCY_MGT = "basic_currency_mgt";
    public static final String BASIC_CURRENCY_MGT_ADD = "basic_currency_mgt_add";
    public static final String BASIC_CURRENCY_MGT_UPDATE = "basic_currency_mgt_update";
    public static final String BASIC_CURRENCY_MGT_DELETE = "basic_currency_mgt_delete";
    public static final String BASIC_CURRENCY_MGT_DOWNLOAD = "basic_currency_mgt_download";
    //供应商管理
    public static final String BASIC_SUPPLIER_MGT = "basic_supplier_mgt";
    public static final String BASIC_SUPPLIER_MGT_ADD = "basic_supplier_mgt_add";
    public static final String BASIC_SUPPLIER_MGT_UPDATE = "basic_supplier_mgt_update";
    public static final String BASIC_SUPPLIER_MGT_DELETE = "basic_supplier_mgt_delete";
    public static final String BASIC_SUPPLIER_MGT_DOWNLOAD = "basic_supplier_mgt_download";
    //系统管理
    public static final String SYSTEM_MENU = "system_menu";
    //用户管理
    public static final String SYSTEM_ADMIN_MGT = "system_admin_mgt";
    public static final String SYSTEM_ADMIN_MGT_ADD = "system_admin_mgt_add";
    public static final String SYSTEM_ADMIN_MGT_UPDATE = "system_admin_mgt_update";
    public static final String SYSTEM_ADMIN_MGT_DELETE = "system_admin_mgt_delete";
    //角色管理
    public static final String SYSTEM_ROLE_MGT = "system_role_mgt";
    public static final String SYSTEM_ROLE_MGT_ADD = "system_role_mgt_add";
    public static final String SYSTEM_ROLE_MGT_UPDATE = "system_role_mgt_update";
    public static final String SYSTEM_ROLE_MGT_DELETE = "system_role_mgt_delete";
    //公告管理
    public static final String SYSTEM_NOTICE_MGT = "system_notice_mgt";
    public static final String SYSTEM_NOTICE_MGT_ADD = "system_notice_mgt_add";
    public static final String SYSTEM_NOTICE_MGT_UPDATE = "system_notice_mgt_update";
    public static final String SYSTEM_NOTICE_MGT_DELETE = "system_notice_mgt_delete";
    //日志管理
    public static final String SYSTEM_LOG_MGT = "system_log_mgt";
    public static final String SYSTEM_LOG_MGT_ADD = "system_log_mgt_add";
    public static final String SYSTEM_LOG_MGT_UPDATE = "system_log_mgt_update";
    public static final String SYSTEM_LOG_MGT_DELETE = "system_log_mgt_delete";
    //报表统计
    public static final String REPORT_MENU = "report_menu";
    //销售收入表
    public static final String REPORT_SALE_INCOME_ALL = "report_sale_income_all";
    //个人销售收入表
    public static final String REPORT_SALE_INCOME_PERSONAL = "report_sale_income_personal";
    //销售收入表（dora）
    public static final String REPORT_SALE_INCOME_DORA = "report_sale_income_dora";
    //收款表
    public static final String REPOTR_GATHERING = "report_gathering";
    //销售成本表
    public static final String REPORT_SALE_COST = "report_sale_cost";
    //材料明细表
    public static final String REPORT_MATERIAL_DETAIL = "report_material_detail";
    
    public static synchronized Set<String> getAllPermissionIds() {
        if (permissionIds != null) {
            return permissionIds;
        } else {
            permissionIds = new HashSet<String>();
        }
        Field[] fields = PermissionId.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                Object fieldValue = field.get(PermissionId.class);
                if (fieldValue instanceof String) {
                    String value = (String) fieldValue;
                    permissionIds.add(value);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return permissionIds;
    }
}