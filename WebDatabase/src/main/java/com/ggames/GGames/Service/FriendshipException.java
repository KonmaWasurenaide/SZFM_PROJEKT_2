package com.ggames.GGames.Service;

/**
 * Egyedi futásidejű kivétel (RuntimeException) a barátsági kapcsolatokkal kapcsolatos üzleti hibák jelzésére.
 *
 * <p>Ezt a kivételt dobja a {@code FriendshipService} réteg, ha érvénytelen barátkérés,
 * vagy jogosultsági hiba történik a barátságkezelés során (pl. már barátok, nem létező kérés).</p>
 */
public class FriendshipException extends RuntimeException {

    /**
     * Konstruktor a kivétel létrehozására egy adott hibaüzenettel.
     *
     * @param message A kivétel részletes leírása.
     */
    public FriendshipException(String message) {
        super(message);
    }
}