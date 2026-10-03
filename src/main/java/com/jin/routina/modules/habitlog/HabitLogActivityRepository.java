package com.jin.routina.modules.habitlog;

import com.jin.routina.modules.activities.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HabitLogActivityRepository extends JpaRepository<HabitLogActivity, Integer> {
    Optional<HabitLogActivity> findByHabitLogAndActivity(HabitLog habitLog, Activity activity);
    boolean existsByHabitLogAndActivity(HabitLog habitLog, Activity activity);
    long countByActivityAndCompletedTrue(Activity activity);
    void deleteByHabitLog(HabitLog habitLog);
    List<HabitLogActivity> findByHabitLog(HabitLog habitLog);
}
