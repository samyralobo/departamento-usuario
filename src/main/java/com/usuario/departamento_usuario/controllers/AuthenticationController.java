package com.usuario.departamento_usuario.controllers;

import com.usuario.departamento_usuario.DTOs.AuthenticationDTO.LoginAuthDTO;
import com.usuario.departamento_usuario.DTOs.AuthenticationDTO.RegisterAuthDTO;
import com.usuario.departamento_usuario.DTOs.AuthenticationDTO.RegisterResponseDTO;
import com.usuario.departamento_usuario.DTOs.AuthenticationDTO.TokenDTO;
import com.usuario.departamento_usuario.repositories.UserRepository;
import com.usuario.departamento_usuario.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginAuthDTO dto){
        try {
            TokenDTO token = authenticationService.login(dto);
            return ResponseEntity.ok(token);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error! Check if the user is " +
                    "already registered, if not, try again! ");
        }

    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterAuthDTO dto){
        RegisterResponseDTO response = authenticationService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
