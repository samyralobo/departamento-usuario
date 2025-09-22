package com.usuario.departamento_usuario.exceptions.CustomExceptions;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(String message) {
    super(message);
  }
}
