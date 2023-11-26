package org.webscraping.scrapers;

import org.hibernate.SessionFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.ArrayList;
import java.util.List;

public class BackMarketScraper {

    public SessionFactory sessionFactory;

    public  BackMarketScraper (SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void scrape() throws InterruptedException {
        FirefoxOptions options = new FirefoxOptions();
        System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver");
        options.setHeadless(true);

        // Creating an instance of the web driver
        WebDriver driver = new FirefoxDriver(options);

        // creating a pause instance
//        WebDriverWait pause = new WebDriverWait(driver, 45);


        driver.get("https://www.backmarket.co.uk/en-gb/search?q=earbuds");
//        pause.until(ExpectedConditions.elementToBeClickable(By.xpath("")));

        try {
            Thread.sleep(10000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        List<WebElement> earbudsList = driver.findElements(By.xpath("//div[@class='productCard']//child::a"));
        List<String> earbudsUrl = new ArrayList<>();

        for ( WebElement earbuds : earbudsList) {
            earbudsUrl.add(earbuds.getAttribute("href"));
        }

//        System.out.println(earbudsUrl.size());

        for (String earbudUrl: earbudsUrl) {
//            System.out.println(earbudUrl);
            driver.navigate().to(earbudUrl);

            String imageUrl = driver.findElement(By.xpath("//li[@class='list-none w-full flex justify-center focus:outline-none']//descendant::img"))
                    .getAttribute("src");
//            System.out.println(imageUrl);

            String name = driver.findElement(By.xpath("//h1[@class='title-1 lg:max-w-[38rem]']"))
                    .getAttribute("innerText").split(" -")[0];
            System.out.println(name);

            String price = driver.findElement(By.xpath("//div[@data-test='normal-price']"))
                    .getAttribute("innerText");
//            System.out.println(price);

            String description = driver.findElement(By.xpath("//h1[@class='title-1 lg:max-w-[38rem]']"))
                    .getAttribute("innerText");
//            System.out.println(description);

            String brand = driver.findElement(By.xpath("//h1[@class='title-1 lg:max-w-[38rem]']"))
                    .getAttribute("innerText").split(" ")[0];
//            System.out.println(brand);


            String color = driver.findElement(By.xpath("//h1[@class='title-1 lg:max-w-[38rem]']"))
                    .getAttribute("innerText").split(" -")[1];
//            System.out.println(color);

//            String size =

            Thread.sleep(5000);
        }

        driver.quit();
    }
}
