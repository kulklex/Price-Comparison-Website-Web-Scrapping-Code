package org.webscraping.scrapers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.stereotype.Component;


@Component
public class FirefoxWebDriverProvider implements WebDriverProvider{
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