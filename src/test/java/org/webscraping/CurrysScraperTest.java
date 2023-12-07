package org.webscraping;

import org.junit.jupiter.api.*;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.webscraping.ProductDao;
import org.springframework.boot.test.context.SpringBootTest;
import org.webscraping.scrapers.CurrysScraper;

import java.util.concurrent.CountDownLatch;

/**
 * JUnit test for the CurrysScraper class.
 */
@SpringBootTest
public class CurrysScraperTest {

    private static CurrysScraper currysScraper;
    private static ProductDao productDao;
    private static CountDownLatch scraperLatch;

    /**
     * Set up resources before running the tests.
     */
    @BeforeAll
    static void setUp() {
        // Configure headless Firefox browser for testing
        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(true);
        System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver");

        // Initialize CurrysScraper and ProductDao for testing
        currysScraper = new CurrysScraper();
        currysScraper.setProductDao(productDao);

        // Initialize ProductDao
        productDao = new ProductDao();
        productDao.init();

        // Initialize CountDownLatch with count 1
        scraperLatch = new CountDownLatch(1);
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
     * Test the CurrysScraper run method.
     */
    @Test
    void testRun() {
        // Start the CurrysScraper
        currysScraper.start();

        try {
            // Wait for the scraper to finish (maximum timeout: 15 seconds)
            boolean scraperCompleted = scraperLatch.await(15000, java.util.concurrent.TimeUnit.MILLISECONDS);

            // Assert that the scraper has completed
            Assertions.assertTrue(scraperCompleted, "CurrysScraper should complete within the timeout.");
        } catch (InterruptedException e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred during the test: " + e.getMessage());
        }
    }

    /**
     * Callback method to signal that the scraper has completed.
     */
    static void onScraperComplete() {
        // Countdown the latch to signal completion
        scraperLatch.countDown();
    }
}
