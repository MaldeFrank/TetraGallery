package org.art.tetragallery.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "admin")
public class Admin{
    @Id
    @GeneratedValue
    @Column(name = "admin_id")
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id_fk", unique = true)
    private User user;
}
