package com.jin.routina.modules.habits;

import com.jin.routina.common.ApiResponse;
import com.jin.routina.modules.habits.dto.CreateHabitRequestDto;
import com.jin.routina.modules.habits.dto.HabitResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/habits")
@RequiredArgsConstructor
public class HabitController {
    private final HabitService habitService;

    @PostMapping
    public ResponseEntity<ApiResponse<HabitResponseDto>> createHabit(@AuthenticationPrincipal String principal, @Valid @RequestBody CreateHabitRequestDto request) {
        Integer userId = Integer.parseInt(principal);
        HabitResponseDto habitResponseDto = habitService.create(userId,request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Habit Created Successfully",habitResponseDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<HabitResponseDto>> updateHabit(@AuthenticationPrincipal String principal,@PathVariable Integer id ,@Valid @RequestBody CreateHabitRequestDto request) {
        Integer userId = Integer.parseInt(principal);
        HabitResponseDto habitResponseDto = habitService.updateHabit(userId,id,request);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Habit Updated Successfully",habitResponseDto));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<HabitResponseDto>>> getHabits(@AuthenticationPrincipal String principal){
        Integer userId = Integer.parseInt(principal);
        List<HabitResponseDto> habits = habitService.getHabits(userId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Habits Fetched Successfully",habits));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<HabitResponseDto>> getHabitById(@AuthenticationPrincipal String principal, @PathVariable Integer id){
        Integer userId = Integer.parseInt(principal);
        HabitResponseDto habitResponseDto = habitService.getHabit(userId,id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Habit Fetched Successfully",habitResponseDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteHabitById(@AuthenticationPrincipal String principal, @PathVariable Integer id){
        Integer userId = Integer.parseInt(principal);
        habitService.deleteHabit(userId,id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Habit Deleted Successfully",null));
    }

}
