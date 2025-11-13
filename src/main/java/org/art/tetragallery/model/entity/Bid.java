package org.art.tetragallery.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "bid")
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bid_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "customer_id_fk")
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "auction_id_fk")
    private Auction auction;
    private BigDecimal amount;
    private Timestamp timestamp;
}
