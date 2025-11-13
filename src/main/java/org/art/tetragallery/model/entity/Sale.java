package org.art.tetragallery.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "sale")
@AllArgsConstructor
@NoArgsConstructor
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_id")
    private long id;
    @OneToOne
    @JoinColumn(name = "product_id_fk")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "customer_id_fk")
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "artist_id_fk")
    private Artist seller;
    private Timestamp date;
}
