package org.webscraping;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.webscraping.scrapers.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        ApplicationContext context = new AnnotationConfigApplicationContext(
                AppConfig.class
        );
        Scraper scraper = (Scraper) context.getBean("scraper");

        try {
            scraper.scrape();
        } catch (Exception ex) {
            System.out.println("Scrape function failed.");
            ex.printStackTrace();
        }


    }
}
