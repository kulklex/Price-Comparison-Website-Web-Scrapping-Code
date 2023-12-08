package org.webscraping.scrapers;

import org.openqa.selenium.WebDriver;

/**
 * This interface represents a provider for obtaining Selenium {@link WebDriver} instances.
 */
public interface WebDriverProvider {

    /**
     * Retrieves a configured instance of {@link WebDriver}.
     *
     * @return A configured instance of {@link WebDriver}.
     */
    WebDriver getWebDriver();
}
