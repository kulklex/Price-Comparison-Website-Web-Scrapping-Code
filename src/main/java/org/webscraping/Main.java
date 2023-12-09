package org.webscraping;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.webscraping.scrapers.*;

public class Main {

    /**
     * The main method to start the web scraping process.
     *
     * @throws InterruptedException If the execution is interrupted during the scraping process.
     */
    public static void main(String[] args) throws InterruptedException {

        // Create the Spring application context using the configuration class AppConfig
        ApplicationContext context = new AnnotationConfigApplicationContext(
                AppConfig.class
        );

        // Retrieve the Scraper bean from the application context
        Scraper scraper = (Scraper) context.getBean("scraper");

        try {
            // Initiate the scraping process
            scraper.scrape();
        } catch (Exception ex) {
            System.out.println("Scrape function failed.");
            ex.printStackTrace();
        }


    }
}
