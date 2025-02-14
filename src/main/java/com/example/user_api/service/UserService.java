package com.example.user_api.service;

import com.example.user_api.dto.UserDTO;
import com.example.user_api.exception.ResourceNotFoundException;
import com.example.user_api.mapper.UserMapper;
import com.example.user_api.model.User;
import com.example.user_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    public List<UserDTO> getAllUsers() {
        log.info("Fetching all users");
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO createUser(UserDTO dto) {
        log.info("Creating user: {}", dto);
        User user = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(user));
    }

    public UserDTO getUserById(Long id) {
        log.info("Fetching user with ID: {}", id);
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
        return mapper.toDTO(user);
    }
}
