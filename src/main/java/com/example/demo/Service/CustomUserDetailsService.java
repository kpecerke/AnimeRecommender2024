package com.example.demo.Service;

import com.example.demo.Perzistent.User;
import com.example.demo.Perzistent.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // Metóda na načítanie používateľa podľa používateľského mena
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Načítanie používateľa z repozitára podľa používateľského mena
        User user = userRepository.findByUsername(username);
        // Ak používateľ neexistuje, vyhodí sa výnimka UsernameNotFoundException
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        // Vráti sa používateľský objekt s používateľským menom, heslom a oprávneniami
        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities("USER")
                .build();
    }
}
