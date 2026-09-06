package com.jin.routina.modules.user;

import com.jin.routina.modules.user.dto.AuthResponseDto;
import com.jin.routina.modules.user.dto.RegisterRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@Valid @RequestBody RegisterRequestDto request){
        AuthResponseDto authResponseDto = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(authResponseDto);
    }
}
