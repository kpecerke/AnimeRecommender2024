package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class PasswordEncoderConfig {

    // Konfiguračná trieda pre šifrovanie hesiel pomocou BCryptPasswordEncoder
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(); // Vytvorenie inštancie BCryptPasswordEncoder
    }
}
