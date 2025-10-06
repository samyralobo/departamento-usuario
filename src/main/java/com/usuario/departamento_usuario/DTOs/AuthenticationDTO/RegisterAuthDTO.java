package com.usuario.departamento_usuario.DTOs.AuthenticationDTO;

import com.usuario.departamento_usuario.models.Department;
import com.usuario.departamento_usuario.models.Enum.UserRole;

public record RegisterAuthDTO(String name,
                              String email,
                              String password,
                              UserRole role,
                              String department) {
}
