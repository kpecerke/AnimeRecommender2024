package com.example.demo.Controller;

import com.example.demo.JwtUtil;
import com.example.demo.Perzistent.AnimeEntity;
import com.example.demo.Perzistent.User;
import com.example.demo.dto.RatingDto;
import com.example.demo.Service.RatingService;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/user") // Nastavuje základnú URL cestu pre všetky endpointy v tomto kontroléri
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    // Endpoint na registráciu používateľa
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            userService.saveUser(user);
            return ResponseEntity.ok("User registered successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // Endpoint na prihlásenie používateľa
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        } catch (Exception e) {
            throw new Exception("Invalid credentials", e);
        }
        final String jwt = jwtUtil.generateToken(user.getUsername());
        return ResponseEntity.ok(jwt);
    }

    // Endpoint na prístup k chránenému zdroju
    @GetMapping("/protected")
    public ResponseEntity<String> getProtectedResource() {
        return ResponseEntity.ok("This is a protected resource");
    }

    // Endpoint na získanie aktuálne autentifikovaného používateľa
    @GetMapping("/me")
    public ResponseEntity<User> getAuthenticatedUser(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findUserByName(userDetails.getUsername());
        user.setPassword("***"); // Skryje heslo
        return ResponseEntity.ok(user);
    }

    // Endpoint na aktualizáciu emailu autentifikovaného používateľa
    @PutMapping("/update/email")
    public ResponseEntity<?> updateEmail(@AuthenticationPrincipal UserDetails userDetails, @RequestBody String newEmail) {
        try {
            User updatedUser = userService.updateUserEmail(userDetails.getUsername(), newEmail.trim());
            updatedUser.setPassword("***"); // Skryje heslo
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Endpoint na aktualizáciu hesla autentifikovaného používateľa
    @PutMapping("/update/password")
    public ResponseEntity<?> updatePassword(@AuthenticationPrincipal UserDetails userDetails, @RequestBody String newPassword) {
        try {
            User updatedUser = userService.updateUserPassword(userDetails.getUsername(), newPassword);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Endpoint na pridanie anime do zoznamu sledovaných anime autentifikovaného používateľa
    @PostMapping("/add-anime/{animeId}")
    public ResponseEntity<?> addAnimeToWatched(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long animeId) {
        try {
            User updatedUser = userService.addAnimeToWatched(userDetails.getUsername(), animeId);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Endpoint na získanie zoznamu sledovaných anime autentifikovaného používateľa
    @GetMapping("/watched-anime")
    public ResponseEntity<Set<AnimeEntity>> getWatchedAnime(@AuthenticationPrincipal UserDetails userDetails) {
        Set<AnimeEntity> watchedAnime = userService.getWatchedAnime(userDetails.getUsername());
        return ResponseEntity.ok(watchedAnime);
    }

    // Endpoint na hodnotenie anime autentifikovaným používateľom
    @PostMapping("/rate")
    public ResponseEntity<RatingDto> rateAnime(@AuthenticationPrincipal UserDetails userDetails, @RequestParam Long animeId, @RequestParam int stars) {
        User user = userService.findUserByName(userDetails.getUsername());
        RatingDto rating = ratingService.rateAnime(animeId, user.getId(), stars);
        return ResponseEntity.ok(rating);
    }

    // Endpoint na získanie hodnotení autentifikovaného používateľa
    @GetMapping("/my-ratings")
    public ResponseEntity<List<RatingDto>> getUserRatings(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findUserByName(userDetails.getUsername());
        List<RatingDto> ratings = ratingService.getUserRatings(user.getId());
        return ResponseEntity.ok(ratings);
    }

    // Endpoint na odporúčanie anime pre autentifikovaného používateľa
    @GetMapping("/recommend")
    public ResponseEntity<RatingDto> recommendAnime(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            User user = userService.findUserByName(userDetails.getUsername());
            RatingDto recommendation = ratingService.recommendAnime(user.getId());
            return ResponseEntity.ok(recommendation);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
