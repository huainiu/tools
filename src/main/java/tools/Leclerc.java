package tools;

import com.beust.jcommander.internal.Lists;
import model.*;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import utils.scp.ScpClient;
import utils.scp.ScpTask;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by jzhang on 03/03/2015.
 */
public class Leclerc {


    private String pathBase = "/home/prod/projects/pige-crawler-catalogue2/web/uploads/data/";

    private EntityManagerFactory entityManagerFactory;
    private ScpClient scpClient = new ScpClient("94.23.63.229","FJKKnaVy68T5vtReSykF");
    private ExecutorService downloadTaskPool = Executors.newFixedThreadPool(5);

    protected final Logger log = Logger.getLogger(getClass());

    public Leclerc() {

        entityManagerFactory = Persistence.createEntityManagerFactory("org.hibernate.crawler.jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("browser.link.open_newwindow.restriction", 1);
        profile.setPreference("permissions.default.image", 2);

        WebDriver driver = new FirefoxDriver(profile);
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);

        List<String> l = Lists.newArrayList();
        try {
            Document doc = Jsoup.connect("http://www.e-leclerc.com/catalogue/magasins").get();
            l = doc.select("div.box-liste-magasins div.magasin a.button[href]").stream().map(docUrl -> docUrl.absUrl("href")).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Enseigne leclercTest = entityManager.find(Enseigne.class, 28);

        int i = 0;

        //get catelogue : http://nos-catalogues-promos.e-leclerc.com/Leclerc/shops/0201/config/config.xml?t=1424696075946

        try {
            for (String url : l) {

                //get the store's information
                driver.get(url + "/infos-magasins");
                if (!driver.getCurrentUrl().startsWith(url)) continue;
                final Magasin mag = new Magasin();
                List<Magasin> listM = entityManager.createNamedQuery("Magasin.findMagasinByUniqueId")
                        .setParameter("uniqueId", url.replace("^.*\\.com\\/", ""))
                        .setParameter("enseigne_id", leclercTest.getId()).getResultList();
                if (listM.size() > 0) mag.setMagasin(listM.get(0));

                log.info((i++) + "/" + l.size());
                log.info("uniqueIDDDDDDDDDDDDDDD:" + url);

                try {
                    mag.setUniqueId(url.replace("^.*magasin\\/", ""));
                    mag.setEnseigne_id(leclercTest.getId());

                    mag.setAdresse(driver.findElement(By.cssSelector("span[itemprop=\"streetAddress\"][ng-show=\"magasin.coordonnee.voie\"]")).getText());
                    mag.setCp(driver.findElement(By.cssSelector("span[itemprop=\"postalCode\"]")).getText());
                    mag.setTel(driver.findElement(By.cssSelector("span[itemprop=\"telephone\"]")).getText());
                    mag.setFax(driver.findElement(By.cssSelector("span[itemprop=\"faxNumber\"]")).getText());
                    mag.setLat(Double.parseDouble(driver.findElement(By.cssSelector("span[itemprop=\"latitude\"]")).getText()));
                    mag.setLog(Double.parseDouble(driver.findElement(By.cssSelector("span[itemprop=\"longitude\"]")).getText()));
                    mag.setVille(driver.findElement(By.cssSelector("span[itemprop=\"addressLocality\"]")).getText());
                    mag.setHoraire(driver.findElements(By.cssSelector("div[ng-if=\"horaire.horaireFormate\"]")).stream().map(t -> t.getText()).reduce("", (a, b) -> a + " " + b));
                    mag.setUrl(url);
                    mag.setLibelle(driver.findElement(By.cssSelector("div.title")).getText());
                    if (mag.getId() <= 0) entityManager.persist(mag);
                    else entityManager.merge(mag);
                    entityManager.flush();
                } catch (Exception e) {
                    continue;
                }

                //get the catalogues
                driver.get(url + "/promotions/Prospectus");
                String codeCata = driver.findElement(By.cssSelector("taggage")).getAttribute("data-xtn2");
                List<WebElement> ListE = driver.findElements(By.cssSelector("section[ng-if=\"prospectus\"] ul.liste-prospectus li.li-prospectus"));

                for (WebElement cataEle : ListE)
                    try {

                        final Catalogue cata = new Catalogue();
                        cata.setImages(cataEle.findElement(By.cssSelector("img")).getAttribute("src"));

                        String urlCata = cata.getImages().replaceAll("pages.*$", "catalog.xml");
                        Document doc = Jsoup.connect(urlCata).timeout(5000).get();
                        String uniqueID = doc.select("catalog").attr("operation_code_el")
                                + "-" + doc.select("catalog").attr("id");

                        log.info(uniqueID);
                        log.info(urlCata);

                        //charger les catalogues existants
                        List<Catalogue> listC = entityManager.createNamedQuery("Catalogue.findCatalogueByUniqueId")
                                .setParameter("uniqueId", uniqueID)
                                .setParameter("enseigne_id", leclercTest.getId()).getResultList();
                        if (listC.size() > 0) cata.setCatalogue(listC.get(0));

                        //MAJ le catalogue
                        cata.setLibelle(Jsoup.connect("http://nos-catalogues-promos.e-leclerc.com/Leclerc/" +
                                cataEle.findElement(By.cssSelector("a[data-codeop]")).getAttribute("data-codeop") + "/" +
                                codeCata).timeout(5000).get().select("div.cata_info").text().replaceFirst("(?s)Offre valable.*$", "").trim() + " - " +
                                cataEle.findElement(By.cssSelector("p.ng-binding")).getText());
                        cata.setEnseigne_id(leclercTest.getId());
                        if (cata.getId() <= 0) cata.setPages(doc.select("page[id],cover[id],backcover[id]").stream()
                                .map(img -> urlCata.replaceAll("catalog.xml", "pages/") + img.attr("zoom"))
                                .reduce("", (a, b) -> (a.length() == 0) ? b : a.concat(";").concat(b)));
                        cata.setUniqueId(uniqueID);
                        log.info("uniqueIDDDDDDDDDDDDDDD:" + uniqueID);

                        boolean isNewCata = getImagesCatalogue(cata);

                        if (cata.getId() <= 0) {
                            entityManager.persist(cata);
                            mag.getCatalogues().add(cata);
                        } else {
                            entityManager.merge(cata);
                            if (!mag.getCatalogues().stream().map(cat -> cat.getId() == cata.getId()).reduce(false, (a, b) -> (a || b)))
                                mag.getCatalogues().add(cata);
                        }
                        entityManager.flush();

                        //produits
                        if (isNewCata) doc.select("products[base] > product").stream().forEach(p -> {
                            Occurence o = new Occurence();
                            List<Occurence> listO = entityManager.createNamedQuery("Occurence.findOccurenceByUniqueId")
                                    .setParameter("uniqueId", p.select("product").attr("id"))
                                    .setParameter("catalogueId", cata.getId()).getResultList();
                            if (listO.size() > 0) o.setOccurence(listO.get(0));
                            o.setProductUniqueId(p.select("product").attr("id"));
                            o.setUniqueId(p.select("product").attr("id"));
                            o.setPage(p.select("product").attr("pageId"));
                            o.setDescriptionLong(p.select("description").text());
                            o.setLibelleShort(p.select("title").first().text());
                            if (p.select("prix").size() > 0)
                                o.setPriceAfterPromo(new BigDecimal(p.select("prix").text()).movePointLeft(2));
                            if (p.select("prix_hors_promotion").size() > 0)
                                o.setPriceAfterPromo(new BigDecimal(p.select("prix_hors_promotion").text()).movePointLeft(2));
                            o.setCatalogueId(cata.getId());
                            if (o.getId() <= 0) entityManager.persist(o);
                            else entityManager.merge(o);

                            log.info(o.getLibelleShort());

                            Productimage pImage = new Productimage();
                            List<Productimage> listP = entityManager.createNamedQuery("Productimage.findProductimageByUniqueId")
                                    .setParameter("productUniqueId", p.select("product").attr("id"))
                                    .setParameter("catalogueId", cata.getId()).getResultList();
                            if (listP.size() > 0) pImage.setProductimage(listP.get(0));
                            pImage.setCatalogueId(cata.getId());
                            pImage.setProductUniqueId(p.select("product").attr("id"));
                            if (pImage.getId() <= 0) {
                                pImage.setImages(urlCata.replaceAll("catalogs.*$", "") + "products/" +
                                        p.select("zoomimages").first().attr("base") + p.select("zoomimages url").first().text());
                                getImagesProducts(pImage, cata);
                            }
                            if (pImage.getId() <= 0) entityManager.persist(pImage);
                            else entityManager.merge(pImage);

                        });


                    } catch (Exception e) {
                        log.info(e);
                        continue;
                    }

                entityManager.merge(mag);
                entityManager.flush();


            }
            ;

        } catch (Exception e) {
            log.info(e);
        } finally {
            downloadTaskPool.shutdown();
            entityManager.getTransaction().commit();
            entityManager.close();
            driver.close();
            while (!downloadTaskPool.isTerminated()) ;
            scpClient.close();
        }




    }



    public void getImagesProducts(Productimage p,Catalogue cata){
        String pathCata = "ens_leclerc3/cat_" + cata.getUniqueId().replaceAll("[^a-zA-Z0-9]","-").toLowerCase();
        scpClient.mkdir(pathBase + pathCata + "/products");

        log.info(p.getImages());
        log.info(p.getProductUniqueId());

//        scpClient.scpUploadFromRemote(pathBase + pathCata + "/products", p.getImages(), "product-" + p.getProductUniqueId() + "-image-1.jpg");

        downloadTaskPool.execute(new ScpTask(scpClient.getSession(), pathBase + pathCata + "/products", p.getImages(), "product-" + p.getProductUniqueId() + "-image-1.jpg"));
        p.setImages(pathCata + "/products/product-" + p.getProductUniqueId() + "-image-1.jpg");

    }

    public boolean getImagesCatalogue(Catalogue cata) {
        String pathCata = "ens_leclerc3/cat_" + cata.getUniqueId().replaceAll("[^a-zA-Z0-9]","-").toLowerCase();
        if (scpClient.mkdir(pathBase + pathCata)) {
            downloadTaskPool.execute(new ScpTask(scpClient.getSession(),pathBase + pathCata, cata.getImages(), "cover.jpg"));

            cata.setImages(pathCata+"/cover.jpg");

            log.info(pathCata+"/cover.jpg");

//            Arrays.asList((cata.getPages().split(";"))).parallelStream().forEach(url ->
//                    scpClient.scpUploadFromRemote(pathBase + pathCata, url, "page" + url.replaceFirst("^.*zoom/","").replaceFirst("\\..*$","") + ".jpg"));
            Arrays.asList((cata.getPages().split(";"))).parallelStream().forEach(url ->
                    downloadTaskPool.execute(new ScpTask(scpClient.getSession(), pathBase + pathCata, url, "page" + url.replaceFirst("^.*zoom/", "").replaceFirst("\\..*$", "") + ".jpg")));


            String pages = "";
            for (int page=1;page<=(cata.getPages().split(";").length);page++) {
                pages += pathCata + "/page" + page + ".jpg;";
            }
            cata.setPages(pages.substring(0,pages.length()-1));
            return true;
        } else {
            return false;
        }

    }

    public static void main(String[] args) {
        new Leclerc();
    }

}
