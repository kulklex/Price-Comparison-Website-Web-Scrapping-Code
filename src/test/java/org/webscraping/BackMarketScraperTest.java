package org.webscraping;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.webscraping.scrapers.BackMarketScraper;

/**
 * JUnit test for the ArgosScraper class.
 */
public class BackMarketScraperTest {

    private static BackMarketScraper backMarketScraper;
    private static ProductDao productDao;

    /**
     * Set up resources before running the tests.
     */
    @BeforeAll
    static void setUp() {
        // Configure headless Firefox browser for testing
        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(true);
        System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver");

        // Initialize ArgosScraper and ProductDao for testing
        backMarketScraper = new BackMarketScraper();
        backMarketScraper.setProductDao(productDao);

        // Initialize ProductDao
        productDao = new ProductDao();
        productDao.init();
    }

    /**
     * Clean up resources after running the tests.
     */
    @AfterAll
    static void tearDown() {
        // Close the ProductDao
        productDao.close();
    }

    /**
     * Test the BackMarketScraper run method.
     */
    @Test
    void testRun() {
        // Start the ArgosScraper
        backMarketScraper.start();

        try {
            // Allow more time for scraping
            backMarketScraper.join(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Assert that the scraper has completed
        Assertions.assertFalse(backMarketScraper.isAlive(), "BackMarketScraper should not be alive after scraping.");
    }
}

