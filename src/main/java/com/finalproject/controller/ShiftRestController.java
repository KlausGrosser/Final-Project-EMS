package com.finalproject.controller;

import com.finalproject.model.entity.User;
import com.finalproject.model.service.UserService;
import com.finalproject.util.autocomplete.UserItem;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/shift")
public class ShiftRestController {

    private final UserService userService;

    public ShiftRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserItem> stateItems(@RequestParam(value = "q", required = false) String query) {
        User[] employeesArray = userService.getAllEmployees().toArray(new User[0]);

        if (query.isEmpty()) {
            return Arrays.stream(employeesArray)
                    .limit(15)
                    .map(this::mapToUserItem)
                    .collect(Collectors.toList());
        }

        return Arrays.stream(employeesArray)
                .filter(user -> user.getFullName()
                        .toLowerCase()
                        .contains(query))
                .limit(15)
                .map(this::mapToUserItem)
                .collect(Collectors.toList());
    }

    private UserItem mapToUserItem(User user) {
        return UserItem.builder()
                .id(user)
                .text(user.getFullName())
                .slug(user.getFullName())
                .build();
    }
}
