package com.example.paintapi.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.paintapi.dto.LoginRequest;
import com.example.paintapi.dto.LoginResponse;
import com.example.paintapi.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    //curl -i -H "Authorization: Bearer eGSwF4KBLENEmkzjL9FxwyhwHyumtvFlwk8XY4l+xlI=" http://localhost:8080/api/paint/list
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        String token = authService.authenticate(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(new LoginResponse(token));
    }
    //@PostMapping("/login")
    //public ResponseEntity<?> login(@RequestBody  UserDto loginDto)
    //{
    //User user =userRepository.findByUserName(loginDto.getUsername())
    //.orElseThrow(()-> new RuntimeException("ユーザー名またはパスワードが正しくありません"));
    //if(!encoder.matches(loginDto.getPassword(), user.getpasswordHash()))
    //{
    //throw new RuntimeException("ユーザー名またはパスワードが正しくありません");
    //}
    //String token =jwtUtil.generateToken(user.getuserName());
    //return ResponseEntity.ok(Map.of("token",token));

}
