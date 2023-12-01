package org.webscraping;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.pmw.tinylog.Logger;
import org.webscraping.scrapers.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        SessionFactory sessionFactory = null;

        try {

                StandardServiceRegistryBuilder standardServiceRegistryBuilder = new StandardServiceRegistryBuilder();
                standardServiceRegistryBuilder.configure("hibernate-cfg.xml");
                StandardServiceRegistry registry = standardServiceRegistryBuilder.build();

                try {
                    sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
                } catch (Exception e) {
                    System.err.println("Session Factory build failed.");
                    e.printStackTrace();
                    StandardServiceRegistryBuilder.destroy(registry);
                }

                Logger.info("Session factory built.");

            } catch (Throwable ex) {
                System.err.println("SessionFactory creation failed." + ex);
            }

        Scrapper scrapper = new Scrapper(sessionFactory);
        scrapper.scrape();


    }
}
