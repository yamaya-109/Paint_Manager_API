package com.example.paintapi.initializer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.paintapi.repository.UserRepository;
import com.example.paintapi.user.User;

@Configuration
public class DataInitializer
{
    @Bean
    CommandLineRunner init(UserRepository userRepository,BCryptPasswordEncoder encoder , @Value("${ADMIN_INIT_PASS:}") String adminInitPass) {
    return args  ->{//ラムダ式
        
        if (adminInitPass == null || adminInitPass.isBlank()) {
            System.out.println("ADMIN_INIT_PASS が未設定のため、admin ユーザーの作成をスキップします。");
            return;
        }else {
            String adminPass = System.getenv("ADMIN_INIT_PASS");
            System.out.println("ADMIN_INIT_PASS = " + adminPass); // ← 表示されるか確認
            userRepository.findAll().forEach(u -> System.out.println("Existing user: " + u.getuserName()));

        }
        
        userRepository.findByUserName("admin").ifPresentOrElse(user -> System.out.println("adminユーザーは既に存在します。"),
                ()->{
                    User adminUser =new User("admin",encoder.encode(adminInitPass));
                    userRepository.save(adminUser);
                    System.out.println("admin ユーザーを作成しました。");
                    
                    }
                );
    };
}
}