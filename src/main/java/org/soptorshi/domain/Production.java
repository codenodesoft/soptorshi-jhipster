package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.soptorshi.domain.enumeration.ProductionWeightStep;
import org.soptorshi.domain.enumeration.UnitOfMeasurements;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * A Production.
 */
@Entity
@Table(name = "production")
@Document(indexName = "production")
public class Production implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "weight_step", nullable = false)
    private ProductionWeightStep weightStep;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "unit", nullable = false)
    private UnitOfMeasurements unit;

    @NotNull
    @Column(name = "quantity", precision = 10, scale = 2, nullable = false)
    private BigDecimal quantity;

    @Column(name = "by_product_description")
    private String byProductDescription;

    @Column(name = "by_product_quantity", precision = 10, scale = 2)
    private BigDecimal byProductQuantity;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_on")
    private Instant updatedOn;

    @Column(name = "wastage", precision = 10, scale = 2)
    private BigDecimal wastage;

    @ManyToOne
    @JsonIgnoreProperties("productions")
    private ProductCategory productCategories;

    @ManyToOne
    @JsonIgnoreProperties("productions")
    private Product products;

    @ManyToOne
    @JsonIgnoreProperties("productions")
    private Requisition requisitions;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductionWeightStep getWeightStep() {
        return weightStep;
    }

    public Production weightStep(ProductionWeightStep weightStep) {
        this.weightStep = weightStep;
        return this;
    }

    public void setWeightStep(ProductionWeightStep weightStep) {
        this.weightStep = weightStep;
    }

    public UnitOfMeasurements getUnit() {
        return unit;
    }

    public Production unit(UnitOfMeasurements unit) {
        this.unit = unit;
        return this;
    }

    public void setUnit(UnitOfMeasurements unit) {
        this.unit = unit;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public Production quantity(BigDecimal quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getByProductDescription() {
        return byProductDescription;
    }

    public Production byProductDescription(String byProductDescription) {
        this.byProductDescription = byProductDescription;
        return this;
    }

    public void setByProductDescription(String byProductDescription) {
        this.byProductDescription = byProductDescription;
    }

    public BigDecimal getByProductQuantity() {
        return byProductQuantity;
    }

    public Production byProductQuantity(BigDecimal byProductQuantity) {
        this.byProductQuantity = byProductQuantity;
        return this;
    }

    public void setByProductQuantity(BigDecimal byProductQuantity) {
        this.byProductQuantity = byProductQuantity;
    }

    public String getRemarks() {
        return remarks;
    }

    public Production remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Production createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public Production createdOn(Instant createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public Production updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public Production updatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public BigDecimal getWastage() {
        return wastage;
    }

    public Production wastage(BigDecimal wastage) {
        this.wastage = wastage;
        return this;
    }

    public void setWastage(BigDecimal wastage) {
        this.wastage = wastage;
    }

    public ProductCategory getProductCategories() {
        return productCategories;
    }

    public Production productCategories(ProductCategory productCategory) {
        this.productCategories = productCategory;
        return this;
    }

    public void setProductCategories(ProductCategory productCategory) {
        this.productCategories = productCategory;
    }

    public Product getProducts() {
        return products;
    }

    public Production products(Product product) {
        this.products = product;
        return this;
    }

    public void setProducts(Product product) {
        this.products = product;
    }

    public Requisition getRequisitions() {
        return requisitions;
    }

    public Production requisitions(Requisition requisition) {
        this.requisitions = requisition;
        return this;
    }

    public void setRequisitions(Requisition requisition) {
        this.requisitions = requisition;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Production production = (Production) o;
        if (production.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), production.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Production{" +
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
            "}";
    }
}
