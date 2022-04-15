package com.finalproject.controller;

import com.finalproject.model.service.UserService;
import com.finalproject.model.service.WorkHoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/check_in_out")
public class WorkHoursController {

    private final WorkHoursService workHoursService;
    private final UserService userService;

    @Autowired
    public WorkHoursController(WorkHoursService workHoursService, UserService userService) {
        this.workHoursService = workHoursService;
        this.userService = userService;
    }

    //GetMappings
    @GetMapping(path = "/getTime")
    public String getTotalWorkedTime() {
        return workHoursService.getTotalWorkedTime();
    }


    //PostMappings
    @PostMapping(path = "/check_in")
    public String startWorking() {
        return workHoursService.start();
    }

    @PostMapping(path = "/check_out")
    public String stopWorking() {

        return workHoursService.stop();
    }

}
