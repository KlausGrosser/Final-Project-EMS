package com.finalproject.controller;

import com.finalproject.dto.RegistrationUserDTO;
import com.finalproject.model.service.UserService;
import com.finalproject.util.email.EmailService;
import com.finalproject.util.exception.UsernameNotUniqueException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Controller that gives pages user registration related
 */
@Log4j2
@Controller
@RequestMapping("/registration")
public class RegistrationController {
    //private final UserService userService;
    private final EmailService emailService;

    public RegistrationController(//UserService userService,
                                  EmailService emailService) {
        //this.userService = userService;
        this.emailService = emailService;
    }

    @GetMapping
    public String getRegistrationPage(@ModelAttribute("user") RegistrationUserDTO registrationUserDTO) {
        return "registration";
    }

    @PostMapping
    public String registerNewUser(@ModelAttribute("user") @Valid RegistrationUserDTO registrationUserDTO,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }

        //userService.createUser(registrationUserDTO);
        emailService.createNewUserAndSendRegistrationMail(registrationUserDTO);

        //return "redirect:/login";
        return "register_check_email";
    }

    @ExceptionHandler(UsernameNotUniqueException.class)
    public String handleRuntimeException(UsernameNotUniqueException e,
                                         Model model) {
        model.addAttribute("user", new RegistrationUserDTO());
        model.addAttribute("usernameErrorMessage", e.getMessage());
        return "registration";
    }

    @GetMapping(path = "/confirm")
    public String confirm(@RequestParam("token") String token){
        emailService.confirmToken(token);
        return "redirect:/login";
    }
}