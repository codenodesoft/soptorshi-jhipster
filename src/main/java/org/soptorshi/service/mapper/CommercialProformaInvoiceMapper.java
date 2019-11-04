package org.soptorshi.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.soptorshi.domain.CommercialProformaInvoice;
import org.soptorshi.service.dto.CommercialProformaInvoiceDTO;

/**
 * Mapper for the entity CommercialProformaInvoice and its DTO CommercialProformaInvoiceDTO.
 */
@Mapper(componentModel = "spring", uses = {CommercialPurchaseOrderMapper.class})
public interface CommercialProformaInvoiceMapper extends EntityMapper<CommercialProformaInvoiceDTO, CommercialProformaInvoice> {

    @Mapping(source = "commercialPurchaseOrder.id", target = "commercialPurchaseOrderId")
    @Mapping(source = "commercialPurchaseOrder.purchaseOrderNo", target = "commercialPurchaseOrderPurchaseOrderNo")
    CommercialProformaInvoiceDTO toDto(CommercialProformaInvoice commercialProformaInvoice);

    @Mapping(source = "commercialPurchaseOrderId", target = "commercialPurchaseOrder")
    CommercialProformaInvoice toEntity(CommercialProformaInvoiceDTO commercialProformaInvoiceDTO);

    default CommercialProformaInvoice fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommercialProformaInvoice commercialProformaInvoice = new CommercialProformaInvoice();
        commercialProformaInvoice.setId(id);
        return commercialProformaInvoice;
    }
}
