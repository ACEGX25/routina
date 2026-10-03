package com.jin.routina.modules.habits.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateHabitRequestDto {
    @NotBlank @Size(max=255)
    private String name;
}
