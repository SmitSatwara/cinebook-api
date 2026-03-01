package com.smitsatwara.cinebook.service;


import com.smitsatwara.cinebook.dto.AuthResponse;
import com.smitsatwara.cinebook.dto.LoginRequest;
import com.smitsatwara.cinebook.dto.RegisterRequest;
import com.smitsatwara.cinebook.model.User;
import com.smitsatwara.cinebook.model.UserRole;
import com.smitsatwara.cinebook.repository.UserRepository;
import com.smitsatwara.cinebook.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest registerRequest){
        if(userRepository.findByEmail(registerRequest.getEmail()).isPresent()){
            throw new RuntimeException("Email already in use");
        }
        User user = new User();
        user.setUserName(registerRequest.getUserName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(UserRole.USER);
        User savedUser=userRepository.save(user);
        String token = jwtUtil.generateToken(savedUser.getEmail());

        return new AuthResponse(token,savedUser.getEmail(),UserRole.USER.name());
    }
    public AuthResponse login(LoginRequest loginRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword())
        );
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found with email: " + loginRequest.getEmail()));
        String token = jwtUtil.generateToken(loginRequest.getEmail());

        return new AuthResponse(token,user.getEmail(),user.getRole().name());

    }

}

