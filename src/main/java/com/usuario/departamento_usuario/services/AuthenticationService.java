package com.usuario.departamento_usuario.services;

import com.usuario.departamento_usuario.DTOs.AuthenticationDTO.LoginAuthDTO;
import com.usuario.departamento_usuario.DTOs.AuthenticationDTO.RegisterAuthDTO;
import com.usuario.departamento_usuario.DTOs.AuthenticationDTO.RegisterResponseDTO;
import com.usuario.departamento_usuario.DTOs.AuthenticationDTO.TokenDTO;
import com.usuario.departamento_usuario.exceptions.CustomExceptions.DepartmentNotFoundException;
import com.usuario.departamento_usuario.exceptions.CustomExceptions.UserAlreadyExistsException;
import com.usuario.departamento_usuario.models.Department;
import com.usuario.departamento_usuario.models.User;
import com.usuario.departamento_usuario.repositories.DepartmentRepository;
import com.usuario.departamento_usuario.repositories.UserRepository;
import com.usuario.departamento_usuario.security.Service.TokenService;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    private DepartmentRepository departmentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public TokenDTO login(LoginAuthDTO dto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.email(), dto.password()
                )
        );
        User user = (User) authentication.getPrincipal();
        String token = tokenService.generateToken(user);
        return new TokenDTO(token);
    }

    public RegisterResponseDTO register(RegisterAuthDTO dto){
        if (userRepository.existsByEmail(dto.email())) {
            throw new UserAlreadyExistsException("This email is already registered");
        }

        Department department = departmentRepository.findByDepartmentName(dto.department())
                .orElseThrow(()-> new DepartmentNotFoundException("Department not found!"));

        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRole(dto.role());
        user.setDepartment(department);

        User savedUser = userRepository.save(user);
        return new RegisterResponseDTO(savedUser.getId(), savedUser.getEmail(), savedUser.getRole());
    }
}
