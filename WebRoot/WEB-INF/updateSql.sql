UPDATE bym_qf_report qfr ,bym_quote_fabric qf SET qfr.orderNo=qf.orderNo WHERE qfr.qfId=qf.id;

UPDATE bym_designerorder de, bym_order o SET de.orderNo=o.orderNo WHERE de.quoteId=o.quoteId;

UPDATE bym_qf_report SET cbModelNum=CONCAT(vcFactoryCode,' ',vcBefModel);

ALTER TABLE bym_order ADD COLUMN qcException VARCHAR(255) DEFAULT '';