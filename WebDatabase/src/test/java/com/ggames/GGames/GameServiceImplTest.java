package com.ggames.GGames;

import com.ggames.GGames.Data.Entity.GameEntity;
import com.ggames.GGames.Data.Repository.GameRepository;
import com.ggames.GGames.Service.Dto.GameDto;
import com.ggames.GGames.Service.Impl.GameServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceImplTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private GameServiceImpl gameService;

    private GameEntity entity1;
    private GameDto dto1;
    private GameEntity entity2;

    @BeforeEach
    void setUp() {
        entity1 = new GameEntity();
        entity1.setId(1L);
        entity1.setName("Cyberpunk 2077");

        dto1 = new GameDto();
        dto1.setId(1L);
        dto1.setName("Cyberpunk 2077");

        entity2 = new GameEntity();
        entity2.setId(2L);
        entity2.setName("The Witcher 3");
    }

    @Test
    @DisplayName("Összes játék lekérése: Sikeres, nem üres lista")
    void getAllGamesForDisplay_Success() {
        List<GameEntity> entityList = Arrays.asList(entity1, entity2);
        when(gameRepository.findAll()).thenReturn(entityList);

        when(modelMapper.map(entity1, GameDto.class)).thenReturn(dto1);
        when(modelMapper.map(entity2, GameDto.class)).thenReturn(new GameDto());

        List<GameDto> result = gameService.getAllGamesForDisplay();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Cyberpunk 2077", result.get(0).getName());

        verify(gameRepository, times(1)).findAll();
        verify(modelMapper, times(2)).map(any(GameEntity.class), eq(GameDto.class));
    }

    @Test
    @DisplayName("Összes játék lekérése: Üres repository")
    void getAllGamesForDisplay_EmptyList() {
        when(gameRepository.findAll()).thenReturn(Collections.emptyList());

        List<GameDto> result = gameService.getAllGamesForDisplay();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(gameRepository, times(1)).findAll();
        verify(modelMapper, never()).map(any(), any());
    }

    @Test
    @DisplayName("Játék lekérése ID alapján: Megtalálva")
    void getGameById_Found() {
        Long gameId = 1L;
        when(gameRepository.findById(gameId)).thenReturn(Optional.of(entity1));

        Optional<GameEntity> result = gameService.getGameById(gameId);

        assertTrue(result.isPresent());
        assertEquals(gameId, result.get().getId());
        verify(gameRepository, times(1)).findById(gameId);
    }

    @Test
    @DisplayName("Játék lekérése név alapján: Megtalálva és DTO-vá alakítva")
    void getGameByName_FoundAndMapped() {
        String name = "Cyberpunk 2077";
        when(gameRepository.findByName(name)).thenReturn(Optional.of(entity1));
        when(modelMapper.map(entity1, GameDto.class)).thenReturn(dto1);

        Optional<GameDto> result = gameService.getGameByName(name);

        assertTrue(result.isPresent());
        assertEquals(name, result.get().getName());
        verify(gameRepository, times(1)).findByName(name);
        verify(modelMapper, times(1)).map(entity1, GameDto.class);
    }

    @Test
    @DisplayName("Játék lekérése név alapján: Nem található")
    void getGameByName_NotFound() {
        String name = "NonExistentGame";
        when(gameRepository.findByName(name)).thenReturn(Optional.empty());

        Optional<GameDto> result = gameService.getGameByName(name);

        assertTrue(result.isEmpty());
        verify(gameRepository, times(1)).findByName(name);
        verify(modelMapper, never()).map(any(), any());
    }

    @Test
    @DisplayName("Játékok tömeges lekérése ID-k alapján: Sikeres és leképezés")
    void getGamesByIds_Success() {
        List<Long> ids = Arrays.asList(1L, 2L);
        List<GameEntity> entityList = Arrays.asList(entity1, entity2);

        when(gameRepository.findAllById(ids)).thenReturn(entityList);
        when(modelMapper.map(entity1, GameDto.class)).thenReturn(dto1);
        when(modelMapper.map(entity2, GameDto.class)).thenReturn(new GameDto());

        List<GameDto> result = gameService.getGamesByIds(ids);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());

        verify(gameRepository, times(1)).findAllById(ids);
        verify(modelMapper, times(2)).map(any(GameEntity.class), eq(GameDto.class));
    }

    @Test
    @DisplayName("Játék mentése: Sikeres DTO-ból Entity-be konverzió és mentés")
    void saveGame_Success() {
        when(modelMapper.map(dto1, GameEntity.class)).thenReturn(entity1);

        gameService.saveGame(dto1);

        verify(modelMapper, times(1)).map(dto1, GameEntity.class);
        verify(gameRepository, times(1)).save(entity1);
    }


    @Test
    @DisplayName("Játék törlése ID alapján: Sikeres hívás")
    void deleteGame_Success() {
        Long idToDelete = 5L;

        gameService.deleteGame(idToDelete);
        verify(gameRepository, times(1)).deleteById(idToDelete);
    }
}