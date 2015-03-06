package model;

import javax.persistence.*;

/**
 * Created by jzhang on 06/03/2015.
 */
@Entity
@IdClass(MagasincataloguePK.class)
public class Magasincatalogue {
    private int magasinId;
    private int catalogueId;
    private Magasin magasinByMagasinId;
    private Catalogue catalogueByCatalogueId;

    @Id
    @Column(name = "magasin_id")
    public int getMagasinId() {
        return magasinId;
    }

    public void setMagasinId(int magasinId) {
        this.magasinId = magasinId;
    }

    @Id
    @Column(name = "catalogue_id")
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

        Magasincatalogue that = (Magasincatalogue) o;

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

    @ManyToOne
    @JoinColumn(name = "magasin_id", referencedColumnName = "id", nullable = false)
    public Magasin getMagasinByMagasinId() {
        return magasinByMagasinId;
    }

    public void setMagasinByMagasinId(Magasin magasinByMagasinId) {
        this.magasinByMagasinId = magasinByMagasinId;
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
