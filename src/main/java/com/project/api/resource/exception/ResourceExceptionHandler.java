package com.project.api.resource.exception;

import com.project.api.service.exception.CpfInvalidException;
import com.project.api.service.exception.CpfNotNumberEvenException;
import com.project.api.service.exception.NameInvalidException;
import com.project.api.service.exception.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public StandardError objectNotFound(ObjectNotFoundException error) {

        return new StandardError(System.currentTimeMillis(), "Not found", error.getMessage());
    }

    @ExceptionHandler(CpfNotNumberEvenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public StandardError objectNotFound(CpfNotNumberEvenException error) {

        return new StandardError(System.currentTimeMillis(), "Invalid request", error.getMessage());
    }

    @ExceptionHandler(CpfInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public StandardError objectNotFound(CpfInvalidException error) {

        return new StandardError(System.currentTimeMillis(),"Invalid request", error.getMessage());
    }

    @ExceptionHandler(NameInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public StandardError objectNotFound(NameInvalidException error) {

        return new StandardError(System.currentTimeMillis(), "Invalid request", error.getMessage());
    }
}