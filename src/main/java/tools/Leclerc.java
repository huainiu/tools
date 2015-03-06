package tools;

import com.beust.jcommander.internal.Lists;
import model.Catalogue;
import model.Magasin;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jzhang on 03/03/2015.
 */
public class Leclerc {

    static{
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http", "warn");
    }

    private EntityManagerFactory entityManagerFactory;

    protected final Logger log = LoggerFactory.getLogger(getClass());

    public Leclerc() {

        entityManagerFactory = Persistence.createEntityManagerFactory("org.hibernate.crawler.jpa");


            WebDriver driver = new FirefoxDriver();
            driver.get("http://www.e-leclerc.com/magasin");

            List<String> catUrl = Lists.newArrayList();


        //Magasin
        //http://www.e-leclerc.com/eleclerc_srv_socle_magasin_int/public/v1/magasin_lire_code_panonceau.action?codePanonceau=1892&cachable=true
/*
            driver.findElements(By.cssSelector("div.liste_magasins div li"))
                    .stream().map(webElement -> {return webElement.findElement(By.cssSelector("div.liste_magasins ul a[href]")).getAttribute("href");})
                    .forEach(m -> {
                        log.info(m);
                    });
*/
        driver.get("http://www.e-leclerc.com/magasin/clichy/infospratiques");
        Magasin mag = new Magasin();
        mag.setAdresse(driver.findElement(By.cssSelector("form#formLongitudeLatitude input[name=\"adresse\"]")).getAttribute("value"));
        mag.setCp(driver.findElement(By.cssSelector("form#formLongitudeLatitude input[name=\"codePostal\"]")).getAttribute("value"));
        mag.setTel(driver.findElement(By.cssSelector("form#formLongitudeLatitude input[name=\"telephone\"]")).getAttribute("value"));
        mag.setLat(Double.parseDouble(driver.findElement(By.cssSelector("form#formLongitudeLatitude input[name=\"latitudeGPS\"]")).getAttribute("value")));
        mag.setLog(Double.parseDouble(driver.findElement(By.cssSelector("form#formLongitudeLatitude input[name=\"longitudeGPS\"]")).getAttribute("value")));
        mag.setVille(driver.findElement(By.cssSelector("form#formLongitudeLatitude input[name=\"ville\"]")).getAttribute("value"));
        mag.setHoraire(driver.findElement(By.cssSelector("ul.box_blue")).getText());

/*
        driver.get("http://www.e-leclerc.com/magasin/clichy");
        catUrl=driver.findElements(By.cssSelector("div.content_20569_cT_image.cT_image img[src]")).stream().map(img -> img.getAttribute("src")).map(url -> url.replaceAll("pages.*$", "catalog.xml")).collect(Collectors.toList());
//                catUrl.get(0).forEach(urlCata -> {

        driver.get(catUrl.get(0));

        //cata page
        Catalogue cata = new Catalogue();
        cata.setImages(driver.getCurrentUrl().replaceAll("catalog.xml", "pages/")
                + driver.findElement(By.cssSelector("cover[id]")).getAttribute("zoom"));
        cata.setPages(driver.findElements(By.cssSelector("page[id],cover[id],backcover[id]")).stream()
                .map(img -> driver.getCurrentUrl().replaceAll("catalog.xml", "pages/") + img.getAttribute("zoom")).reduce("", (a, b) -> a + ";" + b));

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(cata);
        entityManager.getTransaction().commit();
        entityManager.close();

        driver.close();

        */

        //product
//        driver.findElements(By.cssSelector("products[base] > product")).stream();

  //              });

        log.info("sssssssssssssssss"+catUrl.get(0));

    }

    public void chooseMagasin(){

    }
    public static void main(String[] args) {
        new Leclerc();
    }

}
