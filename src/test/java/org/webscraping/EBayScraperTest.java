package org.webscraping;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.webscraping.scrapers.EBayScraper;
import org.webscraping.scrapers.FirefoxWebDriverProvider;

import static org.mockito.Mockito.*;

public class EBayScraperTest {

    /**
     * Test the run method of {@link EBayScraper}.
     * It mocks the dependencies, creates the scraper with mocked dependencies, runs the scraper,
     * and verifies interactions with the mocked dependencies.
     */
    @Test
    public void testRun() {
        // Mocking the dependencies
        FirefoxWebDriverProvider mockWebDriverProvider = mock(FirefoxWebDriverProvider.class);
        WebDriver mockDriver = mock(WebDriver.class);
        when(mockWebDriverProvider.getWebDriver()).thenReturn(mockDriver);

        // Creating the EBayScraper with the mocked dependencies
        EBayScraper eBayScraper = new EBayScraper(mockWebDriverProvider);

        // Running the scraper
        eBayScraper.start();

        // Verifying interactions
        verify(mockWebDriverProvider, atLeastOnce()).getWebDriver();
    }
}
