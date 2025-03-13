package com.example.demo.controller;

import com.example.demo.DTO.Request.APIResponse;
import com.example.demo.DTO.Request.AuthenticationRequest;
import com.example.demo.DTO.Request.IntrospectRequest;
import com.example.demo.DTO.Response.AuthenticationResponse;
import com.example.demo.DTO.Response.IntrospectResponse;
import com.example.demo.exceptions.ErrorCode;
import com.example.demo.services.AuthService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthService authService ;

    @PostMapping("/token")
    public APIResponse<AuthenticationResponse>authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        var result = authService.authenticate(authenticationRequest);
        return APIResponse.<AuthenticationResponse>builder()
                .data(result)
                .build();
    }

    @PostMapping("/introspect")
    public APIResponse<IntrospectResponse>authenticate(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        var result = authService.introspect(request);
        return APIResponse.<IntrospectResponse>builder()
                .data(result)
                .build();
    }
}
