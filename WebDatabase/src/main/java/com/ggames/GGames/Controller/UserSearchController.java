package com.ggames.GGames.Controller;

import com.ggames.GGames.Service.Dto.UserSuggestDto;
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
 * REST kontroller, amely kezeli a felhasználók keresésével, javaslataival és a barátkérések küldésével kapcsolatos API kéréseket.
 *
 * <p>Ez az osztály a barátsági kapcsolatok kezdeményezéséért és az új barátok javaslásáért felel.</p>
 *
 * @author [Ide írhatod az osztály szerzőjét]
 * @since 1.0
 */
@RestController
@RequestMapping("/api/users")
public class UserSearchController {

    /**
     * Szolgáltatás a baráti kapcsolatok logikájának kezelésére.
     */
    private final FriendshipService friendshipService;

    /**
     * Szolgáltatás a felhasználói adatok lekérdezésére.
     */
    private final UserService userService;

    /**
     * Konstruktor a {@code UserSearchController} inicializálásához.
     *
     * @param friendshipService A baráti kapcsolatok szolgáltatása.
     * @param userService A felhasználói szolgáltatás.
     */
    public UserSearchController(FriendshipService friendshipService, UserService userService) {
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
     * Visszaadja azon felhasználók listáját, akiket a rendszer javasol barátnak a bejelentkezett felhasználó számára.
     *
     * @param userDetails A bejelentkezett felhasználó adatai.
     * @return Egy {@code ResponseEntity} objektum, amely tartalmazza a javasolt felhasználók {@code UserSuggestDto} listáját HTTP 200 (OK) státusszal.
     */
    @GetMapping("/suggestable")
    public ResponseEntity<List<UserSuggestDto>> getSuggestableUsers(@AuthenticationPrincipal UserDetails userDetails) {
        UserEntity currentUser = getCurrentUser(userDetails);

        List<UserSuggestDto> suggestableUsers = friendshipService.getSuggestableUsers(currentUser);

        return ResponseEntity.ok(suggestableUsers);
    }

    /**
     * Barátkérést küld a megadott ID-vel rendelkező felhasználónak.
     *
     * @param userDetails A bejelentkezett felhasználó adatai (aki küldi a kérést).
     * @param receiverId A címzett felhasználó egyedi azonosítója.
     * @return Egy {@code ResponseEntity} objektum, amely sikeres küldés esetén üzenetet tartalmaz HTTP 200 (OK) státusszal, hiba esetén pedig hibaüzenetet HTTP 400 (BAD REQUEST) státusszal.
     */
    @PostMapping("/send-request/{receiverId}")
    public ResponseEntity<Map<String, String>> sendFriendRequest(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long receiverId) {

        UserEntity sender = getCurrentUser(userDetails);

        try {
            friendshipService.sendFriendRequest(sender, receiverId);
            return ResponseEntity.ok(Map.of("message", "Barátkérelem sikeresen elküldve."));
        } catch (FriendshipException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}