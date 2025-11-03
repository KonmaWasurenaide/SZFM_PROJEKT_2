package com.ggames.GGames.Data.Repository;

import com.ggames.GGames.Data.Entity.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * A(z) {@code UserRepository} interfész felelős a felhasználói entitások ({@link UserEntity})
 * perzisztens tárolójához (adatbázishoz) való hozzáférésért.
 * <p>
 * Ez az interfész kiterjeszti a Spring Data {@code Repository}-t, és egyedi
 * natív SQL lekérdezéseket tartalmaz a felhasználói adatok kereséséhez,
 * beszúrásához, frissítéséhez és törléséhez.
 * </p>
 */
public interface UserRepository extends Repository<UserEntity, Long> {

    /**
     * Megkeres egy felhasználói entitást annak egyedi azonosítója alapján.
     *
     * @param id A keresendő felhasználó azonosítója.
     * @return Egy {@link Optional}, amely tartalmazza a {@link UserEntity}-t, ha találat van, különben üres.
     */
    @Query(
            value = "SELECT * FROM users WHERE id = :id",
            nativeQuery = true
    )
    Optional<UserEntity> findById(@Param("id") Long id);

    /**
     * Megkeres egy felhasználói entitást annak egyedi felhasználóneve alapján.
     *
     * @param username A keresendő felhasználó neve.
     * @return Egy {@link Optional}, amely tartalmazza a {@link UserEntity}-t, ha találat van, különben üres.
     */
    @Query(
            value = "SELECT * FROM users WHERE username = :username",
            nativeQuery = true
    )
    Optional<UserEntity> findByUsername(@Param("username") String username);

    /**
     * Ellenőrzi, hogy létezik-e felhasználó egy adott felhasználónévvel.
     *
     * @param username Az ellenőrizendő felhasználónév.
     * @return {@code true}, ha létezik felhasználó a megadott felhasználónévvel; egyébként {@code false}.
     */
    @Query(
            value = "SELECT EXISTS(SELECT 1 FROM users WHERE username = :username)",
            nativeQuery = true
    )
    boolean existsByUsername(@Param("username") String username);

    /**
     * Megkeres egy felhasználói entitást annak egyedi e-mail címe alapján.
     *
     * @param email A keresendő felhasználó e-mail címe.
     * @return Egy {@link Optional}, amely tartalmazza a {@link UserEntity}-t, ha találat van, különben üres.
     */
    @Query(
            value = "SELECT * FROM users WHERE email = :email",
            nativeQuery = true
    )
    Optional<UserEntity> findByEmail(@Param("email") String email);

    /**
     * Lekérdezi az adatbázisban tárolt összes felhasználót.
     *
     * @return Az összes {@link UserEntity} entitás {@link List}-je.
     */
    @Query(
            value = "SELECT * FROM users",
            nativeQuery = true
    )
    List<UserEntity> findAll();


    /**
     * Új felhasználót szúr be az adatbázisba natív SQL lekérdezés segítségével.
     * <p>
     * Ezt a metódust általában a regisztráció során használják.
     * A művelethez {@code @Modifying} és {@code @Transactional} annotációk szükségesek.
     * </p>
     *
     * @param username A felhasználó felhasználóneve.
     * @param password A felhasználó titkosított jelszava.
     * @param email A felhasználó e-mail címe.
     * @param role A felhasználó jogosultsági szerepköre (pl. "USER" vagy "ADMIN").
     */
    @Modifying
    @Transactional
    @Query(
            value = "INSERT INTO users (username, password, email, role) VALUES (:username, :password, :email, :role)",
            nativeQuery = true
    )
    void insertUser(
            @Param("username") String username,
            @Param("password") String password,
            @Param("email") String email,
            @Param("role")String role
    );

    /**
     * Elment (beszúr vagy frissít) egy {@link UserEntity} entitást az adatbázisban.
     * <p>
     * Ez egy standard Spring Data metódus. Ha az entitásnak van ID-je, frissít, ha nincs, beszúr.
     * </p>
     *
     * @param user Az elmentendő vagy frissítendő {@link UserEntity}.
     * @return Az elmentett {@link UserEntity} (beleértve a generált ID-t is).
     */
    UserEntity save(UserEntity user);

    /**
     * Frissíti egy meglévő felhasználó felhasználónevét, e-mail címét és szerepkörét (role) annak ID-je alapján.
     * <p>
     * A művelethez {@code @Modifying} és {@code @Transactional} annotációk szükségesek.
     * </p>
     *
     * @param username Az új felhasználónév.
     * @param email Az új e-mail cím.
     * @param role Az új szerepkör.
     * @param id A frissítendő felhasználó azonosítója.
     * @return Az érintett sorok száma (0 vagy 1).
     */
    @Modifying
    @Transactional
    @Query(
            value = "UPDATE users SET username =:username, email = :email, role = :role WHERE id = :id",
            nativeQuery = true
    )
    int updateEmailAndRole(
            @Param("username") String username,
            @Param("email") String email,
            @Param("role") String role,
            @Param("id") Long id
    );

    /**
     * Töröl egy felhasználót az adatbázisból annak egyedi azonosítója alapján.
     * <p>
     * A művelethez {@code @Modifying} és {@code @Transactional} annotációk szükségesek.
     * </p>
     *
     * @param id A törlendő felhasználó azonosítója.
     */
    @Modifying
    @Transactional
    @Query(
            value = "DELETE FROM users WHERE id = :id",
            nativeQuery = true
    )
    void deleteById(@Param("id") Long id);
}