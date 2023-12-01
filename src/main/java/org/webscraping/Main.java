package org.webscraping;


import org.webscraping.scrapers.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Scraper scraper = new Scraper();
        scraper.init();

        try {
            scraper.scrape();
        } catch (Exception ex) {
            System.out.println("Scrape function failed.");
            ex.printStackTrace();
        }


    }
}
