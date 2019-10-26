package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.ContraVoucher;
import org.soptorshi.repository.ContraVoucherRepository;
import org.soptorshi.repository.search.ContraVoucherSearchRepository;
import org.soptorshi.service.ContraVoucherService;
import org.soptorshi.service.dto.ContraVoucherDTO;
import org.soptorshi.service.mapper.ContraVoucherMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.ContraVoucherCriteria;
import org.soptorshi.service.ContraVoucherQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;


import static org.soptorshi.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ContraVoucherResource REST controller.
 *
 * @see ContraVoucherResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class ContraVoucherResourceIntTest {

    private static final String DEFAULT_VOUCHER_NO = "AAAAAAAAAA";
    private static final String UPDATED_VOUCHER_NO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_VOUCHER_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VOUCHER_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_POST_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_POST_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_MODIFIED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_ON = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ContraVoucherRepository contraVoucherRepository;

    @Autowired
    private ContraVoucherMapper contraVoucherMapper;

    @Autowired
    private ContraVoucherService contraVoucherService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.ContraVoucherSearchRepositoryMockConfiguration
     */
    @Autowired
    private ContraVoucherSearchRepository mockContraVoucherSearchRepository;

    @Autowired
    private ContraVoucherQueryService contraVoucherQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restContraVoucherMockMvc;

    private ContraVoucher contraVoucher;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContraVoucherResource contraVoucherResource = new ContraVoucherResource(contraVoucherService, contraVoucherQueryService);
        this.restContraVoucherMockMvc = MockMvcBuilders.standaloneSetup(contraVoucherResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContraVoucher createEntity(EntityManager em) {
        ContraVoucher contraVoucher = new ContraVoucher()
            .voucherNo(DEFAULT_VOUCHER_NO)
            .voucherDate(DEFAULT_VOUCHER_DATE)
            .postDate(DEFAULT_POST_DATE)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedOn(DEFAULT_MODIFIED_ON);
        return contraVoucher;
    }

    @Before
    public void initTest() {
        contraVoucher = createEntity(em);
    }

    @Test
    @Transactional
    public void createContraVoucher() throws Exception {
        int databaseSizeBeforeCreate = contraVoucherRepository.findAll().size();

        // Create the ContraVoucher
        ContraVoucherDTO contraVoucherDTO = contraVoucherMapper.toDto(contraVoucher);
        restContraVoucherMockMvc.perform(post("/api/contra-vouchers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contraVoucherDTO)))
            .andExpect(status().isCreated());

        // Validate the ContraVoucher in the database
        List<ContraVoucher> contraVoucherList = contraVoucherRepository.findAll();
        assertThat(contraVoucherList).hasSize(databaseSizeBeforeCreate + 1);
        ContraVoucher testContraVoucher = contraVoucherList.get(contraVoucherList.size() - 1);
        assertThat(testContraVoucher.getVoucherNo()).isEqualTo(DEFAULT_VOUCHER_NO);
        assertThat(testContraVoucher.getVoucherDate()).isEqualTo(DEFAULT_VOUCHER_DATE);
        assertThat(testContraVoucher.getPostDate()).isEqualTo(DEFAULT_POST_DATE);
        assertThat(testContraVoucher.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testContraVoucher.getModifiedOn()).isEqualTo(DEFAULT_MODIFIED_ON);

        // Validate the ContraVoucher in Elasticsearch
        verify(mockContraVoucherSearchRepository, times(1)).save(testContraVoucher);
    }

    @Test
    @Transactional
    public void createContraVoucherWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contraVoucherRepository.findAll().size();

        // Create the ContraVoucher with an existing ID
        contraVoucher.setId(1L);
        ContraVoucherDTO contraVoucherDTO = contraVoucherMapper.toDto(contraVoucher);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContraVoucherMockMvc.perform(post("/api/contra-vouchers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contraVoucherDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContraVoucher in the database
        List<ContraVoucher> contraVoucherList = contraVoucherRepository.findAll();
        assertThat(contraVoucherList).hasSize(databaseSizeBeforeCreate);

        // Validate the ContraVoucher in Elasticsearch
        verify(mockContraVoucherSearchRepository, times(0)).save(contraVoucher);
    }

    @Test
    @Transactional
    public void getAllContraVouchers() throws Exception {
        // Initialize the database
        contraVoucherRepository.saveAndFlush(contraVoucher);

        // Get all the contraVoucherList
        restContraVoucherMockMvc.perform(get("/api/contra-vouchers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contraVoucher.getId().intValue())))
            .andExpect(jsonPath("$.[*].voucherNo").value(hasItem(DEFAULT_VOUCHER_NO.toString())))
            .andExpect(jsonPath("$.[*].voucherDate").value(hasItem(DEFAULT_VOUCHER_DATE.toString())))
            .andExpect(jsonPath("$.[*].postDate").value(hasItem(DEFAULT_POST_DATE.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.toString())))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getContraVoucher() throws Exception {
        // Initialize the database
        contraVoucherRepository.saveAndFlush(contraVoucher);

        // Get the contraVoucher
        restContraVoucherMockMvc.perform(get("/api/contra-vouchers/{id}", contraVoucher.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contraVoucher.getId().intValue()))
            .andExpect(jsonPath("$.voucherNo").value(DEFAULT_VOUCHER_NO.toString()))
            .andExpect(jsonPath("$.voucherDate").value(DEFAULT_VOUCHER_DATE.toString()))
            .andExpect(jsonPath("$.postDate").value(DEFAULT_POST_DATE.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.toString()))
            .andExpect(jsonPath("$.modifiedOn").value(DEFAULT_MODIFIED_ON.toString()));
    }

    @Test
    @Transactional
    public void getAllContraVouchersByVoucherNoIsEqualToSomething() throws Exception {
        // Initialize the database
        contraVoucherRepository.saveAndFlush(contraVoucher);

        // Get all the contraVoucherList where voucherNo equals to DEFAULT_VOUCHER_NO
        defaultContraVoucherShouldBeFound("voucherNo.equals=" + DEFAULT_VOUCHER_NO);

        // Get all the contraVoucherList where voucherNo equals to UPDATED_VOUCHER_NO
        defaultContraVoucherShouldNotBeFound("voucherNo.equals=" + UPDATED_VOUCHER_NO);
    }

    @Test
    @Transactional
    public void getAllContraVouchersByVoucherNoIsInShouldWork() throws Exception {
        // Initialize the database
        contraVoucherRepository.saveAndFlush(contraVoucher);

        // Get all the contraVoucherList where voucherNo in DEFAULT_VOUCHER_NO or UPDATED_VOUCHER_NO
        defaultContraVoucherShouldBeFound("voucherNo.in=" + DEFAULT_VOUCHER_NO + "," + UPDATED_VOUCHER_NO);

        // Get all the contraVoucherList where voucherNo equals to UPDATED_VOUCHER_NO
        defaultContraVoucherShouldNotBeFound("voucherNo.in=" + UPDATED_VOUCHER_NO);
    }

    @Test
    @Transactional
    public void getAllContraVouchersByVoucherNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        contraVoucherRepository.saveAndFlush(contraVoucher);

        // Get all the contraVoucherList where voucherNo is not null
        defaultContraVoucherShouldBeFound("voucherNo.specified=true");

        // Get all the contraVoucherList where voucherNo is null
        defaultContraVoucherShouldNotBeFound("voucherNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllContraVouchersByVoucherDateIsEqualToSomething() throws Exception {
        // Initialize the database
        contraVoucherRepository.saveAndFlush(contraVoucher);

        // Get all the contraVoucherList where voucherDate equals to DEFAULT_VOUCHER_DATE
        defaultContraVoucherShouldBeFound("voucherDate.equals=" + DEFAULT_VOUCHER_DATE);

        // Get all the contraVoucherList where voucherDate equals to UPDATED_VOUCHER_DATE
        defaultContraVoucherShouldNotBeFound("voucherDate.equals=" + UPDATED_VOUCHER_DATE);
    }

    @Test
    @Transactional
    public void getAllContraVouchersByVoucherDateIsInShouldWork() throws Exception {
        // Initialize the database
        contraVoucherRepository.saveAndFlush(contraVoucher);

        // Get all the contraVoucherList where voucherDate in DEFAULT_VOUCHER_DATE or UPDATED_VOUCHER_DATE
        defaultContraVoucherShouldBeFound("voucherDate.in=" + DEFAULT_VOUCHER_DATE + "," + UPDATED_VOUCHER_DATE);

        // Get all the contraVoucherList where voucherDate equals to UPDATED_VOUCHER_DATE
        defaultContraVoucherShouldNotBeFound("voucherDate.in=" + UPDATED_VOUCHER_DATE);
    }

    @Test
    @Transactional
    public void getAllContraVouchersByVoucherDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        contraVoucherRepository.saveAndFlush(contraVoucher);

        // Get all the contraVoucherList where voucherDate is not null
        defaultContraVoucherShouldBeFound("voucherDate.specified=true");

        // Get all the contraVoucherList where voucherDate is null
        defaultContraVoucherShouldNotBeFound("voucherDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllContraVouchersByVoucherDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        contraVoucherRepository.saveAndFlush(contraVoucher);

        // Get all the contraVoucherList where voucherDate greater than or equals to DEFAULT_VOUCHER_DATE
        defaultContraVoucherShouldBeFound("voucherDate.greaterOrEqualThan=" + DEFAULT_VOUCHER_DATE);

        // Get all the contraVoucherList where voucherDate greater than or equals to UPDATED_VOUCHER_DATE
        defaultContraVoucherShouldNotBeFound("voucherDate.greaterOrEqualThan=" + UPDATED_VOUCHER_DATE);
    }

    @Test
    @Transactional
    public void getAllContraVouchersByVoucherDateIsLessThanSomething() throws Exception {
        // Initialize the database
        contraVoucherRepository.saveAndFlush(contraVoucher);

        // Get all the contraVoucherList where voucherDate less than or equals to DEFAULT_VOUCHER_DATE
        defaultContraVoucherShouldNotBeFound("voucherDate.lessThan=" + DEFAULT_VOUCHER_DATE);

        // Get all the contraVoucherList where voucherDate less than or equals to UPDATED_VOUCHER_DATE
        defaultContraVoucherShouldBeFound("voucherDate.lessThan=" + UPDATED_VOUCHER_DATE);
    }


    @Test
    @Transactional
    public void getAllContraVouchersByPostDateIsEqualToSomething() throws Exception {
        // Initialize the database
        contraVoucherRepository.saveAndFlush(contraVoucher);

        // Get all the contraVoucherList where postDate equals to DEFAULT_POST_DATE
        defaultContraVoucherShouldBeFound("postDate.equals=" + DEFAULT_POST_DATE);

        // Get all the contraVoucherList where postDate equals to UPDATED_POST_DATE
        defaultContraVoucherShouldNotBeFound("postDate.equals=" + UPDATED_POST_DATE);
    }

    @Test
    @Transactional
    public void getAllContraVouchersByPostDateIsInShouldWork() throws Exception {
        // Initialize the database
        contraVoucherRepository.saveAndFlush(contraVoucher);

        // Get all the contraVoucherList where postDate in DEFAULT_POST_DATE or UPDATED_POST_DATE
        defaultContraVoucherShouldBeFound("postDate.in=" + DEFAULT_POST_DATE + "," + UPDATED_POST_DATE);

        // Get all the contraVoucherList where postDate equals to UPDATED_POST_DATE
        defaultContraVoucherShouldNotBeFound("postDate.in=" + UPDATED_POST_DATE);
    }

    @Test
    @Transactional
    public void getAllContraVouchersByPostDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        contraVoucherRepository.saveAndFlush(contraVoucher);

        // Get all the contraVoucherList where postDate is not null
        defaultContraVoucherShouldBeFound("postDate.specified=true");

        // Get all the contraVoucherList where postDate is null
        defaultContraVoucherShouldNotBeFound("postDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllContraVouchersByPostDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        contraVoucherRepository.saveAndFlush(contraVoucher);

        // Get all the contraVoucherList where postDate greater than or equals to DEFAULT_POST_DATE
        defaultContraVoucherShouldBeFound("postDate.greaterOrEqualThan=" + DEFAULT_POST_DATE);

        // Get all the contraVoucherList where postDate greater than or equals to UPDATED_POST_DATE
        defaultContraVoucherShouldNotBeFound("postDate.greaterOrEqualThan=" + UPDATED_POST_DATE);
    }

    @Test
    @Transactional
    public void getAllContraVouchersByPostDateIsLessThanSomething() throws Exception {
        // Initialize the database
        contraVoucherRepository.saveAndFlush(contraVoucher);

        // Get all the contraVoucherList where postDate less than or equals to DEFAULT_POST_DATE
        defaultContraVoucherShouldNotBeFound("postDate.lessThan=" + DEFAULT_POST_DATE);

        // Get all the contraVoucherList where postDate less than or equals to UPDATED_POST_DATE
        defaultContraVoucherShouldBeFound("postDate.lessThan=" + UPDATED_POST_DATE);
    }


    @Test
    @Transactional
    public void getAllContraVouchersByModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        contraVoucherRepository.saveAndFlush(contraVoucher);

        // Get all the contraVoucherList where modifiedBy equals to DEFAULT_MODIFIED_BY
        defaultContraVoucherShouldBeFound("modifiedBy.equals=" + DEFAULT_MODIFIED_BY);

        // Get all the contraVoucherList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultContraVoucherShouldNotBeFound("modifiedBy.equals=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllContraVouchersByModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        contraVoucherRepository.saveAndFlush(contraVoucher);

        // Get all the contraVoucherList where modifiedBy in DEFAULT_MODIFIED_BY or UPDATED_MODIFIED_BY
        defaultContraVoucherShouldBeFound("modifiedBy.in=" + DEFAULT_MODIFIED_BY + "," + UPDATED_MODIFIED_BY);

        // Get all the contraVoucherList where modifiedBy equals to UPDATED_MODIFIED_BY
        defaultContraVoucherShouldNotBeFound("modifiedBy.in=" + UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllContraVouchersByModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        contraVoucherRepository.saveAndFlush(contraVoucher);

        // Get all the contraVoucherList where modifiedBy is not null
        defaultContraVoucherShouldBeFound("modifiedBy.specified=true");

        // Get all the contraVoucherList where modifiedBy is null
        defaultContraVoucherShouldNotBeFound("modifiedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllContraVouchersByModifiedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        contraVoucherRepository.saveAndFlush(contraVoucher);

        // Get all the contraVoucherList where modifiedOn equals to DEFAULT_MODIFIED_ON
        defaultContraVoucherShouldBeFound("modifiedOn.equals=" + DEFAULT_MODIFIED_ON);

        // Get all the contraVoucherList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultContraVoucherShouldNotBeFound("modifiedOn.equals=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllContraVouchersByModifiedOnIsInShouldWork() throws Exception {
        // Initialize the database
        contraVoucherRepository.saveAndFlush(contraVoucher);

        // Get all the contraVoucherList where modifiedOn in DEFAULT_MODIFIED_ON or UPDATED_MODIFIED_ON
        defaultContraVoucherShouldBeFound("modifiedOn.in=" + DEFAULT_MODIFIED_ON + "," + UPDATED_MODIFIED_ON);

        // Get all the contraVoucherList where modifiedOn equals to UPDATED_MODIFIED_ON
        defaultContraVoucherShouldNotBeFound("modifiedOn.in=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllContraVouchersByModifiedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        contraVoucherRepository.saveAndFlush(contraVoucher);

        // Get all the contraVoucherList where modifiedOn is not null
        defaultContraVoucherShouldBeFound("modifiedOn.specified=true");

        // Get all the contraVoucherList where modifiedOn is null
        defaultContraVoucherShouldNotBeFound("modifiedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllContraVouchersByModifiedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        contraVoucherRepository.saveAndFlush(contraVoucher);

        // Get all the contraVoucherList where modifiedOn greater than or equals to DEFAULT_MODIFIED_ON
        defaultContraVoucherShouldBeFound("modifiedOn.greaterOrEqualThan=" + DEFAULT_MODIFIED_ON);

        // Get all the contraVoucherList where modifiedOn greater than or equals to UPDATED_MODIFIED_ON
        defaultContraVoucherShouldNotBeFound("modifiedOn.greaterOrEqualThan=" + UPDATED_MODIFIED_ON);
    }

    @Test
    @Transactional
    public void getAllContraVouchersByModifiedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        contraVoucherRepository.saveAndFlush(contraVoucher);

        // Get all the contraVoucherList where modifiedOn less than or equals to DEFAULT_MODIFIED_ON
        defaultContraVoucherShouldNotBeFound("modifiedOn.lessThan=" + DEFAULT_MODIFIED_ON);

        // Get all the contraVoucherList where modifiedOn less than or equals to UPDATED_MODIFIED_ON
        defaultContraVoucherShouldBeFound("modifiedOn.lessThan=" + UPDATED_MODIFIED_ON);
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultContraVoucherShouldBeFound(String filter) throws Exception {
        restContraVoucherMockMvc.perform(get("/api/contra-vouchers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contraVoucher.getId().intValue())))
            .andExpect(jsonPath("$.[*].voucherNo").value(hasItem(DEFAULT_VOUCHER_NO)))
            .andExpect(jsonPath("$.[*].voucherDate").value(hasItem(DEFAULT_VOUCHER_DATE.toString())))
            .andExpect(jsonPath("$.[*].postDate").value(hasItem(DEFAULT_POST_DATE.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));

        // Check, that the count call also returns 1
        restContraVoucherMockMvc.perform(get("/api/contra-vouchers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultContraVoucherShouldNotBeFound(String filter) throws Exception {
        restContraVoucherMockMvc.perform(get("/api/contra-vouchers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restContraVoucherMockMvc.perform(get("/api/contra-vouchers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingContraVoucher() throws Exception {
        // Get the contraVoucher
        restContraVoucherMockMvc.perform(get("/api/contra-vouchers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContraVoucher() throws Exception {
        // Initialize the database
        contraVoucherRepository.saveAndFlush(contraVoucher);

        int databaseSizeBeforeUpdate = contraVoucherRepository.findAll().size();

        // Update the contraVoucher
        ContraVoucher updatedContraVoucher = contraVoucherRepository.findById(contraVoucher.getId()).get();
        // Disconnect from session so that the updates on updatedContraVoucher are not directly saved in db
        em.detach(updatedContraVoucher);
        updatedContraVoucher
            .voucherNo(UPDATED_VOUCHER_NO)
            .voucherDate(UPDATED_VOUCHER_DATE)
            .postDate(UPDATED_POST_DATE)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedOn(UPDATED_MODIFIED_ON);
        ContraVoucherDTO contraVoucherDTO = contraVoucherMapper.toDto(updatedContraVoucher);

        restContraVoucherMockMvc.perform(put("/api/contra-vouchers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contraVoucherDTO)))
            .andExpect(status().isOk());

        // Validate the ContraVoucher in the database
        List<ContraVoucher> contraVoucherList = contraVoucherRepository.findAll();
        assertThat(contraVoucherList).hasSize(databaseSizeBeforeUpdate);
        ContraVoucher testContraVoucher = contraVoucherList.get(contraVoucherList.size() - 1);
        assertThat(testContraVoucher.getVoucherNo()).isEqualTo(UPDATED_VOUCHER_NO);
        assertThat(testContraVoucher.getVoucherDate()).isEqualTo(UPDATED_VOUCHER_DATE);
        assertThat(testContraVoucher.getPostDate()).isEqualTo(UPDATED_POST_DATE);
        assertThat(testContraVoucher.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testContraVoucher.getModifiedOn()).isEqualTo(UPDATED_MODIFIED_ON);

        // Validate the ContraVoucher in Elasticsearch
        verify(mockContraVoucherSearchRepository, times(1)).save(testContraVoucher);
    }

    @Test
    @Transactional
    public void updateNonExistingContraVoucher() throws Exception {
        int databaseSizeBeforeUpdate = contraVoucherRepository.findAll().size();

        // Create the ContraVoucher
        ContraVoucherDTO contraVoucherDTO = contraVoucherMapper.toDto(contraVoucher);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContraVoucherMockMvc.perform(put("/api/contra-vouchers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contraVoucherDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContraVoucher in the database
        List<ContraVoucher> contraVoucherList = contraVoucherRepository.findAll();
        assertThat(contraVoucherList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ContraVoucher in Elasticsearch
        verify(mockContraVoucherSearchRepository, times(0)).save(contraVoucher);
    }

    @Test
    @Transactional
    public void deleteContraVoucher() throws Exception {
        // Initialize the database
        contraVoucherRepository.saveAndFlush(contraVoucher);

        int databaseSizeBeforeDelete = contraVoucherRepository.findAll().size();

        // Delete the contraVoucher
        restContraVoucherMockMvc.perform(delete("/api/contra-vouchers/{id}", contraVoucher.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ContraVoucher> contraVoucherList = contraVoucherRepository.findAll();
        assertThat(contraVoucherList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ContraVoucher in Elasticsearch
        verify(mockContraVoucherSearchRepository, times(1)).deleteById(contraVoucher.getId());
    }

    @Test
    @Transactional
    public void searchContraVoucher() throws Exception {
        // Initialize the database
        contraVoucherRepository.saveAndFlush(contraVoucher);
        when(mockContraVoucherSearchRepository.search(queryStringQuery("id:" + contraVoucher.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(contraVoucher), PageRequest.of(0, 1), 1));
        // Search the contraVoucher
        restContraVoucherMockMvc.perform(get("/api/_search/contra-vouchers?query=id:" + contraVoucher.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contraVoucher.getId().intValue())))
            .andExpect(jsonPath("$.[*].voucherNo").value(hasItem(DEFAULT_VOUCHER_NO)))
            .andExpect(jsonPath("$.[*].voucherDate").value(hasItem(DEFAULT_VOUCHER_DATE.toString())))
            .andExpect(jsonPath("$.[*].postDate").value(hasItem(DEFAULT_POST_DATE.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedOn").value(hasItem(DEFAULT_MODIFIED_ON.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContraVoucher.class);
        ContraVoucher contraVoucher1 = new ContraVoucher();
        contraVoucher1.setId(1L);
        ContraVoucher contraVoucher2 = new ContraVoucher();
        contraVoucher2.setId(contraVoucher1.getId());
        assertThat(contraVoucher1).isEqualTo(contraVoucher2);
        contraVoucher2.setId(2L);
        assertThat(contraVoucher1).isNotEqualTo(contraVoucher2);
        contraVoucher1.setId(null);
        assertThat(contraVoucher1).isNotEqualTo(contraVoucher2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContraVoucherDTO.class);
        ContraVoucherDTO contraVoucherDTO1 = new ContraVoucherDTO();
        contraVoucherDTO1.setId(1L);
        ContraVoucherDTO contraVoucherDTO2 = new ContraVoucherDTO();
        assertThat(contraVoucherDTO1).isNotEqualTo(contraVoucherDTO2);
        contraVoucherDTO2.setId(contraVoucherDTO1.getId());
        assertThat(contraVoucherDTO1).isEqualTo(contraVoucherDTO2);
        contraVoucherDTO2.setId(2L);
        assertThat(contraVoucherDTO1).isNotEqualTo(contraVoucherDTO2);
        contraVoucherDTO1.setId(null);
        assertThat(contraVoucherDTO1).isNotEqualTo(contraVoucherDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(contraVoucherMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(contraVoucherMapper.fromId(null)).isNull();
    }
}
