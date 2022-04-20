package com.finalproject.controller;

import com.finalproject.dto.RegistrationUserDTO;
import com.finalproject.model.entity.Shift;
import com.finalproject.util.email.EmailService;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller that gives static pages
 */
@Controller
public class PageController implements ErrorController {
    private final EmailService emailService;

    public PageController(EmailService emailService) {
        this.emailService = emailService;
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

   /* @GetMapping(path = "/shifts")
    public String getCheckInPage(){
        return "check_in_out";
    }*/

    @GetMapping(path = "/confirm")
    public String confirm(@RequestParam("token") String token){
        emailService.confirmToken(token);
        return "redirect:/login";
    }

}
