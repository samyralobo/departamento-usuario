package com.usuario.departamento_usuario.DTOs.ResponseDTO;

import java.util.Date;
import java.util.UUID;

public record ListDepartmentsDTO(UUID id,
                                 String departmentName,
                                 String managerName,
                                 Date createdAt,
                                 Date updatedAt) {
}
