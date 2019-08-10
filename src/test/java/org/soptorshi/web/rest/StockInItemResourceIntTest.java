package org.soptorshi.web.rest;

import org.soptorshi.SoptorshiApp;

import org.soptorshi.domain.StockInItem;
import org.soptorshi.domain.ItemCategory;
import org.soptorshi.domain.ItemSubCategory;
import org.soptorshi.domain.InventoryLocation;
import org.soptorshi.domain.InventorySubLocation;
import org.soptorshi.domain.Manufacturer;
import org.soptorshi.domain.StockInProcess;
import org.soptorshi.repository.StockInItemRepository;
import org.soptorshi.repository.search.StockInItemSearchRepository;
import org.soptorshi.service.StockInItemService;
import org.soptorshi.service.dto.StockInItemDTO;
import org.soptorshi.service.mapper.StockInItemMapper;
import org.soptorshi.web.rest.errors.ExceptionTranslator;
import org.soptorshi.service.dto.StockInItemCriteria;
import org.soptorshi.service.StockInItemQueryService;

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
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;


import static org.soptorshi.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.soptorshi.domain.enumeration.ItemUnit;
import org.soptorshi.domain.enumeration.ContainerCategory;
/**
 * Test class for the StockInItemResource REST controller.
 *
 * @see StockInItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoptorshiApp.class)
public class StockInItemResourceIntTest {

    private static final Double DEFAULT_QUANTITY = 1D;
    private static final Double UPDATED_QUANTITY = 2D;

    private static final ItemUnit DEFAULT_UNIT = ItemUnit.KG;
    private static final ItemUnit UPDATED_UNIT = ItemUnit.PCS;

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final ContainerCategory DEFAULT_CONTAINER_CATEGORY = ContainerCategory.BOTTLE;
    private static final ContainerCategory UPDATED_CONTAINER_CATEGORY = ContainerCategory.DRUM;

    private static final String DEFAULT_CONTAINER_TRACKING_ID = "AAAAAAAAAA";
    private static final String UPDATED_CONTAINER_TRACKING_ID = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_EXPIRY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EXPIRY_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_STOCK_IN_BY = "AAAAAAAAAA";
    private static final String UPDATED_STOCK_IN_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_STOCK_IN_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_STOCK_IN_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_PURCHASE_ORDER_ID = "AAAAAAAAAA";
    private static final String UPDATED_PURCHASE_ORDER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    @Autowired
    private StockInItemRepository stockInItemRepository;

    @Autowired
    private StockInItemMapper stockInItemMapper;

    @Autowired
    private StockInItemService stockInItemService;

    /**
     * This repository is mocked in the org.soptorshi.repository.search test package.
     *
     * @see org.soptorshi.repository.search.StockInItemSearchRepositoryMockConfiguration
     */
    @Autowired
    private StockInItemSearchRepository mockStockInItemSearchRepository;

    @Autowired
    private StockInItemQueryService stockInItemQueryService;

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

    private MockMvc restStockInItemMockMvc;

    private StockInItem stockInItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StockInItemResource stockInItemResource = new StockInItemResource(stockInItemService, stockInItemQueryService);
        this.restStockInItemMockMvc = MockMvcBuilders.standaloneSetup(stockInItemResource)
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
    public static StockInItem createEntity(EntityManager em) {
        StockInItem stockInItem = new StockInItem()
            .quantity(DEFAULT_QUANTITY)
            .unit(DEFAULT_UNIT)
            .price(DEFAULT_PRICE)
            .containerCategory(DEFAULT_CONTAINER_CATEGORY)
            .containerTrackingId(DEFAULT_CONTAINER_TRACKING_ID)
            .expiryDate(DEFAULT_EXPIRY_DATE)
            .stockInBy(DEFAULT_STOCK_IN_BY)
            .stockInDate(DEFAULT_STOCK_IN_DATE)
            .purchaseOrderId(DEFAULT_PURCHASE_ORDER_ID)
            .remarks(DEFAULT_REMARKS);
        return stockInItem;
    }

    @Before
    public void initTest() {
        stockInItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createStockInItem() throws Exception {
        int databaseSizeBeforeCreate = stockInItemRepository.findAll().size();

        // Create the StockInItem
        StockInItemDTO stockInItemDTO = stockInItemMapper.toDto(stockInItem);
        restStockInItemMockMvc.perform(post("/api/stock-in-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockInItemDTO)))
            .andExpect(status().isCreated());

        // Validate the StockInItem in the database
        List<StockInItem> stockInItemList = stockInItemRepository.findAll();
        assertThat(stockInItemList).hasSize(databaseSizeBeforeCreate + 1);
        StockInItem testStockInItem = stockInItemList.get(stockInItemList.size() - 1);
        assertThat(testStockInItem.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testStockInItem.getUnit()).isEqualTo(DEFAULT_UNIT);
        assertThat(testStockInItem.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testStockInItem.getContainerCategory()).isEqualTo(DEFAULT_CONTAINER_CATEGORY);
        assertThat(testStockInItem.getContainerTrackingId()).isEqualTo(DEFAULT_CONTAINER_TRACKING_ID);
        assertThat(testStockInItem.getExpiryDate()).isEqualTo(DEFAULT_EXPIRY_DATE);
        assertThat(testStockInItem.getStockInBy()).isEqualTo(DEFAULT_STOCK_IN_BY);
        assertThat(testStockInItem.getStockInDate()).isEqualTo(DEFAULT_STOCK_IN_DATE);
        assertThat(testStockInItem.getPurchaseOrderId()).isEqualTo(DEFAULT_PURCHASE_ORDER_ID);
        assertThat(testStockInItem.getRemarks()).isEqualTo(DEFAULT_REMARKS);

        // Validate the StockInItem in Elasticsearch
        verify(mockStockInItemSearchRepository, times(1)).save(testStockInItem);
    }

    @Test
    @Transactional
    public void createStockInItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stockInItemRepository.findAll().size();

        // Create the StockInItem with an existing ID
        stockInItem.setId(1L);
        StockInItemDTO stockInItemDTO = stockInItemMapper.toDto(stockInItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStockInItemMockMvc.perform(post("/api/stock-in-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockInItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StockInItem in the database
        List<StockInItem> stockInItemList = stockInItemRepository.findAll();
        assertThat(stockInItemList).hasSize(databaseSizeBeforeCreate);

        // Validate the StockInItem in Elasticsearch
        verify(mockStockInItemSearchRepository, times(0)).save(stockInItem);
    }

    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockInItemRepository.findAll().size();
        // set the field null
        stockInItem.setQuantity(null);

        // Create the StockInItem, which fails.
        StockInItemDTO stockInItemDTO = stockInItemMapper.toDto(stockInItem);

        restStockInItemMockMvc.perform(post("/api/stock-in-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockInItemDTO)))
            .andExpect(status().isBadRequest());

        List<StockInItem> stockInItemList = stockInItemRepository.findAll();
        assertThat(stockInItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnitIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockInItemRepository.findAll().size();
        // set the field null
        stockInItem.setUnit(null);

        // Create the StockInItem, which fails.
        StockInItemDTO stockInItemDTO = stockInItemMapper.toDto(stockInItem);

        restStockInItemMockMvc.perform(post("/api/stock-in-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockInItemDTO)))
            .andExpect(status().isBadRequest());

        List<StockInItem> stockInItemList = stockInItemRepository.findAll();
        assertThat(stockInItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockInItemRepository.findAll().size();
        // set the field null
        stockInItem.setPrice(null);

        // Create the StockInItem, which fails.
        StockInItemDTO stockInItemDTO = stockInItemMapper.toDto(stockInItem);

        restStockInItemMockMvc.perform(post("/api/stock-in-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockInItemDTO)))
            .andExpect(status().isBadRequest());

        List<StockInItem> stockInItemList = stockInItemRepository.findAll();
        assertThat(stockInItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContainerCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockInItemRepository.findAll().size();
        // set the field null
        stockInItem.setContainerCategory(null);

        // Create the StockInItem, which fails.
        StockInItemDTO stockInItemDTO = stockInItemMapper.toDto(stockInItem);

        restStockInItemMockMvc.perform(post("/api/stock-in-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockInItemDTO)))
            .andExpect(status().isBadRequest());

        List<StockInItem> stockInItemList = stockInItemRepository.findAll();
        assertThat(stockInItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContainerTrackingIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = stockInItemRepository.findAll().size();
        // set the field null
        stockInItem.setContainerTrackingId(null);

        // Create the StockInItem, which fails.
        StockInItemDTO stockInItemDTO = stockInItemMapper.toDto(stockInItem);

        restStockInItemMockMvc.perform(post("/api/stock-in-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockInItemDTO)))
            .andExpect(status().isBadRequest());

        List<StockInItem> stockInItemList = stockInItemRepository.findAll();
        assertThat(stockInItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStockInItems() throws Exception {
        // Initialize the database
        stockInItemRepository.saveAndFlush(stockInItem);

        // Get all the stockInItemList
        restStockInItemMockMvc.perform(get("/api/stock-in-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockInItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].containerCategory").value(hasItem(DEFAULT_CONTAINER_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].containerTrackingId").value(hasItem(DEFAULT_CONTAINER_TRACKING_ID.toString())))
            .andExpect(jsonPath("$.[*].expiryDate").value(hasItem(DEFAULT_EXPIRY_DATE.toString())))
            .andExpect(jsonPath("$.[*].stockInBy").value(hasItem(DEFAULT_STOCK_IN_BY.toString())))
            .andExpect(jsonPath("$.[*].stockInDate").value(hasItem(DEFAULT_STOCK_IN_DATE.toString())))
            .andExpect(jsonPath("$.[*].purchaseOrderId").value(hasItem(DEFAULT_PURCHASE_ORDER_ID.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())));
    }
    
    @Test
    @Transactional
    public void getStockInItem() throws Exception {
        // Initialize the database
        stockInItemRepository.saveAndFlush(stockInItem);

        // Get the stockInItem
        restStockInItemMockMvc.perform(get("/api/stock-in-items/{id}", stockInItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stockInItem.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.doubleValue()))
            .andExpect(jsonPath("$.unit").value(DEFAULT_UNIT.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.containerCategory").value(DEFAULT_CONTAINER_CATEGORY.toString()))
            .andExpect(jsonPath("$.containerTrackingId").value(DEFAULT_CONTAINER_TRACKING_ID.toString()))
            .andExpect(jsonPath("$.expiryDate").value(DEFAULT_EXPIRY_DATE.toString()))
            .andExpect(jsonPath("$.stockInBy").value(DEFAULT_STOCK_IN_BY.toString()))
            .andExpect(jsonPath("$.stockInDate").value(DEFAULT_STOCK_IN_DATE.toString()))
            .andExpect(jsonPath("$.purchaseOrderId").value(DEFAULT_PURCHASE_ORDER_ID.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()));
    }

    @Test
    @Transactional
    public void getAllStockInItemsByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        stockInItemRepository.saveAndFlush(stockInItem);

        // Get all the stockInItemList where quantity equals to DEFAULT_QUANTITY
        defaultStockInItemShouldBeFound("quantity.equals=" + DEFAULT_QUANTITY);

        // Get all the stockInItemList where quantity equals to UPDATED_QUANTITY
        defaultStockInItemShouldNotBeFound("quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllStockInItemsByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        stockInItemRepository.saveAndFlush(stockInItem);

        // Get all the stockInItemList where quantity in DEFAULT_QUANTITY or UPDATED_QUANTITY
        defaultStockInItemShouldBeFound("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY);

        // Get all the stockInItemList where quantity equals to UPDATED_QUANTITY
        defaultStockInItemShouldNotBeFound("quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllStockInItemsByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockInItemRepository.saveAndFlush(stockInItem);

        // Get all the stockInItemList where quantity is not null
        defaultStockInItemShouldBeFound("quantity.specified=true");

        // Get all the stockInItemList where quantity is null
        defaultStockInItemShouldNotBeFound("quantity.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockInItemsByUnitIsEqualToSomething() throws Exception {
        // Initialize the database
        stockInItemRepository.saveAndFlush(stockInItem);

        // Get all the stockInItemList where unit equals to DEFAULT_UNIT
        defaultStockInItemShouldBeFound("unit.equals=" + DEFAULT_UNIT);

        // Get all the stockInItemList where unit equals to UPDATED_UNIT
        defaultStockInItemShouldNotBeFound("unit.equals=" + UPDATED_UNIT);
    }

    @Test
    @Transactional
    public void getAllStockInItemsByUnitIsInShouldWork() throws Exception {
        // Initialize the database
        stockInItemRepository.saveAndFlush(stockInItem);

        // Get all the stockInItemList where unit in DEFAULT_UNIT or UPDATED_UNIT
        defaultStockInItemShouldBeFound("unit.in=" + DEFAULT_UNIT + "," + UPDATED_UNIT);

        // Get all the stockInItemList where unit equals to UPDATED_UNIT
        defaultStockInItemShouldNotBeFound("unit.in=" + UPDATED_UNIT);
    }

    @Test
    @Transactional
    public void getAllStockInItemsByUnitIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockInItemRepository.saveAndFlush(stockInItem);

        // Get all the stockInItemList where unit is not null
        defaultStockInItemShouldBeFound("unit.specified=true");

        // Get all the stockInItemList where unit is null
        defaultStockInItemShouldNotBeFound("unit.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockInItemsByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        stockInItemRepository.saveAndFlush(stockInItem);

        // Get all the stockInItemList where price equals to DEFAULT_PRICE
        defaultStockInItemShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the stockInItemList where price equals to UPDATED_PRICE
        defaultStockInItemShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllStockInItemsByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        stockInItemRepository.saveAndFlush(stockInItem);

        // Get all the stockInItemList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultStockInItemShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the stockInItemList where price equals to UPDATED_PRICE
        defaultStockInItemShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllStockInItemsByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockInItemRepository.saveAndFlush(stockInItem);

        // Get all the stockInItemList where price is not null
        defaultStockInItemShouldBeFound("price.specified=true");

        // Get all the stockInItemList where price is null
        defaultStockInItemShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockInItemsByContainerCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        stockInItemRepository.saveAndFlush(stockInItem);

        // Get all the stockInItemList where containerCategory equals to DEFAULT_CONTAINER_CATEGORY
        defaultStockInItemShouldBeFound("containerCategory.equals=" + DEFAULT_CONTAINER_CATEGORY);

        // Get all the stockInItemList where containerCategory equals to UPDATED_CONTAINER_CATEGORY
        defaultStockInItemShouldNotBeFound("containerCategory.equals=" + UPDATED_CONTAINER_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllStockInItemsByContainerCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        stockInItemRepository.saveAndFlush(stockInItem);

        // Get all the stockInItemList where containerCategory in DEFAULT_CONTAINER_CATEGORY or UPDATED_CONTAINER_CATEGORY
        defaultStockInItemShouldBeFound("containerCategory.in=" + DEFAULT_CONTAINER_CATEGORY + "," + UPDATED_CONTAINER_CATEGORY);

        // Get all the stockInItemList where containerCategory equals to UPDATED_CONTAINER_CATEGORY
        defaultStockInItemShouldNotBeFound("containerCategory.in=" + UPDATED_CONTAINER_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllStockInItemsByContainerCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockInItemRepository.saveAndFlush(stockInItem);

        // Get all the stockInItemList where containerCategory is not null
        defaultStockInItemShouldBeFound("containerCategory.specified=true");

        // Get all the stockInItemList where containerCategory is null
        defaultStockInItemShouldNotBeFound("containerCategory.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockInItemsByContainerTrackingIdIsEqualToSomething() throws Exception {
        // Initialize the database
        stockInItemRepository.saveAndFlush(stockInItem);

        // Get all the stockInItemList where containerTrackingId equals to DEFAULT_CONTAINER_TRACKING_ID
        defaultStockInItemShouldBeFound("containerTrackingId.equals=" + DEFAULT_CONTAINER_TRACKING_ID);

        // Get all the stockInItemList where containerTrackingId equals to UPDATED_CONTAINER_TRACKING_ID
        defaultStockInItemShouldNotBeFound("containerTrackingId.equals=" + UPDATED_CONTAINER_TRACKING_ID);
    }

    @Test
    @Transactional
    public void getAllStockInItemsByContainerTrackingIdIsInShouldWork() throws Exception {
        // Initialize the database
        stockInItemRepository.saveAndFlush(stockInItem);

        // Get all the stockInItemList where containerTrackingId in DEFAULT_CONTAINER_TRACKING_ID or UPDATED_CONTAINER_TRACKING_ID
        defaultStockInItemShouldBeFound("containerTrackingId.in=" + DEFAULT_CONTAINER_TRACKING_ID + "," + UPDATED_CONTAINER_TRACKING_ID);

        // Get all the stockInItemList where containerTrackingId equals to UPDATED_CONTAINER_TRACKING_ID
        defaultStockInItemShouldNotBeFound("containerTrackingId.in=" + UPDATED_CONTAINER_TRACKING_ID);
    }

    @Test
    @Transactional
    public void getAllStockInItemsByContainerTrackingIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockInItemRepository.saveAndFlush(stockInItem);

        // Get all the stockInItemList where containerTrackingId is not null
        defaultStockInItemShouldBeFound("containerTrackingId.specified=true");

        // Get all the stockInItemList where containerTrackingId is null
        defaultStockInItemShouldNotBeFound("containerTrackingId.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockInItemsByExpiryDateIsEqualToSomething() throws Exception {
        // Initialize the database
        stockInItemRepository.saveAndFlush(stockInItem);

        // Get all the stockInItemList where expiryDate equals to DEFAULT_EXPIRY_DATE
        defaultStockInItemShouldBeFound("expiryDate.equals=" + DEFAULT_EXPIRY_DATE);

        // Get all the stockInItemList where expiryDate equals to UPDATED_EXPIRY_DATE
        defaultStockInItemShouldNotBeFound("expiryDate.equals=" + UPDATED_EXPIRY_DATE);
    }

    @Test
    @Transactional
    public void getAllStockInItemsByExpiryDateIsInShouldWork() throws Exception {
        // Initialize the database
        stockInItemRepository.saveAndFlush(stockInItem);

        // Get all the stockInItemList where expiryDate in DEFAULT_EXPIRY_DATE or UPDATED_EXPIRY_DATE
        defaultStockInItemShouldBeFound("expiryDate.in=" + DEFAULT_EXPIRY_DATE + "," + UPDATED_EXPIRY_DATE);

        // Get all the stockInItemList where expiryDate equals to UPDATED_EXPIRY_DATE
        defaultStockInItemShouldNotBeFound("expiryDate.in=" + UPDATED_EXPIRY_DATE);
    }

    @Test
    @Transactional
    public void getAllStockInItemsByExpiryDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockInItemRepository.saveAndFlush(stockInItem);

        // Get all the stockInItemList where expiryDate is not null
        defaultStockInItemShouldBeFound("expiryDate.specified=true");

        // Get all the stockInItemList where expiryDate is null
        defaultStockInItemShouldNotBeFound("expiryDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockInItemsByExpiryDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stockInItemRepository.saveAndFlush(stockInItem);

        // Get all the stockInItemList where expiryDate greater than or equals to DEFAULT_EXPIRY_DATE
        defaultStockInItemShouldBeFound("expiryDate.greaterOrEqualThan=" + DEFAULT_EXPIRY_DATE);

        // Get all the stockInItemList where expiryDate greater than or equals to UPDATED_EXPIRY_DATE
        defaultStockInItemShouldNotBeFound("expiryDate.greaterOrEqualThan=" + UPDATED_EXPIRY_DATE);
    }

    @Test
    @Transactional
    public void getAllStockInItemsByExpiryDateIsLessThanSomething() throws Exception {
        // Initialize the database
        stockInItemRepository.saveAndFlush(stockInItem);

        // Get all the stockInItemList where expiryDate less than or equals to DEFAULT_EXPIRY_DATE
        defaultStockInItemShouldNotBeFound("expiryDate.lessThan=" + DEFAULT_EXPIRY_DATE);

        // Get all the stockInItemList where expiryDate less than or equals to UPDATED_EXPIRY_DATE
        defaultStockInItemShouldBeFound("expiryDate.lessThan=" + UPDATED_EXPIRY_DATE);
    }


    @Test
    @Transactional
    public void getAllStockInItemsByStockInByIsEqualToSomething() throws Exception {
        // Initialize the database
        stockInItemRepository.saveAndFlush(stockInItem);

        // Get all the stockInItemList where stockInBy equals to DEFAULT_STOCK_IN_BY
        defaultStockInItemShouldBeFound("stockInBy.equals=" + DEFAULT_STOCK_IN_BY);

        // Get all the stockInItemList where stockInBy equals to UPDATED_STOCK_IN_BY
        defaultStockInItemShouldNotBeFound("stockInBy.equals=" + UPDATED_STOCK_IN_BY);
    }

    @Test
    @Transactional
    public void getAllStockInItemsByStockInByIsInShouldWork() throws Exception {
        // Initialize the database
        stockInItemRepository.saveAndFlush(stockInItem);

        // Get all the stockInItemList where stockInBy in DEFAULT_STOCK_IN_BY or UPDATED_STOCK_IN_BY
        defaultStockInItemShouldBeFound("stockInBy.in=" + DEFAULT_STOCK_IN_BY + "," + UPDATED_STOCK_IN_BY);

        // Get all the stockInItemList where stockInBy equals to UPDATED_STOCK_IN_BY
        defaultStockInItemShouldNotBeFound("stockInBy.in=" + UPDATED_STOCK_IN_BY);
    }

    @Test
    @Transactional
    public void getAllStockInItemsByStockInByIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockInItemRepository.saveAndFlush(stockInItem);

        // Get all the stockInItemList where stockInBy is not null
        defaultStockInItemShouldBeFound("stockInBy.specified=true");

        // Get all the stockInItemList where stockInBy is null
        defaultStockInItemShouldNotBeFound("stockInBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockInItemsByStockInDateIsEqualToSomething() throws Exception {
        // Initialize the database
        stockInItemRepository.saveAndFlush(stockInItem);

        // Get all the stockInItemList where stockInDate equals to DEFAULT_STOCK_IN_DATE
        defaultStockInItemShouldBeFound("stockInDate.equals=" + DEFAULT_STOCK_IN_DATE);

        // Get all the stockInItemList where stockInDate equals to UPDATED_STOCK_IN_DATE
        defaultStockInItemShouldNotBeFound("stockInDate.equals=" + UPDATED_STOCK_IN_DATE);
    }

    @Test
    @Transactional
    public void getAllStockInItemsByStockInDateIsInShouldWork() throws Exception {
        // Initialize the database
        stockInItemRepository.saveAndFlush(stockInItem);

        // Get all the stockInItemList where stockInDate in DEFAULT_STOCK_IN_DATE or UPDATED_STOCK_IN_DATE
        defaultStockInItemShouldBeFound("stockInDate.in=" + DEFAULT_STOCK_IN_DATE + "," + UPDATED_STOCK_IN_DATE);

        // Get all the stockInItemList where stockInDate equals to UPDATED_STOCK_IN_DATE
        defaultStockInItemShouldNotBeFound("stockInDate.in=" + UPDATED_STOCK_IN_DATE);
    }

    @Test
    @Transactional
    public void getAllStockInItemsByStockInDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockInItemRepository.saveAndFlush(stockInItem);

        // Get all the stockInItemList where stockInDate is not null
        defaultStockInItemShouldBeFound("stockInDate.specified=true");

        // Get all the stockInItemList where stockInDate is null
        defaultStockInItemShouldNotBeFound("stockInDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockInItemsByPurchaseOrderIdIsEqualToSomething() throws Exception {
        // Initialize the database
        stockInItemRepository.saveAndFlush(stockInItem);

        // Get all the stockInItemList where purchaseOrderId equals to DEFAULT_PURCHASE_ORDER_ID
        defaultStockInItemShouldBeFound("purchaseOrderId.equals=" + DEFAULT_PURCHASE_ORDER_ID);

        // Get all the stockInItemList where purchaseOrderId equals to UPDATED_PURCHASE_ORDER_ID
        defaultStockInItemShouldNotBeFound("purchaseOrderId.equals=" + UPDATED_PURCHASE_ORDER_ID);
    }

    @Test
    @Transactional
    public void getAllStockInItemsByPurchaseOrderIdIsInShouldWork() throws Exception {
        // Initialize the database
        stockInItemRepository.saveAndFlush(stockInItem);

        // Get all the stockInItemList where purchaseOrderId in DEFAULT_PURCHASE_ORDER_ID or UPDATED_PURCHASE_ORDER_ID
        defaultStockInItemShouldBeFound("purchaseOrderId.in=" + DEFAULT_PURCHASE_ORDER_ID + "," + UPDATED_PURCHASE_ORDER_ID);

        // Get all the stockInItemList where purchaseOrderId equals to UPDATED_PURCHASE_ORDER_ID
        defaultStockInItemShouldNotBeFound("purchaseOrderId.in=" + UPDATED_PURCHASE_ORDER_ID);
    }

    @Test
    @Transactional
    public void getAllStockInItemsByPurchaseOrderIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockInItemRepository.saveAndFlush(stockInItem);

        // Get all the stockInItemList where purchaseOrderId is not null
        defaultStockInItemShouldBeFound("purchaseOrderId.specified=true");

        // Get all the stockInItemList where purchaseOrderId is null
        defaultStockInItemShouldNotBeFound("purchaseOrderId.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockInItemsByRemarksIsEqualToSomething() throws Exception {
        // Initialize the database
        stockInItemRepository.saveAndFlush(stockInItem);

        // Get all the stockInItemList where remarks equals to DEFAULT_REMARKS
        defaultStockInItemShouldBeFound("remarks.equals=" + DEFAULT_REMARKS);

        // Get all the stockInItemList where remarks equals to UPDATED_REMARKS
        defaultStockInItemShouldNotBeFound("remarks.equals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllStockInItemsByRemarksIsInShouldWork() throws Exception {
        // Initialize the database
        stockInItemRepository.saveAndFlush(stockInItem);

        // Get all the stockInItemList where remarks in DEFAULT_REMARKS or UPDATED_REMARKS
        defaultStockInItemShouldBeFound("remarks.in=" + DEFAULT_REMARKS + "," + UPDATED_REMARKS);

        // Get all the stockInItemList where remarks equals to UPDATED_REMARKS
        defaultStockInItemShouldNotBeFound("remarks.in=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllStockInItemsByRemarksIsNullOrNotNull() throws Exception {
        // Initialize the database
        stockInItemRepository.saveAndFlush(stockInItem);

        // Get all the stockInItemList where remarks is not null
        defaultStockInItemShouldBeFound("remarks.specified=true");

        // Get all the stockInItemList where remarks is null
        defaultStockInItemShouldNotBeFound("remarks.specified=false");
    }

    @Test
    @Transactional
    public void getAllStockInItemsByItemCategoriesIsEqualToSomething() throws Exception {
        // Initialize the database
        ItemCategory itemCategories = ItemCategoryResourceIntTest.createEntity(em);
        em.persist(itemCategories);
        em.flush();
        stockInItem.setItemCategories(itemCategories);
        stockInItemRepository.saveAndFlush(stockInItem);
        Long itemCategoriesId = itemCategories.getId();

        // Get all the stockInItemList where itemCategories equals to itemCategoriesId
        defaultStockInItemShouldBeFound("itemCategoriesId.equals=" + itemCategoriesId);

        // Get all the stockInItemList where itemCategories equals to itemCategoriesId + 1
        defaultStockInItemShouldNotBeFound("itemCategoriesId.equals=" + (itemCategoriesId + 1));
    }


    @Test
    @Transactional
    public void getAllStockInItemsByItemSubCategoriesIsEqualToSomething() throws Exception {
        // Initialize the database
        ItemSubCategory itemSubCategories = ItemSubCategoryResourceIntTest.createEntity(em);
        em.persist(itemSubCategories);
        em.flush();
        stockInItem.setItemSubCategories(itemSubCategories);
        stockInItemRepository.saveAndFlush(stockInItem);
        Long itemSubCategoriesId = itemSubCategories.getId();

        // Get all the stockInItemList where itemSubCategories equals to itemSubCategoriesId
        defaultStockInItemShouldBeFound("itemSubCategoriesId.equals=" + itemSubCategoriesId);

        // Get all the stockInItemList where itemSubCategories equals to itemSubCategoriesId + 1
        defaultStockInItemShouldNotBeFound("itemSubCategoriesId.equals=" + (itemSubCategoriesId + 1));
    }


    @Test
    @Transactional
    public void getAllStockInItemsByInventoryLocationsIsEqualToSomething() throws Exception {
        // Initialize the database
        InventoryLocation inventoryLocations = InventoryLocationResourceIntTest.createEntity(em);
        em.persist(inventoryLocations);
        em.flush();
        stockInItem.setInventoryLocations(inventoryLocations);
        stockInItemRepository.saveAndFlush(stockInItem);
        Long inventoryLocationsId = inventoryLocations.getId();

        // Get all the stockInItemList where inventoryLocations equals to inventoryLocationsId
        defaultStockInItemShouldBeFound("inventoryLocationsId.equals=" + inventoryLocationsId);

        // Get all the stockInItemList where inventoryLocations equals to inventoryLocationsId + 1
        defaultStockInItemShouldNotBeFound("inventoryLocationsId.equals=" + (inventoryLocationsId + 1));
    }


    @Test
    @Transactional
    public void getAllStockInItemsByInventorySubLocationsIsEqualToSomething() throws Exception {
        // Initialize the database
        InventorySubLocation inventorySubLocations = InventorySubLocationResourceIntTest.createEntity(em);
        em.persist(inventorySubLocations);
        em.flush();
        stockInItem.setInventorySubLocations(inventorySubLocations);
        stockInItemRepository.saveAndFlush(stockInItem);
        Long inventorySubLocationsId = inventorySubLocations.getId();

        // Get all the stockInItemList where inventorySubLocations equals to inventorySubLocationsId
        defaultStockInItemShouldBeFound("inventorySubLocationsId.equals=" + inventorySubLocationsId);

        // Get all the stockInItemList where inventorySubLocations equals to inventorySubLocationsId + 1
        defaultStockInItemShouldNotBeFound("inventorySubLocationsId.equals=" + (inventorySubLocationsId + 1));
    }


    @Test
    @Transactional
    public void getAllStockInItemsByManufacturersIsEqualToSomething() throws Exception {
        // Initialize the database
        Manufacturer manufacturers = ManufacturerResourceIntTest.createEntity(em);
        em.persist(manufacturers);
        em.flush();
        stockInItem.setManufacturers(manufacturers);
        stockInItemRepository.saveAndFlush(stockInItem);
        Long manufacturersId = manufacturers.getId();

        // Get all the stockInItemList where manufacturers equals to manufacturersId
        defaultStockInItemShouldBeFound("manufacturersId.equals=" + manufacturersId);

        // Get all the stockInItemList where manufacturers equals to manufacturersId + 1
        defaultStockInItemShouldNotBeFound("manufacturersId.equals=" + (manufacturersId + 1));
    }


    @Test
    @Transactional
    public void getAllStockInItemsByStockInProcessesIsEqualToSomething() throws Exception {
        // Initialize the database
        StockInProcess stockInProcesses = StockInProcessResourceIntTest.createEntity(em);
        em.persist(stockInProcesses);
        em.flush();
        stockInItem.setStockInProcesses(stockInProcesses);
        stockInItemRepository.saveAndFlush(stockInItem);
        Long stockInProcessesId = stockInProcesses.getId();

        // Get all the stockInItemList where stockInProcesses equals to stockInProcessesId
        defaultStockInItemShouldBeFound("stockInProcessesId.equals=" + stockInProcessesId);

        // Get all the stockInItemList where stockInProcesses equals to stockInProcessesId + 1
        defaultStockInItemShouldNotBeFound("stockInProcessesId.equals=" + (stockInProcessesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultStockInItemShouldBeFound(String filter) throws Exception {
        restStockInItemMockMvc.perform(get("/api/stock-in-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockInItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].containerCategory").value(hasItem(DEFAULT_CONTAINER_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].containerTrackingId").value(hasItem(DEFAULT_CONTAINER_TRACKING_ID)))
            .andExpect(jsonPath("$.[*].expiryDate").value(hasItem(DEFAULT_EXPIRY_DATE.toString())))
            .andExpect(jsonPath("$.[*].stockInBy").value(hasItem(DEFAULT_STOCK_IN_BY)))
            .andExpect(jsonPath("$.[*].stockInDate").value(hasItem(DEFAULT_STOCK_IN_DATE.toString())))
            .andExpect(jsonPath("$.[*].purchaseOrderId").value(hasItem(DEFAULT_PURCHASE_ORDER_ID)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)));

        // Check, that the count call also returns 1
        restStockInItemMockMvc.perform(get("/api/stock-in-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultStockInItemShouldNotBeFound(String filter) throws Exception {
        restStockInItemMockMvc.perform(get("/api/stock-in-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStockInItemMockMvc.perform(get("/api/stock-in-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingStockInItem() throws Exception {
        // Get the stockInItem
        restStockInItemMockMvc.perform(get("/api/stock-in-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStockInItem() throws Exception {
        // Initialize the database
        stockInItemRepository.saveAndFlush(stockInItem);

        int databaseSizeBeforeUpdate = stockInItemRepository.findAll().size();

        // Update the stockInItem
        StockInItem updatedStockInItem = stockInItemRepository.findById(stockInItem.getId()).get();
        // Disconnect from session so that the updates on updatedStockInItem are not directly saved in db
        em.detach(updatedStockInItem);
        updatedStockInItem
            .quantity(UPDATED_QUANTITY)
            .unit(UPDATED_UNIT)
            .price(UPDATED_PRICE)
            .containerCategory(UPDATED_CONTAINER_CATEGORY)
            .containerTrackingId(UPDATED_CONTAINER_TRACKING_ID)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .stockInBy(UPDATED_STOCK_IN_BY)
            .stockInDate(UPDATED_STOCK_IN_DATE)
            .purchaseOrderId(UPDATED_PURCHASE_ORDER_ID)
            .remarks(UPDATED_REMARKS);
        StockInItemDTO stockInItemDTO = stockInItemMapper.toDto(updatedStockInItem);

        restStockInItemMockMvc.perform(put("/api/stock-in-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockInItemDTO)))
            .andExpect(status().isOk());

        // Validate the StockInItem in the database
        List<StockInItem> stockInItemList = stockInItemRepository.findAll();
        assertThat(stockInItemList).hasSize(databaseSizeBeforeUpdate);
        StockInItem testStockInItem = stockInItemList.get(stockInItemList.size() - 1);
        assertThat(testStockInItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testStockInItem.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testStockInItem.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testStockInItem.getContainerCategory()).isEqualTo(UPDATED_CONTAINER_CATEGORY);
        assertThat(testStockInItem.getContainerTrackingId()).isEqualTo(UPDATED_CONTAINER_TRACKING_ID);
        assertThat(testStockInItem.getExpiryDate()).isEqualTo(UPDATED_EXPIRY_DATE);
        assertThat(testStockInItem.getStockInBy()).isEqualTo(UPDATED_STOCK_IN_BY);
        assertThat(testStockInItem.getStockInDate()).isEqualTo(UPDATED_STOCK_IN_DATE);
        assertThat(testStockInItem.getPurchaseOrderId()).isEqualTo(UPDATED_PURCHASE_ORDER_ID);
        assertThat(testStockInItem.getRemarks()).isEqualTo(UPDATED_REMARKS);

        // Validate the StockInItem in Elasticsearch
        verify(mockStockInItemSearchRepository, times(1)).save(testStockInItem);
    }

    @Test
    @Transactional
    public void updateNonExistingStockInItem() throws Exception {
        int databaseSizeBeforeUpdate = stockInItemRepository.findAll().size();

        // Create the StockInItem
        StockInItemDTO stockInItemDTO = stockInItemMapper.toDto(stockInItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStockInItemMockMvc.perform(put("/api/stock-in-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockInItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StockInItem in the database
        List<StockInItem> stockInItemList = stockInItemRepository.findAll();
        assertThat(stockInItemList).hasSize(databaseSizeBeforeUpdate);

        // Validate the StockInItem in Elasticsearch
        verify(mockStockInItemSearchRepository, times(0)).save(stockInItem);
    }

    @Test
    @Transactional
    public void deleteStockInItem() throws Exception {
        // Initialize the database
        stockInItemRepository.saveAndFlush(stockInItem);

        int databaseSizeBeforeDelete = stockInItemRepository.findAll().size();

        // Delete the stockInItem
        restStockInItemMockMvc.perform(delete("/api/stock-in-items/{id}", stockInItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StockInItem> stockInItemList = stockInItemRepository.findAll();
        assertThat(stockInItemList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the StockInItem in Elasticsearch
        verify(mockStockInItemSearchRepository, times(1)).deleteById(stockInItem.getId());
    }

    @Test
    @Transactional
    public void searchStockInItem() throws Exception {
        // Initialize the database
        stockInItemRepository.saveAndFlush(stockInItem);
        when(mockStockInItemSearchRepository.search(queryStringQuery("id:" + stockInItem.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(stockInItem), PageRequest.of(0, 1), 1));
        // Search the stockInItem
        restStockInItemMockMvc.perform(get("/api/_search/stock-in-items?query=id:" + stockInItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockInItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].containerCategory").value(hasItem(DEFAULT_CONTAINER_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].containerTrackingId").value(hasItem(DEFAULT_CONTAINER_TRACKING_ID)))
            .andExpect(jsonPath("$.[*].expiryDate").value(hasItem(DEFAULT_EXPIRY_DATE.toString())))
            .andExpect(jsonPath("$.[*].stockInBy").value(hasItem(DEFAULT_STOCK_IN_BY)))
            .andExpect(jsonPath("$.[*].stockInDate").value(hasItem(DEFAULT_STOCK_IN_DATE.toString())))
            .andExpect(jsonPath("$.[*].purchaseOrderId").value(hasItem(DEFAULT_PURCHASE_ORDER_ID)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockInItem.class);
        StockInItem stockInItem1 = new StockInItem();
        stockInItem1.setId(1L);
        StockInItem stockInItem2 = new StockInItem();
        stockInItem2.setId(stockInItem1.getId());
        assertThat(stockInItem1).isEqualTo(stockInItem2);
        stockInItem2.setId(2L);
        assertThat(stockInItem1).isNotEqualTo(stockInItem2);
        stockInItem1.setId(null);
        assertThat(stockInItem1).isNotEqualTo(stockInItem2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockInItemDTO.class);
        StockInItemDTO stockInItemDTO1 = new StockInItemDTO();
        stockInItemDTO1.setId(1L);
        StockInItemDTO stockInItemDTO2 = new StockInItemDTO();
        assertThat(stockInItemDTO1).isNotEqualTo(stockInItemDTO2);
        stockInItemDTO2.setId(stockInItemDTO1.getId());
        assertThat(stockInItemDTO1).isEqualTo(stockInItemDTO2);
        stockInItemDTO2.setId(2L);
        assertThat(stockInItemDTO1).isNotEqualTo(stockInItemDTO2);
        stockInItemDTO1.setId(null);
        assertThat(stockInItemDTO1).isNotEqualTo(stockInItemDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(stockInItemMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(stockInItemMapper.fromId(null)).isNull();
    }
}