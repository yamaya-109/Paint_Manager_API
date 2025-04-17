package com.example.paintapi.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.paintapi.dto.UserDto;
import com.example.paintapi.repository.UserRepository;
import com.example.paintapi.user.User;

import io.micrometer.observation.annotation.Observed;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Observed
    public User register(UserDto dto) {
        if (userRepository.findByUserName(dto.getUsername()).isPresent()) {
            throw new RuntimeException("ユーザー名が既に使われています");
        }
        User user = new User(dto.getUsername(), passwordEncoder.encode(dto.getPassword()));
        return userRepository.save(user);
    }
}
