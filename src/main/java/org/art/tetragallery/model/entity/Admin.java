package org.art.tetragallery.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Admin{
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;
}
