package model;

import javax.persistence.*;

/**
 * Created by jzhang on 06/03/2015.
 */
@Entity
@NamedQuery(name="Productimage.findProductimageByUniqueId", query="SELECT p FROM Productimage p WHERE p.productUniqueId = :productUniqueId and p.catalogueId = :catalogueId")
public class Productimage {
    private int id;
    private String productUniqueId;
    private String images;
    private int catalogueId;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Basic
    @Column(name = "catalogue_id")
    public int getCatalogueId() {
        return catalogueId;
    }

    public void setCatalogueId(int catalogueId) {
        this.catalogueId = catalogueId;
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

    public void setProductimage(Productimage p) {
        this.productUniqueId = p.getProductUniqueId();
        this.images = p.getImages();
        this.catalogueId = p.getCatalogueId();
        this.id = p.getId();
    }



}
