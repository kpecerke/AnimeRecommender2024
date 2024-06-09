package com.example.demo.Service;

import com.example.demo.Perzistent.ZanerEntity;
import com.example.demo.Perzistent.ZanerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ZanerService {

    private final ZanerRepository zanerRepository;

    @Autowired
    public ZanerService(ZanerRepository zanerRepository) {
        this.zanerRepository = zanerRepository;
    }

    // Metóda pre získanie všetkých žánrov
    public List<ZanerDTO> getAllZaners() {
        List<ZanerEntity> zanerEntities = zanerRepository.findAll();
        return zanerEntities.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Metóda pre získanie žánru podľa ID
    public ZanerDTO getZanerById(Long id) {
        Optional<ZanerEntity> zanerEntityOptional = zanerRepository.findById(id);
        return zanerEntityOptional.map(this::mapToDTO).orElse(null);
    }

    // Metóda pre vytvorenie nového žánru
    public ZanerDTO createZaner(ZanerDTO zanerDTO) {
        ZanerEntity zanerEntity = mapToEntity(zanerDTO);
        ZanerEntity savedZanerEntity = zanerRepository.save(zanerEntity);
        return mapToDTO(savedZanerEntity);
    }

    // Metóda pre aktualizáciu žánru
    public ZanerDTO updateZaner(Long id, ZanerDTO zanerDTO) {
        return zanerRepository.findById(id)
                .map(existingZanerEntity -> {
                    existingZanerEntity.setNazov(zanerDTO.getNazovZ());
                    // Ak máte ďalšie atribúty na aktualizáciu, tak ich aktualizujte podobne ako názov
                    return mapToDTO(zanerRepository.save(existingZanerEntity));
                })
                .orElse(null);
    }

    // Metóda pre odstránenie žánru
    public void deleteZaner(Long id) {
        zanerRepository.deleteById(id);
    }

    // Metóda na mapovanie z entitného objektu na DTO
    private ZanerDTO mapToDTO(ZanerEntity zanerEntity) {
        return new ZanerDTO(
                zanerEntity.getId(),
                zanerEntity.getNazov(),
                zanerEntity.getPocetAnimeTotohoZanru(),
                zanerEntity.getNajlepsieHodnoteneAnime());
    }

    // Metóda na mapovanie z DTO na entitný objekt
    private ZanerEntity mapToEntity(ZanerDTO zanerDTO) {
        ZanerEntity zanerEntity = new ZanerEntity();
        zanerEntity.setId(zanerDTO.getId());
        zanerEntity.setNazov(zanerDTO.getNazovZ());
        zanerEntity.setPocetAnimeTotohoZanru(zanerDTO.getPocetAnimeTohtoZanru());
        zanerEntity.setNajlepsieHodnoteneAnime(zanerDTO.getNajlepsieHodnoteneAnime());
        return zanerEntity;
    }
}
