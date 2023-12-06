package org.webscraping;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.webscraping.entities.Comparison;
import org.webscraping.entities.Product;

import java.util.List;

public class ProductDao {
    public SessionFactory sessionFactory;

    public void saveAndMerge(Comparison comparison) throws Exception{
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            // First find or create Product
            String queryStr = "from Product where name='" + comparison.getProduct().getName() +"'";
            List<Product> productList= session.createQuery(queryStr).getResultList();

            // Checking if product already exists
            if(productList.size() == 1) {
                comparison.setProduct(productList.get(0));
                System.out.println("Product found in the DB with ID: " + productList.get(0).getId());
            }
            else if (productList.isEmpty()) {
                session.saveOrUpdate(comparison.getProduct());
                System.out.println("Product Added with ID: " + comparison.getProduct().getId());
            }
            else {
                throw new Exception("Multiple Products with same name");
            }


            //Save or update comparison
            queryStr = "from Comparison where product_id='" + comparison.getProduct().getId() + "'";
            List<Comparison> comparisonList = session.createQuery(queryStr).getResultList();

            // check if comparison exists
            if(comparisonList.size() == 1) {
                System.out.println("Comparison found with ID: " + comparisonList.get(0).getId());

                //Update comparison if necessary
                System.out.println("Updating Comparison");
                System.out.println("Old price: " + comparisonList.get(0).getPrice() + "\n" + "New price: " + comparison.getPrice());
                System.out.println("Old Url" + comparisonList.get(0).getUrl() + "\n" + "New Url: " + comparison.getUrl());
            }
            else if (comparisonList.isEmpty()) {
                session.saveOrUpdate(comparison);
                System.out.println("Comparison saved with ID: " + comparison.getId());
            }
            else {
                throw new Exception("Multiple comparison with same data");
            }

            // Commit transaction and save the database
            transaction.commit();
        } catch (Exception ex) {
            // Rolling back the transaction in case of an exception
            if(transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        } finally {
            // Closing the Hibernate session
            if (session != null) {
                session.close();
            }
        }
    }


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
