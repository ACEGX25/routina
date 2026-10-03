package com.jin.routina.modules.habits;

import com.jin.routina.modules.habits.dto.CreateHabitRequestDto;
import com.jin.routina.modules.habits.dto.HabitResponseDto;
import com.jin.routina.modules.user.User;
import com.jin.routina.modules.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HabitServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private HabitRepository habitRepository;

    @InjectMocks
    private HabitService habitService;

    @Test
    void shouldCreateHabitSuccessfully() {
        // --- 1. ARRANGE (Prepare the fake data & mock behavior) ---
        Integer userId = 1;
        User fakeUser = new User();
        fakeUser.setId(userId);
        fakeUser.setEmail("jin@example.com");
        CreateHabitRequestDto request = new CreateHabitRequestDto();
        request.setName("Gym");
        Habit savedHabit = new Habit();
        savedHabit.setId(10);
        savedHabit.setName("Gym");
        savedHabit.setUser(fakeUser);
        // Tell our mock stunt doubles what to do when HabitService calls them:
        when(userRepository.findById(userId)).thenReturn(Optional.of(fakeUser));
        when(habitRepository.existsByUserAndName(fakeUser, "Gym")).thenReturn(false);
        when(habitRepository.save(any(Habit.class))).thenReturn(savedHabit);
        // --- 2. ACT (Call the actual method being tested) ---
        HabitResponseDto response = habitService.create(userId, request);
        // --- 3. ASSERT (Verify the output) ---
        assertNotNull(response);
        assertEquals(10, response.getId());
        assertEquals("Gym", response.getName());
        // Verify that habitRepository.save(...) was actually called once
        verify(habitRepository, times(1)).save(any(Habit.class));
    }

    @Test
    void shouldGetHabitByIdSuccessfully() {
        Integer userId = 1;
        Integer habitId = 10;
        User fakeUser = new User();
        fakeUser.setId(userId);

        Habit habit = new Habit();
        habit.setId(habitId);
        habit.setName("Gym");
        habit.setUser(fakeUser);

        when(habitRepository.findById(habitId)).thenReturn(Optional.of(habit));

        HabitResponseDto response = habitService.getHabit(userId, habitId);

        assertNotNull(response);
        assertEquals(habitId, response.getId());
        assertEquals("Gym", response.getName());
    }

    @Test
    void shouldThrowHabitNotFoundExceptionWhenHabitDoesNotExist() {
        Integer userId = 1;
        Integer nonExistentHabitId = 999;

        when(habitRepository.findById(nonExistentHabitId)).thenReturn(Optional.empty());

        assertThrows(com.jin.routina.common.exception.HabitNotFoundException.class, () -> {
            habitService.getHabit(userId, nonExistentHabitId);
        });
    }

    @Test
    void shouldThrowForbiddenExceptionWhenHabitBelongsToAnotherUser() {
        Integer loggedInUserId = 1;
        Integer anotherUserId = 2;
        Integer habitId = 10;

        User anotherUser = new User();
        anotherUser.setId(anotherUserId);

        Habit anotherUsersHabit = new Habit();
        anotherUsersHabit.setId(habitId);
        anotherUsersHabit.setName("Another User's Gym");
        anotherUsersHabit.setUser(anotherUser);

        when(habitRepository.findById(habitId)).thenReturn(Optional.of(anotherUsersHabit));

        assertThrows(com.jin.routina.common.exception.ForbiddenException.class, () -> {
            habitService.getHabit(loggedInUserId, habitId);
        });
    }
}
