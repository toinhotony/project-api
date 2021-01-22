package com.project.api.service.exception;

public class CpfInvalidException extends RuntimeException {

    public CpfInvalidException() {
        super("O Cpf só deve conter numeros");
    }
}
