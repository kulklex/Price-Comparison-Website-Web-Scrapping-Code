package org.webscraping.scrapers;


public class Scraper {

     ArgosScraper argosScraper;
     BackMarketScraper backMarketScraper;
     CurrysScraper currysScraper;
     EBayScraper eBayScraper;
     JohnLewisScraper johnLewisScraper;


    public void setArgosScraper(ArgosScraper argosScraper) {
        this.argosScraper = argosScraper;
    }

    public void setBackMarketScraper(BackMarketScraper backMarketScraper) {
        this.backMarketScraper = backMarketScraper;
    }

    public void setCurrysScraper(CurrysScraper currysScraper) {
        this.currysScraper = currysScraper;
    }

    public void setEBayScraper(EBayScraper eBayScraper) {
        this.eBayScraper = eBayScraper;
    }

    public void setJohnLewisScraper(JohnLewisScraper johnLewisScraper) {
        this.johnLewisScraper = johnLewisScraper;
    }

    public void scrape() {
//        argosScraper.start();
        backMarketScraper.start();
//        currysScraper.start();
//        eBayScraper.start();
//        johnLewisScraper.start();
    }

}
