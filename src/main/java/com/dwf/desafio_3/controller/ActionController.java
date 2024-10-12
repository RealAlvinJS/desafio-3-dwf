package com.dwf.desafio_3.controller;

import com.dwf.desafio_3.entity.Usuario;
import com.dwf.desafio_3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class ActionController {
    @Autowired
    private UserService userService;

    @ModelAttribute("currentUser")
    public Usuario currentUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            Optional<Usuario> currentUser = userService.GetCurrentUser(userDetails.getUsername());
            return currentUser.orElse(null);
        }
        return null;
    }

}
