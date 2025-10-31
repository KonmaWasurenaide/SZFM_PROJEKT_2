package com.ggames.GGames.Controller;

import com.ggames.GGames.Service.Dto.UserDto;
import com.ggames.GGames.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * A(z) {@code RegisterController} osztály a REST API vezérlője
 * a felhasználói fiókok regisztrációjának kezelésére.
 * <p>
 * Ez az osztály kezeli a bejövő HTTP POST kéréseket a regisztrációs végponton.
 * </p>
 */
@RestController
@RequiredArgsConstructor
public class RegisterController {

    /**
     * A {@link UserService} szolgáltatás az üzleti logika elvégzésére szolgál,
     * beleértve az új felhasználó regisztrálását és a validációt.
     */
    private final UserService userService;

    /**
     * Kezeli a felhasználói regisztrációra vonatkozó HTTP POST kéréseket.
     * <p>
     * Ha a regisztráció sikeres, {@code 201 Created} státuszkódot ad vissza.
     * Ha hiba történik a regisztráció során (pl. foglalt felhasználónév/e-mail, hibás jelszó),
     * {@code 400 Bad Request} státuszkódot ad vissza, a hibaüzenettel a válasz törzsében.
     * </p>
     *
     * @param userDto A regisztrációs adatokat (felhasználónév, e-mail, jelszó) tartalmazó {@link UserDto} objektum.
     * @return Egy {@link ResponseEntity}, amely tartalmazza a művelet sikerességét vagy hibaüzenetét.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto) {
        try {
            userService.registerUser(userDto);
            // 201 Created státusz, sikeres regisztráció esetén
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (RuntimeException e) {
            // 400 Bad Request státusz hiba esetén, a hibaüzenettel a törzsben
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}