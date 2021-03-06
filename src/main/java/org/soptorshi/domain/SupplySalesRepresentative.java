package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.soptorshi.domain.enumeration.SupplySalesRepresentativeStatus;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A SupplySalesRepresentative.
 */
@Entity
@Table(name = "supply_sales_representative")
@Document(indexName = "supplysalesrepresentative")
public class SupplySalesRepresentative implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "contact", nullable = false)
    private String contact;

    @Column(name = "email")
    private String email;

    @Column(name = "additional_information")
    private String additionalInformation;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_on")
    private Instant updatedOn;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SupplySalesRepresentativeStatus status;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("supplySalesRepresentatives")
    private SupplyZone supplyZone;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("supplySalesRepresentatives")
    private SupplyArea supplyArea;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("supplySalesRepresentatives")
    private SupplyZoneManager supplyZoneManager;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("supplySalesRepresentatives")
    private SupplyAreaManager supplyAreaManager;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public SupplySalesRepresentative name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public SupplySalesRepresentative contact(String contact) {
        this.contact = contact;
        return this;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public SupplySalesRepresentative email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public SupplySalesRepresentative additionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
        return this;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public SupplySalesRepresentative createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public SupplySalesRepresentative createdOn(Instant createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public SupplySalesRepresentative updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public SupplySalesRepresentative updatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public SupplySalesRepresentativeStatus getStatus() {
        return status;
    }

    public SupplySalesRepresentative status(SupplySalesRepresentativeStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(SupplySalesRepresentativeStatus status) {
        this.status = status;
    }

    public SupplyZone getSupplyZone() {
        return supplyZone;
    }

    public SupplySalesRepresentative supplyZone(SupplyZone supplyZone) {
        this.supplyZone = supplyZone;
        return this;
    }

    public void setSupplyZone(SupplyZone supplyZone) {
        this.supplyZone = supplyZone;
    }

    public SupplyArea getSupplyArea() {
        return supplyArea;
    }

    public SupplySalesRepresentative supplyArea(SupplyArea supplyArea) {
        this.supplyArea = supplyArea;
        return this;
    }

    public void setSupplyArea(SupplyArea supplyArea) {
        this.supplyArea = supplyArea;
    }

    public SupplyZoneManager getSupplyZoneManager() {
        return supplyZoneManager;
    }

    public SupplySalesRepresentative supplyZoneManager(SupplyZoneManager supplyZoneManager) {
        this.supplyZoneManager = supplyZoneManager;
        return this;
    }

    public void setSupplyZoneManager(SupplyZoneManager supplyZoneManager) {
        this.supplyZoneManager = supplyZoneManager;
    }

    public SupplyAreaManager getSupplyAreaManager() {
        return supplyAreaManager;
    }

    public SupplySalesRepresentative supplyAreaManager(SupplyAreaManager supplyAreaManager) {
        this.supplyAreaManager = supplyAreaManager;
        return this;
    }

    public void setSupplyAreaManager(SupplyAreaManager supplyAreaManager) {
        this.supplyAreaManager = supplyAreaManager;
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
        SupplySalesRepresentative supplySalesRepresentative = (SupplySalesRepresentative) o;
        if (supplySalesRepresentative.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), supplySalesRepresentative.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SupplySalesRepresentative{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", contact='" + getContact() + "'" +
            ", email='" + getEmail() + "'" +
            ", additionalInformation='" + getAdditionalInformation() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
