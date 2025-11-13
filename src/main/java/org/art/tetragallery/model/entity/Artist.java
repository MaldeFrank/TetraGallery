package org.art.tetragallery.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "artist")
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "artist_id")
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id_fk", unique = true)
    private User user;
    @OneToMany(mappedBy = "artist")
    private List<Product> products;
}
