package org.webscraping.entities;

import javax.persistence.*;

/**
 * The Comparison entity from the database using annotations
 * @param id represents the id of the comparison
 * @param name represents the name of website showing the comparison
 * @param price represents the product's comparison
 * @param url represents the url to the webpage of the comparison product
 * @param product references the ID of Product entity and represents the id of the product
 */
@Entity(name="Comparison")
@Table(name="comparison")
public class Comparison {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name ="name")
    private String name;

    @Column(name = "price")
    private float price;

    @Column(name = "url")
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public Comparison() {}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

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
