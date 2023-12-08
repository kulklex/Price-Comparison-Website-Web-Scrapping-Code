package org.webscraping.scrapers;

import org.springframework.stereotype.Service;

/**
 * The Scraper class manages the simultaneous operation of several web scraping threads.
 * It gathers individual scrapers and launches them simultaneously.
 */
@Service
public class Scraper {

    /** The scraper for the Argos website. */
     ArgosScraper argosScraper;

    /** The scraper for the Back Market website. */
     BackMarketScraper backMarketScraper;

    /** The scraper for the Currys website. */
     CurrysScraper currysScraper;

    /** The scraper for the eBay website. */
     EBayScraper eBayScraper;

    /** The scraper for the John Lewis website. */
     JohnLewisScraper johnLewisScraper;



    /**
     * Sets the ArgosScraper instance for this Scraper.
     *
     * @param argosScraper The ArgosScraper instance to be set.
     */
    public void setArgosScraper(ArgosScraper argosScraper) {
        this.argosScraper = argosScraper;
    }

    /**
     * Sets the BackMarketScraper instance for this Scraper.
     *
     * @param backMarketScraper The BackMarketScraper instance to be set.
     */
    public void setBackMarketScraper(BackMarketScraper backMarketScraper) {
        this.backMarketScraper = backMarketScraper;
    }

    /**
     * Sets the CurrysScraper instance for this Scraper.
     *
     * @param currysScraper The CurrysScraper instance to be set.
     */
    public void setCurrysScraper(CurrysScraper currysScraper) {
        this.currysScraper = currysScraper;
    }

    /**
     * Sets the EBayScraper instance for this Scraper.
     *
     * @param eBayScraper The EBayScraper instance to be set.
     */
    public void setEBayScraper(EBayScraper eBayScraper) {
        this.eBayScraper = eBayScraper;
    }

    /**
     * Sets the JohnLewisScraper instance for this Scraper.
     *
     * @param johnLewisScraper The JohnLewisScraper instance to be set.
     */
    public void setJohnLewisScraper(JohnLewisScraper johnLewisScraper) {
        this.johnLewisScraper = johnLewisScraper;
    }


    /**
     * Initiates the execution of individual scrapers concurrently.
     * Each scraper runs in a separate thread.
     */
    public void scrape() {
        argosScraper.start();
        backMarketScraper.start();
        currysScraper.start();
        eBayScraper.start();
        johnLewisScraper.start();
    }

}
