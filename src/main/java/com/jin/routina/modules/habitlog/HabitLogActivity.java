package com.jin.routina.modules.habitlog;

import com.jin.routina.modules.activities.Activity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "habit_log_activity")
public class HabitLogActivity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name="habit_log_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private HabitLog habitLog;

    @JoinColumn(name = "activity_log", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Activity activity;

    @Column(name = "completed", nullable = false)
    private boolean completed;


}
