package org.soptorshi.repository.extended;

import org.soptorshi.domain.CommercialBudget;
import org.soptorshi.domain.enumeration.CommercialBudgetStatus;
import org.soptorshi.domain.enumeration.CommercialOrderCategory;
import org.soptorshi.repository.CommercialBudgetRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the CommercialBudget entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommercialBudgetExtendedRepository extends CommercialBudgetRepository {

    boolean existsByBudgetNo(String budgetNo);

    Optional<CommercialBudget> getByBudgetNo(String budgetNo);

    List<CommercialBudget> getAllByBudgetDateGreaterThanEqualAndBudgetDateLessThanEqual(LocalDate fromDate, LocalDate toDate);

    List<CommercialBudget> getAllByTypeAndBudgetDateGreaterThanEqualAndBudgetDateLessThanEqual(CommercialOrderCategory commercialOrderCategory, LocalDate fromDate, LocalDate toDate);

    List<CommercialBudget> getAllByBudgetStatusAndBudgetDateGreaterThanEqualAndBudgetDateLessThanEqual(CommercialBudgetStatus commercialBudgetStatus, LocalDate fromDate, LocalDate toDate);

    Optional<CommercialBudget> getByProformaNoEquals(String proformaNo);
}
