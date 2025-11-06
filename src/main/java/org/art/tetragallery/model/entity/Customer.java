package org.art.tetragallery.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue
    @Column(name = "customer_id")
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id_fk", unique = true)
    private User user;
    @OneToMany(mappedBy = "customer")
    private Set<Sale> sales;
    @OneToMany(mappedBy = "customer")
    private List<Bid> bids;
}
