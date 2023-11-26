package org.webscraping.scrapers;

import org.hibernate.SessionFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.ArrayList;
import java.util.List;

public class ArgosScraper {

    public SessionFactory sessionFactory;

    public  ArgosScraper (SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    public void scrape() throws InterruptedException {
        FirefoxOptions options = new FirefoxOptions();
        System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver");
        options.setHeadless(false);

        // Creating an instance of the web driver
        WebDriver driver = new FirefoxDriver(options);


        driver.get("https://www.argos.co.uk/search/earbuds/?clickOrigin=searchbar:home:auto:earbuds:earbuds");


        try {
            Thread.sleep(3000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        List<WebElement> earbudsList = driver.findElements(By.xpath(""));
        List<String> earbudsUrl = new ArrayList<>();

        for (WebElement earbuds : earbudsList) {
            earbudsUrl.add(earbuds.getAttribute("href"));
        }
    }
}
