//package com.finalproject.controller;
//
//import com.finalproject.dto.LeaveDTO;
//import com.finalproject.model.service.LeaveService;
//import com.finalproject.util.exception.UsernameNotUniqueException;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//
//@Log4j2
//@Controller
//@RequestMapping("/leaves")
//public class LeaveController2 {
//
//    private final LeaveService leaveService;
//
//    public LeaveController2(LeaveService leaveService) {
//        this.leaveService = leaveService;
//    }
//
//    //Get Mapping
//    @GetMapping
//    public String getLeavePage(@ModelAttribute("leave") LeaveDTO leaveDTO) {
//        return "add-leave";
//    }
//
//    @PostMapping
//    public String addNewLeave(@ModelAttribute("leave") @Valid LeaveDTO leaveDTO,
//                              BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "add-leave";
//        }
//
//        leaveService.createLeave(leaveDTO);
//
//        return "leaves";
//    }
//
//    @ExceptionHandler(UsernameNotUniqueException.class)
//    public String handleRuntimeException(UsernameNotUniqueException e,
//                                         Model model) {
//        model.addAttribute("leave", new LeaveDTO());
//        model.addAttribute("usernameErrorMessage", e.getMessage());
//        return "add-leave";
//    }
//}

