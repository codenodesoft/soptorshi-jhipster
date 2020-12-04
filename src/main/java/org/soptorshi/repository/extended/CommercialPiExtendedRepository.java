package org.soptorshi.repository.extended;

import org.soptorshi.domain.CommercialPi;
import org.soptorshi.repository.CommercialPiRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the CommercialPi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommercialPiExtendedRepository extends CommercialPiRepository {

    Optional<CommercialPi> getByPurchaseOrderNoEquals(String purchaseOrderNo);
}
