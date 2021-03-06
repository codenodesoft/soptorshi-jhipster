package org.soptorshi.service.dto;

import io.github.jhipster.service.filter.*;
import org.soptorshi.domain.enumeration.ContainerCategory;
import org.soptorshi.domain.enumeration.ProductType;
import org.soptorshi.domain.enumeration.UnitOfMeasurements;

import java.io.Serializable;
import java.util.Objects;

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
     * Class for filtering UnitOfMeasurements
     */
    public static class UnitOfMeasurementsFilter extends Filter<UnitOfMeasurements> {
    }
    /**
     * Class for filtering ContainerCategory
     */
    public static class ContainerCategoryFilter extends Filter<ContainerCategory> {
    }
    /**
     * Class for filtering ProductType
     */
    public static class ProductTypeFilter extends Filter<ProductType> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter quantity;

    private UnitOfMeasurementsFilter unit;

    private BigDecimalFilter price;

    private ContainerCategoryFilter containerCategory;

    private StringFilter containerTrackingId;

    private LocalDateFilter mfgDate;

    private LocalDateFilter expiryDate;

    private ProductTypeFilter typeOfProduct;

    private StringFilter stockInBy;

    private InstantFilter stockInDate;

    private StringFilter remarks;

    private LongFilter productCategoriesId;

    private LongFilter productsId;

    private LongFilter inventoryLocationsId;

    private LongFilter inventorySubLocationsId;

    private LongFilter vendorId;

    private LongFilter stockInProcessesId;

    private LongFilter requisitionsId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BigDecimalFilter getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimalFilter quantity) {
        this.quantity = quantity;
    }

    public UnitOfMeasurementsFilter getUnit() {
        return unit;
    }

    public void setUnit(UnitOfMeasurementsFilter unit) {
        this.unit = unit;
    }

    public BigDecimalFilter getPrice() {
        return price;
    }

    public void setPrice(BigDecimalFilter price) {
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

    public LocalDateFilter getMfgDate() {
        return mfgDate;
    }

    public void setMfgDate(LocalDateFilter mfgDate) {
        this.mfgDate = mfgDate;
    }

    public LocalDateFilter getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateFilter expiryDate) {
        this.expiryDate = expiryDate;
    }

    public ProductTypeFilter getTypeOfProduct() {
        return typeOfProduct;
    }

    public void setTypeOfProduct(ProductTypeFilter typeOfProduct) {
        this.typeOfProduct = typeOfProduct;
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

    public StringFilter getRemarks() {
        return remarks;
    }

    public void setRemarks(StringFilter remarks) {
        this.remarks = remarks;
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

    public LongFilter getVendorId() {
        return vendorId;
    }

    public void setVendorId(LongFilter vendorId) {
        this.vendorId = vendorId;
    }

    public LongFilter getStockInProcessesId() {
        return stockInProcessesId;
    }

    public void setStockInProcessesId(LongFilter stockInProcessesId) {
        this.stockInProcessesId = stockInProcessesId;
    }

    public LongFilter getRequisitionsId() {
        return requisitionsId;
    }

    public void setRequisitionsId(LongFilter requisitionsId) {
        this.requisitionsId = requisitionsId;
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
            Objects.equals(mfgDate, that.mfgDate) &&
            Objects.equals(expiryDate, that.expiryDate) &&
            Objects.equals(typeOfProduct, that.typeOfProduct) &&
            Objects.equals(stockInBy, that.stockInBy) &&
            Objects.equals(stockInDate, that.stockInDate) &&
            Objects.equals(remarks, that.remarks) &&
            Objects.equals(productCategoriesId, that.productCategoriesId) &&
            Objects.equals(productsId, that.productsId) &&
            Objects.equals(inventoryLocationsId, that.inventoryLocationsId) &&
            Objects.equals(inventorySubLocationsId, that.inventorySubLocationsId) &&
            Objects.equals(vendorId, that.vendorId) &&
            Objects.equals(stockInProcessesId, that.stockInProcessesId) &&
            Objects.equals(requisitionsId, that.requisitionsId);
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
        mfgDate,
        expiryDate,
        typeOfProduct,
        stockInBy,
        stockInDate,
        remarks,
        productCategoriesId,
        productsId,
        inventoryLocationsId,
        inventorySubLocationsId,
        vendorId,
        stockInProcessesId,
        requisitionsId
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
                (mfgDate != null ? "mfgDate=" + mfgDate + ", " : "") +
                (expiryDate != null ? "expiryDate=" + expiryDate + ", " : "") +
                (typeOfProduct != null ? "typeOfProduct=" + typeOfProduct + ", " : "") +
                (stockInBy != null ? "stockInBy=" + stockInBy + ", " : "") +
                (stockInDate != null ? "stockInDate=" + stockInDate + ", " : "") +
                (remarks != null ? "remarks=" + remarks + ", " : "") +
                (productCategoriesId != null ? "productCategoriesId=" + productCategoriesId + ", " : "") +
                (productsId != null ? "productsId=" + productsId + ", " : "") +
                (inventoryLocationsId != null ? "inventoryLocationsId=" + inventoryLocationsId + ", " : "") +
                (inventorySubLocationsId != null ? "inventorySubLocationsId=" + inventorySubLocationsId + ", " : "") +
                (vendorId != null ? "vendorId=" + vendorId + ", " : "") +
                (stockInProcessesId != null ? "stockInProcessesId=" + stockInProcessesId + ", " : "") +
                (requisitionsId != null ? "requisitionsId=" + requisitionsId + ", " : "") +
            "}";
    }

}
