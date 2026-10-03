package com.jin.routina.modules.activities;

import com.jin.routina.modules.habits.Habit;
import com.jin.routina.modules.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {
    List<Activity> findByHabit(Habit habit);
    Optional<Activity> findByIdAndHabit(Integer id,Habit habit);
    Boolean existsByNameAndHabit(String name,Habit habit);
}
