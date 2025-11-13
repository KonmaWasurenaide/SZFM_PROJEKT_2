package com.ggames.GGames.Controller;

import com.ggames.GGames.Data.Entity.UserEntity;
import com.ggames.GGames.Service.ChatService;
import com.ggames.GGames.Service.FriendshipService;
import com.ggames.GGames.Service.UserService;
import com.ggames.GGames.Service.Dto.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST kontroller, amely kezeli a csevegő üzenetek előzményeinek lekérdezését.
 *
 * <p>Lehetővé teszi a felhasználó számára, hogy lekérje a beszélgetéseit egy adott partnerrel,
 * de csak abban az esetben, ha aktív barátsági kapcsolat van köztük.</p>
 */
@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatHistoryController {

    /**
     * Szolgáltatás a csevegő üzenetek üzleti logikájának kezelésére.
     */
    private final ChatService chatService;

    /**
     * Szolgáltatás a felhasználói adatokkal kapcsolatos műveletek kezelésére.
     */
    private final UserService userService;

    /**
     * Szolgáltatás a baráti kapcsolatok ellenőrzésére és kezelésére.
     */
    private final FriendshipService friendshipService;

    /**
     * Lekéri a jelenleg hitelesített felhasználó entitását a Spring Security {@code UserDetails} adatok alapján.
     *
     * @param userDetails A Spring Security által injektált hitelesített felhasználói adatok.
     * @return A bejelentkezett felhasználó {@code UserEntity} objektuma.
     * @throws RuntimeException Ha a hitelesített felhasználó nem található az adatbázisban.
     */
    private UserEntity getCurrentUser(UserDetails userDetails) {
        return userService.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Hitelesített felhasználó nem található."));
    }

    /**
     * Lekéri a két felhasználó közötti üzenetelőzményeket (conversation history) időrendben.
     *
     * <p>A metódus először ellenőrzi a {@code friendshipService} segítségével, hogy a hitelesített felhasználó és a partner barátok-e.
     * Csak pozitív ellenőrzés esetén adja vissza a beszélgetés előzményeit.</p>
     *
     * @param partnerId A csevegőpartner egyedi azonosítója.
     * @param userDetails A bejelentkezett felhasználó hitelesítési adatai.
     * @return Egy {@code ResponseEntity} objektum, amely:
     * <ul>
     * <li>A {@code ChatMessageDto} objektumok listáját tartalmazza HTTP 200 (OK) státusszal, ha a felek barátok.</li>
     * <li>Üres választ tartalmaz HTTP 403 (Forbidden) státusszal, ha a felek nem barátok.</li>
     * </ul>
     */
    @GetMapping("/history/{partnerId}")
    public ResponseEntity<List<ChatMessageDto>> getHistory(
            @PathVariable Long partnerId,
            @AuthenticationPrincipal UserDetails userDetails) {

        UserEntity currentUser = getCurrentUser(userDetails);
        Long currentUserId = currentUser.getId();

        boolean areFriends = friendshipService.areFriends(currentUserId, partnerId);

        if (!areFriends) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        List<ChatMessageDto> history = chatService.getConversationHistory(currentUserId, partnerId);

        return ResponseEntity.ok(history);
    }
}