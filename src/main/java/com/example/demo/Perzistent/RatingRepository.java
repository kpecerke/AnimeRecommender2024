package com.example.demo.Perzistent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    Optional<Rating> findByAnimeAndUser(AnimeEntity anime, User user); // Vyhľadá hodnotenie podľa anime a používateľa.
    List<Rating> findByUser(User user); // Vyhľadá všetky hodnotenia vytvorené daným používateľom.

    @Query("SELECT r.anime.id, AVG(r.rating) as avgRating FROM Rating r GROUP BY r.anime.id ORDER BY avgRating DESC")
    List<Object[]> findAnimeRatingsOrderedByAverage(); // Získa zoznam hodnotení anime zoradených podľa priemernej hodnoty hodnotenia.
}
