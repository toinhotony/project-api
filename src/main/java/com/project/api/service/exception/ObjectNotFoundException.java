package com.project.api.service.exception;

public class ObjectNotFoundException extends RuntimeException {

    public ObjectNotFoundException() {
        super("Objeto não encontrado");
    }
}
