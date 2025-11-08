package com.ggames.GGames.Service;

import com.ggames.GGames.Service.Dto.GameDto;
import com.ggames.GGames.Data.Entity.GameEntity;
import java.util.List;
import java.util.Optional;

/**
 * Service interfész a játékokkal kapcsolatos üzleti logikához és adatkezelési műveletekhez.
 *
 * <p>Felelős a játékadatok lekérdezéséért, listázásáért és a CRUD (Create, Read, Update, Delete) műveletek végrehajtásáért.</p>
 */
public interface GameService {

    /**
     * Visszaadja az összes játékot {@code GameDto} formátumban a megjelenítéshez.
     *
     * @return Az elérhető játékok listája.
     */
    List<GameDto> getAllGamesForDisplay();

    /**
     * Megkeres egy játék entitást az azonosítója (ID) alapján.
     *
     * @param id A keresett játék azonosítója.
     * @return Az {@code Optional} tartalmazza a {@code GameEntity}-t, ha megtalálható.
     */
    Optional<GameEntity> getGameById(Long id);

    /**
     * Megkeres egy játékot a neve alapján, és visszaadja azt {@code GameDto} formátumban.
     *
     * @param name A keresett játék neve.
     * @return Az {@code Optional} tartalmazza a {@code GameDto}-t, ha megtalálható.
     */
    Optional<GameDto> getGameByName(String name);

    /**
     * Visszaadja a játékokat a megadott azonosítók listája alapján.
     *
     * <p>Ez a metódus kritikus a felhasználói könyvtár tartalmának betöltéséhez.</p>
     *
     * @param gameIds A keresett játékok azonosítóinak listája.
     * @return A kapcsolódó {@code GameDto} objektumok listája.
     */
    List<GameDto> getGamesByIds(List<Long> gameIds);

    /**
     * Elment egy új játékot vagy frissíti a meglévőt.
     *
     * @param game A mentendő vagy frissítendő {@code GameDto} objektum.
     */
    void saveGame(GameDto game);

    /**
     * Töröl egy játékot az azonosítója (ID) alapján.
     *
     * @param id A törlendő játék azonosítója.
     */
    void deleteGame(Long id);
}