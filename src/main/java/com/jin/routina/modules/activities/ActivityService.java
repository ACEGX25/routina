package com.jin.routina.modules.activities;

import com.jin.routina.common.exception.ActivityNotFoundException;
import com.jin.routina.common.exception.ForbiddenException;
import com.jin.routina.common.exception.HabitNotFoundException;
import com.jin.routina.modules.activities.dto.ActivityResponseDto;
import com.jin.routina.modules.activities.dto.CreateActivityDto;
import com.jin.routina.modules.habits.Habit;
import com.jin.routina.modules.habits.HabitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final HabitRepository habitRepository;

    public ActivityResponseDto createActivity(Integer userId, CreateActivityDto request) {
        LocalDateTime now = LocalDateTime.now();
        Habit habit = verifyHabitOwnership(userId, request.getHabitId());

        Activity activity = new Activity();
        activity.setHabit(habit);
        activity.setName(request.getName());
        activity.setCreatedAt(now);
        Activity savedActivity = activityRepository.save(activity);

        return mapResponseToDto(savedActivity);
    }

    public List<ActivityResponseDto> getActivitiesByHabit(Integer userId, Integer habitId) {
        Habit habit = verifyHabitOwnership(userId, habitId);
        List<Activity> activities = activityRepository.findByHabit(habit);
        return activities.stream().map(this::mapResponseToDto).toList();
    }

    public void deleteActivity(Integer userId, Integer activityId) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(ActivityNotFoundException::new);

        if (!activity.getHabit().getUser().getId().equals(userId)) {
            throw new ForbiddenException();
        }
        activityRepository.delete(activity);
    }

    public ActivityResponseDto getActivity(Integer userId, Integer activityId) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(ActivityNotFoundException::new);
        if (!activity.getHabit().getUser().getId().equals(userId)) {
            throw new ForbiddenException();
        }
        return mapResponseToDto(activity);
    }

    private Habit verifyHabitOwnership(Integer userId, Integer habitId) {
        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(HabitNotFoundException::new);
        if (!habit.getUser().getId().equals(userId)) {
            throw new ForbiddenException();
        }
        return habit;
    }

    private ActivityResponseDto mapResponseToDto(Activity activity) {
        ActivityResponseDto dto = new ActivityResponseDto();
        dto.setId(activity.getId());
        dto.setName(activity.getName());
        dto.setHabitId(activity.getHabit().getId());
        dto.setCreatedAt(activity.getCreatedAt());

        return dto;
    }
}
