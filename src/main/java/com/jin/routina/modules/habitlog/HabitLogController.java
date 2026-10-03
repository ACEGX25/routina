package com.jin.routina.modules.habitlog;

import com.jin.routina.common.ApiResponse;
import com.jin.routina.modules.habitlog.dto.CreateHabitLogRequestDto;
import com.jin.routina.modules.habitlog.dto.HabitLogResponseDto;
import com.jin.routina.modules.habitlog.dto.HabitStatsResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/habit-logs")
@RequiredArgsConstructor
public class HabitLogController {
    private final HabitLogService habitLogService;

    @PostMapping
    public ResponseEntity<ApiResponse<HabitLogResponseDto>> createHabitLog(@AuthenticationPrincipal String id, @Valid @RequestBody CreateHabitLogRequestDto request) {
        Integer userId = Integer.parseInt(id);
        HabitLogResponseDto habitLogResponseDto = habitLogService.createHabitLog(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Habit Logged Successfully", habitLogResponseDto));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<HabitLogResponseDto>>> getHabitLog(@AuthenticationPrincipal String id, @RequestParam Integer habitId) {
        Integer userId = Integer.parseInt(id);
        List<HabitLogResponseDto> logs = habitLogService.getHabitLog(userId, habitId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Habit Log List", logs));
    }

    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<HabitStatsResponseDto>> getHabitStats(@AuthenticationPrincipal String id, @RequestParam Integer habitId) {
        Integer userId = Integer.parseInt(id);
        HabitStatsResponseDto stats = habitLogService.getHabitStats(userId, habitId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Habit Stats", stats));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteHabitLog(@AuthenticationPrincipal String principal, @PathVariable("id") Integer id) {
        Integer userId = Integer.parseInt(principal);
        habitLogService.deleteHabitLog(userId, id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Habit Log Deleted Successfully", null));
    }
}
