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

@Service
public class FriendshipServiceImpl implements FriendshipService {

    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;

    public FriendshipServiceImpl(FriendshipRepository friendshipRepository, UserRepository userRepository) {
        this.friendshipRepository = friendshipRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserSuggestDto> getSuggestableUsers(UserEntity currentUser) {
        List<UserEntity> suggestableUsers = friendshipRepository.findSuggestableUsers(currentUser.getId());

        return suggestableUsers.stream()
                .map(UserSuggestDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FriendRequestDto> getPendingRequests(UserEntity receiver) {
        List<FriendshipEntity> pendingRequests = friendshipRepository.findByReceiverAndStatus(
                receiver, FriendshipStatus.PENDING);

        return pendingRequests.stream()
                .map(FriendRequestDto::fromEntity)
                .collect(Collectors.toList());
    }
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

    public void rejectFriendRequest(UserEntity receiver, Long requestId) throws FriendshipException {
        FriendshipEntity request = friendshipRepository.findById(requestId)
                .orElseThrow(() -> new FriendshipException("Kérem nem található."));

        if (!request.getReceiver().equals(receiver)) {
            throw new FriendshipException("Nincs jogosultságod ehhez a kérelemhez.");
        }
        friendshipRepository.delete(request);
    }
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
    @Override
    @Transactional(readOnly = true)
    public int countPendingRequests(UserEntity receiver) {
        return friendshipRepository.countByReceiverAndStatus(receiver, FriendshipStatus.PENDING);
    }
}