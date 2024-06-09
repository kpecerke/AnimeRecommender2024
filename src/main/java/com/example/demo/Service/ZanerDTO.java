package com.example.demo.Service;

public class ZanerDTO {
    private Long id;
    private String nazovZ;
    private int pocetAnimeTohtoZanru;
    private String najlepsieHodnoteneAnime;

    // Konštruktor pre inicializáciu hodnôt
    public ZanerDTO(Long id, String nazovZ, int pocetAnimeTohtoZanru, String najlepsieHodnoteneAnime) {
        this.id = id;
        this.nazovZ = nazovZ;
        this.pocetAnimeTohtoZanru = pocetAnimeTohtoZanru;
        this.najlepsieHodnoteneAnime = najlepsieHodnoteneAnime;
    }

    // Gettery a settery pre prístup k atribútom
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNazovZ() {
        return nazovZ;
    }

    public void setNazovZ(String nazovZ) {
        this.nazovZ = nazovZ;
    }

    public int getPocetAnimeTohtoZanru() {
        return pocetAnimeTohtoZanru;
    }

    public void setPocetAnimeTohtoZanru(int pocetAnimeTohtoZanru) {
        this.pocetAnimeTohtoZanru = pocetAnimeTohtoZanru;
    }

    public String getNajlepsieHodnoteneAnime() {
        return najlepsieHodnoteneAnime;
    }

    public void setNajlepsieHodnoteneAnime(String najlepsieHodnoteneAnime) {
        this.najlepsieHodnoteneAnime = najlepsieHodnoteneAnime;
    }
}
