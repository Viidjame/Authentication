package com.example.exception;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@ControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", Objects.requireNonNull(ex.getFieldError()).getDefaultMessage());
        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(value = NotFoundExceptionClass.class)
    public ResponseEntity<CustomErrorException> handleNotFoundException(NotFoundExceptionClass exception) {
        CustomErrorException errorResponse = new CustomErrorException(
                LocalDateTime.now(),
                "Not Found",
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(value = BadRequestExceptionClass.class)
    public ResponseEntity<CustomErrorException> handleBadRequestException(BadRequestExceptionClass exception) {
        CustomErrorException errorResponse = new CustomErrorException(
                LocalDateTime.now(),
                "Bad Request",
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }


}

