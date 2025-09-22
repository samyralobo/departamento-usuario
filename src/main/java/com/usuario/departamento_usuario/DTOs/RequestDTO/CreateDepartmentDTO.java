package com.usuario.departamento_usuario.DTOs.RequestDTO;

import java.util.Date;

public record CreateDepartmentDTO(java.util.UUID id,
                                  String departmentName,
                                  String managerName,
                                  Date createdAt,
                                  Date updatedAt) {
}
