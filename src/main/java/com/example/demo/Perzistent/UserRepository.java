package com.example.demo.Perzistent;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username); // Vyhľadá používateľa podľa užívateľského mena.

    User findByEmail(String email); // Vyhľadá používateľa podľa emailu.
}
