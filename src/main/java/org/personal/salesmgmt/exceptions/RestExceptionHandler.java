package org.personal.salesmgmt.exceptions;

import org.personal.salesmgmt.exceptions.custom.ResourceNotFoundException;
import org.personal.salesmgmt.exceptions.custom.RestTemplateClientException;
import org.personal.salesmgmt.exceptions.custom.RestTemplateServerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.ServletException;
import java.io.IOException;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({RuntimeException.class, ServletException.class, IOException.class})
    protected ResponseEntity<ApiException> handleClassCastException(Exception exception) {
        ApiException apiException = new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), exception);
        return buildResponseEntity(apiException);
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ApiException> handleAccessDeniedException(AccessDeniedException accessDeniedException) {
        ApiException apiException = new ApiException(HttpStatus.BAD_REQUEST, accessDeniedException.getMessage(), accessDeniedException);
        return buildResponseEntity(apiException);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<ApiException> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException) {
        ApiException apiException = new ApiException(HttpStatus.NOT_FOUND, resourceNotFoundException.getMessage(), resourceNotFoundException);
        return buildResponseEntity(apiException);
    }

    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<ApiException> handleAuthenticationException(AuthenticationException authenticationException) {
        ApiException apiException = new ApiException(HttpStatus.UNAUTHORIZED, authenticationException.getMessage(), authenticationException);
        return buildResponseEntity(apiException);
    }

    @ExceptionHandler(RestTemplateClientException.class)
    protected ResponseEntity<ApiException> handleRestTemplateClientException(RestTemplateClientException restTemplateClientException) {
        ApiException apiException = new ApiException(HttpStatus.BAD_REQUEST, restTemplateClientException.getMessage(), restTemplateClientException);
        return buildResponseEntity(apiException);
    }

    @ExceptionHandler(RestTemplateServerException.class)
    protected ResponseEntity<ApiException> handleRestTemplateServerException(RestTemplateServerException restTemplateServerException) {
        ApiException apiException = new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, restTemplateServerException.getMessage(), restTemplateServerException);
        return buildResponseEntity(apiException);
    }

    private ResponseEntity<ApiException> buildResponseEntity(ApiException apiException) {
        return new ResponseEntity<>(apiException, apiException.getStatus());
    }
}
