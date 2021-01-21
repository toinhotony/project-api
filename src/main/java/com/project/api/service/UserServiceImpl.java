package com.project.api.service;

import com.project.api.model.User;
import com.project.api.repository.UserRepository;
import com.project.api.service.dto.UserDTO;
import com.project.api.service.dto.UserIDTO;
import com.project.api.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
        User user = new User();

        user.setName(userIDTO.getName());
        user.setCpf(userIDTO.getCpf());

        return new UserDTO(userRepository.insert(user));
    }

    @Override
    public UserDTO update(String id, UserDTO userDTO) {
        Optional<User> user = userRepository.findById(id);

        if(user.isPresent()) {
            user.get().setName(userDTO.getName());
            user.get().setCpf(userDTO.getCpf());
            return new UserDTO(userRepository.save(user.get()));
        }

        return null;
    }

    @Override
    public void delete(String id) {
        findById(id);
        userRepository.deleteById(id);
    }

    private UserDTO findById(String id) {
        return new UserDTO(userRepository.findById(id).orElseThrow(ObjectNotFoundException::new));
    }
}
