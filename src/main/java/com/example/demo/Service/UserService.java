package com.example.demo.Service;

import com.example.demo.Perzistent.AnimeEntity;
import com.example.demo.Perzistent.AnimeRepository;
import com.example.demo.Perzistent.User;
import com.example.demo.Perzistent.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final AnimeRepository animeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, AnimeRepository animeRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.animeRepository = animeRepository;
    }

    // Uloží nového používateľa
    public User saveUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("User with name " + user.getUsername() + " already exists");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword())); // Šifrovanie hesla
        return userRepository.save(user); // Uloženie používateľa do repozitára
    }

    // Nájde používateľa podľa mena
    public User findUserByName(String userNane) {
        return userRepository.findByUsername(userNane);
    }

    // Odstráni všetkých používateľov
    public void  deleteAll() {
        userRepository.deleteAll();
    }

    // Aktualizuje emailovú adresu existujúceho používateľa
    public User updateUserEmail(String username, String newEmail) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        user.setEmail(newEmail);
        return userRepository.save(user);
    }

    // Aktualizuje heslo existujúceho používateľa
    public User updateUserPassword(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        user.setPassword(bCryptPasswordEncoder.encode(password)); // Šifrovanie nového hesla
        return userRepository.save(user);
    }

    // Pridá anime do zoznamu sledovaných anime pre používateľa
    public User addAnimeToWatched(String username, Long animeId) {
        User user = userRepository.findByUsername(username);
        AnimeEntity anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new RuntimeException("Anime not found"));
        user.getWatchedAnime().add(anime);
        return userRepository.save(user);
    }

    // Získa zoznam sledovaných anime pre používateľa podľa mena
    public Set<AnimeEntity> getWatchedAnime(String username) {
        User user = userRepository.findByUsername(username);
        return user.getWatchedAnime();
    }

}
