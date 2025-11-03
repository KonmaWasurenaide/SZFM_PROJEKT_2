package com.ggames.GGames.Controller;

import com.ggames.GGames.Data.Entity.UserEntity;
import com.ggames.GGames.Service.Dto.UserDto;
import com.ggames.GGames.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * A(z) {@code UserController} osztály a Spring MVC (Model-View-Controller) vezérlője
 * a felhasználói interakciók kezelésére, mint például a bejelentkezés, a navigáció
 * és az adminisztrációs felület elérése.
 * <p>
 * A {@code @Controller} annotációval van jelölve, ami azt jelenti, hogy HTML
 * nézeteket (View) ad vissza.
 * </p>
 */
@Controller
@RequiredArgsConstructor
public class UserController {

    /**
     * A {@link UserService} szolgáltatás az üzleti logika elvégzésére szolgál,
     * főként a felhasználói adatok hitelesítésére.
     */
    private final UserService userService;

    /**
     * Megjeleníti a bejelentkezési oldalt.
     * <p>
     * Ezt a végpontot hívja meg a rendszer, amikor a felhasználónak be kell jelentkeznie.
     * </p>
     *
     * @return A bejelentkezési nézet neve ("kuspgames").
     */
    @GetMapping("/login")
    public String loginPage() {
        return "kuspgames";
    }

    /**
     * Kezeli a hitelesítési kérelmet (bejelentkezés logikája).
     * <p>
     * **Fontos megjegyzés:** Bár ez a metódus ellenőrzi a hitelesítő adatokat,
     * a Spring Security konfigurációja ({@code SecurityConfig}) tipikusan
     * kezeli a tényleges session beállítást. Ezt a metódust valószínűleg csak
     * a puszta hitelesítés tesztelésére használják.
     * </p>
     *
     * @param user A hitelesítő adatokat (felhasználónév és jelszó) tartalmazó {@link UserEntity}.
     * @return {@code 200 OK} státuszt, ha az adatok érvényesek; {@code 401 Unauthorized} státuszt, ha nem.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserEntity user) {
        boolean valid = userService.validateCredentials(user.getUsername(), user.getPassword());
        if (valid) {
            return ResponseEntity.ok().build();
        } else {
            // Mindig általános hibaüzenetet ad vissza a hitelesítő adatok biztonságos kezelése érdekében.
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    /**
     * Megjeleníti az alapértelmezett kezdőoldalt (home page) bejelentkezett felhasználók számára.
     *
     * @return A kezdőlap nézet neve ("home").
     */
    @GetMapping("/home")
    public String home() {
        return "home";
    }

    /**
     * Megjeleníti az adminisztrációs műszerfalat.
     * <p>
     * Csak azokat a felhasználókat engedi be, akik rendelkeznek 'ADMIN' szerepkörrel
     * (ezt az {@code @PreAuthorize} annotáció biztosítja).
     * </p>
     *
     * @return Az adminisztrációs műszerfal nézetének neve ("admin-dashboard").
     */
    @GetMapping("/admin/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminDashboard() {
        return "admin-dashboard";
    }
}