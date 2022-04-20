package com.finalproject.controller;

import com.finalproject.model.entity.Shift;
import com.finalproject.model.entity.User;
import com.finalproject.model.service.UserService;
import com.finalproject.model.service.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


//@RestController
@Controller
@RequestMapping("/shifts")
public class ShiftController {

    private final ShiftService shiftService;
    private final UserService userService;

    @Autowired
    public ShiftController(ShiftService shiftService, UserService userService) {
        this.shiftService = shiftService;
        this.userService = userService;
    }

    //GetMapping
    @GetMapping
    public String getCheckInPage(Model model){
        User employee = userService.getCurrentUser();
        Shift shift = shiftService.findCurrentShift(employee);

        model.addAttribute("shift", shift);

        return "check_in_out";
    }

    @GetMapping(path="/assign_shifts")
    public String getAssignShiftsPage(@ModelAttribute("shift") Shift shift) {
        return "assign-shifts";
    }

    @PostMapping(path = "/get_time")
    public String getTotalWorkedTime(Model model) {
        User employee = userService.getCurrentUser();
        Shift shift = shiftService.findCurrentShift(employee);

        model.addAttribute("shift", shift);
        shiftService.getTotalWorkedTime();
        return "check_in_out";
    }

    //PostMappings
    @PostMapping(path = "/check_in")
    public String startWorking(Model model) {
        User employee = userService.getCurrentUser();
        Shift shift = shiftService.findCurrentShift(employee);

        model.addAttribute("shift", shift);
        shiftService.start();
        return "check_in_out";
    }

    @PostMapping(path = "/check_out")
    public String stopWorking(Model model) {
        User employee = userService.getCurrentUser();
        Shift shift = shiftService.findCurrentShift(employee);

        model.addAttribute("shift", shift);
        shiftService.stop();
        return "check_in_out";
    }
}

