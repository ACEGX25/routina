package com.jin.routina.modules.activities;

import com.jin.routina.common.ApiResponse;
import com.jin.routina.modules.activities.dto.ActivityResponseDto;
import com.jin.routina.modules.activities.dto.CreateActivityDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activities")
@RequiredArgsConstructor
public class ActivityController {
    private final ActivityService activityService;
    @PostMapping
    public ResponseEntity<ApiResponse<ActivityResponseDto>> createActivity(@AuthenticationPrincipal String Id,@Valid @RequestBody CreateActivityDto createActivityDto) {
        Integer userId = Integer.parseInt(Id);
        ActivityResponseDto activityResponseDto = activityService.createActivity(userId,createActivityDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Activity Created Successfully",activityResponseDto));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ActivityResponseDto>>> getActivities(@AuthenticationPrincipal String Id, @RequestParam Integer habitId) {
        Integer userId = Integer.parseInt(Id);
        List <ActivityResponseDto> activities = activityService.getActivitiesByHabit(userId,habitId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Activities Fetched Successfully",activities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ActivityResponseDto>> getActivity(
            @AuthenticationPrincipal String principal,
            @PathVariable Integer id) {
        Integer userId = Integer.parseInt(principal);
        ActivityResponseDto activity = activityService.getActivity(userId, id);
        return ResponseEntity.ok(ApiResponse.success("Activity Fetched Successfully", activity));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteActivity(
            @AuthenticationPrincipal String principal,
            @PathVariable Integer id) {
        Integer userId = Integer.parseInt(principal);
        activityService.deleteActivity(userId, id);
        return ResponseEntity.ok(ApiResponse.success("Activity Deleted Successfully", null));
    }

}
