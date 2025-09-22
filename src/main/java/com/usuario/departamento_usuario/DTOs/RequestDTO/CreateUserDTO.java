package com.usuario.departamento_usuario.DTOs.RequestDTO;

import com.usuario.departamento_usuario.models.Department;

import java.util.UUID;

public record CreateUserDTO(UUID id, String name, String email,
                            String password, Department departmentId) {
}
