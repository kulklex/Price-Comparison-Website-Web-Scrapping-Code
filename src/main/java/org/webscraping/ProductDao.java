package org.webscraping;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class ProductDao {
    public SessionFactory sessionFactory;
//Data Access Object

    public void init() {
        try {
            StandardServiceRegistryBuilder standardServiceRegistryBuilder = new StandardServiceRegistryBuilder();

            standardServiceRegistryBuilder.configure("hibernate.cfg.xml");

            StandardServiceRegistry registry = standardServiceRegistryBuilder.build();
            try {
                sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            }
            catch (Exception ex) {
                System.out.println("Session Factory build failed" + ex);
                ex.printStackTrace();
                StandardServiceRegistryBuilder.destroy(registry);
            }

            System.out.println("Session Factory built successfully");
        } catch (Throwable ex) {
            ex.printStackTrace();
            System.out.println("SessionFactory creation failed." + ex);
        }
    }

}
