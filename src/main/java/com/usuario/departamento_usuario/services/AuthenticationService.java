package com.usuario.departamento_usuario.services;

import com.usuario.departamento_usuario.DTOs.AuthenticationDTO.LoginAuthDTO;
import com.usuario.departamento_usuario.DTOs.AuthenticationDTO.RegisterAuthDTO;
import com.usuario.departamento_usuario.DTOs.AuthenticationDTO.RegisterResponseDTO;
import com.usuario.departamento_usuario.DTOs.AuthenticationDTO.TokenDTO;
import com.usuario.departamento_usuario.models.User;
import com.usuario.departamento_usuario.repositories.UserRepository;
import com.usuario.departamento_usuario.security.SecurityConfiguration;
import com.usuario.departamento_usuario.security.TokenService;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public TokenDTO login(LoginAuthDTO dto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.email(), dto.password()
                )
        );
        String token = tokenService.generateToken((User) authentication);
        return new TokenDTO(token);
    }

    public RegisterResponseDTO register(RegisterAuthDTO dto){
        if (userRepository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("This email is already registered");
        }

        User user = new User();
        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRole(dto.role());

        User savedUser = userRepository.save(user);
        return new RegisterResponseDTO(savedUser.getId(), savedUser.getEmail(), savedUser.getRole());
    }
}
