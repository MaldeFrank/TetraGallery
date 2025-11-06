package org.art.tetragallery.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "shoppingbasket")
public class ShoppingBasket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shoppingbasket_id")
    private long id;
    @OneToOne
    @JoinColumn(name = "customer_id_fk")
    private Customer customer;
    @ManyToMany
    @JoinTable(
            name = "basket_product_link",
            joinColumns = @JoinColumn(name = "basket_id_fk"),
            inverseJoinColumns = @JoinColumn(name = "product_id_fk")
    )
    private List<Product> products;
}
