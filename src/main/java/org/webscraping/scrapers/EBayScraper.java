package org.webscraping.scrapers;

import org.hibernate.SessionFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.ArrayList;
import java.util.List;

public class EBayScraper {


    public SessionFactory sessionFactory;

    public  EBayScraper (SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    public void scrape() throws InterruptedException {
        FirefoxOptions options = new FirefoxOptions();
        System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver");
        options.setHeadless(true);

        // Creating an instance of the web driver
        WebDriver driver = new FirefoxDriver(options);


        driver.get("https://www.ebay.co.uk/sch/i.html?_from=R40&_nkw=earbuds+bluetooth&_sacat=0&LH_TitleDesc=0&rt=nc&LH_PrefLoc=1");


        try {
            Thread.sleep(3000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        List<WebElement> earbudsList = driver.findElements(By.xpath("//ul[@class='srp-results srp-list clearfix']//child::li//descendant::a[@class='s-item__link']"));


        List<String> earbudsUrl = new ArrayList<>();

        for (WebElement earbuds : earbudsList) {
            earbudsUrl.add(earbuds.getAttribute("href"));
            System.out.println(earbudsUrl);
        }


//        System.out.println(earbudsList.size());

        driver.quit();
    }
}
