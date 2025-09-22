package com.usuario.departamento_usuario.exceptions.CustomExceptions;

public class DepartmentNotFoundException extends RuntimeException {
    public DepartmentNotFoundException(String message) {
        super(message);
    }
}
