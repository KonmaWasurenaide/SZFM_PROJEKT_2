package com.ggames.GGames.Security;

import com.ggames.GGames.Data.Entity.UserEntity;
import com.ggames.GGames.Data.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * A Spring Security {@link UserDetailsService} interfészének egyedi implementációja.
 *
 * <p>Feladata a felhasználó adatainak betöltése az adatbázisból a hitelesítési folyamat során.</p>
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Betölti a felhasználó adatait ({@link UserDetails}) annak felhasználóneve alapján a Spring Security számára.
     *
     * @param username A keresendő felhasználó felhasználóneve.
     * @return A betöltött {@link UserEntity} objektum, amely {@link UserDetails}-ként szolgál.
     * @throws UsernameNotFoundException Ha a megadott felhasználónévvel nem található felhasználó.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return user;
    }
}