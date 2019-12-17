package org.soptorshi.service.dto;

import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.ItemUnit;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the StockStatus entity. This class is used in StockStatusResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /stock-statuses?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StockStatusCriteria implements Serializable {
    /**
     * Class for filtering ItemUnit
     */
    public static class ItemUnitFilter extends Filter<ItemUnit> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter containerTrackingId;

    private DoubleFilter totalQuantity;

    private ItemUnitFilter unit;

    private DoubleFilter availableQuantity;

    private DoubleFilter totalPrice;

    private DoubleFilter availablePrice;

    private StringFilter stockInBy;

    private InstantFilter stockInDate;

    private LongFilter stockInItemsId;

    private LongFilter productCategoriesId;

    private LongFilter productsId;

    private LongFilter inventoryLocationsId;

    private LongFilter inventorySubLocationsId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getContainerTrackingId() {
        return containerTrackingId;
    }

    public void setContainerTrackingId(StringFilter containerTrackingId) {
        this.containerTrackingId = containerTrackingId;
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

    public DoubleFilter getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(DoubleFilter availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public DoubleFilter getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(DoubleFilter totalPrice) {
        this.totalPrice = totalPrice;
    }

    public DoubleFilter getAvailablePrice() {
        return availablePrice;
    }

    public void setAvailablePrice(DoubleFilter availablePrice) {
        this.availablePrice = availablePrice;
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

    public LongFilter getStockInItemsId() {
        return stockInItemsId;
    }

    public void setStockInItemsId(LongFilter stockInItemsId) {
        this.stockInItemsId = stockInItemsId;
    }

    public LongFilter getProductCategoriesId() {
        return productCategoriesId;
    }

    public void setProductCategoriesId(LongFilter productCategoriesId) {
        this.productCategoriesId = productCategoriesId;
    }

    public LongFilter getProductsId() {
        return productsId;
    }

    public void setProductsId(LongFilter productsId) {
        this.productsId = productsId;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final StockStatusCriteria that = (StockStatusCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(containerTrackingId, that.containerTrackingId) &&
            Objects.equals(totalQuantity, that.totalQuantity) &&
            Objects.equals(unit, that.unit) &&
            Objects.equals(availableQuantity, that.availableQuantity) &&
            Objects.equals(totalPrice, that.totalPrice) &&
            Objects.equals(availablePrice, that.availablePrice) &&
            Objects.equals(stockInBy, that.stockInBy) &&
            Objects.equals(stockInDate, that.stockInDate) &&
            Objects.equals(stockInItemsId, that.stockInItemsId) &&
            Objects.equals(productCategoriesId, that.productCategoriesId) &&
            Objects.equals(productsId, that.productsId) &&
            Objects.equals(inventoryLocationsId, that.inventoryLocationsId) &&
            Objects.equals(inventorySubLocationsId, that.inventorySubLocationsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        containerTrackingId,
        totalQuantity,
        unit,
        availableQuantity,
        totalPrice,
        availablePrice,
        stockInBy,
        stockInDate,
        stockInItemsId,
        productCategoriesId,
        productsId,
        inventoryLocationsId,
        inventorySubLocationsId
        );
    }

    @Override
    public String toString() {
        return "StockStatusCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (containerTrackingId != null ? "containerTrackingId=" + containerTrackingId + ", " : "") +
                (totalQuantity != null ? "totalQuantity=" + totalQuantity + ", " : "") +
                (unit != null ? "unit=" + unit + ", " : "") +
                (availableQuantity != null ? "availableQuantity=" + availableQuantity + ", " : "") +
                (totalPrice != null ? "totalPrice=" + totalPrice + ", " : "") +
                (availablePrice != null ? "availablePrice=" + availablePrice + ", " : "") +
                (stockInBy != null ? "stockInBy=" + stockInBy + ", " : "") +
                (stockInDate != null ? "stockInDate=" + stockInDate + ", " : "") +
                (stockInItemsId != null ? "stockInItemsId=" + stockInItemsId + ", " : "") +
                (productCategoriesId != null ? "productCategoriesId=" + productCategoriesId + ", " : "") +
                (productsId != null ? "productsId=" + productsId + ", " : "") +
                (inventoryLocationsId != null ? "inventoryLocationsId=" + inventoryLocationsId + ", " : "") +
                (inventorySubLocationsId != null ? "inventorySubLocationsId=" + inventorySubLocationsId + ", " : "") +
            "}";
    }

}
