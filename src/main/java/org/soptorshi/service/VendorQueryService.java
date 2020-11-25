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

import org.soptorshi.domain.Vendor;
import org.soptorshi.domain.*; // for static metamodels
import org.soptorshi.repository.VendorRepository;
import org.soptorshi.repository.search.VendorSearchRepository;
import org.soptorshi.service.dto.VendorCriteria;
import org.soptorshi.service.dto.VendorDTO;
import org.soptorshi.service.mapper.VendorMapper;

/**
 * Service for executing complex queries for Vendor entities in the database.
 * The main input is a {@link VendorCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link VendorDTO} or a {@link Page} of {@link VendorDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VendorQueryService extends QueryService<Vendor> {

    private final Logger log = LoggerFactory.getLogger(VendorQueryService.class);

    private final VendorRepository vendorRepository;

    private final VendorMapper vendorMapper;

    private final VendorSearchRepository vendorSearchRepository;

    public VendorQueryService(VendorRepository vendorRepository, VendorMapper vendorMapper, VendorSearchRepository vendorSearchRepository) {
        this.vendorRepository = vendorRepository;
        this.vendorMapper = vendorMapper;
        this.vendorSearchRepository = vendorSearchRepository;
    }

    /**
     * Return a {@link List} of {@link VendorDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<VendorDTO> findByCriteria(VendorCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Vendor> specification = createSpecification(criteria);
        return vendorMapper.toDto(vendorRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link VendorDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<VendorDTO> findByCriteria(VendorCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Vendor> specification = createSpecification(criteria);
        return vendorRepository.findAll(specification, page)
            .map(vendorMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VendorCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Vendor> specification = createSpecification(criteria);
        return vendorRepository.count(specification);
    }

    /**
     * Function to convert VendorCriteria to a {@link Specification}
     */
    private Specification<Vendor> createSpecification(VendorCriteria criteria) {
        Specification<Vendor> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Vendor_.id));
            }
            if (criteria.getCompanyName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCompanyName(), Vendor_.companyName));
            }
            if (criteria.getContactNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactNumber(), Vendor_.contactNumber));
            }
            if (criteria.getRemarks() != null) {
                specification = specification.and(buildSpecification(criteria.getRemarks(), Vendor_.remarks));
            }
            if(criteria.getAccountId()!=null){
                specification = specification
                    .and(buildSpecification(
                        criteria.getAccountId(),
                        root-> root.join(Vendor_.account, JoinType.LEFT).get(MstAccount_.id)
                    ));
            }
        }
        return specification;
    }
}
