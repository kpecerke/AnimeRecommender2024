package com.example.demo.Perzistent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimeRepository extends JpaRepository<AnimeEntity, Long> {
    List<AnimeEntity> findByNazov(String nazov); // Vyhľadá všetky animy podľa názvu.

    List<AnimeEntity> findByRokVydania(int rok); // Vyhľadá všetky animy vydané v určenom roku.

    List<AnimeEntity> findByNazovStudio(String nazov); // Vyhľadá všetky animy produkované určitým štúdiom podľa názvu štúdia.

    List<AnimeEntity> findByNazovZaner(String nazov); // Vyhľadá všetky animy podľa názvu žánru.

}
