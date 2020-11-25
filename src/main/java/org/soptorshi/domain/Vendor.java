package org.soptorshi.domain;



import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

import org.soptorshi.domain.enumeration.VendorRemarks;

/**
 * A Vendor.
 */
@Entity
@Table(name = "vendor")
@Document(indexName = "vendor")
public class Vendor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_name")
    private String companyName;

    @Lob
    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "address")
    private String address;

    @Column(name = "contact_number")
    private String contactNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "remarks")
    private VendorRemarks remarks;

    @ManyToOne
    @JsonIgnoreProperties("products")
    private MstAccount account;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vendor account(MstAccount account){
        this.account = account;
        return this;
    }

    public MstAccount getAccount() {
        return account;
    }

    public void setAccount(MstAccount account) {
        this.account = account;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Vendor companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDescription() {
        return description;
    }

    public Vendor description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public Vendor address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public Vendor contactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
        return this;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public VendorRemarks getRemarks() {
        return remarks;
    }

    public Vendor remarks(VendorRemarks remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(VendorRemarks remarks) {
        this.remarks = remarks;
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
        Vendor vendor = (Vendor) o;
        if (vendor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vendor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Vendor{" +
            "id=" + getId() +
            ", companyName='" + getCompanyName() + "'" +
            ", description='" + getDescription() + "'" +
            ", address='" + getAddress() + "'" +
            ", contactNumber='" + getContactNumber() + "'" +
            ", remarks='" + getRemarks() + "'" +
            "}";
    }
}
