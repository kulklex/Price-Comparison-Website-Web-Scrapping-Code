package org.webscraping;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.webscraping.scrapers.*;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.atLeastOnce;

/**
 * The ScraperConcurrencyTest class contains JUnit tests for the multithreading functionality.
 */
public class ScraperConcurrencyTest {





    @Test
    public void testConcurrentScraping() throws InterruptedException {
        // Mocking the dependencies
        FirefoxWebDriverProvider mockWebDriverProvider = mock(FirefoxWebDriverProvider.class);
        WebDriver mockDriver = mock(WebDriver.class);
        when(mockWebDriverProvider.getWebDriver()).thenReturn(mockDriver);

        // Creating instances of the individual scrapers and set up with mocked dependencies
        ArgosScraper argosScraper = new ArgosScraper(mockWebDriverProvider);
        BackMarketScraper backMarketScraper = new BackMarketScraper(mockWebDriverProvider);
        CurrysScraper currysScraper = new CurrysScraper(mockWebDriverProvider);
        EBayScraper eBayScraper = new EBayScraper(mockWebDriverProvider);
        JohnLewisScraper johnLewisScraper = new JohnLewisScraper(mockWebDriverProvider);

        // Creating an instance of the Scraper and setting all individual scrapers
        Scraper scraper = new Scraper();
        scraper.setArgosScraper(argosScraper);
        scraper.setBackMarketScraper(backMarketScraper);
        scraper.setCurrysScraper(currysScraper);
        scraper.setEBayScraper(eBayScraper);
        scraper.setJohnLewisScraper(johnLewisScraper);

        // Executing concurrent scraping
        scraper.scrape();

        // Wait for each thread to finish
        argosScraper.join();
        backMarketScraper.join();
        currysScraper.join();
        eBayScraper.join();
        johnLewisScraper.join();

        // Adding assertions
        // Checking if each thread has finished execution
        Assertions.assertFalse(argosScraper.isAlive(), "ArgosScraper should not be alive after scraping.");
        Assertions.assertFalse(backMarketScraper.isAlive(), "BackMarketScraper should not be alive after scraping.");
        Assertions.assertFalse(currysScraper.isAlive(), "CurrysScraper should not be alive after scraping.");
        Assertions.assertFalse(eBayScraper.isAlive(), "EBayScraper should not be alive after scraping.");
        Assertions.assertFalse(johnLewisScraper.isAlive(), "JohnLewisScraper should not be alive after scraping.");
    }
}
