package ats.fu.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<String> handleException(BusinessException e) {
        log.error("Business error [type={}]: {}", e.getType(), e.getMessage());

        HttpStatus status = switch (e.getType()) {
            case 3 -> HttpStatus.NOT_FOUND;       // Resource not found
            default -> HttpStatus.BAD_REQUEST;     // Validation errors
        };

        return ResponseEntity.status(status).body(e.getMessage());
    }
}

