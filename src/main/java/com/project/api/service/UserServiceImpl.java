package com.project.api.service;

import com.project.api.model.User;
import com.project.api.repository.UserRepository;
import com.project.api.service.dto.UserDTO;
import com.project.api.service.dto.UserIDTO;
import com.project.api.service.exception.CpfNotNumberEvenException;
import com.project.api.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;


    @Override
    public List<UserDTO> findAll() {
        List<User> userList = userRepository.findAll();
        return userList.stream().map(UserDTO::new).collect(Collectors.toList());
    }

    @Override
    public UserDTO insert(UserIDTO userIDTO) {
        validateCpf(userIDTO.getCpf().trim());

        User user = new User();

        user.setName(userIDTO.getName().trim());
        user.setCpf(userIDTO.getCpf().trim());

        return new UserDTO(userRepository.insert(user));
    }

    @Override
    public UserDTO update(String id, UserDTO userDTO) {
        validateCpf(userDTO.getCpf().trim());

        User user = findById(id);

        user.setName(userDTO.getName());
        user.setCpf(userDTO.getCpf().trim());

        return new UserDTO(userRepository.save(user));
    }

    @Override
    public void delete(String id) {
        findById(id);
        userRepository.deleteById(id);
    }

    private User findById(String id) {
        return userRepository.findById(id).orElseThrow(ObjectNotFoundException::new);
    }

    private void validateCpf(String cpf) {
        int number = Integer.parseInt(cpf.substring(cpf.length() - 1));

        if(number % 2 != 0)
            throw new CpfNotNumberEvenException();
    }
}
