package org.webscraping;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.webscraping.scrapers.*;

/**
 * AppConfig is a configuration class responsible for defining Spring beans for the application.
 */
@Configuration
@ComponentScan(basePackages = "org.webscraping.scrapers")
public class AppConfig {

    /**
     * The provider for obtaining WebDriver instances.
     */
    private final FirefoxWebDriverProvider webDriverProvider;

    public AppConfig(FirefoxWebDriverProvider webDriverProvider) {
        this.webDriverProvider =  webDriverProvider;
    }

    /**
     * Creates and initializes a ProductDao bean.
     *
     * @return The initialized ProductDao bean.
     */
    @Bean
    public ProductDao getProductDao() {
        ProductDao tmpProductDao = new ProductDao();
        tmpProductDao.init();
        return tmpProductDao;
    }


    /**
     * Creates an ArgosScraper bean and sets its ProductDao dependency.
     *
     * @return The configured ArgosScraper bean.
     */
    @Bean
    public ArgosScraper argosScraper() {
        ArgosScraper tmpArgosScraper = new ArgosScraper(webDriverProvider);
        tmpArgosScraper.setProductDao(getProductDao());
        return tmpArgosScraper;
    }


    /**
     * Creates a BackMarketScraper bean and sets its ProductDao dependency.
     *
     * @return The configured BackMarketScraper bean.
     */
    @Bean
    public BackMarketScraper backMarketScraper() {
        BackMarketScraper tmpBackMarketScraper = new BackMarketScraper(webDriverProvider);
        tmpBackMarketScraper.setProductDao(getProductDao());
        return tmpBackMarketScraper;
    }


    /**
     * Creates a CurrysScraper bean and sets its ProductDao dependency.
     *
     * @return The configured CurrysScraper bean.
     */
    @Bean
    public CurrysScraper currysScraper() {
        CurrysScraper tmpCurrysScraper = new CurrysScraper(webDriverProvider);
        tmpCurrysScraper.setProductDao(getProductDao());
        return tmpCurrysScraper;
    }


    /**
     * Creates an EBayScraper bean and sets its ProductDao dependency.
     *
     * @return The configured EBayScraper bean.
     */
    @Bean
    public EBayScraper eBayScraper() {
        EBayScraper tmpEBayScraper = new EBayScraper(webDriverProvider);
        tmpEBayScraper.setProductDao(getProductDao());
        return tmpEBayScraper;
    }


    /**
     * Creates a JohnLewisScraper bean and sets its ProductDao dependency.
     *
     * @return The configured JohnLewisScraper bean.
     */
    @Bean
    public JohnLewisScraper johnLewisScraper() {
        JohnLewisScraper tmpJohnLewisScraper = new JohnLewisScraper(webDriverProvider);
        tmpJohnLewisScraper.setProductDao(getProductDao());
        return tmpJohnLewisScraper;
    }


    /**
     * Creates a Scraper bean and sets its individual scraper dependencies.
     *
     * @return The configured Scraper bean.
     */
    @Bean
    public Scraper scraper() {
        Scraper tmpScraper = new Scraper();
        tmpScraper.setArgosScraper(argosScraper());
        tmpScraper.setBackMarketScraper(backMarketScraper());
        tmpScraper.setCurrysScraper(currysScraper());
        tmpScraper.setEBayScraper(eBayScraper());
        tmpScraper.setJohnLewisScraper(johnLewisScraper());
        return  tmpScraper;
    }

}
