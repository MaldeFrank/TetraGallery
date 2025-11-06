package org.art.tetragallery.model.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
@ToString(exclude = {"bids"})
@EqualsAndHashCode(exclude = {"bids"})
@Table(name = "auction")
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auction_id")
    private long id;
    private Timestamp timestamp;
    @ManyToOne
    @JoinColumn(name = "artist_seller_id_fk")
    private Artist seller;
    @OneToOne
    @JoinColumn(name = "product_id_fk")
    private Product product;
    private BigDecimal secretPrice;
    @OneToMany(mappedBy = "auction")
    private List<Bid> bids;
}
