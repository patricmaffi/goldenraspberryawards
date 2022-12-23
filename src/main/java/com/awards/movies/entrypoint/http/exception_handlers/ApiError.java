package com.awards.movies.entrypoint.http.exception_handlers;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class ApiError {

    private final UUID errorId;
    private String type;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private final LocalDateTime timestamp;
    private String message;
    private HttpStatus status;
    private List<ValidationError> errors;

    private ApiError() {
        this.errorId = UUID.randomUUID();
        timestamp = LocalDateTime.now();
    }

    ApiError(HttpStatus status) {
        this();
        this.status = status;
    }

    ApiError(HttpStatus status, Throwable ex) {
        this(status);
        this.message = ex.getMessage();
        this.type = ex.getClass().getName();
    }

    ApiError(HttpStatus status, Throwable ex, String message) {
        this(status, ex);
        this.message = message;
    }

    ApiError(HttpStatus status, Throwable ex, List<ValidationError> errors) {
        this(status, ex);
        this.errors = errors;
    }

    ApiError(HttpStatus status, Throwable ex, String message, List<ValidationError> errors) {
        this(status, ex);
        this.message = message;
        this.errors = errors;
    }

    public UUID getErrorId() {
        return errorId;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

    public List<?> getErrors() {
        if (errors != null) {
            return errors;
        }

        return new ArrayList<>();
    }
}