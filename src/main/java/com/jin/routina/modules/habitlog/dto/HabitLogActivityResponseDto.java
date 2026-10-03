package com.jin.routina.modules.habitlog.dto;

import lombok.Data;

@Data
public class HabitLogActivityResponseDto {
    private Integer activityId;
    private String activityName;
    private Boolean completed;
}
