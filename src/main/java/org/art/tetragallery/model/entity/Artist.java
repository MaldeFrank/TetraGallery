package org.art.tetragallery.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "artist")
public class Artist {
    @Id
    @GeneratedValue
    @Column(name = "artist_id")
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id_fk", unique = true)
    private User user;
    @OneToMany(mappedBy = "artist")
    private List<Product> products;
}
