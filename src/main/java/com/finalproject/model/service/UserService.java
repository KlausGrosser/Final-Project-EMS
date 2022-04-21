package com.finalproject.model.service;

import com.finalproject.dto.RegistrationUserDTO;
import com.finalproject.dto.UpdateUserDTO;
import com.finalproject.dto.UpdateUserProfileDTO;
import com.finalproject.model.entity.Activity;
import com.finalproject.model.entity.Authority;
import com.finalproject.model.entity.Supervisor;
import com.finalproject.model.entity.User;
import com.finalproject.model.repository.SupervisorRepository;
import com.finalproject.model.repository.UserRepository;
import com.finalproject.util.exception.UsernameNotUniqueException;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Service with business logic for managing users
 *
 */
@Log4j2
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final MessageSource messageSource;
    private final PasswordEncoder passwordEncoder;
    private final SupervisorRepository supervisorRepository;

    public UserService(UserRepository userRepository,
                       MessageSource messageSource,
                       PasswordEncoder passwordEncoder, SupervisorRepository supervisorRepository) {
        this.userRepository = userRepository;
        this.messageSource = messageSource;
        this.passwordEncoder = passwordEncoder;
        this.supervisorRepository = supervisorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException(username));
    }

    public Page<User> findAllUsersPageable(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User getUserById(long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Invalid user id: " + id));
    }

    public User createUser(RegistrationUserDTO userDTO) {
        User user = User
                .builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .fullName(userDTO.getFirstName() + " " + userDTO.getLastName())
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .enabled(false)
                .firstLogin(true)
                .authorities(Collections.singleton(Authority.USER))
                .department(userDTO.getDepartment().name())
                .supervisorRole(userDTO.isSupervisorRole())
                .supervisorName(userDTO.getSupervisorName())
                .company(userDTO.getCompany())
                .build();
        try {
            userRepository.save(user);
            if(userDTO.isSupervisorRole()){
                Supervisor supervisor = new Supervisor(user.getId(), user.getFullName(), user.getDepartment());
                supervisorRepository.save(supervisor);
            }
            log.info("New user " + user);

        } catch (DataIntegrityViolationException e) {
            log.error("Login not unique: " + userDTO.getUsername());
            throw new UsernameNotUniqueException(messageSource.getMessage(
                    "users.registration.login.not_unique",
                    null,
                    LocaleContextHolder.getLocale()) + userDTO.getUsername(), e);
        }
        return user;
    }

    public void deleteUser(long id) {
        User user = getUserById(id);
        List<Activity> userActivities = user.getActivities();
        for (Activity activity : userActivities) {
            activity.getUsers().remove(user);
        }
        userRepository.delete(user);
    }

    public void updateUser(long id, UpdateUserDTO userDTO) {
        User user = getUserById(id);

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setUsername(userDTO.getUsername());
        user.setSupervisorRole(userDTO.isSupervisorRole());
        if (Objects.nonNull(userDTO.getPassword()) && userDTO.getPassword().length() > 0) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        if (Objects.nonNull(userDTO.getAuthorities())) {
            user.setAuthorities(userDTO.getAuthorities());
        }

        if (Objects.nonNull(userDTO.getDepartment())) {
            user.setDepartment(userDTO.getDepartment().name());
        }

        if (Objects.nonNull(userDTO.getSupervisorName())) {
            user.setSupervisorName(userDTO.getSupervisorName());
        }

        if (Objects.nonNull(userDTO.getCompany())) {
            user.setCompany(userDTO.getCompany());
        }

        try {
            userRepository.save(user);
            if(userDTO.isSupervisorRole()){
                Supervisor supervisor = new Supervisor(user.getId(), user.getFullName(), user.getDepartment());
                supervisorRepository.save(supervisor);
            }
        } catch (DataIntegrityViolationException e) {
            log.error("Login not unique: " + userDTO.getUsername());
            throw new UsernameNotUniqueException(messageSource.getMessage(
                    "users.registration.login.not_unique",
                    null,
                    LocaleContextHolder.getLocale()) + userDTO.getUsername(), e);
        }
    }

    public void save(User employee){
        userRepository.save(employee);
    }

    public User findByUsername(String email){
        return userRepository.findByUsername(email)
                .orElseThrow(()-> new UsernameNotFoundException(email));
    }

    public User findByFullName(String fullName){
        return userRepository.findByFullName(fullName)
                .orElseThrow();
    }

    public int enableUser(String email) {
        return userRepository.enableUser(email);
    }

    public User getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return this.findByUsername(auth.getName());
    }

    public void changePassword(User user, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);

        user.setPassword(encodedPassword);
        user.setFirstLogin(false);

        userRepository.save(user);
    }

    public void updateUserProfile(UpdateUserProfileDTO userDTO) {
        User user = getCurrentUser();

        user.setAddress(userDTO.getAddress());
        user.setTelephone(userDTO.getTelephone());

        if (Objects.nonNull(userDTO.getPassword()) && userDTO.getPassword().length() > 0) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        userRepository.save(user);
    }

       //Get employee by keyword
   public Page<User> findByKeyword(Pageable pageable, String keyword) {
       return userRepository.findByKeyword(keyword, pageable);
   }

   public List<User> getAllEmployees(){
        return userRepository.findAll();
   }

    public ArrayList<Supervisor> getAllSupervisors(){
        return (ArrayList<Supervisor>) supervisorRepository.findAll();
    }

    public void saveToSupervisorRepository(Supervisor supervisor){
        this.supervisorRepository.save(supervisor);
    }

    public void saveAll(List<User> users){
      userRepository.saveAll(users);
    }

    public void saveAllToSupervisorRepo(List<Supervisor> users){
        supervisorRepository.saveAll(users);
    }

    public List<String> getAllEmployeesNames(){
        List<String> names = new ArrayList<>();

        for(User user : userRepository.findAll()){
            names.add(user.getFullName());
        }

        return names;
    }

}
