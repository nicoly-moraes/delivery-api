package com.delivery.api.controllers;

import com.delivery.api.dtos.LoginResponseDto;
import com.delivery.api.dtos.LoginDto;
import com.delivery.api.services.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> authenticate(@Valid @RequestBody LoginDto loginUserDto) {
        return ResponseEntity.ok(authenticationService.authenticate(loginUserDto));
    }
}
