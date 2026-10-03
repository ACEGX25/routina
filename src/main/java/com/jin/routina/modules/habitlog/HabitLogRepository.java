package com.jin.routina.modules.habitlog;

import com.jin.routina.modules.habits.Habit;
import com.jin.routina.modules.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface HabitLogRepository extends JpaRepository<HabitLog, Integer> {
    List<HabitLog> findByHabitOrderByLogDateDesc(Habit habit);
    List<HabitLog> findByHabitAndLogDateBetween(Habit habit, LocalDate start, LocalDate end);
    boolean existsByHabitAndLogDate(Habit habit, LocalDate logDate);
    List<HabitLog> findByHabitUser( User user);
}
