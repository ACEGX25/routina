package com.jin.routina.modules.habits;

import com.jin.routina.common.exception.ForbiddenException;
import com.jin.routina.common.exception.HabitAlreadyExistsException;
import com.jin.routina.common.exception.HabitNotFoundException;
import com.jin.routina.common.exception.UserNotFoundException;
import com.jin.routina.modules.habits.dto.CreateHabitRequestDto;
import com.jin.routina.modules.habits.dto.HabitResponseDto;
import com.jin.routina.modules.user.User;
import com.jin.routina.modules.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class HabitService {
    private final UserRepository userRepository;
    private final HabitRepository habitRepository;

    public HabitResponseDto create(Integer userId, CreateHabitRequestDto createHabitRequestDto) {
        LocalDateTime now = LocalDateTime.now();
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        if (habitRepository.existsByUserAndName(user, createHabitRequestDto.getName())) {
            throw new HabitAlreadyExistsException();
        }

        Habit habit = new Habit();
        habit.setUser(user);
        habit.setName(createHabitRequestDto.getName());
        habit.setCreatedAt(now);
        habit.setUpdatedAt(now);

        Habit savedHabit = habitRepository.save(habit);

        return mapToResponseDto(savedHabit);
    }

    public List<HabitResponseDto> getHabits(Integer userId) {
        List<Habit> habits = habitRepository.findByUserId(userId);
        return habits.stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    public HabitResponseDto getHabit(Integer userId, Integer habitId) {
        Habit habit = verifyHabitOwnership(userId, habitId);
        return mapToResponseDto(habit);
    }

    public void deleteHabit(Integer userId, Integer habitId) {
        Habit habit = verifyHabitOwnership(userId, habitId);
        habitRepository.delete(habit);
    }

    public HabitResponseDto updateHabit(Integer userId, Integer habitId, CreateHabitRequestDto request) {
        Habit habit = verifyHabitOwnership(userId, habitId);
        if (!habit.getName().equalsIgnoreCase(request.getName()) && habitRepository.existsByUserIdAndName(userId, request.getName())) {
            throw new HabitAlreadyExistsException();
        }
        habit.setName(request.getName());
        habit.setUpdatedAt(LocalDateTime.now());
        Habit savedHabit = habitRepository.save(habit);

        return mapToResponseDto(savedHabit);
    }

    public Habit verifyHabitOwnership(Integer userId, Integer habitId) {
        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(HabitNotFoundException::new);
        if (!habit.getUser().getId().equals(userId)) {
            throw new ForbiddenException();
        }
        return habit;
    }


    private HabitResponseDto mapToResponseDto(Habit habit) {
        HabitResponseDto habitResponseDto = new HabitResponseDto();
        habitResponseDto.setId(habit.getId());
        habitResponseDto.setName(habit.getName());
        habitResponseDto.setCreatedAt(habit.getCreatedAt());
        habitResponseDto.setUpdatedAt(habit.getUpdatedAt());

        return habitResponseDto;
    }
}
