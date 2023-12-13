package org.webscrapingtest;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.webscraping.scrapers.BackMarketScraper;
import org.webscraping.scrapers.JohnLewisScraper;
import org.webscraping.scrapers.FirefoxWebDriverProvider;

import static org.mockito.Mockito.*;

public class BackMarketScraperTest {

    /**
     * Test the run method of {@link BackMarketScraper}.
     * It mocks the dependencies, creates the scraper with mocked dependencies, runs the scraper,
     * and verifies interactions with the mocked dependencies.
     */
    @Test
    public void testRun() {
        // Mocking the dependencies
        FirefoxWebDriverProvider mockWebDriverProvider = mock(FirefoxWebDriverProvider.class);
        WebDriver mockDriver = mock(WebDriver.class);
        when(mockWebDriverProvider.getWebDriver()).thenReturn(mockDriver);

        // Creating the JohnLewisScraper with the mocked dependencies
        BackMarketScraper backMarketScraper = new BackMarketScraper(mockWebDriverProvider);

        // Running the scraper
        backMarketScraper.start();

        // Verifying interactions
        verify(mockWebDriverProvider, atLeastOnce()).getWebDriver();
    }
}
