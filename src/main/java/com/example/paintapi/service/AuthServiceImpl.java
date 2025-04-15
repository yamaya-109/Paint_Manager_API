package com.example.paintapi.service;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.paintapi.repository.UserRepository;
import com.example.paintapi.util.JwtUtil;

@Service
public class AuthServiceImpl implements AuthService
{
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authManager;
    public AuthServiceImpl(UserRepository repo,BCryptPasswordEncoder encoder,JwtUtil jwtUtil,AuthenticationManager authManager) {
        {
            this.userRepository =repo;
            this.encoder =encoder;
            this.jwtUtil =jwtUtil;
            this.authManager =authManager;
        }
       
    }

    @Override
    public String authenticate(String username, String password) {
        Authentication auth =authManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
                );

                return jwtUtil.generateToken(username); // トークン生成
    }
}