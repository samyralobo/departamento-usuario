package com.usuario.departamento_usuario.services;

import com.usuario.departamento_usuario.DTOs.RequestDTO.CreateDepartmentDTO;
import com.usuario.departamento_usuario.DTOs.RequestDTO.UpdateDepartmentDTO;
import com.usuario.departamento_usuario.DTOs.ResponseDTO.ListDepartmentsDTO;
import com.usuario.departamento_usuario.DTOs.ResponseDTO.UserDepartmentDTO;
import com.usuario.departamento_usuario.exceptions.CustomExceptions.DepartmentNotFoundException;
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
import java.util.stream.Stream;

@Service
public class DepartmentService {
    @Autowired
    private  DepartmentRepository departmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public CreateDepartmentDTO createDepartment(CreateDepartmentDTO dto){
        Department department = departmentRepository.findByDepartmentName(dto.departmentName())
                .orElseGet(()-> {
                    Department departmentEntity = new Department();
                    departmentEntity.setDepartmentName(dto.departmentName());
                    departmentEntity.setManagerName(dto.managerName());

                    return departmentRepository.save(departmentEntity);
                });

        return dto;
    }

    public Stream<ListDepartmentsDTO> listDepartments(){
        List<Department> departmentEntity = departmentRepository.findAll();
        return departmentEntity.stream().map(department-> new ListDepartmentsDTO(
                department.getId(), department.getDepartmentName(), department.getManagerName(),
                department.getCreatedAt(), department.getUpdatedAt()));
    }

    public List<UserDepartmentDTO> listUsersInDepartmentsById(UUID id){
        Department department = departmentRepository.findById(id)
                .orElseThrow(()-> new DepartmentNotFoundException("Department not found!"));

        List<User> users = department.getUser();

        return users.stream().map(user-> new UserDepartmentDTO(
                user.getId(), user.getName(), user.getEmail(), user.getCreatedAt(),
                user.getUpdatedAt())).collect(Collectors.toList());
    }

    @Transactional
    public void deleteDepartmentById(UUID id){
        Department department = departmentRepository.findById(id)
                .orElseThrow(()->new DepartmentNotFoundException("Department not found!"));

        userRepository.deleteAllByDepartment(department);

        departmentRepository.delete(department);
    }

    @Transactional
    public void updateDepartment(UUID id, UpdateDepartmentDTO dto){
        Department department = departmentRepository.findById(id)
                .orElseThrow(()-> new DepartmentNotFoundException("Department not found!"));

        department.setDepartmentName(dto.departmentName());
        department.setManagerName(dto.managerName());

        departmentRepository.save(department);
    }
}
