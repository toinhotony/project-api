package com.project.api.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.api.resource.exception.ResourceExceptionHandler;
import com.project.api.service.UserService;
import com.project.api.service.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserResourceTest {

    private String PATH_APP = "/api/users";

    @InjectMocks
    private UserResource userResource;

    @Mock
    private UserService service;

    private MockMvc mvc;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(userResource)
                .setControllerAdvice(new ResourceExceptionHandler()).build();
    }

    @Test
    public void findAllTest() throws Exception {
        List<UserDTO> mockResponseService  = mockUserList();

        MockHttpServletRequestBuilder requestBuilder = get(PATH_APP);
        Mockito.when(service.findAll()).thenReturn(mockResponseService);

        String expectedResponde = new ObjectMapper().writeValueAsString(mockResponseService);

        this.mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponde));
    }

    @Test
    public void insertTest() throws Exception {

        MockHttpServletRequestBuilder requestBuilder = post(PATH_APP);
        String param = new ObjectMapper().writeValueAsString(prepareUserDTO());

        this.mvc.perform(requestBuilder
                .contentType("application/json")
                .content(param))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateTest() throws Exception {
        UserDTO user = prepareUserDTO();
        MockHttpServletRequestBuilder requestBuilder = put(PATH_APP.concat("/{id}"), user.getId());

        String param = new ObjectMapper().writeValueAsString(user);

        this.mvc.perform(requestBuilder
                .contentType("application/json")
                .param("id", user.getId())
                .content(param))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteTest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = delete(PATH_APP.concat("/{id}"), "99");

        this.mvc.perform(requestBuilder
                .contentType("application/json")
                .param("id", "99"))
                .andExpect(status().isNoContent());
    }

    private List<UserDTO> mockUserList() {
        List<UserDTO> mockResponse = new ArrayList<>();
        UserDTO userDTO = prepareUserDTO();

        mockResponse.add(userDTO);

        return mockResponse;
    }

    private UserDTO prepareUserDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId("123");
        userDTO.setCpf("000000000-00");
        userDTO.setName("Same name");

        return userDTO;
    }
}