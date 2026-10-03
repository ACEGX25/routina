package com.jin.routina.modules.habits.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HabitResponseDto {
    private Integer id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
