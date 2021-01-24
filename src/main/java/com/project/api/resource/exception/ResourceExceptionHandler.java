package com.project.api.resource.exception;

import com.project.api.service.exception.CpfInvalidException;
import com.project.api.service.exception.CpfNotNumberEvenException;
import com.project.api.service.exception.NameInvalidException;
import com.project.api.service.exception.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException error, HttpServletRequest request) {

        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        StandardError standardError = new StandardError(System.currentTimeMillis(), httpStatus.value(),
                "Não encontrado", error.getMessage(), request.getRequestURI());

        return ResponseEntity.status(httpStatus).body(standardError);
    }

    @ExceptionHandler(CpfNotNumberEvenException.class)
    public ResponseEntity<StandardError> objectNotFound(CpfNotNumberEvenException error, HttpServletRequest request) {

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        StandardError standardError = new StandardError(System.currentTimeMillis(), httpStatus.value(),
                "Solicitação inválida", error.getMessage(), request.getRequestURI());

        return ResponseEntity.status(httpStatus).body(standardError);
    }

    @ExceptionHandler(CpfInvalidException.class)
    public ResponseEntity<StandardError> objectNotFound(CpfInvalidException error, HttpServletRequest request) {

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        StandardError standardError = new StandardError(System.currentTimeMillis(), httpStatus.value(),
                "Solicitação inválida", error.getMessage(), request.getRequestURI());

        return ResponseEntity.status(httpStatus).body(standardError);
    }

    @ExceptionHandler(NameInvalidException.class)
    public ResponseEntity<StandardError> objectNotFound(NameInvalidException error, HttpServletRequest request) {

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        StandardError standardError = new StandardError(System.currentTimeMillis(), httpStatus.value(),
                "Solicitação inválida", error.getMessage(), request.getRequestURI());

        return ResponseEntity.status(httpStatus).body(standardError);
    }
}