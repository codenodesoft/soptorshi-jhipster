package org.soptorshi.service.dto;

import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.VendorRemarks;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the Vendor entity. This class is used in VendorResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /vendors?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class VendorCriteria implements Serializable {
    /**
     * Class for filtering VendorRemarks
     */
    public static class VendorRemarksFilter extends Filter<VendorRemarks> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter companyName;

    private StringFilter contactNumber;

    private VendorRemarksFilter remarks;

    private LongFilter accountId;


    public LongFilter getAccountId() {
        return accountId;
    }

    public void setAccountId(LongFilter accountId) {
        this.accountId = accountId;
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCompanyName() {
        return companyName;
    }

    public void setCompanyName(StringFilter companyName) {
        this.companyName = companyName;
    }

    public StringFilter getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(StringFilter contactNumber) {
        this.contactNumber = contactNumber;
    }

    public VendorRemarksFilter getRemarks() {
        return remarks;
    }

    public void setRemarks(VendorRemarksFilter remarks) {
        this.remarks = remarks;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final VendorCriteria that = (VendorCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(companyName, that.companyName) &&
            Objects.equals(contactNumber, that.contactNumber) &&
            Objects.equals(remarks, that.remarks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        companyName,
        contactNumber,
        remarks
        );
    }

    @Override
    public String toString() {
        return "VendorCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (companyName != null ? "companyName=" + companyName + ", " : "") +
                (contactNumber != null ? "contactNumber=" + contactNumber + ", " : "") +
                (remarks != null ? "remarks=" + remarks + ", " : "") +
            "}";
    }

}
