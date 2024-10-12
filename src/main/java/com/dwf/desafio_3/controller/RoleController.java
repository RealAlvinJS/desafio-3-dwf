package com.dwf.desafio_3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoleController {

    @GetMapping("/admin")
    public String adminPage() {
        return "admin";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboardPage(){
        return "/admin/dashboard";
    }

    @GetMapping("/user")
    public String userPage() {
        return "user";
    }
}