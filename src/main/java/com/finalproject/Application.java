package com.finalproject;

import com.finalproject.model.entity.*;
import com.finalproject.model.repository.ShiftRepository;
import com.finalproject.model.repository.UserRepository;
import com.finalproject.model.service.ShiftService;
import com.finalproject.model.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
          UserService userService,
          ShiftRepository shiftRepository,
          ShiftService shiftService
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
              Department.HUMAN_RESOURCES,
              "90 Bedford Street",
              01724016101,
              true,
              null
      );

      User u2 = new User(
              2L,
              "Monica",
              "Geller",
              "supervisor1@gmail.com",
              passwordEncoder.encode("test"),
              true,
              false,
              adminAuthorities,
              Department.FINANCE,
              "90 Bedford Street",
              01724016102,
              true,
              "Rachel Green"
      );

      User u3 = new User(
              3L,
              "Joey",
              "Tribbiani",
              "supervisor2@gmail.com",
              passwordEncoder.encode("test"),
              true,
              false,
              adminAuthorities,
              Department.SALES,
              "90 Bedford Street",
              01724016103,
              true,
              "Rachel Green"
      );

      User u4 = new User(
              4L,
              "Chandler",
              "Bing",
              "supervisor4@gmail.com",
              passwordEncoder.encode("test"),
              true,
              false,
              adminAuthorities,
              Department.TECH,
              "90 Bedford Street",
              01724016104,
              true,
              "Rachel Green"
      );

      User u5 = new User(
              5L,
              "Phoebe",
              "Buffay",
              "employee1@gmail.com",
              passwordEncoder.encode("test"),
              true,
              false,
              userAuthorities,
              Department.SALES,
              "90 Bedford Street",
              01724016105,
              false,
              "Joey Tribbiani"
      );

      userService.save(u1);
      userService.save(u2);
      userService.save(u3);
      userService.save(u4);
      userService.save(u5);

      Supervisor s1 = new Supervisor(u1.getId(), u1.getFullName(), u1.getDepartment());
      Supervisor s2 = new Supervisor(u2.getId(), u2.getFullName(), u2.getDepartment());
      Supervisor s3 = new Supervisor(u3.getId(), u3.getFullName(), u3.getDepartment());
      Supervisor s4 = new Supervisor(u4.getId(), u4.getFullName(), u4.getDepartment());

      userService.saveToSupervisorRepository(s1);
      userService.saveToSupervisorRepository(s2);
      userService.saveToSupervisorRepository(s3);
      userService.saveToSupervisorRepository(s4);

      Shift shift = new Shift(
              LocalDate.now(),
              LocalDateTime.of(2022,4,20,0, 0),
              LocalDateTime.of(2022,4,20,8, 0),
              u1
      );

      shiftService.saveShiftAndUpdateEmployee(u1, shift);
    };
  }

}