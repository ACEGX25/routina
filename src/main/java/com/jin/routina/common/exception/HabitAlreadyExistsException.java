package com.jin.routina.common.exception;

public class HabitAlreadyExistsException extends RuntimeException {
    public HabitAlreadyExistsException() {
        super("Habit already exists");
    }
}
