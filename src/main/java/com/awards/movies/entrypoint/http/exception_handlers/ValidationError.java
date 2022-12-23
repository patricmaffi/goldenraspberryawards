package com.awards.movies.entrypoint.http.exception_handlers;

public class ValidationError {
    private final String path;
    private final String message;

    public ValidationError(String path, String message) {
        this.path = path;
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public String getMessage() {
        return message;
    }
}
