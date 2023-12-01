package org.webscraping.scrapers;

import org.hibernate.SessionFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.ArrayList;
import java.util.List;

public class BackMarketScraper extends Thread{

    public SessionFactory sessionFactory;

    public  BackMarketScraper (SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
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

               String name = pageDriver.findElement(By.xpath("//h1[@class='title-1 lg:max-w-[38rem]']"))
                       .getAttribute("innerText");


               String priceString = pageDriver.findElement(By.xpath("//div[@data-test='normal-price']"))
                       .getAttribute("innerText").substring(1);

               Float price = Float.valueOf(priceString);


               String description = pageDriver.findElement(By.xpath("//h1[@class='title-1 lg:max-w-[38rem]']"))
                       .getAttribute("innerText");

               String[] brandArray = name.split(" ");

               String brand = brandArray[0].toUpperCase();

               System.out.println("Name: "+name);
               System.out.println("Price: "+price);
               System.out.println("Image: "+imageUrl);
               System.out.println("Description: "+description);
               System.out.println("Brand: "+brand);
               pageDriver.quit();

           }

           driver.quit();
           page++;
       } while (true);
    }
}
