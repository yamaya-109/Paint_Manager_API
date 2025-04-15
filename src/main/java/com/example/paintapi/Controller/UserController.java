package com.example.paintapi.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.paintapi.dto.UserDto;
import com.example.paintapi.service.UserService;
import com.example.paintapi.user.User;

@RestController
@RequestMapping("/api/user")
public class UserController
{
    private UserService userService;
    
    public  UserController(UserService uService) {
        this.userService = uService;
    }
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserDto dto)
    {
        User app_user =userService.register(dto);
        return ResponseEntity.ok(app_user);
    }
}