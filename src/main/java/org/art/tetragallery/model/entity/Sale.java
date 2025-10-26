package org.art.tetragallery.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    private Product product;
    @ManyToOne
    private Customer customer;
    @OneToOne
    private Artist seller;
    private Timestamp date;
}
