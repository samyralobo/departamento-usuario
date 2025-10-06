package com.usuario.departamento_usuario.DTOs.AuthenticationDTO;

import com.usuario.departamento_usuario.models.Enum.UserRole;

import java.util.UUID;

public record RegisterResponseDTO(UUID id,
                                  String email,
                                  UserRole role) {
}
