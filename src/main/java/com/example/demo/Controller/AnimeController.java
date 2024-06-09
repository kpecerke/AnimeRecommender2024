package com.example.demo.Controller;

import com.example.demo.Perzistent.AnimeEntity;
import com.example.demo.Service.AnimeDTO;
import com.example.demo.Service.AnimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/anime") // Nastavuje základnú URL cestu pre všetky endpointy v tomto kontroléri
public class AnimeController {

    private final AnimeService animeService;

    @Autowired // Vstrekuje bean AnimeService do kontroléra
    public AnimeController(AnimeService animeService) {
        this.animeService = animeService;
    }

    // Endpoint na získanie všetkých záznamov anime
    @GetMapping("/all")
    public ResponseEntity<List<AnimeDTO>> getAllAnime() {
        List<AnimeDTO> animeDTOList = animeService.getAllAnime();
        if (animeDTOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Vráti status 204, ak je zoznam prázdny
        }
        return new ResponseEntity<>(animeDTOList, HttpStatus.OK); // Vráti zoznam a status 200
    }

    // Endpoint na získanie konkrétneho anime podľa jeho ID
    @GetMapping("/{id}")
    public ResponseEntity<AnimeDTO> getAnimeById(@PathVariable("id") Long id) {
        AnimeDTO animeDTO = animeService.getAnimeById(id);
        if (animeDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Vráti status 404, ak anime nie je nájdené
        }
        return new ResponseEntity<>(animeDTO, HttpStatus.OK); // Vráti anime DTO a status 200
    }

    // Endpoint na vytvorenie nového záznamu anime
    @PostMapping("/create")
    public ResponseEntity<AnimeDTO> createAnime(@RequestBody AnimeDTO animeDTO) {
        AnimeDTO createdAnime = animeService.createAnime(animeDTO);
        return new ResponseEntity<>(createdAnime, HttpStatus.CREATED); // Vráti vytvorené anime a status 201
    }

    // Endpoint na aktualizáciu existujúceho záznamu anime podľa jeho ID
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAnime(@PathVariable("id") Long id, @RequestBody AnimeDTO animeDTO) {
        AnimeDTO updatedAnime = animeService.updateAnime(id, animeDTO);
        if (updatedAnime == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Vráti status 404, ak anime nie je nájdené
        }
        return new ResponseEntity<>(HttpStatus.OK); // Vráti status 200 pri úspešnej aktualizácii
    }

    // Endpoint na vyhľadávanie anime podľa žánru
    @GetMapping("/search")
    public ResponseEntity<List<AnimeEntity>> searchAnimeByZaner(@RequestParam("zaner") String zaner) {
        List<AnimeEntity> animeEntityList = animeService.searchAnimeByZaner(zaner);
        if (animeEntityList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Vráti status 204, ak nie sú nájdené žiadne výsledky
        }
        return new ResponseEntity<>(animeEntityList, HttpStatus.OK); // Vráti zoznam a status 200
    }

    // Endpoint na vyhľadávanie anime podľa názvu
    @GetMapping("/search-by-nazov")
    public ResponseEntity<List<AnimeEntity>> searchAnimeByNazov(@RequestParam("nazov") String nazov) {
        List<AnimeEntity> animeEntityList = animeService.searchAnimeByNazov(nazov);
        if (animeEntityList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Vráti status 204, ak nie sú nájdené žiadne výsledky
        }
        return new ResponseEntity<>(animeEntityList, HttpStatus.OK); // Vráti zoznam a status 200
    }

    // Endpoint na vyhľadávanie anime podľa roku vydania
    @GetMapping("/search-by-rok")
    public ResponseEntity<List<AnimeEntity>> searchAnimeByRokVydania(@RequestParam("rok") int rok) {
        List<AnimeEntity> animeEntityList = animeService.searchAnimeByRokVydania(rok);
        if (animeEntityList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Vráti status 204, ak nie sú nájdené žiadne výsledky
        }
        return new ResponseEntity<>(animeEntityList, HttpStatus.OK); // Vráti zoznam a status 200
    }

    // Endpoint na vyhľadávanie anime podľa štúdia
    @GetMapping("/search-by-studio")
    public ResponseEntity<List<AnimeEntity>> searchAnimeByStudio(@RequestParam("studio") String studio) {
        List<AnimeEntity> animeEntityList = animeService.searchAnimeByStudio(studio);
        if (animeEntityList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Vráti status 204, ak nie sú nájdené žiadne výsledky
        }
        return new ResponseEntity<>(animeEntityList, HttpStatus.OK); // Vráti zoznam a status 200
    }

    // Endpoint na zmazanie záznamu anime podľa jeho ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAnime(@PathVariable("id") Long id) {
        animeService.deleteAnime(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Vráti status 204 pri úspešnom zmazaní
    }
}
