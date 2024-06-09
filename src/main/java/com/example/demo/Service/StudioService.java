package com.example.demo.Service;

import com.example.demo.Perzistent.StudioEntity;
import com.example.demo.Perzistent.StudioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudioService {

    private final StudioRepository studioRepository;

    @Autowired
    public StudioService(StudioRepository studioRepository) {
        this.studioRepository = studioRepository;
    }

    // Získanie všetkých štúdií a ich prevod na DTO objekty
    public List<com.example.demo.Service.StudioDTO> getAllStudios() {
        List<StudioEntity> studioEntities = studioRepository.findAll();
        return studioEntities.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Získanie štúdia podľa ID
    public com.example.demo.Service.StudioDTO getStudioById(Long id) {
        Optional<StudioEntity> studioEntityOptional = studioRepository.findById(id);
        return studioEntityOptional.map(this::mapToDTO).orElse(null);
    }

    // Vytvorenie nového štúdia na základe DTO objektu
    public com.example.demo.Service.StudioDTO createStudio(com.example.demo.Service.StudioDTO studioDTO) {
        StudioEntity studioEntity = mapToEntity(studioDTO);
        StudioEntity savedStudioEntity = studioRepository.save(studioEntity);
        return mapToDTO(savedStudioEntity);
    }

    // Aktualizácia existujúceho štúdia na základe jeho ID a DTO objektu
    public com.example.demo.Service.StudioDTO updateStudio(Long id, com.example.demo.Service.StudioDTO studioDTO) {
        return studioRepository.findById(id)
                .map(existingStudioEntity -> {
                    existingStudioEntity.setNazov(studioDTO.getNazovS());
                    existingStudioEntity.setPocet_vydanych_anime(studioDTO.getPocet_vydanych_anime());
                    existingStudioEntity.setHodnotenie(studioDTO.getHodnotenie());

                    return mapToDTO(studioRepository.save(existingStudioEntity));
                })
                .orElse(null);
    }

    // Odstránenie štúdia podľa jeho ID
    public void deleteStudio(Long id) {
        studioRepository.deleteById(id);
    }

    // Prevod entity štúdia na DTO objekt
    private com.example.demo.Service.StudioDTO mapToDTO(StudioEntity studioEntity) {
        return new com.example.demo.Service.StudioDTO(
                studioEntity.getId(),
                studioEntity.getNazov(),
                studioEntity.getPocet_vydanych_anime(),
                studioEntity.getHodnotenie());
    }

    // Prevod DTO objektu štúdia na entitu
    private StudioEntity mapToEntity(com.example.demo.Service.StudioDTO studioDTO) {
        StudioEntity studioEntity = new StudioEntity();
        studioEntity.setId(studioDTO.getId());
        studioEntity.setNazov(studioDTO.getNazovS());
        studioEntity.setPocet_vydanych_anime(studioDTO.getPocet_vydanych_anime());
        studioEntity.setHodnotenie(studioDTO.getHodnotenie());
        return studioEntity;
    }
}
