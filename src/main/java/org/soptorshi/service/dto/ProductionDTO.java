package org.soptorshi.service.dto;

import org.soptorshi.domain.enumeration.ProductionWeightStep;
import org.soptorshi.domain.enumeration.UnitOfMeasurements;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the Production entity.
 */
public class ProductionDTO implements Serializable {

    private Long id;

    @NotNull
    private ProductionWeightStep weightStep;

    @NotNull
    private UnitOfMeasurements unit;

    @NotNull
    private BigDecimal quantity;

    private String byProductDescription;

    private BigDecimal byProductQuantity;

    private String remarks;

    private String createdBy;

    private Instant createdOn;

    private String updatedBy;

    private Instant updatedOn;

    private BigDecimal wastage;


    private Long productCategoriesId;

    private String productCategoriesName;

    private Long productsId;

    private String productsName;

    private Long requisitionsId;

    private String requisitionsRequisitionNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductionWeightStep getWeightStep() {
        return weightStep;
    }

    public void setWeightStep(ProductionWeightStep weightStep) {
        this.weightStep = weightStep;
    }

    public UnitOfMeasurements getUnit() {
        return unit;
    }

    public void setUnit(UnitOfMeasurements unit) {
        this.unit = unit;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getByProductDescription() {
        return byProductDescription;
    }

    public void setByProductDescription(String byProductDescription) {
        this.byProductDescription = byProductDescription;
    }

    public BigDecimal getByProductQuantity() {
        return byProductQuantity;
    }

    public void setByProductQuantity(BigDecimal byProductQuantity) {
        this.byProductQuantity = byProductQuantity;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public BigDecimal getWastage() {
        return wastage;
    }

    public void setWastage(BigDecimal wastage) {
        this.wastage = wastage;
    }

    public Long getProductCategoriesId() {
        return productCategoriesId;
    }

    public void setProductCategoriesId(Long productCategoryId) {
        this.productCategoriesId = productCategoryId;
    }

    public String getProductCategoriesName() {
        return productCategoriesName;
    }

    public void setProductCategoriesName(String productCategoryName) {
        this.productCategoriesName = productCategoryName;
    }

    public Long getProductsId() {
        return productsId;
    }

    public void setProductsId(Long productId) {
        this.productsId = productId;
    }

    public String getProductsName() {
        return productsName;
    }

    public void setProductsName(String productName) {
        this.productsName = productName;
    }

    public Long getRequisitionsId() {
        return requisitionsId;
    }

    public void setRequisitionsId(Long requisitionId) {
        this.requisitionsId = requisitionId;
    }

    public String getRequisitionsRequisitionNo() {
        return requisitionsRequisitionNo;
    }

    public void setRequisitionsRequisitionNo(String requisitionRequisitionNo) {
        this.requisitionsRequisitionNo = requisitionRequisitionNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductionDTO productionDTO = (ProductionDTO) o;
        if (productionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductionDTO{" +
            "id=" + getId() +
            ", weightStep='" + getWeightStep() + "'" +
            ", unit='" + getUnit() + "'" +
            ", quantity=" + getQuantity() +
            ", byProductDescription='" + getByProductDescription() + "'" +
            ", byProductQuantity=" + getByProductQuantity() +
            ", remarks='" + getRemarks() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", wastage=" + getWastage() +
            ", productCategories=" + getProductCategoriesId() +
            ", productCategories='" + getProductCategoriesName() + "'" +
            ", products=" + getProductsId() +
            ", products='" + getProductsName() + "'" +
            ", requisitions=" + getRequisitionsId() +
            ", requisitions='" + getRequisitionsRequisitionNo() + "'" +
            "}";
    }
}
