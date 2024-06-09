package com.example.demo.Service;

import com.example.demo.Perzistent.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnimeService {

    private final AnimeRepository animeRepository;
    private final StudioRepository studioRepository;
    @Autowired
    private ZanerRepository zanerRepository;

    // Metóda pre vytvorenie nového anime
    public AnimeDTO createAnime(AnimeDTO animeDTO) {
        AnimeEntity animeEntity = mapToEntity(animeDTO);

        // Kontrola či žáner existuje
        ZanerEntity existingZaner = zanerRepository.findByNazov(animeEntity.getNazovZaner());
        if (existingZaner != null) {
            // Zvýšenie počtu anime pre tento žáner
            existingZaner.setPocetAnimeTotohoZanru(existingZaner.getPocetAnimeTotohoZanru() + 1);
            zanerRepository.save(existingZaner);
        } else {
            // Vytvorenie nového záznamu pre žáner
            ZanerEntity newZaner = new ZanerEntity();
            newZaner.setNazov(animeEntity.getNazovZaner());
            newZaner.setPocetAnimeTotohoZanru(1);
            zanerRepository.save(newZaner);
        }

        // Získanie príslušného štúdia
        StudioEntity studioEntity = studioRepository.findByNazov(animeEntity.getNazovStudio());
        if (studioEntity != null) {
            // Inkrementácia anime pre toto štúdio
            studioEntity.setPocet_vydanych_anime(studioEntity.getPocet_vydanych_anime() + 1);
            studioRepository.save(studioEntity);
        }

        AnimeEntity savedAnimeEntity = animeRepository.save(animeEntity);
        return mapToDTO(savedAnimeEntity);
    }

    // Konštruktor
    @Autowired
    public AnimeService(AnimeRepository animeRepository, StudioRepository studioRepository){
        this.animeRepository = animeRepository;
        this.studioRepository = studioRepository;
    }

    // Metóda pre vyhľadanie anime podľa názvu
    public List<AnimeEntity> searchAnimeByNazov(String nazov) {
        return animeRepository.findByNazov(nazov);
    }

    // Metóda pre vyhľadanie anime podľa roku vydania
    public List<AnimeEntity> searchAnimeByRokVydania(int rok) {
        return animeRepository.findByRokVydania(rok);
    }

    // Metóda pre vyhľadanie anime podľa štúdia
    public List<AnimeEntity> searchAnimeByStudio(String nazovStudio) {
        return animeRepository.findByNazovStudio(nazovStudio);
    }

    // Metóda pre vyhľadanie anime podľa žánru
    public List<AnimeEntity> searchAnimeByZaner(String nazovZaner) {
        return animeRepository.findByNazovZaner(nazovZaner);
    }

    // Metóda pre získanie všetkých anime
    public List<AnimeDTO> getAllAnime() {
        List<AnimeEntity> animeEntities = animeRepository.findAll();
        return animeEntities.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Metóda pre získanie anime podľa ID
    public AnimeDTO getAnimeById(Long id) {
        Optional<AnimeEntity> animeEntityOptional = animeRepository.findById(id);
        return animeEntityOptional.map(this::mapToDTO).orElse(null);
    }

    // Metóda pre aktualizáciu existujúceho anime
    public AnimeDTO updateAnime(Long id, AnimeDTO animeDTO) {
        Optional<AnimeEntity> animeEntityOptional = animeRepository.findById(id);
        if (animeEntityOptional.isPresent()) {
            AnimeEntity existingAnimeEntity = animeEntityOptional.get();

            existingAnimeEntity.setNazov(animeDTO.getNazovA());
            existingAnimeEntity.setRokVydania(animeDTO.getRokVydania());
            existingAnimeEntity.setNazovStudio(animeDTO.getNazovStudio());
            existingAnimeEntity.setNazovZaner(animeDTO.getNazovZaner());

            AnimeEntity updatedAnimeEntity = animeRepository.save(existingAnimeEntity);
            return mapToDTO(updatedAnimeEntity);
        } else {
            return null;
        }
    }

    // Metóda pre vymazanie anime
    public void deleteAnime(Long id) {
        Optional<AnimeEntity> animeEntityOptional = animeRepository.findById(id);
        animeEntityOptional.ifPresent(animeEntity -> {
            // Získanie príslušného štúdia
            StudioEntity studioEntity = studioRepository.findByNazov(animeEntity.getNazovStudio());
            if (studioEntity != null) {
                // Dekrementuj anime pre toto štúdio
                studioEntity.setPocet_vydanych_anime(studioEntity.getPocet_vydanych_anime() - 1);
                studioRepository.save(studioEntity);
            }
        });
        animeRepository.deleteById(id);
    }

    // Mapovacia metóda z entity na DTO
    private AnimeDTO mapToDTO(AnimeEntity animeEntity) {
        return new AnimeDTO(
                animeEntity.getId(),
                animeEntity.getNazov(),
                animeEntity.getRokVydania(),
                animeEntity.getNazovStudio(),
                animeEntity.getNazovZaner()
        );
    }

    // Mapovacia metóda z DTO na entity
    private AnimeEntity mapToEntity(AnimeDTO animeDTO) {
        AnimeEntity animeEntity = new AnimeEntity();
        animeEntity.setId(animeDTO.getId());
        animeEntity.setNazov(animeDTO.getNazovA());
        animeEntity.setRokVydania(animeDTO.getRokVydania());
        animeEntity.setNazovStudio(animeDTO.getNazovStudio());
        animeEntity.setNazovZaner(animeDTO.getNazovZaner());
        return animeEntity;
    }

    // Metóda pre inkrementáciu počtu anime pre dané štúdio
    private void incrementStudioAnimeCount(String studioName) {
        StudioEntity studioEntity = studioRepository.findByNazov(studioName);
        if (studioEntity != null) {
            studioEntity.setPocet_vydanych_anime(studioEntity.getPocet_vydanych_anime() + 1);
            studioRepository.save(studioEntity);
        }
    }

    // Metóda pre dekrementáciu počtu anime pre dané štúdio
    private void decrementStudioAnimeCount(String studioName) {
        StudioEntity studioEntity = studioRepository.findByNazov(studioName);
        if (studioEntity != null) {
            studioEntity.setPocet_vydanych_anime(studioEntity.getPocet_vydanych_anime() - 1);
            studioRepository.save(studioEntity);
        }
    }

}

