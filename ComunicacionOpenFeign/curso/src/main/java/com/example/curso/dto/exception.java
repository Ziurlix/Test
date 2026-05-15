package com.example.curso.dto;

import feign.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class exception {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDTO> manageBadRequest(HttpMessageNotReadableException  ex) {
        ErrorDTO error = new ErrorDTO(
                "Solicitud Json invalida o tiene errores de formato",
                         HttpStatus.BAD_REQUEST.value(),
                         LocalDateTime.now()
            );
            return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> manageValidation(MethodArgumentNotValidException ex) {
        String mensajeError = ex.getBindingResult().getFieldError().getDefaultMessage ();

        ErrorDTO error = new ErrorDTO(
                "Error de validacion: " + mensajeError,
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> manageInternError(Exception ex) {
        ErrorDTO error = new ErrorDTO(
                "Ocurrio error interno inesperado en el servidor.",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }


}