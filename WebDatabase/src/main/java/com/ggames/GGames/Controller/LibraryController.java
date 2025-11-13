package com.ggames.GGames.Controller;

import com.ggames.GGames.Data.Entity.UserEntity;
import com.ggames.GGames.Service.FriendshipService;
import com.ggames.GGames.Service.GameService;
import com.ggames.GGames.Service.UserService;
import com.ggames.GGames.Service.Dto.GameDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

/**
 * Kezeli a bejelentkezett felhasználó könyvtárát, a játékok hozzáadását és eltávolítását.
 *
 * <p>Ez a kontroller felelős a felhasználói fiókkal kapcsolatos tranzakciós műveletekért, mint például a letöltési kérelem indítása
 * és a könyvtár tartalmának megjelenítése.</p>
 *
 * @author [Ide írhatod az osztály szerzőjét]
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
public class LibraryController {

    /**
     * Szolgáltatás a játékokkal kapcsolatos műveletek kezelésére.
     */
    private final GameService gameService;

    /**
     * Szolgáltatás a felhasználói adatokkal kapcsolatos műveletek kezelésére.
     */
    private final UserService userService;

    /**
     * Segédosztály az Entity és DTO objektumok közötti konverzióhoz.
     */
    private final ModelMapper modelMapper;

    /**
     * Szolgáltatás a barátsági kapcsolatokkal kapcsolatos műveletek kezelésére (pl. függőben lévő kérések számlálása).
     */
    private final FriendshipService friendshipService;


    /**
     * Hozzáad egy játékot a felhasználó könyvtárához és átirányít a játék letöltési linkjére.
     *
     * <p>Ha a felhasználó nincs bejelentkezve, átirányít a bejelentkezési oldalra.</p>
     *
     * @param gameId A hozzáadandó játék azonosítója.
     * @param authentication A bejelentkezett felhasználó autentikációs objektuma.
     * @param redirectAttributes Az átirányítási attribútumok, üzenetek továbbítására.
     * @return Átirányítás a játék letöltési linkjére, a főoldalra hiba esetén, vagy a bejelentkezési oldalra.
     */
    @GetMapping("/download/{gameId}")
    public String addToLibraryAndRedirect(@PathVariable Long gameId, Authentication authentication, RedirectAttributes redirectAttributes) {

        if (authentication == null) {
            return "redirect:/login";
        }

        Optional<GameDto> gameOptional = gameService.getGameById(gameId)
                .flatMap(entity -> Optional.of(modelMapper.map(entity, GameDto.class)));

        if (gameOptional.isEmpty() || gameOptional.get().getDownloadLink() == null || gameOptional.get().getDownloadLink().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Hiba: A játék letöltési linkje hiányzik!");
            return "redirect:/home";
        }

        String downloadLink = gameOptional.get().getDownloadLink();
        String username = authentication.getName();

        try {
            userService.addGameToUserLibrary(username, gameId);
            redirectAttributes.addFlashAttribute("successMessage", "Játék hozzáadva a könyvtárhoz és a letöltés elindul!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Hiba történt a könyvtár frissítésekor: " + e.getMessage());
        }
        return "redirect:" + downloadLink;
    }

    /**
     * Megjeleníti a bejelentkezett felhasználó játékgyűjteményét (könyvtárát).
     *
     * <p>A könyvtár tartalmának lekérésén túl lekéri a függőben lévő barátkérések számát is.</p>
     *
     * @param authentication A bejelentkezett felhasználó autentikációs objektuma.
     * @param model A Thymeleaf modell.
     * @return A "library" nézet neve, vagy átirányítás a bejelentkezési oldalra.
     */
    @GetMapping("/library")
    public String showUserLibrary(Authentication authentication, Model model) {
        if (authentication == null) {
            return "redirect:/login";
        }

        String username = authentication.getName();
        try {
            List<Long> ownedGameIds = userService.getUserOwnedGameIds(username);
            List<GameDto> libraryGames = gameService.getGamesByIds(ownedGameIds);

            model.addAttribute("games", libraryGames);
        } catch (Exception e) {
            System.err.println("Database error loading library: " + e.getMessage());
            model.addAttribute("errorMessage", "Hiba történt a könyvtár betöltésekor.");
            model.addAttribute("games", List.of());
        }

        try {
            UserEntity currentUser = userService.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Hitelesített felhasználó nem található."));

            int pendingCount = friendshipService.countPendingRequests(currentUser);

            model.addAttribute("pendingRequestCount", pendingCount);

        } catch (Exception e) {
            model.addAttribute("pendingRequestCount", 0);
            System.err.println("Hiba a barátkérések számolása közben (Library): " + e.getMessage());
        }

        return "library";
    }
}