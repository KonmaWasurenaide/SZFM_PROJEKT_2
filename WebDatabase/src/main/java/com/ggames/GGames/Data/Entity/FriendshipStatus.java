package com.ggames.GGames.Data.Entity;

/**
 * Definiálja a felhasználók közötti baráti kapcsolat (barátkérés) lehetséges állapotait.
 */
public enum FriendshipStatus {
    /**
     * A barátkérés el lett küldve, de még nem került elfogadásra vagy elutasításra.
     */
    PENDING,
    /**
     * A barátkérés elfogadásra került, a kapcsolat aktív barátság.
     */
    ACCEPTED,
    /**
     * A barátkérés elutasításra került.
     */
    REJECTED
}