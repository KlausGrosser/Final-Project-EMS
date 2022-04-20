/*
package com.finalproject.model.service;

import com.finalproject.model.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock private UserRepository userRepository;
  private UserService underTest;

  @BeforeEach
  void setUp() {
        underTest = new UserService(userRepository);
  }

  @Test
  void findAllUsersPageable() {
    //when
    underTest.findAllUsersPageable();
    //then
    verify(userRepository).findAll();

  }

  @Test
  @Disabled    //will disable this test, won´t run
  void createUser() {
  }

  @Test
  @Disabled
  void deleteUser() {
  }
}*/
