package com.example.demo.Perzistent;

import jakarta.persistence.Entity;
import jakarta.persistence.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users") // Zmena názvu tabuľky na "users"
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Jedinečný identifikátor používateľa

    @Email
    @NotBlank
    private String email; // Email používateľa

    private String username; // Užívateľské meno

    @NotBlank
    private String password; // Heslo používateľa

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_anime",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "anime_id")
    )
    private Set<AnimeEntity> watchedAnime = new HashSet<>(); // Množina animí, ktoré používateľ sleduje

    // Gettery a settery pre vlastnosti používateľa
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



    public Set<AnimeEntity> getWatchedAnime() {
        return watchedAnime;
    }

    public void setWatchedAnime(Set<AnimeEntity> watchedAnime) {
        this.watchedAnime = watchedAnime;
    }

}
