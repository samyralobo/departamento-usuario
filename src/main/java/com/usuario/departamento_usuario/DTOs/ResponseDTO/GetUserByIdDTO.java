package com.usuario.departamento_usuario.DTOs.ResponseDTO;

import com.usuario.departamento_usuario.models.Department;

import java.util.Date;
import java.util.UUID;

public record GetUserByIdDTO(String name,
                             String email,
                             Date createdAt,
                             Date updatedAt,
                             UUID departmentId,
                             String departmentName,
                             String managerName) {
}
