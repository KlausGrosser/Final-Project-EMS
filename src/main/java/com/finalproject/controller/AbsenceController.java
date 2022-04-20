package com.finalproject.controller;

import com.finalproject.util.email.EmailService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/absence")
public class AbsenceController {

    private final EmailService emailService;

    public AbsenceController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping(path="/sendWarningToEmployees")
    public void sendWarningToEmployees(){
        emailService.sendWarningToAbsentEmployees();
    }
}
