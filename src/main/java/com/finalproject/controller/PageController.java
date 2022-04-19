package com.finalproject.controller;

import com.finalproject.dto.CompanyDTO;
import com.finalproject.model.service.CompanyService;
import com.finalproject.util.email.EmailService;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Controller that gives static pages
 */
@Controller
public class PageController implements ErrorController {

    private final EmailService emailService;
    private final CompanyService companyService;

    public PageController(EmailService emailService, CompanyService companyService) {
        this.emailService = emailService;
        this.companyService = companyService;
    }

    @GetMapping("/index")
    public String getIndexPage() {
        return "index";
    }

    @GetMapping("/login")
    public String getLoginPage(@RequestParam(value = "error", required = false) String error,
                               @RequestParam(value = "logout", required = false) String logout,
                               Model model) {
        model.addAttribute("error", error != null);
        model.addAttribute("logout", logout != null);
        return "login";
    }

    @GetMapping("/access-denied")
    public String getAccessDeniedPage() {
        return "/error/403";
    }

    @RequestMapping("/error")
    public String getErrorPath() {
        return "/error/500";
    }

    @GetMapping(path = "/check_in_out")
    public String checkIn(){
        return "check_in_out";
    }

    @GetMapping(path = "/confirm")
    public String confirm(@RequestParam("token") String token){
        emailService.confirmToken(token);
        return "redirect:/login";
    }


}
