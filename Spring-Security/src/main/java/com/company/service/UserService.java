package com.company.service;

import com.company.dto.UserDTO;
import com.company.entity.UserEntity;
import com.company.exp.BadRequestException;
import com.company.exp.ItemNotFoundException;
import com.company.repository.UserRepository;
import com.company.util.CurrentUserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String update(UserDTO dto) {
        Optional<UserEntity> byUsername = userRepository.findByUsername(getCurrentUser().getUsername());

        if (byUsername.isEmpty()) {
            throw new BadRequestException("Something went wrong");
        }

        UserEntity entity = byUsername.get();
        entity.setName(dto.getName());
        entity.setUsername(dto.getUsername());
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(entity);

        return "Successfully updated";
    }

    public UserDTO get(Integer id) {
        Optional<UserEntity> optional = userRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("User not found");
        }
        return getDto(optional.get());
    }

    public List<UserDTO> dtoList() {
        List<UserEntity> allEntities = userRepository.findAll();
        List<UserDTO> dtos = new ArrayList<>();
        for (UserEntity allEntity : allEntities) {
            dtos.add(getDto(allEntity));
        }
        return dtos;
    }

    public String deleteByUser() {
        userRepository.deleteByUsername(getCurrentUser().getUsername());
        return "Your account was deleted";
    }

    public String deleteByAdmin(Integer id) {
        Optional<UserEntity> byId = userRepository.findById(id);
        if (byId.isEmpty()) {
            throw new ItemNotFoundException("User not found");
        }
        userRepository.deleteById(id);
        return "Successfully deleted";
    }

    public UserEntity getCurrentUser() {
        String username = CurrentUserUtil.currentUser().getUsername();
        Optional<UserEntity> optional = userRepository.findByUsername(username);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("User not found");
        }
        return optional.get();
    }

    private UserDTO getDto(UserEntity entity) {
        return UserDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .username(entity.getUsername())
                .role(entity.getRole().name())
                .build();
    }
}
