package org.webscraping;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.webscraping.scrapers.ArgosScraper;
import org.webscraping.scrapers.FirefoxWebDriverProvider;

import static org.mockito.Mockito.*;

public class ArgosScraperTest {

    /**
     * Test the run method of {@link ArgosScraper}.
     * It mocks the dependencies, creates the scraper with mocked dependencies, runs the scraper,
     * and verifies interactions with the mocked dependencies.
     */
    @Test
    public void testRun() {
        // Mocking the dependencies
        FirefoxWebDriverProvider mockWebDriverProvider = mock(FirefoxWebDriverProvider.class);
        WebDriver mockDriver = mock(WebDriver.class);
        when(mockWebDriverProvider.getWebDriver()).thenReturn(mockDriver);

        // Creating the ArgosScraper with the mocked dependencies
        ArgosScraper argosScraper = new ArgosScraper(mockWebDriverProvider);

        // Running the scraper
        argosScraper.start();

        // Verifying Firefox Driver
        verify(mockWebDriverProvider, atLeastOnce()).getWebDriver();
    }
}
