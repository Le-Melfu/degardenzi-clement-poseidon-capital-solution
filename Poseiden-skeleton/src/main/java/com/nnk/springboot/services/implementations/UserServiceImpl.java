package com.nnk.springboot.services.implementations;

import com.nnk.springboot.domain.Role;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserCreateDTO;
import com.nnk.springboot.dto.UserResponseDTO;
import com.nnk.springboot.dto.UserUpdateDTO;
import com.nnk.springboot.exception.EntityNotFoundException;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.interfaces.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @SuppressWarnings("null")
    public UserResponseDTO create(UserCreateDTO dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new IllegalArgumentException("Username already exists: " + dto.getUsername());
        }

        User user = User.builder()
                .username(dto.getUsername())
                .passwordHash(passwordEncoder.encode(dto.getPassword()))
                .fullName(dto.getFullName())
                .role(dto.getRole() != null ? dto.getRole() : Role.USER)
                .build();

        User saved = Objects.requireNonNull(userRepository.save(user));
        return toResponseDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO findById(Long id) {
        User user = Objects.requireNonNull(userRepository.findById(Objects.requireNonNull(id))
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id)));
        return toResponseDTO(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponseDTO> findAll(Pageable pageable) {
        Objects.requireNonNull(pageable);
        return userRepository.findAll(pageable)
                .map(this::toResponseDTO);
    }

    @Override
    public UserResponseDTO update(Long id, UserUpdateDTO dto) {
        User user = Objects.requireNonNull(userRepository.findById(Objects.requireNonNull(id))
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id)));

        if (dto.getFullName() != null) {
            user.setFullName(dto.getFullName());
        }
        if (dto.getRole() != null) {
            user.setRole(dto.getRole());
        }

        User updated = Objects.requireNonNull(userRepository.save(user));
        return toResponseDTO(updated);
    }

    @Override
    public void delete(Long id) {
        Long nonNullId = Objects.requireNonNull(id);
        if (!userRepository.existsById(nonNullId)) {
            throw new EntityNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(nonNullId);
    }

    @Override
    public UserResponseDTO toResponseDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .role(user.getRole())
                .build();
    }
}
