package com.ggames.GGames.Controller;

import com.ggames.GGames.Data.Entity.GameEntity;
import com.ggames.GGames.Service.GameService;
import com.ggames.GGames.Service.Dto.GameDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

/**
 * Kezeli az adminisztrátori felülethez kapcsolódó kéréseket, lehetővé téve a Játék CRUD (Create, Read, Update, Delete) műveleteket.
 *
 * <p>Minden metódus csak ADMIN jogosultsággal érhető el.</p>
 */
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final GameService gameService;
    private final ModelMapper modelMapper;

    /**
     * Megjeleníti az adminisztrátori panelt, amely tartalmazza az új játék hozzáadására szolgáló űrlapot
     * és a meglévő játékok listáját.
     *
     * @param model A Thymeleaf modell.
     * @return Az "admin/admin-games" nézet neve.
     */
    @GetMapping("/add-game")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String showAdminPanel(Model model) {
        if (!model.containsAttribute("game")) {
            model.addAttribute("game", new GameEntity());
        }
        model.addAttribute("games", gameService.getAllGamesForDisplay());
        return "admin/admin-games";
    }

    /**
     * Elmenti vagy frissíti a játékot az adatbázisban a kapott űrlapadatok alapján.
     *
     * @param gameEntity A feltöltött vagy szerkesztett játék entitás.
     * @param bindingResult Az űrlap binding eredménye (validációhoz).
     * @param model A Thymeleaf modell.
     * @param redirectAttributes Az átirányítási attribútumok.
     * @return Átirányítás az adminisztrátori panelre sikeres mentés esetén, vagy visszatérés az űrlaphoz hiba esetén.
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
     * @param id A szerkesztendő játék azonosítója.
     * @param model A Thymeleaf modell.
     * @param redirectAttributes Az átirányítási attribútumok.
     * @return Visszatérés az "admin/admin-games" nézetre a kitöltött űrlappal.
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
     * @param id A törlendő játék azonosítója.
     * @param redirectAttributes Az átirányítási attribútumok.
     * @return Átirányítás az adminisztrátori panelre.
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