package org.webscraping;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.webscraping.scrapers.*;

public class ScraperConcurrencyTest {

    @Test
    void testConcurrentScraping() {
        // Creating instances of the individual scrapers and set up any dependencies
        ArgosScraper argosScraper = new ArgosScraper();
        BackMarketScraper backMarketScraper = new BackMarketScraper();
        CurrysScraper currysScraper = new CurrysScraper();
        EBayScraper eBayScraper = new EBayScraper();
        JohnLewisScraper johnLewisScraper = new JohnLewisScraper();

        // Creating an instance of the Scraper and setting all individual scrapers
        Scraper scraper = new Scraper();
        scraper.setArgosScraper(argosScraper);
        scraper.setBackMarketScraper(backMarketScraper);
        scraper.setCurrysScraper(currysScraper);
        scraper.setEBayScraper(eBayScraper);
        scraper.setJohnLewisScraper(johnLewisScraper);

        // Executing concurrent scraping
        scraper.scrape();

        // Optionally, you can add assertions or checks here to ensure the concurrent scraping worked as expected.
        // For example, check if all threads have finished execution.
        // You can use Thread.join() or other mechanisms for synchronization.

        // Adding assertions
        // Check if each thread has finished execution
        Assertions.assertFalse(argosScraper.isAlive(), "ArgosScraper should not be alive after scraping.");
        Assertions.assertFalse(backMarketScraper.isAlive(), "BackMarketScraper should not be alive after scraping.");
        Assertions.assertFalse(currysScraper.isAlive(), "CurrysScraper should not be alive after scraping.");
        Assertions.assertFalse(eBayScraper.isAlive(), "EBayScraper should not be alive after scraping.");
        Assertions.assertFalse(johnLewisScraper.isAlive(), "JohnLewisScraper should not be alive after scraping.");
    }
}
