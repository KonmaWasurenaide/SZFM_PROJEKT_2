package com.ggames.GGames.Service;

import com.ggames.GGames.Data.Entity.UserEntity;
import com.ggames.GGames.Service.Dto.UserDto;
import org.springframework.stereotype.Service;

/**
 * A(z) {@code UserService} interfész definiálja a felhasználói adatokkal kapcsolatos
 * üzleti logikai műveleteket, mint például a regisztráció és a hitelesítés.
 * <p>
 * Ez az interfész a szolgáltatási réteg (Service Layer) része.
 * </p>
 */
@Service
public interface UserService {
    /**
     * Regisztrál egy új felhasználót a megadott adatok alapján.
     * <p>
     * Tipikusan magában foglalja az adatok validálását, a jelszó titkosítását
     * és az új {@link UserEntity} adatbázisba mentését.
     * </p>
     *
     * @param userDto A regisztrálandó felhasználó adatait tartalmazó {@link UserDto}.
     * @return A sikeresen regisztrált és elmentett {@link UserEntity}.
     */
    public UserEntity registerUser(UserDto userDto);

    /**
     * Validates user credentials.
     * Returns true if username exists and password matches, false otherwise.
     * No details are shared to protect against spoofing.
     *
     * @param username The username to validate.
     * @param password The raw password to check against the stored hash.
     * @return {@code true} if the credentials are valid; {@code false} otherwise.
     */
    public boolean validateCredentials(String username, String password);
}