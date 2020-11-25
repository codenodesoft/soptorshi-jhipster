package org.soptorshi.repository.extended;

import org.soptorshi.domain.PurchaseOrderVoucherRelation;
import org.soptorshi.repository.PurchaseOrderVoucherRelationRepository;

import java.util.List;

public interface PurchaseOrderVoucherRelationExtendedRepository extends PurchaseOrderVoucherRelationRepository {
    Boolean existsByVoucherNo(String voucherNo);

    List<PurchaseOrderVoucherRelation> findByVoucherNo(String voucherNo);

    List<PurchaseOrderVoucherRelation> findByPurchaseOrder_Requisition_Id(Long requisitionId);
}
