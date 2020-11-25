package org.soptorshi.service;

import org.soptorshi.domain.PaymentVoucher;
import org.soptorshi.repository.PaymentVoucherRepository;
import org.soptorshi.repository.search.PaymentVoucherSearchRepository;
import org.soptorshi.service.dto.PaymentVoucherDTO;
import org.soptorshi.service.mapper.PaymentVoucherMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing PaymentVoucher.
 */
@Service
@Transactional
public class PaymentVoucherService {

    private final Logger log = LoggerFactory.getLogger(PaymentVoucherService.class);

    private final PaymentVoucherRepository paymentVoucherRepository;

    private final PaymentVoucherMapper paymentVoucherMapper;

    private final PaymentVoucherSearchRepository paymentVoucherSearchRepository;

    public PaymentVoucherService(PaymentVoucherRepository paymentVoucherRepository, PaymentVoucherMapper paymentVoucherMapper, PaymentVoucherSearchRepository paymentVoucherSearchRepository) {
        this.paymentVoucherRepository = paymentVoucherRepository;
        this.paymentVoucherMapper = paymentVoucherMapper;
        this.paymentVoucherSearchRepository = paymentVoucherSearchRepository;
    }

    /**
     * Save a paymentVoucher.
     *
     * @param paymentVoucherDTO the entity to save
     * @return the persisted entity
     */
    public PaymentVoucherDTO save(PaymentVoucherDTO paymentVoucherDTO) {
        log.debug("Request to save PaymentVoucher : {}", paymentVoucherDTO);
        PaymentVoucher paymentVoucher = paymentVoucherMapper.toEntity(paymentVoucherDTO);
        paymentVoucher = paymentVoucherRepository.saveAndFlush(paymentVoucher);
        PaymentVoucherDTO result = paymentVoucherMapper.toDto(paymentVoucher);
        paymentVoucherSearchRepository.save(paymentVoucher);
        return result;
    }

    /**
     * Get all the paymentVouchers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PaymentVoucherDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PaymentVouchers");
        return paymentVoucherRepository.findAll(pageable)
            .map(paymentVoucherMapper::toDto);
    }


    /**
     * Get one paymentVoucher by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<PaymentVoucherDTO> findOne(Long id) {
        log.debug("Request to get PaymentVoucher : {}", id);
        return paymentVoucherRepository.findById(id)
            .map(paymentVoucherMapper::toDto);
    }

    /**
     * Delete the paymentVoucher by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PaymentVoucher : {}", id);
        paymentVoucherRepository.deleteById(id);
        paymentVoucherSearchRepository.deleteById(id);
    }

    /**
     * Search for the paymentVoucher corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PaymentVoucherDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PaymentVouchers for query {}", query);
        return paymentVoucherSearchRepository.search(queryStringQuery(query), pageable)
            .map(paymentVoucherMapper::toDto);
    }
}
