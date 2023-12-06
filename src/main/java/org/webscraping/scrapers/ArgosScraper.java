package org.webscraping.scrapers;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.webscraping.ProductDao;
import org.webscraping.entities.Comparison;
import org.webscraping.entities.Product;

import java.util.ArrayList;
import java.util.List;

public class ArgosScraper extends Thread {

    public ProductDao productDao;

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public void run() {
        FirefoxOptions options = new FirefoxOptions();
        System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver");
        options.setHeadless(true);


        int page = 1;
        do {
            // Creating an instance of the web driver
            WebDriver driver = new FirefoxDriver(options);
            driver.get("https://www.argos.co.uk/list/great-prices-on-selected-headphones/opt/page:"+page);


            try {
                Thread.sleep(1000);
            } catch (Exception ex) {
                ex.printStackTrace();
            }


            List<WebElement> earbudsList = driver.findElements(By.className("ProductCardstyles__Link-h52kot-13"));

            if (earbudsList.isEmpty()) {
                break;
            }

            List<String> earbudsUrl = new ArrayList<>();

            for (WebElement earbuds : earbudsList) {
                earbudsUrl.add(earbuds.getAttribute("href"));
            }
            for (String earbudUrl: earbudsUrl) {
                WebDriver pageDriver = new FirefoxDriver(options);
                pageDriver.get(earbudUrl);
                try {
                    Thread.sleep(1000);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }


                try {
                    String name = pageDriver.findElement(By.className("Namestyles__Main-sc-269llv-1"))
                            .findElement(By.tagName("span")).getText();

                    Float price = Float.valueOf(pageDriver.findElement(By.className("Pricestyles__Li-sc-1oev7i-0"))
                            .getAttribute("content"));

                    String description = pageDriver.findElement(By.className("product-description-content-text"))
                            .getAttribute("innerHTML");

                    String imageUrl = "https:" + pageDriver.findElement(By.className("MediaGallerystyles__ImageWrapper-sc-1jwueuh-2")).findElement(By.tagName("source"))
                            .getAttribute("srcset");

                    String[] brandArray = name.split(" ");

                    String brand = brandArray[0].toUpperCase();

                    System.out.println("Name: "+name);
                    System.out.println("Price: "+price);
                    System.out.println("Image: "+imageUrl);
                    System.out.println("Description: "+description);
                    System.out.println("Brand: "+brand);


                    Product product = new Product();
                    product.setName(name);
                    product.setImageUrl(imageUrl);
                    product.setBrand(brand);
                    product.setDescription(description);



                    Comparison comparison = new Comparison();
                    comparison.setName("Argos");
                    comparison.setUrl(earbudUrl);
                    comparison.setPrice(price);
                    comparison.setProduct(product);

                    try {
                        productDao.saveAndMerge(comparison);
                    } catch (Exception ex) {
                        System.out.println("Unable to save product");
                        ex.printStackTrace();
                    }


                } catch (Exception ex) {
                    System.out.println("Argos Scraper Broke");
                    continue;
                }
                pageDriver.quit();
            }
            driver.quit();
            page++;
        } while (true);
    }
}
