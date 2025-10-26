package org.art.tetragallery.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Artist {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;
    @OneToMany(mappedBy = "artist")
    private List<Product> products;
}
