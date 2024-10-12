package com.dwf.desafio_3.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class encrypt {
    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String rawPassword = "12345";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        System.out.println("Encoded Password: " + encodedPassword);
    }
}
