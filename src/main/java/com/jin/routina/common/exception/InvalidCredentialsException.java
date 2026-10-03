package com.jin.routina.common.exception;

public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException(){
        super("Invalid email or password");
    }
}
