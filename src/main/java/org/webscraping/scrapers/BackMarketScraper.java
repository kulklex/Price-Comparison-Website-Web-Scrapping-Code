package org.webscraping.scrapers;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.webscraping.ProductDao;
import org.webscraping.entities.Comparison;
import org.webscraping.entities.Product;
import org.webscraping.entities.Variants;

import java.util.ArrayList;
import java.util.List;

public class BackMarketScraper extends Thread{

    public ProductDao productDao;

    public  BackMarketScraper (ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public void run() {
        FirefoxOptions options = new FirefoxOptions();
        System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver");
        options.setHeadless(true);

        int page = 1;

       do {
           WebDriver driver = new FirefoxDriver(options);


           driver.get("https://www.backmarket.co.uk/en-gb/search?q=earphones&page="+page);

           try {
               Thread.sleep(1000);
           } catch (Exception ex) {
               ex.printStackTrace();
           }


           List<WebElement> earbudsList = driver.findElements(By.xpath("//div[@class='productCard']//child::a"));

           if (earbudsList.isEmpty()) {
               break;
           }

           List<String> earbudsUrl = new ArrayList<>();

           for ( WebElement earbuds : earbudsList) {
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

               String imageUrl = pageDriver.findElement(By.xpath("//li[@class='list-none w-full flex justify-center focus:outline-none']//descendant::img"))
                       .getAttribute("src");

               String dataToSplit = pageDriver.findElement(By.xpath("//h1[@class='title-1 lg:max-w-[38rem]']"))
                       .getAttribute("innerText");

               String[] nameArray = dataToSplit.split("-");

               String name = nameArray[0];

               String priceString = pageDriver.findElement(By.xpath("//div[@data-test='normal-price']"))
                       .getAttribute("innerText").substring(1);

               Float price = Float.valueOf(priceString);


               String description = pageDriver.findElement(By.xpath("//h1[@class='title-1 lg:max-w-[38rem]']"))
                       .getAttribute("innerText");

               String[] brandArray = dataToSplit.split(" ");

               String brand = brandArray[0].toUpperCase();

               String color = brandArray[brandArray.length - 1];

               System.out.println("Name: "+name);
               System.out.println("Price: "+price);
               System.out.println("Image: "+imageUrl);
               System.out.println("Description: "+description);
               System.out.println("Brand: "+brand);
               System.out.println("Color: "+color);


               Product product = new Product();
               product.setName(name);
               product.setImageUrl(imageUrl);
               product.setBrand(brand);
               product.setDescription(description);


               Variants variants = new Variants();
               variants.setProduct(product);
               variants.setColor(color);

               Comparison comparison = new Comparison();
               comparison.setUrl(earbudUrl);
               comparison.setPrice(price);
               comparison.setVariant(variants);

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
