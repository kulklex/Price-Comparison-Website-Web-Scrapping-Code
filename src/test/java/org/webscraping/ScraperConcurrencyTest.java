package org.webscraping;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.webscraping.scrapers.*;

/**
 * The ScraperConcurrencyTest class contains JUnit tests for the multithreading functionality.
 */
public class ScraperConcurrencyTest {

    /**
     * The provider for obtaining WebDriver instances.
     */
    private final FirefoxWebDriverProvider webDriverProvider;


    /**
     * Constructs ScraperConcurrencyTest with the specified WebDriverProvider.
     *
     * @param webDriverProvider The provider for obtaining WebDriver instances.
     */
    public ScraperConcurrencyTest(FirefoxWebDriverProvider webDriverProvider) {
        this.webDriverProvider = webDriverProvider;
    }

    @Test
    void testConcurrentScraping() {
        // Creating instances of the individual scrapers and set up any dependencies
        ArgosScraper argosScraper = new ArgosScraper(webDriverProvider);
        BackMarketScraper backMarketScraper = new BackMarketScraper(webDriverProvider);
        CurrysScraper currysScraper = new CurrysScraper(webDriverProvider);
        EBayScraper eBayScraper = new EBayScraper(webDriverProvider);
        JohnLewisScraper johnLewisScraper = new JohnLewisScraper(webDriverProvider);

        // Creating an instance of the Scraper and setting all individual scrapers
        Scraper scraper = new Scraper();
        scraper.setArgosScraper(argosScraper);
        scraper.setBackMarketScraper(backMarketScraper);
        scraper.setCurrysScraper(currysScraper);
        scraper.setEBayScraper(eBayScraper);
        scraper.setJohnLewisScraper(johnLewisScraper);

        // Executing concurrent scraping
        scraper.scrape();


        // Adding assertions
        // Checking if each thread has finished execution
        Assertions.assertFalse(argosScraper.isAlive(), "ArgosScraper should not be alive after scraping.");
        Assertions.assertFalse(backMarketScraper.isAlive(), "BackMarketScraper should not be alive after scraping.");
        Assertions.assertFalse(currysScraper.isAlive(), "CurrysScraper should not be alive after scraping.");
        Assertions.assertFalse(eBayScraper.isAlive(), "EBayScraper should not be alive after scraping.");
        Assertions.assertFalse(johnLewisScraper.isAlive(), "JohnLewisScraper should not be alive after scraping.");
    }
}
