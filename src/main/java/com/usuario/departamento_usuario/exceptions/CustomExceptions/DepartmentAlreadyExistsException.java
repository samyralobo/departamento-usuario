package com.usuario.departamento_usuario.exceptions.CustomExceptions;

public class DepartmentAlreadyExistsException extends RuntimeException {
    public DepartmentAlreadyExistsException(String message) {
        super(message);
    }
}
