package com.example.demo.Service;

import com.example.demo.Perzistent.*;
import com.example.demo.dto.RatingDto;
import com.example.demo.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private AnimeRepository animeRepository;

    @Autowired
    private UserRepository userRepository;

    // Metóda pre hodnotenie anime používateľom
    public RatingDto rateAnime(Long animeId, Long userId, int stars) {
        // Získanie entít anime a používateľa
        AnimeEntity anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new ResourceNotFoundException("Anime not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Získanie existujúceho hodnotenia alebo vytvorenie nového
        Rating rating = ratingRepository.findByAnimeAndUser(anime, user)
                .orElse(new Rating());

        // Nastavenie hodnôt pre hodnotenie
        rating.setAnime(anime);
        rating.setUser(user);
        rating.setRating(stars);

        // Uloženie hodnotenia a vrátenie DTO
        Rating savedRating = ratingRepository.save(rating);
        return new RatingDto(savedRating.getAnime().getId(), savedRating.getAnime().getNazov(), savedRating.getRating());
    }

    // Metóda na získanie hodnotení používateľa
    public List<RatingDto> getUserRatings(Long userId) {
        // Získanie používateľa
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Získanie hodnotení používateľa a mapovanie na DTO
        List<Rating> ratings = ratingRepository.findByUser(user);
        return ratings.stream()
                .map(rating -> new RatingDto(rating.getAnime().getId(), rating.getAnime().getNazov(), rating.getRating()))
                .collect(Collectors.toList());
    }

    // Metóda na odporúčanie anime používateľovi
    public RatingDto recommendAnime(Long userId) {
        // Získanie používateľa
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Získanie hodnotení používateľa a idčiek videných anime
        List<Rating> userRatings = ratingRepository.findByUser(user);
        List<Long> seenAnimeIds = userRatings.stream()
                .map(rating -> new Long(rating.getAnime().getId()))
                .collect(Collectors.toList());

        // Získanie hodnotení všetkých anime a ich priemerných hodnôt
        List<Object[]> animeRatings = ratingRepository.findAnimeRatingsOrderedByAverage();

        // Prehľadanie anime s nevidenými id a výber najlepšieho odporúčania
        for (Object[] animeRating : animeRatings) {
            var animeId = ((Integer) animeRating[0]).longValue();

            if (!seenAnimeIds.contains(animeId)) {
                AnimeEntity anime = animeRepository.findById(animeId)
                        .orElseThrow(() -> new ResourceNotFoundException("Anime not found"));
                double avgRating = (Double) animeRating[1];
                return new RatingDto(anime.getId(), anime.getNazov(), (int) avgRating);
            }
        }
        // Ak nie je možné nájsť odporúčanie, vyhodiť výnimku
        throw new ResourceNotFoundException("No suitable recommendation found");
    }
}
