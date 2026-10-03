package com.jin.routina.modules.habitlog;

import com.jin.routina.modules.habits.Habit;
import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "habit_logs")
public class HabitLog {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name="habit_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Habit habit;

    @Column(name= "log_date", nullable = false)
    private LocalDate logDate;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
