package com.example.demo.Perzistent;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "anime")
public class AnimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // Jedinečný identifikátor anime

    @Column(name = "nazov")
    private String nazov; // Názov anime

    @Column(name = "rok_vydania")
    private int rokVydania; // Rok vydania anime

    @Column(name = "nazovStudio")
    private String nazovStudio; // Názov štúdia, ktoré anime vyrobilo

    @Column(name = "nazovZaner")
    private String nazovZaner; // Názov žánru anime

    @ManyToOne
    @JoinColumn(name = "studio_id", referencedColumnName = "id")
    private StudioEntity studio; // Odkaz na štúdio, ktoré vytvorilo anime

    @ManyToMany
    @JoinTable(name = "anime_zaner",
            joinColumns = @JoinColumn(name = "anime_id"),
            inverseJoinColumns = @JoinColumn(name = "zaner_id"))
    private Set<ZanerEntity> zanre; // Zoznam žánrov, ku ktorým anime patrí

    public AnimeEntity() {
    }

    // Gettery a settery pre vlastnosti anime
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazov() {
        return nazov;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
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

    @Override
    public String toString() {
        return "AnimeEntity{" +
                "id=" + id +
                ", nazov='" + nazov + '\'' +
                ", rokVydania=" + rokVydania +
                ", nazovStudio='" + nazovStudio + '\'' +
                ", nazovZaner='" + nazovZaner + '\'' +
                ", studio=" + studio +
                ", zanre=" + zanre +
                '}';
    }
}
