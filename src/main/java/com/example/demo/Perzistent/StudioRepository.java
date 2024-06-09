package com.example.demo.Perzistent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudioRepository extends JpaRepository<StudioEntity, Long> {
    StudioEntity findByNazov(String nazov); // Vyhľadá štúdio podľa názvu.
}
