package com.jin.routina.modules.habitlog.dto;

import lombok.Data;

@Data
public class HabitStatsResponseDto {
    private Integer habitId;
    private Integer currentStreak;
    private Integer longestStreak;
    private Integer totalDaysStreak;
}
