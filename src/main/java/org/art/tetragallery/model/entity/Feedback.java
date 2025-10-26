package org.art.tetragallery.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String message;
    @ManyToOne
    private Product product;
    @ManyToOne
    private Customer customer;
    @ManyToOne
    private Artist owner;
}
