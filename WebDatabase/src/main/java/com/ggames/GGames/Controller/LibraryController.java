package com.ggames.GGames.Controller;

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
 * Ez a kontroller felelős a felhasználói fiókkal kapcsolatos tranzakciós műveletekért.
 */
@Controller
@RequiredArgsConstructor
public class LibraryController {

    private final GameService gameService;
    private final UserService userService;
    private final ModelMapper modelMapper;


    /**
     * Hozzáad egy játékot a felhasználó könyvtárához és elindítja a letöltést.
     * @param gameId A hozzáadandó játék azonosítója.
     * @param authentication A bejelentkezett felhasználó autentikációs objektuma.
     * @param redirectAttributes Az átirányítási attribútumok.
     * @return Átirányítás a letöltési linkre, vagy a bejelentkezési oldalra.
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

        return "library";
    }
}