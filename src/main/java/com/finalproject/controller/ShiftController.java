package com.finalproject.controller;

import com.finalproject.model.service.UserService;
import com.finalproject.model.service.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/check_in_out")
public class ShiftController {

    private final ShiftService shiftService;
    private final UserService userService;

    @Autowired
    public ShiftController(ShiftService shiftService, UserService userService) {
        this.shiftService = shiftService;
        this.userService = userService;
    }

    //GetMappings
    @GetMapping(path = "/getTime")
    public String getTotalWorkedTime() {
        return shiftService.getTotalWorkedTime();
    }


    //PostMappings
    @PostMapping(path = "/check_in")
    public String startWorking() {
        return shiftService.start();
    }

    @PostMapping(path = "/check_out")
    public String stopWorking() {

        return shiftService.stop();
    }

}
