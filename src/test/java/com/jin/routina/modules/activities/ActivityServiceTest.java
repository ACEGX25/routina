package com.jin.routina.modules.activities;

import com.jin.routina.common.exception.ForbiddenException;
import com.jin.routina.modules.activities.dto.ActivityResponseDto;
import com.jin.routina.modules.activities.dto.CreateActivityDto;
import com.jin.routina.modules.habits.Habit;
import com.jin.routina.modules.habits.HabitRepository;
import com.jin.routina.modules.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ActivityServiceTest {
    @Mock
    private ActivityRepository activityRepository;

    @Mock
    private HabitRepository habitRepository;

    @InjectMocks
    private ActivityService activityService;

    @Test
    void shouldThrowForbiddenExceptionWhenHabitBelongsToAnotherUser(){
        Integer loggedInUserId = 1;
        Integer anotherUserId = 2;
        Integer habitId = 10;

        User anotherUser = new User();
        anotherUser.setId(anotherUserId);

        Habit habit = new Habit();
        habit.setId(habitId);
        habit.setUser(anotherUser);

        CreateActivityDto request = new CreateActivityDto();
        request.setHabitId(habitId);
        habit.setName("PushUps");

        when(habitRepository.findById(habitId)).thenReturn(Optional.of(habit));

        assertThrows(ForbiddenException.class, () -> {
            activityService.createActivity(loggedInUserId, request);
        });

        verify(activityRepository,never()).save(any(Activity.class));

    }

    @Test
    void shouldCreateActivitySuccessfully(){
        Integer loggedInUserId = 1;
        Integer habitId = 10;

        User anotherUser = new User();
        anotherUser.setId(loggedInUserId);

        Habit habit = new Habit();
        habit.setId(habitId);
        habit.setUser(anotherUser);

        CreateActivityDto request = new CreateActivityDto();
        request.setHabitId(habitId);
        request.setName("PushUps");

        Activity savedActivity = new Activity();
        savedActivity.setId(100);
        savedActivity.setName("PushUps");
        savedActivity.setHabit(habit);

        when(habitRepository.findById(habitId)).thenReturn(Optional.of(habit));
        when(activityRepository.save(any(Activity.class))).thenReturn(savedActivity);

        ActivityResponseDto response = activityService.createActivity(loggedInUserId, request);

        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals("PushUps",response.getName());
        assertEquals(habitId,response.getHabitId());

        verify(activityRepository, times(1)).save(any(Activity.class));
    }

    @Test
    void shouldDeleteActivitySuccessfully() {
        Integer userId = 1;
        Integer activityId = 100;

        User owner = new User();
        owner.setId(userId);

        Habit habit = new Habit();
        habit.setUser(owner);

        Activity activity = new Activity();
        activity.setId(activityId);
        activity.setHabit(habit);

        when(activityRepository.findById(activityId)).thenReturn(Optional.of(activity));

        activityService.deleteActivity(userId, activityId);

        verify(activityRepository, times(1)).delete(activity);
    }

    @Test
    void shouldThrowForbiddenExceptionWhenDeletingAnotherUsersActivity() {
        Integer loggedInUserId = 1;
        Integer anotherUserId = 2;
        Integer activityId = 100;

        User anotherUser = new User();
        anotherUser.setId(anotherUserId);

        Habit habit = new Habit();
        habit.setUser(anotherUser);

        Activity activity = new Activity();
        activity.setId(activityId);
        activity.setHabit(habit);

        when(activityRepository.findById(activityId)).thenReturn(Optional.of(activity));

        assertThrows(ForbiddenException.class, () -> {
            activityService.deleteActivity(loggedInUserId, activityId);
        });

        verify(activityRepository, never()).delete(any(Activity.class));
    }

    @Test
    void shouldThrowActivityNotFoundExceptionWhenActivityDoesNotExist() {
        Integer userId = 1;
        Integer nonExistentActivityId = 999;

        when(activityRepository.findById(nonExistentActivityId)).thenReturn(Optional.empty());

        assertThrows(com.jin.routina.common.exception.ActivityNotFoundException.class, () -> {
            activityService.deleteActivity(userId, nonExistentActivityId);
        });
    }

    @Test
    void shouldGetActivitiesByHabitSuccessfully() {
        Integer userId = 1;
        Integer habitId = 10;

        User owner = new User();
        owner.setId(userId);

        Habit habit = new Habit();
        habit.setId(habitId);
        habit.setUser(owner);

        Activity act1 = new Activity();
        act1.setId(101);
        act1.setName("Pushups");
        act1.setHabit(habit);

        Activity act2 = new Activity();
        act2.setId(102);
        act2.setName("Pullups");
        act2.setHabit(habit);

        when(habitRepository.findById(habitId)).thenReturn(Optional.of(habit));
        when(activityRepository.findByHabit(habit)).thenReturn(java.util.List.of(act1, act2));

        java.util.List<ActivityResponseDto> result = activityService.getActivitiesByHabit(userId, habitId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Pushups", result.get(0).getName());
        assertEquals("Pullups", result.get(1).getName());
    }

    @Test
    void shouldGetActivityByIdSuccessfully() {
        Integer userId = 1;
        Integer activityId = 100;

        User owner = new User();
        owner.setId(userId);

        Habit habit = new Habit();
        habit.setId(10);
        habit.setUser(owner);

        Activity activity = new Activity();
        activity.setId(activityId);
        activity.setName("Pushups");
        activity.setHabit(habit);

        when(activityRepository.findById(activityId)).thenReturn(Optional.of(activity));

        ActivityResponseDto response = activityService.getActivity(userId, activityId);

        assertNotNull(response);
        assertEquals(activityId, response.getId());
        assertEquals("Pushups", response.getName());
        assertEquals(10, response.getHabitId());
    }
}
