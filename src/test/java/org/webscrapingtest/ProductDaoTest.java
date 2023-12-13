package org.webscrapingtest;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;
import org.webscraping.ProductDao;
import org.webscraping.entities.Comparison;
import org.webscraping.entities.Product;


/**
 * The ProductDaoTest class contains JUnit tests for the ProductDao class.
 */
class ProductDaoTest {
    private static ProductDao productDao;
    private static SessionFactory sessionFactory;

    /**
     * Sets up the test environment by creating a Hibernate SessionFactory and initializing the ProductDao.
     */
    @BeforeAll
    static void setUp() {
        Configuration configuration = new Configuration().configure("hibernate-test.cfg.xml");
        sessionFactory = configuration.buildSessionFactory();
        productDao = new ProductDao();
        productDao.sessionFactory = sessionFactory;
    }

    /**
     * Tears down the test environment by closing the Hibernate SessionFactory.
     */
    @AfterAll
    static void tearDown() {
        sessionFactory.close();
    }

    /**
     * Tests the saveAndMerge method of the ProductDao by creating a test Comparison entity,
     * saving it to the database, and asserting the saved IDs.
     */
    @Test
    void testSaveAndMerge() {
        try {
            // Creating a test Comparison entity
            Comparison comparison = new Comparison();
            Product product = new Product();
            product.setName("Test Product");
            product.setDescription("This is a test product.");
            comparison.setProduct(product);
            comparison.setUrl("https://test.com");
            comparison.setPrice(99.99f);
            comparison.setName("Website Name");

            // Save and merge the test Comparison
            productDao.saveAndMerge(comparison);

            // Asserting that the Comparison and associated Product are saved
            Assertions.assertNotNull(comparison.getId(), "Comparison ID should not be null after saving.");
            Assertions.assertNotNull(product.getId(), "Product ID should not be null after saving.");

            // Deleting the test Comparison and associated Product from the database
            cleanUpTestEntities(comparison);
        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred during the test: " + e.getMessage());
        }
    }


    /**
     * Cleans up the test entities by deleting the specified Comparison and its associated Product from the database.
     *
     * @param comparison The Comparison entity to be deleted.
     */
    private void cleanUpTestEntities(Comparison comparison) {
        try (var session = sessionFactory.openSession()) {
            var transaction = session.beginTransaction();
            session.delete(comparison);
            session.delete(comparison.getProduct());
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail("Exception occurred during cleanup: " + e.getMessage());
        }
    }
}
