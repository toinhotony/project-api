package com.project.api.service;

import com.project.api.resource.exception.ResourceExceptionHandler;
import com.project.api.resource.exception.StandardError;
import com.project.api.service.exception.CpfInvalidException;
import com.project.api.service.exception.CpfNotNumberEvenException;
import com.project.api.service.exception.NameInvalidException;
import com.project.api.service.exception.ObjectNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ResourceExceptionHandlerTest {

    @InjectMocks
    private ResourceExceptionHandler controller;


    @Test
    void shouldGenerateErrorCpfNotNumberEvenException() {
        CpfNotNumberEvenException exception = new CpfNotNumberEvenException();
        StandardError error = controller.objectNotFound(exception);

        assertThat(error.getMessage()).isEqualTo("Cpf is not even!");
    }

    @Test
    void shouldGenerateErrorObjectNotFoundException() {
        ObjectNotFoundException exception = new ObjectNotFoundException();
        StandardError error = controller.objectNotFound(exception);

        assertThat(error.getMessage()).isEqualTo("Object not found!");
    }

    @Test
    void testGenerateErrorCpfInvalidException() {
        CpfInvalidException exception = new CpfInvalidException();
        StandardError error = controller.objectNotFound(exception);

        assertThat(error.getMessage()).isEqualTo("Invalid cpf!");
    }

    @Test
    void shouldGenerateErrorNameInvalidException() {
        NameInvalidException exception = new NameInvalidException();
        StandardError error = controller.objectNotFound(exception);

        assertThat(error.getMessage()).isEqualTo("Invalid name!");
    }
}