package com.usuario.departamento_usuario.DTOs.ResponseDTO;

import java.util.Date;
import java.util.UUID;

public record UserDepartmentDTO(UUID id,
                                String name,
                                String email,
                                Date createdAt,
                                Date updatedAt) {
}
