package com.example.user_api.service;

import com.example.user_api.dto.UserDTO;
import com.example.user_api.exception.ResourceNotFoundException;
import com.example.user_api.mapper.UserMapper;
import com.example.user_api.model.User;
import com.example.user_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserMapper mapper;

    public List<UserDTO> getAllUsers() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO createUser(UserDTO dto) {
        User user = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(user));
    }

    public UserDTO getUserById(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
        return mapper.toDTO(user);
    }
}
