package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.CommercialBudget;
import org.soptorshi.domain.enumeration.CommercialBudgetStatus;
import org.soptorshi.domain.enumeration.CommercialOrderCategory;
import org.soptorshi.domain.enumeration.PaymentType;
import org.soptorshi.repository.extended.CommercialBudgetExtendedRepository;
import org.soptorshi.repository.search.CommercialBudgetSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.CommercialBudgetService;
import org.soptorshi.service.dto.CommercialBudgetDTO;
import org.soptorshi.service.dto.CommercialPiDTO;
import org.soptorshi.service.mapper.CommercialBudgetMapper;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
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

    @Transactional
    public CommercialBudgetDTO save(CommercialBudgetDTO commercialBudgetDTO) {
        log.debug("Request to save CommercialBudget : {}", commercialBudgetDTO);

        String currentUser = SecurityUtils.getCurrentUserLogin().isPresent() ? SecurityUtils.getCurrentUserLogin().get() : "";
        Instant currentDateTime = Instant.now();

        if (commercialBudgetDTO.getId() == null) {
            if(!exists(commercialBudgetDTO.getBudgetNo())) {
                commercialBudgetDTO.setCreatedBy(currentUser);
                commercialBudgetDTO.setCreatedOn(currentDateTime);
                CommercialBudget commercialBudget = commercialBudgetMapper.toEntity(commercialBudgetDTO);
                commercialBudget = commercialBudgetExtendedRepository.save(commercialBudget);
                CommercialBudgetDTO result = commercialBudgetMapper.toDto(commercialBudget);
                commercialBudgetSearchRepository.save(commercialBudget);
                return result;
            }
            else {
                throw new BadRequestAlertException("Budget Number Conflict", "commercialBudget", "idexists");
            }
        } else {
            Optional<CommercialBudget> currentCommercialBudget = commercialBudgetExtendedRepository.findById(commercialBudgetDTO.getId());
            if (currentCommercialBudget.isPresent()) {
                if (!currentCommercialBudget.get().getBudgetStatus().equals(CommercialBudgetStatus.APPROVED) && !currentCommercialBudget.get().getBudgetStatus().equals(CommercialBudgetStatus.REJECTED)) {
                    if (commercialBudgetDTO.getBudgetStatus().equals(CommercialBudgetStatus.APPROVED)) {
                        CommercialPiDTO commercialPiDTO = new CommercialPiDTO();
                        commercialPiDTO.setProformaNo(commercialBudgetDTO.getProformaNo());
                        commercialPiDTO.setCompanyName(commercialBudgetDTO.getCompanyName().isEmpty() ? "" : commercialBudgetDTO.getCompanyName());
                        commercialPiDTO.setPaymentType(commercialBudgetDTO.getPaymentType() == null ? PaymentType.OTHERS : commercialBudgetDTO.getPaymentType());
                        commercialPiDTO.setCommercialBudgetId(commercialBudgetDTO.getId());
                        commercialPiDTO.setCommercialBudgetBudgetNo(commercialBudgetDTO.getBudgetNo());
                        // Temporary Solution, Need to fix as soon as possible.
                        commercialPiDTO.setUpdatedBy(commercialBudgetDTO.getTotalOfferedPrice().toString());
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
                else {
                    throw new BadRequestAlertException("Already Approved Budget", "commercialBudget", "invalidaccess");
                }
            }
        }
        return null;
    }

    public boolean exists(String budgetNo) {
        return commercialBudgetExtendedRepository.existsByBudgetNo(budgetNo);
    }

    public Optional<CommercialBudget> getByBudgetNo(String budgetNo) {
        return commercialBudgetExtendedRepository.getByBudgetNo(budgetNo);
    }

    public List<CommercialBudget> getAllByBudgetDateGreaterThanEqualAndBudgetDateLessThanEqual(LocalDate fromDate, LocalDate toDate) {
        return commercialBudgetExtendedRepository.getAllByBudgetDateGreaterThanEqualAndBudgetDateLessThanEqual(fromDate, toDate);
    }

    List<CommercialBudget> getAllByTypeAndBudgetDateGreaterThanEqualAndBudgetDateLessThanEqual(CommercialOrderCategory commercialOrderCategory, LocalDate fromDate, LocalDate toDate) {
        return commercialBudgetExtendedRepository.getAllByTypeAndBudgetDateGreaterThanEqualAndBudgetDateLessThanEqual(commercialOrderCategory, fromDate, toDate);
    }

    List<CommercialBudget> getAllByBudgetStatusAndBudgetDateGreaterThanEqualAndBudgetDateLessThanEqual(CommercialBudgetStatus commercialBudgetStatus, LocalDate fromDate, LocalDate toDate) {
        return commercialBudgetExtendedRepository.getAllByBudgetStatusAndBudgetDateGreaterThanEqualAndBudgetDateLessThanEqual(commercialBudgetStatus, fromDate, toDate);
    }

    List<CommercialBudget> getAll() {
        return commercialBudgetExtendedRepository.findAll();
    }

    Optional<CommercialBudget> getByCommercialPi(String proformaNo) {
        return commercialBudgetExtendedRepository.getByProformaNoEquals(proformaNo);
    }
}
