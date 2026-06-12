package com.example.user_api.service;

import com.example.user_api.dto.UserRequestDTO;
import com.example.user_api.dto.UserResponseDTO;
import com.example.user_api.exception.ResourceNotFoundException;
import com.example.user_api.mapper.UserMapper;
import com.example.user_api.model.User;
import com.example.user_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    public List<UserResponseDTO> getAllUsers() {
        log.info("Fetching all users");
        return repository.findAll()
                .stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public UserResponseDTO createUser(@Valid UserRequestDTO dto) {
        log.info("Creating user: {}", dto);
        User user = mapper.toEntity(dto);
        return mapper.toResponseDTO(repository.save(user));
    }

    public UserResponseDTO getUserById(Long id) {
        log.info("Fetching user with ID: {}", id);
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
        return mapper.toResponseDTO(user);
    }

    public UserResponseDTO updateUser(Long id, @Valid UserRequestDTO dto) {
        log.info("Updating user with ID: {}", id);
        User existingUser = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
        existingUser.setName(dto.getName());
        existingUser.setEmail(dto.getEmail());
        existingUser.setAge(dto.getAge());
        return mapper.toResponseDTO(repository.save(existingUser));
    }

    public void deleteUser(Long id) {
        log.info("Deleting user with ID: {}", id);
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
        repository.delete(user);
    }
}
