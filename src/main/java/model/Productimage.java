package model;

import javax.persistence.*;

/**
 * Created by jzhang on 06/03/2015.
 */
@Entity
public class Productimage {
    private int id;
    private String productUniqueId;
    private String images;
    private Catalogue catalogueByCatalogueId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "product_unique_id")
    public String getProductUniqueId() {
        return productUniqueId;
    }

    public void setProductUniqueId(String productUniqueId) {
        this.productUniqueId = productUniqueId;
    }

    @Basic
    @Column(name = "images")
    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Productimage that = (Productimage) o;

        if (id != that.id) return false;
        if (images != null ? !images.equals(that.images) : that.images != null) return false;
        if (productUniqueId != null ? !productUniqueId.equals(that.productUniqueId) : that.productUniqueId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (productUniqueId != null ? productUniqueId.hashCode() : 0);
        result = 31 * result + (images != null ? images.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "catalogue_id", referencedColumnName = "id", nullable = false)
    public Catalogue getCatalogueByCatalogueId() {
        return catalogueByCatalogueId;
    }

    public void setCatalogueByCatalogueId(Catalogue catalogueByCatalogueId) {
        this.catalogueByCatalogueId = catalogueByCatalogueId;
    }
}
