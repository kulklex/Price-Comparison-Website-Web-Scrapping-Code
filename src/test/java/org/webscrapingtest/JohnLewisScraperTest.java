package org.webscrapingtest;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.webscraping.scrapers.JohnLewisScraper;
import org.webscraping.scrapers.FirefoxWebDriverProvider;

import static org.mockito.Mockito.*;

public class JohnLewisScraperTest {

    /**
     * Test the run method of {@link JohnLewisScraper}.
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
        JohnLewisScraper johnLewisScraper = new JohnLewisScraper(mockWebDriverProvider);

        // Running the scraper
        johnLewisScraper.start();

        // Verifying interactions
        verify(mockWebDriverProvider, atLeastOnce()).getWebDriver();
    }
}
