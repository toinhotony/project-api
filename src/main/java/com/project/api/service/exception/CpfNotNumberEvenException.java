package com.project.api.service.exception;

public class CpfNotNumberEvenException extends RuntimeException {

    public CpfNotNumberEvenException() {
        super("Cpf is not even!");
    }
}
