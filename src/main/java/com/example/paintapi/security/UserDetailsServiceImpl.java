package com.example.paintapi.security;

import java.util.Collections;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.paintapi.repository.UserRepository;
import com.example.paintapi.user.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    private final UserRepository userRepository;
    
    public UserDetailsServiceImpl(UserRepository userRepository)
    {
        this.userRepository =userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =userRepository.findByUserName(username).orElseThrow(()
                ->new UsernameNotFoundException("ユーザーが見つかりません:"+username));
        
        return org.springframework.security.core.
                userdetails.User.withUsername(user.getuserName()).
                password(user.getpasswordHash()).
                authorities(Collections.emptyList()).build();
    }
    
}