package com.ggames.GGames.Data.Repository;

import com.ggames.GGames.Data.Entity.GameEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * A(z) {@code GameRepository} interfész felelős a játék entitások ({@link GameEntity})
 * perzisztens tárolójához (adatbázishoz) való hozzáférésért.
 * <p>
 * Ez az interfész kiterjeszti a Spring Data {@code Repository}-t, és egyedi
 * natív SQL lekérdezéseket tartalmaz a játékadatok kereséséhez, beszúrásához,
 * frissítéséhez és törléséhez.
 * </p>
 */
public interface GameRepository extends Repository<GameEntity, Long> {

    /**
     * Megkeres egy játék entitást annak egyedi azonosítója alapján.
     *
     * @param id A keresendő játék azonosítója.
     * @return Egy {@link Optional}, amely tartalmazza a {@link GameEntity}-t, ha találat van, különben üres.
     */
    @Query(
            value = "SELECT * FROM games WHERE id = :id",
            nativeQuery = true
    )
    Optional<GameEntity> findGameById(@Param("id") Long id);

    /**
     * Megkeres egy játék entitást annak pontos neve alapján.
     *
     * @param name A keresendő játék neve.
     * @return Egy {@link Optional}, amely tartalmazza a {@link GameEntity}-t, ha találat van, különben üres.
     */
    @Query(
            value = "SELECT * FROM games WHERE name = :name",
            nativeQuery = true
    )
    Optional<GameEntity> findGameByName(@Param("name") String name);

    /**
     * Lekérdezi az összes játékot egy adott fejlesztő (developer) alapján.
     *
     * @param developer A fejlesztő neve, akinek a játékait keresi.
     * @return A talált {@link GameEntity} entitások {@link List}-je.
     */
    @Query(
            value = "SELECT * FROM games WHERE developer = :developer",
            nativeQuery = true
    )
    List<GameEntity> findGamesByDeveloper(@Param("developer") String developer);

    /**
     * Lekérdezi az összes játékot egy adott kiadó (publisher) alapján.
     *
     * @param publisher A kiadó neve, akinek a játékait keresi.
     * @return A talált {@link GameEntity} entitások {@link List}-je.
     */
    @Query(
            value = "SELECT * FROM games WHERE publisher = :publisher",
            nativeQuery = true
    )
    List<GameEntity> findGamesByPublisher(@Param("publisher") String publisher);

    /**
     * Lekérdezi az összes játékot, amelyek tartalmaznak egy adott címkét (tag) a {@code tags} mezőben.
     *
     * @param tag A keresendő címke (tag).
     * @return A talált {@link GameEntity} entitások {@link List}-je.
     */
    @Query(
            value = "SELECT * FROM games WHERE tags LIKE %:tag%",
            nativeQuery = true
    )
    List<GameEntity> findGamesByTag(@Param("tag") String tag);

    /**
     * Lekérdezi az adatbázisban tárolt összes játékot.
     *
     * @return Az összes {@link GameEntity} entitás {@link List}-je.
     */
    @Query(
            value = "SELECT * FROM games",
            nativeQuery = true
    )
    List<GameEntity> findAllGames();

    /**
     * Új játékot szúr be az adatbázisba natív SQL lekérdezés segítségével.
     * <p>
     * A művelethez {@code @Modifying} és {@code @Transactional} annotációk szükségesek.
     * </p>
     *
     * @param name A játék neve.
     * @param developer A játék fejlesztője.
     * @param publisher A játék kiadója.
     * @param relaseDate A játék megjelenési dátuma.
     * @param tags A játék címkéi (tag-jei).
     * @param description A játék leírása.
     */
    @Modifying
    @Transactional
    @Query(
            value = "INSERT INTO games (name, developer, publisher, relase_date, tags, description) " +
                    "VALUES (:name, :developer, :publisher, :relaseDate, :tags, :description)",
            nativeQuery = true
    )
    void insertNewGame(
            @Param("name") String name,
            @Param("developer") String developer,
            @Param("publisher") String publisher,
            @Param("relaseDate") Date relaseDate,
            @Param("tags") String tags,
            @Param("description") String description
    );

    /**
     * Elment (beszúr vagy frissít) egy {@link GameEntity} entitást az adatbázisban.
     * <p>
     * Ez egy standard Spring Data metódus. Ha az entitásnak van ID-je, frissít, ha nincs, beszúr.
     * </p>
     *
     * @param game Az elmentendő vagy frissítendő {@link GameEntity}.
     * @return Az elmentett {@link GameEntity} (beleértve a generált ID-t is).
     */
    GameEntity save(GameEntity game);

    /**
     * Frissíti egy meglévő játék összes adatát annak ID-je alapján.
     * <p>
     * A művelethez {@code @Modifying} és {@code @Transactional} annotációk szükségesek.
     * </p>
     *
     * @param id A frissítendő játék azonosítója.
     * @param name Az új név.
     * @param developer Az új fejlesztő.
     * @param publisher Az új kiadó.
     * @param relaseDate Az új megjelenési dátum.
     * @param tags Az új címkék.
     * @param description Az új leírás.
     * @return Az érintett sorok száma (0 vagy 1).
     */
    @Modifying
    @Transactional
    @Query(
            value = "UPDATE games SET name = :name, developer = :developer, publisher = :publisher, " +
                    "relase_date = :relaseDate, tags = :tags, description = :description WHERE id = :id",
            nativeQuery = true
    )
    int updateGameDetails(
            @Param("id") Long id,
            @Param("name") String name,
            @Param("developer") String developer,
            @Param("publisher") String publisher,
            @Param("relaseDate") Date relaseDate,
            @Param("tags") String tags,
            @Param("description") String description
    );

    /**
     * Töröl egy játékot az adatbázisból annak egyedi azonosítója alapján.
     * <p>
     * A művelethez {@code @Modifying} és {@code @Transactional} annotációk szükségesek.
     * </p>
     *
     * @param id A törlendő játék azonosítója.
     */
    @Modifying
    @Transactional
    @Query(
            value = "DELETE FROM games WHERE id = :id",
            nativeQuery = true
    )
    void deleteGameById(@Param("id") Long id);
}