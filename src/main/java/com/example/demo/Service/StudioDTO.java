package com.example.demo.Service;

public class StudioDTO {
    private Long id;
    private String nazovS;
    private int pocet_vydanych_anime;
    private int hodnotenie;

    // Konštruktor pre vytvorenie inštancie s parametrami
    public StudioDTO(Long id, String nazovS, int pocet_vydanych_anime, int hodnotenie) {
        this.id = id;
        this.nazovS = nazovS;
        this.pocet_vydanych_anime = pocet_vydanych_anime;
        this.hodnotenie = hodnotenie;
    }

    // Gettery a settery pre prístup k atribútom
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNazovS() {
        return nazovS;
    }

    public void setNazovS(String nazovS) {
        this.nazovS = nazovS;
    }

    public int getPocet_vydanych_anime() {
        return pocet_vydanych_anime;
    }

    public void setPocet_vydanych_anime(int pocet_vydanych_anime) {
        this.pocet_vydanych_anime = pocet_vydanych_anime;
    }

    public int getHodnotenie() {
        return hodnotenie;
    }

    public void setHodnotenie(int hodnotenie) {
        this.hodnotenie = hodnotenie;
    }
}
