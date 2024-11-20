package com.delivery.api.services;

import com.delivery.api.dtos.LoginResponseDto;
import com.delivery.api.dtos.LoginDto;
import com.delivery.api.dtos.RegisterUserDto;
import com.delivery.api.entities.User;
import com.delivery.api.entities.UserType;
import com.delivery.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    public User signup(RegisterUserDto input) {
        var user = new User();
        user.setEmail(input.email());
        user.setPassword(passwordEncoder.encode(input.password()));
        user.setType(UserType.CUSTOMER);

        return userRepository.save(user);
    }

    public LoginResponseDto authenticate(LoginDto input) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                input.email(),
                input.password()
            )
        );

        User user = userRepository.findByEmail(input.email()).orElseThrow();

        String jwtToken = jwtService.generateToken(user);

        return new LoginResponseDto(user.getId(), user.getEmail(), user.getType(), jwtToken);
    }
}
