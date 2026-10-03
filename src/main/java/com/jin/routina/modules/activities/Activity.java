package com.jin.routina.modules.activities;

import com.jin.routina.modules.habits.Habit;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Table(name="activities")
@Entity
public class Activity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name="habit_id",nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Habit habit;

    @Column(name="name" , nullable = false)
    private String name;

    @Column(name= "created_at")
    private LocalDateTime createdAt;


}
