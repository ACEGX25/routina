package com.jin.routina.common.exception;

public class ActivityAlreadyExistsException extends RuntimeException {
    public ActivityAlreadyExistsException(){
        super("Activity already exists");
    }
}
