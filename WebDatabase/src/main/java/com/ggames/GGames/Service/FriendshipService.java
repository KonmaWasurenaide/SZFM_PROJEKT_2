package com.ggames.GGames.Service;

import com.ggames.GGames.Service.Dto.FriendDto;
import com.ggames.GGames.Service.Dto.FriendRequestDto;
import com.ggames.GGames.Service.Dto.UserSuggestDto;
import com.ggames.GGames.Data.Entity.FriendshipEntity;
import com.ggames.GGames.Data.Entity.UserEntity;
import com.ggames.GGames.Service.FriendshipException;

import java.util.List;

public interface FriendshipService {

    FriendshipEntity sendFriendRequest(UserEntity sender, Long receiverId) throws FriendshipException;

    List<FriendRequestDto> getPendingRequests(UserEntity receiver);

    FriendshipEntity acceptFriendRequest(UserEntity receiver, Long requestId) throws FriendshipException;

    void rejectFriendRequest(UserEntity receiver, Long requestId) throws FriendshipException;

    List<UserSuggestDto> getSuggestableUsers(UserEntity currentUser);

    List<FriendDto> getFriends(UserEntity currentUser);

    int countPendingRequests(UserEntity receiver);
}