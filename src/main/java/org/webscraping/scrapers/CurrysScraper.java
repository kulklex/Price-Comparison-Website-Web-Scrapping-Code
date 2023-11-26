package org.webscraping.scrapers;

import org.hibernate.SessionFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.ArrayList;
import java.util.List;

public class CurrysScraper {

    public SessionFactory sessionFactory;

    public  CurrysScraper (SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    public void scrape() {
        FirefoxOptions options = new FirefoxOptions();
        System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver");
        options.setHeadless(true);

        // Creating an instance of the web driver
        WebDriver driver = new FirefoxDriver(options);

        // creating a pause instance
//        WebDriverWait pause = new WebDriverWait(driver, 45);


        driver.get("https://www.currys.co.uk/tv-and-audio/headphones/headphones/in-ear-headphones?searchTerm=earbuds");
//        pause.until(ExpectedConditions.elementToBeClickable(By.xpath("")));

        try {
            Thread.sleep(3000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        List<WebElement> earbudsList = driver.findElements(By.xpath("//div[@class='pdp-link click-beacon']//child::a"));
        List<String> earbudsUrl = new ArrayList<>();

        for (WebElement earbuds : earbudsList) {
            earbudsUrl.add(earbuds.getAttribute("href"));
        }

        for (String earbudUrl : earbudsUrl) {
            driver.navigate().to(earbudUrl);

            String imageUrl = driver.findElement(By.xpath("//*[contains(concat( \" \", @class, \" \" ), concat( \" \", \"img-fluid\", \" \" ))]"))
                    .getAttribute("src");
            System.out.println(imageUrl);
        }


        driver.quit();
    }
}
