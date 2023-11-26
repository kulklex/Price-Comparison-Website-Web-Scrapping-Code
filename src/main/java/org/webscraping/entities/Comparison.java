package org.webscraping.entities;

import javax.persistence.*;


@Entity(name="Comparison")
@Table(name="comparison")
public class Comparison {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "price")
    private float price;

    @Column(name = "url")
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variants_id")
    private int variants_id;

    public Comparison() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getVariants_id() {
        return variants_id;
    }

    public void setVariants_id(int variants_id) {
        this.variants_id = variants_id;
    }
}
