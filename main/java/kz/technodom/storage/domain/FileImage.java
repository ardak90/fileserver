package kz.technodom.storage.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A FileImage.
 */
@Entity
@Table(name = "file_image")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FileImage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "size")
    private String size;

    @Column(name = "mime_type")
    private String mimeType;

    @Column(name="uuid")
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @ManyToOne
    @JsonIgnoreProperties("fileImages")
    private DeffectiveProduct deffectiveProduct;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public FileImage expiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getSize() {
        return size;
    }

    public FileImage size(String size) {
        this.size = size;
        return this;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getMimeType() {
        return mimeType;
    }

    public FileImage mimeType(String mimeType) {
        this.mimeType = mimeType;
        return this;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FileImage)) {
            return false;
        }
        return id != null && id.equals(((FileImage) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FileImage{" +
            "id=" + getId() +
            ", expiryDate='" + getExpiryDate() + "'" +
            ", size='" + getSize() + "'" +
            ", mimeType='" + getMimeType() + "'" +
            "}";
    }

    public void set(DeffectiveProduct deffectiveProduct) {
    }

    public DeffectiveProduct getDeffectiveProduct() {
        return deffectiveProduct;
    }

    public void setDeffectiveProduct(DeffectiveProduct deffectiveProduct) {
        this.deffectiveProduct = deffectiveProduct;
    }
}
