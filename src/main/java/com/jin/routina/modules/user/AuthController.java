package com.jin.routina.modules.user;

import com.jin.routina.common.ApiResponse;
import com.jin.routina.common.exception.InvalidCredentialsException;
import com.jin.routina.modules.user.dto.AuthResponseDto;
import com.jin.routina.modules.user.dto.LoginRequestDto;
import com.jin.routina.modules.user.dto.RegisterRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponseDto>> register(@Valid @RequestBody RegisterRequestDto request) {
        AuthResponseDto authResponseDto = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("User registered successfully", authResponseDto));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponseDto>> login(@Valid @RequestBody LoginRequestDto request) {
        AuthResponseDto authResponseDto = authService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("User logged in successfully", authResponseDto));
    }
}
