package org.example.responses;

import org.example.utils.Response;

public class ErrorResponse implements Response {
    private final String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
