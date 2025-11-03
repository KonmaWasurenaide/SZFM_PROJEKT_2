package com.ggames.GGames.Service.Impl;

import com.ggames.GGames.Data.Entity.UserEntity;
import com.ggames.GGames.Data.Repository.UserRepository;
import com.ggames.GGames.Service.Dto.UserDto;
import com.ggames.GGames.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * A(z) {@code UserServiceImpl} osztály a {@link UserService} interfész implementációja.
 * <p>
 * Ez a szolgáltatás (Service) felelős a felhasználói adatok kezelésével kapcsolatos
 * üzleti logikáért, beleértve a regisztrációt és a hitelesítést.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    /**
     * A {@link UserRepository} a felhasználói entitások adatbázisban történő
     * elérésére és mentésére szolgál.
     */
    private final UserRepository userRepository;

    /**
     * A {@link PasswordEncoder} a jelszavak biztonságos hashelésére és
     * a bejelentkezéskor történő összehasonlítására szolgál.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * A {@link ModelMapper} az entitások és DTO-k közötti adatátalakítást segíti.
     */
    private final ModelMapper modelMapper;

    /**
     * Regisztrál egy új felhasználót.
     * <p>
     * Végrehajtja a szükséges ellenőrzéseket (foglalt felhasználónév/e-mail,
     * jelszó erőssége), titkosítja a jelszót, és elmenti az új {@link UserEntity}-t.
     * </p>
     *
     * @param userDto A regisztrálandó felhasználó adatait tartalmazó {@link UserDto}.
     * @return A sikeresen regisztrált és elmentett {@link UserEntity}.
     * @throws RuntimeException Ha a felhasználónév vagy az e-mail cím már foglalt.
     */
    @Override
    public UserEntity registerUser(UserDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new RuntimeException("A felhasználónév már foglalt.");
        }
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new RuntimeException("Az email cím már foglalt.");
        }

        if (!isValidPassword(userDto.getPassword())) {
            throw new RuntimeException("A jelszónak legalább 8 karakter hosszúnak kell lennie, és tartalmaznia kell kisbetűt, nagybetűt, számot és speciális karaktert.");
        }

        UserEntity user = modelMapper.map(userDto, UserEntity.class);
        // Jelszó titkosítása mentés előtt
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Alapértelmezett szerepkör beállítása, ha hiányzik
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("USER");
        }
        return userRepository.save(user);
    }

    /**
     * Ellenőrzi a jelszó erősségi követelményeit.
     * <p>
     * Legalább 8 karakter hosszú, és tartalmaznia kell:
     * <ul>
     * <li>Nagybetűt (A-Z)</li>
     * <li>Kisbetűt (a-z)</li>
     * <li>Számot (0-9)</li>
     * <li>Speciális karaktert</li>
     * </ul>
     * </p>
     *
     * @param password Az ellenőrizendő jelszó.
     * @return {@code true}, ha a jelszó megfelel a követelményeknek; egyébként {@code false}.
     */
    private boolean isValidPassword(String password) {
        if (password == null || password.length() < 8) return false;

        boolean hasUpper = password.matches(".*[A-Z].*");
        boolean hasLower = password.matches(".*[a-z].*");
        boolean hasDigit = password.matches(".*[0-9].*");
        boolean hasSpecial = password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*");

        return hasUpper && hasLower && hasDigit && hasSpecial;
    }


    /**
     * Validates user credentials.
     * Returns true if username exists and password matches, false otherwise.
     * No details are shared to protect against spoofing.
     * <p>
     * Felhasználja az {@code org.mindrot.jbcrypt.BCrypt} könyvtárat
     * a nyers jelszó és a hashelt jelszó összehasonlításához.
     * </p>
     *
     * @param username The username to validate.
     * @param password The raw password to check against the stored hash.
     * @return {@code true} if the credentials are valid; {@code false} otherwise.
     */
    @Override
    public boolean validateCredentials(String username, String password) {
        return userRepository.findByUsername(username)
                .map(user -> org.mindrot.jbcrypt.BCrypt.checkpw(password, user.getPassword()))
                .orElse(false);
    }

}