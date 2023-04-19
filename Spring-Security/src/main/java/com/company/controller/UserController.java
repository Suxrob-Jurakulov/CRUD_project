package com.company.controller;

import com.company.dto.UserDTO;
import com.company.service.AuthService;
import com.company.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthService authService;


    @PostMapping("/adm/create_by_admin")
    public ResponseEntity<String> createByAdmin(@RequestBody UserDTO dto){
        String response = authService.register(dto);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/public/update")
    public ResponseEntity<String> update(@RequestBody UserDTO dto){
        String response = userService.update(dto);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/adm/get/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable("id") Integer id){
        UserDTO dto = userService.get(id);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/adm/get_all")
    public ResponseEntity<List<UserDTO>> getAll(){
        List<UserDTO> dtos = userService.dtoList();
        return ResponseEntity.ok().body(dtos);
    }

    @DeleteMapping("/adm/delete")
    public ResponseEntity<String> deleteByAdmin(@RequestParam("id") Integer id){
        String response = userService.deleteByAdmin(id);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/public/delete")
    public ResponseEntity<String> deleteByUser(){
        String response = userService.deleteByUser();
        return ResponseEntity.ok().body(response);
    }

}
