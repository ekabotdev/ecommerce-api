package com.ekabotdev.ecommerce.user.service;


import com.ekabotdev.ecommerce.user.dto.RegisterRequest;
import com.ekabotdev.ecommerce.user.entity.Role;
import com.ekabotdev.ecommerce.user.entity.User;
import com.ekabotdev.ecommerce.user.exception.EmailAlreadyExistsException;
import com.ekabotdev.ecommerce.user.exception.UsernameAlreadyExistsException;
import com.ekabotdev.ecommerce.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(RegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UsernameAlreadyExistsException("Username already exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));


        user.setRole(Role.USER);

        return userRepository.save(user);
    }
}
