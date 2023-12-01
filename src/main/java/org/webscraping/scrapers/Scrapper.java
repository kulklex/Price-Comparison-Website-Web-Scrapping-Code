package org.webscraping.scrapers;


import org.hibernate.SessionFactory;

public class Scrapper {


    private final SessionFactory sessionFactory;

    public  Scrapper (SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
  public void scrape() {
      ArgosScraper ags = new ArgosScraper(sessionFactory);
      ags.start();

      BackMarketScraper bms = new BackMarketScraper(sessionFactory);
      bms.start();

      CurrysScraper crs = new CurrysScraper(sessionFactory);
      crs.start();


      EBayScraper ebs = new EBayScraper(sessionFactory);
      ebs.start();

      JohnLewisScraper jls = new JohnLewisScraper(sessionFactory);
      jls.start();
  }

}
