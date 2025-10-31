package com.ggames.GGames.Security;

import com.ggames.GGames.Data.Entity.UserEntity;
import com.ggames.GGames.Data.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * A(z) {@code CustomUserDetailsService} osztály implementálja a Spring Security
 * {@link UserDetailsService} interfészét.
 * <p>
 * Ennek a szolgáltatásnak az a feladata, hogy a hitelesítési folyamat során
 * felhasználónév alapján betöltse a felhasználó specifikus adatait ({@link UserDetails})
 * az adatbázisból a {@link UserRepository} segítségével.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    /**
     * A {@link UserRepository} a felhasználói adatok elérésére (adatbázis műveletek) szolgál.
     * Az injektálás a Lombok {@code @RequiredArgsConstructor} annotációval történik.
     */
    private final UserRepository userRepository;

    /**
     * Betölti a felhasználó adatait ({@link UserDetails}) annak felhasználóneve alapján.
     * <p>
     * Ezt a metódust hívja meg a Spring Security a felhasználó hitelesítésekor.
     * Felhasználja a {@link UserRepository}-t a {@link UserEntity} lekéréséhez,
     * majd a felhasználó jelszavát és szerepkörét (role) átalakítja
     * a Spring Security által elvárt formátumra ({@link org.springframework.security.core.userdetails.User}).
     * </p>
     *
     * @param username A keresendő felhasználó felhasználóneve.
     * @return Egy {@link UserDetails} objektum, amely tartalmazza a felhasználó nevét, jelszavát és jogosultságait.
     * @throws UsernameNotFoundException Ha a megadott felhasználónévvel nem található felhasználó.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<GrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_" + user.getRole())
        );
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }
}