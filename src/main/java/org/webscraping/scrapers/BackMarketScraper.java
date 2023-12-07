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


/**
 * BackMarketScraper is a web scraper for extracting product information from the Back Market website.
 * It extends the Thread class to allow concurrent scraping of data.
 */
public class BackMarketScraper extends Thread{

    /**
     * The DAO (Data Access Object) responsible for saving and merging product data.
     */
    public ProductDao productDao;

    /**
     * Sets the ProductDao for this scraper to use.
     *
     * @param productDao The ProductDao instance to be set.
     */
    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    /**
     * Overrides the run method of the Thread class to execute the web scraping logic.
     */
    @Override
    public void run() {
        FirefoxOptions options = new FirefoxOptions();
        System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver");

        /*Set Headless mode */
        options.setHeadless(true);

        int page = 1;

       do {
           // Creating an instance of the web driver
           WebDriver driver = new FirefoxDriver(options);

           // Navigating to the Back Market search page
           driver.get("https://www.backmarket.co.uk/en-gb/search?q=earphones&page="+page);

           try {
               // Adding a delay to allow the page to load
               Thread.sleep(1000);
           } catch (Exception ex) {
               ex.printStackTrace();
           }

           // Extracting product URLs from the page
           List<WebElement> earbudsList = driver.findElements(By.xpath("//div[@class='productCard']//child::a"));

           if (earbudsList.isEmpty()) {
               break;
           }

           List<String> earbudsUrl = new ArrayList<>();

           for ( WebElement earbuds : earbudsList) {
               earbudsUrl.add(earbuds.getAttribute("href"));
           }

           // Looping through each product URL
           for (String earbudUrl: earbudsUrl) {
               WebDriver pageDriver = new FirefoxDriver(options);
               pageDriver.get(earbudUrl);

               try {
                   // Adding a delay to allow the page to load
                   Thread.sleep(1000);
               } catch (Exception ex) {
                   ex.printStackTrace();
               }


               // Extracting product details from the product page
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


               // Displaying extracted information
               System.out.println("Name: "+name);
               System.out.println("Price: "+price);
               System.out.println("Image: "+imageUrl);
               System.out.println("Description: "+description);
               System.out.println("Brand: "+brand);
               System.out.println("Color: "+color);

               // Creating Product object
               Product product = new Product();
               product.setName(name);
               product.setImageUrl(imageUrl);
               product.setBrand(brand);
               product.setDescription(description);

               // Creating Comparison object
               Comparison comparison = new Comparison();
               comparison.setName("Back Market");
               comparison.setUrl(earbudUrl);
               comparison.setPrice(price);
               comparison.setProduct(product);


               // Saving the Comparison object using the provided ProductDao
               try {
                   productDao.saveAndMerge(comparison);
               } catch (Exception ex) {
                   System.out.println("Unable to save product");
                   ex.printStackTrace();
               }
               // Closing the web driver for the product page
               pageDriver.quit();
           }
           // Closing the web driver for the main page
           driver.quit();

           //increase the page to be scraped
           page++;
       } while (true);
    }
}
