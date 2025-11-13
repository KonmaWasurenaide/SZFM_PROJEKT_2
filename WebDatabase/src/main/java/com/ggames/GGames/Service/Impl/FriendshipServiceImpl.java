package com.ggames.GGames.Service.Impl;

import com.ggames.GGames.Service.Dto.FriendDto;
import com.ggames.GGames.Service.Dto.FriendRequestDto;
import com.ggames.GGames.Service.Dto.UserSuggestDto;
import com.ggames.GGames.Data.Entity.FriendshipEntity;
import com.ggames.GGames.Data.Entity.FriendshipStatus;
import com.ggames.GGames.Data.Entity.UserEntity;
import com.ggames.GGames.Data.Repository.FriendshipRepository;
import com.ggames.GGames.Data.Repository.UserRepository;
import com.ggames.GGames.Service.FriendshipException;
import com.ggames.GGames.Service.FriendshipService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A {@link FriendshipService} interfész implementációja, amely a felhasználók közötti baráti kapcsolatok üzleti logikáját kezeli.
 *
 * <p>Felelős a barátkérések küldéséért, elfogadásáért, elutasításáért, a barátok listázásáért,
 * és a barátsági státusz ellenőrzéséért.</p>
 */
@Service
public class FriendshipServiceImpl implements FriendshipService {

    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;

    /**
     * Konstruktor a {@code FriendshipServiceImpl} inicializálásához.
     *
     * @param friendshipRepository A baráti kapcsolatok adatbázis-műveleteihez használt repository.
     * @param userRepository A felhasználói entitások adatbázis-műveleteihez használt repository.
     */
    public FriendshipServiceImpl(FriendshipRepository friendshipRepository, UserRepository userRepository) {
        this.friendshipRepository = friendshipRepository;
        this.userRepository = userRepository;
    }

    /**
     * Lekéri azon felhasználók listáját, akikkel az aktuális felhasználó még nincs baráti kapcsolatban,
     * és nincs függőben lévő kérése sem a felhasználók között.
     *
     * @param currentUser Az aktuálisan bejelentkezett felhasználó entitása.
     * @return {@code UserSuggestDto} objektumok listája, mint javasolt barátok.
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserSuggestDto> getSuggestableUsers(UserEntity currentUser) {
        List<UserEntity> suggestableUsers = friendshipRepository.findSuggestableUsers(currentUser.getId());

        return suggestableUsers.stream()
                .map(UserSuggestDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Lekéri az összes függőben lévő (PENDING) barátkérést, amely az adott felhasználóhoz érkezett.
     *
     * @param receiver A címzett felhasználó entitása.
     * @return {@code FriendRequestDto} objektumok listája.
     */
    @Override
    @Transactional(readOnly = true)
    public List<FriendRequestDto> getPendingRequests(UserEntity receiver) {
        List<FriendshipEntity> pendingRequests = friendshipRepository.findByReceiverAndStatus(
                receiver, FriendshipStatus.PENDING);

        return pendingRequests.stream()
                .map(FriendRequestDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Barátkérést küld a megadott felhasználó felé.
     *
     * <p>Ellenőrzi, hogy a célfelhasználó létezik-e, hogy a küldő nem küld magának kérést,
     * és hogy nincs-e már meglévő (elfogadott vagy függőben lévő) baráti kapcsolat a felek között.</p>
     *
     * @param sender A kérést küldő felhasználó entitása.
     * @param receiverId A cél felhasználó azonosítója.
     * @return A létrehozott {@code FriendshipEntity} objektum.
     * @throws FriendshipException Ha a cél felhasználó nem található, magának küld a felhasználó,
     * vagy már létezik aktív/függőben lévő kapcsolat.
     */
    @Override
    @Transactional
    public FriendshipEntity sendFriendRequest(UserEntity sender, Long receiverId) throws FriendshipException {
        UserEntity receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new FriendshipException("A cél felhasználó nem található."));

        if (sender.getId().equals(receiver.getId())) {
            throw new FriendshipException("Saját magadnak nem küldhetsz barátkérelmet.");
        }

        Optional<FriendshipEntity> existingFriendship = friendshipRepository
                .findBySenderAndReceiverOrReceiverAndSender(sender, receiver, receiver, sender);

        if (existingFriendship.isPresent()) {
            FriendshipStatus status = existingFriendship.get().getStatus();
            if (status == FriendshipStatus.ACCEPTED) {
                throw new FriendshipException("Már barátok vagytok.");
            } else if (status == FriendshipStatus.PENDING) {
                throw new FriendshipException("Már létezik függőben lévő kérelem.");
            }
        }

        FriendshipEntity friendship = new FriendshipEntity();
        friendship.setSender(sender);
        friendship.setReceiver(receiver);
        friendship.setStatus(FriendshipStatus.PENDING);

        return friendshipRepository.save(friendship);
    }

    /**
     * Elfogadja a megadott, függőben lévő barátkérést.
     *
     * <p>Ellenőrzi, hogy a kérés létezik-e, hogy a bejelentkezett felhasználó a címzettje-e,
     * és hogy a kérés állapota valóban {@code PENDING} (függőben lévő) állapotú-e, majd {@code ACCEPTED} állapotra állítja.</p>
     *
     * @param receiver Az aktuálisan bejelentkezett felhasználó (aki elfogadja a kérést).
     * @param requestId Az elfogadandó barátkérés azonosítója.
     * @return A mentett {@code FriendshipEntity} objektum.
     * @throws FriendshipException Ha a kérés nem található, nincs jogosultság a kérelem kezelésére,
     * vagy a kérés állapota nem PENDING.
     */
    @Override
    @Transactional
    public FriendshipEntity acceptFriendRequest(UserEntity receiver, Long requestId) throws FriendshipException {
        FriendshipEntity request = friendshipRepository.findById(requestId)
                .orElseThrow(() -> new FriendshipException("A barátkérelem nem található."));

        if (!request.getReceiver().getId().equals(receiver.getId())) {
            throw new FriendshipException("Nincs jogosultságod ezt a kérelmet kezelni.");
        }

        if (request.getStatus() != FriendshipStatus.PENDING) {
            throw new FriendshipException("A kérelem állapota nem PENDING.");
        }

        request.setStatus(FriendshipStatus.ACCEPTED);
        return friendshipRepository.save(request);
    }

    /**
     * Elutasítja (és törli) a megadott barátkérést.
     *
     * <p>Ellenőrzi, hogy a kérés létezik-e, és hogy a bejelentkezett felhasználó a kérés címzettje-e,
     * majd törli a rekordot az adatbázisból.</p>
     *
     * @param receiver Az aktuálisan bejelentkezett felhasználó (aki elutasítja a kérést).
     * @param requestId Az elutasítandó barátkérés azonosítója.
     * @throws FriendshipException Ha a kérés nem található, vagy nincs jogosultság a kérelemhez.
     */
    @Override
    @Transactional
    public void rejectFriendRequest(UserEntity receiver, Long requestId) throws FriendshipException {
        FriendshipEntity request = friendshipRepository.findById(requestId)
                .orElseThrow(() -> new FriendshipException("Kérem nem található."));

        if (!request.getReceiver().equals(receiver)) {
            throw new FriendshipException("Nincs jogosultságod ehhez a kérelemhez.");
        }
        friendshipRepository.delete(request);
    }

    /**
     * Lekéri az aktuális felhasználó összes elfogadott barátjának listáját.
     *
     * <p>Egyesíti a felhasználó által küldött és a felhasználó által fogadott, {@code ACCEPTED} állapotú kapcsolatokat,
     * majd a másik fél {@code FriendDto} objektumát adja vissza.</p>
     *
     * @param currentUser Az aktuálisan bejelentkezett felhasználó.
     * @return {@code FriendDto} objektumok listája, a felhasználó barátait reprezentálva.
     */
    @Override
    @Transactional(readOnly = true)
    public List<FriendDto> getFriends(UserEntity currentUser) {
        Stream<FriendshipEntity> sentAccepted = friendshipRepository
                .findBySenderAndStatus(currentUser, FriendshipStatus.ACCEPTED).stream();

        Stream<FriendshipEntity> receivedAccepted = friendshipRepository
                .findByReceiverAndStatus(currentUser, FriendshipStatus.ACCEPTED).stream();

        return Stream.concat(sentAccepted, receivedAccepted)
                .map(f -> {
                    UserEntity friendUser = f.getSender().getId().equals(currentUser.getId()) ? f.getReceiver() : f.getSender();
                    return new FriendDto(friendUser.getId(), friendUser.getUsername());
                })
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Megszámolja, hány függőben lévő barátkérés érkezett az adott felhasználóhoz.
     *
     * @param receiver A címzett felhasználó entitása.
     * @return A függőben lévő kérések száma.
     */
    @Override
    @Transactional(readOnly = true)
    public int countPendingRequests(UserEntity receiver) {
        return friendshipRepository.countByReceiverAndStatus(receiver, FriendshipStatus.PENDING);
    }

    /**
     * Ellenőrzi, hogy létezik-e aktív (elfogadott) barátsági kapcsolat két felhasználó között.
     *
     * @param userId1 Az első felhasználó azonosítója.
     * @param userId2 A második felhasználó azonosítója.
     * @return {@code true}, ha a felek barátok; egyébként {@code false}.
     */
    @Override
    public boolean areFriends(Long userId1, Long userId2) {
        if (userId1.equals(userId2)) {
            return false;
        }
        return friendshipRepository.existsActiveFriendship(userId1, userId2);
    }
}