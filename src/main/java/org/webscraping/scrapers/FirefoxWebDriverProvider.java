package org.webscraping.scrapers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link WebDriverProvider} that provides instances of a headless Firefox WebDriver.
 * This class is annotated with {@link Component} to indicate that it is a Spring component and can be
 * automatically discovered and registered by the Spring container.
 */
@Component
public class FirefoxWebDriverProvider implements WebDriverProvider{

    /**
     * Provides a configured instance of a headless Firefox WebDriver.
     *
     * @return A configured instance of the Firefox WebDriver.
     */
    @Override
    public WebDriver getWebDriver() {
        // Configuration for headless Firefox browser
        FirefoxOptions options = new FirefoxOptions();
        System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver");

        /*Set Headless mode */
        options.setHeadless(true);

        // Creating an instance of the web driver
        return new FirefoxDriver(options);
    }
}
