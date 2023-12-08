package org.webscraping.scrapers;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.webscraping.ProductDao;
import org.webscraping.entities.Comparison;
import org.webscraping.entities.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * CurrysScraper is a web scraper for extracting product information from the Currys website.
 * It extends the Thread class to allow concurrent scraping of data.
 */
public class CurrysScraper extends Thread{

    /**
     * The provider for obtaining WebDriver instances.
     */
    private final FirefoxWebDriverProvider webDriverProvider;

    /**
     * The DAO (Data Access Object) responsible for saving and merging product data.
     */
    public ProductDao productDao;


    /**
     * Constructs CurrysScraper with the specified WebDriverProvider.
     *
     * @param webDriverProvider The provider for obtaining WebDriver instances.
     */
    public CurrysScraper(FirefoxWebDriverProvider webDriverProvider) {
        this.webDriverProvider = webDriverProvider;
    }


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

        // Using webDriverProvider to get the WebDriver instance
        WebDriver driver = webDriverProvider.getWebDriver();


//        driver.get("https://www.currys.co.uk/tv-and-audio/headphones/headphones/wireless-earbuds?pmin=60.0&pmax=499.0");
            driver.get("https://www.currys.co.uk/tv-and-audio/headphones/headphones/in-ear-headphones?searchTerm=earphones");

        try {
            // Adding a delay to allow the page to load
            Thread.sleep(1000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Extracting product URLs from the page
        List<WebElement> earbudsList = driver.findElements(By.xpath("//div[@class='pdp-link click-beacon']//child::a"));
        List<String> earbudsUrl = new ArrayList<>();

        for (WebElement earbuds : earbudsList) {
            earbudsUrl.add(earbuds.getAttribute("href"));
        }


        // Looping through each product URL
        for (String earbudUrl : earbudsUrl) {

            // Using webDriverProvider to get the WebDriver instance
            WebDriver pageDriver = webDriverProvider.getWebDriver();
            pageDriver.get(earbudUrl);


            try {
                // Adding a delay to allow the product page to load
                Thread.sleep(1000);
            } catch (Exception ex) {
                ex.printStackTrace();
            }


            // Extracting product details from the product page
            String imageUrl = pageDriver.findElement(By.xpath("//img[@itemprop='image']"))
                    .getAttribute("src");


            String[] splittedArray = pageDriver.findElement(By.xpath("//h1[@class='product-name']"))
                    .getAttribute("innerText").split(" - ");

            String name = splittedArray[0];

            String color = splittedArray[splittedArray.length - 1];

            String[] brandArray = pageDriver.findElement(By.xpath("//h1[@class='product-name']"))
                    .getText().split(" ");

            String description = name+" ";

            String brand = brandArray[0].toUpperCase();

            String priceString = pageDriver.findElement(By.xpath("//div[@class='prices']//descendant::span[@class='value']"))
                    .getAttribute("content");

            Float price = Float.valueOf(priceString);


            // Displaying extracted information
            System.out.println("Name: "+name);
            System.out.println("Price: "+price);
            System.out.println("Image: "+imageUrl);
            System.out.println("Color: "+color);
            System.out.println("Brand: "+brand);

            // Creating Product object
            Product product = new Product();
            product.setName(name);
            product.setImageUrl(imageUrl);
            product.setBrand(brand);
            product.setDescription(description);

            // Creating Comparison object
            Comparison comparison = new Comparison();
            comparison.setName("Currys");
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
    }
}
