package com.ggames.GGames.Controller;

import com.ggames.GGames.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * A {@code LoginController} osztály a Spring MVC vezérlője
 * a bejelentkezési és kijelentkezési folyamatok kezelésére.
 */
@Controller
@RequiredArgsConstructor
public class LoginController {

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}
