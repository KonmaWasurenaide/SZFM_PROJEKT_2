package com.ggames.GGames.Service.Impl;

import com.ggames.GGames.Data.Entity.GameEntity;
import com.ggames.GGames.Data.Repository.GameRepository;
import com.ggames.GGames.Service.Dto.GameDto;
import com.ggames.GGames.Service.GameService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * A {@code GameService} interfész implementációja, amely kezeli a játékokkal kapcsolatos üzleti logikát.
 *
 * <p>Felelős a játékadatok adatbázisból való lekérdezéséért, DTO-vá alakításáért, valamint a játékok mentéséért és törléséért.</p>
 */
@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final ModelMapper modelMapper;

    /**
     * Visszaadja az összes játékot {@code GameDto} formátumban a publikus megjelenítéshez.
     *
     * @return Az elérhető játékok DTO listája.
     */
    @Override
    public List<GameDto> getAllGamesForDisplay() {
        List<GameEntity> entities = gameRepository.findAll();
        return entities.stream()
                .map(entity -> modelMapper.map(entity, GameDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Megkeres egy játék entitást az azonosítója (ID) alapján.
     *
     * @param id A keresett játék azonosítója.
     * @return Az {@code Optional} tartalmazza a {@code GameEntity}-t, ha megtalálható.
     */
    @Override
    public Optional<GameEntity> getGameById(Long id) {
        return gameRepository.findById(id);
    }

    /**
     * Megkeres egy játékot a neve alapján, és visszaadja azt {@code GameDto} formátumban.
     *
     * @param gameName A keresett játék neve.
     * @return Az {@code Optional} tartalmazza a {@code GameDto}-t, ha megtalálható.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<GameDto> getGameByName(String gameName) {
        Optional<GameEntity> entityOptional = gameRepository.findByName(gameName);
        return entityOptional.map(entity -> modelMapper.map(entity, GameDto.class));
    }

    /**
     * Tömegesen lekérdezi a játékokat azonosító lista alapján (pl. a felhasználói könyvtár számára),
     * és DTO-vá alakítja a megjelenítéshez.
     *
     * @param gameIds A keresett játékok azonosítóinak listája.
     * @return A kapcsolódó {@code GameDto} objektumok listája.
     */
    @Override
    @Transactional(readOnly = true)
    public List<GameDto> getGamesByIds(List<Long> gameIds) {
        List<GameEntity> entities = gameRepository.findAllById(gameIds);
        return entities.stream()
                .map(entity -> modelMapper.map(entity, GameDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Elment egy új játékot vagy frissíti a meglévőt.
     *
     * @param gameDto A mentendő vagy frissítendő {@code GameDto} objektum.
     */
    @Override
    @Transactional
    public void saveGame(GameDto gameDto) {
        GameEntity gameEntity = modelMapper.map(gameDto, GameEntity.class);
        gameRepository.save(gameEntity);
    }


    /**
     * Töröl egy játékot az azonosítója (ID) alapján.
     *
     * @param id A törlendő játék azonosítója.
     */
    @Override
    @Transactional
    public void deleteGame(Long id) {
        gameRepository.deleteById(id);
    }
}