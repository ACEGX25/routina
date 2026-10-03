package com.jin.routina.modules.habitlog.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Data
public class CreateHabitLogRequestDto {
    @NotNull(message = "Habit Id Required")
    private Integer habitId;

    @NotNull(message = "Log Date is Required")
    private LocalDate logDate;

    @NotNull(message = "Start time is required")
    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private List<ActivityLogItemDto> activities;
}
