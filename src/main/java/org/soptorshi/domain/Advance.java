package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import org.soptorshi.domain.enumeration.PaymentStatus;

/**
 * A Advance.
 */
@Entity
@Table(name = "advance")
@Document(indexName = "advance")
public class Advance implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount", precision = 10, scale = 2)
    private BigDecimal amount;

    @Lob
    @Column(name = "reason")
    private byte[] reason;

    @Column(name = "reason_content_type")
    private String reasonContentType;

    @Column(name = "provided_on")
    private LocalDate providedOn;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @Column(name = "jhi_left", precision = 10, scale = 2)
    private BigDecimal left;

    @Column(name = "modified_by")
    private Long modifiedBy;

    @Column(name = "modified_on")
    private LocalDate modifiedOn;

    @ManyToOne
    @JsonIgnoreProperties("advances")
    private Employee employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Advance amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public byte[] getReason() {
        return reason;
    }

    public Advance reason(byte[] reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(byte[] reason) {
        this.reason = reason;
    }

    public String getReasonContentType() {
        return reasonContentType;
    }

    public Advance reasonContentType(String reasonContentType) {
        this.reasonContentType = reasonContentType;
        return this;
    }

    public void setReasonContentType(String reasonContentType) {
        this.reasonContentType = reasonContentType;
    }

    public LocalDate getProvidedOn() {
        return providedOn;
    }

    public Advance providedOn(LocalDate providedOn) {
        this.providedOn = providedOn;
        return this;
    }

    public void setProvidedOn(LocalDate providedOn) {
        this.providedOn = providedOn;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public Advance paymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
        return this;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public BigDecimal getLeft() {
        return left;
    }

    public Advance left(BigDecimal left) {
        this.left = left;
        return this;
    }

    public void setLeft(BigDecimal left) {
        this.left = left;
    }

    public Long getModifiedBy() {
        return modifiedBy;
    }

    public Advance modifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDate getModifiedOn() {
        return modifiedOn;
    }

    public Advance modifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
        return this;
    }

    public void setModifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Advance employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
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
        Advance advance = (Advance) o;
        if (advance.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), advance.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Advance{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", reason='" + getReason() + "'" +
            ", reasonContentType='" + getReasonContentType() + "'" +
            ", providedOn='" + getProvidedOn() + "'" +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            ", left=" + getLeft() +
            ", modifiedBy=" + getModifiedBy() +
            ", modifiedOn='" + getModifiedOn() + "'" +
            "}";
    }
}
