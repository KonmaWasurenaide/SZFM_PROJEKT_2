package com.ggames.GGames.Controller;

import com.ggames.GGames.Data.Entity.UserEntity;
import com.ggames.GGames.Service.FriendshipService;
import com.ggames.GGames.Service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Kezeli a felhasználói felülethez kapcsolódó kéréseket, amelyek a baráti kapcsolatok,
 * értesítések és csevegő oldalak megjelenítéséért felelnek.
 *
 * <p>Az összes metódus bejelentkezett felhasználót igényel. A chat szoba betöltése előtt ellenőrzi a barátsági kapcsolatot.</p>
 */
@Controller
public class FriendshipPageController {

    private final FriendshipService friendshipService;
    private final UserService userService;

    /**
     * Konstruktor a {@code FriendshipPageController} inicializálásához.
     *
     * @param friendshipService A barátsági kapcsolatokkal kapcsolatos üzleti logikát kezelő szolgáltatás.
     * @param userService A felhasználói entitásokkal kapcsolatos műveleteket kezelő szolgáltatás.
     */
    public FriendshipPageController(FriendshipService friendshipService, UserService userService) {
        this.friendshipService = friendshipService;
        this.userService = userService;
    }

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
     * Megjeleníti a barátok főoldalát, amely tartalmazza a függőben lévő barátkérések számát és a javasolt felhasználók listáját.
     *
     * @param userDetails A bejelentkezett felhasználó hitelesítési adatai.
     * @param model A Thymeleaf modell az adatok nézet felé történő továbbítására.
     * @return A "friends" nézet neve.
     */
    @GetMapping("/friends")
    public String showFriendsPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        UserEntity currentUser = getCurrentUser(userDetails);

        try {
            int pendingCount = friendshipService.countPendingRequests(currentUser);
            model.addAttribute("pendingRequestCount", pendingCount);

        } catch (Exception e) {
            model.addAttribute("pendingRequestCount", 0);
            System.err.println("Hiba a barátkérések számolása közben (Friends): " + e.getMessage());
        }

        model.addAttribute("suggestableUsers", friendshipService.getSuggestableUsers(currentUser));

        return "friends";
    }

    /**
     * Megjeleníti az értesítések oldalát, amely tartalmazza a felhasználóhoz érkezett függőben lévő barátkéréseket.
     *
     * @param userDetails A bejelentkezett felhasználó hitelesítési adatai.
     * @param model A Thymeleaf modell az adatok nézet felé történő továbbítására.
     * @return A "notifications" nézet neve.
     */
    @GetMapping("/notifications")
    public String showNotificationsPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        UserEntity currentUser = getCurrentUser(userDetails);
        model.addAttribute("pendingRequests", friendshipService.getPendingRequests(currentUser));

        return "notifications";
    }

    /**
     * Megjeleníti a barátlistát tartalmazó oldalt, beleértve a függőben lévő barátkérések számát is.
     *
     * @param userDetails A bejelentkezett felhasználó hitelesítési adatai.
     * @param model A Thymeleaf modell az adatok nézet felé történő továbbítására.
     * @return A "friends_list" nézet neve.
     */
    @GetMapping("/friends/list")
    public String showFriendsListPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        UserEntity currentUser = getCurrentUser(userDetails);

        try {
            int pendingCount = friendshipService.countPendingRequests(currentUser);
            model.addAttribute("pendingRequestCount", pendingCount);

        } catch (Exception e) {
            model.addAttribute("pendingRequestCount", 0);
            System.err.println("Hiba a barátkérések számolása közben (Friends List): " + e.getMessage());
        }

        model.addAttribute("friends", friendshipService.getFriends(currentUser));

        return "friends_list";
    }

    /**
     * Megjeleníti a csevegő szobát egy adott baráttal, feltéve, hogy a két felhasználó barát.
     *
     * <p>A metódus biztonsági ellenőrzést végez a {@code friendshipService} segítségével, hogy megakadályozza a jogosulatlan hozzáférést a csevegéshez.</p>
     *
     * @param userDetails A bejelentkezett felhasználó adatai.
     * @param friendId A beszélgetőpartner egyedi azonosítója.
     * @param model A Thymeleaf modell.
     * @return A "chat_room" nézet neve, vagy átirányítás a barátlistára hibaüzenettel, ha a felek nem barátok.
     * @throws RuntimeException Ha a megadott {@code friendId} alapján a chat partner nem található.
     */
    @GetMapping("/chat/{friendId}")
    public String showChatRoom(@AuthenticationPrincipal UserDetails userDetails,
                               @PathVariable Long friendId,
                               Model model) {

        UserEntity currentUser = getCurrentUser(userDetails);

        boolean areFriends = friendshipService.areFriends(currentUser.getId(), friendId);

        if (!areFriends) {
            return "redirect:/friends/list?error=not_friend_chat";
        }

        UserEntity chatPartner = userService.findById(friendId)
                .orElseThrow(() -> new RuntimeException("Chat partner nem található."));

        model.addAttribute("friendId", friendId);
        model.addAttribute("friendUsername", chatPartner.getUsername());

        return "chat_room";
    }
}