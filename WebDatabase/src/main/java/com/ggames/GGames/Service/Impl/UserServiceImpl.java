package com.ggames.GGames.Service.Impl;

import com.ggames.GGames.Data.Entity.DownloadEntity;
import com.ggames.GGames.Data.Entity.GameEntity;
import com.ggames.GGames.Data.Entity.UserEntity;
import com.ggames.GGames.Data.Repository.DownloadRepository;
import com.ggames.GGames.Data.Repository.FriendshipRepository;
import com.ggames.GGames.Data.Repository.GameRepository;
import com.ggames.GGames.Data.Repository.UserRepository;
import com.ggames.GGames.Service.Dto.UserDto;
import com.ggames.GGames.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * A {@code UserService} interfész implementációja, amely kezeli a felhasználói fiókokkal és a könyvtár logikájával kapcsolatos üzleti műveleteket.
 *
 * <p>Felelős a felhasználók regisztrációjáért, hitelesítéséért és a játékok felhasználói könyvtárhoz való hozzáadásáért/eltávolításáért.</p>
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final FriendshipRepository friendshipRepository;
    private final DownloadRepository downloadRepository;
    private final GameRepository gameRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    /**
     * Regex minta az erős jelszó ellenőrzésére.
     * Követelmények: legalább 8 karakter, tartalmaznia kell számot, kisbetűt, nagybetűt és speciális karaktert.
     */
    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?*~$^/|]).{8,}$";
    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    /**
     * Ellenőrzi, hogy a megadott jelszó megfelel-e az erősségi követelményeknek.
     *
     * @param password A vizsgálandó jelszó.
     * @throws RuntimeException Ha a jelszó nem felel meg a {@code PASSWORD_PATTERN} feltételeinek.
     */
    private void validatePasswordStrength(String password) {
        if (!pattern.matcher(password).matches()) {
            throw new RuntimeException(
                    "A jelszónak legalább 8 karakter hosszúnak kell lennie, és tartalmaznia kell legalább egy kisbetűt, egy nagybetűt, egy számot és egy speciális karaktert."
            );
        }
    }

    /**
     * Megkeres egy felhasználó entitást a bejelentkezési név alapján.
     *
     * @param username A felhasználónév.
     * @return A megtalált {@code UserEntity}.
     * @throws RuntimeException Ha a felhasználó nem található.
     */
    private UserEntity findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Felhasználó nem található: " + username));
    }

    /**
     * Regisztrál egy új felhasználót, hasheli a jelszót, és ellenőrzi a felhasználónév és email egyediségét.
     *
     * <p>A jelszó mentése előtt ellenőrzi a jelszó erősségét és beállítja az alapértelmezett "USER" szerepkört.</p>
     *
     * @param userDto A regisztrációs adatátviteli objektum.
     * @return Az elmentett {@code UserEntity}.
     * @throws RuntimeException Ha a felhasználónév vagy az email cím már foglalt.
     */
    @Override
    @Transactional
    public UserEntity registerUser(UserDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new RuntimeException("Username is already taken.");
        }

        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new RuntimeException("Az email cím már foglalt.");
        }

        validatePasswordStrength(userDto.getPassword());

        UserEntity user = modelMapper.map(userDto, UserEntity.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.getUserRole() == null || user.getUserRole().isEmpty()) {
            user.setUserRole("USER");
        }
        return userRepository.save(user);
    }

    /**
     * Hitelesíti a felhasználó belépési adatait.
     *
     * <p>Megkeresi a felhasználót a bejelentkezési név alapján, majd összehasonlítja a megadott jelszót a hash-elt jelszóval a {@code PasswordEncoder} segítségével.</p>
     *
     * @param username A felhasználónév.
     * @param password A jelszó (hash-elés előtti).
     * @return {@code true}, ha az adatok érvényesek; egyébként {@code false}.
     */
    @Override
    public boolean validateCredentials(String username, String password) {
        return userRepository.findByUsername(username)
                .map(user -> passwordEncoder.matches(password, user.getPassword()))
                .orElse(false);
    }

    /**
     * Hozzáad egy játékot a felhasználó könyvtárához, létrehozva a {@code DownloadEntity} bejegyzést.
     *
     * <p>A művelet csak akkor hajtódik végre, ha a játék létezik, és még nem szerepel a felhasználó könyvtárában.</p>
     *
     * @param username A felhasználó bejelentkezési neve.
     * @param gameId A könyvtárhoz adandó játék azonosítója.
     * @throws RuntimeException Ha a játék nem található.
     */
    @Override
    @Transactional
    public void addGameToUserLibrary(String username, Long gameId) {
        UserEntity user = findUserByUsername(username);

        GameEntity game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Játék nem található: ID " + gameId));

        boolean alreadyDownloaded = downloadRepository.existsByUserAndGame(user, game);

        if (!alreadyDownloaded) {
            DownloadEntity downloadEntry = new DownloadEntity();
            downloadEntry.setUser(user);
            downloadEntry.setGame(game);
            downloadEntry.setDownload_date(LocalDate.now());

            downloadRepository.save(downloadEntry);
        }
    }

    /**
     * Visszaadja a felhasználó által birtokolt (letöltött) játékok azonosítóit (ID-it) a könyvtár betöltéséhez.
     *
     * @param username A felhasználó bejelentkezési neve.
     * @return A birtokolt játékok azonosítóinak listája.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Long> getUserOwnedGameIds(String username) {
        UserEntity user = findUserByUsername(username);

        List<DownloadEntity> downloads = downloadRepository.findByUser(user);

        return downloads.stream()
                .map(d -> d.getGame().getId())
                .collect(Collectors.toList());
    }

    /**
     * Megkeres egy felhasználó entitást a bejelentkezési név alapján.
     *
     * @param username A keresett felhasználónév.
     * @return Az opcionális {@code UserEntity} objektum.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Megkeres egy felhasználó entitást az azonosítója alapján.
     *
     * @param id A keresett azonosító.
     * @return Az opcionális {@code UserEntity} objektum.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UserEntity> findById(Long id) {
        return userRepository.findById(id);
    }

}