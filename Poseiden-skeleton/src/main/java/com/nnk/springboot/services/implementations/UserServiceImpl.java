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

/**
 * Service implementation for managing User entities.
 * Provides CRUD operations and business logic for user management.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs a new UserServiceImpl with the given repository and password encoder.
     *
     * @param userRepository the repository for User entities
     * @param passwordEncoder the password encoder for hashing passwords
     */
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Creates a new user from the provided DTO.
     * The password is automatically hashed before storage.
     *
     * @param dto the DTO containing user creation data
     * @return the created user as a response DTO
     * @throws IllegalArgumentException if the username already exists
     */
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

    /**
     * Retrieves a user by its ID.
     *
     * @param id the ID of the user to retrieve
     * @return the user as a response DTO
     * @throws EntityNotFoundException if the user is not found
     */
    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO findById(Long id) {
        User user = Objects.requireNonNull(userRepository.findById(Objects.requireNonNull(id))
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id)));
        return toResponseDTO(user);
    }

    /**
     * Retrieves all users with pagination.
     *
     * @param pageable pagination information
     * @return a page of user response DTOs
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserResponseDTO> findAll(Pageable pageable) {
        Objects.requireNonNull(pageable);
        return userRepository.findAll(pageable)
                .map(this::toResponseDTO);
    }

    /**
     * Updates an existing user with the provided data.
     *
     * @param id the ID of the user to update
     * @param dto the DTO containing update data
     * @return the updated user as a response DTO
     * @throws EntityNotFoundException if the user is not found
     */
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

    /**
     * Deletes a user by its ID.
     *
     * @param id the ID of the user to delete
     * @throws EntityNotFoundException if the user is not found
     */
    @Override
    public void delete(Long id) {
        Long nonNullId = Objects.requireNonNull(id);
        if (!userRepository.existsById(nonNullId)) {
            throw new EntityNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(nonNullId);
    }

    /**
     * Converts a User entity to a UserResponseDTO.
     * Note: The password hash is never included in the response DTO.
     *
     * @param user the User entity to convert
     * @return the corresponding response DTO
     */
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
