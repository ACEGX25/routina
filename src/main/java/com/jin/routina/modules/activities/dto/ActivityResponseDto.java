package com.jin.routina.modules.activities.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ActivityResponseDto {
    private Integer id;
    private String name;
    private Integer habitId;
    private LocalDateTime createdAt;
}
