package model;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by jzhang on 06/03/2015.
 */
@Entity
@NamedQuery(name="Occurence.findOccurenceByUniqueId", query="SELECT o FROM Occurence o WHERE o.uniqueId = :uniqueId and o.catalogueId = :catalogueId")
public class Occurence {
    private int id;

    @Id
    @javax.persistence.Column(name = "id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int catalogueId;

    @Basic
    @javax.persistence.Column(name = "catalogue_id")
    public int getCatalogueId() {
        return catalogueId;
    }

    public void setCatalogueIdId(int catalogueId) {
        this.catalogueId = catalogueId;
    }

    private String uniqueId;

    @Basic
    @javax.persistence.Column(name = "unique_id")
    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    private String page;

    @Basic
    @javax.persistence.Column(name = "page")
    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    private String libelleShort;

    @Basic
    @javax.persistence.Column(name = "libelle_short")
    public String getLibelleShort() {
        return libelleShort;
    }

    public void setLibelleShort(String libelleShort) {
        this.libelleShort = libelleShort;
    }

    private String libelleLong;

    @Basic
    @javax.persistence.Column(name = "libelle_long")
    public String getLibelleLong() {
        return libelleLong;
    }

    public void setLibelleLong(String libelleLong) {
        this.libelleLong = libelleLong;
    }

    private String ean;

    @Basic
    @javax.persistence.Column(name = "ean")
    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    private String slug;

    @Basic
    @javax.persistence.Column(name = "slug")
    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    private String productUniqueId;

    @Basic
    @javax.persistence.Column(name = "product_unique_id")
    public String getProductUniqueId() {
        return productUniqueId;
    }

    public void setProductUniqueId(String productUniqueId) {
        this.productUniqueId = productUniqueId;
    }

    private BigDecimal priceBeforePromo;

    @Basic
    @javax.persistence.Column(name = "price_before_promo")
    public BigDecimal getPriceBeforePromo() {
        return priceBeforePromo;
    }

    public void setPriceBeforePromo(BigDecimal priceBeforePromo) {
        this.priceBeforePromo = priceBeforePromo;
    }

    private BigDecimal priceAfterPromo;

    @Basic
    @javax.persistence.Column(name = "price_after_promo")
    public BigDecimal getPriceAfterPromo() {
        return priceAfterPromo;
    }

    public void setPriceAfterPromo(BigDecimal priceAfterPromo) {
        this.priceAfterPromo = priceAfterPromo;
    }

    private String descriptionShort;

    @Basic
    @javax.persistence.Column(name = "description_short")
    public String getDescriptionShort() {
        return descriptionShort;
    }

    public void setDescriptionShort(String descriptionShort) {
        this.descriptionShort = descriptionShort;
    }

    private String descriptionLong;

    @Basic
    @javax.persistence.Column(name = "description_long")
    public String getDescriptionLong() {
        return descriptionLong;
    }

    public void setDescriptionLong(String descriptionLong) {
        this.descriptionLong = descriptionLong;
    }

    private String legalText1;

    @Basic
    @javax.persistence.Column(name = "legal_text_1")
    public String getLegalText1() {
        return legalText1;
    }

    public void setLegalText1(String legalText1) {
        this.legalText1 = legalText1;
    }

    private String legalText2;

    @Basic
    @javax.persistence.Column(name = "legal_text_2")
    public String getLegalText2() {
        return legalText2;
    }

    public void setLegalText2(String legalText2) {
        this.legalText2 = legalText2;
    }

    private String validityText;

    @Basic
    @javax.persistence.Column(name = "validity_text")
    public String getValidityText() {
        return validityText;
    }

    public void setValidityText(String validityText) {
        this.validityText = validityText;
    }

    private String category;

    @Basic
    @javax.persistence.Column(name = "category")
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    private String rayon;

    @Basic
    @javax.persistence.Column(name = "rayon")
    public String getRayon() {
        return rayon;
    }

    public void setRayon(String rayon) {
        this.rayon = rayon;
    }

    private String segment;

    @Basic
    @javax.persistence.Column(name = "segment")
    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    private String sousSegment;

    @Basic
    @javax.persistence.Column(name = "sous_segment")
    public String getSousSegment() {
        return sousSegment;
    }

    public void setSousSegment(String sousSegment) {
        this.sousSegment = sousSegment;
    }

    private String descriptionPromo;

    @Basic
    @javax.persistence.Column(name = "description_promo")
    public String getDescriptionPromo() {
        return descriptionPromo;
    }

    public void setDescriptionPromo(String descriptionPromo) {
        this.descriptionPromo = descriptionPromo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Occurence occurence = (Occurence) o;

        if (id != occurence.id) return false;
        if (category != null ? !category.equals(occurence.category) : occurence.category != null) return false;
        if (descriptionLong != null ? !descriptionLong.equals(occurence.descriptionLong) : occurence.descriptionLong != null)
            return false;
        if (descriptionPromo != null ? !descriptionPromo.equals(occurence.descriptionPromo) : occurence.descriptionPromo != null)
            return false;
        if (descriptionShort != null ? !descriptionShort.equals(occurence.descriptionShort) : occurence.descriptionShort != null)
            return false;
        if (ean != null ? !ean.equals(occurence.ean) : occurence.ean != null) return false;
        if (legalText1 != null ? !legalText1.equals(occurence.legalText1) : occurence.legalText1 != null) return false;
        if (legalText2 != null ? !legalText2.equals(occurence.legalText2) : occurence.legalText2 != null) return false;
        if (libelleLong != null ? !libelleLong.equals(occurence.libelleLong) : occurence.libelleLong != null)
            return false;
        if (libelleShort != null ? !libelleShort.equals(occurence.libelleShort) : occurence.libelleShort != null)
            return false;
        if (page != null ? !page.equals(occurence.page) : occurence.page != null) return false;
        if (priceAfterPromo != null ? !priceAfterPromo.equals(occurence.priceAfterPromo) : occurence.priceAfterPromo != null)
            return false;
        if (priceBeforePromo != null ? !priceBeforePromo.equals(occurence.priceBeforePromo) : occurence.priceBeforePromo != null)
            return false;
        if (productUniqueId != null ? !productUniqueId.equals(occurence.productUniqueId) : occurence.productUniqueId != null)
            return false;
        if (rayon != null ? !rayon.equals(occurence.rayon) : occurence.rayon != null) return false;
        if (segment != null ? !segment.equals(occurence.segment) : occurence.segment != null) return false;
        if (slug != null ? !slug.equals(occurence.slug) : occurence.slug != null) return false;
        if (sousSegment != null ? !sousSegment.equals(occurence.sousSegment) : occurence.sousSegment != null)
            return false;
        if (uniqueId != null ? !uniqueId.equals(occurence.uniqueId) : occurence.uniqueId != null) return false;
        if (validityText != null ? !validityText.equals(occurence.validityText) : occurence.validityText != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (uniqueId != null ? uniqueId.hashCode() : 0);
        result = 31 * result + (page != null ? page.hashCode() : 0);
        result = 31 * result + (libelleShort != null ? libelleShort.hashCode() : 0);
        result = 31 * result + (libelleLong != null ? libelleLong.hashCode() : 0);
        result = 31 * result + (ean != null ? ean.hashCode() : 0);
        result = 31 * result + (slug != null ? slug.hashCode() : 0);
        result = 31 * result + (productUniqueId != null ? productUniqueId.hashCode() : 0);
        result = 31 * result + (priceBeforePromo != null ? priceBeforePromo.hashCode() : 0);
        result = 31 * result + (priceAfterPromo != null ? priceAfterPromo.hashCode() : 0);
        result = 31 * result + (descriptionShort != null ? descriptionShort.hashCode() : 0);
        result = 31 * result + (descriptionLong != null ? descriptionLong.hashCode() : 0);
        result = 31 * result + (legalText1 != null ? legalText1.hashCode() : 0);
        result = 31 * result + (legalText2 != null ? legalText2.hashCode() : 0);
        result = 31 * result + (validityText != null ? validityText.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (rayon != null ? rayon.hashCode() : 0);
        result = 31 * result + (segment != null ? segment.hashCode() : 0);
        result = 31 * result + (sousSegment != null ? sousSegment.hashCode() : 0);
        result = 31 * result + (descriptionPromo != null ? descriptionPromo.hashCode() : 0);
        return result;
    }

    public void setOccurence(Occurence o) {
        this.uniqueId = o.getUniqueId();
        this.page = o.getPage();
        this.libelleShort = o.getLibelleShort();
        this.libelleLong = o.getLibelleLong();
        this.ean = o.getEan();
        this.slug = o.getSlug();
        this.productUniqueId = o.getProductUniqueId();
        this.priceBeforePromo = o.getPriceBeforePromo();
        this.priceAfterPromo = o.getPriceAfterPromo();
        this.descriptionShort = o.getDescriptionShort();
        this.descriptionLong = o.getDescriptionLong();
        this.legalText1 = o.getLegalText1();
        this.legalText2 = o.getLegalText2();
        this.validityText = o.getValidityText();
        this.category = o.getCategory();
        this.rayon = o.getRayon();
        this.segment = o.getSegment();
        this.sousSegment = o.getSousSegment();
        this.descriptionPromo = o.getDescriptionPromo();
        this.catalogueId = o.getCatalogueId();
        this.id = o.getId();
    }
}
