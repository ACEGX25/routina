package com.jin.routina.common.exception;

public class HabitLogNotFoundException extends RuntimeException {
    public HabitLogNotFoundException() {
        super("HabitLog not found");
    }
}
