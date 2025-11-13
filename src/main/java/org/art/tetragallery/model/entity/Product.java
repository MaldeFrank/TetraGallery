package org.art.tetragallery.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int id;
    @Column(name = "product_description")
    private String description;
    @ManyToMany
    @JoinTable(
            name = "product_tags_link",
            joinColumns = @JoinColumn(name = "product_fk"),
            inverseJoinColumns = @JoinColumn(name = "tag_fk")
    )
    private List<Tag> tags;
    @ManyToOne
    @JoinColumn(name = "artist_id_fk")
    private Artist artist;
    @OneToMany(mappedBy = "product")
    private List<Feedback> feedback;
}
