package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Captura las excepciones generadas por las validaciones @Valid (ej. @NotBlank, @Pattern)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Extraemos todos los mensajes de error de los campos que fallaron
        List<String> errores = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());
        
        // Unimos los mensajes separados por coma (ej. "El DNI debe tener exactamente 8 dígitos")
        String mensajeLimpiado = String.join(", ", errores);
        
        // Devolvemos un 400 Bad Request con el texto plano, igual que en el caso #6
        return ResponseEntity.badRequest().body(mensajeLimpiado);
    }
}
