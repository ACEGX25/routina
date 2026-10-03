package com.jin.routina.modules.habitlog;

import com.jin.routina.modules.activities.ActivityRepository;
import com.jin.routina.modules.habitlog.dto.HabitStatsResponseDto;
import com.jin.routina.modules.habits.Habit;
import com.jin.routina.modules.habits.HabitRepository;
import com.jin.routina.modules.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HabitLogServiceTest {

    @Mock
    private HabitLogRepository habitLogRepository;

    @Mock
    private HabitRepository habitRepository;

    @Mock
    private HabitLogActivityRepository habitLogActivityRepository;

    @Mock
    private ActivityRepository activityRepository;

    @InjectMocks
    private HabitLogService habitLogService;

    @Test
    void shouldReturnZeroStatsWhenHabitHasNoLogs() {
        // --- 1. ARRANGE ---
        Integer userId = 1;
        Integer habitId = 10;

        User user = new User();
        user.setId(userId);

        Habit habit = new Habit();
        habit.setId(habitId);
        habit.setUser(user);

        when(habitRepository.findById(habitId)).thenReturn(Optional.of(habit));
        when(habitLogRepository.findByHabitOrderByLogDateDesc(habit)).thenReturn(List.of());

        // --- 2. ACT ---
        HabitStatsResponseDto response = habitLogService.getHabitStats(userId, habitId);

        // --- 3. ASSERT ---
        assertNotNull(response);
        assertEquals(habitId, response.getHabitId());
        assertEquals(0, response.getCurrentStreak());
        assertEquals(0, response.getLongestStreak());
        assertEquals(0, response.getTotalDaysStreak());
    }
}
