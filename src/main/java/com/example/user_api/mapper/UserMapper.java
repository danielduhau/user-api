package com.example.user_api.mapper;

import com.example.user_api.dto.UserDTO;
import com.example.user_api.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setAge(dto.getAge());
        return user;
    }

    public UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setAge(user.getAge());
        return dto;
    }
}
