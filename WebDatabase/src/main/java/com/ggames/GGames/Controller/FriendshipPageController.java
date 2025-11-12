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

@Controller
public class FriendshipPageController {

    private final FriendshipService friendshipService;
    private final UserService userService;

    public FriendshipPageController(FriendshipService friendshipService, UserService userService) {
        this.friendshipService = friendshipService;
        this.userService = userService;
    }

    private UserEntity getCurrentUser(UserDetails userDetails) {
        return userService.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Hitelesített felhasználó nem található."));
    }

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

    @GetMapping("/notifications")
    public String showNotificationsPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        UserEntity currentUser = getCurrentUser(userDetails);
        model.addAttribute("pendingRequests", friendshipService.getPendingRequests(currentUser));

        return "notifications";
    }

    @GetMapping("/friends/list")
    public String showFriendsListPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        UserEntity currentUser = getCurrentUser(userDetails);

        // --- Értesítések számlálása ---
        try {
            // Barátkérések számának lekérése
            int pendingCount = friendshipService.countPendingRequests(currentUser);
            model.addAttribute("pendingRequestCount", pendingCount);

        } catch (Exception e) {
            // Hiba esetén nulla kérés, hogy ne törjön össze az oldal
            model.addAttribute("pendingRequestCount", 0);
            System.err.println("Hiba a barátkérések számolása közben (Friends List): " + e.getMessage());
        }
        // -----------------------------

        model.addAttribute("friends", friendshipService.getFriends(currentUser));

        return "friends_list"; // Sablon neve
    }
    @GetMapping("/chat/{friendId}")
    public String showChatRoom(@AuthenticationPrincipal UserDetails userDetails,
                               @PathVariable Long friendId,
                               Model model) {
        UserEntity chatPartner = userService.findById(friendId)
                .orElseThrow(() -> new RuntimeException("Chat partner nem található."));
        model.addAttribute("friendId", friendId);
        model.addAttribute("friendUsername", chatPartner.getUsername());

        return "chat_room";
    }
}