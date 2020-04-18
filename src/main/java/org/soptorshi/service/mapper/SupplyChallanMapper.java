package org.soptorshi.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.soptorshi.domain.SupplyChallan;
import org.soptorshi.service.dto.SupplyChallanDTO;

/**
 * Mapper for the entity SupplyChallan and its DTO SupplyChallanDTO.
 */
@Mapper(componentModel = "spring", uses = {SupplyOrderMapper.class})
public interface SupplyChallanMapper extends EntityMapper<SupplyChallanDTO, SupplyChallan> {

    @Mapping(source = "supplyOrder.id", target = "supplyOrderId")
    @Mapping(source = "supplyOrder.orderNo", target = "supplyOrderOrderNo")
    SupplyChallanDTO toDto(SupplyChallan supplyChallan);

    @Mapping(source = "supplyOrderId", target = "supplyOrder")
    SupplyChallan toEntity(SupplyChallanDTO supplyChallanDTO);

    default SupplyChallan fromId(Long id) {
        if (id == null) {
            return null;
        }
        SupplyChallan supplyChallan = new SupplyChallan();
        supplyChallan.setId(id);
        return supplyChallan;
    }
}
