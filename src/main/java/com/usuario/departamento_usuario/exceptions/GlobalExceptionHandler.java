package com.usuario.departamento_usuario.exceptions;

import com.usuario.departamento_usuario.exceptions.CustomExceptions.DepartmentAlreadyExistsException;
import com.usuario.departamento_usuario.exceptions.CustomExceptions.DepartmentNotFoundException;
import com.usuario.departamento_usuario.exceptions.CustomExceptions.UserAlreadyExistsException;
import com.usuario.departamento_usuario.exceptions.CustomExceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(UserAlreadyExistsException.class)
    private ResponseEntity<String> userAlreadyExistsHnadler(UserAlreadyExistsException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler(DepartmentNotFoundException.class)
    private ResponseEntity<String> departmentNotFoundHandler(DepartmentNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<String> userNotFoundHandler(UserNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(DepartmentAlreadyExistsException.class)
    private ResponseEntity<String> departmentAlreadyExists(DepartmentAlreadyExistsException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }
}
