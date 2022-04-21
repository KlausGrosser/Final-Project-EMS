package com.finalproject;

import com.finalproject.model.entity.*;
import com.finalproject.model.repository.ShiftRepository;
import com.finalproject.model.repository.UserRepository;
import com.finalproject.model.service.CompanyService;
import com.finalproject.model.service.ShiftService;
import com.finalproject.model.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
            UserService userService,
            ShiftRepository shiftRepository,
            ShiftService shiftService,
            CompanyService companyService
    ) {
        return (args) -> {
            Set<Authority> superadminAuthorities = new HashSet<Authority>();
            superadminAuthorities.add(Authority.SUPERADMIN);
            superadminAuthorities.add(Authority.HR_SUPERVISOR);
            superadminAuthorities.add(Authority.HR_AGENT);
            superadminAuthorities.add(Authority.SUPERVISOR);
            superadminAuthorities.add(Authority.USER);

            Set<Authority> hrSupervisorAuthorities = new HashSet<Authority>();
            hrSupervisorAuthorities.add(Authority.HR_SUPERVISOR);
            hrSupervisorAuthorities.add(Authority.HR_AGENT);
            hrSupervisorAuthorities.add(Authority.SUPERVISOR);
            hrSupervisorAuthorities.add(Authority.USER);

            Set<Authority> hrAgentAuthorities = new HashSet<Authority>();
            hrAgentAuthorities.add(Authority.HR_AGENT);
            hrAgentAuthorities.add(Authority.USER);

            Set<Authority> supervisorAuthorities = new HashSet<>();
            supervisorAuthorities.add(Authority.SUPERVISOR);
            supervisorAuthorities.add(Authority.USER);

            Set<Authority> userAuthorities = new HashSet<Authority>();
            userAuthorities.add(Authority.USER);

            Company c0 = new Company(1L,"Delivery Hero", "Gunther", "Berlin, Germany");
            Company c1 = new Company(2L,"Foodora", "Jennifer Aniston", "Storgatan 33, 114 55 Stockholm, Sweden");
            Company c2 = new Company(3L,"Talabat", "Courteney Cox", "153, Sheikh Zayed Road, Dubai");
            Company c3 =  new Company(4L,"Pedidos Ya", "Matt LeBlanc", "Cerrito 1186, Buenos Aires, Argentina");
            Company c4 =  new Company(5L,"Mjam", "Matthew Perry","Barichgasse 38, Top 1.4, 1030 Wien" );

    /*        companyService.save(c1);
            companyService.save(c2);
            companyService.save(c3);
            companyService.save(c4);*/

            User u1 = new User(
                    1L,
                    "Rachel",
                    "Green",
                    "superadmin@gmail.com",
                    passwordEncoder.encode("test"),
                    true,
                    false,
                    superadminAuthorities,
                    Department.HUMAN_RESOURCES,
                    "90 Bedford Street",
                    01724016101,
                    true,
                    null,
                    c0
            );

            User u2 = new User(
                    2L,
                    "Monica",
                    "Geller",
                    "hrsupervisor@gmail.com",
                    passwordEncoder.encode("test"),
                    true,
                    false,
                    hrSupervisorAuthorities,
                    Department.HUMAN_RESOURCES,
                    "90 Bedford Street",
                    01724016102,
                    true,
                    "Rachel Green",
                    c1

            );

            User u3 = new User(
                    3L,
                    "Joey",
                    "Tribbiani",
                    "supervisor1@gmail.com",
                    passwordEncoder.encode("test"),
                    true,
                    false,
                    supervisorAuthorities,
                    Department.SALES,
                    "90 Bedford Street",
                    01724016103,
                    true,
                    "Rachel Green",
                    c2
            );

            User u4 = new User(
                    4L,
                    "Chandler",
                    "Bing",
                    "supervisor2@gmail.com",
                    passwordEncoder.encode("test"),
                    true,
                    false,
                    supervisorAuthorities,
                    Department.TECH,
                    "90 Bedford Street",
                    01724016104,
                    true,
                    "Rachel Green",
                    c3
            );

            User u5 = new User(
                    5L,
                    "Phoebe",
                    "Buffay",
                    "hragent@gmail.com",
                    passwordEncoder.encode("test"),
                    true,
                    false,
                    hrAgentAuthorities,
                    Department.HUMAN_RESOURCES,
                    "90 Bedford Street",
                    01724016105,
                    false,
                    "Joey Tribbiani",
                    c2
            );

            User u6 = new User(
                    5L,
                    "Ross",
                    "Geller",
                    "employee1@gmail.com",
                    passwordEncoder.encode("test"),
                    true,
                    false,
                    userAuthorities,
                    Department.TECH,
                    "90 Bedford Street",
                    01724016105,
                    false,
                    "Chandler Bing",
                    c3
            );


            userService.save(u1);
            userService.save(u2);
            userService.save(u3);
            userService.save(u4);
            userService.save(u5);
            userService.save(u6);

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
                    u6
            );

            shiftService.saveShiftAndUpdateEmployee(u6, shift);
        };
    }

}
