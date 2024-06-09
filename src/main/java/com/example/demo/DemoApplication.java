package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Potlačuje všetky varovania kompilátora pre túto triedu
@SuppressWarnings("ALL")
// Označuje túto triedu ako hlavnú triedu aplikácie Spring Boot
@SpringBootApplication
public class DemoApplication {

    // Hlavná metóda, ktorá je vstupným bodom pre spustenie aplikácie
    public static void main(String[] args) {
        // Spustí aplikáciu Spring Boot s použitím tejto triedy ako konfiguračného zdroja
        SpringApplication.run(DemoApplication.class, args);
    }
}
