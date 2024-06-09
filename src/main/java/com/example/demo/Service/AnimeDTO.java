package com.example.demo.Service;

public class AnimeDTO {
    private int id;
    private String nazovA;
    private int rokVydania;
    private String nazovStudio;
    private String nazovZaner;

    // Konštruktor pre inicializáciu hodnôt
    public AnimeDTO(int id, String nazovA, int rokVydania, String nazovStudio, String nazovZaner) {
        this.id = id;
        this.nazovA = nazovA;
        this.rokVydania = rokVydania;
        this.nazovStudio = nazovStudio;
        this.nazovZaner = nazovZaner;
    }

    // Gettery a settery pre prístup k atribútom
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazovA() {
        return nazovA;
    }

    public void setNazovA(String nazovA) {
        this.nazovA = nazovA;
    }

    public int getRokVydania() {
        return rokVydania;
    }

    public void setRokVydania(int rokVydania) {
        this.rokVydania = rokVydania;
    }

    public String getNazovStudio() {
        return nazovStudio;
    }

    public void setNazovStudio(String nazovStudio) {
        this.nazovStudio = nazovStudio;
    }

    public String getNazovZaner() {
        return nazovZaner;
    }

    public void setNazovZaner(String nazovZaner) {
        this.nazovZaner = nazovZaner;
    }
}
