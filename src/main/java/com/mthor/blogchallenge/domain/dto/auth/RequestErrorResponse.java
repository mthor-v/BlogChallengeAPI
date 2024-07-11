package com.mthor.blogchallenge.domain.dto.auth;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.security.core.AuthenticationException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record RequestErrorResponse(String timestamp, int status, String error,
                                    String message, String path){
    public RequestErrorResponse(HttpMessageNotReadableException ex, WebRequest request){
        this(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")
        ), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(), request.getDescription(false).replace("uri=",""));
    }

    public RequestErrorResponse(AccessDeniedException ex, HttpServletRequest request) {
        this(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")
                ), HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase(),
                ex.getMessage(),request.getRequestURI());
    }

    public RequestErrorResponse(AuthenticationException ex, HttpServletRequest request) {
        this(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")
                ), HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                ex.getMessage(),request.getRequestURI());
    }
}
