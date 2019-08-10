package org.soptorshi.service.dto;

import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.ItemUnit;
import org.soptorshi.domain.enumeration.ContainerCategory;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the StockInProcess entity. This class is used in StockInProcessResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /stock-in-processes?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StockInProcessCriteria implements Serializable {
    /**
     * Class for filtering ItemUnit
     */
    public static class ItemUnitFilter extends Filter<ItemUnit> {
    }
    /**
     * Class for filtering ContainerCategory
     */
    public static class ContainerCategoryFilter extends Filter<ContainerCategory> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter totalQuantity;

    private ItemUnitFilter unit;

    private DoubleFilter unitPrice;

    private IntegerFilter totalContainer;

    private ContainerCategoryFilter containerCategory;

    private StringFilter containerTrackingId;

    private StringFilter quantityPerContainer;

    private LocalDateFilter expiryDate;

    private StringFilter stockInBy;

    private InstantFilter stockInDate;

    private StringFilter purchaseOrderId;

    private StringFilter remarks;

    private LongFilter itemCategoriesId;

    private LongFilter itemSubCategoriesId;

    private LongFilter inventoryLocationsId;

    private LongFilter inventorySubLocationsId;

    private LongFilter manufacturersId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public DoubleFilter getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(DoubleFilter totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public ItemUnitFilter getUnit() {
        return unit;
    }

    public void setUnit(ItemUnitFilter unit) {
        this.unit = unit;
    }

    public DoubleFilter getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(DoubleFilter unitPrice) {
        this.unitPrice = unitPrice;
    }

    public IntegerFilter getTotalContainer() {
        return totalContainer;
    }

    public void setTotalContainer(IntegerFilter totalContainer) {
        this.totalContainer = totalContainer;
    }

    public ContainerCategoryFilter getContainerCategory() {
        return containerCategory;
    }

    public void setContainerCategory(ContainerCategoryFilter containerCategory) {
        this.containerCategory = containerCategory;
    }

    public StringFilter getContainerTrackingId() {
        return containerTrackingId;
    }

    public void setContainerTrackingId(StringFilter containerTrackingId) {
        this.containerTrackingId = containerTrackingId;
    }

    public StringFilter getQuantityPerContainer() {
        return quantityPerContainer;
    }

    public void setQuantityPerContainer(StringFilter quantityPerContainer) {
        this.quantityPerContainer = quantityPerContainer;
    }

    public LocalDateFilter getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateFilter expiryDate) {
        this.expiryDate = expiryDate;
    }

    public StringFilter getStockInBy() {
        return stockInBy;
    }

    public void setStockInBy(StringFilter stockInBy) {
        this.stockInBy = stockInBy;
    }

    public InstantFilter getStockInDate() {
        return stockInDate;
    }

    public void setStockInDate(InstantFilter stockInDate) {
        this.stockInDate = stockInDate;
    }

    public StringFilter getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(StringFilter purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public StringFilter getRemarks() {
        return remarks;
    }

    public void setRemarks(StringFilter remarks) {
        this.remarks = remarks;
    }

    public LongFilter getItemCategoriesId() {
        return itemCategoriesId;
    }

    public void setItemCategoriesId(LongFilter itemCategoriesId) {
        this.itemCategoriesId = itemCategoriesId;
    }

    public LongFilter getItemSubCategoriesId() {
        return itemSubCategoriesId;
    }

    public void setItemSubCategoriesId(LongFilter itemSubCategoriesId) {
        this.itemSubCategoriesId = itemSubCategoriesId;
    }

    public LongFilter getInventoryLocationsId() {
        return inventoryLocationsId;
    }

    public void setInventoryLocationsId(LongFilter inventoryLocationsId) {
        this.inventoryLocationsId = inventoryLocationsId;
    }

    public LongFilter getInventorySubLocationsId() {
        return inventorySubLocationsId;
    }

    public void setInventorySubLocationsId(LongFilter inventorySubLocationsId) {
        this.inventorySubLocationsId = inventorySubLocationsId;
    }

    public LongFilter getManufacturersId() {
        return manufacturersId;
    }

    public void setManufacturersId(LongFilter manufacturersId) {
        this.manufacturersId = manufacturersId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final StockInProcessCriteria that = (StockInProcessCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(totalQuantity, that.totalQuantity) &&
            Objects.equals(unit, that.unit) &&
            Objects.equals(unitPrice, that.unitPrice) &&
            Objects.equals(totalContainer, that.totalContainer) &&
            Objects.equals(containerCategory, that.containerCategory) &&
            Objects.equals(containerTrackingId, that.containerTrackingId) &&
            Objects.equals(quantityPerContainer, that.quantityPerContainer) &&
            Objects.equals(expiryDate, that.expiryDate) &&
            Objects.equals(stockInBy, that.stockInBy) &&
            Objects.equals(stockInDate, that.stockInDate) &&
            Objects.equals(purchaseOrderId, that.purchaseOrderId) &&
            Objects.equals(remarks, that.remarks) &&
            Objects.equals(itemCategoriesId, that.itemCategoriesId) &&
            Objects.equals(itemSubCategoriesId, that.itemSubCategoriesId) &&
            Objects.equals(inventoryLocationsId, that.inventoryLocationsId) &&
            Objects.equals(inventorySubLocationsId, that.inventorySubLocationsId) &&
            Objects.equals(manufacturersId, that.manufacturersId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        totalQuantity,
        unit,
        unitPrice,
        totalContainer,
        containerCategory,
        containerTrackingId,
        quantityPerContainer,
        expiryDate,
        stockInBy,
        stockInDate,
        purchaseOrderId,
        remarks,
        itemCategoriesId,
        itemSubCategoriesId,
        inventoryLocationsId,
        inventorySubLocationsId,
        manufacturersId
        );
    }

    @Override
    public String toString() {
        return "StockInProcessCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (totalQuantity != null ? "totalQuantity=" + totalQuantity + ", " : "") +
                (unit != null ? "unit=" + unit + ", " : "") +
                (unitPrice != null ? "unitPrice=" + unitPrice + ", " : "") +
                (totalContainer != null ? "totalContainer=" + totalContainer + ", " : "") +
                (containerCategory != null ? "containerCategory=" + containerCategory + ", " : "") +
                (containerTrackingId != null ? "containerTrackingId=" + containerTrackingId + ", " : "") +
                (quantityPerContainer != null ? "quantityPerContainer=" + quantityPerContainer + ", " : "") +
                (expiryDate != null ? "expiryDate=" + expiryDate + ", " : "") +
                (stockInBy != null ? "stockInBy=" + stockInBy + ", " : "") +
                (stockInDate != null ? "stockInDate=" + stockInDate + ", " : "") +
                (purchaseOrderId != null ? "purchaseOrderId=" + purchaseOrderId + ", " : "") +
                (remarks != null ? "remarks=" + remarks + ", " : "") +
                (itemCategoriesId != null ? "itemCategoriesId=" + itemCategoriesId + ", " : "") +
                (itemSubCategoriesId != null ? "itemSubCategoriesId=" + itemSubCategoriesId + ", " : "") +
                (inventoryLocationsId != null ? "inventoryLocationsId=" + inventoryLocationsId + ", " : "") +
                (inventorySubLocationsId != null ? "inventorySubLocationsId=" + inventorySubLocationsId + ", " : "") +
                (manufacturersId != null ? "manufacturersId=" + manufacturersId + ", " : "") +
            "}";
    }

}