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
 * EBayScraper is a web scraper for extracting product information from eBay.
 * It extends the Thread class to allow concurrent scraping of data.
 */
public class EBayScraper extends Thread{

    /**
     * The provider for obtaining WebDriver instances.
     */
    private final FirefoxWebDriverProvider webDriverProvider;

    /**
     * The DAO (Data Access Object) responsible for saving and merging product data.
     */
    public ProductDao productDao;

    /**
     * Constructs EBayScraper with the specified WebDriverProvider.
     *
     * @param webDriverProvider The provider for obtaining WebDriver instances.
     */
    public EBayScraper(FirefoxWebDriverProvider webDriverProvider) {
        this.webDriverProvider =  webDriverProvider;
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

        int page = 1;

        do {
            // Using webDriverProvider to get the WebDriver instance
            WebDriver driver = webDriverProvider.getWebDriver();

            // Navigating to the eBay search page for wireless earbuds
            driver.get("https://www.ebay.co.uk/sch/i.html?_from=R40&_nkw=earbuds+bluetooth+wireless&_sacat=0&LH_TitleDesc=0&Brand=Sony%7CApple%7CJBL%7CSamsung%7CTws&LH_BIN=1&_dcat=112529&LH_PrefLoc=1&_sop=16&imm=1&_pgn="+page);


            try {
                // Adding a delay to allow the page to load
                Thread.sleep(3000);
            } catch (Exception ex) {
                ex.printStackTrace();
            }


            // Extracting product URLs from the page
            List<WebElement> earbudsList = driver.findElements(By.xpath("//ul[@class='srp-results srp-list clearfix']//child::li//descendant::a[@class='s-item__link']"));

            //Break if the new page no longer contains data OR if page number exceeds 3
            if (earbudsList.isEmpty() || page >= 4) {
                break;
            }

            List<String> earbudsUrl = new ArrayList<>();

            for (WebElement earbuds : earbudsList) {
                earbudsUrl.add(earbuds.getAttribute("href"));
            }

            // Looping through each product URL
            for(String earbudUrl : earbudsUrl) {
                WebDriver pageDriver = webDriverProvider.getWebDriver();
                pageDriver.get(earbudUrl);

                try {
                    // Adding a delay to allow the product page to load
                    Thread.sleep(3000);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                try {
                    // Extracting product details from the product page
                    String imageUrl = pageDriver.findElement(By.xpath("//img[@class='ux-image-magnify__image--original']")).getAttribute("src");

                    String dataToSplit = pageDriver.findElement(By.xpath("//span[@class='ux-textspans ux-textspans--BOLD']")).getText();

                    String[] nameArray = dataToSplit.split(" - ");
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


                    // Displaying extracted information
                    Product product = new Product();
                    product.setName(name);
                    product.setImageUrl(imageUrl);
                    product.setBrand(brand);
                    product.setDescription(description);


                    // Creating Product and Comparison objects
                    Comparison comparison = new Comparison();
                    comparison.setName("EBay");
                    comparison.setUrl(earbudUrl);
                    comparison.setPrice(price);
                    comparison.setProduct(product);

                    // Saving the Comparison object
                    try {
                        productDao.saveAndMerge(comparison);
                    } catch (Exception ex) {
                        System.out.println("Unable to save product");
                        ex.printStackTrace();
                    }
                } catch (Exception ex) {
                    System.out.println("EBay Scraper Broke");
                    continue;
                }
                // Closing the web driver for the product page
                pageDriver.quit();
            }
            // Closing the web driver for the main page
            driver.quit();
            page++;
        } while (true);
    }
}
