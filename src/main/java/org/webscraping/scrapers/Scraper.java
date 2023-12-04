package org.webscraping.scrapers;


import org.hibernate.SessionFactory;
import org.webscraping.ProductDao;

public class Scraper {

     ProductDao productDao;

     public void init() {
        productDao = new ProductDao();
        productDao.init();
    }


    public void scrape() {
      ArgosScraper ags = new ArgosScraper(productDao);
      ags.start();

      BackMarketScraper bms = new BackMarketScraper(productDao);
      bms.start();
//
//      CurrysScraper crs = new CurrysScraper(productDao);
//      crs.start();
//
//
//      EBayScraper ebs = new EBayScraper(productDao);
//      ebs.start();
//
//      JohnLewisScraper jls = new JohnLewisScraper(productDao);
//      jls.start();
    }

}
