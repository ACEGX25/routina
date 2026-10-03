package com.jin.routina.common.exception;

public class HabitNotFoundException extends RuntimeException {
    public HabitNotFoundException() {
        super("Habit not found");
    }
}
