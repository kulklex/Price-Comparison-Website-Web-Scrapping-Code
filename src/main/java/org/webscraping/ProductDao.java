package org.webscraping;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.webscraping.entities.Comparison;
import org.webscraping.entities.Product;
import org.webscraping.entities.Variants;

import java.util.List;

public class ProductDao {
    public SessionFactory sessionFactory;
//Data Access Object

    public Comparison saveItem(Comparison comparison){
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        //Add or update Product
        session.saveOrUpdate(comparison.getVariants().getProduct());
        System.out.println("Product stored with ID: " + comparison.getVariants().getProduct().getId());

        //Add or update Variants of product
        session.saveOrUpdate(comparison.getVariants());
        System.out.println("Variants of product stored with ID: " + comparison.getVariants().getId());

        //Add or update comparison data
        session.saveOrUpdate(comparison);

        //Commit transaction to save to database
        session.getTransaction().commit();

        session.close();

        System.out.println("Product added to the database with ID: " + comparison.getId());
        return comparison;
    }

    public void saveAndMerge(Comparison comparison) throws Exception{
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        // First find or create Product
        String queryStr = "from Product where name='" + comparison.getVariants().getProduct().getName() +"'";
        List<Product> productList= session.createQuery(queryStr).getResultList();

        // Checking if product already exists
        if(productList.size() == 1) {
            comparison.getVariants().setProduct(productList.get(0));
            System.out.println("Product found with ID: " + productList.get(0).getId());
        }
        else if (productList.isEmpty()) {
            session.saveOrUpdate(comparison.getVariants().getProduct());
            System.out.println("Product Added with ID: " + comparison.getVariants().getProduct().getId());
        }
        else {
            throw new Exception("Multiple Products with same name");
        }


        // Secondly, Find and create product variants
        // Do a similar thing as previously done with product

        queryStr = "from Variants where products_id='" + comparison.getVariants().getProduct().getId() + "'";
        List<Variants> variantsList = session.createQuery(queryStr).getResultList();

        // Check if variant exist
        if (variantsList.size() == 1){
            comparison.setVariant(variantsList.get(0));
            System.out.println("Variant found with ID: " + variantsList.get(0).getId());
        }
        else if (variantsList.isEmpty()) {
            session.saveOrUpdate(comparison.getVariants());
            System.out.println("Product Variant added to the database with ID: "+ comparison.getVariants().getId());
        } else {
            throw new Exception("Multiple Variants with the same name");
        }


        //Finally save or update comparison
        // Do a similar thing as previously done with product variants
        queryStr = "from Comparison where id='" + comparison.getId() + "'";
        List<Comparison> comparisonList = session.createQuery(queryStr).getResultList();

        // check if comparison exists
        if(comparisonList.size() == 1) {
            System.out.println("Comparison found with ID: " + comparisonList.get(0).getId());

            //Update comparison if necessary
            System.out.println("Updating Comparison");
            System.out.println("Old name: " + comparisonList.get(0).getName() + "\n" +"New name: " + comparison.getName());
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
        session.getTransaction().commit();

        session.close();
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
