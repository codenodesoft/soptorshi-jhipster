package org.soptorshi.service.impl;

import org.soptorshi.repository.extended.StockStatusRepositoryExtended;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.domain.StockInProcess;
import org.soptorshi.repository.StockInProcessRepository;
import org.soptorshi.repository.search.StockInProcessSearchRepository;
import org.soptorshi.service.dto.StockInItemDTO;
import org.soptorshi.service.dto.StockInProcessDTO;
import org.soptorshi.service.dto.StockStatusDTO;
import org.soptorshi.service.mapper.StockInProcessMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.soptorshi.service.mapper.StockStatusMapper;
import org.soptorshi.web.rest.errors.InternalServerErrorException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Arrays;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing StockInProcess.
 */
@Service
@Transactional
public class StockInProcessServiceImpl {

    private final Logger log = LoggerFactory.getLogger(StockInProcessServiceImpl.class);

    private final StockInProcessRepository stockInProcessRepository;

    private final StockInProcessMapper stockInProcessMapper;

    private final StockInProcessSearchRepository stockInProcessSearchRepository;

    private final StockInItemServiceImpl stockInItemServiceImpl;

    private final StockStatusRepositoryExtended stockStatusRepositoryExtended;

    private final StockStatusMapper stockStatusMapper;

    public StockInProcessServiceImpl(StockInProcessRepository stockInProcessRepository, StockInProcessMapper stockInProcessMapper, StockInProcessSearchRepository stockInProcessSearchRepository,
                                     StockInItemServiceImpl stockInItemServiceImpl, StockStatusRepositoryExtended  stockStatusRepositoryExtended, StockStatusMapper stockStatusMapper) {
        this.stockInProcessRepository = stockInProcessRepository;
        this.stockInProcessMapper = stockInProcessMapper;
        this.stockInProcessSearchRepository = stockInProcessSearchRepository;
        this.stockInItemServiceImpl = stockInItemServiceImpl;
        this.stockStatusRepositoryExtended = stockStatusRepositoryExtended;
        this.stockStatusMapper = stockStatusMapper;
    }

    /**
     * Save a stockInProcess.
     *
     * @param stockInProcessDTO the entity to save
     * @return the persisted entity
     */

    public StockInProcessDTO save(StockInProcessDTO stockInProcessDTO) {
        log.debug("Request to save StockInProcess : {}", stockInProcessDTO);
        if(stockInProcessDTO.getId() == null) {
            String currentUser = SecurityUtils.getCurrentUserLogin().isPresent() ? SecurityUtils.getCurrentUserLogin().toString() : "";
            Instant currentDateTime = Instant.now();
            stockInProcessDTO.setStockInBy(currentUser);
            stockInProcessDTO.setStockInDate(currentDateTime);
            StockInProcess stockInProcess = stockInProcessMapper.toEntity(stockInProcessDTO);
            stockInProcess = stockInProcessRepository.save(stockInProcess);
            StockInProcessDTO result = stockInProcessMapper.toDto(stockInProcess);
            /*stockInProcessSearchRepository.save(stockInProcess);*/
            int response = insertInStock(result, currentUser, currentDateTime);
            if (response == 0)
                throw new InternalServerErrorException("Mismatch in item container or item per container.");
            return result;
        }
        throw new InternalServerErrorException("Only insert operation is available.");
    }

    /**
     * Get all the stockInProcesses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */

    @Transactional(readOnly = true)
    public Page<StockInProcessDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StockInProcesses");
        return stockInProcessRepository.findAll(pageable)
            .map(stockInProcessMapper::toDto);
    }


    /**
     * Get one stockInProcess by id.
     *
     * @param id the id of the entity
     * @return the entity
     */

    @Transactional(readOnly = true)
    public Optional<StockInProcessDTO> findOne(Long id) {
        log.debug("Request to get StockInProcess : {}", id);
        return stockInProcessRepository.findById(id)
            .map(stockInProcessMapper::toDto);
    }

    /**
     * Delete the stockInProcess by id.
     *
     * @param id the id of the entity
     */

    public void delete(Long id) {
        log.debug("Request to delete StockInProcess : {}", id);
        stockInProcessRepository.deleteById(id);
        stockInProcessSearchRepository.deleteById(id);
    }

    /**
     * Search for the stockInProcess corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */

    @Transactional(readOnly = true)
    public Page<StockInProcessDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of StockInProcesses for query {}", query);
        return stockInProcessSearchRepository.search(queryStringQuery(query), pageable)
            .map(stockInProcessMapper::toDto);
    }

    private int insertInStock(StockInProcessDTO stockInProcessDTO, String currentUser, Instant currentDateTime) {
        String[] containerIds = getContainerIds(stockInProcessDTO);
        String[] itemsPerContainer = stockInProcessDTO.getQuantityPerContainer().split(",");
        if (validateRecord(stockInProcessDTO, containerIds, itemsPerContainer)) return 0;

        for (int i = 0; i < stockInProcessDTO.getTotalContainer(); i++) {
            StockInItemDTO stockInItemDTO = getStockInItemDTO(stockInProcessDTO, Double.parseDouble(itemsPerContainer[i].trim()), containerIds[i].trim(), currentUser, currentDateTime);
            StockInItemDTO result = stockInItemServiceImpl.save(stockInItemDTO);

            StockStatusDTO stockStatusDTO = getStockStatus(stockInProcessDTO, Double.parseDouble(itemsPerContainer[i].trim()), containerIds[i].trim(), currentUser, currentDateTime, result.getId());
            stockStatusRepositoryExtended.save(stockStatusMapper.toEntity(stockStatusDTO));
        }
        return 1;
    }

    private boolean validateRecord(StockInProcessDTO stockInProcessDTO, String[] containerIds, String[] itemsPerContainer) {
        if (!isValidTotalContainer(stockInProcessDTO)) return true;
        if (!isNumberOfContainerAndNumberOfContainerTrackingIdEqual(stockInProcessDTO, containerIds))
            return true;
        if (!isNumberOfContainerAndNumberOfItemsEqual(stockInProcessDTO, itemsPerContainer))
            return true;
        if (isAnyContainerTrackingIdEmpty(containerIds)) return true;
        if (isAnyItemPerContainerEmpty(itemsPerContainer)) return true;
        return isTotalQuantityAndSumOfItemsPerContainerEqual(stockInProcessDTO, itemsPerContainer);
    }

    private boolean isTotalQuantityAndSumOfItemsPerContainerEqual(StockInProcessDTO stockInProcessDTO, String[] itemsPerContainer) {
        Double totalQuantity = 0.0;
        for (String itemPerContainer : itemsPerContainer) {
            totalQuantity += Double.parseDouble(itemPerContainer);
        }
        return !totalQuantity.equals(stockInProcessDTO.getTotalQuantity());
    }

    private boolean isAnyItemPerContainerEmpty(String[] itemsPerContainer) {
        for (String itemPerContainer : itemsPerContainer) {
            if (itemPerContainer.trim().isEmpty()) return true;
        }
        return false;
    }

    private boolean isAnyContainerTrackingIdEmpty(String[] containerIds) {
        for (String containerId : containerIds) {
            if (containerId.trim().isEmpty()) return true;
        }
        return false;
    }

    private boolean isNumberOfContainerAndNumberOfContainerTrackingIdEqual(StockInProcessDTO stockInProcessDTO, String[] containerIds) {
        return containerIds.length == stockInProcessDTO.getTotalContainer();
    }

    private boolean isNumberOfContainerAndNumberOfItemsEqual(StockInProcessDTO stockInProcessDTO, String[] itemsPerContainer) {
        return itemsPerContainer.length == stockInProcessDTO.getTotalContainer();
    }

    private String[] getContainerIds(StockInProcessDTO stockInProcessDTO) {
        return Arrays.stream(stockInProcessDTO.getContainerTrackingId().split(",")
        ).distinct().toArray(String[]::new);
    }

    private boolean isValidTotalContainer(StockInProcessDTO stockInProcessDTO) {
        return stockInProcessDTO.getTotalContainer() != null && stockInProcessDTO.getTotalContainer() != 0;
    }


    private StockInItemDTO getStockInItemDTO(StockInProcessDTO stockInProcessDTO, double quantity, String containerId, String currentUserId, Instant currentDateTime) {
        StockInItemDTO stockInItemDTO = new StockInItemDTO();
        stockInItemDTO.setItemCategoriesId(stockInProcessDTO.getItemCategoriesId());
        stockInItemDTO.setItemSubCategoriesId(stockInProcessDTO.getItemSubCategoriesId());
        stockInItemDTO.setInventoryLocationsId(stockInProcessDTO.getInventoryLocationsId());
        stockInItemDTO.setInventorySubLocationsId(stockInProcessDTO.getInventorySubLocationsId());
        stockInItemDTO.setQuantity(quantity);
        stockInItemDTO.setUnit(stockInProcessDTO.getUnit());
        stockInItemDTO.setPrice(stockInProcessDTO.getUnitPrice() * quantity);
        stockInItemDTO.setContainerCategory(stockInProcessDTO.getContainerCategory());
        stockInItemDTO.setContainerTrackingId(containerId);
        stockInItemDTO.setExpiryDate(stockInProcessDTO.getExpiryDate());
        stockInItemDTO.setStockInBy(currentUserId);
        stockInItemDTO.setStockInDate(currentDateTime);
        stockInItemDTO.setPurchaseOrderId(stockInProcessDTO.getPurchaseOrderId());
        stockInItemDTO.setRemarks(stockInProcessDTO.getRemarks());
        stockInItemDTO.setStockInProcessesId(stockInProcessDTO.getId());
        return stockInItemDTO;
    }

    private StockStatusDTO getStockStatus(StockInProcessDTO stockInProcessDTO, double quantity, String containerId, String currentUserId, Instant currentDateTime, Long stockInItemId) {
        StockStatusDTO stockStatusDTO = new StockStatusDTO();
        stockStatusDTO.setItemCategoriesId(stockInProcessDTO.getItemCategoriesId());
        stockStatusDTO.setItemSubCategoriesId(stockInProcessDTO.getItemSubCategoriesId());
        stockStatusDTO.setInventoryLocationsId(stockInProcessDTO.getInventoryLocationsId());
        stockStatusDTO.setInventorySubLocationsId(stockInProcessDTO.getInventorySubLocationsId());
        stockStatusDTO.setContainerTrackingId(containerId);
        stockStatusDTO.setTotalQuantity(quantity);
        stockStatusDTO.setAvailableQuantity(quantity);
        stockStatusDTO.setUnit(stockInProcessDTO.getUnit());
        stockStatusDTO.setTotalPrice(stockInProcessDTO.getUnitPrice() * quantity);
        stockStatusDTO.setAvailablePrice(stockInProcessDTO.getUnitPrice() * quantity);
        stockStatusDTO.setStockInBy(currentUserId);
        stockStatusDTO.setStockInDate(currentDateTime);
        stockStatusDTO.setStockInItemsId(stockInItemId);
        return stockStatusDTO;
    }
}
