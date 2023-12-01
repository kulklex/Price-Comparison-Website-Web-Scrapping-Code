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

    @Column(name = "price")
    private float price;

    @Column(name = "url")
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variants_id", nullable = false)
    private Variants variants;

    public Comparison() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public Variants getVariants() {
        return variants;
    }

    public void setVariant(Variants variants) {
        this.variants = variants;
    }

    @Override
    public String toString() {
        return "Comparison{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", url='" + url + '\'' +
                ", variants_id=" + variants +
                '}';
    }
}
