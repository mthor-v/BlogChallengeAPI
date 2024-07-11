package com.mthor.blogchallenge.infra;

import com.mthor.blogchallenge.domain.dto.auth.RequestErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.PropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class ErrorControl {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity errorHandler404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity errorHandler400(MethodArgumentNotValidException ex) {
        var errors = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(errors.stream().map(ErrorDataValidation::new).toList());
    }

    @ExceptionHandler(PropertyValueException.class)
    public ResponseEntity errorProperty400(PropertyValueException ex) {
        return ResponseEntity.badRequest().body(new ErrorDataValidation(ex));
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<RequestErrorResponse> httpMessageNotReadableException(HttpMessageNotReadableException ex, WebRequest request){
        RequestErrorResponse response = new RequestErrorResponse(ex, request);
        return ResponseEntity.badRequest().body(response);
    }

    private record ErrorDataValidation(String campo, String mensaje) {
        public ErrorDataValidation(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }

        public ErrorDataValidation(PropertyValueException ex){
            this(ex.getPropertyName(), ex.getMessage().split(":")[0]);
        }
    }

}
