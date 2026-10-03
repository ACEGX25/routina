package com.jin.routina.modules.activities.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateActivityDto {
    @NotNull(message = "Habit Id is required")
    private Integer habitId;


    @NotBlank(message = "Name is required")
    @Size(max = 255)
    private String name;
}
