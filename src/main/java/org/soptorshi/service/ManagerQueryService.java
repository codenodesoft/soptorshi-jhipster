package org.soptorshi.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import org.soptorshi.domain.Manager;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.ManagerRepository;
import org.soptorshi.repository.search.ManagerSearchRepository;
import org.soptorshi.service.dto.ManagerCriteria;
import org.soptorshi.service.dto.ManagerDTO;
import org.soptorshi.service.mapper.ManagerMapper;

/**
 * Service for executing complex queries for Manager entities in the database.
 * The main input is a {@link ManagerCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ManagerDTO} or a {@link Page} of {@link ManagerDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ManagerQueryService extends QueryService<Manager> {

    private final Logger log = LoggerFactory.getLogger(ManagerQueryService.class);

    private final ManagerRepository managerRepository;

    private final ManagerMapper managerMapper;

    private final ManagerSearchRepository managerSearchRepository;

    public ManagerQueryService(ManagerRepository managerRepository, ManagerMapper managerMapper, ManagerSearchRepository managerSearchRepository) {
        this.managerRepository = managerRepository;
        this.managerMapper = managerMapper;
        this.managerSearchRepository = managerSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ManagerDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ManagerDTO> findByCriteria(ManagerCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Manager> specification = createSpecification(criteria);
        return managerMapper.toDto(managerRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ManagerDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ManagerDTO> findByCriteria(ManagerCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Manager> specification = createSpecification(criteria);
        return managerRepository.findAll(specification, page)
            .map(managerMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ManagerCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Manager> specification = createSpecification(criteria);
        return managerRepository.count(specification);
    }

    /**
     * Function to convert ManagerCriteria to a {@link Specification}
     */
    private Specification<Manager> createSpecification(ManagerCriteria criteria) {
        Specification<Manager> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Manager_.id));
            }
            if (criteria.getParentEmployeeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getParentEmployeeId(), Manager_.parentEmployeeId));
            }
            if (criteria.getModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModifiedBy(), Manager_.modifiedBy));
            }
            if (criteria.getModifiedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedOn(), Manager_.modifiedOn));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeId(),
                    root -> root.join(Manager_.employee, JoinType.LEFT).get(Employee_.id)));
            }
        }
        return specification;
    }
}
