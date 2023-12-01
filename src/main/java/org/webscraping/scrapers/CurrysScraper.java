package org.webscraping.scrapers;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.webscraping.ProductDao;

import java.util.ArrayList;
import java.util.List;

public class CurrysScraper extends Thread{

    public ProductDao productDao;

    public  CurrysScraper (ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public void run() {
        FirefoxOptions options = new FirefoxOptions();
        System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver");

        /*Set Headless mode */
        options.setHeadless(true);

        WebDriver driver = new FirefoxDriver(options);


        driver.get("https://www.currys.co.uk/tv-and-audio/headphones/headphones/wireless-earbuds?pmin=60.0&pmax=499.0");

        try {
            Thread.sleep(1000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        List<WebElement> earbudsList = driver.findElements(By.xpath("//div[@class='pdp-link click-beacon']//child::a"));
        List<String> earbudsUrl = new ArrayList<>();

        for (WebElement earbuds : earbudsList) {
            earbudsUrl.add(earbuds.getAttribute("href"));
        }

        for (String earbudUrl : earbudsUrl) {
            WebDriver pageDriver = new FirefoxDriver(options);
            pageDriver.get(earbudUrl);
            try {
                Thread.sleep(1000);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            String imageUrl = pageDriver.findElement(By.xpath("//img[@itemprop='image']"))
                    .getAttribute("src");


            String[] splittedArray = pageDriver.findElement(By.xpath("//h1[@class='product-name']"))
                    .getAttribute("innerText").split(" - ");

            String name = splittedArray[0];

            String color = splittedArray[splittedArray.length - 1];

            String[] brandArray = pageDriver.findElement(By.xpath("//h1[@class='product-name']"))
                    .getText().split(" ");

            String brand = brandArray[0].toUpperCase();

            String priceString = pageDriver.findElement(By.xpath("//div[@class='prices']//descendant::span[@class='value']"))
                    .getAttribute("content");
            Float price = Float.valueOf(priceString);

            System.out.println("Name: "+name);
            System.out.println("Price: "+price);
            System.out.println("Image: "+imageUrl);
            System.out.println("Color: "+color);
            System.out.println("Brand: "+brand);
            pageDriver.quit();

        }
        System.out.println(earbudsList.size());
        driver.quit();
    }
}
