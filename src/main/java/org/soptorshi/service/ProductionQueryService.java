package org.soptorshi.service;

import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.*;
import org.soptorshi.repository.ProductionRepository;
import org.soptorshi.repository.search.ProductionSearchRepository;
import org.soptorshi.service.dto.ProductionCriteria;
import org.soptorshi.service.dto.ProductionDTO;
import org.soptorshi.service.mapper.ProductionMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.JoinType;
import java.util.List;

/**
 * Service for executing complex queries for Production entities in the database.
 * The main input is a {@link ProductionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductionDTO} or a {@link Page} of {@link ProductionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductionQueryService extends QueryService<Production> {

    private final Logger log = LoggerFactory.getLogger(ProductionQueryService.class);

    private final ProductionRepository productionRepository;

    private final ProductionMapper productionMapper;

    private final ProductionSearchRepository productionSearchRepository;

    public ProductionQueryService(ProductionRepository productionRepository, ProductionMapper productionMapper, ProductionSearchRepository productionSearchRepository) {
        this.productionRepository = productionRepository;
        this.productionMapper = productionMapper;
        this.productionSearchRepository = productionSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ProductionDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductionDTO> findByCriteria(ProductionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Production> specification = createSpecification(criteria);
        return productionMapper.toDto(productionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductionDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductionDTO> findByCriteria(ProductionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Production> specification = createSpecification(criteria);
        return productionRepository.findAll(specification, page)
            .map(productionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Production> specification = createSpecification(criteria);
        return productionRepository.count(specification);
    }

    /**
     * Function to convert ProductionCriteria to a {@link Specification}
     */
    private Specification<Production> createSpecification(ProductionCriteria criteria) {
        Specification<Production> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Production_.id));
            }
            if (criteria.getWeightStep() != null) {
                specification = specification.and(buildSpecification(criteria.getWeightStep(), Production_.weightStep));
            }
            if (criteria.getUnit() != null) {
                specification = specification.and(buildSpecification(criteria.getUnit(), Production_.unit));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), Production_.quantity));
            }
            if (criteria.getByProductDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getByProductDescription(), Production_.byProductDescription));
            }
            if (criteria.getByProductQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getByProductQuantity(), Production_.byProductQuantity));
            }
            if (criteria.getRemarks() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemarks(), Production_.remarks));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Production_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), Production_.createdOn));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdatedBy(), Production_.updatedBy));
            }
            if (criteria.getUpdatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedOn(), Production_.updatedOn));
            }
            if (criteria.getWastage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWastage(), Production_.wastage));
            }
            if (criteria.getProductCategoriesId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductCategoriesId(),
                    root -> root.join(Production_.productCategories, JoinType.LEFT).get(ProductCategory_.id)));
            }
            if (criteria.getProductsId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductsId(),
                    root -> root.join(Production_.products, JoinType.LEFT).get(Product_.id)));
            }
            if (criteria.getRequisitionsId() != null) {
                specification = specification.and(buildSpecification(criteria.getRequisitionsId(),
                    root -> root.join(Production_.requisitions, JoinType.LEFT).get(Requisition_.id)));
            }
        }
        return specification;
    }
}
