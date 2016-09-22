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
UPDATE bym_qf_report SET bjTotal=vcQuantity*vcPrice,cbTotal=cbQuantity*cbPrice;
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

-- 20160405
CREATE TABLE temp (id bigint(20) NOT NULL AUTO_INCREMENT,quote_id BIGINT(20),freight DECIMAL(10,3) DEFAULT 0 ,PRIMARY KEY (id));

INSERT INTO temp(freight,quote_id) SELECT CASE WHEN qf.VcOldPriceUnit='yd' THEN SUM(qf.vcProFre*qf.orderQuantity*0.914)+q.lowestFreight+q.arriveTransport ELSE SUM(qf.vcProFre*qf.orderQuantity)+q.lowestFreight+q.arriveTransport END freight,q.id quoteId FROM bym_quote q,bym_quote_fabric qf WHERE q.id=qf.quoteId AND q.isFreight='1' AND q.isWritPerm='1' AND (q.quoteFormate='1' OR q.quoteFormate='5') AND qf.isHidden!='1' GROUP BY qf.quoteId;
INSERT INTO temp(freight,quote_id) SELECT CASE WHEN qf.VcOldPriceUnit='yd' THEN SUM(qf.VcRetFre*qf.orderQuantity*0.914)+q.lowestFreight+q.arriveTransport ELSE SUM(qf.VcRetFre*qf.orderQuantity)+q.lowestFreight+q.arriveTransport END freight,q.id quoteId FROM bym_quote q,bym_quote_fabric qf WHERE q.id=qf.quoteId AND q.isFreight='1' AND q.isWritPerm='1' AND q.quoteFormate='2' AND qf.isHidden!='1' GROUP BY qf.quoteId;
INSERT INTO temp(freight,quote_id) SELECT CASE WHEN qf.VcOldPriceUnit='yd' THEN SUM(qf.DhjInlandTransCost*qf.orderQuantity*0.914)+q.lowestFreight+q.arriveTransport ELSE SUM(qf.DhjInlandTransCost*qf.orderQuantity)+q.lowestFreight+q.arriveTransport END freight,q.id quoteId FROM bym_quote q,bym_quote_fabric qf WHERE q.id=qf.quoteId AND q.isFreight='1' AND q.isWritPerm='1' AND q.quoteFormate='3' AND qf.isHidden!='1' GROUP BY qf.quoteId;

UPDATE bym_designerexpense de , temp t SET de.freight=t.freight WHERE de.quoteId=t.quote_id;
UPDATE bym_designerorder de , temp t SET de.bjFreight=t.freight WHERE de.quoteId=t.quote_id;

DROP TABLE temp;

UPDATE bym_designerexpense de ,bym_quote q SET de.realTotel=q.sumMoney-IFNULL(q.vcProcessFre,0)-IFNULL(q.vcInstallFre,0)-q.urgentCost-de.freight-de.taxationCost-IFNULL(q.vcAftertreatment,0)-IFNULL(q.vcOther,0) WHERE de.quoteId=q.id;
UPDATE bym_designerorder SET bjTotel=(bjClTotel+vcProcessFre+lcFre+vcInstallFre+bjFreight)*taxation;
UPDATE bym_designerorder SET profit=ABS(bjTotel)-ABS(cbClTotel)-ABS(cbTotel);
UPDATE bym_designerorder SET profitRate=profit/bjTotel;
UPDATE bym_designerorder SET bjTotel=-bjTotel WHERE bjTotel>0 AND operation='offset';
UPDATE bym_designerorder SET profit=-profit WHERE profit>0 AND operation='offset';
UPDATE bym_designerorder SET profitRate=-profitRate WHERE profitRate>0 AND operation='offset';
UPDATE bym_designerorder SET profitRate=0 WHERE profitRate IS NULL;


-- 20160405
UPDATE bym_order SET tbYearMonth='201603' WHERE id IN (1472,1490,1491,1492,1493,1494,1495);
UPDATE bym_designerorder de ,bym_qf_report qfr SET de.orderId=qfr.bymOrderId WHERE de.id=qfr.doId;

-- 20160413

UPDATE bym_quote_fabric SET SigMoney=SinglePrice * VcPurDis ;
UPDATE bym_quote_fabric SET SigMoney=DhjCost * VcPurDis WHERE SigMoney=0;

UPDATE bym_qf_report qfr,bym_quote_fabric qf SET qfr.orderNum=qf.OrderQuantity WHERE qfr.qfId=qf.id AND qfr.orderNum=0 ;
UPDATE bym_qf_report qfr,bym_quote_fabric qf SET qfr.singleMoney=qf.SigMoney WHERE qfr.qfId=qf.id AND qfr.singleMoney=0 ;

UPDATE bym_qf_report qfr,bym_quote_fabric qf SET qfr.cbPriceUnit=qf.VcOldPriceUnit WHERE qfr.qfId=qf.id AND qfr.cbPriceUnit IS NULL;

UPDATE bym_qf_report SET cbQuantity=orderNum WHERE cbQuantity=0;
UPDATE bym_qf_report SET cbPrice=singleMoney WHERE cbPrice=0;
UPDATE bym_qf_report SET cbTotal=cbQuantity*cbPrice WHERE cbTotal=0;

UPDATE bym_qf_report qfr,bym_quote_fabric qf SET qfr.amountrmb=qf.amountrmb WHERE qfr.qfId=qf.id AND qfr.amountrmb=0;

UPDATE bym_quote_fabric SET amountrmb=vcQuoteNum * shijia * exchangeRate WHERE amountrmb=0;
UPDATE bym_quote_fabric SET amountrmb=OrderQuantity * SigMoney * exchangeRate WHERE amountrmb=0;
UPDATE bym_qf_report qfr,bym_quote_fabric qf SET qfr.amountrmb=qf.amountrmb WHERE qfr.qfId=qf.id AND qfr.amountrmb=0;
UPDATE bym_qf_report SET amountrmb=amountrmb*1.2 WHERE vcMoney='HKD';
UPDATE bym_qf_report SET amountrmb=cbTotal WHERE priceCur='RMB';


UPDATE bym_qf_report SET cbTotal=-cbTotal WHERE cbTotal>0 AND operation='offset';
UPDATE bym_qf_report SET amountrmb=-amountrmb WHERE amountrmb>0 AND operation='offset';
UPDATE bym_qf_report SET sellProfit=ABS(bjTotal)-ABS(amountrmb);
UPDATE bym_qf_report SET sellProfitRate=sellProfit/bjTotal;
UPDATE bym_qf_report SET sellProfit=-sellProfit WHERE sellProfit>0 AND operation='offset';



-- 20130421

alter table bym_designerorder add column taxes decimal(10,3) DEFAULT 0;
update bym_designerorder bd,bym_quote bq set bd.taxes=bq.taxes where bd.quoteId=bq.id;

UPDATE bym_qf_report SET bjTotal=vcQuantity*vcPrice;
UPDATE bym_qf_report SET bjTotal=-bjTotal WHERE bjTotal>0 AND operation='offset';
UPDATE bym_qf_report SET sellProfit=ABS(bjTotal)-ABS(amountrmb);
UPDATE bym_qf_report SET sellProfitRate=sellProfit/bjTotal;
UPDATE bym_qf_report SET sellProfit=-sellProfit WHERE sellProfit>0 AND operation='offset';
UPDATE bym_qf_report SET sellProfitRate=0 WHERE sellProfitRate IS NULL;

DROP TABLE temp;
CREATE TABLE temp (id bigint(20) NOT NULL AUTO_INCREMENT,doId BIGINT(20),bjTotal DECIMAL(10,3) DEFAULT 0,cbTotal DECIMAL(10,3) DEFAULT 0 ,PRIMARY KEY (id));
INSERT INTO temp(doId,bjTotal,cbTotal) SELECT doId,SUM(bjTotal),SUM(amountrmb) from bym_qf_report WHERE isReplaced='0' GROUP BY doId;
UPDATE bym_designerorder bd ,temp t SET bd.bjClTotel=t.bjTotal,bd.cbClTotel=t.cbTotal WHERE bd.id=t.doId;
DROP TABLE temp;

update bym_designerorder set bjTotel=sumMoney;
update bym_designerorder set CbTotel=ProcessFee+InstallFee+CbFreight+TravelExpenses+DesignFre+OtherFre;
UPDATE bym_designerorder SET CbTotel=-CbTotel WHERE CbTotel>0 AND operation='offset';

UPDATE bym_designerorder SET profit=ABS(bjTotel)-ABS(cbClTotel)-ABS(cbTotel);
UPDATE bym_designerorder SET profitRate=profit/bjTotel;
UPDATE bym_designerorder SET profit=-profit WHERE profit>0 AND operation='offset';
UPDATE bym_designerorder SET profitRate=0 WHERE profitRate IS NULL;
UPDATE bym_qf_report bqr,bym_quote_fabric bqf SET bqr.bymOrderId=bqf.bymOrderId WHERE bqr.qfId=bqf.id;
UPDATE bym_qf_report bqr,bym_order bo SET bqr.supplier=bo.supplier WHERE bqr.bymOrderId=bo.id;


-- 20160503
UPDATE bym_fabric hf,bym_fabric bf SET hf.refid=bf.id WHERE hf.vcFactoryCode=bf.vcFactoryCode AND hf.vcBefModel=bf.vcBefModel AND hf.isHtCode='1' AND bf.isHtCode='0';

-- 20160504
alter table bym_quote MODIFY sumMoney decimal(15,2);
alter table bym_quote MODIFY subtotal decimal(15,2); 
alter table bym_designerexpense MODIFY sumMoney decimal(15,2);
alter table bym_designerorder MODIFY sumMoney decimal(15,2);
alter table bym_designerorder MODIFY bjTotel decimal(15,2);
alter table bym_qf_report MODIFY sumMoney decimal(15,2);
alter table bym_order MODIFY sumMoney decimal(15,2);

-- 20160728

DELETE FROM wf_syslog WHERE id<79613;

ALTER TABLE bym_qf_report ADD COLUMN vcFre DECIMAL(10,2) DEFAULT 0.00;
UPDATE bym_qf_report qfr,bym_quote_fabric qf SET qfr.vcFre=IFNULL(qf.vcProFre,0)  WHERE qfr.qfId=qf.id AND (qf.quoteFormate='1' OR qf.quoteFormate='3');
UPDATE bym_qf_report qfr,bym_quote_fabric qf SET qfr.vcFre=qf.vcRetFre*1.2 WHERE qfr.qfId=qf.id AND (qf.quoteFormate='2' OR qf.quoteFormate='4'  OR qf.quoteFormate='5');
UPDATE bym_qf_report qfr,bym_quote q SET qfr.vcFre=qfr.vcFre*IFNULL(q.containTax,0) WHERE qfr.quoteId=q.id;

ALTER TABLE bym_qf_report ADD COLUMN vcSpecialExp DECIMAL(10,2) DEFAULT 0.00;
UPDATE bym_qf_report qfr,bym_quote_fabric qf SET qfr.vcSpecialExp=IFNULL(qf.vcSpecialExp,0) WHERE qfr.qfId=qf.id;
UPDATE bym_qf_report qfr,bym_quote q SET qfr.vcSpecialExp=qfr.vcSpecialExp*IFNULL(q.containTax,0) WHERE qfr.quoteId=q.id;

ALTER TABLE bym_qf_report ADD COLUMN vcOldPrice DECIMAL(10,2) DEFAULT 0.00;
UPDATE bym_qf_report qfr,bym_quote_fabric qf SET qfr.vcOldPrice=qf.vcOldPrice*IFNULL(qf.vcDiscount,0) WHERE qfr.qfId=qf.id;
UPDATE bym_qf_report qfr,bym_quote q SET qfr.vcOldPrice=qfr.vcOldPrice*IFNULL(q.containTax,0) WHERE qfr.quoteId=q.id;

ALTER TABLE bym_qf_report ADD COLUMN vcOldPriceTotal DECIMAL(10,2) DEFAULT 0.00;
UPDATE bym_qf_report SET vcOldPriceTotal=IFNULL(vcQuantity,0)*IFNULL(vcOldPrice,0);
UPDATE bym_qf_report SET vcOldPriceTotal=-vcOldPriceTotal WHERE vcOldPriceTotal>0 AND operation='offset';

UPDATE bym_quote_fabric SET amountrmb=IFNULL(vcQuoteNum,0) * IFNULL(shijia,0) * IFNULL(exchangeRate,0) WHERE amountrmb=0;
UPDATE bym_quote_fabric SET amountrmb=IFNULL(OrderQuantity,0) * IFNULL(SigMoney,0) * IFNULL(exchangeRate,0) WHERE amountrmb=0;
UPDATE bym_qf_report qfr,bym_quote_fabric qf SET qfr.amountrmb=qf.amountrmb WHERE qfr.qfId=qf.id AND qfr.amountrmb=0;
UPDATE bym_qf_report SET amountrmb=amountrmb*1.2 WHERE vcMoney='HKD';
UPDATE bym_qf_report SET amountrmb=cbTotal WHERE priceCur='RMB';
UPDATE bym_qf_report SET amountrmb=-amountrmb WHERE amountrmb>0 AND operation='offset';
UPDATE bym_quote_fabric SET amountrmb=0 WHERE amountrmb IS NULL;
UPDATE bym_qf_report SET amountrmb=0 WHERE amountrmb IS NULL;


UPDATE bym_qf_report SET sellProfit=ABS(vcOldPriceTotal)-ABS(amountrmb);
UPDATE bym_qf_report SET sellProfitRate=sellProfit/vcOldPriceTotal;
UPDATE bym_qf_report SET sellProfit=-sellProfit WHERE sellProfit>0 AND operation='offset';
UPDATE bym_qf_report SET sellProfit=0 WHERE sellProfit IS NULL;
UPDATE bym_qf_report SET sellProfitRate=0 WHERE sellProfitRate IS NULL;

ALTER TABLE bym_designerorder ADD COLUMN bjOldPriceTatol DECIMAL(10,2) DEFAULT 0.00;
CREATE TABLE temp (id bigint(20) NOT NULL AUTO_INCREMENT,doId BIGINT(20),vcOldPriceTotal DECIMAL(10,3) DEFAULT 0,cbTotal DECIMAL(10,3) DEFAULT 0 ,PRIMARY KEY (id));
INSERT INTO temp(doId,vcOldPriceTotal,cbTotal) SELECT doId,SUM(vcOldPriceTotal),SUM(amountrmb) from bym_qf_report WHERE isReplaced='0' GROUP BY doId;
UPDATE bym_designerorder bd ,temp t SET bd.bjOldPriceTatol=IFNULL(t.vcOldPriceTotal,0),bd.cbClTotel=IFNULL(t.cbTotal,0) WHERE bd.id=t.doId;
DROP TABLE temp;

UPDATE bym_designerorder SET profit=ABS(bjTotel)-ABS(cbClTotel)-ABS(cbTotel);
UPDATE bym_designerorder SET profitRate=profit/bjTotel;
UPDATE bym_designerorder SET profit=-profit WHERE profit>0 AND operation='offset';
UPDATE bym_designerorder SET profit=0 WHERE profit IS NULL;
UPDATE bym_designerorder SET profitRate=0 WHERE profitRate IS NULL;



-- 20160911
ALTER TABLE bym_quote ADD COLUMN projectType VARCHAR(100);
ALTER TABLE bym_quote_fabric ADD COLUMN priceAdjust DECIMAL(10,2) DEFAULT 0;
ALTER TABLE bym_qf_report ADD COLUMN priceAdjust DECIMAL(10,2) DEFAULT 0;

--20160922
ALTER TABLE bym_qf_report ADD COLUMN bjPrice DECIMAL(10,3) DEFAULT 0;
ALTER TABLE bym_qf_report ADD COLUMN vcWidth DECIMAL(10,3) DEFAULT 1;
UPDATE bym_qf_report qfr,bym_quote_fabric qf SET qfr.vcOldPrice=qf.vcOldPrice*qf.vcDiscount WHERE qfr.qfId=qf.id;
UPDATE bym_qf_report qfr,bym_quote_fabric qf SET qfr.vcWidth=qf.vcWidth WHERE qfr.qfId=qf.id;
UPDATE bym_qf_report qfr,bym_quote_fabric qf SET qfr.cbPriceUnit=qf.vcOldPriceUnit WHERE qfr.qfId=qf.id;


UPDATE bym_qf_report SET bjPrice=vcPrice/taxation-vcFre-vcSpecialExp WHERE cbPriceUnit=vcPriceUnit;

UPDATE bym_qf_report SET bjPrice=(vcOldPrice+priceAdjust)/10.764*taxation WHERE cbPriceUnit='㎡' AND vcPriceUnit='sf';
UPDATE bym_qf_report SET bjPrice=(vcOldPrice+priceAdjust)*10.764*taxation WHERE cbPriceUnit='sf' AND vcPriceUnit='㎡';
UPDATE bym_qf_report SET bjPrice=(vcOldPrice/0.914+priceAdjust)/vcWidth*100*taxation WHERE cbPriceUnit='yd' AND vcPriceUnit='㎡';
UPDATE bym_qf_report SET bjPrice=(vcOldPrice+priceAdjust)/vcWidth*100*taxation WHERE cbPriceUnit='m' AND vcPriceUnit='㎡';
UPDATE bym_qf_report SET bjPrice=(vcOldPrice+priceAdjust)*vcWidth/100*taxation WHERE cbPriceUnit='㎡' AND vcPriceUnit='m';
UPDATE bym_qf_report SET bjPrice=(vcOldPrice/0.914+priceAdjust)*taxation WHERE cbPriceUnit='yd' AND vcPriceUnit='m';
UPDATE bym_qf_report SET bjPrice=(vcOldPrice*0.914+priceAdjust)*taxation WHERE cbPriceUnit='m' AND vcPriceUnit='yd';


UPDATE bym_qf_report SET vcOldPriceTotal=bjPrice*vcQuantity;
UPDATE bym_qf_report SET sellProfit=vcOldPriceTotal-amountrmb;
UPDATE bym_qf_report SET sellProfitRate=sellProfit/vcOldPriceTotal;

CREATE TABLE temp (id bigint(20) NOT NULL AUTO_INCREMENT,quoteId BIGINT(20),vcOldPriceTotal DECIMAL(10,3) DEFAULT 0,amountrmb DECIMAL(10,3) DEFAULT 0 ,PRIMARY KEY (id));
INSERT INTO temp(quoteId,vcOldPriceTotal,amountrmb)
SELECT quoteId,SUM(vcOldPriceTotal),SUM(amountrmb) FROM bym_qf_report GROUP BY quoteId;
UPDATE bym_designerorder deo,temp t SET deo.bjOldPriceTatol=t.vcOldPriceTotal,deo.cbClTotel=t.amountrmb WHERE deo.quoteId=t.quoteId;
DROP TABLE temp;

UPDATE bym_designerorder SET profit=BjTotel-CbClTotel-CbTotel;
UPDATE bym_designerorder SET ProfitRate=profit/BjTotel;
UPDATE bym_designerorder SET ProfitRate=0 WHERE ProfitRate IS NULL;
UPDATE bym_qf_report SET sellProfitRate=0 WHERE sellProfitRate IS NULL;




