package model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by jzhang on 06/03/2015.
 */
@Entity
public class Magasin {
    private int id;
    private String uniqueId;
    private String libelle;
    private String slug;
    private String adresse;
    private String cp;
    private String ville;
    private String departement;
    private String url;
    private String description;
    private Integer magIdExport;
    private Double lat;
    private Double log;
    private String tel;
    private String fax;
    private String horaire;
    private String contact;
    private Timestamp lastUpdateDate;
    private Enseigne enseigneByEnseigneId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    @Column(name = "adresse")
    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    @Basic
    @Column(name = "cp")
    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    @Basic
    @Column(name = "ville")
    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    @Basic
    @Column(name = "departement")
    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    @Basic
    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
    @Column(name = "mag_id_export")
    public Integer getMagIdExport() {
        return magIdExport;
    }

    public void setMagIdExport(Integer magIdExport) {
        this.magIdExport = magIdExport;
    }

    @Basic
    @Column(name = "lat")
    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    @Basic
    @Column(name = "log")
    public Double getLog() {
        return log;
    }

    public void setLog(Double log) {
        this.log = log;
    }

    @Basic
    @Column(name = "tel")
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Basic
    @Column(name = "fax")
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    @Basic
    @Column(name = "horaire")
    public String getHoraire() {
        return horaire;
    }

    public void setHoraire(String horaire) {
        this.horaire = horaire;
    }

    @Basic
    @Column(name = "contact")
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Basic
    @Column(name = "last_update_date")
    public Timestamp getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Timestamp lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Magasin magasin = (Magasin) o;

        if (id != magasin.id) return false;
        if (adresse != null ? !adresse.equals(magasin.adresse) : magasin.adresse != null) return false;
        if (contact != null ? !contact.equals(magasin.contact) : magasin.contact != null) return false;
        if (cp != null ? !cp.equals(magasin.cp) : magasin.cp != null) return false;
        if (departement != null ? !departement.equals(magasin.departement) : magasin.departement != null) return false;
        if (description != null ? !description.equals(magasin.description) : magasin.description != null) return false;
        if (fax != null ? !fax.equals(magasin.fax) : magasin.fax != null) return false;
        if (horaire != null ? !horaire.equals(magasin.horaire) : magasin.horaire != null) return false;
        if (lastUpdateDate != null ? !lastUpdateDate.equals(magasin.lastUpdateDate) : magasin.lastUpdateDate != null)
            return false;
        if (lat != null ? !lat.equals(magasin.lat) : magasin.lat != null) return false;
        if (libelle != null ? !libelle.equals(magasin.libelle) : magasin.libelle != null) return false;
        if (log != null ? !log.equals(magasin.log) : magasin.log != null) return false;
        if (magIdExport != null ? !magIdExport.equals(magasin.magIdExport) : magasin.magIdExport != null) return false;
        if (slug != null ? !slug.equals(magasin.slug) : magasin.slug != null) return false;
        if (tel != null ? !tel.equals(magasin.tel) : magasin.tel != null) return false;
        if (uniqueId != null ? !uniqueId.equals(magasin.uniqueId) : magasin.uniqueId != null) return false;
        if (url != null ? !url.equals(magasin.url) : magasin.url != null) return false;
        if (ville != null ? !ville.equals(magasin.ville) : magasin.ville != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (uniqueId != null ? uniqueId.hashCode() : 0);
        result = 31 * result + (libelle != null ? libelle.hashCode() : 0);
        result = 31 * result + (slug != null ? slug.hashCode() : 0);
        result = 31 * result + (adresse != null ? adresse.hashCode() : 0);
        result = 31 * result + (cp != null ? cp.hashCode() : 0);
        result = 31 * result + (ville != null ? ville.hashCode() : 0);
        result = 31 * result + (departement != null ? departement.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (magIdExport != null ? magIdExport.hashCode() : 0);
        result = 31 * result + (lat != null ? lat.hashCode() : 0);
        result = 31 * result + (log != null ? log.hashCode() : 0);
        result = 31 * result + (tel != null ? tel.hashCode() : 0);
        result = 31 * result + (fax != null ? fax.hashCode() : 0);
        result = 31 * result + (horaire != null ? horaire.hashCode() : 0);
        result = 31 * result + (contact != null ? contact.hashCode() : 0);
        result = 31 * result + (lastUpdateDate != null ? lastUpdateDate.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "enseigne_id", referencedColumnName = "id", nullable = false)
    public Enseigne getEnseigneByEnseigneId() {
        return enseigneByEnseigneId;
    }

    public void setEnseigneByEnseigneId(Enseigne enseigneByEnseigneId) {
        this.enseigneByEnseigneId = enseigneByEnseigneId;
    }
}
