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
 * ArgosScraper is a web scraper for extracting product information from the Argos website.
 * It extends the Thread class to allow concurrent scraping of data.
 */
public class ArgosScraper extends Thread {

    /**
     * The provider for obtaining WebDriver instances.
     */
    private final FirefoxWebDriverProvider webDriverProvider;

    /**
     * The DAO (Data Access Object) responsible for saving and merging product data.
     */
    public ProductDao productDao;


    /**
     * Constructs ArgosScraper with the specified WebDriverProvider.
     *
     * @param webDriverProvider The provider for obtaining WebDriver instances.
     */
    public ArgosScraper(FirefoxWebDriverProvider webDriverProvider) {
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



        int page = 2;
        do {
            // Using webDriverProvider to get the WebDriver instance
            WebDriver driver = webDriverProvider.getWebDriver();
            driver.get("https://www.argos.co.uk/list/great-prices-on-selected-headphones/opt/page:"+page);


            try {
                // Adding a delay to allow the page to load
                Thread.sleep(1000);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            System.out.println("Entering page: "+page);
            // Extracting product URLs from the page
            List<WebElement> earbudsList = driver.findElements(By.className("ProductCardstyles__Link-h52kot-13"));


            //Break if the new page no longer contains data
            if (earbudsList.isEmpty()) {
                break;
            }

            List<String> earbudsUrl = new ArrayList<>();

            for (WebElement earbuds : earbudsList) {
                earbudsUrl.add(earbuds.getAttribute("href"));
            }

            // Looping through each product URL
            for (String earbudUrl: earbudsUrl) {
                // Using webDriverProvider to get the WebDriver instance
                WebDriver pageDriver = webDriverProvider.getWebDriver();
                pageDriver.get(earbudUrl);
                try {
                    // Adding a delay to allow the page to load
                    Thread.sleep(1000);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }


                try {
                    String[] nameArray = pageDriver.findElement(By.className("Namestyles__Main-sc-269llv-1"))
                            .findElement(By.tagName("span")).getText().split(" - ");
                    String name = nameArray[0];

                    Float price = Float.valueOf(pageDriver.findElement(By.className("Pricestyles__Li-sc-1oev7i-0"))
                            .getAttribute("content"));

                    String description = pageDriver.findElement(By.className("product-description-content-text"))
                            .getAttribute("innerHTML");

                    String imageUrl = "https:" + pageDriver.findElement(By.className("MediaGallerystyles__ImageWrapper-sc-1jwueuh-2")).findElement(By.tagName("source"))
                            .getAttribute("srcset");

                    String[] brandArray = name.split(" ");

                    String brand = brandArray[0].toUpperCase();

                    // Displaying extracted information
                    System.out.println("Name: "+name);
                    System.out.println("Price: "+price);
                    System.out.println("Image: "+imageUrl);
                    System.out.println("Description: "+description);
                    System.out.println("Brand: "+brand);

                    // Creating Product object
                    Product product = new Product();
                    product.setName(name);
                    product.setImageUrl(imageUrl);
                    product.setBrand(brand);
                    product.setDescription(description);


                    // Creating Comparison object
                    Comparison comparison = new Comparison();
                    comparison.setName("Argos");
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


                } catch (Exception ex) {
                    System.out.println("Argos Scraper Broke");
                    continue;
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
