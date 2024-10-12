package com.dwf.desafio_3.controller;

import com.dwf.desafio_3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping("/users")
    public String listUsers(Model model){
        model.addAttribute("users", service.ListAllUsers());
        return "users";
    }
}
