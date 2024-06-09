package com.example.demo.Perzistent;

import jakarta.persistence.*;

@Entity
@Table(name = "studio")
public class StudioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Jedinečný identifikátor štúdia

    @Column(name = "nazov")
    private String nazov; // Názov štúdia

    @Column(name = "pocet_vydanych_anime")
    private int pocet_vydanych_anime; // Počet vydaných animí štúdia

    @Column(name = "hodnotenie")
    private int hodnotenie; // Hodnotenie štúdia

    // Metóda na zvýšenie počtu vydaných animí štúdia
    public void incrementAnimeCount() {
        this.pocet_vydanych_anime++;
    }

    // Gettery a settery pre vlastnosti štúdia
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNazov() {
        return nazov;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
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

    @Override
    public String toString() {
        return "StudioEntity{" +
                "id=" + id +
                ", nazov='" + nazov + '\'' +
                ", pocet_vydanych_anime=" + pocet_vydanych_anime +
                ", hodnotenie=" + hodnotenie +
                '}';
    }
}
