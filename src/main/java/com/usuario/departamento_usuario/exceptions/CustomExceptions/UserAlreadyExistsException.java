package com.usuario.departamento_usuario.exceptions.CustomExceptions;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
