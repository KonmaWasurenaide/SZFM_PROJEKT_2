package com.ggames.GGames.Controller;

import com.ggames.GGames.Data.Entity.GameEntity;
import com.ggames.GGames.Data.Entity.UserEntity;
import com.ggames.GGames.Service.FriendshipService;
import com.ggames.GGames.Service.GameService;
import com.ggames.GGames.Service.Dto.GameDto;
import com.ggames.GGames.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

/**
 * Kezeli az adminisztrátori felülethez kapcsolódó kéréseket, lehetővé téve a Játék CRUD (Create, Read, Update, Delete) műveleteket.
 *
 * <p>Minden metódus csak ADMIN jogosultsággal érhető el a Spring Security {@code @PreAuthorize("hasAuthority('ROLE_ADMIN')")} annotációjával.</p>
 */
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    /**
     * Szolgáltatás a játékokkal kapcsolatos üzleti logikai műveletek kezelésére.
     */
    private final GameService gameService;

    /**
     * Segédosztály az Entity és DTO objektumok közötti konverzióhoz.
     */
    private final ModelMapper modelMapper;

    /**
     * Szolgáltatás a felhasználókkal kapcsolatos műveletek kezelésére.
     */
    private final UserService userService;

    /**
     * Szolgáltatás a barátsági kapcsolatokkal kapcsolatos műveletek kezelésére, beleértve a függőben lévő kérések számlálását.
     */
    private final FriendshipService friendshipService;

    /**
     * Megjeleníti az adminisztrátori panelt, amely tartalmazza az új játék hozzáadására szolgáló űrlapot
     * és a meglévő játékok listáját.
     *
     * <p>Ezenkívül betölti a bejelentkezett felhasználó függőben lévő barátkéréseinek számát az értesítések megjelenítéséhez.</p>
     *
     * @param model A Thymeleaf modell, amelybe az adatokat beállítjuk (pl. játéklapok, {@code GameEntity} űrlaphoz).
     * @param userDetails A Spring Security által injektált, bejelentkezett felhasználó adatai.
     * @return Az "admin/admin-games" nézet neve.
     */
    @GetMapping("/add-game")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String showAdminPanel(Model model, @AuthenticationPrincipal UserDetails userDetails) {

        if (!model.containsAttribute("game")) {
            model.addAttribute("game", new GameEntity());
        }
        model.addAttribute("games", gameService.getAllGamesForDisplay());

        if (userDetails != null) {
            try {
                UserEntity currentUser = userService.findByUsername(userDetails.getUsername())
                        .orElseThrow(() -> new RuntimeException("Hitelesített felhasználó nem található."));

                int pendingCount = friendshipService.countPendingRequests(currentUser);

                model.addAttribute("pendingRequestCount", pendingCount);

            } catch (Exception e) {
                model.addAttribute("pendingRequestCount", 0);
                System.err.println("Hiba a barátkérések számolása közben (Admin Panel): " + e.getMessage());
            }
        } else {
            model.addAttribute("pendingRequestCount", 0);
        }
        return "admin/admin-games";
    }

    /**
     * Elmenti vagy frissíti a játékot az adatbázisban a kapott űrlapadatok alapján.
     *
     * <p>A metódus elvégzi az űrlap validációját és hibakezelést is. Sikeres mentés esetén átirányít az adminisztrátori panelre.</p>
     *
     * @param gameEntity A feltöltött vagy szerkesztett játék entitás.
     * @param bindingResult Az űrlap binding eredménye (validációhoz).
     * @param model A Thymeleaf modell, hibakezelésre is használva.
     * @param redirectAttributes Az átirányítási attribútumok, üzenetek továbbítására.
     * @return Átirányítás az adminisztrátori panelre sikeres mentés esetén ("redirect:/admin/add-game"), vagy visszatérés az űrlaphoz hiba esetén ("admin/admin-games").
     */
    @PostMapping("/save-game")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String saveGame(@ModelAttribute("game") GameEntity gameEntity,
                           BindingResult bindingResult,
                           Model model,
                           RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "Hiba: Hibás vagy hiányzó űrlap adatok! Kérem, ellenőrizze a Dátum formátumát.");
            model.addAttribute("games", gameService.getAllGamesForDisplay());
            return "admin/admin-games";
        }

        try {
            GameDto gameDto = modelMapper.map(gameEntity, GameDto.class);
            gameService.saveGame(gameDto);

            redirectAttributes.addFlashAttribute("successMessage", "Játék sikeresen mentve/frissítve!");
            return "redirect:/admin/add-game";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Adatbázis hiba történt a mentés során: " + e.getMessage());
            redirectAttributes.addFlashAttribute("game", gameEntity);

            return "redirect:/admin/add-game";
        }
    }

    /**
     * Betölti a kiválasztott játékot a szerkesztő űrlapba az azonosító (ID) alapján.
     *
     * @param id A szerkesztendő játék egyedi azonosítója.
     * @param model A Thymeleaf modell, amelybe a játékot beállítjuk.
     * @param redirectAttributes Az átirányítási attribútumok, hibakezelésre használva.
     * @return Visszatérés az "admin/admin-games" nézetre a kitöltött űrlappal, vagy átirányítás hibás ID esetén.
     */
    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String editGame(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {

        Optional<GameEntity> gameOptional = gameService.getGameById(id);

        if (gameOptional.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Hiba: A játék nem található a szerkesztéshez.");
            return "redirect:/admin/add-game";
        }

        model.addAttribute("game", gameOptional.get());
        model.addAttribute("games", gameService.getAllGamesForDisplay());

        return "admin/admin-games";
    }

    /**
     * Törli a játékot az azonosító (ID) alapján.
     *
     * <p>A műveletet tranzakciósan hajtja végre, majd átirányít az adminisztrátori panelre.</p>
     *
     * @param id A törlendő játék egyedi azonosítója.
     * @param redirectAttributes Az átirányítási attribútumok, üzenetek továbbítására.
     * @return Átirányítás az adminisztrátori panelre ("redirect:/admin/add-game").
     */
    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deleteGame(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            gameService.deleteGame(id);
            redirectAttributes.addFlashAttribute("successMessage", "Játék sikeresen törölve.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Hiba a játék törlése során: " + e.getMessage());
        }
        return "redirect:/admin/add-game";
    }
}