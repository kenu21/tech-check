package org.keniu.controllers;

import org.keniu.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {
    private final UserService userService;

    @Autowired
    public RoleController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/set-role")
    public String setRole(@RequestParam String login, @RequestParam String name) {
        userService.setRole(login, name);
        return "set Role";
    }
}
