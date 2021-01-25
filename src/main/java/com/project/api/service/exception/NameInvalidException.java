package com.project.api.service.exception;

public class NameInvalidException extends RuntimeException {

    public NameInvalidException() {
        super("Invalid name!");
    }
}
