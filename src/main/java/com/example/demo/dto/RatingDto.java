package com.example.demo.dto;

public class RatingDto {
    private Integer animeId; // ID anime
    private String animeTitle; // Názov anime
    private int rating; // Hodnotenie anime

    // Konštruktor na inicializáciu hodnôt RatingDto
    public RatingDto(Integer animeId, String animeTitle, int rating) {
        this.animeId = animeId;
        this.animeTitle = animeTitle;
        this.rating = rating;
    }

    // Getter na získanie ID anime
    public Integer getAnimeId() {
        return animeId;
    }

    // Setter na nastavenie ID anime
    public void setAnimeId(Integer animeId) {
        this.animeId = animeId;
    }

    // Getter na získanie názvu anime
    public String getAnimeTitle() {
        return animeTitle;
    }

    // Setter na nastavenie názvu anime
    public void setAnimeTitle(String animeTitle) {
        this.animeTitle = animeTitle;
    }

    // Getter na získanie hodnotenia anime
    public int getRating() {
        return rating;
    }

    // Setter na nastavenie hodnotenia anime
    public void setRating(int rating) {
        this.rating = rating;
    }
}
