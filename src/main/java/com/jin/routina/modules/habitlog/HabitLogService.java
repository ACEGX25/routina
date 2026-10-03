package com.jin.routina.modules.habitlog;

import com.jin.routina.common.exception.ActivityNotFoundException;
import com.jin.routina.common.exception.ForbiddenException;
import com.jin.routina.common.exception.HabitLogNotFoundException;
import com.jin.routina.common.exception.HabitNotFoundException;
import com.jin.routina.modules.activities.Activity;
import com.jin.routina.modules.activities.ActivityRepository;
import com.jin.routina.modules.habitlog.dto.*;
import com.jin.routina.modules.habits.Habit;
import com.jin.routina.modules.habits.HabitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class HabitLogService {
    private final HabitLogRepository habitLogRepository;
    private final HabitRepository habitRepository;
    private final HabitLogActivityRepository habitLogActivityRepository;
    private final ActivityRepository activityRepository;

    public HabitLogResponseDto createHabitLog(Integer userId, CreateHabitLogRequestDto request){
        LocalDateTime now = LocalDateTime.now();
        Habit habit = verifyHabitOwnership(userId, request.getHabitId());
        HabitLog habitLog = new HabitLog();
        habitLog.setHabit(habit);
        habitLog.setLogDate(request.getLogDate());
        habitLog.setStartTime(request.getStartTime());
        habitLog.setEndTime(request.getEndTime());
        habitLog.setCreatedAt(now);

        HabitLog savedLog = habitLogRepository.save(habitLog);

        if(request.getActivities() != null){
            for(ActivityLogItemDto item : request.getActivities()){
                Activity activity = activityRepository.findByIdAndHabit(item.getId(), habit).orElseThrow(ActivityNotFoundException::new);
                if(!habitLogActivityRepository.existsByHabitLogAndActivity(savedLog, activity)){
                    HabitLogActivity habitLogActivity = new HabitLogActivity();
                    habitLogActivity.setHabitLog(savedLog);
                    habitLogActivity.setActivity(activity);
                    habitLogActivity.setCompleted(item.getCompleted()!=null ? item.getCompleted() : true);
                    habitLogActivityRepository.save(habitLogActivity);
                }
            }
        }

        return mapToHabitLogDto(savedLog);
    }

    public List<HabitLogResponseDto> getHabitLog(Integer userId,Integer habitId){
        Habit habit = verifyHabitOwnership(userId, habitId);
        List<HabitLogResponseDto> habits = habitLogRepository.findByHabitOrderByLogDateDesc(habit)
                .stream()
                .map(this::mapToHabitLogDto)
                .toList();

        return habits;
    }

    public void deleteHabitLog(Integer userId, Integer logId){
        HabitLog log = habitLogRepository.findById(logId).orElseThrow(HabitLogNotFoundException::new);
        if(!log.getHabit().getUser().getId().equals(userId)){
            throw new ForbiddenException();
        }
        habitLogActivityRepository.deleteByHabitLog(log);
        habitLogRepository.delete(log);
    }

    public HabitStatsResponseDto getHabitStats(Integer userId, Integer habitId){
        Habit habit = verifyHabitOwnership(userId, habitId);
        List<HabitLog> logs = habitLogRepository.findByHabitOrderByLogDateDesc(habit);
        if(logs.isEmpty()){
            HabitStatsResponseDto empty = new HabitStatsResponseDto();
            empty.setHabitId(habitId);
            empty.setCurrentStreak(0);
            empty.setLongestStreak(0);
            empty.setTotalDaysStreak(0);
            return empty;
        }
        Set<LocalDate> loggedDates = logs.stream()
                .map(HabitLog::getLogDate)
                .collect(Collectors.toSet());

        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        int currentStreak = 0;
        LocalDate checkDate;

        if(loggedDates.contains(today)){
            checkDate = today;
        }else if(loggedDates.contains(yesterday)){
            checkDate = yesterday;
        }else{
            checkDate = null;
        }

        while(checkDate!=null && loggedDates.contains(checkDate)){
            currentStreak++;
            checkDate = checkDate.minusDays(1);
        }

        List<LocalDate> sortedDates = loggedDates.stream().sorted().toList();
        int totalDaysStreak = loggedDates.size();
        int longestStreak = 0;
        int runningStreak =0;

        LocalDate prevDate = null;

        for(LocalDate date : sortedDates){
            if(prevDate!=null && date.equals(prevDate.plusDays(1))){
                runningStreak++;
            }else{
                runningStreak = 1;
            }
            longestStreak = Math.max(longestStreak, runningStreak);
            prevDate = date;
        }

        HabitStatsResponseDto stats = new HabitStatsResponseDto();
        stats.setHabitId(habitId);
        stats.setCurrentStreak(currentStreak);
        stats.setLongestStreak(longestStreak);
        stats.setTotalDaysStreak(totalDaysStreak);
        return stats;
    }

    private HabitLogResponseDto mapToHabitLogDto(HabitLog habitLog){
        HabitLogResponseDto dto = new HabitLogResponseDto();
        dto.setId(habitLog.getId());
        dto.setHabitId(habitLog.getHabit().getId());
        dto.setHabitName(habitLog.getHabit().getName());
        dto.setLogDate(habitLog.getLogDate());
        dto.setStartTime(habitLog.getStartTime());
        dto.setEndTime(habitLog.getEndTime());
        dto.setCreatedAt(habitLog.getCreatedAt());
        List<HabitLogActivityResponseDto> activities = habitLogActivityRepository.findByHabitLog(habitLog)
                .stream()
                .map(this::mapActivityToDto)
                .toList();

        dto.setActivities(activities);

        return dto;
    }

    private HabitLogActivityResponseDto mapActivityToDto(HabitLogActivity activity){
        HabitLogActivityResponseDto dto = new HabitLogActivityResponseDto();
        dto.setActivityId(activity.getActivity().getId());
        dto.setActivityName(activity.getActivity().getName());
        dto.setCompleted(activity.isCompleted());
        return dto;

    }

    private Habit verifyHabitOwnership(Integer userId, Integer habitId) {
        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(HabitNotFoundException::new);
        if (!habit.getUser().getId().equals(userId)) {
            throw new ForbiddenException();
        }
        return habit;
    }
}
