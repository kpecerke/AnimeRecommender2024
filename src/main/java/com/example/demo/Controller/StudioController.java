package com.example.demo.Controller;

import com.example.demo.Service.StudioDTO;
import com.example.demo.Service.StudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/studio") // Nastavuje základnú URL cestu pre všetky endpointy v tomto kontroléri
public class StudioController {

    private final StudioService studioService;

    @Autowired // Vstrekuje bean StudioService do kontroléra
    public StudioController(StudioService studioService) {
        this.studioService = studioService;
    }

    // Endpoint na získanie všetkých záznamov štúdií
    @GetMapping("/all")
    public ResponseEntity<List<StudioDTO>> getAllStudios() {
        List<StudioDTO> studioDTOList = studioService.getAllStudios();
        if (studioDTOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Vráti status 204, ak je zoznam prázdny
        }
        return new ResponseEntity<>(studioDTOList, HttpStatus.OK); // Vráti zoznam a status 200
    }

    // Endpoint na získanie konkrétneho štúdia podľa jeho ID
    @GetMapping("/{id}")
    public ResponseEntity<StudioDTO> getStudioById(@PathVariable("id") Long id) {
        StudioDTO studioDTO = studioService.getStudioById(id);
        if (studioDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Vráti status 404, ak štúdio nie je nájdené
        }
        return new ResponseEntity<>(studioDTO, HttpStatus.OK); // Vráti štúdio DTO a status 200
    }

    // Endpoint na vytvorenie nového záznamu štúdia
    @PostMapping("/create")
    public ResponseEntity<StudioDTO> createStudio(@RequestBody StudioDTO studioDTO) {
        StudioDTO createdStudio = studioService.createStudio(studioDTO);
        return new ResponseEntity<>(createdStudio, HttpStatus.CREATED); // Vráti vytvorené štúdio a status 201
    }

    // Endpoint na aktualizáciu existujúceho záznamu štúdia podľa jeho ID
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateStudio(@PathVariable("id") Long id, @RequestBody StudioDTO studioDTO) {
        StudioDTO updatedStudio = studioService.updateStudio(id, studioDTO);
        if (updatedStudio == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Vráti status 404, ak štúdio nie je nájdené
        }
        return new ResponseEntity<>(HttpStatus.OK); // Vráti status 200 pri úspešnej aktualizácii
    }

    // Endpoint na zmazanie záznamu štúdia podľa jeho ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteStudio(@PathVariable("id") Long id) {
        studioService.deleteStudio(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Vráti status 204 pri úspešnom zmazaní
    }
}
