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

@RestController
@RequestMapping("/api/friendships")
public class FriendshipNotificationController {

    private final FriendshipService friendshipService;
    private final UserService userService;

    public FriendshipNotificationController(FriendshipService friendshipService, UserService userService) {
        this.friendshipService = friendshipService;
        this.userService = userService;
    }

    private UserEntity getCurrentUser(UserDetails userDetails) {
        return userService.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Hitelesített felhasználó nem található."));
    }
    @GetMapping("/notifications")
    public ResponseEntity<List<FriendRequestDto>> getPendingRequests(@AuthenticationPrincipal UserDetails userDetails) {
        UserEntity receiver = getCurrentUser(userDetails);

        List<FriendRequestDto> pendingRequests = friendshipService.getPendingRequests(receiver);

        return ResponseEntity.ok(pendingRequests);
    }

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