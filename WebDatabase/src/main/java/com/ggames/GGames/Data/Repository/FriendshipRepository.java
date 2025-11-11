package com.ggames.GGames.Data.Repository;

import com.ggames.GGames.Data.Entity.FriendshipEntity;
import com.ggames.GGames.Data.Entity.FriendshipStatus;
import com.ggames.GGames.Data.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendshipRepository extends JpaRepository<FriendshipEntity, Long> {

    Optional<FriendshipEntity> findBySenderAndReceiverOrReceiverAndSender(
            UserEntity user1, UserEntity user2, UserEntity user3, UserEntity user4);
    List<FriendshipEntity> findByReceiverAndStatus(UserEntity receiver, FriendshipStatus status);
    List<FriendshipEntity> findBySender(UserEntity sender);
    List<FriendshipEntity> findByReceiver(UserEntity receiver);
    List<FriendshipEntity> findBySenderAndStatus(UserEntity sender, FriendshipStatus status);
}