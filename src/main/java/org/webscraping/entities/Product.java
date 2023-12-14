package org.webscraping.entities;

import javax.persistence.*;

/**
 * The {@code Product} class represents a product entity in the database.
 * It is used to store information about a product, including its name, description, brand, and image URL.
 * The class is annotated with JPA annotations to map it to a database table named "product".
 */
@Entity(name = "Product")
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * The name of the product.
     */
    @Column(name = "name")
    private String name;

    /**
     * The description of the product.
     */
    @Column(name = "description")
    private String description;

    /**
     * The brand of the product.
     */
    @Column(name = "brand")
    private String brand;

    /**
     * The image URL associated with the product.
     */
    @Column(name = "image_url")
    private String image_url;

    /**
     * Default constructor for the {@code Product} class.
     */
    public Product() {}

    /**
     * Get the id of the product.
     *
     * @return The id of the product.
     */
    public int getId() {
        return id;
    }

    /**
     * Set the id of the product.
     *
     * @param id The id to set for the product.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the name of the product.
     *
     * @return The name of the product.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the product.
     *
     * @param name The name to set for the product.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the description of the product.
     *
     * @return The description of the product.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of the product.
     *
     * @param description The description to set for the product.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the brand of the product.
     *
     * @return The brand of the product.
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Set the brand of the product.
     *
     * @param brand The brand to set for the product.
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Get the image URL associated with the product.
     *
     * @return The image URL associated with the product.
     */
    public String getImageUrl() {
        return image_url;
    }

    /**
     * Set the image URL associated with the product.
     *
     * @param image_url The image URL to set for the product.
     */
    public void setImageUrl(String image_url) {
        this.image_url = image_url;
    }

    /**
     * Override the toString method to provide a string representation of the {@code Product} object.
     *
     * @return A string representation of the {@code Product} object.
     */
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", brand='" + brand + '\'' +
                ", image_url='" + image_url + '\'' +
                '}';
    }
}
