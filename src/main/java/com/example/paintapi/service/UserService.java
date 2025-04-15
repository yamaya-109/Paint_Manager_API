package com.example.paintapi.service;

import com.example.paintapi.dto.UserDto;
import com.example.paintapi.user.User;

public interface UserService
{
    User register(UserDto dto);
}