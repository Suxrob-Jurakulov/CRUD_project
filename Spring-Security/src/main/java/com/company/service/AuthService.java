package com.company.service;

import com.company.config.JwtService;
import com.company.dto.AuthDTO;
import com.company.dto.ProfileDTO;
import com.company.dto.UserDTO;
import com.company.entity.UserEntity;
import com.company.enums.Role;
import com.company.exp.BadRequestException;
import com.company.exp.ItemNotFoundException;
import com.company.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {


    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;


    public String register(UserDTO dto) {
        Optional<UserEntity> byUsername = userRepository.findByUsername(dto.getUsername());
        if (byUsername.isPresent()) {
            throw new BadRequestException("This Username already busy");
        }
        UserEntity entity = new UserEntity();
        entity.setName(dto.getName());
        entity.setUsername(dto.getUsername());
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity.setRole(Role.ROLE_USER);
        userRepository.save(entity);
        return "Successfully created";
    }

    public ProfileDTO login(AuthDTO authDTO) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authDTO.getUsername(), authDTO.getPassword()));
        if (!auth.isAuthenticated()) {
            throw new ItemNotFoundException("Invalid user request");
        }
        String jwt = jwtService.generateToken(authDTO.getUsername());
        ProfileDTO dto = new ProfileDTO();
        dto.setName(authDTO.getUsername());
        dto.setJwt(jwt);
        return dto;
    }
}
