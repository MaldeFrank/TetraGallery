package org.art.tetragallery.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Entity
@Data
public class Customer {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;
    @OneToMany(mappedBy = "customer")
    private Set<Sale> sales;
    @OneToMany(mappedBy = "customer")
    private List<Bid> bids;
}
