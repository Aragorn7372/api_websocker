package org.example.funko2.handler;

import org.example.funko2.exceptions.categoria.CategoriaNotFoundException;
import org.example.funko2.exceptions.funko.FunkoNotFoundException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;// Para capturar los errores de validación
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
/**
 * Clase que maneja los errores de la API.
 * @author Aragorn7372
 */
@RestControllerAdvice
class Handler {
    private final Logger logger = LoggerFactory.getLogger(Handler.class);

    /**
     * Maneja la excepción de MethodArgumentNotValidException.
     * @param ex Excepción de MethodArgumentNotValidException.
     * @return Map con el mensaje de error.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        logger.error("invalido de argumentos");
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
    /**
     * Maneja la excepción de FunkoNotFoundException.
     * @param ex Excepción de FunkoNotFoundException.
     * @return Map con el mensaje de error.
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(FunkoNotFoundException.class)
    public Map<String, String> handleFunkoNotFound(FunkoNotFoundException ex) {
        logger.error("Funko Not Found", ex);
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());
        return error;
    }
    /**
     * Maneja la excepción de CategoriaNotFoundException.
     * @param ex Excepción de CategoriaNotFoundException.
     * @return Map con el mensaje de error.
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CategoriaNotFoundException.class)
    public Map<String, String> handleFunkoNotFound(CategoriaNotFoundException ex) {
        logger.error("Funko Not Found", ex);
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());
        return error;
    }
    /**
     * Maneja la excepción de DataIntegrityViolationException.
     * @param ex Excepción de DataIntegrityViolationException.
     * @return Map con el mensaje de error.
     */
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public Map<String, String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        logger.error("Data integrity violation", ex);
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());
        return error;
    }
}
