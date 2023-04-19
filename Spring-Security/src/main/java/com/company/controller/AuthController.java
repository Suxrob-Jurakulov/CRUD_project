package com.company.controller;

import com.company.dto.AuthDTO;
import com.company.dto.ProfileDTO;
import com.company.dto.UserDTO;
import com.company.service.AuthService;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/public/register")
    public ResponseEntity<String> register(@RequestBody UserDTO dto) {
        String response = authService.register(dto);
        return ResponseEntity.ok().body(response);
    }


    @PostMapping("/public/login")
    public ResponseEntity<ProfileDTO> login(@RequestBody AuthDTO dto) {
        ProfileDTO response = authService.login(dto);
        return ResponseEntity.ok().body(response);
    }
}
