package org.art.tetragallery.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private long id;
    @ManyToOne
    @JoinColumn(name = "user_id_fk")
    private User user;
}
