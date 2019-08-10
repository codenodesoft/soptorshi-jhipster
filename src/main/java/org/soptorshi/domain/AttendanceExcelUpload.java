package org.soptorshi.domain;



import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

import org.soptorshi.domain.enumeration.AttendanceType;

/**
 * A AttendanceExcelUpload.
 */
@Entity
@Table(name = "attendance_excel_upload")
@Document(indexName = "attendanceexcelupload")
public class AttendanceExcelUpload implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "jhi_file")
    private byte[] file;

    @Column(name = "jhi_file_content_type")
    private String fileContentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private AttendanceType type;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getFile() {
        return file;
    }

    public AttendanceExcelUpload file(byte[] file) {
        this.file = file;
        return this;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public AttendanceExcelUpload fileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
        return this;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public AttendanceType getType() {
        return type;
    }

    public AttendanceExcelUpload type(AttendanceType type) {
        this.type = type;
        return this;
    }

    public void setType(AttendanceType type) {
        this.type = type;
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
        AttendanceExcelUpload attendanceExcelUpload = (AttendanceExcelUpload) o;
        if (attendanceExcelUpload.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), attendanceExcelUpload.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AttendanceExcelUpload{" +
            "id=" + getId() +
            ", file='" + getFile() + "'" +
            ", fileContentType='" + getFileContentType() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}