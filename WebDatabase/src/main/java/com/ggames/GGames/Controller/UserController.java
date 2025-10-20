package com.ggames.GGames.Controller;

import com.ggames.GGames.Service.Dto.UserDto;
import com.ggames.GGames.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new UserDto());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") UserDto userDto, Model model) {
        try {
            userService.register(userDto); // mentés az adatbázisba
            return "redirect:/login?success"; // sikeres regisztráció
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage()); // hibaüzenet
            return "register"; // vissza a regisztrációs oldalra
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserEntity user) {
        boolean valid = userService.validateCredentials(user.getUsername(), user.getPassword());
        if (valid) {
            return ResponseEntity.ok().build();
        } else {
            // Always return generic error to avoid leaking info, its a best practice
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/admin/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminDashboard() {
        return "admin-dashboard";
    }
}
