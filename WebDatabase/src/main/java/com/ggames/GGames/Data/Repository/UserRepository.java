package com.ggames.GGames.Data.Repository;

import com.ggames.GGames.Data.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repository interfész a {@link UserEntity} entitások adatbázis-műveleteinek kezelésére.
 *
 * <p>Kiterjeszti a {@code JpaRepository}-t, alapvető CRUD műveleteket biztosítva,
 * valamint egyedi keresési és szűrési metódusokat kínál a felhasználók kezeléséhez.</p>
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Megkeresi a felhasználót a megadott felhasználónév alapján.
     *
     * @param username A keresett felhasználónév.
     * @return Az opcionális {@code UserEntity} objektum.
     */
    Optional<UserEntity> findByUsername(String username);

    /**
     * Ellenőrzi, hogy létezik-e felhasználó a megadott felhasználónévvel.
     *
     * @param username A vizsgált felhasználónév.
     * @return {@code true}, ha létezik felhasználó a felhasználónévvel; egyébként {@code false}.
     */
    boolean existsByUsername(String username);

    /**
     * Megkeresi a felhasználót a megadott email cím alapján.
     *
     * @param email A keresett email cím.
     * @return Az opcionális {@code UserEntity} objektum.
     */
    Optional<UserEntity> findByEmail(String email);

    /**
     * Lekéri azoknak a felhasználóknak a listáját, akik nincsenek benne a megadott azonosítójú listában.
     *
     * <p>Ezt a metódust gyakran használják olyan felhasználók javaslására, akik még nincsenek baráti kapcsolatban (vagy nem szerepelnek egy kiválasztott listában).</p>
     *
     * @param connectedIds Azon felhasználói azonosítók listája, amelyeket ki kell zárni az eredményből (pl. barátok és függőben lévő kérések).
     * @return A kizárt azonosítókkal nem rendelkező {@code UserEntity} objektumok listája.
     */
    @Query("SELECT u FROM UserEntity u WHERE u.id NOT IN :connectedIds")
    List<UserEntity> findSuggestableUsers(@Param("connectedIds") List<Long> connectedIds);
}