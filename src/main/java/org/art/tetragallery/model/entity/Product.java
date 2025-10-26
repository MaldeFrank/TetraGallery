package org.art.tetragallery.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.art.tetragallery.model.enums.Tag;

import java.util.List;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String description;
    private List<Tag> tags;
    @ManyToOne
    private Artist artist;
    @OneToMany(mappedBy = "product")
    private List<Feedback> feedback;
}
