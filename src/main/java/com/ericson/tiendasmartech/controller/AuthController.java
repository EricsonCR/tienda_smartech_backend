package com.ericson.tiendasmartech.controller;

import com.ericson.tiendasmartech.dto.AuthDto;
import com.ericson.tiendasmartech.model.ControllerResponse;
import com.ericson.tiendasmartech.model.ServiceResponse;
import com.ericson.tiendasmartech.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ControllerResponse> login(@RequestBody AuthDto authDto) {
        ControllerResponse response = new ControllerResponse();
        ServiceResponse serviceResponse = authService.login(authDto);
        response.setStatus(serviceResponse.getStatus());
        response.setMessage(serviceResponse.getMessage());
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + serviceResponse.getData());
        return ResponseEntity.ok().headers(headers).body(response);
    }

    @PostMapping("/registrar")
    public ResponseEntity<ControllerResponse> register(@RequestBody AuthDto authDto) {
        ControllerResponse response = new ControllerResponse();
        ServiceResponse serviceResponse = authService.register(authDto);
        response.setStatus(serviceResponse.getStatus());
        response.setMessage(serviceResponse.getMessage());
        response.setData(serviceResponse.getData());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/verifyEmail/{token}")
    public ResponseEntity<ControllerResponse> verifyEmail(@PathVariable String token) {
        ControllerResponse response = new ControllerResponse();
        ServiceResponse serviceResponse = authService.verifyEmail(token);
        response.setStatus(serviceResponse.getStatus());
        response.setMessage(serviceResponse.getMessage());
        response.setData(serviceResponse.getData());
        return ResponseEntity.ok(response);
    }
}
