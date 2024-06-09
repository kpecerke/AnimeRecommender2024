package com.example.demo.Perzistent;

import jakarta.persistence.*;

@Entity
@Table(name = "zaner")
public class ZanerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nazov")
    private String nazov;

    @Column(name = "pocet_anime_tohto_zanru")
    private int pocetAnimeTotohoZanru;

    @Column(name = "najlepsie_hodnotene_anime")
    private String najlepsieHodnoteneAnime;

    // Gettery a settery
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

    public int getPocetAnimeTotohoZanru() {
        return pocetAnimeTotohoZanru;
    }

    public void setPocetAnimeTotohoZanru(int pocetAnimeTotohoZanru) {
        this.pocetAnimeTotohoZanru = pocetAnimeTotohoZanru;
    }

    public String getNajlepsieHodnoteneAnime() {
        return najlepsieHodnoteneAnime;
    }

    public void setNajlepsieHodnoteneAnime(String najlepsieHodnoteneAnime) {
        this.najlepsieHodnoteneAnime = najlepsieHodnoteneAnime;
    }


    @Override
    public String toString() {
        return "ZanerEntity{" +
                "id=" + id +
                ", nazov='" + nazov + '\'' +
                ", pocetAnimeTotohoZanru=" + pocetAnimeTotohoZanru +
                ", najlepsieHodnoteneAnime='" + najlepsieHodnoteneAnime + '\'' +
                '}';
    }
}
