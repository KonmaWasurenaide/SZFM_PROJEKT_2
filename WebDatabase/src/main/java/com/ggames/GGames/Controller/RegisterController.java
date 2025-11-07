package com.ggames.GGames.Controller;

import com.ggames.GGames.Service.Dto.UserDto;
import com.ggames.GGames.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Kezeli a felhasználói regisztrációs kéréseket, megjeleníti az űrlapot és feldolgozza az adatok mentését.
 *
 * <p>Ez a kontroller felelős a {@code /register} útvonal logikájáért.</p>
 */
@Controller
@RequiredArgsConstructor
public class RegisterController {

    private final UserService userService;

    /**
     * Megjeleníti a regisztrációs oldalt, és inicializálja a ModelAttribute-ot az űrlaphoz.
     *
     * @param model A Thymeleaf modell.
     * @return A "register" nézet neve.
     */
    @GetMapping("/register")
    public String registerPage(Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new UserDto());
        }
        return "register";
    }

    /**
     * Feldolgozza a POST kérést, regisztrálja az új felhasználót a megadott adatokkal.
     *
     * @param userDto A regisztrációs űrlapból származó adatátviteli objektum.
     * @param redirectAttributes Az átirányítási attribútumok a sikeres/sikertelen üzenetek átviteléhez.
     * @return Átirányítás a bejelentkezési oldalra sikeres regisztráció esetén, vagy vissza a regisztrációs űrlapra hiba esetén.
     */
    @PostMapping("/register")
    public String register(@ModelAttribute("user") UserDto userDto, RedirectAttributes redirectAttributes) {
        try {
            userService.registerUser(userDto);
            redirectAttributes.addFlashAttribute("success", true);
            return "redirect:/login";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("user", userDto);
            redirectAttributes.addFlashAttribute("globalError", e.getMessage());
            return "redirect:/register";
        }
    }
}