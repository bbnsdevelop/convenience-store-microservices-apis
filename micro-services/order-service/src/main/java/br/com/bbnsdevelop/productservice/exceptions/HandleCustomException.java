package br.com.bbnsdevelop.productservice.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class HandleCustomException {

    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    public ResponseEntity<Map<String, String>> resourceRiquiredFields(MethodArgumentNotValidException ex,
                                                                         WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        log.error("Bad request: {}", errors);
        return new ResponseEntity<Map<String, String>>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { NotFoundException.class })
    public ResponseEntity<Map<String, String>> resourceNotFoundException(NotFoundException ex,  WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        errors.put("Error", ex.getMessage());
        log.error("Bad request: {}", errors);
        return new ResponseEntity<Map<String, String>>(errors, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(value = { NotHasInventoryException.class })
    public ResponseEntity<Map<String, String>> notHasInventoryException(NotHasInventoryException ex,  WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        errors.put("Error", ex.getMessage());
        log.error("Bad request: {}", errors);
        return new ResponseEntity<Map<String, String>>(errors, HttpStatus.BAD_REQUEST);
    }
    
    

}
