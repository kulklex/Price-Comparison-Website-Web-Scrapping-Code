package org.webscraping;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.webscraping.scrapers.ArgosScraper;

/**
 * JUnit test for the ArgosScraper class.
 */
public class ArgosScraperTest {

    private static ArgosScraper argosScraper;
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
        argosScraper = new ArgosScraper();
        AppConfig appConfig = new AppConfig();
        argosScraper.setProductDao(appConfig.getProductDao());

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
     * Test the ArgosScraper run method.
     */
    @Test
    void testRun() {
        // Start the ArgosScraper
        argosScraper.start();

        try {
            // Allow more time for scraping
            argosScraper.join(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Assert that the scraper has completed
        Assertions.assertFalse(argosScraper.isAlive(), "ArgosScraper should not be alive after scraping.");
    }
}

