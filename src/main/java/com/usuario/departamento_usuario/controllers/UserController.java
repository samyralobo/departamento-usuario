package com.usuario.departamento_usuario.controllers;

import com.usuario.departamento_usuario.DTOs.RequestDTO.CreateUserDTO;
import com.usuario.departamento_usuario.DTOs.ResponseDTO.GetUserByIdDTO;
import com.usuario.departamento_usuario.DTOs.ResponseDTO.ListUsersDTO;
import com.usuario.departamento_usuario.DTOs.RequestDTO.UpdateUserDTO;
import com.usuario.departamento_usuario.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/{id}")
    public ResponseEntity<?> createUser (@RequestBody CreateUserDTO dto,
                                         @PathVariable("id") UUID departmentId){
            userService.createUser(dto, departmentId);
            return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<ListUsersDTO>> listUsers(){
        List<ListUsersDTO> users = userService.listUsers();
        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") UUID id){
        GetUserByIdDTO user = userService.getUserById(id);
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable("id") UUID id){
        userService.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{userId}/{departmentId}")
    public ResponseEntity<?> updateUser(@PathVariable("userId") UUID userId,
                                        @PathVariable("departmentId") UUID departmentId,
                                        @RequestBody UpdateUserDTO dto){
        userService.updateUser(userId, departmentId, dto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
