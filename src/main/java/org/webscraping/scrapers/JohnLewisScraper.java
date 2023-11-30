package org.webscraping.scrapers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.List;

public class AmazonScraper {


    public void scrape() {
        FirefoxOptions options = new FirefoxOptions();
        System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver");
        options.setHeadless(true);

        // Creating an instance of the web driver
        WebDriver driver = new FirefoxDriver(options);

        driver.get("https://www.amazon.co.uk/ear-buds/s?k=ear+buds");

        try {
            Thread.sleep(3000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }



//        List<WebElement> earbudsList = driver.findElements(By.className("s-result-list"));
        List<WebElement> earbudsList = driver.findElements(By.xpath("//body/div[@id='a-page']/div[@id='search']/div[@class='s-desktop-width-max s-desktop-content s-wide-grid-style-t1 s-opposite-dir s-wide-grid-style sg-row']/div[@class='sg-col-20-of-24 s-matching-dir sg-col-16-of-20 sg-col sg-col-8-of-12 sg-col-12-of-16']/div[@class='sg-col-inner']/span[@class='rush-component s-latency-cf-section']/div[@class='s-main-slot s-result-list s-search-results sg-row']/div"));
        for ( WebElement earbuds : earbudsList) {
            System.out.println(earbuds.getText());
        }

        driver.quit();
    }
}
