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
 * JohnLewisScraper is a web scraper for extracting product information from the John Lewis website.
 * It extends the Thread class to allow concurrent scraping of data.
 */
public class JohnLewisScraper extends Thread{

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
        options.setHeadless(true);


        do {
            // Creating an instance of the web driver
            WebDriver driver = new FirefoxDriver(options);

            // Navigating to the John Lewis search page for earbuds
            driver.get("https://www.johnlewis.com/search?search-term=earbuds&sortBy=popularity&chunk=3");

            try {
                // Adding a delay to allow the page to load
                Thread.sleep(1000);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            // Extracting product URLs from the page
            List<WebElement> earbudsList = driver.findElements(By.xpath("//div[@class='product-card_c-product-card__product-details__QLyYh']//child::a"));

            if (earbudsList.isEmpty()) {
                break;
            }

            List<String> earbudsUrl = new ArrayList<>();

            for ( WebElement earbuds : earbudsList) {
                earbudsUrl.add(earbuds.getAttribute("href"));
            }

            // Looping through each product URL
            for (String earbudUrl : earbudsUrl) {
                // Creating a new web driver instance for the product page
                WebDriver pageDriver = new FirefoxDriver(options);

                pageDriver.get(earbudUrl);

                try {
                    // Adding a delay to allow the product page to load
                    Thread.sleep(1000);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }


                // Extracting product details from the product page
                String imageUrl = pageDriver.findElement(By.xpath("//img[@class='GalleryImageElement_galleryImage__qeVXg GalleryImageElement_galleryImage__desktop__qP1_O']"))
                        .getAttribute("src");

                String priceString = pageDriver.findElement(By.xpath("//div[@class='Layout_desktopHeader__WUCH3']//descendant::span[@data-testid='product:price']"))
                        .getText().substring(1);

                Float price = Float.valueOf(priceString);


                String name = pageDriver.findElement(By.xpath("//div[@class='Layout_desktopHeader__WUCH3']//descendant::h1")).getText();


                String description = pageDriver.findElement(By.className("ProductAccordion_container__I7B_E")).getAttribute("innerHTML");

                String[] brandArray = pageDriver.findElement(By.xpath("//li[@class='breadcrumbs-carousel--brand-item  breadcrumbs-carousel--brand-item-last']"))
                        .getText().split(" ");

                String brand = brandArray[brandArray.length - 1].toUpperCase();


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
                comparison.setName("John Lewis");
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
        } while (true);
    }
}
