package com.ggames.GGames.Data.Repository;

import com.ggames.GGames.Data.Entity.FriendshipEntity;
import com.ggames.GGames.Data.Entity.FriendshipStatus;
import com.ggames.GGames.Data.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FriendshipRepository extends JpaRepository<FriendshipEntity, Long> {

    Optional<FriendshipEntity> findBySenderAndReceiverOrReceiverAndSender(
            UserEntity user1, UserEntity user2, UserEntity user3, UserEntity user4);
    List<FriendshipEntity> findByReceiverAndStatus(UserEntity receiver, FriendshipStatus status);
    List<FriendshipEntity> findBySender(UserEntity sender);
    List<FriendshipEntity> findByReceiver(UserEntity receiver);
    List<FriendshipEntity> findBySenderAndStatus(UserEntity sender, FriendshipStatus status);
    @Query("SELECT u FROM UserEntity u WHERE u.id != :currentUserId AND u.id NOT IN (" +
            "    SELECT CASE WHEN f.sender.id = :currentUserId THEN f.receiver.id ELSE f.sender.id END " +
            "    FROM FriendshipEntity f " +
            "    WHERE (f.sender.id = :currentUserId OR f.receiver.id = :currentUserId) " +
            "    AND f.status IN ('ACCEPTED', 'PENDING')" +
            ")")
    List<UserEntity> findSuggestableUsers(@Param("currentUserId") Long currentUserId);
    int countByReceiverAndStatus(UserEntity receiver, FriendshipStatus status);
}