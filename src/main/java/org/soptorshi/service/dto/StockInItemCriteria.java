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
 * Criteria class for the StockInItem entity. This class is used in StockInItemResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /stock-in-items?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StockInItemCriteria implements Serializable {
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

    private DoubleFilter quantity;

    private ItemUnitFilter unit;

    private DoubleFilter price;

    private ContainerCategoryFilter containerCategory;

    private StringFilter containerTrackingId;

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

    private LongFilter stockInProcessesId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public DoubleFilter getQuantity() {
        return quantity;
    }

    public void setQuantity(DoubleFilter quantity) {
        this.quantity = quantity;
    }

    public ItemUnitFilter getUnit() {
        return unit;
    }

    public void setUnit(ItemUnitFilter unit) {
        this.unit = unit;
    }

    public DoubleFilter getPrice() {
        return price;
    }

    public void setPrice(DoubleFilter price) {
        this.price = price;
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

    public LongFilter getStockInProcessesId() {
        return stockInProcessesId;
    }

    public void setStockInProcessesId(LongFilter stockInProcessesId) {
        this.stockInProcessesId = stockInProcessesId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final StockInItemCriteria that = (StockInItemCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(unit, that.unit) &&
            Objects.equals(price, that.price) &&
            Objects.equals(containerCategory, that.containerCategory) &&
            Objects.equals(containerTrackingId, that.containerTrackingId) &&
            Objects.equals(expiryDate, that.expiryDate) &&
            Objects.equals(stockInBy, that.stockInBy) &&
            Objects.equals(stockInDate, that.stockInDate) &&
            Objects.equals(purchaseOrderId, that.purchaseOrderId) &&
            Objects.equals(remarks, that.remarks) &&
            Objects.equals(itemCategoriesId, that.itemCategoriesId) &&
            Objects.equals(itemSubCategoriesId, that.itemSubCategoriesId) &&
            Objects.equals(inventoryLocationsId, that.inventoryLocationsId) &&
            Objects.equals(inventorySubLocationsId, that.inventorySubLocationsId) &&
            Objects.equals(manufacturersId, that.manufacturersId) &&
            Objects.equals(stockInProcessesId, that.stockInProcessesId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        quantity,
        unit,
        price,
        containerCategory,
        containerTrackingId,
        expiryDate,
        stockInBy,
        stockInDate,
        purchaseOrderId,
        remarks,
        itemCategoriesId,
        itemSubCategoriesId,
        inventoryLocationsId,
        inventorySubLocationsId,
        manufacturersId,
        stockInProcessesId
        );
    }

    @Override
    public String toString() {
        return "StockInItemCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (quantity != null ? "quantity=" + quantity + ", " : "") +
                (unit != null ? "unit=" + unit + ", " : "") +
                (price != null ? "price=" + price + ", " : "") +
                (containerCategory != null ? "containerCategory=" + containerCategory + ", " : "") +
                (containerTrackingId != null ? "containerTrackingId=" + containerTrackingId + ", " : "") +
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
                (stockInProcessesId != null ? "stockInProcessesId=" + stockInProcessesId + ", " : "") +
            "}";
    }

}