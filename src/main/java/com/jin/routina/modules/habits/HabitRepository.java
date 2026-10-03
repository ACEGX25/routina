package com.jin.routina.modules.habits;

import com.jin.routina.modules.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HabitRepository extends JpaRepository<Habit, Integer> {
    List<Habit> findByUser(User user);

    List<Habit> findByUserId(Integer userId);

    Optional<Habit> findByIdAndUser(Integer id, User user);

    boolean existsByUserAndName(User user, String name);

    boolean existsByUserIdAndName(Integer userId, String name);
}
