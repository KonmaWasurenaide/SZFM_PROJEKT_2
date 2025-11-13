package com.ggames.GGames;

import com.ggames.GGames.Data.Entity.DownloadEntity;
import com.ggames.GGames.Data.Entity.GameEntity;
import com.ggames.GGames.Data.Entity.UserEntity;
import com.ggames.GGames.Data.Repository.DownloadRepository;
import com.ggames.GGames.Data.Repository.GameRepository;
import com.ggames.GGames.Data.Repository.UserRepository;
import com.ggames.GGames.Service.Dto.UserDto;
import com.ggames.GGames.Service.Impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private DownloadRepository downloadRepository;
    @Mock
    private GameRepository gameRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private UserDto userDto;
    private UserEntity userEntity;
    private GameEntity gameEntity;

    @BeforeEach
    void setUp() {
        userDto = new UserDto("testuser", "password123", "test@example.com", "USER");

        userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setUsername("testuser");
        userEntity.setPassword("hashedpassword");
        userEntity.setUserRole("USER");

        gameEntity = new GameEntity();
        gameEntity.setId(10L);
        gameEntity.setName("Test Game");
    }

    @Test
    @DisplayName("Sikeres regisztráció alapértelmezett szerepkörrel")
    void registerUser_Success() {
        // Állítsuk be a mock viselkedését
        when(userRepository.findByUsername(userDto.getUsername())).thenReturn(Optional.empty());
        when(modelMapper.map(userDto, UserEntity.class)).thenReturn(userEntity);
        when(passwordEncoder.encode(userEntity.getPassword())).thenReturn("hashedpassword");
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        UserEntity result = userService.registerUser(userDto);
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        assertEquals("hashedpassword", result.getPassword());
        assertEquals("USER", result.getUserRole());

        verify(userRepository, times(1)).findByUsername("testuser");
        verify(passwordEncoder, times(1)).encode(anyString());
        verify(userRepository, times(1)).save(userEntity);
    }

    @Test
    @DisplayName("Regisztráció hiba: Felhasználónév már foglalt")
    void registerUser_UsernameTaken_ThrowsException() {
        when(userRepository.findByUsername(userDto.getUsername())).thenReturn(Optional.of(userEntity));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.registerUser(userDto));
        assertEquals("Username is already taken.", exception.getMessage());

        verify(userRepository, times(1)).findByUsername("testuser");
        verify(userRepository, never()).save(any(UserEntity.class));
        verify(passwordEncoder, never()).encode(anyString());
    }

    @Test
    @DisplayName("Hitelesítés sikeres: Érvényes adatok")
    void validateCredentials_Success() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(userEntity));
        when(passwordEncoder.matches("password123", "hashedpassword")).thenReturn(true);

        boolean result = userService.validateCredentials("testuser", "password123");

        assertTrue(result);
        verify(userRepository, times(1)).findByUsername("testuser");
        verify(passwordEncoder, times(1)).matches("password123", "hashedpassword");
    }

    @Test
    @DisplayName("Hitelesítés sikertelen: Hibás jelszó")
    void validateCredentials_InvalidPassword() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(userEntity));
        when(passwordEncoder.matches("wrongpassword", "hashedpassword")).thenReturn(false);

        boolean result = userService.validateCredentials("testuser", "wrongpassword");

        assertFalse(result);
        verify(userRepository, times(1)).findByUsername("testuser");
        verify(passwordEncoder, times(1)).matches("wrongpassword", "hashedpassword");
    }

    @Test
    @DisplayName("Hitelesítés sikertelen: Nem létező felhasználó")
    void validateCredentials_UserNotFound() {
        when(userRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

        boolean result = userService.validateCredentials("nonexistent", "password123");

        assertFalse(result);
        verify(userRepository, times(1)).findByUsername("nonexistent");
        verifyNoInteractions(passwordEncoder);
    }

    @Test
    @DisplayName("Játék hozzáadása a könyvtárhoz sikeresen")
    void addGameToUserLibrary_Success_NewDownload() {
        Long gameId = 10L;

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(userEntity));
        when(gameRepository.findById(gameId)).thenReturn(Optional.of(gameEntity));
        when(downloadRepository.existsByUserAndGame(userEntity, gameEntity)).thenReturn(false);
        userService.addGameToUserLibrary("testuser", gameId);

        verify(userRepository, times(1)).findByUsername("testuser");
        verify(gameRepository, times(1)).findById(gameId);
        verify(downloadRepository, times(1)).existsByUserAndGame(userEntity, gameEntity);
        verify(downloadRepository, times(1)).save(argThat(dl ->
                dl.getUser().equals(userEntity) &&
                        dl.getGame().equals(gameEntity) &&
                        dl.getDownload_date().isEqual(LocalDate.now())
        ));
    }

    @Test
    @DisplayName("Játék hozzáadása a könyvtárhoz: Már letöltött")
    void addGameToUserLibrary_AlreadyDownloaded() {
        Long gameId = 10L;

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(userEntity));
        when(gameRepository.findById(gameId)).thenReturn(Optional.of(gameEntity));
        when(downloadRepository.existsByUserAndGame(userEntity, gameEntity)).thenReturn(true);

        userService.addGameToUserLibrary("testuser", gameId);

        verify(userRepository, times(1)).findByUsername("testuser");
        verify(gameRepository, times(1)).findById(gameId);
        verify(downloadRepository, times(1)).existsByUserAndGame(userEntity, gameEntity);
        verify(downloadRepository, never()).save(any(DownloadEntity.class));
    }

    @Test
    @DisplayName("Játék hozzáadása: Felhasználó nem található")
    void addGameToUserLibrary_UserNotFound_ThrowsException() {
        Long gameId = 10L;
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                userService.addGameToUserLibrary("testuser", gameId)
        );
        assertEquals("Felhasználó nem található: testuser", exception.getMessage());
        verify(userRepository, times(1)).findByUsername("testuser");
        verifyNoInteractions(gameRepository, downloadRepository);
    }

    @Test
    @DisplayName("Játék hozzáadása: Játék nem található")
    void addGameToUserLibrary_GameNotFound_ThrowsException() {
        Long gameId = 99L;
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(userEntity));
        when(gameRepository.findById(gameId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                userService.addGameToUserLibrary("testuser", gameId)
        );
        assertEquals("Játék nem található: ID 99", exception.getMessage());
        verify(userRepository, times(1)).findByUsername("testuser");
        verify(gameRepository, times(1)).findById(gameId);
        verifyNoInteractions(downloadRepository);
    }

    @Test
    @DisplayName("Felhasználó birtokolt játék ID-inak lekérése: Van letöltés")
    void getUserOwnedGameIds_HasDownloads() {
        DownloadEntity download1 = new DownloadEntity();
        GameEntity game1 = new GameEntity();
        game1.setId(100L);
        download1.setGame(game1);

        DownloadEntity download2 = new DownloadEntity();
        GameEntity game2 = new GameEntity();
        game2.setId(200L);
        download2.setGame(game2);

        List<DownloadEntity> downloads = List.of(download1, download2);

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(userEntity));
        when(downloadRepository.findByUser(userEntity)).thenReturn(downloads);

        List<Long> result = userService.getUserOwnedGameIds("testuser");

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.containsAll(List.of(100L, 200L)));

        verify(userRepository, times(1)).findByUsername("testuser");
        verify(downloadRepository, times(1)).findByUser(userEntity);
    }

    @Test
    @DisplayName("Felhasználó birtokolt játék ID-inak lekérése: Nincs letöltés")
    void getUserOwnedGameIds_NoDownloads() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(userEntity));
        when(downloadRepository.findByUser(userEntity)).thenReturn(Collections.emptyList());

        List<Long> result = userService.getUserOwnedGameIds("testuser");

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(userRepository, times(1)).findByUsername("testuser");
        verify(downloadRepository, times(1)).findByUser(userEntity);
    }

    @Test
    @DisplayName("Felhasználó keresése felhasználónév alapján: Sikeres")
    void findByUsername_Found() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(userEntity));

        Optional<UserEntity> result = userService.findByUsername("testuser");

        assertTrue(result.isPresent());
        assertEquals("testuser", result.get().getUsername());
        verify(userRepository, times(1)).findByUsername("testuser");
    }
}