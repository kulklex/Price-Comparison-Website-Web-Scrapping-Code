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

public class EBayScraper extends Thread{


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


            driver.get("https://www.ebay.co.uk/sch/i.html?_from=R40&_nkw=earbuds+bluetooth+wireless&_sacat=0&LH_TitleDesc=0&Brand=Sony%7CApple%7CJBL%7CSamsung%7CTws&LH_BIN=1&_dcat=112529&LH_PrefLoc=1&_sop=16&imm=1&_pgn="+page);


            try {
                Thread.sleep(3000);
            } catch (Exception ex) {
                ex.printStackTrace();
            }


            List<WebElement> earbudsList = driver.findElements(By.xpath("//ul[@class='srp-results srp-list clearfix']//child::li//descendant::a[@class='s-item__link']"));

            if (earbudsList.isEmpty() || page >= 4) {
                break;
            }

            List<String> earbudsUrl = new ArrayList<>();

            for (WebElement earbuds : earbudsList) {
                earbudsUrl.add(earbuds.getAttribute("href"));
            }

            for(String earbudUrl : earbudsUrl) {
                WebDriver pageDriver = new FirefoxDriver(options);
                pageDriver.get(earbudUrl);

                try {
                    Thread.sleep(3000);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                String imageUrl = pageDriver.findElement(By.xpath("//img[@class='ux-image-magnify__image--original']")).getAttribute("src");


                String dataToSplit = pageDriver.findElement(By.xpath("//span[@class='ux-textspans ux-textspans--BOLD']")).getText();

                String[] nameArray = dataToSplit.split("-");
                String name = nameArray[0];

                String description = pageDriver.findElement(By.xpath("//span[@class='ux-textspans ux-textspans--BOLD']")).getText();

                String priceString = pageDriver.findElement(By.xpath("//div[@class='x-price-primary']//child::span"))
                        .getText().split(" ")[0].substring(1);

                Float price = Float.valueOf(priceString);

                String[] brandArray = dataToSplit.split(" ");

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
                comparison.setName("EBay");
                comparison.setUrl(earbudUrl);
                comparison.setPrice(price);
                comparison.setProduct(product);

                try {
                    productDao.saveAndMerge(comparison);
                } catch (Exception ex) {
                    System.out.println("Unable to save product");
                    ex.printStackTrace();
                }


                pageDriver.quit();
            }

            driver.quit();
            page++;
        } while (true);
    }
}
