package org.art.tetragallery.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;
    @ManyToMany(mappedBy = "tags")
    private List<Product> product;
    @Column(unique = true, nullable = false)
    private String name;
}
