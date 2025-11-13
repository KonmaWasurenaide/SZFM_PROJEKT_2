package com.ggames.GGames.Controller;

import com.ggames.GGames.Service.Dto.FriendRequestDto;
import com.ggames.GGames.Data.Entity.UserEntity;
import com.ggames.GGames.Service.FriendshipException;
import com.ggames.GGames.Service.FriendshipService;
import com.ggames.GGames.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST kontroller, amely a baráti kapcsolatokhoz kapcsolódó értesítéseket és műveleteket kezeli.
 *
 * <p>Lehetővé teszi a felhasználó számára a függőben lévő barátkérések lekérdezését,
 * valamint a kérések elfogadását és elutasítását.</p>
 *
 * @author [Ide írhatod az osztály szerzőjét]
 * @since 1.0
 */
@RestController
@RequestMapping("/api/friendships")
public class FriendshipNotificationController {

    /**
     * Szolgáltatás a baráti kapcsolatok logikájának kezelésére.
     */
    private final FriendshipService friendshipService;

    /**
     * Szolgáltatás a felhasználói adatok lekérdezésére.
     */
    private final UserService userService;

    /**
     * Konstruktor a {@code FriendshipNotificationController} inicializálásához.
     *
     * @param friendshipService A baráti kapcsolatok szolgáltatása.
     * @param userService A felhasználói szolgáltatás.
     */
    public FriendshipNotificationController(FriendshipService friendshipService, UserService userService) {
        this.friendshipService = friendshipService;
        this.userService = userService;
    }

    /**
     * Lekéri a jelenleg hitelesített felhasználó entitását.
     *
     * @param userDetails A Spring Security által injektált felhasználói adatok.
     * @return A bejelentkezett felhasználó {@code UserEntity} objektuma.
     * @throws RuntimeException Ha a hitelesített felhasználó nem található az adatbázisban.
     */
    private UserEntity getCurrentUser(UserDetails userDetails) {
        return userService.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Hitelesített felhasználó nem található."));
    }

    /**
     * Visszaadja a jelenleg bejelentkezett felhasználóhoz érkezett, függőben lévő barátkérések listáját.
     *
     * @param userDetails A bejelentkezett felhasználó adatai.
     * @return Egy {@code ResponseEntity} objektum, amely tartalmazza a {@code FriendRequestDto} objektumok listáját HTTP 200 (OK) státusszal.
     */
    @GetMapping("/notifications")
    public ResponseEntity<List<FriendRequestDto>> getPendingRequests(@AuthenticationPrincipal UserDetails userDetails) {
        UserEntity receiver = getCurrentUser(userDetails);

        List<FriendRequestDto> pendingRequests = friendshipService.getPendingRequests(receiver);

        return ResponseEntity.ok(pendingRequests);
    }

    /**
     * Elfogadja a megadott ID-vel rendelkező barátkérést.
     *
     * @param userDetails A bejelentkezett felhasználó adatai (aki elfogadja a kérést).
     * @param requestId Az elfogadandó barátkérés egyedi azonosítója.
     * @return Egy {@code ResponseEntity} objektum, amely sikeres elfogadás esetén üzenetet tartalmaz HTTP 200 (OK) státusszal, hiba esetén pedig hibaüzenetet HTTP 400 (BAD REQUEST) státusszal.
     */
    @PutMapping("/accept/{requestId}")
    public ResponseEntity<Map<String, String>> acceptFriendRequest(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long requestId) {

        UserEntity receiver = getCurrentUser(userDetails);

        try {
            friendshipService.acceptFriendRequest(receiver, requestId);
            return ResponseEntity.ok(Map.of("message", "Barátkérelem sikeresen elfogadva."));
        } catch (FriendshipException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Elutasítja a megadott ID-vel rendelkező barátkérést.
     *
     * @param userDetails A bejelentkezett felhasználó adatai (aki elutasítja a kérést).
     * @param requestId Az elutasítandó barátkérés egyedi azonosítója.
     * @return Egy {@code ResponseEntity} objektum, amely sikeres elutasítás esetén üzenetet tartalmaz HTTP 200 (OK) státusszal, hiba esetén pedig hibaüzenetet HTTP 400 (BAD REQUEST) státusszal.
     */
    @PutMapping("/reject/{requestId}")
    public ResponseEntity<Map<String, String>> rejectFriendRequest(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long requestId) {

        UserEntity receiver = getCurrentUser(userDetails);

        try {
            friendshipService.rejectFriendRequest(receiver, requestId);
            return ResponseEntity.ok(Map.of("message", "Barátkérelem sikeresen elutasítva."));
        } catch (FriendshipException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}