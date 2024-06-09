package com.example.demo.Perzistent;

import jakarta.persistence.Entity;
import jakarta.persistence.*;

@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Jedinečný identifikátor hodnotenia

    @ManyToOne
    @JoinColumn(name = "anime_id")
    private AnimeEntity anime; // Odkaz na hodnotené anime

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // Odkaz na používateľa, ktorý urobil hodnotenie

    private int rating; // Počet hviezdičiek hodnotenia

    // Gettery a settery pre vlastnosti hodnotenia
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AnimeEntity getAnime() {
        return anime;
    }

    public void setAnime(AnimeEntity anime) {
        this.anime = anime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

}
