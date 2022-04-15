package com.finalproject;

import com.finalproject.model.entity.Department;
import com.finalproject.model.repository.UserRepository;
import com.finalproject.model.service.UserService;
import com.finalproject.model.entity.Authority;
import com.finalproject.model.entity.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;


/**
 * Spring Boot application class
 */
@SpringBootApplication
@EnableScheduling
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
                    "Green",
                    "owner@gmail.com",
                    passwordEncoder.encode("test"),
                    true,
                    false,
                    superadminAuthorities,
                    Department.HUMAN_RESOURCES
            );
            userRepository.save(u1);

            User u2 = new User(
                    2L,
                    "Monica",
                    "Geller",
                    "supervisor1@gmail.com",
                    passwordEncoder.encode("test"),
                    true,
                    false,
                    adminAuthorities,
                    Department.FINANCE
            );
            userRepository.save(u2);

            User u3 = new User(
                    3L,
                    "Joey",
                    "Tribbiani",
                    "supervisor2@gmail.com",
                    passwordEncoder.encode("test"),
                    true,
                    false,
                    adminAuthorities,
                    Department.SALES
            );
            userRepository.save(u3);

            User u4 = new User(
                    4L,
                    "Chandler",
                    "Bing",
                    "supervisor4@gmail.com",
                    passwordEncoder.encode("test"),
                    true,
                    false,
                    adminAuthorities,
                    Department.TECH
            );
            userRepository.save(u4);
        };
    }
}
