package com.jin.routina.modules.user.dto;

import lombok.Data;

@Data
public class AuthResponseDto {
    private String token;
    private Integer id;
    private String email;
    private String name;

}
