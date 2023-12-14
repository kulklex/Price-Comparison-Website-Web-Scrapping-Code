package org.webscraping.entities;

import javax.persistence.*;

/**
 * The {@code Comparison} class represents a comparison entity in the database.
 * It is used to store information about a product comparison, including its name, price, URL, and associated product.
 * The class is annotated with JPA annotations to map it to a database table named "comparison".
 */
@Entity(name="Comparison")
@Table(name="comparison")
public class Comparison {

    // represents the id of the comparison
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * The name of the comparison.
     */
    @Column(name ="name")
    private String name;

    /**
     * The price of the product in the comparison.
     */
    @Column(name = "price")
    private float price;

    /**
     * The URL associated with the comparison.
     */
    @Column(name = "url")
    private String url;

    /**
     * The product associated with the comparison. It is a many-to-one relationship
     * with the {@link Product} entity, and it is fetched lazily.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    /**
     * Default constructor for the {@code Comparison} class.
     */
    public Comparison() {}


    /**
     * Get the id of the comparison.
     *
     * @return The id of the comparison.
     */
    public int getId() {
        return id;
    }

    /**
     * Set the id of the comparison.
     *
     * @param id The id to set for the comparison.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the name of the comparison.
     *
     * @return The name of the comparison.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the comparison.
     *
     * @param name The name to set for the comparison.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the price of the product in the comparison.
     *
     * @return The price of the product in the comparison.
     */
    public float getPrice() {
        return price;
    }

    /**
     * Set the price of the product in the comparison.
     *
     * @param price The price to set for the product in the comparison.
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * Get the URL associated with the comparison.
     *
     * @return The URL associated with the comparison.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set the URL associated with the comparison.
     *
     * @param url The URL to set for the comparison.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Get the product associated with the comparison.
     *
     * @return The product associated with the comparison.
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Set the product associated with the comparison.
     *
     * @param product The product to set for the comparison.
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * Override the toString method to provide a string representation of the {@code Comparison} object.
     *
     * @return A string representation of the {@code Comparison} object.
     */
    @Override
    public String toString() {
        return "Comparison{" +
                "id=" + id +
                ", name=" + name +
                ", price=" + price +
                ", url='" + url + '\'' +
                '}';
    }
}
