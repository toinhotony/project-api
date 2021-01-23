package com.project.api.service.exception;

public class CpfInvalidException extends RuntimeException {

    public CpfInvalidException() {
        super("Cpf Invalido!");
    }
}
