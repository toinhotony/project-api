package com.project.api.service;

import com.project.api.service.dto.UserDTO;
import com.project.api.service.dto.UserIDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

public interface UserService {

    List<UserDTO> findAll();

    UserDTO insert(UserIDTO userIDTO);

    UserDTO update(String id, UserDTO userDTO);

    void delete(String cpf);

    Properties userJobProperties(MultipartFile file) throws IOException;
}