package org.soptorshi.service.dto;

import io.github.jhipster.service.filter.*;
import org.soptorshi.domain.enumeration.ProductionWeightStep;
import org.soptorshi.domain.enumeration.UnitOfMeasurements;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the Production entity. This class is used in ProductionResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /productions?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProductionCriteria implements Serializable {
    /**
     * Class for filtering ProductionWeightStep
     */
    public static class ProductionWeightStepFilter extends Filter<ProductionWeightStep> {
    }
    /**
     * Class for filtering UnitOfMeasurements
     */
    public static class UnitOfMeasurementsFilter extends Filter<UnitOfMeasurements> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ProductionWeightStepFilter weightStep;

    private UnitOfMeasurementsFilter unit;

    private BigDecimalFilter quantity;

    private StringFilter byProductDescription;

    private BigDecimalFilter byProductQuantity;

    private StringFilter remarks;

    private StringFilter createdBy;

    private InstantFilter createdOn;

    private StringFilter updatedBy;

    private InstantFilter updatedOn;

    private BigDecimalFilter wastage;

    private LongFilter productCategoriesId;

    private LongFilter productsId;

    private LongFilter requisitionsId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public ProductionWeightStepFilter getWeightStep() {
        return weightStep;
    }

    public void setWeightStep(ProductionWeightStepFilter weightStep) {
        this.weightStep = weightStep;
    }

    public UnitOfMeasurementsFilter getUnit() {
        return unit;
    }

    public void setUnit(UnitOfMeasurementsFilter unit) {
        this.unit = unit;
    }

    public BigDecimalFilter getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimalFilter quantity) {
        this.quantity = quantity;
    }

    public StringFilter getByProductDescription() {
        return byProductDescription;
    }

    public void setByProductDescription(StringFilter byProductDescription) {
        this.byProductDescription = byProductDescription;
    }

    public BigDecimalFilter getByProductQuantity() {
        return byProductQuantity;
    }

    public void setByProductQuantity(BigDecimalFilter byProductQuantity) {
        this.byProductQuantity = byProductQuantity;
    }

    public StringFilter getRemarks() {
        return remarks;
    }

    public void setRemarks(StringFilter remarks) {
        this.remarks = remarks;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public InstantFilter getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(InstantFilter createdOn) {
        this.createdOn = createdOn;
    }

    public StringFilter getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(StringFilter updatedBy) {
        this.updatedBy = updatedBy;
    }

    public InstantFilter getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(InstantFilter updatedOn) {
        this.updatedOn = updatedOn;
    }

    public BigDecimalFilter getWastage() {
        return wastage;
    }

    public void setWastage(BigDecimalFilter wastage) {
        this.wastage = wastage;
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
        final ProductionCriteria that = (ProductionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(weightStep, that.weightStep) &&
            Objects.equals(unit, that.unit) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(byProductDescription, that.byProductDescription) &&
            Objects.equals(byProductQuantity, that.byProductQuantity) &&
            Objects.equals(remarks, that.remarks) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedOn, that.updatedOn) &&
            Objects.equals(wastage, that.wastage) &&
            Objects.equals(productCategoriesId, that.productCategoriesId) &&
            Objects.equals(productsId, that.productsId) &&
            Objects.equals(requisitionsId, that.requisitionsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        weightStep,
        unit,
        quantity,
        byProductDescription,
        byProductQuantity,
        remarks,
        createdBy,
        createdOn,
        updatedBy,
        updatedOn,
        wastage,
        productCategoriesId,
        productsId,
        requisitionsId
        );
    }

    @Override
    public String toString() {
        return "ProductionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (weightStep != null ? "weightStep=" + weightStep + ", " : "") +
                (unit != null ? "unit=" + unit + ", " : "") +
                (quantity != null ? "quantity=" + quantity + ", " : "") +
                (byProductDescription != null ? "byProductDescription=" + byProductDescription + ", " : "") +
                (byProductQuantity != null ? "byProductQuantity=" + byProductQuantity + ", " : "") +
                (remarks != null ? "remarks=" + remarks + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
                (updatedBy != null ? "updatedBy=" + updatedBy + ", " : "") +
                (updatedOn != null ? "updatedOn=" + updatedOn + ", " : "") +
                (wastage != null ? "wastage=" + wastage + ", " : "") +
                (productCategoriesId != null ? "productCategoriesId=" + productCategoriesId + ", " : "") +
                (productsId != null ? "productsId=" + productsId + ", " : "") +
                (requisitionsId != null ? "requisitionsId=" + requisitionsId + ", " : "") +
            "}";
    }

}
