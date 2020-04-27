package org.personal.salesmgmt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ApiException> handleClassCastException(Exception exception) {
        ApiException apiException = new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), exception);
        return buildResponseEntity(apiException);
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ApiException> handleAccessDeniedException(AccessDeniedException accessDeniedException) {
        ApiException apiException = new ApiException(HttpStatus.BAD_REQUEST, accessDeniedException.getMessage(), accessDeniedException);
        return buildResponseEntity(apiException);
    }

    private ResponseEntity<ApiException> buildResponseEntity(ApiException apiException) {
        return new ResponseEntity<>(apiException, apiException.getStatus());
    }
}
