package org.soptorshi.service.dto;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import org.soptorshi.domain.enumeration.VendorRemarks;

/**
 * A DTO for the Vendor entity.
 */
public class VendorDTO implements Serializable {

    private Long id;

    private String companyName;

    @Lob
    private String description;

    @Lob
    private String address;

    private String contactNumber;

    private VendorRemarks remarks;

    private Long accountId;

    private String accountName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public VendorRemarks getRemarks() {
        return remarks;
    }

    public void setRemarks(VendorRemarks remarks) {
        this.remarks = remarks;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VendorDTO vendorDTO = (VendorDTO) o;
        if (vendorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vendorDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VendorDTO{" +
            "id=" + id +
            ", companyName='" + companyName + '\'' +
            ", description='" + description + '\'' +
            ", address='" + address + '\'' +
            ", contactNumber='" + contactNumber + '\'' +
            ", remarks=" + remarks +
            ", accountId=" + accountId +
            ", accountName='" + accountName + '\'' +
            '}';
    }
}
