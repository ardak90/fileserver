package kz.technodom.storage.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DeffectiveProduct.
 */
@Entity
@Table(name = "deffective_product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DeffectiveProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "description")
    private String description;

    @Column(name = "jhi_user")
    private String user;

    @OneToMany(mappedBy = "deffectiveProduct", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.NONE)
    private Set<FileImage> fileImages = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public DeffectiveProduct serialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getDescription() {
        return description;
    }

    public DeffectiveProduct description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public Set<FileImage> getFileImages() {
        return fileImages;
    }

    public DeffectiveProduct fileImages(Set<FileImage> fileImages) {
        this.fileImages = fileImages;
        return this;
    }

    public DeffectiveProduct addFileImage(FileImage fileImage) {
        this.fileImages.add(fileImage);
        fileImage.set(this);
        return this;
    }

    public DeffectiveProduct removeFileImage(FileImage fileImage) {
        this.fileImages.remove(fileImage);
        fileImage.set(null);
        return this;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setFileImages(Set<FileImage> fileImages) {
        this.fileImages = fileImages;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeffectiveProduct)) {
            return false;
        }
        return id != null && id.equals(((DeffectiveProduct) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DeffectiveProduct{" +
            "id=" + getId() +
            ", serialNumber='" + getSerialNumber() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
