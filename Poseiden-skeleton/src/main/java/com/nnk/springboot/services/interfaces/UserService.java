package com.nnk.springboot.services.interfaces;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserCreateDTO;
import com.nnk.springboot.dto.UserResponseDTO;
import com.nnk.springboot.dto.UserUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserResponseDTO create(UserCreateDTO dto);
    UserResponseDTO findById(Long id);
    Page<UserResponseDTO> findAll(Pageable pageable);
    UserResponseDTO update(Long id, UserUpdateDTO dto);
    void delete(Long id);
    UserResponseDTO toResponseDTO(User user);
}

