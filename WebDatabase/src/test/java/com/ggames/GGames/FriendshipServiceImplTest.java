package com.ggames.GGames;

import com.ggames.GGames.Data.Entity.FriendshipEntity;
import com.ggames.GGames.Data.Entity.FriendshipStatus;
import com.ggames.GGames.Data.Entity.UserEntity;
import com.ggames.GGames.Data.Repository.FriendshipRepository;
import com.ggames.GGames.Data.Repository.UserRepository;
import com.ggames.GGames.Service.Dto.FriendDto;
import com.ggames.GGames.Service.Dto.FriendRequestDto;
import com.ggames.GGames.Service.Dto.UserSuggestDto;
import com.ggames.GGames.Service.FriendshipException;
import com.ggames.GGames.Service.Impl.FriendshipServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FriendshipServiceImplTest {

    @Mock
    private FriendshipRepository friendshipRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private FriendshipServiceImpl friendshipService;

    private UserEntity alice;
    private UserEntity bob;
    private UserEntity charlie;
    private FriendshipEntity pendingFriendship;
    private FriendshipEntity acceptedFriendship;

    @BeforeEach
    void setUp() {
        alice = createUserEntity(1L, "Alice");
        bob = createUserEntity(2L, "Bob");
        charlie = createUserEntity(3L, "Charlie");

        pendingFriendship = createFriendshipEntity(10L, alice, bob, FriendshipStatus.PENDING);

        acceptedFriendship = createFriendshipEntity(12L, bob, alice, FriendshipStatus.ACCEPTED);
    }

    private UserEntity createUserEntity(Long id, String username) {
        UserEntity user = new UserEntity();
        user.setId(id);
        user.setUsername(username);
        return user;
    }
    private FriendshipEntity createFriendshipEntity(Long id, UserEntity sender, UserEntity receiver, FriendshipStatus status) {
        FriendshipEntity friendship = new FriendshipEntity();
        friendship.setId(id);
        friendship.setSender(sender);
        friendship.setReceiver(receiver);
        friendship.setStatus(status);
        return friendship;
    }

    @Test
    @DisplayName("Javasolt felhasználók lekérése: Sikeres DTO leképezés")
    void getSuggestableUsers_Success() {
        List<UserEntity> suggestableEntities = Arrays.asList(bob, charlie);

        when(friendshipRepository.findSuggestableUsers(alice.getId())).thenReturn(suggestableEntities);

        List<UserSuggestDto> result = friendshipService.getSuggestableUsers(alice);

        assertNotNull(result);
        assertEquals(2, result.size());

        List<String> userNames = result.stream().map(UserSuggestDto::getUsername).collect(Collectors.toList());
        assertTrue(userNames.contains("Bob"));
        assertTrue(userNames.contains("Charlie"));

        verify(friendshipRepository, times(1)).findSuggestableUsers(alice.getId());
    }

    @Test
    @DisplayName("Függőben lévő kérések lekérése: Sikeres DTO leképezés")
    void getPendingRequests_Success() {
        FriendshipEntity pendingFromCharlie = createFriendshipEntity(11L, charlie, alice, FriendshipStatus.PENDING);
        List<FriendshipEntity> pendingEntities = Arrays.asList(pendingFriendship, pendingFromCharlie);

        when(friendshipRepository.findByReceiverAndStatus(eq(bob), eq(FriendshipStatus.PENDING)))
                .thenReturn(pendingEntities);

        List<FriendRequestDto> result = friendshipService.getPendingRequests(bob);

        assertNotNull(result);
        assertEquals(2, result.size());

        List<String> senderNames = result.stream().map(FriendRequestDto::getSenderUsername).toList();
        assertTrue(senderNames.contains("Alice"));
        assertTrue(senderNames.contains("Charlie"));

        verify(friendshipRepository, times(1)).findByReceiverAndStatus(bob, FriendshipStatus.PENDING);
    }

    @Test
    @DisplayName("Barátkérés küldése: Sikeres")
    void sendFriendRequest_Success() throws FriendshipException {
        Long receiverId = bob.getId();

        when(userRepository.findById(receiverId)).thenReturn(Optional.of(bob));
        when(friendshipRepository.findBySenderAndReceiverOrReceiverAndSender(
                eq(alice), eq(bob), eq(bob), eq(alice))).thenReturn(Optional.empty());
        when(friendshipRepository.save(any(FriendshipEntity.class))).thenReturn(pendingFriendship);

        FriendshipEntity result = friendshipService.sendFriendRequest(alice, receiverId);

        assertNotNull(result);
        assertEquals(FriendshipStatus.PENDING, result.getStatus());
        verify(friendshipRepository, times(1)).save(any(FriendshipEntity.class));
    }

    @Test
    @DisplayName("Barátkérés küldése: Hiba - Már függőben lévő kérés")
    void sendFriendRequest_PendingExists_ThrowsException() {
        Long receiverId = bob.getId();

        when(userRepository.findById(receiverId)).thenReturn(Optional.of(bob));
        when(friendshipRepository.findBySenderAndReceiverOrReceiverAndSender(
                eq(alice), eq(bob), eq(bob), eq(alice))).thenReturn(Optional.of(pendingFriendship));

        FriendshipException exception = assertThrows(FriendshipException.class,
                () -> friendshipService.sendFriendRequest(alice, receiverId));

        assertEquals("Már létezik függőben lévő kérelem.", exception.getMessage());
        verify(friendshipRepository, never()).save(any());
    }

    @Test
    @DisplayName("Barátkérés küldése: Hiba - Saját magadnak")
    void sendFriendRequest_SelfRequest_ThrowsException() {
        Long receiverId = alice.getId();
        when(userRepository.findById(receiverId)).thenReturn(Optional.of(alice));

        FriendshipException exception = assertThrows(FriendshipException.class,
                () -> friendshipService.sendFriendRequest(alice, receiverId));

        assertEquals("Saját magadnak nem küldhetsz barátkérelmet.", exception.getMessage());

        verify(userRepository, times(1)).findById(receiverId);
        verify(friendshipRepository, never()).save(any());
    }

    @Test
    @DisplayName("Barátkérés elfogadása: Sikeres")
    void acceptFriendRequest_Success() throws FriendshipException {
        Long requestId = pendingFriendship.getId();

        when(friendshipRepository.findById(requestId)).thenReturn(Optional.of(pendingFriendship));

        when(friendshipRepository.save(any(FriendshipEntity.class))).thenAnswer(invocation -> {
            FriendshipEntity saved = invocation.getArgument(0);
            assertEquals(FriendshipStatus.ACCEPTED, saved.getStatus());
            return saved;
        });

        FriendshipEntity result = friendshipService.acceptFriendRequest(bob, requestId);

        assertEquals(FriendshipStatus.ACCEPTED, result.getStatus());
        verify(friendshipRepository, times(1)).save(any(FriendshipEntity.class));
    }

    @Test
    @DisplayName("Barátkérés elfogadása: Hiba - Rossz user próbálja elfogadni")
    void acceptFriendRequest_WrongUser_ThrowsException() {
        Long requestId = pendingFriendship.getId();

        when(friendshipRepository.findById(requestId)).thenReturn(Optional.of(pendingFriendship));

        FriendshipException exception = assertThrows(FriendshipException.class,
                () -> friendshipService.acceptFriendRequest(charlie, requestId));

        assertEquals("Nincs jogosultságod ezt a kérelmet kezelni.", exception.getMessage());
        verify(friendshipRepository, never()).save(any());
    }

    @Test
    @DisplayName("Barátkérés elutasítása: Sikeres törlés")
    void rejectFriendRequest_Success() throws FriendshipException {
        Long requestId = pendingFriendship.getId();

        when(friendshipRepository.findById(requestId)).thenReturn(Optional.of(pendingFriendship));

        friendshipService.rejectFriendRequest(bob, requestId);

        verify(friendshipRepository, times(1)).delete(pendingFriendship);
    }

    @Test
    @DisplayName("Barátok lekérése: Sikeresen egyesíti a küldött és fogadott kapcsolatokat")
    void getFriends_Success() {
        FriendshipEntity aliceToCharlie = createFriendshipEntity(13L, alice, charlie, FriendshipStatus.ACCEPTED);

        when(friendshipRepository.findBySenderAndStatus(eq(alice), eq(FriendshipStatus.ACCEPTED)))
                .thenReturn(Arrays.asList(aliceToCharlie));

        when(friendshipRepository.findByReceiverAndStatus(eq(alice), eq(FriendshipStatus.ACCEPTED)))
                .thenReturn(Arrays.asList(acceptedFriendship));

        List<FriendDto> result = friendshipService.getFriends(alice);

        assertNotNull(result);
        assertEquals(2, result.size());

        List<String> friendNames = result.stream().map(FriendDto::getUsername).toList();
        assertTrue(friendNames.contains("Bob"));
        assertTrue(friendNames.contains("Charlie"));

        verify(friendshipRepository, times(1)).findBySenderAndStatus(alice, FriendshipStatus.ACCEPTED);
        verify(friendshipRepository, times(1)).findByReceiverAndStatus(alice, FriendshipStatus.ACCEPTED);
    }
}