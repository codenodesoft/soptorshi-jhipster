package org.soptorshi.repository;

import org.soptorshi.domain.SupplyShop;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SupplyShop entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SupplyShopRepository extends JpaRepository<SupplyShop, Long>, JpaSpecificationExecutor<SupplyShop> {

}
