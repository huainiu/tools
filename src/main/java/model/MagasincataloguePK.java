package model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by jzhang on 06/03/2015.
 */
public class MagasincataloguePK implements Serializable {
    private int magasinId;
    private int catalogueId;

    @Column(name = "magasin_id")
    @Id
    public int getMagasinId() {
        return magasinId;
    }

    public void setMagasinId(int magasinId) {
        this.magasinId = magasinId;
    }

    @Column(name = "catalogue_id")
    @Id
    public int getCatalogueId() {
        return catalogueId;
    }

    public void setCatalogueId(int catalogueId) {
        this.catalogueId = catalogueId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MagasincataloguePK that = (MagasincataloguePK) o;

        if (catalogueId != that.catalogueId) return false;
        if (magasinId != that.magasinId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = magasinId;
        result = 31 * result + catalogueId;
        return result;
    }
}
