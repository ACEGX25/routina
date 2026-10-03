package com.jin.routina.modules.habitlog.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class HabitLogResponseDto {
    private Integer id;
    private Integer habitId;
    private String habitName;
    private LocalDate logDate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime createdAt;
    @Valid
    private List<HabitLogActivityResponseDto> activities;
}
