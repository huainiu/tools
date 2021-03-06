package model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jzhang on 06/03/2015.
 */
@Entity
@NamedQuery(name="Catalogue.findCatalogueByUniqueId", query="SELECT c FROM Catalogue c WHERE c.uniqueId = :uniqueId and c.enseigne_id = :enseigne_id")
public class Catalogue {
    private int id;
    private int enseigne_id;
    private String uniqueId;
    private Timestamp dateImport = new Timestamp(new Date().getTime());
    private Timestamp dateExport;
    private String libelle;
    private String slug;
    private String dateDebut;
    private String dateFin;
    private String description;
    private String images;
    private String pdfs;
    private String pages;
//    private Set<Magasin> magasins = new HashSet<Magasin>();


    public void setCatalogue(Catalogue c) {
        this.setId(c.getId());
        this.setEnseigne_id(c.getEnseigne_id());
        this.setUniqueId(c.getUniqueId());
        this.setDateExport(c.getDateExport());
        this.setLibelle(c.getLibelle());
        this.setSlug(c.getSlug());
        this.setDateDebut(c.getDateDebut());
        this.setDateFin(c.getDateFin());
        this.setDescription(c.getDescription());
        this.setImages(c.getImages());
        this.setPdfs(c.getPdfs());
        this.setPages(c.getPages());
//        this.setMagasins(c.getMagasins());
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "enseigne_id")
    public int getEnseigne_id() {
        return enseigne_id;
    }
    public void setEnseigne_id(int enseigne_id) {
        this.enseigne_id = enseigne_id;
    }


    @Basic
    @Column(name = "unique_id")
    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    @Basic
    @Column(name = "date_import")
    public Timestamp getDateImport() {
        return dateImport;
    }

    public void setDateImport(Timestamp dateImport) {
        this.dateImport = dateImport;
    }

    @Basic
    @Column(name = "date_export")
    public Timestamp getDateExport() {
        return dateExport;
    }

    public void setDateExport(Timestamp dateExport) {
        this.dateExport = dateExport;
    }

    @Basic
    @Column(name = "libelle")
    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Basic
    @Column(name = "slug")
    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    @Basic
    @Column(name = "date_debut")
    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    @Basic
    @Column(name = "date_fin")
    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "images")
    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    @Basic
    @Column(name = "pdfs")
    public String getPdfs() {
        return pdfs;
    }

    public void setPdfs(String pdfs) {
        this.pdfs = pdfs;
    }

    @Basic
    @Column(name = "pages")
    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

/*
    @ManyToMany(mappedBy = "catalogues")
    public Set<Magasin> getMagasins() {
        return magasins;
    }

    public void setMagasins(Set<Magasin> magasins) {
        this.magasins = magasins;
    }
*/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Catalogue catalogue = (Catalogue) o;

        if (id != catalogue.id) return false;
        if (dateDebut != null ? !dateDebut.equals(catalogue.dateDebut) : catalogue.dateDebut != null) return false;
        if (dateExport != null ? !dateExport.equals(catalogue.dateExport) : catalogue.dateExport != null) return false;
        if (dateFin != null ? !dateFin.equals(catalogue.dateFin) : catalogue.dateFin != null) return false;
        if (dateImport != null ? !dateImport.equals(catalogue.dateImport) : catalogue.dateImport != null) return false;
        if (description != null ? !description.equals(catalogue.description) : catalogue.description != null)
            return false;
        if (images != null ? !images.equals(catalogue.images) : catalogue.images != null) return false;
        if (libelle != null ? !libelle.equals(catalogue.libelle) : catalogue.libelle != null) return false;
        if (pages != null ? !pages.equals(catalogue.pages) : catalogue.pages != null) return false;
        if (pdfs != null ? !pdfs.equals(catalogue.pdfs) : catalogue.pdfs != null) return false;
        if (slug != null ? !slug.equals(catalogue.slug) : catalogue.slug != null) return false;
        if (uniqueId != null ? !uniqueId.equals(catalogue.uniqueId) : catalogue.uniqueId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (uniqueId != null ? uniqueId.hashCode() : 0);
        result = 31 * result + (dateImport != null ? dateImport.hashCode() : 0);
        result = 31 * result + (dateExport != null ? dateExport.hashCode() : 0);
        result = 31 * result + (libelle != null ? libelle.hashCode() : 0);
        result = 31 * result + (slug != null ? slug.hashCode() : 0);
        result = 31 * result + (dateDebut != null ? dateDebut.hashCode() : 0);
        result = 31 * result + (dateFin != null ? dateFin.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (images != null ? images.hashCode() : 0);
        result = 31 * result + (pdfs != null ? pdfs.hashCode() : 0);
        result = 31 * result + (pages != null ? pages.hashCode() : 0);
        return result;
    }
}
