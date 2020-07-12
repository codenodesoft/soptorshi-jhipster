package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.CommercialBudget;
import org.soptorshi.domain.enumeration.CommercialBudgetStatus;
import org.soptorshi.domain.enumeration.PaymentType;
import org.soptorshi.repository.extended.CommercialBudgetExtendedRepository;
import org.soptorshi.repository.search.CommercialBudgetSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.CommercialBudgetService;
import org.soptorshi.service.dto.CommercialBudgetDTO;
import org.soptorshi.service.dto.CommercialPiDTO;
import org.soptorshi.service.mapper.CommercialBudgetMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

/**
 * Service Implementation for managing CommercialBudget.
 */
@Service
@Transactional
public class CommercialBudgetExtendedService extends CommercialBudgetService {

    private final Logger log = LoggerFactory.getLogger(CommercialBudgetExtendedService.class);

    private final CommercialBudgetExtendedRepository commercialBudgetExtendedRepository;

    private final CommercialBudgetMapper commercialBudgetMapper;

    private final CommercialBudgetSearchRepository commercialBudgetSearchRepository;

    private final CommercialPiExtendedService commercialPiExtendedService;

    public CommercialBudgetExtendedService(CommercialBudgetExtendedRepository commercialBudgetExtendedRepository, CommercialBudgetMapper commercialBudgetMapper, CommercialBudgetSearchRepository commercialBudgetSearchRepository, CommercialPiExtendedService commercialPiExtendedService) {
        super(commercialBudgetExtendedRepository, commercialBudgetMapper, commercialBudgetSearchRepository);
        this.commercialBudgetExtendedRepository = commercialBudgetExtendedRepository;
        this.commercialBudgetMapper = commercialBudgetMapper;
        this.commercialBudgetSearchRepository = commercialBudgetSearchRepository;
        this.commercialPiExtendedService = commercialPiExtendedService;
    }

    /**
     * Save a commercialBudget.
     *
     * @param commercialBudgetDTO the entity to save
     * @return the persisted entity
     */
    @Transactional
    public CommercialBudgetDTO save(CommercialBudgetDTO commercialBudgetDTO) {
        log.debug("Request to save CommercialBudget : {}", commercialBudgetDTO);

        String currentUser = SecurityUtils.getCurrentUserLogin().isPresent() ? SecurityUtils.getCurrentUserLogin().get() : "";
        Instant currentDateTime = Instant.now();

        if (commercialBudgetDTO.getId() == null) {
            if(!exists(commercialBudgetDTO.getBudgetNo())) {
                commercialBudgetDTO.setBudgetStatus(CommercialBudgetStatus.WAITING_FOR_APPROVAL);
                commercialBudgetDTO.setCreatedBy(currentUser);
                commercialBudgetDTO.setCreatedOn(currentDateTime);
                CommercialBudget commercialBudget = commercialBudgetMapper.toEntity(commercialBudgetDTO);
                commercialBudget = commercialBudgetExtendedRepository.save(commercialBudget);
                CommercialBudgetDTO result = commercialBudgetMapper.toDto(commercialBudget);
                commercialBudgetSearchRepository.save(commercialBudget);
                return result;
            }
        } else {
            Optional<CommercialBudget> currentCommercialBudget = commercialBudgetExtendedRepository.findById(commercialBudgetDTO.getId());
            if (currentCommercialBudget.isPresent()) {
                if (!currentCommercialBudget.get().getBudgetStatus().equals(CommercialBudgetStatus.APPROVED)) {
                    if (commercialBudgetDTO.getBudgetStatus().equals(CommercialBudgetStatus.APPROVED)) {
                        CommercialPiDTO commercialPiDTO = new CommercialPiDTO();
                        commercialPiDTO.setProformaNo(commercialBudgetDTO.getProformaNo());
                        commercialPiDTO.setCompanyName(commercialBudgetDTO.getCompanyName().isEmpty() ? "" : commercialBudgetDTO.getCompanyName());
                        commercialPiDTO.setPaymentType(commercialBudgetDTO.getPaymentType() == null ? PaymentType.OTHERS : commercialBudgetDTO.getPaymentType());
                        commercialPiDTO.setCommercialBudgetId(commercialBudgetDTO.getId());
                        commercialPiDTO.setCommercialBudgetBudgetNo(commercialBudgetDTO.getBudgetNo());
                        commercialPiExtendedService.save(commercialPiDTO);
                    }
                    commercialBudgetDTO.setUpdatedBy(currentUser);
                    commercialBudgetDTO.setUpdatedOn(currentDateTime);
                    CommercialBudget commercialBudget = commercialBudgetMapper.toEntity(commercialBudgetDTO);
                    commercialBudget = commercialBudgetExtendedRepository.save(commercialBudget);
                    CommercialBudgetDTO result = commercialBudgetMapper.toDto(commercialBudget);
                    commercialBudgetSearchRepository.save(commercialBudget);
                    return result;
                }
            }
        }
        return null;
    }

    public boolean exists(String budgetNo) {
        return commercialBudgetExtendedRepository.existsByBudgetNo(budgetNo);
    }
}
