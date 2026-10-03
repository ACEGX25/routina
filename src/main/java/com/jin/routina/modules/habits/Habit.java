package com.jin.routina.modules.habits;


import com.jin.routina.modules.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "habits")
@Data
public class Habit {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Integer id;


    @JoinColumn(name="user_id", nullable=false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name="name", nullable=false)
    private String name;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
