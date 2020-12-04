package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.CommercialPi;
import org.soptorshi.domain.enumeration.CommercialPaymentStatus;
import org.soptorshi.domain.enumeration.CommercialPiStatus;
import org.soptorshi.repository.extended.CommercialPiExtendedRepository;
import org.soptorshi.repository.search.CommercialPiSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.CommercialPiService;
import org.soptorshi.service.dto.CommercialPaymentInfoDTO;
import org.soptorshi.service.dto.CommercialPiDTO;
import org.soptorshi.service.mapper.CommercialPiMapper;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

/**
 * Service Implementation for managing CommercialPi.
 */
@Service
@Transactional
public class CommercialPiExtendedService extends CommercialPiService {

    private final Logger log = LoggerFactory.getLogger(CommercialPiExtendedService.class);

    private final CommercialPiExtendedRepository commercialPiRepository;

    private final CommercialPiMapper commercialPiMapper;

    private final CommercialPiSearchRepository commercialPiSearchRepository;

    private final CommercialPaymentInfoExtendedService commercialPaymentInfoExtendedService;

    public CommercialPiExtendedService(CommercialPiExtendedRepository commercialPiRepository, CommercialPiMapper commercialPiMapper, CommercialPiSearchRepository commercialPiSearchRepository, CommercialPaymentInfoExtendedService commercialPaymentInfoExtendedService) {
        super(commercialPiRepository, commercialPiMapper, commercialPiSearchRepository);
        this.commercialPiRepository = commercialPiRepository;
        this.commercialPiMapper = commercialPiMapper;
        this.commercialPiSearchRepository = commercialPiSearchRepository;
        this.commercialPaymentInfoExtendedService = commercialPaymentInfoExtendedService;
    }

    /**
     * Save a commercialPi.
     *
     * @param commercialPiDTO the entity to save
     * @return the persisted entity
     */
    public CommercialPiDTO save(CommercialPiDTO commercialPiDTO) {
        log.debug("Request to save CommercialPi : {}", commercialPiDTO);

        String currentUser = SecurityUtils.getCurrentUserLogin().isPresent() ? SecurityUtils.getCurrentUserLogin().get() : "";
        Instant currentDateTime = Instant.now();

        if (commercialPiDTO.getId() == null) {
            commercialPiDTO.setPiStatus(CommercialPiStatus.WAITING_FOR_PI_APPROVAL_BY_THE_CUSTOMER);
            commercialPiDTO.setCreatedBy(currentUser);
            commercialPiDTO.setCreatedOn(currentDateTime);
            CommercialPi commercialPi = commercialPiMapper.toEntity(commercialPiDTO);
            commercialPi = commercialPiRepository.save(commercialPi);
            CommercialPiDTO result = commercialPiMapper.toDto(commercialPi);
            commercialPiSearchRepository.save(commercialPi);
            return result;
        } else {
            Optional<CommercialPi> currentCommercialPi = commercialPiRepository.findById(commercialPiDTO.getId());

            if (currentCommercialPi.isPresent()) {
                if (currentCommercialPi.get().getPiStatus().equals(CommercialPiStatus.WAITING_FOR_PI_APPROVAL_BY_THE_CUSTOMER)) {

                    if (commercialPiDTO.getPiStatus().equals(CommercialPiStatus.PI_APPROVED_BY_THE_CUSTOMER)) {
                        if (commercialPiDTO.getPurchaseOrderNo().isEmpty()) {
                            throw new BadRequestAlertException("Purchase order number is empty", "commercialPi", "idnull");
                        }

                        CommercialPaymentInfoDTO commercialPaymentInfoDTO = new CommercialPaymentInfoDTO();
                        commercialPaymentInfoDTO.setCommercialPiId(commercialPiDTO.getId());
                        commercialPaymentInfoDTO.setCommercialPiProformaNo(commercialPiDTO.getProformaNo());
                        commercialPaymentInfoDTO.setPaymentStatus(CommercialPaymentStatus.WAITING_FOR_PAYMENT_CONFIRMATION);
                        commercialPaymentInfoDTO.setPaymentType(commercialPiDTO.getPaymentType());
                        commercialPaymentInfoDTO.setTotalAmountPaid(BigDecimal.ZERO);
                        commercialPaymentInfoDTO.setRemainingAmountToPay(BigDecimal.ZERO);
                        // Temporary Solution
                        commercialPaymentInfoDTO.setTotalAmountToPay(BigDecimal.valueOf(Double.parseDouble(commercialPiDTO.getUpdatedBy())));
                        commercialPaymentInfoExtendedService.save(commercialPaymentInfoDTO);
                    }
                    commercialPiDTO.setUpdatedBy(currentUser);
                    commercialPiDTO.setUpdatedOn(currentDateTime);
                    CommercialPi commercialPi = commercialPiMapper.toEntity(commercialPiDTO);
                    commercialPi = commercialPiRepository.save(commercialPi);
                    CommercialPiDTO result = commercialPiMapper.toDto(commercialPi);
                    commercialPiSearchRepository.save(commercialPi);
                    return result;
                }
                throw new BadRequestAlertException("PI already accepted/rejected by the customer", "commercialPi", "idnull");
            }
        }
        return null;
    }

    public Optional<CommercialPi> getByCommercialPo(String commercialPo) {
        return commercialPiRepository.getByPurchaseOrderNoEquals(commercialPo);
    }
}
