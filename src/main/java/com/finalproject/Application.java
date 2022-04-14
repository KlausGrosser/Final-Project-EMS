package com.finalproject;

import com.finalproject.model.repository.UserRepository;
import com.finalproject.model.service.UserService;
import com.finalproject.model.entity.Authority;
import com.finalproject.model.entity.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;


/**
 * Spring Boot application class
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner loadData(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            UserService userService
    ) {
        return (args) -> {
            Set<Authority> superadminAuthorities = new HashSet<Authority>();
            superadminAuthorities.add(Authority.SUPERADMIN);
            superadminAuthorities.add(Authority.ADMIN);
            superadminAuthorities.add(Authority.USER);

            Set<Authority> adminAuthorities = new HashSet<Authority>();
            adminAuthorities.add(Authority.ADMIN);
            adminAuthorities.add(Authority.USER);

            Set<Authority> userAuthorities = new HashSet<Authority>();
            userAuthorities.add(Authority.USER);

            User u1 = new User(
                    1L,
                    "Rachel",
                    "Adams",
                    "test@gmail.com",
                    passwordEncoder.encode("test"),
                    true,
                    false,
                    superadminAuthorities

            );
            userRepository.save(u1);

            User u2 = new User(
                    2L,
                    "John",
                    "Doe",
                    "test2@gmail.com",
                    passwordEncoder.encode("test"),
                    true,
                    false,
                    adminAuthorities
            );
            userRepository.save(u2);

            User u3 = new User(
                    3L,
                    "Joey",
                    "Tribiani",
                    "test3@gmail.com",
                    passwordEncoder.encode("test"),
                    true,
                    false,
                    userAuthorities
            );
            userRepository.save(u3);
        };
    }
}
