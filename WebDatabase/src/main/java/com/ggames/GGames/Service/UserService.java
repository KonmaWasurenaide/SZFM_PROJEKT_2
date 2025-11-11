package com.ggames.GGames.Service;

import com.ggames.GGames.Data.Entity.UserEntity;
import com.ggames.GGames.Service.Dto.GameDto;
import com.ggames.GGames.Service.Dto.UserDto;
import java.util.List;
import java.util.Optional;

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

    /**
     * Megkeresi a felhasználó entitást a felhasználónév alapján.
     * Ez a metódus szolgálja ki a Controllereket a hitelesített felhasználó lekéréséhez.
     *
     * @param username A hitelesítésből származó felhasználónév.
     * @return Az opcionális UserEntity objektum.
     */
    Optional<UserEntity> findByUsername(String username);

    /**
     * ÚJ METÓDUS: Megkeresi a felhasználó entitást az azonosító (ID) alapján.
     * Szükséges a chat partner nevének lekéréséhez a Controllerben.
     *
     * @param id A keresett felhasználó ID-ja.
     * @return Az opcionális UserEntity objektum.
     */
    Optional<UserEntity> findById(Long id);
}