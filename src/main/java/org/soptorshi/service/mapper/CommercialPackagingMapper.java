package org.soptorshi.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.soptorshi.domain.CommercialPackaging;
import org.soptorshi.service.dto.CommercialPackagingDTO;

/**
 * Mapper for the entity CommercialPackaging and its DTO CommercialPackagingDTO.
 */
@Mapper(componentModel = "spring", uses = {CommercialPurchaseOrderMapper.class})
public interface CommercialPackagingMapper extends EntityMapper<CommercialPackagingDTO, CommercialPackaging> {

    @Mapping(source = "commercialPurchaseOrder.id", target = "commercialPurchaseOrderId")
    @Mapping(source = "commercialPurchaseOrder.purchaseOrderNo", target = "commercialPurchaseOrderPurchaseOrderNo")
    CommercialPackagingDTO toDto(CommercialPackaging commercialPackaging);

    @Mapping(source = "commercialPurchaseOrderId", target = "commercialPurchaseOrder")
    CommercialPackaging toEntity(CommercialPackagingDTO commercialPackagingDTO);

    default CommercialPackaging fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommercialPackaging commercialPackaging = new CommercialPackaging();
        commercialPackaging.setId(id);
        return commercialPackaging;
    }
}