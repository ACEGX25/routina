package com.jin.routina.modules.user;

import com.jin.routina.common.exception.EmailAlreadyExistsException;
import com.jin.routina.modules.user.dto.AuthResponseDto;
import com.jin.routina.modules.user.dto.RegisterRequestDto;
import com.jin.routina.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthMethodRepository authMethodRepository;
    private final JwtUtil jwtUtil;
    
    LocalDateTime now = LocalDateTime.now();

    public AuthResponseDto register(RegisterRequestDto request){
        if(userRepository.existsByEmail(request.getEmail())){
            throw new EmailAlreadyExistsException(request.getEmail());
        }
        String hashedPassword = passwordEncoder.encode(request.getPassword());

        User newUser = new User();
        newUser.setEmail(request.getEmail());
        newUser.setName(request.getName());
        newUser.setCreatedAt(now);
        newUser.setUpdatedAt(now);

        User savedUser = userRepository.save(newUser);

        AuthMethod authMethod = new AuthMethod();
        authMethod.setUser(savedUser);
        authMethod.setAuthType(AuthType.PASSWORD);
        authMethod.setPasswordHash(hashedPassword);
        authMethod.setGoogleId(null);
        authMethod.setCreatedAt(now);
        authMethod.setUpdatedAt(now);

        authMethodRepository.save(authMethod);

        String token = jwtUtil.generateToken(savedUser.getId().toString());

        AuthResponseDto authResponseDto = new AuthResponseDto();
        authResponseDto.setToken(token);
        authResponseDto.setId(savedUser.getId());
        authResponseDto.setEmail(savedUser.getEmail());
        authResponseDto.setName(savedUser.getName());

        return authResponseDto;
    }

}
