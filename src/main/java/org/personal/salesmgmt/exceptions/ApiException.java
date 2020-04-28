package org.personal.salesmgmt.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import org.personal.salesmgmt.deserializers.RestTemplateExceptionDeserializer;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonDeserialize(using = RestTemplateExceptionDeserializer.class)
public class ApiException {

    private HttpStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;

    private String message;

    private String debugMessage;

    private ApiException() {
        timestamp = LocalDateTime.now();
    }

    public ApiException(String message, String debugMessage) {
        this();
        this.message = message;
        this.debugMessage = debugMessage;
    }

    public ApiException(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }
}
