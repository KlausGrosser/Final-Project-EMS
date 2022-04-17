package com.finalproject.controller;

import com.finalproject.model.service.SupervisorService;
import org.springframework.stereotype.Controller;

@Controller
public class SupervisorController {

    private final SupervisorService supervisorService;

    public SupervisorController(SupervisorService supervisorService) {
        this.supervisorService = supervisorService;
    }
}
