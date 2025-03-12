package com.example.demo.controller;

import com.example.demo.DTO.Request.APIResponse;
import com.example.demo.DTO.Request.AuthenticationRequest;
import com.example.demo.DTO.Response.AuthenticationResponse;
import com.example.demo.services.AuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthService authService ;

    @PostMapping("/log-in")
    public APIResponse<AuthenticationResponse>authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        boolean result = authService.authenticate(authenticationRequest);
        return APIResponse.<AuthenticationResponse>builder()
                .data(AuthenticationResponse.builder()
                        .authenticated(result)
                        .build())
                .build();
    }
}
