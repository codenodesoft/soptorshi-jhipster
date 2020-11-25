package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.VendorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Vendor and its DTO VendorDTO.
 */
@Mapper(componentModel = "spring", uses = {MstAccountMapper.class})
public interface VendorMapper extends EntityMapper<VendorDTO, Vendor> {

    @Mapping(source = "accountId", target = "account")
    Vendor toEntity(VendorDTO dto);

    @Mapping(source = "account.id", target = "accountId")
    @Mapping(source = "account.name", target = "accountName")
    VendorDTO toDto(Vendor entity);

    default Vendor fromId(Long id) {
        if (id == null) {
            return null;
        }
        Vendor vendor = new Vendor();
        vendor.setId(id);
        return vendor;
    }
}
