package com.usuario.departamento_usuario.services;

import com.usuario.departamento_usuario.DTOs.RequestDTO.CreateUserDTO;
import com.usuario.departamento_usuario.DTOs.RequestDTO.UpdateUserDTO;
import com.usuario.departamento_usuario.DTOs.ResponseDTO.GetUserByIdDTO;
import com.usuario.departamento_usuario.DTOs.ResponseDTO.ListUsersDTO;
import com.usuario.departamento_usuario.exceptions.CustomExceptions.DepartmentNotFoundException;
import com.usuario.departamento_usuario.exceptions.CustomExceptions.UserAlreadyExistsException;
import com.usuario.departamento_usuario.exceptions.CustomExceptions.UserNotFoundException;
import com.usuario.departamento_usuario.models.Department;
import com.usuario.departamento_usuario.models.User;
import com.usuario.departamento_usuario.repositories.DepartmentRepository;
import com.usuario.departamento_usuario.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Transactional
    public CreateUserDTO createUser(CreateUserDTO dto, UUID departmentId){
        //if the email is present, the message is shown
        userRepository.findByEmail(dto.email())
                .ifPresent(u -> { throw new UserAlreadyExistsException("User already exists"); });

        Department department = departmentRepository.findById(departmentId).
                orElseThrow(()-> new DepartmentNotFoundException("Department not found!"));

        User entity = new User();
        entity.setName(dto.name());
        entity.setEmail(dto.email());
        entity.setPassword(dto.password());
        entity.setDepartment(department);

        userRepository.save(entity);
        return dto;
    }

    public List<ListUsersDTO> listUsers(){
        List<User> userEntity = userRepository.findAll();

        return userEntity.stream().map(
                user -> new ListUsersDTO(
                        user.getId(), user.getName(),
                        user.getEmail(), user.getCreatedAt(),
                        user.getUpdatedAt(), user.getDepartment().getId(),
                        user.getDepartment().getDepartmentName(),
                        user.getDepartment().getManagerName())).
                collect(Collectors.toList());
    }

    public GetUserByIdDTO getUserById(UUID id){
        User userEntity = userRepository.findById(id).
                orElseThrow(()-> new UserNotFoundException("User not found!"));

        return new GetUserByIdDTO(userEntity.getName(),
                userEntity.getEmail(),
                userEntity.getCreatedAt(), userEntity.getUpdatedAt(),
                userEntity.getDepartment().getId(), userEntity.getDepartment().getDepartmentName(),
                userEntity.getDepartment().getManagerName());
    }

    @Transactional
    public void deleteUserById(UUID id){
        boolean userExists = userRepository.existsById(id);

        if(!userExists){
            throw new UserNotFoundException("User with id " + id + " was not found!");
        }

        userRepository.deleteById(id);
    }

    @Transactional
    public void updateUser(UUID userId, UUID departmentId, UpdateUserDTO dto){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new UserNotFoundException("User not found!"));

        Department department = departmentRepository.findById(departmentId)
                        .orElseThrow(()-> new DepartmentNotFoundException("Department not found!"));

        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        user.setDepartment(department);

        userRepository.save(user);
    }
}
