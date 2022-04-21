package com.finalproject.controller;

import com.finalproject.dto.CompanyDTO;
import com.finalproject.dto.UpdateUserProfileDTO;
import com.finalproject.model.entity.Activity;
import com.finalproject.dto.UpdateUserDTO;
import com.finalproject.model.entity.Authority;
import com.finalproject.model.entity.Department;
import com.finalproject.model.entity.User;
import com.finalproject.model.repository.CompanyRepository;
import com.finalproject.model.service.CompanyService;
import com.finalproject.model.service.UserService;
import com.finalproject.util.exception.UsernameNotUniqueException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * Controller that react on user related requests
 * @see Activity
 */
@Controller
@Log4j2
@RequestMapping
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final CompanyRepository companyRepository;
    private final CompanyService companyService;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder, CompanyRepository companyRepository, CompanyService companyService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.companyRepository = companyRepository;
        this.companyService = companyService;
    }

/*    @GetMapping("/users")
    public String getListOfUsers(Model model,
                                 @PageableDefault(size = 15,
                                         sort = {"lastName", "firstName"}) Pageable pageable) {
        model.addAttribute("users", userService.findAllUsersPageable(pageable));
        return "users";
    }*/

        @GetMapping("/users")
    public String getListOfUsers(Model model, String keyword,
                                 @PageableDefault(size = 15,
                                         sort = {"lastName", "firstName"}) Pageable pageable) {
        if (keyword != null){
            model.addAttribute("users", userService.findByKeyword(pageable, keyword));
        }
        else {
            model.addAttribute("users", userService.findAllUsersPageable(pageable));
        }
        return "users";
    }



    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }

    @GetMapping("/users/update/{id}")
    public String getUserUpdatePage(@PathVariable("id") long id, Model model) {
        User user = userService.getUserById(id);

        model.addAttribute("user", user);
        model.addAttribute("authorities", Authority.values());
        model.addAttribute("departments", Department.values());
        model.addAttribute("supervisorsList", userService.getAllSupervisors());
        model.addAttribute("companiesList", companyService.findAll());
        return "update-user";
    }

    @PostMapping("/users/update/{id}")
    public String updateUser(@PathVariable("id") long id,
                             @ModelAttribute("user") @Valid UpdateUserDTO userDTO,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("authorities", Authority.values());

            return "update-user";
        }

        try {
            userService.updateUser(id, userDTO);
        } catch (UsernameNotUniqueException e) {
            model.addAttribute("usernameErrorMessage", e.getMessage());
            model.addAttribute("authorities", Authority.values());
            return "update-user";
        }

        return "redirect:/users";
    }

    @GetMapping("/profile")
    public String getUserProfilePage(@AuthenticationPrincipal User user,
                                     Model model) {
        model.addAttribute("user", userService.getUserById(user.getId()));
        model.addAttribute("company", new CompanyDTO());

        if(user.isFirstLogin()){
            return "password_change";
        }else if(user.getAuthorities().contains(Authority.SUPERADMIN) && companyRepository.findAll().isEmpty()){
            return "create_company";
        }else{
            return "user-profile";
        }
    }

    @PostMapping("/change_password")
    public String processChangePassword(HttpServletRequest request, HttpServletResponse response,
                                        Model model, RedirectAttributes ra,
                                        @AuthenticationPrincipal User user) throws ServletException {

        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");

        model.addAttribute("pageTitle", "Change your password");

        if (oldPassword.equals(newPassword)) {
            model.addAttribute("message", "Your new password must be different than the old one.");

            return "password_change";
        }

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            model.addAttribute("message", "Your old password is incorrect.");
            return "password_change";

        } else {
            userService.changePassword(user, newPassword);
            request.logout();
            ra.addFlashAttribute("message", "You have changed your password successfully. "
                    + "Please complete your profile.");

//            return "redirect:/login";
            return "redirect:/user/profileUpdate";
        }

    }

    @GetMapping("/user/profileUpdate")
    public String getUserProfileUpdatePage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        return "update-user-profile";
    }

    @PostMapping("/user/profileUpdate")
    public String updateUserProfile(@ModelAttribute("user") @Valid UpdateUserProfileDTO userDTO,
                             BindingResult bindingResult,
                             Model model) {

        userService.updateUserProfile(userDTO);

        return "redirect:/profile";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException e) {
        log.error(e.getMessage());
        return "error/404";
    }


}
