package com.usuario.departamento_usuario.controllers;

import com.usuario.departamento_usuario.DTOs.RequestDTO.CreateDepartmentDTO;
import com.usuario.departamento_usuario.DTOs.RequestDTO.UpdateDepartmentDTO;
import com.usuario.departamento_usuario.DTOs.ResponseDTO.ListDepartmentsDTO;
import com.usuario.departamento_usuario.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<?> createDepartment(@RequestBody CreateDepartmentDTO dto){
        try{
            departmentService.createDepartment(dto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping
    public ResponseEntity<Stream<?>> listDepartments(){
        Stream<ListDepartmentsDTO> departments = departmentService.listDepartments();
        return ResponseEntity.ok().body(departments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List> listUsersInDepartmentsById(@PathVariable("id") UUID id){
        List users = departmentService.listUsersInDepartmentsById(id);
        return ResponseEntity.ok().body(users);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable("id") UUID id){
        departmentService.deleteDepartmentById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDepartment(@PathVariable("id") UUID id,
                                              @RequestBody UpdateDepartmentDTO dto){
        departmentService.updateDepartment(id, dto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
