package org.webscraping.entities;


import javax.persistence.*;

@Entity(name = "Variants")
@Table(name = "variants")
public class Variants {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "products_id", nullable = false)
    private Product product;

    @Column(name = "color")
    private String color;


    public Variants() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }


    @Override
    public String toString() {
        return "Variants{" +
                "id=" + id +
                ", products_id=" + product +
                ", color='" + color + '\'' +
                '}';
    }
}
