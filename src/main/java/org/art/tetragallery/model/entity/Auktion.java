package org.art.tetragallery.model.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
@ToString(exclude = {"bids"})
@EqualsAndHashCode(exclude = {"bids"})

public class Auktion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Timestamp timestamp;
    private List<String> categories;
    @ManyToOne
    private Artist seller;
    @OneToOne
    private Product product;
    private double secretPrice;
    @OneToMany(mappedBy = "auktion")
    private List<Bid> bids;
}
