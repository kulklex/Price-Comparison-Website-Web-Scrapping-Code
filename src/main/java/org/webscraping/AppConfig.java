package org.webscraping;

import org.springframework.context.annotation.Bean;
import org.webscraping.scrapers.*;

public class AppConfig {


    @Bean
    public ProductDao getProductDao() {
        ProductDao tmpProductDao = new ProductDao();
        tmpProductDao.init();
        return tmpProductDao;
    }

    @Bean
    public ArgosScraper argosScraper() {
        ArgosScraper tmpArgosScraper = new ArgosScraper();
        tmpArgosScraper.setProductDao(getProductDao());
        return tmpArgosScraper;
    }

    @Bean
    public BackMarketScraper backMarketScraper() {
        BackMarketScraper tmpBackMarketScraper = new BackMarketScraper();
        tmpBackMarketScraper.setProductDao(getProductDao());
        return tmpBackMarketScraper;
    }

    @Bean
    public CurrysScraper currysScraper() {
        CurrysScraper tmpCurrysScraper = new CurrysScraper();
        tmpCurrysScraper.setProductDao(getProductDao());
        return tmpCurrysScraper;
    }

    @Bean
    public EBayScraper eBayScraper() {
        EBayScraper tmpEBayScraper = new EBayScraper();
        tmpEBayScraper.setProductDao(getProductDao());
        return tmpEBayScraper;
    }

    @Bean
    public JohnLewisScraper johnLewisScraper() {
        JohnLewisScraper tmpJohnLewisScraper = new JohnLewisScraper();
        tmpJohnLewisScraper.setProductDao(getProductDao());
        return tmpJohnLewisScraper;
    }


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
