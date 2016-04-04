UPDATE bym_qf_report qfr ,bym_quote_fabric qf SET qfr.orderNo=qf.orderNo WHERE qfr.qfId=qf.id;

UPDATE bym_designerorder de, bym_order o SET de.orderNo=o.orderNo WHERE de.quoteId=o.quoteId;

UPDATE bym_qf_report SET cbModelNum=CONCAT(vcFactoryCode,' ',vcBefModel);

ALTER TABLE bym_order ADD COLUMN qcException VARCHAR(255) DEFAULT '';


-- 2016.03.27


update wf_permission set permissionName='下载附件', actionId='quote_mgt_download_attachment' where id=37;
update wf_permission set permissionName='下载附件', actionId='process_to_purchase_mgt_download_attachment' where id=43;
update wf_permission set permissionName='下载附件', actionId='process_purchase_mgt_download_attachment' where id=48;
update wf_permission set permissionName='下载附件', actionId='process_order_mgt_download_attachment' where id=54;

INSERT INTO `wfsc`.`wf_permission`(`permissionName`,`permissionDescription`,`actionId`,`canDelete`,`parent_permission_id`) VALUES ( '上传附件','','quote_mgt_upload_attachment',0,6); 
INSERT INTO `wfsc`.`wf_permission`(`permissionName`,`permissionDescription`,`actionId`,`canDelete`,`parent_permission_id`) VALUES ( '上传附件','','process_to_purchase_mgt_upload_attachment',0,9); 
INSERT INTO `wfsc`.`wf_permission`(`permissionName`,`permissionDescription`,`actionId`,`canDelete`,`parent_permission_id`) VALUES ( '上传附件','','process_purchase_mgt_upload_attachment',0,10); 
INSERT INTO `wfsc`.`wf_permission`(`permissionName`,`permissionDescription`,`actionId`,`canDelete`,`parent_permission_id`) VALUES ( '上传附件','','process_order_mgt_upload_attachment',0,11);

alter table bym_quote add column taxes decimal(10,3) DEFAULT 0;

alter table bym_qf_report add column taxes decimal(10,3) DEFAULT 0;

UPDATE bym_qf_report qr,bym_quote_fabric qf SET qr.bjColor=qf.vcColorNum,qr.cbColor=qf.vcColorNum,qr.orderNum=qf.orderQuantity,qr.cbQuantity=qf.vcQuoteNum,qr.singleMoney=qf.singlePrice*qf.vcPurDis,qr.cbPrice=qf.shijia,qr.cbPriceUnit=qf.vcOldPriceUnit WHERE qr.qfId=qf.id;

UPDATE bym_qf_report SET sumMoney=-sumMoney WHERE sumMoney>0 AND operation='offset';
UPDATE bym_qf_report SET bjTotal=vcQuantity*vcPrice,cbTotal=cbQuantity*cbPrice;
UPDATE bym_qf_report SET bjTotal=-bjTotal WHERE bjTotal>0 AND operation='offset';
UPDATE bym_qf_report SET cbTotal=-cbTotal WHERE cbTotal>0 AND operation='offset';
UPDATE bym_qf_report SET sellProfit=ABS(bjTotal)-ABS(cbTotal);
UPDATE bym_qf_report SET sellProfitRate=sellProfit/bjTotal;
UPDATE bym_qf_report SET sellProfit=-sellProfit WHERE sellProfit>0 AND operation='offset';
UPDATE bym_qf_report offsetQR,bym_qf_report newQR SET offsetQR.createDate=newQR.createDate WHERE offsetQR.operation='offset' AND newQR.operation='add' AND offsetQR.orderNo=newQR.orderNo;
UPDATE bym_designerorder SET bjTotel=(bjClTotel+vcProcessFre+lcFre+vcInstallFre+bjFreight)*taxation;
UPDATE bym_designerorder de,bym_purchase p SET de.cbClTotel=p.clTotal,de.travelExpenses=p.travelExpenses,de.processFee=p.processFee,de.installFee=p.installFee,de.otherFre=p.otherFre WHERE de.quoteId=p.quoteId;
UPDATE bym_designerorder SET profit=ABS(bjTotel)-ABS(cbClTotel)-ABS(cbTotel);
UPDATE bym_designerorder SET profitRate=profit/bjTotel;
UPDATE bym_designerorder SET bjTotel=-bjTotel WHERE bjTotel>0 AND operation='offset';
UPDATE bym_designerorder SET cbClTotel=-cbClTotel WHERE cbClTotel>0 AND operation='offset';
UPDATE bym_designerorder SET cbTotel=-cbTotel WHERE cbTotel>0 AND operation='offset';
UPDATE bym_designerorder SET profit=-profit WHERE profit>0 AND operation='offset';
UPDATE bym_designerorder SET profitRate=-profitRate WHERE profitRate>0 AND operation='offset';


CREATE TABLE `bym_temp` (
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`fbid` bigint(20) ,
 PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO bym_temp(fbid) SELECT id FROM bym_fabric WHERE isHtCode='1' AND refid NOT IN (SELECT id FROM bym_fabric WHERE isHtCode='0');

UPDATE bym_fabric fb,bym_temp t SET fb.refid = NULL WHERE fb.id=t.fbid;

DROP TABLE bym_temp;

-- 2016.03.29
alter table bym_qf_report add column priceCur VARCHAR(20);
alter table bym_qf_report add column vcMoney VARCHAR(20);
alter table bym_qf_report add column amountrmb DECIMAL(10,3) DEFAULT 0;


UPDATE bym_qf_report qfr,bym_quote_fabric qf SET qfr.cbPriceUnit=qf.vcOldPriceUnit,qfr.priceCur=qf.priceCur,qfr.vcMoney=qf.vcMoney,qfr.amountrmb=qf.amountrmb WHERE qfr.qfId=qf.id;
UPDATE bym_qf_report SET amountrmb=amountrmb*1.2 WHERE vcMoney='HKD';

UPDATE bym_qf_report SET sumMoney=ABS(sumMoney)+taxes;
UPDATE bym_qf_report SET sumMoney=-sumMoney WHERE sumMoney>0 AND operation='offset';
UPDATE bym_qf_report SET bjTotal=vcQuantity*vcPrice+taxes,cbTotal=cbQuantity*cbPrice;
UPDATE bym_qf_report SET bjTotal=-bjTotal WHERE bjTotal>0 AND operation='offset';
UPDATE bym_qf_report SET cbTotal=-cbTotal WHERE cbTotal>0 AND operation='offset';
UPDATE bym_qf_report SET amountrmb=-amountrmb WHERE amountrmb>0 AND operation='offset';
UPDATE bym_qf_report SET sellProfit=ABS(bjTotal)-ABS(amountrmb);
UPDATE bym_qf_report SET sellProfitRate=sellProfit/bjTotal;
UPDATE bym_qf_report SET sellProfit=-sellProfit WHERE sellProfit>0 AND operation='offset';

UPDATE bym_fabric hb,bym_fabric fb SET hb.refid=fb.id WHERE hb.isHtCode='1' AND fb.isHtCode='0' and hb.vcFactoryCode=fb.vcFactoryCode and hb.vcBefModel=fb.vcBefModel;

alter table bym_quote_fabric add column bymOrderId bigint(20);
update bym_quote_fabric qf ,bym_order o set qf.bymOrderId=o.id where qf.IsReplaced!='1' and qf.VcFactoryNum=o.FactoryNum;

-- 20160401

alter table bym_qf_report add column bymOrderId bigint(20);
alter table bym_qf_report add column supplier VARCHAR(100);
UPDATE bym_qf_report qfr,bym_quote_fabric qf SET qfr.bymOrderId=qf.bymOrderId WHERE qfr.qfId=qf.id;
update bym_qf_report qfr ,bym_order o set qfr.supplier=o.supplier where qfr.bymOrderId=o.id;
UPDATE bym_designerorder SET taxationFee=0 where taxationFee is NULL;
UPDATE bym_designerorder SET designer1='' where designer1 is NULL;
UPDATE bym_designerorder SET designer2='' where designer2 is NULL;
UPDATE bym_designerorder SET designer3='' where designer3 is NULL;
UPDATE bym_qf_report SET sellProfitRate=0 where sellProfitRate IS NULL;
