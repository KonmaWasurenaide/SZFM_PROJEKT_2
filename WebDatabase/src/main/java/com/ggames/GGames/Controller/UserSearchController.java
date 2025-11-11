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

@RestController
@RequestMapping("/api/users")
public class UserSearchController {

    private final FriendshipService friendshipService;
    private final UserService userService;

    public UserSearchController(FriendshipService friendshipService, UserService userService) {
        this.friendshipService = friendshipService;
        this.userService = userService;
    }

    private UserEntity getCurrentUser(UserDetails userDetails) {
        return userService.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Hitelesített felhasználó nem található."));
    }
    @GetMapping("/suggestable")
    public ResponseEntity<List<UserSuggestDto>> getSuggestableUsers(@AuthenticationPrincipal UserDetails userDetails) {
        UserEntity currentUser = getCurrentUser(userDetails);

        List<UserSuggestDto> suggestableUsers = friendshipService.getSuggestableUsers(currentUser);

        return ResponseEntity.ok(suggestableUsers);
    }

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