package tools;

import com.beust.jcommander.internal.Lists;
import model.Catalogue;
import model.Enseigne;
import model.Magasin;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by jzhang on 03/03/2015.
 */
public class Leclerc {


    private EntityManagerFactory entityManagerFactory;

    protected final Logger log = Logger.getLogger(getClass());

    public Leclerc() {

        entityManagerFactory = Persistence.createEntityManagerFactory("org.hibernate.crawler.jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("browser.link.open_newwindow.restriction", 1);
        profile.setPreference("permissions.default.image", 2);


        WebDriver driver = new FirefoxDriver(profile);
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
//        DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
//        WebDriver driver = new PhantomJSDriver(capabilities);


        driver.get("http://www.e-leclerc.com/catalogue/magasins");

            List<String> catUrl = Lists.newArrayList();


        //Magasin
        //http://www.e-leclerc.com/eleclerc_srv_socle_magasin_int/public/v1/magasin_lire_code_panonceau.action?codePanonceau=1892&cachable=true


        List<String> l = driver.findElements(By.cssSelector("div.box-liste-magasins div.magasin")).parallelStream()
                    .map(webElement -> webElement.findElement(By.cssSelector("div.magasin  a.button[href]")).getAttribute("href")).collect(Collectors.toList());

//        List<String> l = Lists.newArrayList();
//        l.add(driver.findElement(By.cssSelector("div.box-liste-magasins div.magasin")).findElement(By.cssSelector("div.magasin  a.button[href]")).getAttribute("href"));

//        l.clear();
//        l.add("http://www.e-leclerc.com/landerneau");
        Enseigne leclercTest = entityManager.find(Enseigne.class, 11);

        int i = 0;

        for (String url : l) {
            //get the store's information
            driver.get(url + "/infos-magasins");
            if (!driver.getCurrentUrl().startsWith(url)) continue;
            final Magasin mag = new Magasin();
            List<Magasin> listM = entityManager.createNamedQuery("Magasin.findMagasinByUniqueId")
                    .setParameter("uniqueId", url.replace("^.*\\.com\\/", ""))
                    .setParameter("enseigne_id",leclercTest.getId()).getResultList();
            if (listM.size()>0) mag.setMagasin(listM.get(0));

            log.info((i++)+"/"+l.size());
            log.info("uniqueIDDDDDDDDDDDDDDD:"+url);

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
            List<WebElement> ListE = driver.findElements(By.cssSelector("section[ng-if=\"prospectus\"] ul.liste-prospectus li.li-prospectus"));

            for (WebElement cataEle:ListE) {

                WebDriver driverTmp = new FirefoxDriver();
                try {

                    final Catalogue cata = new Catalogue();
                    driverTmp.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);

                    cata.setImages(cataEle.findElement(By.cssSelector("img")).getAttribute("src"));
                    driverTmp.get(cata.getImages().replaceAll("pages.*$", "catalog.xml"));
                    String uniqueID = driverTmp.findElement(By.cssSelector("catalog")).getAttribute("operation_code_el")
                            + "-" + driverTmp.findElement(By.cssSelector("catalog")).getAttribute("id");

                    //charger les catalogues existants
                    List<Catalogue> listC = entityManager.createNamedQuery("Catalogue.findCatalogueByUniqueId")
                            .setParameter("uniqueId", uniqueID)
                            .setParameter("enseigne_id", leclercTest.getId()).getResultList();
                    if (listC.size() > 0) cata.setCatalogue(listC.get(0));

                    //MAJ le catalogue
                    cata.setLibelle(cataEle.findElement(By.cssSelector("p.ng-binding")).getText());
                    cata.setEnseigne_id(leclercTest.getId());
                    cata.setPages(driverTmp.findElements(By.cssSelector("page[id],cover[id],backcover[id]")).stream()
                            .map(img -> driverTmp.getCurrentUrl().replaceAll("catalog.xml", "pages/") + img.getAttribute("zoom"))
                            .reduce("", (a, b) -> (a.length() == 0) ? b : a.concat(";").concat(b)));
                    cata.setUniqueId(uniqueID);
                    log.info("uniqueIDDDDDDDDDDDDDDD:" + uniqueID);

                    if (cata.getId() <= 0) {
                        entityManager.persist(cata);
                        mag.getCatalogues().add(cata);
                    } else {
                        entityManager.merge(cata);
                        if (!mag.getCatalogues().stream().map(cat -> cat.getId() == cata.getId()).reduce(false, (a, b) -> (a || b)))
                            mag.getCatalogues().add(cata);
                    }
                    entityManager.flush();
                } catch (Exception e) {
                    continue;
                } finally {
                    driverTmp.close();
                }


            };
            entityManager.merge(mag);
            entityManager.flush();


        };





//        driver.get("http://www.e-leclerc.com/magasin/clichy");
//        catUrl=driver.findElements(By.cssSelector("div.content_20569_cT_image.cT_image img[src]")).stream().map(img -> img.getAttribute("src")).map(url -> url.replaceAll("pages.*$", "catalog.xml")).collect(Collectors.toList());
//                catUrl.get(0).forEach(urlCata -> {

//        driver.get(catUrl.get(0));

        //cata page
/*        Catalogue cata = new Catalogue();
        cata.setImages(driver.getCurrentUrl().replaceAll("catalog.xml", "pages/")
                + driver.findElement(By.cssSelector("cover[id]")).getAttribute("zoom"));
        cata.setPages(driver.findElements(By.cssSelector("page[id],cover[id],backcover[id]")).stream()
                .map(img -> driver.getCurrentUrl().replaceAll("catalog.xml", "pages/") + img.getAttribute("zoom")).reduce("", (a, b) -> (a.length()==0)?b:a.concat(";").concat(b)));
*/


        entityManager.getTransaction().commit();
        entityManager.close();

        driver.close();


        //product
//        driver.findElements(By.cssSelector("products[base] > product")).stream();

  //              });

//        log.info("sssssssssssssssss"+catUrl.get(0));

    }

    public void chooseMagasin(){

    }
    public static void main(String[] args) {
        new Leclerc();
    }

}
