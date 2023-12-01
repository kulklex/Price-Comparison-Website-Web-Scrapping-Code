package org.webscraping.scrapers;

import org.hibernate.SessionFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.ArrayList;
import java.util.List;

public class JohnLewisScraper extends Thread{

    public SessionFactory sessionFactory;

    public JohnLewisScraper(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void run() {
        FirefoxOptions options = new FirefoxOptions();
        System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver");
        options.setHeadless(true);


        do {
            WebDriver driver = new FirefoxDriver(options);

            driver.get("https://www.johnlewis.com/search?search-term=earbuds&sortBy=popularity&chunk=4");

            try {
                Thread.sleep(1000);
            } catch (Exception ex) {
                ex.printStackTrace();
            }


            List<WebElement> earbudsList = driver.findElements(By.xpath("//div[@class='product-card_c-product-card__product-details__QLyYh']//child::a"));

            if (earbudsList.isEmpty()) {
                break;
            }

            List<String> earbudsUrl = new ArrayList<>();

            for ( WebElement earbuds : earbudsList) {
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


                System.out.println("Name: "+name);
                System.out.println("Price: "+price);
                System.out.println("Image: "+imageUrl);
                System.out.println("Description: "+description);
                System.out.println("Brand: "+brand);
                pageDriver.quit();

            }

            driver.quit();
        } while (true);
    }
}
