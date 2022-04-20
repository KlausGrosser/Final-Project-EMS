package com.finalproject;

import com.finalproject.model.entity.Company;
import com.finalproject.model.entity.Department;
import com.finalproject.model.repository.CompanyRepository;
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
            UserService userService,
            CompanyRepository companyRepository
    ) {
        return (args) -> {
            Set<Authority> superadminAuthorities = new HashSet<Authority>();
            superadminAuthorities.add(Authority.SUPERADMIN);
            superadminAuthorities.add(Authority.ADMIN_HR);
            superadminAuthorities.add(Authority.ADMIN);
            superadminAuthorities.add(Authority.USER);

            Set<Authority> admin_HRAuthorities = new HashSet<Authority>();
            admin_HRAuthorities.add(Authority.ADMIN_HR);
            admin_HRAuthorities.add(Authority.ADMIN);
            admin_HRAuthorities.add(Authority.USER);

            Set<Authority> adminAuthorities = new HashSet<Authority>();
            adminAuthorities.add(Authority.ADMIN);
            adminAuthorities.add(Authority.USER);

            Set<Authority> userAuthorities = new HashSet<Authority>();
            userAuthorities.add(Authority.USER);

            Company c1 = new Company("Foodora", "Jennifer Aniston", "Storgatan 33, 114 55 Stockholm, Sweden");
            Company c2 = new Company("Talabat", "Courteney Cox", "153, Sheikh Zayed Road, Dubai");
            Company c3 =  new Company("Pedidos Ya", "Matt LeBlanc", "Cerrito 1186, Buenos Aires, Argentina");
            Company c4 =  new Company("Mjam", "Matthew Perry","Barichgasse 38, Top 1.4, 1030 Wien" );

           companyRepository.save(c1);
           companyRepository.save(c2);
           companyRepository.save(c3);
           companyRepository.save(c4);


            User u1 = new User(
                    1L,
                    "Rachel",
                    "Green",
                    "HRsupervisor@gmail.com",
                    passwordEncoder.encode("test"),
                    true,
                    false,
                    admin_HRAuthorities,
                    Department.HUMAN_RESOURCES,
                    c1.getName()

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
                    Department.FINANCE,
                    c2.getName()
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
                    Department.SALES,
                    c3.getName()
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
                    Department.TECH,
                    c4.getName()
            );
            userRepository.save(u4);

            User u5 = new User(
                    5L,
                    "Ross",
                    "Geller",
                    "user@gmail.com",
                    passwordEncoder.encode("test"),
                    true,
                    false,
                    userAuthorities,
                    Department.TECH,
                    c4.getName()
            );
            userRepository.save(u5);

            User u0 = new User(
                    0L,
                    "Phoebe",
                    "Buffay",
                    "super_admin@gmail.com",
                    passwordEncoder.encode("test"),
                    true,
                    false,
                    superadminAuthorities,
                    null,
                    null
            );
            userRepository.save(u0);


        };

    }
}

