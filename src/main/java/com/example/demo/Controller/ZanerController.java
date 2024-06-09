package com.example.demo.Controller;

import com.example.demo.Service.ZanerDTO;
import com.example.demo.Service.ZanerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/zaner") // Nastavuje základnú URL cestu pre všetky endpointy v tomto kontroléri
public class ZanerController {

    private final ZanerService zanerService;

    @Autowired // Vstrekuje bean ZanerService do kontroléra
    public ZanerController(ZanerService zanerService) {
        this.zanerService = zanerService;
    }

    // Endpoint na získanie všetkých žánrov
    @GetMapping("/all")
    public ResponseEntity<List<ZanerDTO>> getAllZaners() {
        List<ZanerDTO> zanerDTOList = zanerService.getAllZaners();
        if (zanerDTOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Vráti status 204, ak je zoznam prázdny
        }
        return new ResponseEntity<>(zanerDTOList, HttpStatus.OK); // Vráti zoznam a status 200
    }

    // Endpoint na získanie konkrétneho žánru podľa jeho ID
    @GetMapping("/{id}")
    public ResponseEntity<ZanerDTO> getZanerById(@PathVariable("id") Long id) {
        ZanerDTO zanerDTO = zanerService.getZanerById(id);
        if (zanerDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Vráti status 404, ak žáner nie je nájdený
        }
        return new ResponseEntity<>(zanerDTO, HttpStatus.OK); // Vráti žáner DTO a status 200
    }

    // Endpoint na vytvorenie nového žánru
    @PostMapping("/create")
    public ResponseEntity<ZanerDTO> createZaner(@RequestBody ZanerDTO zanerDTO) {
        ZanerDTO createdZaner = zanerService.createZaner(zanerDTO);
        return new ResponseEntity<>(createdZaner, HttpStatus.CREATED); // Vráti vytvorený žáner a status 201
    }

    // Endpoint na aktualizáciu existujúceho žánru podľa jeho ID
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateZaner(@PathVariable("id") Long id, @RequestBody ZanerDTO zanerDTO) {
        ZanerDTO updatedZaner = zanerService.updateZaner(id, zanerDTO);
        if (updatedZaner == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Vráti status 404, ak žáner nie je nájdený
        }
        return new ResponseEntity<>(HttpStatus.OK); // Vráti status 200 pri úspešnej aktualizácii
    }

    // Endpoint na zmazanie žánru podľa jeho ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteZaner(@PathVariable("id") Long id) {
        zanerService.deleteZaner(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Vráti status 204 pri úspešnom zmazaní
    }
}
