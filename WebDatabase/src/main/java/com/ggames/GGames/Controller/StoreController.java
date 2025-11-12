package com.ggames.GGames.Controller;

import com.ggames.GGames.Data.Entity.UserEntity;
import com.ggames.GGames.Data.Repository.GameRepository;
import com.ggames.GGames.Service.FriendshipService;
import com.ggames.GGames.Service.GameService;
import com.ggames.GGames.Service.Dto.GameDto;
import com.ggames.GGames.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Kezeli a publikus áruház felületeit, a játékok listázását és a játék indítását.
 * Ez a kontroller felelős a böngészéshez szükséges nézetek kiszolgálásáért.
 */
@Controller
@RequiredArgsConstructor
public class StoreController {

    private final GameService gameService;
    private final UserService userService;
    private final FriendshipService friendshipService;

    private static final Pattern UNITY_ID_PATTERN = Pattern.compile("games/([0-9a-fA-F\\-]+)/");

    /**
     * Segédmetódus a sima Unity URL átalakítására a beágyazási formátumra.
     * @param unityGameUrl A Unity Play játék URL-je.
     * @return A beágyazáshoz szükséges iframe forrás URL.
     */
    private String convertUnityUrlToEmbed(String unityGameUrl) {
        if (unityGameUrl == null || unityGameUrl.isEmpty()) {
            return "";
        }
        Matcher matcher = UNITY_ID_PATTERN.matcher(unityGameUrl);
        if (matcher.find()) {
            String gameId = matcher.group(1);
            return String.format("https://play.unity.com/api/v1/games/game/%s/build/latest/frame", gameId);
        }
        return unityGameUrl;
    }


    /**
     * Megjeleníti a főoldalt és listázza az elérhető játékokat.
     * @param model A Thymeleaf modell.
     * @return A "home" nézet neve.
     */
    @GetMapping("/home")
    public String homePage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("games", gameService.getAllGamesForDisplay());
        if (userDetails != null) {
            try {
                UserEntity currentUser = userService.findByUsername(userDetails.getUsername())
                        .orElseThrow(() -> new RuntimeException("Hitelesített felhasználó nem található."));

                int pendingCount = friendshipService.countPendingRequests(currentUser);

                model.addAttribute("pendingRequestCount", pendingCount);

            } catch (Exception e) {
                model.addAttribute("pendingRequestCount", 0);
                System.err.println("Hiba a barátkérések számolása közben: " + e.getMessage());
            }
        } else {
            model.addAttribute("pendingRequestCount", 0);
        }

        return "home";
    }

    /**
     * Megjeleníti egy adott játék részleteit a játék nevének felhasználásával.
     * @param gameName A játék URL-kódolt neve.
     * @param model A Thymeleaf modell.
     * @return A "game-details" nézet, vagy átirányítás hibával.
     */
    @GetMapping("/game/{gameName}")
    public String gameDetailPage(@PathVariable String gameName, Model model) {
        String decodedGameName = URLDecoder.decode(gameName, StandardCharsets.UTF_8);
        Optional<GameDto> gameOptional = gameService.getGameByName(decodedGameName);

        if (gameOptional.isEmpty()) {
            return "redirect:/home?error=gameNotFound";
        }

        model.addAttribute("game", gameOptional.get());
        return "game-details";
    }

    /**
     * Megjeleníti a játék indító oldalt (az iframe-et tartalmazó nézetet).
     * @param gameName A játék URL-kódolt neve.
     * @param model A Thymeleaf modell.
     * @return A "game_play" nézet, vagy átirányítás hibával.
     */
    @GetMapping("/play/{gameName}")
    public String gamePlayPage(@PathVariable String gameName, Model model) {
        String decodedGameName = URLDecoder.decode(gameName, StandardCharsets.UTF_8);

        Optional<GameDto> gameOptional = gameService.getGameByName(decodedGameName);

        if (gameOptional.isEmpty()) {
            return "redirect:/home?error=gameNotFound";
        }

        GameDto game = gameOptional.get();
        String embedUrl = convertUnityUrlToEmbed(game.getPlayLink());

        model.addAttribute("game", game);
        model.addAttribute("embedUrl", embedUrl);

        return "game_play";
    }
}