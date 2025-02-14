package com.example.user_api.service;

import com.example.user_api.dto.UserRequestDTO;
import com.example.user_api.dto.UserResponseDTO;
import com.example.user_api.exception.ResourceNotFoundException;
import com.example.user_api.mapper.UserMapper;
import com.example.user_api.model.User;
import com.example.user_api.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RequiredArgsConstructor
@Slf4j
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private UserMapper mapper;

    @InjectMocks
    private UserService service;

    private User user;
    private UserRequestDTO userRequestDTO;
    private UserResponseDTO userResponseDTO;

    @BeforeEach
    void setUp() {
        user = new User(1L, "John Doe", "john.doe@example.com", 20);
        userRequestDTO = new UserRequestDTO("John Doe", "john.doe@example.com",20 );
        userResponseDTO = new UserResponseDTO(1L, "John Doe", "john.doe@example.com",20);
    }

    @Test
    void getAllUsers_ShouldReturnListOfUserResponseDTO() {
        when(repository.findAll()).thenReturn(Arrays.asList(user));
        when(mapper.toResponseDTO(user)).thenReturn(userResponseDTO);

        List<UserResponseDTO> result = service.getAllUsers();

        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
        verify(repository, times(1)).findAll();
    }

    @Test
    void createUser_ShouldReturnUserResponseDTO() {
        when(mapper.toEntity(userRequestDTO)).thenReturn(user);
        when(repository.save(user)).thenReturn(user);
        when(mapper.toResponseDTO(user)).thenReturn(userResponseDTO);

        UserResponseDTO result = service.createUser(userRequestDTO);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(repository, times(1)).save(user);
    }

    @Test
    void getUserById_ShouldReturnUserResponseDTO_WhenUserExists() {
        when(repository.findById(1L)).thenReturn(Optional.of(user));
        when(mapper.toResponseDTO(user)).thenReturn(userResponseDTO);

        UserResponseDTO result = service.getUserById(1L);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void getUserById_ShouldThrowException_WhenUserNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getUserById(1L));
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void updateUser_ShouldReturnUpdatedUserResponseDTO() {
        when(repository.findById(1L)).thenReturn(Optional.of(user));
        when(repository.save(user)).thenReturn(user);
        when(mapper.toResponseDTO(user)).thenReturn(userResponseDTO);

        UserResponseDTO result = service.updateUser(1L, userRequestDTO);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(repository, times(1)).save(user);
    }

    @Test
    void deleteUser_ShouldRemoveUser_WhenUserExists() {
        when(repository.findById(1L)).thenReturn(Optional.of(user));
        doNothing().when(repository).delete(user);

        service.deleteUser(1L);

        verify(repository, times(1)).delete(user);
    }

    @Test
    void deleteUser_ShouldThrowException_WhenUserNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.deleteUser(1L));
        verify(repository, times(1)).findById(1L);
    }
}
