package com.ggames.GGames.Service;

import com.ggames.GGames.Data.Entity.UserEntity;
import com.ggames.GGames.Service.Dto.GameDto;
import com.ggames.GGames.Service.Dto.UserDto;
import java.util.List;

/**
 * Service interfész a felhasználókkal kapcsolatos üzleti logikához és adatkezelési műveletekhez.
 *
 * <p>Felelős a felhasználói regisztrációért, hitelesítésért és a felhasználói könyvtár (Library) kezeléséért.</p>
 */
public interface UserService {

    /**
     * Regisztrál egy új felhasználót a megadott adatokkal.
     *
     * @param userDto A regisztrációs űrlapból származó adatátviteli objektum.
     * @return Az elmentett {@code UserEntity} objektum.
     */
    UserEntity registerUser(UserDto userDto);

    /**
     * Hitelesíti a felhasználó belépési adatait (felhasználónév és jelszó).
     *
     * @param username A felhasználónév.
     * @param password A jelszó (hash-elés előtti).
     * @return {@code true}, ha az adatok érvényesek; egyébként {@code false}.
     */
    boolean validateCredentials(String username, String password);

    /**
     * Hozzáad egy játékot a felhasználó letöltött (birtokolt) játékainak listájához.
     *
     * @param username A felhasználó bejelentkezési neve.
     * @param gameId A könyvtárhoz adandó játék azonosítója.
     */
    void addGameToUserLibrary(String username, Long gameId);

    /**
     * Visszaadja a felhasználó által birtokolt (letöltött) játékok azonosítóit (ID-it).
     *
     * @param username A felhasználó bejelentkezési neve.
     * @return A birtokolt játékok azonosítóinak listája.
     */
    List<Long> getUserOwnedGameIds(String username);
}