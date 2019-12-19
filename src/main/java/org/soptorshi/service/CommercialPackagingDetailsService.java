package org.soptorshi.service;

import org.soptorshi.domain.CommercialPackagingDetails;
import org.soptorshi.repository.CommercialPackagingDetailsRepository;
import org.soptorshi.repository.search.CommercialPackagingDetailsSearchRepository;
import org.soptorshi.service.dto.CommercialPackagingDetailsDTO;
import org.soptorshi.service.mapper.CommercialPackagingDetailsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing CommercialPackagingDetails.
 */
@Service
@Transactional
public class CommercialPackagingDetailsService {

    private final Logger log = LoggerFactory.getLogger(CommercialPackagingDetailsService.class);

    private final CommercialPackagingDetailsRepository commercialPackagingDetailsRepository;

    private final CommercialPackagingDetailsMapper commercialPackagingDetailsMapper;

    private final CommercialPackagingDetailsSearchRepository commercialPackagingDetailsSearchRepository;

    public CommercialPackagingDetailsService(CommercialPackagingDetailsRepository commercialPackagingDetailsRepository, CommercialPackagingDetailsMapper commercialPackagingDetailsMapper, CommercialPackagingDetailsSearchRepository commercialPackagingDetailsSearchRepository) {
        this.commercialPackagingDetailsRepository = commercialPackagingDetailsRepository;
        this.commercialPackagingDetailsMapper = commercialPackagingDetailsMapper;
        this.commercialPackagingDetailsSearchRepository = commercialPackagingDetailsSearchRepository;
    }

    /**
     * Save a commercialPackagingDetails.
     *
     * @param commercialPackagingDetailsDTO the entity to save
     * @return the persisted entity
     */
    public CommercialPackagingDetailsDTO save(CommercialPackagingDetailsDTO commercialPackagingDetailsDTO) {
        log.debug("Request to save CommercialPackagingDetails : {}", commercialPackagingDetailsDTO);
        CommercialPackagingDetails commercialPackagingDetails = commercialPackagingDetailsMapper.toEntity(commercialPackagingDetailsDTO);
        commercialPackagingDetails = commercialPackagingDetailsRepository.save(commercialPackagingDetails);
        CommercialPackagingDetailsDTO result = commercialPackagingDetailsMapper.toDto(commercialPackagingDetails);
        commercialPackagingDetailsSearchRepository.save(commercialPackagingDetails);
        return result;
    }

    /**
     * Get all the commercialPackagingDetails.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CommercialPackagingDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CommercialPackagingDetails");
        return commercialPackagingDetailsRepository.findAll(pageable)
            .map(commercialPackagingDetailsMapper::toDto);
    }


    /**
     * Get one commercialPackagingDetails by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<CommercialPackagingDetailsDTO> findOne(Long id) {
        log.debug("Request to get CommercialPackagingDetails : {}", id);
        return commercialPackagingDetailsRepository.findById(id)
            .map(commercialPackagingDetailsMapper::toDto);
    }

    /**
     * Delete the commercialPackagingDetails by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CommercialPackagingDetails : {}", id);
        commercialPackagingDetailsRepository.deleteById(id);
        commercialPackagingDetailsSearchRepository.deleteById(id);
    }

    /**
     * Search for the commercialPackagingDetails corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CommercialPackagingDetailsDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CommercialPackagingDetails for query {}", query);
        return commercialPackagingDetailsSearchRepository.search(queryStringQuery(query), pageable)
            .map(commercialPackagingDetailsMapper::toDto);
    }
}
