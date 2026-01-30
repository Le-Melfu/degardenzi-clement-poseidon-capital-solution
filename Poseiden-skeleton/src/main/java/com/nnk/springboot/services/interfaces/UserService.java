package com.nnk.springboot.services.interfaces;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.user.UserCreateDTO;
import com.nnk.springboot.dto.user.UserResponseDTO;
import com.nnk.springboot.dto.user.UserUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/** Service interface for User CRUD operations. */
public interface UserService {
    UserResponseDTO create(UserCreateDTO dto);
    UserResponseDTO findById(Long id);
    Page<UserResponseDTO> findAll(Pageable pageable);
    UserResponseDTO update(Long id, UserUpdateDTO dto);
    void delete(Long id);
    UserResponseDTO toResponseDTO(User user);
}

