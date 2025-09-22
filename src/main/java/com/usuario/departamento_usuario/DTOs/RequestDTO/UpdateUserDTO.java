package com.usuario.departamento_usuario.DTOs.RequestDTO;

import com.usuario.departamento_usuario.models.Department;

public record UpdateUserDTO(String name,
                            String email,
                            String password,
                            Department department) {
}
