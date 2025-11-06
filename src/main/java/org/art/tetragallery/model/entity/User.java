package org.art.tetragallery.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;
    private String name;
    private String email;
}
