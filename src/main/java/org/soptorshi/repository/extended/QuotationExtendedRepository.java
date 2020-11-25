package org.soptorshi.repository.extended;

import org.soptorshi.domain.Quotation;
import org.soptorshi.domain.Requisition;
import org.soptorshi.domain.enumeration.SelectionType;
import org.soptorshi.repository.QuotationRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuotationExtendedRepository extends QuotationRepository {
    Optional<Quotation> findByRequisitionAndAndSelectionStatus(Requisition requisition, SelectionType selectionType);

    Optional<Quotation> findByRequisition_IdAndAndSelectionStatus(Long requisitionId, SelectionType selectionType);

}
