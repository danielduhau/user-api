package com.example.user_api.mapper;

import com.example.user_api.dto.UserRequestDTO;
import com.example.user_api.dto.UserResponseDTO;
import com.example.user_api.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Converte uma entidade User para UserResponseDTO.
     * 
     * @param user a entidade User
     * @return UserResponseDTO
     */
    UserResponseDTO toResponseDTO(User user);

    /**
     * Converte um UserRequestDTO para a entidade User.
     * 
     * @param dto o UserRequestDTO
     * @return uma nova entidade User
     */
    User toEntity(UserRequestDTO dto);
}
