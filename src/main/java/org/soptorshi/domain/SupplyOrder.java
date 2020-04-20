package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.soptorshi.domain.enumeration.SupplyOrderStatus;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A SupplyOrder.
 */
@Entity
@Table(name = "supply_order")
@Document(indexName = "supplyorder")
public class SupplyOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "order_no", nullable = false)
    private String orderNo;

    @Column(name = "date_of_order")
    private LocalDate dateOfOrder;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_on")
    private Instant updatedOn;

    @Column(name = "offer_amount", precision = 10, scale = 2)
    private BigDecimal offerAmount;

    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "supply_order_status", nullable = false)
    private SupplyOrderStatus supplyOrderStatus;

    @ManyToOne
    @JsonIgnoreProperties("supplyOrders")
    private SupplyZone supplyZone;

    @ManyToOne
    @JsonIgnoreProperties("supplyOrders")
    private SupplyArea supplyArea;

    @ManyToOne
    @JsonIgnoreProperties("supplyOrders")
    private SupplySalesRepresentative supplySalesRepresentative;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("supplyOrders")
    private SupplyAreaManager supplyAreaManager;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("supplyOrders")
    private SupplyShop supplyShop;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public SupplyOrder orderNo(String orderNo) {
        this.orderNo = orderNo;
        return this;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public LocalDate getDateOfOrder() {
        return dateOfOrder;
    }

    public SupplyOrder dateOfOrder(LocalDate dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
        return this;
    }

    public void setDateOfOrder(LocalDate dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public SupplyOrder createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public SupplyOrder createdOn(Instant createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public SupplyOrder updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public SupplyOrder updatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public BigDecimal getOfferAmount() {
        return offerAmount;
    }

    public SupplyOrder offerAmount(BigDecimal offerAmount) {
        this.offerAmount = offerAmount;
        return this;
    }

    public void setOfferAmount(BigDecimal offerAmount) {
        this.offerAmount = offerAmount;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public SupplyOrder deliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
        return this;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public SupplyOrderStatus getSupplyOrderStatus() {
        return supplyOrderStatus;
    }

    public SupplyOrder supplyOrderStatus(SupplyOrderStatus supplyOrderStatus) {
        this.supplyOrderStatus = supplyOrderStatus;
        return this;
    }

    public void setSupplyOrderStatus(SupplyOrderStatus supplyOrderStatus) {
        this.supplyOrderStatus = supplyOrderStatus;
    }

    public SupplyZone getSupplyZone() {
        return supplyZone;
    }

    public SupplyOrder supplyZone(SupplyZone supplyZone) {
        this.supplyZone = supplyZone;
        return this;
    }

    public void setSupplyZone(SupplyZone supplyZone) {
        this.supplyZone = supplyZone;
    }

    public SupplyArea getSupplyArea() {
        return supplyArea;
    }

    public SupplyOrder supplyArea(SupplyArea supplyArea) {
        this.supplyArea = supplyArea;
        return this;
    }

    public void setSupplyArea(SupplyArea supplyArea) {
        this.supplyArea = supplyArea;
    }

    public SupplySalesRepresentative getSupplySalesRepresentative() {
        return supplySalesRepresentative;
    }

    public SupplyOrder supplySalesRepresentative(SupplySalesRepresentative supplySalesRepresentative) {
        this.supplySalesRepresentative = supplySalesRepresentative;
        return this;
    }

    public void setSupplySalesRepresentative(SupplySalesRepresentative supplySalesRepresentative) {
        this.supplySalesRepresentative = supplySalesRepresentative;
    }

    public SupplyAreaManager getSupplyAreaManager() {
        return supplyAreaManager;
    }

    public SupplyOrder supplyAreaManager(SupplyAreaManager supplyAreaManager) {
        this.supplyAreaManager = supplyAreaManager;
        return this;
    }

    public void setSupplyAreaManager(SupplyAreaManager supplyAreaManager) {
        this.supplyAreaManager = supplyAreaManager;
    }

    public SupplyShop getSupplyShop() {
        return supplyShop;
    }

    public SupplyOrder supplyShop(SupplyShop supplyShop) {
        this.supplyShop = supplyShop;
        return this;
    }

    public void setSupplyShop(SupplyShop supplyShop) {
        this.supplyShop = supplyShop;
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
        SupplyOrder supplyOrder = (SupplyOrder) o;
        if (supplyOrder.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), supplyOrder.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SupplyOrder{" +
            "id=" + getId() +
            ", orderNo='" + getOrderNo() + "'" +
            ", dateOfOrder='" + getDateOfOrder() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", offerAmount=" + getOfferAmount() +
            ", deliveryDate='" + getDeliveryDate() + "'" +
            ", supplyOrderStatus='" + getSupplyOrderStatus() + "'" +
            "}";
    }
}
