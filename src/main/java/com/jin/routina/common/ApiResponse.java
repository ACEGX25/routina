package com.jin.routina.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    public static <T> ApiResponse<T> success(String message, T data){
        return new ApiResponse<>(true,message,data,LocalDateTime.now());
    }

    public static  <T> ApiResponse<T> error(String message){
        return new ApiResponse<>(false, message, null, LocalDateTime.now());
    }

    public static ApiResponse<Map<String,String>> validationError(Map<String, String> errors){
        return new ApiResponse<>(false,"Validation Failed",errors,LocalDateTime.now());
    }
}
