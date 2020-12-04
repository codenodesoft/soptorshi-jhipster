package org.soptorshi.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.soptorshi.SoptorshiApp;
import org.soptorshi.domain.Product;
import org.soptorshi.domain.ProductCategory;
import org.soptorshi.domain.Production;
import org.soptorshi.domain.Requisition;
import org.soptorshi.domain.enumeration.ProductionWeightStep;
import org.soptorshi.domain.enumeration.UnitOfMeasurements;
import org.soptorshi.repository.ProductionRepository;
import org.soptorshi.repository.search.ProductionSearchRepository;
import org.soptorshi.service.ProductionQueryService;
import org.soptorshi.service.ProductionService;
import org.soptorshi.service.dto.ProductionDTO;
import org.soptorshi.service.mapper.ProductionMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
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
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.soptorshi.web.rest.TestUtil.createFormattingConversionService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Test class for the ProductionResource REST controller.
 *
 * @see ProductionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class ProductionResourceIntTest {

    private static final ProductionWeightStep DEFAULT_WEIGHT_STEP = ProductionWeightStep.RAW;
    private static final ProductionWeightStep UPDATED_WEIGHT_STEP = ProductionWeightStep.FILLET;

    private static final UnitOfMeasurements DEFAULT_UNIT = UnitOfMeasurements.PCS;
    private static final UnitOfMeasurements UPDATED_UNIT = UnitOfMeasurements.KG;

    private static final BigDecimal DEFAULT_QUANTITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_QUANTITY = new BigDecimal(2);

    private static final String DEFAULT_BY_PRODUCT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_BY_PRODUCT_DESCRIPTION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_BY_PRODUCT_QUANTITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_BY_PRODUCT_QUANTITY = new BigDecimal(2);

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final BigDecimal DEFAULT_WASTAGE = new BigDecimal(1);
    private static final BigDecimal UPDATED_WASTAGE = new BigDecimal(2);

    @Autowired
    private ProductionRepository productionRepository;

    @Autowired
    private ProductionMapper productionMapper;

    @Autowired
    private ProductionService productionService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.ProductionSearchRepositoryMockConfiguration
     */
    @Autowired
    private ProductionSearchRepository mockProductionSearchRepository;

    @Autowired
    private ProductionQueryService productionQueryService;

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

    private MockMvc restProductionMockMvc;

    private Production production;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductionResource productionResource = new ProductionResource(productionService, productionQueryService);
        this.restProductionMockMvc = MockMvcBuilders.standaloneSetup(productionResource)
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
    public static Production createEntity(EntityManager em) {
        Production production = new Production()
            .weightStep(DEFAULT_WEIGHT_STEP)
            .unit(DEFAULT_UNIT)
            .quantity(DEFAULT_QUANTITY)
            .byProductDescription(DEFAULT_BY_PRODUCT_DESCRIPTION)
            .byProductQuantity(DEFAULT_BY_PRODUCT_QUANTITY)
            .remarks(DEFAULT_REMARKS)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON)
            .wastage(DEFAULT_WASTAGE);
        return production;
    }

    @Before
    public void initTest() {
        production = createEntity(em);
    }

    @Test
    @Transactional
    public void createProduction() throws Exception {
        int databaseSizeBeforeCreate = productionRepository.findAll().size();

        // Create the Production
        ProductionDTO productionDTO = productionMapper.toDto(production);
        restProductionMockMvc.perform(post("/api/productions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productionDTO)))
            .andExpect(status().isCreated());

        // Validate the Production in the database
        List<Production> productionList = productionRepository.findAll();
        assertThat(productionList).hasSize(databaseSizeBeforeCreate + 1);
        Production testProduction = productionList.get(productionList.size() - 1);
        assertThat(testProduction.getWeightStep()).isEqualTo(DEFAULT_WEIGHT_STEP);
        assertThat(testProduction.getUnit()).isEqualTo(DEFAULT_UNIT);
        assertThat(testProduction.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testProduction.getByProductDescription()).isEqualTo(DEFAULT_BY_PRODUCT_DESCRIPTION);
        assertThat(testProduction.getByProductQuantity()).isEqualTo(DEFAULT_BY_PRODUCT_QUANTITY);
        assertThat(testProduction.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testProduction.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProduction.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testProduction.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testProduction.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testProduction.getWastage()).isEqualTo(DEFAULT_WASTAGE);

        // Validate the Production in Elasticsearch
        verify(mockProductionSearchRepository, times(1)).save(testProduction);
    }

    @Test
    @Transactional
    public void createProductionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productionRepository.findAll().size();

        // Create the Production with an existing ID
        production.setId(1L);
        ProductionDTO productionDTO = productionMapper.toDto(production);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductionMockMvc.perform(post("/api/productions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Production in the database
        List<Production> productionList = productionRepository.findAll();
        assertThat(productionList).hasSize(databaseSizeBeforeCreate);

        // Validate the Production in Elasticsearch
        verify(mockProductionSearchRepository, times(0)).save(production);
    }

    @Test
    @Transactional
    public void checkWeightStepIsRequired() throws Exception {
        int databaseSizeBeforeTest = productionRepository.findAll().size();
        // set the field null
        production.setWeightStep(null);

        // Create the Production, which fails.
        ProductionDTO productionDTO = productionMapper.toDto(production);

        restProductionMockMvc.perform(post("/api/productions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productionDTO)))
            .andExpect(status().isBadRequest());

        List<Production> productionList = productionRepository.findAll();
        assertThat(productionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnitIsRequired() throws Exception {
        int databaseSizeBeforeTest = productionRepository.findAll().size();
        // set the field null
        production.setUnit(null);

        // Create the Production, which fails.
        ProductionDTO productionDTO = productionMapper.toDto(production);

        restProductionMockMvc.perform(post("/api/productions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productionDTO)))
            .andExpect(status().isBadRequest());

        List<Production> productionList = productionRepository.findAll();
        assertThat(productionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = productionRepository.findAll().size();
        // set the field null
        production.setQuantity(null);

        // Create the Production, which fails.
        ProductionDTO productionDTO = productionMapper.toDto(production);

        restProductionMockMvc.perform(post("/api/productions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productionDTO)))
            .andExpect(status().isBadRequest());

        List<Production> productionList = productionRepository.findAll();
        assertThat(productionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductions() throws Exception {
        // Initialize the database
        productionRepository.saveAndFlush(production);

        // Get all the productionList
        restProductionMockMvc.perform(get("/api/productions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(production.getId().intValue())))
            .andExpect(jsonPath("$.[*].weightStep").value(hasItem(DEFAULT_WEIGHT_STEP.toString())))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].byProductDescription").value(hasItem(DEFAULT_BY_PRODUCT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].byProductQuantity").value(hasItem(DEFAULT_BY_PRODUCT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].wastage").value(hasItem(DEFAULT_WASTAGE.intValue())));
    }

    @Test
    @Transactional
    public void getProduction() throws Exception {
        // Initialize the database
        productionRepository.saveAndFlush(production);

        // Get the production
        restProductionMockMvc.perform(get("/api/productions/{id}", production.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(production.getId().intValue()))
            .andExpect(jsonPath("$.weightStep").value(DEFAULT_WEIGHT_STEP.toString()))
            .andExpect(jsonPath("$.unit").value(DEFAULT_UNIT.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.byProductDescription").value(DEFAULT_BY_PRODUCT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.byProductQuantity").value(DEFAULT_BY_PRODUCT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()))
            .andExpect(jsonPath("$.wastage").value(DEFAULT_WASTAGE.intValue()));
    }

    @Test
    @Transactional
    public void getAllProductionsByWeightStepIsEqualToSomething() throws Exception {
        // Initialize the database
        productionRepository.saveAndFlush(production);

        // Get all the productionList where weightStep equals to DEFAULT_WEIGHT_STEP
        defaultProductionShouldBeFound("weightStep.equals=" + DEFAULT_WEIGHT_STEP);

        // Get all the productionList where weightStep equals to UPDATED_WEIGHT_STEP
        defaultProductionShouldNotBeFound("weightStep.equals=" + UPDATED_WEIGHT_STEP);
    }

    @Test
    @Transactional
    public void getAllProductionsByWeightStepIsInShouldWork() throws Exception {
        // Initialize the database
        productionRepository.saveAndFlush(production);

        // Get all the productionList where weightStep in DEFAULT_WEIGHT_STEP or UPDATED_WEIGHT_STEP
        defaultProductionShouldBeFound("weightStep.in=" + DEFAULT_WEIGHT_STEP + "," + UPDATED_WEIGHT_STEP);

        // Get all the productionList where weightStep equals to UPDATED_WEIGHT_STEP
        defaultProductionShouldNotBeFound("weightStep.in=" + UPDATED_WEIGHT_STEP);
    }

    @Test
    @Transactional
    public void getAllProductionsByWeightStepIsNullOrNotNull() throws Exception {
        // Initialize the database
        productionRepository.saveAndFlush(production);

        // Get all the productionList where weightStep is not null
        defaultProductionShouldBeFound("weightStep.specified=true");

        // Get all the productionList where weightStep is null
        defaultProductionShouldNotBeFound("weightStep.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductionsByUnitIsEqualToSomething() throws Exception {
        // Initialize the database
        productionRepository.saveAndFlush(production);

        // Get all the productionList where unit equals to DEFAULT_UNIT
        defaultProductionShouldBeFound("unit.equals=" + DEFAULT_UNIT);

        // Get all the productionList where unit equals to UPDATED_UNIT
        defaultProductionShouldNotBeFound("unit.equals=" + UPDATED_UNIT);
    }

    @Test
    @Transactional
    public void getAllProductionsByUnitIsInShouldWork() throws Exception {
        // Initialize the database
        productionRepository.saveAndFlush(production);

        // Get all the productionList where unit in DEFAULT_UNIT or UPDATED_UNIT
        defaultProductionShouldBeFound("unit.in=" + DEFAULT_UNIT + "," + UPDATED_UNIT);

        // Get all the productionList where unit equals to UPDATED_UNIT
        defaultProductionShouldNotBeFound("unit.in=" + UPDATED_UNIT);
    }

    @Test
    @Transactional
    public void getAllProductionsByUnitIsNullOrNotNull() throws Exception {
        // Initialize the database
        productionRepository.saveAndFlush(production);

        // Get all the productionList where unit is not null
        defaultProductionShouldBeFound("unit.specified=true");

        // Get all the productionList where unit is null
        defaultProductionShouldNotBeFound("unit.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductionsByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        productionRepository.saveAndFlush(production);

        // Get all the productionList where quantity equals to DEFAULT_QUANTITY
        defaultProductionShouldBeFound("quantity.equals=" + DEFAULT_QUANTITY);

        // Get all the productionList where quantity equals to UPDATED_QUANTITY
        defaultProductionShouldNotBeFound("quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllProductionsByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        productionRepository.saveAndFlush(production);

        // Get all the productionList where quantity in DEFAULT_QUANTITY or UPDATED_QUANTITY
        defaultProductionShouldBeFound("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY);

        // Get all the productionList where quantity equals to UPDATED_QUANTITY
        defaultProductionShouldNotBeFound("quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllProductionsByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        productionRepository.saveAndFlush(production);

        // Get all the productionList where quantity is not null
        defaultProductionShouldBeFound("quantity.specified=true");

        // Get all the productionList where quantity is null
        defaultProductionShouldNotBeFound("quantity.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductionsByByProductDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        productionRepository.saveAndFlush(production);

        // Get all the productionList where byProductDescription equals to DEFAULT_BY_PRODUCT_DESCRIPTION
        defaultProductionShouldBeFound("byProductDescription.equals=" + DEFAULT_BY_PRODUCT_DESCRIPTION);

        // Get all the productionList where byProductDescription equals to UPDATED_BY_PRODUCT_DESCRIPTION
        defaultProductionShouldNotBeFound("byProductDescription.equals=" + UPDATED_BY_PRODUCT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProductionsByByProductDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        productionRepository.saveAndFlush(production);

        // Get all the productionList where byProductDescription in DEFAULT_BY_PRODUCT_DESCRIPTION or UPDATED_BY_PRODUCT_DESCRIPTION
        defaultProductionShouldBeFound("byProductDescription.in=" + DEFAULT_BY_PRODUCT_DESCRIPTION + "," + UPDATED_BY_PRODUCT_DESCRIPTION);

        // Get all the productionList where byProductDescription equals to UPDATED_BY_PRODUCT_DESCRIPTION
        defaultProductionShouldNotBeFound("byProductDescription.in=" + UPDATED_BY_PRODUCT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProductionsByByProductDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        productionRepository.saveAndFlush(production);

        // Get all the productionList where byProductDescription is not null
        defaultProductionShouldBeFound("byProductDescription.specified=true");

        // Get all the productionList where byProductDescription is null
        defaultProductionShouldNotBeFound("byProductDescription.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductionsByByProductQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        productionRepository.saveAndFlush(production);

        // Get all the productionList where byProductQuantity equals to DEFAULT_BY_PRODUCT_QUANTITY
        defaultProductionShouldBeFound("byProductQuantity.equals=" + DEFAULT_BY_PRODUCT_QUANTITY);

        // Get all the productionList where byProductQuantity equals to UPDATED_BY_PRODUCT_QUANTITY
        defaultProductionShouldNotBeFound("byProductQuantity.equals=" + UPDATED_BY_PRODUCT_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllProductionsByByProductQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        productionRepository.saveAndFlush(production);

        // Get all the productionList where byProductQuantity in DEFAULT_BY_PRODUCT_QUANTITY or UPDATED_BY_PRODUCT_QUANTITY
        defaultProductionShouldBeFound("byProductQuantity.in=" + DEFAULT_BY_PRODUCT_QUANTITY + "," + UPDATED_BY_PRODUCT_QUANTITY);

        // Get all the productionList where byProductQuantity equals to UPDATED_BY_PRODUCT_QUANTITY
        defaultProductionShouldNotBeFound("byProductQuantity.in=" + UPDATED_BY_PRODUCT_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllProductionsByByProductQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        productionRepository.saveAndFlush(production);

        // Get all the productionList where byProductQuantity is not null
        defaultProductionShouldBeFound("byProductQuantity.specified=true");

        // Get all the productionList where byProductQuantity is null
        defaultProductionShouldNotBeFound("byProductQuantity.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductionsByRemarksIsEqualToSomething() throws Exception {
        // Initialize the database
        productionRepository.saveAndFlush(production);

        // Get all the productionList where remarks equals to DEFAULT_REMARKS
        defaultProductionShouldBeFound("remarks.equals=" + DEFAULT_REMARKS);

        // Get all the productionList where remarks equals to UPDATED_REMARKS
        defaultProductionShouldNotBeFound("remarks.equals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllProductionsByRemarksIsInShouldWork() throws Exception {
        // Initialize the database
        productionRepository.saveAndFlush(production);

        // Get all the productionList where remarks in DEFAULT_REMARKS or UPDATED_REMARKS
        defaultProductionShouldBeFound("remarks.in=" + DEFAULT_REMARKS + "," + UPDATED_REMARKS);

        // Get all the productionList where remarks equals to UPDATED_REMARKS
        defaultProductionShouldNotBeFound("remarks.in=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllProductionsByRemarksIsNullOrNotNull() throws Exception {
        // Initialize the database
        productionRepository.saveAndFlush(production);

        // Get all the productionList where remarks is not null
        defaultProductionShouldBeFound("remarks.specified=true");

        // Get all the productionList where remarks is null
        defaultProductionShouldNotBeFound("remarks.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductionsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productionRepository.saveAndFlush(production);

        // Get all the productionList where createdBy equals to DEFAULT_CREATED_BY
        defaultProductionShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the productionList where createdBy equals to UPDATED_CREATED_BY
        defaultProductionShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllProductionsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        productionRepository.saveAndFlush(production);

        // Get all the productionList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultProductionShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the productionList where createdBy equals to UPDATED_CREATED_BY
        defaultProductionShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllProductionsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productionRepository.saveAndFlush(production);

        // Get all the productionList where createdBy is not null
        defaultProductionShouldBeFound("createdBy.specified=true");

        // Get all the productionList where createdBy is null
        defaultProductionShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductionsByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        productionRepository.saveAndFlush(production);

        // Get all the productionList where createdOn equals to DEFAULT_CREATED_ON
        defaultProductionShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the productionList where createdOn equals to UPDATED_CREATED_ON
        defaultProductionShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllProductionsByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        productionRepository.saveAndFlush(production);

        // Get all the productionList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultProductionShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the productionList where createdOn equals to UPDATED_CREATED_ON
        defaultProductionShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllProductionsByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        productionRepository.saveAndFlush(production);

        // Get all the productionList where createdOn is not null
        defaultProductionShouldBeFound("createdOn.specified=true");

        // Get all the productionList where createdOn is null
        defaultProductionShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductionsByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productionRepository.saveAndFlush(production);

        // Get all the productionList where updatedBy equals to DEFAULT_UPDATED_BY
        defaultProductionShouldBeFound("updatedBy.equals=" + DEFAULT_UPDATED_BY);

        // Get all the productionList where updatedBy equals to UPDATED_UPDATED_BY
        defaultProductionShouldNotBeFound("updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllProductionsByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        productionRepository.saveAndFlush(production);

        // Get all the productionList where updatedBy in DEFAULT_UPDATED_BY or UPDATED_UPDATED_BY
        defaultProductionShouldBeFound("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY);

        // Get all the productionList where updatedBy equals to UPDATED_UPDATED_BY
        defaultProductionShouldNotBeFound("updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllProductionsByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productionRepository.saveAndFlush(production);

        // Get all the productionList where updatedBy is not null
        defaultProductionShouldBeFound("updatedBy.specified=true");

        // Get all the productionList where updatedBy is null
        defaultProductionShouldNotBeFound("updatedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductionsByUpdatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        productionRepository.saveAndFlush(production);

        // Get all the productionList where updatedOn equals to DEFAULT_UPDATED_ON
        defaultProductionShouldBeFound("updatedOn.equals=" + DEFAULT_UPDATED_ON);

        // Get all the productionList where updatedOn equals to UPDATED_UPDATED_ON
        defaultProductionShouldNotBeFound("updatedOn.equals=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllProductionsByUpdatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        productionRepository.saveAndFlush(production);

        // Get all the productionList where updatedOn in DEFAULT_UPDATED_ON or UPDATED_UPDATED_ON
        defaultProductionShouldBeFound("updatedOn.in=" + DEFAULT_UPDATED_ON + "," + UPDATED_UPDATED_ON);

        // Get all the productionList where updatedOn equals to UPDATED_UPDATED_ON
        defaultProductionShouldNotBeFound("updatedOn.in=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    public void getAllProductionsByUpdatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        productionRepository.saveAndFlush(production);

        // Get all the productionList where updatedOn is not null
        defaultProductionShouldBeFound("updatedOn.specified=true");

        // Get all the productionList where updatedOn is null
        defaultProductionShouldNotBeFound("updatedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductionsByWastageIsEqualToSomething() throws Exception {
        // Initialize the database
        productionRepository.saveAndFlush(production);

        // Get all the productionList where wastage equals to DEFAULT_WASTAGE
        defaultProductionShouldBeFound("wastage.equals=" + DEFAULT_WASTAGE);

        // Get all the productionList where wastage equals to UPDATED_WASTAGE
        defaultProductionShouldNotBeFound("wastage.equals=" + UPDATED_WASTAGE);
    }

    @Test
    @Transactional
    public void getAllProductionsByWastageIsInShouldWork() throws Exception {
        // Initialize the database
        productionRepository.saveAndFlush(production);

        // Get all the productionList where wastage in DEFAULT_WASTAGE or UPDATED_WASTAGE
        defaultProductionShouldBeFound("wastage.in=" + DEFAULT_WASTAGE + "," + UPDATED_WASTAGE);

        // Get all the productionList where wastage equals to UPDATED_WASTAGE
        defaultProductionShouldNotBeFound("wastage.in=" + UPDATED_WASTAGE);
    }

    @Test
    @Transactional
    public void getAllProductionsByWastageIsNullOrNotNull() throws Exception {
        // Initialize the database
        productionRepository.saveAndFlush(production);

        // Get all the productionList where wastage is not null
        defaultProductionShouldBeFound("wastage.specified=true");

        // Get all the productionList where wastage is null
        defaultProductionShouldNotBeFound("wastage.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductionsByProductCategoriesIsEqualToSomething() throws Exception {
        // Initialize the database
        ProductCategory productCategories = ProductCategoryResourceIntTest.createEntity(em);
        em.persist(productCategories);
        em.flush();
        production.setProductCategories(productCategories);
        productionRepository.saveAndFlush(production);
        Long productCategoriesId = productCategories.getId();

        // Get all the productionList where productCategories equals to productCategoriesId
        defaultProductionShouldBeFound("productCategoriesId.equals=" + productCategoriesId);

        // Get all the productionList where productCategories equals to productCategoriesId + 1
        defaultProductionShouldNotBeFound("productCategoriesId.equals=" + (productCategoriesId + 1));
    }


    @Test
    @Transactional
    public void getAllProductionsByProductsIsEqualToSomething() throws Exception {
        // Initialize the database
        Product products = ProductResourceIntTest.createEntity(em);
        em.persist(products);
        em.flush();
        production.setProducts(products);
        productionRepository.saveAndFlush(production);
        Long productsId = products.getId();

        // Get all the productionList where products equals to productsId
        defaultProductionShouldBeFound("productsId.equals=" + productsId);

        // Get all the productionList where products equals to productsId + 1
        defaultProductionShouldNotBeFound("productsId.equals=" + (productsId + 1));
    }


    @Test
    @Transactional
    public void getAllProductionsByRequisitionsIsEqualToSomething() throws Exception {
        // Initialize the database
        Requisition requisitions = RequisitionResourceIntTest.createEntity(em);
        em.persist(requisitions);
        em.flush();
        production.setRequisitions(requisitions);
        productionRepository.saveAndFlush(production);
        Long requisitionsId = requisitions.getId();

        // Get all the productionList where requisitions equals to requisitionsId
        defaultProductionShouldBeFound("requisitionsId.equals=" + requisitionsId);

        // Get all the productionList where requisitions equals to requisitionsId + 1
        defaultProductionShouldNotBeFound("requisitionsId.equals=" + (requisitionsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultProductionShouldBeFound(String filter) throws Exception {
        restProductionMockMvc.perform(get("/api/productions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(production.getId().intValue())))
            .andExpect(jsonPath("$.[*].weightStep").value(hasItem(DEFAULT_WEIGHT_STEP.toString())))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].byProductDescription").value(hasItem(DEFAULT_BY_PRODUCT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].byProductQuantity").value(hasItem(DEFAULT_BY_PRODUCT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].wastage").value(hasItem(DEFAULT_WASTAGE.intValue())));

        // Check, that the count call also returns 1
        restProductionMockMvc.perform(get("/api/productions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultProductionShouldNotBeFound(String filter) throws Exception {
        restProductionMockMvc.perform(get("/api/productions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductionMockMvc.perform(get("/api/productions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProduction() throws Exception {
        // Get the production
        restProductionMockMvc.perform(get("/api/productions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProduction() throws Exception {
        // Initialize the database
        productionRepository.saveAndFlush(production);

        int databaseSizeBeforeUpdate = productionRepository.findAll().size();

        // Update the production
        Production updatedProduction = productionRepository.findById(production.getId()).get();
        // Disconnect from session so that the updates on updatedProduction are not directly saved in db
        em.detach(updatedProduction);
        updatedProduction
            .weightStep(UPDATED_WEIGHT_STEP)
            .unit(UPDATED_UNIT)
            .quantity(UPDATED_QUANTITY)
            .byProductDescription(UPDATED_BY_PRODUCT_DESCRIPTION)
            .byProductQuantity(UPDATED_BY_PRODUCT_QUANTITY)
            .remarks(UPDATED_REMARKS)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .wastage(UPDATED_WASTAGE);
        ProductionDTO productionDTO = productionMapper.toDto(updatedProduction);

        restProductionMockMvc.perform(put("/api/productions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productionDTO)))
            .andExpect(status().isOk());

        // Validate the Production in the database
        List<Production> productionList = productionRepository.findAll();
        assertThat(productionList).hasSize(databaseSizeBeforeUpdate);
        Production testProduction = productionList.get(productionList.size() - 1);
        assertThat(testProduction.getWeightStep()).isEqualTo(UPDATED_WEIGHT_STEP);
        assertThat(testProduction.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testProduction.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testProduction.getByProductDescription()).isEqualTo(UPDATED_BY_PRODUCT_DESCRIPTION);
        assertThat(testProduction.getByProductQuantity()).isEqualTo(UPDATED_BY_PRODUCT_QUANTITY);
        assertThat(testProduction.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testProduction.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProduction.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testProduction.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testProduction.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testProduction.getWastage()).isEqualTo(UPDATED_WASTAGE);

        // Validate the Production in Elasticsearch
        verify(mockProductionSearchRepository, times(1)).save(testProduction);
    }

    @Test
    @Transactional
    public void updateNonExistingProduction() throws Exception {
        int databaseSizeBeforeUpdate = productionRepository.findAll().size();

        // Create the Production
        ProductionDTO productionDTO = productionMapper.toDto(production);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductionMockMvc.perform(put("/api/productions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Production in the database
        List<Production> productionList = productionRepository.findAll();
        assertThat(productionList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Production in Elasticsearch
        verify(mockProductionSearchRepository, times(0)).save(production);
    }

    @Test
    @Transactional
    public void deleteProduction() throws Exception {
        // Initialize the database
        productionRepository.saveAndFlush(production);

        int databaseSizeBeforeDelete = productionRepository.findAll().size();

        // Delete the production
        restProductionMockMvc.perform(delete("/api/productions/{id}", production.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Production> productionList = productionRepository.findAll();
        assertThat(productionList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Production in Elasticsearch
        verify(mockProductionSearchRepository, times(1)).deleteById(production.getId());
    }

    @Test
    @Transactional
    public void searchProduction() throws Exception {
        // Initialize the database
        productionRepository.saveAndFlush(production);
        when(mockProductionSearchRepository.search(queryStringQuery("id:" + production.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(production), PageRequest.of(0, 1), 1));
        // Search the production
        restProductionMockMvc.perform(get("/api/_search/productions?query=id:" + production.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(production.getId().intValue())))
            .andExpect(jsonPath("$.[*].weightStep").value(hasItem(DEFAULT_WEIGHT_STEP.toString())))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].byProductDescription").value(hasItem(DEFAULT_BY_PRODUCT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].byProductQuantity").value(hasItem(DEFAULT_BY_PRODUCT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].wastage").value(hasItem(DEFAULT_WASTAGE.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Production.class);
        Production production1 = new Production();
        production1.setId(1L);
        Production production2 = new Production();
        production2.setId(production1.getId());
        assertThat(production1).isEqualTo(production2);
        production2.setId(2L);
        assertThat(production1).isNotEqualTo(production2);
        production1.setId(null);
        assertThat(production1).isNotEqualTo(production2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductionDTO.class);
        ProductionDTO productionDTO1 = new ProductionDTO();
        productionDTO1.setId(1L);
        ProductionDTO productionDTO2 = new ProductionDTO();
        assertThat(productionDTO1).isNotEqualTo(productionDTO2);
        productionDTO2.setId(productionDTO1.getId());
        assertThat(productionDTO1).isEqualTo(productionDTO2);
        productionDTO2.setId(2L);
        assertThat(productionDTO1).isNotEqualTo(productionDTO2);
        productionDTO1.setId(null);
        assertThat(productionDTO1).isNotEqualTo(productionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(productionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(productionMapper.fromId(null)).isNull();
    }
}
