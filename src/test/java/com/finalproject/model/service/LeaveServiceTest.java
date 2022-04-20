/*
package com.finalproject.model.service;

import com.finalproject.model.entity.Leave;
import com.finalproject.model.entity.LeaveReason;
import com.finalproject.model.entity.LeaveStatus;
import com.finalproject.model.repository.LeaveRepository;
import com.finalproject.model.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.finalproject.model.entity.ActivityRequestStatus.APPROVED;
import static com.finalproject.model.entity.LeaveReason.HOLIDAYS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LeaveServiceTest {

  @Mock  private LeaveRepository leaveRepository;
  private LeaveService underTest;

  @BeforeEach
  void setUp() {
      underTest = new LeaveService(leaveRepository);
  }

  @Test
  void findAllLeavesPageable() {
    //when
    underTest.findAllLeavesByList();
    //then
    verify(leaveRepository).findAll();
  }


  @Test
  void deleteLeave() {
  }
}*/
