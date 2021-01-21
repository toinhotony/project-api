package com.project.api.service;

import com.project.api.service.dto.UserDTO;
import com.project.api.service.dto.UserIDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> findAll();

    UserDTO insert(UserIDTO userIDTO);

    UserDTO update(String id, UserDTO userDTO);

    void delete(String cpf);
}