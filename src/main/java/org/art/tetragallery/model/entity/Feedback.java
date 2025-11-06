package org.art.tetragallery.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id")
    private long id;
    private String message;
    @ManyToOne
    @JoinColumn(name = "product_id_fk")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "customer_id_fk")
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "artist_id_fk")
    private Artist owner;
}
