package com.jin.routina.modules.habitlog.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ActivityLogItemDto {
    @NotNull(message = "Activity ID is required")
    private Integer id;
    private Boolean completed= true;
}
